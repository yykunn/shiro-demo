package com.example.demo.controller;

import com.example.demo.param.RoleInsertParam;
import com.example.demo.param.RoleListParam;
import com.example.demo.result.HttpResult;
import com.example.demo.result.RoleEditResult;
import com.example.demo.service.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation("查询所有角色列表")
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    public Object listAll(){
        return HttpResult.success(roleService.listAll());
    }

    @ApiOperation("分页查询角色")
    @ApiImplicitParam(name="roleListParam",value = "参数列表",required = true,paramType = "body",dataType = "RoleListParam")
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public Object listPage(@RequestBody RoleListParam roleListParam){
        return HttpResult.success(roleService.listPage(roleListParam));
    }

    @ApiOperation("新增角色")
    @ApiImplicitParam(name = "param",value = "参数",paramType = "body",required = true,dataType = "RoleInsertParam")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Object insert(@RequestBody RoleInsertParam param){
        try {
            roleService.insert(param);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.fail("新增失败");
        }
        return HttpResult.success("新增成功");
    }

    @ApiOperation("编辑角色")
    @ApiImplicitParam(name = "roleId",value = "主键",paramType = "query",dataType = "Integer",required = true)
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public Object edit(Integer roleId){
        return HttpResult.success(roleService.getById(roleId));
    }

    @ApiOperation("删除角色")
    @ApiImplicitParam(name = "roleId",value = "主键",paramType = "query",dataType = "Integer",required = true)
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object delete(Integer roleId){
        roleService.delete(roleId);
        return HttpResult.success();
    }

    @ApiOperation("更新角色")
    @ApiImplicitParam(name = "param",value = "",paramType = "body",dataType = "RoleEditResult",required = true)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object update(@RequestBody RoleEditResult param){
        roleService.update(param);
        return HttpResult.success();
    }
}
