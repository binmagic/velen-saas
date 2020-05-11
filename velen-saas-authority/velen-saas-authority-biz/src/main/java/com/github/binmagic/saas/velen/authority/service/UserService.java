package com.github.binmagic.saas.velen.authority.service;

import com.github.binmagic.saas.velen.authority.entity.User;
import reactor.core.publisher.Mono;

public interface UserService
{
	Mono<User> getUserByAccount(String account);

	Mono<User> getUserById(String id);

	Mono<User> saveUser(User user);
}
