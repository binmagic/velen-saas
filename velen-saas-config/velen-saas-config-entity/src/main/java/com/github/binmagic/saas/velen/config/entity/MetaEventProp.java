package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class MetaEventProp extends BaseEntity<MetaEventProp>
{

	public static final MetaEventProp EMPTY = new MetaEventProp();

	private String showName;

	private String name;

	private String type;

	private Boolean isPublish;

	private Boolean isVisible;

	private Boolean isInUse;

	private List<String> platform;

	private String timing;

	private String unit;

	private String example;


	private String appId;

	private String createUser;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;
}
