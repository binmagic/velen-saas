package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.dto.CommonGroupDashboardDTO
import com.github.binmagic.saas.velen.config.entity.CommonGroup
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CommonGroupService {

    suspend fun getCommonGroupByAppId(appId:String) : Flux<CommonGroup>

}