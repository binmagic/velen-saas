package com.github.binmagic.saas.velen.authority.repository;

import com.github.binmagic.saas.velen.authority.entity.RoleMenu;
import com.github.binmagic.saas.velen.common.component.dao.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface RoleMenuRepository extends BaseRepository<RoleMenu>
{
	Flux<RoleMenu> findAllByMenuIdIn(List<String> menuIds);

	Mono<Void> deleteByRoleIdAndMenuId(String roleId, String menuId);

}
