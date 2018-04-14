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
//  @author:sunhong  @date:2017-02-27 22:35:39
//*************************************
public class Receiptaddress implements java.io.Serializable {
	private Float Id;
	private Long Vipid;
	private String Linkman;
	private String Address;
	private String Tel;
	private Integer Statetag;
	private Date Lastdate;
	private Integer Bj;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setVipid(Long vipid) {
		this.Vipid = vipid;
	}

	public Long getVipid() {
		return Vipid;
	}

	public void setLinkman(String linkman) {
		this.Linkman = linkman;
	}

	public String getLinkman() {
		return Linkman;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setBj(Integer bj) {
		this.Bj = bj;
	}

	public Integer getBj() {
		return Bj;
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
		// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		strSql.append(" delete from  receiptaddress ");
		strSql.append(" where id=" + Id + " and vipid=" + Vipid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 设置默认收货地址
	public int DefaultAdd() {
		// string qry = "begin";
		// qry += " update receiptaddress set bj=0 where vipid=" + vipid + " and id<>"+id+";";
		// qry += " update receiptaddress set bj=1 where vipid=" + vipid + " and id=" + id + ";";
		// qry += "end;";

		StringBuilder strSql = new StringBuilder();
		strSql.append("begin");
		strSql.append("   update receiptaddress set bj=0 where vipid=" + Vipid + " and id<>" + Id + ";");
		strSql.append("   update receiptaddress set bj=1 where vipid=" + Vipid + " and id=" + Id + ";");
		strSql.append(" end;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			// errmess = "删除失败！";
			return 0;
		}
		return 1;

	}

	// 增加记录
	public int Append() {

		if (Linkman == null || Linkman.equals("")) {
			errmess = "收货人不允许为空！";
			return 0;
		}
		if (Tel == null || Tel.equals("")) {
			errmess = "联系电话不允许为空！";
			return 0;
		}
		if (Address == null || Address.equals("")) {
			errmess = "收货地址不允许为空！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Vipid != null) {
		strSql1.append("vipid,");
		strSql2.append(Vipid + ",");
		// }
		if (Linkman != null) {
			strSql1.append("linkman,");
			strSql2.append("'" + Linkman + "',");
		}
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("'" + Address + "',");
		}
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("'" + Tel + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Bj != null) {
			strSql1.append("bj,");
			strSql2.append(Bj + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append(" declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=receiptaddress_id.nextval; ");
		strSql.append("   insert into receiptaddress (");
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
		if (ret < 0)
			return 0;
		else {
			errmess = param.get("id").getParamvalue().toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {

		if (Id == null || Id == 0) {
			errmess = "id参数无效！";
			return 0;
		}
		if (Linkman != null && Linkman.equals("")) {
			errmess = "收货人不允许为空！";
			return 0;
		}
		if (Tel != null && Tel.equals("")) {
			errmess = "联系电话不允许为空！";
			return 0;
		}
		if (Address != null && Address.equals("")) {
			errmess = "收货地址不允许为空！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update receiptaddress set ");
		if (Linkman != null) {
			strSql.append("linkman='" + Linkman + "',");
		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Bj != null) {
			strSql.append("bj=" + Bj + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=" + Id + " and vipid=" + Vipid + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "修改失败！";
			return 0;
		} else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM receiptaddress ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM receiptaddress ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from receiptaddress");
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