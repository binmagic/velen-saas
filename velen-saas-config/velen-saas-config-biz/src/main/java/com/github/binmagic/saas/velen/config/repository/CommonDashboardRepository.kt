package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.CommonDashboard
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface CommonDashboardRepository : BaseRepository<CommonDashboard> {

    fun findByCommonType(commonType: String): Flux<CommonDashboard>


}