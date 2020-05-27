package com.github.binmagic.saas.velen.config.dto;

import com.github.binmagic.saas.velen.config.entity.Dashboard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareDashboardCreateDTO {

    private String id;

    private String userId;

    private String shareUserId;

    private String appId;

    private String dashboardId;

    private String type;

    private Integer sort;

    private LocalDateTime createTime;

    private String userName;

}
