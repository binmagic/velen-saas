package com.github.binmagic.saas.velen.config.etl;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("velen-etl-dispatcher")
public interface DispatchApi extends com.velen.etl.dispatcher.restful.api.DispatchApi {
}
