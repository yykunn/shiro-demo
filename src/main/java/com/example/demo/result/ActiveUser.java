package com.example.demo.result;

import java.util.List;
import java.util.Set;

import com.example.demo.pojo.SysPerm;
import com.example.demo.pojo.SysRole;
import com.example.demo.pojo.SysUser;

public class ActiveUser extends UserEditResult{


	private Set<SysPerm> perms;

	public ActiveUser(SysUser user) {
		setId(user.getId());
		setAccount(user.getAccount());
		setPassword(user.getPassword());
		setPhone(user.getPhone());
		setEmail(user.getEmail());
		setNickname(user.getNickname());
	}

	public ActiveUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<SysPerm> getPerms() {
		return perms;
	}

	public void setPerms(Set<SysPerm> perms) {
		this.perms = perms;
	}

}
