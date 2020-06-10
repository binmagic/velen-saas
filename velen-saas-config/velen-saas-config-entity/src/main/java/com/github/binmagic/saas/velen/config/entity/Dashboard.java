package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard extends BaseEntity<Dashboard>
{

	private String name;

	private String type;

	private Integer sort;

	@CreatedDate
	private LocalDateTime createTime;

	private String userId;

	private String userName;

	private String appId;

	private Map<String, String> items = new HashMap<>();

	private String config;

}
