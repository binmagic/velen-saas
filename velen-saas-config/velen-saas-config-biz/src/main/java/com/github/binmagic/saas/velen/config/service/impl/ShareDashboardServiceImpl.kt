package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.dto.ShareDashboardDTO
import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.entity.ShareDashboard
import com.github.binmagic.saas.velen.config.repository.DashboardRepository
import com.github.binmagic.saas.velen.config.repository.ShareDashboardRepository
import com.github.binmagic.saas.velen.config.service.ShareDashboardService
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ShareDashboardServiceImpl : ShareDashboardService {
    @Autowired
    lateinit var shareDashboardRepository: ShareDashboardRepository

    @Autowired
    lateinit var dashboardRepository: DashboardRepository

    override suspend fun getShareDashboardByUserIdAndAppId(userId: String, appId: String): Flux<ShareDashboard> {
      return shareDashboardRepository.findByUserIdAndAppId(userId, appId)
    }

    override suspend fun getShareDashboardByGroupId(groupId: String): Flux<ShareDashboardDTO> {
        TODO("Not yet implemented")
    }


    override suspend fun createShareDashboard(shareDashboard: ShareDashboard) :Mono<ShareDashboard> {
        return shareDashboardRepository.insert(shareDashboard)
    }

    override suspend fun upDateShareDashboard(shareDashboard: ShareDashboard) : Mono<ShareDashboard> {
       return shareDashboardRepository.save(shareDashboard)
    }

}