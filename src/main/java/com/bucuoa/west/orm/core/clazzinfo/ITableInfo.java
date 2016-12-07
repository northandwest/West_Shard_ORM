package com.bucuoa.west.orm.core.clazzinfo;

import java.util.List;
import java.util.Map;

public interface ITableInfo {
	String getTableName()  throws Exception ;

	String getPk();
	
	boolean isAutoIncrement();

	List<String> getFields();
	
	Map<String,String> getFieldsType();
	
	String getFieldName(String pname);
}
