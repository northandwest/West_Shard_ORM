package west.orm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bucuoa.west.orm.core.clazzinfo.SingleTableInfo;

import west.orm.core.Activity;


public class TestTableInfo {

	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(TestTableInfo.class);

		Activity act = new Activity();
		act.setNumLimit(100);
		act.setPlaceId(123221);
		act.setTitle("生成代码");
		act.setId(100l);

		long time1 = System.currentTimeMillis();
		SingleTableInfo tableinfo = new SingleTableInfo(act);

		String tableName = tableinfo.getTableName();
		String pk = tableinfo.getPk();

		long time2 = System.currentTimeMillis();
		logger.info("use" + (time2 - time1) + "ms " + tableName + ",pk is " + pk);

		// ShardTableInfo tt = new ShardTableInfo(Act.class);
		// tableName = tt.getTableName();
		// pk = tt.getPk();
		for (int i = 0; i < 10; i++) {
			tableName = tableinfo.getTableName();
		}
		long time3 = System.currentTimeMillis();
		logger.info("use" + (time3 - time2) + "ms " + tableName + ",pk is " + pk);

		
		List<String> fields = tableinfo.getFields();
		long time4 = System.currentTimeMillis();
		logger.info("use" + (time4 - time3) + "ms " + tableName + ",pk is " + pk);
	}

}
