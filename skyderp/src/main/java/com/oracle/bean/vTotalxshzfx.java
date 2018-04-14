/**
 * 
 */
package com.oracle.bean;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.comdot.data.Table;
import com.common.tool.Func;

import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

/**
 * @author Administrator 销售趋势分析
 *
 */
public class vTotalxshzfx {
	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd HH:mm:ss,------------必传
	private Long Accid = (long) 0;

	private String Notedate = "";
	private Long Userid = (long) 0;
	private Long Wareid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;
	private Long Dptid = (long) -1;
	private Long Areaid = (long) -1;
	private Long Brandid = (long) -1;
	private Long Saleid = (long) -1;
	private Long Typeid = (long) -1;
	private Long Provid = (long) -1;
	private Float Discount = (float) -1;
	private String Seasonname = "";
	private String Locale = "";
	private String Warename = "";
	private String Areaname = "";
	private String Wareno = "";
	private String Prodyear = "";
	private Long Houseid = (long) 0;
	private Long Custid = (long) 0;
	private Integer Dateid = 0;// dateid:0=本日，1=昨日，2=本周，3=本月 ,4=指定日期
	private Integer Xsid = 2;// 销售方式 xsid:0=零售，1=批发，2=所有  
	private String Xslist = "111";  //1=零售，2=批发，3=客户销售

	private Integer Ntid = 2;// hzfs:0=品牌， 1=类型， 2=季节，3=店铺，4=客户，5=销售类型
	private Integer Tjtag = 2;// hzfs:0=品牌， 1=类型， 2=季节，3=店铺，4=客户，5=销售类型
	private Integer Housecostbj = 0;

	private String Mindate = "";
	private String Maxdate = "";

	private String Accbegindate = "2000-01-01";
	// private String Nowdate = "";
	private Integer Qxbj = 0;// 1=启用权限控
	private Integer Maxday = 0;// 最大查询天数

	private String errmess;

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setHousecostbj(Integer housecostbj) {
		Housecostbj = housecostbj;
	}

	public String getErrmess() {
		return errmess;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
	}

	public int doTotal() {
		Date Nowday = Func.StrToDate(Nowdate);
		// dateid:0=本日，1=昨日，2=本周，3=本月 ,4=指定日期
		if (Dateid == 0) // 本日
		{
			Maxdate = Nowdate;// nowday.toString("yyyy-MM-dd");
			Mindate = Nowdate;
		} else if (Dateid == 1) // 昨日
		{
			Maxdate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
			Mindate = Func.DateToStr(Func.getDataAdd(Nowday, -1));// nowday.AddDays(-1).toString("yyyy-MM-dd");
		} else if (Dateid == 2) // 本周
		{
			Maxdate = Nowdate;// nowday.toString("yyyy-MM-dd");
			Mindate = Func.DateToStr(Func.getFirstDayOfWeek(Nowday));
		} else if (Dateid == 3) // 本月
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
		// String warning = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		Maxdate += " 23:59:59";
		Mindate += " 00:00:00";

		String qry = "declare ";
		qry += "\n    v_epid  number; ";
		qry += "\n    v_accid  number;  ";
		qry += "\n    v_mindate  varchar2(10);   ";
		qry += "\n    v_maxdate  varchar2(10);   ";
		qry += "\n    v_totalcustnum number(10); ";
		qry += "\n    v_xscustnum number(10); ";
		qry += "\n    v_xsnum number(10); ";
		qry += "\n    v_newcustnum number(10); ";
		qry += "\n    v_totalguestnum number(10);";
		qry += "\n    v_xsguestnum number(10);";
		qry += "\n    v_xxnum number(10);";
		qry += "\n    v_newguestnum number(10);";
		qry += "\n    v_guestcurr number(14,2);";
		qry += "\n    v_guestretailcurr  number(14,2);";
		qry += "\n    v_guestamt  number(14,2);";
		qry += "\n    v_plancurr number(14,2);";
		qry += "\n    v_retcs varchar2(1000);  ";// --储值卡期末
		qry += "\n    v_mlrate0 number(12,4);";
		qry += "\n    v_mlrate1 number(12,4);";
		qry += "\n    v_mlrate number(12,4);";
		qry += "\n    v_custactive number(12,4);";
		qry += "\n    v_guestactive number(12,4);";
		qry += "\n    v_guestrate number(10,4);";
		qry += "\n    v_discount0 number(10,2);";
		qry += "\n    v_price0 number(12,2);";

		qry += "\n    v_totalamt0 number; v_totalcurr0 number;v_fixedcurr0 number;v_mlcurr0 number; ";
		qry += "\n    v_totalamt1 number;v_totalcurr1 number;v_fixedcurr1 number;v_mlcurr1 number; ";
		qry += "\n    v_totalamt number;v_totalcurr number;v_totalretailcurr0 number;v_totalretailcurr number; ";
		qry += "\n    v_fixedcurr number;v_mlcurr number;v_checkcurr number; ";

		qry += "\n begin ";
		// qry += "\n v_plancurr:=0;";

		// 销售计划
		qry += "\n   select f_yearsaleplan(" + Accid + ",to_date('" + Func.subString(Mindate, 1, 10) + "','yyyy-mm-dd'),to_date('" + Func.subString(Maxdate, 1, 10) + "','yyyy-mm-dd')) into v_plancurr from dual; ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "         select ROWID from v_cxwareouthz_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_cxwareouthz_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n       end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_cxwareouthz_data (id,epid,notedate,noteno,custid,guestid,houseid,dptid,wareid,colorid,saleid,discount,sizeid,amount0,curr0,fixedcurr0,mlcurr0,amount1,curr1,fixedcurr1,mlcurr1,amount,curr,retailcurr0,retailcurr1,retailcurr,fixedcurr,mlcurr,checkcurr) ";
		qry += "\n    select v_cxwareouthz_data_id.nextval," + Userid
				+ ",notedate,noteno,custid,0,houseid,dptid,wareid,colorid,saleid,discount,sizeid,amount0,curr0,fixedcurr0,mlcurr0,amount1,curr1,fixedcurr1,mlcurr1,amount,curr,retailcurr0,retailcurr1,retailcurr,fixedcurr,curr-fixedcurr,checkcurr from ( ";
		qry += "\n    select notedate,noteno,custid,houseid,dptid,wareid,colorid,saleid,discount,sizeid";
		qry += "\n    ,sum(amount0) as amount0,sum(curr0) as curr0,sum(fixedcurr0) as fixedcurr0,sum(curr0-fixedcurr0) as mlcurr0";
		qry += "\n    ,sum(amount1) as amount1,sum(curr1) as curr1,sum(fixedcurr1) as fixedcurr1,sum(curr1-fixedcurr1) as mlcurr1";
		qry += "\n    ,sum(amount0+amount1) as amount,sum(curr0+curr1) as curr,sum(retailcurr0) as retailcurr0,sum(retailcurr1) as retailcurr1";
		qry += "\n    ,sum(retailcurr0+retailcurr1) as retailcurr,sum(fixedcurr0+fixedcurr1) as fixedcurr,sum(checkcurr) as checkcurr from ( ";
		boolean bj = false;
		// 店铺零售
		if (Xsid == 0 || Xsid == 2) {// xsid:0=零售，1=批发，2=所有
			qry += "\n     select /*+ ORDERED INDEX(b, IX1_WAREOUTM) USE_NL (a b) */";
			qry += "\n     to_char(a.notedate,'yyyy-mm-dd') as notedate,a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice";
			qry += "\n    ,sum( b.amount) as amount0,sum(b.curr) as curr0 ,sum( b.amount *d.costprice) as fixedcurr0";
			qry += "\n    ,sum( b.amount *c.retailsale) as retailcurr0";
			qry += "\n    ,0 as amount1,0 as curr1,0 as fixedcurr1 ";
			qry += "\n    ,0 as retailcurr1";
			qry += "\n    ,sum(  b.amount*d.costprice1) as checkcurr";
			qry += "\n    from wareouth a  ";
			qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join customer e on a.custid=e.custid ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			if (Housecostbj == 1)
				qry += " and a.houseid=d.houseid";
			else
				qry += " and d.houseid=0";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and a.ntid=0   ";
			if (Areaname.length() > 0)
				qry += " and e.areaname like '" + Areaname + "%'";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			// if (Xsid == 0) {// xsid:0=零售，1=批发，2=所有
			qry += "\n      and a.ntid=0   ";
			// } // --零售
			// else if (Xsid == 1) {
			// qry += "\n and (a.ntid=1 or a.ntid=2) ";
			// } // --经销

			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			if (Custid > 0)
				qry += "\n    and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			if (Saleid >= 0)
				qry += "\n    and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag; // if (handno != null && handno != "") qry += "\n and a.handno like '" + handno.ToUpper() + "%'";
			qry += "\n       group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice ";// }
			bj = true;
			if (Areaname.length() <= 0) {// xsid:0=零售，1=批发，2=所有
				if (bj)
					qry += "\n  union all";
				// 商场零售
				qry += "\n     select /*+ ORDERED INDEX(b, IX1_SHOPSALEM) USE_NL (a b) */";
				qry += "\n     to_char(a.notedate,'yyyy-mm-dd') as notedate,a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice";
				qry += "\n    ,sum(b.amount) as amount0,sum(b.curr) as curr0 ,sum( b.amount*d.costprice) as fixedcurr0";
				qry += "\n    ,sum( b.amount*c.retailsale) as retailcurr0";
				qry += "\n    ,0 as amount1,0 as curr1,0 as fixedcurr1 ";
				qry += "\n    ,0 as retailcurr1";
				qry += "\n    ,sum( b.amount*d.costprice1) as checkcurr";
				qry += "\n    from shopsaleh a  ";
				qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
				qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
				qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
				if (Housecostbj == 1)
					qry += " and a.houseid=d.houseid";
				else
					qry += " and d.houseid=0";
				qry += "\n    where  a.statetag=1 and a.accid=" + Accid;
				if (Qxbj == 1) // 1 启用权限控制
				{
					qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
					qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
				}
				// if (Xsid == 0) {
				// qry += "\n and a.ntid=0 ";
				// } // --零售
				// else if (Xsid == 1) {
				// qry += "\n and (a.ntid=1 or a.ntid=2) ";
				// } // --经销

				qry += "    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

				if (Dptid >= 0)
					qry += "\n   and a.dptid=" + Dptid;
				if (Custid > 0)
					qry += "\n    and a.custid=" + Custid;
				// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
				if (Houseid > 0)
					qry += "\n   and a.houseid=" + Houseid;
				if (Wareid > 0)
					qry += "\n    and b.wareid=" + Wareid;
				if (Colorid > 0)
					qry += "\n   and b.colorid=" + Colorid;
				if (Sizeid > 0)
					qry += "\n    and b.sizeid=" + Sizeid;
				if (Saleid >= 0)
					qry += "\n    and b.saleid=" + Saleid;
				if (Wareno.length() > 0)
					qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
				if (Warename.length() > 0)
					qry += "\n   and c.warename like '%" + Warename + "%'";
				if (Typeid >= 0)
					if (Typeid > 0 && Typeid < 1000) {
						qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
					} else {
						qry += "\n    and c.typeid=" + Typeid;
					}
				if (Brandid >= 0)
					qry += "\n    and c.brandid=" + Brandid;
				if (Areaid >= 0)
					qry += "\n    and c.areaid=" + Areaid;

				if (Provid >= 0)
					qry += "\n    and c.provid=" + Provid;
				if (Seasonname.length() > 0)
					qry += "\n   and c.seasonname='" + Seasonname + "'";
				if (Prodyear.length() > 0)
					qry += "\n   and c.prodyear='" + Prodyear + "'";
				if (Locale.length() > 0)
					qry += "\n   and c.locale='" + Locale + "'";
				if (Tjtag < 2)
					qry += "\n   and b.tjtag=" + Tjtag;
				qry += "\n       group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice ";// }
				bj = true;
			}
		}
		if (Xsid == 1 || Xsid == 2) { // xsid:0=零售，1=批发，2=所有
			if (bj)
				qry += "\n  union all";
			// 批发
			qry += "\n     select /*+ ORDERED INDEX(b, IX1_WAREOUTM) USE_NL (a b) */";
			qry += "\n     to_char(a.notedate,'yyyy-mm-dd') as notedate,a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice";
			qry += "\n    ,0 as amount0,0 as curr0,0 as fixedcurr0,0 as retailcurr0";
			qry += "\n    ,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount1,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr1 ";
			qry += "\n     ,sum( case a.ntid when 2 then -b.amount else b.amount end*d.costprice) as fixedcurr1";
			qry += "\n     ,sum( case a.ntid when 2 then -b.amount else b.amount end*c.retailsale) as retailcurr1 ";
			qry += "\n    ,sum( case a.ntid when 2 then -b.amount else b.amount end*d.costprice1) as checkcurr";
			qry += "\n    from wareouth a  ";
			qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join customer e on a.custid=e.custid ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			if (Housecostbj == 1)
				qry += " and a.houseid=d.houseid";
			else
				qry += " and d.houseid=0";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and (a.ntid=1 or a.ntid=2)   ";
			qry += "    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			if (Areaname.length() > 0)
				qry += " and e.areaname like '" + Areaname + "%'";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			// if (Xsid == 0) {
			// qry += "\n and a.ntid=0 ";
			// } // --零售
			// else if (Xsid == 1) {
			qry += "\n      and (a.ntid=1 or a.ntid=2) ";
			// } // --经销

			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			if (Custid > 0)
				qry += "\n    and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			if (Saleid >= 0)
				qry += "\n    and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;
			qry += "\n       group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice ";// }
		}
		qry += "\n     ) group by notedate,noteno,custid,houseid,dptid,wareid,colorid,saleid,discount,sizeid";
		qry += "\n    );";
		qry += "\n    commit;   ";

		// 会员总数
		qry += "\n    select count(*) into v_totalguestnum from guestvip where statetag=1 and accid=" + Accid + "; ";
		// 新会员数
		qry += "\n    select count(*) into v_newguestnum from guestvip where statetag=1 and accid=" + Accid;
		qry += "      and createdate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and createdate<to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss'); ";
		qry += "\n    v_xxnum:=0; v_xsguestnum:=0; v_guestcurr:=0;";
		// 零售单数
		if (Areaname.length() <= 0) {
			qry += "\n    select count(distinct noteno) into  v_xxnum from (";
			qry += "\n    select distinct a.noteno ";
			qry += "\n    from wareouth a  ";
			qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    where  a.statetag=1  and a.accid=" + Accid + " and a.ntid=0 ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			// if (Custid > 0)
			// qry += "\n and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			// if (Saleid >= 0)
			// qry += "\n and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;
			qry += "\n    union all";
			qry += "\n    select distinct a.noteno ";
			qry += "\n    from shopsaleh a  ";
			qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    where  a.statetag=1  and a.accid=" + Accid + " ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			// if (Custid > 0)
			// qry += "\n and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			// if (Saleid >= 0)
			// qry += "\n and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;
			qry += "\n ) ;";
			// 会员消费数，单数
			qry += "\n    select count(distinct guestid),nvl(sum(curr),0) into  v_xsguestnum , v_guestcurr from (";
			qry += "\n    select a.guestid,b.curr";
			qry += "\n    from wareouth a  ";
			qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and a.ntid=0 and a.guestid>0   ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			// if (Custid > 0)
			// qry += "\n and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			// if (Saleid >= 0)
			// qry += "\n and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;

			qry += "\n    union all";
			qry += "\n    select a.guestid,b.curr";
			qry += "\n    from shopsaleh a  ";
			qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and a.guestid>0   ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			// if (Custid > 0)
			// qry += "\n and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			// if (Saleid >= 0)
			// qry += "\n and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;

			qry += "\n )  ;";
		}

		// 批发客户总数
		qry += "\n    select count(*) into v_totalcustnum from customer where statetag=1 and noused=0 and accid=" + Accid + "; ";

		// 批发新客户总数
		qry += "\n    select count(*) into v_newcustnum from customer where statetag=1 and noused=0 and accid=" + Accid;
		qry += "      and createdate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and createdate<to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss'); ";

		// 批发消费客户数，单数
		qry += "\n    select count( distinct a.custid),count(distinct a.noteno) into  v_xscustnum , v_xsnum";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join customer e on a.custid=e.custid ";
		qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and (a.ntid=1 or a.ntid=2)   ";
		qry += "    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Areaname.length() > 0)
			qry += "\n  and e.areaname like '" + Areaname + "%'";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
		}

		if (Dptid >= 0)
			qry += "\n   and a.dptid=" + Dptid;
		if (Custid > 0)
			qry += "\n    and a.custid=" + Custid;
		// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
		if (Houseid > 0)
			qry += "\n   and a.houseid=" + Houseid;
		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (Saleid >= 0)
			qry += "\n    and b.saleid=" + Saleid;
		if (Wareno.length() > 0)
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n   and c.warename like '%" + Warename + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (Brandid >= 0)
			qry += "\n    and c.brandid=" + Brandid;
		if (Areaid >= 0)
			qry += "\n    and c.areaid=" + Areaid;

		if (Provid >= 0)
			qry += "\n    and c.provid=" + Provid;
		if (Seasonname.length() > 0)
			qry += "\n   and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n   and c.prodyear='" + Prodyear + "'";
		if (Locale.length() > 0)
			qry += "\n   and c.locale='" + Locale + "'";
		if (Tjtag < 2)
			qry += "\n   and b.tjtag=" + Tjtag;
		qry += "\n  ;";

		qry += "\n select nvl(sum(amount0),0),nvl(sum(curr0),0),nvl(sum(fixedcurr0),0),nvl(sum(mlcurr0),0)";
		qry += "\n ,nvl(sum(amount1),0),nvl(sum(curr1),0),nvl(sum(fixedcurr1),0),nvl(sum(mlcurr1),0)";
		qry += "\n ,nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(retailcurr0),0),nvl(sum(retailcurr),0)";
		qry += "\n ,nvl(sum(fixedcurr),0),nvl(sum(mlcurr),0),nvl(sum(checkcurr),0) ";

		qry += "\n  into v_totalamt0,v_totalcurr0,v_fixedcurr0,v_mlcurr0 ";
		qry += "\n  ,v_totalamt1,v_totalcurr1,v_fixedcurr1,v_mlcurr1 ";
		qry += "\n  ,v_totalamt,v_totalcurr,v_totalretailcurr0,v_totalretailcurr ";
		qry += "\n   ,v_fixedcurr,v_mlcurr,v_checkcurr ";

		qry += "\n  from v_cxwareouthz_data where epid=" + Userid + "; ";

		qry += "\n   v_mlrate0:=0;";
		qry += "\n   if v_totalcurr0<>0 then";
		qry += "\n      v_mlrate0 :=Round(v_mlcurr0 / v_totalcurr0,4);";
		qry += "\n   end if;";

		qry += "\n   v_mlrate1:=0;";
		qry += "\n   if v_totalcurr1<>0 then";
		qry += "\n      v_mlrate1 :=Round(v_mlcurr1 / v_totalcurr1,4);";
		qry += "\n   end if;";

		qry += "\n   v_mlrate:=0;";
		qry += "\n   if v_totalcurr<>0 then";
		qry += "\n      v_mlrate :=Round(v_mlcurr / v_totalcurr,4);";
		qry += "\n   end if;";

		qry += "\n   v_custactive := 0;";
		qry += "\n   if v_totalcustnum<>0 then ";
		qry += "\n  	v_custactive :=Round(v_xscustnum / v_totalcustnum, 4);";
		qry += "\n   end if;";

		qry += "\n   v_guestactive := 0;";
		qry += "\n   if v_totalguestnum<>0 then ";
		qry += "\n      v_guestactive := Round(v_xsguestnum / v_totalguestnum, 4);";
		qry += "\n   end if;";

		qry += "\n   v_guestrate := 0;";  //会员消费占比=会员消费金额/总金额
		qry += "\n   if v_totalcurr0<>0 then";
		qry += "\n      v_guestrate := Round(v_guestcurr / v_totalcurr0, 4);";
		qry += "\n   end if;";

		qry += "\n   v_discount0 := 0;";  //零售平均折扣
		qry += "\n   if v_totalretailcurr0 <> 0 then ";
		qry += "\n      v_discount0 := Round(v_totalcurr0 / v_totalretailcurr0, 4);";
		qry += "\n   end if;";

		qry += "\n   v_price0 := 0;";  //零售平均单价=零售总金额/零售总数量
		qry += "\n   if v_totalamt0<>0 then ";
		qry += "\n      v_price0 := Round(v_totalcurr0 / v_totalamt0, 4);";
		qry += "\n   end if;";

		//		qry += "\n select '\"TOTALAMT0\":'||nvl(sum(amount0),0)||',\"TOTALCURR0\":'||nvl(sum(curr0),0)||',\"FIXEDCURR0\":'||nvl(sum(fixedcurr0),0)||',\"MLCURR0\":'||nvl(sum(mlcurr0),0)";
		//		qry += "\n ||',\"TOTALAMT1\":'||nvl(sum(amount1),0)||',\"TOTALCURR1\":'||nvl(sum(curr1),0)||',\"FIXEDCURR1\":'||nvl(sum(fixedcurr1),0)||',\"MLCURR1\":'||nvl(sum(mlcurr1),0)";
		//		qry += "\n ||',TOTALAMT:'||nvl(sum(amount),0)||',TOTALCURR:'||nvl(sum(curr),0)||',TOTALRETAILCURR0:'||nvl(sum(retailcurr0),0)||',TOTALRETAILCURR:'||nvl(sum(retailcurr),0)";
		//		qry += "\n ||',\"FIXEDCURR\":'||nvl(sum(fixedcurr),0)||',\"MLCURR\":'||nvl(sum(mlcurr),0)||',\"CHECKCURR\":'||nvl(sum(checkcurr),0) ";

		qry += "\n select '\"TOTALAMT0\":\"'||v_totalamt0||'\",\"TOTALCURR0\":\"'||v_totalcurr0||'\",\"FIXEDCURR0\":\"'||v_fixedcurr0||'\",\"MLCURR0\":\"'||v_mlcurr0||'\"'";
		qry += "\n ||',\"TOTALAMT1\":\"'||v_totalamt1||'\",\"TOTALCURR1\":\"'||v_totalcurr1||'\",\"FIXEDCURR1\":\"'||v_fixedcurr1||'\",\"MLCURR1\":\"'||v_mlcurr1||'\"'";
		qry += "\n ||',\"TOTALAMT\":\"'||v_totalamt||'\",\"TOTALCURR\":\"'||v_totalcurr||'\",\"TOTALRETAILCURR0\":\"'||v_totalretailcurr0||'\",\"TOTALRETAILCURR\":\"'||v_totalretailcurr||'\"'";
		qry += "\n ||',\"FIXEDCURR\":\"'||v_fixedcurr||'\",\"MLCURR\":\"'||v_mlcurr||'\",\"CHECKCURR\":\"'||v_checkcurr||'\"' ";

		qry += "\n ||',\"TOTALCUSTNUM\":\"'||v_totalcustnum||'\",\"XSCUSTNUM\":\"'||v_xscustnum||'\",\"NEWCUSTNUM\":\"'||v_newcustnum||'\",\"XSNUM\":\"'||v_xsnum||'\"'";
		qry += "\n ||',\"TOTALGUESTNUM\":\"'||v_totalguestnum||'\",\"XSGUESTNUM\":\"'||v_xsguestnum||'\",\"NEWGUESTNUM\":\"'||v_newguestnum||'\",\"XXNUM\":\"'||v_xxnum||'\"'";
		qry += "\n ||',\"GUESTCURR\":\"'||v_guestcurr||'\",\"PLANCURR\":\"'||v_plancurr||'\"'";
		//		qry += "\n ||',\"MLRATE0\":\"'||to_char(v_mlrate0,'fm999990.9999')||'\",\"MLRATE1\":\"'||to_char(v_mlrate1,'fm999990.9999')||'\",\"MLRATE\":\"'||to_char(v_mlrate,'fm999990.9999')||'\"'";
		//		qry += "\n ||',\"CUSTACTIVE\":\"'||to_char(v_custactive,'fm999990.9999')||'\",\"GUESTACTIVE\":\"'||to_char(v_guestactive,'fm999990.9999')||'\",\"GUESTRATE\":\"'||to_char(v_guestrate,'fm999990.9999')||'\"'";
		//		qry += "\n ||',\"DISCOUNT0\":\"'||to_char(v_discount0,'fm999990.9999')||'\",\"PRICE0\":\"'||v_price0||'\"'";

		qry += "\n ||',\"MLRATE0\":\"'||v_mlrate0||'\",\"MLRATE1\":\"'||v_mlrate1||'\",\"MLRATE\":\"'||v_mlrate||'\"'";
		qry += "\n ||',\"CUSTACTIVE\":\"'||v_custactive||'\",\"GUESTACTIVE\":\"'||v_guestactive||'\",\"GUESTRATE\":\"'||v_guestrate||'\"'";
		qry += "\n ||',\"DISCOUNT0\":\"'||v_discount0||'\",\"PRICE0\":\"'||v_price0||'\"'";
		//		qry += "\n    v_mlrate0 number(10,2);";
		//		qry += "\n    v_mlrate1 number(10,2);";
		//		qry += "\n    v_mlrate number(10,2);";
		//		qry += "\n    v_custactive number(10,2);";
		//		qry += "\n    v_guestactive number(10,2);";
		//		qry += "\n    v_guestrate number(10,2);";
		//		qry += "\n    v_discount0 number(10,2);";
		//		qry += "\n    v_price0 number(12,2);";

		qry += "\n  into :retcs ";
		//		qry += "\n  from v_cxwareouthz_data where epid=" + Userid + "; ";
		qry += "\n  from dual;";

		qry += "\n end; ";
		//		System.out.println(qry);
		// LogUtils.LogDebugWrite("totalxshzfx2",qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		//		System.out.println("1111");
		//		System.out.println(param.get("retcs").getParamvalue().toString());
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
		// String Noteno = param.get("Noteno").getParamvalue().toString();
		errmess = param.get("retcs").getParamvalue().toString()//
				+ ",\"MINDATE\":\"" + Mindate + "\"" //
				+ ",\"MAXDATE\":\"" + Maxdate + "\"" + ",\"msg\":\"操作成功！\",\"warning\":\"" + mdv.getErrmess() + "\"";
		return 1;

	}

	//汇总零售，批发，客户销售
	public int doTotal3() {
//		Date Nowday = Func.StrToDate(Nowdate);
		//		Xslist = "111";  //1=零售，2=批发，3=客户销售
		if (Mindate.length() < 0 || Maxdate.length() < 0) {
			errmess = "mindate或maxdate不是一个有效值！";
			return 0;
		}
		Xslist += "000";
		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		// String warning = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		Maxdate += " 23:59:59";
		Mindate += " 00:00:00";

		String qry = "declare ";
		qry += "\n    v_epid  number; ";
		qry += "\n    v_accid  number;  ";
		qry += "\n    v_mindate  varchar2(10);   ";
		qry += "\n    v_maxdate  varchar2(10);   ";
		qry += "\n    v_totalcustnum number(10); ";
		qry += "\n    v_xscustnum number(10); ";
		qry += "\n    v_xsnum number(10); ";
		qry += "\n    v_newcustnum number(10); ";
		qry += "\n    v_totalguestnum number(10);";
		qry += "\n    v_xsguestnum number(10);";
		qry += "\n    v_xxnum number(10);";
		qry += "\n    v_newguestnum number(10);";
		qry += "\n    v_guestcurr number(14,2);";
		qry += "\n    v_guestretailcurr  number(14,2);";
		qry += "\n    v_guestamt  number(14,2);";
		qry += "\n    v_plancurr number(14,2);";
		qry += "\n    v_retcs varchar2(1000);  ";// --储值卡期末
		qry += "\n    v_mlrate0 number(12,4);";
		qry += "\n    v_mlrate1 number(12,4);";
		qry += "\n    v_mlrate number(12,4);";
		qry += "\n    v_custactive number(12,4);";
		qry += "\n    v_guestactive number(12,4);";
		qry += "\n    v_guestrate number(10,4);";
		qry += "\n    v_discount0 number(10,2);";
		qry += "\n    v_price0 number(12,2);";

//		qry += "\n    v_totalamt0 number; v_totalcurr0 number;v_fixedcurr0 number;v_mlcurr0 number; ";
//		qry += "\n    v_totalamt1 number;v_totalcurr1 number;v_fixedcurr1 number;v_mlcurr1 number; ";
		qry += "\n    v_totalamt number;v_totalcurr number;v_totalretailcurr number; ";
		qry += "\n    v_fixedcurr number;v_mlcurr number;v_checkcurr number; ";

		qry += "\n begin ";
		// qry += "\n v_plancurr:=0;";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "         select ROWID from v_cxwareouthz_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_cxwareouthz_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n       end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_cxwareouthz_data (id,epid,notedate,noteno,custid,guestid,houseid,dptid,wareid,colorid,saleid,discount,sizeid,amount,curr,retailcurr,fixedcurr,mlcurr) ";
		qry += "\n    select v_cxwareouthz_data_id.nextval," + Userid
				+ ",notedate,noteno,custid,0,houseid,dptid,wareid,colorid,saleid,discount,sizeid,amount,curr,retailcurr,fixedcurr,curr-fixedcurr from ( ";
		qry += "\n    select notedate,noteno,custid,houseid,dptid,wareid,colorid,saleid,discount,sizeid";
		qry += "\n    ,sum(amount) as amount,sum(curr) as curr";
		qry += "\n    ,sum(retailcurr) as retailcurr,sum(fixedcurr) as fixedcurr from ( ";
		boolean bj = false;
		// 店铺零售
		if (Func.subString(Xslist, 1, 1).equals("1")) {//Xslist = "111";  //1=零售，2=批发，3=客户销售
			//		if (Xsid == 0 || Xsid == 2) {// xsid:0=零售，1=批发，2=所有
			qry += "\n     select /*+ ORDERED INDEX(b, IX1_WAREOUTM) USE_NL (a b) */";
			qry += "\n     to_char(a.notedate,'yyyy-mm-dd') as notedate,a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice";
			qry += "\n    ,sum( b.amount) as amount,sum(b.curr) as curr ,sum( b.amount *d.costprice) as fixedcurr,sum(b.amount*c.retailsale) as retailcurr";
			//			qry += "\n    ,sum(  b.amount*d.costprice1) as checkcurr";
			qry += "\n    from wareouth a  ";
			qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join customer e on a.custid=e.custid ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			if (Housecostbj == 1)
				qry += " and a.houseid=d.houseid";
			else
				qry += " and d.houseid=0";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and a.ntid=0   ";
			if (Areaname.length() > 0)
				qry += " and e.areaname like '" + Areaname + "%'";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			// if (Xsid == 0) {// xsid:0=零售，1=批发，2=所有
			qry += "\n      and a.ntid=0   ";
			// } // --零售
			// else if (Xsid == 1) {
			// qry += "\n and (a.ntid=1 or a.ntid=2) ";
			// } // --经销

			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			if (Custid > 0)
				qry += "\n    and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			if (Saleid >= 0)
				qry += "\n    and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag; // if (handno != null && handno != "") qry += "\n and a.handno like '" + handno.ToUpper() + "%'";
			qry += "\n       group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice ";// }
			bj = true;
			if (Areaname.length() <= 0) {// xsid:0=零售，1=批发，2=所有
				if (bj)
					qry += "\n  union all";
				// 商场零售
				qry += "\n     select /*+ ORDERED INDEX(b, IX1_SHOPSALEM) USE_NL (a b) */";
				qry += "\n     to_char(a.notedate,'yyyy-mm-dd') as notedate,a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice";
				qry += "\n    ,sum(b.amount) as amount,sum(b.curr) as curr ,sum( b.amount*d.costprice) as fixedcurr,sum(b.amount*c.retailsale) as retailcurr";
				qry += "\n    from shopsaleh a  ";
				qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
				qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
				qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
				if (Housecostbj == 1)
					qry += " and a.houseid=d.houseid";
				else
					qry += " and d.houseid=0";
				qry += "\n    where  a.statetag=1 and a.accid=" + Accid;
				if (Qxbj == 1) // 1 启用权限控制
				{
					qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
					qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
				}
				// if (Xsid == 0) {
				// qry += "\n and a.ntid=0 ";
				// } // --零售
				// else if (Xsid == 1) {
				// qry += "\n and (a.ntid=1 or a.ntid=2) ";
				// } // --经销

				qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

				if (Dptid >= 0)
					qry += "\n   and a.dptid=" + Dptid;
				if (Custid > 0)
					qry += "\n    and a.custid=" + Custid;
				// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
				if (Houseid > 0)
					qry += "\n   and a.houseid=" + Houseid;
				if (Wareid > 0)
					qry += "\n    and b.wareid=" + Wareid;
				if (Colorid > 0)
					qry += "\n   and b.colorid=" + Colorid;
				if (Sizeid > 0)
					qry += "\n    and b.sizeid=" + Sizeid;
				if (Saleid >= 0)
					qry += "\n    and b.saleid=" + Saleid;
				if (Wareno.length() > 0)
					qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
				if (Warename.length() > 0)
					qry += "\n   and c.warename like '%" + Warename + "%'";
				if (Typeid >= 0)
					if (Typeid > 0 && Typeid < 1000) {
						qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
					} else {
						qry += "\n    and c.typeid=" + Typeid;
					}
				if (Brandid >= 0)
					qry += "\n    and c.brandid=" + Brandid;
				if (Areaid >= 0)
					qry += "\n    and c.areaid=" + Areaid;

				if (Provid >= 0)
					qry += "\n    and c.provid=" + Provid;
				if (Seasonname.length() > 0)
					qry += "\n   and c.seasonname='" + Seasonname + "'";
				if (Prodyear.length() > 0)
					qry += "\n   and c.prodyear='" + Prodyear + "'";
				if (Locale.length() > 0)
					qry += "\n   and c.locale='" + Locale + "'";
				if (Tjtag < 2)
					qry += "\n   and b.tjtag=" + Tjtag;
				qry += "\n       group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice ";// }
				bj = true;
			}
		}
		// 批发
		if (Func.subString(Xslist, 2, 1).equals("1")) {//Xslist = "111";  //1=零售，2=批发，3=客户销售
			if (bj)
				qry += "\n  union all";
			qry += "\n     select /*+ ORDERED INDEX(b, IX1_WAREOUTM) USE_NL (a b) */";
			qry += "\n     to_char(a.notedate,'yyyy-mm-dd') as notedate,a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice";
			qry += "\n    ,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr ";
			qry += "\n     ,sum( case a.ntid when 2 then -b.amount else b.amount end*d.costprice) as fixedcurr";
			qry += "\n     ,sum( case a.ntid when 2 then -b.amount else b.amount end*c.retailsale) as retailcurr ";
			qry += "\n    from wareouth a  ";
			qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join customer e on a.custid=e.custid ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			if (Housecostbj == 1)
				qry += " and a.houseid=d.houseid";
			else
				qry += " and d.houseid=0";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and (a.ntid=1 or a.ntid=2)   ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			if (Areaname.length() > 0)
				qry += " and e.areaname like '" + Areaname + "%'";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			// if (Xsid == 0) {
			// qry += "\n and a.ntid=0 ";
			// } // --零售
			// else if (Xsid == 1) {
			qry += "\n      and (a.ntid=1 or a.ntid=2) ";
			// } // --经销

			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			if (Custid > 0)
				qry += "\n    and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			if (Saleid >= 0)
				qry += "\n    and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;
			qry += "\n       group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,a.custid,a.houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice ";// }
		}

		// 客户销售
		if (Func.subString(Xslist, 3, 1).equals("1")) {//Xslist = "111";  //1=零售，2=批发，3=客户销售
			if (bj)
				qry += "\n  union all";
			qry += "\n     select /*+ ORDERED INDEX(b, IX1_custsalem) USE_NL (a b) */";
			qry += "\n     to_char(a.notedate,'yyyy-mm-dd') as notedate,a.noteno,a.custid,0 as houseid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice";
			qry += "\n    ,sum(b.amount ) as amount,sum(b.curr) as curr,sum(b.amount*d.costprice) as fixedcurr,sum(b.amount*c.retailsale) as retailcurr";
			//			qry += "\n    ,sum(b.amount*d.costprice) as checkcurr";
			qry += "\n    from custsaleh a  ";
			qry += "\n    join custsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join customer e on a.custid=e.custid ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    left outer join custcost d on b.wareid=d.wareid and a.custid=d.custid and d.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid;
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			if (Areaname.length() > 0)
				qry += " and e.areaname like '" + Areaname + "%'";
			//			if (Qxbj == 1) // 1 启用权限控制
			//			{
			//				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			//				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			//			}
			// } // --经销

			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			if (Custid > 0)
				qry += "\n    and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			//			if (Houseid > 0)
			//				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			if (Saleid >= 0)
				qry += "\n    and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;
			qry += "\n       group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,a.custid,a.dptid,b.wareid,b.colorid,b.saleid,b.sizeid,b.discount,d.costprice ";// }
		}

		qry += "\n     ) group by notedate,noteno,custid,houseid,dptid,wareid,colorid,saleid,discount,sizeid";

		qry += "\n    );";
		qry += "\n    commit;   ";

		// 会员总数
		qry += "\n    select count(*) into v_totalguestnum from guestvip where statetag=1 and accid=" + Accid + "; ";
		// 新会员数
		qry += "\n    select count(*) into v_newguestnum from guestvip where statetag=1 and accid=" + Accid;
		qry += "      and createdate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and createdate<to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss'); ";
		qry += "\n    v_xxnum:=0; v_xsguestnum:=0; v_guestcurr:=0;";
		// 零售单数
		if (Areaname.length() <= 0) {
			qry += "\n    select count(distinct noteno) into  v_xxnum from (";
			qry += "\n    select distinct a.noteno ";
			qry += "\n    from wareouth a  ";
			qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    where  a.statetag=1  and a.accid=" + Accid + " and a.ntid=0 ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			// if (Custid > 0)
			// qry += "\n and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			// if (Saleid >= 0)
			// qry += "\n and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;
			qry += "\n    union all";
			qry += "\n    select distinct a.noteno ";
			qry += "\n    from shopsaleh a  ";
			qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    where  a.statetag=1  and a.accid=" + Accid + " ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			// if (Custid > 0)
			// qry += "\n and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			// if (Saleid >= 0)
			// qry += "\n and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;
			qry += "\n ) ;";
			// 会员消费数，单数
			qry += "\n    select count(distinct guestid),nvl(sum(curr),0) into  v_xsguestnum , v_guestcurr from (";
			qry += "\n    select a.guestid,b.curr";
			qry += "\n    from wareouth a  ";
			qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and a.ntid=0 and a.guestid>0   ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			// if (Custid > 0)
			// qry += "\n and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			// if (Saleid >= 0)
			// qry += "\n and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;

			qry += "\n    union all";
			qry += "\n    select a.guestid,b.curr";
			qry += "\n    from shopsaleh a  ";
			qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and a.guestid>0   ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
			}
			if (Dptid >= 0)
				qry += "\n   and a.dptid=" + Dptid;
			// if (Custid > 0)
			// qry += "\n and a.custid=" + Custid;
			// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
			if (Houseid > 0)
				qry += "\n   and a.houseid=" + Houseid;
			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			// if (Saleid >= 0)
			// qry += "\n and b.saleid=" + Saleid;
			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename like '%" + Warename + "%'";
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (Brandid >= 0)
				qry += "\n    and c.brandid=" + Brandid;
			if (Areaid >= 0)
				qry += "\n    and c.areaid=" + Areaid;

			if (Provid >= 0)
				qry += "\n    and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale='" + Locale + "'";
			if (Tjtag < 2)
				qry += "\n   and b.tjtag=" + Tjtag;

			qry += "\n )  ;";
		}

		// 批发客户总数
		qry += "\n    select count(*) into v_totalcustnum from customer where statetag=1 and noused=0 and accid=" + Accid + "; ";

		// 批发新客户总数
		qry += "\n    select count(*) into v_newcustnum from customer where statetag=1 and noused=0 and accid=" + Accid;
		qry += "      and createdate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and createdate<to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss'); ";

		// 批发消费客户数，单数
		qry += "\n    select count( distinct a.custid),count(distinct a.noteno) into  v_xscustnum , v_xsnum";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join customer e on a.custid=e.custid ";
		qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and (a.ntid=1 or a.ntid=2)   ";
		qry += "    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Areaname.length() > 0)
			qry += "\n  and e.areaname like '" + Areaname + "%'";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
		}

		if (Dptid >= 0)
			qry += "\n   and a.dptid=" + Dptid;
		if (Custid > 0)
			qry += "\n    and a.custid=" + Custid;
		// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
		if (Houseid > 0)
			qry += "\n   and a.houseid=" + Houseid;
		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (Saleid >= 0)
			qry += "\n    and b.saleid=" + Saleid;
		if (Wareno.length() > 0)
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n   and c.warename like '%" + Warename + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (Brandid >= 0)
			qry += "\n    and c.brandid=" + Brandid;
		if (Areaid >= 0)
			qry += "\n    and c.areaid=" + Areaid;

		if (Provid >= 0)
			qry += "\n    and c.provid=" + Provid;
		if (Seasonname.length() > 0)
			qry += "\n   and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n   and c.prodyear='" + Prodyear + "'";
		if (Locale.length() > 0)
			qry += "\n   and c.locale='" + Locale + "'";
		if (Tjtag < 2)
			qry += "\n   and b.tjtag=" + Tjtag;
		qry += "\n  ;";

		qry += "\n select nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(retailcurr),0)";
		qry += "\n ,nvl(sum(fixedcurr),0),nvl(sum(mlcurr),0),nvl(sum(checkcurr),0) ";
		qry += "\n  into  v_totalamt,v_totalcurr,v_totalretailcurr ";
		qry += "\n   ,v_fixedcurr,v_mlcurr,v_checkcurr ";

		qry += "\n  from v_cxwareouthz_data where epid=" + Userid + "; ";

		qry += "\n select '\"TOTALAMT\":\"'||v_totalamt||'\",\"TOTALCURR\":\"'||v_totalcurr||'\",\"TOTALRETAILCURR\":\"'||v_totalretailcurr||'\"'";
		qry += "\n ||',\"FIXEDCURR\":\"'||v_fixedcurr||'\",\"MLCURR\":\"'||v_mlcurr||'\",\"CHECKCURR\":\"'||v_checkcurr||'\"' ";


		qry += "\n  into :retcs ";
		//		qry += "\n  from v_cxwareouthz_data where epid=" + Userid + "; ";
		qry += "\n  from dual;";

		qry += "\n end; ";
		//		System.out.println(qry);
		// LogUtils.LogDebugWrite("totalxshzfx2",qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		//		System.out.println("1111");
		//		System.out.println(param.get("retcs").getParamvalue().toString());
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
		// String Noteno = param.get("Noteno").getParamvalue().toString();
		errmess = param.get("retcs").getParamvalue().toString()//
				+ ",\"MINDATE\":\"" + Mindate + "\"" //
				+ ",\"MAXDATE\":\"" + Maxdate + "\"" + ",\"msg\":\"操作成功！\",\"warning\":\"" + mdv.getErrmess() + "\"";
		return 1;

	}

	public Table GetTable(QueryParam qp, int sortid, String order) {

		String sort = "";

		// StringBuilder strSql = new StringBuilder();
		String qry = "  select wareid,wareno,warename,units,imagename0,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount, rank() over (order by curr desc) as xsord from (";
		qry += " select a.wareid,b.wareno,b.warename,b.units,b.imagename0,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
		// if (xsid == 2)
		qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
		qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";

		qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
		// if (totalcurr == 0)
		// qry += ",1 as rate";
		// else
		// qry += ",round(sum(a.curr)/" + totalcurr + ",2) as rate";

		qry += " FROM v_cxwareouthz_data a";
		qry += " left outer join warecode b on a.wareid=b.wareid";
		qry += " where a.epid=" + Userid;
		if (Dptid >= 0)
			qry += "\n   and a.dptid=" + Dptid;
		if (Custid > 0)
			qry += "\n    and a.custid=" + Custid;
		if (Discount >= 0)
			qry += "\n    and a.discount=" + Discount;
		// if (guestid > 0) qry += "\n and a.guestid=" + guestid;
		if (Houseid > 0)
			qry += "\n   and a.houseid=" + Houseid;
		if (Colorid > 0)
			qry += "\n   and a.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and a.sizeid=" + Sizeid;
		if (Saleid >= 0)
			qry += "\n    and a.saleid=" + Saleid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=b.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and b.typeid=" + Typeid;
			}
		if (Brandid >= 0)
			qry += "\n    and b.brandid=" + Brandid;
		if (Seasonname.length() > 0)
			qry += "\n   and b.seasonname='" + Seasonname + "'";
		if (Notedate.length() > 0)
			qry += "\n   and a.notedate='" + Notedate + "'";
		qry += " group by a.wareid,b.wareno,b.warename,b.units,b.imagename0";
		qry += ")";

		if (sortid == 1) {
			sort = " amount " + order + ",wareid";
		} else if (sortid == 2) {
			sort = " curr " + order + ",wareid";
		} else if (sortid == 3) {
			sort = " mlcurr " + order + ",wareid";
		} else if (sortid == 4) {
			sort = " xsord " + order + ",wareid";
		} else {
			sort = " wareno " + order + ",wareid";
		}

		qp.setQueryString(qry);
		qp.setSortString(sort);
		qp.setSumString("nvl(sum(curr),0) as totalcurr,nvl(sum(amount),0) as totalamt,nvl(sum(mlcurr),0) as totalml");
		qp.setCalcfield("case when [totalcurr] 0 then 1 else curr/[totalcurr] end as rate");
		return DbHelperSQL.GetTable(qp);
	}

	public Table GetTable(QueryParam qp, int hzfs, int sortid, String order, Float totalcurr) {
		String qry = "";
		// String sort = "";
		if (hzfs == 0) // 按货号汇总
		{
			qry = "  select wareid,wareno,warename,units,imagename0,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += "  ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select a.wareid,b.wareno,b.warename,b.units,b.imagename0,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			// if (xsid == 2)
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			// qry += ",sum(a.amount*b.checksale) as checkcurr";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.wareid,b.wareno,b.warename,b.units,b.imagename0";
			qry += ")";

			// if (sortid == 1) {
			// sort = " amount " + order + ",wareid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",wareid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",wareid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",wareid";
			// } else {
			// sort = " wareno " + order + ",wareid";
			// }

		} else if (hzfs == 1) // 按货号+颜色汇总
		{
			qry = "  select wareid,wareno,warename,units,imagename0,colorid,colorname,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select a.wareid,b.wareno,b.warename,b.units,b.imagename0,a.colorid,c.colorname,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			// if (xsid == 2)
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			// qry += ",sum(a.amount*b.checksale) as checkcurr";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.wareid,b.wareno,b.warename,b.units,b.imagename0,a.colorid,c.colorname )";
			// if (sortid == 1) {
			// sort = " amount " + order + ",wareid,colorid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",wareid,colorid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",wareid,colorid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",wareid,colorid";
			// } else {
			// sort = " wareno " + order + ",wareid,colorid";
			// }

		} else if (hzfs == 2) // 按货号+颜色+尺码汇总
		{
			qry = "  select wareid,wareno,warename,units,imagename0,colorid,colorname,sizeid,sizename,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select a.wareid,b.wareno,b.warename,b.units,b.imagename0,a.colorid,c.colorname,a.sizeid,d.sizename,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += " left outer join sizecode d on a.sizeid=d.sizeid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.wareid,b.wareno,b.warename,b.units,b.imagename0,a.colorid,c.colorname,a.sizeid,d.sizename)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",wareid,colorid,sizeid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",wareid,colorid,sizeid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",wareid,colorid,sizeid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",wareid,colorid,sizeid";
			// } else {
			// sort = " wareno " + order + ",wareid,colorid,sizeid";
			// }

		} else if (hzfs == 3) // 类型
		{
			qry = "  select typeid,typename,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select b.typeid,c.fullname as typename,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join waretype c on b.typeid=c.typeid";
			qry += " where a.epid=" + Userid;
			qry += " group by b.typeid,c.fullname)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",typeid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",typeid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",typeid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",typeid";
			// } else {
			// sort = " typename " + order + ",typeid";
			// }
		} else if (hzfs == 10) // 颜色
		{
			qry = "  select colorid,colorname,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select a.colorid,c.colorname ,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.colorid,c.colorname)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",colorid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",colorid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",colorid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",colorid";
			// } else {
			// sort = " colorname " + order + ",colorid";
			// }
		} else if (hzfs == 11) // 销售类型
		{
			qry = "  select saleid,salename,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select a.saleid,c.salename ,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join salecode c on a.saleid=c.saleid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.saleid,c.salename)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",saleid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",saleid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",saleid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",saleid";
			// } else {
			// sort = " salename " + order + ",saleid";
			// }
		} else if (hzfs == 4) // 品牌
		{
			qry = "  select brandid,brandname,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select b.brandid,c.brandname,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join brand c on b.brandid=c.brandid";
			qry += " where a.epid=" + Userid;
			qry += " group by b.brandid,c.brandname)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",brandid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",brandid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",brandid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",brandid";
			// } else {
			// sort = " brandname " + order + ",brandid";
			// }
		} else if (hzfs == 5) // 季节
		{
			qry = "  select seasonname,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select b.seasonname,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			// qry += " left outer join brand c on b.brandid=c.brandid";
			qry += " where a.epid=" + Userid;
			qry += " group by b.seasonname)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",seasonname";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",seasonname";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",seasonname";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",seasonname";
			// } else {
			// sort = " seasonname " + order;
			// }
		} else if (hzfs == 6) // 日期
		{
			qry = "  select notedate,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select a.notedate,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			// qry += " left outer join brand c on b.brandid=c.brandid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.notedate)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",notedate";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",notedate";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",notedate";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",notedate";
			// } else {
			// sort = " notedate " + order;
			// }
		} else
		// xsid:0=零售 1=经销 2=所有
		if (hzfs == 8) // 客户
		{
			qry = "  select custid,custname,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";

			qry += " select a.custid,case a.custid when 0 then cast('<零售>' as nvarchar2(10)) else c.custname end as custname,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";

			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join customer c on a.custid=c.custid";
			// qry += " left outer join brand c on b.brandid=c.brandid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.custid,case a.custid when 0 then cast('<零售>' as nvarchar2(10)) else c.custname end)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",custid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",custid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",custid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",custid";
			// } else {
			// sort = " custname " + order + ",custid";
			// }
		} else if (hzfs == 7) // 店铺
		{
			qry = "  select houseid,housename,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select a.houseid,c.housename,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join warehouse c on a.houseid=c.houseid";
			// qry += " left outer join brand c on b.brandid=c.brandid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.houseid,c.housename)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",houseid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",houseid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",houseid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",houseid";
			// } else {
			// sort = " housename " + order + ",houseid";
			// }
		}

		else if (hzfs == 13) // 部门
		{
			qry = "  select dptid,dptname,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select a.dptid,c.dptname ,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join department c on a.dptid=c.dptid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.dptid,c.dptname)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",dptid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",dptid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",dptid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",dptid";
			// } else {
			// sort = " dptname " + order + ",dptid";
			// }
		} else if (hzfs == 14) // 尺码
		{
			qry = "  select sizeid,sizename,sizeno,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select a.sizeid,c.groupno||'.'||c.sizename as sizename,c.sizeno,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join sizecode c on a.sizeid=c.sizeid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.sizeid,c.groupno||'.'||c.sizename,c.sizeno)";
			// if (sortid == 1) {
			// sort = " amount " + order + ",sizeid";
			// } else if (sortid == 2) {
			// sort = " curr " + order + ",sizeid";
			// } else if (sortid == 3) {
			// sort = " mlcurr " + order + ",sizeid";
			// } else if (sortid == 4) {
			// sort = " xsord " + order + ",sizeid";
			// } else {
			// sort = " sizeno " + order + ",sizeid";
			// }
		} else if (hzfs == 15) {

			qry = "  select discount,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select trunc(a.discount*10)/10 as discount,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			// qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			// qry += " left outer join sizecode b on a.sizeid=b.sizeid";
			qry += " where a.epid=" + Userid;
			qry += " group by  trunc(a.discount*10)/10 )";
		} else if (hzfs == 16) // 货位
		{
			qry = "  select locale,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select b.locale,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			// qry += " left outer join brand c on b.brandid=c.brandid";
			qry += " where a.epid=" + Userid;
			qry += " group by b.locale)";
		} else if (hzfs == 17) // 日期+供应商
		{
			qry = "  select notedate,provid,provname,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate,keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select a.notedate,b.provid,c.provname,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += "\n FROM v_cxwareouthz_data a";
			qry += "\n left outer join warecode b on a.wareid=b.wareid";
			qry += "\n left outer join provide c on b.provid=c.provid";
			qry += "\n where a.epid=" + Userid;
			qry += "\n group by a.notedate,b.provid,c.provname)";
		} else if (hzfs == 18) // 区位
		{
			qry = "  select areaid,areaname,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr, checkmlcurr,price,mlrate, keyid, rank() over (order by curr desc) as xsord from (";
			qry += " select b.areaid,c.areaname,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join area c on b.areaid=c.areaid";
			qry += " where a.epid=" + Userid;
			qry += " group by b.areaid,c.areaname)";
		} else if (hzfs == 19) // 供应商
		{
			qry = "  select provid,provname,amount,curr,amount0,curr0,amount1,curr1,fixedcurr,mlcurr,xsnum,skc,zbrate,discount";
			qry += " ,checkcurr,checkmlcurr,price,mlrate, keyid,rank() over (order by curr desc) as xsord from (";
			qry += " select b.provid,c.provname,sum(a.amount) as amount,sum(a.curr) as curr,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc";
			qry += ",sum(a.checkcurr) as checkcurr,sum(a.curr-a.checkcurr) as checkmlcurr";
			// if (xsid == 2)
			qry += ",sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",sum(a.fixedcurr) as fixedcurr,sum(a.mlcurr) as mlcurr,count(distinct a.noteno) as xsnum";
			qry += ",case sum(a.retailcurr) when 0 then 0 else round(sum(a.curr)/sum(a.retailcurr),2) end as discount";
			qry += ",case sum(a.curr) when 0 then 0 else round(sum(a.mlcurr)/sum(a.curr),2) end as mlrate";
			qry += ",case sum(a.amount) when 0 then 0 else round(sum(a.curr)/sum(a.amount),2) end as price";
			if (totalcurr == 0)
				qry += ",0 as zbrate";
			else
				qry += ",round(sum(a.curr)/" + totalcurr + ",4) as zbrate";
			qry += ",min(a.id) as keyid";
			qry += " FROM v_cxwareouthz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join provide c on b.provid=c.provid";
			qry += " where a.epid=" + Userid;
			qry += " group by b.provid,c.provname)";
		}
		qp.setQueryString(qry);
		// qp.setSortString(sort);
		return DbHelperSQL.GetTable(qp);
	}
}
