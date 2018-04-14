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
//  @author:sunhong  @date:2017-03-16 10:03:53
//*************************************
public class Roleprog implements java.io.Serializable {
	private Long Levelid;
	private Long Progid;
	private Integer Fs;
	private Date Lastdate;
	private Integer Defbj;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setLevelid(Long levelid) {
		this.Levelid = levelid;
	}

	public Long getLevelid() {
		return Levelid;
	}

	public void setProgid(Long progid) {
		this.Progid = progid;
	}

	public Long getProgid() {
		return Progid;
	}

	public void setFs(Integer fs) {
		this.Fs = fs;
	}

	public Integer getFs() {
		return Fs;
	}

	public void setDefbj(Integer defbj) {
		this.Defbj = defbj;
	}

	public Integer getDefbj() {
		return Defbj;
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
		// strSql.append(" update Roleprog set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		return 1;
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Levelid != null) {
			strSql1.append("levelid,");
			strSql2.append(Levelid + ",");
		}
		if (Progid != null) {
			strSql1.append("progid,");
			strSql2.append(Progid + ",");
		}
		if (Fs != null) {
			strSql1.append("fs,");
			strSql2.append(Fs + ",");
		}
		if (Defbj != null) {
			strSql1.append("defbj,");
			strSql2.append(Defbj + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Roleprog_id.nextval; ");
		strSql.append("   insert into Roleprog (");
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
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Roleprog set ");
		if (Levelid != null) {
			strSql.append("levelid=" + Levelid + ",");
		}
		if (Progid != null) {
			strSql.append("progid=" + Progid + ",");
		}
		if (Fs != null) {
			strSql.append("fs=" + Fs + ",");
		}
		if (Defbj != null) {
			strSql.append("defbj=" + Defbj + ",");
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
		strSql.append(" FROM Roleprog ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Roleprog ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM Roleprog ");
		// if (!strWhere.equals("")) {
		// strSql.append(" where " + strWhere);
		// }
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Roleprog");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 后台调 用
	public int Write(int value) {

		String qry;
		qry = " declare";
		qry += "   v_count number; ";
		qry += "   v_defbj number(1); ";
		qry += " begin";
		qry += "   v_defbj:=" + Defbj + "; ";
		if (value == 0) // 选中该功能
		{
			// qry = "delete from progrole where levelid=" + levelid + " and progid=" + progid;
			qry += "   delete from roleprog where levelid=" + Levelid + " and progid=" + Progid + ";";
		} else {
			qry += "    select count(*) into v_count from roleprog where fs=0 and levelid=" + Levelid + " and progid=" + Progid + ";";
			qry += "    if v_count=0 then";
			qry += "       insert into roleprog (levelid,progid,fs,lastdate,defbj) ";
			qry += "       values (" + Levelid + ", " + Progid + ",0,sysdate,v_defbj);";
			qry += "    else ";
			qry += "       update roleprog set defbj=v_defbj where fs=0 and levelid=" + Levelid + " and progid=" + Progid + "; ";
			qry += "    end if; ";
		}
		qry += " end ;";
//        LogUtils.LogDebugWrite("writeprogrole", qry);
		
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 用户调 用
	public int Write1(int value) {

		String qry;
		if (value == 0) {
			qry = " delete from roleprog where fs=0 and levelid=" + Levelid + " and progid=" + Progid;
		} else {
			qry = " declare";
			qry += "   v_count number;";
			qry += " begin";
			qry += "    select count(*) into v_count from roleprog where levelid=" + Levelid + " and progid=" + Progid + " and fs=0;";
			qry += "    if v_count=0 then";
			qry += "     insert into roleprog (fs,levelid,progid,lastdate) ";
			qry += "      values (0," + Levelid + ", " + Progid + ",sysdate);";
			qry += "    end if; ";
			qry += " end ;";
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

	// 用户调 用 成批写权限
	public int Write1(int value, int grpid) {

		String qry;
		if (value == 0) {
			qry = "delete from roleprog a where a.fs=0 and a.levelid=" + Levelid;
			if (grpid > 0)
				qry += " and exists (select 1 from sysprog b where a.progid=b.progid and b.grpid=" + grpid + ")";
		} // + " and houseid=" + houseid;
		else {
			qry = "insert into roleprog (fs,levelid,progid,lastdate)";
			qry += " select 0," + Levelid + ",progid,sysdate from sysprog a ";
			qry += " where not exists (select 1 from roleprog b where b.fs=0 and a.progid=b.progid and b.levelid=" + Levelid + ") ";
			if (grpid > 0)
				qry += " and a.grpid=" + grpid;
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