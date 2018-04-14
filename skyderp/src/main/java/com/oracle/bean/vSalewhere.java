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

import net.sf.json.JSONObject;

/**
 * @author Administrator 销售分布
 */
public class vSalewhere {

	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd
	// HH:mm:ss,------------必传
	private Long Accid = (long) 0;

	private Long Userid = (long) 0;
	private Long Wareid = (long) 0;
	private Long  Typeid= (long) -1;

	private String Houseidlist = "";
	private String Custidlist = "";
	private String Brandidlist = "";
	private String Typeidlist = "";
	private String Dptidlist = "";
	private String Wareno = "";
	private String Warename = "";
	private String Prodyear = "";
	private String Seasonname = "";
	private Long Colorid = (long) 0;

	private String Mindate = "";
	private String Maxdate = "";
	private String Accbegindate = "2000-01-01"; // 套账起用日期
	private String errmess = "";
	private Integer Qxbj = 0;// 1=启用权限控
	private Integer Xsid = 2;// 0=批发 1=退货 2=所有
	private Integer Havesale = 0;// 1=只查有销售的商品
	private Integer All = 0;// 1=显示未发货的客户
	private String Calcdate = ""; // 账套库存登账日期

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	public String getErrmess() {
		return errmess;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setServerdatetime(String serverdatetime) {
		Serverdatetime = serverdatetime;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setHavesale(Integer havesale) {
		Havesale = havesale;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setHouseidlist(String houseidlist) {
		Houseidlist = houseidlist;
	}

	public void setWareid(Long wareid) {
		Wareid = wareid;
	}

	public void setBrandidlist(String brandidlist) {
		Brandidlist = brandidlist;
	}

	public void setTypeidlist(String typeidlist) {
		Typeidlist = typeidlist;
	}

	public void setWareno(String wareno) {
		Wareno = wareno;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setXsid(Integer xsid) {
		Xsid = xsid;
	}

	public void setCustidlist(String custidlist) {
		Custidlist = custidlist;
	}

	public void setMindate(String mindate) {
		Mindate = mindate;
	}

	public void setMaxdate(String maxdate) {
		Maxdate = maxdate;
	}

	public void setErrmess(String errmess) {
		this.errmess = errmess;
	}

	// 分页显示客户销售分布数据
	public Table GetTable(QueryParam qp, int hzfs, String sumsql) {
		String qry = "";
		if (hzfs == 1)// 商品+颜色
		{
			qry = "select x.wareid,b.warename,b.units,b.wareno,x.colorid, c.colorname,x.amount as amtkchj,min(rownum) as keyid," + sumsql;
			qry += "\n from (select epid,wareid,colorid ,sum(amount) as amount from v_cxkczy_data where epid=" + Userid + " and houseid=0 group by epid,wareid,colorid) x";
			qry += "\n left outer join warecode b on x.wareid=b.wareid";
			qry += "\n left outer join colorcode c on x.colorid=c.colorid";
			//			qry += "\n left outer join sizecode d on x.sizeid=d.sizeid";
			qry += "\n left outer join v_salewhere_data a on a.wareid=x.wareid and a.colorid=x.colorid  and a.epid=x.epid ";
			//			qry += " where x.epid=" + Userid + " and x.houseid=0";
			qry += " group by x.wareid,b.warename,b.units,b.wareno,x.colorid, c.colorname,x.amount";
		} else if (hzfs == 2) {// 商品+颜色+尺码

			qry = "select x.wareid,b.warename,b.units,b.wareno,x.colorid, c.colorname,x.sizeid,d.sizename,d.sizeno,x.amount as amtkchj,min(rownum) as keyid," + sumsql;
			qry += "\n from v_cxkczy_data x";
			qry += "\n left outer join warecode b on x.wareid=b.wareid";
			qry += "\n left outer join colorcode c on x.colorid=c.colorid";
			qry += "\n left outer join sizecode d on x.sizeid=d.sizeid";
			qry += "\n left outer join v_salewhere_data a on a.wareid=x.wareid and a.colorid=x.colorid and a.sizeid=x.sizeid and a.epid=x.epid ";

			qry += " where x.epid=" + Userid + " and x.houseid=0";
			qry += " group by x.wareid,b.warename,b.units,b.wareno,x.colorid, c.colorname,x.sizeid,d.sizename,d.sizeno,x.amount";
			// sort = "wareno,colorname,sizeno,keyid";
		} else {// 商品

			qry = "select x.wareid,b.warename,b.units,b.wareno,x.amount as amtkchj,min(rownum) as keyid," + sumsql;
			qry += "\n from (select epid,wareid ,sum(amount) as amount from v_cxkczy_data where epid=" + Userid + " and houseid=0 group by epid,wareid) x";
			qry += "\n left outer join warecode b on x.wareid=b.wareid";
			//			qry += "\n left outer join colorcode c on x.colorid=c.colorid";
			//			qry += "\n left outer join sizecode d on x.sizeid=d.sizeid";
			qry += "\n left outer join v_salewhere_data a on a.wareid=x.wareid and a.epid=x.epid ";
			//			qry += " where x.epid=" + Userid + " and x.houseid=0";
			qry += " group by x.wareid,b.warename,b.units,b.wareno,x.amount";
		}

		//		if (hzfs == 1)// 商品+颜色
		//		{
		//			qry = "select a.wareid,b.warename,b.units,b.wareno,a.colorid,c.colorname,x.amount as amtkchj,min(rownum) as keyid," + sumsql;
		//			qry += "\n from v_salewhere_data a";
		//			qry += "\n left outer join warecode b on a.wareid=b.wareid";
		//			qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		//			// qry += " left outer join v_cxkczy_data e on a.wareid=e.wareid
		//			// and
		//			// a.colorid=e.colorid and a.sizeid=e.sizeid";
		//
		//			qry += "\n left outer join (select wareid,colorid ,sum(amount) as amount from  v_cxkczy_data where epid=" + Userid + " and houseid=0  group by wareid,colorid) x on a.wareid=x.wareid and a.colorid=x.colorid";
		//			qry += "\n where a.epid=" + Userid;// + " and a.epid=e.epid and
		//												// e.houseid=0";
		//			qry += "\n group by a.wareid,b.warename,b.units,b.wareno,a.colorid, c.colorname,x.amount";
		//			// sort += " ,keyid";
		//		} else if (hzfs == 2) {// 商品+颜色+尺码
		//			qry = "select a.wareid,b.warename,b.units,b.wareno,a.colorid, c.colorname,a.sizeid,d.sizename,d.sizeno,x.amount as amtkchj,min(rownum) as keyid," + sumsql;
		//			qry += "\n from v_salewhere_data a";
		//			qry += "\n left outer join warecode b on a.wareid=b.wareid";
		//			qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		//			qry += "\n left outer join sizecode d on a.sizeid=d.sizeid";
		//
		//			qry += "\n left outer join v_cxkczy_data x on a.wareid=x.wareid and a.colorid=x.colorid and a.sizeid=x.sizeid";
		//			// qry += "\n left outer join (select wareid,colorid,sizeid
		//			// ,sum(amount) as amount from v_cxkczy_data where epid=" +
		//			// Userid
		//			// + " and houseid=0 group by wareid,colorid,sizeid) x on
		//			// a.wareid=x.wareid and a.colorid=x.colorid and
		//			// a.sizeid=x.sizeid";
		//
		//			qry += " where a.epid=" + Userid + " and a.epid=x.epid and x.houseid=0";
		//			qry += " group by a.wareid,b.warename,b.units,b.wareno,a.colorid, c.colorname,a.sizeid,d.sizename,d.sizeno,x.amount";
		//			// sort = "wareno,colorname,sizeno,keyid";
		//		} else {// 商品
		//			qry = "select a.wareid,b.warename,b.units,b.wareno,x.amount as amtkchj,min(rownum) as keyid, " + sumsql;
		//			qry += "\n from v_salewhere_data a";
		//			qry += "\n left outer join warecode b on a.wareid=b.wareid";
		//			// qry += " left outer join v_salewhere_data1 e on
		//			// a.wareid=e.wareid
		//			// ";//and a.colorid=e.colorid and a.sizeid=e.sizeid";
		//
		//			qry += "\n left outer join (select wareid ,sum(amount) as amount from  v_cxkczy_data where epid=" + Userid + " and houseid=0  group by wareid) x on a.wareid=x.wareid";
		//
		//			qry += "\n where a.epid=" + Userid;
		//
		//			qry += "\n group by a.wareid,b.warename,b.units,b.wareno,x.amount";
		//			// sort = "wareno,keyid";
		//		}
		// } else { //查所有库存的商品销售
		// if (hzfs == 1)// 商品+颜色
		// {
		// qry = "select
		// a.wareid,b.warename,b.units,b.wareno,a.colorid,c.colorname,x.amount
		// as amtkchj,min(rownum) as keyid," + sumsql;
		// qry += "\n from v_salewhere_data a";
		// qry += "\n left outer join warecode b on a.wareid=b.wareid";
		// qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		// qry += "\n full outer join (select wareid,colorid ,sum(amount) as
		// amount from v_cxkczy_data where epid=" + Userid
		// + " and houseid=0 group by wareid,colorid) x on a.wareid=x.wareid and
		// a.colorid=x.colorid";
		// qry += "\n where a.epid=" + Userid;// + " and a.epid=e.epid and
		// // e.houseid=0";
		// qry += "\n group by a.wareid,b.warename,b.units,b.wareno,a.colorid,
		// c.colorname,x.amount";
		// // sort += " ,keyid";
		// } else if (hzfs == 2) {// 商品+颜色+尺码
		// qry = "select a.wareid,b.warename,b.units,b.wareno,a.colorid,
		// c.colorname,a.sizeid,d.sizename,d.sizeno,x.amount as
		// amtkchj,min(rownum) as keyid," + sumsql;
		//
		// qry += "\n from v_salewhere_data a";
		// qry += "\n left outer join warecode b on a.wareid=b.wareid";
		// qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		// qry += "\n left outer join sizecode d on a.sizeid=d.sizeid";
		//
		// qry += "\n full outer join v_cxkczy_data x on a.wareid=x.wareid and
		// a.colorid=x.colorid and a.sizeid=x.sizeid";
		//
		//
		// qry += " where a.epid=" + Userid + " and a.epid=x.epid and
		// x.houseid=0";
		// qry += " group by a.wareid,b.warename,b.units,b.wareno,a.colorid,
		// c.colorname,a.sizeid,d.sizename,d.sizeno,x.amount";
		// // sort = "wareno,colorname,sizeno,keyid";
		// } else {// 商品
		// qry = "select a.wareid,b.warename,b.units,b.wareno,x.amount as
		// amtkchj,min(rownum) as keyid, " + sumsql;
		// qry += "\n from v_salewhere_data a";
		// qry += "\n left outer join warecode b on a.wareid=b.wareid";
		// // qry += " left outer join v_salewhere_data1 e on
		// // a.wareid=e.wareid
		// // ";//and a.colorid=e.colorid and a.sizeid=e.sizeid";
		//
		// qry += "\n full outer join (select wareid ,sum(amount) as amount from
		// v_cxkczy_data where epid=" + Userid + " and houseid=0 group by
		// wareid) x on a.wareid=x.wareid";
		//
		// qry += "\n where a.epid=" + Userid;
		//
		// qry += "\n group by a.wareid,b.warename,b.units,b.wareno,x.amount";
		// // sort = "wareno,keyid";
		// }
		//
		// }
		qp.setQueryString(qry);
		// qp.setSortString(sort);
		System.out.println("listsalewhere=" + qry);
		qp.setSumString("nvl(sum(amtkchj),0) as totalkcamt,nvl(sum(amthj),0) as totalamt,nvl(sum(curhj),0) as totalcurr");

		return DbHelperSQL.GetTable(qp);
	}

	// 汇总客户销售分布数据
	public int doTotal(JSONObject jsonObject) {
		if (Mindate.equals(""))
			Mindate = Func.subString(Serverdatetime, 1, 10);
		if (Maxdate.equals(""))
			Maxdate = Func.subString(Serverdatetime, 1, 10);
		if (Mindate.compareTo(Accbegindate) < 0)
			Mindate = Accbegindate;
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate;
		Mindate += " 00:00:00";
		Maxdate += " 23:59:59";
		String qry = "declare ";
		qry += "\n    v_epid  number; ";
		qry += "\n    v_accid  number;  ";
		qry += "\n    v_retsql clob;  --nvarchar2(32000);";
		// qry += "\n v_mindate varchar2(10); ";
		// qry += "\n v_maxdate varchar2(10); ";
		qry += "\n begin ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_salewhere_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_salewhere_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		qry += "\n    insert into v_salewhere_data (id,epid,custid,wareid,colorid,sizeid,amount,curr) ";
		qry += "\n    select v_salewhere_data_id.nextval," + Userid + ",custid,wareid,colorid,sizeid,amount,curr from ( ";
		// qry += "\n select /*+ ORDERED INDEX(b, IX1_WAREOUTM) USE_NL (a b)
		// */";
		qry += "\n    select  a.custid,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";

		qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " and a.ntid>0  ";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Xsid == 0) // then --零售//xsid:0=零售 1=经销 2=所有
		{
			qry += "\n      and a.ntid=1   ";
		} else // --经销
		if (Xsid == 1) // then --零售//xsid:0=零售 1=经销 2=所有
		{
			qry += "\n      and a.ntid=2 ";
		}

		qry += "\n   and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Houseidlist.equals(""))
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (!Custidlist.equals(""))
			qry += "\n   and " + Func.getCondition("a.custid", Custidlist);
		if (!Dptidlist.equals(""))
			qry += "\n   and " + Func.getCondition("a.dptid", Dptidlist);
		if (!Brandidlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (!Typeidlist.equals(""))
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}

		if (Wareid > 0)
			qry += "\n and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n and b.colorid=" + Colorid;

		if (Wareno.length() > 0)
			qry += "\n and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n and c.warename like '%" + Warename + "%'";
		if (Prodyear.length() > 0)
			qry += "\n and c.prodyear = '" + Prodyear + "'";
		if (Seasonname.length() > 0)
			qry += "\n and c.Seasonname like '%" + Seasonname + "%'";
		qry += "\n   group by a.custid,b.wareid,b.colorid,b.sizeid";

		qry += "\n);";
		qry += "\n   DECLARE ";
		qry += "\n      CURSOR cur_data IS ";
		qry += "\n      select custid,custname from (";
		qry += "\n      SELECT DISTINCT a.custid,b.custname,0 as sortbj FROM v_salewhere_data a ";
		qry += "\n      left outer join customer b on a.custid=b.custid where a.epid= " + Userid;
		//		if (All == 1) {// 显示所有客户
		qry += "\n     union all";
		qry += "\n     SELECT a.custid,a.custname,1 as sortbj FROM customer a ";
		qry += "\n     where a.accid= " + Accid + " and a.statetag=1 and a.noused=0";
		if (!Custidlist.equals(""))
			qry += "\n   and " + Func.getCondition("a.custid", Custidlist);

		qry += "\n     and not exists (select 1 from v_salewhere_data b where b.epid=" + Userid + " and a.custid=b.custid)";
		//		}
		qry += "\n      ORDER BY sortbj,custid  ) where rownum<=50; ";

		qry += "\n      v_row cur_data%rowtype; ";
		qry += "\n   begin ";
		qry += "\n      v_retsql:='sum(a.amount) as amthj,sum(a.curr) as curhj'; ";
		qry += "\n      FOR v_row IN cur_data LOOP ";
		qry += "\n        v_retsql:=v_retsql||',sum(decode(a.custid,'||v_row.custid||',a.amount)) as amt'||v_row.custid; ";
		qry += "\n        v_retsql:=v_retsql||',sum(decode(a.custid,'||v_row.custid||',a.curr)) as cur'||v_row.custid; ";
		qry += "\n      end loop; ";
		qry += "\n   end; ";
		qry += "\n   select v_retsql into :retsql from dual;";

		qry += "\n end;";
		//		System.out.println(qry);
		// int ret = DbHelperSQL.ExecuteSql(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retsql", new ProdParam(Types.VARCHAR));
		// LogUtils.LogDebugWrite("salewhere 1", qry);
		int ret = DbHelperSQL.ExecuteProc(qry, param, 1800);
		// LogUtils.LogDebugWrite("salewhere 2", "");
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retsql = param.get("retsql").getParamvalue().toString();
		//		System.out.println("retsql=" + retsql);
		// 计算库存
		vTotalCxkczy dal = new vTotalCxkczy();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setAccid(Accid);
		dal.setUserid(Userid);
		dal.setCxdatetime(Maxdate);
		dal.setCalcdate(Calcdate);
		// private Integer Havesale = 0;// 1=只查有销售的商品
		// private Integer All = 0;// 1=显示未发货的客户
		if (Havesale == 1) { // 1=只查有销售的商品
			dal.setBj(3);// bj:1=只查询v_xskczy_data 中的商品的库存,2=减配货占用(返回可用库存) ,3=只查询v_salewhere_data中的商品的库存
			dal.setBj0(1);
		}
		dal.setNohouse(1);// 不分店铺汇总库存
		// dal.setQxbj(0);
		//		System.out.println("salewhere 3:查询库存begin");
		//		LogUtils.LogDebugWrite("salewhere 3", "查询库存begin");
		dal.doCxkczy();
		//		 LogUtils.LogDebugWrite("salewhere 4", "查询库存end");
		//		System.out.println("salewhere 4:查询库存end");
		//		if (Havesale == 0) {// 要显示商品的所有颜色及尺码
		//
		//			System.out.println("salewhere 4.1");
		//
		//			qry = " insert into v_salewhere_data (id,epid,wareid,colorid,sizeid,custid,curr,amount)";
		//			qry += "\n select v_salewhere_data_id.nextval,a.epid,a.wareid,a.colorid,a.sizeid,b.custid,0,0 ";
		//			qry += "\n from v_cxkczy_data a ,customer b";
		//			qry += "\n where a.epid=" + Userid + " and b.accid=" + Accid + " and b.statetag=1 and b.noused=0";
		//			qry += "\n and exists (select 1 from v_salewhere_data d where a.epid=d.epid and b.custid=d.custid)";
		//
		//			if (!Custidlist.equals(""))
		//				qry += "\n   and " + Func.getCondition("b.custid", Custidlist);
		//			qry += "\n and not exists (select 1 from v_salewhere_data c where a.epid=c.epid and a.wareid=c.wareid";
		//			qry += "\n and a.colorid=c.colorid and a.sizeid=c.sizeid and b.custid=c.custid)";
		//			System.out.println("salewhere 4.2=" + qry);
		//			//			System.out.println(qry);
		//			DbHelperSQL.ExecuteSql(qry, 1800);
		//			System.out.println("salewhere 4.3");
		//		}
		errmess = "\"msg\":\"操作成功！\",\"sumsql\":\"" + retsql + "\"";
		// 各列名称及合计
		qry = " SELECT  a.custid,b.custname,sum(a.amount) as amount,sum(a.curr) as curr ";
		qry += "\n FROM v_salewhere_data a";
		qry += "\n left outer join customer b on a.custid=b.custid where a.epid=" + Userid;
		qry += "\n group BY a.custid,b.custname  ";
		if (!Custidlist.equals("")) {
			qry += "\n union all";
			qry += "\n SELECT a.custid,a.custname,0 as amount,0 as curr FROM customer a ";
			qry += "\n where a.accid= " + Accid + " and a.statetag=1 and a.noused=0";
			qry += "\n and " + Func.getCondition("a.custid", Custidlist);
			qry += "\n and not exists (select 1 from v_salewhere_data b where b.epid=" + Userid + " and a.custid=b.custid)";
		}
		qry += "\n order by custid";
		//		System.out.println("salewhere 5:" + qry);
		// LogUtils.LogDebugWrite("salewhere 7", qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		// LogUtils.LogDebugWrite("salewhere 8", "");

		errmess += ",\"columns\":[";
		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				errmess += ",";
			errmess += "{\"custid\":" + tb.getRow(i).get("CUSTID").toString()//
					+ ",\"custname\":\"" + tb.getRow(i).get("CUSTNAME").toString() + "\"" //
					+ ",\"amount\":\"" + tb.getRow(i).get("AMOUNT").toString() + "\""//
					+ ",\"curr\":\"" + tb.getRow(i).get("CURR").toString() + "\"}";
		}
		errmess += "]";
		// LogUtils.LogDebugWrite("salewhere 9", "end");
		return 1;

	}
}
