package com.bucuoa.west.orm.app.utils;

import java.util.List;

import com.bucuoa.west.orm.app.common.Expression;

public class WhereUtils {
	
	static public Object[] getValues(List<Expression> where)
	{
		Object tt[] = new String[where.size()];
		
		for(int i = 0; i < where.size(); i ++)
		{
			Expression and = where.get(i);
			tt[i] = and.getValue();
		}
		
		return tt;
	}
	
	static public String toSql(List<Expression> where)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < where.size(); i ++)
		{
			Expression and = where.get(i);
			if(i != 0)
			{
				sb.append(" ").append(and.getType()).append(" ");
			}
			sb.append(and.toString());
		}
		
		return sb.toString();
	}
	
	static public String toSqlParameters(List<Expression> where)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < where.size(); i ++)
		{
			Expression and = where.get(i);
			if(i != 0)
			{
				sb.append(" ").append(and.getType()).append(" ");
			}
			sb.append(and.toParameterString());
		}
		
		return sb.toString();
	}
}
