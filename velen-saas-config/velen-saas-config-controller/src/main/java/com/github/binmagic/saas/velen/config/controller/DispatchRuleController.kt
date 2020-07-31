package com.github.binmagic.saas.velen.config.controller

import cn.hutool.db.PageResult
import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.dto.DispatchRuleListDTO
import com.github.binmagic.saas.velen.config.dto.DispatchRuleSaveDTO
import com.github.binmagic.saas.velen.config.dto.PlatformTypeDTO
import com.github.binmagic.saas.velen.config.dto.ProcessTypeDTO
import com.github.binmagic.saas.velen.config.entity.DispatchRule
import com.github.binmagic.saas.velen.config.service.DispatchRuleService
import com.velen.etl.dispatcher.entity.PlatformType
import com.velen.etl.dispatcher.entity.ProcessType
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.enterprise.inject.spi.Bean

@RestController
@RequestMapping("/dispatch")
class DispatchRuleController : BaseController() {

    @Autowired
    lateinit var dispatchRuleService: DispatchRuleService

    @GetMapping("/platform")
    suspend fun getPlatFormType(): List<PlatformTypeDTO> {
        val list: MutableList<PlatformTypeDTO> = ArrayList()
        val platformTypes = PlatformType.values()

        for (platform in platformTypes) {
            val platformTypeDTO = PlatformTypeDTO()
            platformTypeDTO.name = platform.name
            platformTypeDTO.key = platform.key
            list.add(platformTypeDTO)
        }
        return list
    }

    @GetMapping("/process")
    suspend fun getProcessType(): List<ProcessTypeDTO> {
        val list: MutableList<ProcessTypeDTO> = ArrayList()
        val processTypes = ProcessType.values()
        for (processType in processTypes) {
            val processTypeDTO = ProcessTypeDTO()
            processTypeDTO.name = processType.name
            processTypeDTO.key = processType.key
            list.add(processTypeDTO)
        }
        return list
    }

    @GetMapping
    suspend fun getPage(query: Page.Query): Page.Result<DispatchRuleListDTO> {
        val findResult = dispatchRuleService.findPage(query).awaitSingle()

        val result = Page.Result<DispatchRuleListDTO>()
        result.total = findResult.total

        for (dispatch in findResult.items) {
            val dispatchRuleListDTO = DispatchRuleListDTO()
            BeanUtils.copyProperties(dispatch, dispatchRuleListDTO)
            result.items.add(dispatchRuleListDTO)
        }
        return result
    }

    @PostMapping
    suspend fun create(@Validated @RequestBody dispatchRuleSaveDTO: DispatchRuleSaveDTO):DispatchRule {
        val appId = currentAppId.awaitSingle()
        val dispatchRule = DispatchRule()
        dispatchRule.appId = currentAppId.awaitSingle()
        dispatchRule.userId = currentUserId.awaitSingle()
        BeanUtils.copyProperties(dispatchRuleSaveDTO, dispatchRule)
        return dispatchRuleService.create(dispatchRule,appId).awaitSingle()
    }

    @PutMapping
    suspend fun update(@Validated @RequestBody dispatchRuleListDTO: DispatchRuleListDTO) {
        val dispatchRule = DispatchRule()
        dispatchRule.appId = currentAppId.awaitSingle()
        dispatchRule.userId = currentUserId.awaitSingle()
        BeanUtils.copyProperties(dispatchRuleListDTO, dispatchRule)
        dispatchRuleService.update(dispatchRule).awaitSingle()
    }

}