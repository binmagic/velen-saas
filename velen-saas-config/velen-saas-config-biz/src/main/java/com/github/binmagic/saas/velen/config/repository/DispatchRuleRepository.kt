package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.DispatchRule
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface DispatchRuleRepository :BaseRepository<DispatchRule> {

    fun findByBusinessName(businessName:String) : Mono<DispatchRule>

}