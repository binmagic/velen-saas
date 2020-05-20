package com.github.binmagic.saas.velen.authority.repository

import com.github.binmagic.saas.velen.authority.entity.RoleResource
import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface RoleResourceRepository : BaseRepository<RoleResource> {

    fun findByRoleIdIn(roleIds : List<String>): Flux<RoleResource>

    fun findAllByResourceIdIn(resourceIds: List<String>): Flux<RoleResource>

    fun deleteByRoleIdAndResourceId(userId : String, resourceId : String) : Mono<Void>

}