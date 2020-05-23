package com.github.binmagic.saas.velen.config.dto;

import com.github.binmagic.saas.velen.config.entity.Dashboard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
