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
//  @author:sunhong  @date:2017-03-26 12:24:04
//*************************************
public class Custbrand implements java.io.Serializable {
	private Long Custid;
	private Long Brandid;
	private Integer Pricetype;
	private Float Discount;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
	}

	public void setBrandid(Long brandid) {
		this.Brandid = brandid;
	}

	public Long getBrandid() {
		return Brandid;
	}

	public void setPricetype(Integer pricetype) {
		this.Pricetype = pricetype;
	}

	public Integer getPricetype() {
		return Pricetype;
	}

	public void setDiscount(Float discount) {
		this.Discount = discount;
	}

	public Float getDiscount() {
		return Discount;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Custid == null || Brandid == null || Custid == 0 || Brandid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("   delete from custbrand  where custid=" + Custid + " and brandid=" + Brandid + ";");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append() {
		if (Custid == null || Brandid == null || Custid == 0 || Brandid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		if (Pricetype == null) {
			errmess = "价格方式无效！";
			return 0;
		}
		if (Discount == null) {
			errmess = "折扣无效！";
			return 0;

		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("   v_count number(10);");
		strSql.append("   v_retcs varchar2(200);");
		strSql.append(" begin");
		strSql.append("   select count(*) into v_count from custbrand where custid= " + Custid + " and brandid=" + Brandid + ";");
		strSql.append("   if v_count=0 then ");
		strSql.append("      insert into custbrand (custid,brandid,pricetype,discount)");
		strSql.append("      values (" + Custid + "," + Brandid + "," + Pricetype + "," + Discount + ");");
		strSql.append("      v_retcs:= '1增加成功！';");
		strSql.append("   else");
		strSql.append("      v_retcs:= '0品牌折扣已存在！';");
		strSql.append("   end if;");
		strSql.append("   select v_retcs into :retcs from dual;");
		strSql.append(" end;");

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));

	}

	// 修改记录
	public int Update() {
		if (Custid == null || Brandid == null || Custid == 0 || Brandid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		if (Pricetype == null) {
			errmess = "价格方式无效！";
			return 0;
		}
		if (Discount == null) {
			errmess = "折扣无效！";
			return 0;

		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("   v_count number(10);");
		strSql.append(" begin");
		strSql.append("   select count(*) into v_count from custbrand where custid= " + Custid + " and brandid=" + Brandid + ";");
		strSql.append("   if v_count=0 then ");
		strSql.append("      insert into custbrand (custid,brandid,pricetype,discount)");
		strSql.append("      values (" + Custid + "," + Brandid + "," + Pricetype + "," + Discount + ");");
		strSql.append("   else");
		strSql.append("      update custbrand set pricetype=" + Pricetype + ",discount=" + Discount + " where custid=" + Custid + " and brandid=" + Brandid + ";");
		strSql.append("   end if;");
		// }
		strSql.append(" end;");

		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作失败！";
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
		strSql.append(" FROM Custbrand ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM brand a ");
		strSql.append(" left outer join custbrand b on a.brandid=b.brandid and b.custid=" + Custid);
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Custbrand");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}
}