package com.jk.service.user;

import java.util.List;

import com.jk.model.user.UserBean;

public class SaveExcelDate implements Runnable {

	private UserService userService;
	
	private List<UserBean> userList;
	
	@Override
	public void run() {
		userService.addBatch(userList);
	}

	public SaveExcelDate() {
	}

	public SaveExcelDate(UserService userService, List<UserBean> userList) {
		super();
		this.userService = userService;
		this.userList = userList;
	}
}
