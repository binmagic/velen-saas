package com.github.binmagic.saas.velen.config.entity;


import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class MetaEvent extends BaseEntity<MetaEvent>
{

	public static final MetaEvent EMPTY = new MetaEvent();

	private String showName;

	private String name;

	private List<String> platform;

	private String timing;

	private List<String> tag;

	private String remark;

	private String appId;

	private String createUser;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	private List<String> propIds;
}
