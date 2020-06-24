package com.github.binmagic.saas.velen.config.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class Deploy extends ApplicationEvent {

    private String appId;

    private String operator;

    public Deploy( String appId, String operator) {
        super(appId);
        this.appId = appId;
        this.operator = operator;
    }

}
