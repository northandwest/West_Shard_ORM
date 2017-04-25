package west;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.ExpressionType;

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
		List<Expression> inner = new ArrayList<Expression>();
		
		Expression exs = new Expression("age",">=", 12);
		Expression ex2 = new Expression("name","=", "'12322d'",ExpressionType.OR);

		inner.add(exs);
		inner.add(ex2);

		Expression exk = new Expression(inner,ExpressionType.AND);
		
		System.out.println(exk.toString());

	}

}
