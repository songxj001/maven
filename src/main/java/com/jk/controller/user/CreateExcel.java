package com.jk.controller.user;

import com.jk.model.user.UserBean;
import com.jk.service.user.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;

public class CreateExcel implements Runnable {

	private UserService userService;
	
	private SXSSFWorkbook workbook;
	
	private UserBean userBean;
	
	public void run() {
		excel();
	}

	private void excel() {
			System.out.println(Thread.currentThread().getName());
			synchronized (workbook) {
			List<UserBean> userBeanList = userService.getUserBakList(userBean);
			System.out.println(Thread.currentThread().getName()+"查完数据了");
			if (userBeanList.size() > 0) {
				Sheet sheet = workbook.createSheet("sheet"+Thread.currentThread().getId());
				Row row = sheet.createRow(0);  
				Cell cell = row.createCell(0);  
				cell.setCellValue("账号");  
				cell = row.createCell(1);  
				cell.setCellValue("姓名");  
				cell = row.createCell(2);  
				cell.setCellValue("密码");  
				cell = row.createCell(3);  
				cell.setCellValue("性别");  
				cell = row.createCell(4);  
				cell.setCellValue("邮箱");  
				cell = row.createCell(5);  
				cell.setCellValue("注册时间");  
				cell = row.createCell(6);  
				cell.setCellValue("等级");  
				cell = row.createCell(7);  
				cell.setCellValue("部门");  
				int size = userBeanList.size();
				for (int j = 0; j < size; j++) {
					System.out.println(Thread.currentThread().getName()+"第："+j);
					UserBean userBean2 = userBeanList.get(j);
					row = sheet.createRow(j+1);
					row.createCell(0).setCellValue(userBean2.getLoginNumber());
					row.createCell(1).setCellValue(userBean2.getName());
					row.createCell(2).setCellValue(userBean2.getPassword());
					row.createCell(3).setCellValue(userBean2.getSex() == 1 ? "男":"女");
					row.createCell(4).setCellValue(userBean2.getEmail());
					row.createCell(5).setCellValue(userBean2.getRegisterTime());
					Integer ulevel = userBean2.getUlevel();
					row.createCell(6).setCellValue(ulevel <= 8 ? "vip"+ulevel : "svip"+(ulevel-8));
					row.createCell(7).setCellValue(userBean2.getShowDeptName());
				}
			}
		}
	}

	public CreateExcel() {
	}

	public CreateExcel(UserService userService, SXSSFWorkbook workbook, UserBean userBean) {
		super();
		this.userService = userService;
		this.workbook = workbook;
		this.userBean = userBean;
	}
	
}
