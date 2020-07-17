package com.github.binmagic.saas.velen.config.event;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateDashboard extends ApplicationEvent {

    private String appId;

    private String operator;

    public CreateDashboard(String appId,String operator) {
        super(appId);
        this.appId=appId;
        this.operator=operator;
    }
}
