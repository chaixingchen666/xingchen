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
//  @author:sunhong  @date:2017-02-19 22:07:02
//*************************************
public class Payway implements java.io.Serializable {
	private String Payno;
	private String Payname;
	private String Payaccount;
	private Long Accid;
	private Integer Noused;
	private Integer Statetag;
	private String Lastop;
	private Date Lastdate;
	private Long Payid;
	private String errmess;
	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setPayno(String payno) {
		this.Payno = payno;
	}

	public String getPayno() {
		return Payno;
	}

	public void setPayname(String payname) {
		this.Payname = payname;
	}

	public String getPayname() {
		return Payname;
	}

	public void setPayaccount(String payaccount) {
		this.Payaccount = payaccount;
	}

	public String getPayaccount() {
		return Payaccount;
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

	public void setPayid(Long payid) {
		this.Payid = payid;
	}

	public Long getPayid() {
		return Payid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	// 删除记录
	public int Delete() {
		if (Payid == 0) {
			errmess = "结算方式id参数无效!";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n  v_count number;");

		strSql.append("\n  v_retcs varchar2(200);");
		strSql.append("\n begin ");

		strSql.append("\n   select count(*) into v_count from payway where accid=" + Accid + " and payid=" + Payid + " and (payno='0' or payno>='O');");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0系统约定的结算方式，不允许删除！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   select count(*) into v_count from (");
		strSql.append("\n   select id from paycurr a where  rownum=1 and accid=" + Accid + " and payid=" + Payid);
		// strSql.append(" and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n   union all");
		strSql.append("\n   select id from incomecurr a where  rownum=1 and accid=" + Accid + " and payid=" + Payid);
		// strSql.append(" and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n   union all");
		strSql.append("\n   select id from waresalepay a where  rownum=1 and accid=" + Accid + " and payid=" + Payid);
		strSql.append("\n   union all");
		strSql.append("\n   select id from guestcurr a where  rownum=1 and payid=" + Payid);
		strSql.append("\n   union all");
		strSql.append("\n   select id from housecost a where  rownum=1 and accid=" + Payid + " and payid=" + Payid);
		strSql.append("\n   );");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0当前结算方式已使用，不允许删除！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n  update payway set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("\n  where payid=" + Payid + " and accid=" + Accid + ";");
		strSql.append("\n  v_retcs:='1删除成功！';");
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

	// 增加记录
	public int Append() {

		if (Payname == null && Payname.equals("")) {
			errmess = "结算方式名称不允许为空！";
			return 0;
		}
		if (Exists())
			return 0; // 判断结算方式名称是否已存在
		if (Payno == null && Payno.equals("")) {
			errmess = "结算方式代号无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("payid,");
		strSql2.append("v_id,");
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Payno != null) {
		strSql1.append("payno,");
		strSql2.append("'" + Payno + "',");
		// }
		// if (Payname != null) {
		strSql1.append("payname,");
		strSql2.append("'" + Payname + "',");
		// }
		if (Payaccount != null) {
			strSql1.append("payaccount,");
			strSql2.append("'" + Payaccount + "',");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		// if (Statetag != null) {
		strSql1.append("statetag,");
		strSql2.append(Statetag + ",");
		// }
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		// if (Lastdate != null) {
		// strSql1.append("lastdate,");
		// strSql2.append("'" + Lastdate + "',");
		// }
		// if (Payid != null) {
		// strSql1.append("payid,");
		// strSql2.append(Payid + ",");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("    v_id:=payway_payid.nextval; ");
		strSql.append("    insert into payway (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("   select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret >= 0) {
			Payid = Long.parseLong(param.get("id").getParamvalue().toString());
			errmess = Payid.toString();
			return 1;
		} else {
			errmess = "操作异常！";
			return 0;
		}
	}

	// 修改记录
	public int Update() {

		if (Payid == null || Payid == 0) {
			errmess = "结算方式id参数无效！";
			return 0;
		}
		// if (Payno != null) {
		// if (Payno.length() <= 0) {
		// errmess = "结算方式代号无效！";
		// return 0;
		// }
		// }
		if (Payname != null)
			if (Exists())
				return 0; // 判断结算方式名称是否已存在
		// {
		// WriteResult(response, 0, dal.getErrmess());
		// return;
		// }

		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update payway set ");

		if (Payno != null && Payno.compareTo("0") > 0 && Payno.compareTo("O") < 0) {
			strSql.append("payno='" + Payno + "',");
		}
		if (Payname != null && Payno.compareTo("0") > 0 && Payno.compareTo("O") < 0) { // 0-O允许更改
			strSql.append("payname='" + Payname + "',");
		}
		if (Payaccount != null) {
			strSql.append("payaccount='" + Payaccount + "',");
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
		// if (Lastdate != null) {
		// strSql.append("lastdate='" + Lastdate + "',");
		// }
		// if (Payid != null) {
		// strSql.append("payid=" + Payid + ",");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where payid=" + Payid + " and accid=" + Accid + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
		{
			errmess="操作异常！";
			return 0;
		}
		else
		{
			errmess="操作成功！";
			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM payway ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM payway ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from payway");
		strSql.append(" where Payname='" + Payname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		if (Payid != null)
			strSql.append(" and Payid<>" + Payid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "结算方式:" + Payname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	public String GetUseEnabledList() {
		Table tb = new Table();
		StringBuilder strSql = new StringBuilder();

		strSql.append("select chars from CHARASSIST a ");
		strSql.append(" where chars<'O' and not exists (select 1 from payway b where b.accid=" + Accid + " and b.payno=a.chars and b.statetag=1)");
		strSql.append(" order by chars");

		tb = DbHelperSQL.Query(strSql.toString()).getTable(1);

		int rs = tb.getRowCount();
		String retcs = "\"CHARSLIST\":\"";
		String fh = "";
		for (int i = 0; i < rs; i++) {
			retcs += fh + tb.getRow(i).get(0).toString();
			fh = ",";
		}
		retcs += "\"";

		return "{\"result\":1,\"msg\":\"操作成功！\",\"total\":1,\"rows\":[{" + retcs + "}]}";
	}

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select payid,payname,payno,payaccount,statetag,noused,lastop,accid";
		qry += " from payway where accid=" + Accid;
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

			sql.append("\n delete from payway where accid=" + Accid + ";");

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
				sql1 += strKey+ ",";
				sql2 += strValue+ ",";

			}
			sql.append("\n " + headsql + " into payway (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
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