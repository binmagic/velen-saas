package com.github.binmagic.saas.velen.common.component.datasource;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jdbc.dynamic")
@ConditionalOnProperty(value = "jdbc.dynamic.url")
@Data
public class DBConfig
{

	private String url;

	private String username;

	private String password;

}
