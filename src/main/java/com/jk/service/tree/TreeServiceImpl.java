package com.jk.service.tree;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.mapper.tree.TreeMapper;
import com.jk.model.tree.TreeBean;

@Service
public class TreeServiceImpl implements TreeService {
	@Autowired
	private TreeMapper treeMapper;
	
	@Override
	public List<TreeBean> getNavigationTree(Integer id) {
		return treeMapper.getNavigationTree(id);
	}
}
