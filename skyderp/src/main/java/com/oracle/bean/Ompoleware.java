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
//  @author:sunhong  @date:2017-03-04 15:44:56
//*************************************
public class Ompoleware implements java.io.Serializable {
	private Long Poleid;
	private Long Wareid;
	private Date Lastdate;
	private String Lastop;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setPoleid(Long poleid) {
		this.Poleid = poleid;
	}

	public Long getPoleid() {
		return Poleid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
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
		strSql.append(" delete Ompoleware ");
		strSql.append(" where poleid=" + Poleid + " and wareid=" + Wareid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录 ok
	public int Append(JSONObject jsonObject) {
		if (Poleid == 0) {
			errmess = "陈列杆id参数无效！";
			return 0;
		}
		if (!jsonObject.has("warelist")) {
			errmess = "陈列杆商品参数无效！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("warelist");
		// {"flyang":"20150107","poleid":"陈列杆id","warelist":[{"wareid":1},{"wareid":2},{"wareid":3}]}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n begin ");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Wareid = Long.parseLong(jsonObject2.getString("wareid"));

			strSql.append("\n   select count(*) into v_count from ompoleware where poleid=" + Poleid + " and wareid=" + Wareid + ";");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      insert into ompoleware (poleid,wareid,lastop,lastdate) ");
			strSql.append("\n      values (" + Poleid + "," + Wareid + ",'" + Lastop + "',sysdate); ");
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
		strSql.append("   update Ompoleware set ");
		if (Poleid != null) {
			strSql.append("poleid=" + Poleid + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
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
		strSql.append(" FROM Ompoleware ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Ompoleware ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取全部数据 ok
	public Table GetTable(String strWhere, String fieldlist, String sort) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Ompoleware a join warecode b on a.wareid=b.wareid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		if (!sort.equals("")) {
			strSql.append(" order by " + sort);
		}
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(strSql.toString());
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Ompoleware");
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