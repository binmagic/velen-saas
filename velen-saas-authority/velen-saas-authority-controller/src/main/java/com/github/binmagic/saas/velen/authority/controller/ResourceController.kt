package com.github.binmagic.saas.velen.authority.controller

import com.github.binmagic.saas.velen.authority.entity.Resource
import com.github.binmagic.saas.velen.authority.service.ResourceService
import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.common.entity.Page
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/permission/resource")
class ResourceController : BaseController() {


    @Autowired
    lateinit var resourceService : ResourceService

    @GetMapping
    suspend fun findResourceList(query : Page.Query) = mono(Unconfined) {
        resourceService.findPage(query)
    }

    @PostMapping
    suspend fun createResource(@RequestBody resource: Resource) = mono(Unconfined) {
        resourceService.createResource(resource)
    }

    @PutMapping
    suspend fun updateResource(@RequestBody resource: Resource) = mono(Unconfined)  {
        resourceService.updateResource(resource)
    }

    @DeleteMapping("{id}")
    suspend fun deleteResource(@PathVariable("id") id: String) = mono(Unconfined) {
        resourceService.deleteResource(id)
    }


}