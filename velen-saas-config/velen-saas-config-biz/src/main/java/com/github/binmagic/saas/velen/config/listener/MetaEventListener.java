package com.github.binmagic.saas.velen.config.listener;

import com.github.binmagic.saas.velen.config.dto.MetaEventETLDTO;
import com.github.binmagic.saas.velen.config.etl.ProfileTableApi;
import com.github.binmagic.saas.velen.config.etl.TableMetadataApi;
import com.github.binmagic.saas.velen.config.event.CreateMetaEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MetaEventListener implements ApplicationListener<CreateMetaEvent> {


    @Autowired
    ProfileTableApi profileTableApi;

    @Autowired
    TableMetadataApi tableMetadataApi;

    @Override
    public void onApplicationEvent(CreateMetaEvent createMetaEvent) {

        MetaEventETLDTO metaEventETLDTO = createMetaEvent.getMetaEventETLDTO();

        tableMetadataApi.createMeta(createMetaEvent.getAppId(),createMetaEvent.getUserName(),
                TableMetadataApi.Convert.toEventMetadataTDO(metaEventETLDTO));

        //profileTableApi.create(createMetaEvent.getAppId(), ProfileTableApi.Convert.toEventTableDTO(metaEventETLDTO), "CREATE");
    }
}
