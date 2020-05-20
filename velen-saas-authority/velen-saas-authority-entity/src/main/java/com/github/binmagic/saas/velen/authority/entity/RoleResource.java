package com.github.binmagic.saas.velen.authority.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResource extends BaseEntity<RoleResource>
{
	private String roleId;

	private String resourceId;
}
