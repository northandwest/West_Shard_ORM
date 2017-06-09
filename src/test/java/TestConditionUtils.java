import java.util.List;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.utils.ConditionUtils;

public class TestConditionUtils {

	public static void main(String[] args) {
		ConditionUtils cu = new ConditionUtils();
		List<Expression> conditions =cu.build().add("sex", 1).add("name", "wujiang").getConditions();
		
		int size = conditions.size();
	}

}
