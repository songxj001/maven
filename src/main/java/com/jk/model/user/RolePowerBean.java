package com.jk.model.user;

/**
 * 
 * Copyright © 2017 金科教育. All rights reserved. <br>
 * 类: RolePoweBean <br>
 * 描述: 角色权限关联表 <br>
 * 作者: Teacher song<br>
 * 时间: 2017年10月23日 下午3:12:19
 */
public class RolePowerBean {

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 权限id
	 */
	private String powerId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPowerId() {
		return powerId;
	}

	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}
	
	
}
