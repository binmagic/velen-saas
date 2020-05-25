package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.DashboardCreateDTO
import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.service.DashboardService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/dashboard")
class DashboardController : BaseController() {


    @Autowired
    lateinit var dashboardService : DashboardService

    suspend fun getDashboardList() : Flux<Dashboard>{
        return dashboardService.getDashboardService()
    }

    @PostMapping
    suspend fun createDashboard(@Validated @RequestBody dashboardCreateDTO: DashboardCreateDTO) : Dashboard{
        val dashboard = Dashboard()
        BeanUtils.copyProperties(dashboardCreateDTO,dashboard)
        val sort= dashboardService.getDashboardByType(dashboard.type).count().awaitSingle().toInt()
        dashboard.sort=sort
        return dashboardService.createDashboard(dashboard).awaitSingle()
    }
}