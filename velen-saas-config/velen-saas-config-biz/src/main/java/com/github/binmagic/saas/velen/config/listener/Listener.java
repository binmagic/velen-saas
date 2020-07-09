package com.github.binmagic.saas.velen.config.listener;

import com.github.binmagic.saas.velen.config.entity.KeyRule;
import com.github.binmagic.saas.velen.config.entity.ParserRule;
import com.github.binmagic.saas.velen.config.etl.ProjectApi;
import com.github.binmagic.saas.velen.config.event.DeployApp;
import com.github.binmagic.saas.velen.config.event.SetFieldRule;
import com.github.binmagic.saas.velen.config.event.SetInputParse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;


public class Listener {

    @Component
    public static class DeployListener implements ApplicationListener<DeployApp> {

        @Autowired
        ProjectApi projectApi;

        @Override
        public void onApplicationEvent(DeployApp deployApp) {
            //projectApi.deployApp(deployApp.getAppId(), deployApp.getType(), deployApp.getOperator());
        }
    }

    @Component
    public static class SetInputParseListener implements ApplicationListener<SetInputParse> {

        @Autowired
        ProjectApi projectApi;

        @Override
        public void onApplicationEvent(SetInputParse setInputParse) {
            List<ParserRule> parserRulesDTO = setInputParse.getParserRulesDTO();
            projectApi.setInputParse(setInputParse.getAppId(), ProjectApi.Convert.toParseFormatTDO(parserRulesDTO), setInputParse.getOperator());
        }
    }

    @Component
    public static class SetFieldRuleListener implements ApplicationListener<SetFieldRule> {

        @Autowired
        ProjectApi projectApi;

        @Override
        public void onApplicationEvent(SetFieldRule setFieldRule) {
            List<KeyRule> fieldRulesTDO=setFieldRule.getFieldRulesTDO();
            projectApi.setFieldRule(setFieldRule.getAppId(),ProjectApi.Convert.toFieldRuleTDO(fieldRulesTDO),setFieldRule.getOperator());
        }
    }

}
