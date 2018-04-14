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
 * @author Administrator 销售业绩统计
 *
 */
public class vTotalxsyjtj {

	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd HH:mm:ss,------------必传
	private Long Accid = (long) 0;

	private Long Userid = (long) 0;
	private Long Wareid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;
	private Long Epid = (long) 0;
	// private Long Dptid = (long) -1;
	private Long Provid = (long) -1;
	private Long Brandid = (long) -1;
	private Long Typeid = (long) -1;
	private String Areaidlist = "";
	private String Houseidlist = "";
	private String Brandidlist = "";
	private String Providlist = "";
	private String Dptidlist = "";

	private String Seasonname = "";
	private String Locale = "";
	private String Warename = "";
	private String Wareno = "";
	private String Prodyear = "";
	private Long Houseid = (long) 0;
	// private Long Custid = (long) 0;
	private Integer Xsid = 2;// 0=零售，1=批发，2=所有
	// private Integer Hzfs = 0;// hzfs:0=品牌， 1=类型， 2=季节，3=店铺，4=客户，5=销售类型

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
		if (Mindate.length() < 0 || Maxdate.length() < 0) {
			errmess = "mindate或maxdate不是一个有效值！";
			return 0;
		}
		if (Houseid > 0 && Houseidlist.length() >= 0)
			Houseidlist = Houseid.toString();
		if (Brandid >= 0 && Brandidlist.length() >= 0)
			Brandidlist = Brandid.toString();
		if (Provid >= 0 && Providlist.length() >= 0)
			Providlist = Provid.toString();
		//System.out.println(Brandidlist+" "+Providlist);
		// if (Xsid == null)
		// Xsid = 2;
		// 校验开始查询日期
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
		qry += "\n    v_totalamt number;";
		qry += "\n    v_totalcurr number;";
		qry += "\n begin ";

		// qry += "\n delete from v_xsyjtj_data where epid=" + userid + ";";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "         select ROWID from v_xsyjtj_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_xsyjtj_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n      insert into v_xsyjtj_data (id,epid,epid0,amount,curr,skc,num,rateamt,ratecur) ";
		qry += "\n      select v_xsyjtj_data_id.nextval," + Userid + ",epid,amount,curr,skc,num,0,0 from ( ";
		// xsid: 0=零售，1=批发，2=所有
		// if (Xsid == 0 || Xsid == 2) {
		qry += "\n      select epid,sum(amount) as amount,sum(curr) as curr,count(distinct '^'||wareid||'^'||colorid||'^') as skc,count(distinct noteno) as num  from ( ";
		// 店铺零售及批发
		qry += "\n      select d.epid,a.noteno,b.wareid,b.colorid,a.rs ";   //,a.freecurr/a.rs as curr1
		qry += "\n      ,round(sum(case a.ntid when 2 then -b.curr else b.curr end)/a.rs,2) as curr ";
		qry += "\n      ,round(sum(case a.ntid when 2 then -b.amount else b.amount end)/a.rs,2) as amount  ";
		qry += "\n      from wareouth a ";
		qry += "\n      join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n      left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n      join waresaleman d on a.accid=d.accid and a.noteno=d.noteno ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1 and a.rs>0 ";
		if (Xsid == 0) // 零售
			qry += "\n  and a.ntid=0";
		else if (Xsid == 1)// 批发
			qry += "\n  and a.ntid>0";

		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			if (Xsid == 1)
				qry += "\n    and  exists (select 1 from employecust x3 where a.custid=x3.custid and x3.epid=" + Userid + " ) ";
		}

		if (Wareid > 0)
			qry += "\n    and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n    and b.sizeid=" + Sizeid;
		// if (epid > 0) qry += "\n and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and x.epid=" + epid + ")";
		if (Epid > 0)
			qry += "\n   and d.epid=" + Epid;

		if (Wareno.length() > 0)
			qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n   and c.warename='" + Warename + "'";
		if (Locale.length() > 0)
			qry += "\n   and c.locale like '" + Locale + "%'";

		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Dptidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.dptid", Dptidlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);

		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and c.typeid=" + Typeid;
			}

		if (Seasonname.length() > 0)
			qry += "\n   and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n   and c.prodyear='" + Prodyear + "'";

		qry += "\n      group by d.epid,a.noteno,b.wareid,b.colorid,a.rs ";
		// }
		// xsid: 0=零售，1=批发，2=所有
		if (Xsid == 0 || Xsid == 2) // 商场零售
		{
			if (Xsid != 1)
				qry += "\n      union all ";
			// 商场零售
			qry += "\n      select d.epid,a.noteno,b.wareid,b.colorid,0 ";
			qry += "\n      ,sum(b.curr) as curr,sum(b.amount) as amount ";
			qry += "\n      from shopsaleh a ";
			qry += "\n      join shopsalem b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n      left outer join warecode c on b.wareid=c.wareid ";
			qry += "\n      join employe d on b.salemanid=d.epid ";
			qry += "\n      where a.accid=" + Accid + " and a.statetag=1  ";

			qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
				//				qry += "\n    and (a.custid=0 or exists (select 1 from employecust x3 where a.custid=x3.custid and x3.epid=" + Userid + " )) ";
			}

			if (Wareid > 0)
				qry += "\n    and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n   and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n    and b.sizeid=" + Sizeid;
			// if (epid > 0) qry += "\n and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and x.epid=" + epid + ")";
			if (Epid > 0)
				qry += "\n   and b.salemanid=" + Epid;

			if (Wareno.length() > 0)
				qry += "\n    and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n   and c.warename='" + Warename + "'";
			if (Locale.length() > 0)
				qry += "\n   and c.locale like '" + Locale + "%'";

			if (Houseidlist.length() > 0)
				qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
			if (Dptidlist.length() > 0)
				qry += "\n   and " + Func.getCondition("a.dptid", Dptidlist);
			if (Areaidlist.length() > 0)
				qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
			if (Brandidlist.length() > 0)
				qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
			if (Providlist.length() > 0)
				qry += "\n   and " + Func.getCondition("c.provid", Providlist);
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "    and c.typeid=" + Typeid;
				}
			if (Seasonname.length() > 0)
				qry += "\n   and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n   and c.prodyear='" + Prodyear + "'";
			qry += "\n      group by d.epid,a.noteno,b.wareid,b.colorid ";
		}
		qry += "\n      ) group by epid); ";
		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from v_xsyjtj_data where epid=" + Userid + ";";
		qry += "\n    update v_xsyjtj_data set rateamt=case v_totalamt when 0 then 0 else round(amount/v_totalamt*100,1) end";
		qry += "\n    ,ratecur=case v_totalcurr when 0 then 0 else round(curr/v_totalcurr*100,1) end  where epid=" + Userid + ";";
		qry += "\n    select v_totalamt,v_totalcurr into :totalamt,:totalcurr from dual;";
		qry += "\n end; ";
		//		//System.out.println(qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt", new ProdParam(Types.FLOAT));
		param.put("totalcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
		// String Noteno = param.get("Noteno").getParamvalue().toString();
		errmess = "\"msg\":\"操作成功！\",\"totalamt\":\"" + param.get("totalamt").getParamvalue() + "\"" //
				+ ",\"totalcurr\":\"" + param.get("totalcurr").getParamvalue() + "\"" //
				+ ",\"warning\":\"" + mdv.getErrmess() + "\"";
		return 1;
	}

	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n  FROM v_xsyjtj_data  a left outer join employe b on a.epid0=b.epid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}

		//System.out.println(strSql.toString());
		qp.setQueryString(strSql.toString());
		// return DbHelperSQL.GetTable(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}
}
