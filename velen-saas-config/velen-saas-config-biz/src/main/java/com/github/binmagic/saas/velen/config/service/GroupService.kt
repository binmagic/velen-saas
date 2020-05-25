package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.dto.GroupDashboardDTO
import com.github.binmagic.saas.velen.config.entity.Group
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface GroupService {
    suspend fun getGroupByUserIdAndAppId(userId : String , appId : String) : Flux<Group>

    suspend fun createGroup(group: Group) : Mono<Group>

    suspend fun updateGroup(group: Group) : Mono<Group>

    suspend fun deleteGroupById(id: String) : Mono<Void>

    suspend fun countGroupByUserIdAndAppId(userId: String,appId: String) :Mono<Long>
}