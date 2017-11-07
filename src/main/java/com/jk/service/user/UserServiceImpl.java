package com.jk.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.controller.user.UserController;
import com.jk.mapper.user.UserMapper;
import com.jk.model.tree.PowerTreeBean;
import com.jk.model.user.DeptBean;
import com.jk.model.user.FileBean;
import com.jk.model.user.LoginHistoryBean;
import com.jk.model.user.MenuBean;
import com.jk.model.user.RoleBean;
import com.jk.model.user.RolePowerBean;
import com.jk.model.user.UserBean;
import com.jk.utils.StringUtil;

@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean saveFile(FileBean fileBean) {
		try {
			userMapper.saveFile(fileBean);
			return true;
		} catch (Exception e) {
			logger.error("保存上传文件信息错误={}",e);
			return false;
		}
	}
	@Override
	public boolean saveFolder(FileBean fileBean) {
		try {
			fileBean.setId(StringUtil.getUuid());
			userMapper.saveFolder(fileBean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public List<UserBean> getUserListByIds(String ids) {
		String[] id = ids.split(",");
		return userMapper.getUserListByIds(id);
	}
	@Override
	public List<FileBean> getFileList(String id) {
		return userMapper.getFileList(id);
	}
	@Override
	public void addBatch(List<UserBean> userList) {
		userMapper.addBatch(userList);
	}
	
	@Override
	public List<DeptBean> getDeptList() {
		return userMapper.getDeptListAll();
	}
	
	@Override
	public List<UserBean> getUserBakList(UserBean userBean) {
		return userMapper.getUserBakList(userBean);
	}
	
	@Override
	public int getUserBakCount(UserBean userBean) {
		return userMapper.getUserBakCount(userBean);
	}
	
	@Override
	public List<MenuBean> getUserMenuPower(Integer id) {
		return userMapper.getUserMenuPower(id);
	}
	
	@Override
	public List<LoginHistoryBean> getMyLoginHistoryList(Integer id) {
		return userMapper.getMyLoginHistoryList(id);
	}
	
	@Override
	public List<LoginHistoryBean> getLoginHistoryList() {
		return userMapper.getLoginHistoryList();
	}
	@Override
	public void saveLoginHistory(LoginHistoryBean loginHistoryBean) {
		userMapper.saveLoginHistory(loginHistoryBean);
	}
	
	@Override
	public UserBean getUserInfoByLoginNumber(String loginNumber) {
		return userMapper.getUserInfoByLoginNumber(loginNumber);
	}
	@Override
	public boolean delMenuById(String id) {
		try {
			
			userMapper.delMenuById(id);
			userMapper.delPowerMenuById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean saveMenu(MenuBean menuBean) {
		try {
			userMapper.saveMenu(menuBean);
			HashMap<String, Object> params = new HashMap<String,Object>();
			params.put("id", StringUtil.getUuid());
			params.put("powerId", menuBean.getPowerId());
			params.put("menuId", menuBean.getId());
			userMapper.savePowerMenu(params);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<MenuBean> getPowerMenu(String powerId) {
		return userMapper.getPowerMenu(powerId);
	}
	@Override
	public boolean deletePowerById(String id) {
		try {
			userMapper.deletePowerById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean updatePower(PowerTreeBean powerTreeBean) {
		try {
			userMapper.updatePower(powerTreeBean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean savePower(PowerTreeBean powerTreeBean) {
		try {
			userMapper.savePower(powerTreeBean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<RolePowerBean> getRolePowerByRoleId(String roleId) {
		return userMapper.getRolePowerByRoleId(roleId);
	}
	@Override
	public boolean saveRolePower(String roleId, String ids) {
		try {
			
			//删除角色的所有权限
			userMapper.deleteRolePowerByRoleId(roleId);
			
			String[] powerId = ids.split(",");
			ArrayList<RolePowerBean> rolePowerList = new ArrayList<RolePowerBean>();
			for (int i = 0; i < powerId.length; i++) {
				RolePowerBean rolePowerBean = new RolePowerBean();
				rolePowerBean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				rolePowerBean.setRoleId(roleId);
				rolePowerBean.setPowerId(powerId[i]);
				rolePowerList.add(rolePowerBean);
			}
			userMapper.saveRolePower(rolePowerList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public List<PowerTreeBean> getPowerList(String id) {
		return userMapper.getPowerList(id);
	}
	@Override
	public boolean delRoleByIds(String ids) {
		try {
			String[] id = ids.split(",");
			userMapper.delRoleByIds(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean updateRoleById(RoleBean roleBean) {
		try {
			userMapper.updateRoleById(roleBean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean saveRole(RoleBean roleBean) {
		try {
			userMapper.saveRole(roleBean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public List<RoleBean> getRoleList() {
		return userMapper.getRoleList();
	}
	@Override
	public boolean unlockUserById(String id) {
		try {
			userMapper.unlockUserById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean lockUserById(String id) {
		try {
			userMapper.lockUserById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean updateUserById(UserBean userBean) {
		try {
			userMapper.updateUserById(userBean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public UserBean getUserInfoById(String id) {
		return userMapper.getUserInfoById(id);
	}
	@Override
	public boolean deleteUserByIds(String ids) {
		try {
			String[] id = ids.split(",");
			userMapper.deleteUserByIds(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean saveUser(UserBean userBean) {
		try {
			userMapper.saveUser(userBean);
			HashMap<String, Object> pramas = new HashMap<String,Object>();
			pramas.put("id", StringUtil.getUuid());
			pramas.put("userId", userBean.getId());
			pramas.put("roleId", userBean.getRoleId());
			userMapper.saveUserRole(pramas);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public List<DeptBean> getDeptList(Integer id) {
		return userMapper.getDeptList(id);
	}
	@Override
	public List<UserBean> getUserList(UserBean userBean) {
		return userMapper.getUserList(userBean);
	}
	
	@Override
	public int getUserCount(UserBean userBean) {
		return userMapper.getUserCount(userBean);
	}
	@Override
	public int test() {
		return userMapper.test();
	}
}
