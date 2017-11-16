package com.zxy.hibernatelock.pojo;

import java.io.Serializable;

public class Student implements Serializable {

	private static final long serialVersionUID = 4643737159865648585L;
	private Integer id;
	private String name;
	private Integer age;
	private Integer version;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	

}
