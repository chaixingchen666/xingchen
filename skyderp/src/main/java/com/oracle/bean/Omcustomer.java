package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-22 19:16:28
//*************************************
public class Omcustomer implements java.io.Serializable {
	private Long Omid;
	private Long Custid;
	private Integer Statetag;
	private String Orderno;
	private String Lastop;
	private Date Lastdate;
	private Long Zbamt;
	private Long Zbcurr;
	private Long Zbskc;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setOmid(Long omid) {
		this.Omid = omid;
	}

	public Long getOmid() {
		return Omid;
	}

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setOrderno(String orderno) {
		this.Orderno = orderno;
	}

	public String getOrderno() {
		return Orderno;
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

	public void setZbamt(Long zbamt) {
		this.Zbamt = zbamt;
	}

	public Long getZbamt() {
		return Zbamt;
	}

	public void setZbcurr(Long zbcurr) {
		this.Zbcurr = zbcurr;
	}

	public Long getZbcurr() {
		return Zbcurr;
	}

	public void setZbskc(Long zbskc) {
		this.Zbskc = zbskc;
	}

	public Long getZbskc() {
		return Zbskc;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// 取消定单
	public int CancelOrder(long accid) {
		String qry = "declare  ";
		qry += "\n      v_noteno varchar2(20); ";
		qry += "\n      v_retcs varchar2(200); ";
		qry += "\n  begin ";
		qry += "\n   begin ";
		qry += "\n     select orderno into v_noteno from omcustomer where statetag=2 and omid=" + Omid + " and custid=" + Custid + "; ";

		qry += "\n     delete from custorderh where accid=" + accid + " and noteno=v_noteno;";
		qry += "\n     delete from custorderm where accid=" + accid + " and noteno=v_noteno;";
		qry += "\n     update omcustomer set statetag=1,orderno='',lastop='" + Lastop + "' where  statetag=2 and omid=" + Omid + " and custid=" + Custid + ";";
		qry += "\n     commit;";
		qry += "\n     v_retcs:= '1操作成功！'; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN      ";
		qry += "\n     v_retcs:= '0订货会状态信息异常，请重新进入再试！' ; ";
		qry += "\n   end;";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n  end;";

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
		// if (retcs.substring(0, 1).equals("0")) {
		// return 0;
		// } else {
		// return 1;
		// }
	}

	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update omcustomer set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增减订货会客户(代理商、经销商)
	public int doWrite(int value) {
		if (Omid == 0 || Custid == 0) {
			errmess = "参数无效！";
			return 0;
		}

		String qry = "declare ";
		qry += "   v_count number; ";
		qry += "begin ";
		if (value == 1) {
			qry += "   select count(*) into v_count from Omcustomer where custid=" + Custid + " and omid=" + Omid + "; ";
			qry += "   if v_count=0 then";
			qry += "      insert into Omcustomer (omid,custid,lastdate,lastop,statetag)";
			qry += "      values (" + Omid + "," + Custid + ",sysdate,'" + Lastop + "',1); ";
			qry += "   end if; ";
		} else {
			qry += "   delete from Omcustomer where omid=" + Omid + " and custid=" + Custid + "; ";
		}
		qry += " end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	public int doWriteAll(long accid, int value) {
		if (Omid == 0) {
			errmess = "参数无效！";
			return 0;
		}

		String qry = "declare ";
		qry += "\n   v_count number;";
		qry += "\n begin  ";
		qry += "\n  delete from omcustomer where omid=" + Omid + " and (length(orderno)=0 or orderno is null);";
		if (value == 1) // 全选
		{

			qry += "\n   declare cursor cur_data is ";
			qry += "\n     select a.custid from customer a";
			qry += "\n     where a.accid=" + accid + " and a.statetag=1 and a.noused=0";
			qry += "\n     and not exists (select 1 from omcustomer b where b.omid=" + Omid + " and a.custid=b.custid);";
			qry += "\n     v_row cur_data%rowtype;";
			qry += "\n  begin";
			qry += "\n     for v_row in cur_data loop";
			qry += "\n        select count(*) into v_count from omcustomer where omid=" + Omid + " and custid=v_row.custid;";
			qry += "\n        if v_count=0 then";
			qry += "\n           insert into omcustomer (omid,custid,statetag,lastop)";
			qry += "\n           values (" + Omid + ",v_row.custid,1,'" + Lastop + "');";
			qry += "\n        end if;";
			qry += "\n     end loop;";
			qry += "\n  end;";

		}
		qry += "\n end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}// 增加记录

	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Omid != null) {
			strSql1.append("omid,");
			strSql2.append(Omid + ",");
		}
		if (Custid != null) {
			strSql1.append("custid,");
			strSql2.append(Custid + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Orderno != null) {
			strSql1.append("orderno,");
			strSql2.append("'" + Orderno + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Zbamt != null) {
			strSql1.append("zbamt,");
			strSql2.append(Zbamt + ",");
		}
		if (Zbcurr != null) {
			strSql1.append("zbcurr,");
			strSql2.append(Zbcurr + ",");
		}
		if (Zbskc != null) {
			strSql1.append("zbskc,");
			strSql2.append(Zbskc + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=omcustomer_id.nextval; ");
		strSql.append("   insert into omcustomer (");
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
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update omcustomer set ");
		if (Omid != null) {
			strSql.append("omid=" + Omid + ",");
		}
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Orderno != null) {
			strSql.append("orderno='" + Orderno + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Zbamt != null) {
			strSql.append("zbamt=" + Zbamt + ",");
		}
		if (Zbcurr != null) {
			strSql.append("zbcurr=" + Zbcurr + ",");
		}
		if (Zbskc != null) {
			strSql.append("zbskc=" + Zbskc + ",");
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
		strSql.append(" FROM omcustomer ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omcustomer ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM omcustomer ");
		// if (!strWhere.equals("")) {
		// strSql.append(" where " + strWhere);
		// }
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from omcustomer");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}
}