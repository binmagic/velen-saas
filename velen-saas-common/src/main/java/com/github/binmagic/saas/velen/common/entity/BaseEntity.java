package com.github.binmagic.saas.velen.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class BaseEntity<E>
{

	@Id
	protected String id;
}
