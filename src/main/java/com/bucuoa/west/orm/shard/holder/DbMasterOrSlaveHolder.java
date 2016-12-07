package com.bucuoa.west.orm.shard.holder;

import com.bucuoa.west.orm.core.base.MasterEnum;

public class DbMasterOrSlaveHolder {

	private static final ThreadLocal<String> masterHolder = new ThreadLocal<String>();
	private static final String master = MasterEnum.MASTER.getType();
	private static final String slave = MasterEnum.SLAVE.getType();

	public static void setDbType(String dbType) {

		masterHolder.set(dbType);
	}

	public static String getDbType() {
		return (String) masterHolder.get();
	}

	public static void clearDbType() {
		masterHolder.remove();
	}
	
	public static void initMaster() {
		clearDbType();
		masterHolder.set(master);
	}
	
	public static void initSlave() {
		clearDbType();
		masterHolder.set(slave);
	}
	
	public static void setMaster(String group) {
		clearDbType();
		masterHolder.set(master+group);
	}
	
	public static void setMaster(String group,int index) {
		clearDbType();
		masterHolder.set(master+group+"_"+index);
	}
	
	
	
	public static void setSlave(String group) {
		clearDbType();
		masterHolder.set(slave+group);
	}
	
	public static void setSlave(String group,int index) {
		clearDbType();
		masterHolder.set(slave+group+"_"+index);
	}
}
