package com.example.demo.param;

import java.util.List;

public class RoleInsertParam {
    private String roleName;
    private List<Integer> permIds;

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
