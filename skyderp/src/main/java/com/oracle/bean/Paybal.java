package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-04-17 09:10:09
//*************************************
public class Paybal implements java.io.Serializable {
	private Float Id;
	private String Daystr;
	private Long Provid;
	private Long Accid;
	private Float Curr;
	private Long Houseid;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setDaystr(String daystr) {
		this.Daystr = daystr;
	}

	public String getDaystr() {
		return Daystr;
	}

	public void setProvid(Long provid) {
		this.Provid = provid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getProvid() {
		return Provid;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
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
		// strSql.append(" update paybal set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
		if (Daystr != null) {
			strSql1.append("daystr,");
			strSql2.append("'" + Daystr + "',");
		}
		if (Provid != null) {
			strSql1.append("provid,");
			strSql2.append(Provid + ",");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
		}
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=paybal_id.nextval; ");
		strSql.append("   insert into paybal (");
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
		strSql.append("   update paybal set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Daystr != null) {
			strSql.append("daystr='" + Daystr + "',");
		}
		if (Provid != null) {
			strSql.append("provid=" + Provid + ",");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
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
		strSql.append(" FROM paybal ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM paybal ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from paybal");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 获取供应商应付款余额(按单据时间取)
	public int doGetBalbynoteno(String noteno) {
		if (noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n     v_nowdate varchar2(20);";
		qry += "\n     v_nowdate0 varchar2(10);";
		qry += "\n     v_noteno varchar2(20);";
		qry += "\n     v_accid number;";
		qry += "\n     v_provid number;";
		qry += "\n     v_curr  number;";
		qry += "\n     v_totalcurr  number;";
		qry += "\n     v_freecurr  number;";
		qry += "\n     v_zpaycurr  number;";
		qry += "\n     v_balcurr  number;";
		qry += "\n     v_qkcurr  number;";
		qry += "\n begin";
		// qry += "\n v_accid:=" + accid + "; ";
		// qry += "\n v_noteno:='" + noteno + "'; ";
		qry += "\n   begin";

		qry += "\n    select to_char(notedate,'yyyy-mm-ddhh24:mi:ss'),provid,totalcurr into v_nowdate,v_provid,v_totalcurr from wareinh where accid=" + Accid + " and noteno='" + noteno + "';";
		qry += "\n    v_nowdate0:=to_char(to_date(substr(v_nowdate,1,10),'yyyy-mm-dd')-1,'yyyy-mm-dd'); ";
		// 本次免单金额
		qry += "\n    select nvl(sum(curr),0) into v_freecurr from paycurr where accid=" + Accid + " and ynoteno='" + noteno + "' and payid=0;";
		// 本次付款金额
		qry += "\n    select nvl(sum(curr),0) into v_zpaycurr from paycurr where accid=" + Accid + " and ynoteno='" + noteno + "' and payid>0;";

		qry += "\n    select nvl(sum(curr),0) into v_curr from (";
		// --上日余额
		qry += "\n    select curr from paybal where provid=v_provid  and daystr=v_nowdate0";
		qry += "\n    union all";
		// --本日初始应收款
		qry += "\n    select sum(a.curr) from firstpaycurr a";
		qry += "\n    where a.accid=" + Accid + " and a.provid=v_provid ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// --本日应付货款
		qry += "\n    union all";
		qry += "\n    select sum(case a.ntid when 0 then b.curr else -b.curr end) from wareinh a  left outer join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    where a.accid=" + Accid + " and a.provid=v_provid ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// --应收费用
		qry += "\n    union all";
		qry += "\n    select sum(a.curr) from paycost a ";
		qry += "\n    where a.accid=" + Accid + " and a.provid=v_provid ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// --已收及折扣
		qry += "\n    union all";
		qry += "\n    select -sum(a.curr) from paycurr a ";
		qry += "\n    where a.accid=" + Accid + " and a.provid=v_provid ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		qry += "\n    ) ;";
		// v_curr=v_balcurr+v_totalcurr-v_freecurr-v_zpaycurr
		// v_balcurr=v_curr-v_totalcurr+v_freecurr+v_zpaycurr
		qry += "\n    v_balcurr:=v_curr-v_totalcurr+v_freecurr+v_zpaycurr; v_qkcurr:=v_totalcurr-v_freecurr-v_zpaycurr;";// where provid=v_provid and accid="+accid+";";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n    v_curr:=0;v_balcurr:=0;v_qkcurr:=0;";
		qry += "\n    end;";
		qry += "\n    select v_curr,v_balcurr,v_qkcurr into :curr,:balcurr,:qkcurr from dual;";
		qry += "\n end;";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("curr", new ProdParam(Types.FLOAT));
		param.put("balcurr", new ProdParam(Types.FLOAT));
		param.put("qkcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
		errmess = "\"msg\":\"操作成功！\",\"CURR\": \"" + param.get("curr").getParamvalue().toString() + "\""//
				+ ",\"BALCURR\": \"" + param.get("balcurr").getParamvalue().toString() + "\""//
				+ ",\"QKCURR\": \"" + param.get("qkcurr").getParamvalue().toString() + "\"";

		return 1;
	}

	// 获取供应商应付款余额(按时间取)
	public int doGetBalbytime(String nowdate) {
		if (Provid == 0) {
			errmess = "provid不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "     v_nowdate varchar2(20);";
		qry += "     v_accid number;";
		qry += "     v_provid number;";
		qry += "     v_nowdate0 varchar2(10);";
		qry += "     v_curr  number;";
		qry += " begin";
		// qry += " v_accid:=" + accid + "; ";
		// qry += " v_provid:=" + provid + "; ";

		if (nowdate.length() <= 0)
			qry += "    v_nowdate:=to_char(sysdate,'yyyy-mm-ddhh24:mi:ss');";
		else
			qry += "    v_nowdate:='" + nowdate + "'; ";
		qry += "    v_nowdate0:=to_char(to_date(substr(v_nowdate,1,10),'yyyy-mm-dd')-1,'yyyy-mm-dd'); ";

		qry += "    select nvl(sum(curr),0) into v_curr from (";
		// --上日余额
		qry += "    select curr from paybal where provid=" + Provid + "  and daystr=v_nowdate0";
		qry += "    union all";
		// --本日初始应收款
		qry += "    select sum(a.curr) from firstpaycurr a";
		qry += "    where a.accid=" + Accid + " and a.provid=" + Provid + " ";
		qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// --本日应付货款
		qry += "    union all";
		qry += "    select sum(case a.ntid when 0 then b.curr else -b.curr end) from wareinh a  left outer join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "    where a.accid=" + Accid + " and a.provid=" + Provid + " and a.ntid>0";
		qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// --应收费用
		qry += "    union all";
		qry += "    select sum(a.curr) from paycost a ";
		qry += "    where a.accid=" + Accid + " and a.provid=" + Provid + " ";
		qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// --已收及折扣
		qry += "    union all";
		qry += "    select -sum(a.curr) from paycurr a ";
		qry += "    where a.accid=" + Accid + " and a.provid=" + Provid + " ";
		qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		qry += "    ) ;";
		qry += "    select v_curr into :curr from dual;";// where provid="+provid+" and accid="+accid+";";
		qry += " end;";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("curr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
		errmess = "\"msg\":\"操作成功！\",\"CURR\": \"" + param.get("curr").getParamvalue().toString() + "\"";

		return 1;
	}

}