package com.github.binmagic.saas.velen.config.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class DashboardCreateDTO
{
    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String type;

    private Integer sort;

    private Integer isPublic;

    private LocalDateTime createTime;

    private String userName;

    private List<String> shareUserId;

    private Map<String, String> items = new HashMap<>();

    private String config;
}
