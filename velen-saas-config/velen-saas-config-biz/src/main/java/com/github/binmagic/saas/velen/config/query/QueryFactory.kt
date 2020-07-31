package com.github.binmagic.saas.velen.config.query

import com.github.binmagic.saas.velen.config.dto.query.ReportQueryDTO
import com.github.binmagic.saas.velen.config.type.QueryTaskType

object QueryFactory {

    fun newQuery(reportQueryDTO: ReportQueryDTO): IQuery? {

        when (reportQueryDTO.subTaskType) {
            QueryTaskType.META_EVENT -> {
                return MetaEventQuery(reportQueryDTO)
            }
            QueryTaskType.USERANALYTICS -> {
                return UserAnalyticsQuery(reportQueryDTO)
            }
        }
        return null
    }

}