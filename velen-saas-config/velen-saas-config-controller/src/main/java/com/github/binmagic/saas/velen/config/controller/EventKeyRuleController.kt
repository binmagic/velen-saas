package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.entity.KeyRule
import com.github.binmagic.saas.velen.config.service.KeyRuleService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard/eventKeyRule")
class EventKeyRuleController : BaseController() {
    @Autowired
    lateinit var keyRuleService: KeyRuleService

    @GetMapping
    suspend fun getEventKeyRule(): List<KeyRule> {
        val appId=currentAppId.awaitSingle()
        return keyRuleService.getKeyRule(appId,"event").collectList().awaitSingle()
    }

    @PostMapping
    suspend fun insertKeyRule(@Validated @RequestBody keyRule: KeyRule): KeyRule {
        return keyRuleService.insertKeyRule(keyRule).awaitSingle()
    }

    @PutMapping
    suspend fun updateEventKeyRule(@Validated @RequestBody keyRule: KeyRule): KeyRule {
        return keyRuleService.updateKeyRule(keyRule).awaitSingle()
    }

    @DeleteMapping("/{id}")
    suspend fun deleteEventKeyRule(@PathVariable id: String) {
        keyRuleService.deleteKeyRule(id).awaitFirstOrNull()
    }
}