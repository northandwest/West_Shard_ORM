package west.orm;

import com.bucuoa.west.orm.core.clazzinfo.ShardTableInfo;
import com.bucuoa.west.orm.core.route.RouteFactory;
import com.bucuoa.west.orm.core.route.IRouter;
import com.bucuoa.west.orm.core.uitls.AnnoationUtil;
import com.bucuoa.west.orm.core.uitls.WStringUtils;
import com.bucuoa.west.orm.shard.ShardInfo;

import west.orm.core.Activity;

public class TestShardTable {
	public static void main(String[] args) {
		Activity act = new Activity();
		act.setNumLimit(100);
		act.setPlaceId(123221);
		act.setTitle("生成代码");

		ShardInfo shardInfo = AnnoationUtil.getShardTableInfo(act);
		String x = WStringUtils.join("==>", shardInfo.getTable(), shardInfo.getPolicy(), String.valueOf(shardInfo.getNums()),
				shardInfo.getShardKey());
		// x = stable.getTable() + ":" + stable.getPolicy() + "==>" +
		// stable.getNums() + "==>" + stable.getShardKey();
		System.out.println(x);

		shardInfo = new ShardTableInfo(act).getShardInfo();
		x = WStringUtils.join("==>", shardInfo.getTable(), shardInfo.getPolicy(), String.valueOf(shardInfo.getNums()),
				shardInfo.getShardKey());

		System.out.println(x);

		IRouter router = new RouteFactory().createRouter(shardInfo);

		String newTableName = router.route();
		
		System.out.println(newTableName);
	}
}
