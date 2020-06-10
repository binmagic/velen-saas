package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.DashboardCreateDTO
import com.github.binmagic.saas.velen.config.dto.GroupDashboardDTO
import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.entity.Group
import com.github.binmagic.saas.velen.config.service.DashboardService
import com.github.binmagic.saas.velen.config.service.GroupService
import com.github.binmagic.saas.velen.config.service.ShareDashboardService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/dashboard/group")
class GroupController : BaseController() {

    @Autowired
    lateinit var groupService: GroupService

    @Autowired
    lateinit var dashboardService: DashboardService

    @Autowired
    lateinit var shareDashboardService: ShareDashboardService

    @GetMapping
    suspend fun getGroup(): List<GroupDashboardDTO> {
        val appId = currentAppId.awaitSingle()
        val userId = currentUserId.awaitSingle()
        val list = groupService.getGroupByUserIdAndAppId(userId, appId).collectList().awaitSingle()
        if (list.isNullOrEmpty()) {
            val groupInfo = GroupDashboardDTO()
            groupInfo.name = "分享给我的概览"
            createGroup(groupInfo)
        }
        val groups = groupService.getGroupByUserIdAndAppId(userId, appId).collectList().awaitSingle()
        val groupDashboardDTOs: ArrayList<GroupDashboardDTO> = ArrayList()
        groups.sortBy { it.sort }
        for (group in groups) {
            val groupDashboardDTO = GroupDashboardDTO()
            BeanUtils.copyProperties(group, groupDashboardDTO)
            val dashboards = dashboardService.getDashboardByType(appId, group.id).collectList().awaitSingle()
            val list: ArrayList<DashboardCreateDTO> = ArrayList()
            for (dashboard in dashboards) {
                val dashboardCreate = DashboardCreateDTO()
                BeanUtils.copyProperties(dashboard, dashboardCreate)
                dashboardCreate.isPublic = 0
                list.add(dashboardCreate)
            }
            val shareDashboards = shareDashboardService.getShareDashboardByType(group.id).collectList().awaitSingle()
            for (shareDashboard in shareDashboards) {
                val shareDashboardDTO = dashboardService.getDashboardById(shareDashboard.dashboardId).awaitSingle()
                shareDashboardDTO.name += shareDashboard.userName
                val shareDashboardCreate = DashboardCreateDTO()
                BeanUtils.copyProperties(shareDashboardDTO, shareDashboardCreate)
                shareDashboardCreate.isPublic = 1
                list.add(shareDashboardCreate)
            }
            list.sortBy { it.sort }
            groupDashboardDTO.list = list
            groupDashboardDTOs.add(groupDashboardDTO)
        }
        return groupDashboardDTOs
    }

    @PostMapping
    suspend fun createGroup(@Validated @RequestBody groupDashboardDTO: GroupDashboardDTO): GroupDashboardDTO {
        val group = Group()
        BeanUtils.copyProperties(groupDashboardDTO, group)
        group.appId = currentAppId.awaitSingle()
        group.userId = currentUserId.awaitSingle()
        group.sort = groupService.countGroupByUserIdAndAppId(group.userId, group.appId).awaitSingle().toInt()
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
    suspend fun updateGroup(@Validated @RequestBody groupDashboardDTOs: List<GroupDashboardDTO>) {
        for (groupDashboardDTO in groupDashboardDTOs) {
            val group = Group()
            BeanUtils.copyProperties(groupDashboardDTO, group)
            val result = groupService.getGroupById(group.id).awaitFirstOrNull()
            if (result == null) {
                createGroup(groupDashboardDTO)
            } else {
                groupService.updateGroup(group)
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
    suspend fun deleteGroup(@PathVariable("id") id: String) {
        groupService.deleteGroupById(id).awaitFirstOrNull()
    }
}