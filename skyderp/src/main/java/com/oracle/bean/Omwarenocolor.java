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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-04-19 14:22:51
//*************************************
public class Omwarenocolor implements java.io.Serializable {
	private Long Omid;
	private Long Wareid;
	private Long Colorid;
	private String Lastop;
	private Date Lastdate;
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

	public void setColorid(Long colorid) {
		this.Colorid = colorid;
	}

	public Long getColorid() {
		return Colorid;
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
		// strSql.append(" update OMWARENOCOLOR set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
	public int Append(JSONObject jsonObject) {
		if (Wareid == 0 || Omid == 0) {
			errmess = "wareid或omid不是一个有效值！";
			return 0;
		}
		if (!jsonObject.has("colorlist")) {
			errmess = "colorlist不是一个有效值！";
			return 0;
		}

		JSONArray jsonArray = jsonObject.getJSONArray("colorlist");
		if (jsonArray.size() == 0) {
			errmess = "colorlist记录无效！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_count  number(10); ";
		qry += "\n    v_retcs  varchar2(100); ";
		qry += "\n begin ";
		// 删除异常的尺码
		qry += "\n   delete from omwarenocolor a  where a.omid=" + Omid + " and a.wareid=" + Wareid + ";";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Colorid = Long.parseLong(jsonObject2.getString("colorid"));
			int value = Integer.parseInt(jsonObject2.getString("value"));

			if (value == 1) // 可用
			{
				qry += "\n   delete from omwarenocolor where omid=" + Omid + " and  wareid=" + Wareid + " and colorid=" + Colorid + "; ";
			} else // 不可用
			{
				qry += "\n   select count(*) into v_count from omwarenocolor where omid=" + Omid + " and wareid=" + Wareid + " and colorid=" + Colorid + ";";
				qry += "\n   if v_count=0 then";
				qry += "\n      insert into omwarenocolor (omid,wareid,colorid,lastop) values (" + Omid + "," + Wareid + "," + Colorid + ",'" + Lastop + "'); ";
				qry += "\n   end if;";
			}
		}
		qry += "\n   commit; ";
		qry += "\n   v_retcs:= '1操作成功！'; ";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end; ";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
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
		strSql.append("   update OMWARENOCOLOR set ");
		if (Omid != null) {
			strSql.append("omid=" + Omid + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Colorid != null) {
			strSql.append("colorid=" + Colorid + ",");
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
		strSql.append(" FROM OMWARENOCOLOR ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM OMWARENOCOLOR ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable() {

		// String qry = "select colorid,colorname,sizeno,selbj from (";

		String qry="select colorid,colorname,colorno,selbj from (";
		qry += "select a.colorid,b.colorname,b.colorno,1 as selbj";
		qry += "\n from warecolor a";
		qry += "\n join colorcode b on a.colorid=b.colorid  ";
		qry += "\n where b.statetag=1 and b.noused=0 and a.wareid=" + Wareid;
		qry += "\n and not exists (select 1 from omwarenocolor c where a.colorid=c.colorid and a.wareid=c.wareid and c.omid=" + Omid + ")";
		qry += "\n union all";
		// 禁用的颜色
		qry += "\n select a.colorid,b.colorname,b.colorno,0 as selbj";
		qry += "\n from warecolor a";
		qry += "\n join colorcode b on a.colorid=b.colorid  ";
		qry += "\n where b.statetag=1 and b.noused=0 and a.wareid=" + Wareid;
		qry += "\n and exists (select 1 from omwarenocolor c where a.colorid=c.colorid and a.wareid=c.wareid and c.omid=" + Omid + ")";
		qry += "\n ) order by colorname";
//System.out.println(qry);
		return DbHelperSQL.GetTable(qry);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from OMWARENOCOLOR");
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