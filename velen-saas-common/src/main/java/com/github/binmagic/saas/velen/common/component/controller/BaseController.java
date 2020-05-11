package com.github.binmagic.saas.velen.common.component.controller;

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
				.map(r -> r.getHeaders().getFirst(Constant.JWT_KEY_NAME));
	}


	protected Mono<String> getCurrentUserAccount()
	{
		return ReactiveRequestContextHolder.getRequest()
				.map(r -> r.getHeaders().getFirst(Constant.JWT_KEY_ACCOUNT));
	}


}
