package com.jk.model.user;

public class FileBean {

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 物理路径
	 */
	private String realPath;
	/**
	 * http路径
	 */
	private String httpPath;
	
	/**
	 * 文件后缀 没有认为文件夹
	 */
	private String type;
	
	/**
	 * 上传时间
	 */
	private String uploadTime;
	
	/**
	 * pid
	 */
	private String pid;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 用户id
	 */
	private Integer userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getHttpPath() {
		return httpPath;
	}

	public void setHttpPath(String httpPath) {
		this.httpPath = httpPath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "FileBean [id=" + id + ", fileName=" + fileName + ", realPath=" + realPath + ", httpPath=" + httpPath
				+ ", type=" + type + ", uploadTime=" + uploadTime + ", pid=" + pid + ", sort=" + sort + ", userId="
				+ userId + "]";
	}
	
}
