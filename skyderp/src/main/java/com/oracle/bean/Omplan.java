package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-02 21:46:25
//*************************************
public class Omplan implements java.io.Serializable {
	private Long Omepid;
	private Long Omid;
	private Float Jhamt;
	private Float Jhcurr;
	private Long Jhskc;
	private Long Jhnum;
	private Float Sjamt;
	private Float Sjcurr;
	private Long Sjskc;
	private Long Sjnum;
	private String Lastop;
	private Date Lastdate;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setOmepid(Long omepid) {
		this.Omepid = omepid;
	}

	public Long getOmepid() {
		return Omepid;
	}

	public void setOmid(Long omid) {
		this.Omid = omid;
	}

	public Long getOmid() {
		return Omid;
	}

	public void setJhamt(Float jhamt) {
		this.Jhamt = jhamt;
	}

	public Float getJhamt() {
		return Jhamt;
	}

	public void setJhcurr(Float jhcurr) {
		this.Jhcurr = jhcurr;
	}

	public Float getJhcurr() {
		return Jhcurr;
	}

	public void setJhskc(Long jhskc) {
		this.Jhskc = jhskc;
	}

	public Long getJhskc() {
		return Jhskc;
	}

	public void setJhnum(Long jhnum) {
		this.Jhnum = jhnum;
	}

	public Long getJhnum() {
		return Jhnum;
	}

	public void setSjamt(Float sjamt) {
		this.Sjamt = sjamt;
	}

	public Float getSjamt() {
		return Sjamt;
	}

	public void setSjcurr(Float sjcurr) {
		this.Sjcurr = sjcurr;
	}

	public Float getSjcurr() {
		return Sjcurr;
	}

	public void setSjskc(Long sjskc) {
		this.Sjskc = sjskc;
	}

	public Long getSjskc() {
		return Sjskc;
	}

	public void setSjnum(Long sjnum) {
		this.Sjnum = sjnum;
	}

	public Long getSjnum() {
		return Sjnum;
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
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update omplan set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Omepid != null) {
			strSql1.append("omepid,");
			strSql2.append(Omepid + ",");
		}
		if (Omid != null) {
			strSql1.append("omid,");
			strSql2.append(Omid + ",");
		}
		if (Jhamt != null) {
			strSql1.append("jhamt,");
			strSql2.append(Jhamt + ",");
		}
		if (Jhcurr != null) {
			strSql1.append("jhcurr,");
			strSql2.append("'" + Jhcurr + "',");
		}
		if (Jhskc != null) {
			strSql1.append("jhskc,");
			strSql2.append(Jhskc + ",");
		}
		if (Jhnum != null) {
			strSql1.append("jhnum,");
			strSql2.append(Jhnum + ",");
		}
		if (Sjamt != null) {
			strSql1.append("sjamt,");
			strSql2.append(Sjamt + ",");
		}
		if (Sjcurr != null) {
			strSql1.append("sjcurr,");
			strSql2.append("'" + Sjcurr + "',");
		}
		if (Sjskc != null) {
			strSql1.append("sjskc,");
			strSql2.append(Sjskc + ",");
		}
		if (Sjnum != null) {
			strSql1.append("sjnum,");
			strSql2.append(Sjnum + ",");
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
		strSql.append("   v_id:=omplan_id.nextval; ");
		strSql.append("   insert into omplan (");
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
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update omplan set ");
		if (Omepid != null) {
			strSql.append("omepid=" + Omepid + ",");
		}
		if (Omid != null) {
			strSql.append("omid=" + Omid + ",");
		}
		if (Jhamt != null) {
			strSql.append("jhamt=" + Jhamt + ",");
		}
		if (Jhcurr != null) {
			strSql.append("jhcurr='" + Jhcurr + "',");
		}
		if (Jhskc != null) {
			strSql.append("jhskc=" + Jhskc + ",");
		}
		if (Jhnum != null) {
			strSql.append("jhnum=" + Jhnum + ",");
		}
		if (Sjamt != null) {
			strSql.append("sjamt=" + Sjamt + ",");
		}
		if (Sjcurr != null) {
			strSql.append("sjcurr='" + Sjcurr + "',");
		}
		if (Sjskc != null) {
			strSql.append("sjskc=" + Sjskc + ",");
		}
		if (Sjnum != null) {
			strSql.append("sjnum=" + Sjnum + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where omid=" + Omid + " and omepid=" + Omepid + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omplan ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omplan ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from omplan");
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