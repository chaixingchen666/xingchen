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

//*************************************
//  @author:sunhong  @date:2017-07-06 17:34:27
//*************************************
public class Label implements java.io.Serializable {
	private Float Labelid;
	private String Labelname;
	private String Shortname;
	private Long Accid;
	private Long Lyfs;
	private Date Lastdate;
	private String Lastop;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setLabelid(Float labelid) {
		this.Labelid = labelid;
	}

	public Float getLabelid() {
		return Labelid;
	}

	public void setLabelname(String labelname) {
		this.Labelname = labelname;
	}

	public String getLabelname() {
		return Labelname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setLyfs(Long lyfs) {
		this.Lyfs = lyfs;
	}

	public Long getLyfs() {
		return Lyfs;
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
		if (Labelid == 0) {
			errmess = "labeldid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append(" begin");
		strSql.append("\n   delete label where labelid=" + Labelid + " and accid=" + Accid + ";");
		strSql.append(" end;");
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
		if (!Func.isNull(Labelname)) {
			errmess = "标签名称不允许为空！";
			return 0;
		}
		if (Lyfs == null) {// lyfs:0=商品，1=零售商（客户），2=会员
			errmess = "lyfs不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("labelid,");
		strSql2.append("v_labelid,");
		// if (Labelname != null) {
		strSql1.append("labelname,");
		strSql2.append("'" + Labelname + "',");
		Shortname = PinYin.getPinYinHeadChar(Labelname);
		// }
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		if (Lyfs != null) { // lyfs:0=商品，1=零售商（客户），2=会员
			strSql1.append("lyfs,");
			strSql2.append(Lyfs + ",");
		}
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_labelid number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count  from label");
		strSql.append("\n   where labelname='" + Labelname + "' and lyfs=" + Lyfs + " and rownum=1 and accid=" + Accid);
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0标签名称已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   v_labelid:=label_id.nextval; ");
		strSql.append("\n   insert into label (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n   v_retcs:='1'||v_labelid;");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual; ");
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
		 return Integer.parseInt(retcs.substring(0, 1));
	}

	// 修改记录
	public int Update() {
		if (Labelid == null || Labelid == 0) {
			errmess = "labelid不是一个有效值！";
			return 0;
		}
		if (!Func.isNull(Labelname)) {
			errmess = "标签名称不允许为空！";
			return 0;
		}
		if (Lyfs == null) {// lyfs:0=商品，1=零售商（客户），2=会员
			errmess = "lyfs不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count  from label");
		strSql.append("\n   where labelname='" + Labelname + "' and lyfs=" + Lyfs + " and lableid<>" + Labelid + " and rownum=1 and accid=" + Accid);
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0标签名称已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   update label set ");
		// if (Labelid != null) {
		// strSql.append("labelid='" + Labelid + "',");
		// }
		// if (Labelname != null) {
		strSql.append("labelname='" + Labelname + "',");
		Shortname = PinYin.getPinYinHeadChar(Labelname);
		// }
		// if (Shortname != null) {
		strSql.append("Shortname='" + Shortname + "',");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		// if (Lyfs != null) {
		// strSql.append("lyfs=" + Lyfs + ",");
		// }
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where accid=" + Accid + " and labelid=" + Labelid + ";");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual; ");
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

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM label ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM label ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from label");
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