package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareDashboard extends BaseEntity<ShareDashboard> {

    private String userId;

    private String shareUserId;

    private String appId;

    private String dashboardId;

    private String type;

    private Integer sort;

    private String userName;

    @CreatedDate
    private LocalDateTime createTime;
}
