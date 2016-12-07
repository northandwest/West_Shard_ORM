package com.bucuoa.west.orm.shard;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.bucuoa.west.orm.core.base.MasterEnum;
import com.bucuoa.west.orm.core.config.ConfigSingleton;
import com.bucuoa.west.orm.shard.holder.DbMasterOrSlaveHolder;
import com.bucuoa.west.orm.shard.holder.DbShardHolder;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private final static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

	private int redSeed = 0;
	private int writeSeed = 0;

	private static final Random random = new Random(1);

	private final String SLAVE = MasterEnum.SLAVE.getType();
	private final String MASTER = MasterEnum.MASTER.getType();

	@Override
	protected Object determineCurrentLookupKey() {

		ConfigSingleton config = ConfigSingleton.getInstance();
		int masterSlave = config.getIntegerProperties("master_or_slave");
		
		String group = DbShardHolder.getGroup();
		if (group == null) {
			group = "0";
		}

		String dbType = DbMasterOrSlaveHolder.getDbType();
		//不读写分离 	强制走master
		if(masterSlave == 0)
		{
			dbType = MASTER;
		}
		
		if (dbType.equals(MASTER)) {
			int i = returnRandomKu(writeSeed);
			if (i == 0) {
				DbMasterOrSlaveHolder.setMaster(group);
			} else {
				DbMasterOrSlaveHolder.setMaster(group,i);
			}
		} else if (dbType.equals(SLAVE)) {
			int i = returnRandomKu(redSeed);
			if (i == 0) {
				DbMasterOrSlaveHolder.setSlave(group);
			} else {
				DbMasterOrSlaveHolder.setSlave(group,i);
			}
		} else {
			DbMasterOrSlaveHolder.setMaster(group);
		}
		
		return DbMasterOrSlaveHolder.getDbType();
	}

	public static int returnRandomKu(int randowSeed) {

		return random.nextInt(randowSeed);
	}

	public int getRedSeed() {
		return redSeed;
	}

	public void setRedSeed(int redSeed) {
		this.redSeed = redSeed;
	}

	public int getWriteSeed() {
		return writeSeed;
	}

	public void setWriteSeed(int writeSeed) {
		this.writeSeed = writeSeed;
	}

}
