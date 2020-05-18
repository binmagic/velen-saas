package com.github.binmagic.saas.velen.authority.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

@Document
@Data
public class Resource extends BaseEntity<Resource>
{
	public static final Resource EMPTY = new Resource();

	private String name;

	private String desc;

	private Integer state;
}
