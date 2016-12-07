package com.bucuoa.west.orm.core.uitls;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WStringUtils {
	public static String UpStr(String str) {
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}

//	public static String join(String join, String... strAry) {
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < strAry.length; i++) {
//			if (i == (strAry.length - 1)) {
//				sb.append(strAry[i]);
//			} else {
//				sb.append(strAry[i]).append(join);
//			}
//		}
//
//		return new String(sb);
//	}
	
	public static String join(String join, Object... strAry) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strAry.length; i++) {
			if (i == (strAry.length - 1)) {
				sb.append(strAry[i].toString());
			} else {
				sb.append(strAry[i].toString()).append(join);
			}
		}

		return new String(sb);
	}

	public static String arround(Object temp, String p) {
		StringBuilder sb = new StringBuilder();
		sb.append(p).append(temp).append(p);

		return sb.toString();
	}

	public static String cutLastDot(String temp) {
		String t$ = "";
		try {
			t$ = temp.substring(0, temp.lastIndexOf(","));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t$;
	}

	public static String format(Date date)
	{
		String format_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

		return format_date;
	}

}
