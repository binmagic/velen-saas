package com.github.binmagic.saas.velen.authority.controller;

import com.github.binmagic.saas.velen.authority.entity.Menu;
import com.github.binmagic.saas.velen.authority.service.MenuService;
import com.github.binmagic.saas.velen.common.component.controller.BaseController;
import com.github.binmagic.saas.velen.common.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController
{

	@Autowired
	MenuService menuService;

	@GetMapping
	public Mono<Page.Result<Menu>> findMenuList(Page.Query query)
	{
		return menuService.findPage(query);
	}

	@PostMapping
	public Mono<Menu> createMenu(@RequestBody Menu menu)
	{
		return menuService.createMenu(menu);
	}


	@PutMapping
	public Mono<Menu> updateMenu(@RequestBody Menu menu){
		return menuService.updateMenu(menu);
	}

	@DeleteMapping("{id}")
	public Mono<Void> deleteMenu(@PathVariable("id") String id){

		return menuService.deleteMenu(id);

	}



}
