package com.github.binmagic.saas.velen.config.event;

import com.github.binmagic.saas.velen.config.dto.MetaEventETLDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateMetaEvent extends ApplicationEvent {

    private String appId;

    private String userName;

    public MetaEventETLDTO metaEventETLDTO;

    public CreateMetaEvent(String appId, String userName, MetaEventETLDTO metaEventETLDTO){
        super(metaEventETLDTO);
        this.appId = appId;
        this.userName = userName;
        this.metaEventETLDTO = metaEventETLDTO;
    }

}
