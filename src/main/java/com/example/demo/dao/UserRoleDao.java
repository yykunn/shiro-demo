package com.example.demo.dao;

import com.example.demo.mapper.SysUserRoleMapper;
import com.example.demo.pojo.SysUserRole;
import com.example.demo.pojo.SysUserRoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRoleDao {
    @Autowired
    SysUserRoleMapper userRoleMapper;

    public List<Integer> findUserRoleIds(Integer userId) {
        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return userRoleMapper.selectByExample(example).stream().map(sysUserRole -> sysUserRole.getRoleId()).collect(Collectors.toList());
    }

    public List<Integer> findRoleUserIds(Integer roleId) {
        SysUserRoleExample userRoleExample = new SysUserRoleExample();
        userRoleExample.createCriteria().andRoleIdEqualTo(roleId);
        List<SysUserRole> userRoleList = userRoleMapper.selectByExample(userRoleExample);
        return userRoleList.stream().map(sysUserRole -> sysUserRole.getUserId()).collect(Collectors.toList());
    }

    public List<SysUserRole> findByUserId(Integer userId){
        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return userRoleMapper.selectByExample(example);
    }

    public void delete(Integer id){
        userRoleMapper.deleteByPrimaryKey(id);
    }

    public void deleteByUserId(Integer userId){
        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        userRoleMapper.deleteByExample(example);
    }

    public void insert(SysUserRole sysUserRole){
        userRoleMapper.insert(sysUserRole);
    }
}
