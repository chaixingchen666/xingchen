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
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-04-27 15:13:26
//*************************************
public class Area implements java.io.Serializable {
	private Long Areaid;
	private String Areaname;
	private Long Accid;
	private Date Lastdate;
	private String Lastop;
	private Integer Statetag;
	private Integer Noused;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setAreaid(Long areaid) {
		this.Areaid = areaid;
	}

	public Long getAreaid() {
		return Areaid;
	}

	public void setAreaname(String areaname) {
		this.Areaname = areaname;
	}

	public String getAreaname() {
		return Areaname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}


	// 删除记录
	public int Delete() {
		if (Areaid == 0) {
			errmess = "areaid无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("select count(*) as num from (");
		strSql.append("select wareid from warecode where Areaid=" + Areaid + " and rownum=1 and statetag=1");
		strSql.append(")");

		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前区位已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update area set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where Areaid=" + Areaid + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append() {
		if (Func.isNull(Areaname)) {
			errmess = "区域名称不允许为空！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("areaid,");
		strSql2.append("v_id,");
		// if (Areaname != null) {
		strSql1.append("areaname,");
		strSql2.append("'" + Areaname + "',");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from area where accid=" + Accid + " and areaname='" + Areaname + "' and statetag=1;");
		strSql.append("\n   if v_count>0 then");
		strSql.append("\n      v_retcs:='0区位已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		
		strSql.append("\n   v_id:=area_areaid.nextval; ");
		strSql.append("\n   insert into area (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
//		strSql.append("\n  select v_id into :id from dual; ");
		strSql.append("\n  v_retcs:='1'||v_id;");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
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

	// 修改记录
	public int Update() {
		if (Areaname != null) {
			if (Areaname.equals("")) {
				errmess = "区域名称不允许为空！";
				return 0;
			}
			if (Exists())
				return 0;// 判断品牌名称是否已存在
		}

		StringBuilder strSql = new StringBuilder();
		
		strSql.append("declare");
		strSql.append("\n  v_count number;");
		strSql.append("\n  v_retcs varchar2(200);");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from area where accid=" + Accid + " and areaname='" + Areaname + "' and areaid<>" + Areaid + " and statetag=1;");
		strSql.append("\n   if v_count>0 then");
		strSql.append("\n      v_retcs:='0区位已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   update area set ");
		// if (Areaid != null) {
		// strSql.append("areaid=" + Areaid + ",");
		// }
		if (Areaname != null) {
			strSql.append("areaname='" + Areaname.toUpperCase() + "',");
		}
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where accid=" + Accid + " and areaid=" + Areaid + " ;");
		strSql.append("\n  v_retcs:='1操作成功！';");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
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
		strSql.append(" FROM area a");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM area a");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from area");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}


	// 从excel导入
	public int doLoadfromExcel() {
		if (Areaid == null)
			Areaid = (long) 0;
		// string wareno=ht["wareno"].ToString().ToUpper();
		// Table tb = new Table();

		String qry = "declare";// "select wareid from warecode where accid=" + accid + " and wareno='" + wareno + "' and rownum=1";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_count number;";
		qry += "\n begin";
		if (Areaid > 0) // 货号已存在,更新商品资料
		{
			qry += "\n  select count(*) into v_count from area where Areaid=" + Areaid + " and accid=" + Accid + ";";
			qry += "\n  if v_count=0 then";
			qry += "\n     v_retcs:='0区位id无效！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";

			qry += "\n  update area set lastdate=sysdate,lastop='" + Lastop + "'";
			if (Areaname != null && Areaname.length() > 0) {
				qry += ",Areaname='" + Areaname + "'";
//				Shortname = PinYin.getPinYinHeadChar(Areaname);
//				qry += ",shortname='" + Shortname + "'";

			}
			qry += " where areaid=" + Areaid + " and accid=" + Accid + ";";

		} else {
			String sqlinsert1 = "lastdate,noused,statetag,accid,lastop";
			String sqlinsert2 = "sysdate,0,1," + Accid + ",'" + Lastop + "'";
			sqlinsert1 += ",Areaid";
			sqlinsert2 += ",area_Areaid.nextval";
			// long brandid = 0;
			// string qry1 = "";

			if (Areaname != null && Areaname.length() > 0) {
				sqlinsert1 += ",Areaname";
				sqlinsert2 += ",'" + Areaname + "'";
//				Shortname = PinYin.getPinYinHeadChar(Areaname);
//				sqlinsert1 += ",shortname";
//				sqlinsert2 += ",'" + Shortname + "'";
			}
			qry += "\n  insert into area (" + sqlinsert1 + ")  \n values (" + sqlinsert2 + ") ;";
		}
		qry += "\n  v_retcs:='1操作成功';";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end;";

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


}