package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.dto.ShareDashboardDTO
import com.github.binmagic.saas.velen.config.entity.ShareDashboard

interface ShareDashboardService {
    suspend fun getShareDashboardByUserIdAndAppId(userId: String,appId: String) : List<ShareDashboard>

    suspend fun getShareDashboardByGroupId(groupId: String) :List<ShareDashboardDTO>

    suspend fun createShareDashboard(shareDashboard: ShareDashboard)

    suspend fun upDateShareDashboard(shareDashboard: ShareDashboard)
}