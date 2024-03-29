package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.entity.Dashboard
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DashboardService {

    suspend fun getDashboards(appId : String) : Flux<Dashboard>

    suspend fun getDashboardByType(appId : String, type : String) : Flux<Dashboard>

    suspend fun getDashboardById(id:String) : Mono<Dashboard>

    suspend fun createDashboard(dashboard: Dashboard) : Mono<Dashboard>

    suspend fun updateDashboard(dashboard: Dashboard) : Mono<Dashboard>

    suspend fun deleteDashboardById(id : String) : Mono<Void>

    suspend fun findAllDashboard(appId: String): Flux<Dashboard>
}