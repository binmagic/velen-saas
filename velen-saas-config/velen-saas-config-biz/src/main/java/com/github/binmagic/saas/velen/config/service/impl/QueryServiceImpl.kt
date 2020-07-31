package com.github.binmagic.saas.velen.config.service.impl

import com.github.binmagic.saas.velen.common.component.datasource.DynamicDataSource
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryResultDTO
import com.github.binmagic.saas.velen.config.query.QueryFactory
import com.github.binmagic.saas.velen.config.service.QueryService
import kotlinx.coroutines.reactive.awaitSingle
import org.davidmoten.rx.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class QueryServiceImpl : QueryService {

    @Autowired
    lateinit var dynamicDataSource: DynamicDataSource

    override suspend fun query(reportQueryDTO: ReportQueryDTO): Mono<ReportQueryResultDTO> {

        val database: Database = dynamicDataSource.source.awaitSingle()

        val query = QueryFactory.newQuery(reportQueryDTO) ?: return Mono.error(RuntimeException())

        return query.query(database)
    }
}