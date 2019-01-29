package com.example.demo.controller;

import com.example.demo.result.HttpResult;
import com.example.demo.pojo.SysPerm;
import com.example.demo.service.PermService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perm")
public class PermController {
    @Autowired
    private PermService permService;

    @ApiOperation(value = "删除权限")
    @ApiImplicitParam(value = "主键",name = "permId",required = true,paramType = "query",dataType = "Integer")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Object delete(Integer permId){
        permService.delete(permId);
        return HttpResult.success();
    }

    @ApiOperation("新增")
    @ApiImplicitParam(name = "perm",value = "gradle:0-游客可访问 1-登录即可访问 2-需要具体uri权限才可访问 id 不传 type:0-菜单 1-按钮 perm:菜单名 url:访问地址",
            required = true,paramType = "body",dataType = "SysPerm")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Object insert(@RequestBody SysPerm perm){
        perm.setId(null);
        permService.insert(perm);
        return HttpResult.success();
    }
    @ApiOperation("查询所有权限")
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    public Object listAll(){
        return permService.listAll();
    }
}
