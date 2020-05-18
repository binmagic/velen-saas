package com.github.binmagic.saas.velen.authority.controller

import com.github.binmagic.saas.velen.authority.entity.Menu
import com.github.binmagic.saas.velen.authority.entity.Resource
import com.github.binmagic.saas.velen.authority.service.ResourceService
import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.common.entity.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("resource")
class ResourceController : BaseController() {


    @Autowired
    lateinit var resourceService : ResourceService

    @GetMapping
    suspend fun findResourceList(query : Page.Query) : Mono<Page.Result<Resource>> {
        return resourceService.findPage(query)
    }


    @PostMapping
    suspend fun createResource(@RequestBody resource: Resource): Mono<Resource> {
        return resourceService.createResource(resource)
    }


    @PutMapping
    suspend fun updateResource(@RequestBody resource: Resource): Mono<Resource> {
        return resourceService.updateResource(resource)
    }

    @DeleteMapping("{id}")
    suspend fun deleteResource(@PathVariable("id") id: String): Mono<Void> {
        return resourceService.deleteResource(id)
    }


}