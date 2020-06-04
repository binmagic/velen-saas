package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.common.component.datasource.DynamicDataSource
import com.github.binmagic.saas.velen.config.dto.ReportMetaEventDTO
import com.github.binmagic.saas.velen.config.service.QueryService
import kotlinx.coroutines.reactive.awaitSingle
import lombok.extern.slf4j.Slf4j
import org.davidmoten.rx.jdbc.Database
import org.davidmoten.rx.jdbc.Parameter
import org.davidmoten.rx.jdbc.SelectBuilder
import org.davidmoten.rx.jdbc.tuple.TupleN
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("query")
@RestController
class QueryController : BaseController(){



    @Autowired
    lateinit var queryService : QueryService

    @Autowired
    lateinit var dynamicDataSource: DynamicDataSource

    @PostMapping("meta-event")
    suspend fun reportMetaEvent(@RequestBody reportMetaEventDTO : ReportMetaEventDTO){

        val database : Database = dynamicDataSource.source.awaitSingle()

        val set = database.select("select * from events")
                .count().blockingGet()


        System.err.println(set)

    }


}