package com.github.binmagic.saas.velen.authority.controller;

import com.github.binmagic.saas.velen.authority.dto.RegisterParamDTO;
import com.github.binmagic.saas.velen.authority.entity.User;
import com.github.binmagic.saas.velen.authority.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController
{

	@Autowired
	UserService userService;


	@PostMapping("register")
	public Mono<Void> register(@Validated @RequestBody RegisterParamDTO registerParamDTO)
	{
		User user = new User();

		BeanUtils.copyProperties(registerParamDTO, user);

		return userService.saveUser(user).then();
	}

}
