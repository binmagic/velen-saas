package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.dto.GroupDTO
import com.github.binmagic.saas.velen.config.entity.Group
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface GroupService {
    suspend fun getGroupByUserIdAndAppId(userId : String , appId : String) : Mono<List<GroupDTO>>

    suspend fun createGroup(group: Group)

    suspend fun updateGroup(group: Group)

    suspend fun deleteGroupById(id: String)
}