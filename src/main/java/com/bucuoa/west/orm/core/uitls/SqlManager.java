package com.bucuoa.west.orm.core.uitls;

import java.util.Date;

public class SqlManager {
	

    public static String combile(String sql, Object... array) {
        StringBuffer result = new StringBuffer();
        
        for (Object obj : array) {
            int j = sql.indexOf("{}");
            
            String substring = sql.substring(0, j);
            sql = sql.substring(j + 2, sql.length());

            result.append(substring).append(" ");
            
            String param = transactSQLInjection(obj.toString());
            
            if (obj instanceof String || obj instanceof Date) {
               
                result.append("'").append(param).append("'");
               
            } else {
            	
                result.append(param);
            }
            
            result.append(" ");
        }

        return (result.toString());
    }
    
    /**
     * sql注入清理方法
     * @param str
     * @return
     */
    public static String transactSQLInjection(String str)
    {
          return str.replaceAll("([';])+|(--)+","");
    }

}
