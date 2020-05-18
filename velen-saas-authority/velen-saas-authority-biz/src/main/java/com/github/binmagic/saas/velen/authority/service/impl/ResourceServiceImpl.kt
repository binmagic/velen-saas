package com.github.binmagic.saas.velen.authority.service.impl

import com.github.binmagic.saas.velen.authority.entity.Resource
import com.github.binmagic.saas.velen.authority.repository.ResourceRepository
import com.github.binmagic.saas.velen.authority.service.ResourceService
import com.github.binmagic.saas.velen.common.entity.Page
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class ResourceServiceImpl : ResourceService {


    @Autowired
    lateinit var resourceRepository: ResourceRepository

    override suspend fun findPage(query: Page.Query): Mono<Page.Result<Resource>> {

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

        val example = Example.of(Resource.EMPTY, exampleMatcher)

        val total = resourceRepository.count().awaitSingle()

        val items = resourceRepository.findAll(example, pageRequest).collectList().awaitSingle()

        return Mono.just(Page.Result(total, items))
    }

    override suspend fun createResource(resource: Resource): Mono<Resource> {
        return resourceRepository.insert(resource)
    }

    override suspend fun updateResource(resource: Resource): Mono<Resource> {
        return resourceRepository.save(resource)
    }

    override suspend fun deleteResource(id: String): Mono<Void> {
        return resourceRepository.deleteById(id)
    }

    override suspend fun getResource(resourceIds: List<String>): Flux<Resource> {

        return resourceRepository.findByIdIn(resourceIds)
    }

}