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
//  @author:sunhong  @date:2017-02-19 22:29:47
//*************************************
public class Department implements java.io.Serializable {
	private Long Dptid;
	private String Dptname;
	private Long Accid;
	private String Shortname;
	private Integer Noused;
	private Integer Statetag;
	private String Lastop;
	private Date Lastdate;
	private String errmess;
	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setDptid(Long dptid) {
		this.Dptid = dptid;
	}

	public Long getDptid() {
		return Dptid;
	}

	public void setDptname(String dptname) {
		this.Dptname = dptname;
	}

	public String getDptname() {
		return Dptname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
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

	public void setLastdate(Date lastdate) {
		this.Lastdate = lastdate;
	}

	public String getLastdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Lastdate);
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	// 删除记录
	public int Delete() {
		if (Dptid == 0) {
			errmess = "部门id参数无效";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num from (");
		strSql.append("\n  select dptid from wareouth where dptid=" + Dptid + " and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select dptid from housecost where dptid=" + Dptid + " and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update department set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where dptid=" + Dptid + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功";
			return 1;
		}
	}

	// 增加记录
	public int Append() {

		if (Dptname == null && Dptname.equals("")) {
			errmess = "部门名称不允许为空！";
			return 0;
		}
		if (Exists()) // 判断部门名称是否已存在
		{
			// WriteResult(response, 0, dal.getErrmess());
			return 0;

		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("dptid,");
		strSql2.append("v_id,");
		// if (Dptid != null) {
		// strSql1.append("dptid,");
		// strSql2.append(Dptid + ",");
		// }
		// if (Dptname != null) {
		strSql1.append("dptname,");
		strSql2.append("'" + Dptname + "',");
		Shortname = PinYin.getPinYinHeadChar(Dptname);
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
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
		// if (Lastdate != null) {
		// strSql1.append("lastdate,");
		// strSql2.append("'" + Lastdate + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("  v_id:=department_dptid.nextval; ");
		strSql.append("  insert into department (");
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
		if (ret >= 0) {
			Dptid = Long.parseLong(param.get("id").getParamvalue().toString());
			errmess = Dptid.toString();
			return 1;
		} else {
			errmess = "操作异常！";
			return 0;
		}
	}

	// 修改记录
	public int Update() {

		if (Dptid == null || Dptid.equals("")) {
			errmess = "部门id参数无效！";
			return 0;
		}
		if (Exists()) // 判断部门名称是否已存在
		{
			// WriteResult(response, 0, dal.getErrmess());
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update department set ");
		// if (Dptid != null) {
		// strSql.append("dptid=" + Dptid + ",");
		// }
		if (Dptname != null) {
			strSql.append("dptname='" + Dptname + "',");
			Shortname = PinYin.getPinYinHeadChar(Dptname);
		}
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		// if (Lastdate != null) {
		// strSql.append("lastdate='" + Lastdate + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where dptid=" + Dptid + " and accid=" + Accid + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM department ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM department ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public Boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from department");
		strSql.append(" where dptname='" + Dptname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		if (Dptid != null)
			strSql.append(" and Dptid<>" + Dptid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "部门:" + Dptname + " 已存在，请重新输入！";
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
		String qry = "select dptid,dptname,shortname,statetag,noused,lastop,accid";
		qry += " from department where (accid=" + Accid+" or accid=0)";
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

			sql.append("\n delete from department where accid=" + Accid + " or accid=0;");

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
			sql.append("\n " + headsql + " into department (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
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