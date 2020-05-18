package com.github.binmagic.saas.velen.authority.repository

import com.github.binmagic.saas.velen.authority.entity.RoleUser
import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface RoleUserRepository : BaseRepository<RoleUser>{

    fun findByUserId(userId : String): Flux<RoleUser>
}