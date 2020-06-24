package com.github.binmagic.saas.velen.authority.etl;


import com.velen.etl.verification.entity.VerifyEnum;
import com.velen.etl.verification.tdo.FieldRuleTDO;
import com.velen.etl.verification.tdo.ParseFormatTDO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.ArrayList;
import java.util.List;

@FeignClient("velen-etl-verification")
public interface ProjectApi extends com.velen.etl.verification.api.ProjectApi {
    class Convert {

    }
}
