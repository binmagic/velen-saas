package com.github.binmagic.saas.velen.authority.repository;

import com.github.binmagic.saas.velen.authority.entity.RoleFunction;
import com.github.binmagic.saas.velen.common.component.dao.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface RoleFunctionRepository extends BaseRepository<RoleFunction>
{
	Flux<RoleFunction> findAllByFunctionIdIn(List<String> functionIds);

	Mono<Void> deleteByRoleIdAndAndFunctionId(String roleId, String functionId);

}
