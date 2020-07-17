package com.github.binmagic.saas.velen.config.etl;

import com.github.binmagic.saas.velen.config.dto.MetaEventETLDTO;

import com.velen.etl.generator.tdo.PropertyMetadataTDO;
import com.velen.etl.generator.tdo.TableMetadataTDO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.ArrayList;
import java.util.List;


@FeignClient("velen-etl-generator")
public interface TableMetadataApi extends com.velen.etl.generator.api.TableMetadataApi {

    class Convert{
        public static TableMetadataTDO toEventMetadataTDO(MetaEventETLDTO metaEventETLDTO){

            TableMetadataTDO tableMetadataTDO=new TableMetadataTDO();
            tableMetadataTDO.setDb(metaEventETLDTO.getAppId());
            tableMetadataTDO.setTable("event");
            List<PropertyMetadataTDO> propertyMetadataTDOS = new ArrayList<>();
            for(MetaEventETLDTO.MetaEventETLPropDTO metaEventETLPropDTO : metaEventETLDTO.getProps())
            {
                PropertyMetadataTDO propertyMetadataTDO = new PropertyMetadataTDO();
                propertyMetadataTDO.setName(metaEventETLPropDTO.getName());
                propertyMetadataTDO.setType(metaEventETLPropDTO.getType());
                propertyMetadataTDO.setIndex(metaEventETLPropDTO.getIndex());
                propertyMetadataTDOS.add(propertyMetadataTDO);
                tableMetadataTDO.setProperties(propertyMetadataTDOS);
            }

            return tableMetadataTDO;
        }
    }

}
