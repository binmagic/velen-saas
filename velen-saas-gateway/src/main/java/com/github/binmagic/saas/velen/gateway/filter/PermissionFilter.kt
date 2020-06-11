package com.github.binmagic.saas.velen.gateway.filter

import cn.hutool.core.util.ObjectUtil
import cn.hutool.core.util.URLUtil
import com.github.binmagic.saas.velen.common.config.JWTConfig
import com.github.binmagic.saas.velen.common.constant.Constant
import com.github.binmagic.saas.velen.gateway.config.AuthConfig
import com.github.binmagic.saas.velen.gateway.provider.AccessProvider
import lombok.extern.slf4j.Slf4j
import org.apache.commons.lang3.StringUtils
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
@Slf4j
class PermissionFilter : SuspendFilter() {

    @Autowired
    lateinit var jwtConfig: JWTConfig

    @Autowired
    lateinit var authConfig: AuthConfig

    @Autowired
    lateinit var accessProvider: AccessProvider

    private fun addHeader(mutate: ServerHttpRequest.Builder, name: String, value: Any?) {
        if (ObjectUtil.isEmpty(value)) {
            return
        }
        val valueStr = value.toString()
        val valueEncode = URLUtil.encode(valueStr)
        mutate.header(name, valueEncode)
    }

    private fun getHeader(headerName: String, request: ServerHttpRequest): String? {
        val headers = request.headers
        var token: String? = StringUtils.EMPTY
        if (headers == null || headers.isEmpty()) {
            return token
        }
        token = headers.getFirst(headerName)
        if (StringUtils.isNotBlank(token)) {
            return token
        }
        val httpCookie = request.cookies.getFirst(headerName)
        return if (httpCookie != null) {
            httpCookie.value
        } else request.queryParams.getFirst(headerName)
    }

    override suspend fun strainer(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val mutate = request.mutate()

        val path = request.uri.path

        if (authConfig.isSkipAuth(path)) {
            return chain.filter(exchange)
        }

        val token: String = getHeader(Constant.BEARER_HEADER_KEY, request)
                ?: return Mono.error(IllegalStateException("un auth"))

        val authInfo = jwtConfig.getAuthInfo(token)

        addHeader(mutate, Constant.JWT_KEY_USER_ID, authInfo.userId)
        addHeader(mutate, Constant.JWT_KEY_NAME, authInfo.name)
        addHeader(mutate, Constant.JWT_KEY_ACCOUNT, authInfo.account)
        addHeader(mutate, Constant.JWT_KEY_SUPERUSER, authInfo.isSuperuser)
        MDC.put(Constant.JWT_KEY_ACCOUNT, authInfo.account.toString())

        if(authConfig.isSkipAccess(path))
        {
            val build = mutate.build()
            return chain.filter(exchange.mutate().request(build).build())
        }

        val appId: String? = getHeader(Constant.APP_ID, request)

        if(!authInfo.isSuperuser)
        {
            var flag = accessProvider.getVerify(appId!!, authInfo.account, path, request.method!!.name)

            if (!flag)
            {
                return Mono.error(IllegalAccessException("illegal access"))
            }

        }

        addHeader(mutate, Constant.APP_ID, appId)

        val build = mutate.build()
        return chain.filter(exchange.mutate().request(build).build())
    }

    override fun getOrder(): Int {
        return -1000
    }
}
