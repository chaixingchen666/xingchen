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
//  @author:sunhong  @date:2017-02-19 20:01:32
//*************************************
public class Warehouse implements java.io.Serializable {
	private Long Houseid;
	private String Housename;
	private Long Accid;
	private String Address;
	private String Tel;
	private Integer Noused;
	private Integer Statetag;
	private String Lastop;
	private String Ontime;
	private String Offtime;
	private Long Rent;
	private Long Aream2;
	private Long Lx;
	private Long Ly;
	private Date Lastdate;
	private String Remark;
	private String Ontime1;
	private String Offtime1;
	private String Ontime2;
	private String Offtime2;
	private String Imagename;
	private Long Pageview;
	private Long Agreeview;
	private Long Numview;
	private String Shortname;
	private Integer Usedbj;
	private String Locano;
	private Long Onhouseid;
	private String Imagename1;
	private Integer Pricetype;
	private Integer Pricetype1;
	private String errmess;

	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setHousename(String housename) {
		this.Housename = housename;
	}

	public String getHousename() {
		return Housename;
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

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
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

	public void setOntime(String ontime) {
		this.Ontime = ontime;
	}

	public String getOntime() {
		return Ontime;
	}

	public void setOfftime(String offtime) {
		this.Offtime = offtime;
	}

	public String getOfftime() {
		return Offtime;
	}

	public void setRent(Long rent) {
		this.Rent = rent;
	}

	public Long getRent() {
		return Rent;
	}

	public void setAream2(Long aream2) {
		this.Aream2 = aream2;
	}

	public Long getAream2() {
		return Aream2;
	}

	public void setLx(Long lx) {
		this.Lx = lx;
	}

	public Long getLx() {
		return Lx;
	}

	public void setLy(Long ly) {
		this.Ly = ly;
	}

	public Long getLy() {
		return Ly;
	}

	public void setLastdate(Date lastdate) {
		this.Lastdate = lastdate;
	}

	public String getLastdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Lastdate);
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setOntime1(String ontime1) {
		this.Ontime1 = ontime1;
	}

	public String getOntime1() {
		return Ontime1;
	}

	public void setOfftime1(String offtime1) {
		this.Offtime1 = offtime1;
	}

	public String getOfftime1() {
		return Offtime1;
	}

	public void setOntime2(String ontime2) {
		this.Ontime2 = ontime2;
	}

	public String getOntime2() {
		return Ontime2;
	}

	public void setOfftime2(String offtime2) {
		this.Offtime2 = offtime2;
	}

	public String getOfftime2() {
		return Offtime2;
	}

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
	}

	public void setPageview(Long pageview) {
		this.Pageview = pageview;
	}

	public Long getPageview() {
		return Pageview;
	}

	public void setAgreeview(Long agreeview) {
		this.Agreeview = agreeview;
	}

	public Long getAgreeview() {
		return Agreeview;
	}

	public void setNumview(Long numview) {
		this.Numview = numview;
	}

	public Long getNumview() {
		return Numview;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setUsedbj(Integer usedbj) {
		this.Usedbj = usedbj;
	}

	public Integer getUsedbj() {
		return Usedbj;
	}

	public void setLocano(String locano) {
		this.Locano = locano;
	}

	public String getLocano() {
		return Locano;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setImagename1(String imagename1) {
		this.Imagename1 = imagename1;
	}

	public String getImagename1() {
		return Imagename1;
	}

	public void setPricetype(Integer pricetype) {
		this.Pricetype = pricetype;
	}

	public Integer getPricetype() {
		return Pricetype;
	}

	public void setPricetype1(Integer pricetype1) {
		this.Pricetype1 = pricetype1;
	}

	public Integer getPricetype1() {
		return Pricetype1;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();

		strSql.append("select count(*) from (");
		strSql.append(" select id from wareinh  where accid=" + Accid + " and houseid=" + Houseid + " and rownum=1");
		strSql.append("  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append(" union all");
		strSql.append(" select id from wareouth where  accid=" + Accid + " and houseid=" + Houseid + " and rownum=1");
		strSql.append("  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append(" union all");
		strSql.append(" select b.id from shopsalem a join shopsaleh b on a.accid=b.accid and a.noteno=b.noteno");
		strSql.append("  where  a.accid=" + Accid + " and a.houseid=" + Houseid + " and rownum=1");
		strSql.append("  and b.notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");

		strSql.append(" union all");
		strSql.append(" select id from allotinh where  accid=" + Accid + " and (houseid=" + Houseid + " or fromhouseid=" + Houseid + ") and rownum=1");
		strSql.append("  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append(" union all");
		strSql.append(" select id from allotouth where  accid=" + Accid + " and (houseid=" + Houseid + "  or tohouseid=" + Houseid + ") and rownum=1");
		strSql.append("  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append(" union all");
		strSql.append(" select id from warecheckh where  accid=" + Accid + " and houseid=" + Houseid + " and rownum=1");
		strSql.append("  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");

		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前店铺已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update warehouse set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where houseid=" + Houseid + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
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
		strSql1.append("houseid,");
		strSql2.append("v_id,");
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		if (Housename != null) {
			strSql1.append("housename,");
			strSql2.append("" + Housename + ",");
		}
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("" + Address + ",");
		}
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("" + Tel + ",");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("" + Lastop + ",");
		}
		if (Ontime != null) {
			strSql1.append("ontime,");
			strSql2.append("" + Ontime + ",");
		}
		if (Offtime != null) {
			strSql1.append("offtime,");
			strSql2.append("" + Offtime + ",");
		}
		if (Rent != null) {
			strSql1.append("rent,");
			strSql2.append(Rent + ",");
		}
		if (Aream2 != null) {
			strSql1.append("aream2,");
			strSql2.append(Aream2 + ",");
		}
		if (Lx != null) {
			strSql1.append("lx,");
			strSql2.append(Lx + ",");
		}
		if (Ly != null) {
			strSql1.append("ly,");
			strSql2.append(Ly + ",");
		}
		// if (Lastdate != null) {
		// strSql1.append("lastdate,");
		// strSql2.append("" + Lastdate + ",");
		// }
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("" + Remark + ",");
		}
		if (Ontime1 != null) {
			strSql1.append("ontime1,");
			strSql2.append("" + Ontime1 + ",");
		}
		if (Offtime1 != null) {
			strSql1.append("offtime1,");
			strSql2.append("" + Offtime1 + ",");
		}
		if (Ontime2 != null) {
			strSql1.append("ontime2,");
			strSql2.append("" + Ontime2 + ",");
		}
		if (Offtime2 != null) {
			strSql1.append("offtime2,");
			strSql2.append("" + Offtime2 + ",");
		}
		if (Imagename != null) {
			strSql1.append("imagename,");
			strSql2.append("" + Imagename + ",");
		}
		if (Pageview != null) {
			strSql1.append("pageview,");
			strSql2.append(Pageview + ",");
		}
		if (Agreeview != null) {
			strSql1.append("agreeview,");
			strSql2.append(Agreeview + ",");
		}
		if (Numview != null) {
			strSql1.append("numview,");
			strSql2.append(Numview + ",");
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("" + Shortname + ",");
		}
		if (Usedbj != null) {
			strSql1.append("usedbj,");
			strSql2.append(Usedbj + ",");
		}
		if (Locano != null) {
			strSql1.append("locano,");
			strSql2.append("" + Locano + ",");
		}
		if (Onhouseid != null) {
			strSql1.append("onhouseid,");
			strSql2.append(Onhouseid + ",");
		}
		if (Imagename1 != null) {
			strSql1.append("imagename1,");
			strSql2.append("" + Imagename1 + ",");
		}
		if (Pricetype != null) {
			strSql1.append("pricetype,");
			strSql2.append(Pricetype + ",");
		}
		if (Pricetype1 != null) {
			strSql1.append("pricetype1,");
			strSql2.append(Pricetype1 + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("  v_id:=warehouse_houseid.nextval; ");
		strSql.append("  insert into warehouse (");
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
		if (ret >= 0) {
			Houseid = Long.parseLong(param.get("id").getParamvalue().toString());
			return 1;
		} else
			return 0;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update warehouse set ");
		// if (Houseid != null) {
		// strSql.append("houseid=" + Houseid + ",");
		// }
		if (Housename != null) {
			strSql.append("housename='" + Housename + "',");
			Shortname = PinYin.getPinYinHeadChar(Housename);
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Ontime != null) {
			strSql.append("ontime='" + Ontime + "',");
		}
		if (Offtime != null) {
			strSql.append("offtime='" + Offtime + "',");
		}
		if (Rent != null) {
			strSql.append("rent=" + Rent + ",");
		}
		if (Aream2 != null) {
			strSql.append("aream2=" + Aream2 + ",");
		}
		if (Lx != null) {
			strSql.append("lx=" + Lx + ",");
		}
		if (Ly != null) {
			strSql.append("ly=" + Ly + ",");
		}
		if (Lastdate != null) {
			strSql.append("lastdate='" + Lastdate + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Ontime1 != null) {
			strSql.append("ontime1='" + Ontime1 + "',");
		}
		if (Offtime1 != null) {
			strSql.append("offtime1='" + Offtime1 + "',");
		}
		if (Ontime2 != null) {
			strSql.append("ontime2='" + Ontime2 + "',");
		}
		if (Offtime2 != null) {
			strSql.append("offtime2='" + Offtime2 + "',");
		}
		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
		}
		if (Pageview != null) {
			strSql.append("pageview=" + Pageview + ",");
		}
		if (Agreeview != null) {
			strSql.append("agreeview=" + Agreeview + ",");
		}
		if (Numview != null) {
			strSql.append("numview=" + Numview + ",");
		}
		if (Usedbj != null) {
			strSql.append("usedbj=" + Usedbj + ",");
		}
		if (Locano != null) {
			strSql.append("locano='" + Locano + "',");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Imagename1 != null) {
			strSql.append("imagename1='" + Imagename1 + "',");
		}
		if (Pricetype != null) {
			strSql.append("pricetype=" + Pricetype + ",");
		}
		if (Pricetype1 != null) {
			strSql.append("pricetype1=" + Pricetype1 + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where houseid=" + Houseid + " and accid=" + Accid + ";");
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
		strSql.append(" FROM warehouse ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM warehouse ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from warehouse");
		strSql.append(" where housename='" + Housename + "' and statetag=1 and rownum=1 and accid=" + Accid);
		if (Houseid != null)
			strSql.append(" and houseid<>" + Houseid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "店铺:" + Housename + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}
}