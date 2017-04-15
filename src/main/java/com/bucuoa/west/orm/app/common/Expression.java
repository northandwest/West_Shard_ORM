package com.bucuoa.west.orm.app.common;

import java.math.BigDecimal;
import java.util.Date;

import com.bucuoa.west.orm.core.uitls.WStringUtils;

public class Expression {

	public Expression(String name, String condition, Object value) {
		this.name = name;
		this.condition = condition;
		this.value = value;
		this.type = ExpressionType.AND.getType();
	}

	public Expression(String name, Object value) {
		this.name = name;
		this.condition = "=";
		this.value = value;
		this.type = ExpressionType.AND.getType();
	}

	public Expression(String name, String condition, Object value, String type) {
		this.name = name;
		this.condition = condition;
		this.value = value;
		this.type = type;
	}

	private String name;
	private String condition;
	private Object value;
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		String valuz = "";
		if (value instanceof String) {
			// 兼容老版本拼单引号
			String temp = value.toString();
			temp = temp.trim();
			if (temp != null && temp.startsWith("'") && temp.endsWith("'")) {
				valuz = temp;
			} else {
				valuz = "'" + value + "'";
			}
		} else if (value instanceof Integer) {
			int doubleValue = ((Integer) value).intValue();
			valuz = String.valueOf(doubleValue);
		} else if (value instanceof Double) {
			double doubleValue = ((Double) value).doubleValue();
			valuz = String.valueOf(doubleValue);
		} else if (value instanceof Long) {
			long longValue = ((Long) value).longValue();
			valuz = String.valueOf(longValue);
		} else if (value instanceof BigDecimal) {
			long longValueExact = ((BigDecimal) value).longValueExact();
			valuz = String.valueOf(longValueExact);
		}

		return WStringUtils.join(" ", " ", name, condition, valuz);
	}

	public String toParameterString() {

		return WStringUtils.join(" ", " ", name, condition, "?");
	}

}
