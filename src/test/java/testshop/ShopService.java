package testshop;

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




}
