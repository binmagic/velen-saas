package com.github.binmagic.saas.velen.authority.granter;

import com.github.binmagic.saas.velen.authority.dto.LoginParamDTO;
import com.github.binmagic.saas.velen.authority.entity.User;
import com.github.binmagic.saas.velen.common.config.JWTConfig;
import com.github.binmagic.saas.velen.common.constant.Constant;
import com.github.binmagic.saas.velen.common.exception.BizException;
import com.github.binmagic.saas.velen.common.util.StrUtil;
import com.github.binmagic.saas.velen.authority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.github.binmagic.saas.velen.authority.granter.RefreshTokenGranter.GRANT_TYPE;


@Component(GRANT_TYPE)
public class RefreshTokenGranter extends AbstractTokenGranter
{
	public static final String GRANT_TYPE = "refresh_token";

	@Autowired
	UserService userService;

	@Autowired
	JWTConfig jwtConfig;

	@Override
	public Mono<User> grant(LoginParamDTO loginParam)
	{
		String grantType = loginParam.getGrantType();
		String refreshTokenStr = loginParam.getRefreshToken();

		if(StrUtil.isAnyBlank(grantType, refreshTokenStr))
		{
			return Mono.error(BizException.of("grantType or refreshToken is blank"));
		}

		JWTConfig.AuthInfo authInfo = jwtConfig.getAuthInfo(refreshTokenStr);

		if(Constant.REFRESH_TOKEN_KEY.equals(authInfo.getTokenType()))
		{
			return Mono.error(BizException.of("refreshToken is invalid"));
		}

		return userService.getUserById(authInfo.getUserId());
	}
}
