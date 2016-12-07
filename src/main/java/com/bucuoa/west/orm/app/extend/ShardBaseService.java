package com.bucuoa.west.orm.app.extend;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;

@Service
public abstract class ShardBaseService<T, PK extends Serializable> {

	public abstract IShardBaseDao<T, PK> getDao();

	@Transactional
	public T saveEntity(T entity) throws Exception {
		T saveEntity = getDao().saveEntity(entity);
		
		return saveEntity;
	}

	@Transactional
	public void updateEntity(T entity) throws Exception {
		getDao().updateEntity(entity);
	}

	public T findEntityById(T entity) throws Exception {
		return getDao().findEntityById(entity);
	}

	public boolean deleteEntityBy(T entity) throws Exception {
		return getDao().deleteEntityBy(entity);
	}
	
	public <T> List<T> findListBy(T t) throws Exception{
		return getDao().findListBy(t);
	}
	
	public boolean updateEntityIn(T t,Long[] ids) throws Exception
	{
		return getDao().updateEntityIn(t, ids);
	}

//	public int getEntityCount(List<Expression> es) throws Exception {
//		return getDao().getEntityCount(es);
//	}

//	public List<T> findEntityList(List<Expression> where, OrderBy orderBy, WPage page) {
//		return getDao().findEntityList(where, orderBy, page);
//	}
//
//	public List<T> findEntityList(List<Expression> where, WPage page) {
//		return getDao().findEntityList(where, page);
//	}
//	
//	public List<T> findEntityList(OrderBy orderBy, WPage page) {
//		return getDao().findEntityList(orderBy, page);
//	}
//
//	public List<T> findEntityList(String[] column, List<Expression> where, OrderBy orderBy, WPage page) {
//		return getDao().findEntityList(column, where, orderBy, page);
//
//	}

}
