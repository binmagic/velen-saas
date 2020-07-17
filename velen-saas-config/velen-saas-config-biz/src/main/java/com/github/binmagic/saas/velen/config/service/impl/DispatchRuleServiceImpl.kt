package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.entity.DispatchRule
import com.github.binmagic.saas.velen.config.repository.DispatchRuleRepository
import com.github.binmagic.saas.velen.config.service.DispatchRuleService
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDateTime

@Service
class DispatchRuleServiceImpl : DispatchRuleService {

    @Autowired
    lateinit var dispatchRuleRepository: DispatchRuleRepository

    override suspend fun getAll(): Flux<DispatchRule> {
        return dispatchRuleRepository.findAll()
    }

    override suspend fun findPage(query: Page.Query): Mono<Page.Result<DispatchRule>> {
        val orderList: MutableList<Sort.Order> = ArrayList(query.sorts.size)

        for ((key, value) in query.sorts) {
            orderList.add(Sort.Order(Sort.Direction.valueOf(value!!), key))
        }

        var exampleMatcher = ExampleMatcher.matchingAll()

        if (query.params.isNotEmpty()) {
            exampleMatcher = ExampleMatcher.matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase(true)
            for ((key) in query.params) {
                exampleMatcher.withMatcher(key, ExampleMatcher.GenericPropertyMatchers.contains())
            }
        }

        val pageRequest = PageRequest.of(query.page - 1, query.limit, Sort.by(orderList))

        val example = Example.of(DispatchRule.EMPTY, exampleMatcher)

        val total = dispatchRuleRepository.count(example).awaitSingle()

        val items = dispatchRuleRepository.findAll(example, pageRequest).collectList().awaitSingle()

        return Page.Result(total, items).toMono()
    }

    override suspend fun create(dispatchRule: DispatchRule): Mono<DispatchRule> {

        dispatchRule.createTime = LocalDateTime.now()
        dispatchRule.updateTime = LocalDateTime.now()
        val item = dispatchRuleRepository.findByBusinessName(dispatchRule.businessName).awaitFirstOrNull()
        if (item != null){
            return Mono.error(RuntimeException("业务名重复"))
        }

        return dispatchRuleRepository.insert(dispatchRule)
    }

    override suspend fun update(dispatchRule: DispatchRule): Mono<DispatchRule> {

        dispatchRule.updateTime = LocalDateTime.now()

        return dispatchRuleRepository.save(dispatchRule)
    }
}