package com.bucuoa.west.orm.core.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;
import com.bucuoa.west.orm.core.clazzinfo.ITableInfo;
import com.bucuoa.west.orm.core.clazzinfo.TableinfoFactory;
import com.bucuoa.west.orm.core.converter.ClassObjectConverter;
import com.bucuoa.west.orm.core.converter.TypeConvert;
import com.bucuoa.west.orm.core.mapping.sqlbuilder.DeleteBuilder;
import com.bucuoa.west.orm.core.mapping.sqlbuilder.InsertBuilder;
import com.bucuoa.west.orm.core.mapping.sqlbuilder.SelectBuilder;
import com.bucuoa.west.orm.core.mapping.sqlbuilder.UpdateBuilder;
import com.bucuoa.west.orm.core.uitls.WStringUtils;

public class MysqlMapping implements Mapping {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static Map<String,ITableInfo> cache = new HashMap<String,ITableInfo>();
		
	private <T> ITableInfo getTableInfo(T t) {
		Class<? extends Object> class1 = t.getClass();
		String cacheKey = class1.getCanonicalName();
		//减少大量类的创建
		ITableInfo iTableInfo = cache.get(cacheKey);
		if(iTableInfo == null)
		{
			long start = System.currentTimeMillis();
			iTableInfo = TableinfoFactory.create(t);
			long end = System.currentTimeMillis();
			
			logger.trace("create {} TableInfo use:{}ms",cacheKey,(end-start));

			cache.put(cacheKey, iTableInfo);
		}

		return iTableInfo;
	}

	@Override
	public <T> String buildUpdate(T t) throws Exception {

		ITableInfo tableInfo = getTableInfo(t);

		String tableName = tableInfo.getTableName();

		StringBuilder sets = new StringBuilder();
		StringBuilder where = new StringBuilder();

		List<String> fields = tableInfo.getFields();
		String pk = tableInfo.getPk();

		Map<String, Object> beanValue = ClassObjectConverter.getBeanValue(t);
		try {

			for (String key : fields) {

				if (!key.equals(pk)) {
					String clazzField = ClassObjectConverter.fieldToClazzProperties(key);

					Object value = beanValue.get(clazzField);
					if (value != null) {
						String convert = TypeConvert.convert(value,",");
						sets.append(key).append("=");
						sets.append(convert);
					}
				}
			}

			String clazzField = ClassObjectConverter.fieldToClazzProperties(pk);

			Object pkValue = beanValue.get(clazzField);

			where.append(pk).append("=").append(pkValue);

		} catch (Exception e) {
			logger.error("buildUpdate error", e);
		}

		String set = WStringUtils.cutLastDot(sets.toString());

		UpdateBuilder update = new UpdateBuilder(tableName);
		update.setSets(set);
		update.setWhere(where.toString());

		return update.toSQL();
	}
	
	@Override
	public <T> String buildUpdate(T t,Long[] ids) throws Exception {

		ITableInfo tableInfo = getTableInfo(t);

		String tableName = tableInfo.getTableName();

		StringBuilder sets = new StringBuilder();
		StringBuilder where = new StringBuilder();

		List<String> fields = tableInfo.getFields();
		String pk = tableInfo.getPk();
		
		Map<String, Object> beanValue = ClassObjectConverter.getBeanValue(t);
		try {

			for (String key : fields) {

				if (!key.equals(pk)) {
					String clazzField = ClassObjectConverter.fieldToClazzProperties(key);
//todo shardkey 不能作为set里修改值
					Object value = beanValue.get(clazzField);
					if (value != null) {
						String convert = TypeConvert.convert(value,",");
						sets.append(key).append("=");
						sets.append(convert);
					}
				}
			}

			String clazzField = ClassObjectConverter.fieldToClazzProperties(pk);

//			Object pkValue = beanValue.get(clazzField);
			String inIds = WStringUtils.join(",", ids);
			where.append(clazzField).append(" in ").append("(").append(inIds).append(")");

		} catch (Exception e) {
			logger.error("buildUpdate error", e);
		}

		String set = WStringUtils.cutLastDot(sets.toString());

		UpdateBuilder update = new UpdateBuilder(tableName);
		update.setSets(set);
		update.setWhere(where.toString());

		return update.toSQL();
	}

	@Override
	public <T> String buildInsert(T t) throws Exception {
		ITableInfo tableInfo = getTableInfo(t);

		String tableName = tableInfo.getTableName();

		InsertBuilder insert = new InsertBuilder(tableName);

		try {

			StringBuilder param = new StringBuilder();
			StringBuilder values = new StringBuilder();

			List<String> fields = tableInfo.getFields();

			Map<String, Object> beanValue = ClassObjectConverter.getBeanValue(t);
			for (String key : fields) {

				String clazzField = ClassObjectConverter.fieldToClazzProperties(key);

				Object value = beanValue.get(clazzField);
				if (value != null) {
					param.append(key).append(",");

					String convert = TypeConvert.convert(value,",");
					values.append(convert);
				}
			}

			String showFileds = WStringUtils.cutLastDot(param.toString());
			String showValues = WStringUtils.cutLastDot(values.toString());

			insert.setFields(showFileds);
			insert.setParams(showValues);

		} catch (Exception e) {
			logger.error("buildInsert error", e);
		}

		return insert.toSQL();
	}

	/*
	 * 获取非空值对象字段 ex: nums_limit
	 */
	public <T> Map<String, Object> getNotNullFields(T t) throws Exception {
		ITableInfo tableInfo = getTableInfo(t);

		Map<String, Object> map = new HashMap<String, Object>();
		try {

			List<String> fields = tableInfo.getFields();
			String pk = tableInfo.getPk();

			Map<String, Object> beanValue = ClassObjectConverter.getBeanValue(t);
			for (String field : fields) {

				if (!field.equals(pk)) {

					// String clazzField = field;
					String clazzField = ClassObjectConverter.fieldToClazzProperties(field);

					Object value = beanValue.get(clazzField);
					if (value != null && !value.equals("")) {
						map.put(field, value);
					}
				}
			}

		} catch (Exception e) {
			logger.error("buildInsert error", e);
		}

		return map;
	}

	@Override
	public <T> String buildDelete(T t) throws Exception {
		ITableInfo tableInfo = getTableInfo(t);

		String tableName = tableInfo.getTableName();

		DeleteBuilder delete = new DeleteBuilder(tableName);

		String pk = tableInfo.getPk();
		List<String> fields = tableInfo.getFields();

		Map<String, Object> beanValue = ClassObjectConverter.getBeanValue(t);

		try {

			StringBuilder where = new StringBuilder();
			//主键不为空直接删掉主键 主键为空 按照属性拼where条件

			String clazzField = ClassObjectConverter.fieldToClazzProperties(pk);
			Object pkValue = beanValue.get(clazzField);
			if(pkValue != null){
					where.append(pk).append("=").append(pkValue);
			}else  
			{

				int index = 0;
				for (String key : fields) {

					if (!key.equals(pk)) {
						String clazzField2 = ClassObjectConverter.fieldToClazzProperties(key);

						Object value = beanValue.get(clazzField2);
						if (value != null) {
							String convert = TypeConvert.convert(value," ");
							
							if(index != 0)
							{
								where.append(" and ");
							}
							where.append(key).append("=");
							where.append(convert);
							
							index ++;
						}
					}
				}

				delete.setWhere(where.toString());
			}


		} catch (Exception e) {
			logger.error("buildDelete error", e);
		}

		return delete.toSQL();
	}

	@Override
	public <T> String buildDelete(T t, List<Expression> wheres) throws Exception {

		ITableInfo tableInfo = getTableInfo(t);

		String tableName = tableInfo.getTableName();

		DeleteBuilder delete = new DeleteBuilder(tableName);

		try {

			StringBuilder where = new StringBuilder();

			for (int i = 0; i < wheres.size(); i++) {
				Expression express = wheres.get(i);
				if (i != 0) {
					where.append(" ").append(express.getType()).append(" ");
				}
				where.append(express.toString());
			}

			delete.setWhere(where.toString());

		} catch (Exception e) {
			logger.error("buildDelete error", e);
		}

		return delete.toSQL();
	}

	public <T> String buildSelectCount(T t, List<Expression> wheres) throws Exception {
		ITableInfo tableInfo = getTableInfo(t);

		String tableName = tableInfo.getTableName();

		StringBuilder conditions = null;

		if (wheres != null && wheres.size() > 0) {
			conditions = new StringBuilder();

			for (int i = 0; i < wheres.size(); i++) {
				Expression and = wheres.get(i);

				if (i != 0) {
					conditions.append(and.getType());
				}

				conditions.append(" ").append(and.toString());
			}
		}

		SelectBuilder select = new SelectBuilder(tableName);

		select.setFields("count(*)");
		if (conditions != null) {
			select.setConditions(conditions.toString());
		}

		return select.toSQL();
	}

	@Override
	public <T> String buildSelect(T t, List<Expression> wheres, OrderBy orderBy, WPage page) throws Exception {
		ITableInfo tableInfo = getTableInfo(t);

		String tableName = tableInfo.getTableName();

		StringBuilder conditions = null;

		if (wheres != null && wheres.size() > 0) {
			conditions = new StringBuilder();

			for (int i = 0; i < wheres.size(); i++) {
				Expression and = wheres.get(i);

				if (i != 0) {
					conditions.append(" ").append(and.getType());
				}

				conditions.append(and.toString());
			}
		}

		SelectBuilder select = new SelectBuilder(tableName);

		select.setFields("*");
		if (conditions != null) {
			select.setConditions(conditions.toString());
		}

		if (orderBy != null) {
			select.setOrderby(orderBy.toString());
		}

		if (page != null) {
			select.setLimit(page.toSql());
		}

		return select.toSQL();
	}

	public <T> String buildSelect(T t, OrderBy orderBy, WPage page) throws Exception {
		ITableInfo tableInfo = getTableInfo(t);

		String tableName = tableInfo.getTableName();

		Map<String, Object> beanValue = ClassObjectConverter.getBeanValue(t);

		StringBuilder conditions = new StringBuilder();

		Set<Entry<String, Object>> entrySet = beanValue.entrySet();

		int i = 0;
		for (Map.Entry<String, Object> entry : entrySet) {
			String fieldName = tableInfo.getFieldName(entry.getKey());
			Expression and = new Expression(fieldName, entry.getValue());

			if (i != 0) {
				conditions.append(" ").append(and.getType());
			}

			conditions.append(and.toString());

			i++;
		}

		SelectBuilder select = new SelectBuilder(tableName);

		select.setFields("*");
		if (conditions != null) {
			select.setConditions(conditions.toString());
		}

		if (orderBy != null) {
			select.setOrderby(orderBy.toString());
		}

		if (page != null) {
			select.setLimit(page.toSql());
		}

		return select.toSQL();
	}

}
