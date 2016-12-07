package com.bucuoa.west.orm.core.mapping.validater;

import com.bucuoa.west.orm.shard.ShardInfo;

public class ShardObjectValidater {
	
	public static <T> boolean check(ShardInfo shardTableInfo)
	{
		if(shardTableInfo.getDirectNums() != 0 && shardTableInfo.getDirectShards() != 0)
		{
			return true;
		}
		
		if(shardTableInfo.getShardValue() == null)
		{
			return false;
		}
		
		return true;
	}

}
