package com.bucuoa.west.orm.core.clazzinfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bucuoa.west.orm.core.base.AnnoationStopWord;
import com.bucuoa.west.orm.core.base.IdObject;
import com.bucuoa.west.orm.core.uitls.AnnoationUtil;

public abstract class BaseTableInfo {
	final Logger logger = LoggerFactory.getLogger(BaseTableInfo.class);

	protected static Map<String, String> cache = new ConcurrentHashMap<String, String>();
	protected static Map<String, String> tableNameCache = new ConcurrentHashMap<String, String>();

	public abstract Class<?> getClazz();
	
	public String getPk() {

		String name = getClazz().getName();
		String pk = cache.get(name);
		if (pk == null || pk.equals("")) {
			pk = AnnoationUtil.getId(getClazz());
			
			cache.put(name, pk);
		}

		return pk;
	}
	
	public boolean isAutoIncrement(){
		IdObject idObject = AnnoationUtil.getIdObject(getClazz());
		
		return idObject.isAutoIncrement();
	}

	public List<String> getFields() {
		Field[] fields = getClazz().getDeclaredFields();
		List<String> data = new ArrayList<String>();
		try {

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

				try {
					Column column = (Column) field.getAnnotation(Column.class);

					data.add(column.name());
				} catch (Exception e) {
					logger.error("getField key:{} filed{} error", key,field,e);
				}
			}
		} catch (Exception e) {
			logger.error("getFields error", e);
		}
		return data;
	}

	public Map<String, String> getFieldsType() {
		Field[] fields = getClazz().getDeclaredFields();
		Map<String, String> typemap = new HashMap<String, String>();
		try {

			for (Field field : fields) {
				String key = field.getName().trim();
				typemap.put(key, field.getType().getName());
			}
		} catch (Exception e) {
			logger.error("getFields error", e);
		}

		return typemap;
	}
}
