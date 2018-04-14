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
import com.netdata.db.QueryParam;

/**
 * @author Administrator
 *
 */
public class vTotalxsthhz {
	private Long Accid = (long) 0;
	private Long Userid = (long) 0;
	private String Nowdate = "";
	private String Accbegindate = "";
	private Integer Qxbj = 0;
	private String Maxdate;
	private String Mindate;
	private String Houseidlist = "";
	private String Custidlist = "";
	private Long Houseid = (long) 0;
	private Long Custid = (long) 0;

	private Long Wareid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;

	private Long Typeid = (long) -1;
	private Long Brandid = (long) -1;
	private Long Dptid = (long) -1;
	private Long Provid = (long) -1;

	private String Wareno = "";
	private String Warename = "";
	private String Seasonname = "";
	private String Prodyear = "";

	private Integer Maxday = 0;
	private String errmess = "";

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

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public int doTotal() {
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
		// vvvvvvvvvvvvvvvvvvvvvvvvvv String mindate0 = Func.DateToStr(Func.getPrevDay(Mindate));

		if (Func.isNull(Houseidlist) && Houseid > 0)
			Houseidlist = Houseid.toString();

		if (Func.isNull(Custidlist) && Custid > 0)
			Custidlist = Custid.toString();
		String qry = "declare ";
		qry += "\n    v_epid  number; ";
		qry += "\n    v_accid  number;  ";
		qry += "\n    v_mindate  varchar2(10);   ";
		qry += "\n    v_maxdate  varchar2(10);   ";
		// --v_ntid number; --业务类型 0=出货 1=退货 2=所有
		// --v_saleid number; --销售类型:0=零售, 其它=销售类型
		// v_xsid in number, --销售方式:0=零售 1=经销 2=所有
		qry += "\n begin ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "         select ROWID from v_xsthhz_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_xsthhz_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_xsthhz_data (id,epid,custid,houseid,wareid,amount0,curr0,amount1,curr1) ";
		qry += "\n    select v_xsthhz_data_id.nextval," + Userid + ",custid,houseid,wareid,amount0,curr0,amount1,curr1 from ( ";
		qry += "\n    select custid,houseid,wareid,sum(amount0) as amount0,sum(curr0) as curr0,sum(amount1) as amount1,sum(curr1) as curr1 from (";
		// 批发
		qry += "\n    select a.custid,a.houseid,b.wareid,sum(b.amount) as amount0,sum(b.curr) as curr0,0 as amount1,0 as curr1 ";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where  a.statetag=1 and a.ntid=1 and a.accid=" + Accid + "   ";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + userid + ") ) ";
		}

		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		// if (dptid >= 0) qry += "\n and a.dptid=" + dptid;

		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);

		if (Dptid >= 0)
			qry += "\n   and a.dptid=" + Dptid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (!Func.isNull(Wareno))
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Func.isNull(Warename))
			qry += "   and c.warename like '%" + Warename + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (Brandid >= 0)
			qry += "\n    and c.brandid=" + Brandid;
		if (Provid >= 0)
			qry += "\n    and c.provid=" + Provid;
		if (!Func.isNull(Seasonname))
			qry += "\n   and c.seasonname='" + Seasonname + "'";
		if (!Func.isNull(Prodyear))
			qry += "\n   and c.prodyear='" + Prodyear + "'";

		qry += "\n   group by a.custid,a.houseid,b.wareid ";
		qry += "\n   union all";
		// 退货
		qry += "\n   select a.custid,a.houseid,b.wareid,0 as amount0,0 as curr0,sum(b.amount) as amount1,sum(b.curr) as curr1 ";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where  a.statetag=1 and a.ntid=2 and a.accid=" + Accid + "   ";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			// qry += "\n and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + userid + ") ) ";
		}

		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		// if (dptid >= 0) qry += "\n and a.dptid=" + dptid;
		if (!Func.isNull(Custidlist))
			qry += "  and " + Func.getCondition("a.custid", Custidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		if (Dptid >= 0)
			qry += "\n   and a.dptid=" + Dptid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (!Func.isNull(Wareno))
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Func.isNull(Warename))
			qry += "   and c.warename like '%" + Warename + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (Brandid >= 0)
			qry += "\n    and c.brandid=" + Brandid;
		if (Provid >= 0)
			qry += "\n    and c.provid=" + Provid;
		if (!Func.isNull(Seasonname))
			qry += "\n   and c.seasonname='" + Seasonname + "'";
		if (!Func.isNull(Prodyear))
			qry += "\n   and c.prodyear='" + Prodyear + "'";

		qry += "\n   group by a.custid,a.houseid,b.wareid ";

		qry += "\n  ) group by  custid,houseid,wareid)  ;";
		qry += "\n    commit;   ";
		qry += "\n    select nvl(sum(amount0),0),nvl(sum(curr0),0),nvl(sum(amount1),0),nvl(sum(curr1),0) ";
		qry += "\n    into :totalamt0,:totalcurr0,:totalamt1,:totalcurr1 from v_xsthhz_data where epid=" + Userid + "; ";
		qry += "\n end; ";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt0", new ProdParam(Types.FLOAT));
		param.put("totalcurr0", new ProdParam(Types.FLOAT));
		param.put("totalamt1", new ProdParam(Types.FLOAT));
		param.put("totalcurr1", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		Float curr0 = Float.parseFloat(param.get("totalcurr0").getParamvalue().toString());
		Float curr1 = Float.parseFloat(param.get("totalcurr1").getParamvalue().toString());
		Float thrate = (float) 0;
		if (curr0 != 0)
			thrate = Func.getRound(curr1 / curr0 * 100, 2);
		errmess = "\"msg\":\"操作成功！\",\"TOTALAMT0\":\"" + param.get("totalamt0").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURR0\":\"" + curr0 + "\""//
				+ ",\"TOTALAMT1\":\"" + param.get("totalamt1").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURR1\":\"" + curr1 + "\""//
				+ ",\"THRATE\":\"" + thrate + "\""//
				+ ",\"WARNING\":\"" + warning + "\""//
		;

		return 1;

	}

	public Table GetTable(QueryParam qp, int hzfs) {
		String qry = "";
		if (hzfs == 0) // 按客户汇总
		{
			qry = " select a.custid,b.custname,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",case sum(a.curr0) when 0 then 0 else round(sum(a.curr1)/sum(a.curr0)*100,2) end as thrate";
			qry += " FROM v_xsthhz_data a";
			qry += " left outer join customer b on a.custid=b.custid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.custid,b.custname";

		} else if (hzfs == 1) // 按店铺汇总
		{
			qry = " select a.houseid,b.housename,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",case sum(a.curr0) when 0 then 0 else round(sum(a.curr1)/sum(a.curr0)*100,2) end as thrate";
			qry += " FROM v_xsthhz_data a";
			qry += " left outer join warehouse b on a.houseid=b.houseid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.houseid,b.housename";
		} else if (hzfs == 2) // 按商品汇总
		{
			qry = " select a.wareid,b.warename,b.wareno,b.units,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += ",case sum(a.curr0) when 0 then 0 else round(sum(a.curr1)/sum(a.curr0)*100,2) end as thrate";
			qry += " FROM v_xsthhz_data a";
			qry += " left outer join warecode b on a.wareid=b.wareid";
			qry += " where a.epid=" + Userid;
			qry += " group by a.wareid,b.warename,b.wareno,b.units";
		}
		qp.setQueryString(qry);
		return DbHelperSQL.GetTable(qp);
	}
}
