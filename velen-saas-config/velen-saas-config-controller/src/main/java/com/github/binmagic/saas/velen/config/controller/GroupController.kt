package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.GroupCreateDTO
import com.github.binmagic.saas.velen.config.dto.GroupDashboardDTO
import com.github.binmagic.saas.velen.config.entity.Group
import com.github.binmagic.saas.velen.config.service.DashboardService
import com.github.binmagic.saas.velen.config.service.GroupService
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.mono
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
    suspend fun createGroup(@Validated @RequestBody groupCreateDTO: GroupCreateDTO): GroupCreateDTO {
        val group = Group()
        BeanUtils.copyProperties(groupCreateDTO, group)
        group.appId = currentAppId.awaitSingle()
        group.userId = currentUserId.awaitSingle()
        group.sort = groupService.countGroupByUserIdAndAppId(group.userId, group.appId).awaitSingle().toInt()
        val dashboards = groupCreateDTO.dashboards
        if (dashboards!=null) {
            for (dashboard in dashboards) {
                dashboard.type = group.id
                dashboardService.updateDashboard(dashboard)
            }
        }
        val result = groupService.createGroup(group).awaitSingle()
        BeanUtils.copyProperties(result, groupCreateDTO)
        return groupCreateDTO
    }

    @PutMapping
    suspend fun updateGroup(@Validated @RequestBody groupCreateDTO: GroupCreateDTO) {
        val group = Group()
        BeanUtils.copyProperties(groupCreateDTO, group)
        groupService.updateGroup(group)
    }

    @DeleteMapping("{id}")
    suspend fun deleteGroup(@PathVariable("id") id: String) {
        groupService.deleteGroupById(id).awaitSingle()
    }
}