package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class CommonGroup extends BaseEntity<CommonGroup> {

    private String name;

    private Integer isParent;

    private String appId;

    private String userId;
}
