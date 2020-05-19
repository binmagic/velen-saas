package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.repository.DashboardRepository
import com.github.binmagic.saas.velen.config.service.DashboardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class DashboardServiceImpl : DashboardService {

    @Autowired
    lateinit var dashboardRepository: DashboardRepository


    override suspend fun getDashboardService(): Flux<Dashboard> {

        return dashboardRepository.findAll()
    }


}