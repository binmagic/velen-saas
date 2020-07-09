package com.github.binmagic.saas.velen.config.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaEventETLDTO
{
	private String name;

	private String remark;

	private String appId;

	private List<MetaEventETLPropDTO> props = new ArrayList<>();

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MetaEventETLPropDTO
	{
		private String name;

		private String type;

		private Integer index;

		private String comment;
	}
}
