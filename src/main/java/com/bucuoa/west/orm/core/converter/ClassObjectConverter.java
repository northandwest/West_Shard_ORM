package com.bucuoa.west.orm.core.converter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bucuoa.west.orm.core.base.AnnoationStopWord;
import com.bucuoa.west.orm.core.clazzinfo.ITableInfo;
import com.bucuoa.west.orm.core.uitls.WStringUtils;

public class ClassObjectConverter {
	static	Logger logger = LoggerFactory.getLogger(ClassObjectConverter.class);
	private static Map<String,Map<String,String>> cache = new HashMap<String,Map<String,String>>();

	public static <T> Map<String,Object> getBeanValue(T t)
	{

		Map<String,Object> values = new HashMap<String,Object>();
		try {
			Class<?> class1 = t.getClass();
			
			Field[] fields = class1.getDeclaredFields();
			for (Field field : fields) {

				String key = field.getName();

				List<String> stopWords = AnnoationStopWord.getStopWords();
				if (stopWords.contains(key.toLowerCase())) {
					continue;
				}

				boolean tran = field.isAnnotationPresent(Transient.class);
				if (tran) {
					continue;
				}

				Object value;
				try {
					PropertyDescriptor pd = new PropertyDescriptor(key, class1);
					Method getMethod = pd.getReadMethod();
					value = getMethod.invoke(t);
					if (value == null) // 属性的值为null equals比较会抛异常
					{
						continue;
					}
					
					values.put(key, value);
				} catch (Exception e) {
					logger.error("getBeanValue error,may be is key{} type error!",key,e);
				}
			}

		} catch (Exception e) {
			logger.error("getBeanValue error",e);
		}
	
		return values;
	}
	
	public static <T> Map<String,String> getFiledMap(T t)
	{
		Map<String,String> filedMap = new HashMap<String,String>();
		try {
			Class<?> class1 = t.getClass();
			
			Field[] fields = class1.getDeclaredFields();
			for (Field field : fields) {

				String key = field.getName();

				List<String> stopWords = AnnoationStopWord.getStopWords();
				if (stopWords.contains(key.toLowerCase())) {
					continue;
				}

				boolean tran = field.isAnnotationPresent(Transient.class);
				if (tran) {
					continue;
				}
				Column column = (Column) field.getAnnotation(Column.class);

				filedMap.put(key,column.name());
			}

		} catch (Exception e) {
			logger.error("getFiledMap error",e);
		}
		
		return filedMap;
	}
	
	public static <T> Map<String,String> getFiledMap(Class<T> clazz)
	{
		String cacheKey = clazz.getCanonicalName();
		Map<String, String> filedMap = cache.get(cacheKey);
		if(filedMap == null)
		{
		
			long start = System.currentTimeMillis();
			filedMap = new HashMap<String,String>();
			try {
				
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
	
					String key = field.getName();
	
					List<String> stopWords = AnnoationStopWord.getStopWords();
					if (stopWords.contains(key.toLowerCase())) {
						continue;
					}
	
					boolean tran = field.isAnnotationPresent(Transient.class);
					if (tran) {
						continue;
					}
					
					try {
						Column column = (Column) field.getAnnotation(Column.class);
	
						filedMap.put(key,column.name());
					} catch (Exception e) {
						logger.error("getFiledMap key:[{}] error",key,e);
					}
				}
	
			} catch (Exception e) {
				logger.error("getFiledMap error",e);
			}
			long end = System.currentTimeMillis();
			logger.trace("create {} filedmap use:{}ms",cacheKey,(end-start));
			cache.put(cacheKey,filedMap);
		}
		return filedMap;
	}
	
	public static String fieldToClazzProperties(String text)
	{
	
			StringBuilder sb = new StringBuilder();
			String[] split = text.split("_");
			for(int i = 0; i < split.length; i ++)
			{
				String str = split[i];
				if(i != 0)
				{
					str = WStringUtils.UpStr(str);	
				}
				sb.append(str);
			}
			return sb.toString();
	
	}
	
	public static String fieldToClazzSetMethod(String text)
	{
	
			StringBuilder sb = new StringBuilder();
			sb.append("set");
			String[] split = text.split("_");
			for(int i = 0; i < split.length; i ++)
			{
				String str = split[i];
				str = WStringUtils.UpStr(str);	
				sb.append(str);
			}
			return sb.toString();
	
	}

}
