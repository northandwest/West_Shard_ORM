package testservice;

import org.springframework.stereotype.Repository;

import com.bucuoa.west.orm.app.extend.IShardBaseDao;
import com.bucuoa.west.orm.app.extend.spring.SpringShardBaseDao;

import west.orm.core.Activity;
@Repository
public class ActDao extends SpringShardBaseDao<Activity, Long> implements IShardBaseDao<Activity, Long>{

}						 
								 
									 
								 
								