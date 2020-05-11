package com.github.binmagic.saas.velen.authority.service;

import com.github.binmagic.saas.velen.authority.entity.Component;
import com.github.binmagic.saas.velen.common.entity.Page;
import reactor.core.publisher.Mono;

public interface ComponentService
{
	Mono<Page.Result<Component>> findPage(Page.Query query);

	Mono<Component> createComponent(Component component);

	Mono<Component> updateComponent(Component component);

	Mono<Void> deleteComponent(String id);
}
