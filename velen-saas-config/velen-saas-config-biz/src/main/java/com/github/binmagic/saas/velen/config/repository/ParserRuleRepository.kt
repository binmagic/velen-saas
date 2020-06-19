package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.ParserRule
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ParserRuleRepository :BaseRepository<ParserRule> {
    fun findByAppIdAndType(appId: String,type:String) : Flux<ParserRule>
}