package com.github.binmagic.saas.velen.config.entity;


import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class MetaEventPropMapping extends BaseEntity<MetaEventPropMapping>
{
	private String eventId;

	private String propId;
}
