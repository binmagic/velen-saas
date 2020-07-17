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
public class MetaEventPropSaveDTO
{
	private String showName;

	private String name;

	private String type;

	private Boolean isPublic;

	private Boolean isVisible;

	private List<String> platform = new ArrayList<>();

	private String timing;

	private String unit;

	private String example;
}
