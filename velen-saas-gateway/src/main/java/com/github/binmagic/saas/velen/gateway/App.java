package com.github.binmagic.saas.velen.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(scanBasePackages = {"com.github.binmagic.saas.velen"})
@EnableDiscoveryClient
@EnableFeignClients({"com.github.binmagic.saas"})
@Slf4j
public class App
{
	public static void main(String[] args) throws UnknownHostException
	{
		System.setProperty("nepxion.banner.shown.ansi.mode", "true");
		ConfigurableApplicationContext application = SpringApplication.run(App.class, args);
		Environment env = application.getEnvironment();
		log.info("\n----------------------------------------------------------\n\t" +
						"应用 '{}' 运行成功! 访问连接:\n\t" +
						"Swagger文档: \t\thttp://{}:{}{}{}/doc.html\n\t" +
						"----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
				env.getProperty("server.servlet.context-path", ""),
				env.getProperty("spring.mvc.servlet.path", ""));
	}
}
