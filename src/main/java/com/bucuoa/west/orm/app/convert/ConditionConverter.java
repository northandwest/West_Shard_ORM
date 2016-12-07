package com.bucuoa.west.orm.app.convert;

public interface ConditionConverter {
	
	ConditionConverter STRING = new StringConditionConverter();
	
	ConditionConverter DATE = new DateConditionConverter();

	ConditionConverter NUMBER = new NumberConditionConverter();

	ConditionConverter LIKE = new LikeConditionConverter();

	String convert(Object obj);
}
