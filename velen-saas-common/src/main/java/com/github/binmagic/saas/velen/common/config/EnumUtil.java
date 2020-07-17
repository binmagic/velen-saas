package com.github.binmagic.saas.velen.common.config;

import com.velen.etl.ResultCode;
import org.springframework.context.annotation.Bean;

public class EnumUtil {

    @Bean
    public static boolean isInResultCode(int key){
        boolean inResultCode = false;
        for (ResultCode resultCode : ResultCode.values()){
            if (resultCode.code() == key){
                inResultCode = true;
                break;
            }
        }
        return inResultCode;
    }

}
