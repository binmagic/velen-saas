package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.entity.MetaEvent
import com.github.binmagic.saas.velen.config.entity.MetaEventProp
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MetadataService {

    suspend fun findMetaEventPropByPage(query: Page.Query, appId: String): Mono<Page.Result<MetaEventProp>>

    suspend fun createMetaEventProp(metaEventProp: MetaEventProp): Mono<MetaEventProp>

    suspend fun findMetaEventByPage(query: Page.Query, appId: String, user: String): Mono<Page.Result<MetaEvent>>

    suspend fun createMetaEvent(metaEvent: MetaEvent): Mono<MetaEvent>

    suspend fun updateMetaEvent(metaEvent: MetaEvent): Mono<MetaEvent>

    suspend fun deleteMetaEvent(id: String): Mono<Void>

    suspend fun updateMetaEventProp(metaEventProp: MetaEventProp): Mono<MetaEventProp>

    suspend fun deleteMetaEventProp(appId: String, id: String): Mono<Void>

    suspend fun all(appId: String,user: String):Mono<Void>
}