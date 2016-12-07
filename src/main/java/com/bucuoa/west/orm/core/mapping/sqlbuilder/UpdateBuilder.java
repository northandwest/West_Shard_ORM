package com.bucuoa.west.orm.core.mapping.sqlbuilder;

public class UpdateBuilder {
	private String sets;
	private String table;
	private String where;

	public UpdateBuilder(String table) {
		this.table = table;
	}

	public String toSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(table).append(" set ").append(sets).append(" where ").append(where);

		return sql.toString();
	}

	public String getSets() {
		return sets;
	}

	public void setSets(String sets) {
		this.sets = sets;
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
