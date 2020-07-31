package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryResultDTO
import com.github.binmagic.saas.velen.config.service.QueryService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("query")
@RestController
class QueryController : BaseController() {

    @Autowired
    lateinit var queryService: QueryService

    @PostMapping("report")
    suspend fun reportMetaEvent(@RequestBody reportQueryDTO: ReportQueryDTO): ReportQueryResultDTO {
        return queryService.query(reportQueryDTO).awaitSingle()
    }
}