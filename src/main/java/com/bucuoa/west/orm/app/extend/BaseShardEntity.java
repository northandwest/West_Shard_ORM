package com.bucuoa.west.orm.app.extend;

public class BaseShardEntity {
	//直接指定库下标 和 表下标
	private int directShardValue;
	private int directTable;
	
	public int getDirectShardValue() {
		return directShardValue;
	}
	public void setDirectShardValue(int directShardValue) {
		this.directShardValue = directShardValue;
	}
	public int getDirectTable() {
		return directTable;
	}
	public void setDirectTable(int directTable) {
		this.directTable = directTable;
	}
	
	

}
