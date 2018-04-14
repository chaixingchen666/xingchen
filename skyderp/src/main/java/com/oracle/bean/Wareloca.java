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
//  @author:sunhong  @date:2017-05-11 11:40:36
//*************************************
public class Wareloca implements java.io.Serializable {
	private Long Locaid;
	private String Locano;
	private Long Accid;
	private Long Areaid;
	private Integer Levelno;
	private Long Rent;
	private Long Maxday;
	private Long Noxsday;
	private Long Maxamt;
	private Integer Statetag;
	private Integer Noused;
	private String Limagename;
	private String Remark;
	private Date Enterdate;
	private Integer Entertag;
	private Long Wareid;
	private Long Colorid;
	private Long Accid0;
	private Long Wareid0;
	private Long Colorid0;
	private Long Ljxsamt;
	private Date Lastdate;
	private String Lastop;
	private Long Provid;
	private Long Houseid;
	private Long Xsrate;
	private String Ywnoteno;
	private Long Provid0;
	private String Remark0;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setLocaid(Long locaid) {
		this.Locaid = locaid;
	}

	public Long getLocaid() {
		return Locaid;
	}

	public void setLocano(String locano) {
		this.Locano = locano;
	}

	public String getLocano() {
		return Locano;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setAreaid(Long areaid) {
		this.Areaid = areaid;
	}

	public Long getAreaid() {
		return Areaid;
	}

	public void setLevelno(Integer levelno) {
		this.Levelno = levelno;
	}

	public Integer getLevelno() {
		return Levelno;
	}

	public void setRent(Long rent) {
		this.Rent = rent;
	}

	public Long getRent() {
		return Rent;
	}

	public void setMaxday(Long maxday) {
		this.Maxday = maxday;
	}

	public Long getMaxday() {
		return Maxday;
	}

	public void setNoxsday(Long noxsday) {
		this.Noxsday = noxsday;
	}

	public Long getNoxsday() {
		return Noxsday;
	}

	public void setMaxamt(Long maxamt) {
		this.Maxamt = maxamt;
	}

	public Long getMaxamt() {
		return Maxamt;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setLimagename(String limagename) {
		this.Limagename = limagename;
	}

	public String getLimagename() {
		return Limagename;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setEnterdate(Date enterdate) {
		this.Enterdate = enterdate;
	}

	public String getEnterdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Enterdate);
	}

	public void setEntertag(Integer entertag) {
		this.Entertag = entertag;
	}

	public Integer getEntertag() {
		return Entertag;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setColorid(Long colorid) {
		this.Colorid = colorid;
	}

	public Long getColorid() {
		return Colorid;
	}

	public void setAccid0(Long accid0) {
		this.Accid0 = accid0;
	}

	public Long getAccid0() {
		return Accid0;
	}

	public void setWareid0(Long wareid0) {
		this.Wareid0 = wareid0;
	}

	public Long getWareid0() {
		return Wareid0;
	}

	public void setColorid0(Long colorid0) {
		this.Colorid0 = colorid0;
	}

	public Long getColorid0() {
		return Colorid0;
	}

	public void setLjxsamt(Long ljxsamt) {
		this.Ljxsamt = ljxsamt;
	}

	public Long getLjxsamt() {
		return Ljxsamt;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setProvid(Long provid) {
		this.Provid = provid;
	}

	public Long getProvid() {
		return Provid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setXsrate(Long xsrate) {
		this.Xsrate = xsrate;
	}

	public Long getXsrate() {
		return Xsrate;
	}

	public void setYwnoteno(String ywnoteno) {
		this.Ywnoteno = ywnoteno;
	}

	public String getYwnoteno() {
		return Ywnoteno;
	}

	public void setProvid0(Long provid0) {
		this.Provid0 = provid0;
	}

	public Long getProvid0() {
		return Provid0;
	}

	public void setRemark0(String remark0) {
		this.Remark0 = remark0;
	}

	public String getRemark0() {
		return Remark0;
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
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update wareloca set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Locaid != null) {
			strSql1.append("locaid,");
			strSql2.append(Locaid + ",");
		}
		if (Locano != null) {
			strSql1.append("locano,");
			strSql2.append("'" + Locano + "',");
		}
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Areaid != null) {
			strSql1.append("areaid,");
			strSql2.append(Areaid + ",");
		}
		if (Levelno != null) {
			strSql1.append("levelno,");
			strSql2.append(Levelno + ",");
		}
		if (Rent != null) {
			strSql1.append("rent,");
			strSql2.append(Rent + ",");
		}
		if (Maxday != null) {
			strSql1.append("maxday,");
			strSql2.append(Maxday + ",");
		}
		if (Noxsday != null) {
			strSql1.append("noxsday,");
			strSql2.append(Noxsday + ",");
		}
		if (Maxamt != null) {
			strSql1.append("maxamt,");
			strSql2.append(Maxamt + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (Limagename != null) {
			strSql1.append("limagename,");
			strSql2.append("'" + Limagename + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Enterdate != null) {
			strSql1.append("enterdate,");
			strSql2.append("'" + Enterdate + "',");
		}
		if (Entertag != null) {
			strSql1.append("entertag,");
			strSql2.append(Entertag + ",");
		}
		if (Wareid != null) {
			strSql1.append("wareid,");
			strSql2.append(Wareid + ",");
		}
		if (Colorid != null) {
			strSql1.append("colorid,");
			strSql2.append(Colorid + ",");
		}
		if (Accid0 != null) {
			strSql1.append("accid0,");
			strSql2.append(Accid0 + ",");
		}
		if (Wareid0 != null) {
			strSql1.append("wareid0,");
			strSql2.append(Wareid0 + ",");
		}
		if (Colorid0 != null) {
			strSql1.append("colorid0,");
			strSql2.append(Colorid0 + ",");
		}
		if (Ljxsamt != null) {
			strSql1.append("ljxsamt,");
			strSql2.append(Ljxsamt + ",");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Provid != null) {
			strSql1.append("provid,");
			strSql2.append(Provid + ",");
		}
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		if (Xsrate != null) {
			strSql1.append("xsrate,");
			strSql2.append(Xsrate + ",");
		}
		if (Ywnoteno != null) {
			strSql1.append("ywnoteno,");
			strSql2.append("'" + Ywnoteno + "',");
		}
		if (Provid0 != null) {
			strSql1.append("provid0,");
			strSql2.append(Provid0 + ",");
		}
		if (Remark0 != null) {
			strSql1.append("remark0,");
			strSql2.append("'" + Remark0 + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=wareloca_id.nextval; ");
		strSql.append("   insert into wareloca (");
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
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update wareloca set ");
		if (Locaid != null) {
			strSql.append("locaid=" + Locaid + ",");
		}
		if (Locano != null) {
			strSql.append("locano='" + Locano + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Areaid != null) {
			strSql.append("areaid=" + Areaid + ",");
		}
		if (Levelno != null) {
			strSql.append("levelno=" + Levelno + ",");
		}
		if (Rent != null) {
			strSql.append("rent=" + Rent + ",");
		}
		if (Maxday != null) {
			strSql.append("maxday=" + Maxday + ",");
		}
		if (Noxsday != null) {
			strSql.append("noxsday=" + Noxsday + ",");
		}
		if (Maxamt != null) {
			strSql.append("maxamt=" + Maxamt + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Limagename != null) {
			strSql.append("limagename='" + Limagename + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Enterdate != null) {
			strSql.append("enterdate='" + Enterdate + "',");
		}
		if (Entertag != null) {
			strSql.append("entertag=" + Entertag + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Colorid != null) {
			strSql.append("colorid=" + Colorid + ",");
		}
		if (Accid0 != null) {
			strSql.append("accid0=" + Accid0 + ",");
		}
		if (Wareid0 != null) {
			strSql.append("wareid0=" + Wareid0 + ",");
		}
		if (Colorid0 != null) {
			strSql.append("colorid0=" + Colorid0 + ",");
		}
		if (Ljxsamt != null) {
			strSql.append("ljxsamt=" + Ljxsamt + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Provid != null) {
			strSql.append("provid=" + Provid + ",");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Xsrate != null) {
			strSql.append("xsrate=" + Xsrate + ",");
		}
		if (Ywnoteno != null) {
			strSql.append("ywnoteno='" + Ywnoteno + "',");
		}
		if (Provid0 != null) {
			strSql.append("provid0=" + Provid0 + ",");
		}
		if (Remark0 != null) {
			strSql.append("remark0='" + Remark0 + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
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
		strSql.append(" FROM wareloca ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM wareloca a");
		strSql.append(" join warehouse b on a.houseid=b.houseid");
		strSql.append(" join area c on a.areaid=c.areaid");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from wareloca");

		return false;
	}

	// 写供应商货位授权记录
	public int Write(int value) {
		if (Provid == 0) {
			errmess = "provid不是一个有效值！";
			return 0;
		}
		String qry;
		if (value == 0) {
			qry = "update wareloca set provid=0 where locaid=" + Locaid + " and provid=" + Provid;
		} else {
			qry = "update wareloca set provid=" + Provid + " where locaid=" + Locaid + " and provid=0";
		}
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

}