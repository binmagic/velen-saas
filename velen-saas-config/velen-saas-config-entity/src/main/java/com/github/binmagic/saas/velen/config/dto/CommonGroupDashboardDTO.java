package com.github.binmagic.saas.velen.config.dto;

import com.github.binmagic.saas.velen.config.entity.CommonDashboard;
import com.github.binmagic.saas.velen.config.entity.CommonGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonGroupDashboardDTO {

    private String id;

    private String name;

    private String parentId;

    private Boolean hasSonGroup;

    private String appId;

    private Integer sort;

    private LocalDateTime createTime;

    private List list;
}
