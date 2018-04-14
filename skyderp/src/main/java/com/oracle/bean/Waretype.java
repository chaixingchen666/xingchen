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

//*************************************
//  @author:sunhong  @date:2017-03-13 23:00:48
//*************************************
public class Waretype implements java.io.Serializable {
	private Long Typeid;
	private String Typename;
	private String Fullname;
	private Integer Noused;
	private Long P_typeid;
	private Long G_typeid;
	private Integer Lastnode;
	private Integer Selected;
	private Date Lastdate;
	private String Shortname;
	private String Typeno;
	private Long Accid;
	private Integer Statetag;
	private Long Ordid;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setTypeid(Long typeid) {
		this.Typeid = typeid;
	}

	public Long getTypeid() {
		return Typeid;
	}

	public void setTypename(String typename) {
		this.Typename = typename;
	}

	public String getTypename() {
		return Typename;
	}

	public void setFullname(String fullname) {
		this.Fullname = fullname;
	}

	public String getFullname() {
		return Fullname;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setP_typeid(Long p_typeid) {
		this.P_typeid = p_typeid;
	}


	public void setG_typeid(Long g_typeid) {
		this.G_typeid = g_typeid;
	}

	public Long getP_typeid() {
		return P_typeid;
	}

	public void setLastnode(Integer lastnode) {
		this.Lastnode = lastnode;
	}

	public Integer getLastnode() {
		return Lastnode;
	}

	public void setSelected(Integer selected) {
		this.Selected = selected;
	}

	public Integer getSelected() {
		return Selected;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setTypeno(String typeno) {
		this.Typeno = typeno;
	}

	public String getTypeno() {
		return Typeno;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setOrdid(Long ordid) {
		this.Ordid = ordid;
	}

	public Long getOrdid() {
		return Ordid;
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
		strSql.append("select count(*) from (");
		strSql.append("select typeid from warecode where typeid=" + Typeid + " and accid=" + Accid + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update Waretype set statetag=2,lastdate=sysdate");
		strSql.append(" where typeid=" + Typeid + " and accid=" + Accid);
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
		if (Typename == null || Typename.equals("")) {
			errmess = "类型名称不能为空!";
			return 0;
		}
		if (Exists())
			return 0;
		if (P_typeid == null || P_typeid == 0) {
			errmess = "上级类型无效！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		// strSql1.append("id,");
		// strSql2.append("v_id,");
		// if (Typeid != null) {
		strSql1.append("typeid,");
		strSql2.append("v_typeid,");
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		strSql1.append("p_typeid,");
		strSql2.append(P_typeid + ",");
		if (Typename != null) {
			strSql1.append("typename,");
			strSql2.append("'" + Typename + "',");
			Shortname = PinYin.getPinYinHeadChar(Typename);
			strSql1.append("fullname,");
			strSql2.append("v_fullname,");
		}
		// if (Fullname != null) {
		// }
		// if (Noused != null) {
		strSql1.append("noused,");
		strSql2.append("0,");
		// }
		// if (P_typeid != null) {
		// }
		// if (Lastnode != null) {
		strSql1.append("lastnode,");
		strSql2.append("1,");
		// }
		// if (Selected != null) {
		strSql1.append("selected,");
		strSql2.append("1,");
		// }
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		if (Typeno != null) {
			strSql1.append("typeno,");
			strSql2.append("'" + Typeno + "',");
		}

		if (G_typeid != null) {
			strSql1.append("g_typeid,");
			strSql2.append( G_typeid + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		// if (Ordid != null) {
		strSql1.append("ordid,");
		strSql2.append("9999,");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_typeid number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_fullname varchar2(100); ");
		strSql.append("\n begin ");
		strSql.append("\n   select typename||'-'||'" + Typename + "' into v_fullname from waretype where typeid=" + P_typeid + "; ");

		strSql.append("\n   v_typeid:=Waretype_typeid.nextval; ");
		strSql.append("\n   insert into Waretype (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n   insert into accwaretype (id,accid,typeid,lastdate) values (accwaretype_id.nextval, " + Accid + " ,v_typeid,sysdate);");
		strSql.append("\n   select count(*) into v_count from accwaretype where accid=" + Accid + " and typeid=" + P_typeid + ";");
		strSql.append("\n   if v_count=0 then");// --如果未找到选中记录，则插入选中记录
		strSql.append("\n      insert into accwaretype (id,accid,typeid,lastdate) values ( accwaretype_id.nextval," + Accid + "," + P_typeid + ",sysdate);");
		strSql.append("\n   end if;");

		strSql.append("\n   select v_typeid into :id from dual; ");
		strSql.append("\n end;");
		// System.out.print(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Typeid = Long.parseLong(param.get("id").getParamvalue().toString());
			errmess = Typeid.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		if (Typename != null) {
			if (Exists())
				return 0;
			Shortname = PinYin.getPinYinHeadChar(Typename);
		}
		if (P_typeid == null || P_typeid == 0) {
			errmess = "上级类型无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("   v_fullname varchar2(200); ");
		strSql.append("\n begin ");
		if (Typename != null) {
			strSql.append("\n   select typename||'-'||'" + Typename + "' into v_fullname from waretype where typeid=" + P_typeid + "; ");
		}
		strSql.append("\n   update Waretype set ");
		if (Typename != null) {
			strSql.append("  typename='" + Typename + "',");
			strSql.append("   fullname=v_fullname,");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		// if (P_typeid != null) {
		// strSql.append("p_typeid=" + P_typeid + ",");
		// }
		// if (Lastnode != null) {
		// strSql.append("lastnode=" + Lastnode + ",");
		// }
		// if (Selected != null) {
		// strSql.append("selected=" + Selected + ",");
		// }
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		// if (Typeno != null) {
		// strSql.append("typeno='" + Typeno + "',");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		// if (Ordid != null) {
		// strSql.append("ordid=" + Ordid + ",");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where typeid=" + Typeid + " and accid=" + Accid + " ;");
		strSql.append("\n  commit;");
		strSql.append("\n end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常!";
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
		strSql.append(" FROM Waretype ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM Waretype ");
		// if (!strWhere.equals("")) {
		// strSql.append(" where " + strWhere);
		// }

		strSql.append("select " + fieldlist + ",'0' as selbj from waretype a where a.lastnode=0 ");
		strSql.append("\n and " + strWhere);
		strSql.append("\n union all");
		strSql.append("\n select " + fieldlist + ",'0' as selbj from waretype a where a.lastnode=1 ");
		strSql.append("\n and " + strWhere);
		strSql.append("\n and not exists (select 1 from accwaretype b where a.typeid=b.typeid and b.accid=" + Accid + ")");
		strSql.append("\n union all");
		strSql.append("\n select " + fieldlist + ",'1' as selbj from waretype a where a.lastnode=1 ");
		strSql.append("\n and " + strWhere);
		strSql.append("\n and exists (select 1 from accwaretype b where a.typeid=b.typeid and b.accid=" + Accid + ")");

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM Waretype ");
		// if (!strWhere.equals("")) {
		// strSql.append(" where " + strWhere);
		// }
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();

		strSql.append("select typeid  from waretype");
		strSql.append(" where typename='" + Typename + "' and statetag=1 and (accid=" + Accid + " or accid=0) and p_typeid=" + P_typeid + " and rownum=1");
		if (Typeid != null)
			strSql.append(" and typeid<>" + Typeid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = Typename + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}


	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select typeid,typename,fullname,lastnode,p_typeid,selected,statetag,accid";
		qry += " from waretype where (accid=" + Accid+" or accid=0)";
		if (downdate.length() <= 0) {
			qry += " and statetag=1 and noused=0";
			headsql = " insert ";
		} else {
			qry += " and lastdate>=to_date('" + downdate + "','yyyy-mm-dd hh24:mi:ss')";
			isupdate = true; // 更改
			headsql = " replace ";
		}
		Table tb = new Table();
		//System.out.println(qry);
		tb = DbHelperSQL.GetTable(qry);

		if (!isupdate)

			sql.append("\n delete from waretype where accid=" + Accid + " or accid=0;");

		for (int i = 0; i < tb.getRowCount(); i++) {
			String sql1 ="";// "accid";
			String sql2 ="";// Accid.toString();
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
				sql1 += strKey+",";
				sql2 += strValue+",";

			}
			sql.append("\n " + headsql + " into waretype (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
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