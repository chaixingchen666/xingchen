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
 * @author Administrator 查询批发出库，零售出库
 */
public class vCxxsck {
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

	private Long Vtid = (long) 0;
	private Long Guestid = (long) -1;
	private Long Wareid = (long) 0;
	private Long Salemanid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;

	// private Long Typeid;
	private String Noteno = "";
	private String Wareno = "";
	private String Warename = "";
	// private String Provname = "";
	private String Houseidlist = "";
	private String Handmanidlist = "";
	private String Brandidlist = "";
	private String Areaidlist = "";
	private Long Typeid = (long) -1;
	private String Custidlist = "";
	private String Providlist = "";
	private String Saleidlist = "";
	private String Areaname = "";
	private String Operant = "";
	private String Seasonname = "";
	private String Prodyear = "";
	private String Locale = ""; // private Integer Ntid = 2;
	private Integer Cxfs = 2; // 0=出库，1=退货，2=所有
	private String Xslist = "111";  //1=零售，2=批发，3=客户销售
	private Integer Spbj = 2; // 0=商品，1=赠品，2=所有

	private String Maxdate;
	private String Mindate;

	private String Currdatetime; // 当前查询的系统时间
	private String Nowdate = "";
	private String Accbegindate = "";
	private Integer Qxbj = 0;
	private Integer Issxy = 0;
	private Integer Housecostbj = 0;

	public void setIssxy(Integer issxy) {
		Issxy = issxy;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	private Integer Maxday = 0;

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setHousecostbj(Integer housecostbj) {
		Housecostbj = housecostbj;
	}

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public void setFieldlist(String fieldlist) {
		Fieldlist = fieldlist;
	}

	public void setGrouplist(String grouplist) {
		Grouplist = grouplist;
	}

	public void setSortlist(String sortlist) {
		Sortlist = sortlist;
	}

	public void setSumlist(String sumlist) {
		Sumlist = sumlist;
	}

	public void setCalcfield(String calcfield) {
		Calcfield = calcfield;
	}

	public void setMindate(String mindate) {
		Mindate = mindate;
	}

	public void setMaxdate(String maxdate) {
		Maxdate = maxdate;
	}

	public vCxxsck() {// 初始值
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Maxdate = df.format(new Date());
		Mindate = Maxdate;
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Currdatetime = df.format(new Date());

	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	// 零售出库尺码横向
	public String GetTable2Json0(QueryParam qp, int sizenum) {
		Grouplist += ",c.sizegroupno";
		Table tb = GetTable0(qp);

		String sqlsize = "";
		String sqlsum = "";
		StringBuilder strSql1 = new StringBuilder();
		long wareid = 0;
		long wareid0 = 0;
		long houseid = 0;
		long guestid = 0;
		long colorid = 0;
		long vtid = 0;
		String notedate = "";
		String operant = "";
		String remark = "";
		String noteno = "";
		String handno = "";
		String fh = "";
		// System.out.println("aaaa: " + qp.getTotalString());
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
				// System.out.println("wareid=" + wareid + " sqlsize=" + sqlsize + " sqlsum=" + sqlsum);
			}
			// System.out.println("wareid=" + sqlsize);
			retcs += fh + "{";
			for (int j = 0; j < cols; j++) {

				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();

				String strValue = tb.getRow(i).get(j).toString();
				// System.out.println(strKey + "=" + strValue);

				if (strKey.equals("HOUSEID"))
					houseid = Long.parseLong(strValue);
				else if (strKey.equals("VTID")) {
					if (strValue != null && strValue.length() > 0)
						vtid = Long.parseLong(strValue);
				} else if (strKey.equals("GUESTID"))
					guestid = Long.parseLong(strValue);
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
				retcs += "\"" + strKey + "\":\"" + strValue + "\",";
				fh = ",";
			}
			strSql1.setLength(0);
			strSql1.append("select " + sqlsum + " \n," + sqlsize);
			strSql1.append(" from (");
			strSql1.append("select sizeid,sum(amount) as amount from (");
			strSql1.append("\n select a.sizeid,sum(a.amount) as amount");
			strSql1.append("\n FROM wareoutm a");
			strSql1.append("\n join wareouth b on a.accid=b.accid and a.noteno=b.noteno");
			strSql1.append("\n left outer join guestvip i on b.guestid=i.guestid");
			strSql1.append("\n left outer join guesttype j on i.vtid=j.vtid");
			strSql1.append("\n where b.accid=" + Accid + " and b.statetag=1 and b.ntid=0");
			strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			if (Salemanid > 0)
				strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");
			strSql1.append("\n and a.wareid=" + wareid);
			if (vtid > 0)
				strSql1.append(" and i.vtid=" + vtid);
			if (colorid > 0)
				strSql1.append(" and a.colorid=" + colorid);
			if (noteno.length() > 0)
				strSql1.append("\n and b.noteno = '" + noteno + "'");
			else {
				if (!Noteno.equals(""))
					strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");
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
				if (operant.length() > 0)
					strSql1.append("\n and b.operant = '" + operant + "'");
				if (guestid > 0)
					strSql1.append(" and b.Guestid=" + guestid);
				else if (Guestid > 0)
					strSql1.append(" and b.Guestid=" + Guestid);

				if (houseid > 0) {
					strSql1.append(" and b.houseid=" + houseid);
				} else if (!Houseidlist.equals("")) {
					strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
				}
			}

			strSql1.append("\n group by a.sizeid");
			strSql1.append("\n union all");
			strSql1.append("\n select a.sizeid,sum(a.amount) as amount");
			strSql1.append("\n FROM shopsalem a");
			strSql1.append("\n join shopsaleh b on a.accid=b.accid and a.noteno=b.noteno");
			strSql1.append("\n left outer join guestvip i on b.guestid=i.guestid");
			strSql1.append("\n left outer join guesttype j on i.vtid=j.vtid");

			strSql1.append("\n where b.accid=" + Accid + " and b.statetag=1");
			strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and a.wareid=" + wareid);
			if (Salemanid > 0)
				strSql1.append("\n and a.salemanid=" + Salemanid);
			// strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");
			if (vtid > 0)
				strSql1.append(" and i.vtid=" + vtid);
			if (colorid > 0)
				strSql1.append(" and a.colorid=" + colorid);
			if (noteno.length() > 0)
				strSql1.append("\n and b.noteno = '" + noteno + "'");
			else {
				if (!Noteno.equals(""))
					strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");

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

				if (guestid > 0)
					strSql1.append(" and b.Guestid=" + guestid);
				else if (Guestid > 0)
					strSql1.append(" and b.Guestid=" + Guestid);
			}

			if (houseid > 0) {
				strSql1.append(" and b.houseid=" + houseid);
			} else if (!Houseidlist.equals("")) {
				strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
			}
			strSql1.append("\n group by a.sizeid ");
			strSql1.append("\n ) group by sizeid) a");

			// System.out.println(strSql1.toString());
			Table tb1 = DbHelperSQL.Query(strSql1.toString()).getTable(1);
			String fh1 = "";
			for (int j = 1; j <= sizenum; j++) {
				retcs += fh1 + "\"AMOUNT" + j + "\":\"" + tb1.getRow(0).get("AMOUNT" + j).toString() + "\""//
						+ ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" //
						+ ",\"SIZENAME" + j + "\":\"" + tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
				fh1 = ",";
			}
			// System.out.println("22222");
			retcs += "}";
			fh = ",";
		}
		retcs += "]}";
		return retcs;

	}

	// 零售出库尺码横向
	public String GetTable2Json00(QueryParam qp, int sizenum) {
		Grouplist += ",sizegroupno";
		Table tb = GetTable00(qp);

		String sqlsize = "";
		String sqlsum = "";
		StringBuilder strSql1 = new StringBuilder();
		long wareid = 0;
		long wareid0 = 0;
		long houseid = 0;
		long guestid = 0;
		long colorid = 0;
		long vtid = 0;
		String notedate = "";
		String operant = "";
		String remark = "";
		String noteno = "";
		String handno = "";
		String fh = "";
		// System.out.println("aaaa: " + qp.getTotalString());
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
				// System.out.println("wareid=" + wareid + " sqlsize=" + sqlsize + " sqlsum=" + sqlsum);
			}
			// System.out.println("wareid=" + sqlsize);
			retcs += fh + "{";
			for (int j = 0; j < cols; j++) {

				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();

				String strValue = tb.getRow(i).get(j).toString();
				// System.out.println(strKey + "=" + strValue);

				if (strKey.equals("HOUSEID"))
					houseid = Long.parseLong(strValue);
				else if (strKey.equals("VTID")) {
					if (strValue != null && strValue.length() > 0)
						vtid = Long.parseLong(strValue);
				} else if (strKey.equals("GUESTID"))
					guestid = Long.parseLong(strValue);
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
				retcs += "\"" + strKey + "\":\"" + strValue + "\",";
				fh = ",";
			}
			strSql1.setLength(0);
			strSql1.append("select " + sqlsum + " \n," + sqlsize);
			strSql1.append(" from (");
			strSql1.append("select sizeid,sum(amount) as amount from (");
			strSql1.append("\n select a.sizeid,sum(a.amount) as amount");
			strSql1.append("\n FROM wareoutm a");
			strSql1.append("\n join wareouth b on a.accid=b.accid and a.noteno=b.noteno");
			strSql1.append("\n left outer join guestvip i on b.guestid=i.guestid");
			strSql1.append("\n left outer join guesttype j on i.vtid=j.vtid");
			strSql1.append("\n where b.accid=" + Accid + " and b.statetag=1");
			strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and b.ntid=0");
			if (Salemanid > 0)
				strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");
			strSql1.append("\n and a.wareid=" + wareid);
			if (vtid > 0)
				strSql1.append(" and i.vtid=" + vtid);
			if (colorid > 0)
				strSql1.append(" and a.colorid=" + colorid);
			if (noteno.length() > 0)
				strSql1.append("\n and b.noteno = '" + noteno + "'");
			else {
				if (!Noteno.equals(""))
					strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");
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
				if (operant.length() > 0)
					strSql1.append("\n and b.operant = '" + operant + "'");
				if (guestid > 0)
					strSql1.append(" and b.Guestid=" + guestid);
				else if (Guestid > 0)
					strSql1.append(" and b.Guestid=" + Guestid);

				if (houseid > 0) {
					strSql1.append(" and b.houseid=" + houseid);
				} else if (!Houseidlist.equals("")) {
					strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
				}
			}

			strSql1.append("\n group by a.sizeid");
			strSql1.append("\n union all");
			strSql1.append("\n select a.sizeid,sum(a.amount) as amount");
			strSql1.append("\n FROM shopsalem a");
			strSql1.append("\n join shopsaleh b on a.accid=b.accid and a.noteno=b.noteno");
			strSql1.append("\n left outer join guestvip i on b.guestid=i.guestid");
			strSql1.append("\n left outer join guesttype j on i.vtid=j.vtid");

			strSql1.append("\n where b.accid=" + Accid + " and b.statetag=1");
			strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and a.wareid=" + wareid);
			if (Salemanid > 0)
				strSql1.append("\n and a.salemanid=" + Salemanid);
			// strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");
			if (vtid > 0)
				strSql1.append(" and i.vtid=" + vtid);
			if (colorid > 0)
				strSql1.append(" and a.colorid=" + colorid);
			if (noteno.length() > 0)
				strSql1.append("\n and b.noteno = '" + noteno + "'");
			else {
				if (!Noteno.equals(""))
					strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");

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

				if (guestid > 0)
					strSql1.append(" and b.Guestid=" + guestid);
				else if (Guestid > 0)
					strSql1.append(" and b.Guestid=" + Guestid);
			}

			if (houseid > 0) {
				strSql1.append(" and b.houseid=" + houseid);
			} else if (!Houseidlist.equals("")) {
				strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
			}
			strSql1.append("\n group by a.sizeid ");
			strSql1.append("\n ) group by sizeid) a");
			// System.out.println(strSql1.toString());
			Table tb1 = DbHelperSQL.Query(strSql1.toString()).getTable(1);
			String fh1 = "";
			for (int j = 1; j <= sizenum; j++) {
				retcs += fh1 + "\"AMOUNT" + j + "\":\"" + tb1.getRow(0).get("AMOUNT" + j).toString() + "\""//
						+ ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" //
						+ ",\"SIZENAME" + j + "\":\"" + tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
				fh1 = ",";
			}
			// System.out.println("22222");
			retcs += "}";
			fh = ",";
		}
		retcs += "]}";
		return retcs;

	}

	// 销售出库尺码横向（零售+批发+客户销售）
	public String GetTable2JsonXs(QueryParam qp, int sizenum) {
		Grouplist += ",sizegroupno";
		Table tb = GetTableXs(qp);

		String sqlsize = "";
		String sqlsum = "";
		StringBuilder strSql1 = new StringBuilder();
		long wareid = 0;
		long wareid0 = 0;
		long houseid = 0;
		long guestid = 0;
		long colorid = 0;
		long vtid = 0;
		String notedate = "";
		String operant = "";
		String remark = "";
		String noteno = "";
		String handno = "";
		String fh = "";
		// System.out.println("aaaa: " + qp.getTotalString());
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
				// System.out.println("wareid=" + wareid + " sqlsize=" + sqlsize + " sqlsum=" + sqlsum);
			}
			// System.out.println("wareid=" + sqlsize);
			retcs += fh + "{";
			for (int j = 0; j < cols; j++) {

				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();

				String strValue = tb.getRow(i).get(j).toString();
				// System.out.println(strKey + "=" + strValue);

				if (strKey.equals("HOUSEID"))
					houseid = Long.parseLong(strValue);
				else if (strKey.equals("VTID")) {
					if (strValue != null && strValue.length() > 0)
						vtid = Long.parseLong(strValue);
				} else if (strKey.equals("GUESTID"))
					guestid = Long.parseLong(strValue);
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
				retcs += "\"" + strKey + "\":\"" + strValue + "\",";
				fh = ",";
			}
			strSql1.setLength(0);
			strSql1.append("select " + sqlsum + " \n," + sqlsize);
			strSql1.append(" from (");
			strSql1.append("select sizeid,sum(amount) as amount from (");
			boolean bj = false;
			//店铺零售
			if (Func.subString(Xslist, 1, 1).equals("1")) {//Xslist = "111";  //1=零售，2=批发，3=客户销售
				strSql1.append("\n select a.sizeid,sum(a.amount) as amount");
				strSql1.append("\n FROM wareoutm a");
				strSql1.append("\n join wareouth b on a.accid=b.accid and a.noteno=b.noteno");
				strSql1.append("\n left outer join guestvip i on b.guestid=i.guestid");
				strSql1.append("\n left outer join guesttype j on i.vtid=j.vtid");
				strSql1.append("\n where b.accid=" + Accid + " and b.statetag=1");
				strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
				strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
				strSql1.append("\n and b.ntid=0");
				if (Salemanid > 0)
					strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");
				strSql1.append("\n and a.wareid=" + wareid);
				if (vtid > 0)
					strSql1.append(" and i.vtid=" + vtid);
				if (colorid > 0)
					strSql1.append(" and a.colorid=" + colorid);
				if (noteno.length() > 0)
					strSql1.append("\n and b.noteno = '" + noteno + "'");
				else {
					if (!Noteno.equals(""))
						strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");
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
					if (operant.length() > 0)
						strSql1.append("\n and b.operant = '" + operant + "'");
					if (guestid > 0)
						strSql1.append(" and b.Guestid=" + guestid);
					else if (Guestid > 0)
						strSql1.append(" and b.Guestid=" + Guestid);

					if (houseid > 0) {
						strSql1.append(" and b.houseid=" + houseid);
					} else if (!Houseidlist.equals("")) {
						strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
					}
				}

				strSql1.append("\n group by a.sizeid");
				strSql1.append("\n union all");
				//商场零售
				strSql1.append("\n select a.sizeid,sum(a.amount) as amount");
				strSql1.append("\n FROM shopsalem a");
				strSql1.append("\n join shopsaleh b on a.accid=b.accid and a.noteno=b.noteno");
				strSql1.append("\n left outer join guestvip i on b.guestid=i.guestid");
				strSql1.append("\n left outer join guesttype j on i.vtid=j.vtid");

				strSql1.append("\n where b.accid=" + Accid + " and b.statetag=1");
				strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
				strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
				strSql1.append("\n and a.wareid=" + wareid);
				if (Salemanid > 0)
					strSql1.append("\n and a.salemanid=" + Salemanid);
				// strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");
				if (vtid > 0)
					strSql1.append(" and i.vtid=" + vtid);
				if (colorid > 0)
					strSql1.append(" and a.colorid=" + colorid);
				if (noteno.length() > 0)
					strSql1.append("\n and b.noteno = '" + noteno + "'");
				else {
					if (!Noteno.equals(""))
						strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");

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

					if (guestid > 0)
						strSql1.append(" and b.Guestid=" + guestid);
					else if (Guestid > 0)
						strSql1.append(" and b.Guestid=" + Guestid);
				}

				if (houseid > 0) {
					strSql1.append(" and b.houseid=" + houseid);
				} else if (!Houseidlist.equals("")) {
					strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
				}
				strSql1.append("\n group by a.sizeid ");
				bj = true;
			}
			//批发
			if (Func.subString(Xslist, 2, 1).equals("1")) {//Xslist = "111";  //1=零售，2=批发，3=客户销售
				if (bj)
					strSql1.append("\n union all");
				strSql1.append("\n select a.sizeid,sum(a.amount) as amount");
				strSql1.append("\n FROM wareoutm a");
				strSql1.append("\n join wareouth b on a.accid=b.accid and a.noteno=b.noteno");
				//				strSql1.append("\n left outer join guestvip i on b.guestid=i.guestid");
				//				strSql1.append("\n left outer join guesttype j on i.vtid=j.vtid");
				strSql1.append("\n where b.accid=" + Accid + " and b.statetag=1");
				strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
				strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
				strSql1.append("\n and (b.ntid=1 or b.ntid=2)");
				if (Salemanid > 0)
					strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");
				strSql1.append("\n and a.wareid=" + wareid);
				//				if (vtid > 0)
				//					strSql1.append(" and i.vtid=" + vtid);
				if (colorid > 0)
					strSql1.append(" and a.colorid=" + colorid);
				if (noteno.length() > 0)
					strSql1.append("\n and b.noteno = '" + noteno + "'");
				else {
					if (!Noteno.equals(""))
						strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");
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
					if (operant.length() > 0)
						strSql1.append("\n and b.operant = '" + operant + "'");
					//					if (guestid > 0)
					//						strSql1.append(" and b.Guestid=" + guestid);
					//					else if (Guestid > 0)
					//						strSql1.append(" and b.Guestid=" + Guestid);

					if (houseid > 0) {
						strSql1.append(" and b.houseid=" + houseid);
					} else if (!Houseidlist.equals("")) {
						strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
					}
				}

				strSql1.append("\n group by a.sizeid");
				bj = true;
			}
			//客户销售
			if (Func.subString(Xslist, 3, 1).equals("1")) {//Xslist = "111";  //1=零售，2=批发，3=客户销售
				if (bj)
					strSql1.append("\n union all");
				strSql1.append("\n select a.sizeid,sum(a.amount) as amount");
				strSql1.append("\n FROM custsalem a");
				strSql1.append("\n join custsaleh b on a.accid=b.accid and a.noteno=b.noteno");
				//				strSql1.append("\n left outer join guestvip i on b.guestid=i.guestid");
				//				strSql1.append("\n left outer join guesttype j on i.vtid=j.vtid");
				strSql1.append("\n where b.accid=" + Accid + " and b.statetag=1");
				strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
				strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
				//				if (Salemanid > 0)
				//					strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");
				strSql1.append("\n and a.wareid=" + wareid);
				//				if (vtid > 0)
				//					strSql1.append(" and i.vtid=" + vtid);
				if (colorid > 0)
					strSql1.append(" and a.colorid=" + colorid);
				if (noteno.length() > 0)
					strSql1.append("\n and b.noteno = '" + noteno + "'");
				else {
					if (!Noteno.equals(""))
						strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");
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
					if (operant.length() > 0)
						strSql1.append("\n and b.operant = '" + operant + "'");
					//					if (guestid > 0)
					//						strSql1.append(" and b.Guestid=" + guestid);
					//					else if (Guestid > 0)
					//						strSql1.append(" and b.Guestid=" + Guestid);

					//					if (houseid > 0) {
					//						strSql1.append(" and b.houseid=" + houseid);
					//					} else if (!Houseidlist.equals("")) {
					//						strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
					//					}
				}

				strSql1.append("\n group by a.sizeid");
				bj = true;
			}
			strSql1.append("\n ) group by sizeid) a");
			// System.out.println(strSql1.toString());
			Table tb1 = DbHelperSQL.Query(strSql1.toString()).getTable(1);
			String fh1 = "";
			for (int j = 1; j <= sizenum; j++) {
				retcs += fh1 + "\"AMOUNT" + j + "\":\"" + tb1.getRow(0).get("AMOUNT" + j).toString() + "\""//
						+ ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" //
						+ ",\"SIZENAME" + j + "\":\"" + tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
				fh1 = ",";
			}
			// System.out.println("22222");
			retcs += "}";
			fh = ",";
		}
		retcs += "]}";
		return retcs;

	}

	// 销售出库(零售+批发+客户销售)
	public Table GetTableXs(QueryParam qp) {
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

		strSql.append(" select ");
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
		strSql.append("\n from (");
		boolean bj = false;
		//零售
		if (Func.subString(Xslist, 1, 1).equals("1")) {//Xslist = "111";  //1=零售，2=批发，3=客户销售
			strSql.append("\n select a.accid,a.noteno,a.notedate,a.houseid,a.operant,a.handno,a.remark");
			strSql.append(" ,b.wareid,b.colorid,b.sizeid,b.amount,b.price0,b.discount,b.price,b.curr,round(b.amount*b.price0,2) as curr0,b.remark0");
			strSql.append(" ,c.wareno,c.warename,c.units,d.colorname,e.sizename,e.sizeno,f.BRANDNAME,g.typename,g.fullname,h.housename,p.provname,c.areaid,r.areaname");
			//			strSql.append(" ,j.vtname,i.guestname,i.vipno,i.mobile,i.vtid,c.prodyear,c.seasonname,c.imagename0");
			strSql.append(" ,c.prodyear,c.seasonname,c.imagename0,round(b.amount*m.costprice,2) as fixedcurr");
			strSql.append(" ,c.retailsale,round(b.amount*c.retailsale,2) as retailcurr,c.provid,c.typeid,c.brandid,c.sizegroupno");
			strSql.append(" ,cast('<零售>' as nvarchar2(100)) as custname,a.custid");
			strSql.append(" ,b.curr-round(b.amount*m.costprice,2) as mlcurr");
			strSql.append("\n FROM wareouth a");
			strSql.append("\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno");
			strSql.append("\n join warecode c on b.wareid=c.wareid");
			strSql.append("\n join colorcode d on b.colorid=d.colorid");
			strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
			strSql.append("\n join brand f on c.brandid=f.brandid");
			strSql.append("\n join waretype g on c.typeid=g.typeid");
			strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
			//			strSql.append("\n left outer join guestvip i on a.guestid=i.guestid");
			//			strSql.append("\n left outer join guesttype j on i.vtid=j.vtid");
			strSql.append("\n left outer join provide p on c.provid=p.provid");
			strSql.append("\n left outer join area r on c.areaid=r.areaid");
			//			if (Issxy == 1)// 是上下衣
			//			{
			strSql.append("\n left outer join warecost m on b.wareid=m.wareid and m.daystr=to_char(a.notedate,'yyyy-mm-dd')");
			if (Housecostbj == 1)
				strSql.append(" and m.houseid=a.houseid");
			else
				strSql.append(" and m.houseid=0");
			//			}
			strSql.append("\n where a.accid=" + Accid + " and a.statetag=1  and a.ntid=0");
			strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			if (Qxbj == 1) // 1 启用权限控制
			{
				strSql.append("\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
				strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
				//			if (Issxy == 1)
				//				strSql.append("\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))");
			}

			if (Salemanid > 0)
				strSql.append("\n and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and x.epid=" + Salemanid + ")");
			//			if (Guestid > 0)
			//				strSql.append(" and a.Guestid=" + Guestid);
			//			if (Vtid > 0)
			//				strSql.append(" and i.vtid=" + Vtid);
			if (Colorid > 0)
				strSql.append(" and b.Colorid=" + Colorid);
			if (Sizeid > 0)
				strSql.append(" and b.Sizeid=" + Sizeid);
			if (Wareid > 0)
				strSql.append(" and b.wareid=" + Wareid);

			if (Wareid > 0)
				strSql.append(" and b.wareid=" + Wareid);
			if (!Wareno.equals(""))
				strSql.append(" and c.wareno like '%" + Wareno + "%'");

			if (!Noteno.equals(""))
				strSql.append(" and a.Noteno like '%" + Noteno + "%'");
			// Spbj = 2; // 0=商品，1=赠品，2=所有
			//			if (Spbj == 0)
			//				strSql.append(" and b.iszp=0");
			//			else if (Spbj == 1)
			//				strSql.append(" and b.iszp=1");
			if (!Warename.equals(""))
				strSql.append(" and c.Warename like '%" + Warename + "%'");
			if (!Locale.equals(""))
				strSql.append(" and c.Locale like '%" + Locale + "%'");
			if (!Seasonname.equals(""))
				strSql.append(" and c.Seasonname like '%" + Seasonname + "%'");
			if (!Prodyear.equals(""))
				strSql.append(" and c.Prodyear = '" + Prodyear + "'");

			if (!Houseidlist.equals(""))
				strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));

			if (!Brandidlist.equals(""))
				strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
			if (!Providlist.equals(""))
				strSql.append(" and " + Func.getCondition("c.provid", Providlist));
			if (!Areaidlist.equals(""))
				strSql.append(" and " + Func.getCondition("c.Areaid", Areaidlist));

			// if (!Typeidlist.equals(""))
			// strSql.append(" and " + Func.getCondition("c.typeid", Typeidlist));
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					strSql.append("  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
				} else {
					strSql.append("    and c.Typeid=" + Typeid);
				}

			//		if (!Grouplist.equals(""))
			//			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));

			strSql.append("\n union all ");
			// 店铺零售
			strSql.append("\n select a.accid,a.noteno,a.notedate,a.houseid,a.operant,a.handno,a.remark");
			strSql.append(" ,b.wareid,b.colorid,b.sizeid,b.amount,b.price0,b.discount,b.price,b.curr,round(b.amount*b.price0,2) as curr0,b.remark0");
			strSql.append(" ,c.wareno,c.warename,c.units,d.colorname,e.sizename,e.sizeno,f.BRANDNAME,g.typename,g.fullname,h.housename,p.provname,c.areaid,r.areaname");
			//			strSql.append(" ,j.vtname,i.guestname,i.vipno,i.mobile,i.vtid,c.prodyear,c.seasonname,c.imagename0");
			strSql.append(" ,c.prodyear,c.seasonname,c.imagename0,round(b.amount*m.costprice,2) as fixedcurr");
			strSql.append(" ,c.retailsale,round(b.amount*c.retailsale,2) as retailcurr,c.provid,c.typeid,c.brandid,c.sizegroupno");
			strSql.append(" ,cast('<零售>' as nvarchar2(100)) as custname,0 as custid");
			strSql.append(" ,b.curr-round(b.amount*m.costprice,2) as mlcurr");
			strSql.append("\n FROM shopsaleh a");
			strSql.append("\n join shopsalem b on a.accid=b.accid and a.noteno=b.noteno");
			strSql.append("\n join warecode c on b.wareid=c.wareid");
			strSql.append("\n join colorcode d on b.colorid=d.colorid");
			strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
			strSql.append("\n join brand f on c.brandid=f.brandid");
			strSql.append("\n join waretype g on c.typeid=g.typeid");
			strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
			//			strSql.append("\n left outer join guestvip i on a.guestid=i.guestid");
			//			strSql.append("\n left outer join guesttype j on i.vtid=j.vtid");
			strSql.append("\n left outer join provide p on c.provid=p.provid");
			strSql.append("\n left outer join area r on c.areaid=r.areaid");
			strSql.append("\n left outer join warecost m on b.wareid=m.wareid and m.daystr=to_char(a.notedate,'yyyy-mm-dd')");
			if (Housecostbj == 1)
				strSql.append(" and m.houseid=a.houseid");
			else
				strSql.append(" and m.houseid=0");
			strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 ");
			strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			if (Qxbj == 1) // 1 启用权限控制
			{
				strSql.append("\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
				strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
			}
			if (Salemanid > 0)
				strSql.append(" and b.Salemanid=" + Salemanid);

			if (Wareid > 0)
				strSql.append(" and b.wareid=" + Wareid);
			if (Colorid > 0)
				strSql.append(" and b.Colorid=" + Colorid);
			if (Sizeid > 0)
				strSql.append(" and b.Sizeid=" + Sizeid);
			if (!Wareno.equals("")) {
				strSql.append(" and c.wareno like '%" + Wareno + "%'");
			}
			if (!Noteno.equals("")) {
				strSql.append(" and a.Noteno like '%" + Noteno + "%'");
			}
			if (!Warename.equals(""))
				strSql.append(" and c.Warename like '%" + Warename + "%'");
			if (!Locale.equals(""))
				strSql.append(" and c.Locale like '%" + Locale + "%'");
			if (!Seasonname.equals(""))
				strSql.append(" and c.Seasonname like '%" + Seasonname + "%'");
			if (!Prodyear.equals(""))
				strSql.append(" and c.Prodyear = '" + Prodyear + "'");

			if (!Houseidlist.equals("")) {
				strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));
			}
			if (!Custidlist.equals("")) {
				strSql.append(" and " + Func.getCondition("a.custid", Custidlist));
			}
			if (!Brandidlist.equals("")) {
				strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
			}
			if (!Areaidlist.equals(""))
				strSql.append(" and " + Func.getCondition("c.Areaid", Areaidlist));
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					strSql.append("  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
				} else {
					strSql.append("    and c.Typeid=" + Typeid);
				}

			if (!Providlist.equals("")) {

				strSql.append(" and " + Func.getCondition("c.provid", Providlist));
			}
			bj = true;
		}
		//批发
		if (Func.subString(Xslist, 2, 1).equals("1")) {//Xslist = "111";  //1=零售，2=批发，3=客户销售
			if (bj)
				strSql.append("\n union all");
			strSql.append("\n select a.accid,a.noteno,a.notedate,a.houseid,a.operant,a.handno,a.remark");
			strSql.append(
					" ,b.wareid,b.colorid,b.sizeid,decode(a.ntid,2,-b.amount,b.amount) as amount,b.price0,b.discount,b.price,decode(a.ntid,2,-b.curr,b.curr) as curr,round(decode(a.ntid,2,-b.amount,b.amount)*b.price0,2) as curr0,b.remark0");
			strSql.append(" ,c.wareno,c.warename,c.units,d.colorname,e.sizename,e.sizeno,f.BRANDNAME,g.typename,g.fullname,h.housename,p.provname,c.areaid,r.areaname");
			strSql.append(" ,c.prodyear,c.seasonname,c.imagename0,round(decode(a.ntid,2,-b.amount,b.amount)*m.costprice,2) as fixedcurr");
			strSql.append(" ,c.retailsale,round(decode(a.ntid,2,-b.amount,b.amount)*c.retailsale,2) as retailcurr,c.provid,c.typeid,c.brandid,c.sizegroupno");
			strSql.append(" ,i.custname,a.custid");
			strSql.append(" ,decode(a.ntid,2,-b.curr+b.amount*m.costprice,b.curr-b.amount*m.costprice) as mlcurr");

			strSql.append("\n FROM wareouth a");
			strSql.append("\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno");
			strSql.append("\n join warecode c on b.wareid=c.wareid");
			strSql.append("\n join colorcode d on b.colorid=d.colorid");
			strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
			strSql.append("\n join brand f on c.brandid=f.brandid");
			strSql.append("\n join waretype g on c.typeid=g.typeid");
			strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
			strSql.append("\n left outer join customer i on a.custid=i.custid");
			//			strSql.append("\n left outer join guesttype j on i.vtid=j.vtid");
			strSql.append("\n left outer join provide p on c.provid=p.provid");
			strSql.append("\n left outer join area r on c.areaid=r.areaid");
			strSql.append("\n left outer join warecost m on b.wareid=m.wareid and m.daystr=to_char(a.notedate,'yyyy-mm-dd')");
			if (Housecostbj == 1)
				strSql.append(" and m.houseid=a.houseid");
			else
				strSql.append(" and m.houseid=0");
			strSql.append("\n where a.accid=" + Accid + " and a.statetag=1  and (a.ntid=1 or a.ntid=2)");
			strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			if (Qxbj == 1) // 1 启用权限控制
			{
				strSql.append("\n    and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ");
				strSql.append("\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
				strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
				//				if (Issxy == 1)
				//					strSql.append("\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))");
			}

			if (Salemanid > 0)
				strSql.append("\n and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and x.epid=" + Salemanid + ")");
			//			if (Guestid > 0)
			//				strSql.append(" and a.Guestid=" + Guestid);
			//			if (Vtid > 0)
			//				strSql.append(" and i.vtid=" + Vtid);
			if (Colorid > 0)
				strSql.append(" and b.Colorid=" + Colorid);
			if (Sizeid > 0)
				strSql.append(" and b.Sizeid=" + Sizeid);
			if (Wareid > 0)
				strSql.append(" and b.wareid=" + Wareid);

			if (Wareid > 0)
				strSql.append(" and b.wareid=" + Wareid);
			if (!Wareno.equals(""))
				strSql.append(" and c.wareno like '%" + Wareno + "%'");

			if (!Noteno.equals(""))
				strSql.append(" and a.Noteno like '%" + Noteno + "%'");
			// Spbj = 2; // 0=商品，1=赠品，2=所有
			//			if (Spbj == 0)
			//				strSql.append(" and b.iszp=0");
			//			else if (Spbj == 1)
			//				strSql.append(" and b.iszp=1");
			if (!Warename.equals(""))
				strSql.append(" and c.Warename like '%" + Warename + "%'");
			if (!Locale.equals(""))
				strSql.append(" and c.Locale like '%" + Locale + "%'");
			if (!Seasonname.equals(""))
				strSql.append(" and c.Seasonname like '%" + Seasonname + "%'");
			if (!Prodyear.equals(""))
				strSql.append(" and c.Prodyear = '" + Prodyear + "'");

			if (!Houseidlist.equals(""))
				strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));
			if (!Custidlist.equals("")) {
				strSql.append(" and " + Func.getCondition("a.custid", Custidlist));
			}

			if (!Brandidlist.equals(""))
				strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
			if (!Providlist.equals(""))
				strSql.append(" and " + Func.getCondition("c.provid", Providlist));
			if (!Areaidlist.equals(""))
				strSql.append(" and " + Func.getCondition("c.Areaid", Areaidlist));


			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					strSql.append("  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
				} else {
					strSql.append("    and c.Typeid=" + Typeid);
				}
			bj = true;
		}
		//客户销售
		if (Func.subString(Xslist, 3, 1).equals("1")) {//Xslist = "111";  //1=零售，2=批发，3=客户销售
			if (bj)
				strSql.append("\n union all");
			strSql.append("\n select a.accid,a.noteno,a.notedate,a.houseid,a.operant,a.handno,a.remark");
			strSql.append(" ,b.wareid,b.colorid,b.sizeid,b.amount,b.price0,b.discount,b.price,b.curr,round(b.amount*b.price0,2) as curr0,b.remark0");
			strSql.append(" ,c.wareno,c.warename,c.units,d.colorname,e.sizename,e.sizeno,f.BRANDNAME,g.typename,g.fullname,'' as housename,p.provname,c.areaid,r.areaname");
			strSql.append(" ,c.prodyear,c.seasonname,c.imagename0,round(b.amount*m.costprice,2) as fixedcurr");
			strSql.append(" ,c.retailsale,round(b.amount*c.retailsale,2) as retailcurr,c.provid,c.typeid,c.brandid,c.sizegroupno");
			strSql.append(" ,i.custname,a.custid");
			strSql.append(" ,b.curr-round(b.amount*m.costprice,2) as mlcurr");
			strSql.append("\n FROM custsaleh a");
			strSql.append("\n join custsalem b on a.accid=b.accid and a.noteno=b.noteno");
			strSql.append("\n join warecode c on b.wareid=c.wareid");
			strSql.append("\n join colorcode d on b.colorid=d.colorid");
			strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
			strSql.append("\n join brand f on c.brandid=f.brandid");
			strSql.append("\n join waretype g on c.typeid=g.typeid");
			strSql.append("\n left outer join customer i on a.custid=i.custid");
			//			strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
			//			strSql.append("\n left outer join guestvip i on a.guestid=i.guestid");
			//			strSql.append("\n left outer join guesttype j on i.vtid=j.vtid");
			strSql.append("\n left outer join provide p on c.provid=p.provid");
			strSql.append("\n left outer join area r on c.areaid=r.areaid");
			strSql.append("\n left outer join custcost m on b.wareid=m.wareid and m.custid=a.custid and m.daystr=to_char(a.notedate,'yyyy-mm-dd')");
			strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 ");
			strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			if (Qxbj == 1) // 1 启用权限控制
			{
				strSql.append("\n    and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ");
				strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
				//				if (Issxy == 1)
				//					strSql.append("\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))");
			}

			//			if (Salemanid > 0)
			//				strSql.append("\n and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and x.epid=" + Salemanid + ")");
			//			if (Guestid > 0)
			//				strSql.append(" and a.Guestid=" + Guestid);
			//			if (Vtid > 0)
			//				strSql.append(" and i.vtid=" + Vtid);
			if (Colorid > 0)
				strSql.append(" and b.Colorid=" + Colorid);
			if (Sizeid > 0)
				strSql.append(" and b.Sizeid=" + Sizeid);
			if (Wareid > 0)
				strSql.append(" and b.wareid=" + Wareid);

			if (Wareid > 0)
				strSql.append(" and b.wareid=" + Wareid);
			if (!Wareno.equals(""))
				strSql.append(" and c.wareno like '%" + Wareno + "%'");

			if (!Noteno.equals(""))
				strSql.append(" and a.Noteno like '%" + Noteno + "%'");
			// Spbj = 2; // 0=商品，1=赠品，2=所有
			//			if (Spbj == 0)
			//				strSql.append(" and b.iszp=0");
			//			else if (Spbj == 1)
			//				strSql.append(" and b.iszp=1");
			if (!Warename.equals(""))
				strSql.append(" and c.Warename like '%" + Warename + "%'");
			if (!Locale.equals(""))
				strSql.append(" and c.Locale like '%" + Locale + "%'");
			if (!Seasonname.equals(""))
				strSql.append(" and c.Seasonname like '%" + Seasonname + "%'");
			if (!Prodyear.equals(""))
				strSql.append(" and c.Prodyear = '" + Prodyear + "'");

//			if (!Houseidlist.equals(""))
//				strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));
			if (!Custidlist.equals("")) {
				strSql.append(" and " + Func.getCondition("a.custid", Custidlist));
			}

			if (!Brandidlist.equals(""))
				strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
			if (!Providlist.equals(""))
				strSql.append(" and " + Func.getCondition("c.provid", Providlist));
			if (!Areaidlist.equals(""))
				strSql.append(" and " + Func.getCondition("c.Areaid", Areaidlist));

			// if (!Typeidlist.equals(""))
			// strSql.append(" and " + Func.getCondition("c.typeid", Typeidlist));
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					strSql.append("  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
				} else {
					strSql.append("    and c.Typeid=" + Typeid);
				}
		}
		strSql.append("\n )");
		if (!Grouplist.equals(""))
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));

		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		qp.setCalcfield(Calcfield);

		qp.setTotalString("\"warning\":\"" + warning + "\"");
		//		 System.out.println(strSql.toString());
		// System.out.print("aaaaa");
		// LogUtils.LogDebugWrite("repCxlsck", strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 零售出库尺码横向
	public String GetTable2Json(QueryParam qp, int sizenum) {
		Grouplist += ",sizegroupno";
		Table tb = GetTable00(qp);

		String sqlsize = "";
		String sqlsum = "";
		StringBuilder strSql1 = new StringBuilder();
		long wareid = 0;
		long wareid0 = 0;
		long houseid = 0;
		long guestid = 0;
		long colorid = 0;
		long vtid = 0;
		String notedate = "";
		String operant = "";
		String remark = "";
		String noteno = "";
		String handno = "";
		String fh = "";
		// System.out.println("aaaa: " + qp.getTotalString());
		String retcs = "{\"result\":1,\"msg\":\"�����ɹ���\"," + qp.getTotalString() + ",\"rows\":[";
		int rows = tb.getRowCount();
		int cols = tb.getColumnCount();
		for (int i = 0; i < rows; i++) {
			wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			if (wareid != wareid0) {
				SizeParam sp = new SizeParam(wareid, sizenum);
				sqlsize = sp.getSqlSize();
				sqlsum = sp.getSqlSum();
				wareid0 = wareid;
				// System.out.println("wareid=" + wareid + " sqlsize=" + sqlsize + " sqlsum=" + sqlsum);
			}
			// System.out.println("wareid=" + sqlsize);
			retcs += fh + "{";
			for (int j = 0; j < cols; j++) {

				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();

				String strValue = tb.getRow(i).get(j).toString();
				// System.out.println(strKey + "=" + strValue);

				if (strKey.equals("HOUSEID"))
					houseid = Long.parseLong(strValue);
				else if (strKey.equals("VTID")) {
					if (strValue != null && strValue.length() > 0)
						vtid = Long.parseLong(strValue);
				} else if (strKey.equals("GUESTID"))
					guestid = Long.parseLong(strValue);
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
				retcs += "\"" + strKey + "\":\"" + strValue + "\",";
				fh = ",";
			}
			strSql1.setLength(0);
			strSql1.append("select " + sqlsum + " \n," + sqlsize);
			strSql1.append(" from (");
			strSql1.append("select sizeid,sum(amount) as amount from (");
			strSql1.append("\n select a.sizeid,sum(a.amount) as amount");
			strSql1.append("\n FROM wareoutm a");
			strSql1.append("\n join wareouth b on a.accid=b.accid and a.noteno=b.noteno");
			strSql1.append("\n left outer join guestvip i on b.guestid=i.guestid");
			strSql1.append("\n left outer join guesttype j on i.vtid=j.vtid");
			strSql1.append("\n where b.accid=" + Accid + " and b.statetag=1");
			strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and b.ntid=0");
			if (Salemanid > 0)
				strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");
			strSql1.append("\n and a.wareid=" + wareid);
			if (vtid > 0)
				strSql1.append(" and i.vtid=" + vtid);
			if (colorid > 0)
				strSql1.append(" and a.colorid=" + colorid);
			if (noteno.length() > 0)
				strSql1.append("\n and b.noteno = '" + noteno + "'");
			else {
				if (!Noteno.equals(""))
					strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");
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
				if (operant.length() > 0)
					strSql1.append("\n and b.operant = '" + operant + "'");
				if (guestid > 0)
					strSql1.append(" and b.Guestid=" + guestid);
				else if (Guestid > 0)
					strSql1.append(" and b.Guestid=" + Guestid);

				if (houseid > 0) {
					strSql1.append(" and b.houseid=" + houseid);
				} else if (!Houseidlist.equals("")) {
					strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
				}
			}

			strSql1.append("\n group by a.sizeid");
			strSql1.append("\n union all");
			strSql1.append("\n select a.sizeid,sum(a.amount) as amount");
			strSql1.append("\n FROM shopsalem a");
			strSql1.append("\n join shopsaleh b on a.accid=b.accid and a.noteno=b.noteno");
			strSql1.append("\n left outer join guestvip i on b.guestid=i.guestid");
			strSql1.append("\n left outer join guesttype j on i.vtid=j.vtid");

			strSql1.append("\n where b.accid=" + Accid + " and b.statetag=1");
			strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and a.wareid=" + wareid);
			if (Salemanid > 0)
				strSql1.append("\n and a.salemanid=" + Salemanid);
			// strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");
			if (vtid > 0)
				strSql1.append(" and i.vtid=" + vtid);
			if (colorid > 0)
				strSql1.append(" and a.colorid=" + colorid);
			if (noteno.length() > 0)
				strSql1.append("\n and b.noteno = '" + noteno + "'");
			else {
				if (!Noteno.equals(""))
					strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");

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

				if (guestid > 0)
					strSql1.append(" and b.Guestid=" + guestid);
				else if (Guestid > 0)
					strSql1.append(" and b.Guestid=" + Guestid);
			}

			if (houseid > 0) {
				strSql1.append(" and b.houseid=" + houseid);
			} else if (!Houseidlist.equals("")) {
				strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
			}
			strSql1.append("\n group by a.sizeid ");
			strSql1.append("\n ) group by sizeid) a");
			// System.out.println(strSql1.toString());
			Table tb1 = DbHelperSQL.Query(strSql1.toString()).getTable(1);
			String fh1 = "";
			for (int j = 1; j <= sizenum; j++) {
				retcs += fh1 + "\"AMOUNT" + j + "\":\"" + tb1.getRow(0).get("AMOUNT" + j).toString() + "\""//
						+ ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" //
						+ ",\"SIZENAME" + j + "\":\"" + tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
				fh1 = ",";
			}
			// System.out.println("22222");
			retcs += "}";
			fh = ",";
		}
		retcs += "]}";
		return retcs;

	}

	// 零售出库
	public Table GetTable00(QueryParam qp) {
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

		strSql.append(" select ");
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
		strSql.append("\n from (");
		strSql.append("\n select a.accid,a.noteno,a.notedate,a.guestid,a.houseid,a.operant,a.handno,a.remark");
		strSql.append(" ,b.wareid,b.colorid,b.sizeid,b.amount,b.price0,b.discount,b.price,b.curr,round(b.amount*b.price0,2) as curr0,b.remark0,b.iszp");
		strSql.append(" ,c.wareno,c.warename,c.units,d.colorname,e.sizename,e.sizeno,f.BRANDNAME,g.typename,g.fullname,h.housename,p.provname,c.areaid,r.areaname");
		strSql.append(" ,j.vtname,i.guestname,i.vipno,i.mobile,i.vtid,c.prodyear,c.seasonname,c.imagename0");
		strSql.append(" ,c.retailsale,round(b.amount*c.retailsale,2) as retailcurr,c.provid,c.typeid,c.brandid,c.sizegroupno");
		strSql.append("\n FROM wareouth a");
		strSql.append("\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n join warecode c on b.wareid=c.wareid");
		strSql.append("\n join colorcode d on b.colorid=d.colorid");
		strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n join brand f on c.brandid=f.brandid");
		strSql.append("\n join waretype g on c.typeid=g.typeid");
		strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
		strSql.append("\n left outer join guestvip i on a.guestid=i.guestid");
		strSql.append("\n left outer join guesttype j on i.vtid=j.vtid");
		strSql.append("\n left outer join provide p on c.provid=p.provid");
		strSql.append("\n left outer join area r on c.areaid=r.areaid");
		if (Issxy == 1)// 是上下衣
		{
			strSql.append("\n left outer join housesaleprice l on b.wareid=l.wareid and a.houseid=l.houseid");
			strSql.append("\n left outer join warecost m on b.wareid=m.wareid and m.daystr=to_char(a.notedate,'yyyy-mm-dd')");
			strSql.append(" and a.houseid=m.houseid");
		}
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1  and a.ntid=0");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		if (Qxbj == 1) // 1 启用权限控制
		{
			strSql.append("\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
			strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
			if (Issxy == 1)
				strSql.append("\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))");
		}

		if (Salemanid > 0)
			strSql.append("\n and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and x.epid=" + Salemanid + ")");
		if (Guestid > 0)
			strSql.append(" and a.Guestid=" + Guestid);
		if (Vtid > 0)
			strSql.append(" and i.vtid=" + Vtid);
		if (Colorid > 0)
			strSql.append(" and b.Colorid=" + Colorid);
		if (Sizeid > 0)
			strSql.append(" and b.Sizeid=" + Sizeid);
		if (Wareid > 0)
			strSql.append(" and b.wareid=" + Wareid);

		if (Wareid > 0)
			strSql.append(" and b.wareid=" + Wareid);
		if (!Wareno.equals(""))
			strSql.append(" and c.wareno like '%" + Wareno + "%'");

		if (!Noteno.equals(""))
			strSql.append(" and a.Noteno like '%" + Noteno + "%'");
		// Spbj = 2; // 0=商品，1=赠品，2=所有
		if (Spbj == 0)
			strSql.append(" and b.iszp=0");
		else if (Spbj == 1)
			strSql.append(" and b.iszp=1");
		if (!Warename.equals(""))
			strSql.append(" and c.Warename like '%" + Warename + "%'");
		if (!Locale.equals(""))
			strSql.append(" and c.Locale like '%" + Locale + "%'");
		if (!Seasonname.equals(""))
			strSql.append(" and c.Seasonname like '%" + Seasonname + "%'");
		if (!Prodyear.equals(""))
			strSql.append(" and c.Prodyear = '" + Prodyear + "'");

		if (!Houseidlist.equals(""))
			strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));

		if (!Brandidlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
		if (!Providlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.provid", Providlist));
		if (!Areaidlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.Areaid", Areaidlist));

		// if (!Typeidlist.equals(""))
		// strSql.append(" and " + Func.getCondition("c.typeid", Typeidlist));
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				strSql.append("  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
			} else {
				strSql.append("    and c.Typeid=" + Typeid);
			}

		//		if (!Grouplist.equals(""))
		//			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));

		strSql.append("\n union all ");
		// 店铺零售
		strSql.append("\n select a.accid,a.noteno,a.notedate,a.guestid,a.houseid,a.operant,a.handno,a.remark");
		strSql.append(" ,b.wareid,b.colorid,b.sizeid,b.amount,b.price0,b.discount,b.price,b.curr,round(b.amount*b.price0,2) as curr0,b.remark0,b.iszp");
		strSql.append(" ,c.wareno,c.warename,c.units,d.colorname,e.sizename,e.sizeno,f.BRANDNAME,g.typename,g.fullname,h.housename,p.provname,c.areaid,r.areaname");
		strSql.append(" ,j.vtname,i.guestname,i.vipno,i.mobile,i.vtid,c.prodyear,c.seasonname,c.imagename0");
		strSql.append(" ,c.retailsale,round(b.amount*c.retailsale,2) as retailcurr,c.provid,c.typeid,c.brandid,c.sizegroupno");
		strSql.append("\n FROM shopsaleh a");
		strSql.append("\n join shopsalem b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n join warecode c on b.wareid=c.wareid");
		strSql.append("\n join colorcode d on b.colorid=d.colorid");
		strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n join brand f on c.brandid=f.brandid");
		strSql.append("\n join waretype g on c.typeid=g.typeid");
		strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
		strSql.append("\n left outer join guestvip i on a.guestid=i.guestid");
		strSql.append("\n left outer join guesttype j on i.vtid=j.vtid");
		strSql.append("\n left outer join provide p on c.provid=p.provid");
		strSql.append("\n left outer join area r on c.areaid=r.areaid");
		if (Issxy == 1)// 是上下衣
		{
			strSql.append("\n left outer join housesaleprice l on b.wareid=l.wareid and a.houseid=l.houseid");
			strSql.append("\n left outer join warecost m on b.wareid=m.wareid and m.daystr=to_char(a.notedate,'yyyy-mm-dd')");
			strSql.append(" and a.houseid=m.houseid");
		}
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 ");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		if (Qxbj == 1) // 1 启用权限控制
		{
			strSql.append("\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
			strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
			if (Issxy == 1)
				strSql.append("\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))");
		}
		if (Spbj == 0)
			strSql.append(" and b.iszp=0");
		else if (Spbj == 1)
			strSql.append(" and b.iszp=1");

		if (Salemanid > 0)
			strSql.append(" and b.Salemanid=" + Salemanid);

		if (Guestid > 0)
			strSql.append(" and a.Guestid=" + Guestid);
		if (Vtid > 0)
			strSql.append(" and i.vtid=" + Vtid);
		if (Wareid > 0)
			strSql.append(" and b.wareid=" + Wareid);
		if (Colorid > 0)
			strSql.append(" and b.Colorid=" + Colorid);
		if (Sizeid > 0)
			strSql.append(" and b.Sizeid=" + Sizeid);
		if (!Wareno.equals("")) {
			strSql.append(" and c.wareno like '%" + Wareno + "%'");
		}
		if (!Noteno.equals("")) {
			strSql.append(" and a.Noteno like '%" + Noteno + "%'");
		}
		if (!Warename.equals(""))
			strSql.append(" and c.Warename like '%" + Warename + "%'");
		if (!Locale.equals(""))
			strSql.append(" and c.Locale like '%" + Locale + "%'");
		if (!Seasonname.equals(""))
			strSql.append(" and c.Seasonname like '%" + Seasonname + "%'");
		if (!Prodyear.equals(""))
			strSql.append(" and c.Prodyear = '" + Prodyear + "'");

		if (!Houseidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));
		}
		if (!Brandidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
		}
		if (!Areaidlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.Areaid", Areaidlist));
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				strSql.append("  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
			} else {
				strSql.append("    and c.Typeid=" + Typeid);
			}

		if (!Providlist.equals("")) {

			strSql.append(" and " + Func.getCondition("c.provid", Providlist));
		}
		strSql.append("\n )");
		if (!Grouplist.equals(""))
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));

		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		qp.setCalcfield(Calcfield);

		qp.setTotalString("\"warning\":\"" + warning + "\"");
		//		 System.out.println(strSql.toString());
		// System.out.print("aaaaa");
		// LogUtils.LogDebugWrite("repCxlsck", strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 零售出库
	public Table GetTable0(QueryParam qp) {
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

		strSql.append(" select ");
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

		strSql.append("\n FROM wareouth a");
		strSql.append("\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n join warecode c on b.wareid=c.wareid");
		strSql.append("\n join colorcode d on b.colorid=d.colorid");
		strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n join brand f on c.brandid=f.brandid");
		strSql.append("\n join waretype g on c.typeid=g.typeid");
		strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
		strSql.append("\n left outer join guestvip i on a.guestid=i.guestid");
		strSql.append("\n left outer join guesttype j on i.vtid=j.vtid");
		strSql.append("\n left outer join provide p on c.provid=p.provid");
		strSql.append("\n left outer join area r on c.areaid=r.areaid");
		if (Issxy == 1)// 是上下衣
		{
			strSql.append("\n left outer join housesaleprice l on b.wareid=l.wareid and a.houseid=l.houseid");
			strSql.append("\n left outer join warecost m on b.wareid=m.wareid and m.daystr=to_char(a.notedate,'yyyy-mm-dd')");
			strSql.append(" and a.houseid=m.houseid");
		}
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1  and a.ntid=0");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");

		if (Qxbj == 1) // 1 启用权限控制
		{
			strSql.append("\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
			strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
			if (Issxy == 1)
				strSql.append("\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))");
		}

		if (Salemanid > 0)
			strSql.append("\n and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and x.epid=" + Salemanid + ")");
		if (Guestid > 0)
			strSql.append(" and a.Guestid=" + Guestid);
		if (Vtid > 0)
			strSql.append(" and i.vtid=" + Vtid);
		if (Colorid > 0)
			strSql.append(" and b.Colorid=" + Colorid);
		if (Sizeid > 0)
			strSql.append(" and b.Sizeid=" + Sizeid);
		if (Wareid > 0)
			strSql.append(" and b.wareid=" + Wareid);

		if (Wareid > 0)
			strSql.append(" and b.wareid=" + Wareid);
		if (!Wareno.equals(""))
			strSql.append(" and c.wareno like '%" + Wareno + "%'");

		if (!Noteno.equals(""))
			strSql.append(" and a.Noteno like '%" + Noteno + "%'");
		// Spbj = 2; // 0=商品，1=赠品，2=所有
		if (Spbj == 0)
			strSql.append(" and b.iszp=0");
		else if (Spbj == 1)
			strSql.append(" and b.iszp=1");
		if (!Warename.equals(""))
			strSql.append(" and c.Warename like '%" + Warename + "%'");
		if (!Locale.equals(""))
			strSql.append(" and c.Locale like '%" + Locale + "%'");
		if (!Seasonname.equals(""))
			strSql.append(" and c.Seasonname like '%" + Seasonname + "%'");
		if (!Prodyear.equals(""))
			strSql.append(" and c.Prodyear = '" + Prodyear + "'");

		if (!Houseidlist.equals(""))
			strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));

		if (!Brandidlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
		if (!Providlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.provid", Providlist));
		if (!Areaidlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.Areaid", Areaidlist));

		// if (!Typeidlist.equals(""))
		// strSql.append(" and " + Func.getCondition("c.typeid", Typeidlist));
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				strSql.append("  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
			} else {
				strSql.append("    and c.Typeid=" + Typeid);
			}

		if (!Grouplist.equals(""))
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));

		strSql.append("\n union all ");
		// 店铺零售
		strSql.append("\n select ");
		if (!Grouplist.equals(""))

			strSql.append(Grouplist + ",");
		if (!Fieldlist.equals(""))
			strSql.append(Fieldlist);
		strSql.append("\n FROM shopsaleh a");
		strSql.append("\n join shopsalem b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n join warecode c on b.wareid=c.wareid");
		strSql.append("\n join colorcode d on b.colorid=d.colorid");
		strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n join brand f on c.brandid=f.brandid");
		strSql.append("\n join waretype g on c.typeid=g.typeid");
		strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
		strSql.append("\n left outer join guestvip i on a.guestid=i.guestid");
		strSql.append("\n left outer join guesttype j on i.vtid=j.vtid");
		strSql.append("\n left outer join provide p on c.provid=p.provid");
		strSql.append("\n left outer join area r on c.areaid=r.areaid");
		if (Issxy == 1)// 是上下衣
		{
			strSql.append("\n left outer join housesaleprice l on b.wareid=l.wareid and a.houseid=l.houseid");
			strSql.append("\n left outer join warecost m on b.wareid=m.wareid and m.daystr=to_char(a.notedate,'yyyy-mm-dd')");
			strSql.append(" and a.houseid=m.houseid");
		}
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 ");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		if (Qxbj == 1) // 1 启用权限控制
		{
			strSql.append("\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
			strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
			if (Issxy == 1)
				strSql.append("\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))");
		}
		if (Spbj == 0)
			strSql.append(" and b.iszp=0");
		else if (Spbj == 1)
			strSql.append(" and b.iszp=1");

		if (Salemanid > 0)
			strSql.append(" and b.Salemanid=" + Salemanid);

		if (Guestid > 0)
			strSql.append(" and a.Guestid=" + Guestid);
		if (Vtid > 0)
			strSql.append(" and i.vtid=" + Vtid);
		if (Wareid > 0)
			strSql.append(" and b.wareid=" + Wareid);
		if (Colorid > 0)
			strSql.append(" and b.Colorid=" + Colorid);
		if (Sizeid > 0)
			strSql.append(" and b.Sizeid=" + Sizeid);
		if (!Wareno.equals("")) {
			strSql.append(" and c.wareno like '%" + Wareno + "%'");
		}
		if (!Noteno.equals("")) {
			strSql.append(" and a.Noteno like '%" + Noteno + "%'");
		}
		if (!Warename.equals(""))
			strSql.append(" and c.Warename like '%" + Warename + "%'");
		if (!Locale.equals(""))
			strSql.append(" and c.Locale like '%" + Locale + "%'");
		if (!Seasonname.equals(""))
			strSql.append(" and c.Seasonname like '%" + Seasonname + "%'");
		if (!Prodyear.equals(""))
			strSql.append(" and c.Prodyear = '" + Prodyear + "'");

		if (!Houseidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));
		}
		if (!Brandidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
		}
		if (!Areaidlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.Areaid", Areaidlist));
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				strSql.append("  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
			} else {
				strSql.append("    and c.Typeid=" + Typeid);
			}

		if (!Providlist.equals("")) {

			strSql.append(" and " + Func.getCondition("c.provid", Providlist));
		}

		if (!Grouplist.equals(""))
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));

		// System.out.println("11111");
		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		qp.setCalcfield(Calcfield);

		qp.setTotalString("\"warning\":\"" + warning + "\"");
		// System.out.print("aaaaa");
		// LogUtils.LogDebugWrite("repCxlsck", strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	/************
	 * // // 零售出库 public Table GetTable0(QueryParam qp) { if (Func.isNull(Mindate)) Mindate = Nowdate; if (Func.isNull(Maxdate)) Maxdate = Nowdate;
	 * 
	 * MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday); Mindate = mdv.getMindate(); String warning = mdv.getErrmess();
	 * 
	 * if (Mindate.compareTo(Accbegindate) < 0) Mindate = Accbegindate; if (Mindate.compareTo(Maxdate) > 0) Maxdate = Mindate;
	 * 
	 * if (Mindate.length() <= 10) Mindate += " 00:00:00"; if (Maxdate.length() <= 10) Maxdate += " 23:59:59"; if (Maxdate.compareTo(Currdatetime) > 0) Maxdate = Currdatetime;
	 * 
	 * StringBuilder strSql = new StringBuilder();
	 * 
	 * strSql.append(" select "); if (!Grouplist.equals("")) strSql.append(Grouplist + ","); if (!Fieldlist.equals("")) Fieldlist += ",";
	 * 
	 * if (!Grouplist.equals("")) // 汇总查询 { Fieldlist += "min(rownum) as keyid"; } else Fieldlist += "rownum as keyid";
	 * 
	 * strSql.append(Fieldlist); strSql.append("\n FROM wareouth a"); strSql.append("\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno"); strSql.append("\n join warecode c on b.wareid=c.wareid"); strSql.append("\n join colorcode d on b.colorid=d.colorid"); strSql.append("\n join sizecode e on b.sizeid=e.sizeid"); strSql.append("\n join brand f on c.brandid=f.brandid"); strSql.append("\n join waretype g on c.typeid=g.typeid"); strSql.append("\n left outer join warehouse h on a.houseid=h.houseid"); strSql.append("\n left outer join guestvip i on a.guestid=i.guestid"); strSql.append("\n left outer join guesttype j on i.vtid=j.vtid"); strSql.append("\n left outer join provide p on c.provid=p.provid"); strSql.append("\n left outer join area r on c.areaid=r.areaid"); if (Issxy == 1)// 是上下衣 { strSql.append("\n left outer join housesaleprice l on b.wareid=l.wareid and a.houseid=l.houseid"); strSql.append("\n left outer join warecost m on b.wareid=m.wareid and
	 * m.daystr=to_char(a.notedate,'yyyy-mm-dd')"); strSql.append(" and a.houseid=m.houseid"); } strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 "); strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')"); strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')"); strSql.append("\n and a.ntid=0"); if (Qxbj == 1) // 1 启用权限控制 { strSql.append("\n and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) "); strSql.append("\n and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) "); if (Issxy == 1) strSql.append("\n and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))"); }
	 * 
	 * if (Salemanid > 0) strSql.append("\n and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and x.epid=" + Salemanid + ")"); if (Guestid > 0) strSql.append(" and a.Guestid=" + Guestid); if (Vtid > 0) strSql.append(" and i.vtid=" + Vtid); if (Colorid > 0) strSql.append(" and b.Colorid=" + Colorid); if (Sizeid > 0) strSql.append(" and b.Sizeid=" + Sizeid); if (Wareid > 0) strSql.append(" and b.wareid=" + Wareid);
	 * 
	 * if (Wareid > 0) strSql.append(" and b.wareid=" + Wareid); if (!Wareno.equals("")) strSql.append(" and c.wareno like '%" + Wareno + "%'");
	 * 
	 * if (!Noteno.equals("")) strSql.append(" and a.Noteno like '%" + Noteno + "%'"); // Spbj = 2; // 0=商品，1=赠品，2=所有 if (Spbj == 0) strSql.append(" and b.iszp=0"); else if (Spbj == 1) strSql.append(" and b.iszp=1"); if (!Warename.equals("")) strSql.append(" and c.Warename like '%" + Warename + "%'"); if (!Locale.equals("")) strSql.append(" and c.Locale like '%" + Locale + "%'"); if (!Seasonname.equals("")) strSql.append(" and c.Seasonname like '%" + Seasonname + "%'"); if (!Prodyear.equals("")) strSql.append(" and c.Prodyear = '" + Prodyear + "'");
	 * 
	 * if (!Houseidlist.equals("")) strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));
	 * 
	 * if (!Brandidlist.equals("")) strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist)); if (!Providlist.equals("")) strSql.append(" and " + Func.getCondition("c.provid", Providlist)); if (!Areaidlist.equals("")) strSql.append(" and " + Func.getCondition("c.Areaid", Areaidlist));
	 * 
	 * // if (!Typeidlist.equals("")) // strSql.append(" and " + Func.getCondition("c.typeid", Typeidlist)); if (Typeid >= 0) if (Typeid > 0 && Typeid < 1000) { strSql.append(" and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") "); } else { strSql.append(" and c.Typeid=" + Typeid); }
	 * 
	 * if (!Grouplist.equals("")) strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));
	 * 
	 * strSql.append("\n union all "); // 店铺零售 strSql.append("\n select "); if (!Grouplist.equals(""))
	 * 
	 * strSql.append(Grouplist + ","); if (!Fieldlist.equals("")) strSql.append(Fieldlist); strSql.append("\n FROM shopsaleh a"); strSql.append("\n join shopsalem b on a.accid=b.accid and a.noteno=b.noteno"); strSql.append("\n join warecode c on b.wareid=c.wareid"); strSql.append("\n join colorcode d on b.colorid=d.colorid"); strSql.append("\n join sizecode e on b.sizeid=e.sizeid"); strSql.append("\n join brand f on c.brandid=f.brandid"); strSql.append("\n join waretype g on c.typeid=g.typeid"); strSql.append("\n left outer join warehouse h on b.houseid=h.houseid"); strSql.append("\n left outer join guestvip i on a.guestid=i.guestid"); strSql.append("\n left outer join guesttype j on i.vtid=j.vtid"); strSql.append("\n left outer join provide p on c.provid=p.provid"); strSql.append("\n left outer join area r on c.areaid=r.areaid"); if (Issxy == 1)// 是上下衣 { strSql.append("\n left outer join housesaleprice l on b.wareid=l.wareid and b.houseid=l.houseid"); strSql.append("\n left outer join
	 * warecost m on b.wareid=m.wareid and m.daystr=to_char(a.notedate,'yyyy-mm-dd')"); strSql.append(" and b.houseid=m.houseid"); } strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 "); strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')"); strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')"); if (Qxbj == 1) // 1 启用权限控制 { strSql.append("\n and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) "); strSql.append("\n and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) "); if (Issxy == 1) strSql.append("\n and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))"); } if (Spbj == 0) strSql.append(" and b.iszp=0"); else if (Spbj == 1) strSql.append(" and b.iszp=1");
	 * 
	 * if (Salemanid > 0) strSql.append(" and b.Salemanid=" + Salemanid);
	 * 
	 * if (Guestid > 0) strSql.append(" and a.Guestid=" + Guestid); if (Vtid > 0) strSql.append(" and i.vtid=" + Vtid); if (Wareid > 0) strSql.append(" and b.wareid=" + Wareid); if (Colorid > 0) strSql.append(" and b.Colorid=" + Colorid); if (Sizeid > 0) strSql.append(" and b.Sizeid=" + Sizeid); if (!Wareno.equals("")) { strSql.append(" and c.wareno like '%" + Wareno + "%'"); } if (!Noteno.equals("")) { strSql.append(" and a.Noteno like '%" + Noteno + "%'"); } if (!Warename.equals("")) strSql.append(" and c.Warename like '%" + Warename + "%'"); if (!Locale.equals("")) strSql.append(" and c.Locale like '%" + Locale + "%'"); if (!Seasonname.equals("")) strSql.append(" and c.Seasonname like '%" + Seasonname + "%'"); if (!Prodyear.equals("")) strSql.append(" and c.Prodyear = '" + Prodyear + "'");
	 * 
	 * if (!Houseidlist.equals("")) { strSql.append(" and " + Func.getCondition("b.houseid", Houseidlist)); } if (!Brandidlist.equals("")) { strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist)); } if (!Areaidlist.equals("")) strSql.append(" and " + Func.getCondition("c.Areaid", Areaidlist)); if (Typeid >= 0) if (Typeid > 0 && Typeid < 1000) { strSql.append(" and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") "); } else { strSql.append(" and c.Typeid=" + Typeid); }
	 * 
	 * if (!Providlist.equals("")) {
	 * 
	 * strSql.append(" and " + Func.getCondition("c.provid", Providlist)); }
	 * 
	 * if (!Grouplist.equals("")) strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));
	 * 
	 * // System.out.println("11111"); qp.setQueryString(strSql.toString()); if (Sortlist.equals("")) Sortlist = "keyid"; else Sortlist += ",keyid"; qp.setSortString(Sortlist); qp.setSumString(Sumlist); qp.setCalcfield(Calcfield);
	 * 
	 * qp.setTotalString("\"warning\":\"" + warning + "\""); // System.out.print("aaaaa"); // LogUtils.LogDebugWrite("repCxlsck", strSql.toString()); return DbHelperSQL.GetTable(qp); } //
	 **************/

	// 批发 尺码横向展开
	public String GetTable2Json1(QueryParam qp, int sizenum) {
		if (Cxfs == null)
			Cxfs = 2;
		Grouplist += ",c.sizegroupno";
		Table tb = GetTable1(qp);

		String sqlsize = "";
		String sqlsum = "";
		StringBuilder strSql1 = new StringBuilder();
		long wareid = 0;
		long wareid0 = 0;
		long houseid = 0;
		long custid = 0;
		long colorid = 0;
		long handmanid = 0;
		long saleid = 0;
		String notedate = "";
		String operant = "";
		String remark = "";
		String noteno = "";
		String handno = "";
		String areaname = "";
		String fh = "";
		// System.out.println("aaaa: " + qp.getTotalString());
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		int rows = tb.getRowCount();
		int cols = tb.getColumnCount();
		for (int i = 0; i < rows; i++) {
			wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			if (wareid != wareid0) {
				SizeParam sp = new SizeParam(wareid, sizenum, "b.ntid", 2);
				sqlsize = sp.getSqlSize();
				sqlsum = sp.getSqlSum();
				wareid0 = wareid;
				// System.out.println("wareid=" + wareid + " sqlsize=" + sqlsize + " sqlsum=" + sqlsum);
			}
			// System.out.println("wareid=" + sqlsize);
			retcs += fh + "{";
			for (int j = 0; j < cols; j++) {

				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();

				String strValue = tb.getRow(i).get(j).toString();
				// System.out.println(strKey + "=" + strValue);

				if (strKey.equals("HOUSEID"))
					houseid = Long.parseLong(strValue);
				if (strKey.equals("SALEID"))
					saleid = Long.parseLong(strValue);
				else if (strKey.equals("CUSTID"))
					custid = Long.parseLong(strValue);
				else if (strKey.equals("HANDMANID"))
					handmanid = Long.parseLong(strValue);
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
			strSql1.setLength(0);
			strSql1.append("select " + sqlsum + "," + sqlsize);

			strSql1.append("\n FROM wareoutm a");
			strSql1.append("\n join wareouth b on a.accid=b.accid and a.noteno=b.noteno");
			// strSql1.append("\n join colorcode d on b.colorid=d.colorid");
			// strSql1.append("\n left outer join warehouse h on a.houseid=h.houseid");
			// strSql1.append("\n left outer join provide i on a.provid=i.provid");
			strSql1.append("\n left outer join customer i on b.custid=i.custid");
			strSql1.append("\n where a.accid=" + Accid + " and b.statetag=1");
			strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
			strSql1.append("\n and a.wareid=" + wareid);
			if (Salemanid > 0)
				strSql1.append("\n and exists (select 1 from waresaleman x where b.accid=x.accid and b.noteno=x.noteno and x.epid=" + Salemanid + ")");

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

				if (custid > 0)
					strSql1.append(" and b.custid=" + custid);
				else if (!Custidlist.equals(""))
					strSql1.append(" and " + Func.getCondition("b.custid", Custidlist));

				// strSql.append("\n and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
				if (Cxfs == 0)// 批发 出库
					strSql1.append(" and b.ntid=1");
				else if (Cxfs == 1)// 批发退库
					strSql1.append(" and b.ntid=2");
				else
					strSql1.append(" and (b.ntid=1 or b.ntid=2)");

				if (houseid > 0) {
					strSql1.append(" and b.houseid=" + houseid);
				} else if (!Houseidlist.equals("")) {
					strSql1.append(" and " + Func.getCondition("b.houseid", Houseidlist));
				}
				if (handmanid > 0)
					strSql1.append(" and i.handmanid=" + handmanid);
				else if (!Handmanidlist.equals(""))
					strSql1.append(" and " + Func.getCondition("i.handmanid", Handmanidlist));
			}
			if (saleid > 0)
				strSql1.append(" and a.saleid=" + saleid);
			else if (!Saleidlist.equals(""))
				strSql1.append(" and " + Func.getCondition("a.saleid", Saleidlist));

			// if (!Providlist.equals("")) {
			// strSql1.append(" and " + Func.getCondition("b.provid", Providlist));
			// }
			// System.out.println("0000:"+strSql1.toString());

			Table tb1 = DbHelperSQL.Query(strSql1.toString()).getTable(1);
			String fh1 = "";
			for (int j = 1; j <= sizenum; j++) {
				retcs += fh1 + "\"AMOUNT" + j + "\":\"" + tb1.getRow(0).get("AMOUNT" + j).toString() + "\""//
						+ ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" //
						+ ",\"SIZENAME" + j + "\":\"" + tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
				fh1 = ",";
			}
			// System.out.println("22222");
			retcs += "}";
			fh = ",";
		}
		retcs += "]}";
		return retcs;

	}

	// 批发出库
	public Table GetTable1(QueryParam qp) {
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
		strSql.append("\n FROM wareouth a");
		strSql.append("\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n left outer join warecode c on b.wareid=c.wareid");
		strSql.append("\n left outer join colorcode d on b.colorid=d.colorid");
		strSql.append("\n left outer join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n left outer join brand f on c.brandid=f.brandid");
		strSql.append("\n left outer join waretype g on c.typeid=g.typeid");
		strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
		strSql.append("\n left outer join customer i on a.custid=i.custid");
		strSql.append("\n left outer join employe j on i.handmanid=j.epid");
		strSql.append("\n left outer join salecode k on b.saleid=k.saleid");
		strSql.append("\n left outer join provide p on c.provid=p.provid");
		strSql.append("\n left outer join area r on c.areaid=r.areaid");
		strSql.append("\n left outer join department l on a.dptid=l.dptid");

		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		if (Qxbj == 1) // 1 启用权限控制
		{
			strSql.append("\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
			strSql.append("\n    and exists (select 1 from employecust x4 where a.custid=x4.custid and x4.epid=" + Userid + " ) ");
			strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
		}
		if (Salemanid > 0)
			strSql.append("\n and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and x.epid=" + Salemanid + ")");
		if (Cxfs == 0)// 批发 出库
			strSql.append(" and a.ntid=1");
		else if (Cxfs == 1)// 批发退库
			strSql.append(" and a.ntid=2");
		else
			strSql.append(" and (a.ntid=1 or a.ntid=2)");
		if (Colorid > 0)
			strSql.append(" and b.Colorid=" + Colorid);
		if (Sizeid > 0)
			strSql.append(" and b.Sizeid=" + Sizeid);

		if (Wareid > 0)
			strSql.append(" and b.wareid=" + Wareid);
		if (!Wareno.equals("")) {
			strSql.append(" and c.wareno like '%" + Wareno + "%'");
		}
		if (!Warename.equals(""))
			strSql.append(" and c.Warename like '%" + Warename + "%'");
		if (!Locale.equals(""))
			strSql.append(" and c.Locale like '%" + Locale + "%'");
		if (!Seasonname.equals(""))
			strSql.append(" and c.Seasonname like '%" + Seasonname + "%'");
		if (!Prodyear.equals(""))
			strSql.append(" and c.Prodyear = '" + Prodyear + "'");

		if (!Noteno.equals("")) {
			strSql.append(" and a.Noteno like '%" + Noteno + "%'");
		}
		if (!Custidlist.equals(""))
			strSql.append(" and " + Func.getCondition("a.custid", Custidlist));
		if (!Handmanidlist.equals(""))
			strSql.append(" and " + Func.getCondition("i.handmanid", Handmanidlist));
		if (!Warename.equals("")) {
			strSql.append(" and c.Warename like '%" + Warename + "%'");
		}
		if (!Houseidlist.equals(""))
			strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));

		if (!Brandidlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
		if (!Areaidlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.areaid", Areaidlist));

		if (!Providlist.equals(""))
			strSql.append(" and " + Func.getCondition("c.provid", Providlist));
		// if (!Typeidlist.equals(""))
		// strSql.append(" and " + Func.getCondition("c.typeid", Typeidlist));
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				strSql.append("  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
			} else {
				strSql.append("    and c.Typeid=" + Typeid);
			}

		if (!Areaname.equals(""))
			strSql.append(" and i.Areaname like '%" + Areaname + "%'");
		if (!Saleidlist.equals(""))
			strSql.append(" and " + Func.getCondition("b.saleid", Saleidlist));

		if (!Grouplist.equals(""))
			strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));
		// System.out.println(strSql.toString());
		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		// System.out.println(strSql.toString());
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		qp.setCalcfield(Calcfield);
		qp.setTotalString("\"warning\":\"" + warning + "\"");
		return DbHelperSQL.GetTable(qp);
	}

	// 零售及批发出库
	public Table GetTable2(QueryParam qp) {
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

		strSql.append(" select notedate,noteno,custname,warename,wareno,units,colorname,sizename,amount,price,curr,id,fs from (");
		// 批发
		strSql.append("\n select a.notedate,a.noteno,c.warename,c.wareno,c.units,d.colorname,e.sizename,f.custname,b.id,0 as fs");
		strSql.append("\n,decode(a.ntid,1,b.amount,-b.amount) as amount,b.price,decode(a.ntid,1,b.curr,-b.curr) as curr");
		strSql.append("\n FROM wareouth a");
		strSql.append("\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n join warecode c on b.wareid=c.wareid");
		strSql.append("\n join colorcode d on b.colorid=d.colorid");
		strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n left outer join customer f on a.custid=f.custid");
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 and (a.ntid=1 or a.ntid=2) ");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");

		strSql.append("\n union all ");
		// 零售
		strSql.append("\n select a.notedate,a.noteno,c.warename,c.wareno,c.units,d.colorname,e.sizename,cast('店铺零售' as nvarchar2(10)) as custname,b.id,1 as fs");
		strSql.append("\n,b.amount,b.price,b.curr");
		strSql.append("\n FROM wareouth a");
		strSql.append("\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n join warecode c on b.wareid=c.wareid");
		strSql.append("\n join colorcode d on b.colorid=d.colorid");
		strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 and a.ntid=0 ");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");

		strSql.append("\n union all ");
		// 商场零售
		strSql.append("\n select a.notedate,a.noteno,c.warename,c.wareno,c.units,d.colorname,e.sizename,cast('商场零售' as nvarchar2(10)) as custname,b.id,2 as fs");
		strSql.append("\n,b.amount,b.price,b.curr");
		strSql.append("\n FROM shopsaleh a");
		strSql.append("\n join shopsalem b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n join warecode c on b.wareid=c.wareid");
		strSql.append("\n join colorcode d on b.colorid=d.colorid");
		strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 ");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n)");
		// System.out.println(strSql.toString());
		qp.setQueryString(strSql.toString());
		qp.setSumString("nvl(sum(amount),0) as totalamt,nvl(sum(curr),0) as totalcurr");
		qp.setSortString("notedate,fs,id");

		qp.setTotalString("\"warning\":\"" + warning + "\"");
		// System.out.print("aaaaa");
		// LogUtils.LogDebugWrite("repCxlsck", strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

}
