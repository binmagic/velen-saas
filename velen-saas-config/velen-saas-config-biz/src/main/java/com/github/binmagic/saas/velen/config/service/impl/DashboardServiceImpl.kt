package com.github.binmagic.saas.velen.config.service.impl

import cn.hutool.core.util.IdUtil
import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.repository.DashboardRepository
import com.github.binmagic.saas.velen.config.service.DashboardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class DashboardServiceImpl : DashboardService {

    @Autowired
    lateinit var dashboardRepository: DashboardRepository


    override suspend fun getDashboardService(): Flux<Dashboard> {

        return dashboardRepository.findAll()

    }

    override suspend fun getDashboardByType(type: String): Flux<Dashboard> {

        return dashboardRepository.findByType(type)

    }

    override suspend fun getDashboardById(id: String): Mono<Dashboard> {
        return dashboardRepository.findById(id)

    }

    override suspend fun createDashboard(dashboard: Dashboard): Mono<Dashboard> {
        val now: LocalDateTime = LocalDateTime.now()
        dashboard.createTime = now
        dashboard.id = IdUtil.fastSimpleUUID()
        return dashboardRepository.insert(dashboard)
    }

    override suspend fun updateDashboard(dashboard: Dashboard): Mono<Dashboard> {

        return dashboardRepository.save(dashboard)

    }

    override suspend fun deleteDashboardById(id: String): Mono<Void> {

        return dashboardRepository.deleteById(id)

    }


}