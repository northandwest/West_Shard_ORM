package com.bucuoa.west.orm.app.extend;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T, PK extends Serializable> {

	T saveEntity(T entity) throws Exception;

	void updateEntity(T entity) throws Exception;

	// public T findEntityById(PK id) throws Exception;

	<T> List<T> findListBy(T t) throws Exception;

	// public T findEntityBy(String filed, String value) throws Exception;

	// public T findEntityBy(String[] filed, Object[] value) throws Exception;

	boolean deleteEntityBy(T t) throws Exception;
	
	boolean updateEntityIn(T t,Long[] ids) throws Exception;

	// public List<T> findEntityList(List<Expression> where, OrderBy orderBy,
	// WPage page);
	//
	// public List<T> findEntityList(List<Expression> where, WPage page);
	//
	// public List<T> findEntityList(OrderBy orderBy, WPage page);
	//
	// public List<T> findEntityList(String[] column, List<Expression> where,
	// OrderBy orderBy, WPage page);

	// public List<Map<String, String>> queryListMap(T t,String sql);
	//
	// public <T> List<T> queryListBean(T t, String sql);

}
