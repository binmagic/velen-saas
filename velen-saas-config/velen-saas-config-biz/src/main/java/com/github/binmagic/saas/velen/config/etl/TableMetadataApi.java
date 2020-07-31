package com.github.binmagic.saas.velen.config.etl;

import com.github.binmagic.saas.velen.config.dto.MetaEventETLDTO;

import com.velen.etl.generator.entity.TableType;
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
            tableMetadataTDO.setTable(TableType.valueOf(0).value());
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
        public static MetaEventETLDTO toMetaEventETLDTO(TableMetadataTDO tableMetadataTDO){
            MetaEventETLDTO metaEventETLDTO = new MetaEventETLDTO();
            metaEventETLDTO.setAppId(tableMetadataTDO.getDb());
            metaEventETLDTO.setName(tableMetadataTDO.getTable());
            for (PropertyMetadataTDO properties :tableMetadataTDO.getProperties()){
                MetaEventETLDTO.MetaEventETLPropDTO  metaEventETLPropDTO = new MetaEventETLDTO.MetaEventETLPropDTO();
                metaEventETLPropDTO.setName(properties.getName());
                metaEventETLPropDTO.setType(properties.getType());
                metaEventETLPropDTO.setComment(properties.getComment());
                metaEventETLPropDTO.setIndex(properties.getIndex());
                metaEventETLDTO.getProps().add(metaEventETLPropDTO);
            }
            return metaEventETLDTO;
        }

    }

}
