package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.entity.CheckRule
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CheckRuleService {

    suspend fun getCheckRule(appId:String,type: String): Flux<CheckRule>

    suspend fun updateCheckRule(checkRule: CheckRule) : Mono<CheckRule>

    suspend fun insertCheckRule(checkRule: CheckRule) :Mono<CheckRule>

    suspend fun deleteCheckRule(id:String) :Mono<Void>

    suspend fun findAllCheckRule(appId: String):Flux<CheckRule>
}