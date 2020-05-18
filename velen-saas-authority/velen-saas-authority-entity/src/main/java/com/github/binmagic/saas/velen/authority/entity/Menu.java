package com.github.binmagic.saas.velen.authority.entity;


import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;


@Document
@Data
public class Menu extends BaseEntity<Menu>
{
	public static final Menu EMPTY = new Menu();

	private String name;

	private Integer state;
}
