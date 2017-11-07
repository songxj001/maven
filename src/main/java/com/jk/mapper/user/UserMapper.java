package com.jk.mapper.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jk.model.tree.PowerTreeBean;
import com.jk.model.user.DeptBean;
import com.jk.model.user.FileBean;
import com.jk.model.user.LoginHistoryBean;
import com.jk.model.user.MenuBean;
import com.jk.model.user.RoleBean;
import com.jk.model.user.RolePowerBean;
import com.jk.model.user.UserBean;

public interface UserMapper {

	int test();

	int getUserCount(UserBean userBean);

	List<UserBean> getUserList(UserBean userBean);

	List<DeptBean> getDeptList(Integer id);

	void saveUser(UserBean userBean);

	void deleteUserByIds(String[] id);

	UserBean getUserInfoById(String id);

	void updateUserById(UserBean userBean);

	void lockUserById(String id);

	void unlockUserById(String id);

	List<RoleBean> getRoleList();

	void saveRole(RoleBean roleBean);

	void updateRoleById(RoleBean roleBean);

	void delRoleByIds(String[] id);

	List<PowerTreeBean> getPowerList(String id);

	void saveRolePower(ArrayList<RolePowerBean> rlePowerList);

	List<RolePowerBean> getRolePowerByRoleId(String roleId);

	void deleteRolePowerByRoleId(String roleId);

	void savePower(PowerTreeBean powerTreeBean);

	void updatePower(PowerTreeBean powerTreeBean);

	void deletePowerById(String id);

	List<MenuBean> getPowerMenu(String powerId);

	void saveMenu(MenuBean menuBean);

	void savePowerMenu(HashMap<String, Object> params);

	void delMenuById(String id);

	void delPowerMenuById(String id);

	void saveUserRole(HashMap<String, Object> pramas);

	UserBean getUserInfoByLoginNumber(String loginNumber);

	void saveLoginHistory(LoginHistoryBean loginHistoryBean);

	List<LoginHistoryBean> getLoginHistoryList();

	List<LoginHistoryBean> getMyLoginHistoryList(Integer id);

	List<MenuBean> getUserMenuPower(Integer id);

	int getUserBakCount(UserBean userBean);

	List<UserBean> getUserBakList(UserBean userBean);

	List<DeptBean> getDeptListAll();

	void addBatch(List<UserBean> userList);

	List<FileBean> getFileList(String id);

	List<UserBean> getUserListByIds(String[] id);

	void saveFolder(FileBean fileBean);

	void saveFile(FileBean fileBean);
	
}
