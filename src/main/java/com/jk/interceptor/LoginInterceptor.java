package com.jk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		String requestURI = request.getRequestURI();
		
		if (requestURI.indexOf("toLogin.do") > -1
				|| requestURI.indexOf(".do") <= -1
				|| requestURI.lastIndexOf("login.do") > -1) {
			return true;
		}
		if (session == null 
				|| session.getAttribute(session.getId()) == null) {
			response.sendRedirect("../user/toLogin.do");
			return false;
		}else{
			return true;
		}
		
		
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
