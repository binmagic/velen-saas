package com.github.binmagic.saas.velen.authority.controller;


import com.github.binmagic.saas.velen.authority.entity.Role;
import com.github.binmagic.saas.velen.authority.entity.RoleComponent;
import com.github.binmagic.saas.velen.authority.entity.RoleFunction;
import com.github.binmagic.saas.velen.authority.entity.RoleMenu;
import com.github.binmagic.saas.velen.authority.service.RoleService;
import com.github.binmagic.saas.velen.common.component.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("role")
public class RoleController extends BaseController
{

	@Autowired
	RoleService roleService;


	@GetMapping
	public Flux<Role> getRoleList()
	{
		return roleService.getRoleList();
	}

	@PostMapping
	public Mono<Role> createRole(@RequestBody Role role)
	{
		return roleService.addRole(role);
	}


	@DeleteMapping("/{id}")
	public Mono<Void> deleteRole(@PathVariable String id)
	{
		return roleService.deleteRole(id);
	}

	@PutMapping
	public Mono<Role> updateRole(@RequestBody Role role)
	{
		return roleService.updateRole(role);
	}


	@GetMapping("/function")
	public Flux<RoleFunction> getOwnerFunctionRoles(@RequestParam ArrayList<String> functionIds)
	{
		return roleService.getOwnerFunctionRoles(functionIds);
	}

	@GetMapping("/menu")
	public Flux<RoleMenu> getOwnerMenuRoles(@RequestParam ArrayList<String> menuIds)
	{
		return roleService.getOwnerMenuRoles(menuIds);
	}

	@GetMapping("/component")
	public Flux<RoleComponent> getOwnerComponentRoles(@RequestParam ArrayList<String> componentIds)
	{
		return roleService.getOwnerComponentRoles(componentIds);
	}

	@PostMapping("/function")
	public Mono<Void> saveFunctionRoles(@RequestBody Map<String, Map<String, Boolean>> data)
	{
		return roleService.saveFunctionRoles(data);
	}

	@PostMapping("/menu")
	public Mono<Void> saveMenuRoles(@RequestBody Map<String, Map<String, Boolean>> data)
	{
		return roleService.saveMenuRoles(data);
	}

	@PostMapping("/component")
	public Mono<Void> saveComponentRoles(@RequestBody Map<String, Map<String, Boolean>> data)
	{
		return roleService.saveComponentRoles(data);
	}


}
