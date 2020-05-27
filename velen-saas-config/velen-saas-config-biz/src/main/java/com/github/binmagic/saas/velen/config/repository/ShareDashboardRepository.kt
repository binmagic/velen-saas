package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.ShareDashboard
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ShareDashboardRepository : BaseRepository<ShareDashboard> {

    fun findByUserIdAndAppId(userId: String, appId: String): Flux<ShareDashboard>

    fun findByType(type: String): Flux<ShareDashboard>


}