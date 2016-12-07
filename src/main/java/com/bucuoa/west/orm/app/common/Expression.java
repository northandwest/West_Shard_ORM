package com.bucuoa.west.orm.app.common;

import com.bucuoa.west.orm.core.uitls.WStringUtils;

public class Expression {

	public Expression(String name, String condition, Object value) {
		this.name = name;
		this.condition = condition;
		this.value = value;
		this.type = ExpressionType.AND.getType();
	}
	
	public Expression(String name, Object value) {
		this.name = name;
		this.condition = "=";
		this.value = value;
		this.type = ExpressionType.AND.getType();
	}

	public Expression(String name, String condition, Object value, String type) {
		this.name = name;
		this.condition = condition;
		this.value = value;
		this.type = type;
	}

	private String name;
	private String condition;
	private Object value;
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		
		return  WStringUtils.join(" "," ",name,condition,String.valueOf(value));
	}
	
	public String toParameterString() {
		
		return WStringUtils.join(" "," ",name,condition,"?");
	}

}
