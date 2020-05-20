package com.github.binmagic.saas.velen.authority.service;

import com.github.binmagic.saas.velen.authority.dto.AppAddMemberDTO;
import com.github.binmagic.saas.velen.authority.dto.AppMemberInfoDTO;
import com.github.binmagic.saas.velen.authority.entity.App;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AppService
{
	Flux<App> findApp(String account);

	Mono<App> createApp(App app);

	Mono<Void> submitToAuditor(String appId);

	Mono<App> updateApp(App app);

	Mono<App> getApp(String appId);

	Flux<AppMemberInfoDTO> getAppMember(String appId);

	Mono<String> getAppMemberRole(String appId, String account);

	Mono<Void> addMember(String appId, List<AppAddMemberDTO> appAddMemberDTO);

	Mono<Void> deleteMember(String appId, String id);
}
