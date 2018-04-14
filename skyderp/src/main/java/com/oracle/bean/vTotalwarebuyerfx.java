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
public class vTotalwarebuyerfx {
	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd
	// HH:mm:ss,------------必传
	private Long Accid = (long) 0;
	private Long Buyeraccid = (long) 0;

	private String Notedate = "";
	private Long Userid = (long) 0;
	private Long Wareid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;
	// private Long Dptid = (long) -1;
	// private Long Areaid = (long) -1;
	// private Long Brandid = (long) -1;
	// private Long Saleid = (long) -1;
	private Long Typeid = (long) -1;
	// private Long Provid = (long) -1;
	private String Seasonname = "";
	private String Warename = "";
	private String Wareno = "";
	private String Prodyear = "";

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

	public void setServerdatetime(String serverdatetime) {
		Serverdatetime = serverdatetime;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
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

	// 汇总商品买家进销比
	public int doTotal() {
		// Date Nowday = Func.StrToDate(Nowdate);
		if (Mindate.length() < 0 || Maxdate.length() < 0) {
			errmess = "mindate或maxdate不是一个有效值！";
			return 0;
		}

		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		// String warning = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		String mindate0 = Func.DateToStr(Func.getPrevDay(Mindate));
		Maxdate += " 23:59:59";
		Mindate += " 00:00:00";
		if (Maxdate.compareTo(Serverdatetime) > 0)
			Maxdate = Serverdatetime;

		String qry = "declare ";
		// qry += "\n v_epid number; ";
		// qry += "\n v_accid number; ";
		qry += "\n     v_totalamtrk number;  "; // --14
		qry += "\n     v_totalcurrrk number;  "; // --14
		qry += "\n     v_totalamtxs number;  "; // --15
		qry += "\n     v_totalcurrxs number;  "; // --15
		qry += "\n     v_totalamtjc number;  "; // --15
		qry += "\n     v_totalamtqc number;  "; // --15
		qry += "\n begin  ";
		// qry += " v_accid:=" + accid + "; ";
		// qry += " v_epid:=" + userid + "; ";

		// qry += " insert into v_warebuyerfx_temp
		// (accid,accname,company,amountrk,amountxs) ";
		// qry += " delete from v_warebuyerfx_data where epid=" + userid + "; ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_warebuyerfx_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_warebuyerfx_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n       end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_warebuyerfx_data (id,epid,accid,custid,amountqc,amountrk,currrk,amountxs,currxs,amountjc) ";
		qry += "\n    select v_warebuyerfx_data_id.nextval," + Userid + ",accid,custid,amountqc,amountrk,currrk,amountxs,currxs,amountqc+amountrk-amountxs from ( ";

		qry += "\n    select accid,custid,nvl(sum(amountqc),0) as amountqc,nvl(sum(amountrk),0) as amountrk,nvl(sum(currrk),0) as currrk";
		qry += "\n    ,nvl(sum(amountxs),0) as amountxs ,nvl(sum(currxs),0) as currxs from ( ";

		qry += "\n    select b.accid,a1.sellcustid as custid, sum(a.amount) as amountqc,0 as amountrk,0 as currrk,0 as amountxs,0 as currxs  ";
		qry += "\n    from waresum a";
		qry += "\n    left outer join warecode b on a.wareid=b.wareid";
		qry += "\n    join accconnect a1 on a1.sellaccid=" + Accid + " and a1.buyaccid=b.accid and a1.sellcustid>0 ";
		qry += "\n    left outer join warecode e on b.accid1=e.accid and b.wareid1=e.wareid";
		qry += "\n    where a.daystr='" + mindate0 + "' and e.accid=" + Accid;

		if (Wareid > 0) {
			qry += "  and e.wareid=" + Wareid;
		}

		// if (typeid > 0) { qry += " and d.typeid=" + typeid; };
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=e.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and e.typeid=" + Typeid;
			}

		// if (brandid >= 0) { qry += " and d.brandid=" + brandid; };
		if (Wareno.length() > 0) {
			qry += "  and e.wareno like '%" + Wareno.toUpperCase() + "%'";
		}

		if (Warename.length() > 0) {
			qry += "  and e.warename like '%" + Warename + "%'";
		}

		if (Prodyear.length() > 0) {
			qry += "  and e.prodyear like '%" + Prodyear + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "  and e.seasonname like '%" + Seasonname + "%'";
		}

		if (Buyeraccid > 0) {
			qry += "  and b.accid=" + Buyeraccid;
		}

		qry += "\n    group by b.accid,a1.sellcustid ";
		// 本期发生
		qry += "\n    union all";
		qry += "\n    select  /*+ index (a IX1_WAREINH) */ a.accid,a1.sellcustid as custid,0 as amountqc";
		qry += "\n    ,sum(case a.ntid when 0 then b.amount else -b.amount end) as amountrk";
		qry += "\n    ,sum(case a.ntid when 0 then b.curr else -b.curr end) as currrk";
		qry += "\n    ,0 as amountxs,0 as currxs  ";
		qry += "\n    from wareinh a ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		// qry += "\n join accreg c on a.accid=c.accid ";
		qry += "\n    join warecode d on b.wareid=d.wareid ";
		qry += "\n    join accconnect a1 on a1.sellaccid=" + Accid + " and a1.buyaccid=a.accid  and a1.sellcustid>0";
		qry += "\n    left outer join warecode e on d.accid1=e.accid and d.wareid1=e.wareid";
		// qry += "\n join customer b1 on a1.sellcustid=b1.custid ";
		qry += "\n    where a.statetag=1  and e.accid=" + Accid;
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Wareid > 0) {
			qry += "  and e.wareid=" + Wareid;
		}

		// if (typeid > 0) { qry += " and d.typeid=" + typeid; };
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=e.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and e.typeid=" + Typeid;
			}

		// if (brandid >= 0) { qry += " and d.brandid=" + brandid; };
		if (Wareno.length() > 0) {
			qry += "  and e.wareno like '%" + Wareno.toUpperCase() + "%'";
		}

		if (Warename.length() > 0) {
			qry += "  and e.warename like '%" + Warename + "%'";
		}

		if (Prodyear.length() > 0) {
			qry += "  and e.prodyear like '%" + Prodyear + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "  and e.seasonname like '%" + Seasonname + "%'";
		}

		if (Buyeraccid > 0) {
			qry += "  and a.accid=" + Buyeraccid;
		}

		qry += "\n    and exists (select 1 from warecode a1 where a1.accid=" + Accid + " and a1.wareid=d.wareid1)  ";
		// qry += " and exists (select 1 from accconnect a1 where
		// a1.sellaccid="+accid+" and a1.buyaccid=a.accid) ";
		qry += "\n    group by a.accid,a1.sellcustid";
		qry += "\n    union all ";
		qry += "\n    select /*+ index (a IX1_WAREOUTH) */ a.accid,a1.sellcustid as custid,0 as amountqc, 0 as amountrk,0 as currrk";
		qry += "\n     ,sum(case a.ntid when 2 then -b.amount else b.amount end) as amountxs  ";
		qry += "\n     ,sum(case a.ntid when 2 then -b.curr else b.curr end) as currxs  ";
		qry += "\n    from wareouth a ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
		// qry += "\n join accreg c on a.accid=c.accid ";
		qry += "\n    join warecode d on b.wareid=d.wareid ";
		qry += "\n    join accconnect a1 on a1.sellaccid=" + Accid + " and a1.buyaccid=a.accid and a1.sellcustid>0 ";
		qry += "\n    left outer join warecode e on d.accid1=e.accid and d.wareid1=e.wareid";
		qry += "\n    where a.statetag=1 and e.accid= " + Accid;

		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Wareid > 0) {
			qry += "  and e.wareid=" + Wareid;
		}

		// if (typeid > 0) { qry += " and d.typeid=" + typeid; };
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=e.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and e.typeid=" + Typeid;
			}

		// if (brandid >= 0) { qry += " and d.brandid=" + brandid; };
		if (Wareno.length() > 0) {
			qry += "  and e.wareno like '%" + Wareno.toUpperCase() + "%'";
		}

		if (Warename.length() > 0) {
			qry += "  and e.warename like '%" + Warename + "%'";
		}

		if (Prodyear.length() > 0) {
			qry += "  and e.prodyear like '%" + Prodyear + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "  and e.seasonname like '%" + Seasonname + "%'";
		}

		if (Buyeraccid > 0) {
			qry += "  and a.accid=" + Buyeraccid;
		}

		qry += "\n    and exists (select 1 from warecode a1 where a1.accid=" + Accid + " and a1.wareid=d.wareid1)  ";
		// qry += " and exists (select 1 from accconnect a1 where
		// a1.sellaccid="+accid+" and a1.buyaccid=a.accid) ";
		qry += "\n    group by a.accid,a1.sellcustid";

		qry += "\n    union all ";
		qry += "\n    select /*+ index (a IX1_SHOPSALEH) */ a.accid,a1.sellcustid as custid,0 as amountqc, 0 as amountrk,0 as currrk";
		qry += "\n    ,sum(b.amount) as amountxs  ";
		qry += "\n    ,sum(b.curr) as currxs  ";
		qry += "\n    from shopsaleh a ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno ";
		// qry += "\n join accreg c on a.accid=c.accid ";
		qry += "\n    join warecode d on b.wareid=d.wareid ";
		qry += "\n    join accconnect a1 on a1.sellaccid=" + Accid + " and a1.buyaccid=a.accid  and a1.sellcustid>0";
		qry += "\n    left outer join warecode e on d.accid1=e.accid and d.wareid1=e.wareid";
		qry += "\n    where a.statetag=1 and e.accid= " + Accid;
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Wareid > 0) {
			qry += "  and e.wareid=" + Wareid;
		}

		// if (typeid > 0) { qry += " and d.typeid=" + typeid; };
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=e.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and e.typeid=" + Typeid;
			}

		// if (brandid >= 0) { qry += " and d.brandid=" + brandid; };
		if (Wareno.length() > 0) {
			qry += "  and e.wareno like '%" + Wareno.toUpperCase() + "%'";
		}

		if (Warename.length() > 0) {
			qry += "  and e.warename like '%" + Warename + "%'";
		}

		if (Prodyear.length() > 0) {
			qry += "  and e.prodyear like '%" + Prodyear + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "  and e.seasonname like '%" + Seasonname + "%'";
		}

		if (Buyeraccid > 0) {
			qry += "  and a.accid=" + Buyeraccid;
		}

		qry += "\n    and exists (select 1 from warecode a1 where a1.accid=" + Accid + " and a1.wareid=d.wareid1)  ";
		// qry += " and exists (select 1 from accconnect a1 where
		// a1.sellaccid="+accid+" and a1.buyaccid=a.accid) ";
		qry += "\n    group by a.accid,a1.sellcustid ";

		qry += "\n    ) group by accid,custid); ";

		// qry += " delete from v_warebuyerfx_data where epid="+userid+"; ";
		// qry += " insert into v_warebuyerfx_data
		// (id,epid,accid,accname,company,amountrk,amountxs,amountjc,raterk,ratexs,ratewc)
		// ";
		// qry += " select
		// v_warebuyerfx_data_id.nextval,"+userid+",accid,accname,company,amountrk,amountxs,amountrk-amountxs,0,0,0
		// from v_warebuyerfx_temp; ";

		qry += "\n    select nvl(sum(amountqc),0),nvl(sum(amountrk),0),nvl(sum(currrk),0),nvl(sum(amountxs),0),nvl(sum(currxs),0),nvl(sum(amountjc),0)";
		qry += "\n    into v_totalamtqc,v_totalamtrk,v_totalcurrrk,v_totalamtxs,v_totalcurrxs,v_totalamtjc from v_warebuyerfx_data where epid=" + Userid + ";";
		// qry += "\n select sum(amountrk),sum(amountxs) into
		// v_totalamtrk,v_totalamtxs from v_warebuyerfx_data where epid=" +
		// userid + "; ";
		qry += "\n    update v_warebuyerfx_data set raterk=case v_totalamtrk when 0 then 0 else round(amountrk/v_totalamtrk*100,1) end ";
		qry += "\n      ,ratexs=case v_totalamtxs when 0 then 0 else round(amountxs/v_totalamtxs*100,1) end ";
		qry += "\n      ,ratewc=case amountrk when 0 then 0 else round(amountxs/amountrk*100,1) end ";
		qry += "\n    where epid=" + Userid + "; ";

		qry += "\n    select v_totalamtqc,v_totalamtrk,v_totalcurrrk,v_totalamtxs,v_totalcurrxs,v_totalamtjc, case v_totalamtrk when 0 then 0 else round(v_totalamtxs/v_totalamtrk*100,1) end ";
		qry += "\n    into :amountqc,:amountrk,:currrk,:amountxs,:currxs,:amountjc,:ratewc from dual; ";
		qry += "\n end; ";
//System.out.println(qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("amountqc", new ProdParam(Types.FLOAT));
		param.put("amountrk", new ProdParam(Types.FLOAT));
		param.put("currrk", new ProdParam(Types.FLOAT));
		param.put("amountxs", new ProdParam(Types.FLOAT));
		param.put("currxs", new ProdParam(Types.FLOAT));
		param.put("amountjc", new ProdParam(Types.FLOAT));
		param.put("ratewc", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount =
		// Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr =
		// Float.parseFloat(param.get("curr").getParamvalue().toString());
		// String Noteno = param.get("Noteno").getParamvalue().toString();
		errmess = "\"msg\":\"操作成功！\",\"TOTALAMTQC\":\"" + param.get("amountqc").getParamvalue() + "\"" //
				+ ",\"TOTALAMTRK\":\"" + param.get("amountrk").getParamvalue() + "\"" //
				+ ",\"TOTALCURRRK\":\"" + param.get("currrk").getParamvalue() + "\"" //
				+ ",\"TOTALAMTXS\":\"" + param.get("amountxs").getParamvalue() + "\"" //
				+ ",\"TOTALCURRXS\":\"" + param.get("currxs").getParamvalue() + "\"" //
				+ ",\"TOTALAMTJC\":\"" + param.get("amountjc").getParamvalue() + "\"" //
				+ ",\"RATEWC\":\"" + param.get("ratewc").getParamvalue() + "\"" //
				+ ",\"CURRDATETIME\":\"" + Maxdate + "\""

				+ ",\"warning\":\"" + mdv.getErrmess() + "\"";
		return 1;
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM v_warebuyerfx_data a ");
		strSql.append("\n left outer join accreg b on a.accid=b.accid");
		strSql.append("\n left outer join customer c on a.custid=c.custid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 分页查询经销商进销比明细
	public Table GetTable(QueryParam qp, int hzfs) {
		String qry = "";
//		String sort = "";
		if (hzfs == 0) // 按货号汇总
		{
			qry = " select a.wareid,b.wareno,b.warename,b.units,sum(a.amountrk) as amountrk,sum(a.amountxs) as amountxs,sum(a.amountjc) as amountjc";
			qry += " , case sum(a.amountrk) when 0 then 0 else round(sum(a.amountxs)/sum(a.amountrk)*100,1) end as ratewc ";
			qry += " ,sum(a.currrk) as currrk,sum(a.currxs) as currxs,min(a.id) as id";
			qry += " ,sum(a.amountqc) as amountqc,sum(a.currqc) as currqc";
			qry += " FROM v_buyerwarefx_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.wareid,b.wareno,b.warename,b.units";
//			sort = "wareno,id";
		} else if (hzfs == 1) // 按货号+颜色汇总
		{
			qry = " select a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname,sum(a.amountrk) as amountrk,sum(a.amountxs) as amountxs,sum(a.amountjc) as amountjc";// ,round(sum(a.amountxs)/sum(a.amountrk),4)
			qry += " , case sum(a.amountrk) when 0 then 0 else round(sum(a.amountxs)/sum(a.amountrk)*100,1) end as ratewc ";
			qry += " ,sum(a.currrk) as currrk,sum(a.currxs) as currxs,min(a.id) as id";
			qry += " ,sum(a.amountqc) as amountqc,sum(a.currqc) as currqc";
			qry += " FROM v_buyerwarefx_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname";
//			sort = "wareno,colorid,id";
		} else if (hzfs == 2) // 按货号+颜色+尺码汇总
		{
			qry = " select a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname,a.sizeid,d.sizename,d.sizeno,a.amountrk,a.amountxs,a.amountjc";
			qry += " , case a.amountrk when 0 then 0 else round(a.amountxs/a.amountrk*100,1) end as ratewc ";
			qry += " ,a.currrk,a.currxs,a.id";
			qry += " ,a.amountqc,a.currqc";
			qry += " FROM v_buyerwarefx_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " left outer join colorcode c on a.colorid=c.colorid";
			qry += " left outer join sizecode d on a.sizeid=d.sizeid";
			qry += " where a.epid=" + Userid;
//			sort = "wareno,colorid,sizeno,id";
		}
//		qp.setSortString(sort);
		qp.setQueryString(qry);
		return DbHelperSQL.GetTable(qp);
	}

}
