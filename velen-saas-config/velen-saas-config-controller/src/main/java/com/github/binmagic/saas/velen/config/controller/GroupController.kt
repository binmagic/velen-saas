package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.config.dto.GroupCreateDTO
import com.github.binmagic.saas.velen.config.entity.Group
import com.github.binmagic.saas.velen.config.service.GroupService
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/group")
class GroupController {

    @Autowired
    lateinit var groupService: GroupService

    @GetMapping
    suspend fun getGroup(userId: String, appId: String) = mono(Unconfined) {

        groupService.getGroupByUserIdAndAppId(userId, appId)

    }

    @PostMapping("/addGroup")
    suspend fun createGroup(@Validated @RequestBody groupCreateDTO: GroupCreateDTO) = mono(Unconfined) {
        val group = Group()
        BeanUtils.copyProperties(groupCreateDTO, group)
        group.sort = groupService.countGroupByUserIdAndAppId(group.userId, group.appId).toInt()
        groupService.createGroup(group)
    }

    @PutMapping("/updateGroup")
    suspend fun updateGroup(@Validated @RequestBody groupCreateDTO: GroupCreateDTO) = mono(Unconfined) {
        val group = Group()
        BeanUtils.copyProperties(groupCreateDTO, group)
        groupService.updateGroup(group)
    }

    @DeleteMapping("/deleteGroup/{id}")
    suspend fun deleteGroup(@PathVariable("id") id: String) = mono(Unconfined) {
        groupService.deleteGroupById(id)
    }
}