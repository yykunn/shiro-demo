package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.SysPermMapper;
import com.example.demo.po.PermGrade;
import com.example.demo.pojo.SysPerm;
import com.example.demo.pojo.SysPermExample;

@Service
public class PermService {
	@Autowired
	SysPermMapper permMapper;

	public List<SysPerm> listPerms(PermGrade grade) {
		SysPermExample example = new SysPermExample();
		example.createCriteria().andGradeEqualTo(grade.getGrade());
		return permMapper.selectByExample(example);
	}
}
