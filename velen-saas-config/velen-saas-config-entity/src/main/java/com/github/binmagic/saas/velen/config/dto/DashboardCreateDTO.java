package com.github.binmagic.saas.velen.config.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class DashboardCreateDTO
{
    private String id;

    @NotEmpty
    private String name;

    private String type;

    private Integer sort;

    private Integer isPublic;

    private LocalDateTime createTime;

    private String userName;
}
