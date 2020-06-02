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
        group.id=IdUtil.fastSimpleUUID()
        return groupRepository.insert(group)
    }

    override suspend fun updateGroup(group: Group) :Mono<Group> {
        return groupRepository.save(group)
    }

    override suspend fun deleteGroupById(id: String) :Mono<Void> {
        return groupRepository.deleteById(id)
    }

    override suspend fun countGroupByUserIdAndAppId(userId: String, appId: String): Mono<Long> {
        return groupRepository.countByUserIdAndAppId(userId, appId)
    }

    override suspend fun getGroupById(id: String): Mono<Group> {
        return groupRepository.findById(id)
    }
}