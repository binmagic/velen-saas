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

	private JSONObject axisConfig;

	private JSONObject measures;

	private String topN;

	private String unit;

	private String valueTypes;
}
