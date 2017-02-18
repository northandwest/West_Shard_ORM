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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;
import com.bucuoa.west.orm.core.ExecuteManager;
import com.bucuoa.west.orm.core.converter.ClassObjectConverter;
import com.bucuoa.west.orm.core.mapping.SQLFactory;
import com.bucuoa.west.orm.core.uitls.AnnoationUtil;

/**
 * west和应用的接口类 项目只需要继承本类就可以实现west提供的功能 用户也可以自己实现本类 只需要注入ExcetueManager即可
 * 
 * @author jake
 */
@Component
public class SpringShardBaseDao<T, PK extends Serializable> extends WestDao<T, PK> {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private Class<T> classz;

	@SuppressWarnings("unchecked")
	protected SpringShardBaseDao() {
		classz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

//	private String getTablePKField() {
//		return AnnoationUtil.getId(classz);
//	}
//	
//	private String getTablePKSetMethod() {
//		 String id = AnnoationUtil.getId(classz);
//		 id = ClassObjectConverter.fieldToClazzSetMethod(id);
//		 return id;
//	}

	public Class<T> getClassz() {
		return classz;
	}

	public T findEntityById(T t) throws Exception {
			
		List<Expression> wheres = new ArrayList<Expression>();

		Map<String, Object> conditions = SQLFactory.getNotNullFields(t);
		
		Set<Entry<String, Object>> entrySet = conditions.entrySet();
		
		for(Entry<String, Object> entry : entrySet)
		{
			Expression expression = new Expression(entry.getKey().toString(), entry.getValue());
			wheres.add(expression);
		}
		
		String selectSql = SQLFactory.selectSql(t,wheres);
		
		long end2 = System.currentTimeMillis();

		T queryOne = excetueManager.queryOne(selectSql, classz);

		long end3 = System.currentTimeMillis();

		logger.info("执行sql : {} use:{}ms",selectSql,(end3 - end2));
		return queryOne;
	}

//	@SuppressWarnings({ "unchecked" })
//	public <E> List<E> findListBy(T t) throws Exception {
//		
//		Class<E> classz2 = (Class<E>) this.classz;
//		
//		List<E> queryList = findListBy(t,classz2);
//
//		return queryList;
//	}
//	
	@SuppressWarnings("hiding")
	public <T> List<T> findListBy(T t) throws Exception {
		
		List<Expression> wheres = new ArrayList<Expression>();

		Map<String, Object> conditions = SQLFactory.getNotNullFields(t);
		
		Set<Entry<String, Object>> entrySet = conditions.entrySet();
		
		for(Entry<String, Object> entry : entrySet)
		{
			Expression expression = new Expression(entry.getKey().toString(), entry.getValue());
			wheres.add(expression);
		}
		
		String selectSql = SQLFactory.selectSql(t,wheres);
		
		long end2 = System.currentTimeMillis();

		List<T> queryList = (List<T>) excetueManager.queryList(selectSql, getClassz());

		long end3 = System.currentTimeMillis();

//		logger.info("执行sql : {} use:{}ms",selectSql,(end3 - end2));
		return queryList;
	}
	
//	public T findEntityBy(String filed, String value) throws Exception {
//
//		List<Expression> wheres = new ArrayList<Expression>();
//		Expression expression = new Expression(filed, value);
//		wheres.add(expression);
//
//		String selectSql = SQLConverter.selectSql(classz, wheres);
//		long end2 = System.currentTimeMillis();
//
//		T queryOne = excetueManager.queryOne(selectSql, classz);
//
//		long end3 = System.currentTimeMillis();
//
//		logger.info("执行sql : " + selectSql + "use:" + (end3 - end2) + "ms");
//		return queryOne;
//	}

	

//	public boolean deleteEntityByCondition(T t, List<Expression> condition) throws Exception {
//
//		String selectSql = SQLConverter.deleteSqlBy(classz, condition);
//		long end2 = System.currentTimeMillis();
//
//		excetueManager.deleteBy(selectSql);
//		long end3 = System.currentTimeMillis();
//
//		logger.info("执行sql : " + selectSql + "use:" + (end3 - end2) + "ms");
//		return true;
//	}

//	public List<T> findEntityList(List<Expression> where, OrderBy orderBy, WPage page) {
//		return findEntityList(null, where, orderBy, page);
//	}
//
//	public List<T> findEntityList(List<Expression> where, WPage page) {
//		return findEntityList(null, where, null, page);
//	}

//	public List<T> findEntityList(String[] column, List<Expression> wheres, OrderBy orderBy, WPage page) {
//
//		List<T> queryList = null;
//		try {
//			String selectSql = SQLConverter.selectSql(classz, wheres, orderBy, page);
//			long end2 = System.currentTimeMillis();
//
//			queryList = excetueManager.queryList(selectSql, classz);
//			long end3 = System.currentTimeMillis();
//
//			logger.info("执行sql : " + selectSql + "use:" + (end3 - end2) + "ms");
//		} catch (Exception e) {
//		}
//
//		if (queryList == null) {
//			queryList = new ArrayList<T>();
//		}
//
//		return queryList;
//	}
	
//	public List<T> findEntityList(OrderBy orderBy, WPage page)
//	{
//		List<T> queryList = null;
//		try {
//			String selectSql = SQLConverter.selectSql(classz, orderBy, page);
//			long end2 = System.currentTimeMillis();
//
//			queryList = excetueManager.queryList(selectSql, classz);
//			long end3 = System.currentTimeMillis();
//
//			logger.info("执行sql : " + selectSql + "use:" + (end3 - end2) + "ms");
//		} catch (Exception e) {
//		}
//
//		if (queryList == null) {
//			queryList = new ArrayList<T>();
//		}
//
//		return queryList;
//	}

//	public List<Map<String, String>> queryListMap(T t,String sql) {
//		List<Map<String, String>> queryListMap = null;
//		long end2 = System.currentTimeMillis();
//
//		queryListMap = excetueManager.queryListMap(sql);
//
//		long end3 = System.currentTimeMillis();
//
//		logger.info("执行sql : " + sql + "use:" + (end3 - end2) + "ms");
//		return queryListMap;
//	}

//	public <T> List<T> queryListBean(T t,String sql) {
//		List<T> list = null;
//		long end2 = System.currentTimeMillis();
//
//		Class<? extends Object> class1 = t.getClass();
//		list = excetueManager.queryList(sql, class1);
//		long end3 = System.currentTimeMillis();
//
//		logger.info("执行sql : " + sql + "use:" + (end3 - end2) + "ms");
//		return list;
//	}

}
