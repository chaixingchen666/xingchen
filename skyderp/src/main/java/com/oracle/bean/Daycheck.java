package com.oracle.bean;

import java.sql.Types;
// import java.text.DateFormat;
// import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
// import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
// import net.sf.json.JSONObject;

// *************************************
// @author:sunhong @date:2017-03-18 00:09:50
// *************************************
public class Daycheck implements java.io.Serializable {
	private Float Classid;
	private Long Houseid;
	private String Begindate; // begindate格式:yyyymmddhhmmss
	private String Enddate;
	private Float Currxs;
	private Float Amountxs;
	private Float Currfy;
	private Float Currqc;
	private Float Currye;
	private Float Curryk;
	private Float Currsjye;
	private Float Curryh;
	private Float Currjc;
	private Date Lastdate;
	private String Lastop;
	private String Remark;
	private Integer Statetag;
	private Float Currxj;
	private Long Epid;
	private String errmess;
	private String Nowdatetime;

	public void setNowdatetime(String nowdatetime) {
		Nowdatetime = nowdatetime;
	}

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setClassid(Float classid) {
		this.Classid = classid;
	}

	public Float getClassid() {
		return Classid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setBegindate(String begindate) {
		this.Begindate = begindate;
	}

	// public String getBegindate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Begindate);
	// }

	public void setEnddate(String enddate) {
		this.Enddate = enddate;
	}

	// public String getEnddate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Enddate);
	// }

	public void setCurrxs(Float currxs) {
		this.Currxs = currxs;
	}

	public Float getCurrxs() {
		return Currxs;
	}

	public void setAmountxs(Float amountxs) {
		this.Amountxs = amountxs;
	}

	public Float getAmountxs() {
		return Amountxs;
	}

	public void setCurrfy(Float currfy) {
		this.Currfy = currfy;
	}

	public Float getCurrfy() {
		return Currfy;
	}

	public void setCurrqc(Float currqc) {
		this.Currqc = currqc;
	}

	public Float getCurrqc() {
		return Currqc;
	}

	public void setCurrye(Float currye) {
		this.Currye = currye;
	}

	public Float getCurrye() {
		return Currye;
	}

	public void setCurryk(Float curryk) {
		this.Curryk = curryk;
	}

	public Float getCurryk() {
		return Curryk;
	}

	public void setCurrsjye(Float currsjye) {
		this.Currsjye = currsjye;
	}

	public Float getCurrsjye() {
		return Currsjye;
	}

	public void setCurryh(Float curryh) {
		this.Curryh = curryh;
	}

	public Float getCurryh() {
		return Curryh;
	}

	public void setCurrjc(Float currjc) {
		this.Currjc = currjc;
	}

	public Float getCurrjc() {
		return Currjc;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setCurrxj(Float currxj) {
		this.Currxj = currxj;
	}

	public Float getCurrxj() {
		return Currxj;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Classid == 0) {
			errmess = "班次id无效!";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append(" delete from  DAYCHECK ");
		strSql.append(" where Classid=" + Classid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(int jbtag) {

		if (Houseid == null || Houseid == 0) {
			errmess = "店铺无效！";
			return 0;
		}
		if (jbtag == 0)
			Epid = (long) 0;
		if (Currqc == null)
			Currqc = (float) 0;

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("classid,");
		strSql2.append("v_classid,");
		// if (Classid != null) {
		// strSql1.append("classid,");
		// strSql2.append("'" + Classid + "',");
		// }
		// if (Houseid != null) {
		strSql1.append("houseid,");
		strSql2.append(Houseid + ",");
		// }
		if (Begindate != null) {
			strSql1.append("begindate,");
			strSql2.append("v_begindate,");

			//			if (Begindate.length() == 14)// yyyymmddhhmmss
			//				strSql2.append("to_date('" + Func.subString(Begindate, 1, 12) + "00','yyyy-mm-dd hh24:mi:ss'),");
			//			else// yyyy-mm-dd hh:mm:ss
			//				strSql2.append("to_date('" + Func.subString(Begindate, 1, 16) + ":00','yyyy-mm-dd hh24:mi:ss'),");
		}
		// if (Enddate != null) {
		// strSql1.append("enddate,");
		// strSql2.append("'" + Enddate + "',");
		// }
		if (Currxs != null) {
			strSql1.append("currxs,");
			strSql2.append("'" + Currxs + "',");
		}
		if (Amountxs != null) {
			strSql1.append("amountxs,");
			strSql2.append("'" + Amountxs + "',");
		}
		if (Currfy != null) {
			strSql1.append("currfy,");
			strSql2.append("'" + Currfy + "',");
		}
		if (Currqc != null) {
			strSql1.append("currqc,");
			strSql2.append("'" + Currqc + "',");
		}
		if (Currye != null) {
			strSql1.append("currye,");
			strSql2.append("'" + Currye + "',");
		}
		if (Curryk != null) {
			strSql1.append("curryk,");
			strSql2.append("'" + Curryk + "',");
		}
		if (Currsjye != null) {
			strSql1.append("currsjye,");
			strSql2.append("'" + Currsjye + "',");
		}
		if (Curryh != null) {
			strSql1.append("curryh,");
			strSql2.append("'" + Curryh + "',");
		}
		if (Currjc != null) {
			strSql1.append("currjc,");
			strSql2.append("'" + Currjc + "',");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Currxj != null) {
			strSql1.append("currxj,");
			strSql2.append("'" + Currxj + "',");
		}
		if (Epid != null) {
			strSql1.append("epid,");
			strSql2.append(Epid + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_classid number; ");
		strSql.append("\n   v_begindate date; ");  //yyyy-mm-dd hh:mm:ss
		strSql.append("\n begin ");
		if (Begindate.length() == 14)// yyyymmddhhmmss
			strSql.append("\n   v_begindate:=to_date('" + Func.subString(Begindate, 1, 12) + "00','yyyy-mm-dd hh24:mi:ss');");
		else// yyyy-mm-dd hh:mm:ss
			strSql.append("\n   v_begindate:=to_date('" + Func.subString(Begindate, 1, 16) + ":00','yyyy-mm-dd hh24:mi:ss');");
		strSql.append("\n  if v_begindate>sysdate then");//开班日期不能超出系统日期
		strSql.append("\n     v_begindate:=sysdate;");
		strSql.append("\n  end if;");

		strSql.append("\n   begin ");
		strSql.append("\n      select classid into v_classid from daycheck where statetag=0 and houseid=" + Houseid + " and epid=" + Epid + ";");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");

		strSql.append("\n      v_classid:=DAYCHECK_classid.nextval; ");
		// strSql.append(" v_currqc:=0;");
		strSql.append("\n      insert into DAYCHECK (");
		strSql.append("\n       " + strSql1.toString());
		strSql.append("\n       ) values (");
		strSql.append("\n        " + strSql2.toString());
		strSql.append("\n       );");
		strSql.append("\n   end;");
		strSql.append("\n   select v_classid into :classid from dual; ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("classid", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Classid = Float.parseFloat(param.get("classid").getParamvalue().toString());
			errmess = Classid.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update DAYCHECK set ");
		if (Classid != null) {
			strSql.append("classid='" + Classid + "',");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Begindate != null) {
			strSql.append("begindate='" + Begindate + "',");
		}
		if (Enddate != null) {
			strSql.append("enddate='" + Enddate + "',");
		}
		if (Currxs != null) {
			strSql.append("currxs='" + Currxs + "',");
		}
		if (Amountxs != null) {
			strSql.append("amountxs='" + Amountxs + "',");
		}
		if (Currfy != null) {
			strSql.append("currfy='" + Currfy + "',");
		}
		if (Currqc != null) {
			strSql.append("currqc='" + Currqc + "',");
		}
		if (Currye != null) {
			strSql.append("currye='" + Currye + "',");
		}
		if (Curryk != null) {
			strSql.append("curryk='" + Curryk + "',");
		}
		if (Currsjye != null) {
			strSql.append("currsjye='" + Currsjye + "',");
		}
		if (Curryh != null) {
			strSql.append("curryh='" + Curryh + "',");
		}
		if (Currjc != null) {
			strSql.append("currjc='" + Currjc + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Currxj != null) {
			strSql.append("currxj='" + Currxj + "',");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
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
		strSql.append(" FROM DAYCHECK ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM DAYCHECK a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists(int jbtag) {
		if (Houseid == 0) {
			errmess = "店铺无效！";
			return 0;
		}
		if (jbtag == 0)// 交班方式0=店铺统一交班,1=分收银员交班
			Epid = (long) 0;

		StringBuilder strSql = new StringBuilder();
		strSql.append("select classid from daycheck where statetag=0 and houseid=" + Houseid + " and epid=" + Epid);
		Table tb = new Table();
		tb = DbHelperSQL.Query(strSql.toString()).getTable(1);
		if (tb.getRowCount() == 0) {
			if (jbtag == 0)
				errmess = "当前店铺无开班记录，是否立即开班？";
			else
				errmess = "当前店铺职员无开班记录，是否立即开班？";
			return 0;
		}
		errmess = tb.getRow(0).get("CLASSID").toString();
		return 1;

	}

	// 交班
	public int End(long accid, int jbtag) {
		if (Classid == 0) {
			errmess = "班次无效1！";
			return 0;
		}
		if (jbtag == 0)
			Epid = (long) 0;
		//		if (Func.isNull(Enddate)) {
		//			Enddate = Nowdatetime;
		//		}
		Table tb = new Table();
		String qry = "";
		if (Func.isNull(Enddate)) {
			qry = "update daycheck set  enddate=to_date(to_char(sysdate,'yyyy-mm-dd hh24:mi:')||'59','yyyy-mm-dd hh24:mi:ss') where statetag=0 and classid=" + Classid;
		} else {
			//yyyy-mm-dd hh:mm:ss
			Enddate = Func.subString(Enddate, 1, 16) + ":59";
			qry = "declare";
			qry += "\n   v_enddate varchar2(20);";
			qry += "\n   v_sysdate varchar2(20);";
			qry += "\n begin";
			qry += "\n   v_enddate:='" + Enddate + "';";
			qry += "\n   v_sysdate:=to_char(sysdate,'yyyy-mm-dd hh24:mi:')||'59';";
			qry += "\n   if v_enddate>v_sysdate then ";
			qry += "\n      v_enddate:=v_sysdate;";
			qry += "\n   end if;";
			qry += "\n   update daycheck set enddate=to_date(v_enddate,'yyyy-mm-dd hh24:mi:ss') where statetag=0 and classid=" + Classid + ";";
			qry += "\n end;";
		}
		//		 System.out.println(qry);
		DbHelperSQL.ExecuteSql(qry);

		qry = "select a.houseid,a.statetag,a.currqc,a.currxj,a.currsjye,a.currye,a.curryh,a.currjc,a.curryk,a.lastop,a.remark,b.housename ";
		qry += "\n ,to_char(a.begindate,'yyyy-mm-dd hh24:mi:ss') as begindate";
		qry += "\n ,to_char(a.enddate,'yyyy-mm-dd hh24:mi:ss') as enddate";
		qry += "\n from daycheck a left outer join warehouse b on a.houseid=b.houseid where  a.classid=" + Classid;
		//		 System.out.println(qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "班次无效2！";
			return 0;
		}
		int statetag = Integer.parseInt(tb.getRow(0).get("STATETAG").toString());
		long houseid = Long.parseLong(tb.getRow(0).get("HOUSEID").toString());
		String mindate = tb.getRow(0).get("BEGINDATE").toString();
		String maxdate = tb.getRow(0).get("ENDDATE").toString();
		Float currqc = Float.parseFloat(tb.getRow(0).get("CURRQC").toString());
		Float currye = Float.parseFloat(tb.getRow(0).get("CURRYE").toString());
		Float currxj = Float.parseFloat(tb.getRow(0).get("CURRXJ").toString());
		Float curryh = Float.parseFloat(tb.getRow(0).get("CURRYH").toString());
		Float currjc = Float.parseFloat(tb.getRow(0).get("CURRJC").toString());
		Float curryk = Float.parseFloat(tb.getRow(0).get("CURRYK").toString());
		Float currsjye = Float.parseFloat(tb.getRow(0).get("CURRSJYE").toString());
		// Float currxs =
		// Float.parseFloat(tb.getRow(0).get("currxs").toString());

		String responsestr = "\"msg\":\"操作成功！\",\"CLASSID\":\"" + Classid + "\"" //
				+ ",\"HOUSEID\":\"" + houseid + "\"" //
				+ ",\"MINDATE\":\"" + mindate + "\""//
				+ ",\"MAXDATE\":\"" + maxdate + "\""//
				+ ",\"LASTOP\":\"" + tb.getRow(0).get("LASTOP").toString() + "\"" // 收银员
				+ ",\"STATETAG\":\"" + statetag + "\"" // 上班结存
				+ ",\"REMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\"" // 收银员
				+ ",\"HOUSENAME\":\"" + tb.getRow(0).get("HOUSENAME").toString() + "\"";
		// String retcs = "";

		// DataTable dt = new DataTable();
		// 销售汇总
		qry = " select wareid,wareno,warename,units,sum(amount) as amount,sum(curr) as curr from (";
		qry += "\n select b.wareid,c.wareno,c.warename,c.units ,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr";
		qry += "\n from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n join warecode c on b.wareid=c.wareid";
		qry += "\n  where a.accid=" + accid + " and a.statetag=1 ";
		if (jbtag > 0)
			qry += " and a.operant='" + Lastop + "'";
		else if (houseid > 0)
			qry += "\n     and a.houseid=" + houseid;
		// if (jbtag > 0) qry += " and a.";
		qry += "\n  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  group by b.wareid,c.wareno,c.warename,c.units";

		qry += "\n union all";
		qry += "\n select b.wareid,c.wareno,c.warename,c.units ,sum(b.amount) as amount,sum(b.curr) as curr";
		qry += "\n from shopsaleh a join shopsalem b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n join warecode c on b.wareid=c.wareid";
		qry += "\n  where a.accid=" + accid + " and a.statetag=1 ";
		if (jbtag > 0)
			qry += "\n and a.operant='" + Lastop + "'";
		else if (houseid > 0)
			qry += "\n     and a.houseid=" + houseid;

		qry += "\n  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  group by b.wareid,c.wareno,c.warename,c.units";
		qry += " ) group by wareid,wareno,warename,units";
		// qry += "\n order by c.wareno";
		// WriteLogTXT("debug", "HouseWorktotal2", qry);
		//System.out.println(qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		int count = tb.getRowCount();
		String fh = "";

		String retcs = ",\"XSLIST\":[";
		Float currxs = (float) 0;
		Float amountxs = (float) 0;
		for (int i = 0; i < count; i++) {
			retcs += fh + "{\"WAREID\":\"" + tb.getRow(i).get("WAREID").toString() + "\"," //
					+ "\"WARENAME\":\"" + tb.getRow(i).get("WARENAME").toString() + "\"," //
					+ "\"WARENO\":\"" + tb.getRow(i).get("WARENO").toString() + "\"," //
					+ "\"UNITS\":\"" + tb.getRow(i).get("UNITS").toString() + "\","//
					+ "\"AMOUNT\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\"," //
					+ "\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"}";
			fh = ",";
			currxs += Float.parseFloat(tb.getRow(i).get("CURR").toString());
			amountxs += Float.parseFloat(tb.getRow(i).get("AMOUNT").toString());
		}
		retcs += "]";

		// 费用汇总

		qry = "select a.cgid,b.cgname ,sum(case a.fs when 1 then a.curr else 0 end) as curr0,sum(case a.fs when 0 then a.curr else 0 end) as curr1,sum(case a.fs when 0 then -a.curr else a.curr end) as curr2";
		qry += " from housecost a join chargescode b on a.cgid=b.cgid";
		qry += "\n  where a.accid=" + accid + " and a.statetag=1 ";
		if (jbtag > 0)
			qry += " and a.operant='" + Lastop + "'";
		else if (houseid > 0)
			qry += "\n  and a.houseid=" + houseid;
		qry += "\n  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  group by a.cgid,b.cgname";
		qry += "\n  order by a.cgid";
		// WriteLogTXT("debug", "HouseWorktotal3", qry);
		//		System.out.println(qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		count = tb.getRowCount();
		Float currfy = (float) 0;
		retcs += ",\"FYLIST\":[";
		fh = "";
		for (int i = 0; i < count; i++) {
			retcs += fh + "{\"CGID\":\"" + tb.getRow(i).get("CGID").toString() + "\"," //
					+ "\"CGNAME\":\"" + tb.getRow(i).get("CGNAME").toString() + "\","//
					+ "\"CURR0\":\"" + tb.getRow(i).get("CURR0").toString() + "\","//
					+ "\"CURR1\":\"" + tb.getRow(i).get("CURR1").toString() + "\"," //
					+ "\"CURR2\":\"" + tb.getRow(i).get("CURR2").toString() + "\"}";
			fh = ",";
			currfy += Float.parseFloat(tb.getRow(i).get("CURR2").toString());
		}
		retcs += "]";

		// 收支汇总 结算方式
		qry = " select payid,payno,payname,sum(curr0) as curr0,sum(curr1) as curr1,sum(curr0-curr1) as curr2 from (";
		// 销售收入
		// --销售收款
		qry += "\n  select  a.payid,b.payno,b.payname,sum(a.curr) as curr0 ,0.00 as curr1";
		qry += "\n  from incomecurr a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  where a.accid=" + accid + " and a.statetag=1 and a.payid>0 ";
		if (jbtag > 0)
			qry += " and a.operant='" + Lastop + "'";
		else if (houseid > 0)
			qry += "\n     and a.houseid=" + houseid;
		qry += "\n  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  group by a.payid,b.payno,b.payname ";
		// --零售收款
		qry += "\n  union all ";
		qry += "\n  select  a.payid,b.payno,b.payname,sum(a.curr) as curr0 ,0.00 as curr1 ";
		qry += "\n  from waresalepay a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  join wareouth c on a.accid=c.accid and a.noteno=c.noteno ";
		qry += "\n  where c.accid=" + accid + " and c.statetag=1 and c.ntid=0";// and b.payno<>'V' ";
		if (jbtag > 0)
			qry += " and c.cashier='" + Lastop + "'";
		else if (houseid > 0)
			qry += "\n     and c.houseid=" + houseid;
		qry += "\n  and c.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and c.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  group by a.payid,b.payno,b.payname ";

		// --商场零售收款
		qry += "\n  union all ";
		qry += "\n  select a.payid,b.payno,b.payname,sum(a.curr) as curr0 ,0.00 as curr1 ";
		qry += "\n  from shopsalepay a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  join shopsaleh c on a.accid=c.accid and a.noteno=c.noteno ";
		qry += "\n  where c.accid=" + accid + " and c.statetag=1 ";//and b.payno<>'V' ";
		if (jbtag > 0)
			qry += " and c.cashier='" + Lastop + "'";
		else if (houseid > 0) {
			qry += "\n and c.houseid=" + houseid;
			// qry += "\n and exists (select 1 from shopsalem d where
			// c.accid=d.accid and c.noteno=d.noteno and d.houseid=" + houseid +
			// ")";
		}
		qry += "\n  and c.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and c.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  group by a.payid,b.payno,b.payname ";

		// 零售开票找零
		qry += "\n  union all ";
		qry += "\n  select  b.payid,b.payno,b.payname,-sum(a.changecurr) as curr0 ,0.00 as curr1 ";
		qry += "\n  from wareouth a ";
		qry += "\n  left outer join payway b on a.accid=b.accid and b.payno='0' ";
		// qry += "\n join wareouth c on a.accid=c.accid and a.noteno=c.noteno
		// ";
		qry += "\n  where a.accid=" + accid + " and a.statetag=1 and a.ntid=0  ";
		if (jbtag > 0)
			qry += " and a.cashier='" + Lastop + "'";
		else if (houseid > 0)
			qry += "\n     and a.houseid=" + houseid;
		qry += "\n  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  group by b.payid,b.payno,b.payname ";

		// 商场开票找零
		qry += "\n  union all ";
		qry += "\n  select  b.payid,b.payno,b.payname,-sum(a.changecurr) as curr0 ,0.00 as curr1 ";
		qry += "\n  from shopsaleh a ";
		qry += "\n  left outer join payway b on a.accid=b.accid and b.payno='0' ";
		qry += "\n  where a.accid=" + accid + " and a.statetag=1 and a.ntid=0  ";
		if (jbtag > 0)
			qry += " and a.cashier='" + Lastop + "'";
		else if (houseid > 0) {
			qry += "\n  and exists (select 1 from shopsalem d where a.accid=d.accid and a.noteno=d.noteno and d.houseid=" + houseid + ")";
		}
		qry += "\n  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  group by b.payid,b.payno,b.payname ";

		// --储值收款
		qry += "\n  union all ";
		qry += "\n  select  a.payid,b.payno,b.payname||'vip' as payname,sum(a.paycurr) as curr0 ,0.00 as curr1 ";
		qry += "\n  from guestcurr a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  join guestvip c on a.guestid=c.guestid ";
		qry += "\n  where c.accid=" + accid + "  and a.fs=0 and a.payid>0 ";
		if (jbtag > 0)
			qry += " and a.operant='" + Lastop + "'";
		else if (houseid > 0)
			qry += "\n     and a.houseid=" + houseid;
		qry += "\n  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  group by a.payid,b.payno,b.payname||'vip' ";

		// --店铺费用支出
		qry += "\n  union all ";
		qry += "\n  select  a.payid,b.payno,b.payname,0 as curr0 ,sum(case a.fs when 1 then a.curr else -a.curr end) as curr1 ";
		qry += "\n  from housecost a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  where a.accid=" + accid + " and a.statetag=1 ";
		if (jbtag > 0)
			qry += " and a.operant='" + Lastop + "'";
		else if (houseid > 0)
			qry += "\n     and a.houseid=" + houseid;
		qry += "\n  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  group by a.payid,b.payno,b.payname ";

		qry += "\n   ) ";
		qry += "\n  group by payid,payno,payname ";
		qry += "\n  order by payno ";
		//		 System.out.println( qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		count = tb.getRowCount();

		// System.out.println( "1");
		retcs += ",\"PAYCURRLIST\":[";
		currxj = (float) 0;
		fh = "";
		for (int i = 0; i < count; i++) {
			// System.out.println(tb.getRow(i).get("PAYNO").toString());
			retcs += fh + "{\"PAYID\":\"" + tb.getRow(i).get("PAYID").toString() + "\"," //
					+ "\"PAYNAME\":\"" + tb.getRow(i).get("PAYNAME").toString() + "\"," //
					+ "\"PAYNO\":\"" + tb.getRow(i).get("PAYNO").toString() + "\"," //
					+ "\"PAYCURR0\":\"" + tb.getRow(i).get("CURR0").toString() + "\"," //
					+ "\"PAYCURR1\":\"" + tb.getRow(i).get("CURR1").toString() + "\","//
					+ "\"PAYCURR2\":\"" + tb.getRow(i).get("CURR2").toString() + "\"}";
			if (tb.getRow(i).get("PAYNO").toString().equals("0"))
				currxj += Float.parseFloat(tb.getRow(i).get("CURR2").toString());
			fh = ",";
		}

		//积分抵扣作为特殊的一种结算方式
		qry = "select nvl(sum(curr),0) as curr from (";
		qry += "\n select sum(jfcurr) as curr";
		qry += "\n from wareouth a";
		qry += "\n  where a.accid=" + accid + " and a.statetag=1 and a.ntid=0  and a.jfcurr<>0 ";
		if (jbtag > 0)
			qry += " and a.cashier='" + Lastop + "'";
		else if (houseid > 0)
			qry += "\n     and a.houseid=" + houseid;
		qry += "\n  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  union all";
		qry += "\n select sum(jfcurr) as curr";
		qry += "\n from shopsaleh a";
		qry += "\n  where a.accid=" + accid + " and a.statetag=1 and a.jfcurr<>0  ";
		if (jbtag > 0)
			qry += " and a.cashier='" + Lastop + "'";
		else if (houseid > 0)
			qry += "\n     and a.houseid=" + houseid;
		qry += "\n  and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += "\n  and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')  ";
		qry += ")";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			retcs += fh + "{\"PAYID\":\"-1\"," //
					+ "\"PAYNAME\":\"<积分抵扣>\"," //
					+ "\"PAYNO\":\"*\"," //
					+ "\"PAYCURR0\":\"" + tb.getRow(0).get("CURR").toString() + "\"," //
					+ "\"PAYCURR1\":\"0\","//
					+ "\"PAYCURR2\":\"" + tb.getRow(0).get("CURR").toString() + "\"}";
		}
		// System.out.println(currxj);
		retcs += "]";
		if (statetag == 0) {
			currye = currqc + currxj;
			currsjye = currye;
			curryk = currsjye - currye;
			currjc = currsjye - curryh;
			qry = "update daycheck set currye=" + currye + ",currsjye=" + currsjye + ",curryk=0,currxj=" + currxj //
					+ ",currjc=" + currjc + ",currfy=" + currfy;
			qry += " ,currxs=" + currxs + ",amountxs=" + amountxs;
			qry += " where classid=" + Classid;
			DbHelperSQL.ExecuteSql(qry);

		}

		responsestr += ",\"CURRQC\":\"" + Func.FormatNumber(currqc, 2) + "\"" // 上班结存
				+ ",\"CURRXJ\":\"" + Func.FormatNumber(currxj, 2) + "\"" // 现金收入
				+ ",\"CURRYE\":\"" + Func.FormatNumber(currye, 2) + "\"" // 本班余额
				+ ",\"CURRSJYE\":\"" + Func.FormatNumber(currsjye, 2) + "\"" // 实际余额
				+ ",\"CURRYK\":\"" + Func.FormatNumber(curryk, 2) + "\"" // 损益金额
				+ ",\"CURRYH\":\"" + Func.FormatNumber(curryh, 2) + "\"" // 银行存款
				+ ",\"CURRJC\":\"" + Func.FormatNumber(currjc, 2) + "\"" // 本班结存
				+ ",\"CURRXS\":\"" + Func.FormatNumber(currxs, 2) + "\"" // 销售金额
		;
		errmess = responsestr + retcs;
		return 1;
	}

	// 交班
	public int WorkClose() {

		if (Currsjye == null) {
			errmess = "实际余额无效！";
			return 0;
		}
		// decimal currsjye = decimal.Parse(ht["currsjye"].ToString());

		if (Curryh == null) {
			errmess = "银行存款无效！";
			return 0;
		}
		// decimal curryh = decimal.Parse(ht["curryh"].ToString());

		String qry = "declare ";
		qry += "\n   v_curryh  number(16,2); ";
		// qry += " v_currxj number(16,2); ";
		qry += "\n   v_currsjye  number(16,2); ";
		qry += "\n begin  ";
		qry += "\n   v_curryh:=" + Curryh + "; ";
		// qry += " v_currxj:=" + currxj + "; ";
		qry += "\n   v_currsjye:=" + Currsjye + "; ";
		qry += "\n   update daycheck set statetag=1,lastdate=sysdate,lastop='" + Lastop + "'";
		// ,enddate=to_date('" + enddate + "','yyyymmddhh24miss')
		if (Remark == null)
			qry += ",remark='" + Remark + "'";
		qry += "   ,curryh=v_curryh,currsjye=v_currsjye"; // ,currxj=v_currxj
		// qry += " ,currye=currqc+v_currxj"; //本班余额=上班结存+现金小计
		qry += "   ,curryk=v_currsjye-currye"; // 损益金额=实际余额-本班余额
		qry += "   ,currjc=v_currsjye-v_curryh"; // 本班结存=实际余额-银行存款
		qry += "\n   where classid=" + Classid + "; ";
		qry += "\n end;";
		// int ret = DbHelperSQL.ExecuteSql(qry);
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}
}