package com.bucuoa.west.orm.core;

import java.sql.Connection;
import java.util.Date;
/**
 * 会话实体类
 * @author jake
 *
 */
public class Session {
	private String id;
	//jdbc 链接
	private Connection connection; 
	//创建耗时x ms
	private long createUseTime;
	//创建时间
	private Date createTime;
	
	public long getCreateUseTime() {
		return createUseTime;
	}

	public void setCreateUseTime(long createUseTime) {
		this.createUseTime = createUseTime;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	

}
