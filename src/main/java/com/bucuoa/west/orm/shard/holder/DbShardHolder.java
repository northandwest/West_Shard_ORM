package com.bucuoa.west.orm.shard.holder;
/**
 * 存分片到线程
 * @author wujiang3
 *
 */
public class DbShardHolder {

	private static final ThreadLocal<String> shardHolder = new ThreadLocal<String>();

	public static void setGroup(String group) {

		shardHolder.set(group);
	}

	public static String getGroup() {
		return (String) shardHolder.get();
	}

	public static void clearGroup() {
		shardHolder.remove();
	}
	


}
