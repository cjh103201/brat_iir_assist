package com.iirtech.brats.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iirtech.brats.model.service.SearchService;


@Controller
@RequestMapping(value="/concordance")
public class SearchController {

	@Autowired
	@Qualifier("searchService")
	private SearchService searchService;
	
	@RequestMapping(value="search.action", method = RequestMethod.GET)
	public String search() {
		return "concordance/search";
	}
}

