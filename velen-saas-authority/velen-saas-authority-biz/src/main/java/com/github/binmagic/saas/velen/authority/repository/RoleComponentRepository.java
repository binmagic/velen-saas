package com.github.binmagic.saas.velen.authority.repository;

import com.github.binmagic.saas.velen.authority.entity.RoleComponent;
import com.github.binmagic.saas.velen.common.component.dao.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface RoleComponentRepository extends BaseRepository<RoleComponent>
{
	Flux<RoleComponent> findAllByComponentIdIn(List<String> componentIds);

	Mono<Void> deleteByRoleIdAndComponentId(String roleId, String componentId);
}
