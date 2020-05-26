package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Tag extends BaseEntity<Tag>
{
	private String name;

	private String color;
}
