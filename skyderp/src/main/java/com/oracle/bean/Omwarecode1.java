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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-04 17:36:58
//*************************************
public class Omwarecode1 implements java.io.Serializable {
	private Long Omid;
	private Long Wareid;
	private Long Wareid1;
	private Date Lastdate;
	private String Lastop;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

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

	public void setWareid1(Long wareid1) {
		this.Wareid1 = wareid1;
	}

	public Long getWareid1() {
		return Wareid1;
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
		strSql.append(" delete OMWARECODE1 ");
		strSql.append(" where omid=" + Omid + " and wareid=" + Wareid + " and wareid1=" + Wareid1);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		errmess = "删除成功！";
		return 1;
	}

	// 增加记录 ok
	public int Append(JSONObject jsonObject) {

		if (Omid == 0 || Wareid == 0) {
			errmess = "订货会id或商品id参数无效！";
			return 0;
		}
		if (!jsonObject.has("warelist")) {
			errmess = "搭配商品参数无效！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("warelist");
		// "warelist":[{"wareid":4650},{"wareid":4649}]

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n begin ");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Wareid1 = Long.parseLong(jsonObject2.getString("wareid"));
			strSql.append("\n   select count(*) into v_count from OMWARECODE1 where omid=" + Omid + " and wareid=" + Wareid + " and wareid1=" + Wareid1 + ";");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      insert into OMWARECODE1 (omid,wareid,wareid1,lastop,lastdate) ");
			strSql.append("\n      values (" + Omid + "," + Wareid + "," + Wareid1 + ",'" + Lastop + "',sysdate); ");
			strSql.append("\n   end if;");

			strSql.append("\n   select count(*) into v_count from OMWARECODE1 where omid=" + Omid + " and wareid=" + Wareid1 + " and wareid1=" + Wareid + ";");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      insert into OMWARECODE1 (omid,wareid,wareid1,lastop,lastdate) ");
			strSql.append("\n      values (" + Omid + "," + Wareid1 + "," + Wareid + ",'" + Lastop + "',sysdate); ");
			strSql.append("\n   end if;");

		}
		strSql.append("\n end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
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
		strSql.append("   update OMWARECODE1 set ");
		if (Omid != null) {
			strSql.append("omid=" + Omid + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Wareid1 != null) {
			strSql.append("wareid1=" + Wareid1 + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
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
		strSql.append(" FROM OMWARECODE1 ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM OMWARECODE1 ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取全部数据
	public Table GetTable(String strWhere, String fieldlist, String order) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM OMWARECODE1 a join warecode b on a.wareid1=b.wareid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		if (!order.equals("")) {
			strSql.append(" order by " + order);
		}

		return DbHelperSQL.GetTable(strSql.toString());
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from OMWARECODE1");
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