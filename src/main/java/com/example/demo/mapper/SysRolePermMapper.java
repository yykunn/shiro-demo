package com.example.demo.mapper;

import com.example.demo.pojo.SysRolePerm;
import com.example.demo.pojo.SysRolePermExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRolePermMapper {
    int countByExample(SysRolePermExample example);

    int deleteByExample(SysRolePermExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysRolePerm record);

    int insertSelective(SysRolePerm record);

    List<SysRolePerm> selectByExample(SysRolePermExample example);

    SysRolePerm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysRolePerm record, @Param("example") SysRolePermExample example);

    int updateByExample(@Param("record") SysRolePerm record, @Param("example") SysRolePermExample example);

    int updateByPrimaryKeySelective(SysRolePerm record);

    int updateByPrimaryKey(SysRolePerm record);
}