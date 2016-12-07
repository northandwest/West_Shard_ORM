package west;

import java.util.ArrayList;
import java.util.List;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.utils.WhereUtils;

public class TestWhereUtils {

	public static void main(String[] args) {

		Expression ex = new Expression("name","wujiang");
		Expression ex2 = new Expression("password","wujiang");
		
		List<Expression> where = new ArrayList<Expression>();
		where.add(ex2);
		where.add(ex);
		
		String sqlParameters = WhereUtils.toSqlParameters(where);
		System.out.println(sqlParameters);

		String parameterString = ex.toParameterString();
		System.out.println(parameterString);
	}

}
