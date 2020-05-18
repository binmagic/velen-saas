package com.github.binmagic.saas.velen.authority.repository

import com.github.binmagic.saas.velen.authority.entity.Resource
import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ResourceRepository : BaseRepository<Resource>{

    fun findByIdIn(ids : List<String>): Flux<Resource>

}