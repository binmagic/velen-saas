package com.github.binmagic.saas.velen.authority.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DeployApp extends ApplicationEvent {

    private String appId;

    private String type;

    private String operator;

    public DeployApp(String appId, String type, String operator) {
        super(appId);
        this.appId = appId;
        this.type = type;
        this.operator = operator;
    }

}
