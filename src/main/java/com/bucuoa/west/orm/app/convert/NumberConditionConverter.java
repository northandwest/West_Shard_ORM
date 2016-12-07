package com.bucuoa.west.orm.app.convert;

class NumberConditionConverter implements ConditionConverter {
	
	public String convert(Object obj)
	{
		return obj.toString();
	}

}
