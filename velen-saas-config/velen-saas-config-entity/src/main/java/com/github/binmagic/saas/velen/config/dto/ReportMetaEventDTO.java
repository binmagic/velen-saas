package com.github.binmagic.saas.velen.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportMetaEventDTO
{
	private LocalDateTime fromDate;

	private LocalDateTime toDate;

	private Boolean useCache;

	private String subTaskType;

	private String request_id;
}
