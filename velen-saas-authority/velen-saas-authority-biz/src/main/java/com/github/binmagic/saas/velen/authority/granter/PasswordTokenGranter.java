package com.github.binmagic.saas.velen.authority.granter;

import com.github.binmagic.saas.velen.authority.dto.LoginParamDTO;
import com.github.binmagic.saas.velen.authority.entity.User;
import com.github.binmagic.saas.velen.common.component.secret.PasswordEncryptComponent;
import com.github.binmagic.saas.velen.common.exception.BizException;
import com.github.binmagic.saas.velen.common.util.StrUtil;
import com.github.binmagic.saas.velen.authority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.github.binmagic.saas.velen.authority.granter.PasswordTokenGranter.GRANT_TYPE;


@Component(GRANT_TYPE)
public class PasswordTokenGranter extends AbstractTokenGranter
{
	public static final String GRANT_TYPE = "password";

	@Autowired
	UserService userService;

	@Override
	public Mono<User> grant(LoginParamDTO loginParam)
	{
		if(StrUtil.isAnyBlank(loginParam.getAccount(), loginParam.getPassword()))
		{
			return Mono.error(BizException.of("password or account is null"));
		}

		Mono<User> userMono = userService.getUserByAccount(loginParam.getAccount());
		return userMono.flatMap(u -> {
			PasswordEncryptComponent passwordEncryptComponent = new PasswordEncryptComponent(u.getSalt(), PasswordEncryptComponent.MD5);
			if(!passwordEncryptComponent.isPasswordValid(u.getPassword(), loginParam.getPassword()))
			{
				return Mono.error(BizException.of("password is not correct"));
			}
			return Mono.just(u);
		});
	}
}
