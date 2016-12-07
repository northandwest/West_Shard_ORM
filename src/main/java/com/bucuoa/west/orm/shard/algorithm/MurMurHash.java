package com.bucuoa.west.orm.shard.algorithm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MurMurHash implements IAlgorithm { 
  
    private static final int NODE_NUM = 256; // 每个机器节点关联的虚拟节点个数  
    boolean flag = false;  
    
    private static Integer[] array = null;
    private static String[] tableArray = null;

    //1048576
    public static Integer[] initArrary(int segment)
    {
    	
    	if(array == null)
    	{
    		int count = 1048576 / segment;
    		
	    	array = new Integer[1024*1024];
	    	int phase = 0;
	    	for(int i = 0; i < 1048576; i ++)
	    	{
	    		if(i != 0 && i%count==0)
	    		{
	    			phase ++;
	    		}
	    		array[i] = phase;
	    	}
    	}
    	return array;
    }
    
    public static String[] initTableArrary(int segment,int datasourceNums)
    {
    	
    	if(tableArray == null)
    	{
    		tableArray = new String[segment];
	    	int phase = 0;
	    	for(int i = 0; i < segment; i ++)
	    	{
	    		int datasourceNo = i % datasourceNums;
				if(i != 0 && datasourceNo == 0)
	    		{
	    			phase ++;
	    		}
	    		tableArray[i] = datasourceNo+"_"+phase;
	    	}
    	}
    	return tableArray;
    }

      
  
    /**
     *  MurMurHash算法，是非加密HASH算法，性能很高， 
     *  比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免） 
     *  等HASH算法要快很多，而且据说这个算法的碰撞率很低. 
     *  http://murmurhash.googlepages.com/
     */
    private static Long hash(String key) {
          
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());  
        int seed = 0x1234ABCD;  
          
        ByteOrder byteOrder = buf.order();  
        buf.order(ByteOrder.LITTLE_ENDIAN);  
  
        long m = 0xc6a4a7935bd1e995L;  
        int r = 47;  
  
        long h = seed ^ (buf.remaining() * m);  
  
        long k;  
        while (buf.remaining() >= 8) {  
            k = buf.getLong();  
  
            k *= m;  
            k ^= k >>> r;  
            k *= m;  
  
            h ^= k;  
            h *= m;  
        }  
  
        if (buf.remaining() > 0) {  
            ByteBuffer finish = ByteBuffer.allocate(8).order(  
                    ByteOrder.LITTLE_ENDIAN);  
            // for big-endian version, do this first:  
            // finish.position(8-buf.remaining());  
            finish.put(buf).rewind();  
            h ^= finish.getLong();  
            h *= m;  
        }  
  
        h ^= h >>> r;  
        h *= m;  
        h ^= h >>> r;  
  
        buf.order(byteOrder);  
        return h;  
    }


	@Override
	public ShardResult getShardResult(int shard, int tablenums, Object shardValue) {
		Long hashCode = hash(shardValue.toString());
		Long step1 = Math.abs(hashCode % (1024*1024));
		
		int segment = tablenums * shard;
		
		Integer[] initArrary = initArrary(segment);
		Integer segmentNo = initArrary[step1.intValue()];
		
		String[] initTableArrary = initTableArrary(segment,shard);
		
		String curtable = initTableArrary[segmentNo];
		
		ShardResult result = new ShardResult();
		String dNo = curtable.split("_")[0];
		String tNo = curtable.split("_")[1];
		
		result.setDatasourceNo(Integer.parseInt(dNo));
		result.setTableNo(Integer.parseInt(tNo));
		
		return result;
	}  
	
	
    
    public static void main2() {
    	
    	long time1 = System.currentTimeMillis();
    	for(int i = 0; i < 1024; i ++){
        	long timew = System.currentTimeMillis();

    			Long hash = hash("101客户端"+i);
    	        int k = Math.abs(hash.intValue())%(NODE_NUM*4);
    	        long time4 = System.currentTimeMillis();

    	        System.out.println(i+"=>hash  use:"+(time4-timew)+"ms "+hash+"==>"+k+"->"+(k%4)+"=>"+(k%256));
    	}
    	
    	long time2 = System.currentTimeMillis();
    }  
    
    public static void main(String[] args) {
    	
    	long time1 = System.currentTimeMillis();
    	for(int i = 0; i < 100; i ++){
        	long timew = System.nanoTime();
        	MurMurHash hash = new MurMurHash();
        	hash.getShardResult(4,256,timew);
    	}
    	
    	long time2 = System.currentTimeMillis();
    }  
      
}  