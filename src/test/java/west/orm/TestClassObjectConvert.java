package west.orm;

import com.bucuoa.west.orm.core.converter.ClassObjectConverter;

public class TestClassObjectConvert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fieldToClazzProperties = ClassObjectConverter.fieldToClazzProperties("user_id");
		System.out.println(fieldToClazzProperties);
	}

}
