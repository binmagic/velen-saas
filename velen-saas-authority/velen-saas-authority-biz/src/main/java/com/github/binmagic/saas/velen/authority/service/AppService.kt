package com.github.binmagic.saas.velen.authority.service

import com.github.binmagic.saas.velen.authority.dto.AppAddMemberDTO
import com.github.binmagic.saas.velen.authority.dto.AppMemberInfoDTO
import com.github.binmagic.saas.velen.authority.entity.App
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AppService {

    suspend fun findApp(account : String) :Flux<App>

    suspend fun createApp(app:App) :Mono<App>

    suspend fun submitToAuditor(appId : String) : Mono<Void>

    suspend fun updateApp(app:App) : Mono<App>

    suspend fun getApp(appId: String) : Mono<App>

    suspend fun getAppMember(appId: String) : Flux<AppMemberInfoDTO>

    suspend fun getAppMemberRole(appId: String,account: String) : Mono<String>

    suspend fun addMember(appId: String, appAddMemberDTO: List<AppAddMemberDTO>) : Mono<Void>

    suspend fun deleteMember(appId: String,id:String) : Mono<Void>

    suspend fun deleteApp(appId: String) : Mono<Void>
}