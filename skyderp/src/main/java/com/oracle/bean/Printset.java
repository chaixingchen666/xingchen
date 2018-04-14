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
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-16 16:55:24
//*************************************
public class Printset implements java.io.Serializable {
	private Long Prtid;
	private String Prtname;
	private Long Accid;
	private Integer Statetag;
	private Integer Noused;
	private String Ipstr;
	private Integer Port;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setPrtid(Long prtid) {
		this.Prtid = prtid;
	}

	public void setPort(Integer port) {
		this.Port = port;
	}

	public void setIpstr(String ipstr) {
		Ipstr = ipstr;
	}

	public Long getPrtid() {
		return Prtid;
	}

	public void setPrtname(String prtname) {
		this.Prtname = prtname;
	}

	public String getPrtname() {
		return Prtname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

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
	// 删除记录
	public int Delete() {
		if (Prtid == 0) {
			errmess = "prtid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// // strSql.append("select wareid from warecode where id=" + id + " and
		// rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		strSql.append("begin ");
		strSql.append("\n   update printset set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("\n   where prtid=" + Prtid + " and accid=" + Accid + "; ");
		strSql.append("\n   delete from PROGPRINT where prtid=" + Prtid + "; ");
		strSql.append("\n   delete from backprint where prtid=" + Prtid + "; ");
		strSql.append("\n end;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append() {
		if (Prtname == null || Prtname.equals("")) {
			errmess = "打印端口名称不允许为空！";
			return 0;
		}
		//		if (!Func.isNull(Ipstr)) {
		//			if (!Func.isIpaddress(Ipstr)) {
		//				errmess = "不是一个有效的ip地址！";
		//				return 0;
		//			}
		//			//			if (Port != null && Port > 0)
		//			if (Port == null || Port < 50701 || Port > 50799) {
		//				errmess = "端口号应在50701-50799之间！";
		//				return 0;
		//			}
		//		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("prtid,");
		strSql2.append("v_id,");
		// if (Prtid != null) {
		// strSql1.append("prtid,");
		// strSql2.append(Prtid + ",");
		// }
		// if (Prtname != null) {
		strSql1.append("prtname,");
		strSql2.append("'" + Prtname + "',");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (Port != null) {
			strSql1.append("Port,");
			strSql2.append(Port + ",");
		}
		if (Ipstr != null) {
			strSql1.append("Ipstr,");
			strSql2.append("'" + Ipstr + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_id from Printset where accid=" + Accid + " and prtname='" + Prtname + "' and statetag=1;");
		strSql.append("\n   if v_id>0 then");
		strSql.append("\n      v_retcs:='0端口已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   v_id:=Printset_prtid.nextval; ");
		strSql.append("\n   insert into Printset (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n   v_retcs:='1'||v_id;");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual; ");
		strSql.append("\n end;");
		 Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		 param.put("retcs", new ProdParam(Types.VARCHAR));
		 int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		 if (ret < 0) {
		 errmess = "操作异常！";
		 return 0;
		 }
		 String retcs = param.get("retcs").getParamvalue().toString();
		
		 errmess = retcs.substring(1);
		 return Integer.parseInt(retcs.substring(0, 1));
	}

	// 修改记录
	public int Update() {
		//		if (!Func.isNull(Ipstr)) {
		//			if (!Func.isIpaddress(Ipstr)) {
		//				errmess = "不是一个有效的ip地址！";
		//				return 0;
		//			}
		//			if (Port == null || Port < 50701 || Port > 50799) {
		//				errmess = "端口号应在50701-50799之间！";
		//				return 0;
		//			}
		//		}
		//		if (Port != null && Port > 0)
		//			if (Port < 50701 || Port > 50799) {
		//				errmess = "端口号应在50701-50799之间！";
		//				return 0;
		//			}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("\n   update Printset set ");
		// if (Prtid != null) {
		// strSql.append("prtid=" + Prtid + ",");
		// }
		if (Prtname != null) {
			strSql.append("prtname='" + Prtname + "',");
		}
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Ipstr != null) {
			strSql.append("Ipstr='" + Ipstr + "',");
		}
		if (Port != null) {
			strSql.append("Port=" + Port + ",");
		}

		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where prtid=" + Prtid + " and accid=" + Accid + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
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
		strSql.append(" FROM Printset ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Printset ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Printset");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	public int Get() {
		String qry = "select ipstr,port from printset where accid=" + Accid + " and prtid=" + Prtid + " and statetag=1";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "未找到打印端口！";
			return 0;
		}
		errmess = "\"msg\":\"操作成功！\",\"IPSTR\":\"" + tb.getRow(0).get("IPSTR").toString() + "\"" //
				+ ",\"PORT\":\"" + tb.getRow(0).get("PORT").toString() + "\"";

		return 1;
	}
}