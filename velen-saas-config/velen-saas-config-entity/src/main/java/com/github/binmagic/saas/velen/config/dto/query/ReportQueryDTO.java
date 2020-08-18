package com.github.binmagic.saas.velen.config.dto.query;

import com.alibaba.fastjson.JSONObject;
import com.github.binmagic.saas.velen.config.type.QueryTaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportQueryDTO
{
	private String fromDate;

	private String toDate;

	private Boolean useCache;

	private QueryTaskType subTaskType;

	private String requestId;

	private List<String> byFields;

	private AxisConfig axisConfig;

	private Measures measures;

	private String topN;

	private String unit;

	private String valueTypes;

	@Data
	public static class AxisConfig{

	}

	@Data
	public static class Measures{

		private List<String> props;

		private int page;

		private int limit;

		private Filter filter;
	}

	@Data
	public static class Filter{

		private boolean union;

		private List<Condition> conditions;
	}

	@Data
	public static class Condition{

		private String op;

		private String prop;

		private List<String> params;
	}


}
