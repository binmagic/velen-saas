package com.github.binmagic.saas.velen.config.event;

import com.github.binmagic.saas.velen.config.dto.ParserRuleDTO;
import com.github.binmagic.saas.velen.config.entity.ParserRule;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class SetInputParse extends ApplicationEvent {

    private String appId;

    private List<ParserRule> parserRulesDTO;

    private String operator;

    public SetInputParse( String appId, List<ParserRule> parserRulesDTO, String operator) {
        super(appId);
        this.appId = appId;
        this.parserRulesDTO = parserRulesDTO;
        this.operator = operator;
    }
}
