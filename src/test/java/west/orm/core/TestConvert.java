package west.orm.core;

import java.math.BigDecimal;

public class TestConvert {

	public static void main(String[] args) {
		BigDecimal bd = new BigDecimal("12321212.22");
		System.out.println(bd.intValue()+","+bd.toString()+","+bd);
	}

}
