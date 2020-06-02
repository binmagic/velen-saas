package com.github.binmagic.saas.velen.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaEventSaveDTO
{

	private String showName;

	private String name;

	private List<String> platform;

	private String timing;

	private List<String> tag;

	private String remark;

}
