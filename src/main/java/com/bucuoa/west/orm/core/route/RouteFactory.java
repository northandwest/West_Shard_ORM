package com.bucuoa.west.orm.core.route;

import com.bucuoa.west.orm.shard.ShardInfo;

public class RouteFactory  {

	public <T> IRouter createRouter(ShardInfo shardInfo)
	{
		
		if(shardInfo.getDirectNums() != 0 && shardInfo.getDirectShards() != 0)
		{
			return new DirectRouter(shardInfo);
		}else
		{
			return new ShardRouter(shardInfo);
		}

	}
}
