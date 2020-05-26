package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.entity.CommonDashboard
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CommonDashboardService {

    suspend fun getCommonDashboardServiceByType(commonType:String):Flux<CommonDashboard>

    suspend fun createCommonDashboard(commonDashboard: CommonDashboard) : Mono<CommonDashboard>

    suspend fun updateCommonDashboard(commonDashboard: CommonDashboard) : Mono<CommonDashboard>

    suspend fun deleteCommonDashboardById(id:String) : Mono<Void>

}