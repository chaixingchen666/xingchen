package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-03-31 19:49:22
//*************************************
public class Mobilearea implements java.io.Serializable {
	private String Mobile;
	private String Province;
	private String City;
	private String Zone;
	private String Postcode;
	private String Remark;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setProvince(String province) {
		this.Province = province;
	}

	public String getProvince() {
		return Province;
	}

	public void setCity(String city) {
		this.City = city;
	}

	public String getCity() {
		return City;
	}

	public void setZone(String zone) {
		this.Zone = zone;
	}

	public String getZone() {
		return Zone;
	}

	public void setPostcode(String postcode) {
		this.Postcode = postcode;
	}

	public String getPostcode() {
		return Postcode;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Mobile == null || Mobile.length() <= 0) {
			errmess = "mobile不是一个有效的值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		strSql.append(" delete from MOBILEAREA ");
		strSql.append(" where Mobile='" + Mobile + "'");
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
		if (Mobile == null || Mobile.length() <= 0) {
			errmess = "mobile不是一个有效的值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		// strSql1.append("id,");
		// strSql2.append("v_id,");
		// if (Mobile != null) {
		strSql1.append("mobile,");
		strSql2.append("'" + Mobile + "',");
		// }
		if (Province != null) {
			strSql1.append("province,");
			strSql2.append("'" + Province + "',");
		}
		if (City != null) {
			strSql1.append("city,");
			strSql2.append("'" + City + "',");
		}
		if (Zone != null) {
			strSql1.append("zone,");
			strSql2.append("'" + Zone + "',");
		}
		if (Postcode != null) {
			strSql1.append("postcode,");
			strSql2.append("'" + Postcode + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("  v_count number;");

		strSql.append("  v_retcs varchar2(200);");
		strSql.append(" begin ");
		strSql.append("   select count(*) into v_count from MOBILEAREA where mobile='" + Mobile + "';");
		strSql.append("   if v_count>0 then ");
		strSql.append("      v_retcs:='0移动电话已存在！';");
		strSql.append("      goto exit;");
		strSql.append("   end if;");
		strSql.append("   insert into MOBILEAREA (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append(" <<exit>>");
		strSql.append(" select v_retcs into :retcs from dual;");

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
		if (Mobile == null || Mobile.length() <= 0) {
			errmess = "mobile不是一个有效的值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update MOBILEAREA set ");
		// if (Mobile != null) {
		// strSql.append("mobile='" + Mobile + "',");
		// }
		if (Province != null) {
			strSql.append("province='" + Province + "',");
		}
		if (City != null) {
			strSql.append("city='" + City + "',");
		}
		if (Zone != null) {
			strSql.append("zone='" + Zone + "',");
		}
		if (Postcode != null) {
			strSql.append("postcode='" + Postcode + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where Mobile=" + Mobile + " ;");
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
		strSql.append(" FROM MOBILEAREA ");
		if (strWhere.length()>0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM MOBILEAREA ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from MOBILEAREA");
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