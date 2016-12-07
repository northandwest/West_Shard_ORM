package com.bucuoa.west.orm.app.extend;

import java.io.Serializable;
import java.util.List;

public interface IShardBaseDao<T, PK extends Serializable> extends IBaseDao<T, PK> {
	T findEntityById(T t) throws Exception;
	<T> List<T> findListBy(T t) throws Exception;
}
