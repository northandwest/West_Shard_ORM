package com.bucuoa.west.orm.core.uitls;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bucuoa.west.orm.app.extend.BaseShardEntity;
import com.bucuoa.west.orm.core.base.AnnoationStopWord;
import com.bucuoa.west.orm.core.base.IdObject;
import com.bucuoa.west.orm.shard.ShardInfo;
import com.bucuoa.west.orm.shard.annonation.ShardKey;
import com.bucuoa.west.orm.shard.annonation.ShardTable;

public class AnnoationUtil {
	private final static Logger logger = LoggerFactory.getLogger(AnnoationUtil.class);

	public static <T> String getTablename(T t) {
		Class<?> clazz = t.getClass();
		String tablename =  getTablename(clazz);

		return tablename;
	}

	public static <T> String getTablename(Class<?> clazz) {
		String tablename = null;

		if (clazz.isAnnotationPresent(Table.class)) {

			Table myAnnotation = (Table) clazz.getAnnotation(Table.class);
			tablename = myAnnotation.name();
		}

		return tablename;
	}
	
	public static <T> boolean isSingleTable(Class<T> clazz)
	{
		
		if (clazz.isAnnotationPresent(ShardTable.class)) {
			return false;
		}
		
		return true;
	}
	
	public static <T> ShardInfo getShardTableInfo(T t) {
		Class<?> clazz = t.getClass();
		ShardInfo info = new ShardInfo();
		
		if(t instanceof BaseShardEntity)
		{
			
			int directShardValue = ((BaseShardEntity) t).getDirectShardValue();
			int directTable = ((BaseShardEntity) t).getDirectTable();
			
			info.setDirectNums(directTable);
			info.setDirectShards(directShardValue);
		}
		
		if (clazz.isAnnotationPresent(ShardTable.class)) {

			ShardTable stAnnotation = (ShardTable) clazz.getAnnotation(ShardTable.class);
			String tablename = getTablename(clazz);
			
			info.setPolicy(stAnnotation.policy());
			info.setNums(stAnnotation.nums());
			info.setTable(tablename);
			info.setShards(stAnnotation.shards());
			
			String shardkey = getShardKey(clazz);
			info.setShardKey(shardkey);
		
			Object shardValue = getObjectFieldValue(t,info.getShardKey());
			
			info.setShardValue(shardValue);
		}

		return info;
	}

	public static String getShardKey(Class<?> clazz) {
		String shardkey = null;
		try {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String key = field.getName().trim();

				List<String> stopWords = AnnoationStopWord.getStopWords();
				if (stopWords.contains(key.toLowerCase())) {
					continue;
				}
				
				boolean tran = field.isAnnotationPresent(Transient.class);
				if (tran) {
					continue;
				}

				if (field.isAnnotationPresent(ShardKey.class)) {
					shardkey = key;
					
					break;
				}
			}

		} catch (Exception e) {
			logger.error("getId error", e);
		}
		
		return shardkey;
	}

	public static <T> Object getObjectFieldValue(T t, String shardKey) {
		Object shardValue = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(shardKey, t.getClass());
			Method getMethod = pd.getReadMethod();
			shardValue = getMethod.invoke(t);
			
		} catch (IllegalAccessException e) {
			logger.error("getObjectFieldValue error", e);
		} catch (IllegalArgumentException e) {
			logger.error("getObjectFieldValue error", e);
		} catch (InvocationTargetException e) {
			logger.error("getObjectFieldValue error", e);
		} catch (IntrospectionException e) {
			logger.error("getObjectFieldValue error", e);
		}
		return shardValue;
	}

	public static String getId(Class<?> clazz) {
		String idname = null;

		try {
			Field[] fields = clazz.getDeclaredFields();

			for (Field field : fields) {
				String key = field.getName().trim();
				List<String> stopWords = AnnoationStopWord.getStopWords();
				if (stopWords.contains(key.toLowerCase())) {
					continue;
				}
				
				boolean tran = field.isAnnotationPresent(Transient.class);
				if (tran) {
					continue;
				}

				Column column = (Column) field.getAnnotation(Column.class);

				if (field.isAnnotationPresent(Id.class)) {

					idname = column.name();
					break;
				}
			}

		} catch (Exception e) {
			logger.error("getId error", e);
		}

		return idname;
	}
	
	public static IdObject getIdObject(Class<?> clazz) {
		IdObject idObject = new IdObject();
		
		try {
			Field[] fields = clazz.getDeclaredFields();

			for (Field field : fields) {
				String key = field.getName().trim();
				List<String> stopWords = AnnoationStopWord.getStopWords();
				if (stopWords.contains(key.toLowerCase())) {
					continue;
				}
				
				boolean tran = field.isAnnotationPresent(Transient.class);
				if (tran) {
					continue;
				}

				Column column = (Column) field.getAnnotation(Column.class);

				if (field.isAnnotationPresent(Id.class)) {

					idObject.setName(column.name());
					
					if (field.isAnnotationPresent(GeneratedValue.class)) {
						GeneratedValue generatedValue = (GeneratedValue) field.getAnnotation(GeneratedValue.class);
						GenerationType strategy = generatedValue.strategy();
						
						if(strategy!= null && strategy.equals(GenerationType.IDENTITY))
						{
							idObject.setPkStrategy("autoIncrement");
						}
					}
					break;
				}
			}

		} catch (Exception e) {
			logger.error("getId error", e);
		}

		return idObject;
	}

}
