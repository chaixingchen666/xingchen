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
//  @author:sunhong  @date:2017-03-05 18:25:13
//*************************************
public class Printdev implements java.io.Serializable {
	private Float Id;
	private String Prtdevno;
	private Date Regdate;
	private String Remark;
	private Long Accid;
	private Integer Statetag;
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

	public void setPrtdevno(String prtdevno) {
		this.Prtdevno = prtdevno;
	}

	public String getPrtdevno() {
		return Prtdevno;
	}

	public void setRegdate(Date regdate) {
		this.Regdate = regdate;
	}

	public String getRegdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Regdate);
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
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
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update Printdev set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append() {
		if (Prtdevno.equals("")) {
			errmess = "设备号无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("printdev_id.nextval,");
		strSql1.append("prtdevno,");
		strSql2.append("'" + Prtdevno + "',");
		strSql1.append("regdate,");
		strSql2.append("sysdate,");
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		// if (Accid != null) {
		// strSql1.append("accid,");
		// strSql2.append(Accid + ",");
		// }
		// if (Statetag != null) {
		strSql1.append("statetag,");
		strSql2.append("1,");
		// }
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		// strSql.append(" v_id number; ");
		strSql.append("   v_count number; ");
		strSql.append("   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from printdev where prtdevno='" + Prtdevno + "';");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:= '0设备号已存在！' ; ");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		// strSql.append(" v_id:=Printdev_id.nextval; ");
		strSql.append("\n   insert into Printdev (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n  v_retcs:='1增加成功！';");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual; ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("0"))
			return 0;
		else
			return 1;
	}

	// 查找设备
	public int Find() {
		if (Prtdevno.equals("")) {
			errmess = "设备号无效！";
			return 0;
		}
		String qry = "declare ";
		qry += "    v_statetag number(1);";
		qry += "    v_id number;";
		qry += "    v_retcs varchar2(200);";
		qry += " begin";
		qry += "   begin";
		qry += "     select statetag,id into v_statetag,v_id from printdev where prtdevno='" + Prtdevno + "' and statetag<=1;";
		qry += "     if v_statetag=0 then ";
		qry += "        v_retcs:= '0设备号 " + Prtdevno + " 未启用！'; ";
		qry += "        goto exit;";
		qry += "     end if;";
		qry += "   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "     v_retcs:= '0设备号 " + Prtdevno + " 未登记！' ; ";
		qry += "     goto exit;";
		qry += "   end ;";
		qry += "   update printdev set accid=" + Accid + ",lastdate=sysdate where id=v_id;";
		qry += "   v_retcs:= '1设备可用！' ; ";
		qry += "   <<exit>>";
		qry += "   select v_retcs into :retcs from dual;";
		qry += " end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("0"))
			return 0;
		else
			return 1;

	}

	// 修改记录
	public int Update() {
		if (Prtdevno == null || Prtdevno.equals("")) {
			errmess = "设备号无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Printdev set ");
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where Prtdevno='" + Prtdevno + "' ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "修改失败！";
			return 0;
		} else {
			errmess = "修改成功！";
			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Printdev ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据 ok
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select " + fieldlist);
		strSql.append(" FROM Printdev a left outer join accreg b on a.accid=b.accid and length(b.mobile)>0");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Printdev");
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