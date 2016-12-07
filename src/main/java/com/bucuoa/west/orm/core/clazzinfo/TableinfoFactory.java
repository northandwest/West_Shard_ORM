package com.bucuoa.west.orm.core.clazzinfo;

public class TableinfoFactory {

	public static  <T> ITableInfo create(T t)
	{
		SingleTableInfo single = new SingleTableInfo(t);
		if(single.isSingleTable())
		{
			return single;
		}else
		{
			return new ShardTableInfo(t);
		}
	}

}
