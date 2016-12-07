package com.bucuoa.west.orm.app.common;

public class OrderBy {
	private String[] filed;
	private String[] type;

	public OrderBy(String filed, String type) {
		if(filed == null)
		{
			filed = "";
			type = "";
		}
		this.filed = filed.split(",");
		this.type = type.split(",");
	}

	public String[] getFiled() {
		return filed;
	}

	public String[] getType() {
		return type;
	}
	
	public String toString()
	{
		StringBuilder querySql = new StringBuilder();
		if (null != filed && filed.length > 0 && null != type
				&& type.length > 0 && filed.length == type.length) {
			querySql.append(" order by ");
			for (int i = 0, length = filed.length; i < length; i++) {
				querySql.append(filed[i] + " " + type[i]);
				if (i < filed.length - 1) {
					querySql.append(", ");
				}
			}
		}
		
		return querySql.toString();
	}

}
