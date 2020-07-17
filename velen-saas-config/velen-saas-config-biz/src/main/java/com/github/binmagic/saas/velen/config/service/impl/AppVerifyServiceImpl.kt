package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.common.config.EnumUtil
import com.github.binmagic.saas.velen.config.entity.AppVerify
import com.github.binmagic.saas.velen.config.etl.ProjectApi
import com.github.binmagic.saas.velen.config.repository.AppVerifyRepository
import com.github.binmagic.saas.velen.config.service.AppVerifyService
import com.velen.etl.ResultCode
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class AppVerifyServiceImpl : AppVerifyService {
    @Autowired
    lateinit var appVerifyRepository: AppVerifyRepository

    @Autowired
    lateinit var projectApi: ProjectApi

    override suspend fun getAppVerify(appId: String,user:String): Mono<AppVerify> {
        val mono = appVerifyRepository.findByAppId(appId).awaitFirstOrNull()
        if (mono == null){
            val appVerify = AppVerify()
            appVerify.appId = appId
            appVerify.verify = false
            appVerify.createUser = user
            return appVerifyRepository.insert(appVerify)
        }
        return mono.toMono()
    }



    override suspend fun updateAppVerify(appVerify: AppVerify): Mono<AppVerify> {

        val resp = projectApi.setVerify(appVerify.appId,appVerify.verify,appVerify.createUser)
        if (EnumUtil.isInResultCode(resp.statusCodeValue)){
            return Mono.error(RuntimeException(ResultCode.valueOf(resp.statusCodeValue).message()))
        }

        val appVerify1 = appVerifyRepository.findByAppId(appVerify.appId).awaitSingle()
        appVerify.id = appVerify1.id



        return appVerifyRepository.save(appVerify)
    }
}