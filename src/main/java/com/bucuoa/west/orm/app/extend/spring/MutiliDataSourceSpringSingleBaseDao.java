package com.bucuoa.west.orm.app.extend.spring;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;
import com.bucuoa.west.orm.core.ExecuteManager;
import com.bucuoa.west.orm.core.base.IdObject;
import com.bucuoa.west.orm.core.converter.ClassObjectConverter;
import com.bucuoa.west.orm.core.mapping.SQLFactory;
import com.bucuoa.west.orm.core.uitls.AnnoationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * west和应用的接口类 项目只需要继承本类就可以实现west提供的功能 用户也可以自己实现本类 只需要注入ExcetueManager即可
 * 
 * @author jake
 */
public abstract class MutiliDataSourceSpringSingleBaseDao<T, PK   extends Serializable> {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public abstract ExecuteManager<T, PK> getExcetueManager() ;

	@SuppressWarnings("unchecked")
	protected MutiliDataSourceSpringSingleBaseDao() {
		classz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	private Class<T> classz;
	private Class<T> getClassz() {
		return classz;
	}
	
	protected String getTablePKField() {
		return AnnoationUtil.getId(getClassz());
	}

	protected IdObject getIdInfo() {
		return AnnoationUtil.getIdObject(getClassz());
	}

	protected String getTablePKSetMethod() {
		String id = AnnoationUtil.getId(getClassz());
		id = ClassObjectConverter.fieldToClazzSetMethod(id);
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public T saveEntity(T entity) throws Exception {
		String insertSql = SQLFactory.insertSql(entity);

		String setPK = getTablePKSetMethod();

		long start = System.currentTimeMillis();

		IdObject idInfo = getIdInfo();

		if (idInfo != null && idInfo.isAutoIncrement()) {
			PK id = (PK) getExcetueManager().executeUpdateSql(insertSql);

			Class<?> clazz = entity.getClass();
			clazz.getMethod(setPK, id.getClass()).invoke(entity, id);
		} else {
			boolean success = getExcetueManager().executeUpdateSqlNoID(insertSql);
		}
		long end = System.currentTimeMillis();

		logger.trace("执行sql:{} use:{}ms", insertSql, (end - start));
		return entity;
	}

	public void updateEntity(T entity) throws Exception {
		long start = System.currentTimeMillis();
		String updateSql = SQLFactory.updateSql(entity);

		getExcetueManager().executeUpdateSql(updateSql);

		long end = System.currentTimeMillis();
		
		logger.trace("执行sql:{} use:{}ms", updateSql, (end - start));

	}
	
	
	public boolean updateEntityIn(T t,Long[] ids) throws Exception
	{
		String updateSql = SQLFactory.updateSql(t,ids);
		
		getExcetueManager().executeUpdateSql(updateSql);
		return true;
	}


	public Connection getConnection()
	{
		return getExcetueManager().getSessionFactory().getSession().getConnection();
	}

	public int getEntityCount(T t) throws Exception {

		String sql = SQLFactory.selectCountSql(t, null);

		if (sql == null) {
			throw new IllegalArgumentException("The sql statement is null.");
		}

		int queryCount = 0;
		long start = System.currentTimeMillis();

		queryCount = getExcetueManager().queryCount(sql);

		long end = System.currentTimeMillis();

		logger.trace("执行sql:{} use:{}ms", sql, (end - start));

		return queryCount;
	}

	public int getEntityCount(List<Expression> wheres) throws Exception {

		String selectCountSql = SQLFactory.selectCountSql(getClassz().newInstance(), wheres);
		int queryCount = 0;
		long start = System.currentTimeMillis();

		queryCount = getExcetueManager().queryCount(selectCountSql);

		long end = System.currentTimeMillis();

		logger.trace("执行sql:{} use:{}ms", selectCountSql, (end - start));

		return queryCount;
	}

	public boolean deleteEntityBy(T t) throws Exception {

		String selectSql = 	SQLFactory.deleteSql(t);
		long start = System.currentTimeMillis();

		getExcetueManager().deleteBy(selectSql);

		long end = System.currentTimeMillis();

		logger.trace("执行sql:{} use:{}ms", selectSql, (end - start));

		return true;
	}

	public T findEntityById(PK id) throws Exception {

		List<Expression> wheres = new ArrayList<Expression>();
		String tablePKField = getTablePKField();
		Expression expression = new Expression(tablePKField, id);
		wheres.add(expression);

		String setPK = getTablePKSetMethod();

		T newInstance = classz.newInstance();
		classz.getMethod(setPK, id.getClass()).invoke(newInstance, id);

		String selectSql = SQLFactory.selectSql(newInstance, wheres);

		long start = System.currentTimeMillis();

		T queryOne = getExcetueManager().queryOne(selectSql, classz);

		long end = System.currentTimeMillis();

		logger.trace("执行sql : {} use:{}ms", selectSql, (end - start));
		return queryOne;
	}

	@SuppressWarnings({ "unchecked" })
	public List<T> findListBy(T t) throws Exception {

		Class<T> classz2 = (Class<T>) this.classz;

		List<T> queryList = (List<T>) findListBy(t, classz2);

		return queryList;
	}

	public <T> List<T> findListBy(T t, Class<T> clazz) throws Exception {

		List<Expression> wheres = new ArrayList<Expression>();

		Map<String, Object> conditions = SQLFactory.getNotNullFields(t);

		Set<Entry<String, Object>> entrySet = conditions.entrySet();

		for (Entry<String, Object> entry : entrySet) {
			Expression expression = new Expression(entry.getKey().toString(), entry.getValue());
			wheres.add(expression);
		}

		String selectSql = SQLFactory.selectSql(t, wheres);

		long start = System.currentTimeMillis();

		List<T> queryList = (List<T>) getExcetueManager().queryList(selectSql, clazz);

		long end = System.currentTimeMillis();

		logger.trace("执行sql : {} use:{}ms", selectSql, (end - start));
		return queryList;
	}

	public boolean deleteEntityById(PK id) throws Exception {
		T newInstance = classz.newInstance();

		String setPK = getTablePKSetMethod();
		classz.getMethod(setPK, id.getClass()).invoke(newInstance, id);

		String deleteSql = SQLFactory.deleteSql(newInstance);// .deleteSqlBy(classz,
																// condition);
		long start = System.currentTimeMillis();

		getExcetueManager().deleteBy(deleteSql);
		long end = System.currentTimeMillis();

		logger.trace("执行sql : " + deleteSql + "use:" + (end - start) + "ms");
		return true;
	}

	public boolean deleteEntityByCondition(List<Expression> condition) throws Exception {

		String deleteSql = SQLFactory.deleteSqlBy(classz, condition);
		long start = System.currentTimeMillis();

		getExcetueManager().deleteBy(deleteSql);
		long end = System.currentTimeMillis();

		logger.trace("执行sql : " + deleteSql + "use:" + (end - start) + "ms");
		return true;
	}

	public List<T> findEntityList(List<Expression> where, OrderBy orderBy, WPage page) {
		return findEntityList(null, where, orderBy, page);
	}

	public List<T> findEntityList(List<Expression> where, WPage page) {
		return findEntityList(null, where, null, page);
	}

	public List<T> findEntityList(String[] column, List<Expression> wheres, OrderBy orderBy, WPage page) {

		List<T> queryList = null;
		try {
			T newInstance = classz.newInstance();
			String selectSql = SQLFactory.selectSql(newInstance, wheres, orderBy, page);
			long start = System.currentTimeMillis();

			queryList = getExcetueManager().queryList(selectSql, classz);
			long end = System.currentTimeMillis();

			logger.trace("执行sql : " + selectSql + "use:" + (end - start) + "ms");
		} catch (Exception e) {
		}

		if (queryList == null) {
			queryList = new ArrayList<T>();
		}

		return queryList;
	}

	public List<T> findEntityList(OrderBy orderBy, WPage page) {
		List<T> queryList = null;
		try {
			String selectSql = SQLFactory.selectSql(classz, orderBy, page);
			long start = System.currentTimeMillis();

			queryList = getExcetueManager().queryList(selectSql, classz);
			long end = System.currentTimeMillis();

			logger.trace("执行sql : " + selectSql + "use:" + (end - start) + "ms");
		} catch (Exception e) {
		}

		if (queryList == null) {
			queryList = new ArrayList<T>();
		}

		return queryList;
	}

	public int queryCount(String sql) {
		long start = System.currentTimeMillis();

		int count = getExcetueManager().queryCount(sql);

		long end = System.currentTimeMillis();

		logger.trace("执行sql : " + sql + "use:" + (end - start) + "ms");
		return count;
	}

	public List<T> queryListBean(Class<T> clazz, String sql) {
		List<T> list = null;
		long start = System.currentTimeMillis();

		list = (List<T>) getExcetueManager().queryList(sql, clazz);
		long end = System.currentTimeMillis();

		logger.trace("执行sql : " + sql + "use:" + (end - start) + "ms");
		return list;
	}
	
	public List<Map<String, Object>> queryListMap(String sql) {
		List<Map<String, Object>> queryListMap = null;
		long start = System.currentTimeMillis();

		queryListMap = getExcetueManager().queryListMap(sql);

		long end = System.currentTimeMillis();

		logger.trace("执行sql : " + sql + "use:" + (end - start) + "ms");
		return queryListMap;
	}


}
