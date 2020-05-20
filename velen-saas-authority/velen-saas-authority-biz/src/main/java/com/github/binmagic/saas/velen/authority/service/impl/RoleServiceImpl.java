package com.github.binmagic.saas.velen.authority.service.impl;

import com.github.binmagic.saas.velen.authority.entity.Role;
import com.github.binmagic.saas.velen.authority.entity.RoleFunction;
import com.github.binmagic.saas.velen.authority.entity.RoleResource;
import com.github.binmagic.saas.velen.authority.repository.*;
import com.github.binmagic.saas.velen.authority.service.RoleService;
import com.github.binmagic.saas.velen.authority.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class RoleServiceImpl implements RoleService
{

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RoleFunctionRepository roleFunctionRepository;

	@Autowired
	RoleMenuRepository roleMenuRepository;

	@Autowired
	RoleComponentRepository roleComponentRepository;

	@Autowired
	RoleResourceRepository roleResourceRepository;

	@Autowired
	VerifyService verifyService;

	@Override
	public Flux<Role> getRoleList()
	{
		return roleRepository.findAll();
	}

	@Override
	public Mono<Role> addRole(Role role)
	{
		return roleRepository.insert(role);
	}

	@Override
	public Mono<Void> deleteRole(String id)
	{
		return roleRepository.deleteById(id);
	}

	@Override
	public Mono<Role> updateRole(Role role)
	{
		return roleRepository.save(role);
	}

	@Override
	public Flux<RoleFunction> getOwnerFunctionRoles(List<String> functionIds)
	{
		return roleFunctionRepository.findAllByFunctionIdIn(functionIds);
	}

	@Override
	public Flux<RoleResource> getOwnerResourceRoles(List<String> resourceIds)
	{
		return roleResourceRepository.findAllByResourceIdIn(resourceIds);
	}

	@Override
	public Mono<Void> saveFunctionRoles(Map<String, Map<String, Boolean>> data)
	{
		List<Mono<Void>> monoList = new ArrayList<>();
		Map<String, List<String>> functionIds = new HashMap<>();
		for(Map.Entry<String, Map<String, Boolean>> entry : data.entrySet())
		{
			if(!functionIds.containsKey(entry.getKey()))
			{
				functionIds.put(entry.getKey(), new ArrayList<>());
			}
			for(Map.Entry<String, Boolean> _entry : entry.getValue().entrySet())
			{
				if(_entry.getValue())
				{
					functionIds.get(entry.getKey()).add(_entry.getKey());
					monoList.add(roleFunctionRepository.save(new RoleFunction(_entry.getKey(), entry.getKey())).then());
				}
				else
				{
					monoList.add(roleFunctionRepository.deleteByRoleIdAndAndFunctionId(_entry.getKey(), entry.getKey()));
				}
			}
		}
		return Mono.when(monoList).doOnNext(aVoid -> {
			for(Map.Entry<String, List<String>> entry : functionIds.entrySet())
			{
				verifyService.writeRoleVerify(entry.getKey(), entry.getValue());
			}
		});
	}

	@Override
	public Mono<Void> saveResourceRoles(Map<String, Map<String, Boolean>> data)
	{
		List<Mono<Void>> monoList = new ArrayList<>();
		for(Map.Entry<String, Map<String, Boolean>> entry : data.entrySet())
		{
			for(Map.Entry<String, Boolean> _entry : entry.getValue().entrySet())
			{
				if(_entry.getValue())
				{
					monoList.add(roleResourceRepository.save(new RoleResource(_entry.getKey(), entry.getKey())).then());
				}
				else
				{
					monoList.add(roleResourceRepository.deleteByRoleIdAndResourceId(_entry.getKey(), entry.getKey()));
				}
			}
		}
		return Mono.when(monoList);
	}

	@Override
	public Flux<RoleFunction> getRoleFunctions(List<String> ids)
	{
		return null;
	}

	@Override
	public Flux<RoleResource> getRoleResources(List<String> ids)
	{
		return roleResourceRepository.findByRoleIdIn(ids);
	}

}
