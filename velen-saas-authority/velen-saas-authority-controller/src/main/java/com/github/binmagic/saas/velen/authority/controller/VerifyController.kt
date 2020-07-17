package com.github.binmagic.saas.velen.authority.controller

import com.github.binmagic.saas.velen.authority.service.VerifyService
import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.common.config.SecretConfig
import com.github.binmagic.saas.velen.common.entity.Error
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/permission/verify")
class VerifyController : BaseController() {

    @Autowired
    lateinit var secretConfig: SecretConfig

    @Autowired
    lateinit var verifyService: VerifyService


    @PostMapping
    suspend fun writeVerify(secretKey: String, account: String, appId: String, role: String): ResponseEntity<Any> {

        if (StringUtils.isEmpty(account)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Error(Error.ERROR_TYPE_FORBID, "userId is null"))
        }

        if(secretKey != secretConfig.secretKey){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Error(Error.ERROR_TYPE_FORBID, "secret key not eq"));
        }

//        val roles : String = appService.getAppMemberRole(appId, account)
//                .awaitFirstOrNull()?:return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Error(Error.ERROR_TYPE_FORBID, "userId is null"));

        verifyService.writeVerify(account, appId, role, true).awaitFirstOrNull()

        return ResponseEntity.ok().build()
    }

}