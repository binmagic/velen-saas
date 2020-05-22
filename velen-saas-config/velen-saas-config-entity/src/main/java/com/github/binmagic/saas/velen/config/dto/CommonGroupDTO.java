package com.github.binmagic.saas.velen.config.dto;

import com.github.binmagic.saas.velen.config.entity.CommonDashboard;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommonGroupDTO {

    private String id;

    private String name;

    private Integer isParent;

    private String appId;

    private String userId;

    private Integer sort;

    @CreatedDate
    private LocalDateTime createTime;

    private List<CommonDashboard> list;
}
