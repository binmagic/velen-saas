package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.dto.CommonGroupDashboardDTO
import reactor.core.publisher.Mono

interface CommonGroupService {

    suspend fun getCommonGroupByAppId(appId:String) : Mono<List<CommonGroupDashboardDTO>>

}