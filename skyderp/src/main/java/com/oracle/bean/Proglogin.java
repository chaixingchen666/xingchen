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
//  @author:sunhong  @date:2017-03-12 00:11:12
//*************************************
public class Proglogin implements java.io.Serializable {
	private Float Id;
	private Long Epid;
	private Long Progid;
	private Date Logindate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setProgid(Long progid) {
		this.Progid = progid;
	}

	public Long getProgid() {
		return Progid;
	}

	public void setLogindate(Date logindate) {
		this.Logindate = logindate;
	}

	public String getLogindate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Logindate);
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
		// strSql.append(" update proglogin set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		return 1;
	}

	// 增加记录
	public int Append(long accid) {
		if (accid == 1000) {
			errmess = "演示账套！";
			return 1;
		}
		if (Progid == 0) {
			errmess = "授权正常！";
			return 1;
		}
		if (Epid==null && Epid<=0)
		{		
			errmess = "epid不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_levelid number(10); ";
		qry += "\n   v_epid number(10); ";
		qry += "\n   v_progid number(10); ";
		qry += "\n   v_epno varchar2(20); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n begin ";
		qry += "\n   v_epid:=" + Epid + ";";
		qry += "\n   v_progid:=" + Progid + ";";
		qry += "\n   begin";
		qry += "\n     select levelid,epno into v_levelid,v_epno from employe where epid=v_epid;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN   ";
		qry += "\n     v_retcs:= '0用户未找到！' ; ";
		qry += "\n     goto exit;";
		qry += "\n   end; ";
		qry += "\n   if v_levelid=0 then "; // 系统管理员，可以用所有功能
		qry += "\n      v_retcs:= '1该功能'||v_progid||'允许使用！' ; ";
		qry += "\n      insert into proglogin (id,epid,progid,LOGINDATE)";
		qry += "\n      values (proglogin_id.nextval,v_epid,v_progid,sysdate);";
		qry += "\n      goto exit;";
		qry += "\n   end if; ";
		qry += "\n   begin";
		qry += "\n     select levelid into v_levelid  from roleprog where fs=0 and levelid=v_levelid and progid=v_progid; ";
		qry += "\n     v_retcs:= '1该功能'||v_progid||'允许使用！' ; ";
		qry += "\n     insert into proglogin (id,epid,progid,LOGINDATE)";
		qry += "\n     values (proglogin_id.nextval,v_epid,v_progid,sysdate);";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN   ";
		qry += "\n     v_retcs:= '0该功能'||v_progid||'未授权' ; ";
		qry += "\n   end; ";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret == 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));

	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update proglogin set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Progid != null) {
			strSql.append("progid=" + Progid + ",");
		}
		if (Logindate != null) {
			strSql.append("logindate='" + Logindate + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
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
		strSql.append(" FROM proglogin ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM proglogin ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据
	public Table GetTable(String qry) {

		return DbHelperSQL.GetTable(qry);
	}
	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from proglogin");
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