package com.github.binmagic.saas.velen.authority.service

import reactor.core.publisher.Mono

interface VerifyService
{
    fun writeVerify(account: String, appId : String, role : String, force : Boolean): Mono<Boolean>

    fun getVerify(account: String, method: String, uri: String): Mono<Boolean>

    fun writeRoleVerify(role: String, ids : List<String>): Mono<Void>
}