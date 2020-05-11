package com.github.binmagic.saas.velen.authority.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

@Document
@Data
@Entity
@Accessors(chain = true)
public class AppMember extends BaseEntity<AppMember>
{

	private String appId;

	private String memberUserId;

	private String memberRoleId;

}
