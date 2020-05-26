package com.github.binmagic.saas.velen.config.dto;

import com.github.binmagic.saas.velen.config.entity.Dashboard;
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
public class GroupDashboardDTO {

    private String id;

    private String name;

    private String userId;

    private Integer sort;

    private String appId;

    private LocalDateTime createTime;

    private List<Dashboard> list;
}
