package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.entity.ParserRule
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ParserRuleService {
    suspend fun getEventRule(appId: String,type:String): Flux<ParserRule>

    suspend fun addEventRule(parserRule: ParserRule): Mono<ParserRule>

    suspend fun updateEventRule(parserRule: ParserRule): Mono<ParserRule>

    suspend fun deleteEventRule(id: String): Mono<Void>
}