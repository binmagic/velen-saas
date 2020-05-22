package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.entity.Dashboard
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DashboardService {

    suspend fun getDashboardService() : Flux<Dashboard>

    suspend fun getDashboardByType(type : String) : List<Dashboard>

    suspend fun createDashboard(dashboard: Dashboard)

    suspend fun updateDashboard(dashboard: Dashboard)

    suspend fun deleteDashboardById(id : String)
}