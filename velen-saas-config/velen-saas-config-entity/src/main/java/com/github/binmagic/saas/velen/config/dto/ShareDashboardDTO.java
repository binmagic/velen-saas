package com.github.binmagic.saas.velen.config.dto;

import com.github.binmagic.saas.velen.config.entity.Dashboard;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
public class ShareDashboardDTO {

    private String id;

    private String userId;

    private String shareUserId;

    private String appId;

    private Dashboard dashboard;

    private String groupId;

    private Integer sort;

    @CreatedDate
    private LocalDateTime createTime;

}
