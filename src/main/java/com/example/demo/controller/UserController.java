package com.example.demo.controller;

import com.example.demo.param.UserListParam;
import com.example.demo.pojo.SysUser;
import com.example.demo.result.UserEditResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.result.HttpResult;
import com.example.demo.param.UserInsertParam;
import com.example.demo.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@ApiOperation(value = "分页查询用户列表", notes = "")
	@ApiImplicitParam(name="userListParam",value = "查询参数",dataType = "UserListParam",paramType = "body",required = true)
	@RequestMapping(value = "/listPage", method = RequestMethod.POST)
	public Object listPage(@RequestBody UserListParam userListParam) {
		return HttpResult.success(userService.listPage(userListParam));
	}

	@ApiOperation(value = "新增用户")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Object insert(@ApiParam(value = "", required = true) @RequestBody(required = true) UserInsertParam user) {
		try {
			userService.insert(user.dBUser());
		} catch (Exception e) {
			e.printStackTrace();
			return  HttpResult.fail("新增失败");
		}
		return HttpResult.success("插入成功");
	}
	
	@ApiOperation(value = "删除用户", notes = "")
	@ApiImplicitParam (name = "userId", value = "账号", required = true,paramType="query", dataType = "Integer")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Object delete(Integer userId) {
		userService.delete(userId);
		return HttpResult.success("删除成功");
	}

	@ApiOperation(value = "编辑用户")
	@ApiImplicitParam (name = "userId", value = "账号", required = true,paramType="query", dataType = "Integer")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public Object edit(Integer userId) {
		return HttpResult.success(userService.getUserById(userId));
	}

	@ApiOperation(value = "更新用户")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(@ApiParam(value = "前台校验两次密码是否相同", required = true) @RequestBody(required = true) UserEditResult user) {
		try {
			userService.update(user);
		} catch (Exception e) {
			e.printStackTrace();
			return  HttpResult.fail("更新失败");
		}
		return HttpResult.success("更新成功");
	}
}
