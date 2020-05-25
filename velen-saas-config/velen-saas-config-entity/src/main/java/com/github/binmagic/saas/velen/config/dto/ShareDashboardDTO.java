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

    private String name;

    private String type;

    private Integer sort;

    private LocalDateTime createTime;

    private String shareName;

}
