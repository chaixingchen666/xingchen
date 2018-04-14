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
//  @author:sunhong  @date:2017-03-13 23:04:39
//*************************************
public class Online2type implements java.io.Serializable {
	private Long Onhouseid;
	private Long Typeid;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setTypeid(Long typeid) {
		this.Typeid = typeid;
	}

	public Long getTypeid() {
		return Typeid;
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
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		// strSql.append(" update ONLINE2TYPE set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		return 1;
	}

	// 增加记录
	public int Append(JSONObject jsonObject) {

//		System.out.println("111111");
		if (Onhouseid == null && Onhouseid == 0) {
			errmess = "线上店铺参数无效";
			return 0;
		}
		if (!jsonObject.has("typelist")) {
			errmess = "未传入线上店铺的经营类型参数！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n begin ");
		// "typelist":[{"typeid":102,"value":1},{"typeid":101,"value":1},{"typeid":105,"value":0}]
		JSONArray jsonArray = jsonObject.getJSONArray("typelist");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long typeid = Long.parseLong(jsonObject2.getString("typeid"));
			int value = Integer.parseInt(jsonObject2.getString("value"));
			if (value == 0) {
				strSql.append("\n  delete from ONLINE2TYPE where ONHOUSEID=" + Onhouseid + " and TYPEID=" + typeid + ";");
			} else {
				strSql.append("\n   select count(*) into v_count from ONLINE2TYPE where ONHOUSEID=" + Onhouseid + " and TYPEID=" + typeid + ";");
				strSql.append("\n   if v_count=0 then ");
				strSql.append("\n      insert into ONLINE2TYPE (ONHOUSEID,TYPEID,LASTOP)");
				strSql.append("\n      values (" + Onhouseid + "," + typeid + ",'" + Lastop + "');");
				strSql.append("\n   end if;");
			}
		}

		strSql.append("\n   select count(*) into :count from ONLINE2TYPE where onhouseid=" + Onhouseid + ";");
		strSql.append("\n end;");
//		System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("count", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = param.get("count").getParamvalue().toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update ONLINE2TYPE set ");
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Typeid != null) {
			strSql.append("typeid=" + Typeid + ",");
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
		strSql.append(" FROM ONLINE2TYPE ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM ONLINE2TYPE ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from ONLINE2TYPE");
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