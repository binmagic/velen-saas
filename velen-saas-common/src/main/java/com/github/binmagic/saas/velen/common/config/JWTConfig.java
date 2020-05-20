package com.github.binmagic.saas.velen.common.config;

import cn.hutool.core.convert.Convert;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.binmagic.saas.velen.common.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JWTConfig
{
	private String tokenSecret; //秘钥
	private long tokenExpireTime = 5 * 60 * 1000; //token过期时间
	private long tokenRefreshExpireTime = tokenExpireTime * 10; //refreshToken过期时间
	private String issuer = "issuer"; //签发人

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class JWTAuthInfo
	{
		private String account;
		private String userId;
		private String name;
		private boolean superuser;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Accessors(chain = true)
	public static class AuthInfo
	{
		private String token;
		private String tokenType;
		private String refreshToken;
		private String name;
		private String account;
		private String userId;
		private long expire;
		private Date expiration;
		private boolean superuser;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Token
	{
		private String token;
		private Long expire;
		private Date expiration;
	}


	public AuthInfo getAuthInfo(String token)
	{
		DecodedJWT decodedJWT = JWT.decode(token);
		String tokenType = decodedJWT.getClaim(Constant.JWT_KEY_TOKEN_TYPE).asString();
		String userId = decodedJWT.getClaim(Constant.JWT_KEY_USER_ID).asString();
		String account = decodedJWT.getClaim(Constant.JWT_KEY_ACCOUNT).asString();
		String name = decodedJWT.getClaim(Constant.JWT_KEY_NAME).asString();
		Boolean superuser = Boolean.parseBoolean(decodedJWT.getClaim(Constant.JWT_KEY_SUPERUSER).asString());
		Date expiration = decodedJWT.getExpiresAt();
		return (new AuthInfo()).setToken(token)
				.setSuperuser(superuser)
				.setExpire(expiration != null ? expiration.getTime() : 0L)
				.setTokenType(tokenType).setUserId(userId).setAccount(account).setName(name);
	}

	public AuthInfo createAutoInfo(JWTAuthInfo userInfo, Long expireMillis)
	{
		if(expireMillis == null || expireMillis <= 0L)
		{
			expireMillis = tokenExpireTime;
		}

		Map<String, String> param = new HashMap(16);
		param.put(Constant.JWT_KEY_TOKEN_TYPE, Constant.BEARER_HEADER_KEY);
		param.put(Constant.JWT_KEY_USER_ID, Convert.toStr(userInfo.getUserId(), "0"));
		param.put(Constant.JWT_KEY_ACCOUNT, userInfo.getAccount());
		param.put(Constant.JWT_KEY_NAME, userInfo.getName());
		param.put(Constant.JWT_KEY_SUPERUSER, Convert.toStr(userInfo.isSuperuser()));
		Token token = generate(param, expireMillis);
		AuthInfo authInfo = new AuthInfo();
		authInfo.setSuperuser(userInfo.isSuperuser());
		authInfo.setAccount(userInfo.getAccount());
		authInfo.setName(userInfo.getName());
		authInfo.setUserId(userInfo.getUserId());
		authInfo.setTokenType(Constant.BEARER_HEADER_KEY);
		authInfo.setToken(token.getToken());
		authInfo.setExpire(token.getExpire());
		authInfo.setExpiration(token.getExpiration());
		authInfo.setRefreshToken(this.createRefreshToken(userInfo).getToken());
		return authInfo;
	}

	private Token createRefreshToken(JWTAuthInfo userInfo)
	{
		Map<String, String> param = new HashMap<>(16);
		param.put(Constant.JWT_KEY_TOKEN_TYPE, Constant.REFRESH_TOKEN_KEY);
		param.put(Constant.JWT_KEY_USER_ID, Convert.toStr(userInfo.getUserId(), "0"));
		return generate(param, getTokenRefreshExpireTime());
	}

	public Token generate(Map<String, String> map)
	{
		return generate(map, tokenExpireTime);
	}

	/**
	 * 生成签名
	 */
	public Token generate(Map<String, String> map, long tokenExpireTime)
	{
		Date now = new Date();
		Algorithm algorithm = Algorithm.HMAC256(tokenSecret); //算法

		Date expiresAt = new Date(now.getTime() + tokenExpireTime);

		JWTCreator.Builder builder = JWT.create()
				.withIssuer(issuer) //签发人
				.withIssuedAt(now) //签发时间
				.withExpiresAt(expiresAt) //过期时间
				;
		for(Map.Entry<String, String> entry : map.entrySet())
		{
			builder.withClaim(entry.getKey(), entry.getValue());
		}
		String tokenStr = builder.sign(algorithm);

		Token token = new Token();
		token.setExpiration(expiresAt);
		token.setExpire(tokenExpireTime);
		token.setToken(tokenStr);
		return token;
	}

	public boolean verify(String token)
	{
		Algorithm algorithm = Algorithm.HMAC256(tokenSecret); //算法
		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer(issuer)
				.build();
		verifier.verify(token);
		return true;
	}

	public Map<String, String> parse(String token)
	{
		Map<String, Claim> claimMap = JWT.decode(token).getClaims();

		Map<String, String> result = new HashMap<>();

		for(Map.Entry<String, Claim> entry : claimMap.entrySet())
		{
			result.put(entry.getKey(), entry.getValue().asString());
		}
		return result;
	}

	public void getClaims(String token)
	{

		JWT.decode(token);

	}

}
