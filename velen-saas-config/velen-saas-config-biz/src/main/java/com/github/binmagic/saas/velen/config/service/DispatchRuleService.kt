package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.entity.DispatchRule
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DispatchRuleService {

    suspend fun getAll(): Flux<DispatchRule>

    suspend fun findPage(query: Page.Query): Mono<Page.Result<DispatchRule>>

    suspend fun create(dispatchRule: DispatchRule, appId: String): Mono<DispatchRule>

    suspend fun update(dispatchRule: DispatchRule): Mono<DispatchRule>

    suspend fun fast(dispatchRule: DispatchRule): Mono<Void>
}