package com.github.binmagic.saas.velen.authority.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@FeignClient(name= "VerifyApi", path = "/")
public interface VerifyApi
{
	@PostMapping("/verify")
	Mono<ResponseEntity> writeVerify(@RequestParam("userId") String userId);
}
