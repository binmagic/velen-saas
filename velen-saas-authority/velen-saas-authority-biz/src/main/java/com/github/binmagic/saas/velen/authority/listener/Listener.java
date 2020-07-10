package com.github.binmagic.saas.velen.authority.listener;

import com.github.binmagic.saas.velen.authority.etl.ProjectApi;
import com.github.binmagic.saas.velen.authority.etl.TableMetadataApi;
import com.github.binmagic.saas.velen.authority.event.CreateApp;
import com.github.binmagic.saas.velen.authority.event.CreateDashboard;
import com.github.binmagic.saas.velen.authority.event.DeployApp;
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

            ResponseEntity res = projectApi.create(createApp.getAppId(), createApp.getTopic(), createApp.getEnforced(), createApp.getOperator());

        }
    }

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
    public static class CreateDashboardListener implements ApplicationListener<CreateDashboard> {

        @Autowired
        TableMetadataApi tableMetadataApi;

        @Override
        public void onApplicationEvent(CreateDashboard createDashboard) {
            tableMetadataApi.createApp(createDashboard.getAppId(), createDashboard.getOperator());
        }
    }


}
