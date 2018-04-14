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
//  @author:sunhong  @date:2017-05-11 23:31:47
//*************************************
public class Paycheck implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Date Notedate;
	private Long Accid;
	private Long Accid1;
	private Long Provid1;
	private String Checkman;
	private Integer Statetag;
	private String Lastop;
	private String Operant;
	private Float Curr0;
	private Float Curr;
	private String Bankgroup;
	private String Bankname;
	private String Bankman;
	private String Bankno;
	private String Remark;
	private Date Lastdate;
	private String Refuse;
	private String Cwcheck;
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

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
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

	public void setOperant(String operant) {
		this.Operant = operant;
	}

	public String getOperant() {
		return Operant;
	}

	public void setCurr0(Float curr0) {
		this.Curr0 = curr0;
	}

	public Float getCurr0() {
		return Curr0;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
	}

	public void setBankgroup(String bankgroup) {
		this.Bankgroup = bankgroup;
	}

	public String getBankgroup() {
		return Bankgroup;
	}

	public void setBankname(String bankname) {
		this.Bankname = bankname;
	}

	public String getBankname() {
		return Bankname;
	}

	public void setBankman(String bankman) {
		this.Bankman = bankman;
	}

	public String getBankman() {
		return Bankman;
	}

	public void setBankno(String bankno) {
		this.Bankno = bankno;
	}

	public String getBankno() {
		return Bankno;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRefuse(String refuse) {
		this.Refuse = refuse;
	}

	public String getRefuse() {
		return Refuse;
	}

	public void setCwcheck(String cwcheck) {
		this.Cwcheck = cwcheck;
	}

	public String getCwcheck() {
		return Cwcheck;
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
		// strSql.append(" update paycheck set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
//		if (Id != null) {
//			strSql1.append("id,");
//			strSql2.append("'" + Id + "',");
//		}
		if (Noteno != null) {
			strSql1.append("noteno,");
			strSql2.append("'" + Noteno + "',");
		}
		if (Notedate != null) {
			strSql1.append("notedate,");
			strSql2.append("'" + Notedate + "',");
		}
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Accid1 != null) {
			strSql1.append("accid1,");
			strSql2.append(Accid1 + ",");
		}
		if (Provid1 != null) {
			strSql1.append("provid1,");
			strSql2.append(Provid1 + ",");
		}
		if (Checkman != null) {
			strSql1.append("checkman,");
			strSql2.append("'" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Operant != null) {
			strSql1.append("operant,");
			strSql2.append("'" + Operant + "',");
		}
		if (Curr0 != null) {
			strSql1.append("curr0,");
			strSql2.append("'" + Curr0 + "',");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
		}
		if (Bankgroup != null) {
			strSql1.append("bankgroup,");
			strSql2.append("'" + Bankgroup + "',");
		}
		if (Bankname != null) {
			strSql1.append("bankname,");
			strSql2.append("'" + Bankname + "',");
		}
		if (Bankman != null) {
			strSql1.append("bankman,");
			strSql2.append("'" + Bankman + "',");
		}
		if (Bankno != null) {
			strSql1.append("bankno,");
			strSql2.append("'" + Bankno + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Refuse != null) {
			strSql1.append("refuse,");
			strSql2.append("'" + Refuse + "',");
		}
		if (Cwcheck != null) {
			strSql1.append("cwcheck,");
			strSql2.append("'" + Cwcheck + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=paycheck_id.nextval; ");
		strSql.append("   insert into paycheck (");
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
		strSql.append("   update paycheck set ");
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
		if (Accid1 != null) {
			strSql.append("accid1=" + Accid1 + ",");
		}
		if (Provid1 != null) {
			strSql.append("provid1=" + Provid1 + ",");
		}
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Operant != null) {
			strSql.append("operant='" + Operant + "',");
		}
		if (Curr0 != null) {
			strSql.append("curr0='" + Curr0 + "',");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		if (Bankgroup != null) {
			strSql.append("bankgroup='" + Bankgroup + "',");
		}
		if (Bankname != null) {
			strSql.append("bankname='" + Bankname + "',");
		}
		if (Bankman != null) {
			strSql.append("bankman='" + Bankman + "',");
		}
		if (Bankno != null) {
			strSql.append("bankno='" + Bankno + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Refuse != null) {
			strSql.append("refuse='" + Refuse + "',");
		}
		if (Cwcheck != null) {
			strSql.append("cwcheck='" + Cwcheck + "',");
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
		strSql.append(" FROM paycheck ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM paycheck a");
		strSql.append("\n left outer join provide b on a.provid1=b.provid");
		if (strWhere.length() > 0) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from paycheck");
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