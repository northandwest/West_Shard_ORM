package testshop;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;
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
