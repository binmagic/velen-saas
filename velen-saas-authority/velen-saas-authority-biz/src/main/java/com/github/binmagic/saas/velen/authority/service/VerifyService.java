package com.github.binmagic.saas.velen.authority.service;

import reactor.core.publisher.Mono;

public interface VerifyService
{
	Mono<Void> writeVerify(String userId);

	Mono<Boolean> getVerify(String userId, String method, String uri);
}
