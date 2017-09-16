package com.iirtech.brats.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object arg2) throws Exception {
		
		String url = req.getRequestURI();
		
		boolean redirect = false;
		if( url.contains("home.action") || url.contains("/")) {
			redirect = false;
		} else {
			if(req.getSession().getAttribute("userType") == null ) {
				redirect = true;
			}
		}
		if(redirect) {
			resp.sendRedirect("/brats/home.action");
		}
		
		return !redirect; //false가 반환되면 요청 처리 중
	}

	
}
