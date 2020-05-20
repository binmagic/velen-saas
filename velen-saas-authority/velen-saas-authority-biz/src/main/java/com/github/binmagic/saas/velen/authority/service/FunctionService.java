package com.github.binmagic.saas.velen.authority.service;


import com.github.binmagic.saas.velen.authority.entity.Function;
import com.github.binmagic.saas.velen.common.entity.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface FunctionService
{
	Mono<Page.Result<Function>> findPage(Page.Query query);

	Mono<Function> createFunction(Function function);

	Mono<String> deleteFunction(String id);

	Mono<Function> updateFunction(Function function);

	Flux<Function> getFunctionList(List<String> ids);

}
