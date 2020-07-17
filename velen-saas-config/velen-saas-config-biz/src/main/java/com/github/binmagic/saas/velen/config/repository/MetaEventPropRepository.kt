package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.MetaEventProp
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface MetaEventPropRepository : BaseRepository<MetaEventProp>{

    fun findByAppId(appId : String) : Flux<MetaEventProp>

}