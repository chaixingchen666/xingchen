/**
 * 
 */
package com.oracle.bean;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;

/**
 * @author Administrator
 *
 */
public class vTotaljyhztj {
	private Long Accid;
	private Long Houseid;
	private String Nowdatetime;
	private String Accdate;
	private String Mindate;
	private String Maxdate;
	private String errmess;

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setHouseid(Long houseid) {
		Houseid = houseid;
	}

	public void setMindate(String mindate) {
		Mindate = mindate;
	}

	public void setMaxdate(String maxdate) {
		Maxdate = maxdate;
	}

	public void setAccdate(String accdate) {
		Accdate = accdate;
	}

	public void setNowdatetime(String nowdatetime) {
		Nowdatetime = nowdatetime;
	}

	public String getErrmess() {
		return errmess;
	}

	public int doTotal(int housecostbj) {
		System.out.println("doTotaljyhztj  1");
		if (Mindate.compareTo(Accdate) < 0)
			Mindate = Accdate;

		// DateTime nowday = DateTime.Parse(Mindate);
		String Mindate0 = Func.DateToStr(Func.getPrevDay(Mindate)); // nowday.AddDays(-1).ToString("yyyy-MM-dd");
		String Mindate1 = Func.DateToStr(Func.getNextDay(Mindate));// nowday.AddDays(1).ToString("yyyy-MM-dd");

		// nowday = DateTime.Parse(Maxdate);
		// string Maxdate0 = nowday.AddDays(-1).ToString("yyyy-MM-dd");
		// string Maxdate1 = nowday.AddDays(1).ToString("yyyy-MM-dd");

		String Maxdate0 = Func.DateToStr(Func.getPrevDay(Maxdate)); // nowday.AddDays(-1).ToString("yyyy-MM-dd");
		String Maxdate1 = Func.DateToStr(Func.getNextDay(Maxdate));// nowday.AddDays(1).ToString("yyyy-MM-dd");

		String qry = "declare ";
		qry += "\n    v_zskcurr  number;  ";
		qry += "\n    v_zfkcurr  number;   ";
		qry += "\n    v_yskcurr  number; ";
		qry += "\n    v_yfkcurr  number; ";
		// qry += "\n v_Accid number; ";
		qry += "\n    v_zrkamt number; ";
		qry += "\n    v_zxsamt number; ";
		qry += "\n    v_zykamt number; ";
		qry += "\n    v_zkcamt number; ";
		qry += "\n    v_zztamt number; ";
		qry += "\n    v_retailcurr number; ";
		qry += "\n    v_fixedcurr number; ";
		qry += "\n    v_ztretailcurr number; ";
		qry += "\n    v_ztfixedcurr number; ";
		qry += "\n    v_zfycurr number;  ";// --累计经营费用
		qry += "\n    v_brkamt number;   ";// --本期采购数量
		qry += "\n    v_brkcurr number;  ";// --本期采购金额
		qry += "\n    v_brtamt number;   ";// --本期采购退货数量
		qry += "\n    v_brtcurr number;  ";// --本期采购退货金额
		qry += "\n    v_bxsamt number;   ";// --本期销售数量
		qry += "\n    v_bxscurr number;  ";// --本期销售金额
		qry += "\n    v_bxtamt number;   ";// --本期销售退货数量
		qry += "\n    v_bxtcurr number;  ";// --本期销售退货金额
		qry += "\n    v_bdramt number;   ";// --本期调入数量
		qry += "\n    v_bdcamt number;   ";// --本期调出数量
		qry += "\n    v_bykamt number;   ";// --本期盘亏数量
		qry += "\n    v_byyamt number;   ";// --本期盘盈数量

		qry += "\n    v_bskzrcurr number;  ";// --本期收款折让(销售)
		qry += "\n    v_bfkzrcurr number;  ";// --本期付款折让(采购)
		qry += "\n    v_brknum number;   ";// --本期采购笔数
		qry += "\n    v_brtnum number;   ";// --本期采购退货笔数
		qry += "\n    v_bxsnum number;   ";// --本期销售笔数
		qry += "\n    v_bxtnum number;   ";// --本期销售退货笔数

		qry += "\n    v_bysfycurr number;  ";// --本期应收费用
		qry += "\n    v_byffycurr number;  ";// --本期应付费用
		qry += "\n    v_bjyfycurr number;  ";// --本期经营费用

		qry += "\n    v_kqccurr number;   ";// --储值卡期初
		qry += "\n    v_kzjcurr number;  ";// --储值卡增加
		qry += "\n    v_kjscurr number;  ";// --储值卡减少
		qry += "\n    v_kqmcurr number;  ";// --储值卡期末
		qry += "\n    v_retcs varchar2(1000);  ";// --储值卡期末

		qry += "\n begin  ";

		// --本期采购入库数量，金额
		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(cgnum),0) into v_brkamt, v_brkcurr,v_brknum from ( ";
		qry += "\n      select sum(b.amount ) as amount ,sum(b.curr) as curr,count(distinct a.Accid||'^'||a.noteno) as cgnum ";
		qry += "\n      from wareinh a  ";
		qry += "\n      join wareinm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 and a.ntid=0  ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n      union all ";
		qry += "\n      select sum(b.amount) as amount ,sum(b.curr) as curr,count(distinct a.Accid||'^'||a.noteno) as cgnum  from firsthouseh a  ";
		qry += "\n      join firsthousem b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1  ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n    );  ";

		// --本期采购退库数量，金额
		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(ctnum),0) into v_brtamt, v_brtcurr,v_brtnum from ( ";
		qry += "\n      select sum(b.amount ) as amount ,sum(b.curr) as curr ,count(distinct a.Accid||'^'||a.noteno) as ctnum";
		qry += "\n      from wareinh a  ";
		qry += "\n      join wareinm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 and a.ntid=1  ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		qry += "\n    );  ";

		// --本期销售出库数量，金额
		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(xsnum),0) into v_bxsamt, v_bxscurr,v_bxsnum from ( ";
		qry += "\n      select sum(b.amount ) as amount  ,sum(b.curr) as curr,count(distinct a.Accid||'^'||a.noteno) as xsnum";
		qry += "\n      from wareouth a ";
		qry += "\n      join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 and (a.ntid=0 or a.ntid=1)";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		// 商场销售
		qry += "\n      union all";
		qry += "\n      select sum(b.amount ) as amount,sum(b.curr) as curr,count(distinct a.Accid||'^'||a.noteno) as xsnum";
		qry += "\n      from shopsaleh a ";
		qry += "\n      join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";


		// 店铺优惠销售
		qry += "\n      union all";
		qry += "\n      select 0 as amount  ,-a.freecurr as curr,0 as xsnum";
		qry += "\n      from wareouth a ";
//		qry += "\n      join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + " and a.ntid=0 and a.statetag=1";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		// 商场优惠销售
		qry += "\n      union all";
		qry += "\n      select 0 as amount,-a.freecurr as curr,0 as xsnum";
		qry += "\n      from shopsaleh a ";
//		qry += "\n      join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + "  and a.statetag=1 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";

		qry += "\n    ); ";
		// --本期销售退库数量，金额
		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(xtnum),0) into v_bxtamt, v_bxtcurr,v_bxtnum from ( ";
		qry += "\n      select sum(b.amount ) as amount  ,sum(b.curr) as curr,count(distinct a.Accid||'^'||a.noteno) as xtnum";
		qry += "\n      from wareouth a ";
		qry += "\n      join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 and a.ntid=2";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		qry += "\n    ); ";
		// --本期调入数量
		qry += "\n    select nvl(sum(amount),0) into v_bdramt from (  ";
		qry += "\n      select sum(b.amount ) as amount  ";
		qry += "\n      from allotinh a  ";
		qry += "\n      join allotinm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1  ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		qry += "\n    );  ";
		// --本期调出数量
		qry += "\n    select nvl(sum(amount),0) into v_bdcamt from (  ";
		qry += "\n      select sum(b.amount ) as amount  ";
		qry += "\n      from allotouth a  ";
		qry += "\n      join allotoutm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1  ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		qry += "\n    );  ";

		// qry += "\n v_bykamt number; ";// --本期盘亏数量
		// qry += "\n v_byyamt number; ";// --本期盘盈数量

		// --本期盘亏数量
		qry += "\n    select nvl(sum(amount),0) into v_bykamt from (  ";
		qry += "\n      select -sum(b.amount ) as amount  ";
		qry += "\n      from warecheckh a  ";
		qry += "\n      join warecheckm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 and b.amount<0 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		qry += "\n    );  ";

		// --本期盘盈数量
		qry += "\n    select nvl(sum(amount),0) into v_byyamt from (  ";
		qry += "\n      select sum(b.amount ) as amount  ";
		qry += "\n      from warecheckh a  ";
		qry += "\n      join warecheckm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 and b.amount>0 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		qry += "\n    );  ";

		// --本期收款折让
		qry += "\n    select nvl(sum(curr),0) into v_bskzrcurr from incomecurr a  ";
		qry += "\n    where a.Accid=" + Accid + " and a.statetag=1 and a.payid=0 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd'); ";
		// --本期付款折让
		qry += "\n    select nvl(sum(curr),0) into v_bfkzrcurr from paycurr a  ";
		qry += "\n    where a.Accid=" + Accid + " and a.statetag=1 and a.payid=0 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd'); ";

		// --本期应收费用
		qry += "\n    select nvl(sum(curr),0) into v_bysfycurr from incomecost a  ";
		qry += "\n    where a.Accid=" + Accid + " and a.statetag=1 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd'); ";
		// --本期应付费用
		qry += "\n    select nvl(sum(curr),0) into v_byffycurr from paycost a  ";
		qry += "\n    where Accid=" + Accid + " and statetag=1 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd'); ";
		qry += "\n      v_bjyfycurr:=0;"; // 未用
		// v_kqccurr number; --储值卡期初
		// v_kzjcurr number; --储值卡增加
		// v_kjscurr number; --储值卡减少
		// v_kqmcurr number; --储值卡期末
		// --储值卡期初
		qry += "\n    select nvl(sum(case a.fs when 0 then a.curr else -a.curr end),0) into v_kqccurr  ";
		qry += "\n    from guestcurr a join guestvip b on a.guestid=b.guestid ";
		qry += "\n    where b.Accid=" + Accid + "  ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n    and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd')  ";
		qry += "\n    and a.notedate<to_date('" + Mindate + "','yyyy-mm-dd') ; ";

		// --储值卡增加
		qry += "\n    select nvl(sum(a.curr),0) into v_kzjcurr  ";
		qry += "\n    from guestcurr a join guestvip b on a.guestid=b.guestid ";
		qry += "\n    where b.Accid=" + Accid + " and a.fs=0 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n    and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd'); ";
		// --储值卡减少
		qry += "\n    select nvl(sum(a.curr),0) into v_kjscurr  ";
		qry += "\n    from guestcurr a join guestvip b on a.guestid=b.guestid ";
		qry += "\n    where b.Accid=" + Accid + " and a.fs=1 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n    and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd'); ";

		qry += "\n    v_kqmcurr:=v_kqccurr+v_kzjcurr-v_kjscurr; ";

		// --累计店铺经营费用
		qry += "\n    select nvl(sum(case a.fs when 0 then -a.curr else a.curr end),0) into v_zfycurr from housecost a where a.Accid=" + Accid + " and a.statetag=1";
		// if (Houseid > 0) qry += "\n and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd'); ";

		// --累计收款
		qry += "\n    select nvl(sum(curr),0) into v_zskcurr from ( ";
		qry += "\n      select sum(curr) as curr from incomecurr a where payid>0 and statetag=1 and Accid=" + Accid;
		// if (Houseid > 0) qry += "\n and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n      union all   ";
		// --店铺零售收款
		qry += "\n      select sum(checkcurr) as curr from wareouth a where ntid=0 and statetag=1 and Accid=" + Accid;
		// if (Houseid > 0) qry += "\n and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd')  ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n    ); ";

		// --累计应收款
		qry += "\n    select nvl(sum(curr),0) into v_yskcurr from ( ";
		// --上日余额
		qry += "\n    select curr from incomebal a where a.daystr='" + Maxdate0 + "' ";
		qry += "\n    and exists (select 1 from customer b where a.custid=b.custid and b.Accid=" + Accid + " ) ";
		qry += "\n    union all ";
		// --本日初始应收款 ";
		qry += "\n    select sum(a.curr) from firstincomecurr a ";
		qry += "\n    where a.Accid=" + Accid + "  ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		// --本日应收货款
		qry += "\n    union all ";
		qry += "\n    select sum(case a.ntid when 1 then b.curr else -b.curr end) from wareouth a   ";
		qry += "\n    left outer join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n    where a.Accid=" + Accid + " and a.ntid>0 ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		// --应收费用
		qry += "\n    union all ";
		qry += "\n    select sum(a.curr) from incomecost a  ";
		qry += "\n    where a.Accid=" + Accid + "  ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		// --已收及折扣
		qry += "\n    union all ";
		qry += "\n    select -sum(a.curr) from incomecurr a  ";
		qry += "\n    where a.Accid=" + Accid + "   ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		qry += "\n    ) ; ";

		// --累计付款
		qry += "\n    select nvl(sum(curr),0) into v_zfkcurr from paycurr a where payid>0 and Accid=" + Accid + " and statetag=1 ";
		qry += "\n      and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd') ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ;";

		// --应付款余额
		qry += "\n    select nvl(sum(curr),0) into v_yfkcurr from ( ";
		// --上日余额
		qry += "\n    select curr from paybal a where a.daystr='" + Maxdate0 + "' ";
		qry += "\n    and exists (select 1 from provide b where a.provid=b.provid and b.Accid=" + Accid + " ) ";

		qry += "\n    union all ";
		// --本日初始应付款
		qry += "\n    select sum(a.curr) from firstpaycurr a ";
		qry += "\n    where a.Accid=" + Accid + "  ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		// --本日应付货款
		qry += "\n    union all ";
		qry += "\n    select sum(case a.ntid when 0 then b.curr else -b.curr end) from wareinh a   ";
		qry += "\n    left outer join wareinm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n    where a.Accid=" + Accid + "  ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		// --应付费用
		qry += "\n    union all ";
		qry += "\n    select sum(a.curr) from paycost a  ";
		qry += "\n    where a.Accid=" + Accid + " ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		// --已付及折扣
		qry += "\n    union all ";
		qry += "\n    select -sum(a.curr) from paycurr a  ";
		qry += "\n    where a.Accid=" + Accid + "   ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		qry += "\n    ) ; ";

		// --累计进货
		qry += "\n    select nvl(sum(amount),0) into v_zrkamt from ( ";
		qry += "\n      select sum(case a.ntid when 0 then b.amount else -b.amount end) as amount  ";
		qry += "\n      from wareinh a ";
		qry += "\n      join wareinm b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 ";
		qry += "\n      and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd') ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      union all ";
		qry += "\n      select sum(b.amount) as amount from firsthouseh a ";
		qry += "\n      join firsthousem b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd') ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n    ); ";

		// --累计销货
		qry += "\n    select nvl(sum(amount),0) into v_zxsamt from ( ";
		qry += "\n      select sum(case a.ntid when 2 then -b.amount else b.amount end) as amount  ";
		qry += "\n      from wareouth a ";
		qry += "\n      join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd') ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		// 商场销售
		qry += "\n   union all";
		qry += "\n      select sum(b.amount) as amount  ";
		qry += "\n      from shopsaleh a ";
		qry += "\n      join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd') ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";

		qry += "\n    ); ";

		// --累计盈亏
		qry += "\n    select nvl(sum(amount),0) into v_zykamt from ( ";
		qry += "\n      select sum(b.amount) as amount  ";
		qry += "\n      from warecheckh a ";
		qry += "\n      join warecheckm b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 and b.amount<>0 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n      and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd') ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n    ); ";

		// --存货零售额,成本，数量
		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0),nvl(round(sum(fixedcurr),0),0) into v_zkcamt,v_retailcurr,v_fixedcurr from ( ";
		// --上日结存
		qry += "\n    select a.amount,a.amount*b.retailsale as curr,a.amount*c.costprice as fixedcurr  ";
		qry += "\n    from waresum a  ";
		qry += "\n    join warecode b on a.wareid=b.wareid ";
		qry += "\n    left outer join warecost c on a.wareid=c.wareid and c.daystr='" + Maxdate0 + "' ";
		if (housecostbj == 1)
			qry += "  and a.Houseid=c.Houseid ";
		else
			qry += "  and c.Houseid=0";
		qry += "\n    where a.daystr='" + Maxdate0 + "' and b.Accid=" + Accid + " ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n    union all ";
		// --本日初始入库
		qry += "\n    select sum(b.amount) as amount,sum(b.amount*c.retailsale) as curr ,sum(b.amount*d.costprice) as fixedcurr  ";
		qry += "\n    from firsthouseh a  ";
		qry += "\n    left outer join firsthousem b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n    join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr='" + Maxdate + "' ";
		if (housecostbj == 1)
			qry += "  and a.Houseid=d.Houseid ";
		else
			qry += "  and d.Houseid=0";
		qry += "\n    where a.Accid=" + Accid + " and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		// --本日采购入库
		qry += "\n    union all ";
		qry += "\n    select sum(case a.ntid when 0 then b.amount else -b.amount end) as amount ";
		qry += "\n    ,sum(case a.ntid when 0 then b.amount else -b.amount end*c.retailsale) as curr  ";
		qry += "\n    ,sum(case a.ntid when 0 then b.curr else -b.curr end) as fixedcurr  ";
		qry += "\n    from wareinh a   ";
		qry += "\n    left outer join wareinm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n    join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr='" + Maxdate + "' ";
		if (housecostbj == 1)
			qry += "  and a.Houseid=d.Houseid ";
		else
			qry += "  and d.Houseid=0";
		qry += "\n    where a.Accid=" + Accid + " and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n    union all ";
		// --调入库
		qry += "\n    select sum(b.amount) as amount,sum(b.amount*c.retailsale) as curr ,sum(b.amount*d.costprice) as fixedcurr  ";
		qry += "\n    from allotinh a  ";
		qry += "\n    left outer join allotinm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n    join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr='" + Maxdate + "' ";
		if (housecostbj == 1)
			qry += "  and a.Houseid=d.Houseid ";
		else
			qry += "  and d.Houseid=0";
		qry += "\n    where a.Accid=" + Accid + " and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;

		// --本日销售出库
		qry += "\n    union all ";
		qry += "\n    select -sum(case a.ntid when 2 then -b.amount else b.amount end) as amount ";
		qry += "\n    ,-sum(case a.ntid when 2 then -b.amount else b.amount end*c.retailsale) as curr  ";
		qry += "\n    ,-sum(case a.ntid when 2 then -b.amount else b.amount end*d.costprice) as fixedcurr  ";
		qry += "\n    from wareouth a  ";
		qry += "\n    left outer join wareoutm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n    join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr='" + Maxdate + "' ";
		if (housecostbj == 1)
			qry += "  and a.Houseid=d.Houseid ";
		else
			qry += "  and d.Houseid=0";
		qry += "\n    where a.Accid=" + Accid + "  ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;

		// --本日零售优惠
//		qry += "\n    union all ";
//		qry += "\n    select 0 as amount ";
//		qry += "\n    ,-freecurr as curr  ";
//		qry += "\n    ,0 as fixedcurr  ";
//		qry += "\n    from wareouth a  ";
//
//		qry += "\n    where a.Accid=" + Accid + " and a.ntid=0  ";
//		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
//		if (Houseid > 0)
//			qry += "\n     and a.Houseid=" + Houseid;

		// --本日商场销售出库
		qry += "\n    union all ";
		qry += "\n    select -sum( b.amount ) as amount ";
		qry += "\n    ,-sum(b.amount*c.retailsale) as curr  ";
		qry += "\n    ,-sum(b.amount*d.costprice) as fixedcurr  ";
		qry += "\n    from shopsaleh a  ";
		qry += "\n    left outer join shopsalem b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n    join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr='" + Maxdate + "' ";
		if (housecostbj == 1)
			qry += "  and a.Houseid=d.Houseid ";
		else
			qry += "  and d.Houseid=0";
		qry += "\n    where a.Accid=" + Accid + "  ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;

		// --本日商场销售优惠
//		qry += "\n    union all ";
//		qry += "\n    select 0 as amount ";
//		qry += "\n    ,-freecurr as curr  ";
//		qry += "\n    ,0 as fixedcurr  ";
//		qry += "\n    from shopsaleh a  ";
//		qry += "\n    where a.Accid=" + Accid + "  ";
//		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
//		if (Houseid > 0)
//			qry += "\n     and a.Houseid=" + Houseid;

		qry += "\n    union all ";
		// --调出库
		qry += "\n    select -sum(b.amount) as amount,-sum(b.amount*c.retailsale) as curr ,-sum(b.amount*d.costprice) as fixedcurr  ";
		qry += "\n    from allotouth a  ";
		qry += "\n    left outer join allotoutm b on a.Accid=b.Accid and a.noteno=b.noteno  ";
		qry += "\n    join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr='" + Maxdate + "' ";
		if (housecostbj == 1)
			qry += "  and a.Houseid=d.Houseid ";
		else
			qry += "  and d.Houseid=0";
		qry += "\n    where a.Accid=" + Accid + " and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n    union all ";
		// --本日盘点
		qry += "\n    select sum(b.amount) as amount,sum(b.amount*c.retailsale) as curr,sum(b.amount*d.costprice) as fixedcurr   ";
		qry += "\n    from warecheckh a  ";
		qry += "\n    left outer join warecheckm b on a.noteno=b.noteno  and a.Accid=b.Accid ";
		qry += "\n    join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr='" + Maxdate + "' ";
		if (housecostbj == 1)
			qry += "  and a.Houseid=d.Houseid ";
		else
			qry += "  and d.Houseid=0";
		qry += "\n    where a.Accid=" + Accid + " and b.amount<>0 ";
		qry += "\n    and a.statetag=1 and a.notedate>=to_date('" + Maxdate + "','yyyy-mm-dd') and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd') ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;

		qry += "\n    ) ; ";
		// --在途商品
		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0),nvl(round(sum(fixedcurr),0),0) into v_zztamt,v_ztretailcurr,v_ztfixedcurr from ( ";
		qry += "\n      select sum(b.amount) as amount,sum(b.amount*c.retailsale) as curr,sum(b.amount*d.costprice) as fixedcurr  ";
		qry += "\n      from allotouth a ";
		qry += "\n      join allotoutm b on a.Accid=b.Accid and a.noteno=b.noteno ";
		qry += "\n      join warecode c on b.wareid=c.wareid ";
		qry += "\n      left outer join warecost d on b.wareid=d.wareid and d.daystr='" + Maxdate + "'  ";
		if (housecostbj == 1)
			qry += "  and a.Houseid=d.Houseid ";
		else
			qry += "  and d.Houseid=0";

		qry += "\n      where a.Accid=" + Accid + " and a.statetag=1 ";
		qry += "\n      and a.notedate>=to_date('" + Accdate + "','yyyy-mm-dd') ";
		qry += "\n      and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";

		if (Houseid > 0)
			qry += "\n     and a.toHouseid=" + Houseid;
		qry += "\n      and (a.noteno1='' or a.noteno1 is null)";

		qry += "\n      ); ";

		// to_char(a.notedate,'yyyy-mm-dd')
		// qry += "\n v_bskzrcurr number; ";// --本期收款折让
		// qry += "\n v_bfkzrcurr number; ";// --本期付款折让
		// qry += "\n v_brknum number; ";// --本期采购笔数
		// qry += "\n v_brtnum number; ";// --本期采购退货笔数
		// qry += "\n v_bxsnum number; ";// --本期销售笔数
		// qry += "\n v_bxtnum number; ";// --本期销售退货笔数

		// qry += "\n select '1','\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into :v_retbj,:v_notenolist from wareinh where id=v_id; ";

		qry += "\n  select '\"ZSKCURR\":'||v_zskcurr||',\"YSKCURR\":'||v_yskcurr||',\"ZFKCURR\":'||v_zfkcurr ";
		qry += "\n  ||',\"YFKCURR\":'||v_yfkcurr";
		qry += "\n  ||',\"ZSYCURR\":'||(v_zskcurr+v_yskcurr-v_zfkcurr-v_yfkcurr-v_zfycurr) ";
		qry += "\n  ||',\"ZRKAMT\":'||v_zrkamt||',\"ZXSAMT\":'||v_zxsamt||',\"ZYKAMT\":'||v_zykamt||',\"ZKCAMT\":'||v_zkcamt";
		qry += "\n  ||',\"ZZTAMT\":'||v_zztamt||',\"ZYEAMT\":'||(v_zkcamt+v_zztamt)||',\"RETAILCURR\":'||(v_retailcurr+v_ztretailcurr)||',\"FIXEDCURR\":'||(v_fixedcurr+v_ztfixedcurr)";

		qry += "\n  ||',\"ZFYCURR\":'||v_zfycurr||',\"BRKAMT\":'||v_brkamt||',\"BRKCURR\":'||v_brkcurr||',\"BRTAMT\":'||v_brtamt||',\"BRTCURR\":'||v_brtcurr";

		qry += "\n  ||',\"BXSAMT\":'||v_bxsamt||',\"BXSCURR\":'||v_bxscurr||',\"BXTAMT\":'||v_bxtamt||',\"BXTCURR\":'||v_bxtcurr||',\"BDRAMT\":'||v_bdramt";

		qry += "\n  ||',\"BDCAMT\":'||v_bdcamt||',\"BYYAMT\":'||v_byyamt||',\"BYKAMT\":'||v_bykamt||',\"KQCCURR\":'||v_kqccurr";

		qry += "\n  ||',\"KZJCURR\":'||v_kzjcurr||',\"KJSCURR\":'||v_kjscurr||',\"KQMCURR\":'||v_kqmcurr||',\"BJYFYCURR\":'||v_bjyfycurr";

		qry += "\n  ||',\"BYFFYCURR\":'||v_byffycurr||',\"BYSFYCURR\":'||v_bysfycurr||',\"BSKZRCURR\":'||v_bskzrcurr||',\"BFKZRCURR\":'||v_bfkzrcurr";

		qry += "\n  ||',\"BRKNUM\":'||v_brknum||',\"BRTNUM\":'||v_brtnum||',\"BXSNUM\":'||v_bxsnum||',\"BXTNUM\":'||v_bxtnum";

		qry += " \n  into :retcs from dual;";
		// qry += "\n select v_zskcurr,v_yskcurr,v_zfkcurr,v_yfkcurr";
		// qry += "\n ,v_zskcurr+v_yskcurr-v_zfkcurr-v_yfkcurr-v_zfycurr";
		// qry += "\n ,v_zrkamt,v_zxsamt,v_zykamt,v_zkcamt,v_zztamt,v_zkcamt+v_zztamt , v_retailcurr+v_ztretailcurr,v_fixedcurr+v_ztfixedcurr ";
		// qry += "\n ,v_zfycurr,v_brkamt,v_brkcurr,v_brtamt,v_brtcurr";
		// qry += "\n ,v_bxsamt,v_bxscurr,v_bxtamt,v_bxtcurr,v_bdramt,v_bdcamt,v_byyamt,v_bykamt,v_kqccurr";

		// qry += "\n ,v_kzjcurr,v_kjscurr,v_kqmcurr,v_bjyfycurr,v_byffycurr,v_bysfycurr,v_bskzrcurr,v_bfkzrcurr";
		// qry += "\n ,v_brknum,v_brtnum,v_bxsnum,v_bxtnum";
		// qry += "\n into :zskcurr,:yskcurr,:zfkcurr,:yfkcurr,:zsycurr,:zrkamt,:zxsamt,:zykamt,:zkcamt,:zztamt,:zyeamt , :retailcurr,:fixedcurr ";// 1-13
		// qry += "\n ,:zfycurr,:brkamt,:brkcurr,:brtamt,:brtcurr";
		// qry += "\n ,:bxsamt,:bxscurr,:bxtamt,:bxtcurr,:bdramt,:bdcamt,:byyamt,:bykamt";// 14-26
		// qry += "\n ,:kqccurr,:kzjcurr,:kjscurr,:kqmcurr,:bjyfycurr,:byffycurr,:bysfycurr,:bskzrcurr,:bfkzrcurr";// 27-35
		// qry += "\n ,:brknum,:brtnum,:bxsnum,:bxtnum";// 36-39

		// qry += "\n from dual; ";
		qry += "\n end; ";

		//System.out.println("doTotaljyhztj 2:" + qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));// 1

		//System.out.println("doTotaljyhztj 2.1");
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		//System.out.println("doTotaljyhztj 2.2。4");
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
//		System.out.println("doTotaljyhztj  2.3。4");

		String responsestr = "\"msg\":\"操作成功！\",\"SERVERDATETIME\":\"" + Nowdatetime + "\"," + param.get("retcs").getParamvalue().toString();
		// + ",\"ZSKCURR\":\"" + param.get("zskcurr").getParamvalue().toString() + "\"" //
		// + ",\"YSKCURR\":\"" + param.get("yskcurr").getParamvalue().toString() + "\"" //
		// + ",\"ZFKCURR\":\"" + param.get("zfkcurr").getParamvalue().toString() + "\"" //
		// + ",\"YFKCURR\":\"" + param.get("yfkcurr").getParamvalue().toString() + "\"" //
		// + ",\"ZSYCURR\":\"" + param.get("zsycurr").getParamvalue().toString() + "\""
		//
		// + ",\"ZRKAMT\":\"" + param.get("zrkamt").getParamvalue().toString() + "\"" //
		// + ",\"ZXSAMT\":\"" + param.get("zxsamt").getParamvalue().toString() + "\""//
		// + ",\"ZYKAMT\":\"" + param.get("zykamt").getParamvalue().toString() + "\"" //
		// + ",\"ZKCAMT\":\"" + param.get("zkcamt").getParamvalue().toString() + "\"" //
		// + ",\"ZZTAMT\":\"" + param.get("zztamt").getParamvalue().toString() + "\"" //
		// + ",\"ZYEAMT\":\"" + param.get("zyeamt").getParamvalue().toString() + "\""//
		// + ",\"RETAILCURR\":\"" + param.get("retailcurr").getParamvalue().toString() + "\"" //
		// + ",\"FIXEDCURR\":\"" + param.get("fixedcurr").getParamvalue().toString() + "\""
		//
		// + ",\"ZFYCURR\":\"" + param.get("zfycurr").getParamvalue().toString() + "\"" //
		// + ",\"BRKAMT\":\"" + param.get("brkamt").getParamvalue().toString() + "\"" //
		// + ",\"BRKCURR\":\"" + param.get("brkcurr").getParamvalue().toString() + "\"" //
		// + ",\"BRTAMT\":\"" + param.get("brtamt").getParamvalue().toString() + "\"" //
		// + ",\"BRTCURR\":\"" + param.get("brtcurr").getParamvalue().toString() + "\"" //
		// + ",\"BXSAMT\":\"" + param.get("bxsamt").getParamvalue().toString() + "\""//
		// + ",\"BXSCURR\":\"" + param.get("bxscurr").getParamvalue().toString() + "\"" //
		// + ",\"BXTAMT\":\"" + param.get("bxtamt").getParamvalue().toString() + "\"" //
		// + ",\"BXTCURR\":\"" + param.get("bxtcurr").getParamvalue().toString() + "\"" //
		// + ",\"BDRAMT\":\"" + param.get("bdramt").getParamvalue().toString() + "\""//
		// + ",\"BDCAMT\":\"" + param.get("bdcamt").getParamvalue().toString() + "\"" //
		// + ",\"BYYAMT\":\"" + param.get("byyamt").getParamvalue().toString() + "\""//
		// + ",\"BYKAMT\":\"" + param.get("bykamt").getParamvalue().toString() + "\"" //
		// + ",\"KQCCURR\":\"" + param.get("kqccurr").getParamvalue().toString() + "\"" //
		// + ",\"KZJCURR\":\"" + param.get("kzjcurr").getParamvalue().toString() + "\"" //
		// + ",\"KJSCURR\":\"" + param.get("kjscurr").getParamvalue().toString() + "\"" //
		// + ",\"KQMCURR\":\"" + param.get("kqmcurr").getParamvalue().toString() + "\""
		//
		// + ",\"BJYFYCURR\":\"" + param.get("bjyfycurr").getParamvalue().toString() + "\""//
		// + ",\"BYFFYCURR\":\"" + param.get("byffycurr").getParamvalue().toString() + "\"" //
		// + ",\"BYSFYCURR\":\"" + param.get("bysfycurr").getParamvalue().toString() + "\"" //
		// + ",\"BSKZRCURR\":\"" + param.get("bskzrcurr").getParamvalue().toString() + "\""//
		// + ",\"BFKZRCURR\":\"" + param.get("bfkzrcurr").getParamvalue().toString() + "\""
		// // qry += "\n ,:brknum,:brtnum,:bxsnum,:bxtnum";
		// + ",\"BRKNUM\":\"" + param.get("brknum").getParamvalue().toString() + "\"" //
		// + ",\"BRTNUM\":\"" + param.get("brtnum").getParamvalue().toString() + "\"" //
		// + ",\"BXSNUM\":\"" + param.get("bxsnum").getParamvalue().toString() + "\"" //
		// + ",\"BXTNUM\":\"" + param.get("bxtnum").getParamvalue().toString() + "\"" ;
//		System.out.println("doTotaljyhztj  3");

		// 结算方式
		Table tb = new Table();

		qry = " select payid,payno,payname,sum(curr0) as curr0,sum(curr1) as curr1,sum(curr0-curr1) as curr2 from (";
		// --销售收款
		qry += "\n  select  a.payid,b.payno,b.payname,sum(a.curr) as curr0 ,0.00 as curr1";
		qry += "\n  from incomecurr a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  where a.Accid=" + Accid + " and a.statetag=1 and a.payid>0 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n  and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n  and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n  group by a.payid,b.payno,b.payname ";
		// --零售收款
		qry += "\n  union all ";
		qry += "\n  select  a.payid,b.payno,b.payname,sum(a.curr) as curr0 ,0.00 as curr1 ";
		qry += "\n  from waresalepay a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  join wareouth c on a.Accid=c.Accid and a.noteno=c.noteno ";
		qry += "\n  where c.Accid=" + Accid + " and c.statetag=1 and c.ntid=0  ";  //and b.payno<>'V'
		if (Houseid > 0)
			qry += "\n     and c.Houseid=" + Houseid;
		qry += "\n  and c.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n  and c.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n  group by a.payid,b.payno,b.payname ";
		// 找零
		qry += "\n  union all ";
		qry += "\n  select  b.payid,b.payno,b.payname,-sum(a.changecurr) as curr0 ,0.00 as curr1 ";
		qry += "\n  from wareouth a ,payway b";
		// qry += "\n left outer join payway b on a.payid=b.payid ";
		// qry += "\n join wareouth c on a.Accid=c.Accid and a.noteno=c.noteno ";
		qry += "\n  where a.Accid=" + Accid + " and a.statetag=1 and a.ntid=0 and a.changecurr>0 and b.Accid=" + Accid + " and b.payno='0'";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n  and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n  and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n  group by b.payid,b.payno,b.payname ";

		// --店铺收款
		qry += "\n  union all ";
		qry += "\n  select  a.payid,b.payno,b.payname,sum(a.curr) as curr0 ,0.00 as curr1 ";
		qry += "\n  from shopsalepay a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  join shopsaleh c on a.Accid=c.Accid and a.noteno=c.noteno ";
		qry += "\n  where c.Accid=" + Accid + " and c.statetag=1";//  and b.payno<>'V' ";
		if (Houseid > 0)  qry += "\n and c.Houseid=" + Houseid;
//			qry += "\n  and exists ( select 1 from shopsalem d where c.Accid=d.Accid and c.noteno=d.noteno and d.Houseid=" + Houseid + ")";
		qry += "\n  and c.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n  and c.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n  group by a.payid,b.payno,b.payname ";
		// 店铺找零
		qry += "\n  union all ";
		qry += "\n  select  b.payid,b.payno,b.payname,-sum(a.changecurr) as curr0 ,0.00 as curr1 ";
		qry += "\n  from shopsaleh a ,payway b";
		qry += "\n  where a.Accid=" + Accid + " and a.statetag=1 and a.changecurr>0 and b.Accid=" + Accid + " and b.payno='0'";
		if (Houseid > 0)  qry += "\n and a.Houseid=" + Houseid;
//			qry += "\n  and exists ( select 1 from shopsalem d where a.Accid=d.Accid and a.noteno=d.noteno and d.Houseid=" + Houseid + ")";

		qry += "\n  and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n  and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n  group by b.payid,b.payno,b.payname ";

		// --储值收款
		qry += "\n  union all ";
		qry += "\n  select  a.payid,b.payno,b.payname,sum(a.curr) as curr0 ,0.00 as curr1 ";
		qry += "\n  from guestcurr a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  join guestvip c on a.guestid=c.guestid ";
		qry += "\n  where c.Accid=" + Accid + "  and a.fs=0 and a.payid>0 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n  and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n  and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n  group by a.payid,b.payno,b.payname ";

		// --店铺费用收支
		qry += "\n  union all ";
		qry += "\n  select  a.payid,b.payno,b.payname,sum(case a.fs when 0 then a.curr else 0 end) as curr0 ,sum(case a.fs when 1 then a.curr else 0 end) as curr1 ";
		qry += "\n  from housecost a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  where a.Accid=" + Accid + " and a.statetag=1 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n  and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n  and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n  group by a.payid,b.payno,b.payname ";

		// --采购付款
		qry += "\n  union all ";
		qry += "\n  select  a.payid,b.payno,b.payname,0.00 as curr0 ,sum(a.curr) as curr1 ";
		qry += "\n  from paycurr a ";
		qry += "\n  left outer join payway b on a.payid=b.payid ";
		qry += "\n  where a.Accid=" + Accid + " and a.statetag=1 and a.payid>0 ";
		if (Houseid > 0)
			qry += "\n     and a.Houseid=" + Houseid;
		qry += "\n  and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd')  ";
		qry += "\n  and a.notedate<to_date('" + Maxdate1 + "','yyyy-mm-dd')  ";
		qry += "\n  group by a.payid,b.payno,b.payname ) ";
		qry += "\n  group by payid,payno,payname ";
		qry += "\n  order by payno ";
		// WriteDebugTXT("totaljyhztj2", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		// string retcs;
		String fh = "";
		int count = tb.getRowCount();

		responsestr += ",\"PAYCURRLIST\":[";

		for (int i = 0; i < count; i++) {
			responsestr += fh + "{\"PAYID\":\"" + tb.getRow(i).get("PAYID").toString() + "\"," //
					+ "\"PAYNAME\":\"" + tb.getRow(i).get("PAYNAME").toString() + "\"," //
					+ "\"PAYCURR0\":\"" + tb.getRow(i).get("CURR0").toString() + "\","//
					+ "\"PAYCURR1\":\"" + tb.getRow(i).get("CURR1").toString() + "\","//
					+ "\"PAYCURR2\":\"" + tb.getRow(i).get("CURR2").toString() + "\"}";
			fh = ",";
		}
		responsestr += "]";
		errmess = responsestr;
		return 1;
	}
}
