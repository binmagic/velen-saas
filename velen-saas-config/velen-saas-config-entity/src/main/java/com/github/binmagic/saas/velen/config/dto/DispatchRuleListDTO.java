package com.github.binmagic.saas.velen.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DispatchRuleListDTO {

    private String id;

    private String platform;

    private Integer platformType;

    private String process;

    private Integer processType;

    private String businessName;

    private String dsl;

    private Map<String,String> properties;

    private LocalDateTime createTime;

}