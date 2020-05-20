package com.github.binmagic.saas.velen.authority.controller

import com.github.binmagic.saas.velen.authority.dto.*
import com.github.binmagic.saas.velen.authority.entity.App
import com.github.binmagic.saas.velen.authority.service.AppService
import com.github.binmagic.saas.velen.common.component.controller.BaseController
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.asFlux
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.adapter.rxjava.toFlowable
import reactor.util.function.Tuple3

@RestController
@RequestMapping("/accounts/app")
class AppController : BaseController(){

    @Autowired
    lateinit var appService: AppService

    @GetMapping
    suspend fun getAppInfo(): AppInfoDTO {
        val appId = currentAppId.awaitSingle()
        val app = appService.getApp(appId).awaitSingle()
        val appInfoDTO = AppInfoDTO()
        BeanUtils.copyProperties(app, appInfoDTO)
        return appInfoDTO
    }

    @GetMapping("/member")
    suspend fun getAppMemberInfo(): List<AppMemberInfoDTO> {

        val appId = currentAppId.awaitSingle()

        return appService.getAppMember(appId)
                .collectList().awaitSingle()
    }

    @GetMapping("my")
    suspend fun getApp(): List<AppInfoDTO> {
        val account = currentUserAccount.awaitSingle()
        val apps : List<App>  = appService.findApp(account).collectList().awaitSingle()
        val appInfoDTOs : ArrayList<AppInfoDTO> = ArrayList()
        for(app in apps){
            val appInfoDTO = AppInfoDTO()
            BeanUtils.copyProperties(app, appInfoDTO)
            appInfoDTOs.add(appInfoDTO)
        }
        return appInfoDTOs
    }

    @PutMapping
    suspend fun updateAppInfo(@Validated @RequestBody appUpdateDTO: AppUpdateDTO): ResponseEntity<AppInfoDTO> {
        val appId = currentAppId.awaitSingle()
        var app = appService.getApp(appId).awaitSingle()
        appUpdateDTO.id = appId
        BeanUtils.copyProperties(appUpdateDTO, app)
        app = appService.updateApp(app).awaitSingle()
        val appInfoDTO = AppInfoDTO()
        BeanUtils.copyProperties(app, appInfoDTO)
        return ResponseEntity.ok(appInfoDTO)
    }

    @PostMapping
    suspend fun createApp(@Validated @RequestBody appSaveDTO: AppSaveDTO): AppInfoDTO {
        var app = App()
        app.owner = currentUserAccount.awaitSingle()
        BeanUtils.copyProperties(appSaveDTO, app)
        app = appService.createApp(app).awaitSingle()
        val appInfoDTO = AppInfoDTO()
        BeanUtils.copyProperties(app, appInfoDTO)
        return appInfoDTO
    }

    @PostMapping("/member")
    suspend fun addMember(@Validated @RequestBody appAddMemberDTO: List<AppAddMemberDTO>) {
        appService.addMember(currentAppId.awaitSingle(), appAddMemberDTO)
                .awaitFirstOrNull()
    }

    @DeleteMapping("/member/{id}")
    suspend fun deleteMember(@PathVariable id: String): Mono<Void> {
        return appService.deleteMember(currentAppId.awaitSingle(), id)
    }

}