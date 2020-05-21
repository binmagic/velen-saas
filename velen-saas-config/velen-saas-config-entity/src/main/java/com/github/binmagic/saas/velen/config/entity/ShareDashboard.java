package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class ShareDashboard extends BaseEntity<ShareDashboard> {
    private String userId;

    private String shareUserId;

    private String dashboardId;

    private String groupId;
}
