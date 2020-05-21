package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Bookmarks extends BaseEntity<Bookmarks>
{

	private String name;

	private String type;

	private String config;

	private String data;

	private LocalDateTime createTime;

	private String userId;

}
