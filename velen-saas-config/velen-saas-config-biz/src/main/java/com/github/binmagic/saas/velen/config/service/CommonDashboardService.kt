package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.dto.CommonGroupDTO
import com.github.binmagic.saas.velen.config.entity.CommonDashboard
import reactor.core.publisher.Mono

interface CommonDashboardService {

    suspend fun getCommonDashboardServiceByType(commonType:String):List<CommonDashboard>

}