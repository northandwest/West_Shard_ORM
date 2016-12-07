package west.orm.core;

import com.bucuoa.west.orm.app.convert.ConditionConverter;

public class TestConvert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String tt = "123";
		String convert = ConditionConverter.STRING.convert(tt);
		System.out.println(convert);
		
		convert = ConditionConverter.LIKE.convert(tt);
		System.out.println(convert);
		
		convert = ConditionConverter.DATE.convert(tt);
		System.out.println(convert);
		
		convert = ConditionConverter.NUMBER.convert(tt);
		System.out.println(convert);
	}

}
