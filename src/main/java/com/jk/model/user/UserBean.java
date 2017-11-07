package com.jk.model.user;

import com.jk.model.Page;

/**
 * 
 * Copyright © 2017 金科教育. All rights reserved. <br>
 * 类: UserBean <br>
 * 描述: 用户表 <br>
 * 作者: Teacher song<br>
 * 时间: 2017年10月19日 下午1:43:29
 */
public class UserBean extends Page {

	/**
	 * 主键id
	 */
	private Integer id;
	
	/**
	 * 登陆账号
	 */
	private String loginNumber;
	/**
	 * 昵称
	 */
	private String name;
	
	/**
	 * 密码（md5加密）
	 */
	private String password;
	
	/**
	 * 性别
	 * 1：男
	 * 0：女
	 */
	private Integer sex;
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 头像
	 */
	private String headImg;
	
	/**
	 * 开号时间
	 */
	private String registerTime;
	
	/**
	 * 等级
	 * 1-8 是vip
	 * 9-16 是svip
	 */
	private Integer ulevel;
	
	/**
	 * 部门id
	 */
	private Integer deptId;
	
	/**
	 * 部门名称（页面展示用）
	 */
	private String showDeptName;
	
	/**
	 * 状态
	 * 0：正常
	 * 1：冻结
	 */
	private Integer status;
	
	/**
	 * 角色id
	 */
	private String roleId;
	
	/**
	 * 验证码
	 */
	private String checkCode;
	
	/**
	 * 图片真实物理路径
	 */
	private String realPath;
	
	
	

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginNumber() {
		return loginNumber;
	}

	public void setLoginNumber(String loginNumber) {
		this.loginNumber = loginNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getUlevel() {
		return ulevel;
	}

	public void setUlevel(Integer ulevel) {
		this.ulevel = ulevel;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getShowDeptName() {
		return showDeptName;
	}

	public void setShowDeptName(String showDeptName) {
		this.showDeptName = showDeptName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", loginNumber=" + loginNumber + ", name=" + name + ", password=" + password
				+ ", sex=" + sex + ", email=" + email + ", headImg=" + headImg + ", registerTime=" + registerTime
				+ ", ulevel=" + ulevel + ", deptId=" + deptId + ", showDeptName=" + showDeptName + ", status=" + status
				+ ", roleId=" + roleId + ", checkCode=" + checkCode + ", realPath=" + realPath + "]";
	}
	
	
}
