package com.iirtech.brats.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iirtech.brats.model.service.MemberService;


@Controller
@RequestMapping(value="/account")
public class AccountController {

	@Autowired
	@Qualifier("memberService")
	private MemberService memberService;
	
	@RequestMapping(value="login.action", method = RequestMethod.POST)
	public String login(String id, String password, HttpSession session, HttpServletResponse resp) {
		String result = memberService.getLoginInformation(id, password);

		if(result.equals("false")) {
			try {
				resp.setContentType("text/html; charset=utf-8");
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('아이디와 비밀번호를 확인해주세요.');history.go(-1);</script>");
				out.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return "home";
		} else {
			session.setAttribute("userType", result); //0:inner, 1:kisti
			return "redirect:/home.action";
		}
	}
	
	@RequestMapping(value="logout.action", method = RequestMethod.GET)
	public String login(HttpSession session) {
		
		session.removeAttribute("userType");
		
		return "redirect:/home.action";
	}

}

