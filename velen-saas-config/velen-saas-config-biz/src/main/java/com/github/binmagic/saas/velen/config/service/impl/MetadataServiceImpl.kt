package com.github.binmagic.saas.velen.config.service.impl

import cn.hutool.core.bean.BeanUtil
import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.dto.MetaEventETLDTO
import com.github.binmagic.saas.velen.config.etl.ProfileTableApi
import com.github.binmagic.saas.velen.config.entity.MetaEvent
import com.github.binmagic.saas.velen.config.entity.MetaEventProp
import com.github.binmagic.saas.velen.config.event.CreateMetaEvent
import com.github.binmagic.saas.velen.config.repository.MetaEventPropRepository
import com.github.binmagic.saas.velen.config.repository.MetaEventRepository
import com.github.binmagic.saas.velen.config.service.MetadataService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDateTime
import java.util.ArrayList

@Service
class MetadataServiceImpl : MetadataService {

    @Autowired
    lateinit var metaEventPropRepository: MetaEventPropRepository

    @Autowired
    lateinit var metaEventRepository: MetaEventRepository


    @Autowired
    lateinit var applicationContext: ApplicationContext

    override suspend fun findMetaEventPropByPage(query: Page.Query, appId: String): Mono<Page.Result<MetaEventProp>> {
        val orderList: MutableList<Sort.Order> = ArrayList(query.sorts.size)

        for ((key, value) in query.sorts) {
            orderList.add(Sort.Order(Sort.Direction.valueOf(value!!), key))
        }
        var exampleMatcher = ExampleMatcher.matching()
                .withMatcher("appId", ExampleMatcher.GenericPropertyMatchers.exact())

        val metaEventProp = BeanUtil.mapToBean(query.params, MetaEventProp::class.java, true)
        metaEventProp.appId = appId

        val pageRequest = PageRequest.of(query.page - 1, query.limit, Sort.by(orderList))

        val example = Example.of(metaEventProp, exampleMatcher)

        val total = metaEventPropRepository.count(example).awaitSingle()

        val items = metaEventPropRepository.findAll(example, pageRequest).collectList().awaitSingle()

        return Page.Result(total, items).toMono()
    }


    override suspend fun createMetaEventProp(metaEventProp: MetaEventProp): Mono<MetaEventProp> {

        metaEventProp.createTime = LocalDateTime.now()
        metaEventProp.updateTime = LocalDateTime.now()



        return metaEventPropRepository.insert(metaEventProp)
    }

    override suspend fun findMetaEventByPage(query: Page.Query, appId: String): Mono<Page.Result<MetaEvent>> {

        val orderList: MutableList<Sort.Order> = ArrayList(query.sorts.size)

        for ((key, value) in query.sorts) {
            orderList.add(Sort.Order(Sort.Direction.valueOf(value!!), key))
        }

        var exampleMatcher =
                ExampleMatcher.matching().withMatcher("appId", ExampleMatcher.GenericPropertyMatchers.exact())

        val metaEvent = BeanUtil.mapToBean(query.params, MetaEvent::class.java, true)
        metaEvent.appId = appId

        val pageRequest = PageRequest.of(query.page - 1, query.limit, Sort.by(orderList))

        val example = Example.of(metaEvent, exampleMatcher)

        val total = metaEventRepository.count(example).awaitSingle()

        val items = metaEventRepository.findAll(example, pageRequest).collectList().awaitSingle()

        return Page.Result(total, items).toMono()

    }

    override suspend fun createMetaEvent(metaEvent: MetaEvent): Mono<MetaEvent> {

        val now = LocalDateTime.now()
        metaEvent.createTime = now
        metaEvent.updateTime = now

        val metaEventETLDTO = MetaEventETLDTO()

        BeanUtils.copyProperties(metaEvent, metaEventETLDTO)

        for (propId in metaEvent.propIds) {
            val prop = metaEventPropRepository.findById(propId).awaitSingle()
            val list = metaEventPropRepository.findAll().collectList().awaitSingle()
            val index = list.indexOf(prop)
            val metaEventETLPropDTO = MetaEventETLDTO.MetaEventETLPropDTO()
            BeanUtils.copyProperties(prop, metaEventETLPropDTO)
            metaEventETLPropDTO.index = index
            metaEventETLDTO.props.add(metaEventETLPropDTO)
        }

        val mono = metaEventRepository.insert(metaEvent)

        mono.subscribe {
            applicationContext.publishEvent(CreateMetaEvent(metaEvent.appId, metaEvent.createUser, metaEventETLDTO))
        }

        return mono
    }


}