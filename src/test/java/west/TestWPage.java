package west;

import com.bucuoa.west.orm.app.common.WPage;

public class TestWPage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			WPage page = new WPage();
			page.setPageNo(2);
			page.setTotalCount(64);
			
			System.out.println(page.toString());
	}

}
