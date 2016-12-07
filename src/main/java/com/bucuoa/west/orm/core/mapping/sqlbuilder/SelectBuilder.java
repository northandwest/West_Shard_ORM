package com.bucuoa.west.orm.core.mapping.sqlbuilder;

public class SelectBuilder {

	private String fields;
	private String conditions;
	private String limit;
	private String orderby;
	private String table;

	public SelectBuilder(String table) {
		this.table = table;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fileds) {
		this.fields = fileds;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String toSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("select ").append(fields).append(" from ").append(table);

		if (conditions != null) {
			sql.append(" where ").append(conditions);
		}

		if (orderby != null) {
			sql.append(orderby);
		}

		if (limit != null) {
			sql.append(limit);
		}

		return sql.toString();
	}

}
