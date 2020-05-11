package com.github.binmagic.saas.velen.authority.service.impl;

import com.github.binmagic.saas.velen.authority.entity.Component;
import com.github.binmagic.saas.velen.common.entity.Page;
import com.github.binmagic.saas.velen.authority.repository.ComponentRepository;
import com.github.binmagic.saas.velen.authority.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ComponentServiceImpl implements ComponentService
{

	@Autowired
	ComponentRepository componentRepository;


	@Override
	public Mono<Page.Result<Component>> findPage(Page.Query query)
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

		Example<Component> example = Example.of(Component.EMPTY, exampleMatcher);

		return Mono.zip(componentRepository.count(example), componentRepository.findAll(example, pageRequest).collectList()
				, Page.Result::new);
	}

	@Override
	public Mono<Component> createComponent(Component component)
	{
		return componentRepository.insert(component);
	}

	@Override
	public Mono<Component> updateComponent(Component component)
	{
		return componentRepository.save(component);
	}

	@Override
	public Mono<Void> deleteComponent(String id)
	{
		return componentRepository.deleteById(id);
	}

}
