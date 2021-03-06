package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.CheckRule
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface CheckRuleRepository : BaseRepository<CheckRule> {
    fun findByAppIdAndType(appId: String, type: String): Flux<CheckRule>

    fun findByAppId(appId: String):Flux<CheckRule>
}