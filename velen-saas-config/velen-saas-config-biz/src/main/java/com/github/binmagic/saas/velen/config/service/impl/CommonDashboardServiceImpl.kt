package com.github.binmagic.saas.velen.config.service.impl

import cn.hutool.core.util.IdUtil
import com.github.binmagic.saas.velen.config.entity.CommonDashboard
import com.github.binmagic.saas.velen.config.repository.CommonDashboardRepository
import com.github.binmagic.saas.velen.config.service.CommonDashboardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class CommonDashboardServiceImpl : CommonDashboardService {

    @Autowired
    lateinit var commonDashboardRepository: CommonDashboardRepository

    override suspend fun getCommonDashboardServiceByType(commonType: String): Flux<CommonDashboard> {

        return commonDashboardRepository.findByCommonType(commonType)

    }

    override suspend fun createCommonDashboard(commonDashboard: CommonDashboard): Mono<CommonDashboard> {
        val now: LocalDateTime = LocalDateTime.now()
        commonDashboard.createTime = now
        commonDashboard.id = IdUtil.fastSimpleUUID()
        return commonDashboardRepository.insert(commonDashboard)
    }

    override suspend fun updateCommonDashboard(commonDashboard: CommonDashboard): Mono<CommonDashboard> {
        return commonDashboardRepository.save(commonDashboard)
    }

    override suspend fun deleteCommonDashboardById(id: String): Mono<Void> {
        return commonDashboardRepository.deleteById(id)
    }
}