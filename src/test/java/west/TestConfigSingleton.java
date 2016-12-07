package west;

import com.bucuoa.west.orm.core.config.ConfigSingleton;

public class TestConfigSingleton {

	public static void main(String[] args) {
		System.out.println("---------------单例模式实例--------------");
		System.out.println("第一次取得实例");
		ConfigSingleton s1 = ConfigSingleton.getInstance();
		System.out.println(s1.getProperties("sys_name"));
		System.out.println(s1.getProperties("password"));
		System.out.println("第二次取得实例");
		ConfigSingleton s2 = ConfigSingleton.getInstance();
		System.out.println(s2.getProperties("sys_name"));
		System.out.println(s2.getProperties("password"));
		System.out.println(s2.getProperties("password2"));

		if (s1 == s2) {
			System.out.println(">>>>>s1,s2为同一实例（改进懒汉式）<<<<<");
		}
		System.out.println();
	}

}
