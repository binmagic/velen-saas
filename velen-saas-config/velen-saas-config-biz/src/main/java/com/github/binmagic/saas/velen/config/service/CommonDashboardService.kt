package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.entity.CommonDashboard
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CommonDashboardService {

    suspend fun getCommonDashboardServiceByType(commonType:String):Flux<CommonDashboard>

}