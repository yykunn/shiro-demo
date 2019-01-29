package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.param.RoleInsertParam;
import com.example.demo.param.RoleListParam;
import com.example.demo.result.RoleEditResult;
import com.example.demo.utils.StringUtils;
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
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;

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

	public List<SysRole> listPage(RoleListParam param){
		SysRoleExample example = new SysRoleExample();
		int skip = (param.getPageIndex()-1)*param.getPageSize();
		if(StringUtils.check(param.getRoleName())){
			example.createCriteria().andRoleNameLike(param.getRoleName());
		}
		example.setLimitClause(skip+","+param.getPageSize());
		return roleMapper.selectByExample(example);
	}

	@Transactional
	public void insert(RoleInsertParam param) throws Exception {
		SysRole role = new SysRole();
		role.setRoleName(param.getRoleName());
		roleMapper.insert(role);
		param.getPermIds().forEach(permId -> {
			SysRolePerm rolePerm = new SysRolePerm();
			rolePerm.setPermId(permId);
			rolePerm.setRoleId(role.getId());
			rolePermMapper.insert(rolePerm);
		});
	}

	@Transactional
	public void update(RoleEditResult result) {
		SysRole role = new SysRole();
		role.setRoleName(result.getRoleName());
		role.setId(result.getId());
		roleMapper.updateByPrimaryKeySelective(role);

		// remove old role perms relations
		SysRolePermExample example = new SysRolePermExample();
		example.createCriteria().andRoleIdEqualTo(result.getId());
		rolePermMapper.deleteByExample(example);

		// insert new role perms relations
		result.getPermIds().forEach(permId -> {
			SysRolePerm rolePerm = new SysRolePerm();
			rolePerm.setRoleId(result.getId());
			rolePerm.setPermId(permId);
			rolePermMapper.insertSelective(rolePerm);
		});
	}

	@Transactional
	public void delete(Integer roleId) {
		SysRolePermExample example = new SysRolePermExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		rolePermMapper.deleteByExample(example);
		roleMapper.deleteByPrimaryKey(roleId);
	}

	public List<SysPerm> findRolePerms(int roleId) {
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

	public RoleEditResult getById(Integer roleId) {
		RoleEditResult result = new RoleEditResult();
		SysRole role = roleMapper.selectByPrimaryKey(roleId);
		result.setId(roleId);
		result.setRoleName(role.getRoleName());
		SysRolePermExample example = new SysRolePermExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		List<Integer> perms = rolePermMapper.selectByExample(example).stream().
				map(sysRolePerm -> sysRolePerm.getPermId()).collect(Collectors.toList());
		result.setPermIds(perms);
		return  result;
	}
}
