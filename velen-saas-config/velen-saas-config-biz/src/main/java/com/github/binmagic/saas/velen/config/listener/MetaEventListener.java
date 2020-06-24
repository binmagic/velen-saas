package com.github.binmagic.saas.velen.config.listener;

import com.github.binmagic.saas.velen.config.dto.MetaEventETLDTO;
import com.github.binmagic.saas.velen.config.etl.ProfileTableApi;
import com.github.binmagic.saas.velen.config.event.CreateMetaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MetaEventListener implements ApplicationListener<CreateMetaEvent> {


    @Autowired
    ProfileTableApi profileTableApi;

    @Override
    public void onApplicationEvent(CreateMetaEvent createMetaEvent) {

        MetaEventETLDTO metaEventETLDTO = createMetaEvent.getMetaEventETLDTO();

        profileTableApi.create(createMetaEvent.getAppId(), ProfileTableApi.Convert.toEventTableDTO(metaEventETLDTO), "CREATE");
    }
}
