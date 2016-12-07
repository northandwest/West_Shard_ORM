package west.orm;

import com.bucuoa.west.orm.shard.algorithm.IAlgorithm;
import com.bucuoa.west.orm.shard.algorithm.ShardResult;

public class TestHashAlgorithm {

	public static void main(String[] args) {
		ShardResult shardResult = IAlgorithm.hashAlgorithm.getShardResult(4,  256, 13312327);
		
		int datasourceNums = shardResult.getDatasourceNo();
		int tableNums = shardResult.getTableNo();
		
		System.out.println("datasourceNums=>"+datasourceNums+",tableNums=>"+tableNums);
	}

}
