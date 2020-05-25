package com.github.binmagic.saas.velen.config.dto;


import com.github.binmagic.saas.velen.config.entity.Dashboard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateDTO {

    private String id;

    @NotEmpty
    private String name;

    private String userId;

    private Integer sort;

    private String appId;

    private LocalDateTime createDate;

    private List<Dashboard> dashboards;
}
