/**
 * 
 */
package com.oracle.bean;

import com.comdot.data.Table;
import com.common.tool.Func;
//import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.QueryParam;

/**
 * @author Administrator
 *
 */
public class vTotalomorder {
	private String Fieldlist = ""; // 要显示的项目列表
	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	private String Calcfield = ""; // 要求和的项目列表

	private Long Accid;
	private Long Userid;
	private Long Omid = (long) 0;

	private Integer Qxbj = 0;
	private String Poleidlist = "";
	private String Custidlist = "";
	private String Omepidlist = "";
	private String Wareno = "";
	private String Warename = "";
	private String Seasonname = "";
	private String Prodyear = "";
	private String Locale = ""; // private String Houseidlist = "";
	private String Brandidlist = "";
	private String Typeidlist = "";

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	// 获取分页数据,尺码横向展开
	public String GetTable2Json(QueryParam qp, int sizenum) {
		Grouplist += ",b.sizegroupno";
		Table tb = GetTable(qp);

		String sqlsize = "";
		String sqlsum = "";
		String qry1;

		long wareid = 0;
		long wareid0 = 0;
		long omepid = 0;
		long custid = 0;
		long colorid = 0;
		String fh = "";
		//System.out.println("aaaa: " + qp.getTotalString());
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		//System.out.println(qp.getTotalString() );
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
			//System.out.println("wareid=" + sqlsize);
			retcs += fh + "{";
			for (int j = 0; j < cols; j++) {

				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();

				String strValue = tb.getRow(i).get(j).toString();
				//System.out.println(strKey + "=" + strValue);

				if (strKey.equals("OMEPID"))
					omepid = Long.parseLong(strValue);
				if (strKey.equals("CUSTID"))
					custid = Long.parseLong(strValue);
				else if (strKey.equals("COLORID"))
					colorid = Long.parseLong(strValue);

				retcs += "\"" + strKey + "\":\"" + strValue + "\",";
				fh = ",";
			}
			qry1 = "select " + sqlsum + "," + sqlsize;
			qry1 += "\n FROM omcustware a where a.omid=" + Omid;
			qry1 += " and a.wareid=" + wareid;
			if (custid > 0)
				qry1 += " and exists (select 1 from omuser b where a.omepid=b.omepid and b.custid=" + custid + ")";
			if (omepid > 0)
				qry1 += " and a.omepid=" + omepid;
			if (colorid > 0)
				qry1 += " and a.colorid=" + colorid;
			//System.out.println("11111");

			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
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
		strSql.append("\n FROM omcustware a");
		strSql.append("\n join warecode b on a.wareid=b.wareid");
		strSql.append("\n join colorcode d on a.colorid=d.colorid");
		strSql.append("\n join sizecode e on a.sizeid=e.sizeid");
		strSql.append("\n join brand f on b.brandid=f.brandid");
		strSql.append("\n join waretype g on b.typeid=g.typeid");
		strSql.append("\n join omuser c on a.omepid=c.omepid");
		strSql.append("\n join customer h on c.custid=h.custid");
		strSql.append("\n where a.omid=" + Omid + " and b.statetag=1 and a.amount>0");
		if (Warename.length() > 0) {
			strSql.append(" and b.Warename like '%" + Warename + "%'");
		}
		if (Wareno.length() > 0) {
			strSql.append(" and b.Wareno like '%" + Wareno + "%'");
		}
//		if (Locale.length() > 0)
//			strSql.append(" and b.Locale like '%" + Locale + "%'");
		if (Seasonname.length() > 0)
			strSql.append(" and b.Seasonname like '%" + Seasonname + "%'");
		if (Prodyear.length() > 0)
			strSql.append(" and b.Prodyear = '" + Prodyear + "'");

		if (Brandidlist.length() > 0) {
			strSql.append(" and " + Func.getCondition("b.brandid", Brandidlist));
		}
		if (Typeidlist.length() > 0) {
			strSql.append(" and " + Func.getCondition("b.typeid", Typeidlist));
		}

		if (Custidlist.length() > 0) {
			strSql.append(" and " + Func.getCondition("c.custid", Custidlist));
		}
		if (Omepidlist.length() > 0) {
			strSql.append(" and " + Func.getCondition("a.omepid", Omepidlist));
		}

		if (Poleidlist.length() > 0) {
			strSql.append("\n  and exists (select 1 from ompole a1 join ompoleware b1 on a1.poleid=b1.poleid");
			strSql.append("\n  where b1.wareid=a.wareid and a1.omid=a.omid and " + Func.getCondition("a1.poleid", Poleidlist) + ")");
		}
		if (Grouplist.length() > 0)
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));
		//System.out.println("aaa2="+strSql.toString());
		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		qp.setCalcfield(Calcfield);
		return DbHelperSQL.GetTable(qp);
	}

}
