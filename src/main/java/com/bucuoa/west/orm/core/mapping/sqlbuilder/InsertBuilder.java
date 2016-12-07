package com.bucuoa.west.orm.core.mapping.sqlbuilder;

public class InsertBuilder {
	private String fields;
	private String params;
	private String table;

	public InsertBuilder(String table) {
		this.table = table;
	}

	public String toSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(table).append(" (").append(fields).append(") ").append(" values(")
				.append(params).append(")");

		return sql.toString();
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

}
