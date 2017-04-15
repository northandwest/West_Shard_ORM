package west;

import org.junit.Test;

import com.bucuoa.west.orm.app.common.Expression;

public class ExpressionTest {

	@Test
	public void test() {
		Expression ex = new Expression("x", "'12322d'");
		System.out.println(ex.toString() + " and " + ex.toParameterString());

		ex = new Expression("x", "'12322d'");
		System.out.println(ex.toString() + " and " + ex.toParameterString());
		ex = new Expression("x", "wo ai beij tian anm");
		System.out.println(ex.toString() + " and " + ex.toParameterString());
		
		ex = new Expression("x", 12322d);
		System.out.println(ex.toString() + " and " + ex.toParameterString());

		ex = new Expression("x", 12322l);
		System.out.println(ex.toString() + " and " + ex.toParameterString());

		ex = new Expression("x", 123221212);
		System.out.println(ex.toString() + " and " + ex.toParameterString());
	}

}
