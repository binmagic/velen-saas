package com.github.binmagic.saas.velen.authority.service;


import com.github.binmagic.saas.velen.authority.entity.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


public interface RoleService
{
	Flux<Role> getRoleList();

	Mono<Role> addRole(Role role);

	Mono<Void> deleteRole(String id);

	Mono<Role> updateRole(Role role);

	Flux<RoleFunction> getOwnerFunctionRoles(List<String> functionIds);

	Flux<RoleMenu> getOwnerMenuRoles(List<String> menuIds);

	Flux<RoleComponent> getOwnerComponentRoles(List<String> componentIds);

	Mono<Void> saveFunctionRoles(Map<String, Map<String, Boolean>> data);

	Mono<Void> saveMenuRoles(Map<String, Map<String, Boolean>> data);

	Mono<Void> saveComponentRoles(Map<String, Map<String, Boolean>> data);

	Flux<RoleFunction> getRoleFunctions(List<String> ids);

	Flux<RoleResource> getRoleResources(List<String> ids);
}
