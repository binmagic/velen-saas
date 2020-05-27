package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.ShareDashboardCreateDTO
import com.github.binmagic.saas.velen.config.entity.ShareDashboard
import com.github.binmagic.saas.velen.config.service.ShareDashboardService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/dashboard/shareDashboard")
class ShareDashboardController : BaseController() {

    @Autowired
    lateinit var shareDashboardService:ShareDashboardService

    @PostMapping
    suspend fun createShareDashboard(@Validated @RequestBody shareDashboardCreateDTO: ShareDashboardCreateDTO)  {
        val userName = currentUserName.awaitSingle()
        val userId = currentUserId.awaitSingle()
        val appId = currentAppId.awaitSingle()
        shareDashboardCreateDTO.appId=appId
        shareDashboardCreateDTO.shareUserId=userId
        shareDashboardCreateDTO.userName=userName
        val shareDashboard= ShareDashboard()
        BeanUtils.copyProperties(shareDashboardCreateDTO,shareDashboard)
        shareDashboardService.createShareDashboard(shareDashboard)
    }

    @PutMapping
    suspend fun updateShareDashboard(@Validated @RequestBody shareDashboardCreateDTO: ShareDashboardCreateDTO) {
        val shareDashboard = ShareDashboard()
        BeanUtils.copyProperties(shareDashboardCreateDTO,shareDashboard)
        shareDashboardService.upDateShareDashboard(shareDashboard)
    }

}