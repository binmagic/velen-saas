package com.github.binmagic.saas.velen.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "secret")
@Data
public class SecretConfig
{
	private String secretKey;
}
