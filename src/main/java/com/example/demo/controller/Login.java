package com.example.demo.controller;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;
import com.example.demo.vo.LoginVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("login")
public class Login {

	@Autowired
	UserService userService;

	@ApiOperation(value = "test path", notes = "")
	@RequestMapping(value = "/test/{account}", method = { RequestMethod.GET })
	public Object login(@ApiParam(value = "", required = true) @PathVariable("account") int account) {
		return userService.findUserById(account);
	}

	@ApiOperation(value = "login", notes = "")
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public Object login(@ApiParam(value = "", required = false) @Valid @RequestBody(required = false) LoginVo loginVo) {
		// 添加用户认证信息
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginVo.getAccount(),
				loginVo.getPassword());
		// 进行验证，这里可以捕获异常，然后返回对应信息
		subject.login(usernamePasswordToken);
		return "login success:" + loginVo.getAccount() + " " + loginVo.getPassword();
	}

	// 登出
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "logout";
	}

	// 错误页面展示
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated() /** 登录认证是否成功(敏感信息) **/
		/* && !subject.isRemembered() */ /** 部分认证信息是否成功(非敏感信息) **/
		) {
			return "您未登陆";
		}
		return "ok";
	}

	// 错误页面展示
	@RequestMapping(value = "/refuse", method = RequestMethod.GET)
	public String refuse() {
		return "您无权访问该链接";
	}

	// 游客展示
	@RequestMapping(value = "/introduction", method = RequestMethod.GET)
	public String introduction() {
		return "welcome";
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public Object userInfo() {
		Subject subject = SecurityUtils.getSubject();
		return subject.getPrincipal();
	}
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public Object userList() {
		return "uselist";
	}
}
