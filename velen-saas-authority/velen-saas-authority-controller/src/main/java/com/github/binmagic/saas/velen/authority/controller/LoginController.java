package com.github.binmagic.saas.velen.authority.controller;


import com.github.binmagic.saas.velen.authority.dto.LoginParamDTO;
import com.github.binmagic.saas.velen.authority.granter.TokenGranter;
import com.github.binmagic.saas.velen.authority.granter.TokenGranterBuilder;
import com.github.binmagic.saas.velen.common.component.controller.BaseController;
import com.github.binmagic.saas.velen.common.config.JWTConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("login")
public class LoginController extends BaseController
{

	@Autowired
	TokenGranterBuilder tokenGranterBuilder;

	@Autowired
	JWTConfig jwtConfig;

	@PostMapping
	private Mono<ResponseEntity<JWTConfig.AuthInfo>> auth(@Validated @RequestBody LoginParamDTO loginParamDTO)
	{
		TokenGranter granter = tokenGranterBuilder.getGranter(loginParamDTO.getGrantType());

		return granter.grant(loginParamDTO).map(u -> {
			JWTConfig.JWTAuthInfo jwtAuthInfo = new JWTConfig.JWTAuthInfo(u.getAccount(), u.getId(), u.getName());
			JWTConfig.AuthInfo authInfo = jwtConfig.createAutoInfo(jwtAuthInfo
					, jwtConfig.getTokenExpireTime());
			return ResponseEntity.ok(authInfo);
		});
	}
}
