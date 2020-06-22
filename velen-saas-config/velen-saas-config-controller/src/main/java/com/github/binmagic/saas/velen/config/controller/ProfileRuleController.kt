package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.entity.KeyRule
import com.github.binmagic.saas.velen.config.entity.ParserRule
import com.github.binmagic.saas.velen.config.service.KeyRuleService
import com.github.binmagic.saas.velen.config.service.ParserRuleService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard/profileRule")
class ProfileRuleController : BaseController() {
    @Autowired
    lateinit var parserRuleService: ParserRuleService


    @GetMapping
    suspend fun getEventRule(): List<ParserRule> {
        val appId = currentAppId.awaitSingle()
        return parserRuleService.getEventRule(appId, "profile").collectList().awaitSingle()
    }

    @PostMapping
    suspend fun addEventRule(@Validated @RequestBody parserRule: ParserRule): ParserRule {
        val appId = currentAppId.awaitSingle()
        parserRule.appId = appId
        parserRule.type = "profile"
        return parserRuleService.addEventRule(parserRule).awaitSingle()
    }

    @PutMapping
    suspend fun updateEventRule(@Validated @RequestBody parserRule: ParserRule): ParserRule {
        return parserRuleService.updateEventRule(parserRule).awaitSingle()
    }

    @DeleteMapping("/{id}")
    suspend fun deleteEventRule(@PathVariable id: String) {
        parserRuleService.deleteEventRule(id).awaitFirstOrNull()
    }


}