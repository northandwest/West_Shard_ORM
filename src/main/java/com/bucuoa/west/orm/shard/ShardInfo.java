package com.bucuoa.west.orm.shard;

public class ShardInfo {
	private String policy;
	private int nums; //单库分表
	private int shards; //分库
	private String shardKey; //分库主键
	private String table;
	private Object shardValue;
	
	private int directNums; //指定分表下标
	private int directShards;//指定分库下标
	
	
	public int getDirectNums() {
		return directNums;
	}
	public void setDirectNums(int directNums) {
		this.directNums = directNums;
	}
	public int getDirectShards() {
		return directShards;
	}
	public void setDirectShards(int directShards) {
		this.directShards = directShards;
	}
	public Object getShardValue() {
		return shardValue;
	}
	public void setShardValue(Object shardValue) {
		this.shardValue = shardValue;
	}
	public int getShards() {
		return shards;
	}
	public void setShards(int shards) {
		this.shards = shards;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public String getShardKey() {
		return shardKey;
	}
	public void setShardKey(String shardKey) {
		this.shardKey = shardKey;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
	

}
