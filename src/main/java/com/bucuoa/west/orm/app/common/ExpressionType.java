package com.bucuoa.west.orm.app.common;

public enum ExpressionType {
	AND("and"), OR("or");

	private String type;

	ExpressionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
