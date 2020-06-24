package com.github.binmagic.saas.velen.authority.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateApp extends ApplicationEvent {

    private String appId;

    private Boolean enforced;

    private String operator;

    public CreateApp( String appId, Boolean enforced, String operator) {
        super(appId);
        this.appId = appId;
        this.enforced = enforced;
        this.operator = operator;
    }
}
