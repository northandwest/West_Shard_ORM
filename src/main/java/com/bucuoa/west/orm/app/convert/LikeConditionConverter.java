package com.bucuoa.west.orm.app.convert;

class LikeConditionConverter implements ConditionConverter {
	
	public String convert(Object obj)
	{
		return "%"+obj+"%";
	}

}
