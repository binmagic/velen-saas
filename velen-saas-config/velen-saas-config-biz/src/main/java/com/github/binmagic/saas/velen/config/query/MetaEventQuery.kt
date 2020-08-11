package com.github.binmagic.saas.velen.config.query

import com.github.binmagic.saas.velen.common.util.ResultSetUtil
import com.github.binmagic.saas.velen.config.dto.query.MetaEventReportQueryResultDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryResultDTO
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.davidmoten.rx.jdbc.Database
import reactor.core.publisher.Mono

class MetaEventQuery(reportQueryDTO: ReportQueryDTO) : IQuery(reportQueryDTO) {


    override suspend fun query(database: Database): Mono<ReportQueryResultDTO> {

        val data = reportQueryDTO.measures

        val props = data.getJSONArray("props")

        val select = StringBuilder()

        for (i in 0 until props.size) {
            val prop = props.getString(i)
            select.append(prop).append(",")
        }

        val page = data.getInteger("page") ?: 1
        val limit = data.getInteger("limit") ?: 10

        val filter = data.getJSONObject("filter");

        val union = filter.getString("union")
        var conditions = filter.getJSONArray("conditions")

        val whereArr = mutableListOf<String>()

        for (i in 0 until conditions.size) {
            val obj = conditions.getJSONObject(i)
            val filter = obj.getString("filter")
            var op = "="
            val params = obj.getJSONArray("params")
            val param = if (params.size > 0) params[0].toString() else ""
            val prop = obj.getString("prop")
            whereArr.add(" ${prop} ${op} '${param}' ")
        }

        val sql = "select ${select.substring(0, select.length - 1)} from event" +
                " where 1 = 1 ${if (whereArr.size > 0) "and" else ""} ${whereArr.joinToString(union)} limit ${(page - 1) * limit},${limit}"

        val countSql = "select count(1) from event" +
                " where 1 = 1 ${if (whereArr.size > 0) "and" else ""} ${whereArr.joinToString(union)}"

        val count = database.select(countSql).getAs(Long::class.java)
                .awaitSingle()

        val metaEventReportQueryResultDTO = MetaEventReportQueryResultDTO()

        if (count > 0) {
            val result = database.select(sql)
                    .get { rs -> ResultSetUtil.toListMap(rs) }
                    .awaitFirstOrNull()

            metaEventReportQueryResultDTO.rows = result
        }

        metaEventReportQueryResultDTO.total = count

        return Mono.just(metaEventReportQueryResultDTO)
    }
}