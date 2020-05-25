package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.dto.CommonGroupDashboardDTO
import com.github.binmagic.saas.velen.config.entity.CommonDashboard
import com.github.binmagic.saas.velen.config.entity.CommonGroup
import com.github.binmagic.saas.velen.config.repository.CommonDashboardRepository
import com.github.binmagic.saas.velen.config.repository.CommonGroupRepository
import com.github.binmagic.saas.velen.config.service.CommonGroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CommonGroupServiceImpl : CommonGroupService {

    @Autowired
    lateinit var commonGroupRepository: CommonGroupRepository

    @Autowired
    lateinit var commonDashboardRepository: CommonDashboardRepository

    override suspend fun getCommonGroupByAppId(appId: String): Flux<CommonGroup> {

        /*val list:MutableList<CommonGroupDashboardDTO> = commonGroupRepository.findByAppId(appId) as MutableList<CommonGroupDashboardDTO>
        list.sortBy { it.sort }
        for (group : CommonGroupDashboardDTO in list){
            val dashboardList:MutableList<CommonDashboard> = commonDashboardRepository.findByCommonType(group.id) as MutableList<CommonDashboard>
            dashboardList.sortBy { it.sort }
            group.list=dashboardList
        }*/
        return commonGroupRepository.findByAppId(appId)

    }

}