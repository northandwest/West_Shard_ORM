package com.bucuoa.west.orm.core.converter;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bucuoa.west.orm.core.clazzinfo.ShardTableInfo;
import com.bucuoa.west.orm.core.clazzinfo.SingleTableInfo;
import com.bucuoa.west.orm.core.clazzinfo.ITableInfo;
import com.bucuoa.west.orm.core.clazzinfo.TableinfoFactory;

/**
 * 数据库字段封装成对象
 * 
 * @author jake
 *
 */
public class DBObjectConverter {

	private static <T> ITableInfo getTableInfo(T t) {
		
		ITableInfo info =  TableinfoFactory.create(t);
		
		return info;
	}

	private static Logger logger = LoggerFactory.getLogger(DBObjectConverter.class);

	public static <T> List<T> getListBean(Class<T> clazz, ResultSet rst)
			throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException {

		List<T> list = new ArrayList<T>();
		T tempInstance = clazz.newInstance();
		ITableInfo tableInfo = getTableInfo(tempInstance);

		List<String> fields = tableInfo.getFields();
		Map<String, String> fieldsType = tableInfo.getFieldsType();

		while (rst.next()) {

			T item = clazz.newInstance();

			for (String filedname : fields) {

				try {
					String objectFileName = ClassObjectConverter.fieldToClazzProperties(filedname);
					String typex = fieldsType.get(objectFileName);
					if(typex == null)
					{
						logger.error("getListBean filedname:{} no type", filedname);
						continue;
					}
					
					if (typex.equals("java.util.Date")) {
						Date tempDate = rst.getTimestamp(filedname);
						if (tempDate != null) {
							BeanUtils.setProperty(item, objectFileName, tempDate);
						}

					} else {
						Object tempStr = rst.getString(filedname);
						BeanUtils.setProperty(item, objectFileName, tempStr);
					}

				} catch (SQLException e) {
					logger.error("getListBean", e);
				}
			}

			list.add(item);
		}

		return list;
	}

	public static List<Map<String, Object>> getListMap(ResultSet rst, List<String> columns) throws SQLException {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		while (rst.next()) {

			Map<String, Object> map = new HashMap<String, Object>();
			for (String column : columns) {

				try {

					Object temp = rst.getObject(column);
					if (temp != null) {
						column = ClassObjectConverter.fieldToClazzProperties(column);
						map.put(column, temp);
					}
				} catch (SQLException e) {
					logger.error("getListMap", e);
				}

			}

			list.add(map);

		}
		return list;
	}

	public static List<String> getColumns(ResultSet rst) throws SQLException {

		List<String> columns = new ArrayList<String>();

		ResultSetMetaData metaData = rst.getMetaData();
		int numCols = metaData.getColumnCount();

		for (int i = 1; i <= numCols; i++) {
			String columnName = metaData.getColumnName(i);
			columns.add(columnName);
		}

		return columns;
	}

	public static <T> T getOneBean(Class<T> clazz, ResultSet rst)
			throws SQLException, IllegalAccessException, InvocationTargetException {

		T item = null;
		
		ITableInfo tableInfo = null;
		try {
			T tempInstance = clazz.newInstance();
			tableInfo = getTableInfo(tempInstance);

		} catch (InstantiationException e2) {
			logger.error("getListBean tableInfo error", e2);
		}

		List<String> fields = tableInfo.getFields();
		Map<String, String> fieldsType = tableInfo.getFieldsType();
		
		if (rst.next()) {

			try {
				item = clazz.newInstance();
			} catch (InstantiationException e1) {
				logger.error("getColumns InstantiationException", e1);
			} catch (IllegalAccessException e1) {
				logger.error("getColumns IllegalAccessException", e1);
			}


			for (String filedname : fields) {

				try {
					String objectFileName = ClassObjectConverter.fieldToClazzProperties(filedname);
					String typex = fieldsType.get(objectFileName);
					if(typex == null)
					{
						logger.error("getListBean filedname:{} no type", filedname);
						continue;
					}
					
					if (typex.equals("java.util.Date")) {
						Date tempDate = rst.getTimestamp(filedname);
						if (tempDate != null) {
							BeanUtils.setProperty(item, objectFileName, tempDate);
						}

					} else {
						Object tempStr = rst.getString(filedname);
						BeanUtils.setProperty(item, objectFileName, tempStr);
					}

				} catch (SQLException e) {
					logger.error("getListBean", e);
				}
			}

		}

		return item;
	}
}
