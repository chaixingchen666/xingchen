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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-30 18:13:46
//*************************************
public class Salemanplan implements java.io.Serializable {
	private Long Accid;
	private Float Id;
	private Long Epid;
	private Date Saledate;
	private Float Curr;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setSaledate(Date saledate) {
		this.Saledate = saledate;
	}

	public String getSaledate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Saledate);
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
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
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update salemanplan set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
		if (Epid != null) {
			strSql1.append("epid,");
			strSql2.append(Epid + ",");
		}
		if (Saledate != null) {
			strSql1.append("saledate,");
			strSql2.append("'" + Saledate + "',");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=salemanplan_id.nextval; ");
		strSql.append("   insert into salemanplan (");
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
		strSql.append("   update salemanplan set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Saledate != null) {
			strSql.append("saledate='" + Saledate + "',");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
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
		strSql.append(" FROM salemanplan ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM salemanplan a");
		strSql.append(" join employe b on a.epid=b.epid");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from salemanplan");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 取店员销售计划
	public int doGetSalemanplan(long houseid, int yearnum, int monthnum) {
		Float totalcurr = (float) 0;
		Table tb = new Table();
		String qry = "select nvl(curr" + monthnum + ",0) as curr from houseplan where houseid=" + houseid + " and yearnum=" + yearnum;
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			totalcurr = Float.parseFloat(tb.getRow(0).get("CURR").toString());
		}
		String responsestr = "\"msg\":\"操作成功！\",\"TOTALCURR\":\"" + totalcurr + "\"";
		String tempdate = yearnum + "-" + monthnum + "-01";
		// 取店铺员工,只取10名员工
		qry = "select to_char(a.saledate,'yyyy-mm-dd') as datestr,to_char(saledate,'dd') as daystr,sum(a.curr) as curr from salemanplan a ";
		qry += "\n join employe b on a.epid=b.epid where b.statetag=1 and b.noused=0 ";
		qry += "\n and b.accid=" + Accid + " and b.houseid=" + houseid;
		// qry += "\n and a.saledate";
		// qry += "\n and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss') ";
		// qry += "\n and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n  and a.saledate>=Trunc(to_date('" + tempdate + "','yyyy-mm-dd'), 'MONTH')";
		qry += "\n  and a.saledate<=LAST_DAY(Trunc(to_date('" + tempdate + "','yyyy-mm-dd'), 'MONTH')) + 1 - 1 / 86400";
		qry += "\n  group by to_char(a.saledate,'yyyy-mm-dd'),to_char(saledate,'dd')";
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();

		String responsestr1 = ",\"daycurr\":[";

		Float totalcurr1 = (float) 0;
		Float curr = (float) 0;
		String fh = "";

		for (int i = 0; i < rs; i++) {
			curr = Float.parseFloat(tb.getRow(i).get("CURR").toString());
			responsestr1 += fh + "{\"DATESTR\":\"" + tb.getRow(i).get("DATESTR").toString() + "\""//
					+ ",\"DAYSTR\":\"" + tb.getRow(i).get("DAYSTR").toString() + "\"" //
					+ ",\"CURR\":\"" + curr + "\"}";
			fh = ",";

			totalcurr1 = totalcurr1 + curr;
		}
		responsestr1 += "]";
		responsestr += ",\"TOTALCURR1\":\"" + totalcurr1 + "\"" + responsestr1;
		errmess = responsestr;
		return 1;
	}

	// 自动均分店员销售计划
	public int doAutoSalemanplan(long houseid, int yearnum, int monthnum) {
		String tempdate = yearnum + "-" + monthnum + "-01";
		String qry = "declare ";
		qry += "\n     v_jhcurr number(16,2);";
		qry += "\n     v_mannum number;";
		qry += "\n     v_maxday number;";
		qry += "\n     v_daycurr number(16,2);";
		qry += "\n     v_retcs varchar(100);";
		qry += "\n  begin ";
		qry += "\n    begin";
		qry += "\n      select nvl(curr" + monthnum + ",0) into v_jhcurr from houseplan where houseid=" + houseid + " and yearnum=" + yearnum + ";";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n      v_jhcurr:=0; ";
		qry += "\n    end;";
		qry += "\n    if v_jhcurr=0 then";
		qry += "\n       v_retcs:= '0店铺未设定月销售计划金额！' ; ";
		qry += "\n       goto exit; ";
		qry += "\n    end if;";
		qry += "\n    select count(*) into v_mannum from employe where accid=" + Accid + " and houseid=" + houseid + " and statetag=1 and noused=0;";
		qry += "\n    if v_mannum=0 then";
		qry += "\n       v_retcs:= '0店铺未设定销售人员！' ; ";
		qry += "\n       goto exit; ";
		qry += "\n    end if;";
		qry += "\n    select to_number(to_char(last_day(to_date('" + tempdate + "','yyyy-mm-dd')),'dd')) into v_maxday from dual;";
		qry += "\n    v_daycurr:=round(v_jhcurr*10000/v_mannum/v_maxday,0);"; // 每日每人计划
		qry += "\n    delete from salemanplan a where exists (select 1 from employe b where a.epid=b.epid and b.houseid=" + houseid + "); ";
		qry += "\n    insert into salemanplan (id,epid,saledate,curr) ";
		qry += "\n    select salemanplan_id.nextval,a.epid, b.daystr,v_daycurr from employe a";
		qry += "\n    ,(SELECT TRUNC(to_date('" + tempdate + "','yyyy-mm-dd'), 'MM') + ROWNUM - 1 as daystr  FROM DUAL  CONNECT BY ROWNUM <= TO_NUMBER(TO_CHAR(LAST_DAY(to_date('" + tempdate
				+ "','yyyy-mm-dd')), 'dd'))) b ";
		qry += "\n    where a.accid=" + Accid + " and a.houseid=" + houseid + " ;";
		qry += "\n    v_retcs:='1操作成功！' ; ";
		qry += "\n    <<exit>>";
		qry += "\n    select v_retcs into :retcs from dual;";
		qry += "\n  end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 每日店员销售日计划明细
	public int Write(long houseid, String currdate, JSONObject jsonObject) {
		if (currdate == null || currdate.length() <= 0) {
			errmess = "currdate参数不是一个有效值！";
			return 0;
		}
		if (houseid == 0) {
			errmess = "houseid参数不是一个有效值！";
			return 0;

		}
		if (!jsonObject.has("epcurrlist")) {
			errmess = "epcurrlist未传入！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("epcurrlist");

		// Epid = (long) 0;
		// Curr = (float) 0;
		String qry = "declare ";
		qry += "\n     v_id number; ";
		qry += "\n begin ";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Epid = Long.parseLong(jsonObject2.getString("epid"));
			Curr = Float.parseFloat(jsonObject2.getString("curr"));
			qry += "\n   begin";
			qry += "\n     select id into v_id from salemanplan where epid=" + Epid + " and saledate=to_date('" + currdate + "','yyyy-mm-dd');";
			qry += "\n     update salemanplan set curr=" + Curr + " where id=v_id;";
			qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n     insert into salemanplan (id,epid,saledate,curr,lastop)";
			qry += "\n     values (salemanplan_id.nextval," + Epid + ",to_date('" + currdate + "','yyyy-mm-dd')," + Curr + ",'" + Lastop + "'); ";
			qry += "\n   end; ";
		}
		qry += "\n end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

}