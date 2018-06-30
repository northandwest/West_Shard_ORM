package testservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bucuoa.west.orm.app.extend.IShardBaseDao;
import com.bucuoa.west.orm.app.extend.ShardBaseService;

@Service
public class ActService extends ShardBaseService<Act,Long> implements IShardBaseDao<Act,Long>{
	@Autowired
	private ActDao dao;

	public IShardBaseDao getDao() {
		return dao;
	}
	



}
