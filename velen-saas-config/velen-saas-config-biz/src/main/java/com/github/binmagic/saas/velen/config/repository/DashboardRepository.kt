package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.Dashboard
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface DashboardRepository : BaseRepository<Dashboard> {

    fun findByAppIdAndType(appId: String, type: String): Flux<Dashboard>

    fun findByAppId(appId: String): Flux<Dashboard>
}