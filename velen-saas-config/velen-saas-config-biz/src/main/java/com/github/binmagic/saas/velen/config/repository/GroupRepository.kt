package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.Group
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface GroupRepository:BaseRepository<Group> {

    fun findByUserIdAndAppId(userId : String, appId : String) : Flux<Group>

    fun countByUserIdAndAppId(userId: String,appId: String) : Mono<Long>

}