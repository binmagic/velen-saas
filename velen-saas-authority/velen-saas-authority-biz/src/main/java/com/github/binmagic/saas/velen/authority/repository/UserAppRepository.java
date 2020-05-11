package com.github.binmagic.saas.velen.authority.repository;

import com.github.binmagic.saas.velen.authority.entity.UserApp;
import com.github.binmagic.saas.velen.common.component.dao.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserAppRepository extends BaseRepository<UserApp>
{

	Flux<UserApp> findByUserId(String userId);

}
