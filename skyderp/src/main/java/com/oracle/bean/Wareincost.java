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
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-04-11 12:02:49
//*************************************
public class Wareincost implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private Long Cgid;
	private Float Curr;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setCgid(Long cgid) {
		this.Cgid = cgid;
	}

	public Long getCgid() {
		return Cgid;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Func.isNull(Noteno) || Func.isNull(Id)) {
			errmess = "noteno或id不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_totalcurr number; ");
		strSql.append("\n   v_totalcost number; ");
		strSql.append("\n   v_ntid number(1); ");
		strSql.append("\n begin ");
		strSql.append("\n  delete from wareincost ");
		strSql.append("\n  where id=" + Id + " and accid= " + Accid + " and noteno='" + Noteno + "';");
		// 汇总数量及金额
		strSql.append("\n  select nvl(sum(curr),0) into v_totalcost from wareincost where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("\n  select nvl(sum(curr),0) into v_totalcurr from wareinm where accid=" + Accid + " and noteno='" + Noteno + "';");
		// strSql.Append("\n v_totalcurr:=v_totalcurr+v_totalcost;");
		strSql.append("   select ntid into v_ntid from wareinh where accid=" + Accid + " and noteno='" + Noteno + "';");
		strSql.append("   if v_ntid=1 then ");
		strSql.append("      v_totalcurr:=v_totalcurr-v_totalcost;");
		strSql.append("   else");
		strSql.append("      v_totalcurr:=v_totalcurr+v_totalcost;");
		strSql.append("   end if;");

		strSql.append("\n  update wareinh set totalcurr=v_totalcurr,totalcost=v_totalcost where accid=" + Accid + " and noteno='" + Noteno + "';");
		// qry += " update wareouth set totalcost=v_totalcost,totalcurr=case ntid when 2 then v_totalcurr-v_totalcost else v_totalcurr+v_totalcost end where accid=" + accid + " and noteno='" + model.noteno + "';";

		strSql.append("\n  commit;");
		strSql.append("\n  select '\"msg\":\"操作成功\",\"TOTALCURR\":\"'||v_totalcurr||'\",\"TOTALCOST\":\"'||v_totalcost||'\"' into :retcs from dual;  ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		errmess = param.get("retcs").getParamvalue().toString();

		return 1;
	}

	// 增加记录
	public int Append() {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		if (Func.isNull(Cgid)) {
			errmess = "cgid不是一个有效值！";
			return 0;
		}
		if (Curr == null || Curr == 0) {
			errmess = "curr不是一个有效值！";
			return 0;
		}

		String qry = "declare ";
		qry += "   v_accid  number;";
		qry += "   v_totalcost  number;";
		qry += "   v_totalcurr  number;";
		qry += "   v_retcs varchar2(200);";
		qry += "   v_noteno nvarchar2(20);";
		qry += "   v_id  number;";
		qry += "   v_count  number;";
		qry += "   v_ntid  number(1);";
		qry += "   v_retbj number(1); ";
		qry += "begin ";
		qry += "   v_retbj:=0;";
		qry += "   select count(*) into v_count from chargescode where accid=" + Accid + " and statetag=1 and cgid=" + Cgid + ";";
		qry += "   if v_count=0 then ";
		qry += "      v_retcs:='0费用项目无效！';";
		qry += "      goto exit;";
		qry += "   end if;";

		qry += " begin ";
		qry += "   v_id:=wareincost_ID.NEXTVAL;";
		qry += "   insert into wareincost (id, accid,noteno,cgid,curr,lastdate)";
		qry += "   values (v_id," + Accid + ",'" + Noteno + "'," + Cgid + "," + Curr + ",sysdate);";
		qry += "   select nvl(sum(curr),0) into v_totalcost from wareincost where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "   select nvl(sum(curr),0) into v_totalcurr from wareinm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "   select ntid into v_ntid from wareinh where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "   if v_ntid=1 then ";
		qry += "      v_totalcurr:=v_totalcurr-v_totalcost;";
		qry += "   else";
		qry += "      v_totalcurr:=v_totalcurr+v_totalcost;";
		qry += "   end if;";
		qry += "   update wareinh set totalcost=v_totalcost,totalcurr=v_totalcurr where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "   commit;";
		// qry += " select '1','\"ID\":\"'||id||'\"' into :v_retbj,:v_notenolist from wareoutcost where id=v_id; ";
		qry += "   v_retbj:=1;  v_retcs:= '\"msg\":\"操作成功\",\"ID\":\"'||v_id||'\",\"TOTALCURR\":\"'||v_totalcurr||'\",\"TOTALCOST\":\"'||v_totalcost||'\"';  ";
		// qry += " insert into LOGRECORD (id,accid,progname,remark,lastop)";
		// qry += " values (LOGRECORD_id.nextval," + accid + ",'销售费用','【增加记录】单据号:'||v_noteno||';金额:" + model.curr + "','" + lastop + "');";
		qry += "   exception";
		qry += "   when others then ";
		qry += "   begin";
		qry += "     rollback;";
		qry += "     v_retcs:='\"msg\":\"操作失败，请重试！\"' ;";
		qry += "   end; ";
		qry += " end; ";
		qry += " <<exit>>";
		qry += " select v_retbj,v_retcs into :retbj,:retcs from dual; ";

		qry += "end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retbj", new ProdParam(Types.INTEGER));
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// int retbj = Integer.parseInt(param.get("retbj").getParamvalue().toString());
		errmess = param.get("retcs").getParamvalue().toString();
		return Integer.parseInt(param.get("retbj").getParamvalue().toString());
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update wareincost set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Noteno != null) {
			strSql.append("noteno='" + Noteno + "',");
		}
		if (Cgid != null) {
			strSql.append("cgid=" + Cgid + ",");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
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
		strSql.append(" FROM wareincost ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM wareincost a");
		strSql.append(" left outer join chargescode b on a.cgid=b.cgid");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setSumString("nvl(sum(curr),0) as totalcurr");
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from wareincost");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	public int Save(JSONObject jsonObject) {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		if (!jsonObject.has("costlist")) {
			errmess = "costlist不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n   v_totalcost number; ";
		qry += "\n   v_totalcurr number; ";
		qry += "\n begin ";
		qry += "\n   delete from wareincost where accid=" + Accid + " and noteno='" + Noteno + "';";
		JSONArray jsonArray = jsonObject.getJSONArray("costlist");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Cgid = Long.parseLong(jsonObject2.getString("cgid"));
			Curr = Float.parseFloat(jsonObject2.getString("curr"));
			if (Curr != 0) {
				qry += "\n   insert into wareincost (id,accid,noteno,cgid,curr)";
				qry += "\n   values (wareincost_id.nextval," + Accid + ",'" + Noteno + "'," + Cgid + "," + Curr + ");";
			}
		}
		qry += "\n   select nvl(sum(curr),0) into v_totalcost from  wareincost where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   select nvl(sum(curr),0) into v_totalcurr from  wareinm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update wareinh set totalcurr=v_totalcurr+v_totalcost,totalcost=v_totalcost where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n end;";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 获取分页数据 新
	public Table GetTable(QueryParam qp, Integer all) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select A.CGID,B.CGNAME,A.CURR");
		strSql.append("\n FROM wareincost a ");
		strSql.append("\n left outer join chargescode b on a.cgid=b.cgid");
		strSql.append("\n where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		if (all == 1) {
			strSql.append("\n union all");
			strSql.append("\n select b.CGID,b.CGNAME,0 as CURR");
			strSql.append("\n FROM chargescode b ");
			strSql.append("\n where b.accid=" + Accid + " and b.statetag=1 and b.bj=1");
			strSql.append("\n and not exists (select 1 from wareincost a where a.noteno='" + Noteno //
					+ "' and a.accid=" + Accid + " and a.cgid=b.cgid)");
		}
		qp.setQueryString(strSql.toString());
		qp.setSumString("nvl(sum(curr),0) as totalcurr");
		return DbHelperSQL.GetTable(qp);
	}

}