package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.entity.Bookmarks
import com.github.binmagic.saas.velen.config.entity.MetaEventProp
import com.github.binmagic.saas.velen.config.repository.MetaEventPropRepository
import com.github.binmagic.saas.velen.config.repository.MetaEventRepository
import com.github.binmagic.saas.velen.config.service.MetadataService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.ArrayList

@Service
class MetadataServiceImpl : MetadataService {


    @Autowired
    lateinit var metaEventPropRepository: MetaEventPropRepository

    @Autowired
    lateinit var metaEventRepository: MetaEventRepository


    override suspend fun findMetaEventPropByPage(query: Page.Query): Mono<Page.Result<MetaEventProp>> {

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

        val example = Example.of(MetaEventProp.EMPTY, exampleMatcher)

        val total = metaEventPropRepository.count(example).awaitSingle()

        val items = metaEventPropRepository.findAll(example, pageRequest).collectList().awaitSingle()

        return Page.Result(total, items).toMono()
    }


}