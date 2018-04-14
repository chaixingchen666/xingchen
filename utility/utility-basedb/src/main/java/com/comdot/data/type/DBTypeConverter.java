package com.comdot.data.type;

import java.util.*;

/*
/
/数据库字段类型和java类型的转换
 *ResultSet有类似的方法，这里自己做了个不全自己补上*
*/

public class DBTypeConverter {

	private static Map<String, String> typeImage;

	//键值对便于查询
	public static String getType(String dbType) {
		if (typeImage == null) {
			typeImage = new HashMap<String, String>();

			typeImage.put("CHAR", "STRING");
			typeImage.put("VARCHAR2", "STRING");
			typeImage.put("NVARCHAR2", "STRING");
			typeImage.put("NVARCHAR", "STRING");
			typeImage.put("VARCHAR", String.class.getName());
			typeImage.put("CHAR", String.class.getName());
			typeImage.put("TEXT", String.class.getName());
			typeImage.put("NUMERIC", Float.class.getName());
			typeImage.put("NUMBER", Float.class.getName());
			typeImage.put("INT", Integer.class.getName());
			typeImage.put("BIGINT", Integer.class.getName());
			typeImage.put("DATETIME", "DATE");
			typeImage.put("BIT", Boolean.class.getName());
		}
		return typeImage.get(dbType);
	}

}
