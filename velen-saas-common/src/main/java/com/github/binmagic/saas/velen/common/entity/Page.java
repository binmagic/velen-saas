package com.github.binmagic.saas.velen.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page
{
	private Page()
	{
	}

	@Data
	public static class Query
	{

		private int page = 1;

		private int limit = 15;

		private Map<String, String> params = new HashMap<>();

		private Map<String, String> sorts = new HashMap<>();
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Result<T>
	{
		private long total = 0;

		private List<T> items = new ArrayList<>();
	}


}
