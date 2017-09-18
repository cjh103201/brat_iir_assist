package com.iirtech.brats.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iirtech.brats.model.service.ConcordanceService;


@Controller
@RequestMapping(value="/concordance")
public class ConcordanceController {

	@Autowired
	@Qualifier("concordanceService")
	private ConcordanceService concordanceService;
	
	@RequestMapping(value="concordance.action", method = RequestMethod.GET)
	public String search() {
		return "concordance/concordance";
	}
}

