package com.github.binmagic.saas.velen.config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BookmarksListDTO
{
	private String id;
	private String type;

	private String name;

	private LocalDateTime createTime;

	private List<String> dashboards;

	private String appId;

	private String data;

	private String config;
}
