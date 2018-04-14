package com.oracle.bean;

import java.sql.Types;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-03-30 22:46:35
//*************************************
public class Subitem implements java.io.Serializable {
	private Long Subid;
	private String Subname;
	private String Shortname;
	private Long Accid;
	private Integer Lx;
	private Integer Statetag;
	private Integer Imgindex;
	private Integer Noused;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setSubid(Long subid) {
		this.Subid = subid;
	}

	public Long getSubid() {
		return Subid;
	}

	public void setSubname(String subname) {
		this.Subname = subname;
	}

	public String getSubname() {
		return Subname;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setLx(Integer lx) {
		this.Lx = lx;
	}

	public Integer getLx() {
		return Lx;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getStatetag() {
		return Statetag;
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
		if (Subid == 0) {
			errmess = "subid不是一个有效的值!";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin");
		strSql.append("\n   select accid into v_count from SUBITEM where  subid=" + Subid + ";");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      v_retcs:='0系统约定项目，不允许删除!';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   select count(*) into  v_count from (select id from bookdetail where subid=" + Subid + " and rownum=1); ");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0当前记账项目已使用，不允许删除!';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   update SUBITEM set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("\n   where subid=" + Subid + " and accid=" + Accid + "; ");
		strSql.append("\n   delete from Bookhousepay where subid="+Subid+" and accid="+Accid+";");
		strSql.append("\n   v_retcs:='1操作成功';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
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

	// 增加记录
	public int Append() {
		if (Subname == null || Subname.length() <= 0) {
			errmess = "记账项目subname不是一个有效的值！";
			return 0;
		}
		if (Lx == null || Lx < 1 || Lx > 2) {
			errmess = "lx不是一个有效的值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("subid,");
		strSql2.append("v_subid,");
		// if (Subid != null) {
		// strSql1.append("subid,");
		// strSql2.append(Subid + ",");
		// }
		if (Subname != null) {
			strSql1.append("subname,");
			strSql2.append("'" + Subname + "',");
			Shortname = PinYin.getPinYinHeadChar(Subname);
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Lx != null) {
			strSql1.append("lx,");
			strSql2.append(Lx + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Imgindex != null) {
			strSql1.append("Imgindex,");
			strSql2.append(Imgindex + ",");
		}
		if (Noused != null) {
			strSql1.append("Noused,");
			strSql2.append(Noused + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_subid number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		// strSql.append("\n select accid into v_count from SUBITEM where subid=" + Subid + ";");
		// strSql.append("\n if v_count=0 then ");
		// strSql.append("\n v_retcs:='0系统约定项目，不允许修改!';");
		// strSql.append("\n goto exit;");
		// strSql.append("\n end if;");

		strSql.append("\n   select count(*) into v_count from subitem where (accid=0 or accid=" + Accid + ")");
		strSql.append("\n   and statetag=1 and subname='" + Subname + "';");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0记账项目已经存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		if (Lx == 1)// 收入类
		{
			strSql.append("\n   select count(*) into v_count from subitem where (accid=0 or accid=" + Accid + ")");
			strSql.append("\n   and statetag=1 and lx=1;");
			strSql.append("\n   if v_count>=50 then ");
			strSql.append("\n      v_retcs:='0收入类项目最多允许定义50种！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");

		} else if (Lx == 2)// 支出类
		{
			strSql.append("\n   select count(*) into v_count from subitem where (accid=0 or accid=" + Accid + ")");
			strSql.append("\n   and statetag=1 and lx=2;");
			strSql.append("\n   if v_count>=100 then ");
			strSql.append("\n      v_retcs:='0支出类项目最多允许定义100种！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");

		}

		strSql.append("\n   v_subid:=SUBITEM_subid.nextval; ");
		strSql.append("\n   insert into SUBITEM (");
		strSql.append("\n  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n  v_retcs:='1'||v_subid; ");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
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

	// 修改记录
	public int Update() {
		if (Subid == null || Subid == 0) {

			errmess = "subid不是一个有效的值！";
			return 0;
		}
		if (Subname != null && Subname.length() <= 0) {
			errmess = "记账项目subname不是一个有效的值！";
			return 0;

		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("   v_subid number; ");
		strSql.append("   v_count number; ");
		strSql.append("   v_retcs varchar2(200); ");
		strSql.append(" begin ");

		if (Subname != null) {
			strSql.append("   select count(*) into v_count from subitem where (accid=0 or accid=" + Accid + ")");
			strSql.append("   and statetag=1 and subname='" + Subname + "' and subid<>" + Subid + ";");
			strSql.append("   if v_count>0 then ");
			strSql.append("      v_retcs:='0记账项目已经存在！';");
			strSql.append("      goto exit;");
			strSql.append("   end if;");
		}
		strSql.append("   update SUBITEM set ");
		// if (Subid != null) {
		// strSql.append("subid=" + Subid + ",");
		// }
		if (Subname != null) {
			strSql.append("subname='" + Subname + "',");
			Shortname = PinYin.getPinYinHeadChar(Subname);
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
//		if (Lx != null) {
//			strSql.append("lx=" + Lx + ",");
//		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Imgindex != null) {
			strSql.append("Imgindex=" + Imgindex + ",");
		}
		if (Noused != null) {
			strSql.append("Noused=" + Noused + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where subid=" + Subid + " and accid=" + Accid + " ;");
		strSql.append("  v_retcs:='1操作成功！'; ");
		strSql.append("  <<exit>>");
		strSql.append("  select v_retcs into :retcs from dual;");
		strSql.append(" end;");
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
		strSql.append(" FROM SUBITEM ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM SUBITEM ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(String strWhere, String fieldlist, String sort) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM SUBITEM ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		if (sort.length() > 0) {
			strSql.append(" order by " + sort);
		}
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(strSql.toString());
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from SUBITEM");
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