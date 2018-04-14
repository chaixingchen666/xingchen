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
//  @author:sunhong  @date:2017-02-22 08:52:16
//*************************************
public class Omcollect implements java.io.Serializable {
	private Long Omepid;
	private Long Omid;
	private Long Wareid;
	private Date Lastdate;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setOmepid(Long omepid) {
		this.Omepid = omepid;
	}

	public Long getOmepid() {
		return Omepid;
	}

	public void setOmid(Long omid) {
		this.Omid = omid;
	}

	public Long getOmid() {
		return Omid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}
	//
	// public void setLastdate(Date lastdate) {
	// this.Lastdate = lastdate;
	// }
	//
	// public String getLastdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Lastdate);
	// }

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
		// strSql.append(" update omcollect set statetag=2,lastdate=sysdate");
		// // strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) == 0) {
		// errmess = "删除失败！";
		// return 0;
		// }

		String qry = "delete from Omcollect where omid=" + Omid + " and omepid=" + Omepid + " and wareid=" + Wareid;

		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 增加记录
	public int Append() {

		String qry = "declare";
		qry += "   v_count number;";
		qry += " begin";
		qry += "   select count(*) into v_count from Omcollect where omid=" + Omid + " and omepid=" + Omepid + " and wareid=" + Wareid + ";";
		qry += "   if v_count=0 then ";
		qry += "      insert into Omcollect (omid,omepid,wareid)";
		qry += "      values (" + Omid + "," + Omepid + "," + Wareid + ");";
		qry += "   end if;";
		qry += " end;";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "增加失败！";
			return 0;
		} else {
			errmess = "增加成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update omcollect set ");
		if (Omepid != null) {
			strSql.append("omepid=" + Omepid + ",");
		}
		if (Omid != null) {
			strSql.append("omid=" + Omid + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
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
		strSql.append(" FROM omcollect ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		// string qry = " select " + fieldlist;
		// qry += " from warecode a ";
		// qry += " where a.accid=" + htp.maccid + " and a.statetag=1 and a.noused=0";
		// qry += " and exists (select 1 from Omcollect a1 where a1.omid=" + htp.omid + " and a.wareid=a1.wareid and a1.omepid=" + htp.userid + ")";

		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM omcollect b left outer join warecode a on a.wareid=b.wareid ");
		strSql.append("\n join omwarecode c on b.omid=c.omid and a.wareid=c.wareid");
	
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
//		System.out.println("aa a " + strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from omcollect");
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