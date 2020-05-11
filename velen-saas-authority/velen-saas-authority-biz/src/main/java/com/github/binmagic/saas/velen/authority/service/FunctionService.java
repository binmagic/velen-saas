package com.github.binmagic.saas.velen.authority.service;


import com.github.binmagic.saas.velen.authority.entity.Function;
import com.github.binmagic.saas.velen.common.entity.Page;
import reactor.core.publisher.Mono;


public interface FunctionService
{
	Mono<Page.Result<Function>> findPage(Page.Query query);

	Mono<Function> createFunction(Function function);

	Mono<String> deleteFunction(String id);

	Mono<Function> updateFunction(Function function);
}
