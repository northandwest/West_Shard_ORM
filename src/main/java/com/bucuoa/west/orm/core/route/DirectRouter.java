package com.bucuoa.west.orm.core.route;

import com.bucuoa.west.orm.core.uitls.WStringUtils;
import com.bucuoa.west.orm.shard.ShardInfo;

public class DirectRouter implements IRouter {
	private int dbNums;
	private String tableName;
	private ShardInfo shardInfo;
	
	public DirectRouter(ShardInfo shardInfo) {
		this.shardInfo = shardInfo;
	}
	
	@Override
	public String route() {
		String newTableName = WStringUtils.join("_", shardInfo.getTable(), String.valueOf(shardInfo.getDirectNums()));

		return newTableName;
	}

	public int getDbNums() {
		return dbNums;
	}

	public void setDbNums(int dbNums) {
		this.dbNums = dbNums;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	

}
