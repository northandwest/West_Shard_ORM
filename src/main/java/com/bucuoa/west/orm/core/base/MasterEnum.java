package com.bucuoa.west.orm.core.base;

public enum MasterEnum {
	MASTER("master"),
	SLAVE("slave");
	
	private String type;
	MasterEnum(String type)
	{
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
}
