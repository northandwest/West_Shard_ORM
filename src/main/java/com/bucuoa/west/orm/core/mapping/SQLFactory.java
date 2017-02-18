package com.bucuoa.west.orm.core.mapping;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;
import com.bucuoa.west.orm.core.base.DialectEnum;

/**
 * sql生成类
 * 
 * @author jake 把对象直接映射成sql
 */
public class SQLFactory {
	final static Logger logger = LoggerFactory.getLogger(SQLFactory.class);

	private static String defaultDialect = "mysql";
	private static Mapping builder = null;

	private static Mapping getBuilder() {
		MappingFactory factory = new DefaultMappingFactory();

		if (builder == null) {

			if (defaultDialect.equals(DialectEnum.MYSQL.getDialect())) {
				builder = factory.createMysql();
			}
		}

		return builder;
	}

	public static <T> String insertSql(T t) throws Exception {
		Mapping builder = getBuilder();
		long start = System.currentTimeMillis();

		String sql = builder.buildInsert(t);
		long end = System.currentTimeMillis();
		logger.info("build sql:{} use:{}ms ", sql, (end - start));
		return sql;
	}

	public static <T> String selectCountSql(T t, List<Expression> wheres) throws Exception {
		Mapping builder = getBuilder();
		long start = System.currentTimeMillis();

		String sql = builder.buildSelectCount(t, wheres);

		long end = System.currentTimeMillis();
		logger.info("build sql:{} use:{}ms", sql, (end - start));
		return sql;
	}

	public static <T> String selectSql(T t, List<Expression> wheres, OrderBy orderBy, WPage page) throws Exception {

		Mapping builder = getBuilder();
		long start = System.currentTimeMillis();

		String sql = builder.buildSelect(t, wheres, orderBy, page);

		long end = System.currentTimeMillis();
		logger.info("build sql:{} use:{}ms", sql, (end - start));
		return sql;
	}

	public static <T> String selectSql(T t, OrderBy orderBy, WPage page) throws Exception {

		Mapping builder = getBuilder();
		long start = System.currentTimeMillis();

		String sql = builder.buildSelect(t, orderBy, page);

		long end = System.currentTimeMillis();
		logger.info("build sql:{} use:{}ms", sql, (end - start));
		return sql;
	}

	// public static <T> String selectOneSql(T t)throws Exception {
	//
	// Mapping builder = getBuilder();
	// long start = System.currentTimeMillis();
	//
	// String sql = builder.buildSelect(t,null,null);
	//
	// long end = System.currentTimeMillis();
	// logger.info("use:" + (end - start) + "ms " + sql );
	// return sql;
	// }

	public static <T> String updateSql(T t) throws Exception {
		Mapping builder = getBuilder();
		long start = System.currentTimeMillis();

		String sql = builder.buildUpdate(t);

		long end = System.currentTimeMillis();
		logger.info("build sql:{} use:{}ms", sql, (end - start));

		return sql;
	}

	public static <T> String updateSql(T t, Long[] ids) throws Exception {
		Mapping builder = getBuilder();
		long start = System.currentTimeMillis();

		String sql = builder.buildUpdate(t, ids);

		long end = System.currentTimeMillis();
		logger.info("build sql:{} use:{}ms", sql, (end - start));

		return sql;
	}

	public static <T> String deleteSql(T t) throws Exception {
		Mapping builder = getBuilder();
		long start = System.currentTimeMillis();

		String sql = builder.buildDelete(t);

		long end = System.currentTimeMillis();
		logger.info("build sql:{} use:{}ms", sql, (end - start));

		return sql;
	}

	public static <T> String deleteSqlBy(T t, List<Expression> wheres) throws Exception {
		Mapping builder = getBuilder();
		long start = System.currentTimeMillis();

		String sql = builder.buildDelete(t);

		long end = System.currentTimeMillis();
		logger.info("build sql:{} use:{}ms", sql, (end - start));

		return sql;
	}

	public static <T> String selectSql(T t, List<Expression> wheres) throws Exception {
		return selectSql(t, wheres, null, null);
	}

	public static <T> String selectSql(T t, List<Expression> wheres, WPage page) throws Exception {
		return selectSql(t, wheres, null, page);
	}

	public static <T> String selectSql(T t, List<Expression> wheres, OrderBy orderBy) throws Exception {
		return selectSql(t, wheres, orderBy, null);
	}

	public static <T> Map<String, Object> getNotNullFields(T t) throws Exception {
		Mapping builder = getBuilder();

		Map<String, Object> map = builder.getNotNullFields(t);

		return map;
	}

}
