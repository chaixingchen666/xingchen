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
//  @author:sunhong  @date:2017-02-26 22:46:57
//*************************************
public class Vipmessage implements java.io.Serializable {
	private Float Messid;
	private Long Vipid;
	private Long Onhouseid;
	private String Remark;
	private Integer Rdbj;
	private Integer Statetag;
	private Integer Lx;
	private Integer Jg;
	private Date Lastdate;
	private String Lastop;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setMessid(Float messid) {
		this.Messid = messid;
	}

	public Float getMessid() {
		return Messid;
	}

	public void setVipid(Long vipid) {
		this.Vipid = vipid;
	}

	public Long getVipid() {
		return Vipid;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRdbj(Integer rdbj) {
		this.Rdbj = rdbj;
	}

	public Integer getRdbj() {
		return Rdbj;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setLx(Integer lx) {
		this.Lx = lx;
	}

	public Integer getLx() {
		return Lx;
	}

	public void setJg(Integer jg) {
		this.Jg = jg;
	}

	public Integer getJg() {
		return Jg;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {
		if (Messid==0)
		{
			errmess="messid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		strSql.append(" update Vipmessage set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where messid=" + Messid );
		if (DbHelperSQL.ExecuteSql(strSql.toString()) == 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("messid,");
		strSql2.append("v_id,");
		// if (Messid != null) {
		// strSql1.append("messid,");
		// strSql2.append("'" + Messid + "',");
		// }
		if (Vipid != null) {
			strSql1.append("vipid,");
			strSql2.append(Vipid + ",");
		}
		if (Onhouseid != null) {
			strSql1.append("onhouseid,");
			strSql2.append(Onhouseid + ",");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Rdbj != null) {
			strSql1.append("rdbj,");
			strSql2.append(Rdbj + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Lx != null) {
			strSql1.append("lx,");
			strSql2.append(Lx + ",");
		}
		if (Jg != null) {
			strSql1.append("jg,");
			strSql2.append(Jg + ",");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Vipmessage_messid.nextval; ");
		strSql.append("   insert into Vipmessage (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		Messid = Float.parseFloat(param.get("id").getParamvalue().toString());
		return ret;
	}

	// 修改记录
	public int Update(int jg) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_jg number(1);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin ");
		strSql.append("\n  begin ");
		strSql.append("\n    select jg into v_jg from vipmessage where messid=" + Messid + ";");
		if (jg > 0) {
			strSql.append("\n   if v_jg=3 then");
			strSql.append("\n      v_retcs:= '0当前通知消息已失效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		strSql.append("\n    update vipmessage set rdbj=1,jg=" + jg + " where messid=" + Messid + ";");
		strSql.append("\n    v_retcs:= '1操作完成！';");
		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n    v_retcs:= '0未找到通知消息！' ;");
		strSql.append("\n  end;");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));

		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		//System.out.println("1111");
		if (ret == 0) {
			errmess = "操作失败！";
			return 0;
		}
		//System.out.println("2222");
		String retcs = param.get("retcs").getParamvalue().toString();
		//System.out.println("3333 "+retcs);
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));

	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Vipmessage ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Vipmessage a left outer join onlinehouse b on a.onhouseid=b.onhouseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Vipmessage");
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