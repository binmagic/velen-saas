package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.entity.MetaEventProp
import reactor.core.publisher.Mono

interface MetadataService {

    suspend fun findMetaEventPropByPage(query : Page.Query) : Mono<Page.Result<MetaEventProp>>
}