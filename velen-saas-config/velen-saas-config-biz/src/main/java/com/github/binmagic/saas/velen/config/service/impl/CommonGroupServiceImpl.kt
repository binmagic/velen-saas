package com.github.binmagic.saas.velen.config.service.impl

import cn.hutool.core.util.IdUtil
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
import java.time.LocalDateTime

@Service
class CommonGroupServiceImpl : CommonGroupService {

    @Autowired
    lateinit var commonGroupRepository: CommonGroupRepository

    override suspend fun createCommonGroup(commonGroup: CommonGroup): Mono<CommonGroup> {
        val now: LocalDateTime = LocalDateTime.now()
        commonGroup.createTime=now
        commonGroup.id= IdUtil.fastSimpleUUID()
        return commonGroupRepository.insert(commonGroup)
    }

    override suspend fun updateCommonGroup(commonGroup: CommonGroup): Mono<CommonGroup> {
        return commonGroupRepository.save(commonGroup)
    }

    override suspend fun countCommonGroupByAppId(appId: String): Mono<Long> {
        return commonGroupRepository.countByAppId(appId)
    }

    override suspend fun getCommonGroupByAppId(appId: String): Flux<CommonGroup> {
        return commonGroupRepository.findByAppId(appId)
    }

    override suspend fun deleteCommonGroupById(id: String): Mono<Void> {
        return commonGroupRepository.deleteById(id)
    }

}