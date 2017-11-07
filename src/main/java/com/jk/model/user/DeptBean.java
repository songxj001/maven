package com.jk.model.user;

import java.util.List;

/**
 * 
 * Copyright © 2017 金科教育. All rights reserved. <br>
 * 类: DeptBean <br>
 * 描述: 部门 <br>
 * 作者: Teacher song<br>
 * 时间: 2017年10月19日 下午1:50:53
 */
public class DeptBean {

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 部门名称
	 */
	private String text;
	
	/**
	 * 子节点
	 */
	private List<DeptBean> children;
	
	/**
	 * 父id
	 */
	private Integer pid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<DeptBean> getChildren() {
		return children;
	}

	public void setChildren(List<DeptBean> children) {
		this.children = children;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
}
