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
//  @author:sunhong  @date:2017-02-25 16:07:44
//*************************************
public class Waretype implements java.io.Serializable {
	private Long Typeid;
	private String Typename;
	private String Fullname;
	private Integer Noused;
	private Long P_typeid;
	private Integer Lastnode;
	private Integer Selected;
	private Date Lastdate;
	private String Shortname;
	private String Typeno;
	private Long Accid;
	private Integer Statetag;
	private Long Ordid;
	private String errmess;
//	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setTypeid(Long typeid) {
		this.Typeid = typeid;
	}

	public Long getTypeid() {
		return Typeid;
	}

	public void setTypename(String typename) {
		this.Typename = typename;
	}

	public String getTypename() {
		return Typename;
	}

	public void setFullname(String fullname) {
		this.Fullname = fullname;
	}

	public String getFullname() {
		return Fullname;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setP_typeid(Long p_typeid) {
		this.P_typeid = p_typeid;
	}

	public Long getP_typeid() {
		return P_typeid;
	}

	public void setLastnode(Integer lastnode) {
		this.Lastnode = lastnode;
	}

	public Integer getLastnode() {
		return Lastnode;
	}

	public void setSelected(Integer selected) {
		this.Selected = selected;
	}

	public Integer getSelected() {
		return Selected;
	}

//	public void setLastdate(Date lastdate) {
//		this.Lastdate = lastdate;
//	}

//	public String getLastdate() {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		return df.format(Lastdate);
//	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setTypeno(String typeno) {
		this.Typeno = typeno;
	}

	public String getTypeno() {
		return Typeno;
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

	public void setOrdid(Long ordid) {
		this.Ordid = ordid;
	}

	public Long getOrdid() {
		return Ordid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

//	public void setAccbegindate(String accbegindate) {
//		this.Accbegindate = accbegindate;
//	}

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
		strSql.append(" update waretype set statetag=2,lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) == 0) {
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
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Typeid != null) {
			strSql1.append("typeid,");
			strSql2.append(Typeid + ",");
		}
		if (Typename != null) {
			strSql1.append("typename,");
			strSql2.append("'" + Typename + "',");
		}
		if (Fullname != null) {
			strSql1.append("fullname,");
			strSql2.append("'" + Fullname + "',");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (P_typeid != null) {
			strSql1.append("p_typeid,");
			strSql2.append(P_typeid + ",");
		}
		if (Lastnode != null) {
			strSql1.append("lastnode,");
			strSql2.append(Lastnode + ",");
		}
		if (Selected != null) {
			strSql1.append("selected,");
			strSql2.append(Selected + ",");
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		if (Typeno != null) {
			strSql1.append("typeno,");
			strSql2.append("'" + Typeno + "',");
		}
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Ordid != null) {
			strSql1.append("ordid,");
			strSql2.append(Ordid + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=waretype_id.nextval; ");
		strSql.append("   insert into waretype (");
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
		return ret;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update waretype set ");
		if (Typeid != null) {
			strSql.append("typeid=" + Typeid + ",");
		}
		if (Typename != null) {
			strSql.append("typename='" + Typename + "',");
		}
		if (Fullname != null) {
			strSql.append("fullname='" + Fullname + "',");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (P_typeid != null) {
			strSql.append("p_typeid=" + P_typeid + ",");
		}
		if (Lastnode != null) {
			strSql.append("lastnode=" + Lastnode + ",");
		}
		if (Selected != null) {
			strSql.append("selected=" + Selected + ",");
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Typeno != null) {
			strSql.append("typeno='" + Typeno + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Ordid != null) {
			strSql.append("ordid=" + Ordid + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		return DbHelperSQL.ExecuteSql(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM waretype ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM waretype ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public Table GetTable(String qry) {
System.out.println(qry);
		return DbHelperSQL.GetTable(qry);
	}
	
	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from waretype");
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