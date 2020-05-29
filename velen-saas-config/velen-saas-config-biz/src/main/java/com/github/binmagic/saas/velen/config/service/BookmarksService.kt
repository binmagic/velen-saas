package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.entity.Bookmarks
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookmarksService {

    suspend fun getAll(): Flux<Bookmarks>

    suspend fun create(bookmarks: Bookmarks): Mono<Bookmarks>

    suspend fun getById(id: String): Mono<Bookmarks>

    suspend fun update(bookmarks: Bookmarks): Mono<Bookmarks>

    suspend fun findPage(query: Page.Query): Mono<Page.Result<Bookmarks>>

}