package com.github.binmagic.saas.velen.common.component.controller;

import cn.hutool.core.util.StrUtil;
import com.github.binmagic.saas.velen.common.constant.Constant;
import com.github.binmagic.saas.velen.common.util.ReactiveRequestContextHolder;
import reactor.core.publisher.Mono;

public abstract class BaseController
{

	protected Mono<String> getCurrentUserId()
	{
		return ReactiveRequestContextHolder.getRequest()
				.map(r -> r.getHeaders().getFirst(Constant.JWT_KEY_USER_ID));
	}

	protected Mono<String> getCurrentUserName()
	{
		return ReactiveRequestContextHolder.getRequest()
				.map(r -> {
					String name = r.getHeaders().getFirst(Constant.JWT_KEY_NAME);
					return StrUtil.isBlank(name) ? "" : name;
				});
	}

	protected Mono<String> getCurrentUserAccount()
	{
		return ReactiveRequestContextHolder.getRequest()
				.map(r -> r.getHeaders().getFirst(Constant.JWT_KEY_ACCOUNT));
	}

	protected Mono<String> getCurrentAppId()
	{
		return ReactiveRequestContextHolder.getRequest()
				.map(r -> r.getHeaders().getFirst(Constant.APP_ID));
	}


	protected Mono<Boolean> isSuperuser()
	{
		return ReactiveRequestContextHolder.getRequest()
				.map(r -> Boolean.parseBoolean(r.getHeaders().getFirst(Constant.JWT_KEY_SUPERUSER)));
	}
}
