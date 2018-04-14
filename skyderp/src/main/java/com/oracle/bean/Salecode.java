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
//  @author:sunhong  @date:2017-02-20 17:34:06
//*************************************
public class Salecode implements java.io.Serializable {
	private Long Saleid;
	private String Salename;
	private Long Accid;
	private Integer Noused;
	private Integer Statetag;
	private String Lastop;
	private Date Lastdate;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setSaleid(Long saleid) {
		this.Saleid = saleid;
	}

	public Long getSaleid() {
		return Saleid;
	}

	public void setSalename(String salename) {
		this.Salename = salename;
	}

	public String getSalename() {
		return Salename;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
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

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(" select id from wareoutm where saleid=" + Saleid + " and rownum=1 and accid=" + Accid);
		// strSql.append(" union all");
		// strSql.append(" select id from custorderm where saleid=" + Saleid + " and rownum=1 and accid=" + Accid);
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update salecode set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where saleid=" + Saleid + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("saleid,");
		strSql2.append("v_id,");
		// if (Saleid != null) {
		// strSql1.append("saleid,");
		// strSql2.append(Saleid + ",");
		// }
		if (Salename != null) {
			strSql1.append("salename,");
			strSql2.append("'" + Salename + "',");
		}
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=salecode_saleid.nextval; ");
		strSql.append("   insert into salecode (");
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
			Saleid = Long.parseLong(param.get("id").getParamvalue().toString());
			return ret;
		} else
			return 0;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update salecode set ");
		// if (Saleid != null) {
		// strSql.append("saleid=" + Saleid + ",");
		// }
		if (Salename != null) {
			strSql.append("salename='" + Salename + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
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
		strSql.append("lastdate=sysdate");
		strSql.append("  where saleid=" + Saleid + " and accid=" + Accid + " ;");
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
		strSql.append(" FROM salecode ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM salecode ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from salecode");
		strSql.append(" where salename='" + Salename + "' and statetag=1 and rownum=1 and accid=" + Accid);
		if (Saleid != null)
			strSql.append(" and Saleid<>" + Saleid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "销售方式:" + Salename + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select saleid,salename,noused,statetag,accid";
		qry += " from salecode where (accid=" + Accid + " or accid=0)";
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

			sql.append("\n delete from salecode where accid=" + Accid + " or accid=0;");

		for (int i = 0; i < tb.getRowCount(); i++) {
			String sql1 = "";// "lastdate";//"accid";
			String sql2 = "";// "sysdate";//Accid.toString();
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
			sql.append("\n " + headsql + " into salecode (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
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