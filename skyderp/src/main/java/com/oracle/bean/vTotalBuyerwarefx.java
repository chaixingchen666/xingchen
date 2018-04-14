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
public class vTotalBuyerwarefx {
	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd HH:mm:ss,------------必传
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
		qry += "\n      v_epid number;  ";
		qry += "\n      v_accid  number;  ";
		qry += "\n      v_totalamtqc number;  "; // --14
		qry += "\n      v_totalamtrk number;  "; // --14
		qry += "\n      v_totalamtxs number;  "; // --15
		qry += "\n      v_totalamtjc number;  "; // --15
		qry += "\n      v_totalcurrrk number;  "; // --14
		qry += "\n      v_totalcurrxs number;  "; // --15
		qry += "\n begin  ";
		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n         select ROWID from v_buyerwarefx_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_buyerwarefx_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_buyerwarefx_data (id,epid,wareid,colorid,sizeid,amountqc,amountrk,currrk,amountxs,currxs,amountjc,ratewc) ";
		qry += "\n    select v_buyerwarefx_data_id.nextval," + Userid + ",wareid,colorid,sizeid,amountqc,amountrk,currrk,amountxs,currxs,amountqc+amountrk-amountxs,0 from ( ";
		qry += "\n    select wareid,colorid,sizeid,sum(amountqc) as amountqc,sum(amountrk) as amountrk,sum(currrk) as currrk,sum(amountxs) as amountxs ,sum(currxs) as currxs from ( ";
		// 期初

		qry += "\n    select d1.wareid,e1.colorid,f1.sizeid,sum(a.amount) as amountqc,0 as amountrk,0 as currrk,0 as amountxs,0 as currxs";
		qry += "\n    from waresum a ";
		qry += "\n    join warecode d on a.wareid=d.wareid ";
		qry += "\n    join warecode d1 on d.wareid1=d1.wareid and d.accid1=d1.accid   ";
		qry += "\n    join colorcode e on a.colorid=e.colorid ";
		qry += "\n    join colorcode e1 on e.colorid1=e1.colorid and e.accid1=e1.accid ";
		qry += "\n    join sizecode f on a.sizeid=f.sizeid ";
		qry += "\n    join sizecode f1 on f.sizeid1=f1.sizeid and f.accid1=f1.accid ";
		qry += "\n    where a.daystr='" + mindate0 + "' and d1.accid=" + Accid + " and e1.accid=" + Accid + " and f1.accid=" + Accid + "";
		qry += "\n    and exists (select 1 from accconnect a1 where a1.sellaccid=" + Accid + " and a1.buyaccid=d.accid) ";
		if (Buyeraccid > 0) {
			qry += "\n  and d.accid=" + Buyeraccid;
		}

		if (Wareid > 0) {
			qry += "\n  and d1.wareid=" + Wareid;
		} else {
			if (Wareno.length() > 0) {
				qry += "  and d1.wareno like '%" + Wareno.toUpperCase() + "%'";
			}
			;
			if (Warename.length() > 0)
				qry += "   and d1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=d1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and d1.typeid=" + Typeid;
			}
		if (Prodyear.length() > 0) {
			qry += "\n  and d1.prodyear like '%" + Prodyear + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "\n  and d1.seasonname like '%" + Seasonname + "%'";
		}

		if (Colorid > 0) {
			qry += "\n  and e1.colorid=" + Colorid;
		}

		if (Sizeid > 0) {
			qry += "\n  and f1.sizeid=" + Sizeid;
		}

		qry += "\n    group by d1.wareid,e1.colorid,f1.sizeid";
		qry += "\n    union all";
		// 入库
		qry += "\n    select /*+ index (a IX1_WAREINH) */ d1.wareid,e1.colorid,f1.sizeid,0 as amountqc";
		qry += "\n    ,sum(case a.ntid when 0 then b.amount else -b.amount end) as amountrk";
		qry += "\n    ,sum(case a.ntid when 0 then b.curr else -b.curr end) as currrk";
		qry += "\n    ,0 as amountxs ,0 as currxs ";
		qry += "\n    from wareinh a ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    join warecode d on b.wareid=d.wareid ";
		qry += "\n    join warecode d1 on d.wareid1=d1.wareid and d.accid1=d1.accid ";

		qry += "\n    join colorcode e on b.colorid=e.colorid ";
		qry += "\n    join colorcode e1 on e.colorid1=e1.colorid and e.accid1=e1.accid ";

		qry += "\n    join sizecode f on b.sizeid=f.sizeid ";
		qry += "\n    join sizecode f1 on f.sizeid1=f1.sizeid and f.accid1=f1.accid ";

		qry += "\n    where a.statetag=1 and d1.accid=" + Accid + " and e1.accid=" + Accid + " and f1.accid=" + Accid;
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and exists (select 1 from accconnect a1 where a1.sellaccid=" + Accid + " and a1.buyaccid=a.accid) ";
		if (Buyeraccid > 0) {
			qry += "\n  and a.accid=" + Buyeraccid;
		}

		if (Wareid > 0) {
			qry += "\n  and d1.wareid=" + Wareid;
		} else {
			if (Wareno.length() > 0) {
				qry += "\n  and d1.wareno like '%" + Wareno.toUpperCase() + "%'";
			}

			if (Warename.length() > 0)
				qry += "\n   and d1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=d1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and d1.typeid=" + Typeid;
			}

		if (Prodyear.length() > 0) {
			qry += "\n  and d1.prodyear like '%" + Prodyear + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "\n  and d1.seasonname like '%" + Seasonname + "%'";
		}

		if (Colorid > 0) {
			qry += "\n  and e1.colorid=" + Colorid;
		}

		if (Sizeid > 0) {
			qry += "  and f1.sizeid=" + Sizeid;
		}

		qry += "\n    group by d1.wareid,e1.colorid,f1.sizeid ";
		//批发,退货，店铺零售
		qry += "\n    union all ";
		qry += "\n    select /*+ index (a IX1_WAREOUTH) */ d1.wareid,e1.colorid,f1.sizeid,0 as amountqc, 0 as amountrk,0 as currrk";
		qry += "\n    ,sum(case a.ntid when 2 then -b.amount else b.amount end) as amountxs  ";
		qry += "\n    ,sum(case a.ntid when 2 then -b.curr else b.curr end) as currxs  ";
		qry += "\n    from wareouth a ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    join warecode d on b.wareid=d.wareid ";
		qry += "\n    join warecode d1 on d.wareid1=d1.wareid and d.accid1=d1.accid ";

		qry += "\n    join colorcode e on b.colorid=e.colorid ";
		qry += "\n    join colorcode e1 on e.colorid1=e1.colorid and e.accid1=e1.accid ";

		qry += "\n    join sizecode f on b.sizeid=f.sizeid ";
		qry += "\n    join sizecode f1 on f.sizeid1=f1.sizeid and f.accid1=f1.accid ";

		qry += "\n    where a.statetag=1 and d1.accid=" + Accid + " and e1.accid=" + Accid + " and f1.accid=" + Accid;
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and exists (select 1 from accconnect a1 where a1.sellaccid=" + Accid + " and a1.buyaccid=a.accid) ";
		if (Buyeraccid > 0) {
			qry += "  and a.accid=" + Buyeraccid;
		}

		if (Wareid > 0) {
			qry += "  and d1.wareid=" + Wareid;
		} else {
			if (Wareno.length() > 0) {
				qry += "  and d1.wareno like '%" + Wareno.toUpperCase() + "%'";
			}

			if (Warename.length() > 0)
				qry += "   and d1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=d1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and d1.typeid=" + Typeid;
			}

		if (Prodyear.length() > 0) {
			qry += "\n  and d1.prodyear like '%" + Prodyear + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "\n  and d1.seasonname like '%" + Seasonname + "%'";
		}

		if (Colorid > 0) {
			qry += "  and e1.colorid=" + Colorid;
		}

		if (Sizeid > 0) {
			qry += "  and f1.sizeid=" + Sizeid;
		}

		// if (typeid > 0) { qry += " and d.typeid=" + typeid; };
		qry += "\n    group by d1.wareid,e1.colorid,f1.sizeid ";

		
		//商场零售
		qry += "\n    union all ";
		qry += "\n    select /*+ index (a IX1_SHOPSALEH) */ d1.wareid,e1.colorid,f1.sizeid,0 as amountqc, 0 as amountrk,0 as currrk";
		qry += "\n    ,sum(b.amount) as amountxs  ";
		qry += "\n    ,sum(b.curr) as currxs  ";
		qry += "\n    from shopsaleh a ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    join warecode d on b.wareid=d.wareid ";
		qry += "\n    join warecode d1 on d.wareid1=d1.wareid and d.accid1=d1.accid ";

		qry += "\n    join colorcode e on b.colorid=e.colorid ";
		qry += "\n    join colorcode e1 on e.colorid1=e1.colorid and e.accid1=e1.accid ";

		qry += "\n    join sizecode f on b.sizeid=f.sizeid ";
		qry += "\n    join sizecode f1 on f.sizeid1=f1.sizeid and f.accid1=f1.accid ";

		qry += "\n    where a.statetag=1 and d1.accid=" + Accid + " and e1.accid=" + Accid + " and f1.accid=" + Accid;
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and exists (select 1 from accconnect a1 where a1.sellaccid=" + Accid + " and a1.buyaccid=a.accid) ";
		if (Buyeraccid > 0) {
			qry += "  and a.accid=" + Buyeraccid;
		}

		if (Wareid > 0) {
			qry += "  and d1.wareid=" + Wareid;
		} else {
			if (Wareno.length() > 0) {
				qry += "  and d1.wareno like '%" + Wareno.toUpperCase() + "%'";
			}

			if (Warename.length() > 0)
				qry += "   and d1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=d1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and d1.typeid=" + Typeid;
			}

		if (Prodyear.length() > 0) {
			qry += "\n  and d1.prodyear like '%" + Prodyear + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "\n  and d1.seasonname like '%" + Seasonname + "%'";
		}

		if (Colorid > 0) {
			qry += "  and e1.colorid=" + Colorid;
		}

		if (Sizeid > 0) {
			qry += "  and f1.sizeid=" + Sizeid;
		}

		// if (typeid > 0) { qry += " and d.typeid=" + typeid; };
		qry += "\n    group by d1.wareid,e1.colorid,f1.sizeid ";

		qry += "\n    ) group by wareid,colorid,sizeid );  ";

		// qry += " delete from v_buyerwarefx_data where epid="+userid+"; ";
		// qry += " insert into v_buyerwarefx_data (id,epid,wareid,colorid,sizeid,amountrk,amountxs,amountjc,ratewc) ";
		// qry += " select v_buyerwarefx_data_id.nextval,"+userid+",wareid,colorid,sizeid,amountrk,amountxs,amountrk-amountxs,0 from v_buyerwarefx_temp; ";

		qry += "\n    select sum(amountrk),sum(amountxs) into v_totalamtrk,v_totalamtxs from v_buyerwarefx_data where epid=" + Userid + "; ";
		// qry += " update v_buyerwarefx_data set ratewc=case amountrk when 0 then 0 else round(amountxs/amountrk*100,1) end ";
		// qry += " where epid="+userid+"; ";

		// qry += " select nvl(sum(amountrk),0),nvl(sum(amountxs),0),nvl(sum(amountjc),0) into :amountrk,:amountxs,:amountjc from v_buyerwarefx_data where epid="+userid+"; ";

		qry += "\n  select nvl(sum(amountqc),0),nvl(sum(amountrk),0),nvl(sum(currrk),0),nvl(sum(amountxs),0),nvl(sum(currxs),0),nvl(sum(amountjc),0) ";
		qry += "\n  into v_totalamtqc,v_totalamtrk,v_totalcurrrk,v_totalamtxs,v_totalcurrxs,v_totalamtjc from v_buyerwarefx_data where epid=" + Userid + ";  ";

		qry += "\n  select v_totalamtqc,v_totalamtrk,v_totalcurrrk,v_totalamtxs,v_totalcurrxs,v_totalamtjc, case v_totalamtrk when 0 then 0 else round(v_totalamtxs/v_totalamtrk*100,1) end ";
		qry += "\n  into :amountqc,:amountrk,:currrk,:amountxs,:currxs,:amountjc,:ratewc from dual; ";

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
		if (DbHelperSQL.ExecuteProc(qry, param,1800) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
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

}
