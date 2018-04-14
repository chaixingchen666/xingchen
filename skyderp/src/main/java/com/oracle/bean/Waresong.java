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
//  @author:sunhong  @date:2017-05-12 15:36:47
//*************************************
public class Waresong implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Date Notedate;
	private Long Accid;
	private Long Wareid;
	private Long Colorid;
	private Long Locaid;
	private Float Amount0;
	private Float Price0;
	private Float Curr0;
	private Float Amount;
	private Float Price;
	private Float Curr;
	private String Remark;
	private Long Accid1;
	private Long Provid1;
	private String Ywnoteno;
	private String Checkman;
	private String Lastop;
	private Date Lastdate;
	private Integer Statetag;
	private Integer Ntid;
	private String Operant;
	private Float Price1;
	private Float Price2;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
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

	public void setLocaid(Long locaid) {
		this.Locaid = locaid;
	}

	public Long getLocaid() {
		return Locaid;
	}

	public void setAmount0(Float amount0) {
		this.Amount0 = amount0;
	}

	public Float getAmount0() {
		return Amount0;
	}

	public void setPrice0(Float price0) {
		this.Price0 = price0;
	}

	public Float getPrice0() {
		return Price0;
	}

	public void setCurr0(Float curr0) {
		this.Curr0 = curr0;
	}

	public Float getCurr0() {
		return Curr0;
	}

	public void setAmount(Float amount) {
		this.Amount = amount;
	}

	public Float getAmount() {
		return Amount;
	}

	public void setPrice(Float price) {
		this.Price = price;
	}

	public Float getPrice() {
		return Price;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setAccid1(Long accid1) {
		this.Accid1 = accid1;
	}

	public Long getAccid1() {
		return Accid1;
	}

	public void setProvid1(Long provid1) {
		this.Provid1 = provid1;
	}

	public Long getProvid1() {
		return Provid1;
	}

	public void setYwnoteno(String ywnoteno) {
		this.Ywnoteno = ywnoteno;
	}

	public String getYwnoteno() {
		return Ywnoteno;
	}

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
	}

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

	public void setNtid(Integer ntid) {
		this.Ntid = ntid;
	}

	public Integer getNtid() {
		return Ntid;
	}

	public void setOperant(String operant) {
		this.Operant = operant;
	}

	public String getOperant() {
		return Operant;
	}

	public void setPrice1(Float price1) {
		this.Price1 = price1;
	}

	public Float getPrice1() {
		return Price1;
	}

	public void setPrice2(Float price2) {
		this.Price2 = price2;
	}

	public Float getPrice2() {
		return Price2;
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
		// strSql.append(" update waresong set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
		// if (Id != null) {
		// strSql1.append("id,");
		// strSql2.append("'" + Id + "',");
		// }
		if (Noteno != null) {
			strSql1.append("noteno,");
			strSql2.append("'" + Noteno + "',");
		}
		if (Notedate != null) {
			strSql1.append("notedate,");
			strSql2.append("'" + Notedate + "',");
		}
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Wareid != null) {
			strSql1.append("wareid,");
			strSql2.append(Wareid + ",");
		}
		if (Colorid != null) {
			strSql1.append("colorid,");
			strSql2.append(Colorid + ",");
		}
		if (Locaid != null) {
			strSql1.append("locaid,");
			strSql2.append(Locaid + ",");
		}
		if (Amount0 != null) {
			strSql1.append("amount0,");
			strSql2.append("'" + Amount0 + "',");
		}
		if (Price0 != null) {
			strSql1.append("price0,");
			strSql2.append("'" + Price0 + "',");
		}
		if (Curr0 != null) {
			strSql1.append("curr0,");
			strSql2.append("'" + Curr0 + "',");
		}
		if (Amount != null) {
			strSql1.append("amount,");
			strSql2.append("'" + Amount + "',");
		}
		if (Price != null) {
			strSql1.append("price,");
			strSql2.append("'" + Price + "',");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Accid1 != null) {
			strSql1.append("accid1,");
			strSql2.append(Accid1 + ",");
		}
		if (Provid1 != null) {
			strSql1.append("provid1,");
			strSql2.append(Provid1 + ",");
		}
		if (Ywnoteno != null) {
			strSql1.append("ywnoteno,");
			strSql2.append("'" + Ywnoteno + "',");
		}
		if (Checkman != null) {
			strSql1.append("checkman,");
			strSql2.append("'" + Checkman + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Ntid != null) {
			strSql1.append("ntid,");
			strSql2.append(Ntid + ",");
		}
		if (Operant != null) {
			strSql1.append("operant,");
			strSql2.append("'" + Operant + "',");
		}
		if (Price1 != null) {
			strSql1.append("price1,");
			strSql2.append("'" + Price1 + "',");
		}
		if (Price2 != null) {
			strSql1.append("price2,");
			strSql2.append("'" + Price2 + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=waresong_id.nextval; ");
		strSql.append("   insert into waresong (");
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
		strSql.append("   update waresong set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Noteno != null) {
			strSql.append("noteno='" + Noteno + "',");
		}
		if (Notedate != null) {
			strSql.append("notedate='" + Notedate + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Colorid != null) {
			strSql.append("colorid=" + Colorid + ",");
		}
		if (Locaid != null) {
			strSql.append("locaid=" + Locaid + ",");
		}
		if (Amount0 != null) {
			strSql.append("amount0='" + Amount0 + "',");
		}
		if (Price0 != null) {
			strSql.append("price0='" + Price0 + "',");
		}
		if (Curr0 != null) {
			strSql.append("curr0='" + Curr0 + "',");
		}
		if (Amount != null) {
			strSql.append("amount='" + Amount + "',");
		}
		if (Price != null) {
			strSql.append("price='" + Price + "',");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Accid1 != null) {
			strSql.append("accid1=" + Accid1 + ",");
		}
		if (Provid1 != null) {
			strSql.append("provid1=" + Provid1 + ",");
		}
		if (Ywnoteno != null) {
			strSql.append("ywnoteno='" + Ywnoteno + "',");
		}
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Ntid != null) {
			strSql.append("ntid=" + Ntid + ",");
		}
		if (Operant != null) {
			strSql.append("operant='" + Operant + "',");
		}
		if (Price1 != null) {
			strSql.append("price1='" + Price1 + "',");
		}
		if (Price2 != null) {
			strSql.append("price2='" + Price2 + "',");
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
		strSql.append(" FROM waresong ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM waresong a");
		strSql.append(" join wareloca b on a.locaid=b.locaid");
		strSql.append(" left outer join area f on b.areaid=f.areaid");
		strSql.append(" left outer join warecode c on a.wareid=c.wareid");
		strSql.append(" left outer join colorcode d on a.colorid=d.colorid");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from waresong");
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