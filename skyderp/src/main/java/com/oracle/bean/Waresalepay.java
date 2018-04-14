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
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-04-17 10:03:30
//*************************************
public class Waresalepay implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private Long Payid;
	private Float Curr;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setPayid(Long payid) {
		this.Payid = payid;
	}

	public Long getPayid() {
		return Payid;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update Waresalepay set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Id != null) {
			strSql1.append("id,");
			strSql2.append("'" + Id + "',");
		}
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Noteno != null) {
			strSql1.append("noteno,");
			strSql2.append("'" + Noteno + "',");
		}
		if (Payid != null) {
			strSql1.append("payid,");
			strSql2.append(Payid + ",");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Waresalepay_id.nextval; ");
		strSql.append("   insert into Waresalepay (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Waresalepay set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Noteno != null) {
			strSql.append("noteno='" + Noteno + "',");
		}
		if (Payid != null) {
			strSql.append("payid=" + Payid + ",");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
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
		strSql.append(" FROM Waresalepay ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Waresalepay ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据
	public Table GetTable(String strWhere, String fieldlist, String sort) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Waresalepay a");
		strSql.append(" left outer join payway b on a.payid=b.payid");

		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		if (sort.length() > 0) {
			strSql.append(" order by " + sort);
		}

		return DbHelperSQL.GetTable(strSql.toString());
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Waresalepay");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 取前台单据结算记录
	public int doGetWaresalepaye() {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n     v_totalamt number(16,2);";
		qry += "\n     v_totalcurr number(16,2);";
		qry += "\n     v_freecurr number(16,2);";
		qry += "\n     v_changecurr number(16,2);";
		qry += "\n     v_checkcurr number(16,2);";
		qry += "\n     v_zpaycurr number(16,2);";
		qry += "\n begin";
		// qry += "\n select nvl(sum(curr),0) into v_totalcurr from wareoutm where accid=" + accid + " and noteno='" + noteno + "'; ";
		qry += "\n    select totalamt,totalcurr,checkcurr,freecurr,zpaycurr,changecurr,yhjcurr,jfcurr,usecent ";
		qry += "\n    into :totalamt, :totalcurr,:checkcurr,:freecurr,:zpaycurr,:changecurr,:yhjcurr,:jfcurr,:usecent ";
		qry += "\n    from wareouth where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n end;";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt", new ProdParam(Types.FLOAT));
		param.put("totalcurr", new ProdParam(Types.FLOAT));
		param.put("freecurr", new ProdParam(Types.FLOAT));
		param.put("checkcurr", new ProdParam(Types.FLOAT));
		param.put("zpaycurr", new ProdParam(Types.FLOAT));
		param.put("changecurr", new ProdParam(Types.FLOAT));
		param.put("yhjcurr", new ProdParam(Types.FLOAT));
		param.put("jfcurr", new ProdParam(Types.FLOAT));
		param.put("usecent", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
		// String Noteno = param.get("Noteno").getParamvalue().toString();
		String retcs = "\"msg\":\"操作成功！\",\"TOTALCURR\":\"" + param.get("totalcurr").getParamvalue().toString() + "\""//
				+ ",\"TOTALAMT\": \"" + param.get("totalamt").getParamvalue().toString() + "\""//
				+ ",\"FREECURR\": \"" + param.get("freecurr").getParamvalue().toString() + "\""//
				+ ",\"CHECKCURR\": \"" + param.get("checkcurr").getParamvalue().toString() + "\""//
				+ ",\"ZPAYCURR\": \"" + param.get("zpaycurr").getParamvalue().toString() + "\""//
				+ ",\"CHANGECURR\": \"" + param.get("changecurr").getParamvalue().toString() + "\""//
				+ ",\"YHJCURR\": \"" + param.get("yhjcurr").getParamvalue().toString() + "\""//
				+ ",\"JFCURR\": \"" + param.get("jfcurr").getParamvalue().toString() + "\""//
				+ ",\"USECENT\": \"" + param.get("usecent").getParamvalue().toString() + "\""//
				;
		qry = "select a.payid,b.payname,b.payno,a.curr from waresalepay a";
		qry += " left outer join payway b on a.payid=b.payid";
		qry += " where a.accid=" + Accid + " and a.noteno='" + Noteno + "' order by b.payno";

		Table tb = DbHelperSQL.Query(qry).getTable(1);
		retcs += ",\"PAYLIST\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"PAYID\":\"" + tb.getRow(i).get("PAYID").toString() + "\""//
					+ ",\"PAYNAME\":\"" + tb.getRow(i).get("PAYNAME").toString() + "\"" //
					+ ",\"PAYNO\":\"" + tb.getRow(i).get("PAYNO").toString() + "\"" //
					+ ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"" + "}";
		}
		retcs += "]";
		errmess = retcs;
		return 1;
	}
}