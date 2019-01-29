package com.example.demo.result;

import com.example.demo.pojo.SysPerm;

import java.util.List;

public class RoleEditResult {
    private Integer id;
    private String roleName;
    private List<Integer> permIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Integer> getPermIds() {
        return permIds;
    }

    public void setPermIds(List<Integer> permIds) {
        this.permIds = permIds;
    }
}
