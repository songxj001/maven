package com.jk.service.user;

import java.util.List;

import com.jk.model.tree.PowerTreeBean;
import com.jk.model.user.DeptBean;
import com.jk.model.user.FileBean;
import com.jk.model.user.LoginHistoryBean;
import com.jk.model.user.MenuBean;
import com.jk.model.user.RoleBean;
import com.jk.model.user.RolePowerBean;
import com.jk.model.user.UserBean;

public interface UserService {

	int test();

	int getUserCount(UserBean userBean);

	List<UserBean> getUserList(UserBean userBean);

	List<DeptBean> getDeptList(Integer id);

	boolean saveUser(UserBean userBean);

	boolean deleteUserByIds(String ids);

	UserBean getUserInfoById(String id);

	boolean updateUserById(UserBean userBean);

	boolean lockUserById(String id);

	boolean unlockUserById(String id);

	List<RoleBean> getRoleList();

	boolean saveRole(RoleBean roleBean);

	boolean updateRoleById(RoleBean roleBean);

	boolean delRoleByIds(String ids);

	List<PowerTreeBean> getPowerList(String id);

	boolean saveRolePower(String id, String ids);

	List<RolePowerBean> getRolePowerByRoleId(String roleId);

	boolean savePower(PowerTreeBean powerTreeBean);

	boolean updatePower(PowerTreeBean powerTreeBean);

	boolean deletePowerById(String id);

	List<MenuBean> getPowerMenu(String powerId);

	boolean saveMenu(MenuBean menuBean);

	boolean delMenuById(String id);

	UserBean getUserInfoByLoginNumber(String loginNumber);

	void saveLoginHistory(LoginHistoryBean loginHistoryBean);

	List<LoginHistoryBean> getLoginHistoryList();

	List<LoginHistoryBean> getMyLoginHistoryList(Integer id);

	List<MenuBean> getUserMenuPower(Integer id);

	int getUserBakCount(UserBean userBean);

	List<UserBean> getUserBakList(UserBean userBean);

	List<DeptBean> getDeptList();

	void addBatch(List<UserBean> userList);

	List<FileBean> getFileList(String id);

	List<UserBean> getUserListByIds(String ids);

	boolean saveFolder(FileBean fileBean);

	boolean saveFile(FileBean fileBean);

}
