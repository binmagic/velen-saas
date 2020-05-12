package com.github.binmagic.saas.velen.authority.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class AppMemberInfoDTO
{

	private String id;

	private String memberId;

	private String memberName;

	private String memberRoleId;

}
