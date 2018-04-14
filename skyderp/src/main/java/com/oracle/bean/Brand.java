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

/**
 * 
 * @sun hong
 *
 */

public class Brand implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3182315082117146053L;
	private Long Brandid; //
	private String Brandname; //
	private Long Accid; //
	private Integer Lyfs; //
	private Long Accid1; //
	private Long Brandid1; //
	private Integer Noused; //
	private Integer Statetag; //
	private String Lastop; //
	private Date Lastdate; //
	private String Shortname; //
	private Long Userid; //
	private String errmess;
	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	private Integer Qxbj; //
	private Integer Alluser = 0; // 授权所有职员

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	public String getErrmess() {
		return errmess;
	}

	public Long getBrandid() {
		return Brandid;
	}

	public void setBrandid(Long brandid) {
		this.Brandid = brandid;
	}

	public String getBrandname() {
		return Brandname;
	}

	public void setBrandname(String brandname) {
		this.Brandname = brandname;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Integer getLyfs() {
		return Lyfs;
	}

	public void setLyfs(Integer lyfs) {
		this.Lyfs = lyfs;
	}

	public Long getAccid1() {
		return Accid1;
	}

	public void setAccid1(Long accid1) {
		this.Accid1 = accid1;
	}

	public Long getBrandid1() {
		return Brandid1;
	}

	public void setBrandid1(Long brandid1) {
		this.Brandid1 = brandid1;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Lastdate);
	}

	public void setLastdate(Date lastdate) {
		this.Lastdate = lastdate;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public Long getUserid() {
		return Userid;
	}

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public Integer getQxbj() {
		return Noused;
	}

	public void setQxbj(Integer qxbj) {
		this.Qxbj = qxbj;
	}

	// 删除一条数据
	public int Delete() {
		if (Brandid == 0) {
			errmess = "brandid无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("select count(*) as num from (");
		strSql.append("select wareid from warecode where brandid=" + Brandid + " and rownum=1 and statetag=1");
		strSql.append(")");

		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前品牌已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update brand set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where brandid=" + Brandid + " and accid=" + Accid);
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
		if (Brandname == null || Brandname.equals("")) {
			errmess = "品牌名称不允许为空！";
			return 0;
		}
		Brandname = Brandname.replace("'", "").replace("\"", "");
		if (Exists())
			return 0;// 判断品牌名称是否已存在

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		// System.out.println("append begin");
		strSql1.append("brandid,");
		strSql2.append("v_id,");
		strSql1.append("brandname,");
		strSql2.append("'" + Brandname + "',");

		strSql1.append("shortname,");
		strSql2.append("'" + PinYin.getPinYinHeadChar(Brandname) + "',");

		strSql1.append("accid,");
		strSql2.append("" + Accid + ",");
		if (Lyfs != null) {
			strSql1.append("lyfs,");
			strSql2.append("" + Lyfs + ",");
		}
		if (Accid1 != null) {
			strSql1.append("accid1,");
			strSql2.append("" + Accid1 + ",");
		}
		if (Brandid1 != null) {
			strSql1.append("brandid1,");
			strSql2.append("" + Brandid1 + ",");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append("" + Noused + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append("" + Statetag + ",");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n begin ");
		strSql.append("\n  v_id:=brand_brandid.nextval; ");

		strSql.append("\n insert into brand (");
		strSql.append("\n  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("\n  " + strSql2.toString());
		strSql.append(");");
		if (Qxbj == 1)// 如果启用了权限控制，增加的品牌自动加入权限表
		{
			if (Alluser == 1) {// 自动授权所有用户
				strSql.append("\n   insert into employebrand (id,epid,brandid)");
				strSql.append("\n   select employebrand_id.nextval,epid,v_id from employe b where b.statetag=1 and b.accid= " + Accid + ";");
			} else {
				strSql.append("\n   insert into employebrand (id,epid,brandid)");
				strSql.append("\n   values (employebrand_id.nextval," + Userid + ",v_id); ");
			}
		}
		strSql.append("\n  select v_id into :brandid from dual; "); // 不加这2句要出错
		strSql.append("\n end;");
		// System.out.println(strSql.toString());
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// Types.BIGINT
		param.put("brandid", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret >= 0) {
			// 取返回值
			// System.out.println("retid="+ret+" brandid="+param.get("brandid").getParamvalue().toString());

			Brandid = Long.parseLong(param.get("brandid").getParamvalue().toString());
			errmess = Brandid.toString();
			// System.out.println("append end");
			return 1;
		} else {
			errmess = "操作异常！";
			return 0;
		}
	}

	// 修改记录
	public int Update() {

		if (Brandid == null || Brandid == 0) {
			errmess = "品牌id参数无效！";
			return 0;
		}
		if (Brandname != null) {
			if (Brandname.equals("")) {
				errmess = "品牌名称不允许为空！";
				return 0;
			}
			Brandname = Brandname.replace("'", "").replace("\"", "");
			if (Exists())
				return 0;// 判断品牌名称是否已存在
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number;");
		strSql.append("\n begin ");
		strSql.append("\n  update brand set ");
		if (Brandname != null) {
			strSql.append("brandname='" + Brandname + "',");
			Shortname = PinYin.getPinYinHeadChar(Brandname);
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Lyfs != null) {
			strSql.append("lyfs=" + Lyfs + ",");
		}
		if (Accid1 != null) {
			strSql.append("accid1=" + Accid1 + ",");
		}
		if (Brandid1 != null) {
			strSql.append("brandid1=" + Brandid1 + ",");
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
		strSql.append(" lastdate=sysdate");
		strSql.append("\n  where brandid=" + Brandid + " and accid=" + Accid + " ;");
		if (Qxbj != null && Qxbj == 1)// 如果启用了权限控制，增加的品牌自动加入权限表
		{
			if (Alluser == 1) {// 自动授权所有用户
				strSql.append("\n   insert into employebrand (id,epid,brandid)");
				strSql.append("\n   select employebrand_id.nextval,epid," + Brandid + " from employe b where b.statetag=1 and b.accid= " + Accid);
				strSql.append("\n   and not exists (select 1 from employebrand c where b.epid=c.epid and c.brandid=" + Brandid + ");");
			} else {
				strSql.append("\n   select count(*) into v_count from employebrand where epid=" + Userid + " and brandid=" + Brandid + ";");
				strSql.append("\n   if v_count=0 then ");
				strSql.append("\n      insert into employebrand (id,epid,brandid)");
				strSql.append("\n      values (employebrand_id.nextval," + Userid + "," + Brandid + ") ;");
				strSql.append("\n   end if;");
			}
		}
		strSql.append("\n  commit;");
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
		strSql.append(" FROM brand ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		// System.out.println(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM brand ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		// System.out.println(qp.getQueryString());
		return DbHelperSQL.GetTable(qp);
	}

	/// <summary>
	/// 是否存在该记录
	/// </summary>
	public boolean Exists() {
		// if (Brandname == "") {
		// errmess = "品牌名称必须输入！";
		// return true;
		// }
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from brand");
		strSql.append(" where brandname='" + Brandname + "' and statetag=1  and rownum=1 and accid=" + Accid);
		if (Brandid != null)
			strSql.append(" and brandid<>" + Brandid);
		// System.out.println(strSql.toString());
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "品牌:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 从excel导入
	public int doLoadfromExcel() {
		if (Brandid == null)
			Brandid = (long) 0;
		// string wareno=ht["wareno"].ToString().ToUpper();
		// Table tb = new Table();

		String qry = "declare";// "select wareid from warecode where accid=" + accid + " and wareno='" + wareno + "' and rownum=1";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_count number;";
		qry += "\n begin";
		if (Brandid > 0) // 货号已存在,更新商品资料
		{
			qry += "\n  select count(*) into v_count from brand where Brandid=" + Brandid + " and accid=" + Accid + ";";
			qry += "\n  if v_count=0 then";
			qry += "\n     v_retcs:='0品牌id无效！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";

			qry += "\n  update brand set lastdate=sysdate,lastop='" + Lastop + "'";
			if (Brandname != null && Brandname.length() > 0) {
				qry += ",brandname='" + Brandname + "'";
				Shortname = PinYin.getPinYinHeadChar(Brandname);
				qry += ",shortname='" + Shortname + "'";

			}
			qry += " where brandid=" + Brandid + " and accid=" + Accid + ";";

		} else {
			String sqlinsert1 = "lastdate,noused,statetag,accid,lastop";
			String sqlinsert2 = "sysdate,0,1," + Accid + ",'" + Lastop + "'";
			sqlinsert1 += ",brandid";
			sqlinsert2 += ",brand_brandid.nextval";
			// long brandid = 0;
			// string qry1 = "";

			if (Brandname != null && Brandname.length() > 0) {
				sqlinsert1 += ",brandname";
				sqlinsert2 += ",'" + Brandname + "'";
				Shortname = PinYin.getPinYinHeadChar(Brandname);
				sqlinsert1 += ",shortname";
				sqlinsert2 += ",'" + Shortname + "'";
			}
			qry += "\n  insert into brand (" + sqlinsert1 + ")  \n values (" + sqlinsert2 + ") ;";
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

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select brandid,brandname,shortname,noused,statetag,accid";
		qry += " from brand where (accid=" + Accid + " or accid=0)";
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

			sql.append("\n delete from brand where accid=" + Accid + " or accid=0;");

		for (int i = 0; i < tb.getRowCount(); i++) {
			String sql1 = "";
			String sql2 = "";
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
			sql.append("\n " + headsql + " into brand (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
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