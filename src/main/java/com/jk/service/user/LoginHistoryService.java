package com.jk.service.user;

import com.jk.model.user.LoginHistoryBean;

public class LoginHistoryService extends Thread {

	private UserService userService;
	
	private LoginHistoryBean loginHistoryBean;
	
	@Override
	public void run() {
		userService.saveLoginHistory(loginHistoryBean);
	}

	public LoginHistoryService(UserService userService, LoginHistoryBean loginHistoryBean) {
		super();
		this.userService = userService;
		this.loginHistoryBean = loginHistoryBean;
	}

	public LoginHistoryBean getLoginHistoryBean() {
		return loginHistoryBean;
	}

	public void setLoginHistoryBean(LoginHistoryBean loginHistoryBean) {
		this.loginHistoryBean = loginHistoryBean;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public LoginHistoryService() {
		super();
	}
	
}
