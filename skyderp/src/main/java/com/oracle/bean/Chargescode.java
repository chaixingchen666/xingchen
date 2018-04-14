package com.oracle.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
import net.sf.json.JSONObject;

// *************************************
// @author:sunhong @date:2017-03-16 15:58:37
// *************************************
public class Chargescode implements java.io.Serializable {
	private Long Cgid;
	private Long Accid;
	private String Cgname;
	private Integer Noused;
	private Integer Statetag;
	private Integer Bj;
	private String Lastop;
	private Date Lastdate;
	private String Shortname;
	private String errmess;
	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setCgid(Long cgid) {
		this.Cgid = cgid;
	}

	public Long getCgid() {
		return Cgid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setCgname(String cgname) {
		this.Cgname = cgname;
	}

	public String getCgname() {
		return Cgname;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
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

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Cgid == 0) {
			errmess = "cgid参数无效";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num from (");
		strSql.append("select id from incomecost where cgid=" + Cgid + " and rownum=1 and accid=" + Accid);
		strSql.append("\n and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n union all");
		strSql.append("\n select id from paycost where cgid=" + Cgid + " and rownum=1 and accid=" + Accid);
		strSql.append("\n and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n union all");
		strSql.append("\n select id from housecost where cgid=" + Cgid + " and rownum=1 and accid=" + Accid);
		strSql.append("\n and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");

		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update chargescode set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where cgid=" + Cgid + " and accid=" + Accid);
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
		if (Cgname == null || Cgname.equals("")) {
			errmess = "费用名称不允许为空！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("cgid,");
		strSql2.append("v_id,");
		// if (Cgid != null) {
		// strSql1.append("cgid,");
		// strSql2.append(Cgid + ",");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Cgname != null) {
			strSql1.append("cgname,");
			strSql2.append("'" + Cgname + "',");
			Shortname = Func.strLeft(PinYin.getPinYinHeadChar(Cgname), 16);
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Bj != null) {
			strSql1.append("bj,");
			strSql2.append(Bj + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");

		if (Bj != null && Bj == 1) {
			strSql.append("\n   select count(*) into v_count from chargescode where accid=" + Accid + " and bj=1 and statetag=1;");
			strSql.append("\n   if v_count>=5 then ");
			strSql.append("\n      v_retcs:='0单据费用项目最多允许设置5个';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		strSql.append("\n   v_id:=chargescode_cgid.nextval; ");
		strSql.append("\n   insert into chargescode (");
		strSql.append("\n  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n  v_retcs:='1'||v_id;");
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

		// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// param.put("id", new ProdParam(Types.BIGINT));
		// int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// // id = Long.parseLong(param.get("id").getParamvalue().toString());
		// if (ret < 0) {
		// errmess = "操作异常！";
		// return 0;
		// } else {
		// Cgid = Long.parseLong(param.get("id").getParamvalue().toString());
		// errmess = Cgid.toString();
		// return 1;
		// }
	}

	// 修改记录
	public int Update() {
		if (Cgid == null || Cgid == 0) {
			errmess = "cgid参数无效";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");

		if (Bj != null && Bj == 1) {
			strSql.append("\n   select count(*) into v_count from chargescode where accid=" + Accid + " and bj=1 and statetag=1 and cgid<>" + Cgid + ";");
			strSql.append("\n   if v_count>=5 then ");
			strSql.append("\n      v_retcs:='0单据费用项目最多允许设置5个';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		strSql.append("\n   update chargescode set ");
		// if (Cgid != null) {
		// strSql.append("cgid=" + Cgid + ",");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Cgname != null) {
			strSql.append("cgname='" + Cgname + "',");
			Shortname = Func.strLeft(PinYin.getPinYinHeadChar(Cgname), 16);
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Bj != null) {
			strSql.append("bj=" + Bj + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where cgid=" + Cgid + " and accid=" + Accid + " ;");
		strSql.append("\n  commit;");
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
		strSql.append(" FROM chargescode ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM chargescode ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from chargescode");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select cgid,cgname,noused,statetag,bj,lastop,accid";
		qry += " from chargescode where accid=" + Accid;
		if (downdate.length() <= 0) {
			qry += " and statetag=1 and noused=0";
			headsql = " insert ";
		} else {
			qry += " and lastdate>=to_date('" + downdate + "','yyyy-mm-dd hh24:mi:ss')";
			isupdate = true; // 更改
			headsql = " replace ";
		}
		Table tb = new Table();
		// System.out.println(qry);
		tb = DbHelperSQL.GetTable(qry);

		if (!isupdate)

			sql.append("\n delete from chargescode where accid=" + Accid + ";");

		for (int i = 0; i < tb.getRowCount(); i++) {
			String sql1 = "";// "accid";
			String sql2 = "";// Accid.toString();
			for (int j = 0; j < tb.getColumnCount(); j++) {//
				// if (j > 0) {
				// sql1 += ",";
				// sql2 += ",";
				// }
				String fieldtype = tb.getMeta().get(j + 1).getType();

				String strKey = tb.getRow(i).getColumn(j + 1).getName();//
				String strValue = "";
				if (fieldtype.equals("NUMBER"))
					strValue = tb.getRow(i).get(j).toString();//
				else
					strValue = "'" + tb.getRow(i).get(j).toString() + "'";//
				sql1 += strKey + ",";
				sql2 += strValue + ",";

			}
			sql.append("\n " + headsql + " into chargescode (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
		}

		String path = Func.getRootPath();
		String filename = "down" + Func.desEncode(Accid + "_" + Func.DateToStr(new Date(), "yyyyMMddHHmmss")).toLowerCase() + Func.getRandomNumA(5) + ".sql";// "brand.sql";
		String savefile = path + "/temp/" + filename;
		PrintStream printStream;
		try {
			printStream = new PrintStream(new FileOutputStream(savefile));
			printStream.println(sql.toString());
			printStream.close();
			errmess = "/temp/" + filename;
			return 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errmess = "操作失败：" + e.getMessage();
			return 0;
		}

	}

}