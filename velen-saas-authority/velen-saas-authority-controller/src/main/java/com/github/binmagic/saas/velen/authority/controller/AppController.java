package com.github.binmagic.saas.velen.authority.controller;

import com.github.binmagic.saas.velen.authority.dto.*;
import com.github.binmagic.saas.velen.authority.entity.App;
import com.github.binmagic.saas.velen.authority.service.AppService;
import com.github.binmagic.saas.velen.common.component.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/accounts/app")
public class AppController extends BaseController
{
	@Autowired
	AppService appService;

	@GetMapping("/{appId}")
	public Mono<AppInfoDTO> getAppInfo(@PathVariable String appId)
	{
		return appService.getApp(appId)
				.flatMap(app -> {
					AppInfoDTO appInfoDTO = new AppInfoDTO();
					BeanUtils.copyProperties(app, appInfoDTO);
					return Mono.just(appInfoDTO);
				});
	}

	@GetMapping("/{appId}/member")
	public Flux<AppMemberInfoDTO> getAppMemberInfo(@PathVariable String appId)
	{
		return appService.getAppMember(appId);
	}

	@GetMapping
	public Flux<App> getApp()
	{
		return getCurrentUserId()
				.flatMapMany(userId -> appService.findApp(userId));
	}

	@PutMapping
	public Mono<App> updateAppInfo(@Validated @RequestBody AppUpdateDTO appUpdateDTO)
	{
		return appService.getApp(appUpdateDTO.getId())
				.map(app -> {
					BeanUtils.copyProperties(appUpdateDTO, app);
					return app;
				}).doOnNext(app -> {
					appService.updateApp(app);
				});
	}

	@PostMapping
	public Mono<App> createApp(@Validated @RequestBody AppSaveDTO appSaveDTO)
	{
		return Mono.zip(getCurrentUserId(), getCurrentUserName(), getCurrentUserAccount())
				.flatMap(tuple -> {
					App app = new App();
					app.setOwnerId(tuple.getT1());
					app.setOwnerName(tuple.getT2());
					app.setOwnerAccount(tuple.getT3());
					BeanUtils.copyProperties(appSaveDTO, app);
					return appService.createApp(app);
				});
	}

	@PostMapping("/{appId}/member")
	public Mono<Void> addMember(@PathVariable String appId
			, @Validated @RequestBody List<AppAddMemberDTO> appAddMemberDTO)
	{
		return getCurrentUserId().flatMap(userId -> appService.addMember(userId, appId, appAddMemberDTO));
	}

	@DeleteMapping("/{appId}/member/{id}")
	public Mono<Void> deleteMember(@PathVariable String appId, @PathVariable String id)
	{
		return appService.deleteMember(appId, id);
	}

}
