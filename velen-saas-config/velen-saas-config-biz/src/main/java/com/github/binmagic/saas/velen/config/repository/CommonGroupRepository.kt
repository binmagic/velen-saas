package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.CommonGroup
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface CommonGroupRepository : BaseRepository<CommonGroup> {

    fun findByAppId(appId: String) : Flux<CommonGroup>


    fun countByAppId(appId: String) : Mono<Long>
}