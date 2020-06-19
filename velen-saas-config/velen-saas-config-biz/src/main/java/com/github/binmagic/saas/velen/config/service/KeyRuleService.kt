package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.entity.KeyRule
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface KeyRuleService {
    suspend fun getKeyRule(type: String): Flux<KeyRule>

    suspend fun insertKeyRule(keyRule: KeyRule): Mono<KeyRule>

    suspend fun updateKeyRule(keyRule: KeyRule): Mono<KeyRule>

    suspend fun deleteKeyRule(id: String): Mono<Void>
}