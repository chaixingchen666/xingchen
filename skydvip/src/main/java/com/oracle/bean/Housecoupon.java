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
//  @author:sunhong  @date:2017-02-25 18:04:38
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
	private Date Begindate;
	private Date Overdate;
	private Date Lastdate;
	private String Lastop;
	private Integer Statetag;
	private String errmess;
//	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

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

	public void setBegindate(Date begindate) {
		this.Begindate = begindate;
	}

	public String getBegindate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Begindate);
	}

	public void setOverdate(Date overdate) {
		this.Overdate = overdate;
	}

	public String getOverdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Overdate);
	}

//	public void setLastdate(Date lastdate) {
//		this.Lastdate = lastdate;
//	}
//
//	public String getLastdate() {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		return df.format(Lastdate);
//	}

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
//
//	public void setAccbegindate(String accbegindate) {
//		this.Accbegindate = accbegindate;
//	}

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
		strSql.append(" update Housecoupon set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
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
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Cpid != null) {
			strSql1.append("cpid,");
			strSql2.append(Cpid + ",");
		}
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
			strSql2.append("'" + Begindate + "',");
		}
		if (Overdate != null) {
			strSql1.append("overdate,");
			strSql2.append("'" + Overdate + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Housecoupon_id.nextval; ");
		strSql.append("   insert into Housecoupon (");
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
		return ret;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Housecoupon set ");
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
			strSql.append("begindate='" + Begindate + "',");
		}
		if (Overdate != null) {
			strSql.append("overdate='" + Overdate + "',");
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
		return DbHelperSQL.ExecuteSql(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Housecoupon ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Housecoupon ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}
	// 获取分页数据
	public Table GetTable(QueryParam qp) {
//		StringBuilder strSql = new StringBuilder();
//		strSql.append("select " + fieldlist);
//		strSql.append(" FROM Housecoupon ");
//		if (!strWhere.equals("")) {
//			strSql.append(" where " + strWhere);
//		}
//		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Housecoupon");
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