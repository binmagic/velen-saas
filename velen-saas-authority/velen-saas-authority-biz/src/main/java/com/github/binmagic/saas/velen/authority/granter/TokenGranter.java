package com.github.binmagic.saas.velen.authority.granter;

import com.github.binmagic.saas.velen.authority.dto.LoginParamDTO;
import com.github.binmagic.saas.velen.authority.entity.User;
import reactor.core.publisher.Mono;

public interface TokenGranter {

    /**
     * 获取用户信息
     *
     * @param loginParam 授权参数
     * @return LoginDTO
     */
    Mono<User> grant(LoginParamDTO loginParam);
}