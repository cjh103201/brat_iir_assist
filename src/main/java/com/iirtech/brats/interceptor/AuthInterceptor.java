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
		
		boolean redirect = false;
		Object userType = req.getSession().getAttribute("userType");
		
		String url = req.getRequestURI();
		System.out.println(userType);
		System.out.println(url);
		
		if(url.contains("/error") || url.contains("/concordance") ) {
			if(userType == null) {
				redirect = true;
			}
		}

		if(redirect) {
			resp.sendRedirect("/brats/");
		}
		
		return !redirect; //false가 반환되면 요청 처리 중
	}

	
}
