package com.bucuoa.west.orm.core.uitls;

public class SqlInjectUtils {
	public static String filterSql(String str)
    {
          return str.replaceAll(".*([';]+|(--)+).*", " ");
    }
}
