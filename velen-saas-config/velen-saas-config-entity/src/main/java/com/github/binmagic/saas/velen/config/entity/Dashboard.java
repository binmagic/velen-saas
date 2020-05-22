package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Dashboard extends BaseEntity<Dashboard>
{
	private String name;

	private String type;

	private Integer sort;

	@CreatedDate
	private LocalDateTime createTime;

}
