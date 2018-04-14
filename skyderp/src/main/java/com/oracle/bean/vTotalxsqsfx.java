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
public class vTotalxsqsfx {
	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd HH:mm:ss,------------必传
	private Long Accid = (long) 0;

	private Long Userid = (long) 0;
	private Long Wareid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;
	private Long Dptid = (long) -1;
	private Long Areaid = (long) -1;
	private Long Brandid = (long) -1;
	private Long Typeid = (long) -1;
	private String Seasonname = "";
	private String Warename = "";
	private String Wareno = "";
	private String Prodyear = "";
	private Long Houseid = (long) 0;
	private Long Custid = (long) 0;
	private Integer Hzfs = 0;// 0=本日时点，1=本月时点，2=按日汇总，3=按周汇总，4=按月汇总
	private Integer Xsid = 2;// hzfs:0=品牌， 1=类型， 2=季节，3=店铺，4=客户，5=销售类型
	private Integer Housecostbj = 0;

	private String Mindate = "";
	private String Maxdate = "";
	private String Mintime = "";
	private String Maxtime = "";
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
		// Hzfs : 0=本日时点，1=本月时点，2=按日汇总，3=按周汇总，4=按月汇总
		Maxdate = Nowdate;
		if (Hzfs == 3 || Hzfs == 4) // 最小查询日期自动改为1.1
		{
			Mindate = Func.DateToStr(Func.getFirstDayOfYear(Func.StrToDate(Nowdate)));
		} else if (Hzfs == 0) // 本日时点
		{
			// maxdate = nowdate.ToString("yyyy-MM-dd");
			Mindate = Nowdate;
		} else if (Hzfs == 1 || Hzfs == 2) // 本月时点,按日汇总
		{
			Mindate = Func.DateToStr(Func.getFirstDayOfMonth(Func.StrToDate(Nowdate)));
		}

		// 校验开始查询日期
		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, 0);
		Mindate = mdv.getMindate();
		// String warning = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期

		Maxdate += " 23:59:59";
		Mindate += " 00:00:00";
		String qry = "declare ";
		qry += "     v_totalamt number;  "; // --14
		qry += "     v_totalcurr number;  "; // --15
		qry += "     v_retailcurr number;  "; // --15
		qry += "     v_fixedcurr number;  "; // --15
		qry += "     v_mlcurr number;  "; // --15
		qry += "     v_checkcurr number;  "; // --15
		qry += "     v_maxnum int; ";
		qry += "     v_minnum int;";
		qry += "     v_count int;";
		qry += "     v_i int;";
		qry += "     v_datestr varchar2(20);";
		qry += "     v_mintimestr varchar2(10);";
		qry += "     v_maxtimestr varchar2(10);";

		qry += "\n begin  ";
		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_xsqsfx_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_xsqsfx_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_xsqsfx_data(id,epid,datestr,amount,curr,skc,retailcurr,fixedcurr,mlcurr,checkcurr)";
		qry += "\n    select v_xsqsfx_data_id.nextval," + Userid + ",datestr,amount,curr,skc,retailcurr,fixedcurr,curr-fixedcurr,checkcurr from (";
		// 零售，批发
		qry += "\n    select datestr,sum(amount) as amount,sum(curr) as curr,sum(retailcurr) as retailcurr,sum(fixedcurr) as fixedcurr,sum(checkcurr) as checkcurr,count(distinct '^'||wareid||'^'||colorid||'^') as skc from (";
		// hzfs:0=本日时点，1=本月时点，2=按日汇总，3=按周汇总，4=按月汇总 0=月1=周2=日3=时

		if (Hzfs == 4) {
			qry += "\n    select to_char(a.notedate,'mm')||'月' as datestr";
		} // 月
		else if (Hzfs == 3) {
			qry += "\n    select  to_char(notedate,'IW')||'周('||to_char(TRUNC(a.notedate,'IW'),'mmdd')||'-'||to_char(TRUNC(a.notedate,'IW')+ 6,'mmdd')||')' as datestr";
		} // 周
		else if (Hzfs == 2) {
			qry += "\n    select to_char(a.notedate,'mm')||'月'||to_char(a.notedate,'dd')||'日' as datestr";
		} // 日
		else {
			qry += "\n    select to_char(a.notedate,'hh24')||'点' as datestr";
		}

		qry += "\n    ,b.wareid,b.colorid,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount";
		qry += "\n    ,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr  ";
		qry += "\n    ,sum(case a.ntid when 2 then -b.amount else b.amount end*c.retailsale) as retailcurr";
		qry += "\n    ,sum(case a.ntid when 2 then -b.amount else b.amount end*d.costprice) as fixedcurr";
		qry += "\n    ,sum(case a.ntid when 2 then -b.amount else b.amount end*d.costprice1) as checkcurr";

		qry += "\n    from wareouth a   ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid  ";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (Housecostbj == 1)
			qry += "  and a.houseid=d.houseid";
		else
			qry += "  and d.houseid=0";
		qry += "\n    where  a.statetag=1  and a.accid=" + Accid + " ";
		if (Xsid == 0) {
			qry += "  and a.ntid=0";
		} else // 零售
		if (Xsid == 1) {
			qry += "  and (a.ntid=1 or a.ntid=2)";
		} // 经销
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " )  ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") )  ";
		}

		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (Custid > 0) {
			qry += "  and a.custid=" + Custid;
		}

		if (Houseid > 0) {
			qry += "  and a.houseid=" + Houseid;
		}

		if (Wareid > 0) {
			qry += "  and b.wareid=" + Wareid;
		}

		if (Brandid >= 0) {
			qry += "  and c.brandid=" + Brandid;
		}

		if (Areaid >= 0) {
			qry += "  and c.areaid=" + Areaid;
		}

		if (Dptid >= 0) {
			qry += "  and a.dptid=" + Dptid;
		}

		// if (typeid > 0) { qry += " and c.typeid=" + typeid; };
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and c.typeid=" + Typeid;
			}

		if (Wareno.length() > 0) {
			qry += "  and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		}

		if (Warename.length() > 0) {
			qry += "  and c.warename like '%" + Warename + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "  and c.seasonname = '" + Seasonname + "'";
		}

		if (Prodyear.length() > 0) {
			qry += "  and c.prodyear = '" + Prodyear + "'";
		}

		if (Hzfs == 4) {
			qry += "\n     group by to_char(a.notedate,'mm')";
		} else // 月
		if (Hzfs == 3) {
			qry += "\n     group by to_char(notedate,'IW')||'周('||to_char(TRUNC(a.notedate,'IW'),'mmdd')||'-'||to_char(TRUNC(a.notedate,'IW')+ 6,'mmdd')||')'";
		} else // 周
		if (Hzfs == 2) {
			qry += "\n      group by to_char(a.notedate,'mm')||'月'||to_char(a.notedate,'dd')||'日'";
		} // 日
		else
			qry += "\n   group by  to_char(a.notedate,'hh24') ";

		qry += ",b.wareid,b.colorid  ";
		qry += "\n  union all";
		// 商场零售

		if (Hzfs == 4) {
			qry += "\n    select to_char(a.notedate,'mm')||'月' as datestr";
		} // 月
		else if (Hzfs == 3) {
			qry += "\n    select  to_char(notedate,'IW')||'周('||to_char(TRUNC(a.notedate,'IW'),'mmdd')||'-'||to_char(TRUNC(a.notedate,'IW')+ 6,'mmdd')||')' as datestr";
		} // 周
		else if (Hzfs == 2) {
			qry += "\n    select to_char(a.notedate,'mm')||'月'||to_char(a.notedate,'dd')||'日' as datestr";
		} // 日
		else {
			qry += "\n    select to_char(a.notedate,'hh24')||'点' as datestr";
		}

		qry += "\n    ,b.wareid,b.colorid,sum(b.amount) as amount";
		qry += "\n    ,sum( b.curr) as curr  ";
		qry += "\n    ,sum(b.amount*c.retailsale) as retailcurr";
		qry += "\n    ,sum(b.amount*d.costprice) as fixedcurr";
		qry += "\n    ,sum(b.amount*d.costprice1) as checkcurr";
		qry += "\n    from shopsaleh a   ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid  ";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd')";

		if (Housecostbj == 1)
			qry += "  and a.houseid=d.houseid";
		else
			qry += "  and d.houseid=0";
		qry += "\n    where  a.statetag=1  and a.accid=" + Accid + " ";
		if (Xsid == 0) {
			qry += "  and a.ntid=0";
		} else // 零售
		if (Xsid == 1) {
			qry += "  and (a.ntid=1 or a.ntid=2)";
		} // 经销
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " )  ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") )  ";
		}

		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (Custid > 0) {
			qry += "  and a.custid=" + Custid;
		}
		;
		if (Houseid > 0) {
			qry += "  and a.houseid=" + Houseid;
		}

		if (Wareid > 0) {
			qry += "  and b.wareid=" + Wareid;
		}

		if (Brandid >= 0) {
			qry += "  and c.brandid=" + Brandid;
		}

		if (Areaid >= 0) {
			qry += "  and c.areaid=" + Areaid;
		}

		if (Dptid >= 0) {
			qry += "  and a.dptid=" + Dptid;
		}

		// if (typeid > 0) { qry += " and c.typeid=" + typeid; };
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and c.typeid=" + Typeid;
			}

		if (Wareno.length() > 0) {
			qry += "  and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		}

		if (Warename.length() > 0) {
			qry += "  and c.warename like '%" + Warename + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "  and c.seasonname = '" + Seasonname + "'";
		}

		if (Prodyear.length() > 0) {
			qry += "  and c.prodyear = '" + Prodyear + "'";
		}

		if (Hzfs == 4) {
			qry += "\n     group by to_char(a.notedate,'mm')";
		} else // 月
		if (Hzfs == 3) {
			qry += "\n     group by to_char(notedate,'IW')||'周('||to_char(TRUNC(a.notedate,'IW'),'mmdd')||'-'||to_char(TRUNC(a.notedate,'IW')+ 6,'mmdd')||')'";
		} else // 周
		if (Hzfs == 2) {
			qry += "\n      group by to_char(a.notedate,'mm')||'月'||to_char(a.notedate,'dd')||'日'";
		} // 日
		else
			qry += "\n   group by  to_char(a.notedate,'hh24') ";
		qry += ",b.wareid,b.colorid  ";

		qry += "\n ) group by datestr);";

		if (Hzfs == 4) // 月
		{
			qry += "\n   v_maxnum:=to_number(to_char(to_date('" + Func.subString(Maxdate, 1, 10) + "','yyyy-mm-dd'),'mm')); ";
			qry += "\n   v_i:=1;";
			qry += "\n   while v_i<=v_maxnum loop";
			qry += "\n     v_datestr:=trim(to_char(v_i,'00'))||'月';";
			qry += "\n     select count(*) into v_count from v_xsqsfx_data where epid=" + Userid + " and datestr=v_datestr;";
			qry += "\n     if v_count=0 then";
			qry += "\n       insert into v_xsqsfx_data (id,epid,datestr,amount,curr,skc)";
			qry += "\n       values (v_xsqsfx_data_id.nextval," + Userid + ",v_datestr,0,0,0);";
			qry += "\n     end if;";
			qry += "\n     v_i:=v_i+1;";
			qry += "\n   end loop;";

		} else if (Hzfs == 0 || Hzfs == 1) // 时
		{
			qry += "\n   select min(datestr),max(datestr) into v_mintimestr,v_maxtimestr from v_xsqsfx_data where epid=" + Userid + ";";
			qry += "\n   v_maxnum:=to_number(substr(v_maxtimestr,1,2)); ";
			qry += "\n   v_i:=to_number(substr(v_mintimestr,1,2));";
			qry += "\n   while v_i<=v_maxnum loop";
			qry += "\n     v_datestr:=trim(to_char(v_i,'00'))||'点'; ";
			qry += "\n     select count(*) into v_count from v_xsqsfx_data where epid=" + Userid + " and datestr=v_datestr;";
			qry += "\n     if v_count=0 then";
			qry += "\n       insert into v_xsqsfx_data (id,epid,datestr,amount,curr)";
			qry += "\n       values (v_xsqsfx_data_id.nextval," + Userid + ",v_datestr,0,0);";
			qry += "\n     end if;";
			qry += "\n     v_i:=v_i+1;";
			qry += "\n   end loop;";

		}

		qry += "\n   commit;   ";

		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(retailcurr),0),nvl(sum(fixedcurr),0),nvl(sum(mlcurr),0),nvl(sum(checkcurr),0)";
		qry += "\n   into v_totalamt,v_totalcurr,v_retailcurr,v_fixedcurr,v_mlcurr,v_checkcurr from v_xsqsfx_data where epid=" + Userid + ";";

		qry += "\n   update v_xsqsfx_data set ratecur=case v_totalcurr when 0 then 0 else round(curr/v_totalcurr*100,1) end  where epid=" + Userid + ";";

		qry += "\n   select v_totalamt,v_totalcurr,v_retailcurr,v_fixedcurr,v_mlcurr,v_checkcurr ";
		qry += "\n   into :amount,:curr,:retailcurr,:fixedcurr,:mlcurr,:checkcurr from dual; ";
		qry += "\n end; ";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("amount", new ProdParam(Types.FLOAT));
		param.put("curr", new ProdParam(Types.FLOAT));
		param.put("retailcurr", new ProdParam(Types.FLOAT));
		param.put("fixedcurr", new ProdParam(Types.FLOAT));
		param.put("mlcurr", new ProdParam(Types.FLOAT));
		param.put("checkcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
		// String Noteno = param.get("Noteno").getParamvalue().toString();
		errmess = "\"msg\":\"操作成功！\",\"amount\":\"" + param.get("amount").getParamvalue() + "\"" //
				+ ",\"curr\":\"" + param.get("curr").getParamvalue() + "\"" //
				+ ",\"retailcurr\":\"" + param.get("retailcurr").getParamvalue() + "\"" //
				+ ",\"fixedcurr\":\"" + param.get("fixedcurr").getParamvalue() + "\"" //
				+ ",\"mlcurr\":\"" + param.get("mlcurr").getParamvalue() + "\"" //
				+ ",\"checkcurr\":\"" + param.get("checkcurr").getParamvalue() + "\"" //
				+ ",\"warning\":\"" + mdv.getErrmess() + "\"";
		return 1;

	}

	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM v_xsqsfx_data a");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}
}
