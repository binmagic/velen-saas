package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class DispatchRule extends BaseEntity<DispatchRule> {

    public static final DispatchRule EMPTY = new DispatchRule();

    private String platform;

    private Integer platformType;

    private String process;

    private Integer processType;

    private String businessName;

    private String dsl;

    private List<String> appParameters = new ArrayList<>();

    private Map<String,String> environmentVariables = new HashMap<>();

    private Map<String,String> properties = new HashMap<>();

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String appId;

    private String userId;
}
