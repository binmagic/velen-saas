package com.github.binmagic.saas.velen.config.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportQueryResultDTO
{
	private String byField;

	private String dateUpdateTime;

	private String dataSufficientUpdateTime;

	private Integer samplingFactor;

	private Boolean truncated;
}
