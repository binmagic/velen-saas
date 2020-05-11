package com.github.binmagic.saas.velen.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error
{
	public static final String ERROR_TYPE_FORBID = "FORBID";


	private String errorType;

	private String error;
}
