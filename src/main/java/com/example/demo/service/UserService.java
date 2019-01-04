package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.SysRoleMapper;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.mapper.SysUserRoleMapper;
import com.example.demo.pojo.SysRole;
import com.example.demo.pojo.SysUser;
import com.example.demo.pojo.SysUserExample;
import com.example.demo.pojo.SysUserRole;
import com.example.demo.pojo.SysUserRoleExample;
import com.example.demo.utils.PasswordHelper;

@Service
public class UserService {
	@Autowired
	SysUserMapper userMapper;

	@Autowired
	SysRoleMapper roleMapper;

	@Autowired
	SysUserRoleMapper userRoleMapper;
	
	public List<SysUser> listAll(){
		return userMapper.selectByExample(new SysUserExample());
	}

	public SysUser getUserById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public SysUser getUserByAccount(String account) {
		SysUserExample example = new SysUserExample();
		example.createCriteria().andAccountEqualTo(account);
		List<SysUser> users = userMapper.selectByExample(example);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public void insert(SysUser user) {
		user.setId(null);
		PasswordHelper.encryptPassword(user);
		userMapper.insert(user);
	}

	public void update(SysUser user) {
		userMapper.updateByPrimaryKeySelective(user);
	}

	public void delete(String account) {
		SysUserExample example = new SysUserExample();
		example.createCriteria().andAccountEqualTo(account);
		userMapper.deleteByExample(example);
	}

	/**
	 * 查询用户的角色
	 * 
	 * @param uid
	 * @return
	 */
	public List<SysRole> findUserRoles(int uid) {
		List<SysRole> roles = new ArrayList<>();
		SysUserRoleExample example = new SysUserRoleExample();
		example.createCriteria().andUserIdEqualTo(uid);
		for (SysUserRole userRole : userRoleMapper.selectByExample(example)) {
			roles.add(roleMapper.selectByPrimaryKey(userRole.getRoleId()));
		}
		return roles;
	}

}
