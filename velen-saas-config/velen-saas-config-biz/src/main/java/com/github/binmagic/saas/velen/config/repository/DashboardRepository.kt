package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.Dashboard
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface DashboardRepository : BaseRepository<Dashboard> {

    fun findByType(type : String): List<Dashboard>

    fun findDashboardById(id:String) :Dashboard
}