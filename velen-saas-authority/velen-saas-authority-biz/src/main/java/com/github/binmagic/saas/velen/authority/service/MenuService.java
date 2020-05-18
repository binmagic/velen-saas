package com.github.binmagic.saas.velen.authority.service;

import com.github.binmagic.saas.velen.authority.entity.Menu;
import com.github.binmagic.saas.velen.common.entity.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuService
{
	Mono<Page.Result<Menu>> findPage(Page.Query query);

	Mono<Menu> createMenu(Menu menu);

	Mono<Menu> updateMenu(Menu menu);

	Mono<Void> deleteMenu(String id);


}
