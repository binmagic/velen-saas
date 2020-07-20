package com.github.binmagic.saas.velen.config.service.impl

import cn.hutool.core.util.IdUtil
import com.github.binmagic.saas.velen.config.dto.GroupDashboardDTO
import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.entity.Group
import com.github.binmagic.saas.velen.config.repository.DashboardRepository
import com.github.binmagic.saas.velen.config.repository.GroupRepository
import com.github.binmagic.saas.velen.config.service.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class GroupServiceImpl : GroupService {

    @Autowired
    lateinit var groupRepository: GroupRepository

    override suspend fun getGroupByUserIdAndAppId(userId: String, appId: String): Flux<Group> {
        return groupRepository.findByUserIdAndAppId(userId, appId)
    }

    override suspend fun createGroup(group: Group) :Mono<Group> {
        val now:LocalDateTime=LocalDateTime.now()
        group.createTime=now
        group.id = null
        return groupRepository.insert(group)
    }

    override suspend fun updateGroup(group: Group) :Mono<Group> {
        return groupRepository.save(group)
    }

    override suspend fun deleteGroupById(id: String) :Mono<Void> {
        return groupRepository.deleteById(id)
    }

    override suspend fun countGroup(userId: String, appId: String,isPublic: Int): Mono<Long> {
        return groupRepository.countByUserIdAndAppIdAndIsPublic(userId,appId,isPublic)
    }

    override suspend fun countCommonGroup(appId: String,isPublic: Int): Mono<Long> {
        return groupRepository.countByAppIdAndIsPublic(appId,isPublic)
    }
    override suspend fun getGroupById(id: String): Mono<Group> {
        return groupRepository.findById(id)
    }

    override suspend fun getGroup(userId: String, appId: String, isPublic: Int): Flux<Group> {
        return groupRepository.findByUserIdAndAppIdAndIsPublic(userId, appId, isPublic)
    }

    override suspend fun getCommonGroup(appId: String, isPublic: Int): Flux<Group> {
        return groupRepository.findByAppIdAndIsPublic(appId, isPublic)
    }

    override suspend fun findAllGroup(appId: String): Flux<Group> {
        return groupRepository.findByAppId(appId)
    }
}