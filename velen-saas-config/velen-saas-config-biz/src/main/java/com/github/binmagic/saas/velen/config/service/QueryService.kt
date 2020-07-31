package com.github.binmagic.saas.velen.config.service

import com.github.binmagic.saas.velen.config.dto.query.ReportQueryDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryResultDTO
import reactor.core.publisher.Mono

interface QueryService {

    suspend fun query(reportQueryDTO: ReportQueryDTO): Mono<ReportQueryResultDTO>

}