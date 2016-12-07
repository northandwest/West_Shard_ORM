package testshop;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bucuoa.west.orm.shard.algorithm.IAlgorithm;
import com.bucuoa.west.orm.shard.algorithm.MurMurHash;
import com.bucuoa.west.orm.shard.algorithm.ShardResult;

public class TestMurHash {

	public static void main(String[] args) {
		IAlgorithm mur = new MurMurHash();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < 10; i++) {
			ShardResult shardResult = mur.getShardResult(2, 16, i);
			int tableNo = shardResult.getTableNo();
			int datasourceNo = shardResult.getDatasourceNo();
//			System.out.println(i + "==>" + shardResult.getDatasourceNo() + "=" + tableNo);

			String key = String.valueOf(tableNo);
			Integer object = map.get(key);

			if (object == null) {
				object = 0;
			} else {
				object++;
			}

			map.put(key, object);
			
			String key2 = "db_"+String.valueOf(datasourceNo);
			Integer object2 = map.get(key2);
			if (object2 == null) {
				object2 = 0;
			} else {
				object2 ++;
			}
			
			map.put(key2, object2);


		}
		
		Set<Entry<String, Integer>> entrySet = map.entrySet();
		for(Entry entry:entrySet)
		{
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
	}

}
