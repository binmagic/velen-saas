package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.dto.ShareDashboardDTO
import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.entity.ShareDashboard
import com.github.binmagic.saas.velen.config.repository.DashboardRepository
import com.github.binmagic.saas.velen.config.repository.ShareDashboardRepository
import com.github.binmagic.saas.velen.config.service.ShareDashboardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShareDashboardServiceImpl : ShareDashboardService {
    @Autowired
    lateinit var shareDashboardRepository: ShareDashboardRepository

    @Autowired
    lateinit var dashboardRepository: DashboardRepository

    override suspend fun getShareDashboardByUserIdAndAppId(userId: String, appId: String): List<ShareDashboard> {
      return shareDashboardRepository.findByUserIdAndAppId(userId, appId)
    }

    override suspend fun getShareDashboardByGroupId(groupId: String): List<ShareDashboardDTO> {
        val shareList:MutableList<ShareDashboard> = shareDashboardRepository.findByGroupId(groupId) as MutableList<ShareDashboard>
        val list:MutableList<ShareDashboardDTO> = mutableListOf<ShareDashboardDTO>()
        for (share: ShareDashboard in shareList){
            val dashboard: Dashboard = dashboardRepository.findDashboardById(share.dashboardId)
            val shareDashboardDTO= ShareDashboardDTO()
            shareDashboardDTO.id=share.id
            shareDashboardDTO.appId=share.appId
            shareDashboardDTO.createTime=share.createTime
            shareDashboardDTO.dashboard=dashboard
            shareDashboardDTO.groupId=share.groupId
            shareDashboardDTO.sort=share.sort
            shareDashboardDTO.shareUserId=share.shareUserId
            shareDashboardDTO.userId=share.userId
            list.add(shareDashboardDTO)
        }
        return list
    }

    override suspend fun createShareDashboard(shareDashboard: ShareDashboard) {
        shareDashboardRepository.insert(shareDashboard)
    }

    override suspend fun upDateShareDashboard(shareDashboard: ShareDashboard) {
        shareDashboardRepository.save(shareDashboard)
    }

}