package com.bucuoa.west.orm.app.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.WPage;

public class RequestConverter {
	private Map<String,Object> request;
	
	private Map<String,String> pageParam = new HashMap<String,String>();

	public RequestConverter(Map<String,Object> request) {
		this.request = request;
	}

	public List<Expression> getWhereCondition() {
		List<Expression> paralist = new ArrayList<Expression>();

		Iterator<String> iter = request.keySet().iterator();
		while (iter.hasNext()) {

			String key = iter.next();
			
			String[] values = (String[]) request.get(key);

			String value = values[0];
			if (value != null && !"".equals(value)) {
				
			if (key.startsWith("filters")) {
					value = value.trim();
				
					String tt[] = key.split("-");
					String name = tt[1];
					String type = tt[3];
					//数值化转换
					Object tempk = null ;
					if(!type.equals("S"))
					{
						tempk = new BigDecimal(value);
					}
					Expression and = new Expression(name,tempk);
					paralist.add(and);
				}
			
			
			if(key.equalsIgnoreCase("pageno"))
			{
				pageParam.put("pageno", value);
			}
			
			if(key.equalsIgnoreCase("pagesize"))
			{
				pageParam.put("pagesize", value);
			}
			
			}
		}

		return paralist;
	}

	public int getPageNO() {
		int pageNO = 1;
		try {
//			String[] values = (String[]) request.get("pageno");
			String value = pageParam.get("pageno");
			pageNO = Integer.parseInt(value);
		} catch (Exception e) {
			pageNO = 1;
		}

		if (pageNO < 1) {
			pageNO = 1;
		}
		
		return pageNO;
	}
	
	public int getPageSize() {
		int pageSize = 30;
		try {
//			String[] values = (String[])  request.get("pagesize");
			String value =  pageParam.get("pagesize");
			pageSize = Integer.parseInt(value);
		} catch (Exception e) {
			pageSize = 30;
		}

		if (pageSize < 1) {
			pageSize = 30;
		}
		
		return pageSize;
	}
	
	public WPage getPage()
	{
		WPage page = new WPage();
		page.setPageNo(getPageNO());
		page.setPageSize(getPageSize());
		
		return page;
	}

}
