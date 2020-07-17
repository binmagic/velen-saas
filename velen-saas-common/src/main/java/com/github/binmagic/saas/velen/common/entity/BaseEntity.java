package com.github.binmagic.saas.velen.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class BaseEntity<E> implements Cloneable
{

	@Id
	protected String id;

	@Override
	public E clone()
	{
		try
		{
			return (E)super.clone();
		}
		catch(Exception e)
		{

		}
		return null;
	}
}
