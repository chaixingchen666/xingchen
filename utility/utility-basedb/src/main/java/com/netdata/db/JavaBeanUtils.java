/*
 * 
 * 转换oracle表为java文件
 * 
 */

package com.netdata.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
//import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Date;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.netdata.db.DbHelperSQL;

/**
 * 为了链接oracle数据库 生成表对应的javabean
 * 
 * @author sunhong
 *
 */

public class JavaBeanUtils implements Serializable {
	// private PreparedStatement ps = null;
	// private ResultSet rs = null;
	private static Connection con = null;
	// private CallableStatement cst = null;

	// static class Ora {
	// static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	// static final String DATABASE_URL =
	// "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	// static final String DATABASE_USER = "databi";
	// static final String DATABASE_PASSWORD = "123456";
	// static final String DATABASE_TABLE = "bas_dept"; // 需要生成的表名
	// }
	//
	// static class MySql {
	// static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	// static final String DATABASE_URL =
	// "jdbc:mysql://localhost/plusoft_test?useUnicode=true&characterEncoding=GBK";
	// static final String DATABASE_USER = "root";
	// static final String DATABASE_PASSWORD = "1234";
	// }

	public static Connection getOracleConnection() {
		// Connection oc = null;
		try {
			con = DbHelperSQL.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;

		// try {
		// Class.forName(Ora.DRIVER_CLASS);
		// con=DriverManager.getConnection(Ora.DATABASE_URL,Ora.DATABASE_USER,Ora.DATABASE_PASSWORD);
		// return con;
		// } catch (Exception ex) {
		//System.out.println(ex.getMessage());
		// }
		// return con;
	}
	//
	// public static Connection getMySqlConnection() {
	// try {
	// Class.forName(MySql.DRIVER_CLASS);
	// con = DriverManager.getConnection(MySql.DATABASE_URL,
	// MySql.DATABASE_USER, MySql.DATABASE_PASSWORD);
	// return con;
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return con;
	// }

	public static List<Map> getOracleTable(String Table) throws SQLException {
		getOracleConnection();
		//System.out.println("getOracleTable 1");

		List<Map> list = new ArrayList<Map>();
		try {
			DatabaseMetaData m_DBMetaData = con.getMetaData();

			// getColumns(java.lang.String catalog, java.lang.String
			// schema,java.lang.String table, java.lang.String col)
			// ResultSet colrs = m_DBMetaData.getColumns(null,
			// Ora.DATABASE_USER.toUpperCase(), Table.toUpperCase(), "%");
			//			ResultSet colrs = m_DBMetaData.getColumns(null, "FLYANG", Table.toUpperCase(), "%");
			ResultSet colrs = m_DBMetaData.getColumns(null, m_DBMetaData.getUserName(), Table.toUpperCase(), "%");
			// ResultSet colrs =
			// m_DBMetaData.getColumns(null,Ora.DATABASE_USER.toUpperCase(),
			// Table.toUpperCase(),"%");

			//System.out.println("getOracleTable 2");
			while (colrs.next()) {
				//System.out.println("getOracleTable 3");

				Map map = new HashMap();
				String columnName = colrs.getString("COLUMN_NAME");
				String columnType = colrs.getString("TYPE_NAME");
				int datasize = colrs.getInt("COLUMN_SIZE");
				int digits = colrs.getInt("DECIMAL_DIGITS");
				int nullable = colrs.getInt("NULLABLE");
				String remarks = colrs.getString("REMARKS");
				//System.out.println(columnName+" "+columnType+" "+datasize+"
				// "+digits+" "+ nullable);
				map.put("columnName", columnName);
				map.put("columnType", columnType);
				map.put("datasize", datasize);
				map.put("remarks", remarks);
				list.add(map);
				//System.out.println("TABLE_CAT" + "===" +
				// colrs.getString("TABLE_CAT"));
				//System.out.println("TABLE_SCHEM" + "===" +
				// colrs.getString("TABLE_SCHEM"));
				//System.out.println("TABLE_NAME" + "===" +
				// colrs.getString("TABLE_NAME"));
				//System.out.println("COLUMN_NAME" + "===" +
				// colrs.getString("COLUMN_NAME"));
				//System.out.println("DATA_TYPE" + "===" +
				// colrs.getString("DATA_TYPE"));
				//System.out.println("TYPE_NAME" + "===" +
				// colrs.getString("TYPE_NAME"));
				//System.out.println("COLUMN_SIZE" + "===" +
				// colrs.getString("COLUMN_SIZE"));
				//System.out.println("BUFFER_LENGTH" + "===" +
				// colrs.getString("BUFFER_LENGTH"));
				//System.out.println("DECIMAL_DIGITS" + "===" +
				// colrs.getString("DECIMAL_DIGITS"));
				//System.out.println("NUM_PREC_RADIX" + "===" +
				// colrs.getString("NUM_PREC_RADIX"));
				//System.out.println("NULLABLE" + "===" +
				// colrs.getString("NULLABLE"));
				//System.out.println("REMARKS" + "===" +
				// colrs.getString("REMARKS"));
				//System.out.println("COLUMN_DEF" + "===" +
				// colrs.getString("COLUMN_DEF"));
				//System.out.println("SQL_DATA_TYPE" + "===" +
				// colrs.getString("SQL_DATA_TYPE"));
				//System.out.println("SQL_DATETIME_SUB" + "===" +
				// colrs.getString("SQL_DATETIME_SUB"));
				//System.out.println("CHAR_OCTET_LENGTH" + "===" +
				// colrs.getString("CHAR_OCTET_LENGTH"));
				//System.out.println("ORDINAL_POSITION" + "===" +
				// colrs.getString("ORDINAL_POSITION"));
				//System.out.println("IS_NULLABLE" + "===" +
				// colrs.getString("IS_NULLABLE"));
			}
			// while(colRet.next()){
			// System.out.print("列名："+colRet.getString("COLUMN_NAME"));
			// System.out.print(" 数据类型是："+colRet.getString("DATA_TYPE"));
			// System.out.print(" 类型名称是："+colRet.getString("TYPE_NAME"));
			// System.out.print(" 列大小是："+colRet.getString("COLUMN_SIZE"));
			//System.out.println(" 注释是："+colRet.getString("REMARKS"));
			// }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return list;
	}

	/**
	 * 把Oracle字段类型 转化为 java类型
	 * 
	 * @param sqlType
	 *            字段类型
	 * @param size
	 *            字段大小
	 * @param scale
	 *            默认=0
	 * @return
	 */
	public static String oracleSqlType2JavaType(String sqlType, int size, int scale) {
		if (sqlType.equals("integer")) {
			return "Integer";
		} else if (sqlType.equals("long")) {
			return "Long";
		} else if (sqlType.equals("float") || sqlType.equals("float precision") || sqlType.equals("double") || sqlType.equals("double precision")) {
			return "Float";
		} else if (sqlType.equals("number") || sqlType.equals("decimal") || sqlType.equals("numeric") || sqlType.equals("real")) {
			if (size > 10)
				scale = 1;
			return scale == 0 ? (size < 10 ? "Integer" : "Long") : "Float";
		} else if (sqlType.equals("varchar") || sqlType.equals("varchar2") || sqlType.equals("char") || sqlType.equals("nvarchar") || sqlType.equals("nchar")) {
			return "String";
		} else if (sqlType.equals("datetime") || sqlType.equals("date") || sqlType.equals("timestamp")) {
			return "Date";
		}
		return "String";
	}

	public static String getItems(List<Map> map, String tablename) {
		// 记得转化成小写
		//System.out.println("getItems begin");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String nowdatetime = df.format(new Date()); // 10分钟
		// Date date=new Date(0);

		StringBuffer sb = new StringBuffer();
		sb.append("package com.oracle.bean;");

		sb.append("\n import java.sql.Types;");
		sb.append("\n import java.text.DateFormat;");
		sb.append("\n import java.text.SimpleDateFormat;");
		sb.append("\n import java.util.Date;");
		sb.append("\n import java.util.HashMap;");
		sb.append("\n import java.util.Map;");
		sb.append("\n import com.comdot.data.DataSet;");
		sb.append("\n import com.comdot.data.Table;");
		//		sb.append("\n import com.common.tool.PinYin;");
		sb.append("\n import com.netdata.db.DbHelperSQL;");
		sb.append("\n import com.netdata.db.ProdParam;");
		sb.append("\n import com.netdata.db.QueryParam;");
		//		sb.append("\n import net.sf.json.JSONObject;");

		sb.append("\n//*************************************");
		sb.append("\n//  @author:sunhong  @date:" + df.format(new Date()));
		sb.append("\n//*************************************");

		sb.append("\n public class " + getUpperOne(tablename.toLowerCase()) + "  implements java.io.Serializable  {");
		// 得到私有属性
		for (Map map0 : map) {
			String columnname = map0.get("columnName").toString();
			String columntype = map0.get("columnType").toString();
			String columnsize = map0.get("datasize").toString();
			String remarks = map0.get("remarks") == null ? "" : map0.get("remarks").toString();
			String javaType = oracleSqlType2JavaType(columntype.toLowerCase(), Integer.parseInt(columnsize), 0);
			String temp = "\n    private " + javaType + " " + getUpperOne(columnname.toLowerCase()) + ";";
			if (!remarks.equals(""))
				temp += "  // " + remarks;
			//System.out.println("name:" + columnname + " type:" + columntype + " size:" + columnsize + " jtype:" + javaType);
			sb.append(temp);
		}
		sb.append("\n private String errmess;");
		sb.append("\n //private String Accbegindate = \"2000-01-01\"; // 账套开始使用日期");

		// 得到getter和setter
		for (Map map0 : map) {
			String columnname = map0.get("columnName").toString();
			String columntype = map0.get("columnType").toString();
			String columnsize = map0.get("datasize").toString();
			String javaType = oracleSqlType2JavaType(columntype.toLowerCase(), Integer.parseInt(columnsize), 0);
			if (!columnname.toLowerCase().equals("lastdate")) {
				String temp = "\n   public void " + "set" + getUpperOne(columnname.toLowerCase()) + "(" + javaType + " " + columnname.toLowerCase() + "){";
				temp += "\n this." + getUpperOne(columnname.toLowerCase()) + " = " + columnname.toLowerCase() + ";";
				temp += "\n}";

				sb.append(temp);

				if (javaType.equals("Date")) {
					temp = "\n public String get" + getUpperOne(columnname.toLowerCase()) + "(){";

					temp += "\n  DateFormat df = new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\");";
					temp += "\n  return df.format(" + getUpperOne(columnname.toLowerCase()) + ");";

					// temp1 = "\n return " + getUpperOne(columnname.toLowerCase()) + ";";
					temp += "\n }";

				} else {

					temp = "\n public " + javaType + " " + "get" + getUpperOne(columnname.toLowerCase()) + "(){";
					temp += "\n return " + getUpperOne(columnname.toLowerCase()) + ";";
					temp += "\n }";
				}
				sb.append(temp);
			}
		}

		sb.append("\n  public String getErrmess() { // 取错误提示");
		sb.append("\n  	return errmess;");
		sb.append("\n  }");
		sb.append("\n  //public void setAccbegindate(String accbegindate) {");
		sb.append("\n  //	  this.Accbegindate = accbegindate;");
		sb.append("\n  //}");
		sb.append("\n  //删除记录 ");
		sb.append("\n  public int Delete() {");

		sb.append("\n	StringBuilder strSql = new StringBuilder();");

		sb.append("\n	//strSql.append(\"select count(*) as num from (\");");
		sb.append("\n	//strSql.append(\"select wareid from warecode where id=\" + id + \" and rownum=1 and statetag=1\");");
		sb.append("\n	//strSql.append(\")\");");

		sb.append("\n	//if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {");
		sb.append("\n	//	errmess = \"当前记录已使用，不允许删除\";");
		sb.append("\n	//	return 0;");
		sb.append("\n	//}");

		sb.append("\n	//strSql.setLength(0);");
		sb.append("\n	//strSql.append(\" update " + tablename + " set statetag=2,lastop='\" + Lastop + \"',lastdate=sysdate\");");
		sb.append("\n	//strSql.append(\" where id=\" + id + \" and accid=\" + Accid);");
		sb.append("\n	//if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {");
		sb.append("\n	//	errmess = \"删除失败！\";");
		sb.append("\n	//	return 0;");
		sb.append("\n   //} else {");
		sb.append("\n	//	errmess = \"删除成功！\";");

		sb.append("\n	return 1;");
		sb.append("\n  //}");
		sb.append("\n  }");

		sb.append("\n //增加记录 ");
		sb.append("\n public int Append() {");

		sb.append("\n	StringBuilder strSql = new StringBuilder();");
		sb.append("\n	StringBuilder strSql1 = new StringBuilder();");
		sb.append("\n	StringBuilder strSql2 = new StringBuilder();");
		sb.append("\n	strSql1.append(\"id,\");");
		sb.append("\n	strSql2.append(\"v_id,\");");
		for (Map map0 : map) {
			String columnname = map0.get("columnName").toString();
			String columntype = map0.get("columnType").toString();
			String columnsize = map0.get("datasize").toString();
			String javaType = oracleSqlType2JavaType(columntype.toLowerCase(), Integer.parseInt(columnsize), 0);
			if (!columnname.toLowerCase().equals("lastdate")) {

				sb.append("\n	if (" + getUpperOne(columnname.toLowerCase()) + " != null) {");
				sb.append("\n		strSql1.append(\"" + columnname.toLowerCase() + ",\");");
				if (javaType.equals("Integer") || javaType.equals("Long") || javaType.equals("Number") || javaType.equals("Float")) {
					sb.append("\n		strSql2.append(" + getUpperOne(columnname.toLowerCase()) + "+\",\");");

				} else {
					sb.append("\n		strSql2.append(\"'\" + " + getUpperOne(columnname.toLowerCase()) + " + \"',\");");
				}
				sb.append("\n	}");
			}
		}

		sb.append("\n	strSql1.append(\"lastdate\");");
		sb.append("\n	strSql2.append(\"sysdate\");");
		sb.append("\n	strSql.append(\"declare \");");
		sb.append("\n	strSql.append(\"   v_id number; \");");
		sb.append("\n	strSql.append(\" begin \");");
		sb.append("\n	strSql.append(\"   v_id:=" + tablename + "_id.nextval; \");");

		sb.append("\n	strSql.append(\"   insert into " + tablename + " (\");");
		sb.append("\n	strSql.append(\"  \" + strSql1.toString());");
		sb.append("\n	strSql.append(\")\");");
		sb.append("\n	strSql.append(\" values (\");");
		sb.append("\n	strSql.append(\"  \" + strSql2.toString());");
		sb.append("\n	strSql.append(\");\");");
		sb.append("\n	strSql.append(\"  select v_id into :id from dual; \");"); // 不加这2句要出错
		sb.append("\n	strSql.append(\" end;\");");
		// sb.append("\n return DbHelperSQL.ExecuteSql(strSql.toString());");

		sb.append("\n	Map<String, ProdParam> param = new HashMap<String, ProdParam>();");
		sb.append("\n	param.put(\"id\", new ProdParam(Types.BIGINT));");
		sb.append("\n	int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);");
		sb.append("\n	//id = Long.parseLong(param.get(\"id\").getParamvalue().toString());");
		// sb.append("\n return ret;");
		sb.append("\n	if (ret < 0) {");
		sb.append("\n		errmess=\"操作异常！\";");
		sb.append("\n		return 0;");
		sb.append("\n	}");
		sb.append("\n	else {");
		sb.append("\n		errmess=\"操作成功！\";");
		sb.append("\n		return 1;");
		sb.append("\n	}");

		sb.append("\n }");

		sb.append("\n   //修改记录 ");
		sb.append("\n   public int Update() {");

		sb.append("\n	StringBuilder strSql = new StringBuilder();");
		sb.append("\n	strSql.append(\"begin \");");

		sb.append("\n   strSql.append(\"   update " + tablename + " set \");");
		for (Map map0 : map) {
			String columnname = map0.get("columnName").toString();
			String columntype = map0.get("columnType").toString();
			String columnsize = map0.get("datasize").toString();
			String javaType = oracleSqlType2JavaType(columntype.toLowerCase(), Integer.parseInt(columnsize), 0);
			if (!columnname.toLowerCase().equals("lastdate")) {
				sb.append("\n	if (" + getUpperOne(columnname.toLowerCase()) + " != null) {");
				if (javaType.equals("Integer") || javaType.equals("Long") || javaType.equals("Number") || javaType.equals("Float")) {
					sb.append("\n		strSql.append(\"" + columnname.toLowerCase() + "=\"+" + getUpperOne(columnname.toLowerCase()) + "+\",\");");

				} else {
					sb.append("\n		strSql.append(\"" + columnname.toLowerCase() + "='\" + " + getUpperOne(columnname.toLowerCase()) + " + \"',\");");
				}
				sb.append("\n	}");
			}

		}
		sb.append("\n   strSql.append(\"lastdate=sysdate\");");
		sb.append("\n   strSql.append(\"  where id=id ;\");");
		sb.append("\n   strSql.append(\"  commit;\");");
		sb.append("\n	strSql.append(\" end;\");");

		sb.append("\n	int ret= DbHelperSQL.ExecuteSql(strSql.toString());");
		sb.append("\n	if (ret < 0) {");
		sb.append("\n		errmess=\"操作异常！\";");
		sb.append("\n		return 0;");
		sb.append("\n	}");
		sb.append("\n	else {");
		sb.append("\n		errmess=\"操作成功！\";");
		sb.append("\n		return 1;");
		sb.append("\n	  }");
		sb.append("\n	}");

		sb.append("\n   // 获得数据列表");
		sb.append("\n   public DataSet GetList(String strWhere, String fieldlist) {");
		sb.append("\n	StringBuilder strSql = new StringBuilder();");
		sb.append("\n	strSql.append(\"select \" + fieldlist);");
		sb.append("\n	strSql.append(\" FROM " + tablename + " \");");
		sb.append("\n	if (strWhere.length()>0) {");
		sb.append("\n		strSql.append(\" where \" + strWhere);");
		sb.append("\n	}");
		sb.append("\n	return DbHelperSQL.Query(strSql.toString());");
		sb.append("\n}");

		sb.append("\n  // 获取分页数据");
		sb.append("\n    public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {");
		sb.append("\n	  StringBuilder strSql = new StringBuilder();");
		sb.append("\n	  strSql.append(\"select \" + fieldlist);");
		sb.append("\n	  strSql.append(\" FROM " + tablename + " \");");
		sb.append("\n     if (strWhere.length()>0) {");
		sb.append("\n		strSql.append(\" where \" + strWhere);");
		sb.append("\n	  }");
		sb.append("\n	qp.setQueryString(strSql.toString());");
		sb.append("\n	return DbHelperSQL.GetTable(qp);");
		sb.append("\n}");

		// 是否存在该记录
		sb.append("\n  public boolean Exists() {");
		sb.append("\n  	StringBuilder strSql = new StringBuilder();");
		sb.append("\n  	strSql.append(\"select count(*) as num  from " + tablename + "\");");
		sb.append("\n//	strSql.append(\" where brandname='\" + Brandname + \"' and statetag=1  and rownum=1 and accid=\" + Accid);");
		sb.append("\n//  	if (Brandid != null)");
		sb.append("\n//  		strSql.append(\" and brandid<>\" + Brandid);");
		sb.append("\n//  	if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {");
		sb.append("\n//  		errmess = \"???:\" + Brandname + \" 已存在，请重新输入！\";");
		sb.append("\n//  		return true;");
		sb.append("\n//  	}");
		sb.append("\n  	return false;");
		sb.append("\n  }");

		sb.append("}");
		return sb.toString();

	}

	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String getUpperOne(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	// public static void main(String[] args) throws IOException {
	public static void TableAll2Java() throws IOException {

		// JavaBeanUtils.sysoutOracleTCloumns("pexam_items_title", "his_yhkf");
		try {
			String tables = "bi_bas_dept";
			String[] arr = tables.split(",");
			for (String string : arr) {
				String name = getUpperOne(string.toLowerCase());
				List<Map> map = getOracleTable(string);
				String a = getItems(map, string);
				File file = new File("g:\\" + name + ".java");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(file, true);// true表示在文件末尾追加
				fos.write(a.getBytes());
				fos.close();// 流要及时关闭
			}
			System.out.println("生成java完成");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int Table2Java(String tablename) throws IOException {

		//System.out.println("Table2Java begin");
		try {
			String filename = getUpperOne(tablename.toLowerCase());

			//System.out.println("Table2Java 1 filename=" + filename);

			List<Map> map = getOracleTable(tablename);
			//System.out.println("Table2Java 2");

			String a = getItems(map, tablename);
			File file = new File("g:\\" + filename + ".java");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file, true);// true表示在文件末尾追加
			fos.write(a.getBytes());
			fos.close();// 流要及时关闭
			//System.out.println("Table2Java ok");
			return 1;
			//System.out.println("生成java完成");
		} catch (

		SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Table2Java err");
		return 0;
		// JavaBeanUtils.sysoutOracleTCloumns("pexam_items_title", "his_yhkf");
		// try {
		// //String tables = "bi_bas_dept";
		// String[] arr = tables.split(",");
		// for (String string : arr) {
		// String name = getUpperOne(string.toLowerCase());
		// List<Map> map = getOracleTable(string);
		// String a = getItems(map, string);
		// File file = new File("g:\\" + name + ".java");
		// if (!file.exists()) {
		// file.createNewFile();
		// }
		// FileOutputStream fos = new FileOutputStream(file, true);//
		// true表示在文件末尾追加
		// fos.write(a.getBytes());
		// fos.close();// 流要及时关闭
		// }
		//System.out.println("生成java完成");
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
