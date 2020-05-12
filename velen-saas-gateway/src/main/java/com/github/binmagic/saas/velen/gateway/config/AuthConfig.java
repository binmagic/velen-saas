package com.github.binmagic.saas.velen.gateway.config;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;


@Configuration
@RefreshScope
@ConfigurationProperties(AuthConfig.PREFIX)
@Data
public class AuthConfig
{
	public static final String PREFIX = "auth";

	private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

	/**
	 * 自定义API集合
	 */
	private final List<String> skipUrl = new ArrayList<>();

	private static List<String> defaultSkipUrl = CollUtil.newArrayList(
			"/error",
			"/actuator/**",
			"/**/login",
			"/**/register",
			"/gate/**",
			"/static/**",
			"/anno/**",
			"/**/anno/**",
			"/**/swagger-ui.html",
			"/**/doc.html",
			"/**/webjars/**",
			"/**/v2/api-docs/**",
			"/**/v2/api-docs-ext/**",
			"/**/swagger-resources/**"
	);

	public boolean isSkip(String path)
	{
		return defaultSkipUrl.stream().anyMatch((url) -> path.startsWith(url) || ANT_PATH_MATCHER.match(url, path))
				|| skipUrl.stream().anyMatch((url) -> path.startsWith(url) || ANT_PATH_MATCHER.match(url, path));
	}

}
