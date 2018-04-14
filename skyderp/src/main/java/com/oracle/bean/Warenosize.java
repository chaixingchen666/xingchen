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
//  @author:sunhong  @date:2017-04-19 11:53:55
//*************************************
public class Warenosize implements java.io.Serializable {
	private Long Wareid;
	private Long Sizeid;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setSizeid(Long sizeid) {
		this.Sizeid = sizeid;
	}

	public Long getSizeid() {
		return Sizeid;
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
		// strSql.append(" update Warenosize set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
		if (Wareid == 0) {
			errmess = "wareid不是一个有效值！";
			return 0;
		}
		if (!jsonObject.has("sizelist")) {
			errmess = "sizelist不是一个有效值！";
			return 0;
		}

		JSONArray jsonArray = jsonObject.getJSONArray("sizelist");
		if (jsonArray.size() == 0) {
			errmess = "sizelist记录无效！";
			return 0;
		}
		String qry = "declare ";
		qry += "    v_count  number(10); ";
		qry += "    v_retcs  varchar2(100); ";
		qry += " begin ";
		// 删除异常的尺码
		qry += "\n   delete from warenosize a  where a.wareid=" + Wareid;
		qry += "\n   and not exists (";
		qry += "\n   select c.sizeid from sizecode c join warecode b on c.accid=b.accid and c.groupno=b.sizegroupno ";
		qry += "\n   where c.statetag=1 and b.wareid=a.wareid);";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Sizeid = Long.parseLong(jsonObject2.getString("sizeid"));
			int value = Integer.parseInt(jsonObject2.getString("value"));

			if (value == 1) // 可用
			{
				qry += "\n   delete from warenosize where wareid=" + Wareid + " and sizeid=" + Sizeid + "; ";
			} else // 不可用
			{
				qry += "\n  if f_waresizeisused(" + Wareid + "," + Sizeid + ")=0 then ";// 尺码未被使用
				qry += "\n     select count(*) into v_count from warenosize where wareid=" + Wareid + " and sizeid=" + Sizeid + ";";
				qry += "\n     if v_count=0 then";
				qry += "\n        insert into warenosize (wareid,sizeid) values (" + Wareid + "," + Sizeid + "); ";
				qry += "\n     end if;";
				qry += "\n  end if; ";
			}
		}
		qry += "\n  select count(*) into v_count from sizecode a join warecode b on a.groupno=b.sizegroupno and a.accid=b.accid";
		qry += "\n  where b.wareid=" + Wareid + " and not exists (select 1 from warenosize c where a.sizeid=c.sizeid and b.wareid=c.wareid);";
		qry += "\n  if v_count=0 then";
		qry += "\n     rollback;";
		qry += "\n     v_retcs:= '0至少要选中一个尺码！' ; ";
		qry += "\n     goto exit;";
		qry += "\n  end if;";
		qry += "\n  commit; ";
		qry += "\n  v_retcs:= '1操作成功！'; ";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
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
		strSql.append("   update Warenosize set ");
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Sizeid != null) {
			strSql.append("sizeid=" + Sizeid + ",");
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
		strSql.append(" FROM Warenosize ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Warenosize ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(int maxsize) {
		String qry = "select sizeid,sizename,sizeno,selbj,isused from (";

		qry += "select a.sizeid,a.sizename,a.sizeno,1 as selbj,f_waresizeisused(" + Wareid + ",a.sizeid) as isused";
		qry += " from sizecode a";
		qry += " join warecode b on a.groupno=b.sizegroupno and a.accid=b.accid  ";
		qry += " where a.statetag=1 and a.noused=0 and b.wareid=" + Wareid;
		qry += " and not exists (select 1 from warenosize c where c.wareid=b.wareid and a.sizeid=c.sizeid)";
		qry += " union all";
		qry += " select a.sizeid,a.sizename,a.sizeno,0 as selbj,0 as isused";
		qry += " from sizecode a";
		qry += " join warecode b on a.groupno=b.sizegroupno  and a.accid=b.accid ";
		// qry += " left outer join warenosize b on b.wareid=b.wareid and a.sizeid=b.sizeid";
		qry += " where a.statetag=1 and a.noused=0 and b.wareid=" + Wareid;
		qry += " and exists (select 1 from warenosize c where c.wareid=b.wareid and a.sizeid=c.sizeid)";

		qry += ") where rownum<=" + maxsize + "  order by sizeno";
		return DbHelperSQL.GetTable(qry);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Warenosize");
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