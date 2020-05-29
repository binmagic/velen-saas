package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.dto.MetaEventPropDTO
import com.github.binmagic.saas.velen.config.service.MetadataService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("metadata")
@RestController
class MetadataController : BaseController() {

    @Autowired
    lateinit var metadataService: MetadataService

    @GetMapping("/event/prop")
    suspend fun findPageMetaEventProp(query : Page.Query) : Page.Result<MetaEventPropDTO>{

        val pageResult = metadataService.findMetaEventPropByPage(query).awaitSingle()

        val result = Page.Result<MetaEventPropDTO>()
        result.total = pageResult.total

        for(entry in pageResult.items){

            val metaEventPropDTO = MetaEventPropDTO()
            BeanUtils.copyProperties(entry, metaEventPropDTO)
            result.items.add(metaEventPropDTO)
        }

        return result
    }

}