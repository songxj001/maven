package com.jk.model.tree;

/**
 * 
 * Copyright © 2017 金科教育. All rights reserved. <br>
 * 类: TreeBean <br>
 * 描述: 功能导航树 <br>
 * 作者: Teacher song<br>
 * 时间: 2017年10月19日 上午10:40:54
 */
public class TreeBean {

	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 节点名称
	 */
	private String name;
	/**
	 * 父id
	 */
	private String pId;
	/**
	 * 是否打开
	 */
	private boolean open;
	/**
	 * 跳转地址
	 */
	private String pathUrl;
	
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

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}

}
