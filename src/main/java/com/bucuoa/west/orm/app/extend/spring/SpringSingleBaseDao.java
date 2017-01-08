package com.bucuoa.west.orm.app.extend.spring;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;
import com.bucuoa.west.orm.core.mapping.SQLFactory;

/**
 * west和应用的接口类 项目只需要继承本类就可以实现west提供的功能 用户也可以自己实现本类 只需要注入ExcetueManager即可
 * 
 * @author jake
 *
 * @param <T>
 * @param <PK>
 */
@Component
public class SpringSingleBaseDao<T, PK extends Serializable>  extends WestDao<T, PK> {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private Class<T> classz;

	@SuppressWarnings("unchecked")
	protected SpringSingleBaseDao() {
		classz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getClassz() {
		return classz;
	}
	/**
	 * 根据实体Id查询指定实体对象
	 * 
	 * @param id
	 *            实体对象的id
	 * @return 返回指定实体对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T findEntityById(PK id) throws Exception {
			
		List<Expression> wheres = new ArrayList<Expression>();
		String tablePKField = super.getTablePKField();
		Expression expression = new Expression(tablePKField, id);
		wheres.add(expression);
		
		String setPK = getTablePKSetMethod();
		
		T newInstance = classz.newInstance();
		classz.getMethod(setPK, id.getClass()).invoke(newInstance, id);
		
		String selectSql = SQLFactory.selectSql(newInstance,wheres);
		
		long start = System.currentTimeMillis();

		T queryOne = excetueManager.queryOne(selectSql, classz);

		long end = System.currentTimeMillis();

//		logger.info("执行sql : {} use:{}ms",selectSql,(end - start));
		return queryOne;
	}

	@SuppressWarnings({ "unchecked" })
	public <T> List<T> findListBy(T t) throws Exception {
		
		Class<T> classz2 = (Class<T>) this.classz;
		
		List<T> queryList = (List<T>)findListBy(t,classz2);

		return queryList;
	}
	
	public <T> List<T> findListBy(T t,Class<T> clazz) throws Exception {
		
		List<Expression> wheres = new ArrayList<Expression>();

		Map<String, Object> conditions = SQLFactory.getNotNullFields(t);
		
		Set<Entry<String, Object>> entrySet = conditions.entrySet();
		
		for(Entry<String, Object> entry : entrySet)
		{
			Expression expression = new Expression(entry.getKey().toString(), entry.getValue());
			wheres.add(expression);
		}
		
		String selectSql = SQLFactory.selectSql(t,wheres);
		
		long start = System.currentTimeMillis();

		List<T> queryList = (List<T>) excetueManager.queryList(selectSql, clazz);

		long end = System.currentTimeMillis();

//		logger.info("执行sql : {} use:{}ms",selectSql,(end - start));
		return queryList;
	}
	
//	public T findEntityBy(String filed, String value) throws Exception {
//
//		List<Expression> wheres = new ArrayList<Expression>();
//		Expression expression = new Expression(filed, value);
//		wheres.add(expression);
//
//		String selectSql = SQLConverter.selectSql(classz, wheres);
//		long start = System.currentTimeMillis();
//
//		T queryOne = excetueManager.queryOne(selectSql, classz);
//
//		long end = System.currentTimeMillis();
//
//		logger.info("执行sql : " + selectSql + "use:" + (end - start) + "ms");
//		return queryOne;
//	}

	
	
	public boolean deleteEntityById(PK id) throws Exception {
		T newInstance = classz.newInstance();
		
		String setPK = getTablePKSetMethod();
		classz.getMethod(setPK, id.getClass()).invoke(newInstance, id);
		
		String deleteSql = SQLFactory.deleteSql(newInstance);//.deleteSqlBy(classz, condition);
		long start = System.currentTimeMillis();

		excetueManager.deleteBy(deleteSql);
		long end = System.currentTimeMillis();

//		logger.info("执行sql : " + deleteSql + "use:" + (end - start) + "ms");
		return true;
	}
	public boolean deleteEntityByCondition(List<Expression> condition) throws Exception {

		String deleteSql = SQLFactory.deleteSqlBy(classz, condition);
		long start = System.currentTimeMillis();

		excetueManager.deleteBy(deleteSql);
		long end = System.currentTimeMillis();

		logger.info("执行sql : " + deleteSql + "use:" + (end - start) + "ms");
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

			queryList = excetueManager.queryList(selectSql, classz);
			long end = System.currentTimeMillis();

//			logger.info("执行sql : " + selectSql + "use:" + (end - start) + "ms");
		} catch (Exception e) {
		}

		if (queryList == null) {
			queryList = new ArrayList<T>();
		}

		return queryList;
	}
	
	public List<T> findEntityList(OrderBy orderBy, WPage page)
	{
		List<T> queryList = null;
		try {
			String selectSql = SQLFactory.selectSql(classz, orderBy, page);
			long start = System.currentTimeMillis();

			queryList = excetueManager.queryList(selectSql, classz);
			long end = System.currentTimeMillis();

//			logger.info("执行sql : " + selectSql + "use:" + (end - start) + "ms");
		} catch (Exception e) {
		}

		if (queryList == null) {
			queryList = new ArrayList<T>();
		}

		return queryList;
	}

	public List<Map<String, Object>> queryListMap(String sql) {
		List<Map<String, Object>> queryListMap = null;
		long start = System.currentTimeMillis();

		queryListMap = excetueManager.queryListMap(sql);

		long end = System.currentTimeMillis();

//		logger.info("执行sql : " + sql + "use:" + (end - start) + "ms");
		return queryListMap;
	}
	
	public int queryCount(String sql) {

		int count = excetueManager.queryCount(sql);
//		logger.info("执行sql : " + sql + "use:" + (end - start) + "ms");
		return count;
	}
	

	public <T> List<T> queryListBean(T t,String sql) {
		List<T> list = null;
		long start = System.currentTimeMillis();

		Class<? extends Object> class1 = t.getClass();
		list = (List<T>) excetueManager.queryList(sql, class1);
		long end = System.currentTimeMillis();

//		logger.info("执行sql : " + sql + "use:" + (end - start) + "ms");		
		return list;
	}

}
