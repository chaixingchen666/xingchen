/**
 * 
 */
package com.oracle.bean;

import com.comdot.data.Table;
import com.common.tool.Func;

import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class vTotalcustcheck {
	private Long Accid = (long) 0;
	private Long Userid = (long) 0;

	private Long Houseid = (long) 0;
	private Long Custid = (long) 0;

	private String Maxdate;

	private String Mindate;

	private String Houseidlist = "";
	private String Custidlist = "";
	private String errmess = "";
	private String Areaname = "";

	private String Nowdate = "";
	private String Accbegindate = "";
	private Integer Qxbj = 0;

	private Integer Maxday = 0;
	private String Calcdate = "";

	public void setCalcdate(String calcdate) {
		this.Calcdate = calcdate;
	}

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public String getErrmess() {
		return errmess;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public void setMaxdate(String maxdate) {
		Maxdate = maxdate;
	}

	public void setMindate(String mindate) {
		Mindate = mindate;
	}

	// 汇总期初余额
	public int doTotalqc(String mindate, String maxdate) {
		// System.out.println("mindate=" + mindate + " maxdate=" + maxdate);
		String mindate0 = Func.DateToStr(Func.getPrevDay(mindate));
		mindate += " 00:00:00";
		maxdate += " 23:59:59";
		String qry = "declare ";
		qry += "\n    v_epid number; ";
		qry += "\n begin ";
		qry += "\n    delete from v_incomebal_data where epid=" + Userid + ";";
		qry += "\n    insert into v_incomebal_data (id,epid,custid,houseid,curr) ";
		qry += "\n    select v_incomebal_data_id.nextval," + Userid + ",custid,houseid,curr from ( ";

		qry += "\n    select custid, houseid,nvl(sum(curr),0) as curr from ( ";
		// 期初余额
		qry += "\n    select a.custid,a.houseid,sum(a.curr) as curr ";
		qry += "\n    from incomebal a ";
		qry += "\n    left outer join customer b on a.custid=b.custid ";
		qry += "\n    where a.daystr='" + mindate0 + "' ";
		qry += "\n    and b.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		if (!Func.isNull(Areaname))
			qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by a.custid,a.houseid";

		qry += "\n    union all  ";
		// --初始应收
		qry += "\n    select a.custid,a.houseid,sum(a.curr) as curr ";
		qry += "\n    from firstincomecurr a  ";
		qry += "\n    left outer join customer b on a.custid=b.custid ";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by a.custid,a.houseid";
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    union all ";
		// --本期销售应收
		// qry += "\n select a.custid,to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),a.noteno,cast('批发' as nvarchar2(100)),a.remark as
		// remark0,'XS' as notetype,sum(b.amount) as xsamt,sum(b.curr) as
		// xscurr,0 as xtamt,0 as xtcurr,sum(b.curr),0,0 ";
		qry += "\n    select a.custid,a.houseid,sum(a.totalcurr) as curr ";
		qry += "\n    from wareouth a  ";
		// qry += "\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno
		// ";
		qry += "\n    left outer join customer c on a.custid=c.custid ";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.ntid=1 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and c.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		// qry += "\n group by a.custid,a.notedate,a.noteno,a.remark";
		qry += "\n    group by a.custid,a.houseid";
		qry += "\n    union all ";
		// --本期销售应退
		// qry += "\n select a.custid,to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),a.noteno,cast('批发退货' as nvarchar2(100)),a.remark as
		// remark0,'XT' as notetype,0 as xsamt,0 as xscurr,sum(b.amount) as
		// xtamt,sum(b.curr) as xtcurr,-sum(b.curr),0,0 ";
		qry += "\n    select a.custid,a.houseid,-sum(a.totalcurr) as curr ";
		qry += "\n    from wareouth a  ";
		// qry += "\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno
		// ";
		qry += "\n    left outer join customer c on a.custid=c.custid ";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.accid=" + Accid;
		qry += "      and a.ntid=2 ";// and to_char(a.notedate,'yyyy-mm-dd')
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and c.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		// qry += "\n group by a.custid,a.notedate,a.noteno,a.remark ";
		qry += "\n    group by a.custid,a.houseid";
		qry += "\n    union all ";
		// --销售费用
		qry += "\n    select a.custid,a.houseid,sum(a.curr) as curr ";
		qry += "\n    from incomecost a  ";
		qry += "\n    left outer join customer b on a.custid=b.custid ";
		// qry += "\n left outer join chargescode c on a.cgid=c.cgid ";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by a.custid,a.houseid";
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    union all ";
		// --销售折让
		qry += "\n    select a.custid,a.houseid,-sum(a.curr) as curr ";
		qry += "\n    from incomecurr a  ";
		qry += "\n    left outer join customer b on a.custid=b.custid ";
		// -- left outer join payway c on a.payid=c.payid
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.payid=0 and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by a.custid,a.houseid";
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    union all ";
		// --销售已收
		qry += "\n    select a.custid,a.houseid,-sum(a.curr) as curr";
		qry += "\n    from incomecurr a  ";
		qry += "\n    left outer join customer b on a.custid=b.custid ";
		qry += "\n    left outer join payway c on a.payid=c.payid ";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.payid>0 and a.accid=" + Accid;
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by a.custid,a.houseid";
		qry += "\n    ) group by custid,houseid";
		qry += "\n    ); ";
		qry += "\n end;";
		// System.out.println(qry);
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "汇总期初失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	public int doTotal(int dateid) {
        //dateid:0=本日，1=昨日，2=本周，3=本月 ,4=指定日期
		Date Nowday = Func.StrToDate(Nowdate);
		if (dateid == 0) // 本日
		{
			Maxdate = Nowdate;// nowday.toString("yyyy-MM-dd");
			Mindate = Nowdate;
		} else if (dateid == 1) // 昨日
		{
			Maxdate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
			Mindate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
		} else if (dateid == 2) // 本周
		{
			Maxdate = Nowdate;// nowday.toString("yyyy-MM-dd");
			Mindate = Func.DateToStr(Func.getFirstDayOfWeek(Nowday));
		} else if (dateid == 3) // 本月
		{
			Maxdate = Nowdate;// nowday.toString("yyyy-MM-dd");
			Mindate = Func.DateToStr(Func.getFirstDayOfMonth(Nowday));
			// maxdate = nowday.toString("yyyy-MM-dd");
			// mindate = nowday.AddDays(-(nowday.Day) + 1).toString("yyyy-MM-dd");//本月第一天
		} else {
			if (Mindate.length() < 0 || Maxdate.length() < 0) {
				errmess = "mindate或maxdate不是一个有效值！";
				return 0;
			}

		}

		
		if (Func.isNull(Mindate) || Func.isNull(Maxdate)) {
			errmess = "mindate或maxdate不是一个有效值";
			return 0;
		}
		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		String warning = mdv.getErrmess();

		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate;

		if (Mindate.length() <= 10)
			Mindate += " 00:00:00";
		if (Maxdate.length() <= 10)
			Maxdate += " 23:59:59";
		String mindate0 = Func.DateToStr(Func.getPrevDay(Mindate));
		// String maxdate0 = Mindate;
		int bj = 0;
		if (mindate0.compareTo(Calcdate) > 0) {
			// maxdate0 = Func.DateToStr(Func.getPrevDay(mindate0)) + "
			// 00:00:00";

			doTotalqc(Func.DateToStr(Func.getNextDay(Calcdate)), Func.DateToStr(Func.getPrevDay(Mindate)));
			// System.out.println("ok");
			mindate0 = Calcdate;
			bj = 1;
		}

		if (Func.isNull(Houseidlist) && Houseid > 0)
			Houseidlist = Houseid.toString();

		if (Func.isNull(Custidlist) && Custid > 0)
			Custidlist = Custid.toString();

		String qry = "declare ";
		qry += "\n    v_epid number; ";
		qry += "\n    v_accid number; ";
		qry += "\n    v_mindate varchar2(10); ";
		qry += "\n    v_maxdate varchar2(10); ";
		qry += "\n    v_mindate0 varchar2(10); ";
		qry += "\n    v_curr1 number; ";
		qry += "\n    v_curr2 number; ";
		qry += "\n    v_curr3 number; ";
		qry += "\n    v_balcurr number; ";
		qry += "\n    v_id number; ";
		qry += "\n    v_custid number; ";
		qry += "\n    v_custid1 number; ";
		qry += "\n    v_bj  number(1); ";
		qry += "\n    v_custnum  number(10); "; // 总客户数
		qry += "\n    v_qkcustnum  number(10); "; // 总客户数
		// v_custid,v_id,v_curr1,v_curr2,v_curr3
		qry += "\n begin ";
		// qry+=" insert into v_wlcheck_temp
		// (notedate,remark,noteno,curr0,curr1,curr2) ";
		qry += "\n    select count(*) into v_custnum from customer where accid=" + Accid + " and statetag=1;";
		qry += "\n    delete from v_custcheck_data where epid=" + Userid + "; ";
		qry += "\n    insert into v_custcheck_data (id,epid,custid,notedate,noteno,notetype,remark,remark0,xsamt,xscurr,xtamt,xtcurr,fycurr,curr1,curr2,curr3) ";
		qry += "\n    select v_custcheck_data_id.nextval," + Userid
				+ ",custid, notedate,noteno,notetype,remark,remark0,nvl(xsamt,0),nvl(xscurr,0),nvl(xtamt,0),nvl(xtcurr,0),nvl(fycurr,0),nvl(curr1,0),nvl(curr2,0),nvl(curr3,0) from ( ";
		if (bj == 1) { // 要计算期初

			qry += "\n    select a.custid,' ' as notedate,cast(' ' as nvarchar2(20)) as noteno,cast('期初' as nvarchar2(100)) as remark,cast('' as nvarchar2(100)) as remark0,'**' as notetype,0 as xsamt,0 as xscurr,0 as xtamt,0 as xtcurr,0 as fycurr,0 as curr1,0 as curr2,sum(a.curr) as curr3 ";
			qry += "\n    from v_incomebal_data a ";
			qry += "\n    left outer join customer b on a.custid=b.custid ";
			qry += "\n    where a.epid='" + Userid + "' ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			// qry += "\n and b.accid=" + Accid + " ";
			if (!Func.isNull(Areaname))
				qry += " and b.areaname like '" + Areaname + "%'";
			if (!Func.isNull(Custidlist))
				qry += "  and " + Func.getCondition("a.custid", Custidlist);
			if (!Func.isNull(Houseidlist))
				qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
			qry += "\n    group by a.custid";

			qry += "\n   union all";
			qry += "\n   select a.custid,' ' as notedate,cast(' ' as nvarchar2(20)) as noteno,cast('期初' as nvarchar2(100)) as remark,cast('' as nvarchar2(100)) as remark0,'**' as notetype,0 as xsamt,0 as xscurr,0 as xtamt,0 as xtcurr,0 as fycurr,0 as curr1,0 as curr2,0 as curr3 ";
			qry += "\n   from customer a ";
			qry += "\n   where a.accid=" + Accid + " ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (!Func.isNull(Areaname))
				qry += " and a.areaname like '" + Areaname + "%'";

			if (!Func.isNull(Custidlist))
				qry += " and " + Func.getCondition("a.custid", Custidlist);
			qry += "\n   and not exists (select 1 from v_incomebal_data b where a.custid=b.custid and b.epid=" + Userid;
			if (!Func.isNull(Houseidlist))
				qry += " and " + Func.getCondition("b.houseid", Houseidlist);
			qry += "\n )";

		} else {// 直接取期初
			qry += "\n    select a.custid,' ' as notedate,cast(' ' as nvarchar2(20)) as noteno,cast('期初' as nvarchar2(100)) as remark,cast('' as nvarchar2(100)) as remark0,'**' as notetype,0 as xsamt,0 as xscurr,0 as xtamt,0 as xtcurr,0 as fycurr,0 as curr1,0 as curr2,sum(a.curr) as curr3 ";
			qry += "\n    from incomebal a ";
			qry += "\n    left outer join customer b on a.custid=b.custid ";
			qry += "\n    where a.daystr='" + mindate0 + "' ";
			qry += "\n    and b.accid=" + Accid + " ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (!Func.isNull(Areaname))
				qry += " and b.areaname like '" + Areaname + "%'";
			if (!Func.isNull(Custidlist))
				qry += "  and " + Func.getCondition("a.custid", Custidlist);
			if (!Func.isNull(Houseidlist))
				qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
			qry += "\n    group by a.custid";

			qry += "\n    union all";
			qry += "\n    select a.custid,' ' as notedate,cast(' ' as nvarchar2(20)) as noteno,cast('期初' as nvarchar2(100)) as remark,cast('' as nvarchar2(100)) as remark0,'**' as notetype,0 as xsamt,0 as xscurr,0 as xtamt,0 as xtcurr,0 as fycurr,0 as curr1,0 as curr2,0 as curr3 ";
			qry += "\n    from customer a ";
			qry += "\n    where a.accid=" + Accid + " ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (!Func.isNull(Areaname))
				qry += " and a.areaname like '" + Areaname + "%'";

			if (!Func.isNull(Custidlist))
				qry += "  and " + Func.getCondition("a.custid", Custidlist);
			qry += "\n    and not exists (select 1 from incomebal b where a.custid=b.custid and  b.daystr='" + mindate0 + "'";
			if (!Func.isNull(Houseidlist))
				qry += "  and " + Func.getCondition("b.houseid", Houseidlist);
			qry += "\n     )";
		}

		qry += "\n    union all  ";
		// --期初应收
		qry += "\n    select a.custid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,cast('初始' as nvarchar2(100)),a.remark as remark0,'QS' as notetype,0 as xsamt,0 as xscurr,0 as xtamt,0 as xtcurr,0 as fycurr,a.curr,0,0 ";
		qry += "\n    from firstincomecurr a  ";
		qry += "\n    left outer join customer b on a.custid=b.custid ";
		qry += "\n    where a.statetag=1 ";// and
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		qry += "\n    and a.accid=" + Accid + " ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    union all ";
		// --本期销售应收
		// qry += "\n select a.custid,to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),a.noteno,cast('批发' as nvarchar2(100)),a.remark as
		// remark0,'XS' as notetype,sum(b.amount) as xsamt,sum(b.curr) as
		// xscurr,0 as xtamt,0 as xtcurr,sum(b.curr),0,0 ";
		qry += "\n    select a.custid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,cast('批发' as nvarchar2(100)),a.remark as remark0,'XS' as notetype,a.totalamt as xsamt,a.totalcurr-a.totalcost as xscurr,0 as xtamt,0 as xtcurr,a.totalcost as fycurr,a.totalcurr as curr1,0,0 ";
		qry += "\n    from wareouth a  ";
		// qry += "\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno
		// ";
		qry += "\n    left outer join customer c on a.custid=c.custid ";
		qry += "\n    where a.statetag=1";// and
		qry += "\n    and a.ntid=1 ";// and to_char(a.notedate,'yyyy-mm-dd')
		qry += "\n    and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}

		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and c.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		// qry += "\n group by a.custid,a.notedate,a.noteno,a.remark";
		qry += "\n    union all ";
		// --本期销售应退
		// qry += "\n select a.custid,to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),a.noteno,cast('批发退货' as nvarchar2(100)),a.remark as
		// remark0,'XT' as notetype,0 as xsamt,0 as xscurr,sum(b.amount) as
		// xtamt,sum(b.curr) as xtcurr,-sum(b.curr),0,0 ";
		qry += "\n    select a.custid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,cast('批发退货' as nvarchar2(100)),a.remark as remark0,'XT' as notetype,0 as xsamt,0 as xscurr,a.totalamt as xtamt,a.totalcurr+a.totalcost as xtcurr,a.totalcost as fycurr,-a.totalcurr as curr1,0,0 ";
		qry += "\n    from wareouth a  ";
		// qry += "\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno
		// ";
		qry += "\n    left outer join customer c on a.custid=c.custid ";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.accid=" + Accid;
		qry += "      and a.ntid=2 ";// and to_char(a.notedate,'yyyy-mm-dd')
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and c.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		// qry += "\n group by a.custid,a.notedate,a.noteno,a.remark ";
		qry += "\n    union all ";
		// --销售费用
		qry += "\n    select a.custid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,c.cgname,a.remark as remark0,'SF' as notetype,0 as xsamt,0 as xscurr,0 as xtamt,0 as xtcurr,a.curr as fycurr,a.curr ,0,0 ";
		qry += "\n    from incomecost a  ";
		qry += "\n    left outer join customer b on a.custid=b.custid ";
		qry += "\n    left outer join chargescode c on a.cgid=c.cgid ";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    union all ";
		// --销售折让
		qry += "\n    select a.custid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,cast('折让' as nvarchar2(100)),a.remark as remark0,'SZ' as notetype,0 as xsamt,0 as xscurr,0 as xtamt,0 as xtcurr,0 as fycurr,-a.curr,0,0 ";
		qry += "\n    from incomecurr a  ";
		qry += "\n    left outer join customer b on a.custid=b.custid ";
		// -- left outer join payway c on a.payid=c.payid
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.payid=0 and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    union all ";
		// --销售已收
		qry += "\n    select a.custid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,c.payname,a.remark as remark0,'SK' as notetype,0 as xsamt,0 as xscurr,0 as xtamt,0 as xtcurr,0 as fycurr,0,a.curr,0 ";
		qry += "\n    from incomecurr a  ";
		qry += "\n    left outer join customer b on a.custid=b.custid ";
		qry += "\n    left outer join payway c on a.payid=c.payid ";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.payid>0 and a.accid=" + Accid;
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Areaname))
			qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n     ); ";
		qry += "\n    delete from v_custcheck_data a where epid=" + Userid + " and  not exists (";
		qry += "\n    select distinct custid from v_custcheck_data b where (xsamt<>0 or xscurr<>0 or xtamt<>0 or xtcurr<>0 or curr1<>0 or curr2<>0 or curr3<>0 or fycurr<>0 )";
		qry += "\n    and b.epid=" + Userid + " and a.custid=b.custid);";
		// --计算余额
		qry += "\n    declare cursor cur_v_custcheck_data is select custid,id,curr1,curr2,curr3 from v_custcheck_data where epid=" + Userid + " order by custid,notedate,id;  ";
		qry += "\n    begin ";
		qry += "\n     open cur_v_custcheck_data; ";
		qry += "\n     fetch cur_v_custcheck_data  into v_custid,v_id,v_curr1,v_curr2,v_curr3;   ";
		qry += "\n     while cur_v_custcheck_data%found loop  ";
		qry += "\n        v_custid1:=v_custid; ";
		qry += "\n        v_balcurr:=0; v_bj:=0; ";
		qry += "\n        while cur_v_custcheck_data%found and v_custid1=v_custid  loop  ";
		qry += "\n           if v_bj=0 then ";
		qry += "\n              v_balcurr:=v_curr3; ";
		qry += "\n              v_bj:=1; ";
		qry += "\n           else ";
		qry += "\n              v_balcurr:=v_balcurr+v_curr1-v_curr2; ";
		qry += "\n              update v_custcheck_data set curr3=v_balcurr where id=v_id; ";
		qry += "\n           end if; ";

		qry += "\n           fetch cur_v_custcheck_data  into v_custid,v_id,v_curr1,v_curr2,v_curr3;   ";
		qry += "\n        end loop;  ";
		qry += "\n     end loop;  ";

		qry += "\n     close cur_v_custcheck_data; ";
		qry += "\n   end;";

		qry += "\n  commit;";

		// 欠款客户数
		qry += "\n  select count(*) into v_qkcustnum from (";
		qry += "      select a.custid,sum((case a.notetype when '**' then curr3 else 0 end)+a.curr1-a.curr2) as curr3";
		qry += "      FROM v_custcheck_data a where a.epid=" + Userid;
		qry += "      group by a.custid";
		qry += "  ) where curr3>0; ";

		qry += "\n    select v_custnum,v_qkcustnum,nvl(sum(a.xsamt),0),nvl(sum(a.xscurr),0),nvl(sum(a.xtamt),0),nvl(sum(a.xtcurr),0),nvl(sum(a.curr1),0),nvl(sum(a.curr2),0) ";
		// qry += "\n ,nvl(sum(case when notetype='FY' then a.curr1 else 0
		// end),0)"; //费用
		qry += "\n    ,nvl(sum(fycurr),0)"; // 费用
		qry += "\n    ,nvl(sum(case notetype when 'SZ' then a.curr1 else 0 end),0)";// 折让
		qry += "\n    ,nvl(sum(case notetype when '**' then a.curr3 else 0 end),0)"; // 期初
		qry += "\n    ,nvl(sum( (case notetype when '**' then a.curr3 else 0 end)+a.curr1-a.curr2),0)"; // 期末
		qry += "\n     into :totalcustnum,:totalqkcustnum,:xsamt,:xscurr,:xtamt,:xtcurr,:totalcurr1,:totalcurr2,:fycurr,:zkcurr,:curr0,:curr3 from v_custcheck_data a where a.epid=" + Userid + "; ";
		qry += "\n end;";
		// System.out.println("aaa:"+qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalcustnum", new ProdParam(Types.DOUBLE));
		param.put("totalqkcustnum", new ProdParam(Types.DOUBLE));
		param.put("xsamt", new ProdParam(Types.DOUBLE));
		param.put("xscurr", new ProdParam(Types.DOUBLE));
		param.put("xtamt", new ProdParam(Types.DOUBLE));
		param.put("xtcurr", new ProdParam(Types.DOUBLE));
		param.put("totalcurr1", new ProdParam(Types.DOUBLE));
		param.put("totalcurr2", new ProdParam(Types.DOUBLE));

		param.put("fycurr", new ProdParam(Types.DOUBLE));
		param.put("zkcurr", new ProdParam(Types.DOUBLE));
		param.put("curr0", new ProdParam(Types.DOUBLE));
		param.put("curr3", new ProdParam(Types.DOUBLE));

		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		Double curr1 = Double.parseDouble(param.get("totalcurr1").getParamvalue().toString());
		Double curr2 = Double.parseDouble(param.get("totalcurr2").getParamvalue().toString());
		Double hkrate = (double) 0;
		if (curr1 > 0)
			hkrate = Func.getRound(curr2 / curr1, 2);
		errmess = "\"msg\":\"操作成功！\",\"XSAMT\":\"" + param.get("xsamt").getParamvalue().toString() + "\""//
				+ ",\"XSCURR\":\"" + Func.FormatNumber(Double.parseDouble(param.get("xscurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"XTAMT\":\"" + param.get("xtamt").getParamvalue().toString() + "\""//
				+ ",\"XTCURR\":\"" + Func.FormatNumber(Double.parseDouble(param.get("xtcurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"FYCURR\":\"" + Func.FormatNumber(Double.parseDouble(param.get("fycurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"ZKCURR\":\"" + Func.FormatNumber(Double.parseDouble(param.get("zkcurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"CURR0\":\"" + Func.FormatNumber(Double.parseDouble(param.get("curr0").getParamvalue().toString()), 2) + "\""//
				+ ",\"CURR1\":\"" + Func.FormatNumber(curr1, 2) + "\""//
				+ ",\"CURR2\":\"" + Func.FormatNumber(curr2, 2) + "\""//
				+ ",\"CURR3\":\"" + Func.FormatNumber(Double.parseDouble(param.get("curr3").getParamvalue().toString()), 2) + "\""//
				+ ",\"CUSTNUM\":\"" + param.get("totalcustnum").getParamvalue().toString() + "\""//
				+ ",\"QKCUSTNUM\":\"" + param.get("totalqkcustnum").getParamvalue().toString() + "\""//
				+ ",\"HKRATE\":\"" + hkrate + "\""//
				+ ",\"WARNING\":\"" + warning + "\""//
				+ ",\"MINDATE\":\"" + Mindate + "\""//
				+ ",\"MAXDATE\":\"" + Maxdate + "\""//
		;

		return 1;
	}

	//
	public Table GetTable(QueryParam qp, int qkid) {
		String qry = " select a.custid,b.custname,b.areaname,b.mobile,sum(a.xsamt) as xsamt,sum(a.xscurr) as xscurr,sum(a.xtamt) as xtamt,sum(a.xtcurr) as xtcurr";
		qry += "\n ,sum(a.fycurr) as fycurr,sum(case a.notetype when 'SZ' then a.curr1 else 0 end) as zkcurr";
		qry += "\n ,sum(case a.notetype when '**' then curr3 else 0 end) as curr0";
		qry += "\n ,sum(a.curr1) as curr1,sum(a.curr2) as curr2,sum((case a.notetype when '**' then curr3 else 0 end)+a.curr1-a.curr2) as curr3";
		qry += "\n ,min(a.id) as keyid";
		qry += "\n FROM v_custcheck_data a left outer join customer b on a.custid=b.custid where a.epid=" + Userid;
		qry += "\n group by a.custid,b.custname,b.areaname,b.mobile";
		// qkid:0=有欠款 1=有余额 2=所有
		if (qkid == 0)
			qry += "\n having sum((case a.notetype when '**' then curr3 else 0 end)+a.curr1-a.curr2)>0";
		else if (qkid == 1)
			qry += "\n having sum((case a.notetype when '**' then curr3 else 0 end)+a.curr1-a.curr2)<0";

		qp.setQueryString(qry);
		String sumstr = "nvl(sum(xsamt),0) as xsamt,nvl(sum(xscurr),0) as xscurr,nvl(sum(xtamt),0) as xtamt,nvl(sum(xtcurr),0) as xtcurr,nvl(sum(fycurr),0) as fycurr"//
				+ " ,nvl(sum(zkcurr),0) as zkcurr,nvl(sum(curr0),0) as curr0,nvl(sum(curr1),0) as curr1,nvl(sum(curr2),0) as curr2,nvl(sum(curr3),0) as curr3";
		qp.setSumString(sumstr);
		return DbHelperSQL.GetTable(qp);
	}

	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		// 取余额
		strSql.append("select curr3 from ");
		strSql.append(" (select curr3 from  v_custcheck_data a ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		strSql.append("  order by notedate desc,id desc ) where rownum=1");
		Table tb = DbHelperSQL.Query(strSql.toString()).getTable(1);
		Float curr3 = (float) 0;
		if (tb.getRowCount() > 0)
			curr3 = Float.parseFloat(tb.getRow(0).get("CURR3").toString());
		strSql.setLength(0);
		strSql.append("select " + fieldlist);
		strSql.append(" FROM v_custcheck_data a ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setTotalString("\"curr3\":\"" + curr3 + "\"");
		qp.setSumString(
				" nvl(sum(curr1),0) as curr1,nvl(sum(curr2),0) as curr2,nvl(sum(XSAMT),0) as xsamt,nvl(sum(XSCURR),0) as xscurr,nvl(sum(XTAMT),0) as xtamt,nvl(sum(XTCURR),0) as xtcurr,NVL(SUM(FYCURR),0) AS FyCURR");
		return DbHelperSQL.GetTable(qp);
	}

}
