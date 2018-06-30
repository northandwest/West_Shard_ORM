package com.bucuoa.west.orm.app.extend;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;
import com.bucuoa.west.orm.app.extend.spring.MutiliDataSourceSpringSingleBaseDao;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjun16 on 2018/1/23.
 */
public abstract class AbstractBaseService<T, PK extends Serializable> {

    public abstract MutiliDataSourceSpringSingleBaseDao<T, PK> getDao();

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
        if (list != null && list.size() > 0) {
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

    public boolean updateEntityIn(T t, Long[] ids) throws Exception {
        return getDao().updateEntityIn(t, ids);
    }

    public List<T> queryListBean(Class<T> clazz, String sql,Object... params) {
        return getDao().queryListBean(clazz, sql,params);
    }
//	 public List<T> findEntityList(String[] column, List<Expression> where,
//	 OrderBy orderBy, WPage page) {
//
////		 getDao().queryListMap(sql)
//	 return getDao().findEntityList(column, where, orderBy, page);
//
//	 }
}
