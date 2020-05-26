package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.config.dto.CommonDashboardCreateDTO
import com.github.binmagic.saas.velen.config.entity.CommonDashboard
import com.github.binmagic.saas.velen.config.service.CommonDashboardService
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/dashboard/commonDashboard")
class CommonDashboardController {

    @Autowired
    lateinit var commonDashboardService:CommonDashboardService

    @PostMapping
    suspend fun createCommonDashboard(@Validated @RequestBody commonDashboardCreateDTO: CommonDashboardCreateDTO) : CommonDashboard{
        val commonDashboard = CommonDashboard()
        BeanUtils.copyProperties(commonDashboardCreateDTO,commonDashboard)
        val sort=commonDashboardService.getCommonDashboardServiceByType(commonDashboard.commonType).count().awaitSingle().toInt()
        commonDashboard.sort=sort
        return  commonDashboardService.createCommonDashboard(commonDashboard).awaitSingle()

    }

    @PutMapping
    suspend fun updateCommonDashboard(@Validated @RequestBody commonDashboardCreateDTO: CommonDashboardCreateDTO) : CommonDashboard{
        val commonDashboard = CommonDashboard()
        BeanUtils.copyProperties(commonDashboardCreateDTO,commonDashboard)
        return commonDashboardService.updateCommonDashboard(commonDashboard).awaitSingle()
    }

    @DeleteMapping("{id}")
    suspend fun deleteCommonDashboard(@PathVariable id : String) : Mono<Void>{
        return commonDashboardService.deleteCommonDashboardById(id)
    }
}