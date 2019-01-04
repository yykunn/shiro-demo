package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.SysPermMapper;
import com.example.demo.mapper.SysRoleMapper;
import com.example.demo.mapper.SysRolePermMapper;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.mapper.SysUserRoleMapper;
import com.example.demo.pojo.SysPerm;
import com.example.demo.pojo.SysRole;
import com.example.demo.pojo.SysRolePerm;
import com.example.demo.pojo.SysRolePermExample;
import com.example.demo.pojo.SysUser;
import com.example.demo.pojo.SysUserExample;
import com.example.demo.pojo.SysUserRole;
import com.example.demo.pojo.SysUserRoleExample;

@Service
public class UserService {
	@Autowired
	SysUserMapper userMapper;

	@Autowired
	SysRoleMapper roleMapper;

	@Autowired
	SysPermMapper permMapper;

	@Autowired
	SysUserRoleMapper userRoleMapper;

	@Autowired
	SysRolePermMapper rolePermMapper;

	public SysUser findUserById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public SysUser findUserByAccount(String account) {
		SysUserExample example = new SysUserExample();
		example.createCriteria().andAccountEqualTo(account);
		List<SysUser> users = userMapper.selectByExample(example);
		if(users!=null&&users.size()>0) {
			return users.get(0);
		}
		return null;
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

	public List<SysPerm> getRolePerm(int roleId) {
		List<SysPerm> perms = new ArrayList<>();
		SysRolePermExample exam = new SysRolePermExample();
		exam.createCriteria().andRoleIdEqualTo(roleId);
		List<SysRolePerm> rolePerms = rolePermMapper.selectByExample(exam);
		for (SysRolePerm rolePerm : rolePerms) {
			perms.add(permMapper.selectByPrimaryKey(rolePerm.getPermId()));
		}
		return perms;
	}
}
