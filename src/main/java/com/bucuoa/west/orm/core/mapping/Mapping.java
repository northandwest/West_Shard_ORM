package com.bucuoa.west.orm.core.mapping;

import java.util.List;
import java.util.Map;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;

/**
 * 对象映射成数据库字段
 * 
 * @author jake
 * 
 */
public interface Mapping {

	<T> String buildUpdate(T t) throws Exception ;

	<T> String buildInsert(T t) throws Exception ;

	<T> String buildDelete(T t) throws Exception ;

	<T> String buildSelect(T t, List<Expression> wheres, OrderBy orderBy, WPage page) throws Exception ;

	<T> String buildSelectCount(T t, List<Expression> wheres) throws Exception ;

	<T> String buildDelete(T t, List<Expression> wheres) throws Exception ;

	<T> String buildSelect(T t, OrderBy orderBy, WPage page) throws Exception ;
	
	<T> Map<String,Object> getNotNullFields(T t) throws Exception ;
	
	<T> String buildUpdate(T t,Long[] ids) throws Exception;
}
