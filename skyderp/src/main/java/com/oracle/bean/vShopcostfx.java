package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;

import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-04-01 17:30:50
//*************************************
public class vShopcostfx {
	private String Nowdate = "";
	private String Accbegindate = "2000-01-01";
	private Long Accid;
	private Long Userid;

	private Long Houseid = (long) 0;
	// private Long Wareid;
	private String Mindate = "";
	private String Maxdate = "";

	private String Houseidlist = "";

	private String errmess;

	private Integer Qxbj = 0;
	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setMindate(String mindate) {
		this.Mindate = mindate;
	}

	public void setMaxdate(String maxdate) {
		this.Maxdate = maxdate;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, int hzfs,String sort) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select ");
		// String sort;
		if (hzfs == 0) {
			strSql.append(" a.houseid,b.housename");
			 //sort = "houseid";
		} else {
			strSql.append(" a.notedate");
			// sort = "notedate";
		}
		sort += ",keyid";
		strSql.append(",sum(a.amountxs) as amountxs,sum(a.currxs) as currxs,sum(a.amountxt) as amountxt,sum(a.currxt) as currxt ");
		strSql.append(",sum(a.currcb) as currcb,sum(a.currys) as currys,sum(a.currsk) as currsk,sum(a.currqk) as currqk,sum(a.currfy) as currfy");
		strSql.append(",sum(a.currml) as currml,min(rownum) as keyid");

		strSql.append("\n from v_shopcostfx_data a");
		if (hzfs == 0)
			strSql.append("\n left outer join warehouse b on a.houseid=b.houseid");
		strSql.append("\n where a.epid=" + Userid);
		if (hzfs == 0)
			strSql.append("\n group by a.houseid,b.housename");
		else
			strSql.append("\n group by a.notedate");

		String sumString = "nvl(sum(amountxs),0) as amountxs,nvl(sum(currxs),0) as currxs,nvl(sum(amountxt),0) as amountxt,nvl(sum(currxt),0) as currxt ";
		sumString += ",nvl(sum(currcb),0) as currcb,nvl(sum(currys),0) as currys,nvl(sum(currsk),0) as currsk,nvl(sum(currqk),0) as currqk,nvl(sum(currfy),0) as currfy";
		sumString += ",nvl(sum(currml),0) as currml";

		qp.setQueryString(strSql.toString());
		qp.setSumString(sumString);
		qp.setSortString(sort);
		return DbHelperSQL.GetTable(qp);
	}

	// 查询统计
	public int Total(int housecostbj) {
		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, 0);
		Mindate = mdv.getMindate();
		// String warning = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
	
		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n begin";
		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_shopcostfx_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n            delete from v_shopcostfx_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		qry += "\n    insert into v_shopcostfx_data (id,epid,notedate,houseid,amountxs,currxs,amountxt,currxt,currys,currsk,currqk,currcb,currfy,currml)";
		qry += "\n    select v_shopcostfx_data_id.nextval," + Userid + ",notedate,houseid,amountxs,currxs,amountxt,currxt,currys,currsk,currys-currsk,currcb,currfy,currys-currcb-currfy from (";
		qry += "\n    select notedate,houseid,sum(amountxs) as amountxs,sum(currxs) as currxs,sum(amountxt) as amountxt,sum(currxt) as currxt ";
		qry += "\n    ,sum(currcb) as currcb,sum(currxs-currxt+currxf-currzk) as currys,sum(currsk) as currsk,sum(currfy) as currfy";
		qry += "\n    from (";
		// 销售
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as notedate,a.houseid,sum(b.amount) as amountxs,sum(b.curr) as currxs,0 as amountxt,0 as currxt";
		qry += "\n    ,nvl(sum(b.amount*d.costprice),0) as currcb,0 as currxf,0 as currfy,0 as currsk,0 as currzk";
		qry += "\n    from wareouth a";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		if (housecostbj == 1)
			qry += " and a.houseid=d.houseid";
		else
			qry += " and d.houseid=0";

		qry += "\n    where a.accid=" + Accid + " and a.statetag=1 and (a.ntid=0 or a.ntid=1)";
		qry += "\n    and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.houseid";
		qry += "\n    union all";
		// 销售退货
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as notedate,a.houseid,0 as amountxs,0as currxs,sum(b.amount) as amountxt,sum(b.curr)  as currxt";
		qry += "\n    ,-nvl(sum(b.amount*d.costprice),0) as currcb,0 as currxf,0 as currfy,0 as currsk,0 as currzk";
		qry += "\n    from wareouth a";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		if (housecostbj == 1)
			qry += " and a.houseid=d.houseid";
		else
			qry += " and d.houseid=0";
		qry += "\n    where a.accid=" + Accid + " and a.statetag=1 and a.ntid=2";
		qry += "\n    and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.houseid";
		qry += "\n    union all";
		// 商场销售
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as notedate,a.houseid,sum(b.amount) as amountxs,sum(b.curr) as currxs,0 as amountxt,0 as currxt";
		qry += "\n    ,nvl(sum(b.amount*d.costprice),0) as currcb,0 as currxf,0 as currfy,0 as currsk,0 as currzk";
		qry += "\n    from shopsaleh a";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n    left outer join warecost d on b.wareid=d.wareid and d.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		if (housecostbj == 1)
			qry += " and a.houseid=d.houseid";
		else
			qry += " and d.houseid=0";
		qry += "\n    where a.accid=" + Accid + " and a.statetag=1 ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.houseid";
		// 批发销售费用
		qry += "\n    union all";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as notedate,a.houseid,0 as amountxs,0 as currxs,0 as amountxt,0 as currxt";
		qry += "\n    ,0 as currcb,sum(decode(a.ntid,1,b.curr,-b.curr)) as currxf,0 as currfy,0 as currsk,0 as currzk";
		qry += "\n    from wareouth a";
		qry += "\n    join wareoutcost b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n    where a.accid=" + Accid + " and a.statetag=1 and (a.ntid=1 or a.ntid=2)";
		qry += "\n    and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.houseid";

		// 店铺费用
		qry += "\n    union all";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as notedate,a.houseid,0 as amountxs,0 as currxs,0 as amountxt,0 as currxt";
		qry += "\n    ,0 as currcb,0 as currxf,sum(decode(a.fs,0,-a.curr,a.curr)) as currfy,0 as currsk,0 as currzk";
		qry += "\n    from housecost a where a.accid=" + Accid + " and a.statetag=1";
		qry += "\n    and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.houseid";

		// 收款
		qry += "\n    union all";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as notedate,a.houseid,0 as amountxs,0 as currxs,0 as amountxt,0 as currxt";
		qry += "\n    ,0 as currcb,0 as currxf,0 as currfy,sum(a.curr) as currsk,0 as currzk";
		qry += "\n    from incomecurr a where a.accid=" + Accid + " and a.statetag=1 and a.payid>0";
		qry += "\n    and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.houseid";
		// 折让
		qry += "\n    union all";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as notedate,a.houseid,0 as amountxs,0 as currxs,0 as amountxt,0 as currxt";
		qry += "\n    ,0 as currcb,0 as currxf,0 as currfy,0 as currsk,sum(a.curr) as currzk";
		qry += "\n    from incomecurr a";
		qry += "\n    where a.accid=" + Accid + " and a.statetag=1 and a.payid=0";
		qry += "\n    and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.houseid";

		// 店铺零售收款
		qry += "\n  union all ";
		qry += "\n  select to_char(c.notedate,'yyyy-mm-dd') as notedate,c.houseid,0 as amountxs,0 as currxs,0 as amountxt,0 as currxt";
		qry += "\n   ,0 as currcb,0 as currxf,0 as currfy,sum(a.curr) as currsk,0 as currzk";
		qry += "\n  from waresalepay a ";
		// qry += "\n left outer join payway b on a.payid=b.payid ";
		qry += "\n  join wareouth c on a.accid=c.accid and a.noteno=c.noteno ";
		qry += "\n  where c.accid=" + Accid + " and c.statetag=1 and c.ntid=0";// and b.payno<>'V' ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where c.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("c.houseid", Houseidlist);
		qry += "\n  and c.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n  and c.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    group by to_char(c.notedate,'yyyy-mm-dd'),c.houseid";
		// 店铺零售找零
		qry += "\n  union all ";
		// qry += "\n select b.payid,b.payno,b.payname,-sum(a.changecurr) as curr0 ,0.00 as curr1 ";
		qry += "\n  select to_char(a.notedate,'yyyy-mm-dd') as notedate,a.houseid,0 as amountxs,0 as currxs,0 as amountxt,0 as currxt";
		qry += "\n   ,0 as currcb,0 as currxf,0 as currfy,-sum(a.changecurr) as currsk,0 as currzk";
		qry += "\n  from wareouth a ";// ,payway b";
		// qry += "\n left outer join +payway b on a.payid=b.payid ";
		// qry += "\n join wareouth c on a.accid=c.accid and a.noteno=c.noteno ";
		qry += "\n  where a.accid=" + Accid + " and a.statetag=1 and a.ntid=0 and a.changecurr>0 ";// and b.accid=" + Accid;// + " and b.payno='0'";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n  and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n  and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.houseid";

		// 商场开票收款
		qry += "\n  union all ";
		qry += "\n  select to_char(c.notedate,'yyyy-mm-dd') as notedate,c.houseid,0 as amountxs,0 as currxs,0 as amountxt,0 as currxt";
		qry += "\n   ,0 as currcb,0 as currxf,0 as currfy,sum(a.curr) as currsk,0 as currzk";
		qry += "\n  from shopsalepay a ";
		qry += "\n  join shopsaleh c on a.accid=c.accid and a.noteno=c.noteno ";
//		qry += "\n  join shopsalem b on c.accid=d.accid and c.noteno=b.noteno";
		qry += "\n  where c.accid=" + Accid + " and c.statetag=1";// and b.payno<>'V' ";
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("c.houseid", Houseidlist);
		
		qry += "\n  and c.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n  and c.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where c.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		qry += "\n    group by to_char(c.notedate,'yyyy-mm-dd'),c.houseid";

		// 商场开票找零
		qry += "\n  union all ";
		// qry += "\n select b.payid,b.payno,b.payname,-sum(a.changecurr) as curr0 ,0.00 as curr1 ";
		qry += "\n  select to_char(a.notedate,'yyyy-mm-dd') as notedate,a.houseid,0 as amountxs,0 as currxs,0 as amountxt,0 as currxt";
		qry += "\n   ,0 as currcb,0 as currxf,0 as currfy,-sum(a.changecurr) as currsk,0 as currzk";
		qry += "\n  from shopsaleh a ";// ,payway b";
		// qry += "\n left outer join +payway b on a.payid=b.payid ";
		// qry += "\n join wareouth c on a.accid=c.accid and a.noteno=c.noteno ";
		qry += "\n  where a.accid=" + Accid + " and a.statetag=1 and a.changecurr>0 ";// and b.accid=" + Accid;// + " and b.payno='0'";
		if (Houseidlist != null && Houseidlist.length() > 0)
			qry += "\n  and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n  and a.notedate>=to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n  and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.houseid";

		qry += "\n    ) group by notedate,houseid); ";
		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功";
			return 1;
		}
	}
}