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
//  @author:sunhong  @date:2017-05-11 08:59:40
//*************************************
public class Emplarea implements java.io.Serializable {
	private Long Accid;
	private Long Epid;
	private Long Areaid;
	private Date Lastdate;
	private String Lastop;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getEpid() {
		return Epid;
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
		// strSql.append(" update emplarea set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
	public int Append(int value) {
		String qry;
		if (value == 0) {
			qry = "delete from emplarea where epid=" + Epid + " and areaid=" + Areaid;
		} else {
			qry = " declare";
			qry += "   v_count number;";
			qry += " begin";
			qry += "    select count(*) into v_count from emplarea where epid=" + Epid + " and areaid=" + Areaid + " ;";
			qry += "    if v_count=0 then";
			qry += "       insert into emplarea (epid,areaid,lastop) ";
			qry += "       values (" + Epid + ", " + Areaid + ",'" + Lastop + "');";
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

	// 成批 增加记录
	public int AppendAll(int value) {
		String qry;
		if (value == 0) {
			qry = "delete from emplarea where epid=" + Epid;
		} else {
			qry = "insert into emplarea (epid,areaid,lastop)";
			qry += " select " + Epid + ",areaid,'" + Lastop + "' from area a where a.accid=" + Accid;
			qry += " and a.statetag=1 and a.noused=0 and not exists (select 1 from emplarea b where a.areaid=b.areaid and b.epid=" + Epid + ") ";
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
		strSql.append("   update emplarea set ");
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
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
		strSql.append(" FROM emplarea ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();

		strSql.append(" select a.areaid,b.areaname,'1' as selbj from emplarea a");
		strSql.append("\n join area b on a.areaid=b.areaid");
		strSql.append("\n where b.statetag=1 and b.noused=0 and b.accid=" + Accid + " and a.epid=" + Epid);
		strSql.append("\n union all");
		strSql.append("\n select b.areaid,b.areaname,'0' as selbj from area b ");
		strSql.append("\n where b.statetag=1 and b.noused=0 and b.accid=" + Accid);
		strSql.append("\n and not exists (select 1 from emplarea a where a.areaid=b.areaid and a.epid=" + Epid + ")");

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from emplarea");
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