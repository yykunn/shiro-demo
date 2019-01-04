package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.po.HttpResult;
import com.example.demo.po.UserRecord;
import com.example.demo.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@ApiOperation(value = "用户列表", notes = "")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object listAll() {
		return HttpResult.success(userService.listAll());
	}

	@ApiOperation(value = "新增用户")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object insert(@ApiParam(value = "", required = true) @RequestBody(required = true) UserRecord user) {
		userService.insert(user.dBUser());
		return HttpResult.success("插入成功");
	}
	
	@ApiOperation(value = "删除用户", notes = "")
	@ApiImplicitParam (name = "account", value = "账号", required = true,paramType="query", dataType = "String")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Object delete(String account) {
		userService.delete(account);
		return HttpResult.success("删除成功");
	}
}
