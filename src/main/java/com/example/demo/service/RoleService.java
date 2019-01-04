package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.SysPermMapper;
import com.example.demo.mapper.SysRoleMapper;
import com.example.demo.mapper.SysRolePermMapper;
import com.example.demo.pojo.SysPerm;
import com.example.demo.pojo.SysRole;
import com.example.demo.pojo.SysRoleExample;
import com.example.demo.pojo.SysRolePerm;
import com.example.demo.pojo.SysRolePermExample;
import com.example.demo.pojo.SysRolePermExample.Criteria;

@Service
public class RoleService {
	@Autowired
	SysRoleMapper roleMapper;

	@Autowired
	SysPermMapper permMapper;

	@Autowired
	SysRolePermMapper rolePermMapper;

	public List<SysRole> listAll() {
		return roleMapper.selectByExample(new SysRoleExample());
	}

	public void insert(SysRole role) {
		roleMapper.insert(role);
	}

	public void update(SysRole role) {
		roleMapper.updateByPrimaryKeySelective(role);
	}

	public void delete(SysRole role) {
		roleMapper.deleteByPrimaryKey(role.getId());
	}

	public List<SysPerm> getRolePerms(int roleId) {
		List<SysPerm> perms = new ArrayList<>();
		SysRolePermExample exam = new SysRolePermExample();
		Criteria criteria = exam.createCriteria();
		criteria.andRoleIdEqualTo(roleId);

		List<SysRolePerm> rolePerms = rolePermMapper.selectByExample(exam);
		for (SysRolePerm rolePerm : rolePerms) {
			perms.add(permMapper.selectByPrimaryKey(rolePerm.getPermId()));
		}
		return perms;
	}
}
