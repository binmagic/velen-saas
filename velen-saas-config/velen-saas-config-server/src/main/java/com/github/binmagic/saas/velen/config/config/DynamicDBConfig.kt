package com.github.binmagic.saas.velen.config.config

import com.github.binmagic.saas.velen.common.constant.Constant
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.util.context.Context

@Configuration
open class DynamicDBConfig {

    @Bean
    open fun config(): WebFilter {

        return WebFilter { exchange: ServerWebExchange, chain: WebFilterChain ->

            val appId: String? = exchange.request.headers.getFirst(Constant.APP_ID)

            if (appId == null) {
                chain.filter(exchange);
            } else {
                chain.filter(exchange)
                        .subscriberContext { ctx: Context -> ctx.put(Constant.APP_ID, appId) }
            }
        }
    }


}