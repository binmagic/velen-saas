package com.github.binmagic.saas.velen.gateway.filter

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

abstract class SuspendFilter : GlobalFilter, Ordered {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        return Mono.create { sink ->
            run {
                GlobalScope.launch {
                    strainer(exchange, chain).awaitFirstOrNull()
                    sink.success()
                }
            }
        }
    }

    abstract suspend fun strainer(exchange: ServerWebExchange, chain: GatewayFilterChain) : Mono<Void>
}