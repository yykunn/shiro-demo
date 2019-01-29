package com.example.demo.service;

import com.example.demo.dao.UserRoleDao;
import com.example.demo.mapper.SysRoleMapper;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.param.UserListParam;
import com.example.demo.pojo.*;
import com.example.demo.result.UserEditResult;
import com.example.demo.result.UserListPageResult;
import com.example.demo.utils.PasswordHelper;
import com.example.demo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    SysUserMapper userMapper;

    @Autowired
    SysRoleMapper roleMapper;

    @Autowired
    UserRoleDao userRoleDao;

    public List<UserListPageResult> listPage(UserListParam userListParam) {
        List<Integer> userIds = null;
        if (userListParam.getRoleId() != null) {
            userIds = userRoleDao.findRoleUserIds(userListParam.getRoleId());
        }
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        if (userIds != null) {
            criteria.andIdIn(userIds);
        }
        if (StringUtils.check(userListParam.getAccount())) {
            criteria.andAccountLike(userListParam.getAccount());
        }
        if (StringUtils.check(userListParam.getNickname())) {
            criteria.andNicknameLike(userListParam.getNickname());
        }
        int skip = (userListParam.getPageIndex() - 1) * userListParam.getPageSize();
        example.setLimitClause(skip + "," + userListParam.getPageSize());

        List<SysUser> users = userMapper.selectByExample(example);
        return users.stream().map(sysUser -> {
            UserListPageResult result = new UserListPageResult();
            result.setId(sysUser.getId());
            result.setAccount(sysUser.getAccount());
            result.setNickname(sysUser.getNickname());
            result.setRoles(findUserRoles(sysUser.getId()));
            return result;
        }).collect(Collectors.toList());
    }

    public UserEditResult getUserById(int id) {
        UserEditResult result = new UserEditResult();
        SysUser sysUser = userMapper.selectByPrimaryKey(id);
        result.setAccount(sysUser.getAccount());
        result.setEmail(sysUser.getEmail());
        result.setId(sysUser.getId());
        result.setNickname(sysUser.getNickname());
        result.setPassword(sysUser.getPassword());
        result.setPhone(sysUser.getPhone());
        result.setSalt(sysUser.getSalt());
        result.setRoles(findUserRoles(sysUser.getId()));
        return result;
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

    public void insert(SysUser user) throws Exception {
        user.setId(null);
        PasswordHelper.encryptPassword(user);
        userMapper.insert(user);
    }

    @Transactional
    public void update(UserEditResult userEditResult) {
        SysUser user = new SysUser();
        user.setPassword(userEditResult.getPassword());
        user.setSalt(userEditResult.getSalt());
        user.setAccount(userEditResult.getAccount());
        user.setEmail(userEditResult.getEmail());
        user.setId(userEditResult.getId());
        user.setNickname(userEditResult.getNickname());
        user.setPhone(userEditResult.getPhone());
        PasswordHelper.encryptPassword(user);
        userMapper.updateByPrimaryKeySelective(user);

        // delete old user roles relations
        userRoleDao.deleteByUserId(user.getId());

        // insert new user roles relations
        List<SysRole> roles = userEditResult.getRoles();
        roles.forEach(sysRole -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(sysRole.getId());
            sysUserRole.setUserId(user.getId());
            userRoleDao.insert(sysUserRole);
        });
    }

    @Transactional
    public void delete(Integer userId) {
        userRoleDao.deleteByUserId(userId);
        userMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 查询用户的角色
     *
     * @param uid
     * @return
     */
    public List<SysRole> findUserRoles(int uid) {
        List<SysRole> roles = new ArrayList<>();
        for (Integer roleId : userRoleDao.findUserRoleIds(uid)) {
            roles.add(roleMapper.selectByPrimaryKey(roleId));
        }
        return roles;
    }

}
