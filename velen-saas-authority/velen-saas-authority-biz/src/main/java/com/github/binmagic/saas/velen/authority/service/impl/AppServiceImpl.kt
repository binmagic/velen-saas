package com.github.binmagic.saas.velen.authority.service.impl

import cn.hutool.core.util.IdUtil
import com.github.binmagic.saas.velen.authority.dto.AppAddMemberDTO
import com.github.binmagic.saas.velen.authority.dto.AppMemberInfoDTO
import com.github.binmagic.saas.velen.authority.entity.App
import com.github.binmagic.saas.velen.authority.entity.AppMember
import com.github.binmagic.saas.velen.authority.etl.ProjectApi
import com.github.binmagic.saas.velen.authority.etl.TableMetadataApi
import com.github.binmagic.saas.velen.authority.repository.AppMemberRepository
import com.github.binmagic.saas.velen.authority.repository.AppRepository
import com.github.binmagic.saas.velen.authority.service.AppService
import com.github.binmagic.saas.velen.common.constant.Constant
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class AppServiceImpl : AppService {

    @Autowired
    lateinit var appRepository: AppRepository

    @Autowired
    lateinit var appMemberRepository: AppMemberRepository

    @Autowired
    lateinit var tableMetadataApi: TableMetadataApi

    @Autowired
    lateinit var projectApi: ProjectApi


    override suspend fun findApp(account: String): Flux<App> {
        val list = appMemberRepository.findByAccount(account).collectList().awaitSingle()
        val apps: ArrayList<String> = ArrayList()
        for (item in list) {
            apps.add(item.appId)
        }
        return appRepository.findByIdIn(apps)
    }

    override suspend fun createApp(app: App): Mono<App> {
        val now = LocalDateTime.now()
        app.appKey = IdUtil.fastSimpleUUID()
        app.createTime = now
        app.state = App.DRAFT
        app.updateTime = now
        app.id = IdUtil.fastSimpleUUID();

        if (appRepository.findByName(app.name).awaitFirstOrNull() != null) {
            return Mono.error(RuntimeException("app名重复"))
        }

        val resp1 = tableMetadataApi.createApp(app.id, app.owner)
        if (resp1.statusCode != HttpStatus.OK) {
            return Mono.error(RuntimeException())
        }
        /*val resp2 = projectApi.create(app.id, "topic", false, app.owner)
        if (resp2.statusCode != HttpStatus.OK) {
            return Mono.error(RuntimeException())
        }*/
        appMemberRepository.insert(AppMember(app.id, app.owner, Constant.ROLE_MANAGER)).awaitSingle()
        appRepository.insert(app).awaitSingle()

        return Mono.just(app)
    }

    override suspend fun submitToAuditor(appId: String): Mono<Void> {
        val app = appRepository.findById(appId).awaitSingle()
        app.state = App.WAIT_CHECK
        return appRepository.save(app).then()
    }

    override suspend fun updateApp(app: App): Mono<App> {
        val app1 = getApp(app.id).awaitSingle()
        app1.name = app.name
        app1.desc = app.desc

        return appRepository.save(app1)
    }

    override suspend fun getApp(appId: String): Mono<App> {
        return appRepository.findById(appId)
    }

    override suspend fun getAppMember(appId: String): Flux<AppMemberInfoDTO> {
        val appMembers = appMemberRepository.findByAppId(appId).collectList().awaitSingle()
        val flux: Flux<AppMemberInfoDTO> = Flux.just()
        for (appMember in appMembers) {
            val appMemberInfoDTO = AppMemberInfoDTO()
            BeanUtils.copyProperties(appMember, appMemberInfoDTO)
            flux.concatWithValues(appMemberInfoDTO)
        }
        return flux
    }

    override suspend fun getAppMemberRole(appId: String, account: String): Mono<String> {
        val appMember = appMemberRepository.findFirstByAccountAndAppId(account, appId).awaitSingle()
        return Mono.just(appMember.role)
    }

    override suspend fun addMember(appId: String, appAddMemberDTO: List<AppAddMemberDTO>): Mono<Void> {
        val app = appRepository.findById(appId).awaitSingle()
        val monoList = ArrayList<Mono<AppMember>>(appAddMemberDTO.size)
        for (dto in appAddMemberDTO) {
            val appMember = AppMember()
            appMember.appId = app.id
            appMember.account = dto.account
            appMember.role = dto.role
            monoList.add(appMemberRepository.insert(appMember))
        }
        return Flux.concat(monoList).then()
    }

    override suspend fun deleteMember(appId: String, id: String): Mono<Void> {
        return appMemberRepository.deleteById(id)
    }

    override suspend fun deleteApp(appId: String): Mono<Void> {
        val app = getApp(appId).awaitSingle()
        val resp = tableMetadataApi.dropApp(appId, app.owner)
        if (resp.statusCode != HttpStatus.OK) {
            return Mono.error(RuntimeException())
        }
        return appRepository.deleteById(appId)
    }
}