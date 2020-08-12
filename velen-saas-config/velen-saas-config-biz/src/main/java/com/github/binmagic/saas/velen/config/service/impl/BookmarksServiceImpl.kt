package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.entity.Bookmarks
import com.github.binmagic.saas.velen.config.repository.BookmarksRepository
import com.github.binmagic.saas.velen.config.service.BookmarksService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDateTime
import java.util.ArrayList

@Service
class BookmarksServiceImpl : BookmarksService {

    @Autowired
    lateinit var bookmarksRepository: BookmarksRepository

    override suspend fun getAll(): Flux<Bookmarks>
    {
        return bookmarksRepository.findAll()
    }

    override suspend fun create(bookmarks: Bookmarks): Mono<Bookmarks> {

        bookmarks.createTime = LocalDateTime.now()

        return bookmarksRepository.insert(bookmarks)
    }

    override suspend fun getById(id: String): Mono<Bookmarks> {

        return bookmarksRepository.findById(id)
    }

    override suspend fun update(bookmarks: Bookmarks): Mono<Bookmarks> {

        bookmarks.updateTime = LocalDateTime.now()

        return bookmarksRepository.save(bookmarks)
    }

    override suspend fun findPage(query: Page.Query): Mono<Page.Result<Bookmarks>> {

        val orderList: MutableList<Sort.Order> = ArrayList(query.sorts.size)

        for ((key, value) in query.sorts) {
            orderList.add(Sort.Order(Sort.Direction.valueOf(value!!), key))
        }

        var exampleMatcher = ExampleMatcher.matchingAll()

        if (query.params.isNotEmpty()) {
            exampleMatcher = ExampleMatcher.matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase(true)
            for ((key) in query.params) {
                exampleMatcher.withMatcher(key, ExampleMatcher.GenericPropertyMatchers.contains())
            }
        }

//        val pageRequest = PageRequest.of(query.page - 1, query.limit, Sort.by(orderList))

        val example = Example.of(Bookmarks.EMPTY, exampleMatcher)

        val total = bookmarksRepository.count(example).awaitSingle()

        if (query.limit == 0 || query.limit == null) {
            query.limit = total.toInt()
        }

        val pageRequest = PageRequest.of(query.page - 1, query.limit, Sort.by(orderList))

        val items = bookmarksRepository.findAll(example, pageRequest).collectList().awaitSingle()

        return Page.Result(total, items).toMono()
    }


}