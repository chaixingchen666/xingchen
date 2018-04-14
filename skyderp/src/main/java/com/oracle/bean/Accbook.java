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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-02 17:28:39
//*************************************
public class Accbook implements java.io.Serializable {
	private Long Userid;
	private Long Bookid;
	private Long Accid;
	private String Bookname;
	private String Notedate;
	private Integer Statetag;
	private String Bookcolor;
	private Float Firstcurr;
	private Float Balcurr;
	private String Remark;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public void setBookid(Long bookid) {
		this.Bookid = bookid;
	}

	public Long getBookid() {
		return Bookid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setBookname(String bookname) {
		this.Bookname = bookname;
	}

	public String getBookname() {
		return Bookname;
	}

	public void setNotedate(String notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Notedate);
		return Notedate;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public void setBookcolor(String bookcolor) {
		this.Bookcolor = bookcolor;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setFirstcurr(Float firstcurr) {
		this.Firstcurr = firstcurr;
	}

	public Float getFirstcurr() {
		return Firstcurr;
	}

	public void setBalcurr(Float balcurr) {
		this.Balcurr = balcurr;
	}

	public Float getBalcurr() {
		return Balcurr;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
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
		if (Bookid == null || Bookid == 0) {
			errmess = "账本id参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from bookdetail where bookid=" + Bookid + " and accid=" + Accid + " and rownum=1 ;");
		strSql.append("\n   if v_count>0 then");
		strSql.append("\n      v_retcs:='0账本已有记账记录，不允许删除！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   update accbook set statetag=2 where bookid=" + Bookid + " and accid=" + Accid + ";");
		// strSql.append("\n delete from bookemploye where bookid=" + Bookid + ";");
		// strSql.append("\n delete from bookhousepay where bookid=" + Bookid + " and accid=" + Accid + ";");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");

		//		System.out.println(strSql.toString());
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

	// 账本启用，禁用
	public int Enabled() {
		if (Bookid == 0) {
			errmess = "账本参数无效:bookid";
			return 0;
		}
		if (Statetag < 1 || Statetag > 2) {
			errmess = "状态参数无效:statetag";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_notedate date;");
		strSql.append("\n   v_firstcurr number;");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin");
		strSql.append("\n   begin");
		if (Statetag == 2) // 禁用
		{
			strSql.append("\n   select notedate,firstcurr into v_notedate,v_firstcurr from accbook where statetag=1 and  bookid=" + Bookid + " and accid=" + Accid + ";");
			strSql.append("\n   update accbook set statetag=2 where bookid=" + Bookid + " and accid=" + Accid + ";");
			strSql.append("\n   v_retcs:= '1操作成功！';");
		} else // 启用
		{
			strSql.append("\n   select notedate,firstcurr into v_notedate,v_firstcurr from accbook where statetag=0 and  bookid=" + Bookid + " and accid=" + Accid + ";");

			strSql.append("\n   insert into bookdetail (id,bookid,subid,remark,curr0,curr1,curr2,ywnoteno,notedate,lastdate,lastop)");
			strSql.append("\n   values (bookdetail_id.nextval," + Bookid + ",0,'期初',0,0,v_firstcurr,'',trunc(v_notedate-1),sysdate,'" + Lastop + "');");
			strSql.append("\n   update accbook set statetag=1 where bookid=" + Bookid + " and accid=" + Accid + ";");
			strSql.append("\n   v_retcs:= '1操作成功！' ;");
		}
		strSql.append("\n     goto exit;");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n     v_retcs:= '0未找到账本！' ;");
		strSql.append("\n   end;");
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

		if (retcs.substring(0, 1).equals("0"))
			return 0;
		else
			return 1;

	}

	// 增加记录
	public int Append(JSONObject jsonObject) {

		if (Bookname == null || Bookname.equals("")) {
			errmess = "账本名称不允许为空！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("bookid,");
		strSql2.append("v_bookid,");

		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		strSql1.append("bookname,");
		strSql2.append("'" + Bookname + "',");
		if (Notedate != null) {
			strSql1.append("notedate,");
			strSql2.append("to_date('" + Notedate + "','yyyy-mm-dd'),");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Bookcolor != null) {
			strSql1.append("Bookcolor,");
			strSql2.append("'" + Bookcolor + "',");
		}
		if (Firstcurr != null) {
			strSql1.append("firstcurr,");
			strSql2.append(Firstcurr + ",");
			Balcurr = Firstcurr;
		}
		if (Balcurr != null) {
			strSql1.append("balcurr,");
			strSql2.append(Balcurr + ",");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_bookid number; ");
		strSql.append("\n begin ");
		strSql.append("\n   v_bookid:=accbook_bookid.nextval; ");
		strSql.append("\n   insert into accbook (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n   delete from  bookemploye where bookid=v_bookid;");
		if (jsonObject.has("epidlist")) {
			JSONArray jsonArray = jsonObject.getJSONArray("epidlist");

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long epid = Long.parseLong(jsonObject2.getString("epid"));
				strSql.append("\n  insert into bookemploye (id,epid,bookid)");
				strSql.append("  values (bookemploye_id.nextval," + epid + ",v_bookid);");

			}
		}

		strSql.append("\n  select v_bookid into :bookid from dual; ");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("bookid", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		Bookid = Long.parseLong(param.get("bookid").getParamvalue().toString());
		errmess = Bookid.toString();
		return 1;
	}

	// 修改记录
	public int Update(JSONObject jsonObject) {
		if (Bookid == null || Bookid == 0) {
			errmess = "账本id无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_balcurr number;");
		strSql.append("\n begin ");
		strSql.append("\n   update accbook set ");
		// if (Bookid != null) {
		// strSql.append("bookid=" + Bookid + ",");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Bookname != null) {
			strSql.append("bookname='" + Bookname + "',");
		}
		if (Notedate != null) {
			strSql.append("notedate=to_date('" + Notedate + "','yyyy-mm-dd'),");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Bookcolor != null) {
			strSql.append("Bookcolor='" + Bookcolor + "',");
		}
		if (Firstcurr != null) {
			strSql.append("firstcurr=" + Firstcurr + ",");
			Balcurr = Firstcurr;
		}
		if (Balcurr != null) {
			strSql.append("balcurr=" + Balcurr + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where bookid=" + Bookid + " and accid=" + Accid + ";");
		if (jsonObject.has("epidlist")) {
			JSONArray jsonArray = jsonObject.getJSONArray("epidlist");

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long epid = Long.parseLong(jsonObject2.getString("epid"));
				int value = Integer.parseInt(jsonObject2.getString("value"));
				// if (value == 0)
				strSql.append("\n  delete from bookemploye where epid=" + epid + " and bookid=" + Bookid + ";");
				// else {
				if (value == 1) {
					strSql.append("\n  insert into bookemploye (id,epid,bookid)");
					strSql.append("  values (bookemploye_id.nextval," + epid + "," + Bookid + ");");
				}

			}
		}

		if (Statetag != null && Statetag == 1) {//启用账本
			strSql.append("\n  commit;");
			strSql.append("\n   select f_calcbookbalcurr(bookid,notedate) into v_balcurr from accbook where bookid=" + Bookid + "; ");
			//			strSql.append("\n  p_calcbookbalcurr(" + Bookid + ",notedate); ");
		}

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
		strSql.append(" FROM accbook ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM accbook a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere) {
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		//
		// strSql.append(" FROM accbook a");

		strSql.append("select a.bookid,a.bookname ,nvl(b.curr2,0) as balcurr");
		strSql.append("\n  from accbook a");
		strSql.append("\n  left outer join (");

		strSql.append("\n  select bookid,curr2 from (");
		strSql.append("\n  select notedate,bookid,curr2,id ,row_number()");
		strSql.append("\n  over(partition by to_char(notedate,'yyyy-mm-dd') order by notedate desc,id desc) as rn");
		strSql.append("\n  from bookdetail  where ");
		strSql.append("\n  where " + strWhere);

		strSql.append("\n  ) where rn=1");
		strSql.append("\n  ) b on a.bookid=b.bookid");
		strSql.append("\n  where a.accid=" + Accid + " and a.statetag=1");
		if (Userid > 0)
			strSql.append("\n  and exists (select 1 from bookemploye d where a.bookid=d.bookid and d.epid=" + Userid + ")");

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from accbook");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	// 获取账本查询条件
	public int doOption(long userid, int lx, long levelid) { // lx:1=收入，2=支出，0=转账
		// 账本
		String qry = "select bookid,bookname from accbook a where accid=" + Accid + " and statetag=1";
		if (levelid > 0)
			qry += " and exists (select 1 from BookEmploye b where a.bookid=b.bookid and b.epid=" + userid + ")";
		Table tb = new Table();
		tb = DbHelperSQL.GetTable(qry);
		if (tb.getRowCount() == 0) {
			errmess = "当前用户没有授权可以查看的账本！";
			return 0;
		}
		String retcs = "\"msg\":\"操作成功！\",\"booklist\":[";
		for (int i = 0; i < tb.getRowCount(); i++) { // lx:0=收入，1=支出，2=余额
			if (i > 0)
				retcs += ",";
			retcs += "{";
			for (int j = 0; j < tb.getColumnCount(); j++) {//
				if (j > 0)
					retcs += ",";
				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();//
				String strValue = tb.getRow(i).get(j).toString();//
				retcs += "\"" + strKey + "\":\"" + strValue + "\"";
			}
			retcs += "}";
		}
		retcs += "]";
		// 记账类别
		qry = " select subid,subname from subitem where statetag=1 and noused=0 and (accid=0 or accid=" + Accid + ")";
		// lx:1=收入，2=支出，0=转账
		qry += " and lx=" + lx;
		tb = DbHelperSQL.GetTable(qry);
		retcs += ",\"subitemlist\":[";
		for (int i = 0; i < tb.getRowCount(); i++) { // lx:0=收入，1=支出，2=余额
			if (i > 0)
				retcs += ",";
			retcs += "{";
			for (int j = 0; j < tb.getColumnCount(); j++) {//
				if (j > 0)
					retcs += ",";
				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();//
				String strValue = tb.getRow(i).get(j).toString();//
				retcs += "\"" + strKey + "\":\"" + strValue + "\"";
			}
			retcs += "}";
		}
		retcs += "]";
		errmess = retcs;
		return 1;
	}

	//计算刷新账本余额
	public int Calc() {
		if (Bookid == 0) {
			errmess = "账本id不是一个有效值！";
			return 0;
		}
		if (!Func.isValidDate(Notedate)) {
			errmess = "notedate不是一个有效日期型！";
			return 0;
		}
		Notedate = Func.subString(Notedate, 1, 10) + " 00:00:00";
		String qry = "declare";
		qry += "\n   v_curr number;";
		qry += "\n begin";
		//		qry += "\n    v_curr:=f_calcbookbalcurr(" + Bookid + ",to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss'));";
		qry += "\n    p_calcbookbalcurr(" + Bookid + ",to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss'));";
		qry += "\n end;";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}
}