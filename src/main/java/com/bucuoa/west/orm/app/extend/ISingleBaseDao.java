package com.bucuoa.west.orm.app.extend;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;

public interface ISingleBaseDao<T, PK extends Serializable> extends IBaseDao<T, PK> {

	T findEntityById(PK id) throws Exception;

	boolean deleteEntityById(PK id) throws Exception;

	List<Map<String, Object>> queryListMap(String sql);

	List<T> findEntityList(List<Expression> where, OrderBy orderBy, WPage page);

	List<T> findEntityList(List<Expression> where, WPage page);

	List<T> findEntityList(OrderBy orderBy, WPage page);

	List<T> queryListBean(Class<T> clazz, String sql);
	
	int getEntityCount(T t) throws Exception;

	int getEntityCount(List<Expression> condition) throws Exception;
	
	boolean deleteEntityByCondition(List<Expression> condition) throws Exception;

}
