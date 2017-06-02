package com.ge.predix.ccm.entity;

import java.io.Serializable;

public class TbColumn implements Serializable{

	/**
	 */
	private static final long serialVersionUID = 7741092810384555081L;
	private String name; 
	private String length1 ;
	private String length2 ;
	private String type ;
	private String notnull;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLength1() {
		return length1;
	}
	public void setLength1(String length1) {
		this.length1 = length1;
	}
	public String getLength2() {
		return length2;
	}
	public void setLength2(String length2) {
		this.length2 = length2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNotnull() {
		return notnull;
	}
	public void setNotnull(String notnull) {
		this.notnull = notnull;
	}

}
