package com.jk.model.user;

import com.jk.model.Page;

/**
 * 
 * Copyright © 2017 金科教育. All rights reserved. <br>
 * 类: RoleBean <br>
 * 描述: TODO <br>
 * 作者: Teacher song<br>
 * 时间: 2017年10月23日 上午10:23:17
 */
public class RoleBean extends Page{

	/**
	 * 主键id
	 */
	private String id;
	
	/**
	 * 角色名称
	 */
	private String name;
	
	/**
	 * 备注
	 */
	private String node;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
}
