package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.dto.GroupDashboardDTO
import com.github.binmagic.saas.velen.config.entity.Dashboard
import com.github.binmagic.saas.velen.config.entity.Group
import com.github.binmagic.saas.velen.config.repository.DashboardRepository
import com.github.binmagic.saas.velen.config.repository.GroupRepository
import com.github.binmagic.saas.velen.config.service.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GroupServiceImpl : GroupService {

    @Autowired
    lateinit var groupRepository: GroupRepository
    
    @Autowired
    lateinit var dashboardRepository: DashboardRepository

    override suspend fun getGroupByUserIdAndAppId(userId: String, appId: String): Mono<List<GroupDashboardDTO>> {
        val list:MutableList<GroupDashboardDTO> = groupRepository.findByUserIdAndAppId(userId, appId) as MutableList<GroupDashboardDTO>
        list.sortBy { it.sort }
        for (group : GroupDashboardDTO in list){
            val dashboardList:MutableList<Dashboard> = dashboardRepository.findByType(group.id) as MutableList<Dashboard>
            dashboardList.sortBy { it.sort }
            group.list=dashboardList
        }
        return Mono.just(list)
    }

    override suspend fun createGroup(group: Group) :Mono<Group> {
        return groupRepository.insert(group)
    }

    override suspend fun updateGroup(group: Group) :Mono<Group> {
        return groupRepository.save(group)
    }

    override suspend fun deleteGroupById(id: String) :Mono<Void> {
        return groupRepository.deleteById(id)
    }

    override suspend fun countGroupByUserIdAndAppId(userId: String, appId: String): Long {
        return groupRepository.countByUserIdAndAppId(userId, appId)
    }
}