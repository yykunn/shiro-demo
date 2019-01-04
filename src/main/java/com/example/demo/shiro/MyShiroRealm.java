package com.example.demo.shiro;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.po.ActiveUser;
import com.example.demo.pojo.SysPerm;
import com.example.demo.pojo.SysUser;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;

//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm {

	// 用于用户查询
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	// 角色权限和对应权限添加
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 获取登录用户名
		ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
		// 添加角色和权限
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		if (activeUser == null) {
			return simpleAuthorizationInfo;
		}
		// 添加角色
		simpleAuthorizationInfo
				.addRoles(activeUser.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toList()));
		// 添加权限
		simpleAuthorizationInfo.addStringPermissions(
				activeUser.getPerms().stream().map(perm -> perm.getUri()).filter(uri->uri != null&&!"".equals(uri.trim())).collect(Collectors.toList()));

		return simpleAuthorizationInfo;
	}

	// 用户认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
		if (authenticationToken.getPrincipal() == null) {
			return null;
		}
		// 获取用户信息
		String name = (String) authenticationToken.getPrincipal();
		SysUser user = userService.getUserByAccount(name);
		if (user == null) {
			// 这里返回后会报出对应异常
			return null;
		}
		// 这里验证authenticationToken和simpleAuthenticationInfo的信息
		ActiveUser activeUser = new ActiveUser(user);
		activeUser.setRoles(userService.findUserRoles(user.getId()));
		Set<SysPerm> perms = new HashSet<>();
		activeUser.getRoles().forEach(role -> {
			perms.addAll(roleService.getRolePerms(role.getId()));
		});
		activeUser.setPerms(perms);
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, user.getPassword(),
				ByteSource.Util.bytes(user.getSalt()), getName());
		return simpleAuthenticationInfo;

	}

	/**
	 * 清除缓存 修改权限时，在service层调用，清除登录用户的缓存
	 */
	public void clearCache() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
}