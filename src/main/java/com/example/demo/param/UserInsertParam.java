package com.example.demo.param;

import com.example.demo.pojo.SysUser;

public class UserInsertParam {
	private String account;

	private String password;

	private String phone;

	private String email;

	private String nickname;

	private String salt;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	public SysUser dBUser() {
		SysUser user = new SysUser();
		user.setAccount(account);
		user.setEmail(email);
		user.setNickname(nickname);
		user.setPassword(password);
		user.setPhone(phone);
		user.setSalt(salt);
		return user;
	}
}