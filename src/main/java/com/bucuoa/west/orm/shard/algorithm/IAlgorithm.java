package com.bucuoa.west.orm.shard.algorithm;

public interface IAlgorithm {
	
	IAlgorithm hashAlgorithm = new HashAlgorithm();
	IAlgorithm murMurHash = new MurMurHash();

	
	ShardResult getShardResult(int shard, int tablenums, Object shardValue);
}
