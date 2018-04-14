/**
 * 
 */
package com.oracle.bean;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.comdot.data.Table;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

/**
 * @author Administrator
 *
 */
public class vTotalkcjgfx {
	private Long Accid = (long) 0;
	private Long Userid = (long) 0;
	private Long Houseid = (long) 0;
	private String errmess = "";
	private String Nowdate = "";

	public String getErrmess() {
		return errmess;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setHouseid(Long houseid) {
		Houseid = houseid;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public int doTotal(int hzfs, int housecostbj) {
		// hzfs:0=品牌 1=类型 2=季节 3=店铺 4=颜色 ,5=供应商,6=尺码,7=区位
		String qry = "declare ";
		qry += "\n  v_totalamt number; ";
		qry += "\n  v_totalcurr number; ";
		qry += "\n  v_fixedcurr number; ";
		qry += "\n  v_checkcurr number; ";
		qry += "\n  v_epid number(10); ";
		qry += "\n  v_accid number(10); ";
		qry += "\n  v_nowdate0 varchar2(10);";
		qry += "\n  v_nowdate varchar2(10); ";
		qry += "\n begin  ";

		qry += "\n   delete from v_kcjgfx_data where epid=" + Userid + ";";
		qry += "\n   insert into v_kcjgfx_data (id,epid,codeid,codename,amount,curr,fixedcurr,checkcurr,skc)";
		// qry += "\n select v_kcjgfx_data_id.nextval," + userid + ",codeid,codename,amount,curr,skc from (";
		qry += "\n   select v_kcjgfx_data_id.nextval," + Userid + ",codeid,codename,amount,curr,fixedcurr,checkcurr,skc from ( ";
		if (hzfs == 0) {
			qry += "\n select b.brandid as codeid,c.brandname as codename";
		} // 按品牌汇总
		else if (hzfs == 1) {
			qry += "\n select b.typeid as codeid,c.fullname as codename";
		} // 按类型
		else if (hzfs == 2) {
			qry += "\n select 0 as codeid,b.seasonname as codename";
		} // 按季节
		else if (hzfs == 3) {
			qry += "\n select a.houseid as codeid,c.housename as codename";
		} // 按店铺
		else if (hzfs == 4) {
			qry += "\n select a.colorid as codeid,c.colorname as codename";
		} // 按店铺
		else if (hzfs == 5) {
			qry += "\n select b.provid as codeid,c.provname as codename";
		} // 按供应商
		else if (hzfs == 6) {
			qry += "\n select a.sizeid as codeid,c.groupno||'.'||c.sizename as codename";
		} // 按区位
		else if (hzfs == 7) {
			qry += "\n select b.areaid as codeid,c.areaname as codename";
		} // 按供应商

		qry += " ,sum(a.amount) as amount,count(distinct '^'||a.wareid||'^'||a.colorid||'^') as skc ";
		qry += " ,sum(a.amount*c.costprice) as fixedcurr,sum(a.amount*b.checksale) as checkcurr";

		if (Houseid == 0)
			qry += ",sum(a.amount*b.retailsale) as curr";
		else
			qry += ",sum(a.amount*case when f.retailsale is null then b.retailsale else f.retailsale end) as curr";

		qry += "\n from v_cxkczy_data a";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join warecost c on a.wareid=c.wareid and c.daystr='" + Nowdate + "'";
		if (housecostbj == 1)
			qry += " and a.houseid=c.houseid";
		else
			qry += "  and c.houseid=0";

		if (Houseid > 0)
			qry += "\n left outer join housesaleprice f on a.wareid=f.wareid and f.houseid=" + Houseid;

		if (hzfs == 0) {
			qry += "\n left outer join brand c on b.brandid=c.brandid";
		} else if (hzfs == 1) {
			qry += "\n left outer join waretype c on b.typeid=c.typeid";
		} else if (hzfs == 3) {
			qry += "\n left outer join warehouse c on a.houseid=c.houseid";
		} else if (hzfs == 4) {
			qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		} else if (hzfs == 5) {
			qry += "\n left outer join provide c on b.provid=c.provid";
		} // 按供应商
		else if (hzfs == 6) {
			qry += "\n left outer join sizecode c on a.sizeid=c.sizeid";
		} // 按供应商
		else if (hzfs == 7) {
			qry += "\n left outer join area c on b.areaid=c.areaid";
		} // 按供应商
		qry += "\n where a.epid=" + Userid;
		if (hzfs == 0) {
			qry += "\n group by b.brandid ,c.brandname";
		} // 按品牌汇总
		else if (hzfs == 1) {
			qry += "\n group by b.typeid ,c.fullname ";
		} // 按类型
		else if (hzfs == 2) {
			qry += "\n group by b.seasonname";
		} // 按季节
		else if (hzfs == 3) {
			qry += "\n group by a.houseid ,c.housename";
		} // 按店铺
		else if (hzfs == 4) {
			qry += "\n group by a.colorid ,c.colorname";
		} // 按颜色
		else if (hzfs == 5) {
			qry += "\n group by b.provid ,c.provname ";
		} // 按类型
		else if (hzfs == 6) {
			qry += "\n group by a.sizeid ,c.groupno||'.'||c.sizename ";
		} // 按尺码
		else if (hzfs == 7) {
			qry += "\n group by b.areaid ,c.areaname ";
		} // 按类型
		qry += "\n ) ;";

		qry += "\n   select sum(amount),sum(curr),sum(fixedcurr),sum(checkcurr) into v_totalamt,v_totalcurr,v_fixedcurr,v_checkcurr from v_kcjgfx_data where epid=" + Userid + ";";
		qry += "\n   update v_kcjgfx_data set rateamt=case v_totalamt when 0 then 0 else round(amount/v_totalamt*100,1) end,ratecur=case v_totalcurr when 0 then 0 else round(curr/v_totalcurr*100,1) end  where epid="
				+ Userid + ";";
		qry += "\n   select nvl(v_totalamt,0),nvl(v_totalcurr,0),nvl(v_fixedcurr,0),nvl(v_checkcurr,0) ";
		qry += "\n   into :amount,:curr,:fixedcurr,:checkcurr from dual;";
		qry += "\n end;";
		
		System.out.println(qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("amount", new ProdParam(Types.FLOAT));
		param.put("curr", new ProdParam(Types.FLOAT));
		param.put("fixedcurr", new ProdParam(Types.FLOAT));
		param.put("checkcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		errmess = "\"msg\":\"操作成功！\",\"amount\":\"" + param.get("amount").getParamvalue() + "\"" //
				+ ",\"curr\":\"" + param.get("curr").getParamvalue() + "\"" //
				+ ",\"fixedcurr\":\"" + param.get("fixedcurr").getParamvalue() + "\"" //
				+ ",\"checkcurr\":\"" + param.get("checkcurr").getParamvalue() + "\"" //
				;
		return 1;
	}

	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM v_kcjgfx_data a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		System.out.println(strSql.toString());
		qp.setQueryString(strSql.toString());
//		qp.setSumString("nvl(sum(totalamt),0) as totalamount,nvl(sum(totalfactamt),0) as totalfactamt,nvl(sum(TOTALCYAMT),0) as TOTALCYAMT,nvl(sum(totalcurr),0) as totalcurr");

		return DbHelperSQL.GetTable(qp);
	}

}
