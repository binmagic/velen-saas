package com.github.binmagic.saas.velen.authority.entity;


import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;


@Document
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu extends BaseEntity<RoleMenu>
{

	private String roleId;

	private String menuId;

}
