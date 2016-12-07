package com.bucuoa.west.orm.core.mapping.sqlbuilder;

public class DeleteBuilder {

	private String table;
	private String where;
	
	public DeleteBuilder(String table)
	{
		this.table = table;
	}
	
	public String toSQL()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(table).append(" where ").append(where);
		return sql.toString();
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}
	
	
}
