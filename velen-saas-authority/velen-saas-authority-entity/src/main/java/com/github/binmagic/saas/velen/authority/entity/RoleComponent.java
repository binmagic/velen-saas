package com.github.binmagic.saas.velen.authority.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleComponent extends BaseEntity<RoleComponent>
{
	private String roleId;

	private String componentId;

}
