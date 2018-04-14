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
//  @author:sunhong  @date:2017-03-18 21:23:54
//*************************************
public class Accwaretype implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private Long Typeid;
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

	public void setTypeid(Long typeid) {
		this.Typeid = typeid;
	}

	public Long getTypeid() {
		return Typeid;
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
		// strSql.append(" update Accwaretype set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Id != null) {
			strSql1.append("id,");
			strSql2.append("'" + Id + "',");
		}
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Typeid != null) {
			strSql1.append("typeid,");
			strSql2.append(Typeid + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Accwaretype_id.nextval; ");
		strSql.append("   insert into Accwaretype (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
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
		strSql.append("   update Accwaretype set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Typeid != null) {
			strSql.append("typeid=" + Typeid + ",");
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
		strSql.append(" FROM Accwaretype ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM waretype ");

		strSql.append("select " + fieldlist + ",'0' as selbj from waretype a where a.lastnode=0 and ");
		strSql.append(strWhere);
		strSql.append("\n union all");
		strSql.append("\n select " + fieldlist + ",'0' as selbj from waretype a where a.lastnode=1 and ");
		strSql.append(strWhere);
		strSql.append("\n and not exists (select 1 from accwaretype b where a.typeid=b.typeid and b.accid=" + Accid + ")");
		strSql.append("\n union all");
		strSql.append("\n select " + fieldlist + ",'1' as selbj from waretype a where a.lastnode=1 and ");
		strSql.append(strWhere);
		strSql.append("\n and exists (select 1 from accwaretype b where a.typeid=b.typeid and b.accid=" + Accid + ")");
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Accwaretype");

		return false;
	}

	// 写账户商品类型记录
	public int doAccwaretype(int value) {
		if (Typeid == 0) {
			errmess = "typeid参数无效！";
			return 0;
		}

		String qry = "declare  ";

		qry += "\n    v_retcs varchar2(200);";
		qry += "\n    v_accid number;";
		qry += "\n    v_typeid number;";
		qry += "\n    v_value  number;";
		qry += "\n    v_count number;";
		qry += "\n    v_ptypeid number;";
		qry += "\n begin";
		qry += "\n   v_accid:=" + Accid + ";  ";
		qry += "\n   v_typeid:=" + Typeid + ";  ";
		qry += "\n   v_value:=" + value + ";  ";
		qry += "\n   if v_value=0 then  ";// --设为未选中;";
		qry += "\n      select count(*) into v_count from warecode where typeid=" + Typeid + " and accid=" + Accid + " and noused=0 and statetag=1;";
		qry += "\n      if v_count>0 then ";
		qry += "\n         v_retcs:='0当前类型已使用，不允许取消选择！';";
		qry += "\n         goto exit;";
		qry += "\n      end if;";
		qry += "\n      delete from accwaretype where accid=" + Accid + " and typeid=" + Typeid + "";
		qry += "\n      and not exists (select 1 from warecode b where b.typeid=accwaretype.typeid and b.accid=" + Accid + " and b.noused=0 and b.statetag=1); ";
		qry += "\n   else";
		qry += "\n     select count(*) into v_count from accwaretype where accid=" + Accid + " and typeid=" + Typeid + ";";
		qry += "\n     if v_count=0 then  "; // --设为选中
		qry += "\n        insert into accwaretype (id,accid,typeid,lastdate) values (accwaretype_id.nextval, " + Accid + " ," + Typeid + ",sysdate);";
		qry += "\n     end if;";
		qry += "\n   end if;";

		// --处理上级类型的是否为选中
		qry += "\n   select p_typeid into v_ptypeid from waretype where typeid=" + Typeid + ";";
		qry += "\n   select count(*) into v_count from accwaretype a ";
		qry += "\n      join waretype b on a.typeid=b.typeid and a.accid=" + Accid + " ";
		qry += "\n      where b.p_typeid=v_ptypeid;";
		qry += "\n   if v_count=0 then   ";// --如果没有一个下级被 选中，则本级类型设为未选中,删除选中记录
		qry += "\n      delete from accwaretype where accid=" + Accid + " and typeid=v_ptypeid;";
		qry += "\n   else";// --如果有一个以上下级被选中，则本级类型设为选中
		qry += "\n      select count(*) into v_count from accwaretype where accid=" + Accid + " and typeid=v_ptypeid;";
		qry += "\n      if v_count=0 then";// --如果未找到选中记录，则插入选中记录
		qry += "\n         insert into accwaretype (id,accid,typeid,lastdate) values ( accwaretype_id.nextval," + Accid + ",v_ptypeid,sysdate);";
		qry += "\n      end if;";
		qry += "\n   end if;  ";
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		System.out.println(qry);
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

	// 成批写账户商品类型记录
	public int doAllAccwaretype(int value) {
		if (Typeid == 0) {
			errmess = "typeid参数无效！";
			return 0;
		}

		String qry = "declare  ";
		qry += "\n   v_accid  number;";
		qry += "\n   v_ptypeid  number;";
		qry += "\n   v_count  number;";
		qry += "\n begin ";
		qry += "\n   v_accid:=" + Accid + ";";
		qry += "\n   v_ptypeid:=" + Typeid + ";";
		if (value == 1) // 全选
		{
			qry += "\n   insert into accwaretype (id,accid,typeid,lastdate)";
			qry += "\n   select accwaretype_id.nextval," + Accid + ",a.typeid,sysdate from waretype a ";
			qry += "\n   where a.p_typeid=" + Typeid + " and a.statetag=1 ";
			qry += "\n   and not exists (select 1 from accwaretype b where b.accid=" + Accid + " and a.typeid=b.typeid);";

			qry += "\n   insert into accwaretype (id,accid,typeid,lastdate)";
			qry += "\n   select accwaretype_id.nextval," + Accid + ",a.typeid,sysdate from waretype a ";
			qry += "\n   where a.typeid=" + Typeid + " ";
			qry += "\n   and not exists (select 1 from accwaretype b where b.accid=" + Accid + " and a.typeid=b.typeid);";
		} else // 取消
		{
			qry += "\n   delete from accwaretype where accid=" + Accid + "  ";
			qry += "\n   and exists (select 1 from waretype a where a.typeid=accwaretype.typeid and a.p_typeid=" + Typeid + ")";
			qry += "\n   and not exists (select 1 from warecode b where b.typeid=accwaretype.typeid and b.accid=" + Accid + " and b.noused=0 and b.statetag=1); ";

			qry += "\n   select count(*) into v_count from accwaretype a ";
			qry += "\n      join waretype b on a.typeid=b.typeid and a.accid=" + Accid + " ";
			qry += "\n      where b.p_typeid=" + Typeid + ";";
			qry += "\n   if v_count=0 then   ";// --如果没有一个下级被 选中，则本级类型设为未选中,删除选中记录
			qry += "\n      delete from accwaretype where accid=" + Accid + " and typeid=" + Typeid + ";";
			qry += "\n   end if;  ";

		}
		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

}