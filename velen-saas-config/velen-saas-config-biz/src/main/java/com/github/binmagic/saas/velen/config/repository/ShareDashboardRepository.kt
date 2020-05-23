package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.ShareDashboard
import org.springframework.stereotype.Repository

@Repository
interface ShareDashboardRepository:BaseRepository<ShareDashboard> {

    fun findByUserIdAndAppId(userId :String ,appId: String) : List<ShareDashboard>

    fun findByGroupId(groupId : String) : List<ShareDashboard>


}