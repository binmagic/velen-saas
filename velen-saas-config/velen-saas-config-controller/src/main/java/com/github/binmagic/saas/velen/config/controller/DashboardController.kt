package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.DashboardCreateDTO
import com.github.binmagic.saas.velen.config.dto.DashboardInfoDTO
import com.github.binmagic.saas.velen.config.dto.DashboardUpdateDTO
import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.service.DashboardService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard")
class DashboardController : BaseController() {


    @Autowired
    lateinit var dashboardService: DashboardService

    @GetMapping
    suspend fun getDashboardList(): List<Dashboard> {

        val appId = currentAppId.awaitSingle()

        return dashboardService.getDashboards(appId).collectList().awaitSingle()

    }

    @GetMapping("/{id}")
    suspend fun getDashboard(@PathVariable id: String): DashboardInfoDTO {

        val dashboard = dashboardService.getDashboardById(id).awaitSingle()

        val dashboardInfoDTO = DashboardInfoDTO()

        BeanUtils.copyProperties(dashboard, dashboardInfoDTO)

        return dashboardInfoDTO
    }

    @PutMapping
    suspend fun updateDashboard(@Validated @RequestBody dashboardUpdateDTO: DashboardUpdateDTO): DashboardInfoDTO {

        val dashboard = dashboardService.getDashboardById(dashboardUpdateDTO.id).awaitSingle()

        BeanUtils.copyProperties(dashboardUpdateDTO, dashboard)

        val updatedDashboard = dashboardService.updateDashboard(dashboard).awaitSingle()

        val dashboardInfoDTO = DashboardInfoDTO()

        BeanUtils.copyProperties(updatedDashboard, dashboardInfoDTO)

        return dashboardInfoDTO
    }

    @PostMapping
    suspend fun createDashboard(@Validated @RequestBody dashboardCreateDTO: DashboardCreateDTO): Dashboard {
        val userId = currentUserId.awaitSingle()
        val userName = currentUserAccount.awaitSingle()
        val appId = currentAppId.awaitSingle()
        val dashboard = Dashboard()
        BeanUtils.copyProperties(dashboardCreateDTO, dashboard)
        dashboard.appId = appId
        dashboard.userId = userId
        dashboard.userName = userName
        val sort = dashboardService.getDashboardByType(appId, dashboard.type)
                .count().awaitSingle().toInt()
        dashboard.sort = sort
        return dashboardService.createDashboard(dashboard).awaitSingle()
    }

    @DeleteMapping("{id}")
    suspend fun deleteDashboardById(@PathVariable("id") id: String) {
        dashboardService.deleteDashboardById(id).awaitFirstOrNull()
    }
}