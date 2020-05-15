package com.github.binmagic.saas.velen.authority.controller;

import com.github.binmagic.saas.velen.authority.dto.LoginParamDTO;
import com.github.binmagic.saas.velen.authority.dto.RegisterParamDTO;
import com.github.binmagic.saas.velen.authority.entity.User;
import com.github.binmagic.saas.velen.authority.granter.TokenGranter;
import com.github.binmagic.saas.velen.authority.granter.TokenGranterBuilder;
import com.github.binmagic.saas.velen.authority.service.UserService;
import com.github.binmagic.saas.velen.common.component.controller.BaseController;
import com.github.binmagic.saas.velen.common.config.JWTConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class UserController extends BaseController
{

	@Autowired
	UserService userService;


	@Autowired
	TokenGranterBuilder tokenGranterBuilder;

	@Autowired
	JWTConfig jwtConfig;

	@PostMapping("login")
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


	@PostMapping("register")
	public Mono<Void> register(@Validated @RequestBody RegisterParamDTO registerParamDTO)
	{
		User user = new User();

		BeanUtils.copyProperties(registerParamDTO, user);

		return userService.saveUser(user).then();
	}

	@GetMapping("authorization")
	public Mono<Void> authorization(){

		return getCurrentUserId().flatMap(uid -> {

			return Mono.empty();

		}).then();
	}


}
