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
//  @author:sunhong  @date:2017-05-11 09:44:56
//*************************************
public class Provarea implements java.io.Serializable {
	private Long Accid;
	private Long Provid;
	private Long Areaid;
	private Date Lastdate;
	private String Lastop;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setProvid(Long provid) {
		this.Provid = provid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}


	public Long getProvid() {
		return Provid;
	}

	public void setAreaid(Long areaid) {
		this.Areaid = areaid;
	}

	public Long getAreaid() {
		return Areaid;
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
		// strSql.append(" update Provarea set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}
	// 成批 增加记录
	public int AppendAll(int value) {
		String qry;
		if (value == 0) {
			qry = "delete from Provarea where provid=" + Provid;
		} else {
			qry = "insert into Provarea (Provid,areaid,lastop)";
			qry += " select " + Provid + ",areaid,'" + Lastop + "' from area a where a.accid=" + Accid;
			qry += " and a.statetag=1 and a.noused=0 and not exists (select 1 from Provarea b where a.areaid=b.areaid and b.Provid=" + Provid + ") ";
		}
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}


	// 增加记录
	public int Append(int value) {
		String qry;
		if (value == 0) {
			qry = "delete from Provarea where provid=" + Provid + " and areaid=" + Areaid;
		} else {
			qry = " declare";
			qry += "   v_count number;";
			qry += " begin";
			qry += "    select count(*) into v_count from Provarea where Provid=" + Provid + " and areaid=" + Areaid + " ;";
			qry += "    if v_count=0 then";
			qry += "       insert into Provarea (provid,areaid,lastop) ";
			qry += "       values (" + Provid + ", " + Areaid + ",'" + Lastop + "');";
			qry += "    end if;";
			qry += "    commit;";
			qry += " end ;";
		}
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
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
		strSql.append("   update Provarea set ");
		if (Provid != null) {
			strSql.append("provid=" + Provid + ",");
		}
		if (Areaid != null) {
			strSql.append("areaid=" + Areaid + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
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
		strSql.append(" FROM Provarea ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.areaid,b.areaname,'1' as selbj from provarea a");
		strSql.append(" join area b on a.areaid=b.areaid");
		strSql.append( " where b.statetag=1 and b.noused=0 and b.accid=" + Accid + " and a.provid=" + Provid);
		strSql.append( " union all");
		strSql.append( " select b.areaid,b.areaname,'0' as selbj from area b ");
		strSql.append(" where b.statetag=1 and b.noused=0 and b.accid=" + Accid);
		strSql.append( " and not exists (select 1 from provarea a where a.areaid=b.areaid and a.provid=" +Provid + ")");

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Provarea");
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