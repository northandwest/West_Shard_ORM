package com.bucuoa.west.orm.app.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bucuoa.west.orm.core.uitls.WStringUtils;
/**
 * 表达式类
 * 可以嵌套表达式
 * 类似 and ( a='abd' and c='kkk')
 * @author wujiang
 *
 */
public class Expression {

	private String name;
	private String condition;
	private Object value;
	private String type;
	private boolean hasInner = false;;
	List<Expression> inner = null;// new ArrayList<Expression>();
	//嵌套表达式构造方法
	public Expression(List<Expression> inner, ExpressionType type) {
		this.hasInner = true;
		this.inner = inner;	
		this.type = type.getType();
	}
	
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

	public Expression(String name, String condition, Object value, ExpressionType type) {
		this.name = name;
		this.condition = condition;
		this.value = value;
		this.type = type.getType();
	}


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
		if(this.hasInner)
		{
			List<Expression> inner2 = this.inner;
			StringBuilder sb = new StringBuilder();
			sb.append(" ").append(this.getType()).append(" (");
			int k = 0;
			for(Expression innerk : inner2)
			{
				String temp = build(innerk.getName(),innerk.getCondition(),innerk.getValue());
				
				if(k != 0)
				{
					sb.append(" ").append(innerk.getType()).append(" ");
				}
				
				sb.append(temp);
			
				k ++;
			}
			sb.append(" )");
			return sb.toString();
		}else
		{
			return build(this.name,this.condition,this.value);
		}
		
	}

	private String build(String namex,String conditionx,Object value) {
		if (hasInner == false &&  value == null) {
			return "";
		}

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

		return WStringUtils.join(" ", " ", namex, conditionx, valuz);
	}

	public String toParameterString() {

		return WStringUtils.join(" ", " ", name, condition, "?");
	}

}
