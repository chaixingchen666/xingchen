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

import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-05-18 17:32:07
//*************************************
public class Waresaleman implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private String Findbox;
	private Long Epid;
	private Date Lastdate;
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

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public void setFindbox(String findbox) {
		this.Findbox = findbox;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
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
		// strSql.append(" update waresaleman set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
		if (Id != null) {
			strSql1.append("id,");
			strSql2.append("'" + Id + "',");
		}
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Noteno != null) {
			strSql1.append("noteno,");
			strSql2.append("'" + Noteno + "',");
		}
		if (Epid != null) {
			strSql1.append("epid,");
			strSql2.append(Epid + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=waresaleman_id.nextval; ");
		strSql.append("   insert into waresaleman (");
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
		strSql.append("   update waresaleman set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Noteno != null) {
			strSql.append("noteno='" + Noteno + "',");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
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
		strSql.append(" FROM waresaleman ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList() {
		String qry = "SELECT noteno, TRANSLATE (LTRIM (text, '~'), '*~', '*,') salemanlist";
		qry += " FROM (SELECT ROW_NUMBER () OVER (PARTITION BY noteno ORDER BY noteno,lvl DESC) rn,noteno, text";
		qry += "  FROM (SELECT  noteno, LEVEL lvl, SYS_CONNECT_BY_PATH (epname,'~') text";
		qry += "  FROM (SELECT  a.noteno, a.epid,b.epname as epname, ROW_NUMBER () OVER (PARTITION BY a.noteno ORDER BY a.noteno,a.epid) x";
		qry += " FROM waresaleman a join employe b on a.epid=b.epid where a.accid=" + Accid + " and a.noteno='" + Noteno + "' ORDER BY a.epid) a";
		qry += "  CONNECT BY noteno = PRIOR noteno AND x - 1 = PRIOR x))";
		qry += "  WHERE rn = 1";
		return DbHelperSQL.Query(qry);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM waresaleman ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, long houseid) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.epid,b.epname,'1' as selbj from waresaleman a");
		strSql.append("\n join employe b on a.epid=b.epid");
		strSql.append("\n where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		if (!Func.isNull(Findbox))
			strSql.append("\n and (b.epname like '%" + Findbox + "%' or b.shortname like '%" + Findbox.toUpperCase() + "%' or b.epno like '%" + Findbox.toUpperCase() + "%')");
		if (houseid != 0) {
			strSql.append("\n union all");
			strSql.append("\n select a.epid,a.epname,'0' as selbj from employe a where a.accid=" + Accid + " and a.statetag=1 and a.noused=0");
			if (!Func.isNull(Findbox))
				strSql.append("\n and (a.epname like '%" + Findbox + "%' or a.shortname like '%" + Findbox.toUpperCase() + "%' or a.epno like '%" + Findbox.toUpperCase() + "%')");
			if (houseid > 0)
				strSql.append("\n and a.houseid=" + houseid); // 同一店铺的职员
			strSql.append("\n and not exists (select 1 from waresaleman b where a.epid=b.epid and b.noteno='" + Noteno + "' and b.accid=" + Accid + ")");
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from waresaleman");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	public int Write(int value, int clearall) {
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		// String qry="begin ";
		String qry = " declare";
		qry += "   v_count number;";
		qry += " begin";

		if (value == 0)
			qry += "  delete from waresaleman where accid=" + Accid + " and epid=" + Epid + " and noteno='" + Noteno + "';";
		else {
			if (clearall == 1) // 清除原来的所有销售人
				qry += "  delete from waresaleman where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "    select count(*) into v_count from waresaleman where epid=" + Epid + " and noteno='" + Noteno + "' and accid=" + Accid + " ;";
			qry += "    if v_count=0 then";
			qry += "      insert into waresaleman (id,epid,accid,noteno,lastdate) ";
			qry += "      values (waresaleman_id.nextval," + Epid + ", " + Accid + ",'" + Noteno + "',sysdate);";
			qry += "    end if;";
		}
		qry += " end ;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}
}