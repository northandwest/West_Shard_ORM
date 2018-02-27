package com.bucuoa.west.orm.app.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Joiner {
	private StringBuilder sb = new StringBuilder();
	private String on = "";
	private String mapSpliter = "=";
	
	Joiner(String on) {
		this.on = on;
	}
	
	public void withKeyValueSeparator(String mapSpliter)
	{
		this.mapSpliter = mapSpliter;
	}

	public static Joiner on(String on) {

		return new Joiner(on);
	}

	public String join(String[] str) {
		for (int i = 0; i < str.length; i ++)
		{
			String s = str[i];
			
			sb.append(s);
			if(i != str.length -1)
			{
				sb.append(on);
			}
		}

		return sb.toString();
	}
	
	public String join(List<String> list) {
		for (int i = 0; i < list.size(); i ++)
		{
			String s = list.get(i);
			
			sb.append(s);
			if(i != list.size() -1)
			{
				sb.append(on);
			}
		}

		return sb.toString();
	}
	
	
	public String join(Map<String,Object> map) {
		Set<Entry<String, Object>> entrySet = map.entrySet();

		int i = 0; 
		for(Entry<String, Object> entry : entrySet)
		{
			sb.append(entry.getKey()).append(mapSpliter).append(entry.getValue());
			if(i != map.size() -1)
			{
				sb.append(on);
			}
			i ++;
		}

		return sb.toString();
	}
	
	
	

}
