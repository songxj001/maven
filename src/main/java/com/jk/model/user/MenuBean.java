package com.jk.model.user;

/**
 * 
 * Copyright © 2017 金科教育. All rights reserved. <br>
 * 类: MenuBean <br>
 * 描述: TODO <br>
 * 作者: Teacher song<br>
 * 时间: 2017年10月25日 下午1:41:08
 */
public class MenuBean {
	
	/**
	 * 主键id
	 */
	private String id;
	
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * url
	 */
	private String url;
	/**
	 * 备注
	 */
	private String node;
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPowerId() {
		return powerId;
	}

	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}
}
