package com.github.binmagic.saas.velen.authority.service.impl;


import cn.hutool.core.util.IdUtil;
import com.github.binmagic.saas.velen.authority.entity.*;
import com.github.binmagic.saas.velen.authority.repository.*;
import com.github.binmagic.saas.velen.common.component.secret.PasswordEncryptComponent;
import com.github.binmagic.saas.velen.authority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService
{

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleUserRepository roleUserRepository;

	@Override
	public Mono<User> getUserByAccount(String account)
	{
		return userRepository.findFirstByAccount(account);
	}

	@Override
	public Mono<User> getUserById(String id)
	{
		return userRepository.findById(id);
	}

	@Override
	public Mono<User> saveUser(User user)
	{
		String salt = IdUtil.fastSimpleUUID();
		PasswordEncryptComponent passwordEncryptComponent
				= new PasswordEncryptComponent(salt
				, PasswordEncryptComponent.MD5);
		user.setSalt(salt);
		user.setPassword(passwordEncryptComponent.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Flux<RoleUser> getUserRole(String userId)
	{
		return roleUserRepository.findByUserId(userId);
	}
}
