package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.config.entity.Tag
import com.github.binmagic.saas.velen.config.repository.TagRepository
import com.github.binmagic.saas.velen.config.service.TagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class TagServiceImpl : TagService {

    @Autowired
    lateinit var tagRepository: TagRepository


    override fun getAll(): Flux<Tag> {
        return tagRepository.findAll()
    }

}