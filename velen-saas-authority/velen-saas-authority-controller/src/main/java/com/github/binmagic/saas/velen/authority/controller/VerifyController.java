package com.github.binmagic.saas.velen.authority.controller;

import com.github.binmagic.saas.velen.authority.service.VerifyService;
import com.github.binmagic.saas.velen.common.component.controller.BaseController;
import com.github.binmagic.saas.velen.common.config.SecretConfig;
import com.github.binmagic.saas.velen.common.entity.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/verify")
public class VerifyController extends BaseController
{

	@Autowired
	SecretConfig secretConfig;

	@Autowired
	VerifyService verifyService;

	@PostMapping
	public Mono<ResponseEntity> writeVerify(String secretKey, String userId)
	{

		if(StringUtils.isEmpty(userId))
		{
			return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(Error.builder().errorType(Error.ERROR_TYPE_FORBID).error("auth failed")));
		}

		if(secretKey == null || !secretKey.equals(secretConfig.getSecretKey()))
		{
			return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(Error.builder().errorType(Error.ERROR_TYPE_FORBID).error("secret key error").build()));
		}

		return verifyService.writeVerify(userId).then(Mono.just(ResponseEntity.ok().build()));
	}

}
