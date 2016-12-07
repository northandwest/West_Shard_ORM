package com.bucuoa.west.orm.app.extend;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;

public interface IBaseDao<T, PK extends Serializable> {

	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	T saveEntity(T entity) throws Exception;

	void updateEntity(T entity) throws Exception;

	// Connection getConnection();
	/**
	 * 当个实体查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	// public T findEntityById(PK id) throws Exception;

	<T> List<T> findListBy(T t) throws Exception;

	// public T findEntityBy(String filed, String value) throws Exception;

	// public T findEntityBy(String[] filed, Object[] value) throws Exception;

	/**
	 * 统计数查询
	 * 
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	// public int selectCount(String sql, Object... param) throws Exception;



	/**
	 * 删除操作
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
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
