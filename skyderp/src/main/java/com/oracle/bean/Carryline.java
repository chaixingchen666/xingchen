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
//  @author:sunhong  @date:2017-03-21 19:39:26
//*************************************
public class Carryline implements java.io.Serializable {
	private Long Carryid;
	private String Carryname;
	private String Shortname;
	private Long Accid;
	private String Address;
	private String Postcode;
	private String Tel;
	private String Linkman;
	private String Mobile;
	private String Bankname;
	private String Taxno;
	private String Accountno;
	private String Remark;
	private String Urladd;
	private Integer Noused;
	private Integer Statetag;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setCarryid(Long carryid) {
		this.Carryid = carryid;
	}

	public Long getCarryid() {
		return Carryid;
	}

	public void setCarryname(String carryname) {
		this.Carryname = carryname;
	}

	public String getCarryname() {
		return Carryname;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setPostcode(String postcode) {
		this.Postcode = postcode;
	}

	public String getPostcode() {
		return Postcode;
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setLinkman(String linkman) {
		this.Linkman = linkman;
	}

	public String getLinkman() {
		return Linkman;
	}

	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setBankname(String bankname) {
		this.Bankname = bankname;
	}

	public String getBankname() {
		return Bankname;
	}

	public void setTaxno(String taxno) {
		this.Taxno = taxno;
	}

	public String getTaxno() {
		return Taxno;
	}

	public void setAccountno(String accountno) {
		this.Accountno = accountno;
	}

	public String getAccountno() {
		return Accountno;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setUrladd(String urladd) {
		this.Urladd = urladd;
	}

	public String getUrladd() {
		return Urladd;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
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
		if (Carryid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num from (");
		strSql.append(" select  id from viporderh where carryid=" + Carryid + " and rownum=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前快递公司已使用，不允许删除!";
			return 0;
		}
		strSql.setLength(0);
		strSql.append("declare ");
		strSql.append("   v_carryname varchar2(200);");
		strSql.append("begin ");
		strSql.append("   select carryname into v_carryname from carryline where  carryid=" + Carryid + " and accid=" + Accid + " and statetag=1;");
		strSql.append("   update carryline set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("   where carryid=" + Carryid + " and accid=" + Accid + ";");
		strSql.append("   insert into LOGRECORD (id,accid,progname,remark,lastop)");
		strSql.append("   values (LOGRECORD_id.nextval," + Accid + ",'物流单位','【删除记录】公司id:" + Carryid + "; 公司名称:'||v_carryname,'" + Lastop + "');");
		strSql.append("   commit;");

		strSql.append("  EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("   v_carryname:=''; ");
		strSql.append("end; ");
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
		if (Carryname == null || Carryname.equals("")) {
			errmess = "物流公司名称不能为空！";
			return 0;
		}
		if (Exists())
			return 0;
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("carryid,");
		strSql2.append("v_carryid,");
		// if (Carryid != null) {
		// strSql1.append("carryid,");
		// strSql2.append(Carryid + ",");
		// }
		if (Carryname != null) {
			strSql1.append("carryname,");
			strSql2.append("'" + Carryname + "',");
			Shortname = PinYin.getPinYinHeadChar(Carryname);
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("'" + Address + "',");
		}
		if (Postcode != null) {
			strSql1.append("postcode,");
			strSql2.append("'" + Postcode + "',");
		}
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("'" + Tel + "',");
		}
		if (Linkman != null) {
			strSql1.append("linkman,");
			strSql2.append("'" + Linkman + "',");
		}
		if (Mobile != null) {
			strSql1.append("mobile,");
			strSql2.append("'" + Mobile + "',");
		}
		if (Bankname != null) {
			strSql1.append("bankname,");
			strSql2.append("'" + Bankname + "',");
		}
		if (Taxno != null) {
			strSql1.append("taxno,");
			strSql2.append("'" + Taxno + "',");
		}
		if (Accountno != null) {
			strSql1.append("accountno,");
			strSql2.append("'" + Accountno + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Urladd != null) {
			strSql1.append("urladd,");
			strSql2.append("'" + Urladd + "',");
		}
		// if (Noused != null) {
		strSql1.append("noused,");
		strSql2.append(Noused + ",");
		// }
		// if (Statetag != null) {
		strSql1.append("statetag,");
		strSql2.append(Statetag + ",");
		// }
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_carryid number; ");
		strSql.append(" begin ");
		strSql.append("   v_carryid:=carryline_carryid.nextval; ");
		strSql.append("   insert into carryline (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_carryid into :carryid from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("carryid", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			// id = Long.parseLong(param.get("id").getParamvalue().toString());
			Carryid = Long.parseLong(param.get("carryid").getParamvalue().toString());
			errmess = Carryid.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		if (Carryid == null || Carryid == 0) {
			errmess = "单位id参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update carryline set ");
		// if (Carryid != null) {
		// strSql.append("carryid=" + Carryid + ",");
		// }
		if (Carryname != null) {
			strSql.append("carryname='" + Carryname + "',");
			Shortname = PinYin.getPinYinHeadChar(Carryname);
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
//		if (Accid != null) {
//			strSql.append("accid=" + Accid + ",");
//		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Postcode != null) {
			strSql.append("postcode='" + Postcode + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Linkman != null) {
			strSql.append("linkman='" + Linkman + "',");
		}
		if (Mobile != null) {
			strSql.append("mobile='" + Mobile + "',");
		}
		if (Bankname != null) {
			strSql.append("bankname='" + Bankname + "',");
		}
		if (Taxno != null) {
			strSql.append("taxno='" + Taxno + "',");
		}
		if (Accountno != null) {
			strSql.append("accountno='" + Accountno + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Urladd != null) {
			strSql.append("urladd='" + Urladd + "',");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
//		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
//		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where Carryid=" + Carryid + "  and accid="+Accid+";");
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
		strSql.append(" FROM carryline ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM carryline ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select carryid  from carryline");
		strSql.append(" where carryname='" + Carryname + "' and statetag=1 and accid=" + Accid + " and rownum=1");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = Carryname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}
}