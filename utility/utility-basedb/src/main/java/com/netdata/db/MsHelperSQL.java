package com.netdata.db;

//import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
//import java.io.InputStream;
import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import java.text.DateFormat;

//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

//import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.commons.dbcp.BasicDataSource;
//import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;
//import org.apache.tomcat.jdbc.pool.DataSource;

import com.comdot.data.DataSet;
import com.comdot.data.Row;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.LogUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.comdot.data.Meta;
import com.comdot.data.Column;

//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFDataFormat;
//import com.comdot.data.DataException;
import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.ss.util.Region;
//import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MsHelperSQL { //mssql
	// private static DataSource dataSource;

	private static BasicDataSource ds = new BasicDataSource();

	private static String config_properties_filename = Func.getRootPath() + "sqlserver.properties";// oracle配置文件
	private static String errmess = "Err0001:NULL";

	// private static Object cell;
	// private String className = this.getClass().getName();
//	private static String pubdbname = "";
	static {
		//		System.out.println(config_properties_filename);

		File file = new File(config_properties_filename);

		FileInputStream is = null;
		try {
			Properties prop = new Properties();
			// prop.load(MsHelperSQL.class.getResourceAsStream(config_properties_filename));// 配置文件和当前类的class放在一起
			is = new FileInputStream(file);
			prop.load(is);
			// 使用的JDBC驱动的完整有效的java 类名
			//			System.out.println("driver=" + prop.getProperty("driver") + " url=" + prop.getProperty("url"));

//			pubdbname = prop.getProperty("pubdbname");
//			System.out.println("pubdbname=" + pubdbname);

			ds.setDriverClassName(prop.getProperty("driver"));
			// 传递给JDBC驱动的用于建立连接的URL
			ds.setUrl(prop.getProperty("url"));
			// 传递给JDBC驱动的用于建立连接的用户名
			ds.setUsername(prop.getProperty("username"));
			// 传递给JDBC驱动的用于建立连接的密码
			ds.setPassword(prop.getProperty("password"));
			// #初始化连接: 连接池启动时创建的初始化连接数量,1.2版本后支持
			ds.setInitialSize(Integer.parseInt(prop.getProperty("initialSize")));
			// #连接池的最大数据库连接数。设为0表示无限制
			ds.setMaxActive(Integer.parseInt(prop.getProperty("maxActive")));
			// #最大空闲数，数据库连接的最大空闲时间。超过空闲时间，数据库连
			// #接将被标记为不可用，然后被释放。设为0表示无限制
			ds.setMaxIdle(Integer.parseInt(prop.getProperty("maxIdle")));
			// #最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制
			ds.setMaxWait(Integer.parseInt(prop.getProperty("maxWait")));

			// #验证使用的SQL语句
			ds.setValidationQuery(prop.getProperty("validationQuery")); // = SELECT 1
			//System.out.println(prop.getProperty("validationQuery"));
			// #指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除.
			ds.setTestWhileIdle(Boolean.parseBoolean(prop.getProperty("testWhileIdle")));// testWhileIdle = true

			// #借出连接时不要测试，否则很影响性能 testOnBorrow = false
			ds.setTestOnBorrow(Boolean.parseBoolean(prop.getProperty("testOnBorrow")));// testWhileIdle = true
			// #每30秒运行一次空闲连接回收器
			ds.setTimeBetweenEvictionRunsMillis(Integer.parseInt(prop.getProperty("timeBetweenEvictionRunsMillis")));// = 30000
			// #池中的连接空闲30分钟后被回收
			ds.setMinEvictableIdleTimeMillis(Integer.parseInt(prop.getProperty("minEvictableIdleTimeMillis")));// = 1800000
			// #在每次空闲连接回收器线程(如果有)运行时检查的连接数量
			ds.setNumTestsPerEvictionRun(Integer.parseInt(prop.getProperty("numTestsPerEvictionRun")));// =10

			// #超过removeAbandonedTimeout时间后，是否进 行没用连接（废弃）的回收（默认为false，调整为true)
			ds.setRemoveAbandoned(Boolean.parseBoolean(prop.getProperty("removeAbandoned")));
			// #超过时间限制，回收没有用(废弃)的连接（默认为 300秒，调整为180）
			ds.setRemoveAbandonedTimeout(Integer.parseInt(prop.getProperty("removeAbandonedTimeout")));
			//			ds=BasicDataSourceFactory.createDataSource(prop);
		} catch (IOException e) {
			// e.printStackTrace();
			errmess = "Err1048:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL", errmess);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				// e.printStackTrace();
				errmess = "Err1049:" + e.getMessage();
				LogUtils.LogErrWrite("MsHelperSQL", errmess);
			}
		}

	}

	public static Connection getConnection() throws SQLException {

		// Connection conn = ds.getConnection();
		// //LogUtils.LogDebugWrite("getConnection", "" + conn.hashCode());
		// return conn; // 从它的池中获取连接
		try {
			return ds.getConnection();
		} catch (Exception e) {
			errmess = "Err1069:SqlServer初始化连接池失败：" + e.getMessage();
			System.out.println(errmess);
			LogUtils.LogErrWrite("MsHelperSQL.getConnection",errmess);
			return null;
		}
	}

	public static String RunSql(String qry) {
		String retstr = "";
		Connection oc = null;
		Statement om = null;
		ResultSet rs = null;
		try {
			oc = getConnection();
			if (oc != null) {
				om = oc.createStatement();
				rs = om.executeQuery(qry);
				rs.next();
				if (rs.getRow() > 0) {
					String ss = rs.getString(1);
					if (ss == null || ss.length() <= 0)
						ss = "";
					retstr = ss;
				} else
					retstr = "";

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "Err1050:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.RunSql", errmess + " sql=" + qry);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (om != null)
				try {
					om.close();
				} catch (Exception e2) {
				}
			if (oc != null)
				try {
					oc.close();
				} catch (Exception e2) {
				}
		}
		return retstr;
	}

	/// <summary>
	/// 执行SQL语句，返回影响的记录数
	/// </summary>
	/// <param name="SQLString">SQL语句</param>
	/// <returns>影响的记录数</returns>
	public static int ExecuteSql(String qry) {
		int count = -1;

		Connection oc = null;
		Statement om = null;
		try {
			oc = getConnection();
			if (oc != null) {
				om = oc.createStatement();
				om.execute(qry);
				count = 1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			errmess = "Err1051:" + e1.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.ExecuteSql", errmess + " sql=" + qry);
		} finally {
			if (om != null)
				try {
					om.close();
				} catch (Exception e2) {
				}
			if (oc != null)
				try {
					oc.close();
				} catch (Exception e2) {
				}
		}
		return count;

	}

	/// <summary>
	/// 执行SQL语句，返回影响的记录数
	/// </summary>
	/// <param name="SQLString">SQL语句</param>
	/// <returns>影响的记录数</returns>
	public static int ExecuteSql(String qry, int sec) {
		int count = -1;

		Connection oc = null;
		Statement om = null;
		try {
			oc = getConnection();
			if (oc != null) {
				om = oc.createStatement();
				om.setQueryTimeout(sec); // 毫秒
				om.execute(qry);
				count = 1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			errmess = "Err1052:" + e1.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.ExecuteSql", errmess + " sql=" + qry);
		} finally {
			if (om != null)
				try {
					om.close();
				} catch (Exception e2) {
				}
			if (oc != null)
				try {
					oc.close();
				} catch (Exception e2) {
				}
		}
		return count;

	}

	/// <summary>
	/// 执行SQL语句，返回影响的记录数,不带输入参数
	/// </summary>
	/// <param name="SQLString">SQL语句</param>
	/// <returns>影响的记录数</returns>
	public static int ExecuteProc(String qry, Map<String, ProdParam> param) {
		int count = -1;

		Connection oc = null;
		CallableStatement cst = null;
		try {
			//System.out.println("ExecuteProc 1");
			oc = getConnection();
			if (oc != null) {
				cst = oc.prepareCall(qry);

				for (Map.Entry<String, ProdParam> m : param.entrySet()) {
					//System.out.println("ExecuteProc " + m.getKey() + " " + m.getValue().getParamtype());

					cst.registerOutParameter(m.getKey(), m.getValue().getParamtype());
					//System.out.println("=======");
				}
				// cst.setQueryTimeout(times);
				cst.executeUpdate();

				//System.out.println("ExecuteProc 3");
				count = 1;

				for (Map.Entry<String, ProdParam> m : param.entrySet()) {
					String paramkey = m.getKey();
					switch (m.getValue().getParamtype()) {
					case Types.FLOAT:
						m.getValue().setParamvalue(cst.getFloat(paramkey));
						break;
					case Types.BIGINT:
						m.getValue().setParamvalue(cst.getLong(paramkey));
						break;
					case Types.INTEGER:
						m.getValue().setParamvalue(cst.getInt(paramkey));
						break;
					case Types.VARCHAR:
						m.getValue().setParamvalue(cst.getString(paramkey));
						break;
					default:
						break;
					}
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			errmess = "Err1053:" + e1.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.ExecuteProc", errmess + " sql=" + qry);
			//System.out.println("MsHelperSQL.ExecuteProc:"+ errmess);
			// e1.printStackTrace();
		} finally {
			if (cst != null)
				try {
					cst.close();
				} catch (Exception e2) {
				}
			if (oc != null)
				try {
					oc.close();
				} catch (Exception e2) {
				}
		}
		return count;

	}

	public static int ExecuteProc(String qry, Map<String, ProdParam> param, int sec) {
		int count = -1;

		Connection oc = null;
		CallableStatement cst = null;
		try {
			//System.out.println("ExecuteProc 1");
			oc = getConnection();
			if (oc != null) {
				cst = oc.prepareCall(qry);
				for (Map.Entry<String, ProdParam> m : param.entrySet()) {

					cst.registerOutParameter(m.getKey(), m.getValue().getParamtype());
				}

				cst.setQueryTimeout(sec);
				cst.executeUpdate();

				//System.out.println("ExecuteProc 3");
				count = 1;

				for (Map.Entry<String, ProdParam> m : param.entrySet()) {
					String paramkey = m.getKey();
					switch (m.getValue().getParamtype()) {
					case Types.FLOAT:
						m.getValue().setParamvalue(cst.getFloat(paramkey));
						break;
					case Types.BIGINT:
						m.getValue().setParamvalue(cst.getLong(paramkey));
						break;
					case Types.INTEGER:
						m.getValue().setParamvalue(cst.getInt(paramkey));
						break;
					case Types.VARCHAR:
						m.getValue().setParamvalue(cst.getString(paramkey));
						break;
					default:
						break;
					}
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			errmess = "Err1054:" + e1.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.ExecuteProc", errmess + " sql=" + qry);
			//System.out.println("MsHelperSQL.ExecuteProc:"+ errmess);
			// e1.printStackTrace();
		} finally {
			if (cst != null)
				try {
					cst.close();
				} catch (Exception e2) {
				}
			if (oc != null)
				try {
					oc.close();
				} catch (Exception e2) {
				}
		}
		return count;

	}

	/// <summary>
	/// 执行SQL语句，返回影响的记录数,带输入参数
	/// </summary>
	/// <param name="SQLString">SQL语句</param>
	/// <returns>影响的记录数</returns>
	public static int ExecuteProcIn(String qry, Map<String, ProdParam> param) {
		int count = -1;

		Connection oc = null;
		CallableStatement cst = null;
		try {
			oc = getConnection();
			if (oc != null) {
				cst = oc.prepareCall(qry);
				//System.out.println("ExecuteProcIn 2");

				// cst.reg
				for (Map.Entry<String, ProdParam> m : param.entrySet()) {
					//System.out.println("ExecuteProc ：key=" + m.getKey() + " ordid=" + m.getValue().getOrdid() + " type=" + m.getValue().getParamtype() + " count=" + m.getValue().getIotype());
					int ordid = m.getValue().getOrdid();
					if (m.getValue().getIotype() == 1) // 输出参数
					{
						//System.out.println("ExecuteProcIn 2.1 输出参数:" + m.getKey());
						cst.registerOutParameter(ordid, m.getValue().getParamtype());
						//System.out.println("ExecuteProcIn 2.1 end");
					} else {
						//System.out.println("ExecuteProcIn 2.0 输入参数:" + m.getKey());
						// 传入输入参数
						switch (m.getValue().getParamtype()) {
						case Types.FLOAT:
							cst.setFloat(ordid, Float.parseFloat(m.getValue().getParamvalue().toString()));
							break;
						case Types.BIGINT:
							cst.setInt(ordid, Integer.parseInt(m.getValue().getParamvalue().toString()));

							break;
						case Types.INTEGER:
							cst.setInt(ordid, Integer.parseInt(m.getValue().getParamvalue().toString()));
							break;
						case Types.VARCHAR:
							cst.setString(ordid, m.getValue().getParamvalue().toString());
							break;
						default:
							break;

						}
					}
					//System.out.println(m.getKey() + " over");
				}
				//System.out.println("ExecuteProcIn 3");
				cst.executeUpdate();

				//System.out.println("ExecuteProcIn 4");

				for (Map.Entry<String, ProdParam> m : param.entrySet())
					if (m.getValue().getIotype() == 1) // 输出参数
					{
						// String paramkey = m.getKey();
						//System.out.println("ExecuteProcIn 5 key="+paramkey+" type="+m.getValue().getParamtype());

						int ordid = m.getValue().getOrdid();

						switch (m.getValue().getParamtype()) {
						case Types.FLOAT:
							m.getValue().setParamvalue(cst.getFloat(ordid));
							break;
						case Types.BIGINT:
							m.getValue().setParamvalue(cst.getLong(ordid));
							break;
						case Types.INTEGER:
							m.getValue().setParamvalue(cst.getInt(ordid));
							break;
						case Types.VARCHAR:
							m.getValue().setParamvalue(cst.getString(ordid));
							break;
						default:
							break;
						}
						// cst.registerOutParameter(m.getKey(),
						// m.getValue().getParamtype());
					}
				//System.out.println("ExecuteProc 2");
				//System.out.println("ExecuteProcIn end");
				count = 1;

				// om.execute(qry);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			errmess = "Err1055:" + e1.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.ExecuteProc", errmess + " sql=" + qry);
			//System.out.println("MsHelperSQL.ExecuteProc:"+ errmess);
			// e1.printStackTrace();
		} finally {
			if (cst != null)
				try {
					cst.close();
				} catch (Exception e2) {
				}
			if (oc != null)
				try {
					oc.close();
				} catch (Exception e2) {
				}
		}
		return count;

	}

	public static int ExecuteProcIn(String qry, Map<String, ProdParam> param, int sec) {
		int count = -1;

		Connection oc = null;
		CallableStatement cst = null;
		try {
			oc = getConnection();
			if (oc != null) {
				cst = oc.prepareCall(qry);
				cst.setQueryTimeout(sec);
				//System.out.println("ExecuteProcIn 2");

				// cst.reg
				for (Map.Entry<String, ProdParam> m : param.entrySet()) {
					//System.out.println("ExecuteProc ：key=" + m.getKey() + " ordid=" + m.getValue().getOrdid() + " type=" + m.getValue().getParamtype() + " count=" + m.getValue().getIotype());
					int ordid = m.getValue().getOrdid();
					if (m.getValue().getIotype() == 1) // 输出参数
					{
						//System.out.println("ExecuteProcIn 2.1 输出参数:" + m.getKey());
						cst.registerOutParameter(ordid, m.getValue().getParamtype());
						//System.out.println("ExecuteProcIn 2.1 end");
					} else {
						//System.out.println("ExecuteProcIn 2.0 输入参数:" + m.getKey());
						// 传入输入参数
						switch (m.getValue().getParamtype()) {
						case Types.FLOAT:
							cst.setFloat(ordid, Float.parseFloat(m.getValue().getParamvalue().toString()));
							break;
						case Types.BIGINT:
							cst.setInt(ordid, Integer.parseInt(m.getValue().getParamvalue().toString()));

							break;
						case Types.INTEGER:
							cst.setInt(ordid, Integer.parseInt(m.getValue().getParamvalue().toString()));
							break;
						case Types.VARCHAR:
							cst.setString(ordid, m.getValue().getParamvalue().toString());
							break;
						default:
							break;

						}
					}
					//System.out.println(m.getKey() + " over");
				}
				//System.out.println("ExecuteProcIn 3");
				cst.executeUpdate();

				//System.out.println("ExecuteProcIn 4");

				for (Map.Entry<String, ProdParam> m : param.entrySet())
					if (m.getValue().getIotype() == 1) // 输出参数
					{
						// String paramkey = m.getKey();
						//System.out.println("ExecuteProcIn 5 key="+paramkey+" type="+m.getValue().getParamtype());

						int ordid = m.getValue().getOrdid();

						switch (m.getValue().getParamtype()) {
						case Types.FLOAT:
							m.getValue().setParamvalue(cst.getFloat(ordid));
							break;
						case Types.BIGINT:
							m.getValue().setParamvalue(cst.getLong(ordid));
							break;
						case Types.INTEGER:
							m.getValue().setParamvalue(cst.getInt(ordid));
							break;
						case Types.VARCHAR:
							m.getValue().setParamvalue(cst.getString(ordid));
							break;
						default:
							break;
						}
						// cst.registerOutParameter(m.getKey(),
						// m.getValue().getParamtype());
					}
				//System.out.println("ExecuteProc 2");
				//System.out.println("ExecuteProcIn end");
				count = 1;

				// om.execute(qry);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			errmess = "Err1056:" + e1.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.ExecuteProc", errmess + " sql=" + qry);
			//System.out.println("MsHelperSQL.ExecuteProc:"+ errmess);
			// e1.printStackTrace();
		} finally {
			if (cst != null)
				try {
					cst.close();
				} catch (Exception e2) {
				}
			if (oc != null)
				try {
					oc.close();
				} catch (Exception e2) {
				}
		}
		return count;

	}

	// public int RunProc(String storedProcName) {
	// int count = -1;
	//
	// Connection oc = null;
	// CallableStatement cst = null;
	// try {
	// //System.out.println("ExecuteProc 1");
	// oc = getConnection();
	// if (oc != null) {
	// cst = oc.prepareCall(sql);
	// }
	// } catch (SQLException e1) {
	// // TODO Auto-generated catch block
	// errmess = "Err1028:" + e1.getMessage();
	// FyLogUtils.LogErrWrite("MsHelperSQL.RunProc", errmess);
	// //System.out.println("MsHelperSQL.ExecuteProc:"+ errmess);
	// // e1.printStackTrace();
	// } finally {
	// if (cst != null)
	// try {
	// cst.close();
	// } catch (Exception e2) {
	// }
	// if (oc != null)
	// try {
	// oc.close();
	// } catch (Exception e2) {
	// }
	// }
	// return count;
	//
	// // using (OracleConnection connection = new OracleConnection(connectionString))
	// // {
	// // string StrValue;
	// // connection.Open();
	// // OracleCommand cmd;
	// // cmd = BuildQueryCommand(connection, storedProcName, parameters);
	// // StrValue = cmd.ExecuteScalar().ToString();
	// // connection.Close();
	// // return StrValue;
	// // }
	// }

	/// <summary>
	/// 执行查询语句，返回DataSet
	/// </summary>
	/// <param name="SQLString">查询语句</param>
	/// <returns>DataSet</returns>
	public static DataSet Query(String qry) {
		//System.out.println("Query begin");

		DataSet ds = new DataSet();//
		Connection oc = null;
		Statement om = null;
		ResultSet rs = null;
		try {
			oc = getConnection();
			if (oc != null) {
				om = oc.createStatement();
				//				om.execute("alter session set nls_sort='SCHINESE_PINYIN_M'");
				rs = om.executeQuery(qry);
				// rs.next();
				//System.out.println("记录数=" + rs.getRow());
				// ds = ;
				Table tb = new Table();
//				System.out.println("ResultSet2Table begin ");

				tb = ResultSet2Table(rs);
//				System.out.println("ResultSet2Table end row=" + tb.getRowCount());

				//System.out.println("Query 2 ");
				Map<String, Table> map = new HashMap<String, Table>();
				map.put("tb1", tb);
				ds.setTables(map);
				//				System.out.println("Query 3");

				Map<Integer, String> map0 = new HashMap<Integer, String>();
				map0.put(1, "tb1");
				ds.setIndex$name(map0);
				//				System.out.println("Query 4 end");

//				System.out.println("Query ok tb.getRowCount()=" + ds.getTable("tb1").getRowCount());
				//				System.out.println("Query ok");
				return ds;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "Err1057:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.DataSet", errmess + " sql=" + qry);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (om != null)
				try {
					om.close();
				} catch (Exception e2) {
				}
			if (oc != null)
				try {
					oc.close();
				} catch (Exception e2) {
				}
		}
		//System.out.println("Query err");
		return ds;

	}

	public static Table ResultSet2Table(ResultSet rs) {

		//System.out.println("ResultSet2Table begin");

		// DataSet ds = new DataSet();
		Table tb = new Table();

		try {
			ResultSetMetaData cols = rs.getMetaData();
			int columns = cols.getColumnCount();

			Meta meta = new Meta();

			// Map<String, Column> map = new HashMap<String, Column>();

			for (int i = 1; i <= columns; i++) {
				// cols.getColumnName(i) + "\":\"" + rs.getString(i) + "\"";
				Column col = new Column();
				col.setName(cols.getColumnName(i).toUpperCase());
				col.setIndex(i - 1);
				col.setType(cols.getColumnTypeName(i).toUpperCase());
				// map.put(col.getName(), col);

				meta.addColumn(i, col);
//				System.out.println("ResultSet2Table=" + col.getName() + " " + col.getType());
			}

			//System.out.println("ResultSet2Table 1");

			// meta.setColumns(map);
			//System.out.println("map=" + meta.getColumns().toString());
			// meta.get(1).
			//System.out.println("ResultSet2Table-1");
			//System.out.println("aaa " + meta.get("EPNAME").toString());

			tb.setMeta(meta);
//			System.out.println("ResultSet2Table meta.getCount()=" + meta.getCount());

			// int count = 0;
			// rs.beforeFirst();//
			// String strlist = "";
			// rs.first();
			while (rs.next()) {
				// rs.ge
				// String value = "";
				// String colname="";
				// Row row = new Row();
				// tb.addRow(row);
				//				System.out.println("ResultSet2Table-2---1 "+count);

				Row row = new Row(meta);

				// row.setMeta(meta);
//				System.out.println("begin for");
				String typename = "";
				for (int i = 1; i <= columns; i++) {
					Object obj = new Object();

					obj = rs.getString(i);
//					System.out.println(obj.toString() + "  i=" + i);

					if (obj == null)
						obj = "";
					typename = cols.getColumnTypeName(i).toUpperCase();
//					System.out.println("ResultSet2Table 1:typename=" + typename);

					// 如果是字符类型，替换回车换行
					if (typename.equals("VARCHAR") || typename.equals("NVARCHAR")) {

						String ss = obj.toString();
						ss = ss.replace("\n", "\\n");
						ss = ss.replace("\r", "\\r");
						ss = ss.replace("\"", "'");
						obj = ss;
						//System.out.println("new1 for " + i + " " + ss);
						//System.out.println("\n======================");
						//System.out.println("\n " + i + " name:" + cols.getColumnName(i) + " type:" + cols.getColumnTypeName(i) + " values:" + obj.toString());

					}
//					System.out.println("ResultSet2Table: 2:i=" + i);
					// get
					row.set(i, obj);

//					System.out.println("ResultSet2Table: for end -- " + i + " ");

					//System.out.println(i);
					// row.
					// colname=cols.getColumnName(i);
					// value = rs.getString(i) + ",";
					// strlist += rs.getString(i) + ",";
				}
				//System.out.println("end for 1");
				// tb.

				tb.addRow(row);

				//System.out.println("end for 2");

				// count++;
			}
			//
			// Map<String, Table> map = new HashMap<String, Table>();
			// //System.out.println("ResultSet2Table 1");
			// map.put("tb", tb);
			// // ds =
			// ds.setTables(map);

			//System.out.println("ResultSet2Table 2");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "Err1058:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.ResultSet2Table", errmess);
			// }

			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
		}

		//System.out.println("ResultSet2Table ok");

		return tb;
	}

	// 返回所有记录
	public static String DataTable2Json(Table tb) {
//		System.out.println("DataTable2Json:0 ");

		StringBuilder jsonString = new StringBuilder();
		int rows = 0;
		if (tb != null) {
			rows = tb.getRowCount();
		}
//		System.out.println("DataTable2Json:1 rows=" + rows);
		jsonString.append("{\"result\":1,\"msg\":\"操作成功！\"");
		jsonString.append(",\"total\":" + rows);
		jsonString.append("," + Table2Json(tb) + "}");
//		System.out.println("DataTable2Json:2 end");

		return jsonString.toString();
	}

	public static String DataTable2Json(Table tb, String totalstr) {
//		System.out.println("DataTable2Json:00 ");

		//System.out.println("DataTable2Json begin");
		StringBuilder jsonString = new StringBuilder();
		if (totalstr.equals("")) {
			int rows = 0;
			if (tb != null) {
				rows = tb.getRowCount();
			}
			totalstr = String.valueOf(rows);
//			System.out.println("DataTable2Json:11 rows=" + rows);
		}
		// jsonString.append("{\"total\":\"" + totalstr + "\"");
		jsonString.append("{\"result\":1,\"msg\":\"操作成功！\"");
		jsonString.append("," + totalstr);
		jsonString.append("," + Table2Json(tb) + "}");
//		System.out.println("DataTable2Json:22 end");

		//System.out.println("DataTable2Json end");
		return jsonString.toString();
	}

	public static String Table2Json(Table tb) {
		//System.out.println("Table2Json 1.0");
		StringBuilder jsonString = new StringBuilder();
		int rows = 0;
		int cols = 0;
		if (tb != null) {
			//System.out.println("Table2Json 1.1");
			rows = tb.getRowCount();
			//System.out.println("Table2Json 1.2 rows=" + rows);
			cols = tb.getColumnCount();
			//System.out.println("Table2Json 1.3 cols=" + cols);
		}
		//System.out.println("Table2Json 2");
		jsonString.append("\"rows\":[");
		String fh = "";
		// for (int i = 1; i <= rows; i++) {
		// jsonString.append(fh + "{");
		// String fh1 = "";
		// for (int j = 0; j < cols; j++) {
		// String strKey = tb.getRow(i - 1).getColumn(j + 1).getName();
		//
		// String strValue = tb.getRow(i - 1).get(j).toString();
		// jsonString.append(fh1 + "\"" + strKey + "\":\"" + strValue + "\"");
		// fh1 = ",";
		// }
		// jsonString.append("}");
		// fh = ",";
		// }
		for (int i = 0; i < rows; i++) {
			jsonString.append(fh + "{");
			String fh1 = "";
			for (int j = 0; j < cols; j++) {
				String strKey = tb.getRow(i).getColumn(j + 1).getName();

				String strValue = tb.getRow(i).get(j).toString();
				jsonString.append(fh1 + "\"" + strKey + "\":\"" + strValue + "\"");
				fh1 = ",";
			}
			jsonString.append("}");
			fh = ",";
		}

		//System.out.println("Table2Json 3");
		jsonString.append("]");

		return jsonString.toString();
	}

	// table另存为excel
	public static String Table2Excel(Table tb, JSONObject jsonObject) {
		// "columns":[{"brandid":"品牌id"},{"brandname":"品牌名称"}]
		if (!jsonObject.has("columns")) {
			return "0未传入列标题参数";
		}
		// LogUtils.LogDebugWrite("Table2Excel", "begin");
		int rows = 0;
		// int cols = 0;
		if (tb != null) {
			//System.out.println("Table2Json 1.1");
			rows = tb.getRowCount();
			//System.out.println("Table2Json 1.2 rows=" + rows);
			// cols = tb.getColumnCount();
			//System.out.println("Table2Json 1.3 cols=" + cols);
		}
		if (rows == 0) // || cols == 0
			return "";
		// String xlsfilename = "temp/xls_" + Func.getRandomNumA(16) + ".xlsx";
		String xlsfilename = "temp/xls" + Func.DateToStr(new Date(), "yyMMddHHmmss") + Func.getRandomNumA(6) + ".xlsx";
		String outPath = Func.getRootPath() + xlsfilename;
		// LogUtils.LogDebugWrite("Table2Excel", outPath);
		// 创建工作文档对象
		Workbook wb = new XSSFWorkbook();
		JSONArray colArray = jsonObject.getJSONArray("columns");

		XSSFCellStyle headCellStyle = (XSSFCellStyle) wb.createCellStyle(); // 表头单元格式
		headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		Font headfont = wb.createFont();
		headfont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("黑体");
		headCellStyle.setFont(headfont);
		// XSSFCellStyle cellStyle =null;// (XSSFCellStyle) wb.createCellStyle();
		// LogUtils.LogDebugWrite("Table2Excel", "000-1");
		// 创建sheet对象
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		int currRow = 0;

		if (jsonObject.has("reptitle")) // 如果有报表标题，则显示报表标题
		{
			String reptitle = jsonObject.getString("reptitle");
			org.apache.poi.ss.usermodel.Row row = (org.apache.poi.ss.usermodel.Row) sheet1.createRow(currRow);
			Cell cell = ((org.apache.poi.ss.usermodel.Row) row).createCell(0);

			cell.setCellValue(reptitle);
			cell.setCellStyle(headCellStyle);
			sheet1.addMergedRegion(new CellRangeAddress(currRow, currRow, 0, colArray.size() - 1));

			currRow++;
		}

		// 表头行数
		int headrows = 1;
		if (jsonObject.has("headrows"))
			headrows = Integer.parseInt(jsonObject.getString("headrows"));

		// LogUtils.LogDebugWrite("Table2Excel", "loop begin");
		// "columns":[{"fieldno":"brandid","fieldname":"品牌id","fieldtype":"C"},{"fieldno":"brandname","fieldname":"品牌名称","fieldtype":"C"}]
		// int[] arr1=new int[10];
		// LogUtils.LogDebugWrite("Table2Excel", "headrows=" + headrows);

		// org.apache.poi.ss.usermodel.Row row = (org.apache.poi.ss.usermodel.Row) sheet1.createRow(currRow);
		ArrayList<org.apache.poi.ss.usermodel.Row> arrrow = new ArrayList<org.apache.poi.ss.usermodel.Row>();
		// List arrrow = new List();
		for (int i = 0; i < headrows; i++) // 初始表头行
		{
			org.apache.poi.ss.usermodel.Row row1 = (org.apache.poi.ss.usermodel.Row) sheet1.createRow(currRow + i);
			// org.apache.poi.ss.usermodel.Row row = (org.apache.poi.ss.usermodel.Row) sheet1.createRow(currRow);
			arrrow.add(row1);
		}

		// List<org.apache.poi.ss.usermodel.Row> arrrow = new List<org.apache.poi.ss.usermodel.Row>(4);

		// LogUtils.LogDebugWrite("Table2Excel", "111 arrrow.size=" + arrrow.size());

		// 表头
		for (int j = 0; j < colArray.size(); j++) {
			JSONObject jsonObject1 = colArray.getJSONObject(j);
			// LogUtils.LogDebugWrite("Table2Excel", "222");
			String colname = jsonObject1.getString("fieldname");

			int level = 0;
			if (jsonObject1.has("level"))
				level = Integer.parseInt(jsonObject1.getString("level"));
			// LogUtils.LogDebugWrite("Table2Excel", "444");
			Cell cell = ((org.apache.poi.ss.usermodel.Row) arrrow.get(level)).createCell(j);
			// LogUtils.LogDebugWrite("Table2Excel", "colname=" + colname + " ,level=" + level);

			// LogUtils.LogDebugWrite("Table2Excel", j + "=" + colname);
			cell.setCellValue(colname);
			cell.setCellStyle(headCellStyle);
			if (headrows > 1 && jsonObject1.has("m_title0")) {
				colname = jsonObject1.getString("m_title0");

				if (colname.length() > 0) {
					level = 0;
					int row = 0;
					if (jsonObject1.has("m_row0"))
						row = Integer.parseInt(jsonObject1.getString("m_row0"));
					int col = 0;
					if (jsonObject1.has("m_col0"))
						col = Integer.parseInt(jsonObject1.getString("m_col0"));
					if (row > 0 || col > 0)// 合并单元格
					{
						Cell cell1 = ((org.apache.poi.ss.usermodel.Row) arrrow.get(0)).createCell(j);
						cell1.setCellValue(colname);
						sheet1.addMergedRegion(new CellRangeAddress(currRow + level, currRow + level + row, j, j + col));
						cell1.setCellStyle(headCellStyle);
						// sheet.addMergedRegion(new CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
					}
				}
			}
			if (headrows > 1 && jsonObject1.has("m_title1")) {
				colname = jsonObject1.getString("m_title1");
				if (colname.length() > 0) {
					level = 1;
					int row = 0;
					if (jsonObject1.has("m_row1"))
						row = Integer.parseInt(jsonObject1.getString("m_row1"));
					int col = 0;
					if (jsonObject1.has("m_col1"))
						col = Integer.parseInt(jsonObject1.getString("m_col1"));
					if (row > 0 || col > 0)// 合并单元格
					{
						Cell cell2 = ((org.apache.poi.ss.usermodel.Row) arrrow.get(1)).createCell(j);
						cell2.setCellValue(colname);
						sheet1.addMergedRegion(new CellRangeAddress(currRow + level, currRow + level + row, j, j + col));
						cell2.setCellStyle(headCellStyle);
						// sheet.addMergedRegion(new CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
					}
				}
			}
		}
		currRow += headrows;
		// 简单表头
		// for (int j = 0; j < jsonArray.size(); j++) {
		// JSONObject jsonObject1 = jsonArray.getJSONObject(j);
		//
		// Cell cell = ((org.apache.poi.ss.usermodel.Row) row).createCell(j);
		// cell.setCellValue(jsonObject1.getString("fieldname"));
		// cell.setCellStyle(cellStyle1);
		// }

		XSSFCellStyle amtCellStyle = (XSSFCellStyle) wb.createCellStyle();// 数量格式
		amtCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 数字右对齐
		amtCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		XSSFCellStyle CurCellStyle = (XSSFCellStyle) wb.createCellStyle();// 金额格式
		CurCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 数字右对齐
		CurCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		DataFormat df = wb.createDataFormat();
		CurCellStyle.setDataFormat(df.getFormat("0.00"));

		// 表体
		for (int i = 0; i < rows; i++) {

			org.apache.poi.ss.usermodel.Row row = (org.apache.poi.ss.usermodel.Row) sheet1.createRow(currRow);
			// "columns":[{"fieldno":"brandid","fieldname":"品牌id","fieldtype":"C"},{"fieldno":"brandname","fieldname":"品牌名称","fieldtype":"C"}]
			for (int j = 0; j < colArray.size(); j++) {
				JSONObject jsonObject1 = colArray.getJSONObject(j);
				String fieldno = jsonObject1.getString("fieldno").toUpperCase();
				// LogUtils.LogDebugWrite("Table2Excel", "fieldno=" + fieldno);
				String fieldtype = "C";
				if (jsonObject1.has("fieldtype"))
					fieldtype = jsonObject1.getString("fieldtype").toUpperCase();

				Cell cell = ((org.apache.poi.ss.usermodel.Row) row).createCell(j);
				String strValue = "";
				if (fieldtype.equals("*")) {
					strValue = "***";
					cell.setCellValue(strValue);
				} else {
					try {
						strValue = tb.getRow(i).get(fieldno).toString();
					} catch (Exception e) {
						errmess = "Err1046:fieldno=" + fieldno + " ;" + e.getMessage();

						LogUtils.LogErrWrite("MsHelperSQL.Table2Excel", errmess);
						// strValue = "err";
					}
					// N保留2位小数，G原值 P百分比
					if (fieldtype.equals("N")) {

						Float f = (float) 0;
						if (strValue != null && strValue.length() > 0) {
							try {
								f = Float.parseFloat(strValue);
							} catch (Exception e) {

							}
						}
						// LogUtils.LogDebugWrite("Table2Excel 111:", f.toString());
						f = Func.getRound(f, 2);
						// LogUtils.LogDebugWrite("Table2Excel 222:", f.toString());
						cell.setCellValue(f);

						cell.setCellStyle(CurCellStyle);

						// LogUtils.LogDebugWrite("Table2Excel", "a5");
					} else if (fieldtype.equals("G")) {// 原值

						// LogUtils.LogDebugWrite(fieldno, "strValue=" + strValue);
						Float f = (float) 0;
						if (strValue != null && strValue.length() > 0) {
							try {
								f = Float.parseFloat(strValue);
							} catch (Exception e) {

							}
						}
						cell.setCellValue(f);

						cell.setCellStyle(amtCellStyle);

						// LogUtils.LogDebugWrite("Table2Excel", "a5");
					} else {
						// cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 数字右对齐
						cell.setCellValue(strValue);

					}
				}
			}
			currRow++;
		}
		// LogUtils.LogDebugWrite("Table2Excel", "loop end");
		// 表头
		// for (int j = 0; j < jsonArray.size(); j++) {
		// sheet1.autoSizeColumn(j);
		// }
		// 合计行
		org.apache.poi.ss.usermodel.Row rowhj = (org.apache.poi.ss.usermodel.Row) sheet1.createRow(currRow);
		boolean hjok = false;
		for (int j = 0; j < colArray.size(); j++) {
			JSONObject jsonObject1 = colArray.getJSONObject(j);
			if (jsonObject1.has("fieldfooter")) {
				Cell cell = ((org.apache.poi.ss.usermodel.Row) rowhj).createCell(j);
				String fieldtype = "C";
				if (jsonObject1.has("fieldtype"))
					fieldtype = jsonObject1.getString("fieldtype").toUpperCase();

				String strValue = jsonObject1.getString("fieldfooter");
				if (strValue == null || strValue.length() <= 0) {
					strValue = "";
					fieldtype = "C";
				}

				if (fieldtype.equals("C")) {// 如果是字符型，直接显示
					cell.setCellValue(strValue);

				} else
					try {
						Float f = Float.parseFloat(strValue);
						cell.setCellValue(f);
						if (fieldtype.equals("N"))
							cell.setCellStyle(CurCellStyle);
						// if (fieldtype.equals("G"))
						else
							cell.setCellStyle(CurCellStyle);

					} catch (Exception e) {

					}
				hjok = true;

			}

			sheet1.autoSizeColumn(j);
		}
		if (hjok) {// 如果有合计，第1列显示合计这两个字
			Cell cell = ((org.apache.poi.ss.usermodel.Row) rowhj).createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headCellStyle);
		}

		String retcs = "";
		// 创建文件流
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(outPath);
			// LogUtils.LogDebugWrite("Table2Excel", "1111");
			wb.write(stream);
			// LogUtils.LogDebugWrite("Table2Excel", "2222");
			retcs = "1" + xlsfilename;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			xlsfilename = "";
			retcs = "0导出失败1:" + e.getMessage();
			errmess = "Err1059:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.Table2Excel", errmess);
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			xlsfilename = "";
			retcs = "0导出失败2:" + e.getMessage();
			errmess = "Err1060:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.Table2Excel", errmess);
			// e.printStackTrace();
		} finally {// 关闭文件流
			if (stream != null)
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					errmess = "Err1061:" + e.getMessage();
					LogUtils.LogErrWrite("MsHelperSQL.Table2Excel", errmess);
				}
		}

		return retcs;

	}

	// json记录另存为excel
	public static String Json2Excel(String jsontable, JSONObject columnsObject) {
		// "columns":[{"brandid":"品牌id"},{"brandname":"品牌名称"}]
		if (!columnsObject.has("columns")) {
			return "0未传入列标题参数！";
		}
		if (jsontable == null || jsontable.equals("")) {

			return "0json表参数无效！";
		}
		// JSONArray jsonArray = jsonObject.getJSONArray("columns");
		JSONObject tb = JSONObject.fromObject(jsontable);
		if (!tb.has("rows")) {
			return "0未传入有效数据记录！";
		}

		// {"total":"7","rows":[{"WAREID":"1261806","WARENO":"001","AMOUNT":"134","KEYID":"1","ROWNUMBER":"1","AMOUNT1":"18","SIZEID1":"32290","SIZENAME1":"XS","AMOUNT2":"12","SIZEID2":"32291","SIZENAME2":"S","AMOUNT3":"16","SIZEID3":"32292","SIZENAME3":"M","AMOUNT4":"16","SIZEID4":"32293","SIZENAME4":"L","AMOUNT5":"18","SIZEID5":"32294","SIZENAME5":"XL","AMOUNT6":"18","SIZEID6":"32295","SIZENAME6":"XXL","AMOUNT7":"18","SIZEID7":"32296","SIZENAME7":"3XL","AMOUNT8":"18","SIZEID8":"32297","SIZENAME8":"4XL"},{"WAREID":"1261808","WARENO":"002","AMOUNT":"176","KEYID":"43","ROWNUMBER":"2","AMOUNT1":"17","SIZEID1":"32290","SIZENAME1":"XS","AMOUNT2":"24","SIZEID2":"32291","SIZENAME2":"S","AMOUNT3":"26","SIZEID3":"32292","SIZENAME3":"M","AMOUNT4":"26","SIZEID4":"32293","SIZENAME4":"L","AMOUNT5":"22","SIZEID5":"32294","SIZENAME5":"XL","AMOUNT6":"17","SIZEID6":"32295","SIZENAME6":"XXL","AMOUNT7":"22","SIZEID7":"32296","SIZENAME7":"3XL","AMOUNT8":"22","SIZEID8":"32297","SIZENAME8":"4XL"},{"WAREID":"1583773","WARENO":"004","AMOUNT":"45","KEYID":"85","ROWNUMBER":"3","AMOUNT1":"7","SIZEID1":"32290","SIZENAME1":"XS","AMOUNT2":"3","SIZEID2":"32291","SIZENAME2":"S","AMOUNT3":"3","SIZEID3":"32292","SIZENAME3":"M","AMOUNT4":"8","SIZEID4":"32293","SIZENAME4":"L","AMOUNT5":"8","SIZEID5":"32294","SIZENAME5":"XL","AMOUNT6":"8","SIZEID6":"32295","SIZENAME6":"XXL","AMOUNT7":"4","SIZEID7":"32296","SIZENAME7":"3XL","AMOUNT8":"4","SIZEID8":"32297","SIZENAME8":"4XL"},{"WAREID":"1586259","WARENO":"003","AMOUNT":"67","KEYID":"121","ROWNUMBER":"4","AMOUNT1":"9","SIZEID1":"32290","SIZENAME1":"XS","AMOUNT2":"9","SIZEID2":"32291","SIZENAME2":"S","AMOUNT3":"8","SIZEID3":"32292","SIZENAME3":"M","AMOUNT4":"17","SIZEID4":"32293","SIZENAME4":"L","AMOUNT5":"6","SIZEID5":"32294","SIZENAME5":"XL","AMOUNT6":"6","SIZEID6":"32295","SIZENAME6":"XXL","AMOUNT7":"6","SIZEID7":"32296","SIZENAME7":"3XL","AMOUNT8":"6","SIZEID8":"32297","SIZENAME8":"4XL"},{"WAREID":"1586292","WARENO":"000","AMOUNT":"130","KEYID":"171","ROWNUMBER":"5","AMOUNT1":"13","SIZEID1":"32290","SIZENAME1":"XS","AMOUNT2":"16","SIZEID2":"32291","SIZENAME2":"S","AMOUNT3":"20","SIZEID3":"32292","SIZENAME3":"M","AMOUNT4":"19","SIZEID4":"32293","SIZENAME4":"L","AMOUNT5":"20","SIZEID5":"32294","SIZENAME5":"XL","AMOUNT6":"14","SIZEID6":"32295","SIZENAME6":"XXL","AMOUNT7":"14","SIZEID7":"32296","SIZENAME7":"3XL","AMOUNT8":"14","SIZEID8":"32297","SIZENAME8":"4XL"},{"WAREID":"1782312","WARENO":"006","AMOUNT":"29","KEYID":"195","ROWNUMBER":"6","AMOUNT1":"10","SIZEID1":"32290","SIZENAME1":"XS","AMOUNT2":"7","SIZEID2":"32291","SIZENAME2":"S","AMOUNT3":"8","SIZEID3":"32292","SIZENAME3":"M","AMOUNT4":"4","SIZEID4":"32293","SIZENAME4":"L","AMOUNT5":"0","SIZEID5":"32294","SIZENAME5":"XL","AMOUNT6":"0","SIZEID6":"32295","SIZENAME6":"XXL","AMOUNT7":"0","SIZEID7":"32296","SIZENAME7":"3XL","AMOUNT8":"0","SIZEID8":"32297","SIZENAME8":"4XL"},{"WAREID":"1633107","WARENO":"005","AMOUNT":"13","KEYID":"203","ROWNUMBER":"7","AMOUNT1":"13","SIZEID1":"32298","SIZENAME1":"均码","AMOUNT2":"0","SIZEID2":"0","SIZENAME2":"","AMOUNT3":"0","SIZEID3":"0","SIZENAME3":"","AMOUNT4":"0","SIZEID4":"0","SIZENAME4":"","AMOUNT5":"0","SIZEID5":"0","SIZENAME5":"","AMOUNT6":"0","SIZEID6":"0","SIZENAME6":"","AMOUNT7":"0","SIZEID7":"0","SIZENAME7":"","AMOUNT8":"0","SIZEID8":"0","SIZENAME8":""}]}

//		LogUtils.LogDebugWrite("Json2Excel", jsontable);
		// int rows = 0;
		// int cols = 0;
		// if (tb != null) {
		// rows = tb.getRowCount();
		// cols = tb.getColumnCount();
		// }

		// if (rows == 0 || cols == 0)
		// return "";
		String xlsfilename = "temp/xls" + Func.DateToStr(new Date(), "yyMMddHHmmss") + Func.getRandomNumA(6) + ".xlsx";
		String outPath = Func.getRootPath() + xlsfilename;
		// LogUtils.LogDebugWrite("Table2Excel", outPath);
		// 创建工作文档对象
		Workbook wb = new XSSFWorkbook();

		// LogUtils.LogDebugWrite("Table2Excel", "000-1");
		// 创建sheet对象
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");

		// LogUtils.LogDebugWrite("Table2Excel", "loop begin");
		// 表头各列信息
		JSONArray colArray = columnsObject.getJSONArray("columns");

		// 表头格式
		XSSFCellStyle headCellStyle = (XSSFCellStyle) wb.createCellStyle();
		headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 表头居中
		headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		Font headfont = wb.createFont();
		headfont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		headfont.setFontName("黑体");
		headCellStyle.setFont(headfont);

		int currRow = 0;
		// LogUtils.LogDebugWrite("Json2Excel", jsontable);

		if (columnsObject.has("reptitle")) // 如果有报表标题，则显示报表标题
		{
			String reptitle = columnsObject.getString("reptitle");
			org.apache.poi.ss.usermodel.Row row = (org.apache.poi.ss.usermodel.Row) sheet1.createRow(currRow);
			Cell cell = ((org.apache.poi.ss.usermodel.Row) row).createCell(0);

			cell.setCellValue(reptitle);
			cell.setCellStyle(headCellStyle);
			sheet1.addMergedRegion(new CellRangeAddress(currRow, currRow, 0, colArray.size() - 1));

			currRow++;
			// LogUtils.LogDebugWrite("Json2Excel", reptitle);
		}

		// LogUtils.LogDebugWrite("Json2Excel", "333");
		JSONArray tbrows = tb.getJSONArray("rows");

		// {
		// "total": "7",
		// "rows": [
		// {
		// "WAREID": "1261806",
		// "WARENO": "001",
		// "AMOUNT": "134",
		// "KEYID": "1",
		// "ROWNUMBER": "1",
		// "AMOUNT1": "18",
		// "SIZEID1": "32290",
		// "SIZENAME1": "XS",
		// "AMOUNT2": "12",
		// "SIZEID2": "32291",
		// "SIZENAME2": "S",
		// "AMOUNT3": "16",
		// "SIZEID3": "32292",
		// "SIZENAME3": "M",
		// "AMOUNT4": "16",
		// "SIZEID4": "32293",
		// "SIZENAME4": "L",
		// "AMOUNT5": "18",
		// "SIZEID5": "32294",
		// "SIZENAME5": "XL",
		// "AMOUNT6": "18",
		// "SIZEID6": "32295",
		// "SIZENAME6": "XXL",
		// "AMOUNT7": "18",
		// "SIZEID7": "32296",
		// "SIZENAME7": "3XL",
		// "AMOUNT8": "18",
		// "SIZEID8": "32297",
		// "SIZENAME8": "4XL"
		// },
		// LogUtils.LogDebugWrite("Json2Excel", "loop begin");
		XSSFCellStyle amtCellStyle = (XSSFCellStyle) wb.createCellStyle();
		amtCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 数字右对齐
		amtCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		XSSFCellStyle curCellStyle = (XSSFCellStyle) wb.createCellStyle();
		curCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 数字右对齐
		curCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		DataFormat df = wb.createDataFormat();
		curCellStyle.setDataFormat(df.getFormat("0.00"));

		String sizegroupno = "";
		String sizegroupno0 = "";
		// int currRow = 0;
		// LogUtils.LogDebugWrite("Json2Excel", "loop begin");
		for (int i = 0; i < tbrows.size(); i++) {
			JSONObject tbrec = tbrows.getJSONObject(i);
			sizegroupno = tbrec.getString("SIZEGROUPNO");

			// LogUtils.LogDebugWrite("Json2Excel", "loop sizegroupno=" + sizegroupno);

			if (!sizegroupno.equals(sizegroupno0)) {// 显示表头尺码名称
				org.apache.poi.ss.usermodel.Row row = (org.apache.poi.ss.usermodel.Row) sheet1.createRow(currRow);

				for (int j = 0; j < colArray.size(); j++) {
					JSONObject jsonObject1 = colArray.getJSONObject(j);
					String coltitle = jsonObject1.getString("fieldname");
					String fieldno = jsonObject1.getString("fieldno").toUpperCase();
					// 取尺码的名称
					if (Func.subString(fieldno, 1, 6).equals("AMOUNT") && fieldno.length() > 6) {
						fieldno = fieldno.replace("AMOUNT", "SIZENAME");
						coltitle = tbrec.getString(fieldno);
					}
					Cell cell = ((org.apache.poi.ss.usermodel.Row) row).createCell(j);
					cell.setCellValue(coltitle);
					cell.setCellStyle(headCellStyle);

				}
				currRow++;
				sizegroupno0 = sizegroupno;
			}
			// 表体
			// for (int i = 0; i < rows; i++) {
			// LogUtils.LogDebugWrite("Table2Excel", "a1");

			org.apache.poi.ss.usermodel.Row row = (org.apache.poi.ss.usermodel.Row) sheet1.createRow(currRow);

			// LogUtils.LogDebugWrite("Table2Excel", "colcount="+colArray.size());

			for (int j = 0; j < colArray.size(); j++) {// 显示名列数据
				JSONObject jsonObject1 = colArray.getJSONObject(j);
				String fieldno = jsonObject1.getString("fieldno").toUpperCase();
				// LogUtils.LogDebugWrite("Json2Excel " ,j+"="+ fieldno);
				String coltitle = "***";
				// 取尺码的名称
				if (Func.subString(fieldno, 1, 6).equals("AMOUNT") && fieldno.length() > 6) {
					// fieldno = fieldno.replace("AMOUNT", "SIZENAME");
					coltitle = tbrec.getString(fieldno.replace("AMOUNT", "SIZENAME"));
					if (coltitle == null)
						coltitle = "";
				}

				String fieldtype = "C";
				if (jsonObject1.has("fieldtype"))
					fieldtype = jsonObject1.getString("fieldtype").toUpperCase();

				// LogUtils.LogDebugWrite("Table2Excel", "a3 fieldno="+fieldno+ " fieldtype="+fieldtype);
				Cell cell = ((org.apache.poi.ss.usermodel.Row) row).createCell(j);
				String strValue = tbrec.getString(fieldno);// tb.getRow(i).get(fieldno).toString();
				if (coltitle.equals("")) {
					strValue = "";
					fieldtype = "C";
				}
				// N保留2位小数，G原值，P百分比
				if (fieldtype.equals("N")) {

					Float f = (float) 0;
					if (strValue != null && strValue.length() > 0) {
						try {
							f = Float.parseFloat(strValue);
						} catch (Exception e) {

						}
						// f = Float.valueOf(strValue);
					}

					// LogUtils.LogDebugWrite("Json2Excel 111:", f.toString());
					// f = Func.getRound(f, 2);
					// LogUtils.LogDebugWrite("Json2Excel 222:", f.toString());
					cell.setCellValue(f);
					// f = Func.getRound(f, 2);

					cell.setCellStyle(curCellStyle);

					// LogUtils.LogDebugWrite("Table2Excel", "a5");
				} else if (fieldtype.equals("G") && coltitle.length() > 0) {// 原值

					// LogUtils.LogDebugWrite(fieldno, "strValue=" + strValue);
					Float f = (float) 0;
					if (strValue != null && strValue.length() > 0) {
						try {
							f = Float.parseFloat(strValue);
						} catch (Exception e) {

						}
					}
					cell.setCellValue(f);

					cell.setCellStyle(amtCellStyle);

					// LogUtils.LogDebugWrite("Table2Excel", "a5");
				} else {
					// cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 数字右对齐
					cell.setCellValue(strValue);
				}
				// cell.setCellType(CELL_TYPE_NUMERIC);
				// Cell cell = ((org.apache.poi.ss.usermodel.Row) row).createCell(j);
				// cell.setCellValue(jsonObject1.getString("fieldname"));
			}
			currRow++;
		}
		// LogUtils.LogDebugWrite("Table2Excel", "loop end");
		// 各列自动适应列宽
		// 合计行
		org.apache.poi.ss.usermodel.Row rowhj = (org.apache.poi.ss.usermodel.Row) sheet1.createRow(currRow);
		boolean hjok = false;
		for (int j = 0; j < colArray.size(); j++) {
			JSONObject jsonObject1 = colArray.getJSONObject(j);
			if (jsonObject1.has("fieldfooter")) {
				Cell cell = ((org.apache.poi.ss.usermodel.Row) rowhj).createCell(j);
				String fieldtype = "C";
				if (jsonObject1.has("fieldtype"))
					fieldtype = jsonObject1.getString("fieldtype").toUpperCase();

				String strValue = jsonObject1.getString("fieldfooter");
				if (strValue == null || strValue.length() <= 0) {
					strValue = "";
					fieldtype = "C";
				}

				if (fieldtype.equals("C")) {// 如果是字符型，直接显示
					cell.setCellValue(strValue);

				} else
					try {
						Float f = Float.parseFloat(strValue);
						cell.setCellValue(f);
						if (fieldtype.equals("N"))
							cell.setCellStyle(curCellStyle);
						// if (fieldtype.equals("G"))
						else
							cell.setCellStyle(amtCellStyle);

					} catch (Exception e) {

					}
				hjok = true;

			}

			sheet1.autoSizeColumn(j);
		}
		if (hjok) {// 如果有合计，第1列显示合计这两个字
			Cell cell = ((org.apache.poi.ss.usermodel.Row) rowhj).createCell(0);
			cell.setCellValue("合计");
			cell.setCellStyle(headCellStyle);
		}
		String retcs = "";
		// 创建文件流
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(outPath);
			// LogUtils.LogDebugWrite("Table2Excel", "1111");
			wb.write(stream);
			// LogUtils.LogDebugWrite("Table2Excel", "2222");
			retcs = "1" + xlsfilename;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			xlsfilename = "";
			retcs = "0导出失败1:" + e.getMessage();
			errmess = "Err1062:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.Json2Excel", errmess);
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			xlsfilename = "";
			retcs = "0导出失败2:" + e.getMessage();
			errmess = "Err1063:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.Json2Excel", errmess);
			// e.printStackTrace();
		} finally {// 关闭文件流
			if (stream != null)
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					errmess = "Err1064:" + e.getMessage();
					LogUtils.LogErrWrite("MsHelperSQL.Json2Excel", errmess);
				}
		}
		return retcs;
	}

	// 取全部记录
	public static Table GetTable(String qry) {
		return Query(qry).getTable(1);
	}

	// 分页取数
	public static Table GetTable(QueryParam query) {

		String qry2 = " SELECT COUNT(*) AS total";
		if (!query.getSumString().equals(""))
			qry2 += "," + query.getSumString();
		qry2 += " FROM (\n " + query.getQueryString() + " \n) t1 ";

		System.out.println("qry2: " + qry2);

		Table tb = new Table();
		tb = MsHelperSQL.Query(qry2).getTable(1);
		//		System.out.println("0000");

		String totalstr = query.getTotalString();
		String fh = "";
		if (totalstr.length() > 0)
			fh = ",";
		String calcfield = query.getCalcfield().toLowerCase();
//		System.out.println("getColumnCount=" + tb.getColumnCount());
		if (calcfield.length() == 0) {
			for (int j = 0; j < tb.getColumnCount(); j++) {

				//				System.out.println("1111-0:1-0");
				String fdname = tb.getRow(0).getColumn(j + 1).getName().toLowerCase();

				//				System.out.println("1111-1:" + fdname);
				String fdvalue = tb.getRow(0).get(j).toString();
				//				System.out.println("1111-2:" + fdvalue);
				totalstr += fh + "\"" + fdname + "\":\"" + fdvalue + "\"";
				fh = ",";
			}
		} else {
			for (int j = 0; j < tb.getColumnCount(); j++) {
				String fdname = tb.getRow(0).getColumn(j + 1).getName().toLowerCase();
				String fdvalue = tb.getRow(0).get(j).toString();
				totalstr += fh + "\"" + fdname + "\":\"" + fdvalue + "\"";
				calcfield = calcfield.replaceAll("\\[" + fdname + "\\]", fdvalue);
				fh = ",";
			}
		}
		//		System.out.println("2222:" + totalstr);
		//System.out.println("2.calcfield=" + calcfield);
		//System.out.println("gettable: totalstr=" + totalstr);
		query.setTotalString(totalstr);
		String qry1 = "";
		if (query.getPageIndex() < 0) // 返回所有记录
		{
			qry1 = " SELECT t2.*";
			if (!calcfield.equals(""))
				qry1 += "," + calcfield;
			qry1 += "\n from (select *, ROW_NUMBER() OVER(Order by " + query.getSortString() + ") AS RowNumber";
			qry1 += "\n	from (" + query.getQueryString()+" ) t1 ) as t2";
			System.out.println("qry1所有: " + qry1);

		} else {
			int lastrec = query.getPageIndex() * query.getPageSize();
			int firstrec = (query.getPageIndex() - 1) * query.getPageSize() + 1;

			qry1 = " SELECT t2.*";
			if (!calcfield.equals(""))
				qry1 += "," + calcfield;
			qry1 += "\n from (select *, ROW_NUMBER() OVER(Order by " + query.getSortString() + ") AS RowNumber";
			qry1 += "\n	from (" + query.getQueryString()+" ) t1 ) as t2";
			qry1 += "\n where RowNumber  BETWEEN " + firstrec + " and " + lastrec;

			System.out.println("qry1: " + qry1);

		}

		return Query(qry1).getTable(1);
	}
	// // 分页取数
	// public static Table GetTable(QueryParam query) {
	// int lastrec = query.getPageIndex() * query.getPageSize();
	// int firstrec = (query.getPageIndex() - 1) * query.getPageSize() + 1;
	//
	// String qry1 = " SELECT t1.*";
	// if (!query.getCalcfield().equals(""))
	// qry1 += "," + query.getCalcfield();
	// qry1 += " FROM ( SELECT t2.*, ROWNUM as rownumber";
	// qry1 += " FROM ( SELECT * FROM (\n " + query.getQueryString() + " \n ) order by " + query.getSortString();
	// qry1 += " ) t2 \n WHERE ROWNUM <= " + lastrec + " ) t1 WHERE rownumber >= " + firstrec;
	//System.out.println("qry1: " + qry1);
	//
	// String qry2 = " SELECT COUNT(*) AS total";
	// if (!query.getSumString().equals(""))
	// qry2 += "," + query.getSumString();
	// qry2 += " FROM (\n " + query.getQueryString() + " \n) t1 ";
	//
	//System.out.println("qur2: " + qry2);
	//
	// Table tb = new Table();
	// tb = MsHelperSQL.Query(qry2).getTable(1);
	//
	// //System.out.println("gettable: 111" );
	// String totalstr = "";
	// String fh = "";
	// for (int j = 0; j < tb.getColumnCount(); j++) {
	// totalstr += fh + "\"" + tb.getRow(0).getColumn(j + 1).getName().toLowerCase() + "\":\"" + tb.getRow(0).get(j).toString() + "\"";
	// fh = ",";
	// }
	// //System.out.println("gettable: totalstr=" + totalstr);
	// query.setTotalString(totalstr);
	//
	//
	// return Query(qry1).getTable(1);
	// }

	// }
	// 判断记录是否存在
	public static boolean Exists(String qry) {
		Connection oc = null;
		Statement om = null;
		ResultSet rs = null;
		try {
			oc = getConnection();
			if (oc != null) {
				om = oc.createStatement();

				rs = om.executeQuery(qry);
				rs.next();
				if (rs.getRow() > 0)
					return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "Err1065:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.Exists", errmess);
			// // TODO Auto-generated catch block
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (om != null)
				try {

					om.close();
				} catch (Exception e2) {
				}
			if (oc != null)
				try {
					oc.close();
				} catch (Exception e2) {
				}
		}
		return false;

	}

	// 执行一条计算查询结果语句，返回查询结果（object）
	public static int GetSingleCount(String qry) {
		Connection oc = null;
		Statement om = null;
		ResultSet rs = null;
		try {
			oc = getConnection();
			if (oc != null) {
				om = oc.createStatement();
				rs = om.executeQuery(qry);
				rs.next();

				if (rs.getRow() > 0) {
					return rs.getInt(1);
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "Err1066:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.GetSingleCount", errmess);
			// // TODO Auto-generated catch block
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (om != null)
				try {
					om.close();
				} catch (Exception e2) {
				}
			if (oc != null)
				try {
					oc.close();
				} catch (Exception e2) {
				}
		}
		return 0;

	}

	// 遍历类变量从json中取对应数据
	public static void JsonConvertObject1(Object model, JSONObject jsonObject) {
		//System.out.println("Object name=" + model.getClass().getName().toString());
		try {
			// JSONObject jsonObject = JSONObject.fromObject(httpinfo.getDatajson());

			// 获取实体类的所有属性，返回Field数组
			Field[] field = model.getClass().getDeclaredFields();
			//System.out.println("field =" + field.toString());
			// 获取属性的名字
			String[] modelName = new String[field.length];
			String[] modelType = new String[field.length];
			//System.out.println("field =" + field.toString() + " modelName=" + modelName.toString() + " modelType=" + modelType.toString() + " field.length=" + field.length);

			for (int i = 0; i < field.length; i++) {
				//System.out.println(i);
				// 获取属性的名字
				String name = field[i].getName();
				modelName[i] = name;
				// 获取属性类型
				String type = field[i].getGenericType().toString();
				modelType[i] = type;

				// 关键。。。可访问私有变量
				field[i].setAccessible(true);

				// 给属性设置
				// field[i].set(model, field[i].getType().getConstructor(field[i].getType()).newInstance(jsonObject.getString(name.toLowerCase())));

				// 将属性的首字母大写
				// name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
				name = Func.getUpperOne(name);

				//System.out.println(" d 5 ");
				//System.out.println(" name=" + name + " type=" + type);

				if (type.equals("class java.lang.String")) {
					// 给属性设置
					if (jsonObject.has(name.toLowerCase())) {
						String value = jsonObject.getString(name.toLowerCase()).trim();

						//System.out.println(name + "=" + value);
						// field[i].set(model, field[i].getType().getConstructor(field[i].getType()).newInstance(value));
						field[i].set(model, value);
					}

				} else if (type.equals("class java.lang.Integer") || type.equals("class java.lang.Short")) {

					if (jsonObject.has(name.toLowerCase())) {
						int value = Integer.parseInt(jsonObject.getString(name.toLowerCase()));

						field[i].set(model, value);
					}
				} else if (type.equals("class java.lang.Long")) {
					if (jsonObject.has(name.toLowerCase())) {
						long value = Long.parseLong(jsonObject.getString(name.toLowerCase()));

						//System.out.println(name + "=" + value);
						field[i].set(model, value);
					}
				} else if (type.equals("class java.lang.Float")) {
					if (jsonObject.has(name.toLowerCase())) {
						float value = Float.parseFloat(jsonObject.getString(name.toLowerCase()));
						field[i].set(model, value);

					}
				} else if (type.equals("class java.lang.Double")) {
					if (jsonObject.has(name.toLowerCase())) {
						double value = Double.parseDouble(jsonObject.getString(name.toLowerCase()));
						field[i].set(model, value);

					}
				} else if (type.equals("class java.lang.Boolean")) {
					if (jsonObject.has(name.toLowerCase())) {
						boolean value = Boolean.parseBoolean(jsonObject.getString(name.toLowerCase()));
						field[i].set(model, value);

					}
				} else if (type.equals("class java.util.Date")) {
					if (jsonObject.has(name.toLowerCase())) {
						String datestr = jsonObject.getString(name.toLowerCase());
						DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date value = f.parse(datestr);
						//System.out.println(name + "=" + datestr);
						// field[i].set(model, field[i].getType().getConstructor(field[i].getType()).newInstance(value));
						field[i].set(model, value);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errmess = "Err1067:" + e.getMessage();
			LogUtils.LogErrWrite("MsHelperSQL.JsonConvertObject1", errmess);
			// e.printStackTrace();
			// // TODO Auto-generated catch block
		}
	}

	// 遍历json取数据传给类变量
	@SuppressWarnings("rawtypes")
	public static void JsonConvertObject(Object model, JSONObject jsonObject) {
		//System.out.println("JsonConvertObject");
		Iterator iterator = jsonObject.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (!key.equals("flyang")) {
				//System.out.println("key=" + key);
				Object value = jsonObject.get(key);
				//System.out.println("value=" + value);

				key = Func.getUpperOne(key);
				//System.out.println("0 key=" + key + " value=" + value);
				Field field;

				try {

					//System.out.println("11");
					field = model.getClass().getDeclaredField(key);
					//System.out.println("22");

					if (field == null)
						break;
					String type = field.getGenericType().toString();
					field.setAccessible(true);

					//System.out.println("1 key=" + key + " value=" + value + " type=" + type);
					// field.set(model, field.getType().getConstructor(field.getType()).newInstance(value));
					if (type.equals("class java.lang.String")) {
						// 给属性设置
						String value1 = value.toString().trim();
						//System.out.println(name + "=" + value);
						field.set(model, value1);

					} else if (type.equals("class java.lang.Integer") || type.equals("class java.lang.Short")) {
						int value1 = Integer.parseInt(value.toString());

						field.set(model, value1);
					} else if (type.equals("class java.lang.Long")) {

						long value1 = Long.parseLong(value.toString());
						field.set(model, value1);
					} else if (type.equals("class java.lang.Float")) {
						float value1 = Float.parseFloat(value.toString());
						field.set(model, value1);
					} else if (type.equals("class java.lang.Double")) {
						double value1 = Double.parseDouble(value.toString());
						field.set(model, value1);

					} else if (type.equals("class java.lang.Boolean")) {
						boolean value1 = Boolean.parseBoolean(value.toString());
						field.set(model, value1);

					} else if (type.equals("class java.util.Date")) {
						String datestr = value.toString();
						DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date value1 = f.parse(datestr);

						//System.out.println(name + "=" + datestr);
						// field[i].set(model, field[i].getType().getConstructor(field[i].getType()).newInstance(value));
						field.set(model, value1);

					}
					//System.out.println("2 key=" + key + " value=" + value + " ok");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					errmess = "Err1068:" + e.getMessage();
					// FyLogUtils.LogErrWrite("MsHelperSQL.JsonConvertObject", errmess);
					// e.printStackTrace();
					// TODO Auto-generated catch block
				}
			}
		}

	}

	public String getErrmess() {
		return errmess;
	}
}
