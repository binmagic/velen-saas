package com.github.binmagic.saas.velen.authority.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.binmagic.saas.velen.authority.dto.AppAddMemberDTO;
import com.github.binmagic.saas.velen.authority.dto.AppMemberInfoDTO;
import com.github.binmagic.saas.velen.authority.entity.App;
import com.github.binmagic.saas.velen.authority.entity.AppMember;
import com.github.binmagic.saas.velen.authority.repository.AppMemberRepository;
import com.github.binmagic.saas.velen.authority.repository.AppRepository;
import com.github.binmagic.saas.velen.authority.service.AppService;
import com.github.binmagic.saas.velen.common.constant.Constant;
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
	AppRepository appRepository;

	@Autowired
	AppMemberRepository appMemberRepository;

	public Flux<App> findApp(String userId)
	{
		return appMemberRepository.findByUserId(userId)
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

		return appRepository.insert(app).flatMap(_app -> appMemberRepository
				.insert(new AppMember(_app.getId(), app.getOwnerId(), Constant.ROLE_MANAGER))
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
		return appMemberRepository.findByAppId(appId)
				.flatMap(appMember -> Mono.just(new AppMemberInfoDTO().setId(appMember.getId())
						.setMemberId(appMember.getUserId()).setMemberName("").setMemberRoleId(appMember.getRoleId())));
	}

	@Override
	public Mono<Void> addMember(String userId, String appId, List<AppAddMemberDTO> appAddMemberDTO)
	{
		return appRepository.findFirstByIdAndOwnerId(appId, userId)
				.flatMap(app -> {
					List<Mono> monoList = new ArrayList<>(appAddMemberDTO.size());
					appAddMemberDTO.stream().forEach(dto -> {
						AppMember appMember = new AppMember().setAppId(app.getId())
								.setRoleId(dto.getRole()).setUserId(dto.getAccount());
						monoList.add(appMemberRepository.insert(appMember));
					});
					return Flux.concat(monoList.toArray(new Mono[0])).then();
				});
	}

	@Override
	public Mono<Void> deleteMember(String appId, String id)
	{
		return appMemberRepository.deleteById(id);
	}

}
