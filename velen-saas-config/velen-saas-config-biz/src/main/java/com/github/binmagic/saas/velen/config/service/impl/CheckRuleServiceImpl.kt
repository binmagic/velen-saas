package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.entity.CheckRule
import com.github.binmagic.saas.velen.config.repository.CheckRuleRepository
import com.github.binmagic.saas.velen.config.service.CheckRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CheckRuleServiceImpl :CheckRuleService {
    @Autowired
    lateinit var checkRuleRepository:CheckRuleRepository

    override suspend fun getCheckRule(appId:String,type: String): Flux<CheckRule> {
        return checkRuleRepository.findByAppIdAndType(appId,type)
    }

    override suspend fun updateCheckRule(checkRule: CheckRule): Mono<CheckRule> {
        return checkRuleRepository.save(checkRule)
    }

    override suspend fun insertCheckRule(checkRule: CheckRule): Mono<CheckRule> {
        return checkRuleRepository.insert(checkRule)
    }

    override suspend fun deleteCheckRule(id: String): Mono<Void> {
        return checkRuleRepository.deleteById(id)
    }
}