package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.common.config.EnumUtil
import com.github.binmagic.saas.velen.config.entity.ParserRule
import com.github.binmagic.saas.velen.config.etl.ProjectApi
import com.github.binmagic.saas.velen.config.event.DeployApp
import com.github.binmagic.saas.velen.config.event.SetInputParse
import com.github.binmagic.saas.velen.config.repository.ParserRuleRepository
import com.github.binmagic.saas.velen.config.service.ParserRuleService
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
class ParserRuleServiceImpl : ParserRuleService {
    @Autowired
    lateinit var parserRuleRepository: ParserRuleRepository

    @Autowired
    lateinit var projectApi: ProjectApi

    override suspend fun getEventRule(appId: String, user: String, type: String): Flux<ParserRule> {
        return parserRuleRepository.findByAppIdAndType(appId, type)
    }

    override suspend fun addEventRule(parserRule: ParserRule): Mono<ParserRule> {
        parserRule.id = null
        val mono = parserRuleRepository.insert(parserRule)
        val list = parserRuleRepository.findByAppIdAndType(parserRule.appId,parserRule.type).collectList().awaitSingle()
        val resp = projectApi.setInputParse(parserRule.appId,ProjectApi.Convert.toParseFormatTDO(list),parserRule.createUser)
        if (EnumUtil.isInResultCode(resp.statusCodeValue)){
            return Mono.error(RuntimeException(ResultCode.valueOf(resp.statusCodeValue).message()))
        }

        return mono
    }

    override suspend fun updateEventRule(parserRule: ParserRule): Mono<ParserRule> {
        return parserRuleRepository.save(parserRule)
    }

    override suspend fun deleteEventRule(id: String): Mono<Void> {
        return parserRuleRepository.deleteById(id)
    }

    override suspend fun findAllEventRule(appId: String): Flux<ParserRule> {
        return parserRuleRepository.findByAppId(appId)
    }
}