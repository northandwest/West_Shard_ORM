package com.bucuoa.west.orm.core.base;

public class IdObject {
	
	private String name;
	private String pkStrategy;//主键生成策略 仅支持 自动生成和 空类型
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPkStrategy() {
		return pkStrategy;
	}
	public void setPkStrategy(String pkStrategy) {
		this.pkStrategy = pkStrategy;
	}

	
	public boolean isAutoIncrement()
	{
		if(pkStrategy!= null && pkStrategy.equals("autoIncrement"))
		{
			return true;
		}
		
		return false;
	}
}
