package com.github.binmagic.saas.velen.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardInfoDTO
{
	private String id;

	private String name;

	private String type;

	private LocalDateTime createTime;

	private String userId;

	private String userName;

	private Map<String, String> items;

	private String config;

}
