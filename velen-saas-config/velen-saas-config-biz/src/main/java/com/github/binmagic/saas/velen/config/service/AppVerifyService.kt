package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.entity.AppVerify
import reactor.core.publisher.Mono

interface AppVerifyService {

    suspend fun getAppVerify(appId:String,user:String) :Mono<AppVerify>


    suspend fun updateAppVerify(appVerify: AppVerify) : Mono<AppVerify>
}