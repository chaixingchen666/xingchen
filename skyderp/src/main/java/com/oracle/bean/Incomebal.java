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
//  @author:sunhong  @date:2017-04-17 09:10:16
//*************************************
public class Incomebal implements java.io.Serializable {
	private Float Id;
	private String Daystr;
	private Long Custid;
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

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getCustid() {
		return Custid;
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
		// strSql.append(" update incomebal set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
		if (Custid != null) {
			strSql1.append("custid,");
			strSql2.append(Custid + ",");
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
		strSql.append("   v_id:=incomebal_id.nextval; ");
		strSql.append("   insert into incomebal (");
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
		strSql.append("   update incomebal set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Daystr != null) {
			strSql.append("daystr='" + Daystr + "',");
		}
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
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
		strSql.append(" FROM incomebal ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM incomebal ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from incomebal");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 获取客户应收款余额(按单据时间取)
	public int doGetBalbynoteno(String noteno) {
		if (noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "     v_nowdate varchar2(20);";
		qry += "     v_noteno varchar2(20);";
		qry += "     v_accid number;";
		// qry += " v_houseid number;";
		qry += "     v_custid number;";
		qry += "     v_nowdate0 varchar2(10);";
		qry += "     v_curr  number;";
		qry += "     v_checkcurr  number;";
		qry += "     v_zpaycurr  number;"; // 上次余额
		qry += "     v_freecurr  number;"; // 本单折让金额
		qry += "\n     v_balcurr  number;";
		qry += "\n     v_qkcurr  number;";
		qry += " begin";
		// qry += " v_accid:=" + accid + "; ";
		// qry += " v_noteno:='" + noteno + "'; ";
		qry += "   begin";
		qry += "    select to_char(notedate,'yyyy-mm-ddhh24:mi:ss'),custid,checkcurr,zpaycurr into v_nowdate,v_custid,v_checkcurr,v_zpaycurr from wareouth where accid=" + Accid + " and noteno='" + noteno + "';";
		qry += "    v_nowdate0:=to_char(to_date(substr(v_nowdate,1,10),'yyyy-mm-dd')-1,'yyyy-mm-dd'); ";
		// 免单金额
		qry += "    select nvl(sum(curr),0) into v_freecurr from incomecurr where accid=" + Accid + " and ynoteno='" + noteno + "' and payid=0;";

		qry += "    select nvl(sum(curr),0) into v_curr from (";
		// --上日余额
		qry += "    select curr from incomebal where custid=v_custid  and daystr=v_nowdate0";
		qry += "    union all";
		// --本日初始应收款
		qry += "    select sum(a.curr) from firstincomecurr a";
		qry += "    where a.accid=" + Accid + " and a.custid=v_custid ";
		qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// --本日应收货款
		qry += "    union all";
		qry += "    select sum(case a.ntid when 1 then b.curr else -b.curr end) from wareouth a  left outer join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "    where a.accid=" + Accid + " and a.custid=v_custid and a.ntid>0";
		qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// --应收费用
		qry += "    union all";
		qry += "    select sum(a.curr) from incomecost a ";
		qry += "    where a.accid=" + Accid + " and a.custid=v_custid ";
		qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// --已收及折扣
		qry += "    union all";
		qry += "    select -sum(a.curr) from incomecurr a ";
		qry += "    where a.accid=" + Accid + " and a.custid=v_custid ";
		qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		qry += "    ) ;";
		qry += "    select creditcurr,creditok,v_curr-v_checkcurr+v_freecurr+v_zpaycurr,v_checkcurr-v_freecurr-v_zpaycurr ";
		qry += "       into v_creditcurr,v_creditok,v_balcurr,v_qkcurr from customer where custid=v_custid and accid=" + Accid + ";";
		qry += "    EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "     v_curr:=0;v_creditcurr:=0;v_creditok:=0;v_balcurr:=0;v_qkcurr:=0;";// where custid=v_custid and accid="+accid+";";
		qry += "    end;";
		qry += "    select v_curr,v_creditcurr,v_creditok,v_balcurr,v_qkcurr  into :curr,:creditcurr,:creditok,:balcurr,:qkcurr from dual;";
		qry += " end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("curr", new ProdParam(Types.FLOAT));
		param.put("creditcurr", new ProdParam(Types.FLOAT));
		param.put("creditok", new ProdParam(Types.INTEGER));
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
				+ ",\"CREDITCURR\": \"" + param.get("creditcurr").getParamvalue().toString() + "\""//
				+ ",\"CREDITOK\": \"" + param.get("creditok").getParamvalue().toString() + "\""//
				+ ",\"BALCURR\": \"" + param.get("balcurr").getParamvalue().toString() + "\""//
				+ ",\"QKCURR\": \"" + param.get("qkcurr").getParamvalue().toString() + "\"";
		return 1;
	}

	// 获取客户应收款余额(按时间取)
	public int doGetBalbytime(String nowdate) {
		if (Custid==0 )
		{
			errmess="custid不是一个有效值！";
			return 0;
		}
        String qry = "declare ";
        qry += "     v_nowdate varchar2(20);";
        //qry += "     v_accid number;";
        //qry += "     v_custid number;";
        qry += "     v_nowdate0 varchar2(10);";
        qry += "     v_curr  number;";
        qry += " begin";
        //qry += "    v_accid:=" + accid + "; ";
        //qry += "    v_custid:=" + custid + "; ";
        if (nowdate.length()<=0) qry += "    v_nowdate:=to_char(sysdate,'yyyy-mm-ddhh24:mi:ss');";
        else qry += "    v_nowdate:='" + nowdate + "'; ";
        qry += "    v_nowdate0:=to_char(to_date(substr(v_nowdate,1,10),'yyyy-mm-dd')-1,'yyyy-mm-dd'); ";

        qry += "    select nvl(sum(curr),0) into v_curr from (";
        //--上日余额
        qry += "    select curr from incomebal where custid=" + Custid + "  and daystr=v_nowdate0";
        qry += "    union all";
        //--本日初始应收款
        qry += "    select sum(a.curr) from firstincomecurr a";
        qry += "    where a.accid=" + Accid + " and a.custid=" + Custid + " ";
        qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
        //--本日应收货款
        qry += "    union all";
        qry += "    select sum(case a.ntid when 1 then b.curr else -b.curr end) from wareouth a  left outer join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
        qry += "    where a.accid=" +Accid + " and a.custid=" + Custid + " and a.ntid>0";
        qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
        //--应收费用
        qry += "    union all";
        qry += "    select sum(a.curr) from incomecost a ";
        qry += "    where a.accid=" + Accid + " and a.custid=" + Custid + " ";
        qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
        //--已收及折扣
        qry += "    union all";
        qry += "    select -sum(a.curr) from incomecurr a ";
        qry += "    where a.accid=" + Accid + " and a.custid=" + Custid + " ";
        qry += "    and a.statetag=1 and a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
        qry += "    ) ;";
        qry += "    select v_curr,creditcurr,creditok into :curr,:creditcurr,:creditok from customer where custid=" + Custid + " and accid=" + Accid + ";";
        qry += " end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("curr", new ProdParam(Types.FLOAT));
		param.put("creditcurr", new ProdParam(Types.FLOAT));
		param.put("creditok", new ProdParam(Types.INTEGER));
//		param.put("balcurr", new ProdParam(Types.FLOAT));
//		param.put("qkcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
		errmess = "\"msg\":\"操作成功！\",\"CURR\": \"" + param.get("curr").getParamvalue().toString() + "\""//
				+ ",\"CREDITCURR\": \"" + param.get("creditcurr").getParamvalue().toString() + "\""//
				+ ",\"CREDITOK\": \"" + param.get("creditok").getParamvalue().toString() + "\""//
				;
		return 1;
	}

}