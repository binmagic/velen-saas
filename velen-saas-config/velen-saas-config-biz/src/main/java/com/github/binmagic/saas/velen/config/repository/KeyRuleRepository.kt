package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.KeyRule
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface KeyRuleRepository :BaseRepository<KeyRule>{

    fun findByAppIdAndType(appId:String ,type:String) :Flux<KeyRule>

    fun findByAppId(appId: String):Flux<KeyRule>

}