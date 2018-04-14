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
public class vTotalGuestbal {

	private String Fieldlist = ""; // 要显示的项目列表
	// private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	// private String Calcfield = ""; // 要求和的项目列表

	private Long Accid = (long) 0;
	private Long Userid = (long) 0;

	private String Maxdate;
	private String Mindate;
	private String Houseidlist = "";
	private String Fkhouseidlist = "";
	private String Vtidlist = "";
	private String Vipno = "";
	private String Guestname = "";
	private String Lastop = "";

	private String errmess = "";

	private String Nowdate = "";
	private String Accbegindate = "";
	private Integer Maxday = 0;

	public String getErrmess() {
		return errmess;
	}

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public int Total() {
		if (Func.isNull(Mindate) || Func.isNull(Maxdate)) {
			errmess = "mindate或maxdate不是一个有效值";
			return 0;
		}
		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		String warning = mdv.getErrmess();

		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate;

		if (Mindate.length() <= 10)
			Mindate += " 00:00:00";
		if (Maxdate.length() <= 10)
			Maxdate += " 23:59:59";
		String qry = "declare";
		qry += "\n begin";
		qry += "\n    delete from v_guestbal_data where epid=" + Userid + ";";
		qry += "\n    insert into v_guestbal_data (id,epid,guestid,cent0,cent1,curr0,curr1)";
		qry += "\n    select v_guestbal_data_id.nextval," + Userid + ",guestid,cent0,cent1,curr0,curr1 from (";
		// 积分
		qry += "\n    select guestid,sum(cent0) as cent0,sum(cent1) as cent1,sum(curr0) as curr0,sum(curr1) as curr1 from (";
		qry += "\n    select a.guestid,sum(decode(a.fs,0,a.cent,0)) as cent0,sum(decode(a.fs,1,a.cent,0)) as cent1,0 as curr0,0 as curr1 ";
		qry += "\n    from guestcent a join guestvip b on a.guestid=b.guestid";
		qry += "\n    where b.accid=" + Accid;
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Lastop.length() > 0)
			qry += "\n and b.Lastop like '%" + Lastop + "%'";
		if (Vipno.length() > 0)
			qry += "\n and b.vipno like '%" + Vipno + "%'";
		if (Guestname.length() > 0)
			qry += "\n and b.Guestname like '%" + Guestname + "%'";
		if (!Func.isNull(Vtidlist))
			qry += "  and " + Func.getCondition("b.vtid", Vtidlist);

		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);

		if (!Func.isNull(Fkhouseidlist))
			qry += "  and " + Func.getCondition("b.houseid", Fkhouseidlist);

		qry += "\n    group by a.guestid";
		// 储值
		qry += "\n    union all";
		qry += "\n    select a.guestid,0 as cent0,0 as cent1,sum(decode(a.fs,0,a.curr,0)) as curr0,sum(decode(a.fs,1,a.curr,0)) as curr1 ";
		qry += "\n    from guestcurr a join guestvip b on a.guestid=b.guestid";
		qry += "\n    where b.accid=" + Accid;
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Lastop.length() > 0)
			qry += "\n and b.Lastop like '%" + Lastop + "%'";
		if (Vipno.length() > 0)
			qry += "\n and b.vipno like '%" + Vipno + "%'";
		if (Guestname.length() > 0)
			qry += "\n and b.Guestname like '%" + Guestname + "%'";
		if (!Func.isNull(Vtidlist))
			qry += "  and " + Func.getCondition("b.vtid", Vtidlist);
		if (!Func.isNull(Houseidlist))
			qry += "  and " + Func.getCondition("a.houseid", Houseidlist);
		if (!Func.isNull(Fkhouseidlist))
			qry += "  and " + Func.getCondition("b.houseid", Fkhouseidlist);
		qry += "\n    group by a.guestid";
		qry += "\n    ) group by guestid";
		qry += "\n    );";
		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	public Table GetTable(QueryParam qp) {

		StringBuilder strSql = new StringBuilder();
		strSql.append("select ");
		strSql.append(Fieldlist);
		strSql.append("\n FROM v_guestbal_data a");
		strSql.append("\n join guestvip b on a.guestid=b.guestid");
		strSql.append("\n left outer join guesttype c on b.vtid=c.vtid");
		strSql.append("\n left outer join warehouse d on b.houseid=d.houseid");
		strSql.append("\n where a.epid=" + Userid);

		// System.out.println("aaa2="+strSql.toString());
		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "id";
		else
			Sortlist += ",id";
		qp.setSortString(Sortlist);
		qp.setSumString(Sumlist);
		return DbHelperSQL.GetTable(qp);
	}

}
