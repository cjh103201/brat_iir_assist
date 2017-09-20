package com.iirtech.brats.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	
	@RequestMapping(value="text.action", method = RequestMethod.GET, produces = "applications/json;charset=utf-8")
	@ResponseBody
	public String textConcordance(HttpServletResponse resp, String text) {
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		result = concordanceService.textConcordance(text);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(result);
		
		resp.setContentType("application/json;charset=utf-8");
		
		return json;
	}
	
	@RequestMapping(value="entity.action", method = RequestMethod.GET, produces = "applications/json;charset=utf-8")
	@ResponseBody
	public String entityConcordance(HttpServletResponse resp, String type, String text) {
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		result = concordanceService.entityConcordance(type, text);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(result);
		
		resp.setContentType("application/json;charset=utf-8");
		
		return json;
	}

	@RequestMapping(value="event.action", method = RequestMethod.GET, produces = "applications/json;charset=utf-8")
	@ResponseBody
	public String eventConcordance(HttpServletResponse resp, String type, String text) {
		System.out.println(type);
		System.out.println(text);
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		result = concordanceService.eventConcordance(type, text);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(result);
		
		resp.setContentType("application/json;charset=utf-8");
		
		return json;
	}

}