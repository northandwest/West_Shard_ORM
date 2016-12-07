package com.bucuoa.west.orm.core.clazzinfo;

import java.util.HashMap;
import java.util.Map;

import com.bucuoa.west.orm.core.converter.ClassObjectConverter;
import com.bucuoa.west.orm.core.uitls.AnnoationUtil;
import com.bucuoa.west.orm.shard.holder.DbShardHolder;

public class SingleTableInfo extends BaseTableInfo implements ITableInfo {

	private Class<?> clazz;
	private Map<String, String> filedMap = new HashMap<String, String>();

	public <T> SingleTableInfo(T t) {
		this.clazz = t.getClass();
		this.filedMap = ClassObjectConverter.getFiledMap(this.clazz);
	}

	@Override
	public Class<?> getClazz() {
		return clazz;
	}

	public String getFieldName(String pname) {
		return this.filedMap.get(pname);
	}

	@Override
	public String getTableName() {
		String key = clazz.getName();
		String tablename = tableNameCache.get(key);
		if (tablename == null || tablename.equals("")) {
			tablename = AnnoationUtil.getTablename(clazz);
			tableNameCache.put(key, tablename);
		}
		
		DbShardHolder.clearGroup();

		return tablename;
	}

	public boolean isSingleTable() {

		return AnnoationUtil.isSingleTable(clazz);
	}
}
