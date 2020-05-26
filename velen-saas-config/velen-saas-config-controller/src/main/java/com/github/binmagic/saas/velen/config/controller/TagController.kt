package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.entity.Tag
import com.github.binmagic.saas.velen.config.service.TagService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("tag")
@RestController
class TagController : BaseController() {

    @Autowired
    lateinit var tagService: TagService

    @GetMapping
    suspend fun all(): List<Tag> {
        return tagService.getAll().collectList().awaitSingle()
    }
}