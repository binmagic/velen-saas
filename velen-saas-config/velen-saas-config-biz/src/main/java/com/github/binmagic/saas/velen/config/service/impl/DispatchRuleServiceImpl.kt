package com.github.binmagic.saas.velen.config.service.impl

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.IdUtil
import com.github.binmagic.saas.velen.common.config.EnumUtil
import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.common.util.StringFormat
import com.github.binmagic.saas.velen.config.entity.App
import com.github.binmagic.saas.velen.config.entity.DispatchRule
import com.github.binmagic.saas.velen.config.repository.AppRepository
import com.github.binmagic.saas.velen.config.repository.DispatchRuleRepository
import com.github.binmagic.saas.velen.config.service.DispatchRuleService
import com.velen.etl.ResultCode
import com.velen.etl.dispatcher.entity.JobSubmitTDO
import com.velen.etl.dispatcher.restful.api.DispatchApi
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.commons.util.IdUtils
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDateTime

@Service
class DispatchRuleServiceImpl : DispatchRuleService {

    @Autowired
    lateinit var dispatchRuleRepository: DispatchRuleRepository

    @Autowired
    lateinit var dispatchApi: DispatchApi

    @Autowired
    lateinit var appRepository: AppRepository

    override suspend fun getAll(): Flux<DispatchRule> {
        return dispatchRuleRepository.findAll()
    }

    override suspend fun findPage(query: Page.Query): Mono<Page.Result<DispatchRule>> {
        val orderList: MutableList<Sort.Order> = ArrayList(query.sorts.size)

        for ((key, value) in query.sorts) {
            orderList.add(Sort.Order(Sort.Direction.valueOf(value!!), key))
        }

        var exampleMatcher = ExampleMatcher.matchingAll()

        if (query.params.isNotEmpty()) {
            exampleMatcher = ExampleMatcher.matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase(true)
            for ((key) in query.params) {
                exampleMatcher.withMatcher(key, ExampleMatcher.GenericPropertyMatchers.contains())
            }
        }

//        val pageRequest = PageRequest.of(query.page - 1, query.limit, Sort.by(orderList))

        val example = Example.of(DispatchRule.EMPTY, exampleMatcher)
        example.probe.properties = null
        example.probe.appParameters = null
        example.probe.environmentVariables = null

        val total = dispatchRuleRepository.count(example).awaitSingle()

        if (query.limit == 0 || query.limit == null) {
            query.limit = total.toInt()
        }

        val pageRequest = PageRequest.of(query.page - 1, query.limit, Sort.by(orderList))

        val items = dispatchRuleRepository.findAll(example, pageRequest).collectList().awaitSingle()

        return Page.Result(total, items).toMono()
    }

    override suspend fun create(dispatchRule: DispatchRule, appId: String): Mono<DispatchRule> {

        dispatchRule.createTime = LocalDateTime.now()
        dispatchRule.updateTime = LocalDateTime.now()
        val item = dispatchRuleRepository.findByBusinessName(dispatchRule.businessName).awaitFirstOrNull()
        if (item != null) {
            return Mono.error(RuntimeException("业务名重复"))
        }

        return dispatchRuleRepository.insert(dispatchRule)
    }

    override suspend fun update(dispatchRule: DispatchRule): Mono<DispatchRule> {

        dispatchRule.updateTime = LocalDateTime.now()

        return dispatchRuleRepository.save(dispatchRule)
    }

    override suspend fun fast(dispatchRule: DispatchRule): Mono<DispatchRule> {
        val dispatchRuleDTO = DispatchRule()
        BeanUtil.copyProperties(dispatchRule,dispatchRuleDTO)
        val app = appRepository.findById(dispatchRule.appId).awaitSingle()

        val map = BeanUtil.beanToMap(app)
        val name = DispatchApi.make(dispatchRule.appId, dispatchRule.businessName)
        map["pid"] = IdUtil.fastSimpleUUID()
        val propMap: MutableMap<String, String> = HashMap()
        if (!dispatchRule.properties.isNullOrEmpty()) {
            for (entry in dispatchRule.properties) {
                entry.setValue(StringFormat.format(entry.value, map))
            }

            for (entry in dispatchRule.properties) {
                val key = entry.key.replace("_", ".")
                propMap[key] = entry.value
            }
        }

        val environmentMap: MutableMap<String, String> = HashMap()

        if (!dispatchRule.environmentVariables.isNullOrEmpty()) {
            for (entry in dispatchRule.environmentVariables) {
                entry.setValue(StringFormat.format(entry.value, map))
            }

            for (entry in dispatchRule.environmentVariables) {
                val key = entry.key.replace("_", ".")
                environmentMap[key] = entry.value
            }
        }
        val jobSubmitTDO = JobSubmitTDO()
        jobSubmitTDO.appId = IdUtil.fastSimpleUUID()
        jobSubmitTDO.appName = name
        jobSubmitTDO.appResource = dispatchRule.dsl
        jobSubmitTDO.appParameters = dispatchRule.appParameters
        jobSubmitTDO.environmentVariables = dispatchRule.environmentVariables
        jobSubmitTDO.platformProperties = dispatchRule.properties
        val resp = dispatchApi.deploy(dispatchRule.platform, dispatchRule.process, name, dispatchRule.dsl, propMap)
        //val resp = dispatchApi.deploy(dispatchRule.platform,dispatchRule.process, jobSubmitTDO)
        if (EnumUtil.isInResultCode(resp.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(resp.statusCodeValue).message()))
        }

        return dispatchRuleRepository.save(dispatchRuleDTO)
    }


}