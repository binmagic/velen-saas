package com.github.binmagic.saas.velen.config.query

import com.github.binmagic.saas.velen.common.util.ResultSetUtil
import com.github.binmagic.saas.velen.config.dto.query.MetaEventReportQueryResultDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryDTO
import com.github.binmagic.saas.velen.config.dto.query.ReportQueryResultDTO
import com.github.haivan.sqlbuilder.SqlBuilder
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.davidmoten.rx.jdbc.Database
import reactor.core.publisher.Mono

class MetaEventQuery(reportQueryDTO: ReportQueryDTO) : IQuery(reportQueryDTO) {


    override suspend fun query(database: Database): Mono<ReportQueryResultDTO> {

        val data = reportQueryDTO.measures

        val sqlBuilder = SqlBuilder.newBuilder()
        sqlBuilder.from("event")
                .select(data.props.toTypedArray())
                .limit(data.limit)
                .offset(data.limit * (data.page-1))

        val conditions = data.filter.conditions
        if(conditions.size > 0) {
            sqlBuilder.where {
                for (i in 0 until conditions.size) {
                    val obj = conditions[i]
                    if (!data.filter.isUnion) {
                        it.or()
                    }
                    when (obj.op) {
                        "equal" -> it.where(obj.prop, obj.params[0])
                        "notEqual" -> it.whereNot(obj.prop, obj.params[0])
                        "contain" -> it.whereContains(obj.prop, obj.params[0], false, null)
                        "notContain" -> it.whereNotContains(obj.prop, obj.params[0], false, null)
                        "less" -> it.where(obj.prop, "<", obj.params[0])
                        "greater" -> it.where(obj.prop, ">", obj.params[0])
                        "between" -> it.whereBetween(obj.prop, obj.params[0], obj.params[1])
                        "true" -> it.whereNotNull(obj.prop)
                        "false" -> it.whereNull(obj.prop)
                    }
                }
                return@where it
            }
        }

        val sql = sqlBuilder.build().toString()

        val countSql = sqlBuilder.asCount().build().toString()

        val count = database.select(countSql)
                .getAs(Long::class.java)
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