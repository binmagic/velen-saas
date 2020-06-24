package com.github.binmagic.saas.velen.authority.listener;

import com.github.binmagic.saas.velen.authority.etl.ProjectApi;
import com.github.binmagic.saas.velen.authority.event.CreateApp;
import com.github.binmagic.saas.velen.authority.event.Deploy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


public class Listener {
    @Component
    @Slf4j
    public static class CreateAppListener implements ApplicationListener<CreateApp> {

        @Autowired
        ProjectApi projectApi;

        @Override
        public void onApplicationEvent(CreateApp createApp) {

            ResponseEntity res = projectApi.create(createApp.getAppId(), createApp.getEnforced(), createApp.getOperator());

            log.info(res.toString());
        }
    }

    @Component
    public static class DeployListener implements ApplicationListener<Deploy> {

        @Autowired
        ProjectApi projectApi;

        @Override
        public void onApplicationEvent(Deploy deploy) {
            projectApi.deploy(deploy.getAppId(), deploy.getOperator());
        }
    }

}
