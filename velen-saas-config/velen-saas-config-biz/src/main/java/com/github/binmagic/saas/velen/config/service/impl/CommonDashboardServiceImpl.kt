package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.entity.CommonDashboard
import com.github.binmagic.saas.velen.config.repository.CommonDashboardRepository
import com.github.binmagic.saas.velen.config.service.CommonDashboardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommonDashboardServiceImpl : CommonDashboardService {

    @Autowired
    lateinit var commonDashboardRepository: CommonDashboardRepository

    override suspend fun getCommonDashboardServiceByType(commonType: String): List<CommonDashboard> {

        return commonDashboardRepository.findByCommonType(commonType)

    }
}