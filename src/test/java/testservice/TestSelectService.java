package testservice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class TestSelectService {

	public static void testSelect()
	{
		ActService service = new ActService();
		
		Act act = new Act();
		
		act.setId(1211212l);
		act.setPlaceId(1022);
		
		try {
			Act findEntity = service.findEntityById(act);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testSelectList()
	{
		ActService service = new ActService();
		
		Act act = new Act();
		
		act.setId(1211212l);
		act.setPlaceId(1022);
		
//		try {
//			List<? extends Object> findListBy = service.findListBy(act);//.findEntityById(act);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public static void testUpdate()
	{
		ActService service = new ActService();
		
		Act act = new Act();
		
		act.setId(1211212l);
		act.setPlaceId(1022);
		act.setNumLimit(12);
		act.setTitle("jd 11.11超级品牌节");
		
		try {
			service.updateEntity(act);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testSave()
	{
		ActService service = new ActService();
		
		Act act = new Act();
		
		act.setId(1211212l);
		act.setPlaceId(1022);
		
		try {
			Act aa2 = service.saveEntity(act);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testDelete()
	{
		ActService service = new ActService();
		
		Act act = new Act();
		
		act.setId(1211212l);
		act.setPlaceId(1022);
		
		try {
			service.deleteEntityBy(act);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		testSave();
		
//		testObjectFill();
	}

	private static void testObjectFill() {
		Act act = new Act();
		
		act.setId(1211212l);
		act.setPlaceId(1022);
		
		Long id = 123456789l;
		
		Class<?> clazz = act.getClass();
		try {
			
			Method method = clazz.getMethod("setId", id.getClass());
			method.invoke(act, id);
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		System.out.println(act.getId());
	}
}
