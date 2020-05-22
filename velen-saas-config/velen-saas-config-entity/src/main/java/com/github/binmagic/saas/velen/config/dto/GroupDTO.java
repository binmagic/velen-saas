package com.github.binmagic.saas.velen.config.dto;

import com.github.binmagic.saas.velen.config.entity.Dashboard;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GroupDTO {

    private String id;

    private String name;

    private String userId;

    private Integer sort;

    private String appId;

    @CreatedDate
    private LocalDateTime createTime;

    private List<Dashboard> list;
}
