package com.github.binmagic.saas.velen.config.query

import com.github.binmagic.saas.velen.common.util.ResultSetUtil
import com.github.binmagic.saas.velen.config.dto.query.MetaEventReportQueryResultDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryResultDTO
import kotlinx.coroutines.reactive.awaitSingle
import org.davidmoten.rx.jdbc.Database
import reactor.core.publisher.Mono
import reactor.kotlin.adapter.rxjava.toFlux
import java.lang.StringBuilder
import kotlin.collections.HashMap

class MetaEventQuery(reportQueryDTO: ReportQueryDTO) : IQuery(reportQueryDTO) {


    override suspend fun query(database: Database): Mono<ReportQueryResultDTO> {

        val data = reportQueryDTO.measures

        val props = data.getJSONArray("props")

        val select = StringBuilder()

        for(i in 0 until props.size){
            val prop = props.getString(i)
            select.append(prop).append(",")
        }

        val sql = "select ${select.substring(0, select.length - 1)} from event"

        val result = database.select(sql)
                .get { rs -> ResultSetUtil.toListMap(rs) }
                .awaitSingle()

        val metaEventReportQueryResultDTO = MetaEventReportQueryResultDTO()

        metaEventReportQueryResultDTO.rows = result

        return Mono.just(metaEventReportQueryResultDTO)
    }
}