package com.bucuoa.west.orm.core.converter;

import java.util.Date;

import com.bucuoa.west.orm.core.uitls.WStringUtils;

public class TypeConvert {

	public static String convert(Object value,String append) {
		StringBuilder values = new StringBuilder();

		if (value instanceof Integer || value instanceof Double || value instanceof Long) {
			values.append(value).append(append);
		} else if (value instanceof Date) {
			String format_date = WStringUtils.format((Date) value);
			String arround = WStringUtils.arround(format_date, "'");
			values.append(arround).append(append);
		} else {
			String arround = WStringUtils.arround(value, "'");
			values.append(arround).append(append);
		}

		return values.toString();
	}

}
