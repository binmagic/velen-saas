package com.github.binmagic.saas.velen.authority.controller;

import com.github.binmagic.saas.velen.authority.entity.Function;
import com.github.binmagic.saas.velen.authority.service.FunctionService;
import com.github.binmagic.saas.velen.common.component.controller.BaseController;
import com.github.binmagic.saas.velen.common.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("function")
public class FunctionController extends BaseController
{


	@Autowired
	FunctionService functionService;

	@GetMapping
	public Mono<Page.Result<Function>> findPage(Page.Query query)
	{
		return functionService.findPage(query);
	}

	@PostMapping
	public Mono<Function> createFunction(@RequestBody Function function)
	{
		return functionService.createFunction(function);
	}

	@DeleteMapping("/{id}")
	public Mono<String> deleteFunction(@PathVariable(value = "id") String id)
	{
		return functionService.deleteFunction(id);
	}

	@PutMapping
	public Mono<Function> updateFunction(@RequestBody Function function)
	{
		return functionService.updateFunction(function);
	}


}
