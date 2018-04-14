/**
 * 
 */
package com.oracle.bean;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.QueryParam;

/**
 * @author Administrator
 *
 */
public class vHousecost {
	private String Fieldlist = ""; // 要显示的项目列表
	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	private String Calcfield = ""; // 要求和的项目列表
	private Long Accid;
	private Long Userid;
	private String errmess;
	private String Noteno = "";
	private String Maxdate;
	private String Mindate;
	private String Houseidlist = "";
	private String Payidlist = "";
	private String Cgidlist = "";
	private String Currdatetime;

	private String Nowdate = "";
	private String Accbegindate = "";
	private Integer Qxbj = 0;
	private Integer Maxday = 0;

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
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

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// 获取分页明细数据
	public Table GetTable(QueryParam qp) {
		if (Func.isNull(Mindate))
			Mindate = Nowdate;
		if (Func.isNull(Maxdate))
			Maxdate = Nowdate;
		if (Mindate.compareTo(Accbegindate) < 0)
			Mindate = Accbegindate;
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate;
		//System.out.println("fieldlist=" + Fieldlist);
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
			Fieldlist += "min(a.id) as keyid";
		} else
			Fieldlist += "a.id as keyid";
		strSql.append(Fieldlist);
		strSql.append(" FROM housecost a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid ");
		strSql.append(" left outer join chargescode c on a.cgid=c.cgid ");
		strSql.append(" left outer join payway d on a.payid=d.payid ");
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1");
		strSql.append("\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')");
		strSql.append("\n and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')");
		if (!Noteno.equals("")) {
			strSql.append(" and a.Noteno like '%" + Noteno + "%'");
		}
		if (!Houseidlist.equals(""))
			strSql.append(" and " + Func.getCondition("a.houseid", Houseidlist));

		if (!Payidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("a.payid", Payidlist));
		}
		if (!Cgidlist.equals("")) {
			strSql.append(" and " + Func.getCondition("a.cgid", Cgidlist));
		}
		if (Qxbj == 1)
			strSql.append("\n and exists (select 1 from employehouse x where a.HOUSEID=x.HOUSEID and x.EPID=" + Userid + ")");

		if (!Grouplist.equals(""))
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
