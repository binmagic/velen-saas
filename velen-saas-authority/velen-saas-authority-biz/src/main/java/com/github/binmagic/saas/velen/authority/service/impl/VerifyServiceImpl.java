package com.github.binmagic.saas.velen.authority.service.impl;

import com.github.binmagic.saas.velen.authority.service.VerifyService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VerifyServiceImpl implements VerifyService
{
	@Override
	public Mono<Void> writeVerify(String userId)
	{
		return null;
	}

	@Override
	public Mono<Boolean> getVerify(String userId, String method, String uri)
	{
		return null;
	}
}
