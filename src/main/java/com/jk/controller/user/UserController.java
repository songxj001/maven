package com.jk.controller.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jk.ConstantsBean;
import com.jk.controller.tree.TreeController;
import com.jk.model.tree.PowerTreeBean;
import com.jk.model.user.DeptBean;
import com.jk.model.user.FileBean;
import com.jk.model.user.LoginHistoryBean;
import com.jk.model.user.MenuBean;
import com.jk.model.user.RoleBean;
import com.jk.model.user.RolePowerBean;
import com.jk.model.user.UserBean;
import com.jk.pool.ThreadPool;
import com.jk.service.user.LoginHistoryService;
import com.jk.service.user.SaveExcelDate;
import com.jk.service.user.UserService;
import com.jk.utils.FileUtil;
import com.jk.utils.IpUtil;
import com.jk.utils.Md5Util;
import com.jk.utils.StringUtil;
import com.jk.utils.TimeUtil;
import com.jk.utils.ZipUtils;
import com.jk.utils.readDat.IPSeeker;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * 方法: upload <br>
	 * 描述: 文件上传 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年11月3日 上午11:04:06
	 * @param file
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="upload.do")
	@ResponseBody
	public boolean upload(@RequestParam MultipartFile file,HttpServletRequest request,String id) throws Exception{
		String originalFilename = file.getOriginalFilename();
		String upFile = FileUtil.upFile(file, ConstantsBean.UPLOAD_FILE_PATH);
		FileBean fileBean = new FileBean();
		fileBean.setId(StringUtil.getUuid());
		fileBean.setFileName(originalFilename);
		fileBean.setHttpPath(ConstantsBean.File_SERVER_PATH+upFile);
		fileBean.setPid(id);
		fileBean.setRealPath(ConstantsBean.UPLOAD_FILE_PATH+"\\"+upFile);
		int lastIndexOf = originalFilename.lastIndexOf(".");
		System.out.println(lastIndexOf);
		fileBean.setType(originalFilename.substring(originalFilename.lastIndexOf(".")+1,originalFilename.length() ));
		UserBean attribute = (UserBean) request.getSession().getAttribute(request.getSession().getId());
		fileBean.setUserId(attribute.getId());
		logger.info("文件上传保存信息="+fileBean);
		return userService.saveFile(fileBean);
	}
	
	/**
	 * 
	 * 方法: saveFolder <br>
	 * 描述: 新建文件夹 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年11月2日 下午3:55:51
	 * @param fileBean
	 * @return
	 */
	@RequestMapping(value="saveFolder")
	@ResponseBody
	public boolean saveFolder(FileBean fileBean){
		logger.info("新建文件夹页面传参="+fileBean.toString());
		return userService.saveFolder(fileBean);
	}
	
	
	/**
	 * 
	 * 方法: downloadWord <br>
	 * 描述: poi导出word <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年11月2日 下午1:39:30
	 * @param ids
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="downloadWord.do")
	public void downloadWord(String ids,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<UserBean> userList = userService.getUserListByIds(ids);
		String templetePath = request.getRealPath("/")+"template\\userTemplete.doc";
		FileInputStream fileInputStream = null;
		OutputStream os = null;
		HashMap<String, String> params = new HashMap<String,String>();
		for (UserBean userBean : userList) {
			fileInputStream = new FileInputStream(templetePath);
			HWPFDocument doc = new HWPFDocument(fileInputStream);
			Range range = doc.getRange();  
			//把range范围内的${reportDate}替换为当前的日期  
			range.replaceText("${id}", userBean.getId().toString());  
			range.replaceText("${name}", userBean.getName());  
			range.replaceText("${loginNumber}", userBean.getLoginNumber());  
			range.replaceText("${sex}", userBean.getSex() == 1 ? "男":"女");  
			range.replaceText("${registerTime}", userBean.getRegisterTime());  
			range.replaceText("${email}", userBean.getEmail());  
			range.replaceText("${showDeptName}", userBean.getShowDeptName());  
			range.replaceText("${status}", userBean.getStatus() == 1 ? "冻结":"正常"); 
			String realPath = userBean.getRealPath();
			String imgStr = FileUtil.getImgStr(realPath);
			range.replaceText("/9j/headImg", imgStr);  
			String uuid = StringUtil.getUuid();
			String path = request.getRealPath("/")+uuid+".doc";
			os = new FileOutputStream(path);  
			params.put(userBean.getName()+userBean.getId()+".doc", path);
			//把doc输出到输出流中  
			doc.write(os);  
			os.flush();
			os.close();
			fileInputStream.close();
		}
		
		ZipUtils.zip(params, "员工信息.zip", request, response);
		
		Collection<String> values = params.values();
		Iterator<String> iterator = values.iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			new File(string).delete();
		}
		
	}
	
	/**
	 * 
	 * 方法: getFileList <br>
	 * 描述: 获取文件列表 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年11月2日 上午9:16:36
	 * @param id
	 * @return
	 */
	@RequestMapping(value="getFileList.do")
	@ResponseBody
	public List<FileBean> getFileList(String id){
		if(StringUtils.isEmpty(id)){
			id = "0";
		}
		List<FileBean> fileList = userService.getFileList(id);
		return fileList;
		
	}
	
	/**
	 * 
	 * 方法: toFilePage <br>
	 * 描述: 跳转资源管理 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年11月1日 下午3:33:08
	 * @return
	 */
	@RequestMapping(value="toFilePage.do")
	public String toFilePage(){
		return "/WEB-INF/user/fileList";
	}
	
	/**
	 * 
	 * 方法: toDruidPage <br>
	 * 描述: 数据库监控 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年11月1日 下午3:31:53
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="toDruid.do")
	public String toDruidPage(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.setAttribute("druidName", ConstantsBean.DRUID_USER_NAME);
		session.setAttribute("druidPwd", ConstantsBean.DRUID_USER_PASSWORD);
		return "/WEB-INF/user/druid";
	}
	
	/**
	 * 
	 * 方法: uploadExcel <br>
	 * 描述: 上传Excel <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年11月1日 下午2:52:32
	 * @param excelfile
	 * @throws Exception
	 */
	@RequestMapping(value="uploadExcel.do")
	public void uploadExcel(@RequestParam MultipartFile excelfile) throws Exception{
		List<DeptBean> deptList = userService.getDeptList();
		InputStream inputStream = excelfile.getInputStream();
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		int numberOfSheets = xssfWorkbook.getNumberOfSheets();
		System.out.println("总共"+numberOfSheets);
		ArrayList<UserBean> userList = new ArrayList<UserBean>();
		for (int i = 0; i < numberOfSheets; i++) {
			XSSFSheet sheetAt = xssfWorkbook.getSheetAt(i);
			int lastRowNum = sheetAt.getLastRowNum();
			for (int j = 3; j <= lastRowNum; j++) {
				UserBean userBean = new UserBean();
				XSSFRow row = sheetAt.getRow(j);
				String loginNumber = row.getCell(0).getStringCellValue();
				userBean.setLoginNumber(loginNumber);
				userBean.setName(row.getCell(1).getStringCellValue());
				String password = row.getCell(2).getCellType() == XSSFCell.CELL_TYPE_NUMERIC ? String.valueOf(row.getCell(2).getNumericCellValue()).replaceAll(".0", "") : row.getCell(2).getStringCellValue();
				userBean.setPassword(Md5Util.getMd532(password));
				userBean.setSex(row.getCell(3).getStringCellValue() == "男" ? 1:0);
				userBean.setEmail(row.getCell(4).getStringCellValue());
				XSSFCell cell = row.getCell(5);
				boolean cellDateFormatted = HSSFDateUtil.isCellDateFormatted(cell);
				userBean.setRegisterTime(cellDateFormatted ? TimeUtil.DatetoString(cell.getDateCellValue(), "yyyy-MM-dd") : row.getCell(5).getStringCellValue());
				String dept = row.getCell(6).getStringCellValue();
				for (DeptBean deptBean : deptList) {
					if (dept.equals(deptBean.getText())) {
						userBean.setDeptId(deptBean.getId());
						break;
					}
				}
				userBean.setStatus(Integer.parseInt(String.valueOf(row.getCell(7).getNumericCellValue()).replaceAll(".0", "")));
				userList.add(userBean);
				if (userList.size() >= 500 || ((i == numberOfSheets-1) && j == lastRowNum )) {
					ThreadPool.execute(new SaveExcelDate(userService, userList));
					userList = new ArrayList<UserBean>();
				}
				
			}
		}
		
	}
	/**
	 * 
	 * 方法: downloadExcel <br>
	 * 描述: 下载模板 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月31日 下午3:16:07
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="downloadExcel.do")
	public void downloadExcel(HttpServletResponse response) throws Exception{
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,7));
		// 产生表格标题行  
        XSSFRow rowm = sheet.createRow(0);
        XSSFCell cellTiltle = rowm.createCell(0);  
        cellTiltle.setCellValue("员工信息表");
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setBoldweight((short)11);
        cellStyle.setFont(font);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellTiltle.setCellStyle(cellStyle );
		XSSFRow row = sheet.createRow(2);
		row.createCell(0).setCellValue("账号");
		row.createCell(1).setCellValue("姓名");
		row.createCell(2).setCellValue("密码");
		row.createCell(3).setCellValue("性别(男/女)");
		row.createCell(4).setCellValue("邮箱");
		row.createCell(5).setCellValue("入职时间");
		row.createCell(6).setCellValue("部门");
		row.createCell(7).setCellValue("状态（0：正常 / 1 ：冻结）");
		OutputStream stream = response.getOutputStream();
		String headStr = "attachment; filename=\"" + "111.xlsx" + "\"";  
        response.setContentType("APPLICATION/OCTET-STREAM");  
        response.setHeader("Content-Disposition", headStr); 
		wb.write(stream  );
		
	}
	
	/**
	 * 
	 * 方法: exportExcel <br>
	 * 描述: 导出 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月31日 下午2:03:28
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="exportExcel.do")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		SXSSFWorkbook workbook = new SXSSFWorkbook(5000);
		
		int rows = 50000;
		int totalPage = 0;
		UserBean userBean = new UserBean();
		int count = userService.getUserBakCount(userBean);
		totalPage = (int) Math.ceil(count/rows) <= 0 ? 1:(int) Math.ceil(count/rows);
		System.out.println("总共"+totalPage+"页");
		ThreadPoolExecutor executor = new ThreadPoolExecutor(totalPage,totalPage, 300, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());  
		for (int i = 1; i <= totalPage; i++) {
			System.out.println("第"+i+"页");
			userBean = new UserBean();
			userBean.setPage(i);
			userBean.setRows(rows);
			userBean.calculate();
			executor.execute(new CreateExcel(userService,workbook,userBean));  
//		    executor.shutdown();//只是不能再提交新任务，等待执行的任务不受影响  
//			executor = ThreadPool.execute2();
		}

        executor.shutdown();//只是不能再提交新任务，等待执行的任务不受影响 
		 boolean loop = true;  
	     do {    //等待所有任务完成  
	         loop = !executor.awaitTermination(2, TimeUnit.SECONDS);  //阻塞，直到线程池里所有任务结束
	     } while(loop);  
		
		System.out.println(request.getRealPath("/"));
		String filePath = request.getRealPath("/")+StringUtil.getUuid()+".xlsx";
		OutputStream out = new FileOutputStream(new File(filePath));
		workbook.write(out);
		out.flush();
		out.close();
		workbook.dispose();
		FileUtil.downloadFile(request, response, filePath, "用户信息表.xlsx");
		new File(filePath).delete();
	}
	
	/**
	 * 
	 * 方法: getMyLoginHistoryList <br>
	 * 描述: 获取我的登陆历史 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月30日 下午1:44:25
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getMyLoginHistoryList.do")
	@ResponseBody
	public List<LoginHistoryBean> getMyLoginHistoryList(HttpServletRequest request){
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute(session.getId());
		return userService.getMyLoginHistoryList(userBean.getId());
	}
	
	/**
	 * 
	 * 方法: toMyLoginHistoryPage <br>
	 * 描述: 跳转我的登陆日志页面 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月26日 下午3:57:07
	 * @return
	 */
	@RequestMapping(value="toMyLoginHistoryPage.do")
	public String toMyLoginHistoryPage(){
		return "/WEB-INF/user/myLoginHistory";
	}
	
	/**
	 * 
	 * 方法: getLoginHistoryList <br>
	 * 描述: 查询全部登陆日志 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月26日 下午3:55:46
	 * @return
	 */
	@RequestMapping(value="getLoginHistoryList.do")
	@ResponseBody
	public List<LoginHistoryBean> getLoginHistoryList(){
		return userService.getLoginHistoryList();
	}
	
	/**
	 * 
	 * 方法: toLoginHistoryPage <br>
	 * 描述: 跳转登陆历史页 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月26日 下午3:35:28
	 * @return
	 */
	@RequestMapping(value="toLoginHistoryPage.do")
	public String toLoginHistoryPage(){
		return "/WEB-INF/user/loginHistory";
	}
	
	/**
	 * 
	 * 方法: toLogin <br>
	 * 描述: 跳转登陆 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月26日 下午3:33:38
	 * @return
	 */
	@RequestMapping("toLogin.do")
	public String toLogin(){
		return "/index";
	}
	
	/**
	 * 
	 * 方法: login <br>
	 * 描述: TODO <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月26日 下午1:47:13
	 * @param userBean
	 * @param request
	 * @return
	 * code 
	 * 	0:成功
	 * 	1:验证码错误
	 *  2：账号不存在
	 *  3：账号冻结
	 *  4：密码错误
	 */
	@RequestMapping(value="login.do")
	@ResponseBody
	public HashMap<String, Object> login(UserBean userBean,HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<String,Object>();
		String checkCode = userBean.getCheckCode().toUpperCase();
		String validateCode = request.getSession().getAttribute("validateCode").toString();
		if (!validateCode.equals(checkCode)) {
			//验证码错误
			result.put("code", 1);
			return result;
		}
		//登陆日志
		LoginHistoryBean loginHistoryBean = new LoginHistoryBean();
		loginHistoryBean.setIpAddr(IpUtil.getIpAddr(request));
		IPSeeker ipSeeker = IPSeeker.getInstance();
        String zore = ipSeeker.getAddress(loginHistoryBean.getIpAddr());
		loginHistoryBean.setIpRealAddr(zore);
		//通过登录账号获取用户信息
		UserBean userInfo = userService.getUserInfoByLoginNumber(userBean.getLoginNumber());
		if (userInfo == null) {
			loginHistoryBean.setStatus(1);
			loginHistoryBean.setNode("登陆账号不存在，登陆账号="+userBean.getLoginNumber());
			//用户不存在
			result.put("code", 2);
		}else{
			Integer status = userInfo.getStatus();
			if (status == 1) {
				//账号被冻结
				result.put("code", 3);
				loginHistoryBean.setStatus(1);
				loginHistoryBean.setNode("账号被冻结");
			}else{
				String password = userInfo.getPassword();
				String md532 = Md5Util.getMd532(userBean.getPassword());
				if (!password.equals(md532)) {
					//密码错误
					result.put("code", 4);
					loginHistoryBean.setStatus(1);
					loginHistoryBean.setNode("密码错误");
				}else{
					result.put("code", 0);
					loginHistoryBean.setStatus(0);
					loginHistoryBean.setUserId(userInfo.getId());
					loginHistoryBean.setNode("登陆成功");
				}
			}
		}
		
		
		HttpSession session = request.getSession();
		session.setAttribute(session.getId(), userInfo);
		session.setAttribute("validateCode", "");
		
		ThreadPool.execute(new LoginHistoryService(userService,loginHistoryBean));
		return result;
		
	}
	
	/**
	 * 
	 * 方法: getUserRoleList <br>
	 * 描述: 角色列表 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月25日 下午3:13:42
	 * @return
	 */
	@RequestMapping(value="getUserRoleList.do")
	@ResponseBody
	public List<RoleBean> getUserRoleList(){
		return userService.getRoleList();
	}
	
	/**
	 * 
	 * 方法: delMenuById <br>
	 * 描述: 删除菜单 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月25日 下午2:54:59
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delMenuById.do")
	@ResponseBody
	public boolean delMenuById(String id){
		return userService.delMenuById(id);
	}
	
	/**
	 * 
	 * 方法: saveMenu <br>
	 * 描述: 保存菜单 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月25日 下午2:53:52
	 * @param menuBean
	 * @return
	 */
	@RequestMapping(value="saveMenu.do")
	@ResponseBody
	public boolean saveMenu(MenuBean menuBean){
		menuBean.setId(StringUtil.getUuid());
		return userService.saveMenu(menuBean);
	}
	
	/**
	 * 
	 * 方法: getPowerMenu <br>
	 * 描述: 查询权限的详细菜单权限 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月25日 下午1:50:23
	 * @param powerId
	 * @return
	 */
	@RequestMapping(value="getPowerMenu.do")
	@ResponseBody
	public List<MenuBean> getPowerMenu(String powerId){
		return userService.getPowerMenu(powerId);
	}
	
	/**
	 * 
	 * 方法: deletePowerById <br>
	 * 描述: 删除权限 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月25日 下午1:49:13
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deletePowerById.do")
	@ResponseBody
	public boolean deletePowerById(String id){
		return userService.deletePowerById(id);
	}
	
	/**
	 * 
	 * 方法: savePower <br>
	 * 描述: 保存或修改权限 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月24日 下午2:15:36
	 * @param powerTreeBean
	 * @return
	 */
	@RequestMapping(value="savePower.do")
	@ResponseBody
	public boolean savePower(PowerTreeBean powerTreeBean){
		if (powerTreeBean != null && StringUtils.isNotEmpty(powerTreeBean.getId())) {
			return userService.updatePower(powerTreeBean);
		}else{
			powerTreeBean.setId(StringUtil.getUuid());
			return userService.savePower(powerTreeBean);
		}
	}
	
	/**
	 * 
	 * 方法: getPowerListAll <br>
	 * 描述: 获取权限树列表（所有，包含根节点） <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月24日 上午10:47:01
	 * @return
	 */
	@RequestMapping(value="getPowerListAll.do")
	@ResponseBody
	public List<PowerTreeBean> getPowerListAll(){
		String id = "0";
		List<PowerTreeBean> treeList = treeNode(id);
		return treeList;
	}
	/**
	 * 
	 * 方法: treeNode <br>
	 * 描述: 递归查询 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月24日 上午10:48:32
	 * @param id
	 * @return
	 */
	private List<PowerTreeBean> treeNode(String id) {
		List<PowerTreeBean> treeList = userService.getPowerList(id);
		for (PowerTreeBean treeBean : treeList) {
			List<PowerTreeBean> treeNode = treeNode(treeBean.getId());
			treeBean.setChildren(treeNode);
		}
		return treeList;
	}
	
	/**
	 * 
	 * 方法: toPowerPage <br>
	 * 描述: 跳转权限列表页面 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月24日 上午10:39:04
	 * @return
	 */
	@RequestMapping(value="toPowerPage.do")
	public ModelAndView toPowerPage(){
		ModelAndView modelAndView = new ModelAndView("/WEB-INF/user/powerList");
		return modelAndView;	
	}
	
	
	/**
	 * 
	 * 方法: getPowerList <br>
	 * 描述: 获取权限列表 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月24日 上午10:36:28
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="getPowerList.do")
	@ResponseBody
	public List<PowerTreeBean> getPowerList(@RequestParam String roleId){
		//查询出角色所拥有的权限，进行回填
		List<RolePowerBean> rolePowerList = userService.getRolePowerByRoleId(roleId);
		String id = "0";
		List<PowerTreeBean> treeList = treeNode(id,rolePowerList);
		return treeList;
	}
	
	/**
	 * 
	 * 方法: saveRolePower <br>
	 * 描述: 保存角色权限 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月24日 上午10:08:40
	 * @param id
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="saveRolePower.do")
	@ResponseBody
	public boolean saveRolePower(String id,String ids){
		return userService.saveRolePower(id,ids);
	}

	/**
	 * 
	 * 方法: treeNode <br>
	 * 描述: 递归查询 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月23日 下午2:55:28
	 * @param id
	 * @return
	 */
	private List<PowerTreeBean> treeNode(String id,List<RolePowerBean> rolePowerList) {
		List<PowerTreeBean> treeList = userService.getPowerList(id);
		for (PowerTreeBean treeBean : treeList) {
			for (RolePowerBean powerTreeList : rolePowerList) {
				if (treeBean.getId().equals(powerTreeList.getPowerId())) {
					treeBean.setChecked(true);
				}
			}
			List<PowerTreeBean> treeNode = treeNode(treeBean.getId(),rolePowerList);
			treeBean.setChildren(treeNode);
		}
		return treeList;
	}
	
	/**
	 * 
	 * 方法: delRoleByIds <br>
	 * 描述: 批量删除角色 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月23日 下午2:47:57
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="delRoleByIds.do")
	@ResponseBody
	public boolean delRoleByIds(@RequestParam String ids){
	return userService.	delRoleByIds(ids);
	}
	
	/**
	 * 
	 * 方法: saveRole <br>
	 * 描述: 保存或修改角色 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月23日 下午2:27:30
	 * @param roleBean
	 * @return
	 */
	@RequestMapping(value="saveRole.do")
	@ResponseBody
	public boolean saveRole(RoleBean roleBean){
		if(roleBean != null && StringUtils.isNotEmpty(roleBean.getId())){
			return userService.updateRoleById(roleBean);
		}else{
			roleBean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			return userService.saveRole(roleBean);
		}
	}
	
	/**
	 * 
	 * 方法: getRoleList <br>
	 * 描述: 获取角色列表 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月23日 下午2:01:41
	 * @return
	 */
	@RequestMapping(value="getRoleList.do")
	@ResponseBody
	public List<RoleBean> getRoleList(){
		return userService.getRoleList();
	}
	
	/**
	 * 
	 * 方法: toRolePage <br>
	 * 描述: 跳转角色列表页 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月23日 上午10:27:40
	 * @return
	 */
	@RequestMapping(value="toRolePage.do")
	public String toRolePage(){
		return "/WEB-INF/user/roleList";
	}
	
	/**
	 * 
	 * 方法: unlockUserById <br>
	 * 描述: 解冻 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月23日 上午10:26:20
	 * @param id
	 * @return
	 */
	@RequestMapping(value="unlockUserById.do",method=RequestMethod.POST)
	@ResponseBody
	public boolean unlockUserById(@RequestParam String id){
		return userService.unlockUserById(id);
	}
	
	/**
	 * 
	 * 方法: getUserInfoById <br>
	 * 描述: 修改回填 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月20日 下午3:17:15
	 * @param id
	 * @return
	 */
	@RequestMapping(value="getUserInfoById.do",method=RequestMethod.POST)
	@ResponseBody
	public UserBean getUserInfoById(@RequestParam String id){
		UserBean userBean =  userService.getUserInfoById(id);
		return userBean;
	}
	
	/**
	 * 
	 * 方法: lockUserById <br>
	 * 描述: 冻结用户 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月20日 下午3:17:08
	 * @param id
	 * @return
	 */
	@RequestMapping(value="lockUserById.do",method=RequestMethod.POST)
	@ResponseBody
	public boolean lockUserById(@RequestParam String id){
		return userService.lockUserById(id);
	}
	
	/**
	 * 
	 * 方法: deleteUserByIds <br>
	 * 描述: 批量删除 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月20日 下午2:10:34
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="deleteUserByIds.do",method=RequestMethod.POST)
	@ResponseBody
	public boolean deleteUserByIds(@RequestParam String ids){
		boolean isOk = userService.deleteUserByIds(ids);
		return isOk;
	}
	
	/**
	 * 
	 * 方法: saveUserForm <br>
	 * 描述: 保存支持批量传参 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月20日 下午1:50:03
	 * @param userBean
	 * @return
	 */
	@RequestMapping(value="saveUserForm.do")
	@ResponseBody
	public boolean saveUserForm(UserBean userBean){
		boolean isOk = false;
		if (userBean != null && userBean.getId() != null) {
			isOk = userService.updateUserById(userBean);
		}else{
			userBean.setPassword(Md5Util.getMd532(userBean.getPassword()));
			isOk = userService.saveUser(userBean);
		}
		return isOk;
	}
	
	/**
	 * 
	 * 方法: fileupload <br>
	 * 描述: 图片上传 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月19日 下午5:26:07
	 * @param file
	 * @return
	 */
	@RequestMapping(value="fileupload.do")
	@ResponseBody
	public String fileupload(@RequestParam MultipartFile file){
		String upFile = FileUtil.upFile(file, ConstantsBean.IMG_PATH);
		return ConstantsBean.IMG_SERVER_PATH+upFile;
	}
	
	/**
	 * 
	 * 方法: getDeptList <br>
	 * 描述: 获取部门字典数据 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月19日 下午5:19:29
	 * @return
	 */
	@RequestMapping("/getDeptList.do")
	@ResponseBody
	public List<DeptBean> getDeptList(){
		//部门的根节点
		Integer id = 0;
		List<DeptBean> treeNode = treeNode(id);
		return treeNode;
	}

	/**
	 * 
	 * 方法: treeNode <br>
	 * 描述: 递归查询 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月19日 下午4:09:31
	 * @param id
	 * @return
	 */
	private List<DeptBean> treeNode(Integer id) {
		List<DeptBean>  deptList = userService.getDeptList(id);
		if (deptList != null && deptList.size() > 0) {
			for (DeptBean deptBean : deptList) {
				List<DeptBean> children = treeNode(deptBean.getId());
				deptBean.setChildren(children);
			}
		}
		return deptList;
	}
	
	/**
	 * 
	 * 方法: toUserAddPage <br>
	 * 描述: 跳转添加页面 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月19日 下午3:25:08
	 * @return
	 */
	@RequestMapping(value="toUserAddPage.do")
	public String toUserAddPage(){
		return "/WEB-INF/user/userAdd";
	}
	
	/**
	 * 
	 * 方法: getUserList <br>
	 * 描述: 分页展示 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月19日 下午2:37:44
	 * @param userBean
	 * @return
	 */
	@RequestMapping(value="getUserList.do")
	@ResponseBody
	public HashMap<String, Object> getUserList(UserBean userBean){
		userBean.calculate();
		HashMap<String, Object> result = new HashMap<String,Object>();
		int count = userService.getUserCount(userBean);
		List<UserBean> userList = userService.getUserList(userBean);
		result.put("total", count);
		result.put("rows", userList);
		return result;
	}
	
	/**
	 * 
	 * 方法: toIndex <br>
	 * 描述: TODO <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月19日 上午10:58:14
	 * @return
	 */
	@RequestMapping(value="toIndex.do",method=RequestMethod.GET)
	public String toIndex(){
		//int count = userService.test();
		return "/WEB-INF/index";
	}
	
	/**
	 * 
	 * 方法: toUserListPage <br>
	 * 描述: 跳转用户列表页 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月19日 下午1:40:01
	 * @return
	 */
	@RequestMapping(value="toUserListPage.do")
	public String toUserListPage(){
		return "/WEB-INF/user/userList";
	}
	
}
