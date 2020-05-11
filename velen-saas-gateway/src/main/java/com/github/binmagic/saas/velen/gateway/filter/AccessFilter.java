package com.github.binmagic.saas.velen.common.gateway.filter;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import com.github.binmagic.saas.velen.authority.api.VerifyApi;
import com.github.binmagic.saas.velen.common.config.IgnoreTokenConfig;
import com.github.binmagic.saas.velen.common.config.JWTConfig;
import com.github.binmagic.saas.velen.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AccessFilter implements GlobalFilter, Ordered
{

	@Autowired
	StringRedisTemplate template;

	@Autowired
	VerifyApi verifyApi;

	@Autowired
	JWTConfig jwtConfig;

	@Autowired
	IgnoreTokenConfig ignoreTokenConfig;


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
	{
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		ServerHttpRequest.Builder mutate = request.mutate();

		if(ignoreTokenConfig.isIgnoreToken(request.getPath().toString()))
		{
			log.debug("access filter not execute");
			return chain.filter(exchange);
		}

		JWTConfig.AuthInfo authInfo;
		try
		{
			String token = getHeader(Constant.BEARER_HEADER_KEY, request);

			authInfo = jwtConfig.getAuthInfo(token);
		}
		catch(Exception e)
		{
			return Mono.error(e);
		}

		if(authInfo != null)
		{
			addHeader(mutate, Constant.JWT_KEY_USER_ID, authInfo.getUserId());
			addHeader(mutate, Constant.JWT_KEY_NAME, authInfo.getName());
			addHeader(mutate, Constant.JWT_KEY_ACCOUNT, authInfo.getAccount());

			MDC.put(Constant.JWT_KEY_USER_ID, String.valueOf(authInfo.getUserId()));
		}

		ServerHttpRequest build = mutate.build();
		return chain.filter(exchange.mutate().request(build).build());
	}

	private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value)
	{
		if(ObjectUtil.isEmpty(value))
		{
			return;
		}
		String valueStr = value.toString();
		String valueEncode = URLUtil.encode(valueStr);
		mutate.header(name, valueEncode);
	}

	protected String getHeader(String headerName, ServerHttpRequest request)
	{
		HttpHeaders headers = request.getHeaders();

		String token = StringUtils.EMPTY;

		if(headers == null || headers.isEmpty())
		{
			return token;
		}

		token = headers.getFirst(headerName);

		if(StringUtils.isNotBlank(token))
		{
			return token;
		}

		HttpCookie httpCookie = request.getCookies().getFirst(headerName);

		if(httpCookie != null)
		{
			return httpCookie.getValue();
		}

		return request.getQueryParams().getFirst(headerName);
	}

	@Override
	public int getOrder()
	{
		return -1000;
	}
}
