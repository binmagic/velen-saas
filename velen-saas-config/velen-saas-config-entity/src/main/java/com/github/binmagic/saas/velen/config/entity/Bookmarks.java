package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Bookmarks extends BaseEntity<Bookmarks>
{

	public static final Bookmarks EMPTY = new Bookmarks();

	private String name;

	private String type;

	private String config;

	private String data;

	private List<String> dashboards = new ArrayList<>();

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	private String userId;

	private String appId;
}
