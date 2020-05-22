package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.repository.DashboardRepository
import com.github.binmagic.saas.velen.config.service.DashboardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class DashboardServiceImpl : DashboardService {

    @Autowired
    lateinit var dashboardRepository: DashboardRepository


    override suspend fun getDashboardService(): Flux<Dashboard> {

        return dashboardRepository.findAll()

    }

    override suspend fun getDashboardByType(type: String): List<Dashboard> {

        return dashboardRepository.findByType(type)

    }

    override suspend fun createDashboard(dashboard: Dashboard) {

        dashboardRepository.insert(dashboard)

    }

    override suspend fun updateDashboard(dashboard: Dashboard) {

        dashboardRepository.save(dashboard)

    }

    override suspend fun deleteDashboardById(id: String) {

        dashboardRepository.deleteById(id)

    }


}