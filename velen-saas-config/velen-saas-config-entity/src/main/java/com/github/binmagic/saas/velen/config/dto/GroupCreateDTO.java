package com.github.binmagic.saas.velen.config.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateDTO {

    private String id;

    @NotEmpty
    private String name;

    private String userId;

    private Integer sort;

    private String appId;

    private LocalDateTime createDate;
}
