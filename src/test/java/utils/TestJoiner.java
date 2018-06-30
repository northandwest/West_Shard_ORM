package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.bucuoa.west.orm.app.utils.Joiner;

public class TestJoiner {
	@Test
	public void testArray() {
		String join = Joiner.on(",").join(new String[] { "a", "b", "c" });
		System.out.println(join);

		join = Joiner.on(";").join(new String[] { "a", "b", "c" });
		System.out.println(join);

	}

	@Test
	public void testMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("k", "v");
		map.put("k2", "v");
		map.put("k3", "v");

		String join = Joiner.on(",").join(map);

		System.out.println(join);

		Joiner j = Joiner.on(",");
		j.withKeyValueSeparator(">");
		join = j.join(map);

		System.out.println(join);
	}

	@Test
	public void testList() {

		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");

		String join = Joiner.on(",").join(list);
		System.out.println(join);
	}
}
