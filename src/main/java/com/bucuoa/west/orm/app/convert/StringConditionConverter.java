package com.bucuoa.west.orm.app.convert;

class StringConditionConverter implements ConditionConverter {
	
	public String convert(Object obj)
	{
		return "'"+obj+"'";
	}

}
