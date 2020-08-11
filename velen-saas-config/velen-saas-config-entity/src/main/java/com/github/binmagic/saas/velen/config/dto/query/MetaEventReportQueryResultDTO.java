package com.github.binmagic.saas.velen.config.dto.query;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class MetaEventReportQueryResultDTO extends ReportQueryResultDTO
{
	private List<Map<String, Object>> rows = new ArrayList<>();

	private long total;

}
