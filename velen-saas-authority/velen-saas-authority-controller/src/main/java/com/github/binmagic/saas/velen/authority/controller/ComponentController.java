package com.github.binmagic.saas.velen.authority.controller;

import com.github.binmagic.saas.velen.authority.entity.Component;
import com.github.binmagic.saas.velen.authority.service.ComponentService;
import com.github.binmagic.saas.velen.common.component.controller.BaseController;
import com.github.binmagic.saas.velen.common.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("component")
public class ComponentController extends BaseController
{

	@Autowired
	ComponentService componentService;

	@GetMapping
	public Mono<Page.Result<Component>> findPage(Page.Query query)
	{
		return componentService.findPage(query);
	}

	@PostMapping
	public Mono<Component> createComponent(@RequestBody Component component)
	{
		return componentService.createComponent(component);
	}

	@PutMapping
	public Mono<Component> updateComponent(@RequestBody Component component)
	{
		return componentService.updateComponent(component);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteComponent(@PathVariable String id)
	{
		return componentService.deleteComponent(id);
	}
}
