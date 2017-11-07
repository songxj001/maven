package com.jk.model.user;

import com.jk.model.Page;
import com.jk.utils.StringUtil;

public class LoginHistoryBean extends Page {

	/**
	 * 主键id
	 */
	private String id = StringUtil.getUuid();
	
	/**
	 * 用户id
	 * 默认不存在 -1
	 */
	private Integer userId = -1;
	
	/**
	 * 登陆时间
	 */
	private String loginTime;
	
	/**
	 * 状态
	 * 0：成功
	 * 1：失败
	 */
	private Integer status; 
	
	/**
	 * 登陆ip地址
	 */
	private String ipAddr;
	
	/**
	 * ip对应地址位置
	 */
	private String ipRealAddr;
	
	/**
	 * 备注
	 */
	private String node;
	
	private String userName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getIpRealAddr() {
		return ipRealAddr;
	}

	public void setIpRealAddr(String ipRealAddr) {
		this.ipRealAddr = ipRealAddr;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
