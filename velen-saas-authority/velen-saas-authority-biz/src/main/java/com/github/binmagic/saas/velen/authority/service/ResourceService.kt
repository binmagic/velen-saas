package com.github.binmagic.saas.velen.authority.service

import com.github.binmagic.saas.velen.authority.entity.Resource
import com.github.binmagic.saas.velen.common.entity.Page
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ResourceService {

    suspend fun findPage(query: Page.Query): Mono<Page.Result<Resource>>

    suspend fun createResource(resource : Resource): Mono<Resource>

    suspend fun updateResource(resource : Resource): Mono<Resource>

    suspend fun deleteResource(id: String): Mono<Void>

    suspend fun getResource(resourceIds : List<String>): Flux<Resource>
}