package testshop;

import org.springframework.stereotype.Repository;

import com.bucuoa.west.orm.app.extend.ISingleBaseDao;
import com.bucuoa.west.orm.app.extend.spring.SpringSingleBaseDao;

import testservice.Shop;
@Repository
public class ShopDao extends SpringSingleBaseDao<Shop, Long> implements ISingleBaseDao<Shop, Long>{


}						 
								 
									 
								 
								