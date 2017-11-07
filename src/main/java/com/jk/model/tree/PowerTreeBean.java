package com.jk.model.tree;

import java.util.List;

public class PowerTreeBean {

	/**
	 * 主键id
	 */
	private String id;
	
	/**
	 * 节点名称
	 */
	private String text;
	/**
	 * 父id
	 */
	private String pid;
	
	/**
	 * 跳转地址
	 */
	private String url;
	
	private Boolean checked = false;
	
	/**
	 * 子节点
	 */
	private List<PowerTreeBean> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<PowerTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<PowerTreeBean> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
}
