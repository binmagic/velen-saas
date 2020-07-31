package com.github.binmagic.saas.velen.config.query

import com.github.binmagic.saas.velen.config.dto.query.ReportQueryDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryResultDTO
import org.davidmoten.rx.jdbc.Database
import reactor.core.publisher.Mono

class UserAnalyticsQuery(reportQueryDTO: ReportQueryDTO) : IQuery(reportQueryDTO){


    override suspend fun query(database: Database): Mono<ReportQueryResultDTO> {



        database.select("select ")
        return Mono.empty()
    }
}