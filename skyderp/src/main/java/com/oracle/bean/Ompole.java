package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.LogUtils;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-22 19:43:56
//*************************************
public class Ompole implements java.io.Serializable {
	private Long Poleid;
	private String Polename;
	private Long Omid;
	private String Imagename;
	private Long Warenum;
	private Date Lastdate;
	private String Lastop;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setPoleid(Long poleid) {
		this.Poleid = poleid;
	}

	public Long getPoleid() {
		return Poleid;
	}

	public void setPolename(String polename) {
		this.Polename = polename;
	}

	public String getPolename() {
		return Polename;
	}

	public void setOmid(Long omid) {
		this.Omid = omid;
	}

	public Long getOmid() {
		return Omid;
	}

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
	}

	public void setWarenum(Long warenum) {
		this.Warenum = warenum;
	}

	public Long getWarenum() {
		return Warenum;
	}

	// public void setLastdate(Date lastdate) {
	// this.Lastdate = lastdate;
	// }

	// public String getLastdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Lastdate);
	// }

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录 ok
	public int Delete() {
		if (Poleid == 0) {
			errmess = "陈列杆参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		strSql.append(" begin");
		strSql.append("   delete from ompole where poleid=" + Poleid + ";");
		strSql.append("   delete from ompoleware where poleid=" + Poleid + ";");
		strSql.append(" end;");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录 Ok
	public int Append(String pictjson) {
		if (Omid == 0) {
			errmess = "订货会id无效！";
			return 0;

		}
		if (Polename == null || Polename.equals("")) {
			errmess = "陈列杆名称无效！";
			return 0;
		}
//		LogUtils.LogDebugWrite("updateompole.append", " 000 " + pictjson);
		if (!pictjson.equals("")) {
			LogUtils.LogDebugWrite("updateompole.append", " 111 " + pictjson);

			JSONObject jsonObject = JSONObject.fromObject(pictjson);
			if (!jsonObject.has("base64string")) {
				errmess = "图片参数无效1！";
				return 0;
			}
			// String base64string = "";
			String base64string = jsonObject.getString("base64string");
			String retcs = Func.Base64UpFastDFS(base64string);
			if (retcs.substring(0, 1).equals("0")) {
				errmess = "图片参数无效2！";
				return 0;
			}
			Imagename = retcs.substring(1);
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("poleid,");
		strSql2.append("v_id,");
		// if (Polename != null) {
		strSql1.append("polename,");
		strSql2.append("'" + Polename + "',");
		// }
		// if (Omid != null) {
		strSql1.append("omid,");
		strSql2.append(Omid + ",");
		// }
		if (Imagename != null) {
			strSql1.append("imagename,");
			strSql2.append("'" + Imagename + "',");
		}
		if (Warenum != null) {
			strSql1.append("warenum,");
			strSql2.append(Warenum + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n begin ");
		strSql.append("\n   v_id:=ompole_poleid.nextval; ");
		strSql.append("\n   insert into ompole (");
		strSql.append("\n  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n  select v_id into :id from dual; ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret >= 0) {
			long Poleid = Long.parseLong(param.get("id").getParamvalue().toString());
			errmess = "" + Poleid;
			return 1;
		} else {
			errmess = "增加失败！";
			return 0;
		}
	}

	// 修改记录 ok
	public int Update(String pictjson) {
		if (Omid == 0 || Poleid == 0) {
			errmess = "订货会id或陈列杆id无效！";
			return 0;
		}
//		LogUtils.LogDebugWrite("updateompole.update", "1 000 " + pictjson);
		if (!pictjson.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(pictjson);
			if (jsonObject.has("base64string")) {
				// String base64string = "";
				String base64string = jsonObject.getString("base64string");
				String retcs = Func.Base64UpFastDFS(base64string);
				if (retcs.substring(0, 1).equals("1")) {
					Imagename = retcs.substring(1);
				}
			}
		}
//		LogUtils.LogDebugWrite("updateompole.update", "2");
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("\n   update ompole set ");
		// if (Poleid != null) {
		// strSql.append("poleid=" + Poleid + ",");
		// }
		if (Polename != null) {
			strSql.append("polename='" + Polename + "',");
		}
		if (Omid != null) {
			strSql.append("omid=" + Omid + ",");
		}
		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
		}
		if (Warenum != null) {
			strSql.append("warenum=" + Warenum + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where poleid=" + Poleid + " ;");
		strSql.append("\n  commit;");
		strSql.append("\n end;");
//		LogUtils.LogDebugWrite("updateompole.update", strSql.toString());
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;

		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM ompole ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM ompole ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		//System.out.println(strSql.toString());
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from ompole");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}
}