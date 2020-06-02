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
public class MetaEventDTO
{

	private String showName;

	private String name;

	private List<String> platform = new ArrayList<>();

	private String timing;

	private List<String> tag = new ArrayList<>();

	private String remark;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;
}
