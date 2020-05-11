package com.github.binmagic.saas.velen.authority.service.impl;

import com.github.binmagic.saas.velen.authority.entity.Role;
import com.github.binmagic.saas.velen.authority.entity.RoleComponent;
import com.github.binmagic.saas.velen.authority.entity.RoleFunction;
import com.github.binmagic.saas.velen.authority.entity.RoleMenu;
import com.github.binmagic.saas.velen.authority.repository.RoleComponentRepository;
import com.github.binmagic.saas.velen.authority.repository.RoleFunctionRepository;
import com.github.binmagic.saas.velen.authority.repository.RoleMenuRepository;
import com.github.binmagic.saas.velen.authority.repository.RoleRepository;
import com.github.binmagic.saas.velen.authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public Flux<RoleMenu> getOwnerMenuRoles(List<String> menuIds)
	{
		return roleMenuRepository.findAllByMenuIdIn(menuIds);
	}

	@Override
	public Flux<RoleComponent> getOwnerComponentRoles(List<String> componentIds)
	{
		return roleComponentRepository.findAllByComponentIdIn(componentIds);
	}

	@Override
	public Mono<Void> saveFunctionRoles(Map<String, Map<String, Boolean>> data)
	{
		List<Mono<Void>> monoList = new ArrayList<>();
		for(Map.Entry<String, Map<String, Boolean>> entry : data.entrySet())
		{
			for(Map.Entry<String, Boolean> _entry : entry.getValue().entrySet())
			{
				if(_entry.getValue())
				{
					monoList.add(roleFunctionRepository.save(new RoleFunction(_entry.getKey(), entry.getKey())).then());
				}
				else
				{
					monoList.add(roleFunctionRepository.deleteByRoleIdAndAndFunctionId(_entry.getKey(), entry.getKey()));
				}
			}
		}
		return Mono.when(monoList);
	}

	@Override
	public Mono<Void> saveMenuRoles(Map<String, Map<String, Boolean>> data)
	{
		List<Mono<Void>> monoList = new ArrayList<>();
		for(Map.Entry<String, Map<String, Boolean>> entry : data.entrySet())
		{
			for(Map.Entry<String, Boolean> _entry : entry.getValue().entrySet())
			{
				if(_entry.getValue())
				{
					monoList.add(roleMenuRepository.save(new RoleMenu(_entry.getKey(), entry.getKey())).then());
				}
				else
				{
					monoList.add(roleMenuRepository.deleteByRoleIdAndMenuId(_entry.getKey(), entry.getKey()));
				}
			}
		}
		return Mono.when(monoList);
	}

	@Override
	public Mono<Void> saveComponentRoles(Map<String, Map<String, Boolean>> data)
	{
		List<Mono<Void>> monoList = new ArrayList<>();
		for(Map.Entry<String, Map<String, Boolean>> entry : data.entrySet())
		{
			for(Map.Entry<String, Boolean> _entry : entry.getValue().entrySet())
			{
				if(_entry.getValue())
				{
					monoList.add(roleComponentRepository.save(new RoleComponent(_entry.getKey(), entry.getKey())).then());
				}
				else
				{
					monoList.add(roleComponentRepository.deleteByRoleIdAndComponentId(_entry.getKey(), entry.getKey()));
				}
			}
		}
		return Mono.when(monoList);
	}

}
