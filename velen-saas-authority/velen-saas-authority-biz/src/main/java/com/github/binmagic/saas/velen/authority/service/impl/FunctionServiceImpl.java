package com.github.binmagic.saas.velen.authority.service.impl;

import com.github.binmagic.saas.velen.authority.entity.Function;
import com.github.binmagic.saas.velen.common.entity.Page;
import com.github.binmagic.saas.velen.authority.repository.FunctionRepository;
import com.github.binmagic.saas.velen.authority.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class FunctionServiceImpl implements FunctionService
{

	@Autowired
	FunctionRepository functionRepository;

	@Override
	public Mono<Page.Result<Function>> findPage(Page.Query query)
	{
		List<Sort.Order> orderList = new ArrayList<>(query.getSorts().size());

		for(Map.Entry<String, String> entry : query.getSorts().entrySet())
		{
			orderList.add(new Sort.Order(Sort.Direction.valueOf(entry.getValue()), entry.getKey()));
		}

		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll();

		if(!query.getParams().isEmpty())
		{
			exampleMatcher = ExampleMatcher.matching()
					.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
					.withIgnoreCase(true);
			for(Map.Entry<String, String> entry : query.getParams().entrySet())
			{
				exampleMatcher.withMatcher(entry.getKey(), ExampleMatcher.GenericPropertyMatchers.contains());
			}
		}

		PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getLimit(), Sort.by(orderList));

		Example<Function> example = Example.of(Function.EMPTY, exampleMatcher);

		return Mono.zip(functionRepository.count(example), functionRepository.findAll(example, pageRequest).collectList()
				, Page.Result::new);
	}

	@Override
	public Mono<Function> createFunction(Function function)
	{
		LocalDateTime now = LocalDateTime.now();
		function.setCreateTime(now);
		function.setUpdateTime(now);
		function.setState(Function.STATE_ENABLE);
		return functionRepository.insert (function);
	}

	@Override
	public Mono<String> deleteFunction(String id)
	{
		return functionRepository.deleteById(id).thenReturn(id);
	}

	@Override
	public Mono<Function> updateFunction(Function function)
	{
		function.setUpdateTime(LocalDateTime.now());

		return functionRepository.save(function);
	}

	@Override
	public Flux<Function> getFunctionList(List<String> ids)
	{

		return null;
	}


}
