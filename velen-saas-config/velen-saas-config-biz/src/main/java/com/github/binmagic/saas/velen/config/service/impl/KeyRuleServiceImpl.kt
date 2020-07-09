package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.entity.KeyRule
import com.github.binmagic.saas.velen.config.event.DeployApp
import com.github.binmagic.saas.velen.config.event.SetFieldRule
import com.github.binmagic.saas.velen.config.repository.KeyRuleRepository
import com.github.binmagic.saas.velen.config.service.KeyRuleService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class KeyRuleServiceImpl :KeyRuleService {
    @Autowired
    lateinit var keyRuleRepository: KeyRuleRepository

    @Autowired
    lateinit var applicationContext: ApplicationContext

    override suspend fun getKeyRule(appId:String,type: String): Flux<KeyRule> {
        val mono=keyRuleRepository.findByType(type)
        val list = mono.collectList().awaitSingle()
        mono.subscribe {
            applicationContext.publishEvent(SetFieldRule(appId,list,"admin"))

            applicationContext.publishEvent(DeployApp(appId,"type","admin"))
        }
        return mono

    }

    override suspend fun insertKeyRule(keyRule: KeyRule): Mono<KeyRule> {
        return keyRuleRepository.insert(keyRule)
    }

    override suspend fun updateKeyRule(keyRule: KeyRule): Mono<KeyRule> {
        return keyRuleRepository.save(keyRule)
    }

    override suspend fun deleteKeyRule(id: String): Mono<Void> {
        return keyRuleRepository.deleteById(id)
    }

}