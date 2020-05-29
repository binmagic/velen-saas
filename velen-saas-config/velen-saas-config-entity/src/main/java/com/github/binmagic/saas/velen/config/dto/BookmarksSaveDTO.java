package com.github.binmagic.saas.velen.config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarksSaveDTO
{
	private String name;

	private String type;

	private String config;

	private String data;

	private List<String> dashboards = new ArrayList<>();
}
