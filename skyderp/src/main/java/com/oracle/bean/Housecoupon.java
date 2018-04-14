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
//  @author:sunhong  @date:2017-03-15 21:16:42
//*************************************
public class Housecoupon implements java.io.Serializable {
	private Long Cpid;
	private Long Onhouseid;
	private Float Curr;
	private Float Xfcurr;
	private Long Totalnum;
	private Long Factnum;
	private Long Usenum;
	private String Remark;
	private String Begindate;
	private String Overdate;
	private Date Lastdate;
	private String Lastop;
	private Integer Statetag;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setCpid(Long cpid) {
		this.Cpid = cpid;
	}

	public Long getCpid() {
		return Cpid;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
	}

	public void setXfcurr(Float xfcurr) {
		this.Xfcurr = xfcurr;
	}

	public Float getXfcurr() {
		return Xfcurr;
	}

	public void setTotalnum(Long totalnum) {
		this.Totalnum = totalnum;
	}

	public Long getTotalnum() {
		return Totalnum;
	}

	public void setFactnum(Long factnum) {
		this.Factnum = factnum;
	}

	public Long getFactnum() {
		return Factnum;
	}

	public void setUsenum(Long usenum) {
		this.Usenum = usenum;
	}

	public Long getUsenum() {
		return Usenum;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setBegindate(String begindate) {
		this.Begindate = begindate;
	}

	// public String getBegindate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Begindate);
	// }

	public void setOverdate(String overdate) {
		this.Overdate = overdate;
	}

	// public String getOverdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Overdate);
	// }

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
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
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		// strSql.append(" update housecoupon set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		return 1;
	}

	// 增加记录
	public int Append() {
		if (Onhouseid == null || Onhouseid == 0) {
			errmess = "缺少线上店铺id参数！";
			return 0;
		}
		// decimal curr = 0;
		// if (ht["curr"] != null) curr = decimal.Parse(ht["curr"].ToString());
		if (Curr == null || Curr == 0) {
			errmess = "优惠券金额不能为零！";
			return 0;
		}
		if (Begindate == null || Begindate.equals("")) {
			errmess = "有效期无效1！";
			return 0;
		}
		if (Overdate == null || Overdate.equals("")) {
			errmess = "有效期无效2！";
			return 0;
		}
		if (Xfcurr == null)
			Xfcurr = (float) 0;
		if (Xfcurr < Curr) {
			errmess = "使用券的最低消费金额不能小于优惠券金额！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("cpid,");
		strSql2.append("v_id,");
		if (Onhouseid != null) {
			strSql1.append("onhouseid,");
			strSql2.append(Onhouseid + ",");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
		}
		if (Xfcurr != null) {
			strSql1.append("xfcurr,");
			strSql2.append("'" + Xfcurr + "',");
		}
		if (Totalnum != null) {
			strSql1.append("totalnum,");
			strSql2.append(Totalnum + ",");
		}
		if (Factnum != null) {
			strSql1.append("factnum,");
			strSql2.append(Factnum + ",");
		}
		if (Usenum != null) {
			strSql1.append("usenum,");
			strSql2.append(Usenum + ",");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Begindate != null) {
			strSql1.append("begindate,");
			strSql2.append("to_date('" + Begindate + "','yyyy-mm-dd'),");
		}
		if (Overdate != null) {
			strSql1.append("overdate,");
			strSql2.append("to_date('" + Overdate + "','yyyy-mm-dd'),");
		}
		// if (Lastop != null) {
		// }
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from housecoupon where onhouseid=" + Onhouseid + " and to_char(sysdate,'yyyy-mm-dd')<=to_char(overdate,'yyyy-mm-dd');");
		strSql.append("\n   if v_count>=20 then ");
		strSql.append("\n      v_retcs:='0店铺在用优惠卷最多只允许发送20种！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if; ");
		strSql.append("\n   v_id:=housecoupon_cpid.nextval; ");
		strSql.append("\n   insert into housecoupon (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n  v_retcs:='1'||v_id; ");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual; ");

		strSql.append("\n end;");
	//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		if (retcs.substring(0, 1).equals("0")) {
			errmess = retcs.substring(1);

			return 0;
		} else {
			errmess = "{\"result\":\"1\",\"msg\":\"操作成功！\",\"id\":\"" + retcs.substring(1) + "\",\"remark\":\"" + Remark + "\"}";

			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update housecoupon set ");
		if (Cpid != null) {
			strSql.append("cpid=" + Cpid + ",");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		if (Xfcurr != null) {
			strSql.append("xfcurr='" + Xfcurr + "',");
		}
		if (Totalnum != null) {
			strSql.append("totalnum=" + Totalnum + ",");
		}
		if (Factnum != null) {
			strSql.append("factnum=" + Factnum + ",");
		}
		if (Usenum != null) {
			strSql.append("usenum=" + Usenum + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Begindate != null) {
			strSql.append("begindate=to_date('" + Begindate + "','yyyy-mm-dd'),");
		}
		if (Overdate != null) {
			strSql.append("overdate=to_date('" + Overdate + "','yyyy-mm-dd'),");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
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
		strSql.append(" FROM housecoupon ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM housecoupon a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from housecoupon");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 更改店铺优惠券状态
	public int Change() {
		// statetag: 0=取消发布 1=发布 2=过期 3=删除
		String qry = "declare";
		qry += "\n    v_count number;";
		qry += "\n begin";
		if (Statetag == 2) {
			qry += "\n  select count(*) into v_count from vipcoupon where cpid=" + Cpid + " ;";
			qry += "\n  if v_count>0 then";
			// qry += "\n select '1优惠券已有会员领用，不允许删除!' into :retcs from dual;";
			qry += "\n     update Housecoupon set statetag=3 where cpid=" + Cpid + " and onhouseid=" + Onhouseid + ";";
			qry += "\n  else";
			qry += "\n     delete from Housecoupon where cpid=" + Cpid + " and onhouseid=" + Onhouseid + ";";
			qry += "\n  end if;";
		} else {
			qry += "\n   update Housecoupon set statetag=" + Statetag + " where cpid=" + Cpid + " and onhouseid=" + Onhouseid + ";";
			if (Statetag == 1) {
				qry += "\n   update onlinehouse set lastdate=sysdate where onhouseid=" + Onhouseid + ";";
			}

		}
		// qry += "\n select '0操作成功!' into :retcs from dual;";
		qry += "\n end;";

		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功!";
			return 1;
		}
	}
}