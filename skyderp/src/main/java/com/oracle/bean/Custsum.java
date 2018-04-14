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
//  @author:sunhong  @date:2017-08-26 23:44:53
//*************************************
public class Custsum implements java.io.Serializable {
	private Float Id;
	private String Daystr;
	private Long Custid;
	private Long Wareid;
	private Long Colorid;
	private Long Sizeid;
	private Float Amount;
	private Float Price;
	private Float Curr;
	private String errmess;

	// private String Accbegindate;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setDaystr(String daystr) {
		this.Daystr = daystr;
	}

	public String getDaystr() {
		return Daystr;
	}

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setColorid(Long colorid) {
		this.Colorid = colorid;
	}

	public Long getColorid() {
		return Colorid;
	}

	public void setSizeid(Long sizeid) {
		this.Sizeid = sizeid;
	}

	public Long getSizeid() {
		return Sizeid;
	}

	public void setAmount(Float amount) {
		this.Amount = amount;
	}

	public Float getAmount() {
		return Amount;
	}

	public void setPrice(Float price) {
		this.Price = price;
	}

	public Float getPrice() {
		return Price;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and
		// rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update custsum set statetag=2,lastop='" + Lastop +
		// "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Id != null) {
			strSql1.append("id,");
			strSql2.append("'" + Id + "',");
		}
		if (Daystr != null) {
			strSql1.append("daystr,");
			strSql2.append("'" + Daystr + "',");
		}
		if (Custid != null) {
			strSql1.append("custid,");
			strSql2.append(Custid + ",");
		}
		if (Wareid != null) {
			strSql1.append("wareid,");
			strSql2.append(Wareid + ",");
		}
		if (Colorid != null) {
			strSql1.append("colorid,");
			strSql2.append(Colorid + ",");
		}
		if (Sizeid != null) {
			strSql1.append("sizeid,");
			strSql2.append(Sizeid + ",");
		}
		if (Amount != null) {
			strSql1.append("amount,");
			strSql2.append("'" + Amount + "',");
		}
		if (Price != null) {
			strSql1.append("price,");
			strSql2.append("'" + Price + "',");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=custsum_id.nextval; ");
		strSql.append("   insert into custsum (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update custsum set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Daystr != null) {
			strSql.append("daystr='" + Daystr + "',");
		}
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Colorid != null) {
			strSql.append("colorid=" + Colorid + ",");
		}
		if (Sizeid != null) {
			strSql.append("sizeid=" + Sizeid + ",");
		}
		if (Amount != null) {
			strSql.append("amount='" + Amount + "',");
		}
		if (Price != null) {
			strSql.append("price='" + Price + "',");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM custsum ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM custsum ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from custsum");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	public int myTotalCust(String nowdate, long accid) {
		// System.out.println("myTotalWaresum begin");

		Date nowday = Func.StrToDate(nowdate, "yyyy-MM-dd");

		String nowdate0 = Func.DateToStr(Func.getPrevDay(nowday), "yyyy-MM-dd"); // nowday.AddDays(-1).ToString("yyyy-MM-dd");
		String nowdate1 = Func.DateToStr(Func.getNextDay(nowday), "yyyy-MM-dd"); // nowday.AddDays(1).ToString("yyyy-MM-dd");
		String qry = "declare  ";
		qry += "\n    v_nowdate varchar2(10); ";
		qry += "\n    v_nowdate0 varchar2(10); ";
		qry += "\n    v_count number; ";
		qry += "\n    v_accid number; ";
		qry += "\n begin ";
		qry += "\n    v_nowdate:='" + nowdate + "';";
		qry += "\n    v_nowdate0:='" + nowdate0 + "';";
		qry += "\n    v_accid=" + accid + ";";
		// --计算客户商品成本begin
		qry += "\n    declare cursor cur_data is ";
		qry += "\n    select a.custid,a.wareid,a.amountqc,a.currqc,a.amountrk,a.currrk,a.amountrt,a.amountck,a.amountpy,a.amountpk";
		qry += "\n    ,nvl(b.costprice,0) as costprice from (";
		qry += "\n    select custid,wareid,sum(amountqc) as amountqc,sum(currqc) as currqc";
		qry += "\n    ,sum(amountrk) as amountrk,sum(currrk) as currrk,sum(amountrt) as amountrt,sum(amountck) as amountck";
		qry += "\n    ,sum(amountpy) as amountpy,sum(amountpk) as amountpk";
		qry += "\n    from (";

		qry += "\n     select a.custid,a.wareid,nvl(sum(b.amount),0) as amountqc,nvl(round(sum(b.amount*a.costprice),2),0) as currqc,0 as amountrk,0 as currrk,0 as amountrt,0 as amountck";
		qry += "\n    ,0 as amountpy,0 as amountpk";
		qry += "\n    from custcost a ";
		qry += "\n    left outer join custsum b on a.wareid=b.wareid and a.daystr=b.daystr and a.custid=b.custid";
		qry += "\n    where a.daystr=v_nowdate0 ";
		// qry += "\n and a.daystr>='"+Accbegindate+"'";
		if (accid > 0)
			qry += "\n    and exists (select 1 from customer c where c.accid=v_accid and c.custid=a.custid )";
		qry += "\n     group by a.custid,a.wareid";
		qry += "\n     union all  ";
		// --期初入库
		qry += "\n     select a.custid,b.wareid,0 as amountqc,0 as currqc,sum(b.amount) as amountrk,SUM(b.curr) as currrk,0 as amountrt,0 as amountck";
		qry += "\n     ,0 as amountpy,0 as amountpk";
		qry += "\n     from firstcusth a join firstcustm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n     where a.statetag=1  ";
		if (accid > 0)
			qry += "\n  and a.accid=" + accid;
		qry += "\n     and a.notedate>=to_date(v_nowdate||' 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n     group by a.custid,b.wareid ";
		qry += "\n     union all  ";
		// --批发入库
		qry += "\n     select a.custid,b.wareid,0 as amountqc,0 as currqc,sum(b.amount) as amountrk,SUM(b.curr) as currrk,0 as amountrt,0 as amountck";
		qry += "\n     ,0 as amountpy,0 as amountpk";
		qry += "\n     from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n     where a.statetag=1 and a.ntid=1  ";
		if (accid > 0)
			qry += "\n  and a.accid=" + accid;
		qry += "\n     and a.notedate>=to_date(v_nowdate||' 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n     group by a.custid,b.wareid";
		qry += "\n     union all  ";
		// --批发退货
		qry += "\n    select a.custid,b.wareid,0 as amountqc,0 as currqc,0 as amountrk,0 as currrk,sum(b.amount) as amountrt ,0 as amountck";
		qry += "\n     ,0 as amountpy,0 as amountpk";
		qry += "\n     from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n    where a.statetag=1 and a.ntid=2";
		if (accid > 0)
			qry += "\n  and a.accid=" + accid;
		qry += "\n     and a.notedate>=to_date(v_nowdate||' 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n     group by a.custid,b.wareid  ";
		qry += "\n     union all";
		// --客户销售出库
		qry += "\n      select a.custid,b.wareid,0 as amountqc,0 as currqc,0 as amountrk,0 as currrk,0 as amountrt,sum(b.amount) as amountck";
		qry += "\n     ,0 as amountpy,0 as amountpk";
		qry += "\n     from custsaleh a join custsalem b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n     where  a.statetag=1     ";
		if (accid > 0)
			qry += "\n  and a.accid=" + accid;
		qry += "\n     and a.notedate>=to_date(v_nowdate||' 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n     group by a.custid,b.wareid  ";
		qry += "\n     union all  ";
		// --客户盘点
		qry += "\n     select a.custid,b.wareid,0 as amountqc,0 as currqc,0 as amountrk,0 as currrk,0 as amountrt,0 as amountck";
		qry += "\n     ,sum(case when b.amount>0 then b.amount else 0 end) as amountpy,sum(case when b.amount<0 then -b.amount else 0 end) as amountpk";
		qry += "\n     from custcheckh a join custcheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n     where a.accid=v_accid and  (b.amount>0 or b.amount<0)";
		if (accid > 0)
			qry += "\n  and a.accid=" + accid;
		qry += "\n     and a.notedate>=to_date(v_nowdate||' 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date(v_nowdate||' 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n     group by a.custid,b.wareid";
		qry += "\n     ) group by custid,wareid) a ";
		qry += "\n      left outer join custcost b on a.wareid=b.wareid and b.daystr=v_nowdate0 and b.custid=a.custid";
		qry += "\n     where  exists (select 1 from warecode b1 where b1.accid=v_accid and b1.wareid=a.wareid ) ";
		qry += "\n     ;";
		qry += "\n     v_row cur_data%rowtype; ";
		qry += "\n     begin ";
		qry += "\n    v_count:=0;";
		qry += "\n    for v_row in cur_data loop ";
		qry += "\n      v_costprice:=v_row.costprice;";
		// -- v_costprice1:=v_row.costprice1;
		qry += "\n      v_amount:=v_row.amountqc-v_row.amountrt-v_row.amountpk; ";
		qry += "\n      v_curr:=round(v_amount*v_costprice,2)+v_row.currrk; ";
		// -- v_curr1:=round(v_amount*v_costprice1,2)+v_row.currrk1;
		qry += "\n      v_amount:=v_amount+v_row.amountrk; ";
		qry += "\n      if v_amount>0 then ";
		qry += "\n         v_costprice:=round(v_curr/v_amount,2); ";
		// -- v_costprice1:=round(v_curr1/v_amount,2);
		qry += "\n        end if; ";
		qry += "\n        v_amount:=v_amount-v_row.amountck+v_row.amountpy; ";
		qry += "\n        v_curr:=v_amount*v_costprice;";
		qry += "\n       delete from custcost where daystr=v_nowdate and wareid=v_row.wareid and custid=v_row.custid ; ";
		qry += "\n       insert into custcost (id,daystr,wareid,custid,amount,curr,costprice)  ";
		qry += "\n       values ( custcost_ID.NEXTVAL,v_nowdate,v_row.wareid,v_row.custid,v_amount,v_curr,v_costprice); ";

		qry += "\n       delete from custcost where daystr=v_nowdate1 and wareid=v_row.wareid and custid=v_row.custid ; ";
		qry += "\n       insert into custcost (id,daystr,wareid,custid,amount,curr,costprice)  ";
		qry += "\n       values ( custcost_ID.NEXTVAL,v_nowdate1,v_row.wareid,v_row.custid,v_amount,v_curr,v_costprice); ";

		qry += "\n       v_count:=v_count+1;";
		qry += "\n       if v_count>1000 then ";
		qry += "\n          commit;";
		qry += "\n          v_count:=0; ";
		qry += "\n       end if;";
		qry += "\n     end loop; ";
		qry += "\n     end;     ";
		// --计算客户商品成本end

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		// qry += "\n SELECT ROWID FROM waresum WHERE daystr='2016-03-29' order
		// by rowid; ";// ----按ROWID排序的Cursor，删除条件是XXX=XXXX，根据实际情况来定。

		qry += "\n       SELECT  ROWID from  custsum a where daystr='" + nowdate + "' ";
		if (accid > 0)
			qry += "\n    and exists (select 1 from customer b where b.accid=" + accid + " and b.custid=a.custid ) ";
		qry += "\n       order by rowid; ";

		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from custsum  where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    declare cursor cur_data is ";
		qry += "\n    select custid,wareid,colorid,sizeid,sum(amount) as amount,0 as curr,0 as price  ";
		qry += "\n    from ( ";
		/*--取期初*/
		qry += "\n    select custid,wareid,colorid,sizeid,amount,curr from custsum a where daystr='" + nowdate0 + "' and (amount>0 or amount<0) and houseid>0";
		if (accid > 0)
			qry += "\n    and exists (select 1 from customer b where b.accid=" + accid + " and b.custid=a.custid and b.statetag=1 ) ";
		else
			qry += "\n    and exists (select 1 from customer b where b.custid=a.custid and b.statetag=1 ) ";
		// else qry += " and exists (select 1 from warecode a1 join accreg b1 on
		// a1.accid=b1.accid where a.wareid=a1.wareid and
		// b1.begindate<to_date('" + nowdate + " 23:59:59" + "','yyyy-mm-dd
		// hh24:mi:ss' ))";
		qry += "\n    union all ";
		/*--批发入库*/
		qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount,SUM(b.curr) as curr ";
		qry += "\n    from wareouth a join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.ntid=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss' ))";
		qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid ";
		qry += "\n    union all ";
		/*--批发退库*/
		qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amount,-SUM(b.curr) as curr ";
		qry += "\n    from wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.ntid=2 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid ";
		qry += "\n    union all ";
		/*--期初入库*/
		qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount,SUM(b.curr) as curr ";
		qry += "\n    from firstcusth a join firstcustm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid ";
		/*--客户销售   */
		qry += "\n    union all ";
		qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid,-sum(b.amount) as amount,-SUM(b.curr) as curr ";
		qry += "\n    from custsaleh a join custsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    where a.statetag=1 and (a.ntid=0 or a.ntid=1) and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd') ";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid ";
		/*--客户盘存  */
		qry += "\n    union all  ";
		qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount,SUM(b.curr) as curr  ";
		qry += "\n    from custcheckh a join custcheckm b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n    where a.statetag=1 and (b.amount>0 or b.amount<0) and a.notedate>=to_date('" + nowdate + "','yyyy-mm-dd') and a.notedate<to_date('" + nowdate1 + "','yyyy-mm-dd')";
		if (accid > 0)
			qry += " and a.accid=" + accid;
		// else qry += " and exists (select 1 from accreg b1 where
		// a.accid=b1.accid and b1.begindate<to_date('" + nowdate + " 23:59:59"
		// + "','yyyy-mm-dd hh24:mi:ss') )";
		qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid ";

		qry += "\n    ) group by custid,wareid,colorid,sizeid ; ";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin ";
		qry += "\n      v_count:=0; ";
		qry += "\n      for v_row in cur_data loop ";
		qry += "\n        delete from custsum where daystr='" + nowdate + "' and custid=v_row.custid   and wareid=v_row.wareid  and colorid=v_row.colorid and sizeid=v_row.sizeid ; ";
		qry += "\n        insert into custsum (id,daystr,custid,wareid,colorid,sizeid,amount,curr,price) ";
		qry += "\n        values ( custsum_id.nextval,'" + nowdate + "',v_row.custid,v_row.wareid,v_row.colorid,v_row.sizeid,nvl(v_row.amount,0),nvl(v_row.curr,0),nvl(v_row.price,0) ); ";
		qry += "\n        v_count:=v_count+1; ";
		qry += "\n        if v_count>1000 then ";
		qry += "\n           v_count:=0; ";
		qry += "\n           commit; ";
		qry += "\n        end if; ";
		qry += "\n      end loop; ";
		qry += "\n    end;  ";

		qry += "\n    commit; ";

		qry += "\n end;";
		DbHelperSQL db = new DbHelperSQL();
		int ret = db.ExecuteSql(qry, 1800);
		if (ret < 0)
			errmess = db.getErrmess();
		else
			errmess = "汇总成功！";
		return ret;

	}

}