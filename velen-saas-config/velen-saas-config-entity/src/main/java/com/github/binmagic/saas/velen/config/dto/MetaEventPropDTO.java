package com.github.binmagic.saas.velen.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaEventPropDTO
{
	private String id;

	private String showName;

	private String name;

	private String type;

	private Boolean isPublish;

	private Boolean isInUser;

	private Boolean isVisible;

	private List<String> platform = new ArrayList<>();

	private String timing;

	private String unit;

	private String example;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;
}
