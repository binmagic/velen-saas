package com.github.binmagic.saas.velen.config.query

import com.github.binmagic.saas.velen.config.dto.query.ReportQueryDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryResultDTO
import org.davidmoten.rx.jdbc.Database
import reactor.core.publisher.Mono

abstract class IQuery(reportQueryDTO: ReportQueryDTO) {

    protected var reportQueryDTO: ReportQueryDTO = reportQueryDTO

    abstract suspend fun query(database : Database): Mono<ReportQueryResultDTO>
}