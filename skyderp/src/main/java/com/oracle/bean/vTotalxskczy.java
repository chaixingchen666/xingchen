/**
 * 
 */
package com.oracle.bean;

import com.comdot.data.Table;
import com.common.tool.Func;

// import com.common.tool.Func;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONObject;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class vTotalxskczy {
	// private String Fieldlist = ""; // 要显示的项目列表
	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	// private String Calcfield = ""; // 要计算的项目列表

	private Long Userid = (long) 0;
	private Long Accid = (long) 0;
	private String errmess = "";
	private String Mindate = "";
	private String Maxdate = "";
	private String Jhdate = "";  // 累计进货开始日期
	private String Nowdate = "";
	private String Calcdate = "";
	private String Accbegindate = "2000-01-01"; // 套账起用日期
	private String Currdatetime; // 当前查询的系统时间
	private Integer Qxbj = 0;
	private String Houseidlist = "";
	private String Brandidlist = "";
	private String Providlist = "";
	private String Custidlist = "";
	private String Wareno = "";
	private String Warename = "";
	private String Seasonname = "";
	private String Prodyear = "";

	private Long Wareid = (long) 0;
	private Long Houseid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;
	private Long Typeid = (long) -1;

	private Integer Maxday = 0;

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
	}

	public void setWareid(Long wareid) {
		Wareid = wareid;
	}

	public void setColorid(Long colorid) {
		Colorid = colorid;
	}

	public String getErrmess() {
		return errmess;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}
	// public void setCurrdatetime(String currdatetime) {
	// Currdatetime = currdatetime;
	// }

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public int doTotal(int onlyxs) {

		// onlyxs:1=只查有销售的商品

		if (Func.isNull(Mindate))
			Mindate = Nowdate;
		if (Func.isNull(Maxdate))
			Maxdate = Nowdate;

		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		String warning = mdv.getErrmess();

		if (Mindate.compareTo(Accbegindate) < 0)
			Mindate = Accbegindate;
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate;

		if (Mindate.length() <= 10)
			Mindate += " 00:00:00";
		if (Maxdate.length() <= 10)
			Maxdate += " 23:59:59";

		String qry = "declare ";
		qry += "\n    v_epid  number; ";
		qry += "\n    v_accid  number;  ";
		qry += "\n    v_mindate  varchar2(10);   ";
		qry += "\n    v_maxdate  varchar2(10);   ";
		qry += "\n    v_retsql clob;  --nvarchar2(32000);";
		qry += "\n begin ";

		// qry += "\n delete from v_xskczy_data where epid=" + userid + "; ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_xskczy_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_xskczy_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_xskczy_data (id,epid,houseid,wareid,colorid,sizeid,xsamt,xscurr,kcamt) ";
		qry += "\n    select v_xskczy_data_id.nextval," + Userid + ",houseid,wareid,colorid,sizeid,xsamt,xscurr,kcamt from ( ";

		qry += "\n    select houseid ,wareid,colorid,sizeid,nvl(sum(xsamt),0) as xsamt,nvl(sum(xscurr),0) as xscurr,nvl(sum(kcamt),0) as kcamt from (";
		qry += "\n    select houseid ,wareid,colorid,sizeid,0 as xsamt,0 as xscurr,sum(amount) as kcamt ";
		qry += "\n    from v_cxkczy_data where epid=" + Userid;
		qry += "\n    group by houseid ,wareid,colorid,sizeid";
		qry += "\n    union all ";
		// 店铺零售及批发销售
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,sum(case a.ntid when 2 then -b.amount else b.amount end) as xsamt";
		qry += "\n    ,sum(case a.ntid when 2 then -b.curr else b.curr end) as xscurr,0 as kcamt ";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where  a.statetag=1 and a.accid=" + Accid;
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		// --v_ntid in number, --业务类型 0=出货 1=退货 2=所有
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Houseidlist.equals(""))
			qry += "\n  and " + Func.getCondition("a.Houseid", Houseidlist);

		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (!Func.isNull(Wareno))
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Func.isNull(Warename))
			qry += "\n   and c.warename like '%" + Warename + "%'";

		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (!Brandidlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		// if (areaid >= 0) qry += "\n and c.areaid=" + areaid;
		if (!Seasonname.equals(""))
			qry += "\n      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid";
		qry += "\n    union all";
		// 商场零售
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,sum(b.amount) as xsamt";
		qry += "\n    ,sum(b.curr) as xscurr,0 as kcamt ";
		qry += "\n    from shopsaleh a  ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where  a.statetag=1 and a.accid=" + Accid + "   ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += "\n   and " + Func.getCondition("a.Houseid", Houseidlist);

		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (!Func.isNull(Wareno))
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Func.isNull(Warename))
			qry += "\n   and c.warename like '%" + Warename + "%'";

		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (!Brandidlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		// if (areaid >= 0) qry += "\n and c.areaid=" + areaid;
		if (!Seasonname.equals(""))
			qry += "\n      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid";
		qry += "\n  ) group by houseid ,wareid,colorid,sizeid ); ";
		if (onlyxs == 1) { // onlyxs:1=只查有销售的商品
			qry += "\n   delete from v_xskczy_data where epid=" + Userid + " and xsamt=0;";
		}

		qry += "\n   commit;   ";

		qry += "\n   DECLARE ";
		qry += "\n      CURSOR CUR_v_xskczy_data IS ";
		qry += "\n      SELECT DISTINCT a.houseid,b.housename FROM v_xskczy_data a ";
		qry += "\n      left outer join warehouse b on a.houseid=b.houseid where a.epid= " + Userid;
		qry += "\n      ORDER BY a.houseid;   ";
		qry += "\n      v_row CUR_v_xskczy_data%rowtype; ";
		qry += "\n   begin ";
		qry += "\n      v_retsql:='sum(a.xsamt) as xsamthj,sum(a.xscurr) as xscurrhj,sum(a.kcamt) as kcamthj'; ";
		qry += "\n      FOR v_row IN CUR_v_xskczy_data LOOP ";
		qry += "\n          v_retsql:=v_retsql||',sum(decode(a.houseid,'||v_row.houseid||',a.xsamt)) as xsamt'||v_row.houseid; ";
		qry += "\n          v_retsql:=v_retsql||',sum(decode(a.houseid,'||v_row.houseid||',a.xscurr)) as xscurr'||v_row.houseid; ";
		qry += "\n          v_retsql:=v_retsql||',sum(decode(a.houseid,'||v_row.houseid||',a.kcamt)) as kcamt'||v_row.houseid; ";
		qry += "\n      end loop; ";
		qry += "\n   end; ";
		qry += "\n   select v_retsql into :retsql from dual;";

		// 更新库存数据
		qry += "\n end; ";
		// System.out.println(qry);
		// LogUtils.LogDebugWrite("TotalXskczy", qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retsql", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		errmess = "\"msg\":\"操作成功！\",\"sumlist\":\"" + param.get("retsql").getParamvalue().toString() + "\""//
				+ ",\"warning\":\"" + warning + "\"";

		// 各列名称及合计
		qry = "SELECT  a.houseid,b.housename,sum(a.xsamt) as xsamt,sum(a.xscurr) as xscurr,sum(a.kcamt) as kcamt \n FROM v_xskczy_data a";

		qry += "\n left outer join warehouse b on a.houseid=b.houseid where a.epid=" + Userid;
		qry += "\n group BY a.houseid,b.housename  ";
		qry += "\n order by b.housename,a.houseid";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		Float totalxsamt = (float) 0;
		Float totalxscurr = (float) 0;
		Float totalkcamt = (float) 0;

		errmess += ",\"columns\":[";
		// String fh = "";
		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				errmess += ",";
			Float xsamt = Float.parseFloat(tb.getRow(i).get("XSAMT").toString());
			Float xscurr = Float.parseFloat(tb.getRow(i).get("XSCURR").toString());
			Float kcamt = Float.parseFloat(tb.getRow(i).get("KCAMT").toString());
			errmess += "{\"houseid\":" + tb.getRow(i).get("HOUSEID").toString()//
					+ ",\"housename\":\"" + tb.getRow(i).get("HOUSENAME").toString() + "\"" //
					+ ",\"xsamt\":" + xsamt //
					+ ",\"xscurr\":" + xscurr//
					+ ",\"kcamt\":" + kcamt//
					+ "}";
			totalxsamt += xsamt;
			totalxscurr += xscurr;
			totalkcamt += kcamt;
		}
		errmess += "]";
		errmess += ",\"totalxsamt\":" + totalxsamt //
				+ ",\"totalxscurr\":" + totalxscurr //
				+ ",\"totalkcamt\":" + totalkcamt;

		return 1;
	}

	// 分页显示商品库存分布数据
	public Table GetTable(QueryParam qp) {

		Sumlist += ",min(rownum) as keyid";
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";

		String qry = "select " + Grouplist + "," + Sumlist;
		qry += "\n FROM v_xskczy_data a";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		qry += "\n left outer join sizecode d on a.sizeid=d.sizeid";
		qry += "\n left outer join provide e on b.provid=e.provid";
		qry += "\n where a.epid=" + Userid;
		qry += "\n group by " + Func.delGroupOfAs(Grouplist);
		// a.wareid,b.wareno,b.warename,b.units,b.retailsale,a.colorid,c.colorname,a.sizeid,d.sizename,d.sizeno,b.locale,b.imagename0

		qp.setQueryString(qry);
		qp.setSortString(Sortlist);
		// System.out.println(qry);
		// qp.setSumString("nvl(sum(amtkchj),0) as totalkcamt,nvl(sum(amthj),0)
		// as totalamt,nvl(sum(curhj),0) as totalcurr");

		return DbHelperSQL.GetTable(qp);
	}

	// 分页显示商品库存分布数据
	public Table GetTableSpfb(QueryParam qp) {

		// Sumlist += ",min(id) as keyid";
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";

		String qry = "select " + Grouplist + ",sum(a.kcamt) as amount,min(id) as keyid";// +
																						// Sumlist;
		qry += "\n FROM v_xskczy_data a";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		qry += "\n left outer join brand d on b.brandid=d.brandid";
		qry += "\n left outer join provide e on b.provid=e.provid";
		qry += "\n left outer join waretype f on b.typeid=f.typeid";
		qry += "\n where a.epid=" + Userid;
		qry += "\n group by " + Func.delGroupOfAs(Grouplist);
		// a.wareid,b.wareno,b.warename,b.units,b.retailsale,a.colorid,c.colorname,a.sizeid,d.sizename,d.sizeno,b.locale,b.imagename0
		qp.setQueryString(qry);
		qp.setSortString(Sortlist);
		qp.setSumString("nvl(sum(amount),0) as totalamount");
		return DbHelperSQL.GetTable(qp);
	}

	// 分页显示商品库存分布数据
	public Table GetTableSpfb1(QueryParam qp) {

		Sumlist += ",sum(a.kcamt) as kcamt,sum(a.jhamt) as jhamt,sum(a.xsamt) as xsamt,sum(a.xscurr) as xscurr,min(a.id) as keyid";
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";

		String qry = "select a.houseid,b.housename,a.fs";
		qry += "\n ," + Sumlist;
		qry += "\n FROM v_xskczy_data a";
		qry += "\n left outer join warehouse b on a.houseid=b.houseid";
		qry += "\n where a.epid=" + Userid + " and a.fs=0";
		qry += "\n and a.wareid=" + Wareid + " and a.colorid=" + Colorid;
		qry += "\n group by a.houseid,b.housename,a.fs";
		qry += "\n union all";
		qry += "\n select  a.houseid,b.custname as housename,a.fs";//
		qry += "\n ," + Sumlist;																																																																																			// //
		qry += "\n FROM v_xskczy_data a";
		qry += "\n left outer join customer b on a.houseid=b.custid";
		qry += "\n where a.epid=" + Userid + " and a.fs=1";
		qry += "\n and a.wareid=" + Wareid + " and a.colorid=" + Colorid;
		qry += "\n group by a.houseid,b.custname,a.fs";

		//		System.out.println(qry);

		qp.setQueryString(qry);
		qp.setSortString("fs,housename,keyid");
		qp.setSumString("nvl(sum(kcamt),0) as totalkcamt,nvl(sum(jhamt),0) as totaljhamt,nvl(sum(xsamt),0) as totalxsamt,nvl(sum(xscurr),0) as totalxscurr");

		return DbHelperSQL.GetTable(qp);
	}

	public int doTotalSpfb(int cxkh) {

		// cxkh=1 要查询客户库存及销售

		if (Func.isNull(Mindate))
			Mindate = Nowdate;
		if (Func.isNull(Maxdate))
			Maxdate = Nowdate;
		if (Func.isNull(Jhdate))
			Jhdate = Nowdate;

		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		String warning = mdv.getErrmess();

		if (Jhdate.compareTo(Accbegindate) < 0)
			Jhdate = Accbegindate;

		if (Mindate.compareTo(Accbegindate) < 0)
			Mindate = Accbegindate;
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate;

		if (Mindate.length() <= 10)
			Mindate += " 00:00:00";
		if (Maxdate.length() <= 10)
			Maxdate += " 23:59:59";
		if (Jhdate.length() <= 10)
			Jhdate += " 00:00:00";

		String qry = "declare ";
		qry += "\n    v_epid  number; ";
		qry += "\n    v_accid  number;  ";
		qry += "\n    v_mindate  varchar2(10);   ";
		qry += "\n    v_maxdate  varchar2(10);   ";
		qry += "\n    v_retsql clob;  --nvarchar2(32000);";
		qry += "\n begin ";

		// qry += "\n delete from v_xskczy_data where epid=" + userid + "; ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_xskczy_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_xskczy_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		// 店铺的库存，进货，及销售begin
		qry += "\n    insert into v_xskczy_data (id,epid,houseid,fs,wareid,colorid,sizeid,xsamt,xscurr,jhamt,kcamt) ";
		qry += "\n    select v_xskczy_data_id.nextval," + Userid + ",houseid,0,wareid,colorid,sizeid,xsamt,xscurr,jhamt,kcamt from ( ";
		// 店铺库存
		qry += "\n    select houseid ,wareid,colorid,sizeid,nvl(sum(xsamt),0) as xsamt,nvl(sum(xscurr),0) as xscurr,nvl(sum(jhamt),0) as jhamt,nvl(sum(kcamt),0) as kcamt from (";
		qry += "\n    select a.houseid ,a.wareid,a.colorid,a.sizeid,0 as xsamt,0 as xscurr,0 as jhamt,sum(a.amount) as kcamt ";
		qry += "\n    from v_cxkczy_data a ";
		qry += "\n    left outer join warecode c on a.wareid=c.wareid ";
		qry += "\n    where a.epid=" + Userid;
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}

		qry += "\n    group by a.houseid ,a.wareid,a.colorid,a.sizeid";
		qry += "\n    union all ";
		// 店铺零售及批发销售
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,sum(case a.ntid when 2 then -b.amount else b.amount end) as xsamt";
		qry += "\n    ,sum(case a.ntid when 2 then -b.curr else b.curr end) as xscurr,0 as jhamt,0 as kcamt ";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where  a.statetag=1 and a.accid=" + Accid;
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		// --v_ntid in number, --业务类型 0=出货 1=退货 2=所有
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Houseidlist.equals(""))
			qry += "\n  and " + Func.getCondition("a.Houseid", Houseidlist);

		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (!Func.isNull(Wareno))
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Func.isNull(Warename))
			qry += "\n   and c.warename like '%" + Warename + "%'";

		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (!Brandidlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		// if (areaid >= 0) qry += "\n and c.areaid=" + areaid;
		if (!Seasonname.equals(""))
			qry += "\n      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid";
		qry += "\n    union all";
		// 商场零售
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,sum(b.amount) as xsamt";
		qry += "\n    ,sum(b.curr) as xscurr,0 as jhamt,0 as kcamt ";
		qry += "\n    from shopsaleh a  ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where  a.statetag=1 and a.accid=" + Accid + "   ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += "\n   and " + Func.getCondition("a.Houseid", Houseidlist);

		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (!Func.isNull(Wareno))
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Func.isNull(Warename))
			qry += "\n   and c.warename like '%" + Warename + "%'";

		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (!Brandidlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		// if (areaid >= 0) qry += "\n and c.areaid=" + areaid;
		if (!Seasonname.equals(""))
			qry += "\n      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid";
		// 累计进货 --采购
		qry += "\n    union all";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,0 as xsamt";
		qry += "\n    ,0 as xscurr,sum(decode(a.ntid,0,b.amount,-b.amount)) as jhamt,0 as kcamt ";
		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where  a.statetag=1 and a.accid=" + Accid + "   ";
		qry += "\n    and a.notedate>=to_date('" + Jhdate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += "\n   and " + Func.getCondition("a.Houseid", Houseidlist);

		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (!Func.isNull(Wareno))
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Func.isNull(Warename))
			qry += "\n   and c.warename like '%" + Warename + "%'";

		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (!Brandidlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		// if (areaid >= 0) qry += "\n and c.areaid=" + areaid;
		if (!Seasonname.equals(""))
			qry += "\n      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid";

		// 累计进货 --调入（加）
		qry += "\n    union all";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,0 as xsamt";
		qry += "\n    ,0 as xscurr,sum(b.amount) as jhamt,0 as kcamt ";
		qry += "\n    from allotinh a  ";
		qry += "\n    join allotinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where  a.statetag=1 and a.accid=" + Accid + "   ";
		qry += "\n    and a.notedate>=to_date('" + Jhdate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += "\n   and " + Func.getCondition("a.Houseid", Houseidlist);

		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (!Func.isNull(Wareno))
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Func.isNull(Warename))
			qry += "\n   and c.warename like '%" + Warename + "%'";

		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (!Brandidlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		// if (areaid >= 0) qry += "\n and c.areaid=" + areaid;
		if (!Seasonname.equals(""))
			qry += "\n      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid";

		// 累计进货 --调出(减)
		qry += "\n    union all";
		qry += "\n    select a.houseid,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,0 as xsamt";
		qry += "\n    ,0 as xscurr,-sum(b.amount) as jhamt,0 as kcamt ";
		qry += "\n    from allotouth a  ";
		qry += "\n    join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where  a.statetag=1 and a.accid=" + Accid + "   ";
		qry += "\n    and a.notedate>=to_date('" + Jhdate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += "\n   and " + Func.getCondition("a.Houseid", Houseidlist);

		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		if (!Func.isNull(Wareno))
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Func.isNull(Warename))
			qry += "\n   and c.warename like '%" + Warename + "%'";

		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (!Brandidlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Providlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (!Seasonname.equals(""))
			qry += "\n      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.houseid,b.wareid,b.colorid,b.sizeid";

		qry += "\n  ) group by houseid,wareid,colorid,sizeid ); ";
		// 店铺的库存，进货，及销售end;

		// 客户的库存，进货及销售begin
		if (cxkh == 1) {
			qry += "\n    insert into v_xskczy_data (id,epid,houseid,fs,wareid,colorid,sizeid,xsamt,xscurr,jhamt,kcamt) ";
			qry += "\n    select v_xskczy_data_id.nextval," + Userid + ",custid,1,wareid,colorid,sizeid,xsamt,xscurr,jhamt,kcamt from ( ";
			// 店铺库存
			qry += "\n    select custid ,wareid,colorid,sizeid,nvl(sum(xsamt),0) as xsamt,nvl(sum(xscurr),0) as xscurr,nvl(sum(jhamt),0) as jhamt,nvl(sum(kcamt),0) as kcamt from (";
			qry += "\n    select a.custid ,a.wareid,a.colorid,a.sizeid,0 as xsamt,0 as xscurr,0 as jhamt,sum(a.amount) as kcamt ";
			qry += "\n    from v_khkczy_data a ";
			qry += "\n    left outer join warecode c on a.wareid=c.wareid ";
			qry += "\n    where a.epid=" + Userid;
			if (!Custidlist.equals(""))
				qry += "\n  and " + Func.getCondition("a.custid", Custidlist);
			if (Qxbj == 1) // 1 启用权限控制
			{
				//qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and exists (select 1 from employecust x3 where a.custid=x3.custid and x3.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}

			qry += "\n    group by a.custid,a.wareid,a.colorid,a.sizeid";
			qry += "\n    union all ";
			// 客户销售
			qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid";
			qry += "\n    ,sum(b.amount) as xsamt";
			qry += "\n    ,sum(b.curr) as xscurr,0 as jhamt,0 as kcamt ";
			qry += "\n    from custsaleh a  ";
			qry += "\n    join custsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid;
			if (Qxbj == 1) // 1 启用权限控制
			{
				//				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and exists (select 1 from employecust x3 where a.custid=x3.custid and x3.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			// --v_ntid in number, --业务类型 0=出货 1=退货 2=所有
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			// if (!Houseidlist.equals(""))
			// qry += "\n and " + Func.getCondition("a.Houseid", Houseidlist);
			if (!Custidlist.equals(""))
				qry += "\n  and " + Func.getCondition("a.custid", Custidlist);

			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			if (!Func.isNull(Wareno))
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (!Func.isNull(Warename))
				qry += "\n   and c.warename like '%" + Warename + "%'";

			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (!Brandidlist.equals(""))
				qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
			if (!Providlist.equals(""))
				qry += "\n   and " + Func.getCondition("c.provid", Providlist);
			// if (areaid >= 0) qry += "\n and c.areaid=" + areaid;
			if (!Seasonname.equals(""))
				qry += "\n      and c.Seasonname='" + Seasonname + "'";
			if (!Prodyear.equals(""))
				qry += "\n      and c.prodyear='" + Prodyear + "'";
			qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid";
			// 累计进货 --批发出库
			qry += "\n    union all";
			qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid";
			qry += "\n    ,0 as xsamt";
			qry += "\n    ,0 as xscurr,sum(decode(a.ntid,1,b.amount,-b.amount)) as jhamt,0 as kcamt ";
			qry += "\n    from wareouth a  ";
			qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and (a.ntid=1 or a.ntid=2)  ";
			qry += "\n    and a.notedate>=to_date('" + Jhdate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			if (Qxbj == 1) // 1 启用权限控制
			{
				// qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and exists (select 1 from employecust x3 where a.custid=x3.custid and x3.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (!Custidlist.equals(""))
				qry += "\n  and " + Func.getCondition("a.custid", Custidlist);
			// if (!Houseidlist.equals(""))
			// qry += "\n and " + Func.getCondition("a.Houseid", Houseidlist);

			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			if (!Func.isNull(Wareno))
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (!Func.isNull(Warename))
				qry += "\n   and c.warename like '%" + Warename + "%'";

			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and c.typeid=" + Typeid;
				}
			if (!Brandidlist.equals(""))
				qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
			if (!Providlist.equals(""))
				qry += "\n   and " + Func.getCondition("c.provid", Providlist);
			// if (areaid >= 0) qry += "\n and c.areaid=" + areaid;
			if (!Seasonname.equals(""))
				qry += "\n      and c.Seasonname='" + Seasonname + "'";
			if (!Prodyear.equals(""))
				qry += "\n      and c.prodyear='" + Prodyear + "'";
			qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid";

			qry += "\n  ) group by custid,wareid,colorid,sizeid ); ";
		}
		// 客户的库存，进货及销售end

		qry += "\n   commit;   ";

		// qry += "\n DECLARE ";
		// qry += "\n CURSOR CUR_v_xskczy_data IS ";
		// qry += "\n SELECT DISTINCT a.sizeid,b.sizename FROM v_xskczy_data a
		// ";
		// qry += "\n left outer join sizecode b on a.sizeid=b.sizeid where
		// a.epid= " + Userid;
		// qry += "\n ORDER BY a.sizeid; ";
		// qry += "\n v_row CUR_v_xskczy_data%rowtype; ";
		// qry += "\n begin ";
		// qry += "\n v_retsql:='sum(a.xsamt) as xsamthj,sum(a.xscurr) as
		// xscurrhj,sum(a.kcamt) as kcamthj,sum(a.jhamt) as jhamthj'; ";
		// qry += "\n FOR v_row IN CUR_v_xskczy_data LOOP ";
		// qry += "\n
		// v_retsql:=v_retsql||',sum(decode(a.sizeid,'||v_row.sizeid||',a.xsamt))
		// as xsamt'||v_row.sizeid; ";
		//// qry += "\n
		// v_retsql:=v_retsql||',sum(decode(a.houseid,'||v_row.houseid||',a.xscurr))
		// as xscurr'||v_row.houseid; ";
		// qry += "\n
		// v_retsql:=v_retsql||',sum(decode(a.sizeid,'||v_row.sizeid||',a.kcamt))
		// as kcamt'||v_row.sizeid; ";
		// qry += "\n end loop; ";
		// qry += "\n end; ";
		// qry += "\n select v_retsql into :retsql from dual;";
		// qry += "\n select 'aaa' into :retsql from dual;";

		// 更新库存数据
		qry += "\n end; ";

		// System.out.println(qry);
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "\"msg\":\"操作成功！\""//
					+ ",\"warning\":\"" + warning + "\"";
			return 1;
		}
		// LogUtils.LogDebugWrite("TotalXskczy", qry);
		// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// param.put("retsql", new ProdParam(Types.VARCHAR));
		// int ret = DbHelperSQL.ExecuteProc(qry, param);
		// if (ret < 0) {
		// errmess = "操作异常！";
		// return 0;
		// }
		// errmess = "\"sumlist\":\"" +
		// param.get("retsql").getParamvalue().toString() + "\""//
		// + ",\"warning\":\"" + warning + "\"";

		// 各列名称及合计
		// qry = "SELECT a.houseid,b.housename,sum(a.xsamt) as
		// xsamt,sum(a.xscurr) as xscurr,sum(a.kcamt) as kcamt \n FROM
		// v_xskczy_data a";
		//
		// qry += "\n left outer join warehouse b on a.houseid=b.houseid where
		// a.epid=" + Userid;
		// qry += "\n group BY a.houseid,b.housename ";
		// qry += "\n order by a.houseid";
		// Table tb = new Table();
		// tb = DbHelperSQL.Query(qry).getTable(1);
		// Float totalxsamt = (float) 0;
		// Float totalxscurr = (float) 0;
		// Float totalkcamt = (float) 0;
		//
		// errmess += ",\"columns\":[";
		// // String fh = "";
		// for (int i = 0; i < tb.getRowCount(); i++) {
		// if (i > 0)
		// errmess += ",";
		// Float xsamt = Float.parseFloat(tb.getRow(i).get("XSAMT").toString());
		// Float xscurr =
		// Float.parseFloat(tb.getRow(i).get("XSCURR").toString());
		// Float kcamt = Float.parseFloat(tb.getRow(i).get("KCAMT").toString());
		// errmess += "{\"houseid\":" + tb.getRow(i).get("HOUSEID").toString()//
		// + ",\"housename\":\"" + tb.getRow(i).get("HOUSENAME").toString() +
		// "\"" //
		// + ",\"xsamt\":" + xsamt //
		// + ",\"xscurr\":" + xscurr//
		// + ",\"kcamt\":" + kcamt//
		// + "}";
		// totalxsamt += xsamt;
		// totalxscurr += xscurr;
		// totalkcamt += kcamt;
		// }
		// errmess += "]";
		// errmess += ",\"totalxsamt\":" + totalxsamt //
		// + ",\"totalxscurr\":" + totalxscurr //
		// + ",\"totalkcamt\":" + totalkcamt;

		// return 1;
	}

	// 获取商品尺码汇总信息
	public int doGetware() {
		if (Wareid == 0) {
			errmess = "wareid不是一个有效值！";
			return 0;
		}
		// 取尺码
		String qry = " select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += " left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += " where a.statetag=1 and a.noused=0 and b.wareid=" + Wareid + " and a.accid=" + Accid;
		qry += " and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += " order by a.sizeno,a.sizename";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到该商品！";
			return 0;
		}
		int rs = tb.getRowCount();
		String retcs = "\"msg\":\"操作成功！\",\"sizelist\":[";
		String sumlist = "";

		for (int i = 0; i < rs; i++) {
			if (i > 0) {
				retcs += ",";
				sumlist += ",";

			}
			Sizeid = Long.parseLong(tb.getRow(i).get("SIZEID").toString());
			retcs += "{\"SIZEID\":\"" + Sizeid + "\"," //
					+ "\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
			// qry += "\n insert into v_xskczy_data
			// (id,epid,houseid,fs,wareid,colorid,sizeid,xsamt,xscurr,jhamt,kcamt)
			// ";

			sumlist += "sum(decode(a.sizeid," + Sizeid + ",a.kcamt,0)) as kcamt" + Sizeid + ",sum(decode(a.sizeid," + Sizeid + ",a.xsamt,0)) as xsamt" + Sizeid;
		}
		retcs += "],\"sumlist\":\"" + sumlist + "\"";
		errmess = retcs;
		return 1;
	}
}
