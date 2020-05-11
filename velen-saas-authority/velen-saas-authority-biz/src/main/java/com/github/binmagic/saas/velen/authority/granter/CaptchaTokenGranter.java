package com.github.binmagic.saas.velen.authority.granter;

import com.github.binmagic.saas.velen.authority.dto.LoginParamDTO;
import com.github.binmagic.saas.velen.authority.entity.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import static com.github.binmagic.saas.velen.authority.granter.CaptchaTokenGranter.GRANT_TYPE;


@Component(GRANT_TYPE)
public class CaptchaTokenGranter extends AbstractTokenGranter
{
	public static final String GRANT_TYPE = "captcha";

	@Override
	public Mono<User> grant(LoginParamDTO loginParam)
	{
		return Mono.empty();
	}
}
