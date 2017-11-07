package com.jk.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jk.model.user.MenuBean;
import com.jk.model.user.UserBean;
import com.jk.service.user.UserService;

public class PowerInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		//请求地址
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
			UserBean userBean = (UserBean) session.getAttribute(session.getId());
			List<MenuBean> menuList = userService.getUserMenuPower(userBean.getId());
			
			for (MenuBean menuBean : menuList) {
				String powerUrl = menuBean.getUrl();
				if (powerUrl.indexOf(requestURI) > -1
						|| requestURI.indexOf("toIndex.do") > -1
						|| requestURI.indexOf("Page.do") > -1
						|| requestURI.indexOf("getNavigationTree.do") > -1) {
					return true;
				}
			}
			response.sendRedirect("../noPower.jsp");
			return false;
		}
		
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
