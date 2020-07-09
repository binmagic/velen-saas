package com.github.binmagic.saas.velen.authority.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateApp extends ApplicationEvent {

    private String appId;

    private String topic;

    private Boolean enforced;

    private String operator;

    public CreateApp( String appId, String topic, Boolean enforced, String operator) {
        super(appId);
        this.appId = appId;
        this.topic = topic;
        this.enforced = enforced;
        this.operator = operator;
    }
}
