package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class KeyRule extends BaseEntity<KeyRule> {

    private String key;

    private String name;

    private Integer ruleKey;

    private String rule;

    private String type;

    private String appId;

    private String createUser;
}
