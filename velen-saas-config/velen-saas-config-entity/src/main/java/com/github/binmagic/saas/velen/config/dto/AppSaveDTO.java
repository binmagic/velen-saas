package com.github.binmagic.saas.velen.config.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class AppSaveDTO
{
	private String name;

	private String desc;

	private String template;
}
