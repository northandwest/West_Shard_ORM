package com.bucuoa.west.orm.app.utils;

import java.util.ArrayList;
import java.util.List;
/**
 * 条件链式封装器
 * 减少controller代码
 */
import com.bucuoa.west.orm.app.common.Expression;

public class ConditionUtils {
	List<Expression> list;

	public ConditionUtils() {
	}

	public ConditionUtils build() {
		list = new ArrayList<Expression>();

		return this;
	}

	public ConditionUtils add(String key, Object value) {
		Expression ex = new Expression(key, value);
		list.add(ex);
		return this;
	}

	public List<Expression> getConditions() {
		return list;
	}
}
