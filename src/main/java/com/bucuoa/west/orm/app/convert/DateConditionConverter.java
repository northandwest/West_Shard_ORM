package com.bucuoa.west.orm.app.convert;

class DateConditionConverter implements ConditionConverter {
	
	public String convert(Object obj)
	{
		return "'"+obj+"'";
	}

}
