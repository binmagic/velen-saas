package com.github.binmagic.saas.velen.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BizException extends RuntimeException
{
	private String error;

	public static BizException of(String error)
	{
		return new BizException(error);
	}

}
