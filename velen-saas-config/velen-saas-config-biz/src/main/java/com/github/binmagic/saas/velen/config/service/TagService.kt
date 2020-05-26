package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.entity.Tag
import reactor.core.publisher.Flux

interface TagService {
    fun getAll(): Flux<Tag>
}