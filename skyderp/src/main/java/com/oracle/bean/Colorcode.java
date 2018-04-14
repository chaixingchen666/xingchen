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
// @author:sunhong @date:2017-03-26 10:36:03
// *************************************
public class Colorcode implements java.io.Serializable {
	private Long Colorid;
	private String Colorname;
	private String Colorno;
	private Long Accid;
	private Integer Lyfs;
	private Long Accid1;
	private Long Colorid1;
	private Long Brandid;
	private Integer Noused;
	private Integer Statetag;
	private String Lastop;
	private Date Lastdate;
	private String Shortname;
	private Integer Usedbj;
	private String errmess;
	private Long Wareid;
	private String Brandname;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setColorid(Long colorid) {
		this.Colorid = colorid;
	}

	public Long getColorid() {
		return Colorid;
	}

	public void setColorname(String colorname) {
		this.Colorname = colorname;
	}

	public String getColorname() {
		return Colorname;
	}

	public void setColorno(String colorno) {
		this.Colorno = colorno;
	}

	public String getColorno() {
		return Colorno;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setLyfs(Integer lyfs) {
		this.Lyfs = lyfs;
	}

	public Integer getLyfs() {
		return Lyfs;
	}

	public void setAccid1(Long accid1) {
		this.Accid1 = accid1;
	}

	public Long getAccid1() {
		return Accid1;
	}

	public void setColorid1(Long colorid1) {
		this.Colorid1 = colorid1;
	}

	public Long getColorid1() {
		return Colorid1;
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

	public void setUsedbj(Integer usedbj) {
		this.Usedbj = usedbj;
	}

	public Integer getUsedbj() {
		return Usedbj;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Colorid == 0) {
			errmess = "colorid无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and
		// rownum=1 and statetag=1");
		// strSql.append(")");
		strSql.append("  select f_colorisused(" + Colorid + ",usedbj) as v_usedbj from colorcode where colorid=" + Colorid);
		if (Integer.parseInt(DbHelperSQL.RunSql(strSql.toString()).toString()) == 1) {
			errmess = "当前颜色已使用，不允许删除";
			return 0;
		}

		strSql.setLength(0);
		strSql.append(" begin");
		strSql.append("   delete from warecolor where colorid=" + Colorid + ";");
		strSql.append("   delete from warebarcode where accid=" + Accid + " and colorid=" + Colorid + ";");
		strSql.append("   update Colorcode set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("   where colorid=" + Colorid + " and accid=" + Accid + ";");
		strSql.append(" end;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0)

		{
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append() {
		if (Colorname == null || Colorname.equals("")) {
			errmess = "颜色名称不允许为空！";
			return 0;
		}
		if (Colorno == null || Colorno.equals("")) {
			errmess = "颜色代码不允许为空！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("colorid,");
		strSql2.append("v_id,");
		// if (Colorid != null) {
		// strSql1.append("colorid,");
		// strSql2.append(Colorid + ",");
		// }
		// if (Colorname != null) {
		strSql1.append("colorname,");
		strSql2.append("'" + Colorname + "',");
		Shortname = PinYin.getPinYinHeadChar(Colorname);
		// }
		if (Colorno != null) {
			strSql1.append("colorno,");
			strSql2.append("'" + Colorno.toUpperCase() + "',");
		}
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Lyfs != null) {
			strSql1.append("lyfs,");
			strSql2.append(Lyfs + ",");
		}
		if (Brandid != null) {
			strSql1.append("Brandid,");
			strSql2.append(Brandid + ",");
		}
		if (Accid1 != null) {
			strSql1.append("accid1,");
			strSql2.append(Accid1 + ",");
		}
		if (Colorid1 != null) {
			strSql1.append("colorid1,");
			strSql2.append(Colorid1 + ",");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		if (Usedbj != null) {
			strSql1.append("usedbj,");
			strSql2.append(Usedbj + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append("   v_count number; ");
		strSql.append("   v_retcs varchar2(200); ");
		strSql.append(" begin ");

		if (Brandid != null && Brandid > 0) {
			strSql.append("   select count(*) into v_count  from brand");
			strSql.append("   where brandid=" + Brandid + " and statetag=1 and accid=" + Accid + ";");
			strSql.append("   if v_count=0 then");
			strSql.append("      v_retcs:='0品牌不存在，请重新输入！';");
			strSql.append("      goto exit;");
			strSql.append("   end if;");
		}

		strSql.append("   select count(*) into v_count  from colorcode");
		strSql.append("   where colorname='" + Colorname + "' and statetag=1 and accid=" + Accid);
		if (Brandid != null && Brandid > 0) {
			strSql.append("  and brandid=" + Brandid);
		} else
			strSql.append("  and brandid=0");
		strSql.append("   and accid1=0 and rownum=1;");
		strSql.append("   if v_count>0 then");
		strSql.append("      v_retcs:='0颜色已存在，请重新输入！';");
		strSql.append("      goto exit;");
		strSql.append("   end if;");
		strSql.append("   v_id:=Colorcode_colorid.nextval; ");
		strSql.append("   insert into Colorcode (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		if (Wareid != null && Wareid > 0) {
			strSql.append("\n   insert into warecolor (id,accid,wareid,colorid,lastop)");
			strSql.append("\n   values (warecolor_id.nextval," + Accid + "," + Wareid + ",v_id,'" + Lastop + "');");
		}
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
		// errmess = "操作成功！";
		// return 1;
		// }
	}

	// 修改记录
	public int Update() {
		if (Colorid == null || Colorid == 0) {
			errmess = "colorid无效！";
			return 0;
		}
		if (Colorname != null && Colorname.length() <= 0) {
			errmess = "颜色名称不允许为空！";
			return 0;
		}
		if (Colorno != null && Colorno.length() <= 0) {
			errmess = "颜色代码不允许为空！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("   v_count number; ");
		strSql.append("   v_retcs varchar2(200); ");
		strSql.append("begin ");
		if (Brandid != null && Brandid > 0) {
			strSql.append("   select count(*) into v_count  from brand");
			strSql.append("   where brandid=" + Brandid + " and statetag=1 and accid=" + Accid + ";");
			strSql.append("   if v_count=0 then");
			strSql.append("      v_retcs:='0品牌不存在，请重新输入！';");
			strSql.append("      goto exit;");
			strSql.append("   end if;");

		}
		if (Colorname != null) {
			strSql.append("   select count(*) into v_count  from colorcode");
			strSql.append("   where colorname='" + Colorname + "' and colorid<>" + Colorid + " and statetag=1 and accid=" + Accid);
			if (Brandid != null && Brandid > 0) {
				strSql.append("  and brandid=" + Brandid);
			} else
				strSql.append("  and brandid=0");
			strSql.append("   and accid1=0 and rownum=1;");
			strSql.append("   if v_count>0 then");
			strSql.append("      v_retcs:='0颜色已存在，请重新输入！';");
			strSql.append("      goto exit;");
			strSql.append("   end if;");
			Shortname = PinYin.getPinYinHeadChar(Colorname);
		}

		strSql.append("   update Colorcode set ");
		// if (Colorid != null) {
		// strSql.append("colorid=" + Colorid + ",");
		// }
		if (Colorname != null) {
			strSql.append("colorname='" + Colorname + "',");
			Shortname = PinYin.getPinYinHeadChar(Colorname);
		}
		if (Colorno != null) {
			strSql.append("colorno='" + Colorno.toUpperCase() + "',");
		}
		if (Brandid != null) {
			strSql.append("Brandid=" + Brandid + ",");
		}
		if (Lyfs != null) {
			strSql.append("lyfs=" + Lyfs + ",");
		}
		if (Accid1 != null) {
			strSql.append("accid1=" + Accid1 + ",");
		}
		if (Colorid1 != null) {
			strSql.append("colorid1=" + Colorid1 + ",");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Usedbj != null) {
			strSql.append("usedbj=" + Usedbj + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where colorid=" + Colorid + " and accid=" + Accid + " ;");
		strSql.append("  v_retcs:='1操作成功！';");
		strSql.append("  <<exit>>");
		strSql.append("  select v_retcs into :retcs from dual; ");
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
		strSql.append(" FROM Colorcode a");
		strSql.append(" left outer join brand b on a.brandid=b.brandid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist, long wareid) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Colorcode a");
		strSql.append(" left outer join brand b on a.brandid=b.brandid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
			if (wareid > 0) {
				strSql.append(" and not exists (select 1 from warecolor c where a.colorid=c.colorid and c.wareid=" + wareid + ")");
			}
		} else if (wareid > 0) {
			strSql.append(" where not exists (select 1 from warecolor c where a.colorid=c.colorid and c.wareid=" + wareid + ")");
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Colorcode");
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

	// 从excel导入颜色
	public int LoadFromXLS() {
		if (Colorid == null)
			Colorid = (long) 0;
		// DataTable dt = new DataTable();

		String qry = "declare ";// "select wareid from warecode where accid=" +
								// accid + " and wareno='" + wareno + "' and
								// rownum=1";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_count number;";
		qry += "\n   v_brandid number(10);";
		qry += "\n begin";

		if (Colorid > 0) // 货号已存在,更新商品资料
		{
			//
			qry += "\n  select count(*) into v_count from colorcode where colorid=" + Colorid + " and statetag=1 and accid=" + Accid + ";";
			qry += "\n  if v_count=0 then";
			qry += "\n     v_retcs:='0颜色id无效！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";
			if (Brandname != null) {
				if (Brandname.equals("") || Brandname.equals("无")) {
					qry += "  v_brandid:=0;";
				} else {
					qry += "\n  begin";
					qry += "\n     select brandid into v_brandid from brand where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid + ";";
					qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
					qry += "\n     v_retcs:='0品牌" + Brandname + " 未找到！';";
					qry += "\n     goto exit;";
					qry += "\n  end;";
				}
			}

			if (Colorname != null && !Colorname.equals("")) {
				qry += "\n  select count(*) into v_count from colorcode where colorname='" + Colorname + "' and brandid=v_brandid and colorid<>" + Colorid + " and statetag=1 and rownum=1 and accid=" + Accid + ";";

				qry += "\n  if v_count>0 then";
				qry += "\n     v_retcs:='0颜色名称" + Colorname + " 已存在！';";
				qry += "\n     goto exit;";
				qry += "\n  end if;";
			}
			qry += "\n  update colorcode set lastdate=sysdate,lastop='" + Lastop + "'";
			if (Colorname != null && !Colorname.equals("")) {
				qry += ",Colorname='" + Colorname + "'";
			}

			if (Colorno != null && !Colorno.equals("")) {
				qry += ",colorno='" + Colorno + "'";
			}
			if (Brandname != null) {
				qry += ",brandid=v_brandid";
			}
			if (Noused != null) {
				qry += ",Noused=" + Noused;
			}
			qry += " where colorid=" + Colorid + " and accid=" + Accid + ";";

		} else {
			String sqlinsert1 = "lastdate,statetag,accid,lastop";
			String sqlinsert2 = "sysdate,1," + Accid + ",'" + Lastop + "'";
			sqlinsert1 += ",colorid";
			sqlinsert2 += ",colorcode_colorid.nextval";
			if (Brandname != null) {
				if (Brandname.equals("")) {
					qry += "  v_brandid:=0;";
				} else {
					qry += "\n  begin";
					qry += "\n     select brandid into v_brandid from brand where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid + ";";
					qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
					qry += "\n     v_retcs:='0品牌" + Brandname + " 未找到！';";
					qry += "\n     goto exit;";
					qry += "\n  end;";
				}
				sqlinsert1 += ",brandid";
				sqlinsert2 += ",v_brandid";
			}

			if (Colorname == null || Colorname.length() <= 0) {
				errmess = "颜色名称不允许为空！";
				return 0;
			}
			qry += "\n  select count(*) into v_count from colorcode where colorname='" + Colorname + "' and brandid=v_brandid and rownum=1 and statetag=1 and accid=" + Accid + ";";
			qry += "\n  if v_count>0 then";
			qry += "\n     v_retcs:='0颜色名称" + Colorname + " 已存在！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";

			sqlinsert1 += ",colorname";
			sqlinsert2 += ",'" + Colorname + "'";
			Shortname = PinYin.getPinYinHeadChar(Colorname);
			sqlinsert1 += ",shortname";
			sqlinsert2 += ",'" + Shortname + "'";
			// }
			if (Colorno != null && !Colorno.equals("")) {
				sqlinsert1 += ",colorno";
				sqlinsert2 += ",'" + Colorno + "'";
			}
			if (Noused != null) {
				sqlinsert1 += ",Noused";
				sqlinsert2 += "," + Noused;
			}

			// {
			qry += "\n  insert into colorcode (" + sqlinsert1 + ") \n values (" + sqlinsert2 + ") ;";
			// }
		}
		qry += "\n  v_retcs:='1操作成功';";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end;";
		// System.out.println(qry);
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

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select colorid,colorname,colorno,statetag,noused,lastop,accid";
		qry += " from colorcode where accid=" + Accid;
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

			sql.append("\n delete from colorcode where accid=" + Accid + ";");

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
			sql.append("\n " + headsql + " into colorcode (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
		}

		String path = Func.getRootPath();
		String filename = "down" + Func.desEncode(Accid + "_" + Func.DateToStr(new Date(), "yyyyMMddHHmmss")).toLowerCase() + ".sql";// "brand.sql";
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