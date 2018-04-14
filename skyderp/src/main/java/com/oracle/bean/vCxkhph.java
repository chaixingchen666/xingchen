/**
 * 
 */
package com.oracle.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.comdot.data.Table;
import com.common.tool.Func;

//import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.QueryParam;

/**
 * @author Administrator 查询客户配货
 */
public class vCxkhph {
	private String Fieldlist = ""; // 要显示的项目列表
	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	private String Calcfield = ""; // 要计算的项目列表

	private Long Accid;
	private Long Userid;

	public void setUserid(Long userid) {
		Userid = userid;
	}

	// private Long Provid = (long) -1;
	private Long Wareid = (long) 0;
	// private Long Typeid;
	private String Noteno = "";
	private String Wareno = "";
	private String Warename = "";
	private String Seasonname = "";
	private String Prodyear = "";
	private String Locale = "";

	private String Areaname = "";
	private String Houseidlist = "";
	private String Brandidlist = "";
//	private String Typeidlist = "";
	private String Custidlist = "";
	private String Providlist = "";
	private String Operant = "";
	private Long Typeid = (long)-1;
	private Integer Overbj = 2;// 0=未完订单，1=已完订单，2=所有

	private String Maxdate;
	private String Mindate;

	private String Currdatetime;
	private String Nowdate = "";
	private String Accbegindate = "";
	private Integer Qxbj = 0;

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	private Integer Maxday = 0;

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
	}

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public vCxkhph() {// 初始值
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Maxdate = df.format(new Date());
		Mindate = Maxdate;
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Currdatetime = df.format(new Date());

	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public String GetTable2Json(QueryParam qp, int sizenum) {
		Grouplist += ",c.sizegroupno";

		Table tb = GetTable(qp);

		String sqlsize = "";
		String sqlsum = "";
		StringBuilder strSql1 = new StringBuilder();
		long wareid = 0;
		long wareid0 = 0;
		long houseid = 0;
		long colorid = 0;
		long custid = 0;
		String notedate = "";
		String operant = "";
		String remark = "";
		String noteno = "";
		String handno = "";
		String areaname = "";
		String fh = "";
		//System.out.println("aaaa: " + qp.getTotalString());
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		int rows = tb.getRowCount();
		int cols = tb.getColumnCount();
		for (int i = 0; i < rows; i++) {
			wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			if (wareid != wareid0) {
				SizeParam sp = new SizeParam(wareid, sizenum);
				sqlsize = sp.getSqlSize();
				sqlsum = sp.getSqlSum();
				wareid0 = wareid;
				//System.out.println("wareid=" + wareid + " sqlsize=" + sqlsize + " sqlsum=" + sqlsum);
			}
			//System.out.println("wareid=" + wareid + " sqlsize=" + sqlsize + " sqlsum=" + sqlsum);
			retcs += fh + "{";
			for (int j = 0; j < cols; j++) {

				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();

				String strValue = tb.getRow(i).get(j).toString();
				//System.out.println(strKey + "=" + strValue);

				if (strKey.equals("HOUSEID"))
					houseid = Long.parseLong(strValue);
				if (strKey.equals("CUSTID"))
					custid = Long.parseLong(strValue);
				else if (strKey.equals("COLORID"))
					colorid = Long.parseLong(strValue);
				else if (strKey.equals("NOTENO"))
					noteno = strValue;
				else if (strKey.equals("NOTEDATE"))
					notedate = strValue;
				else if (strKey.equals("HANDNO"))
					handno = strValue;
				else if (strKey.equals("REMARK"))
					remark = strValue;
				else if (strKey.equals("OPERANT"))
					operant = strValue;
				else if (strKey.equals("AREANAME"))
					areaname = strValue;

				retcs += "\"" + strKey + "\":\"" + strValue + "\",";
				fh = ",";
			}
			//System.out.println(sqlsum+" "+sqlsize);
			strSql1.setLength(0);
			strSql1.append("select " + sqlsum + " \n," + sqlsize);

			strSql1.append("\n FROM warepeim a");
			strSql1.append("\n join warepeih b on a.accid=b.accid and a.noteno=b.noteno");
			strSql1.append("\n left outer join customer i on b.custid=i.custid");
			strSql1.append("\n where a.accid=" + Accid + " and b.statetag=1");
			strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and a.wareid=" + wareid);
			if (areaname.length() > 0)
				strSql1.append("\n and i.areaname = '" + areaname + "'");

			if (colorid > 0)
				strSql1.append(" and a.colorid=" + colorid);
			if (noteno.length() > 0)
				strSql1.append("\n and b.noteno = '" + noteno + "'");
			else {
				if (!Noteno.equals("")) {
					strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");
				}
				if (operant.length() > 0)
					strSql1.append("\n and b.operant = '" + operant + "'");

				if (handno.length() > 0)
					strSql1.append("\n and b.handno = '" + handno + "'");
				if (remark.length() > 0)
					strSql1.append("\n and b.remark = '" + remark + "'");
				if (notedate.length() > 0) {
					strSql1.append("\n and b.notedate >=to_date('" + notedate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')");
					strSql1.append("\n and b.notedate <=to_date('" + notedate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')");
				} else {
					strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
					strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
				}

				if (Overbj < 2)
					strSql1.append(" and b.Overbj=" + Overbj);
				if (custid > 0)
					strSql1.append(" and b.custid=" + custid);
				else if (!Custidlist.equals(""))
					strSql1.append(" and " + Func.getCondition("b.custid", Custidlist));
				if (houseid > 0)
					strSql1.append(" and b.houseid=" + houseid);
				else if (!Custidlist.equals(""))
					strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
			}

			System.out.println("1111:  " + strSql1.toString());
			Table tb1 = DbHelperSQL.Query(strSql1.toString()).getTable(1);
			String fh1 = "";
			for (int j = 1; j <= sizenum; j++) {
				retcs += fh1 + "\"AMOUNT" + j + "\":\"" + tb1.getRow(0).get("AMOUNT" + j).toString() + "\""//
						+ ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" //
						+ ",\"SIZENAME" + j + "\":\"" + tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
				fh1 = ",";
			}
			//System.out.println("22222");
			retcs += "}";
			fh = ",";
		}
		retcs += "]}";
		return retcs;
	}

	// 获取分页明细数据
	public Table GetTable(QueryParam qp) {
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
		if (Maxdate.compareTo(Currdatetime) > 0)
			Maxdate = Currdatetime;

		StringBuilder strSql = new StringBuilder();
		strSql.append("select ");
		if (!Grouplist.equals(""))
			strSql.append(Grouplist + ",");
		if (!Fieldlist.equals(""))
			Fieldlist += ",";

		if (!Grouplist.equals("")) // 汇总查询
		{
			Fieldlist += "min(rownum) as keyid";
		} else
			Fieldlist += "rownum as keyid";
		strSql.append(Fieldlist);
		strSql.append("\n FROM warepeih a");
		strSql.append("\n join warepeim b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n left outer join warecode c on b.wareid=c.wareid");
		strSql.append("\n left outer join colorcode d on b.colorid=d.colorid");
		strSql.append("\n left outer join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n left outer join brand f on c.brandid=f.brandid");
		strSql.append("\n left outer join waretype g on c.typeid=g.typeid");
		strSql.append("\n left outer join provide h on c.provid=h.provid");
		strSql.append("\n left outer join customer i on a.custid=i.custid");
		strSql.append("\n left outer join warehouse j on a.houseid=j.houseid");
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("   and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		if (Qxbj == 1) // 1 启用权限控制
		{
			// strSql.append("\n and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
			strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
			strSql.append("\n    and exists (select 1 from employecust x3 where a.cuustid=x3.brandid and x3.epid=" + Userid + ")  ");
			strSql.append("\n    and (c.provid=0 or exists (select 1 from employeprov x4 where c.provid=x4.provid and x4.epid=" + Userid + ") ) ");
		}
		// strSql.append("\n and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		if (Overbj < 2)
			strSql.append(" and a.Overbj=" + Overbj);
		if (Wareid > 0)
			strSql.append(" and b.wareid=" + Wareid);
		if (!Wareno.equals("")) {
			strSql.append(" and c.wareno like '%" + Wareno + "%'");
		}
		if (!Noteno.equals("")) {
			strSql.append(" and a.Noteno like '%" + Noteno + "%'");
		}
		if (!Custidlist.equals(""))
			strSql.append(" and " + Func.getCondition("a.custid", Custidlist));
		// if (!Provname.equals("")) {
		// strSql.append(" and i.Provname like '%" + Provname + "%'");
		// }
		if (!Warename.equals("")) {
			strSql.append(" and c.Warename like '%" + Warename + "%'");
		}
		if (!Locale.equals(""))
			strSql.append(" and c.Locale like '%" + Locale + "%'");
		if (!Seasonname.equals(""))
			strSql.append(" and c.Seasonname like '%" + Seasonname + "%'");
		if (!Prodyear.equals(""))
			strSql.append(" and c.Prodyear = '" + Prodyear + "'");
		if (!Houseidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));
		}
		if (!Brandidlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				strSql.append("  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
			} else {
				strSql.append("    and c.Typeid=" + Typeid);
			}
		if (!Providlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.provid", Providlist));
		if (!Areaname.equals(""))
			strSql.append(" and i.Areaname like '%" + Areaname + "%'");

		if (!Grouplist.equals(""))
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));
		//System.out.println(strSql.toString());
		// QueryParam qp = new QueryParam(Pageindex, Pagesize, sort);
		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		qp.setCalcfield(Calcfield);
		qp.setTotalString("\"warning\":\"" + warning + "\"");
		return DbHelperSQL.GetTable(qp);
	}

}
