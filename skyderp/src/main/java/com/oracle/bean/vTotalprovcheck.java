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
public class vTotalprovcheck {
	private Long Accid = (long) 0;
	private Long Userid = (long) 0;

	private Long Houseid = (long) 0;
	private Long Provid = (long) 0;

	private String Maxdate;
	private String Mindate;

	private String Houseidlist = "";
	private String Providlist = "";
	private String errmess = "";

	private String Nowdate = "";
	private String Accbegindate = "";
	private Integer Qxbj = 0;
	private String Vipno = "";
	private String Guestname = "";

	private Integer Maxday = 0;
	private String Calcdate = "";
	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setCalcdate(String calcdate) {
		this.Calcdate = calcdate;
	}

	public void setVipno(String vipno) {
		this.Vipno = vipno;
	}

	public void setGuestname(String guestname) {
		this.Guestname = guestname;
	}

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
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
		qry += "\n    delete from v_paybal_data where epid=" + Userid + ";";
		qry += "\n    insert into v_paybal_data (id,epid,provid,houseid,curr) ";
		qry += "\n    select v_paybal_data_id.nextval," + Userid + ",provid,houseid,curr from ( ";

		qry += "\n    select provid, houseid,nvl(sum(curr),0) as curr from ( ";
		// 期初余额
		qry += "\n    select a.provid,a.houseid,sum(a.curr) as curr ";
		qry += "\n    from paybal a ";
		qry += "\n    left outer join provide b on a.provid=b.provid ";
		qry += "\n    where a.daystr='" + mindate0 + "' ";
		qry += "\n    and b.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		// if (!Func.isNull(Areaname))
		// qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by a.provid,a.houseid";

		qry += "\n    union all  ";
		// --初始应收
		qry += "\n    select a.provid,a.houseid,sum(a.curr) as curr ";
		qry += "\n    from firstpaycurr a  ";
		qry += "\n    left outer join provide b on a.provid=b.provid ";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// if (!Func.isNull(Areaname))
		// qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by a.provid,a.houseid";
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    union all ";
		// --本期采购应付
		qry += "\n    select a.provid,a.houseid,sum(a.totalcurr) as curr ";
		qry += "\n    from wareinh a  ";
		// qry += "\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno
		// ";
		qry += "\n    left outer join provide c on a.provid=c.provid ";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.ntid=0 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// if (!Func.isNull(Areaname))
		// qry += " and c.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    group by a.provid,a.houseid";
		qry += "\n    union all ";
		// --本期采购应退
		qry += "\n    select a.provid,a.houseid,-sum(a.totalcurr) as curr ";
		qry += "\n    from wareinh a  ";
		// qry += "\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno
		// ";
		qry += "\n    left outer join provide c on a.provid=c.provid ";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.accid=" + Accid;
		qry += "      and a.ntid=1 ";// and to_char(a.notedate,'yyyy-mm-dd')
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// if (!Func.isNull(Areaname))
		// qry += " and c.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    group by a.provid,a.houseid";
		qry += "\n    union all ";
		// --销售费用
		qry += "\n    select a.provid,a.houseid,sum(a.curr) as curr ";
		qry += "\n    from paycost a  ";
		qry += "\n    left outer join provide b on a.provid=b.provid ";
		// qry += "\n left outer join chargescode c on a.cgid=c.cgid ";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// if (!Func.isNull(Areaname))
		// qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by a.provid,a.houseid";
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    union all ";
		// --销售折让
		qry += "\n    select a.provid,a.houseid,-sum(a.curr) as curr ";
		qry += "\n    from paycurr a  ";
		qry += "\n    left outer join provide b on a.provid=b.provid ";
		// -- left outer join payway c on a.payid=c.payid
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.payid=0 and a.accid=" + Accid + " ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// if (!Func.isNull(Areaname))
		// qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by a.provid,a.houseid";
		/*
		 * if (qxpublic.Substring(1 - 1, 1) == "1") //1 启用权限控制 { qry +=
		 * "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="
		 * + userid + " ) "; }
		 */
		qry += "\n    union all ";
		// --销售已收
		qry += "\n    select a.provid,a.houseid,-sum(a.curr) as curr";
		qry += "\n    from paycurr a  ";
		qry += "\n    left outer join provide b on a.provid=b.provid ";
		qry += "\n    left outer join payway c on a.payid=c.payid ";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.payid>0 and a.accid=" + Accid;
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		// if (!Func.isNull(Areaname))
		// qry += " and b.areaname like '" + Areaname + "%'";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by a.provid,a.houseid";
		qry += "\n    ) group by provid,houseid";
		qry += "\n    ); ";
		qry += "\n end;";
		// System.out.println("汇总期初:"+qry);
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "汇总期初失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	public int doTotal(int dateid) {
		// dateid:0=本日，1=昨日，2=本周，3=本月 ,4=指定日期
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

		int bj = 0;
		if (mindate0.compareTo(Calcdate) > 0) {

			doTotalqc(Func.DateToStr(Func.getNextDay(Calcdate)), Func.DateToStr(Func.getPrevDay(Mindate)));
			mindate0 = Calcdate;
			bj = 1;
		}

		if (Func.isNull(Houseidlist) && Houseid > 0)
			Houseidlist = Houseid.toString();

		if (Func.isNull(Providlist) && Provid > 0)
			Providlist = Provid.toString();

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
		qry += "\n    v_provid number; ";
		qry += "\n    v_provid1 number; ";
		qry += "\n    v_bj  number(1); ";
		qry += "\n    v_provnum  number(10); "; // 总客户数
		qry += "\n    v_qkprovnum  number(10); "; // 总客户数
		// v_provid,v_id,v_curr1,v_curr2,v_curr3
		qry += "\n begin ";
		// qry+=" insert into v_wlcheck_temp
		// (notedate,remark,noteno,curr0,curr1,curr2) ";
		qry += "\n    select count(*) into v_provnum from provide where accid=" + Accid + " and statetag=1;";
		qry += "\n    delete from v_provcheck_data where epid=" + Userid + "; ";
		qry += "\n    insert into v_provcheck_data (id,epid,provid,notedate,noteno,notetype,remark,remark0,cgamt,cgcurr,ctamt,ctcurr,fycurr,curr1,curr2,curr3) ";
		qry += "\n    select v_provcheck_data_id.nextval," + Userid
				+ ",provid, notedate,noteno,notetype,remark,remark0,nvl(cgamt,0),nvl(cgcurr,0),nvl(ctamt,0),nvl(ctcurr,0),nvl(fycurr,0),nvl(curr1,0),nvl(curr2,0),nvl(curr3,0) from ( ";
		if (bj == 1) { // 要计算期初

			qry += "\n    select a.provid,' ' as notedate,cast(' ' as nvarchar2(20)) as noteno,cast('期初' as nvarchar2(100)) as remark,cast('' as nvarchar2(100)) as remark0,'**' as notetype,0 as cgamt,0 as cgcurr,0 as ctamt,0 as ctcurr,0 as fycurr,0 as curr1,0 as curr2,sum(a.curr) as curr3 ";
			qry += "\n    from v_paybal_data a ";
			qry += "\n    left outer join provide b on a.provid=b.provid ";
			qry += "\n    where a.epid='" + Userid + "' ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
			}
			if (!Func.isNull(Providlist))
				qry += "  and " + Func.getCondition("a.provid", Providlist);
			if (!Func.isNull(Houseidlist))
				qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
			qry += "\n    group by a.provid";

			qry += "\n   union all";
			qry += "\n   select a.provid,' ' as notedate,cast(' ' as nvarchar2(20)) as noteno,cast('期初' as nvarchar2(100)) as remark,cast('' as nvarchar2(100)) as remark,'**' as notetype,0 as cgamt,0 as cgcurr,0 as ctamt,0 as ctcurr,0 as fycurr,0 as curr1,0 as curr2,0 as curr3 ";
			qry += "\n   from provide a ";
			qry += "\n   where a.accid=" + Accid + " ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
			}

			if (!Func.isNull(Providlist))
				qry += " and " + Func.getCondition("a.provid", Providlist);
			qry += "\n   and not exists (select 1 from v_paybal_data b where a.provid=b.provid and b.epid=" + Userid;
			if (!Func.isNull(Houseidlist))
				qry += " and " + Func.getCondition("b.houseid", Houseidlist);
			qry += "\n )";
		} else {
			qry += "\n    select a.provid,' ' as notedate,cast(' ' as nvarchar2(20)) as noteno,cast('期初' as nvarchar2(100)) as remark,cast('' as nvarchar2(100)) as remark0,'**' as notetype,0 as cgamt,0 as cgcurr,0 as ctamt,0 as ctcurr,0 as fycurr,0 as curr1,0 as curr2,sum(a.curr) as curr3 ";
			qry += "\n    from paybal a ";
			qry += "\n    left outer join provide b on a.provid=b.provid ";
			qry += "\n    where a.daystr='" + mindate0 + "' ";
			qry += "\n    and b.accid=" + Accid + " ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
			}
			if (!Func.isNull(Providlist))
				qry += "  and " + Func.getCondition("a.provid", Providlist);
			if (!Func.isNull(Houseidlist))
				qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
			qry += "\n    group by a.provid";

			qry += "\n    union all";

			qry += "\n    select a.provid,' ' as notedate,cast(' ' as nvarchar2(20)) as noteno,cast('期初' as nvarchar2(100)) as remark,cast('' as nvarchar2(100)) as remark,'**' as notetype,0 as cgamt,0 as cgcurr,0 as ctamt,0 as ctcurr,0 as fycurr,0 as curr1,0 as curr2,0 as curr3 ";
			qry += "\n    from provide a ";
			qry += "\n    where a.accid=" + Accid + " ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
			}
			if (!Func.isNull(Providlist))
				qry += "  and " + Func.getCondition("a.provid", Providlist);
			qry += "\n    and not exists (select 1 from paybal b where a.provid=b.provid and  b.daystr='" + mindate0 + "'";

			if (!Func.isNull(Houseidlist))
				qry += "  and " + Func.getCondition("b.houseid", Houseidlist);

			qry += "\n     )";
		}
		qry += "\n    union all  ";
		// --期初应收
		qry += "\n    select a.provid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,cast('初始' as nvarchar2(100)),a.remark,'QF' as notetype,0 as cgamt,0 as cgcurr,0 as ctamt,0 as ctcurr,0 as fycurr,a.curr,0,0 ";
		qry += "\n    from firstpaycurr a  ";
		qry += "\n    left outer join provide b on a.provid=b.provid ";
		qry += "\n    where a.statetag=1 ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);

		qry += "\n    and b.accid=" + Accid + " ";
		qry += "\n    union all ";
		// --本期采购应收
		// qry += "\n select a.provid,to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),a.noteno,cast('采购' as nvarchar2(100)),a.remark,'CG' as
		// notetype,sum(b.amount) as cgamt,sum(b.curr) as cgcurr,0 as ctamt,0 as
		// ctcurr,sum(b.curr),0,0 ";
		qry += "\n    select a.provid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,cast('采购' as nvarchar2(100)),a.remark,'CG' as notetype,a.totalamt as cgamt,a.totalcurr-a.totalcost as cgcurr,0 as ctamt,0 as ctcurr,a.totalcost as fycurr,a.totalcurr as curr1,0,0 ";
		qry += "\n    from wareinh a  ";
		// qry += "\n join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    left outer join provide c on a.provid=c.provid ";
		qry += "\n    where a.statetag=1 ";// and
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		qry += "\n    and a.ntid=0 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "    and c.accid=" + Accid + " ";
		// qry += "\n group by a.provid,a.notedate,a.noteno,a.remark";
		qry += "\n    union all ";
		// --本期采购应退
		// qry += "\n select a.provid,to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),a.noteno,cast('退货' as nvarchar2(100)),a.remark,'CT' as
		// notetype,0 as cgamt,0 as cgcurr,sum(b.amount) as ctamt,sum(b.curr) as
		// ctcurr,-sum(b.curr),0,0 ";
		qry += "\n    select a.provid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,cast('退货' as nvarchar2(100)),a.remark,'CT' as notetype,0 as cgamt,0 as cgcurr,a.totalamt as ctamt,a.totalcurr-a.totalcost as ctcurr,a.totalcost as fycurr,-a.totalcurr as curr1,0,0 ";
		qry += "\n    from wareinh a  ";
		// qry += "\n join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    left outer join provide c on a.provid=c.provid ";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.ntid=1 ";// and to_char(a.notedate,'yyyy-mm-dd')
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    and c.accid=" + Accid;
		// qry += "\n group by a.provid,a.notedate,a.noteno ,a.remark";
		qry += "\n    union all ";
		// --采购费用
		qry += "\n    select a.provid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,c.cgname,a.remark,'FF' as notetype,0 as cgamt,0 as cgcurr,0 as ctamt,0 as ctcurr,a.curr as fycurr,a.curr,0,0 ";
		qry += "\n    from paycost a  ";
		qry += "\n    left outer join provide b on a.provid=b.provid ";
		qry += "\n    left outer join chargescode c on a.cgid=c.cgid ";
		qry += "\n    where a.statetag=1 ";// and
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    and b.accid=" + Accid + " ";
		qry += "\n    union all ";
		// --采购折让
		qry += "\n    select a.provid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,cast('折让' as nvarchar2(100)),a.remark,'FZ' as notetype,0 as cgamt,0 as cgcurr,0 as ctamt,0 as ctcurr,0 as fycurr,-a.curr,0,0 ";
		qry += "\n    from paycurr a  ";
		qry += "\n    left outer join provide b on a.provid=b.provid ";
		// -- left outer join payway c on a.payid=c.payid
		qry += "\n    where a.statetag=1 ";// and
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);

		qry += "\n    and a.payid=0 and b.accid=" + Accid + " ";
		qry += "\n    union all ";
		// --采购已收
		qry += "\n    select a.provid,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),a.noteno,c.payname,a.remark,'FK' as notetype,0 as cgamt,0 as cgcurr,0 as ctamt,0 as ctcurr,0 as fycurr,0,a.curr,0 ";
		qry += "\n    from paycurr a  ";
		qry += "\n    left outer join provide b on a.provid=b.provid ";
		qry += "\n    left outer join payway c on a.payid=c.payid ";
		qry += "\n    where a.statetag=1 ";// and
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
		}
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Providlist))
			qry += "  and " + Func.getCondition("a.provid", Providlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    and a.payid>0 and b.accid=" + Accid + " ); ";

		qry += "\n    delete from v_provcheck_data a where epid=" + Userid + " and  not exists (";
		qry += "\n    select distinct provid from v_provcheck_data b where (cgamt<>0 or cgcurr<>0 or ctamt<>0 or curr1<>0 or curr2<>0 or curr3<>0 or ctcurr<>0 or fycurr<>0 )";
		qry += "\n    and b.epid=" + Userid + " and a.provid=b.provid);";
		// --计算余额
		qry += "\n    declare cursor cur_v_provcheck_data is select provid,id,curr1,curr2,curr3 from v_provcheck_data where epid=" + Userid + " order by provid,notedate,id;  ";

		qry += "\n    begin ";
		qry += "\n     open cur_v_provcheck_data; ";
		qry += "\n     fetch cur_v_provcheck_data  into v_provid,v_id,v_curr1,v_curr2,v_curr3;   ";
		qry += "\n     while cur_v_provcheck_data%found loop  ";
		qry += "\n        v_provid1:=v_provid; ";
		qry += "\n        v_balcurr:=0; v_bj:=0; ";
		qry += "\n        while cur_v_provcheck_data%found and v_provid1=v_provid  loop  ";
		qry += "\n           if v_bj=0 then ";
		qry += "\n              v_balcurr:=v_curr3; ";
		qry += "\n              v_bj:=1; ";
		qry += "\n           else ";
		qry += "\n              v_balcurr:=v_balcurr+v_curr1-v_curr2; ";
		qry += "\n              update v_provcheck_data set curr3=v_balcurr where id=v_id; ";
		qry += "\n           end if; ";

		qry += "\n           fetch cur_v_provcheck_data  into v_provid,v_id,v_curr1,v_curr2,v_curr3;   ";
		qry += "\n        end loop;  ";
		qry += "\n     end loop;  ";

		qry += "\n     close cur_v_provcheck_data; ";
		qry += "\n   end;";

		qry += "\n  commit;";
		// xsid:0=有欠款 1=无欠款 2=所有
		// if (qkid == 0) { qry += " delete from v_provcheck_data where currqm=0
		// and epid=" + userid + "; "; }
		// else
		// if (qkid == 1) { qry += " delete from v_provcheck_data where
		// currqm<>0 and epid=" + userid + "; "; }
		// 欠款客户数
		qry += "\n  select count(*) into v_qkprovnum from (";
		qry += "      select a.provid,sum((case a.notetype when '**' then curr3 else 0 end)+a.curr1-a.curr2) as curr3";
		qry += "      FROM v_provcheck_data a where a.epid=" + Userid;
		qry += "      group by a.provid";
		qry += "  ) where curr3>0; ";

		qry += "\n    select v_provnum,v_qkprovnum,nvl(sum(a.cgamt),0),nvl(sum(a.cgcurr),0),nvl(sum(a.ctamt),0),nvl(sum(a.ctcurr),0)";
		qry += "\n    ,nvl(sum(a.curr1),0),nvl(sum(a.curr2),0) "; // :v_totalcurr1,:v_totalcurr2
		// qry += "\n ,nvl(sum(case when notetype='FY' then a.curr1 else 0
		// end),0)"; // v_fycurr
		qry += "\n    ,nvl(sum(a.fycurr),0)"; // v_fycurr
		qry += "\n   ,nvl(sum(case notetype when 'FZ' then a.curr1 else 0 end),0)"; // v_zkcurr
		qry += "\n    ,nvl(sum(case notetype when '**' then a.curr3 else 0 end),0)"; // v_curr0
		qry += "\n    ,nvl(sum( (case notetype when '**' then a.curr3 else 0 end)+a.curr1-a.curr2),0)"; // v_curr3
		qry += "\n     into :totalprovnum,:totalqkprovnum,:cgamt,:cgcurr,:ctamt,:ctcurr,:totalcurr1,:totalcurr2,:fycurr,:zkcurr,:curr0,:curr3 ";
		qry += "\n     from v_provcheck_data a where a.epid=" + Userid + "; ";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalprovnum", new ProdParam(Types.FLOAT));
		param.put("totalqkprovnum", new ProdParam(Types.FLOAT));
		param.put("cgamt", new ProdParam(Types.FLOAT));
		param.put("cgcurr", new ProdParam(Types.FLOAT));
		param.put("ctamt", new ProdParam(Types.FLOAT));
		param.put("ctcurr", new ProdParam(Types.FLOAT));
		param.put("totalcurr1", new ProdParam(Types.FLOAT));
		param.put("totalcurr2", new ProdParam(Types.FLOAT));

		param.put("fycurr", new ProdParam(Types.FLOAT));
		param.put("zkcurr", new ProdParam(Types.FLOAT));
		param.put("curr0", new ProdParam(Types.FLOAT));
		param.put("curr3", new ProdParam(Types.FLOAT));

		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		Float curr1 = Float.parseFloat(param.get("totalcurr1").getParamvalue().toString());
		Float curr2 = Float.parseFloat(param.get("totalcurr2").getParamvalue().toString());
		Float hkrate = (float) 0;
		if (curr1 > 0)
			hkrate = Func.getRound(curr2 / curr1, 2);
		errmess = "\"msg\":\"操作成功！\",\"CGAMT\":\"" + param.get("cgamt").getParamvalue().toString() + "\""//
				+ ",\"CGCURR\":\"" + Func.FormatNumber(Float.parseFloat(param.get("cgcurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"CTAMT\":\"" + param.get("ctamt").getParamvalue().toString() + "\""//
				+ ",\"CTCURR\":\"" + Func.FormatNumber(Float.parseFloat(param.get("ctcurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"FYCURR\":\"" + param.get("fycurr").getParamvalue().toString() + "\""//
				+ ",\"ZKCURR\":\"" + Func.FormatNumber(Float.parseFloat(param.get("zkcurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"CURR0\":\"" + Func.FormatNumber(Float.parseFloat(param.get("curr0").getParamvalue().toString()), 2) + "\""//
				+ ",\"CURR1\":\"" + Func.FormatNumber(curr1, 2) + "\""//
				+ ",\"CURR2\":\"" + Func.FormatNumber(curr2, 2) + "\""//
				+ ",\"CURR3\":\"" + Func.FormatNumber(Float.parseFloat(param.get("curr3").getParamvalue().toString()), 2) + "\""//
				+ ",\"PROVNUM\":\"" + param.get("totalprovnum").getParamvalue().toString() + "\""//
				+ ",\"QKPROVNUM\":\"" + param.get("totalqkprovnum").getParamvalue().toString() + "\""//
				+ ",\"HKRATE\":\"" + hkrate + "\""//
				+ ",\"WARNING\":\"" + warning + "\""//
				+ ",\"MINDATE\":\"" + Mindate + "\""//
				+ ",\"MAXDATE\":\"" + Maxdate + "\""//
		;

		return 1;
	}

	//
	public Table GetTable(QueryParam qp, int qkid) {
		String qry = " select a.provid,b.provname,sum(a.cgamt) as cgamt,sum(a.cgcurr) as cgcurr,sum(a.ctamt) as ctamt,sum(a.ctcurr) as ctcurr";
		qry += "\n ,sum(a.fycurr) as fycurr,sum(case a.notetype when 'FZ' then a.curr1 else 0 end) as zkcurr";
		qry += "\n ,sum(case a.notetype when '**' then curr3 else 0 end) as curr0";
		qry += "\n ,sum(a.curr1) as curr1,sum(a.curr2) as curr2,sum((case a.notetype when '**' then curr3 else 0 end)+a.curr1-a.curr2) as curr3";
		qry += "\n ,min(a.id) as keyid";
		qry += "\n FROM v_provcheck_data a left outer join provide b on a.provid=b.provid where a.epid=" + Userid;

		qry += "\n group by a.provid,b.provname";
		// qkid:0=有欠款 1=有余额 2=所有
		if (qkid == 0)
			qry += "\n having sum((case a.notetype when '**' then curr3 else 0 end)+a.curr1-a.curr2)>0";
		else if (qkid == 1)
			qry += "\n having sum((case a.notetype when '**' then curr3 else 0 end)+a.curr1-a.curr2)<0";

		qp.setQueryString(qry);
		String sumstr = "nvl(sum(cgamt),0) as cgamt,nvl(sum(cgcurr),0) as cgcurr,nvl(sum(ctamt),0) as ctamt,nvl(sum(ctcurr),0) as ctcurr,nvl(sum(fycurr),0) as fycurr"//
				+ " ,nvl(sum(zkcurr),0) as zkcurr,nvl(sum(curr0),0) as curr0,nvl(sum(curr1),0) as curr1,nvl(sum(curr2),0) as curr2,nvl(sum(curr3),0) as curr3";

		qp.setSumString(sumstr);

		return DbHelperSQL.GetTable(qp);
	}

	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		// 取余额
		strSql.append("select curr3 from ");
		strSql.append(" (select curr3 from  v_provcheck_data a ");
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
		strSql.append(" FROM v_provcheck_data a ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setTotalString("\"curr3\":\"" + curr3 + "\"");
		qp.setSumString(
				" NVL(SUM(CURR1),0) AS CURR1,NVL(SUM(CURR2),0) AS CURR2,NVL(SUM(CGAMT),0) AS CGAMT,NVL(SUM(CGCURR),0) AS CGCURR,NVL(SUM(CTAMT),0) AS CTAMT,NVL(SUM(CTCURR),0) AS CTCURR,NVL(SUM(FYCURR),0) AS FyCURR");
		return DbHelperSQL.GetTable(qp);
	}

}
