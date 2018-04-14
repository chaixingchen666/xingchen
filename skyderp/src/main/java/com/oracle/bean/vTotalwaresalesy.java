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
 * @author Administrator
 *
 */
public class vTotalwaresalesy {
	private Long Userid;
	private Long Accid;
	private String errmess;

	private String Mindate = "";
	private String Maxdate = "";
	private String Accbegindate = "2000-01-01";
	private String Nowdate = "";
	private Integer Qxbj = 0;// 1=启用权限控
	private Integer Maxday = 0;// 最大查询天数
	private Long Houseid = (long) -1;
	private Long Salemanid = (long) 0;
//	private Long Areaid = (long) -1;
	private Long Vtid = (long) -1;
	private Long Guestid = (long) 0;
	private String Noteno = "";
	private String Wareno = "";
	private String Lastop = "";
	private Long Wareid = (long) 0;
	//供应商ID
	private String Providlist = "";
	//商品类型ID
	private Long Typeid = (long) 0;

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

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public int doTotal() {
		if (Mindate.length() < 0 || Maxdate.length() < 0) {
			errmess = "mindate或maxdate不是一个有效值！";
			return 0;
		}
		if (Mindate.length() < 0 || Maxdate.length() < 0) {
			errmess = "mindate或maxdate不是一个有效值！";
			return 0;
		}

		// 校验开始查询日期
		//yyyy-mm-dd hh:mm
		MinDateValid mdv = new MinDateValid(Func.strLeft(Mindate,10), Nowdate, Accbegindate, Maxday);
//		12345678901234567890
//		2017-09-01 00:00
		Mindate = mdv.getMindate()+Func.subString(Mindate, 11, 6);
//		String warning = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		Mindate += ":00";
		Maxdate += ":59";

		String qry = "declare ";
		qry += "\n     v_epid number;  ";
		qry += "\n     v_accid  number;  ";
		qry += "\n     v_xjpaycurr number;";
		qry += "\n     v_mindate  varchar2(20);  ";
		qry += "\n     v_maxdate  varchar2(20);  ";
		qry += "\n     v_retsql varchar2(8000);  ";
		// qry += "\n v_retsql clob;";
		qry += "\n begin  ";
//		qry += "\n    update shopsaleh a set houseid=(select min(houseid) from shopsalem  b where a.accid=b.accid and a.noteno=b.noteno) ";
//		qry += "\n	  where a.statetag=1 and a.houseid=0 and a.accid=" + Accid + ";";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "         select ROWID from v_waresalesy_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_waresalesy_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_waresalesy_data (id,epid,accid,noteno,notedate,ntid,houseid,totalamt,totalcurr,freecurr,jfcurr,checkcurr,zpaycurr,changecurr,guestid,operant,salemanlist,remark)";
		qry += "\n    select v_waresalesy_data_id.nextval," + Userid + "," + Accid
				+ ",noteno,notedate,ntid,houseid,totalamt,totalcurr,freecurr,jfcurr,checkcurr,zpaycurr,changecurr,guestid,cashier,salemanlist,remark from (";
		qry += "\n    select noteno,notedate,ntid,houseid,totalamt,totalcurr,freecurr,jfcurr,checkcurr,zpaycurr,changecurr,guestid,cashier,f_getwareoutsalemanname(accid,noteno) as salemanlist,remark";
		qry += "\n    from wareouth a ";
		qry += "\n    where  a.statetag=1 and a.ntid=0 and a.accid=" + Accid + " ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (!Func.isNull(Lastop))
			qry += "  and a.cashier='" + Lastop + "'";
		if (Houseid > 0) {
			qry += "  and a.houseid=" + Houseid;
		}
		;
		if (!Func.isNull(Noteno))
			qry += "  and a.noteno like '%" + Noteno + "%'";
		if (Guestid > 0)
			qry += " and a.guestid=" + Guestid;
//		if (Areaid > 0)
//			qry += " and exists (select 1 from warecode a1 where a.guestid=a1.guestid and a1.vtid=" + Vtid + ")";
//			qry += " and a.Areaid=" + Areaid;
		if (Vtid > 0)
			qry += " and exists (select 1 from guestvip a1 where a.guestid=a1.guestid and a1.vtid=" + Vtid + ")";

		if (Salemanid > 0)
			qry += " and exists (select 1 from waresaleman a1 where a1.accid=a.accid and a1.noteno=a.noteno and a1.epid=" + Salemanid + ")";
		if (Wareid > 0)
			qry += " and exists (select 1 from wareoutm a2 where a2.accid=a.accid and a2.noteno=a.noteno and a2.wareid=" + Wareid + ")";
		else if (!Func.isNull(Wareno))
			qry += " and exists (select 1 from wareoutm a2 join warecode a3 on a2.wareid=a3.wareid where a2.accid=a.accid and a2.noteno=a.noteno and a3.wareno like '%" + Wareno.toUpperCase() + "%')";

		if (!"".equals(Providlist)) {
			qry += " and exists (select 1 from wareoutm a2 join warecode a3 on a2.wareid=a3.wareid where a2.accid=a.accid and a2.noteno=a.noteno and " + Func.getCondition("a3.provid", Providlist) + ")";
		}
		if (Typeid > 0L) {
			qry += " and exists (select 1 from wareoutm a2 join warecode a3 on a2.wareid=a3.wareid where a2.accid=a.accid and a2.noteno=a.noteno and a3.typeid =" + Typeid + ")";
		}

		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " )  ";
			// qry += " and exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid="+userid+" ) ";
		}

		qry += "\n    union all";
		qry += "\n    select noteno,notedate,3,houseid,totalamt,totalcurr,freecurr,jfcurr,checkcurr,zpaycurr,changecurr,guestid,cashier,f_getshopsalemanname(accid,noteno) as salemanlist,remark";
		qry += "\n    from shopsaleh a ";
		qry += "\n    where  a.statetag=1 and a.accid=" + Accid + " ";
		// qry += "\n and to_char(a.notedate,'yyyy-mm-ddhh24:mi:00') >=v_mindate ";
		// qry += "\n and to_char(a.notedate,'yyyy-mm-ddhh24:mi:59') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (!Func.isNull(Lastop))
			qry += "  and a.cashier='" + Lastop + "'";
		if (Houseid > 0) {
			qry += "  and a.houseid=" + Houseid;
		}
		
		if (!Func.isNull(Noteno))
			qry += "  and a.noteno like '%" + Noteno + "%'";
		if (Guestid > 0)
			qry += " and a.guestid=" + Guestid;
		if (Vtid > 0)
			qry += " and exists (select 1 from guestvip a1 where a.guestid=a1.guestid and a1.vtid=" + Vtid + ")";

		if (Salemanid > 0)
			qry += " and exists (select 1 from shopsalem a1 where a1.accid=a.accid and a1.noteno=a.noteno and a1.salemanid=" + Salemanid + ")";

		if (Wareid > 0)
			qry += " and exists (select 1 from shopsalem a2 where a2.accid=a.accid and a2.noteno=a.noteno and a2.wareid=" + Wareid + ")";
		else if (!Func.isNull(Wareno))
			qry += " and exists (select 1 from shopsalem a2 join warecode a3 on a2.wareid=a3.wareid where a2.accid=a.accid and a2.noteno=a.noteno and a3.wareno like '%" + Wareno.toUpperCase() + "%')";

        if (!"".equals(Providlist)) {
            qry += " and exists (select 1 from shopsalem a2 join warecode a3 on a2.wareid=a3.wareid where a2.accid=a.accid and a2.noteno=a.noteno and " + Func.getCondition("a3.provid", Providlist) + ")";
        }
		if (Typeid > 0L) {
			qry += " and exists (select 1 from shopsalem a2 join warecode a3 on a2.wareid=a3.wareid where a2.accid=a.accid and a2.noteno=a.noteno and a3.typeid =" + Typeid + ")";
		}

		if (Qxbj == 1) // 1 启用权限控制
		{
//			qry += "\n    and exists (select 1 from shopsalem a1,employehouse x1 where a1.accid=a.accid and a1.noteno=a.noteno and a1.houseid=x1.houseid and x1.epid=" + Userid + " )  ";
			 qry += "\n and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			// qry += " and exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid="+userid+" ) ";
		}
		qry += " ) ;";

		// }
		qry += "\n   commit;   ";

		// qry += " select nvl(sum(amount),0),nvl(sum(curr),0) into :v_totalamt,:v_totalcurr from v_xszbfx_data where epid="+userid+"; ";

		// 收现金合计
		qry += "\n     select nvl(sum(curr),0) into v_xjpaycurr from (";
		qry += "\n      select sum(a.curr) as curr ";
		qry += "\n      from waresalepay a";
		qry += "\n      join v_waresalesy_data b on a.accid=b.accid and a.noteno=b.noteno and b.ntid<3 ";
		qry += "\n      left outer join payway c on a.payid=c.payid";
		qry += "\n      where b.epid=" + Userid + " and c.payno='0'";
		qry += "\n      union all";
		qry += "\n      select sum(a.curr)  as curr";
		qry += "\n      from shopsalepay a";
		qry += "\n      join v_waresalesy_data b on a.accid=b.accid and a.noteno=b.noteno and b.ntid=3 ";
		qry += "\n      left outer join payway c on a.payid=c.payid";
		qry += "\n      where b.epid=" + Userid + " and c.payno='0' ";
		qry += "\n     );";

		qry += "\n      v_retsql:=''; ";
		qry += "\n      DECLARE  CURSOR CUR_v_waresalepay_data IS ";
		qry += "\n      select payid,payno,count(*) from (";
		qry += "\n        select a.payid,c.payno,count(*) as num ";
		qry += "\n        from waresalepay a";
		qry += "\n        join v_waresalesy_data b on a.accid=b.accid and a.noteno=b.noteno and b.ntid<3 ";
		qry += "\n        left outer join payway c on a.payid=c.payid";
		qry += "\n        where b.epid=" + Userid + "";
		qry += "\n        group by a.payid,c.payno";
		qry += "\n        union all";
		qry += "\n        select a.payid,c.payno,count(*) as num ";
		qry += "\n        from shopsalepay a";
		qry += "\n        join v_waresalesy_data b on a.accid=b.accid and a.noteno=b.noteno and b.ntid=3 ";
		qry += "\n        left outer join payway c on a.payid=c.payid";
		qry += "\n        where b.epid=" + Userid + " ";
		qry += "\n        group by a.payid,c.payno";
		qry += "\n      ) group by payid,payno";

		qry += "\n      order by payno,payid;";

		qry += "\n       v_row CUR_v_waresalepay_data%rowtype; ";
		qry += "\n    begin ";
		qry += "\n       FOR v_row IN CUR_v_waresalepay_data LOOP ";
		qry += "\n         v_retsql:=v_retsql||',sum(decode(b.payid,'||v_row.payid||',b.curr)) as paycurr'||v_row.payid; ";
		qry += "\n         if v_row.payno='0' then "; // 如果是现金列，减去找零，得实收现金
		qry += "\n            v_retsql:=v_retsql||',sum(decode(b.payid,'||v_row.payid||',b.curr))-a.changecurr as xjpaycurr'; ";
		qry += "\n         end if;";
		qry += "\n       end loop; ";
		qry += "\n    end; ";

		qry += "\n    select nvl(sum(totalamt),0),nvl(sum(totalcurr),0),nvl(sum(freecurr),0),nvl(sum(jfcurr),0),nvl(sum(checkcurr),0),nvl(sum(zpaycurr),0),nvl(sum(changecurr),0),v_xjpaycurr-nvl(sum(changecurr),0),nvl(v_retsql,' ') ";
		qry += "\n      into :totalamt,:totalcurr,:freecurr,:jfcurr,:checkcurr,:zpaycurr,:changecurr,:xjpaycurr,:retsql from v_waresalesy_data where epid=" + Userid + "; ";
		qry += "\n end; ";
//System.out.println(qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt", new ProdParam(Types.FLOAT));
		param.put("totalcurr", new ProdParam(Types.FLOAT));
		param.put("freecurr", new ProdParam(Types.FLOAT));
		param.put("jfcurr", new ProdParam(Types.FLOAT));
		param.put("checkcurr", new ProdParam(Types.FLOAT));
		param.put("zpaycurr", new ProdParam(Types.FLOAT));
		param.put("changecurr", new ProdParam(Types.FLOAT));
		param.put("xjpaycurr", new ProdParam(Types.FLOAT));
		param.put("retsql", new ProdParam(Types.VARCHAR));
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
				+ ",\"freecurr\":\"" + param.get("freecurr").getParamvalue() + "\""//
				+ ",\"jfcurr\":\"" + param.get("jfcurr").getParamvalue() + "\""//
				+ ",\"checkcurr\":\"" + param.get("checkcurr").getParamvalue() + "\""//
				+ ",\"zpaycurr\":\"" + param.get("zpaycurr").getParamvalue() + "\""//
				+ ",\"changecurr\":\"" + param.get("changecurr").getParamvalue() + "\""//
				+ ",\"xjpaycurr\":\"" + param.get("xjpaycurr").getParamvalue() + "\""//
				+ ",\"sumsql\":\"" + param.get("retsql").getParamvalue() + "\""//
				+ ",\"warning\":\"" + mdv.getErrmess() + "\"";

		qry = " select payid,payno,payname,sum(curr) as curr from (";
		qry += "\n    select a.payid,c.payno,c.payname,sum(a.curr) as curr ";
		qry += "\n    from waresalepay a";
		qry += "\n    join v_waresalesy_data b on a.accid=b.accid and a.noteno=b.noteno and b.ntid<3 ";
		qry += "\n    left outer join payway c on a.payid=c.payid";
		qry += "\n    where b.epid=" + Userid;
		qry += "\n    group by a.payid,c.payno,c.payname";
		qry += "\n    union all";
		qry += "\n    select a.payid,c.payno,c.payname,sum(a.curr) as curr ";
		qry += "\n    from shopsalepay a";
		qry += "\n    join v_waresalesy_data b on a.accid=b.accid and a.noteno=b.noteno and b.ntid=3 ";
		qry += "\n    left outer join payway c on a.payid=c.payid";
		qry += "\n    where b.epid=" + Userid;
		qry += "\n    group by a.payid,c.payno,c.payname";
		qry += "\n    ) group by payid,payno,payname";
		qry += "\n    order by payno,payid";

		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);

		errmess += ",\"columns\":[";
		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				errmess += ",";
			errmess += "{\"PAYID\":" + tb.getRow(i).get("PAYID").toString()//
					+ ",\"PAYNO\":\"" + tb.getRow(i).get("PAYNO").toString() + "\"" //
					+ ",\"PAYNAME\":\"" + tb.getRow(i).get("PAYNAME").toString() + "\"" //
					+ ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"}";
		}
		errmess += "]";
		return 1;
	}

	public Table GetTable(QueryParam qp, String sumsql) {
		String qry = "select a.id,a.noteno,a.notedate,a.totalamt,a.totalcurr,a.freecurr,a.jfcurr,a.checkcurr,a.zpaycurr,a.changecurr,a.operant,c.guestname||' '||c.vipno as viplist,a.salemanlist,a.remark,d.housename";
		qry += "\n   " + sumsql;
		qry += "\n   from v_waresalesy_data a";
		qry += "\n   left outer join waresalepay b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n   left outer join guestvip c on a.guestid=c.guestid ";
		qry += "\n   left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n   where a.epid=" + Userid + " and a.ntid<3";
		qry += "\n   group by a.id,a.noteno,a.notedate,a.totalamt,a.totalcurr,a.freecurr,a.jfcurr,a.checkcurr,a.zpaycurr,a.changecurr,a.operant,c.guestname||' '||c.vipno,a.salemanlist,a.remark,d.housename";
		qry += "\n   union all";
		qry += "\n   select a.id,a.noteno,a.notedate,a.totalamt,a.totalcurr,a.freecurr,a.jfcurr,a.checkcurr,a.zpaycurr,a.changecurr,a.operant,c.guestname||' '||c.vipno as viplist,a.salemanlist,a.remark,d.housename";
		qry += "\n   " + sumsql;
		qry += "\n   from v_waresalesy_data a";
		qry += "\n   left outer join shopsalepay b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n   left outer join guestvip c on a.guestid=c.guestid ";
		qry += "\n   left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n   where a.epid=" + Userid + " and a.ntid=3 ";
		qry += "\n   group by a.id,a.noteno,a.notedate,a.totalamt,a.totalcurr,a.freecurr,a.jfcurr,a.checkcurr,a.zpaycurr,a.changecurr,a.operant,c.guestname||' '||c.vipno,a.salemanlist,a.remark,d.housename";
		qp.setQueryString(qry);
		return DbHelperSQL.GetTable(qp);
	}
}
