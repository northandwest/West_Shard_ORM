package com.bucuoa.west.orm.core.base;

public enum AlgorithemEnum {
	
	HASH("hash"),
	MurMurHash("murhash");
	
	private String policy;
	AlgorithemEnum(String policy)
	{
		this.policy = policy;
	}
	
	public String getPolicy()
	{
		return this.policy;
	}

}
