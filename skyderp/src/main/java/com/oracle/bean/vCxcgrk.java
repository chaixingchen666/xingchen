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

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class vCxcgrk {
	private String Fieldlist = ""; // 要显示的项目列表

	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	private String Calcfield = ""; // 要求和的项目列表

	private Long Accid;
	private Long Userid;
	private Long Provid = (long) -1;
	private Long Wareid = (long) 0;
	// private Long Typeid;
	private String Noteno = "";
	private String Wareno = "";
	private String Warename = "";
	// private String Provname = "";
	private String Houseidlist = "";
	// private String Providlist = "";
	private String Brandidlist = "";
	private String Areaidlist = "";
	private String Providlist = "";
	private String Operant = "";
	private Integer Ntid = 2;
	private String Seasonname = "";
	private String Prodyear = "";
	private String Locale = "";
	private String Maxdate;
	private String Mindate;

	private String Findbox = "";
	private Integer Iszero = 0;
	private Long Areaid = (long) -1;
	private Long Typeid = (long) -1;

	private String Currdatetime; // 当前查询的系统时间

	private String Nowdate = "";
	private String Accbegindate = "";
	private Integer Qxbj = 0;

	private Integer Maxday = 0;

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
	}

	private Integer Issxy = 0;

	public void setIssxy(Integer issxy) {
		Issxy = issxy;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setCurrdatetime(String currdatetime) {
		Currdatetime = currdatetime;
	}

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public vCxcgrk() {// 初始值
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Maxdate = df.format(new Date());
		Mindate = Maxdate;
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Currdatetime = df.format(new Date());

	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setUserid(Long userid) {
		Userid = userid;
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

	// 尺码横向展开
	public String GetTable2Json(QueryParam qp, int sizenum) {
		Grouplist += ",c.sizegroupno";
		Table tb = GetTable(qp);

		//System.out.println(qp.getTotalString());
		String sqlsize = "";
		String sqlsum = "";
		StringBuilder strSql1 = new StringBuilder();
		long wareid = 0;
		long wareid0 = 0;
		long houseid = 0;
		long provid = 0;
		long colorid = 0;
		String notedate = "";
		String operant = "";
		String remark = "";
		String noteno = "";
		String handno = "";
		String fh = "";
		//System.out.println("aaaa: " + qp.getTotalString());
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		int rows = tb.getRowCount();
		int cols = tb.getColumnCount();
		for (int i = 0; i < rows; i++) {
			wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			if (wareid != wareid0) {
				SizeParam sp = new SizeParam(wareid, sizenum, "b.ntid", 1);
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

				if (strKey.equals("HOUSEID"))
					houseid = Long.parseLong(strValue);
				else if (strKey.equals("PROVID"))
					provid = Long.parseLong(strValue);
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
			//System.out.println(sqlsum+" "+sqlsize);
			strSql1.setLength(0);
			strSql1.append("select " + sqlsum + " \n," + sqlsize);

			strSql1.append("\n FROM wareinm a");
			strSql1.append("\n join wareinh b on a.accid=b.accid and a.noteno=b.noteno");
			// strSql1.append("\n join colorcode d on b.colorid=d.colorid");
			// strSql1.append("\n left outer join warehouse h on a.houseid=h.houseid");
			// strSql1.append("\n left outer join provide i on a.provid=i.provid");
			strSql1.append("\n where a.accid=" + Accid + " and b.statetag=1");
			if (Qxbj == 1) // 1 启用权限控制
			{
				strSql1.append("\n    and exists (select 1 from employehouse x1 where b.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
				// strSql1.append("\n and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
			}

			strSql1.append("\n and a.wareid=" + wareid);
			if (colorid > 0)
				strSql1.append("\n and a.colorid=" + colorid);
			if (noteno.length() > 0)
				strSql1.append("\n and b.noteno = '" + noteno + "'");
			else {
				if (!Noteno.equals("")) {
					strSql1.append("\n and b.Noteno like '%" + Noteno + "%'");
				}
				if (notedate.length() > 0) {
					strSql1.append("\n and b.notedate >=to_date('" + notedate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')");
					strSql1.append("\n and b.notedate <=to_date('" + notedate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')");
				} else {
					strSql1.append("\n and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
					strSql1.append("\n and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
				}
				if (operant.length() > 0)
					strSql1.append("\n and b.operant = '" + operant + "'");

				if (handno.length() > 0)
					strSql1.append("\n and b.handno = '" + handno + "'");
				if (remark.length() > 0)
					strSql1.append("\n and b.remark = '" + remark + "'");
				if (Ntid < 2)
					strSql1.append("\n and b.ntid=" + Ntid);
				if (houseid > 0) {
					strSql1.append("\n and b.houseid=" + houseid);
				} else if (!Houseidlist.equals("")) {
					strSql1.append("\n and " + Func.getCondition("b.houseid", Houseidlist));
				}
				if (provid > 0)
					strSql1.append("\n and b.provid=" + provid);
				else if (!Providlist.equals("")) {
					strSql1.append("\n and " + Func.getCondition("b.provid", Providlist));
				}
			}
			//System.out.println(strSql1.toString());
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
		//System.out.println("fieldlist=" + Fieldlist);
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
		strSql.append("\n FROM wareinh a");
		strSql.append("\n join wareinm b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n join warecode c on b.wareid=c.wareid");
		strSql.append("\n join colorcode d on b.colorid=d.colorid");
		strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n join brand f on c.brandid=f.brandid");
		strSql.append("\n join waretype g on c.typeid=g.typeid");
		strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
		strSql.append("\n left outer join provide i on a.provid=i.provid");
		strSql.append("\n left outer join area r on c.areaid=r.areaid");
		if (Issxy == 1)// 是上下衣
			strSql.append("\n left outer join housesaleprice j on b.wareid=j.wareid and a.houseid=j.houseid");

		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		//		if (htp.getQxbj() == 1) {// 1 启用权限控制
		//			strwhere += " and (a.houseid=0 or exists (select 1 from employehouse x where a.HOUSEID=x.HOUSEID and x.EPID=" + htp.getUserid() + "))";
		//			strwhere += " and (a.provid=0 or exists (select 1 from employeprov x where a.provid=x.provid and x.EPID=" + htp.getUserid() + "))";
		//		}

		if (Qxbj == 1) { // 1 启用权限控制

			strSql.append("\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
			strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
			if (Issxy == 1)
				strSql.append("\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))");
			else 
				strSql.append("\n    and exists (select 1 from employeprov x4 where a.provid=x4.provid and x4.epid=" + Userid + " ) ");
		}

		// strSql.append("\n and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		if (Ntid < 2)
			strSql.append(" and a.ntid=" + Ntid);
		if (Wareid > 0)
			strSql.append(" and b.wareid=" + Wareid);
		if (!Wareno.equals("")) {
			strSql.append(" and c.wareno like '%" + Wareno + "%'");
		}
		if (!Noteno.equals("")) {
			strSql.append(" and a.Noteno like '%" + Noteno + "%'");
		}
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
		if (!Providlist.equals("")) {
			strSql.append(" and " + Func.getCondition("a.provid", Providlist));
		}
		if (!Brandidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
		}
		if (!Areaidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("c.areaid", Areaidlist));
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				strSql.append(" and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
			} else {
				strSql.append(" and c.Typeid=" + Typeid);
			}

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

	// 获取分页明细数据
	public String GetTable2Excel(QueryParam qp, JSONObject jsonObject) {
		//System.out.println("fieldlist=" + Fieldlist);
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
		strSql.append("\n FROM wareinh a");
		strSql.append("\n join wareinm b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("\n join warecode c on b.wareid=c.wareid");
		strSql.append("\n join colorcode d on b.colorid=d.colorid");
		strSql.append("\n join sizecode e on b.sizeid=e.sizeid");
		strSql.append("\n join brand f on c.brandid=f.brandid");
		strSql.append("\n join waretype g on c.typeid=g.typeid");
		strSql.append("\n left outer join warehouse h on a.houseid=h.houseid");
		strSql.append("\n left outer join provide i on a.provid=i.provid");
		strSql.append("\n left outer join area r on c.areaid=r.areaid");
		if (Issxy == 1)// 是上下衣
			strSql.append("\n left outer join housesaleprice j on b.wareid=j.wareid and a.houseid=j.houseid");

		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");

		if (Qxbj == 1) // 1 启用权限控制
		{
			strSql.append("\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ");
			strSql.append("\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ");
			if (Issxy == 1)
				strSql.append("\n     and (c.areaid=0 or exists (select 1 from emplarea x3 where c.areaid=x3.areaid and x3.epid=" + Userid + "))");
		}

		// strSql.append("\n and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		if (Ntid < 2)
			strSql.append(" and a.ntid=" + Ntid);
		if (Wareid > 0)
			strSql.append(" and b.wareid=" + Wareid);
		if (!Wareno.equals("")) {
			strSql.append(" and c.wareno like '%" + Wareno + "%'");
		}
		if (!Noteno.equals("")) {
			strSql.append(" and a.Noteno like '%" + Noteno + "%'");
		}
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
		if (!Providlist.equals("")) {
			strSql.append(" and " + Func.getCondition("a.provid", Providlist));
		}
		if (!Brandidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("c.brandid", Brandidlist));
		}
		if (!Areaidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("c.areaid", Areaidlist));
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				strSql.append(" and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ");
			} else {
				strSql.append(" and c.Typeid=" + Typeid);
			}

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
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);
		//		qp.setTotalString("\"warning\":\"" + warning + "\"");
		//		return DbHelperSQL.GetTable(qp);
	}

	// 项目 代号 允许汇总 明细表 汇总表
	// 单据号 noteno 分组 a.noteno
	// 单据日期 notedate 分组 a.notedate to_char(a.notedate,'yyyy-mm-dd'）as notedate
	// 手工单号 handno 分组 a.handno
	// 摘要 remark a.remark
	// 制单人 operant 分组 a.operant
	// 供应商 provname 分组 a.provid,i.provname
	// 店铺 housename 分组 a.houseid,h.housename
	// 货号 wareno 分组 b.wareid,c.wareno
	// 商品名称 warename 分组 c.warename
	// 单位 units 分组 c.units
	// 品牌 brandname 分组 c.brandid,f.brandname
	// 类型 typename 分组 c.typeid,g.typename
	// 颜色 colorname 分组 b.colorid,d.colorname
	// 尺码 sizename 分组 b.sizeid,e.sizename
	// 数量 amount 汇总 case a.ntid when 0 then b.amount else -b.amount end as amount sum(case a.ntid when 0 then b.amount else -b.amount end) as amount
	// 原价 price0 汇总 b.price1
	// 折扣 discount 汇总 b.discount
	// 单价 price 汇总 b.price sum(b.curr)/sum(b.amount) as price
	// 金额 curr 汇总 case a.ntid when 0 then b.curr else -b.curr end as curr sum(case a.ntid when 0 then b.curr else -b.curr end) as curr

}
