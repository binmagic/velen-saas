package com.github.binmagic.saas.velen.authority.service;

import com.github.binmagic.saas.velen.authority.dto.AppMemberInfoDTO;
import com.github.binmagic.saas.velen.authority.entity.App;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AppService
{
	Flux<App> findApp(String userId);

	Mono<App> createApp(App app);

	Mono<Void> submitToAuditor(String userId, String appId);

	Mono<App> updateApp(App app);

	Mono<App> getApp(String appId);

	Flux<AppMemberInfoDTO> getAppMember(String appId);
}
