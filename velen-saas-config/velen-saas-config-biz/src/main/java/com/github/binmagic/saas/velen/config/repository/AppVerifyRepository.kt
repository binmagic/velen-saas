package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.AppVerify
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface AppVerifyRepository: BaseRepository<AppVerify> {

    fun findByAppId(appId:String) : Mono<AppVerify>

}