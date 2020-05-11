package com.github.binmagic.saas.velen.authority.service.impl;

import com.github.binmagic.saas.velen.authority.entity.Menu;
import com.github.binmagic.saas.velen.common.entity.Page;
import com.github.binmagic.saas.velen.authority.repository.MenuRepository;
import com.github.binmagic.saas.velen.authority.service.MenuService;
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
public class MenuServiceImpl implements MenuService
{

	@Autowired
	MenuRepository menuRepository;

	@Override
	public Mono<Page.Result<Menu>> findPage(Page.Query query)
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

		Example<Menu> example = Example.of(Menu.EMPTY, exampleMatcher);

		return Mono.zip(menuRepository.count(example), menuRepository.findAll(example, pageRequest).collectList()
				, Page.Result::new);
	}

	@Override
	public Mono<Menu> createMenu(Menu menu)
	{
		return menuRepository.insert(menu);
	}

	@Override
	public Mono<Menu> updateMenu(Menu menu)
	{
		return menuRepository.save(menu);
	}

	@Override
	public Mono<Void> deleteMenu(String id)
	{
		return menuRepository.deleteById(id);
	}
}
