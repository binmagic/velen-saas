package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.CommonGroupDashboardDTO
import com.github.binmagic.saas.velen.config.entity.CommonDashboard
import com.github.binmagic.saas.velen.config.entity.CommonGroup
import com.github.binmagic.saas.velen.config.entity.QCommonGroup.commonGroup
import com.github.binmagic.saas.velen.config.service.CommonDashboardService
import com.github.binmagic.saas.velen.config.service.CommonGroupService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.ws.rs.Path

@RestController
@RequestMapping("/dashboard/commonGroup")
class CommonGroupController:BaseController() {

    @Autowired
    lateinit var commonGroupService: CommonGroupService

    @Autowired
    lateinit var commonDashboardService:CommonDashboardService

    @GetMapping
    suspend fun getCommonGroup() :List<CommonGroupDashboardDTO>{
        val appId=currentAppId.awaitSingle()
        val commonGroups = commonGroupService.getCommonGroupByAppIdAndParentId(appId,"0").collectList().awaitSingle()
        val commonGroupDashboardDTOs : ArrayList<CommonGroupDashboardDTO> = ArrayList()
        commonGroups.sortBy { it.sort }
        for (commonGroup in commonGroups){
            val commonGroupDashboardDTO= CommonGroupDashboardDTO()
            BeanUtils.copyProperties(commonGroup,commonGroupDashboardDTO)
            val list = commonGroupService.getCommonGroupByAppIdAndParentId(appId,commonGroup.id).collectList().awaitSingle()

            if (commonGroupDashboardDTO.list.isNullOrEmpty()){
                commonGroupDashboardDTO.list = commonDashboardService.getCommonDashboardServiceByType(commonGroup.id).collectList().awaitSingle().sortBy { it.sort } as List<CommonDashboard>
                commonGroupDashboardDTO.hasSonGroup= false
            }else{
                commonGroupDashboardDTO.hasSonGroup=true
                for(item in list){
                    val dto=CommonGroupDashboardDTO()
                    BeanUtils.copyProperties(item,dto)
                    dto.list=commonDashboardService.getCommonDashboardServiceByType(dto.id).collectList().awaitSingle().sortBy { it.sort } as List<CommonDashboard>
                    commonGroupDashboardDTO.list.add(dto)
                }
            }
        }
        return commonGroupDashboardDTOs
    }


    @PostMapping
    suspend fun createCommonGroup(@Validated @RequestBody commonGroupDashboardDTO: CommonGroupDashboardDTO) : CommonGroupDashboardDTO{
        val commonGroup = CommonGroup()
        BeanUtils.copyProperties(commonGroupDashboardDTO,commonGroup)
        val appId= currentAppId.awaitSingle()
        commonGroup.appId=appId
        commonGroup.sort = commonGroupService.countCommonGroupByAppId(appId).awaitSingle().toInt()

        val result= commonGroupService.createCommonGroup(commonGroup).awaitSingle()
        BeanUtils.copyProperties(result,commonGroupDashboardDTO)
        return commonGroupDashboardDTO
    }

    @PutMapping
    suspend fun updateCommonGroup(@Validated @RequestBody commonGroupDashboardDTO: CommonGroupDashboardDTO) :CommonGroupDashboardDTO{
        val commonGroup=CommonGroup()
        BeanUtils.copyProperties(commonGroupDashboardDTO,commonGroup)
        val result = commonGroupService.updateCommonGroup(commonGroup).awaitSingle()
        BeanUtils.copyProperties(result,commonGroupDashboardDTO)
        return commonGroupDashboardDTO
    }

    @DeleteMapping("{id}")
    suspend fun deleteCommonGroupById(@PathVariable id:String) : Mono<Void>{
        val commonGroups = commonGroupService.getCommonGroupByAppId(currentAppId.awaitSingle()).collectList().awaitSingle()
        val commonDashboards = commonDashboardService.getCommonDashboardServiceByType(id).collectList().awaitSingle()
        for (commonDashboard in commonDashboards){
            commonDashboard.commonType=commonGroups[0].id
            commonDashboardService.updateCommonDashboard(commonDashboard)
        }
        return commonGroupService.deleteCommonGroupById(id)
    }
}