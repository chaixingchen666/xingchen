/**
 * 
 */
package com.oracle.bean;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.comdot.data.Table;
import com.common.tool.Func;

import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONObject;

/**
 * @author Administrator 销售趋势分析
 *
 */
public class vTotalSzjyhz {
	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	private String Sumlist = ""; // 要求和的项目列表
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd
	// HH:mm:ss,------------必传
	private Long Accid = (long) 0;

	private String Notedate = "";
	private Long Userid = (long) 0;
	private String Houseidlist = "";
	private String Operant = "";

	private String Mindate = "";
	private String Maxdate = "";

	private String Accbegindate = "2000-01-01";
	// private String Nowdate = "";
	private Integer Qxbj = 0;// 1=启用权限控
	private Integer Maxday = 0;// 最大查询天数

	private String errmess;

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

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

	public int doTotal() {

		if (Mindate.length() < 0 || Maxdate.length() < 0) {
			errmess = "mindate或maxdate不是一个有效值！";
			return 0;
		}

		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		// String warning = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		Mindate += " 00:00:00";
		Maxdate += " 23:59:59";

		String qry = "declare ";
		// qry += "\n v_epid number; ";
		qry += "\n    v_curr number; ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalzk number; ";
		qry += "\n    v_totalgz number; ";
		qry += "\n    v_fh varchar2(1); ";
		// qry += "\n v_maxdate varchar2(10); ";
		qry += "\n    v_retsql clob;";
		qry += "\n    v_pcolumn clob;";
		qry += "\n    v_fcolumn clob;";

		qry += "\n begin ";
		// qry += "\n v_plancurr:=0;";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_szjyhz_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_szjyhz_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n       end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		// 客户本期发生额
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select custid,sum(curr) as curr from (";
		//批发，退货
		qry += "\n      select a.custid,sum(decode(a.ntid,2,-a.totalcurr,a.totalcurr)) as curr";
		qry += "\n      from wareouth a ";
		// qry += "\n join wareoutm b on a.accid=b.accid and a.noteno=b.noteno
		// ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1 ";
		qry += "\n      and (a.ntid=1 or a.ntid=2)";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}

		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		qry += "\n      group by a.custid";
		//店铺零售
		qry += "\n      union all";
		qry += "\n      select 0,sum(a.checkcurr) as curr";
		qry += "\n      from wareouth a ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1 and a.ntid=0 ";//and (a.ntid=1 or a.ntid=2)";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			//			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";

		//商场零售
		qry += "\n      union all";
		qry += "\n      select 0,sum(a.checkcurr) as curr";
		qry += "\n      from shopsaleh a ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1 ";//and (a.ntid=1 or a.ntid=2)";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			//			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";

		qry += "\n      ) group by custid;";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n          insert into v_szjyhz_data(id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		qry += "\n          values (v_szjyhz_data_id.nextval," + Userid + ",0,v_row.custid,0,-1,-1,v_row.curr);";
		qry += "\n      end loop;";
		qry += "\n    end;";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计

		// 客户 收款、折让
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select custid,payid,sum(curr) as curr from (";
		qry += "\n      select a.custid,a.payid,sum(a.curr) as curr";
		qry += "\n      from incomecurr a ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n      group by a.custid,a.payid";
		//店铺零售
		qry += "\n      union all";
		qry += "\n      select 0,a.payid,sum(a.curr) as curr";
		qry += "\n      from waresalepay a ";
		qry += "\n      join wareouth b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n      where b.accid=" + Accid + " and b.statetag=1 and b.ntid=0";
		qry += "\n      and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			//			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where b.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Operant.length() > 0)
			qry += "\n      and b.operant='" + Operant + "'";
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("b.houseid", Houseidlist);
		qry += "\n      group by a.payid";
		//商场零售
		qry += "\n      union all";
		qry += "\n      select 0,a.payid,sum(a.curr) as curr";
		qry += "\n      from shopsalepay a ";
		qry += "\n      join shopsaleh b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n      where b.accid=" + Accid + " and b.statetag=1";
		qry += "\n      and b.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and b.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			//			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where b.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Operant.length() > 0)
			qry += "\n      and b.operant='" + Operant + "'";
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("b.houseid", Houseidlist);
		qry += "\n      group by a.payid";

		//店铺零售找零
		qry += "\n      union all";
		qry += "\n      select 0,b.payid,-sum(a.changecurr) as curr";
		qry += "\n      from wareouth a,payway b ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1 and a.ntid=0 and a.changecurr<>0";
		qry += "\n      and a.accid=b.accid and b.payno='0' and b.statetag=1";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			//			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n      group by b.payid";
		//商场零售找零
		qry += "\n      union all";
		qry += "\n      select 0,b.payid,-sum(a.changecurr) as curr";
		qry += "\n      from shopsaleh a,payway b ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1  and a.changecurr<>0";
		qry += "\n      and a.accid=b.accid and b.payno='0' and b.statetag=1";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			//			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		qry += "\n      group by b.payid";

		qry += "\n      ) group by custid,payid;";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n          insert into v_szjyhz_data(id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		qry += "\n          values (v_szjyhz_data_id.nextval," + Userid + ",0,v_row.custid,0,v_row.payid,0,v_row.curr);";
		qry += "\n      end loop;";
		qry += "\n    end;";
		// 客户 费用
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select custid,cgid,sum(curr) as curr from (";
		qry += "\n      select a.custid,a.cgid,sum(a.curr) as curr";
		qry += "\n      from incomecost a ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		qry += "\n      group by a.custid,a.cgid";
		qry += "\n      union all";
		qry += "\n      select a.custid,b.cgid,sum(b.curr) as curr";
		qry += "\n      from wareouth a ";
		qry += "\n      join wareoutcost b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1 and a.ntid>0";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employecust x1 where a.custid=x1.custid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		qry += "\n      group by a.custid,b.cgid";
		qry += "\n      ) group by custid,cgid;";

		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n          insert into v_szjyhz_data(id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		qry += "\n          values (v_szjyhz_data_id.nextval," + Userid + ",0,v_row.custid,0,v_row.cgid,1,v_row.curr);";
		qry += "\n      end loop;";
		qry += "\n    end;";
		// jllb --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计

		// 采购发生
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select a.provid,sum(decode(a.ntid,0,a.totalcurr,-a.totalcurr)) as curr";
		qry += "\n      from wareinh a ";
		// qry += "\n join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		qry += "\n      group by a.provid;";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n          insert into v_szjyhz_data(id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		qry += "\n          values (v_szjyhz_data_id.nextval," + Userid + ",2,v_row.provid,1,-1,-1,v_row.curr);";
		qry += "\n      end loop;";
		qry += "\n    end;";

		// 供应商付款、折让
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select a.provid,a.payid,sum(a.curr) as curr";
		qry += "\n      from paycurr a ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		qry += "\n      group by a.provid,a.payid;";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n          insert into v_szjyhz_data(id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n          values (v_szjyhz_data_id.nextval," + Userid + ",2,v_row.provid,1,v_row.payid,0,v_row.curr);";
		qry += "\n      end loop;";
		qry += "\n    end;";
		// 供应商 费用
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select provid,cgid,sum(curr) as curr from (";
		qry += "\n      select a.provid,a.cgid,sum(a.curr) as curr";
		qry += "\n      from paycost a ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		qry += "\n      group by a.provid,a.cgid";
		qry += "\n      union all";
		qry += "\n      select a.provid,b.cgid,sum(decode(a.ntid,0,b.curr,-b.curr)) as curr";
		qry += "\n      from wareinh a ";
		qry += "\n      join wareincost b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employeprov x1 where a.provid=x1.provid and x1.epid=" + Userid + " ) ";
			qry += "\n     and exists (select 1 from employehouse x2 where a.houseid=x2.houseid and x2.epid=" + Userid + " ) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		qry += "\n      group by a.provid,b.cgid";
		qry += "\n      ) group by provid,cgid;";

		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n          insert into v_szjyhz_data(id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n          values (v_szjyhz_data_id.nextval," + Userid + ",2,v_row.provid,1,v_row.cgid,1,v_row.curr);";
		qry += "\n      end loop;";
		qry += "\n    end;";
		// 店铺费用
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select a.houseid,a.cgid,a.payid,sum(decode(a.fs,0,a.curr,-a.curr)) as curr";
		qry += "\n      from housecost a ";
		qry += "\n      where a.accid=" + Accid + " and a.statetag=1";
		qry += "\n      and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n      and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) {
			qry += "\n     and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Operant.length() > 0)
			qry += "\n      and a.operant='" + Operant + "'";
		qry += "\n      group by a.houseid,a.cgid,a.payid;";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n          insert into v_szjyhz_data(id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		qry += "\n          values (v_szjyhz_data_id.nextval," + Userid + ",4,v_row.houseid,2,v_row.payid,0,v_row.curr);";
		qry += "\n          insert into v_szjyhz_data(id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		qry += "\n          values (v_szjyhz_data_id.nextval," + Userid + ",4,v_row.houseid,2,v_row.cgid,1,v_row.curr);";
		qry += "\n      end loop;";
		qry += "\n    end;";
		// 客户挂账
		qry += "\n    insert into v_szjyhz_data (id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		qry += "\n    select v_szjyhz_data_id.nextval," + Userid + ",0,xmid,xmlb,-2,-2,curr from (";
		qry += "\n    select xmlb,xmid,sum(curr) as curr from (";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n    select xmlb,xmid,curr from v_szjyhz_data where epid=" + Userid + " and pfid=-1 and xmid>0 and jllb=0"; // 总金额-付款额-折扣
		qry += "\n    union all";
		qry += "\n    select xmlb,xmid,-curr from v_szjyhz_data where epid=" + Userid + " and pfid>=0 and pflb=0 and xmid>0 and jllb=0";
		qry += "\n    ) group by xmlb,xmid order by xmlb,xmid);";
		// 供应商挂账
		qry += "\n    insert into v_szjyhz_data (id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		qry += "\n    select v_szjyhz_data_id.nextval," + Userid + ",2,xmid,xmlb,-2,-2,curr from (";
		qry += "\n    select xmlb,xmid,sum(curr) as curr from (";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n    select xmlb,xmid,curr from v_szjyhz_data where epid=" + Userid + " and pfid=-1 and xmid>0 and jllb=2"; // 总金额-付款额-折扣
		qry += "\n    union all";
		qry += "\n    select xmlb,xmid,-curr from v_szjyhz_data where epid=" + Userid + " and pfid>=0 and pflb=0 and xmid>0 and jllb=2";
		qry += "\n    ) group by xmlb,xmid order by xmlb,xmid);";

		// --客户小计
		qry += "\n    insert into v_szjyhz_data (id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n    select v_szjyhz_data_id.nextval," + Userid + ",1,-1,xmlb,pfid,pflb,curr from (";
		qry += "\n    select xmlb,pfid,pflb,sum(curr) as curr from v_szjyhz_data where epid=" + Userid + " and xmlb=0";
		qry += "\n    group by xmlb,pfid,pflb ) ;";

		// --供应商小计
		qry += "\n    insert into v_szjyhz_data (id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n    select v_szjyhz_data_id.nextval," + Userid + ",3,-1,xmlb,pfid,pflb,curr from (";
		qry += "\n    select xmlb,pfid,pflb,sum(curr) as curr from v_szjyhz_data where epid=" + Userid + " and xmlb=1";
		qry += "\n    group by xmlb,pfid,pflb ) ;";

		// --店铺小计
		qry += "\n    insert into v_szjyhz_data (id,epid,jllb,xmid,xmlb,pfid,pflb,curr)";
		// jllb : --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		qry += "\n    select v_szjyhz_data_id.nextval," + Userid + ",5,-1,xmlb,pfid,pflb,curr from (";
		qry += "\n    select xmlb,pfid,pflb,sum(curr) as curr from v_szjyhz_data where epid=" + Userid + " and xmlb=2";
		qry += "\n    group by xmlb,pfid,pflb ) ;";

		qry += "\n   v_retsql:='sum(decode(a.pfid,-1,a.curr,0)) as totalcurr';";
		// jllb NUMBER(1) DEFAULT (0),
		// --0=客户，1=客户小计，2=供应商，3=供应商小计，4=店铺，5=店铺小计，6=合计
		// xmid NUMBER(10) DEFAULT (0), --客户/供应商/店铺 小计合计行xmid=0 铺
		// xmlb NUMBER(1) DEFAULT (0), --0=客户，1=供应商，2=店铺
		// pfid NUMBER(10) DEFAULT (0), --结算方式/费用项目 -1=发生总额，0=折让,-2=挂账
		// pflb NUMBER(1) DEFAULT (0), --0=结算方式，1=费用项目，-1=其他,

		//		qry += "\n   select nvl(sum(curr),0) into v_totalcurr from v_szjyhz_data where epid=" + Userid + " and pfid=-1 and xmid>0;";
		//		qry += "\n   select nvl(sum(curr),0) into v_totalzk from v_szjyhz_data where epid=" + Userid + " and pfid=0 and xmid>0;";
		qry += "\n   select nvl(sum(decode(jllb,2,-curr,curr)),0) into v_totalcurr from v_szjyhz_data where epid=" + Userid + " and pfid=-1 and xmid>=0;";
		qry += "\n   select nvl(sum(decode(jllb,2,-curr,curr)),0) into v_totalzk from v_szjyhz_data where epid=" + Userid + " and pfid=0 and xmid>=0;";
		// 挂账=总金额-折让-已结  (不含店铺jllb<4)
		qry += "\n   select v_totalcurr-v_totalzk-nvl(sum(decode(jllb,2,-curr,curr)),0) into v_totalgz from v_szjyhz_data where epid=" + Userid + " and pfid>0 and pflb=0 and xmid>=0 and jllb<4;";

		// qry += "\n
		// v_pcolumn:='{\"fieldname\":\"totalcurr\",\"title\":\"总金额\",\"curr\":\"'||v_curr||'\"}';";
		// qry += "\n select nvl(sum(curr),0) into v_totalcurr from
		// v_szjyhz_data where epid=" + Userid + " and pfid=-1;";
		qry += "\n   v_pcolumn:='';v_fh:='';";
		// 结算方式合计
		qry += "\n   DECLARE ";
		qry += "\n      CURSOR cur_data IS ";
		qry += "\n      SELECT a.pfid,b.payno,b.payname,sum(decode(a.jllb,2,-a.curr,a.curr)) as curr FROM v_szjyhz_data a ";
		qry += "\n      join payway b on a.pfid=b.payid where a.epid= " + Userid;
		qry += "\n      and a.pflb=0 and a.pfid>0 and (a.jllb=0 or a.jllb=2 or a.jllb=4)";
		qry += "\n      group BY b.payno,a.pfid,b.payname order by b.payno,a.pfid;   ";
		qry += "\n      v_row cur_data%rowtype; ";
		qry += "\n   begin ";
		qry += "\n      v_curr:=0;";
		qry += "\n      FOR v_row IN cur_data LOOP ";
		qry += "\n        v_retsql:=v_retsql||',sum(decode(a.pfid,'||v_row.pfid||',a.curr)) as paycurr_'||v_row.pfid; ";
		// qry += "\n if length(v_pcolumn)>0 then ";
		// qry += "\n v_pcolumn:=v_pcolumn||',';";
		// qry += "\n end if;";
		qry += "\n        v_pcolumn:=v_pcolumn||v_fh||'{\"fieldname\":\"paycurr_'||v_row.pfid||'\",\"title\":\"'||v_row.payname||'\",\"curr\":\"'||v_row.curr||'\"}';";
		qry += "\n        v_fh:=',';";
		qry += "\n        v_curr:=v_curr+v_row.curr;";
		qry += "\n      end loop; ";
		qry += "\n      v_retsql:=v_retsql||',sum(case when a.pflb=0 and a.pfid>0 then a.curr else 0 end) as paycurr_hj'; ";
		qry += "\n      v_pcolumn:=v_pcolumn||v_fh||'{\"fieldname\":\"paycurr_hj\",\"title\":\"小计\",\"curr\":\"'||v_curr||'\"}';";
		qry += "\n   end; ";
		qry += "\n   v_retsql:=v_retsql||',sum(decode(a.pfid,0,a.curr,0)) as paycurr_zk';";

		qry += "\n   v_retsql:=v_retsql||',sum(decode(a.pfid,-2,a.curr,0)) as totalgz';";

		qry += "\n   v_fcolumn:='';";
		qry += "\n   v_fh:='';";
		// 费用项目合计
		qry += "\n   DECLARE ";
		qry += "\n      CURSOR cur_data IS ";
		qry += "\n      SELECT a.pfid,b.cgname,sum(decode(a.jllb,2,-a.curr,a.curr)) as curr FROM v_szjyhz_data a ";
		qry += "\n      join chargescode b on a.pfid=b.cgid where a.epid= " + Userid;
		qry += "\n      and a.pflb=1  and a.pfid>0 and (a.jllb=0 or a.jllb=2 or a.jllb=4)";
		qry += "\n      group BY a.pfid,b.cgname order by a.pfid;   ";
		qry += "\n      v_row cur_data%rowtype; ";
		qry += "\n   begin ";
		qry += "\n      v_curr:=0;";
		qry += "\n      FOR v_row IN cur_data LOOP ";
		qry += "\n        v_retsql:=v_retsql||',sum(decode(a.pfid,'||v_row.pfid||',a.curr)) as fycurr_'||v_row.pfid; ";

		qry += "\n        v_fcolumn:=v_fcolumn||v_fh||'{\"fieldname\":\"fycurr_'||v_row.pfid||'\",\"title\":\"'||v_row.cgname||'\",\"curr\":\"'||v_row.curr||'\"}';";
		qry += "\n        v_fh:=',';";
		qry += "\n        v_curr:=v_curr+v_row.curr;";
		qry += "\n      end loop; ";
		// qry += "\n v_retsql:=v_retsql||',sum(decode(a.pflb,1,a.curr,0)) as
		// fycurr_hj'; ";
		qry += "\n      v_retsql:=v_retsql||',sum(case when a.pflb=1 and a.pfid>0 then a.curr else 0 end) as fycurr_hj'; ";
		qry += "\n      v_fcolumn:=v_fcolumn||v_fh||'{\"fieldname\":\"fycurr_hj\",\"title\":\"小计\",\"curr\":\"'||v_curr||'\"}';";
		qry += "\n   end; ";

		qry += "\n   select v_retsql,v_pcolumn,v_fcolumn,v_totalcurr,v_totalzk,v_totalgz into :retsql,:pcolumn,:fcolumn,:totalcurr,:totalzk,:totalgz from dual;";

		qry += "\n end; ";
		//		 System.out.println(qry);
		// LogUtils.LogDebugWrite("totalxshzfx2",qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retsql", new ProdParam(Types.VARCHAR));
		param.put("pcolumn", new ProdParam(Types.VARCHAR));
		param.put("fcolumn", new ProdParam(Types.VARCHAR));
		param.put("totalcurr", new ProdParam(Types.DOUBLE));
		param.put("totalzk", new ProdParam(Types.DOUBLE));
		param.put("totalgz", new ProdParam(Types.DOUBLE));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount =
		// Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr =
		// Float.parseFloat(param.get("curr").getParamvalue().toString());
		// String Noteno = param.get("Noteno").getParamvalue().toString();
		errmess = "\"msg\":\"操作成功！\",\"sumlist\":\"" + param.get("retsql").getParamvalue().toString() + "\""//
				+ ",\"totalcurr\":\"" + param.get("totalcurr").getParamvalue().toString() + "\""//
				+ ",\"totalzk\":\"" + param.get("totalzk").getParamvalue().toString() + "\""//
				+ ",\"totalgz\":\"" + param.get("totalgz").getParamvalue().toString() + "\""//
				+ ",\"pcolumns\":[" + param.get("pcolumn").getParamvalue().toString() + "]"//
				+ ",\"fcolumns\":[" + param.get("fcolumn").getParamvalue().toString() + "]";
		return 1;

	}

	public Table GetTable(QueryParam qp) {
		String qry = "  select a.xmid,decode(a.xmid,-1,'小计',0,'<零售>',b.custname) as xmname,a.xmlb,a.jllb,min(id) as id";
		qry += "," + Sumlist;
		qry += "\n FROM v_szjyhz_data a";
		qry += "\n left outer join customer b on a.xmid=b.custid";
		qry += "\n where a.epid=" + Userid + " and a.xmlb=0"; // 0=客户，1=供应商，2=店铺
		qry += "\n group by a.xmid,b.custname,a.xmlb,a.jllb";
		qry += "\n union all";
		qry += "\n select a.xmid,decode(a.xmid,-1,'小计',b.provname) as xmname,a.xmlb,a.jllb,min(id) as id";
		qry += "," + Sumlist;
		qry += "\n FROM v_szjyhz_data a";
		qry += "\n left outer join provide b on a.xmid=b.provid";
		qry += "\n where a.epid=" + Userid + " and a.xmlb=1"; // 0=客户，1=供应商，2=店铺
		qry += "\n group by a.xmid,b.provname,a.xmlb,a.jllb";
		qry += "\n union all";
		qry += "\n select a.xmid,decode(a.xmid,-1,'小计',b.housename) as xmname,a.xmlb,a.jllb,min(id) as id";
		qry += "," + Sumlist;
		qry += "\n FROM v_szjyhz_data a";
		qry += "\n left outer join warehouse b on a.xmid=b.houseid";
		qry += "\n where a.epid=" + Userid + " and a.xmlb=2"; // 0=客户，1=供应商，2=店铺
		qry += "\n group by a.xmid,b.housename,a.xmlb,a.jllb";

		qp.setQueryString(qry);
		//		 System.out.println(qry);
		// qp.setSortString("jllb,xmlb,xmid,id");

		// qp.setSumString("nvl(sum(curr),0) as totalcurr,nvl(sum(amount),0) as
		// totalamt,nvl(sum(mlcurr),0) as totalml");
		// qp.setCalcfield("totalcurr-paycurr_zk-paycurr_hj as totalgz");
		return DbHelperSQL.GetTable(qp);
	}

	public String GetTable2Excel(QueryParam qp, JSONObject jsonObject) {

		// String sort = "";

		// StringBuilder strSql = new StringBuilder();
		String qry = "  select a.xmid,decode(a.xmid,-1,'小计',0,'<零售>',b.custname) as xmname,a.xmlb,a.jllb,min(id) as id";
		qry += "," + Sumlist;
		qry += "\n FROM v_szjyhz_data a";
		qry += "\n left outer join customer b on a.xmid=b.custid";
		qry += "\n where a.epid=" + Userid + " and a.xmlb=0"; // 0=客户，1=供应商，2=店铺
		qry += "\n group by a.xmid,b.custname,a.xmlb,a.jllb";
		qry += "\n union all";
		qry += "\n select a.xmid,decode(a.xmid,-1,'小计',b.provname) as xmname,a.xmlb,a.jllb,min(id) as id";
		qry += "," + Sumlist;
		qry += "\n FROM v_szjyhz_data a";
		qry += "\n left outer join provide b on a.xmid=b.provid";
		qry += "\n where a.epid=" + Userid + " and a.xmlb=1"; // 0=客户，1=供应商，2=店铺
		qry += "\n group by a.xmid,b.provname,a.xmlb,a.jllb";
		qry += "\n union all";
		qry += "\n select a.xmid,decode(a.xmid,-1,'小计',b.housename) as xmname,a.xmlb,a.jllb,min(id) as id";
		qry += "," + Sumlist;
		qry += "\n FROM v_szjyhz_data a";
		qry += "\n left outer join warehouse b on a.xmid=b.houseid";
		qry += "\n where a.epid=" + Userid + " and a.xmlb=2"; // 0=客户，1=供应商，2=店铺
		qry += "\n group by a.xmid,b.housename,a.xmlb,a.jllb";

		qp.setQueryString(qry);
		qp.setSortString("jllb,xmlb,xmid,id");
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);
	}

}
