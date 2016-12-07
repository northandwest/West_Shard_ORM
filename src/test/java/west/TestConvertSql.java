package west;

import java.util.ArrayList;
import java.util.List;

import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.OrderBy;
import com.bucuoa.west.orm.app.common.WPage;
import com.bucuoa.west.orm.core.mapping.SQLFactory;

import west.orm.core.Activity;
import west.orm.core.UlewoUser;

public class TestConvertSql {

	public static void main(String[] args) {
		SQLFactory convert = new SQLFactory();
		
		Activity act = new Activity();
		act.setNumLimit(100);
		act.setPlaceId(123221);
		act.setTitle("生成代码");
		act.setId(100l);
		
		act.setDirectShardValue(5);
		act.setDirectTable(5);
		
		UlewoUser user = new UlewoUser();
		user.setActivationCode("fuck");
		user.setAddress("北京房山区");
		user.setAge("21");
		user.setEmail("yswj81@163.com");
		user.setIsvalid("ok");
		user.setMark(2);
		user.setNickName("碧瑶");
		user.setPassword("abcdefg");
		user.setUserId(10000);
		
//		testInsertSql(convert,user);
		
		for(int i = 0; i< 10; i ++)
		{
			act.setPlaceId(act.getPlaceId()+i);
			testInsertSql(convert, act);
			testDeleteSql(convert, act);
			testUpdateSql(convert, act);
			testSelectSql(convert,act);
		}
		
//		testSelectSql(convert, wheres, orderBy, page);
		
	}

	private static void testSelectSql(SQLFactory convert,Activity obj)  {
		
		
		List<Expression> wheres = new ArrayList();
		Expression expression = new Expression("age","1");
		Expression expression2 = new Expression("user_id",2002);

		wheres.add(expression);
		wheres.add(expression2);

		
		OrderBy orderBy = new OrderBy("user_id","asc"); 
		
		WPage page = new WPage();
		page.setTotalCount(100);
		try {
			String selectSql0 = convert.selectSql(obj, null);
			
			String selectSql01 = convert.selectSql(obj, null,orderBy,page);

			String selectSql = convert.selectSql(obj, wheres);
			
			String selectSql2 = convert.selectSql(obj, wheres, orderBy);
			
			String selectSql3 = convert.selectSql(obj, wheres, page);
			
			String selectSql4 = convert.selectSql(obj, wheres, orderBy, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		System.out.println(selectSql0);
//		System.out.println(selectSql01);
//		System.out.println(selectSql);
//		System.out.println(selectSql2);
//		System.out.println(selectSql3);
//		System.out.println(selectSql4);
	}

	private static void testUpdateSql(SQLFactory convert, Object obj) {
		try {
			String deleteSql = convert.updateSql(obj);
//			System.out.println(deleteSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testInsertSql(SQLFactory convert, Object act) {
		try {
			String deleteSql = convert.insertSql(act);
//			System.out.println(deleteSql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testDeleteSql(SQLFactory sql, Activity act) {
		try {
			String deleteSql = sql.deleteSql(act);
//			System.out.println(deleteSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
