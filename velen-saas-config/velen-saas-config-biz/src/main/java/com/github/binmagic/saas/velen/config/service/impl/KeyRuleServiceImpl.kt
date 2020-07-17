package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.common.config.EnumUtil
import com.github.binmagic.saas.velen.config.dto.InputParseTypeDTO
import com.github.binmagic.saas.velen.config.entity.KeyRule
import com.github.binmagic.saas.velen.config.etl.ProjectApi
import com.github.binmagic.saas.velen.config.event.DeployApp
import com.github.binmagic.saas.velen.config.event.SetFieldRule
import com.github.binmagic.saas.velen.config.repository.KeyRuleRepository
import com.github.binmagic.saas.velen.config.service.KeyRuleService
import com.velen.etl.ResultCode
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.RuntimeException

@Service
class KeyRuleServiceImpl : KeyRuleService {
    @Autowired
    lateinit var keyRuleRepository: KeyRuleRepository

    @Autowired
    lateinit var projectApi: ProjectApi

    override suspend fun getKeyRule(appId: String, user: String, type: String): Flux<KeyRule> {

        return keyRuleRepository.findByAppIdAndType(appId, type)

    }

    override suspend fun insertKeyRule(keyRule: KeyRule): Mono<KeyRule> {
        val mono = keyRuleRepository.insert(keyRule)
        val list = keyRuleRepository.findByAppIdAndType(keyRule.appId, keyRule.type).collectList().awaitSingle()
        val resp = projectApi.setFieldRule(keyRule.appId, ProjectApi.Convert.toFieldRuleTDO(list), keyRule.createUser)
        if (EnumUtil.isInResultCode(resp.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(resp.statusCodeValue).message()))
        }

        return mono
    }

    override suspend fun updateKeyRule(keyRule: KeyRule): Mono<KeyRule> {
        return keyRuleRepository.save(keyRule)
    }

    override suspend fun deleteKeyRule(id: String): Mono<Void> {
        return keyRuleRepository.deleteById(id)
    }

}