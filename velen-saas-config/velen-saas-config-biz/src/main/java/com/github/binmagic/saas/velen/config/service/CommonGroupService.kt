package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.dto.CommonGroupDTO
import reactor.core.publisher.Mono

interface CommonGroupService {

    suspend fun getCommonGroupByAppId(appId:String) : Mono<List<CommonGroupDTO>>

}