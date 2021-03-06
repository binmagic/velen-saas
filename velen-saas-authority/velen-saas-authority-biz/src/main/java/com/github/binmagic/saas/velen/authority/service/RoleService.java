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

	Flux<RoleResource> getOwnerResourceRoles(List<String> resourceIds);

	Mono<Void> saveFunctionRoles(Map<String, Map<String, Boolean>> data);

	Mono<Void> saveResourceRoles(Map<String, Map<String, Boolean>> data);

	Flux<RoleFunction> getRoleFunctions(List<String> ids);

	Flux<RoleResource> getRoleResources(List<String> ids);
}
