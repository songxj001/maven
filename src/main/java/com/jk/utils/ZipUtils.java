/** 
 * <pre>项目名称:springMVN 
 * 文件名称:ZipUtils.java 
 * 包名:com.jk.user.untils 
 * 创建日期:2017年7月24日下午3:27:39 
 * Copyright (c) 2017, guansipeng 807465327@qq.com  All Rights Reserved.</pre> 
 */  
package com.jk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Copyright © 2017 金科教育. All rights reserved. <br>
 * 类: ZipUtils <br>
 * 描述: TODO <br>
 * 作者: Teacher song<br>
 * 时间: 2017年11月2日 上午10:57:49
 */
public class ZipUtils {

	/**
	 * 
	 * 方法: zip <br>
	 * 描述: TODO <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年11月2日 上午11:05:38
	 * @param fileHash
	 * @param zipName
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void zip(HashMap<String, String> fileHash,String zipName,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取项目真实物理路径
		String realPath = request.getRealPath("/");
		//压缩文件真实物理位置
		String zipPath = realPath+"\\"+zipName;
		//创建压缩文件输出流
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(new File(zipPath)));  
		//文件字节缓冲流
		byte[] buf = new byte[1024]; 
		
		Set<String> keySet = fileHash.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			File file = new File(fileHash.get(key));
			//获取单个文件的输入流
			FileInputStream fileInputStream = new FileInputStream(file);
			//压缩文件里的单个文件名称
			zipOutputStream.putNextEntry(new ZipEntry(key));
			//读取单个文件流 写入到压缩流里边
			int len;  
            while ((len = fileInputStream.read(buf)) > 0) {
            	  zipOutputStream.write(buf, 0, len);  
            }  
            //关流
            zipOutputStream.closeEntry();  
            fileInputStream.close();  
			
		}
		//关流
		zipOutputStream.flush();
		zipOutputStream.close();
		FileUtil.downloadFile(request, response, zipPath, new File(zipPath).getName());
		new File(zipPath).delete();
	}
	
	/**
	 * 
	 * 方法: zip <br>
	 * 描述: TODO <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年11月2日 上午10:57:54
	 * @param filePath
	 * @param zipName
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void zip(List<File> filePath,String zipName,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取项目真实物理路径
		String realPath = request.getRealPath("/");
		//压缩文件真实物理位置
		String zipPath = realPath+"\\"+zipName;
		//创建压缩文件输出流
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(new File(zipPath)));  
		//文件字节缓冲流
		byte[] buf = new byte[1024]; 
		//循环压缩文件到一个压缩文件里
		for (int i = 0; i < filePath.size(); i++) {
			//获取需要压缩的单个文件
			File file = filePath.get(i);
			//获取单个文件的输入流
			FileInputStream fileInputStream = new FileInputStream(file);
			//压缩文件里的单个文件名称
			zipOutputStream.putNextEntry(new ZipEntry(filePath.get(i).getName()));
			//读取单个文件流 写入到压缩流里边
			int len;  
            while ((len = fileInputStream.read(buf)) > 0) {
            	  zipOutputStream.write(buf, 0, len);  
            }  
            //关流
            zipOutputStream.closeEntry();  
            fileInputStream.close();  
		}
		//关流
		zipOutputStream.flush();
		zipOutputStream.close();
		FileUtil.downloadFile(request, response, zipPath, new File(zipPath).getName());
		new File(zipPath).delete();
	}
}
