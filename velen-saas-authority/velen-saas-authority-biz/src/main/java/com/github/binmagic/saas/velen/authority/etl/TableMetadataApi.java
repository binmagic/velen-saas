package com.github.binmagic.saas.velen.authority.etl;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.ArrayList;
import java.util.List;


@FeignClient("velen-etl-generator")
public interface TableMetadataApi extends com.velen.etl.generator.api.TableMetadataApi {



}
