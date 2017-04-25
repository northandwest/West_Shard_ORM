package com.bucuoa.west.orm.app.extend;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;

@Service
public abstract class SingleBaseService<T, PK extends Serializable> {

	public abstract ISingleBaseDao<T, PK> getDao();

	@Transactional
	public T saveEntity(T entity) throws Exception {
		T saveEntity = getDao().saveEntity(entity);
		return saveEntity;
	}

	@Transactional
	public void updateEntity(T entity) throws Exception {
		getDao().updateEntity(entity);
	}

	public boolean deleteEntityBy(T entity) throws Exception {
		return getDao().deleteEntityBy(entity);
	}

	public T findEntityById(PK id) throws Exception {
		return getDao().findEntityById(id);
	}
	
	public T findEntityBy(T entity) throws Exception {
		List<T> list = getDao().findListBy(entity);
		if(list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}
	
	public List<T> findEntityListBy(T entity) throws Exception {
		List<T> list = getDao().findListBy(entity);
		return list;
	}

	public boolean deleteEntityById(PK id) throws Exception {
		return getDao().deleteEntityById(id);
	}

	public int getEntityCount(List<Expression> es) throws Exception {
		return getDao().getEntityCount(es);
	}

	public List<T> findEntityList(List<Expression> where, OrderBy orderBy, WPage page) {
		return getDao().findEntityList(where, orderBy, page);
	}

	public List<T> findEntityList(List<Expression> where, WPage page) {
		return getDao().findEntityList(where, page);
	}

	public List<T> findEntityList(OrderBy orderBy, WPage page) {
		return getDao().findEntityList(orderBy, page);
	}
	
	public boolean updateEntityIn(T t,Long[] ids) throws Exception
	{
		return getDao().updateEntityIn(t, ids);
	}
	
	public List<T> queryListBean(Class<T> clazz, String sql) 
	{
		return getDao().queryListBean(clazz, sql);
	}
//	 public List<T> findEntityList(String[] column, List<Expression> where,
//	 OrderBy orderBy, WPage page) {
//		 
////		 getDao().queryListMap(sql)
//	 return getDao().findEntityList(column, where, orderBy, page);
//	
//	 }

}
