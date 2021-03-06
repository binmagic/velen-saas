package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.dto.*
import com.github.binmagic.saas.velen.config.entity.MetaEvent
import com.github.binmagic.saas.velen.config.entity.MetaEventProp
import com.github.binmagic.saas.velen.config.service.MetadataService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RequestMapping("metadata")
@RestController
class MetadataController : BaseController() {

    @Autowired
    lateinit var metadataService: MetadataService

    @GetMapping("event")
    suspend fun findPageMetaEvent(query: Page.Query): Page.Result<MetaEventDTO> {

        val appId = currentAppId.awaitSingle()
        val user = currentUserAccount.awaitSingle()

        val pageResult = metadataService.findMetaEventByPage(query, appId, user).awaitSingle()

        val result = Page.Result<MetaEventDTO>()
        result.total = pageResult.total

        for (entry in pageResult.items) {
            val metaEventDTO = MetaEventDTO()
            BeanUtils.copyProperties(entry, metaEventDTO)
            result.items.add(metaEventDTO)
        }

        return result
    }


    @PostMapping("event")
    suspend fun createMetaEvent(@Validated @RequestBody metaEventSaveDTO: MetaEventSaveDTO) {

        val metaEvent = MetaEvent()
        metaEvent.appId = currentAppId.awaitSingle()
        metaEvent.createUser = currentUserId.awaitSingle()
        metaEvent.isVisible = true
        BeanUtils.copyProperties(metaEventSaveDTO, metaEvent)

        metadataService.createMetaEvent(metaEvent).awaitSingle()
    }


    @GetMapping("/event/prop")
    suspend fun findPageMetaEventProp(query: Page.Query): Page.Result<MetaEventPropDTO> {
        val appId = currentAppId.awaitSingle()

        val pageResult = metadataService.findMetaEventPropByPage(query, appId).awaitSingle()

        val result = Page.Result<MetaEventPropDTO>()
        result.total = pageResult.total

        for (entry in pageResult.items) {

            val metaEventPropDTO = MetaEventPropDTO()
            BeanUtils.copyProperties(entry, metaEventPropDTO)
            result.items.add(metaEventPropDTO)
        }

        return result
    }

    @PostMapping("/event/prop")
    suspend fun createMetaEventProp(@Validated @RequestBody metaEventPropSaveDTO: MetaEventPropSaveDTO) {

        val metaEventProp = MetaEventProp()

        BeanUtils.copyProperties(metaEventPropSaveDTO, metaEventProp)

        metaEventProp.appId = currentAppId.awaitSingle()
        metaEventProp.createUser = currentUserId.awaitSingle()

        metadataService.createMetaEventProp(metaEventProp).awaitSingle()
    }

    @PutMapping("event")
    suspend fun updateMetaEvent(@Validated @RequestBody metaEventDTO: MetaEventDTO) {
        val metaEvent = MetaEvent()
        metaEvent.appId = currentAppId.awaitSingle()
        metaEvent.createUser = currentUserId.awaitSingle()
        BeanUtils.copyProperties(metaEventDTO, metaEvent)
        metadataService.updateMetaEvent(metaEvent).awaitFirstOrNull()
    }

    @PutMapping("/event/prop")
    suspend fun updateMetaEventProp(@Validated @RequestBody metaEventPropDTO: MetaEventPropDTO) {
        val metaEventProp = MetaEventProp()

        metaEventProp.appId = currentAppId.awaitSingle()
        metaEventProp.createUser = currentUserId.awaitSingle()

        BeanUtils.copyProperties(metaEventPropDTO,metaEventProp)
        metadataService.updateMetaEventProp(metaEventProp).awaitFirstOrNull()
    }

    @DeleteMapping("/event/prop/{id}")
    suspend fun deleteMetaEventProp(@PathVariable id : String) {
        val appId= currentAppId.awaitSingle()
        metadataService.deleteMetaEventProp(appId, id).awaitFirstOrNull()
    }

    @GetMapping("/all")
    suspend fun all(){
        val appId = currentAppId.awaitSingle()
        val user = currentUserAccount.awaitSingle()
        metadataService.all(appId, user)
    }
}