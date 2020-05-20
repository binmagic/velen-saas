package com.github.binmagic.saas.velen.authority.service.impl

import com.github.binmagic.saas.velen.authority.service.FunctionService
import com.github.binmagic.saas.velen.authority.service.VerifyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class VerifyServiceImpl : VerifyService {

    private val METHOD_LIST : List<String> = listOf("GET", "POST", "PATCH", "DELETE", "PUT", "ALL")

    private val EXPIRE_TIME = Duration.ofDays(3L)

    @Autowired
    lateinit var redisTemplate: ReactiveStringRedisTemplate

    @Autowired
    lateinit var functionService: FunctionService

    override fun writeVerify(account: String, appId : String, role : String, force : Boolean): Mono<Boolean> {
        val key = "${account}_${appId}"

        return if(force){
            redisTemplate.opsForValue().set(key, role, EXPIRE_TIME)
        }else{
            redisTemplate.opsForValue().setIfAbsent(key, role, EXPIRE_TIME)
        }

    }

    override fun getVerify(account: String, method: String, uri: String): Mono<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun writeRoleVerify(role: String, ids : List<String>): Mono<Void> {
        return functionService.getFunctionList(ids)
                .doOnNext {
                    for(method in METHOD_LIST){
                        redisTemplate.delete("${role}_${method}")
                    }
                }
                .flatMapSequential {
                    redisTemplate.opsForSet().add("${role}_${it.method}", it.uri)
                            .thenReturn(it)
                }.then()
    }

}