package com.github.binmagic.saas.velen.authority.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Document
@Data
public class User extends BaseEntity<User>
{
	private String account;

	private String name;

	private String phone;

	private String mail;

	private String password;

	private String salt;

	private Integer state;

	@CreatedDate
	private LocalDateTime createTime;
}
