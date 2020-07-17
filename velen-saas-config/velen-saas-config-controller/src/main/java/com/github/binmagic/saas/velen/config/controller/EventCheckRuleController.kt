package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.entity.CheckRule
import com.github.binmagic.saas.velen.config.service.CheckRuleService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard/eventCheckRule")
class EventCheckRuleController :BaseController() {

    @Autowired
    lateinit var checkRuleService: CheckRuleService

    @GetMapping
    suspend fun getEventCheckRule():List<CheckRule>{
        val appId= currentAppId.awaitSingle()
        return checkRuleService.getCheckRule(appId,"event").collectList().awaitSingle()
    }

    @PostMapping
    suspend fun addEventCheckRule(@Validated @RequestBody checkRule: CheckRule):CheckRule{
        val appId = currentAppId.awaitSingle()
        val user = currentUserAccount.awaitSingle()
        checkRule.appId = appId
        checkRule.createUser = user
        return checkRuleService.insertCheckRule(checkRule).awaitSingle()
    }

    @PutMapping
    suspend fun updateCheckRule(@Validated @RequestBody checkRule: CheckRule) : CheckRule{
        return checkRuleService.updateCheckRule(checkRule).awaitSingle()
    }

    @DeleteMapping("/{id}")
    suspend fun deleteCheckRule(@PathVariable id:String){
        checkRuleService.deleteCheckRule(id).awaitFirstOrNull()
    }

}