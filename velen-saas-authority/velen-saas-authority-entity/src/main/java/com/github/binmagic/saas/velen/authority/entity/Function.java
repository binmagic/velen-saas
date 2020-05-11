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
public class Function extends BaseEntity<Function>
{

	public static final Function EMPTY = new Function();

	public static final int STATE_ENABLE = 1;
	public static final int STATE_DISABLE = 2;

	private String name;

	private String method;

	private String uri;

	private Integer state;

	private LocalDateTime updateTime;

	@CreatedDate
	private LocalDateTime createTime;
}
