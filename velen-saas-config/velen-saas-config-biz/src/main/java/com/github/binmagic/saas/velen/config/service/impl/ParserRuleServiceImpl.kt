package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.entity.ParserRule
import com.github.binmagic.saas.velen.config.event.DeployApp
import com.github.binmagic.saas.velen.config.event.SetInputParse
import com.github.binmagic.saas.velen.config.repository.ParserRuleRepository
import com.github.binmagic.saas.velen.config.service.ParserRuleService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ParserRuleServiceImpl : ParserRuleService {
    @Autowired
    lateinit var parserRuleRepository: ParserRuleRepository

    @Autowired
    lateinit var applicationContext: ApplicationContext

    override suspend fun getEventRule(appId: String, type: String): Flux<ParserRule> {
        val mono = parserRuleRepository.findByAppIdAndType(appId, type)
        val list =mono.collectList().awaitSingle()
        mono.subscribe {
            applicationContext.publishEvent(SetInputParse( appId, list, "admin"))

            applicationContext.publishEvent(DeployApp(appId, "type","admin"))
        }
        return mono
    }

    override suspend fun addEventRule(parserRule: ParserRule): Mono<ParserRule> {
        return parserRuleRepository.insert(parserRule)
    }

    override suspend fun updateEventRule(parserRule: ParserRule): Mono<ParserRule> {
        return parserRuleRepository.save(parserRule)
    }

    override suspend fun deleteEventRule(id: String): Mono<Void> {
        return parserRuleRepository.deleteById(id)
    }
}