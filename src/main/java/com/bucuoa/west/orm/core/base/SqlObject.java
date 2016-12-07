package com.bucuoa.west.orm.core.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlObject {
	private String sql;
	private List<String> cloumnName = new ArrayList<String>();
	private Map<String, Object> paramsValue = new HashMap<String, Object>();
	private String tableName;
	private Serializable primaryKeyValue;
	private String primaryKey;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<String> getCloumnName() {
		return cloumnName;
	}

	public void setCloumnName(List<String> cloumnName) {
		this.cloumnName = cloumnName;
	}

	public Map<String, Object> getParamsValue() {
		return paramsValue;
	}

	public void setParamsValue(Map<String, Object> paramsValue) {
		this.paramsValue = paramsValue;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Serializable getPrimaryKeyValue() {
		return primaryKeyValue;
	}

	public void setPrimaryKeyValue(Serializable primaryKeyValue) {
		this.primaryKeyValue = primaryKeyValue;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

}
