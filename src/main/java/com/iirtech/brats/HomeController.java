package com.iirtech.brats;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iirtech.brats.model.service.CountingService;

@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("countingService")
	private CountingService countingService;
	
	@RequestMapping(value = { "/", "home.action"}, method = RequestMethod.GET)
	public String index(Locale locale, Model model, HttpServletRequest request, HttpSession session) {		
		
		if(request.getSession().getAttribute("userType") == null) {
			return "index";
		} else {
			
			int totalDocNo = countingService.getTotalDocNo();
			int notDocNo = countingService.getNotDocNo();
			int eventNo = countingService.getEventNo();
			int distinctEventNo = countingService.getDistinctEventNo();
			int entityNo = countingService.getEntityNo();
			
			session.setAttribute("totalDocNo", totalDocNo	);
			session.setAttribute("notDocNo", notDocNo	);
			session.setAttribute("eventNo",	eventNo);
			session.setAttribute("distinctEventNo", distinctEventNo	);
			session.setAttribute("entityNo",	entityNo);
			
			return "home";
		}
	}
}
