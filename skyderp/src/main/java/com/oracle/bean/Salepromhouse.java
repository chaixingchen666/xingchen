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

//*************************************
//  @author:sunhong  @date:2017-11-15 21:31:43
//*************************************
public class Salepromhouse implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Noteno;
	private Long Houseid;
	private Date Lastdate;
	private String errmess;
	private Long Userid;

	//private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public void setUserid(Long userid) {
		this.Userid = userid;
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

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	//public void setAccbegindate(String accbegindate) {
	//	  this.Accbegindate = accbegindate;
	//}
	//删除记录 
	public int Delete() {
		if (Func.isNull(Noteno) || Houseid == 0) {
			errmess = "noteno或houseid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append(" delete SALEPROMhouse ");
		strSql.append(" where houseid=" + Houseid + " and noteno='" + Noteno + "' and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	//增加记录 
	public int Append() {
		if (Noteno.length() <= 0 || Houseid == 0) {
			errmess = "noteno或houseid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		//		if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		//		}
		//		if (Noteno != null) {
		strSql1.append("noteno,");
		strSql2.append("'" + Noteno + "',");
		//		}
		//		if (Houseid != null) {
		strSql1.append("houseid,");
		strSql2.append(Houseid + ",");
		//		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Houseid + " and statetag=1;");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0店铺无效！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   select count(*) into v_count from SALEPROMHOUSE where accid=" + Accid + " and noteno='" + Noteno + "' and houseid=" + Houseid + " ;");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0店铺已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   v_id:=SALEPROMhouse_id.nextval; ");

		strSql.append("\n   insert into SALEPROMHOUSE (" + strSql1.toString() + ")");
		strSql.append("\n   values (" + strSql2.toString() + ");");
		strSql.append("\n   v_retcs:='1'||v_id;");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual; ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	//修改记录 
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update SALEPROMhouse set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Noteno != null) {
			strSql.append("noteno='" + Noteno + "',");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
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

	//修改记录 
	public int Write(int value) {
		if (Noteno.length() <= 0 || Houseid == 0) {
			errmess = "noteno或houseid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		//		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_count number; ");
		strSql.append("begin ");
		if (value == 0) {
			strSql.append("\n   delete SALEPROMhouse where accid=" + Accid + " and noteno='" + Noteno + "' and houseid=" + Houseid + ";");
		} else {
			strSql.append("\n   select count(*) into v_count from SALEPROMHOUSE where accid=" + Accid + " and noteno='" + Noteno + "' and houseid=" + Houseid + " ;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      insert into SALEPROMHOUSE (id,accid,noteno,houseid)");
			strSql.append("\n      values (SALEPROMhouse_id.nextval," + Accid + ",'" + Noteno + "'," + Houseid + ");");
			strSql.append("\n   end if;");

		}
		//		strSql.append("   v_retcs:='1操作成功!';");
		//		strSql.append("   <<exit>>");
		//		strSql.append("   select v_retcs into :retcs from dual;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
		//		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		//		param.put("retcs", new ProdParam(Types.VARCHAR));
		//		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		//		if (ret < 0) {
		//			errmess = "操作异常！";
		//			return 0;
		//		}
		//		String retcs = param.get("retcs").getParamvalue().toString();
		//
		//		errmess = retcs.substring(1);
		//		return Integer.parseInt(retcs.substring(0, 1));
	}

	//全选，清除
	public int WriteAll(int value, int qxbj) {
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		//		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n begin ");
		strSql.append("\n   delete SALEPROMhouse where accid=" + Accid + " and noteno='" + Noteno + "';");
		if (value == 1) {
			strSql.append("\n   insert into SALEPROMHOUSE (id,accid,noteno,houseid)");
			strSql.append("\n   select SALEPROMhouse_id.nextval," + Accid + ",'" + Noteno + "',houseid ");
			strSql.append("\n   from warehouse a where accid=" + Accid + " and statetag=1 and noused=0");
			if (qxbj == 1)
				strSql.append("\n  and exists (select 1 from employehouse x where a.houseid=x.houseid and x.epid=" + Userid + ")");

			strSql.append("\n  ;");
		}
		//		strSql.append("   v_retcs:='1操作成功!';");
		//		strSql.append("   <<exit>>");
		//		strSql.append("   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
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
		strSql.append(" FROM SALEPROMhouse ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM SALEPROMhouse a ");
		strSql.append("\n left outer join warehouse b on a.houseid=b.houseid");
		if (strWhere.length() > 0) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, int qxbj) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select a.houseid,a.housename,decode(b.id,null,0,1) as selbj");
		strSql.append("\n from warehouse a ");
		strSql.append("\n left outer join SALEPROMhouse b on a.houseid=b.houseid and b.noteno='" + Noteno + "'");
		strSql.append("\n where a.accid=" + Accid + " and a.statetag=1 and a.noused=0");
		if (qxbj == 1)
			strSql.append("\n  and exists (select 1 from employehouse x where a.houseid=x.houseid and x.epid=" + Userid + ")");
		//		strSql.append("\n order by a.houename");
		//		strSql.append("select a.houseid,b.housename,1 as selbj");
		//		strSql.append("\n FROM SALEPROMhouse a ");
		//		strSql.append("\n left outer join warehouse b on a.houseid=b.houseid");
		//		strSql.append("\n where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
		//		strSql.append("\n union all");
		//		strSql.append("\n select a.houseid,a.housename,0 as selbj");
		//		strSql.append("\n FROM warehouse a ");
		//		strSql.append("\n where a.accid=" + Accid );
		//		strSql.append("\n and not exists (select 1 from SALEPROMhouse

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from SALEPROMhouse");
		//	strSql.append(" where brandname='" + Brandname + "' and statetag=1  and rownum=1 and accid=" + Accid);
		//  	if (Brandid != null)
		//  		strSql.append(" and brandid<>" + Brandid);
		//  	if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		//  		errmess = "???:" + Brandname + " 已存在，请重新输入！";
		//  		return true;
		//  	}
		return false;
	}
}