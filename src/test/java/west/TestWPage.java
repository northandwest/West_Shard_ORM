package west;

import com.bucuoa.west.orm.app.common.WPage;

public class TestWPage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = -1; i < 10; i ++)
		{
			printTest(i);
		}

	}

	private static void printTest(int pageNo) {
		WPage page = new WPage();
		page.setTotalCount(150);
		page.setPageNo(pageNo);
		System.out.println("-------------=="+pageNo+"==-----------------");
		System.out.println("prePageNo:"+page.getPrevPageNo());
		System.out.println("PageNo:"+page.getPageNo());
		System.out.println("nextPageNo:"+page.getNextPageNo());
		System.out.println("getTotalPage:"+page.getTotalPage());
		System.out.println("-------------------------------");

	}

}
