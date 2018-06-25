package testshop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bucuoa.west.orm.app.extend.ISingleBaseDao;
import com.bucuoa.west.orm.app.extend.SingleBaseService;

import testservice.Shop;



public class ShopService extends SingleBaseService<Shop, Long>  {
	@Autowired
	private ShopDao dao;

	public ISingleBaseDao<Shop, Long> getDao() {
		return dao;
	}

	public void getList() {
		
		List<Shop> queryListBean = getDao().queryListBean(Shop.class, "select * from shop");
		
		for(Shop shop :queryListBean)
		{
			System.out.println(shop.getName());
		}
	}
	
	public void getList2() {
		
//		getDao().queryListBean(clazz, sql, params);
//		
//		getDao().queryListMap(sql);
		
		String sql = "select * from shop where a={} and b={}";
		getDao().queryListMap(sql, "a","b");
		
	}

}
