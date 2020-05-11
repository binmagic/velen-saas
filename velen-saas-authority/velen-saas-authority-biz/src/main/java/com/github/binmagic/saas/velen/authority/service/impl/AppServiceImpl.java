package com.github.binmagic.saas.velen.authority.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.binmagic.saas.velen.authority.dto.AppMemberInfoDTO;
import com.github.binmagic.saas.velen.authority.entity.App;
import com.github.binmagic.saas.velen.authority.entity.UserApp;
import com.github.binmagic.saas.velen.authority.repository.AppMemberRepository;
import com.github.binmagic.saas.velen.authority.repository.AppRepository;
import com.github.binmagic.saas.velen.authority.repository.UserAppRepository;
import com.github.binmagic.saas.velen.authority.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppServiceImpl implements AppService
{

	@Autowired
	UserAppRepository userAppRepository;

	@Autowired
	AppRepository appRepository;

	@Autowired
	AppMemberRepository appMemberRepository;

	public Flux<App> findApp(String userId)
	{
		return userAppRepository.findByUserId(userId)
				.collectList().flatMapMany(apps -> {
					List<String> appIds = new ArrayList<>();
					apps.forEach(app -> appIds.add(app.getAppId()));
					return appRepository.findByIdIn(appIds);
				});
	}

	@Override
	public Mono<App> createApp(App app)
	{

		LocalDateTime now = LocalDateTime.now();
		app.setAppKey(IdUtil.fastSimpleUUID())
				.setState(App.DRAFT)
				.setUpdateTime(now)
				.setCreateTime(now);

		return appRepository.insert(app).flatMap(_app -> userAppRepository
				.insert(new UserApp(_app.getId(), app.getOwnerId()))
				.thenReturn(_app));
	}

	@Override
	public Mono<Void> submitToAuditor(String userId, String appId)
	{
		return appRepository.findFirstByIdAndOwnerId(appId, userId)
				.flatMap(app -> {
					app.setState(App.WAIT_CHECK);
					return appRepository.save(app).then();
				});
	}

	@Override
	public Mono<App> updateApp(App app)
	{
		return getApp(app.getId())
				.flatMap(_app -> {
					_app.setName(app.getName())
							.setDesc(app.getDesc());
					return appRepository.save(_app);
				});
	}

	@Override
	public Mono<App> getApp(String appId)
	{
		return appRepository.findById(appId);
	}

	@Override
	public Flux<AppMemberInfoDTO> getAppMember(String appId)
	{
		return Flux.empty();
//		return appMemberRepository.findByAppId(appId)
//				.flatMap();
	}

}
