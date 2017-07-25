package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bucuoa.west.orm.app.common.Pager;

public class TestPager {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testPager() {
		for(int i = -1 ; i < 20; i ++)
		printPager(i);
	}

	private void printPager(int page) {
		Pager pager = new Pager(page,30);

		pager.setTotalRow(100);
		
		List<Map<String,Object>> data= new ArrayList<Map<String,Object>>();
				
		pager.setData(data);
		
		int curPage = pager.getCurPage();
		int pageSize = pager.getPageSize();
		int start = pager.getStart();
		int end = pager.getEnd();
		
		int totalPage = pager.getTotalPage();
		int totalRow = pager.getTotalRow();
		
		int prePage = pager.getPrePage();
		int nextPage = pager.getNextPage();
//		logger.info("当前页:%d 页面大小:%d start:%d end:%d 总页数:%d, 总行数:%d",curPage,pageSize,start,end,totalPage,totalRow);
		String stringTemplate = page+"=>当前页:%d 页面大小:%d start:%d end:%d 总页数:%d, 总行数:%d prePage:%d nextPage:%d isEnd:%b isStart:%b";
		System.out.println(String.format(stringTemplate,curPage,pageSize,start,end,totalPage,totalRow,prePage,nextPage,pager.isEnd(),pager.isStart()));
	}

}
