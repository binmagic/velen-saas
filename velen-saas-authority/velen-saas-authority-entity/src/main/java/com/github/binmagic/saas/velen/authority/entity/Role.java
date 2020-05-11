package com.github.binmagic.saas.velen.authority.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.time.LocalDateTime;


@Document
@Data
@Entity
public class Role extends BaseEntity<Role>
{
	private String name;

	private Integer state;

	@CreatedDate
	private LocalDateTime createTime;
}
