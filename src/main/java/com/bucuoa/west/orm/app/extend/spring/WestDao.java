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

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象的id
	 * @throws Exception
	 */
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

	/**
	 * 属性没有值的不修改
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void updateEntity(T entity) throws Exception {
		long start = System.currentTimeMillis();
		String updateSql = SQLFactory.updateSql(entity);

		excetueManager.executeUpdateSql(updateSql);

		long end = System.currentTimeMillis();

//		logger.info("执行sql:{} use:{}ms", updateSql, (end - start));
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
	/**
	 * 根据sql语句查询所有符合条件的实体数（没有使用防注入的方法）
	 * 
	 * @param condition
	 *            传入的sql。
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * 根据id删除实体对象（物理删除）
	 * 
	 * @param id
	 *            实体对象的id
	 * @return 是否删除成功; true：删除成功; false：删除失败
	 * @throws Exception
	 */
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
