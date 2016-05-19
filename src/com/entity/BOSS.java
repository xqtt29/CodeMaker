package com.entity;

import java.util.Map;

public class BOSS {
	private String code;
	private String name;
	private Map map;
	
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isNull(){
		boolean flag=false;
		if((this.code==null||"".equals(this.code))&&(this.name==null||"".equals(this.name)))
			flag=true;
		return flag;
	}
}
