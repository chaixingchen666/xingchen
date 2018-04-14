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
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-22 11:22:01
//*************************************
public class Omorder implements java.io.Serializable {
	private Long Id;
	private Long Omid;
	private Long Omepid;
	private Long Accid;
	private String Noteno;
	private Long Totalamt;
	private Long Totalcurr;
	private Long Totalnum;
	private String Lastop;
	private Date Lastdate;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setId(Long id) {
		this.Id = id;
	}

	public Long getId() {
		return Id;
	}

	public void setOmid(Long omid) {
		this.Omid = omid;
	}

	public Long getOmid() {
		return Omid;
	}

	public void setOmepid(Long omepid) {
		this.Omepid = omepid;
	}

	public Long getOmepid() {
		return Omepid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setTotalamt(Long totalamt) {
		this.Totalamt = totalamt;
	}

	public Long getTotalamt() {
		return Totalamt;
	}

	public void setTotalcurr(Long totalcurr) {
		this.Totalcurr = totalcurr;
	}

	public Long getTotalcurr() {
		return Totalcurr;
	}

	public void setTotalnum(Long totalnum) {
		this.Totalnum = totalnum;
	}

	public Long getTotalnum() {
		return Totalnum;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	// public void setLastdate(Date lastdate) {
	// this.Lastdate = lastdate;
	// }
	//
	// public String getLastdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Lastdate);
	// }

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {

		if (Noteno.equals("")) {
			errmess = "单据号无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("begin");
		strSql.append("\n    delete from custorderh where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n    delete from custorderm where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n    delete from omorder where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n    insert into LOGRECORD (id,accid,progname,remark,lastop)");
		strSql.append("\n    values (LOGRECORD_id.nextval," + Accid + ",'订货会','【订单撤单】单据号:" + Noteno + "','" + Lastop + "');");
		strSql.append("\n end;");

		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append(String omepidlist, long custid) {
		// StringBuilder strSql = new StringBuilder();
		if (omepidlist.equals("")) {
			errmess = "参数无效！";
			return 0;
		}

		int priceprec = 0;// int.Parse(qxpublic.Substring(4 - 1, 1));

		String qry = "declare  ";
		qry += "\n    v_paccid number; ";
		qry += "\n    v_count number; ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_totalnum number; ";
		qry += "\n    v_id number; ";
		qry += "\n    v_noteno varchar2(20); ";
		qry += "\n    v_discount number; ";
		qry += "\n    v_pricetype number(1); ";
		qry += "\n    v_notenolist clob;";
		qry += "\n    v_fh varchar2(1); ";
		qry += "\n    v_retcs clob; ";
		qry += "\n    v_ret varchar2(1);";// varchar2(100); ";
		qry += "\n    v_remark varchar2(200);";// varchar2(100); ";
		qry += "\n  begin ";
		qry += "\n    v_notenolist:='';";
		qry += "\n    select omname into v_remark from omsubject where omid=" + Omid + ";"; // 订货会名称填入订单的摘要中
		qry += "\n    begin ";
		qry += "\n      select b.pricetype,b.discount into v_pricetype,v_discount from omcustomer a join customer b on a.custid=b.custid where a.statetag=1 and a.omid=" + Omid + " and a.custid=" + custid + "; ";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN      ";
		qry += "\n      v_retcs:= '0订货会状态信息异常，请重新进入再试！'; ";
		qry += "\n      goto exit; ";
		qry += "\n    end; ";
		// 每个订货人产生一张定单
		qry += "\n  begin ";
		qry += "\n    v_fh:='';v_ret:='1';v_notenolist:='';";
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select distinct a.omepid,b.omepname from omcustware a join omuser b on a.omepid=b.omepid ";
		qry += "\n      where a.omid=" + Omid;
		qry += "\n      and (" + Func.getCondition("a.omepid", omepidlist) + ")";
		qry += "\n      and not exists (select 1 from omorder c where a.omid=c.omid and a.omepid=c.omepid)";
		// js:0=公司，1=自营店，2=经销商
		// if (fs == 1) qry += " and b.js=2";
		// else if (fs == 2) qry += " and (b.js=2 or b.js=1)";
		qry += "\n      ;";
		qry += "\n        v_row cur_data%rowtype;";
		qry += "\n      begin";
		qry += "\n        v_count:=0;";
		qry += "\n        for v_row in cur_data loop";
		// 判断必定款是否定货
		// qry += "\n select count(distinct wareid) into v_count from omwarecode a where a.omid=" + Omid + " and a.mustbj=1 and a.noused=0";
		// qry += "\n and not exists (select 1 from omcustware b ";
		// qry += "\n where a.omid=b.omid and a.wareid=b.wareid ";
		// qry += "\n and b.omepid=v_row.omepid and b.amount>0);";
		// qry += "\n if v_count>0 then";// 有未定货的必定款
		// qry += "\n v_notenolist:=v_fh||'用户:'||v_row.omepname||'有'||v_count||'款商品必须定货！';";
		// qry += "\n v_ret:='0';";
		// qry += "\n v_fh:=',';";
		// qry += "\n else";
		// 生成单据号
		qry += "\n             select 'KO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from custorderh ";
		qry += "\n             where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'KO'||To_Char(SYSDATE,'yyyymmdd'); ";
		qry += "\n             insert into custorderh (id, accid,noteno,notedate,custid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate,ywly) ";
		qry += "\n             values (custorderh_ID.NEXTVAL," + Accid + ",v_noteno,sysdate," + custid + ",1,0,0,v_remark,'',v_row.omepname,'" + Lastop + "',sysdate,1); ";
		// --插入订货会商品明细记录
		qry += "\n             insert into custorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0) ";
		qry += "\n             select custorderm_id.nextval," + Accid + ",v_noteno,wareid,colorid,sizeid,amount,0,v_discount,0,0,'' from ( ";
		qry += "\n             select wareid,colorid,sizeid,sum(amount) as amount ";
		qry += "\n             from omcustware a where a.omid=" + Omid;
		qry += "\n             and a.omepid=v_row.omepid and a.amount>0";
		qry += "\n             and exists (select 1 from omwarecode c where a.wareid=c.wareid and a.omid=c.omid and c.noused=0)";
		qry += "\n             and not exists (select 1 from omwarenocolor d where a.omid=d.omid and a.wareid=d.wareid and a.colorid=d.colorid)";

		qry += "\n             group by wareid,colorid,sizeid );";

		// qry += "\n commit;";

		// 更新价格
		qry += "\n             update custorderm set price0=(select case v_pricetype when 1 then sale1  when 2 then sale2 when 3 then sale3 when 4 then sale4  when 5 then sale5 else retailsale end ";
		qry += "\n             from warecode where custorderm.wareid=warecode.wareid)";
		qry += "\n             where accid=" + Accid + " and noteno=v_noteno; ";
		// qry += "\n commit;";

		// 特价商品不打折
		qry += "\n             update custorderm a set discount=1";
		qry += "\n             where a.accid=" + Accid + " and a.noteno=v_noteno ";
		qry += "\n             and exists (select 1 from warecode b where a.wareid=b.wareid and b.tjtag=1);";
		// qry += "\n commit;";

		qry += "\n             update custorderm set price=round(price0*discount," + priceprec + "),curr=round(round(price0*discount," + priceprec + "),2)*amount ";
		qry += "\n             where accid=" + Accid + " and noteno=v_noteno;";
		// qry += "\n commit;";
		// 汇总数量，金额
		qry += "\n             select nvl(sum(amount),0),nvl(sum(curr),0),count(distinct wareid) into v_totalamt,v_totalcurr,v_totalnum from custorderm where accid=" + Accid + " and noteno=v_noteno;  ";
		qry += "\n             update custorderh set totalcurr=v_totalcurr,totalamt=v_totalamt  ";
		qry += "\n             where accid=" + Accid + " and noteno=v_noteno; ";

		qry += "\n             delete from OMORDER where omid=" + Omid + " and omepid=v_row.omepid ;";
		qry += "\n             insert into OMORDER (id,omid,omepid,accid,noteno,totalamt,totalnum,totalcurr,lastop)";
		qry += "\n             values (OMORDER_id.nextval," + Omid + ",v_row.omepid," + Accid + ",v_noteno,v_totalamt,v_totalnum,v_totalcurr,'" + Lastop + "');";

		// qry += "\n v_notenolist:=v_notenolist||v_noteno;";

		qry += "\n             insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n             values (LOGRECORD_id.nextval," + Accid + ",'订货会','【生成订单】订单号:'||v_noteno,'" + Lastop + "');";
		qry += "\n             commit;";
		qry += "\n             v_count:=v_count+1;";
		// qry += "\n end if;";
		qry += "\n        end loop;";
		qry += "\n      end;";
		// qry += "\n if v_notenolist='' then ";
		// qry += "\n v_notenolist:='操作成功！';";
		// qry += "\n end if;";
		// }

		qry += "\n      if v_count>0 then ";
		qry += "\n         v_retcs:='1'||'本次共生成'||v_count||'张订单！' ; ";
		qry += "\n      else ";
		qry += "\n         v_retcs:='1'||'本次未生成订单！' ; ";
		qry += "\n      end if;";

		qry += "\n      goto exit; ";
		qry += "\n      exception ";
		qry += "\n      when others then  ";
		qry += "\n      begin ";
		qry += "\n        rollback; "; // 回滚
		qry += "\n        v_retcs:= '0生成订单失败' ; ";
		qry += "\n        goto exit; ";
		qry += "\n      end; ";
		qry += "\n    end; ";
		qry += "\n    <<exit>>";
		qry += "\n    select v_retcs into :retcs from dual;";
		qry += "\n end; ";
		// LogUtils.LogDebugWrite("omorder.append", qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("0"))
			return 0;
		else
			return 1;
	}

	// 微信生成客户订单
	public int Append(long custid) {
		// StringBuilder strSql = new StringBuilder();

		int priceprec = 0;// int.Parse(qxpublic.Substring(4 - 1, 1));

		String qry = "declare  ";
		qry += "\n    v_paccid number; ";
		qry += "\n    v_count number; ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_totalnum number; ";
		qry += "\n    v_id number; ";
		qry += "\n    v_noteno varchar2(20); ";
		qry += "\n    v_discount number; ";
		qry += "\n    v_pricetype number(1); ";
		// qry += "\n v_notenolist clob;";
		qry += "\n    v_fh varchar2(1); ";
		qry += "\n    v_retcs clob; ";
		qry += "\n    v_ret varchar2(1);";// varchar2(100); ";
		qry += "\n  begin ";
		qry += "\n    v_ret:='1';";
		qry += "\n  begin ";
		qry += "\n    select pricetype,discount into v_pricetype,v_discount from customer where custid=" + custid + ";";
		// 生成单据号
		qry += "\n    select 'KO'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from custorderh ";
		qry += "\n    where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'KO'||To_Char(SYSDATE,'yyyymmdd'); ";
		qry += "\n    insert into custorderh (id, accid,noteno,notedate,custid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate) ";
		qry += "\n    values (custorderh_ID.NEXTVAL," + Accid + ",v_noteno,sysdate," + custid + ",1,0,0,'','','" + Lastop + "','" + Lastop + "',sysdate); ";
		// --插入订货会商品明细记录
		qry += "\n    insert into custorderm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0) ";
		qry += "\n    select custorderm_id.nextval," + Accid + ",v_noteno,wareid,colorid,sizeid,amount,0,v_discount,0,0,'' from ( ";
		qry += "\n    select wareid,colorid,sizeid,sum(amount) as amount ";
		qry += "\n    from omcustware a where a.omid=0 and a.omepid=" + Omepid;
		qry += "\n    and a.amount>0";
		qry += "\n    group by wareid,colorid,sizeid );";

		qry += "\n    commit;";
		// 更新价格
		qry += "\n    update custorderm set price0=(select case v_pricetype when 1 then sale1  when 2 then sale2 when 3 then sale3 when 4 then sale4  when 5 then sale5 else retailsale end ";
		qry += "\n    from warecode where custorderm.wareid=warecode.wareid)";
		qry += "\n    where accid=" + Accid + " and noteno=v_noteno; ";
		qry += "\n    commit;";

		// 特价商品不打折
		qry += "\n    update custorderm a set discount=1";
		qry += "\n    where a.accid=" + Accid + " and a.noteno=v_noteno ";
		qry += "\n    and exists (select 1 from warecode b where a.wareid=b.wareid and b.tjtag=1);";
		qry += "\n    commit;";

		qry += "\n    update custorderm set price=round(price0*discount," + priceprec + "),curr=round(round(price0*discount," + priceprec + "),2)*amount ";
		qry += "\n    where accid=" + Accid + " and noteno=v_noteno;";
		qry += "\n    commit;";
		// 汇总数量，金额
		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0),count(distinct wareid) into v_totalamt,v_totalcurr,v_totalnum from custorderm where accid=" + Accid + " and noteno=v_noteno;  ";
		qry += "\n    update custorderh set totalcurr=v_totalcurr,totalamt=v_totalamt  ";
		qry += "\n    where accid=" + Accid + " and noteno=v_noteno; ";
		qry += "\n    delete from omcustware where omid=0 and omepid=" + Omepid + ";";
		// }
		qry += "\n      v_retcs:=v_ret||v_noteno ; ";
		qry += "\n      goto exit; ";

		qry += "\n      exception ";
		qry += "\n      when others then  ";
		qry += "\n      begin ";
		qry += "\n        rollback; "; // 回滚
		qry += "\n        v_retcs:= '0生成订单失败' ; ";
		qry += "\n        goto exit; ";
		qry += "\n      end; ";
		qry += "\n    end; ";
		qry += "\n    <<exit>>";
		qry += "\n    select v_retcs into :retcs from dual;";
		qry += "\n end; ";
		// LogUtils.LogDebugWrite("omorder.append", qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("0"))
			return 0;
		else
			return 1;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update omorder set ");
		if (Id != null) {
			strSql.append("id=" + Id + ",");
		}
		if (Omid != null) {
			strSql.append("omid=" + Omid + ",");
		}
		if (Omepid != null) {
			strSql.append("omepid=" + Omepid + ",");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Noteno != null) {
			strSql.append("noteno='" + Noteno + "',");
		}
		if (Totalamt != null) {
			strSql.append("totalamt=" + Totalamt + ",");
		}
		if (Totalcurr != null) {
			strSql.append("totalcurr=" + Totalcurr + ",");
		}
		if (Totalnum != null) {
			strSql.append("totalnum=" + Totalnum + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omorder ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omorder ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from omorder");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	// 显示客户订货的商品汇总列表
	public Table listOmOrderList(QueryParam qp, long custid, String fieldlist, String findbox) {

		String qry = "select  sum(b.amount) as totalamt,sum(b.curr) as totalcurr,count(distinct b.wareid) as totalnum";
		qry += "\n  from custorderh a";
		qry += "\n  join custorderm b on a.accid=b.accid and a.noteno=b.noteno ";// join omorder b on a.accid=b.accid and a.noteno=b.noteno";
		// qry += "\n join omorder c on a.accid=c.accid and a.noteno=c.noteno and c.omid=" + omid;
		qry += "\n  where a.accid=" + Accid;// + " and a.statetag=1";
		if (!findbox.equals(""))
			qry += " and (a.noteno like '%" + findbox + "%' or a.operant like '%" + findbox + "%')";
		qry += "\n  and a.custid=" + custid;
		qry += "\n  and exists (select 1 from omorder c where a.accid=c.accid and a.noteno=c.noteno and c.omid=" + Omid + ")";

		float totalamt = 0;
		float totalcurr = 0;
		int totalnum = 0;

		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() > 0) {
			if (tb.getRow(0).get("TOTALAMT").toString() != "")
				totalamt = Float.parseFloat(tb.getRow(0).get("TOTALAMT").toString());
			if (tb.getRow(0).get("TOTALCURR").toString() != "")
				totalcurr = Float.parseFloat(tb.getRow(0).get("TOTALCURR").toString());
			if (tb.getRow(0).get("TOTALNUM").toString() != "")
				totalnum = Integer.parseInt(tb.getRow(0).get("TOTALNUM").toString());
		}

		qry = "select " + fieldlist;
		qry += "\n  from custorderh a";
		qry += "\n  join customer b on a.custid=b.custid ";// join omorder b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n  join omorder c on a.accid=c.accid and a.noteno=c.noteno and c.omid=" + Omid;
		qry += "\n  where a.accid=" + Accid;// + " and a.statetag=1";

		if (!findbox.equals(""))
			qry += " and (a.noteno like '%" + findbox + "%' or a.operant like '%" + findbox + "%')";
		qry += "\n  and a.custid=" + custid;
		// System.out.println("qqqq1="+qry);
		qp.setQueryString(qry);
		qp.setSumString(totalamt + " as totalamt," + totalcurr + " as totalcurr," + totalnum + " as totalnum");
		return DbHelperSQL.GetTable(qp);

	}

}