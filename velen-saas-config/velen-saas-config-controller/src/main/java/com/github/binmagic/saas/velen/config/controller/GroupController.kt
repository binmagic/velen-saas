package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.GroupDashboardDTO
import com.github.binmagic.saas.velen.config.entity.Group
import com.github.binmagic.saas.velen.config.service.DashboardService
import com.github.binmagic.saas.velen.config.service.GroupService
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/dashboard/group")
class GroupController : BaseController() {

    @Autowired
    lateinit var groupService: GroupService

    @Autowired
    lateinit var dashboardService: DashboardService

    @GetMapping
    suspend fun getGroup(): List<GroupDashboardDTO> {
        val appId = currentAppId.awaitSingle()
        val userId = currentUserId.awaitSingle()
        val groups = groupService.getGroupByUserIdAndAppId(userId, appId).collectList().awaitSingle()
        val groupDashboardDTOs: ArrayList<GroupDashboardDTO> = ArrayList()

        groups.sortBy { it.sort }
        for (group in groups) {
            val groupDashboardDTO = GroupDashboardDTO()
            BeanUtils.copyProperties(group, groupDashboardDTO)
            val dashboards = dashboardService.getDashboardByType(group.id).collectList().awaitSingle()
            dashboards.sortBy { it.sort }
            groupDashboardDTO.list = dashboards
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
            for (dashboard in dashboards) {
                dashboard.type = group.id
                dashboardService.updateDashboard(dashboard)
            }
        }
        val result = groupService.createGroup(group).awaitSingle()
        BeanUtils.copyProperties(result, groupDashboardDTO)
        return groupDashboardDTO
    }

    @PutMapping
    suspend fun updateGroup(@Validated @RequestBody groupDashboardDTO: GroupDashboardDTO) : GroupDashboardDTO {
        val group = Group()
        BeanUtils.copyProperties(groupDashboardDTO, group)
        val result=groupService.updateGroup(group).awaitSingle()
        BeanUtils.copyProperties(result,groupDashboardDTO)
        return groupDashboardDTO
    }

    @DeleteMapping("{id}")
    suspend fun deleteGroup(@PathVariable("id") id: String) : Mono<Void> {
        val appId=currentAppId.awaitSingle()
        val userId=currentUserId.awaitSingle()
        val groups = groupService.getGroupByUserIdAndAppId(userId, appId).collectList().awaitSingle()
        val dashboards = dashboardService.getDashboardByType(id).collectList().awaitSingle()
        for (dashboard in dashboards){
            dashboard.type=groups[0].id
            dashboardService.updateDashboard(dashboard)
        }
        return groupService.deleteGroupById(id)
    }
}