package com.jk.controller.tree;

import com.jk.model.tree.TreeBean;
import com.jk.model.user.UserBean;
import com.jk.service.tree.TreeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/tree")
public class TreeController {
	private static Logger logger = Logger.getLogger(TreeController.class);
	@Autowired
	private TreeService treeService;
	
	/**
	 * 
	 * 方法: getNavigationTree <br>
	 * 描述: 获取功能导航树 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年10月19日 上午11:08:23
	 * @return
	 */
	@RequestMapping(value="getNavigationTree.do",method=RequestMethod.POST)
	@ResponseBody
	public List<TreeBean> getNavigationTree(HttpServletRequest request){
		System.out.println("123");
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute(session.getId());
		logger.info("获取session信息="+userBean.toString());
		List<TreeBean> treeList =  treeService.getNavigationTree(userBean.getId());
		logger.info("获取导航树信息="+treeList.toString());
		return treeList;
	}
}
