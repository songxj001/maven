package com.jk.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Encoder;

/**
 * 
 * <pre>
 * 项目名称：ssh-student    
 * 类名称：FileUtil    
 * 类描述：    文件上传工具类
 * 创建人：Huanglt huanglitai@sina.cn    
 * 创建时间：2016年3月1日 下午4:19:50    
 * 修改人：Huanglt huanglitai@sina.cn     
 * 修改时间：2016年3月1日 下午4:19:50    
 * 修改备注：       
 * &#64;version
 * </pre>
 */
public class FileUtil {
	
	/**
	  * 对字节数组字符串进行Base64解码并生成图片
	  * @param imgStr 图片数据
	  * @param imgFilePath 保存图片全路径地址
	  * @return
	  */
	 public static boolean generateImage(String imgStr,String imgFilePath){
	     //
	     if (imgStr == null) //图像数据为空
	         return false;
	  
	     try 
	     {
	         //Base64解码
	         byte[] b = Base64.decodeBase64(imgStr);
	         for(int i=0;i<b.length;++i)
	         {
	             if(b[i]<0)
	             {//调整异常数据
	                 b[i]+=256;
	             }
	         }
	         //生成jpeg图片
	
	         OutputStream out = new FileOutputStream(imgFilePath);    
	         out.write(b);
	         out.flush();
	         out.close();
	         return true;
	     } 
	     catch (Exception e) 
	     {
	         return false;
	     }
	 }
	 /**
	 * 将图片转换成Base64编码
	 * @param imgFile 待处理图片
	 * @return
	 */
	public static String getImgStr(String imgFile){
	    //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	
	    
	    InputStream in = null;
	    byte[] data = null;
	    //读取图片字节数组
	    try 
	    {
	        in = new FileInputStream(imgFile);        
	        data = new byte[in.available()];
	        in.read(data);
	        in.close();
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	    return new String(Base64.encodeBase64(data));
	}
	
	/**
	 * 
	 * 方法: binaryToBase64Str <br>
	 * 描述: 二进制转换base64字符串 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017-4-8 上午11:39:03
	 * @param bytes
	 * @return
	 */
	public static String binaryToBase64Str(byte[] bytes){
		return new BASE64Encoder().encodeBuffer(bytes).trim();
	}
	
	/**
	 * 
	 * 方法: base64 <br>
	 * 描述: 文件转换二进制 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017-4-8 上午11:13:57
	 * @param img
	 * @return
	 * @throws IOException
	 */
	public static byte[] base64(File img) throws IOException {
		BufferedImage bi = ImageIO.read(img);    
		ByteArrayOutputStream baos = new ByteArrayOutputStream();    
		ImageIO.write(bi, "jpg", baos);    
		byte[] bytes = baos.toByteArray();   
		System.out.println(bytes);
//		String trim = new BASE64Encoder().encodeBuffer(bytes).trim();
		return bytes;
	}
	
	/**
	 * 
	 * 方法: fileUpload <br>
	 * 描述: TODO <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年5月29日 下午9:07:57
	 * @param srcFile
	 * @param srcFileName
	 * @param path
	 * @return
	 */
	public static String fileUpload(File srcFile, String srcFileName, String path) {
		// 文件上传
		FileInputStream is = null;
		FileOutputStream os = null;
		String newFilName = null;
		try {
			// 读取源文件创建读取流
			is = new FileInputStream(srcFile);
			// 创建新文件 创建写入流
			// 后缀
			String hz = srcFileName.substring(srcFileName.lastIndexOf("."));
			newFilName = UUID.randomUUID() + hz;
			// 新文件路径(如果文件夹不存在 则创建)
			File pathFile = new File(path);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			// 创建新的文件
			File newFile = new File(path + "\\" + newFilName);
			// 写入流
			os = new FileOutputStream(newFile);
			// 循环写入
			int b = is.read();
			while (b != -1) {
				os.write(b);
				b = is.read();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {// 如果写入流不为null 则关闭
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {// 如果读取流不为null 则关闭
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newFilName;
	}
	
	public static String upFile(MultipartFile img, String filePath) {

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		FileInputStream is = null;
		BufferedInputStream bis = null;
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String realName = img.getOriginalFilename();
		String type = realName.substring(realName.lastIndexOf("."),realName.length());
		String uuidName = UUID.randomUUID().toString()+type;
		File f = new File(filePath + "/" + uuidName);
		try {
			is = (FileInputStream) img.getInputStream();
			bis = new BufferedInputStream(is);
			fos = new FileOutputStream(f);
			bos = new BufferedOutputStream(fos);
			int bytesRead = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead  = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (null != bos) {
					bos.close();
					bos = null;
				}
				if (null != fos) {
					fos.close();
					fos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}

				if (null != bis) {
					bis.close();
					bis = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return uuidName;
	}
	/**
	 * 文件下载
	 * 
	 * @param response
	 * @param downloadFile
	 */
	public static void downloadFile(HttpServletRequest request,
			HttpServletResponse response, String filePath, String fileName) {
		BufferedInputStream bis = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			File file = new File(filePath);
			is = new FileInputStream(file); // :文件流的声明
			os = response.getOutputStream(); // 重点突出
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(os);

			if (request.getHeader("User-Agent").toLowerCase()
					.indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
			} else {
				// 对文件名进行编码处理中文问题
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
				fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
			}

			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
																// // 重点突出
			// inline在浏览器中直接显示，不提示用户下载
			// attachment弹出对话框，提示用户进行下载保存本地
			// 默认为inline方式
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			// response.setHeader("Content-Disposition",
			// "attachment; filename="+fileName); // 重点突出
			int bytesRead = 0;
			byte[] buffer = new byte[4096];// 4k或者8k
			while ((bytesRead = bis.read(buffer)) != -1) { // 重点
				bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != bis) {
					bis.close();
					bis = null;
				}
				if (null != bos) {
					bos.close();
					bos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
	/**
	 * <pre>
	 * fileDownLoad(文件下载)   
	 * 创建人：Huanglt huanglitai@sina.cn    
	 * 创建时间：2016年3月31日 下午12:39:11    
	 * 修改人：Huanglt huanglitai@sina.cn     
	 * 修改时间：2016年3月31日 下午12:39:11    
	 * 修改备注： 
	 * &#64;param request
	 * &#64;param response
	 * &#64;param fileName
	 * </pre>
	 */
	public static void fileDownLoad(HttpServletRequest request, HttpServletResponse response, String fileName) {
		InputStream is = null;// 文件读取流
		OutputStream os = null;// 文件输出流
		BufferedInputStream bis = null;// 文件读取缓冲流;
		BufferedOutputStream bos = null;// 文件输出缓冲流;
		try {
			is = new FileInputStream(request.getRealPath("upload") + "/" + fileName);// 读取文件
			bis = new BufferedInputStream(is);// 文件读取缓冲流;
			os = response.getOutputStream();// 重点突出输出到浏览器
			bos = new BufferedOutputStream(os);
			// 解决浏览器兼容问题
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
			} else {
				// 对文件名进行编码处理中文问题
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
				fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
			}

			response.reset();
			response.setContentType("application/x-msdownload");/// 不同类型的文件对应不同的MIME类型
			// inline在浏览器中直接显示，不提示用户下载
			// attachment弹出对话框，提示用户进行下载保存本地
			// 默认为inline方式
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			// svn
			//
			byte[] b = new byte[4096];// 缓冲区
			int s = 0;
			while ((s = bis.read(b)) != -1) {
				bos.write(b, 0, s);
				bos.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (bos != null) {
					bos.flush();
					bos.close();
					bos = null;
				}

				if (os != null) {
					os.flush();
					os.close();
					os = null;
				}

				if (bis != null) {
					bis.close();
					bis = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
