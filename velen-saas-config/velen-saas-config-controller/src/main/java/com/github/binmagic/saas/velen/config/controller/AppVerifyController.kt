package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.entity.AppVerify
import com.github.binmagic.saas.velen.config.service.AppVerifyService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard/appVerify")
class AppVerifyController: BaseController() {

    @Autowired
    lateinit var appVerifyService: AppVerifyService

    @GetMapping
    suspend fun getAppVerify() :AppVerify {
        val appId = currentAppId.awaitSingle()
        val user= currentUserAccount.awaitSingle()
        return appVerifyService.getAppVerify(appId,user).awaitSingle()
    }

    @PutMapping
    suspend fun updateAppVerify(@RequestBody appVerify: AppVerify) : AppVerify{
        val appId = currentAppId.awaitSingle()
        val user= currentUserAccount.awaitSingle()
        appVerify.appId = appId
        appVerify.createUser = user
        return appVerifyService.updateAppVerify(appVerify).awaitSingle()
    }
}