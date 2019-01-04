package com.example.demo.po;

import java.util.List;
import java.util.Set;

import com.example.demo.pojo.SysPerm;
import com.example.demo.pojo.SysRole;
import com.example.demo.pojo.SysUser;

public class ActiveUser {
	private Integer id;

	private String account;

	private String password;

	private String phone;

	private String email;

	private String nickname;

	private List<SysRole> roles;

	private Set<SysPerm> perms;

	public ActiveUser(SysUser user) {
		id = user.getId();
		account = user.getAccount();
		password = user.getPassword();
		phone = user.getPhone();
		email = user.getEmail();
		nickname = user.getNickname();
	}

	public ActiveUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public Set<SysPerm> getPerms() {
		return perms;
	}

	public void setPerms(Set<SysPerm> perms) {
		this.perms = perms;
	}

}
