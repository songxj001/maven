package com.jk.mapper.tree;

import java.util.List;

import com.jk.model.tree.TreeBean;

public interface TreeMapper {

	List<TreeBean> getNavigationTree(Integer id);

}
