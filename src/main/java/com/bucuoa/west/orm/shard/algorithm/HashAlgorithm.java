package com.bucuoa.west.orm.shard.algorithm;

class HashAlgorithm implements IAlgorithm {

	@Override
	public ShardResult getShardResult(int shard, int tablenums, Object shardValue) {
		
		int hashCode = Math.abs(shardValue.hashCode());
		int dbNums = hashCode % shard;
		int tableNums = (hashCode % tablenums);
		ShardResult result = new ShardResult();
		result.setDatasourceNo(dbNums);
		result.setTableNo(tableNums);
		
		return result;
	}

}
