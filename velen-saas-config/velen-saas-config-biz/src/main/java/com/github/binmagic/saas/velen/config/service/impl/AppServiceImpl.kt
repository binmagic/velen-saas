package com.github.binmagic.saas.velen.config.service.impl

import cn.hutool.core.util.IdUtil
import com.github.binmagic.saas.velen.common.config.EnumUtil
import com.github.binmagic.saas.velen.common.constant.Constant
import com.github.binmagic.saas.velen.config.dto.AppAddMemberDTO
import com.github.binmagic.saas.velen.config.dto.AppMemberInfoDTO
import com.github.binmagic.saas.velen.config.entity.App
import com.github.binmagic.saas.velen.config.entity.AppMember
import com.github.binmagic.saas.velen.config.etl.ProjectApi
import com.github.binmagic.saas.velen.config.etl.TableMetadataApi
import com.github.binmagic.saas.velen.config.repository.AppMemberRepository
import com.github.binmagic.saas.velen.config.repository.AppRepository
import com.github.binmagic.saas.velen.config.repository.DashboardRepository
import com.github.binmagic.saas.velen.config.service.*
import com.velen.etl.ResultCode
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.apache.commons.lang3.StringUtils
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

    @Autowired
    lateinit var metadataService: MetadataService

    @Autowired
    lateinit var groupService: GroupService

    @Autowired
    lateinit var keyRuleService: KeyRuleService

    @Autowired
    lateinit var parserRuleService: ParserRuleService

    @Autowired
    lateinit var checkRuleService: CheckRuleService

    @Autowired
    lateinit var dashboardService: DashboardService

    @Autowired
    lateinit var shareDashboardService: ShareDashboardService

    override suspend fun findApp(account: String): Flux<App> {
        val list = appMemberRepository.findByAccount(account).collectList().awaitSingle()
        val apps: ArrayList<String> = ArrayList()
        for (item in list) {
            apps.add(item.appId)
        }
        return appRepository.findByIdInAndState(apps, App.DRAFT)
    }

    override suspend fun createApp(app: App, userId: String): Mono<App> {
        val now = LocalDateTime.now()
        app.appKey = IdUtil.fastSimpleUUID()
        app.createTime = now
        app.state = App.DRAFT
        app.updateTime = now
        app.id = IdUtil.fastSimpleUUID();

        if (appRepository.findByName(app.name).awaitFirstOrNull() != null) {
            return Mono.error(RuntimeException("app名重复"))
        }

        val resp1 = tableMetadataApi.createApp(app.id, app.name ,app.owner)
        if (EnumUtil.isInResultCode(resp1.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(resp1.statusCodeValue).message()))
        }
        val resp2 = projectApi.create(app.id, false, app.owner)
        if (EnumUtil.isInResultCode(resp2.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(resp2.statusCodeValue).message()))
        }
        appMemberRepository.insert(AppMember(app.id, app.owner, Constant.ROLE_MANAGER)).awaitSingle()
        appRepository.insert(app).awaitSingle()

        if (!StringUtils.isBlank(app.extend)) {
            val source = appRepository.findById(app.extend).awaitSingle()
            extend(source, app, userId)
        }

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
        app.state = 0
        val resp = tableMetadataApi.dropApp(appId, app.owner)
        if (resp.statusCodeValue == ResultCode.GENERATOR_DATABASE_NOT_EXISTS.code()) {
            return appRepository.save(app).then()
        }
        if (EnumUtil.isInResultCode(resp.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(resp.statusCodeValue).message()))
        }

        val resp2 = projectApi.destroy(appId)
        if (EnumUtil.isInResultCode(resp2.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(resp2.statusCodeValue).message()))
        }

        return appRepository.save(app).then()
    }

    override suspend fun findAppTemplates(): Flux<App> {
        return appRepository.findByExtendIsNull()
    }

    private suspend fun extend(source: App, target: App, userId: String) {

        val eventProps = metadataService.findAllMetaEventProp(source.id).collectList().awaitSingle()

        val events = metadataService.findAllMetaEvent(source.id).collectList().awaitSingle()

        val dashboards = dashboardService.findAllDashboard(source.id).collectList().awaitSingle()

        val shareDashboards = shareDashboardService.findAllShareDashboard(source.id).collectList().awaitSingle()

        val groups = groupService.findAllGroup(source.id).collectList().awaitSingle()

        val parserRules = parserRuleService.findAllEventRule(source.id).collectList().awaitSingle()

        val keyRules = keyRuleService.findAllKeyRule(source.id).collectList().awaitSingle()

        val checkRules = checkRuleService.findAllCheckRule(source.id).collectList().awaitSingle()

        val eventPropIdMapping = hashMapOf<String, String>()

        for (eventProp in eventProps) {
            val eventPropClone = eventProp.clone()
            eventPropClone.createUser = target.owner
            eventPropClone.appId = target.id
            metadataService.createMetaEventProp(eventPropClone).awaitSingle()
            eventPropIdMapping[eventProp.id] = eventPropClone.id
        }

        for (event in events) {
            val eventClone = event.clone()
            val eventPropIds = arrayListOf<String>()
            for (propId in event.propIds) {
                val newId = eventPropIdMapping[propId]
                if (newId != null)
                    eventPropIds.add(newId)
            }
            eventClone.propIds = eventPropIds
            eventClone.appId = target.id
            eventClone.createUser = target.owner
            metadataService.createMetaEvent(eventClone).awaitSingle()
        }

        val groupIdMapping = hashMapOf<String, String>()

        for (group in groups) {
            val groupClone = group.clone()
            groupClone.appId = target.id
            groupClone.userId = userId

            groupService.createGroup(groupClone).awaitSingle()
            groupIdMapping[group.id] = groupClone.id
        }

        for (dashboard in dashboards) {
            val dashboardClone = dashboard.clone()
            dashboardClone.userId = userId
            dashboardClone.appId = target.id
            dashboardClone.userName = target.owner
            groupIdMapping.forEach {
                if (dashboard.type == it.key) {
                    dashboardClone.type = it.value
                    return@forEach
                }
            }
            dashboardService.createDashboard(dashboardClone).awaitSingle()
        }

        for (shareDashboard in shareDashboards) {
            val shareDashboardClone = shareDashboard.clone()
            shareDashboardClone.appId = target.id
            groupIdMapping.forEach {
                if (shareDashboard.type == it.key) {
                    shareDashboardClone.type = it.value
                    return@forEach
                }
            }
            shareDashboardService.createShareDashboard(shareDashboardClone).awaitSingle()
        }



        for (parserRule in parserRules) {
            val parserRuleClone = parserRule.clone()
            parserRuleClone.appId = target.id
            parserRuleClone.createUser = target.owner
            parserRuleService.addEventRule(parserRuleClone).awaitSingle()
        }

        for (keyRule in keyRules) {
            val keyRuleClone = keyRule.clone()
            keyRuleClone.appId = target.id
            keyRuleClone.createUser = target.owner
            keyRuleService.insertKeyRule(keyRuleClone).awaitSingle()
        }

        for (checkRule in checkRules) {
            val checkRuleClone = checkRule.clone()
            checkRuleClone.appId = target.id
            checkRuleClone.createUser = target.owner
            checkRuleService.insertCheckRule(checkRuleClone).awaitSingle()
        }
    }

}