package com.bucuoa.west.orm.core.base;

public enum DialectEnum {
	
	MYSQL("mysql");
	
	private String dialect;
	
	DialectEnum(String dialect)
	{
		this.dialect = dialect;
	}
	
	public String getDialect() {
		return dialect;
	}

}
