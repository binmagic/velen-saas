package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Group extends BaseEntity<Group> {

    private String name;

    private String userId;

    private Integer sort;

    private String appId;

}
