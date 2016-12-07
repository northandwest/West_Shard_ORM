package com.bucuoa.west.orm.core.clazzinfo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bucuoa.west.orm.core.converter.ClassObjectConverter;
import com.bucuoa.west.orm.core.mapping.validater.ShardObjectValidater;
import com.bucuoa.west.orm.core.route.IRouter;
import com.bucuoa.west.orm.core.route.RouteFactory;
import com.bucuoa.west.orm.core.uitls.AnnoationUtil;
import com.bucuoa.west.orm.shard.ShardInfo;
import com.bucuoa.west.orm.shard.holder.DbShardHolder;

public class ShardTableInfo extends BaseTableInfo implements ITableInfo {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private Class<?> clazz;
	private ShardInfo shardInfo;
	
	private Map<String,String> filedMap = new HashMap<String,String>();
	
	private boolean validateSuccess;

	@Override
	public Class<?> getClazz() {
		return clazz;
	}

	public <T> ShardTableInfo(T t) {
		this.clazz = t.getClass();
		this.shardInfo = AnnoationUtil.getShardTableInfo(t);
		
		validateSuccess = ShardObjectValidater.check(this.shardInfo);
		this.filedMap =  ClassObjectConverter.getFiledMap(t);
		
	}

	public String getFieldName(String pname)
	{
		return this.filedMap.get(pname);
	}
	
	@Override
	public String getTableName() throws Exception {
		
		if(!validateSuccess)
		{
			throw new Exception("shard key value is null");
		}
		IRouter router = new RouteFactory().createRouter(shardInfo);
		
		String newTableName = router.route();
		
		DbShardHolder.setGroup(String.valueOf(router.getDbNums()));

		logger.info("shard policy=>"+shardInfo.getPolicy()+","+newTableName + "==>db:" + router.getDbNums());
		
		return newTableName;
	}

	public ShardInfo getShardInfo() {

		ShardInfo shardTableInfo = this.shardInfo;

		return shardTableInfo;
	}

}
