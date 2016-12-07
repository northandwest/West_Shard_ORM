package com.bucuoa.west.orm.core.route;

import com.bucuoa.west.orm.core.base.AlgorithemEnum;
import com.bucuoa.west.orm.core.uitls.WStringUtils;
import com.bucuoa.west.orm.shard.ShardInfo;
import com.bucuoa.west.orm.shard.algorithm.IAlgorithm;
import com.bucuoa.west.orm.shard.algorithm.ShardResult;

public class ShardRouter implements IRouter {
	private ShardInfo shardInfo;
	private int dbNums;
	private String tableName;

	public ShardRouter(ShardInfo shardInfo) {
		this.shardInfo = shardInfo;
	}

	@Override
	public String route() {
		String table = shardInfo.getTable();

		ShardResult shardResult = null;

		if (shardInfo.getPolicy().equals(AlgorithemEnum.HASH.getPolicy())) {
			shardResult = IAlgorithm.hashAlgorithm.getShardResult(shardInfo.getShards(), shardInfo.getNums(),
					shardInfo.getShardValue());
		}else if(shardInfo.getPolicy().equals(AlgorithemEnum.MurMurHash.getPolicy())) {
			shardResult = IAlgorithm.murMurHash.getShardResult(shardInfo.getShards(), shardInfo.getNums(),
					shardInfo.getShardValue());
		}
		this.dbNums = shardResult.getDatasourceNo();

		String newTableName = WStringUtils.join("_", table, String.valueOf(shardResult.getTableNo()));
		this.tableName = newTableName;
		
//		DatasourceShardHolder.setDbShard(dbNums);

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
