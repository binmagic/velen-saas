package com.github.binmagic.saas.velen.authority.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Document
@Data
@Entity
@Accessors(chain = true)
public class App extends BaseEntity<App>
{
	public static final Integer DRAFT = 1;

	public static final Integer WAIT_CHECK = 1 << 1;

	public static final Integer REFUSE = 1 << 2;

	public static final Integer APPLY = 1 << 3;

	public static final Integer INVALID = 1 << 4;

	private String name;

	private String desc;

	private String appKey;

	private String ownerId;

	private String ownerName;

	private Integer state;

	@CreatedDate
	private LocalDateTime createTime;

	private LocalDateTime updateTime;
}
