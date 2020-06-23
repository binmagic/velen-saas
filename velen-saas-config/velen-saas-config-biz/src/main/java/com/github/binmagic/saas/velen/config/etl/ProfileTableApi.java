package com.github.binmagic.saas.velen.config.etl;

import com.github.binmagic.saas.velen.config.dto.MetaEventETLDTO;
import com.velen.etl.generator.dto.EventTableColumnTDO;
import com.velen.etl.generator.dto.EventTableTDO;
import com.velen.etl.generator.entity.GenerateEnum;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("velen-etl-generator")
public interface ProfileTableApi extends com.velen.etl.generator.api.ProfileTableApi
{
	class Convert
	{
		public static EventTableTDO toEventTableDTO(MetaEventETLDTO metaEventETLDTO)
		{
			EventTableTDO eventTableTDO = new EventTableTDO();

			for(MetaEventETLDTO.MetaEventETLPropDTO metaEventETLPropDTO : metaEventETLDTO.getProps())
			{

				EventTableColumnTDO eventTableColumnTDO = new EventTableColumnTDO();

				eventTableColumnTDO.setComment(metaEventETLPropDTO.getComment());
				eventTableColumnTDO.setName(metaEventETLPropDTO.getName());
				eventTableColumnTDO.setType(GenerateEnum.DataType.valueOf(metaEventETLPropDTO.getType()));
				eventTableTDO.getColumns().add(eventTableColumnTDO);
			}

			return eventTableTDO;
		}
	}
}
