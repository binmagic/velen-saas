package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.entity.KeyRule
import com.github.binmagic.saas.velen.config.repository.KeyRuleRepository
import com.github.binmagic.saas.velen.config.service.KeyRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class KeyRuleServiceImpl :KeyRuleService {
    @Autowired
    lateinit var keyRuleRepository: KeyRuleRepository

    override suspend fun getKeyRule(type: String): Flux<KeyRule> {
        return keyRuleRepository.findByType(type)
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