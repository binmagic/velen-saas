package com.github.binmagic.saas.velen.common.util;

public class StrUtil
{

	public static boolean isAnyBlank(String... strArr)
	{

		for(int i = 0; i < strArr.length; i++)
		{
			if(cn.hutool.core.util.StrUtil.isBlank(strArr[i]))
			{
				return true;
			}
		}
		return false;

	}

}
