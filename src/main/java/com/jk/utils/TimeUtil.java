package com.jk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class TimeUtil {

	/**
	 * 
	 * 方法: DatetoString <br>
	 * 描述: 时间转换字符串 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月31日 下午4:23:56
	 * @param date
	 * @param format
	 * @return
	 */
	public static String DatetoString(Date date,String format){
		SimpleDateFormat simpleDateFormat = StringUtils.isNotEmpty(format) ? new SimpleDateFormat(format) : new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}
}
