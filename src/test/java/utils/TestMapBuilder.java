package utils;

import java.util.Map;

import org.junit.Test;

import com.bucuoa.west.orm.app.utils.MapBuilder;

public class TestMapBuilder {

	@Test
	public void test(){
		 Map<String, String> build = MapBuilder.builder().put("w", "k1").put("w2", "k2").put("w3", "k4").build();
		 
		 String key = build.get("w");
		 
		 System.out.println(key);
		 
	}
}
