package com.github.binmagic.saas.velen.config

import com.github.binmagic.saas.velen.common.component.dao.ExpandRepositoryImpl
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication(scanBasePackages = ["com.github.binmagic.saas.velen"])
@EnableDiscoveryClient
@Slf4j
@EnableReactiveMongoRepositories(repositoryBaseClass = ExpandRepositoryImpl::class, basePackages = ["com.github.binmagic.saas"])
open class App

fun main(args: Array<String>){
    SpringApplication.run(App::class.java, *args)
}
