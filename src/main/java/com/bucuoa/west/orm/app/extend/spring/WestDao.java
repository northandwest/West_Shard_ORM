package com.bucuoa.west.orm.app.extend.spring;

/**
 * 为提高用户体验，单表操作的基础操作和分库分表的参数是不一样的，个性化处理
 */
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.core.ExecuteManager;
import com.bucuoa.west.orm.core.base.IdObject;
import com.bucuoa.west.orm.core.converter.ClassObjectConverter;
import com.bucuoa.west.orm.core.mapping.SQLFactory;
import com.bucuoa.west.orm.core.uitls.AnnoationUtil;

public abstract class WestDao<T, PK extends Serializable> {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected ExecuteManager<T, PK> excetueManager;

	public abstract Class<T> getClassz();

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
			PK id = (PK) excetueManager.executeUpdateSql(insertSql);

			Class<?> clazz = entity.getClass();
			clazz.getMethod(setPK, id.getClass()).invoke(entity, id);
		} else {
			boolean success = excetueManager.executeUpdateSqlNoID(insertSql);
		}
		long end = System.currentTimeMillis();

//		logger.info("执行sql:{} use:{}ms", insertSql, (end - start));
		return entity;
	}

	public void updateEntity(T entity) throws Exception {
		long start = System.currentTimeMillis();
		String updateSql = SQLFactory.updateSql(entity);

		excetueManager.executeUpdateSql(updateSql);

		long end = System.currentTimeMillis();

	}
	
	
	public boolean updateEntityIn(T t,Long[] ids) throws Exception
	{
		String updateSql = SQLFactory.updateSql(t,ids);
		
		excetueManager.executeUpdateSql(updateSql);
		return true;
	}


	public Connection getConnection()
	{
		return excetueManager.getSessionFactory().getSession().getConnection();
	}

	public int getEntityCount(T t) throws Exception {

		String sql = SQLFactory.selectCountSql(t, null);

		if (sql == null) {
			throw new IllegalArgumentException("The sql statement is null.");
		}

		int queryCount = 0;
		long start = System.currentTimeMillis();

		queryCount = excetueManager.queryCount(sql);

		long end = System.currentTimeMillis();

//		logger.info("执行sql:{} use:{}ms", sql, (end - start));

		return queryCount;
	}

	public int getEntityCount(List<Expression> wheres) throws Exception {

		if(wheres != null && wheres.size() < 1)
		{
			return 0;
		}
		String selectCountSql = SQLFactory.selectCountSql(getClassz().newInstance(), wheres);
		int queryCount = 0;
		long start = System.currentTimeMillis();

		queryCount = excetueManager.queryCount(selectCountSql);

		long end = System.currentTimeMillis();

//		logger.info("执行sql:{} use:{}ms", selectCountSql, (end - start));

		return queryCount;
	}

	public boolean deleteEntityBy(T t) throws Exception {
		List<Expression> wheres = new ArrayList<Expression>();
		long end1 = System.currentTimeMillis();

		Map<String, Object> conditions = SQLFactory.getNotNullFields(t);

		Set<Entry<String, Object>> entrySet = conditions.entrySet();

		for (Entry<String, Object> entry : entrySet) {
			Expression expression = new Expression(entry.getKey().toString(), entry.getValue());
			wheres.add(expression);
		}
		String selectSql = SQLFactory.deleteSql(t);
		long start = System.currentTimeMillis();

//		logger.info("生成sql:{} use:{}ms", selectSql, (start - end1));

		excetueManager.deleteBy(selectSql);

		long end = System.currentTimeMillis();

//		logger.info("执行sql:{} use:{}ms", selectSql, (end - start));

		return true;
	}
}
