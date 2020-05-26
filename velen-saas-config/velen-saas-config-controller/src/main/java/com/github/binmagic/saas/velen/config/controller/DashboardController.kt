package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.DashboardCreateDTO
import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.service.DashboardService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

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

    @PutMapping
    suspend fun updateDashboard(@Validated @RequestBody dashboardCreateDTO: DashboardCreateDTO) :Dashboard{
        val dashboard= Dashboard()
        BeanUtils.copyProperties(dashboardCreateDTO,dashboard)
        return dashboardService.updateDashboard(dashboard).awaitSingle()
    }

    @DeleteMapping("{id}")
    suspend fun deleteDashboardById(@PathVariable("id") id:String) :Mono<Void> {
        return dashboardService.deleteDashboardById(id)
    }
}