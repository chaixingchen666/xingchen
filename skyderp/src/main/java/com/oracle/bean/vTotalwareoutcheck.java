/**
 * 
 */
package com.oracle.bean;

import com.comdot.data.Table;
import com.common.tool.Func;

import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class vTotalwareoutcheck {
	private Long Accid = (long) 0;
	private Long Userid = (long) 0;

	private String Maxdate;
	private String Mindate;

	private Long Epid = (long) 0;
	private Long Houseid = (long) 0;
	private String Houseidlist = "";
	private Long Custid = (long) 0;
	private String Custidlist = "";
	private String errmess = "";

	private String Nowdate = "";
	private String Accbegindate = "";
	private Integer Hzfs = 0; // 0=商品 1=商品,颜色 2=商品,颜色,尺码

	private Integer Maxday = 0;

	private Integer Qxbj = 0;
	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
	}

	public String getErrmess() {
		return errmess;
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

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public int doTotal() {
		if (Func.isNull(Mindate) || Func.isNull(Maxdate)) {
			errmess = "mindate或maxdate不是一个有效值";
			return 0;
		}
		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		// String warning = mdv.getErrmess();

		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate;

		if (Mindate.length() <= 10)
			Mindate += " 00:00:00";
		if (Maxdate.length() <= 10)
			Maxdate += " 23:59:59";
		String mindate0 = Func.DateToStr(Func.getPrevDay(Mindate));

		if (Func.isNull(Houseidlist) && Houseid > 0)
			Houseidlist = Houseid.toString();

		if (Func.isNull(Custidlist) && Custid > 0)
			Custidlist = Custid.toString();

		String qry = "declare ";
		qry += "\n     v_custid number(10);";
		qry += "\n     v_balcurr number;";
		qry += "\n     v_balcurr1 number;";
		qry += "\n begin ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_wareoutcheck_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_wareoutcheck_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n       end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		qry += "\n    insert into v_wareoutcheck_data (id,epid,datestr,noteno,fs,wareid,colorid,sizeid,amount,price,curr) ";
		if (Hzfs == 0) // 商品
		{
			qry += "\n    select v_wareoutcheck_data_id.nextval," + Userid + ",datestr,noteno,fs,wareid,0,0,amount,0,curr from ( ";
			qry += "\n    select datestr,noteno,wareid,fs,sum(amount) as amount,sum(curr) as curr from (";
			// 销售
			qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr,a.noteno, b.wareid,0 as fs,sum(b.amount) as amount ,sum(b.curr) as curr  ";
			qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    where a.accid=" + Accid + " and a.ntid=1 and a.statetag=1 and b.amount>0  ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (Custidlist.length() > 0)
				qry += " and " + Func.getCondition("a.custid", Custidlist);
			if (Houseidlist.length() > 0)
				qry += " and " + Func.getCondition("a.houseid", Houseidlist);
			if (Epid > 0)
				qry += "    and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and  x.epid=" + Epid + ")";

			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,b.wareid ";
			qry += "\n    union all";
			// 负销售
			qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr,a.noteno, b.wareid,1 as fs,sum(b.amount) as amount ,sum(b.curr) as curr  ";
			qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    where a.accid=" + Accid + " and a.ntid=1 and a.statetag=1 and b.amount<0  ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (Custidlist.length() > 0)
				qry += " and " + Func.getCondition("a.custid", Custidlist);
			if (Houseidlist.length() > 0)
				qry += " and " + Func.getCondition("a.houseid", Houseidlist);

			if (Epid > 0)
				qry += "    and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and  x.epid=" + Epid + ")";

			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,b.wareid ";
			// 销售退货
			qry += "\n    union all";
			qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr,a.noteno, b.wareid,1 as fs,-sum(b.amount) as amount ,-sum(b.curr) as curr  ";
			qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    where a.accid=" + Accid + " and a.ntid=2 and a.statetag=1 ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (Custidlist.length() > 0)
				qry += " and " + Func.getCondition("a.custid", Custidlist);
			if (Houseidlist.length() > 0)
				qry += " and " + Func.getCondition("a.houseid", Houseidlist);
			if (Epid > 0)
				qry += "    and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and  x.epid=" + Epid + ")";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,b.wareid ";
			qry += "\n    ) group by datestr,noteno,wareid,fs ";
			qry += "\n    ); ";
		} else if (Hzfs == 1) // 商品+颜色
		{
			qry += "\n    select v_wareoutcheck_data_id.nextval," + Userid + ",datestr,noteno,fs,wareid,colorid,0,amount,0,curr from ( ";
			qry += "\n    select datestr,noteno,wareid,colorid,fs,sum(amount) as amount,sum(curr) as curr from ( ";

			qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr,a.noteno, b.wareid,b.colorid,0 as fs ,sum(b.amount) as amount,sum( b.curr) as curr  ";
			qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    where a.accid=" + Accid + " and a.ntid=1 and a.statetag=1 and b.amount>0  ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (Custidlist.length() > 0)
				qry += " and " + Func.getCondition("a.custid", Custidlist);
			if (Houseidlist.length() > 0)
				qry += " and " + Func.getCondition("a.houseid", Houseidlist);
			if (Epid > 0)
				qry += "    and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and  x.epid=" + Epid + ")";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,b.wareid,b.colorid ";
			qry += "\n    union all";
			qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr,a.noteno, b.wareid,b.colorid,1 as fs ,sum(b.amount) as amount,sum( b.curr) as curr  ";
			qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    where a.accid=" + Accid + " and a.ntid=1 and a.statetag=1 and b.amount<0 ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (Custidlist.length() > 0)
				qry += " and " + Func.getCondition("a.custid", Custidlist);
			if (Houseidlist.length() > 0)
				qry += " and " + Func.getCondition("a.houseid", Houseidlist);
			if (Epid > 0)
				qry += "    and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and  x.epid=" + Epid + ")";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,b.wareid,b.colorid ";
			qry += "\n    union all";
			qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr,a.noteno, b.wareid,b.colorid,1 as fs ,-sum(b.amount) as amount,-sum( b.curr) as curr  ";
			qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    where a.accid=" + Accid + " and a.ntid=2 and a.statetag=1  ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (Custidlist.length() > 0)
				qry += " and " + Func.getCondition("a.custid", Custidlist);
			if (Houseidlist.length() > 0)
				qry += " and " + Func.getCondition("a.houseid", Houseidlist);
			if (Epid > 0)
				qry += "   and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and  x.epid=" + Epid + ")";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,b.wareid,b.colorid ";

			qry += "\n    ) group by datestr,noteno,wareid,colorid,fs ";
			qry += "\n    ); ";
		} else // 商品+颜色+尺码
		{
			qry += "\n    select v_wareoutcheck_data_id.nextval," + Userid + ",datestr,noteno,fs,wareid,colorid,sizeid,amount,0,curr from ( ";
			qry += "\n    select datestr,noteno,wareid,colorid,sizeid,fs,sum(amount) as amount,sum(curr) as curr from ( ";

			qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr,a.noteno, b.wareid,b.colorid,b.sizeid,0 as fs,sum(b.amount) as amount,sum(b.curr) as curr  ";
			qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    where a.accid=" + Accid + " and a.ntid=1 and a.statetag=1 and b.amount>0 ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (Custidlist.length() > 0)
				qry += " and " + Func.getCondition("a.custid", Custidlist);
			if (Houseidlist.length() > 0)
				qry += " and " + Func.getCondition("a.houseid", Houseidlist);
			if (Epid > 0)
				qry += "    and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and  x.epid=" + Epid + ")";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,b.wareid,b.colorid,b.sizeid ";

			qry += "\n    union all";
			qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr,a.noteno, b.wareid,b.colorid,b.sizeid,1 as fs,sum(b.amount) as amount,sum(b.curr) as curr  ";
			qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    where a.accid=" + Accid + " and a.ntid=1 and a.statetag=1 and b.amount<0 ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (Custidlist.length() > 0)
				qry += " and " + Func.getCondition("a.custid", Custidlist);
			if (Houseidlist.length() > 0)
				qry += " and " + Func.getCondition("a.houseid", Houseidlist);
			if (Epid > 0)
				qry += "    and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and  x.epid=" + Epid + ")";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,b.wareid,b.colorid,b.sizeid ";

			qry += "\n    union all";
			qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr,a.noteno, b.wareid,b.colorid,b.sizeid,1 as fs,-sum(b.amount) as amount,-sum(b.curr) as curr  ";
			qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    where a.accid=" + Accid + " and a.ntid=2 and a.statetag=1 ";
			if (Qxbj == 1) {
				qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			}
			if (Custidlist.length() > 0)
				qry += " and " + Func.getCondition("a.custid", Custidlist);
			if (Houseidlist.length() > 0)
				qry += " and " + Func.getCondition("a.houseid", Houseidlist);
			if (Epid > 0)
				qry += "    and exists (select 1 from waresaleman x where a.accid=x.accid and a.noteno=x.noteno and  x.epid=" + Epid + ")";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd'),a.noteno,b.wareid,b.colorid,b.sizeid ";
			qry += "\n    ) group by datestr,noteno,wareid,colorid,sizeid,fs ";
			qry += "\n    ); ";

		}
		qry += "\n    update v_wareoutcheck_data set price=round(curr/amount,2) where epid=" + Userid + " and amount<>0; ";

		// --统计收款情况
		// qry += " delete from v_wareoutcheck1_data where epid="+userid+"; ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_wareoutcheck1_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_wareoutcheck1_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_wareoutcheck1_data (id,epid,datestr,fircurr,sumcurr,paycurr,cutcurr,balcurr) ";
		qry += "\n    select v_wareoutcheck1_data_id.nextval," + Userid + ",datestr,0,sumcurr,paycurr,cutcurr,0 from ( ";
		qry += "\n    select datestr,sum(sumcurr) as sumcurr,sum(paycurr) as paycurr,sum(cutcurr) as cutcurr from ( ";
		// --应收款
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr, sum(case a.ntid when 1 then b.curr else -b.curr end) as sumcurr,0 as paycurr,0 as cutcurr ";
		qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    where a.accid=" + Accid + " and a.ntid>=1  and a.statetag=1  ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		if (Custidlist.length() > 0)
			qry += " and " + Func.getCondition("a.custid", Custidlist);
		if (Houseidlist.length() > 0)
			qry += " and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd') ";
		qry += "\n    union all ";
		// --应收费用
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr, sum(a.curr) as sumcurr,0 as paycurr,0 as cutcurr ";
		qry += "\n    from incomecost a  ";
		qry += "\n    where a.accid=" + Accid + " and a.statetag=1  ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		if (Custidlist.length() > 0)
			qry += " and " + Func.getCondition("a.custid", Custidlist);
		if (Houseidlist.length() > 0)
			qry += " and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd') ";
		qry += "\n    union all ";
		// --应收期初
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr, sum(a.curr) as sumcurr,0 as paycurr,0 as cutcurr ";
		qry += "\n    from firstincomecurr a  ";
		qry += "\n    where a.accid=" + Accid + " and a.statetag=1  ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		if (Custidlist.length() > 0)
			qry += " and " + Func.getCondition("a.custid", Custidlist);
		if (Houseidlist.length() > 0)
			qry += " and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd') ";

		qry += "\n    union all ";
		// --已收款
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr, 0 as sumcurr,sum(a.curr) as paycurr,0 as cutcurr ";
		qry += "\n    from incomecurr a  ";
		qry += "\n    where a.accid=" + Accid + " and a.payid>0  and a.statetag=1  ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		if (Custidlist.length() > 0)
			qry += " and " + Func.getCondition("a.custid", Custidlist);
		if (Houseidlist.length() > 0)
			qry += " and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd') ";
		qry += "\n    union all ";
		// --折让
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd') as datestr, 0 as sumcurr,0 as paycurr,sum(a.curr) as cutcurr ";
		qry += "\n    from incomecurr a  ";
		qry += "\n    where a.accid=" + Accid + " and a.payid=0  and a.statetag=1  ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') ";
		qry += "\n    and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		if (Custidlist.length() > 0)
			qry += " and " + Func.getCondition("a.custid", Custidlist);
		if (Houseidlist.length() > 0)
			qry += " and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n    group by to_char(a.notedate,'yyyy-mm-dd') ";

		qry += "\n    ) group by datestr ); ";
		// --查找期初余额
		qry += "\n    select nvl(sum(a.curr),0) into v_balcurr  ";
		qry += "\n    from incomebal a ";
		qry += "\n    join customer b on a.custid=b.custid  ";
		qry += "\n    where b.accid=" + Accid + " and a.daystr='" + mindate0 + "' ";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
		}
		if (Custidlist.length() > 0)
			qry += " and " + Func.getCondition("a.custid", Custidlist);
		if (Houseidlist.length() > 0)
			qry += " and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "  ;";
		// --计算余额
		qry += "\n    declare cursor cur_data is  ";
		qry += "\n    select id,fircurr,sumcurr,paycurr,cutcurr,balcurr from v_wareoutcheck1_data where epid=" + Userid + " order by datestr,id ; ";
		// qry += " for update nowait; ";
		qry += "\n      v_row cur_data%rowtype; ";
		qry += "\n    begin ";
		qry += "\n      for v_row in cur_data loop ";
		qry += "\n          v_balcurr1:=v_balcurr+v_row.sumcurr-v_row.paycurr-v_row.cutcurr; ";
		qry += "\n          update v_wareoutcheck1_data set fircurr=v_balcurr,balcurr=v_balcurr1 where id=v_row.id; ";
		qry += "\n          v_balcurr:=v_balcurr1; ";
		qry += "\n      end loop; ";
		qry += "\n    end;";
		qry += "\n    commit;";
		qry += "\n  end;";

		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "\"msg\":\"操作成功！\",\"warning\":\"" + mdv.getErrmess() + "\"";
			return 1;
		}

	}

	//
	public Table GetTable(QueryParam qp, int hzfs) {
		String qry = " select a.datestr,a.noteno,a.wareid,c.warename,c.wareno,c.units,a.fs";
		if (hzfs > 0)
			qry += ",a.colorid,d.colorno,d.colorname";
		if (hzfs > 1)
			qry += ",a.sizeid,e.sizeno,e.sizename";
		qry += ",a.amount,a.price,a.curr,b.fircurr,b.sumcurr,b.paycurr,b.cutcurr,b.balcurr";
		qry += " FROM v_wareoutcheck_data  a";
		qry += " left outer join v_wareoutcheck1_data b on a.epid=b.epid and a.datestr=b.datestr ";
		qry += " left outer join warecode c on a.wareid=c.wareid";
		if (hzfs > 0)
			qry += " left outer join colorcode d on a.colorid=d.colorid";
		if (hzfs > 1)
			qry += " left outer join sizecode e on a.sizeid=e.sizeid";
		qry += " where a.epid=" + Userid;

		qp.setQueryString(qry);
		//		String sumstr = "nvl(sum(cgamt),0) as cgamt,nvl(sum(cgcurr),0) as cgcurr,nvl(sum(ctamt),0) as ctamt,nvl(sum(ctcurr),0) as ctcurr,nvl(sum(fycurr),0) as fycurr"//
		//				+ " ,nvl(sum(zkcurr),0) as zkcurr,nvl(sum(curr0),0) as curr0,nvl(sum(curr1),0) as curr1,nvl(sum(curr2),0) as curr2,nvl(sum(curr3),0) as curr3";
		//		qp.setSumString(sumstr);

		return DbHelperSQL.GetTable(qp);
	}

	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		return DbHelperSQL.GetTable(qp);
	}

}
