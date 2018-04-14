package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-04-30 21:20:44
//*************************************
public class Incomelink implements java.io.Serializable {
	private Float Id;
	private Long Accid;

	private Long Houseid;
	private Long Custid;
	private Integer Notetype;
	private String Noteno;
	private String Pnoteno;
	private Integer Fs;
	private Float Curr;
	private String Notedate;
	private String Accbegindate;
	private Date Lastdate;
	private String Lastop;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setNotetype(Integer notetype) {
		this.Notetype = notetype;
	}

	public Integer getNotetype() {
		return Notetype;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public void setNotedate(String notedate) {
		this.Notedate = notedate;
	}

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setPnoteno(String pnoteno) {
		this.Pnoteno = pnoteno;
	}

	public String getPnoteno() {
		return Pnoteno;
	}

	public void setFs(Integer fs) {
		this.Fs = fs;
	}

	public Integer getFs() {
		return Fs;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
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

	public void setCustid(Long custid) {
		Custid = custid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();

		return 1;
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Notetype != null) {
			strSql1.append("notetype,");
			strSql2.append(Notetype + ",");
		}
		if (Noteno != null) {
			strSql1.append("noteno,");
			strSql2.append("'" + Noteno + "',");
		}
		if (Pnoteno != null) {
			strSql1.append("pnoteno,");
			strSql2.append("'" + Pnoteno + "',");
		}
		if (Fs != null) {
			strSql1.append("fs,");
			strSql2.append(Fs + ",");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=incomelink_id.nextval; ");
		strSql.append("   insert into incomelink (");
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
		// strSql.append("begin ");
		// strSql.append(" update incomelink set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		// if (Notetype != null) {
		// strSql.append("notetype=" + Notetype + ",");
		// }
		// if (Noteno != null) {
		// strSql.append("noteno='" + Noteno + "',");
		// }
		// if (Pnoteno != null) {
		// strSql.append("pnoteno='" + Pnoteno + "',");
		// }
		// if (Fs != null) {
		// strSql.append("fs=" + Fs + ",");
		// }
		// if (Curr != null) {
		// strSql.append("curr='" + Curr + "',");
		// }
		// if (Lastop != null) {
		// strSql.append("lastop='" + Lastop + "',");
		// }
		// strSql.append("lastdate=sysdate");
		// strSql.append(" where id=id ;");
		// strSql.append(" commit;");
		// strSql.append(" end;");
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
		strSql.append(" FROM incomelink ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取收款勾兑分页数据
	public Table GetTable(QueryParam qp) {
		// fs:0销售收款勾销售单1销售收款勾退货单2销售退款勾退货单
		// 销售应收
		String qry = "select notetype,accid,noteno,notedate,remark,fs,operant ";
		qry += ",sum(totalcurr) as totalcurr,sum(gdcurr) as gdcurr,sum(balcurr) as balcurr,sum(gdcurr0) as gdcurr0 from (";
		qry += "\n select a.accid,a.noteno,a.notedate,a.remark,a.operant,a.totalcurr,a.gdcurr,a.totalcurr-a.gdcurr as balcurr,0 as gdcurr0,0 as fs,0 as notetype";
		qry += "\n from wareouth a where a.statetag=1 and a.ntid=1 and a.accid=" + Accid + " and a.custid=" + Custid;
		if (Houseid > 0)
			qry += " and a.houseid=" + Houseid;
		// qry += "\n and a.notedate<=to_date('" + Notedate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n and a.notedate>=to_date('" + Accbegindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		// 销售应退
		qry += "\n union all";
		qry += "\n select a.accid,a.noteno,a.notedate,a.remark,a.operant,-a.totalcurr,-a.gdcurr,-a.totalcurr+a.gdcurr as balcurr,0 as gdcurr0,1 as fs,0 as notetype";
		qry += "\n from wareouth a where a.statetag=1 and a.ntid=2 and a.accid=" + Accid + " and a.custid=" + Custid;
		if (Houseid > 0)
			qry += " and a.houseid=" + Houseid;
		// qry += "\n and a.notedate<=to_date('" + Notedate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n and a.notedate>=to_date('" + Accbegindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n union all";
		// 本单勾对销售应收
		qry += "\n select a.accid,a.noteno,a.notedate,a.remark,a.operant,0 as totalcurr,0 as gdcurr,0 as balcurr,sum(b.curr) as gdcurr0,0 as fs,0 as notetype";
		qry += "\n from wareouth a join incomelink b on a.accid=b.accid and a.noteno=b.noteno and b.notetype=0";
		// notetype:0销售(退库) 1物料销售（退库）
		qry += "\n where a.statetag=1 and a.accid=" + Accid + " and a.custid=" + Custid;
		if (Houseid > 0)
			qry += " and a.houseid=" + Houseid;
		// qry += "\n and a.accid=b.accid and a.noteno=b.noteno and b.notetype=0";
		qry += "\n and b.pnoteno='" + Pnoteno + "' and a.ntid=1";
		qry += "\n group by a.accid,a.noteno,a.notedate,a.remark,a.operant";

		qry += "\n union all";
		// 本单勾对销售应退
		qry += "\n select a.accid,a.noteno,a.notedate,a.remark,a.operant,0 as totalcurr,0 as gdcurr,0 as balcurr,-sum(b.curr) as gdcurr0,1 as fs,0 as notetype";
		qry += "\n from wareouth a join incomelink b on a.accid=b.accid and a.noteno=b.noteno and b.notetype=0";
		// notetype:0销售(退库) 1物料销售（退库）
		qry += "\n where a.statetag=1 and a.accid=" + Accid + " and a.custid=" + Custid;
		if (Houseid > 0)
			qry += " and a.houseid=" + Houseid;
		// qry += "\n and a.accid=b.accid and a.noteno=b.noteno and b.notetype=0";
		qry += "\n and b.pnoteno='" + Pnoteno + "' and a.ntid=2";
		qry += "\n group by a.accid,a.noteno,a.notedate,a.remark,a.operant)";
		qry += "\n group by notetype,accid,noteno,notedate,remark,fs,operant";
		qry += "\n having sum(balcurr)<>0 or sum(gdcurr0)<>0";

		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(totalcurr),0) as totalcurr,nvl(sum(gdcurr),0) as gdcurr,nvl(sum(balcurr),0) as balcurr,nvl(sum(gdcurr0),0) as gdcurr0");
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from incomelink");

		return false;
	}

	// 销售收款勾销售单
	public int doCheck(int opid) {// opid:1=勾单，0=取消勾单
		if (Pnoteno.length() <= 0 || Noteno.length() <= 0) {
			errmess = "pnoteno或noteno不是一个有效值！";
			return 0;
		}
		// incomelink-> fs:0销售收款勾销售单1销售收款勾退货单2销售退款勾退货单
		if (opid == 1 && Fs != 0 && Fs != 1) {
			errmess = "fs不是一个有效值！";
			return 0;
		}

		String qry = "declare";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_curr number(16,2);";
		qry += "\n   v_balcurr number(16,2);";

		qry += "\n begin";
		qry += "\n   v_curr:=" + Curr + ";";
		if (opid == 0) {// 取消勾单
			// qry += "\n select paycurr=v_curr from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';";
			if (Fs == 1) // 1销售收款勾退货单
			{
				qry += "\n   v_curr:=-v_curr;";
				qry += "\n   update wareouth set gdcurr=gdcurr-v_curr where accid=" + Accid + " and noteno='" + Noteno + "';";
				qry += "\n   update incomecurr set gdcurr=gdcurr-v_curr where accid=" + Accid + " and noteno='" + Pnoteno + "';";
				qry += "\n   delete incomelink where accid=" + Accid + " and noteno='" + Noteno + "' and pnoteno='" + Pnoteno + "' and notetype=" + Notetype + ";";
				qry += "\n   v_curr:=-v_curr;";
			} else {
				qry += "\n   update wareouth set gdcurr=gdcurr-v_curr where accid=" + Accid + " and noteno='" + Noteno + "';";
				qry += "\n   update incomecurr set gdcurr=gdcurr-v_curr where accid=" + Accid + " and noteno='" + Pnoteno + "';";
				qry += "\n   delete incomelink where accid=" + Accid + " and noteno='" + Noteno + "' and pnoteno='" + Pnoteno + "' and notetype=" + Notetype + ";";
			}
		} else {// 勾单
			if (Fs == 1) // 1销售收款勾退货单
			{
				// 查询对应的销售单的勾单余额
				qry += "\n   select totalcurr-gdcurr into v_balcurr from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';";
				// qry += "\n if v_balcurr<v_curr then ";
				qry += "\n      v_curr:=v_balcurr;";
				// qry += "\n end if;";
				qry += "\n   update wareouth set gdcurr=gdcurr+v_curr where accid=" + Accid + " and noteno='" + Noteno + "';";
				qry += "\n   update incomecurr set gdcurr=gdcurr-v_curr where accid=" + Accid + " and noteno='" + Pnoteno + "';";

				qry += "\n   delete incomelink where accid=" + Accid + " and noteno='" + Noteno + "' and pnoteno='" + Pnoteno + "' and notetype=" + Notetype + ";";
				// incomelink-> notetype:0销售(退库) 1物料销售（
				// incomelink-> fs:0销售收款勾销售单1销售收款勾退货单2销售退款勾退货单
				qry += "\n   insert into incomelink (id,accid,noteno,notetype,pnoteno,fs,curr,lastop)";
				qry += "\n   values (incomelink_id.nextval," + Accid + ",'" + Noteno + "'," + Notetype + ",'" + Pnoteno + "'," + Fs + ",v_curr,'" + Lastop + "');";
				qry += "\n   v_curr:=-v_balcurr;";

			} else {

				// 查询对应的销售单的勾单余额
				qry += "\n   select totalcurr-gdcurr into v_balcurr from wareouth where accid=" + Accid + " and noteno='" + Noteno + "';";
				qry += "\n   if v_balcurr<v_curr then ";
				qry += "\n      v_curr:=v_balcurr;";
				qry += "\n   end if;";
				qry += "\n   update wareouth set gdcurr=gdcurr+v_curr where accid=" + Accid + " and noteno='" + Noteno + "';";
				qry += "\n   update incomecurr set gdcurr=gdcurr+v_curr where accid=" + Accid + " and noteno='" + Pnoteno + "';";
				qry += "\n   delete incomelink where accid=" + Accid + " and noteno='" + Noteno + "' and pnoteno='" + Pnoteno + "' and notetype=" + Notetype + ";";
				// incomelink-> notetype:0销售(退库) 1物料销售（
				// incomelink-> fs:0销售收款勾销售单1销售收款勾退货单2销售退款勾退货单
				qry += "\n   insert into incomelink (id,accid,noteno,notetype,pnoteno,fs,curr,lastop)";
				qry += "\n   values (incomelink_id.nextval," + Accid + ",'" + Noteno + "'," + Notetype + ",'" + Pnoteno + "'," + Fs + ",v_curr,'" + Lastop + "');";
			}

		}
		// 取本销售单勾单金额
		qry += "\n  select v_curr into :gdcurr from dual;";
		qry += "\n end;";
//		LogUtils.LogDebugWrite("incomecheck", qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("gdcurr", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		Curr = Float.parseFloat(param.get("gdcurr").getParamvalue().toString());
		errmess = Curr.toString();
		return 1;

	}

}