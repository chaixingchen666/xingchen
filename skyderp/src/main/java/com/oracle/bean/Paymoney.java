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
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-21 21:14:30
//*************************************
public class Paymoney implements java.io.Serializable {
	private Float Id;
	private Date Notedate;
	private Long Accid;
	private Float Fs;
	private Float Curr;
	private Float Paycurr;
	private String Orderno;
	private String Remark;
	private Integer Zt;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
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

	public void setFs(Float fs) {
		this.Fs = fs;
	}

	public Float getFs() {
		return Fs;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
	}

	public void setPaycurr(Float paycurr) {
		this.Paycurr = paycurr;
	}

	public Float getPaycurr() {
		return Paycurr;
	}

	public void setOrderno(String orderno) {
		this.Orderno = orderno;
	}

	public String getOrderno() {
		return Orderno;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setZt(Integer zt) {
		this.Zt = zt;
	}

	public Integer getZt() {
		return Zt;
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
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update Paymoney set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
		if (Paycurr == null || Curr == null || Fs == null || Zt == null) {
			errmess = "充值参数无效！";
			return 0;
		}
		if (!( // 新充值
		Paycurr == 1880 && Curr == 2000 || Paycurr == 2880 && Curr == 3200 || Paycurr == 3880 && Curr == 4500)) {
			errmess = "该充值活动已结束！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Notedate != null) {
		strSql1.append("notedate,");
		strSql2.append("sysdate,");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Fs != null) {
		strSql1.append("fs,");
		strSql2.append("'" + Fs + "',");
		// }
		// if (Curr != null) {
		strSql1.append("curr,");
		strSql2.append("'" + Curr + "',");
		// }
		// if (Paycurr != null) {
		strSql1.append("paycurr,");
		strSql2.append("'" + Paycurr + "',");
		// }
		if (Orderno != null) {
			strSql1.append("orderno,");
			strSql2.append("'" + Orderno + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		// if (Zt != null) {
		strSql1.append("zt,");
		strSql2.append(Zt + ",");
		// }
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Paymoney_id.nextval; ");
		strSql.append("   insert into Paymoney (");
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
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Id = Float.parseFloat(param.get("id").getParamvalue().toString());
			errmess =Func.FormatNumber(Id).toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		// StringBuilder strSql = new StringBuilder();
		String qry = "declare ";
		qry += "\n   v_curr number; ";
		qry += "\n   v_paycurr number; ";
		qry += "\n   v_remark varchar2(200); ";
		qry += "\n   v_orderno varchar2(40); ";
		qry += "\n   v_accid number; ";
		qry += "\n   v_id number; ";
		qry += "\n   v_notedate date; ";
		qry += "\n   v_balcurr number; ";
		qry += "\n begin ";
		// qry += " v_accid:=" + accid + "; ";
		// qry += " v_id:=" + id + "; ";
		qry += "\n   v_balcurr:=0; ";
		qry += "\n   update paymoney set ";
		qry += "   zt=" + Zt + ",";
		if (Orderno != null && !Orderno.equals("")) {
			qry += "orderno='" + Orderno + "',";
		}
		qry += " lastdate=sysdate";
		qry += "  where id=" + Id + " and accid=" + Accid + "; ";
		if (Zt == 1) // 充值成功
		{
			qry += "\n    select notedate,curr,nvl(paycurr,0),remark,orderno into v_notedate,v_curr,v_paycurr,v_remark,v_orderno from paymoney where id=" + Id + " and accid=" + Accid + "; ";
			qry += "\n    insert into accmoney (id,accid,notedate,fs,curr,paycurr,remark,handno,lastdate,lastop) ";
			qry += "\n       values (accmoney_id.nextval," + Accid + " ,v_notedate,0,v_curr,v_paycurr,v_remark,v_orderno,sysdate,'" + Lastop + "'); ";
			qry += "\n    select NVL(sum(curr),0) into v_balcurr from accmoney where accid=" + Accid + "; ";
			qry += "\n    update accreg set balcurr=v_balcurr,succtag=2,usernum=usernum+floor(v_paycurr/1000) where accid=" + Accid + "; ";
			qry += "\n    select nvl(v_balcurr,0) into :balcurr from dual;";
		} else {
			qry += "\n    select nvl(balcurr,0) into :balcurr from accreg where accid=" + Accid + "; ";

		}
		qry += "\n end; ";
//		System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("balcurr", new ProdParam(Types.DOUBLE));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
//		System.out.println("11112222");
		errmess = param.get("balcurr").getParamvalue().toString();
//		System.out.println("2222");
		return 1;


	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Paymoney ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Paymoney ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Paymoney");
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