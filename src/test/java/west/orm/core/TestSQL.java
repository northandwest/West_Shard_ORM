package west.orm.core;

import com.bucuoa.west.orm.core.uitls.SqlManager;

public class TestSQL {

	public static void main(String[] args) {
		
	    String log =  "{\"time\":\"{}\",\"key\":\"{}\",\"hostname\":\"127.0.0.1\",\"processState\":" + "\"" + 0 + "\",\"elapsedTime\":\"{}\",\"count\":\"{}\"}";

	    
		String combile = SqlManager.combile(log, new Object[] { System.currentTimeMillis(), "west orm", System.currentTimeMillis()-1000l,1 });
		
		System.out.println(combile);
	}

}
