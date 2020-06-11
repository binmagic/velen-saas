package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.DashboardCreateDTO
import com.github.binmagic.saas.velen.config.dto.GroupDashboardDTO
import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.entity.Group
import com.github.binmagic.saas.velen.config.service.DashboardService
import com.github.binmagic.saas.velen.config.service.GroupService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard/commonGroup")
class CommonGroupController : BaseController() {

    @Autowired
    lateinit var groupService: GroupService

    @Autowired
    lateinit var dashboardService: DashboardService

    @GetMapping
    suspend fun getCommonGroup(): List<GroupDashboardDTO> {
        val appId = currentAppId.awaitSingle()
        val userId = currentUserId.awaitSingle()
        val groups = groupService.getCommonGroup(appId, 1).collectList().awaitSingle()
        val commonGroupDashboardDTOs: ArrayList<GroupDashboardDTO> = ArrayList()
        groups.sortBy { it.sort }
        for (group in groups) {
            val commonGroupDashboardDTO = GroupDashboardDTO()
            BeanUtils.copyProperties(group, commonGroupDashboardDTO)
            val commonDashboards = dashboardService.getDashboardByType(appId, group.id).collectList().awaitSingle()
            commonDashboards.sortBy { it.sort }
            for (commonDashboard in commonDashboards) {
                val dashboardCreateDTO = DashboardCreateDTO()
                BeanUtils.copyProperties(commonDashboard, dashboardCreateDTO)
                commonGroupDashboardDTO.list.add(dashboardCreateDTO)
            }
            commonGroupDashboardDTOs.add(commonGroupDashboardDTO)
        }
        return commonGroupDashboardDTOs
    }

    suspend fun getCommonGroupV2(): List<GroupDashboardDTO> {
        val appId = currentAppId.awaitSingle()
        val groups = groupService.getCommonGroup(appId, 1).collectList().awaitSingle()
        val commonGroupDashboardDTOs: ArrayList<GroupDashboardDTO> = ArrayList()
        groups.sortBy { it.sort }
        for (group in groups) {
            val groupDashboardDTO = GroupDashboardDTO()
            BeanUtils.copyProperties(group, groupDashboardDTO)
            if (!group.dashboardId.isNullOrEmpty()) {
                for (id in group.dashboardId) {
                    val dashboard = dashboardService.getDashboardById(id).awaitSingle()
                    val dashboardCreatDTO = DashboardCreateDTO()
                    BeanUtils.copyProperties(dashboard, dashboardCreatDTO)
                    groupDashboardDTO.list.add(dashboardCreatDTO)
                }
            }
            commonGroupDashboardDTOs.add(groupDashboardDTO)
        }
        return commonGroupDashboardDTOs
    }

    @PostMapping
    suspend fun createCommonGroup(@Validated @RequestBody groupDashboardDTO: GroupDashboardDTO): GroupDashboardDTO {
        val group = Group()
        BeanUtils.copyProperties(groupDashboardDTO, group)
        group.appId = currentAppId.awaitSingle()
        group.isPublic = 1
        group.sort = groupService.countCommonGroup(group.appId, group.isPublic).awaitSingle().toInt()
        val dashboards = groupDashboardDTO.list
        if (!dashboards.isNullOrEmpty()) {
            for (dashboardCreate in dashboards) {
                dashboardCreate.type = group.id
                val dashboard = Dashboard()
                BeanUtils.copyProperties(dashboardCreate, dashboard)
                dashboardService.updateDashboard(dashboard).awaitSingle()
            }
        }
        val result = groupService.createGroup(group).awaitSingle()
        BeanUtils.copyProperties(result, groupDashboardDTO)
        return groupDashboardDTO
    }

    @PutMapping
    suspend fun updateCommonGroup(@Validated @RequestBody groupDashboardDTOs: List<GroupDashboardDTO>) {
        for (groupDashboardDTO in groupDashboardDTOs) {
            val group = Group()
            BeanUtils.copyProperties(groupDashboardDTO, group)
            val result = groupService.getGroupById(group.id).awaitFirstOrNull()
            if (result == null) {
                createCommonGroup(groupDashboardDTO)
            } else {
                groupService.updateGroup(group).awaitSingle()
                if (!groupDashboardDTO.list.isNullOrEmpty()) {
                    for (dashboardCreate in groupDashboardDTO.list) {
                        var dashboard = Dashboard()
                        BeanUtils.copyProperties(dashboardCreate, dashboard)
                        dashboardService.updateDashboard(dashboard).awaitSingle()
                    }
                }
            }
        }
    }

    @DeleteMapping("/{id}")
    suspend fun deleteCommonGroup(@PathVariable("id") id: String) {
        groupService.deleteGroupById(id).awaitFirstOrNull()
    }
}