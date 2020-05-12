package com.github.binmagic.saas.velen.authority.repository;

import com.github.binmagic.saas.velen.authority.entity.AppMember;
import com.github.binmagic.saas.velen.common.component.dao.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AppMemberRepository extends BaseRepository<AppMember>
{
	Flux<AppMember> findByAppId(String appId);

	Flux<AppMember> findByUserId(String userId);
}
