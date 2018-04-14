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
//  @author:sunhong  @date:2017-03-08 21:22:18
//*************************************
public class Sysreadnotice implements java.io.Serializable {
	private Float Id;
	private Float Ggid;
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

	public void setGgid(Float ggid) {
		this.Ggid = ggid;
	}

	public Float getGgid() {
		return Ggid;
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
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update sysreadnotice set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// // strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		return 1;
	}

	// 增加记录 ok
	public int Append() {

		if (Ggid == null || Ggid == 0 || Epid == null || Epid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		String qry = " declare";
		qry += "   v_count number;";
		qry += " begin";
		qry += "    select count(*) into v_count from sysreadnotice where epid=" + Epid + " and ggid=" + Ggid + " ;";
		qry += "    if v_count=0 then";
		qry += "       insert into sysreadnotice (id,ggid,epid,lastdate) ";
		qry += "       values (SYSREADNOTICE_id.nextval," + Ggid + ", " + Epid + ",sysdate);";
		qry += "    end if;";
		qry += " end ;";

		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
//		StringBuilder strSql = new StringBuilder();
//		strSql.append("begin ");
//		strSql.append("   update sysreadnotice set ");
//		if (Id != null) {
//			strSql.append("id='" + Id + "',");
//		}
//		if (Ggid != null) {
//			strSql.append("ggid='" + Ggid + "',");
//		}
//		if (Epid != null) {
//			strSql.append("epid=" + Epid + ",");
//		}
//		strSql.append("lastdate=sysdate");
//		strSql.append("  where id=id ;");
//		strSql.append("  commit;");
//		strSql.append(" end;");
//		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
//		if (ret < 0)
//			return 0;
//		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM sysreadnotice ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM sysreadnotice ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from sysreadnotice");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}
}