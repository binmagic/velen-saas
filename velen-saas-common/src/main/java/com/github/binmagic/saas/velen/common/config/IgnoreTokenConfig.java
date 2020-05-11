package com.github.binmagic.saas.velen.common.config;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

import java.util.List;

import static com.github.binmagic.saas.velen.common.config.IgnoreTokenConfig.PREFIX;

@Configuration
@ConfigurationProperties(PREFIX)
@Data
public class IgnoreTokenConfig
{
	public static final String PREFIX = "ignore.token";

	private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
	private List<String> url = CollUtil.newArrayList(
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

	public boolean isIgnoreToken(String path)
	{
		return getUrl().stream().anyMatch((url) -> path.startsWith(url) || ANT_PATH_MATCHER.match(url, path));
	}

}
