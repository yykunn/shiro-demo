package com.example.demo.po;
/**
 * 权限等级 
 * @author admin
 *
 */
public enum PermGrade {
	OPEN(0), LOGIN(1), AUTH(2);
	private int grade;

	PermGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
