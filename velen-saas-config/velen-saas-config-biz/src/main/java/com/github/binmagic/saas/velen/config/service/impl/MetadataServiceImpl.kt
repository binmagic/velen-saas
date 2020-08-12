package com.github.binmagic.saas.velen.config.service.impl

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.IdUtil
import com.github.binmagic.saas.velen.common.config.EnumUtil
import com.github.binmagic.saas.velen.common.entity.Page
import com.github.binmagic.saas.velen.config.dto.MetaEventETLDTO
import com.github.binmagic.saas.velen.config.entity.MetaEvent
import com.github.binmagic.saas.velen.config.entity.MetaEventProp
import com.github.binmagic.saas.velen.config.etl.TableMetadataApi
import com.github.binmagic.saas.velen.config.repository.AppVerifyRepository
import com.github.binmagic.saas.velen.config.repository.MetaEventPropRepository
import com.github.binmagic.saas.velen.config.repository.MetaEventRepository
import com.github.binmagic.saas.velen.config.service.MetadataService
import com.velen.etl.ResultCode
import com.velen.etl.generator.tdo.PropertyMetadataTDO
import com.velen.etl.generator.tdo.TableMetadataTDO
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.BeanUtils
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

@Service
class MetadataServiceImpl : MetadataService {

    @Autowired
    lateinit var metaEventPropRepository: MetaEventPropRepository

    @Autowired
    lateinit var metaEventRepository: MetaEventRepository

    @Autowired
    lateinit var tableMetadataApi: TableMetadataApi

    @Autowired
    lateinit var appVerifyRepository: AppVerifyRepository


    override suspend fun findMetaEventPropByPage(query: Page.Query, appId: String): Mono<Page.Result<MetaEventProp>> {
        val orderList: MutableList<Sort.Order> = ArrayList(query.sorts.size)

        for ((key, value) in query.sorts) {
            orderList.add(Sort.Order(Sort.Direction.valueOf(value!!), key))
        }
        var exampleMatcher = ExampleMatcher.matching()
                .withMatcher("appId", ExampleMatcher.GenericPropertyMatchers.exact())

        val metaEventProp = BeanUtil.mapToBean(query.params, MetaEventProp::class.java, true)
        metaEventProp.appId = appId


        val example = Example.of(metaEventProp, exampleMatcher)

        val total = metaEventPropRepository.count(example).awaitSingle()

        if (query.limit == 0 || query.limit == null) {
            query.limit = total.toInt()
        }

        val pageRequest = PageRequest.of(query.page - 1, query.limit, Sort.by(orderList))

        val items = metaEventPropRepository.findAll(example, pageRequest).collectList().awaitSingle()

        return Page.Result(total, items).toMono()
    }


    override suspend fun createMetaEventProp(metaEventProp: MetaEventProp): Mono<MetaEventProp> {

        val now = LocalDateTime.now()
        metaEventProp.createTime = now
        metaEventProp.updateTime = now
        metaEventProp.id = null


        return metaEventPropRepository.insert(metaEventProp)
    }

    override suspend fun findMetaEventByPage(query: Page.Query, appId: String, user: String): Mono<Page.Result<MetaEvent>> {

        val orderList: MutableList<Sort.Order> = ArrayList(query.sorts.size)

        for ((key, value) in query.sorts) {
            orderList.add(Sort.Order(Sort.Direction.valueOf(value!!), key))
        }

        var exampleMatcher =
                ExampleMatcher.matching().withMatcher("appId", ExampleMatcher.GenericPropertyMatchers.exact())

        val metaEvent = BeanUtil.mapToBean(query.params, MetaEvent::class.java, true)
        metaEvent.appId = appId

        val example = Example.of(metaEvent, exampleMatcher)

        val total = metaEventRepository.count(example).awaitSingle()

        if (query.limit == 0 || query.limit == null) {
            query.limit = total.toInt()
        }

        val pageRequest = PageRequest.of(query.page - 1, query.limit, Sort.by(orderList))

        val items = metaEventRepository.findAll(example, pageRequest).collectList().awaitSingle()

        return Page.Result(total, items).toMono()

    }

    override suspend fun createMetaEvent(metaEvent: MetaEvent): Mono<MetaEvent> {

        val now = LocalDateTime.now()
        metaEvent.createTime = now
        metaEvent.updateTime = now
        metaEvent.id = null

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


        val responseEntity = tableMetadataApi.alterTable(metaEvent.appId, TableMetadataApi.Convert.toEventMetadataTDO(metaEventETLDTO), metaEvent.createUser)
        if (EnumUtil.isInResultCode(responseEntity.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(responseEntity.statusCodeValue).message()))
        }

        return metaEventRepository.insert(metaEvent)
    }

    override suspend fun updateMetaEvent(metaEvent: MetaEvent): Mono<MetaEvent> {
        val metaEventQuery = metaEventRepository.findById(metaEvent.id).awaitSingle()
        val delProp: MutableList<String> = ArrayList(metaEventQuery.propIds)
        val addProp: MutableList<String> = ArrayList(metaEvent.propIds)
        delProp.removeAll(metaEvent.propIds)
        addProp.removeAll(metaEventQuery.propIds)

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

        val responseEntity = tableMetadataApi.updateTable(metaEvent.appId, "event",
                TableMetadataApi.Convert.toEventMetadataTDO(metaEventETLDTO), metaEvent.createUser)
        if (EnumUtil.isInResultCode(responseEntity.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(responseEntity.statusCodeValue).message()))
        }
        /*val delList: MutableList<PropertyMetadataTDO> = ArrayList()
        for (metaEventPropId in delProp) {
            val propertyMetadataTDO = PropertyMetadataTDO()
            val prop = metaEventPropRepository.findById(metaEventPropId).awaitSingle()
            val props = metaEventPropRepository.findAll().collectList().awaitSingle()
            val index = props.indexOf(prop)
            BeanUtils.copyProperties(prop, propertyMetadataTDO)
            propertyMetadataTDO.index = index
            delList.add(propertyMetadataTDO)
        }

        val resp = tableMetadataApi.removeProperties(metaEvent.appId, "event", delList)
        if (EnumUtil.isInResultCode(resp.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(resp.statusCodeValue).message()))
        }*/

        /*val addList: MutableList<PropertyMetadataTDO> = ArrayList()
        for (metaEventPropId in addProp) {
            val propertyMetadataTDO = PropertyMetadataTDO()
            val prop = metaEventPropRepository.findById(metaEventPropId).awaitSingle()
            val props = metaEventPropRepository.findAll().collectList().awaitSingle()
            val index = props.indexOf(prop)
            BeanUtils.copyProperties(prop, propertyMetadataTDO)
            propertyMetadataTDO.index = index
            addList.add(propertyMetadataTDO)
        }

        val responseEntity = tableMetadataApi.addProperties(metaEvent.appId, "event", addList)
        if (EnumUtil.isInResultCode(responseEntity.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(responseEntity.statusCodeValue).message()))
        }*/

        return metaEventRepository.save(metaEvent)
    }

    override suspend fun deleteMetaEvent(id: String): Mono<Void> {

        return metaEventRepository.deleteById(id)
    }

    override suspend fun updateMetaEventProp(metaEventProp: MetaEventProp): Mono<MetaEventProp> {

        val list: MutableList<PropertyMetadataTDO> = ArrayList()
        val propertyMetadataTDO = PropertyMetadataTDO()

        propertyMetadataTDO.name = metaEventProp.name
        propertyMetadataTDO.type = metaEventProp.type
        val props = metaEventPropRepository.findAll().collectList().awaitSingle()
        val prop = metaEventPropRepository.findById(metaEventProp.id).awaitSingle()
        val index = props.indexOf(prop)
        propertyMetadataTDO.index = index

        list.add(propertyMetadataTDO)
        /*val resp = tableMetadataApi.updateProperties(metaEventProp.appId, "event", list)
        if (EnumUtil.isInResultCode(resp.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(resp.statusCodeValue).message()))
        }*/

        return metaEventPropRepository.save(metaEventProp)
    }

    override suspend fun deleteMetaEventProp(appId: String, id: String): Mono<Void> {
        val metaEvents = metaEventRepository.findAll().collectList().awaitSingle()
        val delList: MutableList<PropertyMetadataTDO> = ArrayList()
        for (metaEvent in metaEvents) {
            if (metaEvent.propIds.indexOf(id) != -1) {
                metaEvent.propIds.remove(id)
                metaEventRepository.save(metaEvent)
                val metaEventProp = metaEventPropRepository.findById(id).awaitSingle()
                val propertyMetadataTDO = PropertyMetadataTDO()
                BeanUtils.copyProperties(metaEventProp, propertyMetadataTDO)
                val props = metaEventPropRepository.findAll().collectList().awaitSingle()
                val index = props.indexOf(metaEventProp)
                propertyMetadataTDO.index = index
                delList.add(propertyMetadataTDO)
            }
        }
        /*val resp = tableMetadataApi.removeProperties(appId, "event", delList)
        if (EnumUtil.isInResultCode(resp.statusCodeValue)) {
            return Mono.error(RuntimeException(ResultCode.valueOf(resp.statusCodeValue).message()))
        }*/
        return metaEventPropRepository.deleteById(id)
    }

    override suspend fun all(appId: String, user: String): Mono<Void> {
        val now = LocalDateTime.now()
        val list = metaEventRepository.findByAppId(appId).collectList().awaitSingle()
        if (!appVerifyRepository.findByAppId(appId).awaitSingle().verify) {
            val tableMetadatasTDO = tableMetadataApi.getTables(appId, user)
            println("getTables$tableMetadatasTDO")
            for (metaEvent in list) {
                val tableMetadataTDO = tableMetadataApi.getTable(appId, metaEvent.name, metaEvent.createUser)
                if (tableMetadatasTDO.contains(tableMetadataTDO)) {
                    tableMetadatasTDO.remove(tableMetadataTDO)
                }
                println("getTable$tableMetadataTDO")
            }
            println("getTables$tableMetadatasTDO")
            if (!tableMetadatasTDO.isNullOrEmpty()) {
                for (item in tableMetadatasTDO) {
                    val metaEventETLDTO = TableMetadataApi.Convert.toMetaEventETLDTO(item)
                    val metaEvent = MetaEvent()
                    metaEvent.appId = appId
                    metaEvent.createTime = now
                    metaEvent.createUser = user
                    metaEvent.isVisible = false
                    metaEvent.name = metaEventETLDTO.name
                    metaEvent.updateTime = now
                    metaEvent.showName = metaEventETLDTO.name
                    val propIds: MutableList<String> = ArrayList()
                    for (prop in metaEventETLDTO.props) {
                        val metaEventProp = MetaEventProp()
                        metaEventProp.createUser = user
                        metaEventProp.appId = appId
                        metaEventProp.createTime = now
                        metaEventProp.name = prop.name
                        metaEventProp.type = prop.type
                        metaEventProp.showName = prop.name
                        val monoProp = metaEventPropRepository.insert(metaEventProp).awaitSingle()
                        propIds.add(monoProp.id)
                    }
                    metaEvent.propIds = propIds
                    metaEventRepository.insert(metaEvent).awaitSingle()
                }
            }
        }
        return Mono.just(list).then()
    }

    override suspend fun findAllMetaEvent(appId: String): Flux<MetaEvent> {

        return metaEventRepository.findByAppId(appId)
    }

    override suspend fun findAllMetaEventProp(appId: String): Flux<MetaEventProp> {
        return metaEventPropRepository.findByAppId(appId)
    }


}
