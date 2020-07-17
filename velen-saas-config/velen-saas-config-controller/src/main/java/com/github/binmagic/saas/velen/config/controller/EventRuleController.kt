package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.FieldRuleTypeDTO
import com.github.binmagic.saas.velen.config.dto.InputParseTypeDTO
import com.github.binmagic.saas.velen.config.entity.KeyRule
import com.github.binmagic.saas.velen.config.entity.ParserRule
import com.github.binmagic.saas.velen.config.etl.ProjectApi
import com.github.binmagic.saas.velen.config.service.KeyRuleService
import com.github.binmagic.saas.velen.config.service.ParserRuleService
import com.velen.etl.verification.entity.VerifyEnum
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard/rule")
class EventRuleController : BaseController() {
    @Autowired
    lateinit var parserRuleService: ParserRuleService

    @GetMapping
    suspend fun getEventRule(): List<ParserRule> {
        val appId = currentAppId.awaitSingle()
        val user = currentUserAccount.awaitSingle()
        return parserRuleService.getEventRule(appId, user,"event").collectList().awaitSingle()
    }

    @PostMapping
    suspend fun addEventRule(@Validated @RequestBody parserRule: ParserRule): ParserRule {
        val appId = currentAppId.awaitSingle()
        val user = currentUserAccount.awaitSingle()
        parserRule.appId = appId
        parserRule.type = "event"
        parserRule.createUser = user
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

    @GetMapping("/inputParseType")
    suspend fun getInputParseType(): List<InputParseTypeDTO> {
        val list: MutableList<InputParseTypeDTO> = ArrayList()
        val inputParseType = VerifyEnum.InputParseType.values()
        for (type in inputParseType) {
            val inputParseTypeDTO = InputParseTypeDTO()
            inputParseTypeDTO.name = type.name
            inputParseTypeDTO.key = type.key
            list.add(inputParseTypeDTO)
        }
        return list
    }

    @GetMapping("/fieldRuleType")
    suspend fun getFieldRuleType(): List<FieldRuleTypeDTO> {
        val list: MutableList<FieldRuleTypeDTO> = ArrayList()
        val fieldRuleType = VerifyEnum.FieldRuleType.values()
        for (type in fieldRuleType) {
            val fieldRuleTypeDTO = FieldRuleTypeDTO()
            fieldRuleTypeDTO.name = type.name
            fieldRuleTypeDTO.key = type.key
            list.add(fieldRuleTypeDTO)
        }
        return list
    }
}