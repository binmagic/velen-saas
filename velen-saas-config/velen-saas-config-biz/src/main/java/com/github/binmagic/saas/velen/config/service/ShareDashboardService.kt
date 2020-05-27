package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.dto.ShareDashboardDTO
import com.github.binmagic.saas.velen.config.entity.ShareDashboard
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ShareDashboardService {

    suspend fun getShareDashboardByUserIdAndAppId(userId: String,appId: String) : Flux<ShareDashboard>

    suspend fun getShareDashboardByType(type: String) :Flux<ShareDashboard>

    suspend fun createShareDashboard(shareDashboard: ShareDashboard) : Mono<ShareDashboard>

    suspend fun upDateShareDashboard(shareDashboard: ShareDashboard) : Mono<ShareDashboard>
}