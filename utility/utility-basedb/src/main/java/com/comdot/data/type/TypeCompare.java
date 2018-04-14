package com.comdot.data.type;

import com.comdot.data.DataException;

//通过类型转换来确认是否符合当前类型，如果转换失败则抛出异常
public class TypeCompare {

	public static boolean typeCompare(Object entity, String typeName) throws DataException {
		boolean ret = false;
		try {

			if (typeName == String.class.getName()) {
				if (entity == null)
					entity = "";
				String.valueOf(entity);
				return true;
			}
			if (typeName.equals("NVARCHAR2") || typeName.equals("VARCHAR2") || typeName.equals("VARCHAR")  || typeName.equals("STRING") || typeName.equals("CHAR") || typeName.equals("NVARCHAR")) {
				if (entity == null)
					entity = "";
				String.valueOf(entity);
				return true;
			}
			if (typeName == Integer.class.getName() || typeName.equals("INT") || typeName.equals("BIGINT")) {
				if (entity == null || entity.equals(""))
					entity = "0";
				Integer.valueOf(entity.toString());
				return true;
			}
			if (typeName == Double.class.getName()) {
				if (entity == null || entity.equals(""))
					entity = "0";
				Double.valueOf(entity.toString());
				return true;
			}
			if (typeName == Float.class.getName()) {
				if (entity == null || entity.equals(""))
					entity = "0";
				Float.valueOf(entity.toString());
				return true;
			}
			// if (typeName == Number.class.getName()) {
			if (typeName.equals("NUMBER") || typeName.equals("NUMERIC")) {
				if (entity == null || entity.equals(""))
					entity = "0";
				Float.valueOf(entity.toString());
				return true;
			}
			if (typeName.equals("DATE") || typeName.equals("DATETIME")) {

				String.valueOf(entity.toString());
				return true;
			}
			if (typeName == Boolean.class.getName() || typeName.equals("BIT")) {
				if (entity == null)
					entity = "0";
				Boolean.valueOf(entity.toString());
				return true;
			}

		} catch (Exception e) {
			throw new DataException(" type:" + typeName + " is not compareable");
		}

		return ret;
	}
}
