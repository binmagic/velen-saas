package com.github.binmagic.saas.velen.config.repository;

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository;
import com.github.binmagic.saas.velen.config.entity.App;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface AppRepository extends BaseRepository<App>
{
	Flux<App> findByIdInAndState(List<String> ids,Integer state);

	Mono<App> findByName(String name);

	Flux<App> findByExtendIsNull();
}
