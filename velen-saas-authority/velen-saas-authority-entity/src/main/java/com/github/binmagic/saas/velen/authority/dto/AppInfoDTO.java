package com.github.binmagic.saas.velen.authority.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class AppInfoDTO
{
	private String id;

	private String name;

	private String desc;

	private String appKey;

	private String owner;

	private LocalDateTime createTime;
}
