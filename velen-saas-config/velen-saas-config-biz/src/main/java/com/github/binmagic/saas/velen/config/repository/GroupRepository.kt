package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.Group
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface GroupRepository : BaseRepository<Group> {

    fun findByUserIdAndAppId(userId: String, appId: String): Flux<Group>

    fun countByUserIdAndAppIdAndIsPublic(userId: String, appId: String, isPublic: Int): Mono<Long>

    fun countByAppIdAndIsPublic(appId: String, isPublic: Int): Mono<Long>

    fun findByUserIdAndAppIdAndIsPublic(userId: String, appId: String, isPublic: Int): Flux<Group>

    fun findByAppIdAndIsPublic(appId: String, isPublic: Int): Flux<Group>

    fun findByAppId(appId: String): Flux<Group>
}