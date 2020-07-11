package com.github.binmagic.saas.velen.authority.etl;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("velen-etl-generator")
public interface TableMetadataApi extends com.velen.etl.generator.api.TableMetadataApi {
}
