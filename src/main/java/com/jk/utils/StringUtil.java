package com.jk.utils;

import java.util.UUID;

public class StringUtil {

	/**
	 * 
	 * 方法: getUuid <br>
	 * 描述: 生成uuid <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月24日 下午2:06:45
	 * @return
	 */
	public static String getUuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
