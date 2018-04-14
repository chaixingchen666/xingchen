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
//  @author:sunhong  @date:2017-03-16 09:40:50
//*************************************
public class Userrole implements java.io.Serializable {
	private Long Levelid;
	private String Levelname;
	private Long Accid;
	private Date Lastdate;
	private String Xxstr;
	private String Lastop;
	private Long Glevelid;
	// private String Levelname0;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setLevelid(Long levelid) {
		this.Levelid = levelid;
	}

	public Long getLevelid() {
		return Levelid;
	}

	public void setLevelname(String levelname) {
		this.Levelname = levelname;
	}

	public String getLevelname() {
		return Levelname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setXxstr(String xxstr) {
		this.Xxstr = xxstr;
	}

	public String getXxstr() {
		return Xxstr;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setGlevelid(Long glevelid) {
		this.Glevelid = glevelid;
	}

	public Long getGlevelid() {
		return Glevelid;
	}

	// public void setLevelname0(String levelname0) {
	// this.Levelname0 = levelname0;
	// }
	//
	// public String getLevelname0() {
	// return Levelname0;
	// }

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Levelid == 0) {
			errmess = "levelid无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_count number;");

		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin ");
		// strSql.append("\n select count(*) into v_count from (");
		strSql.append("\n    select count(*) into v_count from employe where levelid=" + Levelid + " and statetag=1 and rownum=1;");
		// strSql.append("\n );");
		strSql.append("\n    if v_count>0 then ");
		strSql.append("\n      v_retcs:='0当前角色已使用，不允许删除！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   delete from  userrole ");// set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("\n   where levelid=" + Levelid + " and accid=" + Accid + ";");
		strSql.append("\n   v_retcs:='1删除成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
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

	// 增加记录
	public int Append() {
		if (Levelname == null || Levelname.equals("")) {
			errmess = "角色名称未输入!";
			return 0;
		}

		if (Exists()) {
			return 0;
		}
		if (Glevelid == null || Glevelid <= 0 || Glevelid > 9) {
			errmess = "所属角色无效!";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("levelid,");
		strSql2.append("v_id,");
		// if (Levelname != null) {
		strSql1.append("levelname,");
		strSql2.append("'" + Levelname + "',");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Xxstr != null) {
			strSql1.append("xxstr,");
			strSql2.append("'" + Xxstr + "',");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		// if (Glevelid != null) {
		strSql1.append("glevelid,");
		strSql2.append(Glevelid + ",");
		// }
		// if (Levelname0 != null) {
		// strSql1.append("levelname0,");
		// strSql2.append("'" + Levelname0 + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_id from userrole where (accid=" + Accid + " or accid=0) and levelname='" + Levelname + "';");
		strSql.append("\n   if v_id>0 then ");
		strSql.append("\n      v_retcs:='0角色名称已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   v_id:=userrole_levelid.nextval; ");
		strSql.append("\n   insert into userrole (");
		strSql.append("\n  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n  v_retcs:='1操作成功！';");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual; ");
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
		//		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		//		param.put("id", new ProdParam(Types.BIGINT));
		//		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		//		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		//		if (ret < 0) {
		//			errmess = "操作异常！";
		//			return 0;
		//		} else {
		//			Levelid = Long.parseLong(param.get("id").getParamvalue().toString());
		//			errmess = Levelid.toString();
		//			return 1;
		//		}
	}

	// 修改记录
	public int Update() {
		if (Levelid == null || Levelid == 0) {
			errmess = "levelid无效！";
			return 0;
		}
		if (Levelname != null)
			if (Exists()) {
				return 0;
			}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_id from userrole where (accid=" + Accid + " or accid=0) and levelname='" + Levelname + "' and levelid<>" + Levelid + ";");
		strSql.append("\n   if v_id>0 then ");
		strSql.append("\n      v_retcs:='0角色名称已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   update userrole set ");
		// if (Levelid != null) {
		// strSql.append("levelid=" + Levelid + ",");
		// }
		if (Levelname != null) {
			strSql.append("levelname='" + Levelname + "',");
		}
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Xxstr != null) {
			strSql.append("xxstr='" + Xxstr + "',");
		}
		//		if (Lastop != null) {
		//		}
		if (Glevelid != null) {
			strSql.append("glevelid=" + Glevelid + ",");
		}
		// if (Levelname0 != null) {
		// strSql.append("levelname0='" + Levelname0 + "',");
		// }
		strSql.append("lastop='" + Lastop + "',");
		strSql.append("lastdate=sysdate");
		strSql.append("  where levelid=" + Levelid + " and accid=" + Accid + " ;");
		strSql.append("\n  v_retcs:='1操作成功！';");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual; ");
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

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(",a.glevelid,b.levelname as glevelname");
		strSql.append(" FROM userrole a");
		strSql.append(" left outer join userrole b on a.glevelid=b.levelid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM userrole a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据
	public Table GetTable(String strWhere, String fieldlist, String sort) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM userrole a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		if (!sort.equals("")) {
			strSql.append(" order by " + sort);
		}
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(strSql.toString());
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from userrole");
		strSql.append(" where Levelname='" + Levelname + "' and rownum=1 and accid=" + Accid);
		if (Levelid != null)
			strSql.append(" and Levelid<>" + Levelid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "角色:" + Levelname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

}