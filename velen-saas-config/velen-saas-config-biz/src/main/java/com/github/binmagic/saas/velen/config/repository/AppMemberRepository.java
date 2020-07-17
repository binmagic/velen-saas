package com.github.binmagic.saas.velen.config.repository;

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository;
import com.github.binmagic.saas.velen.config.entity.AppMember;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AppMemberRepository extends BaseRepository<AppMember>
{
	Flux<AppMember> findByAppId(String appId);

	Flux<AppMember> findByAccount(String account);

	Mono<AppMember> findFirstByAccountAndAppId(String account, String appId);
}
