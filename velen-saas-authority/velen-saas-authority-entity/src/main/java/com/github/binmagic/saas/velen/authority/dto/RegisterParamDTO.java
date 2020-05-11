package com.github.binmagic.saas.velen.authority.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class RegisterParamDTO
{
	@NotEmpty
	private String account;

	private String password;

	private String code;

}
