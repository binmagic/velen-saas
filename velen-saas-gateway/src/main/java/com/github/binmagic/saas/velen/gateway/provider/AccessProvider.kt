package com.github.binmagic.saas.velen.gateway.provider

import com.github.binmagic.saas.velen.authority.api.VerifyApi
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import reactor.core.publisher.Mono

@Component
class AccessProvider {

    private val ANT_PATH_MATCHER = AntPathMatcher()

    @Autowired
    lateinit var redisTemplate: ReactiveStringRedisTemplate

    @Autowired
    lateinit var verifyApi: VerifyApi

    private fun isMatch(urls: List<String>, path: String): Boolean {

        for (url in urls) {
            if (path.startsWith(url) || ANT_PATH_MATCHER.match(url, path)) {
                return true
            }
        }
        return false
    }

    suspend fun getVerify(appId: String, account: String, path: String, method: String): Boolean {

        val role = getUserRole(account, appId) ?: return false

        var urls = redisTemplate.opsForSet().members("${role}_${method}").collectList().awaitFirst()

        if (isMatch(urls, path)) {
            return true
        }

        urls = redisTemplate.opsForSet().members("${role}_ALL").collectList().awaitFirst()

        if(isMatch(urls, path)){
            return true
        }

        return false
    }


    suspend fun getUserRole(account: String, appId: String) : String?{

        val key = "${account}_${appId}"

        val role = redisTemplate.opsForValue().get(key).awaitFirstOrNull()

        if(role != null){
            return role
        }

        val resp = verifyApi.writeVerify(account, appId).awaitSingle()

        return redisTemplate.opsForValue().get(key).awaitFirstOrNull()
    }

}