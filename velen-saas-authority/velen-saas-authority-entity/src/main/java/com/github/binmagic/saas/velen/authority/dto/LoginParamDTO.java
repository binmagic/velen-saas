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
public class LoginParamDTO
{
	private String account;

	private String password;

	@NotEmpty(message = "授权类型不能为空")
	private String grantType;

	private String refreshToken;

	private String key;

	private String code;
}
