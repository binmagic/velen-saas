package com.github.binmagic.saas.velen.config.event;

import com.github.binmagic.saas.velen.config.entity.KeyRule;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class SetFieldRule extends ApplicationEvent {

    private String appId;

    private List<KeyRule> fieldRulesTDO;

    private String operator;

    public SetFieldRule( String appId, List<KeyRule> fieldRulesTDO, String operator) {
        super(appId);
        this.appId = appId;
        this.fieldRulesTDO = fieldRulesTDO;
        this.operator = operator;
    }
}
