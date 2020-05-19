package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.entity.Dashboard
import reactor.core.publisher.Flux

interface DashboardService {

    suspend fun getDashboardService() : Flux<Dashboard>
}