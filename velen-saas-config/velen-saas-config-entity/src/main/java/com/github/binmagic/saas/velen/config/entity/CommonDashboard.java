package com.github.binmagic.saas.velen.config.entity;

import com.github.binmagic.saas.velen.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class CommonDashboard extends BaseEntity<CommonDashboard> {

    private String dashboardName;

    private String commonType;

}
