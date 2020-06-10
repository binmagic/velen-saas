package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.dto.BookmarksListDTO
import com.github.binmagic.saas.velen.config.dto.BookmarksSaveDTO
import com.github.binmagic.saas.velen.config.dto.BookmarksSaveRespDTO
import com.github.binmagic.saas.velen.config.dto.BookmarksUpdateDTO
import com.github.binmagic.saas.velen.config.entity.Bookmarks
import com.github.binmagic.saas.velen.config.service.BookmarksService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bookmarks")
class BookmarksController : BaseController() {


    @Autowired
    lateinit var bookmarksService: BookmarksService


    @GetMapping
    suspend fun all(query : Page.Query): Page.Result<BookmarksListDTO> {

        val findResult = bookmarksService.findPage(query).awaitSingle()

        val result = Page.Result<BookmarksListDTO>()

        result.total = findResult.total

        for (bookmark in findResult.items) {
            val bookmarksListDTO = BookmarksListDTO()
            BeanUtils.copyProperties(bookmark, bookmarksListDTO)
            result.items.add(bookmarksListDTO)
        }

        return result
    }

    @PostMapping
    suspend fun create(@Validated @RequestBody bookmarksSaveDTO: BookmarksSaveDTO) : BookmarksSaveRespDTO{

        val bookmarks = Bookmarks()
        bookmarks.appId = currentAppId.awaitSingle()
        bookmarks.userId = currentUserId.awaitSingle()

        BeanUtils.copyProperties(bookmarksSaveDTO, bookmarks)
        bookmarksService.create(bookmarks).awaitSingle()

        val bookmarksSaveRespDTO = BookmarksSaveRespDTO()
        BeanUtils.copyProperties(bookmarks, bookmarksSaveRespDTO)

        return bookmarksSaveRespDTO
    }

    @PutMapping
    suspend fun update(@Validated @RequestBody bookmarksUpdateDTO: BookmarksUpdateDTO) : ResponseEntity<Any>{

        val bookmarks = bookmarksService.getById(bookmarksUpdateDTO.id).awaitFirstOrNull()
                ?: return ResponseEntity.notFound().build()

        BeanUtils.copyProperties(bookmarksUpdateDTO, bookmarks)

        bookmarksService.update(bookmarks).awaitSingle()

        return ResponseEntity.ok().build()
    }

}