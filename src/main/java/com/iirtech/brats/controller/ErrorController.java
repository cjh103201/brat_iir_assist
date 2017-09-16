package com.iirtech.brats.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iirtech.brats.model.service.MentionTypeCheckService;


@Controller
@RequestMapping(value="/error")
public class ErrorController {

	@Autowired
	@Qualifier("mentionTypeCheckService")
	private MentionTypeCheckService mentionTypeCheckService;
	
	@RequestMapping(value="mentionType.action", method = RequestMethod.GET)
	public String findMentionTypeError(HttpSession session) {
		
		String userType = session.getAttribute("userType").toString();
		System.out.println(userType);
		ArrayList<String> folderList = new ArrayList<>();

		folderList = mentionTypeCheckService.getFolderList_IIR("");

		session.setAttribute("folderList", folderList);
		return "error/mentionType";
	}
	
	@RequestMapping(value="folderList.action", method = RequestMethod.GET, produces = "applications/json;charset=utf-8")
	@ResponseBody
	public String getNextFolderList(HttpServletResponse resp, String folderName) {
		
		ArrayList<String> folderList = mentionTypeCheckService.getFolderList_IIR(folderName);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(folderList);
		
		resp.setContentType("application/json;charset=utf-8");
		
		return json;
	}
	
	@RequestMapping(value="fileList.action", method = RequestMethod.GET, produces = "applications/json;charset=utf-8")
	@ResponseBody
	public String getFileList(HttpServletResponse resp, String folderName, String nextPath) {
		
		ArrayList<String> fileList = mentionTypeCheckService.getFileList(folderName, nextPath);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(fileList);
		
		resp.setContentType("application/json;charset=utf-8");
		
		return json;
	}
	
	
}