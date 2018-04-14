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
// @author:sunhong @date:2017-03-19 21:43:00
// *************************************
public class Sizecode implements java.io.Serializable {
	private Long Sizeid;
	private String Sizename;
	private String Groupno;
	private String Sizeno;
	private Long Accid;
	private Integer Noused;
	private Integer Statetag;
	private String Lastop;
	private Integer Lyfs;
	private Long Accid1;
	private Long Sizeid1;
	private Date Lastdate;
	private Integer Usedbj;
	private String Sizesm;
	private String errmess;

	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setSizeid(Long sizeid) {
		this.Sizeid = sizeid;
	}

	public Long getSizeid() {
		return Sizeid;
	}

	public void setSizename(String sizename) {
		this.Sizename = sizename;
	}

	public String getSizename() {
		return Sizename;
	}

	public void setGroupno(String groupno) {
		this.Groupno = groupno;
	}

	public String getGroupno() {
		return Groupno;
	}

	public void setSizeno(String sizeno) {
		this.Sizeno = sizeno;
	}

	public String getSizeno() {
		return Sizeno;
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

	public void setSizeid1(Long sizeid1) {
		this.Sizeid1 = sizeid1;
	}

	public Long getSizeid1() {
		return Sizeid1;
	}

	public void setUsedbj(Integer usedbj) {
		this.Usedbj = usedbj;
	}

	public Integer getUsedbj() {
		return Usedbj;
	}

	public void setSizesm(String sizesm) {
		this.Sizesm = sizesm;
	}

	public String getSizesm() {
		return Sizesm;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	// 删除记录
	public int Delete() {
		if (Sizeid == 0) {
			errmess = "sizeid无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		// strSql.setLength(0);
		strSql.append("declare");
		strSql.append("\n  v_sizenum number(10); ");
		strSql.append("\n  v_retcs varchar2(200);");
		strSql.append("\n  v_usedbj number(1);");
		strSql.append("\n begin");
		strSql.append("\n    v_sizenum:=0;");
		strSql.append("\n    select f_sizeisused(sizeid,usedbj) into v_usedbj from sizecode where sizeid=" + Sizeid + " and accid=" + Accid + ";");
		strSql.append("\n    if v_usedbj>0 then ");
		strSql.append("\n       v_retcs:='0当前尺码已有库存，不允许删除！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");

		strSql.append("\n    delete from warebarcode where accid=" + Accid + " and sizeid=" + Sizeid + ";");

		strSql.append("\n    update sizecode set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("\n    where Sizeid=" + Sizeid + " and accid=" + Accid + ";");
		// strSql.append("\n select nvl(max(num),0) into v_sizenum from ( select
		// groupno,count(*) as num from sizecode where accid=" + Accid + " and
		// statetag=1 and noused=0 group by groupno ); ");
		strSql.append("\n    select nvl(max(num),0) into v_sizenum from ( select groupno,count(*) as num from sizecode where accid=" + Accid + " and statetag=1 group by groupno ); ");

		strSql.append("\n    update accreg set sizenum=v_sizenum where accid=" + Accid + ";");
		strSql.append("\n    v_retcs:='1操作成功！';");
		strSql.append("\n    <<exit>>");
		strSql.append("\n    select v_retcs,v_sizenum into :retcs,:sizenum from dual;");
		strSql.append("\n end;");

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("sizenum", new ProdParam(Types.INTEGER));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		if (retcs.substring(0, 1).equals("1")) {
			errmess = "\"msg\": \"" + retcs.substring(1) + "\"" //
					+ ",\"SIZENUM\": " + param.get("sizenum").getParamvalue().toString();

			return 1;
		} else {
			errmess = retcs.substring(1);
			return 0;
		}
	}

	// 增加记录
	public int Append() {
		// System.out.println(" addsizecode append");
		if (Sizename == null || Sizename.equals("")) {
			errmess = "尺码名称不允许为空！";
			return 0;
		}
		Sizename = Sizename.toUpperCase();
		// if (!Func.isNumAndEnCh(Sizename)) {
		if (!Func.isMatchCode(Sizename, "^[A-Za-z0-9\\-\\#\\/]+$")) {// “-/#”
			errmess = "尺码名称只能由字母或数字或-/#组成！";
			return 0;
		}
		if (Func.getLength(Sizename) > 10) {
			errmess = "尺码名称最多10个字符！";
			return 0;
		}
		if (Sizeno != null && Sizeno.length() > 0) {

			if (!Func.isNumAndEnCh(Sizeno)) {
				errmess = "尺码代号只能由字母或数字组成！";
				return 0;
			}
			// Sizeno = Sizeno.toUpperCase();
		}
		if (Groupno == null || Groupno.equals("")) {
			errmess = "尺码组不允许为空！";
			return 0;
		}
		// Groupno = Groupno.toUpperCase();
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("sizeid,");
		strSql2.append("v_id,");

		// if (Sizename != null) {
		strSql1.append("sizename,");
		strSql2.append("'" + Sizename + "',");
		// }
		// if (Groupno != null) {
		strSql1.append("groupno,");
		strSql2.append("'" + Groupno.toUpperCase() + "',");
		// }
		if (Sizeno != null) {
			strSql1.append("sizeno,");
			strSql2.append("'" + Sizeno.toUpperCase() + "',");
		}
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Noused != null) {
		strSql1.append("noused,");
		strSql2.append(Noused + ",");
		// }
		// if (Statetag != null) {
		strSql1.append("statetag,");
		strSql2.append(Statetag + ",");
		// }
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Lyfs != null) {
			strSql1.append("lyfs,");
			strSql2.append(Lyfs + ",");
		}
		if (Accid1 != null) {
			strSql1.append("accid1,");
			strSql2.append(Accid1 + ",");
		}
		if (Sizeid1 != null) {
			strSql1.append("sizeid1,");
			strSql2.append(Sizeid1 + ",");
		}
		if (Usedbj != null) {
			strSql1.append("usedbj,");
			strSql2.append(Usedbj + ",");
		}
		if (Sizesm != null) {
			strSql1.append("sizesm,");
			strSql2.append("'" + Sizesm + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_sizenum number; ");
		strSql.append("\n begin ");
		strSql.append("\n    v_sizenum:=0;");
		// if (Sizename != null) {
		strSql.append("\n    select count(*) into v_count  from sizecode");
		strSql.append("\n    where sizename='" + Sizename + "' and statetag=1 and accid=" + Accid + " and groupno='" + Groupno + "' and rownum=1 ;");
		strSql.append("\n    if v_count>0 then ");
		strSql.append("\n       v_retcs:='0尺码:" + Sizename + "已存在！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");
		// }
		if (Sizeno != null) {
			strSql.append("\n   select count(*) into v_count  from sizecode");
			strSql.append("\n   where sizeno='" + Sizeno + "' and statetag=1 and accid=" + Accid + " and groupno='" + Groupno + "' and rownum=1;");
			strSql.append("\n   if v_count>0 then ");
			strSql.append("\n      v_retcs:='0尺码代号:" + Sizeno + "已存在！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		strSql.append("\n   select count(*) into v_count from sizecode where sizeid<>" + Sizeid + " and statetag=1 and accid=" + Accid + " and groupno='" + Groupno + "';");
		strSql.append("\n   if v_count>=15 then ");
		strSql.append("\n      v_retcs:='0每组尺码类型不允许超过15个！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   v_id:=sizecode_sizeid.nextval; ");
		strSql.append("\n   insert into sizecode (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n    v_retcs:='1'||v_id;");

		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs,v_sizenum into :retcs,:sizenum from dual;");
		strSql.append("\n end;");
		// System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("sizenum", new ProdParam(Types.INTEGER));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		if (retcs.substring(0, 1).equals("1")) {
			errmess = "\"msg\": \"" + retcs.substring(1) + "\"" //
					+ ",\"SIZENUM\": " + param.get("sizenum").getParamvalue().toString();

			return 1;
		} else {
			errmess = retcs.substring(1);
			return 0;
		}
	}

	// 修改记录
	public int Update() {
		if (Sizeid == null || Sizeid == 0) {
			errmess = "sizeid无效！";
			return 0;
		}
		// if (Func.isNull(Sizename ))
		if (Sizename != null) {
			if (Sizename.length() <= 0) {
				errmess = "尺码名称不允许为空！";
				return 0;
			}
			Sizename = Sizename.toUpperCase();

			if (!Func.isMatchCode(Sizename, "^[A-Za-z0-9\\-\\#\\/]+$")) {// “-/#”
				errmess = "尺码名称只能由字母或数字或-/#组成！";
				return 0;
			}
			if (Func.getLength(Sizename) > 10) {
				errmess = "尺码名称最多10个字符！";
				return 0;
			}
		}

		if (Sizeno != null && Sizeno.length() > 0) {

			if (!Func.isNumAndEnCh(Sizeno)) {
				errmess = "尺码代号只能由字母或数字组成！";
				return 0;
			}
		}
		if (Sizeno != null || Sizename != null)
			if (Func.isNull(Groupno)) {
				errmess = "尺码组不允许为空！";
				return 0;
			}

		// System.out.println("11111");
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_sizenum number;");
		strSql.append("\n begin ");
		strSql.append("\n    v_sizenum:=0;");
		if (Sizename != null) {
			strSql.append("\n   select count(*) into v_count  from sizecode");
			strSql.append("\n   where sizename='" + Sizename + "' and statetag=1 and accid=" + Accid + " and groupno='" + Groupno + "' and rownum=1 and sizeid<>" + Sizeid + ";");
			strSql.append("\n   if v_count>0 then ");
			strSql.append("\n      v_retcs:='0尺码:" + Sizename + "已存在！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Sizeno != null) {
			Sizeno = Sizeno.toUpperCase();

			Groupno = Groupno.toUpperCase();
			strSql.append("\n   select count(*) into v_count  from sizecode");
			strSql.append("\n   where sizeno='" + Sizeno + "' and statetag=1 and accid=" + Accid + " and groupno='" + Groupno + "' and rownum=1 and sizeid<>" + Sizeid + ";");
			strSql.append("\n   if v_count>0 then ");
			strSql.append("\n      v_retcs:='0尺码代号:" + Sizeno + "已存在！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		strSql.append("\n   select count(*) into v_count from sizecode where sizeid<>" + Sizeid + " and statetag=1 and accid=" + Accid + " and groupno='" + Groupno + "';");
		strSql.append("\n   if v_count>=15 then ");
		strSql.append("\n      v_retcs:='0每组尺码类型不允许超过15个！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   update sizecode set ");
		// if (Sizeid != null) {
		// strSql.append("sizeid=" + Sizeid + ",");
		// }
		if (Sizename != null) {
			strSql.append("sizename='" + Sizename + "',");
		}
		// if (Groupno != null) {
		// strSql.append("groupno='" + Groupno.toUpperCase() + "',");
		// }
		if (Sizeno != null) {
			strSql.append("sizeno='" + Sizeno.toUpperCase() + "',");
		}
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		if (Lyfs != null) {
			strSql.append("lyfs=" + Lyfs + ",");
		}
		if (Accid1 != null) {
			strSql.append("accid1=" + Accid1 + ",");
		}
		if (Sizeid1 != null) {
			strSql.append("sizeid1=" + Sizeid1 + ",");
		}
		if (Usedbj != null) {
			strSql.append("usedbj=" + Usedbj + ",");
		}
		if (Sizesm != null) {
			strSql.append("sizesm='" + Sizesm + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where sizeid=" + Sizeid + " and accid=" + Accid + " ;");
		strSql.append("\n    select nvl(max(num),0) into v_sizenum from ( select groupno,count(*) as num from sizecode where accid=" + Accid + " and statetag=1 group by groupno ); ");
		strSql.append("\n    if v_sizenum>15 then v_sizenum:=15; end if;");
		strSql.append("\n    update accreg set sizenum=v_sizenum where accid=" + Accid + ";");
		strSql.append("\n    v_retcs:='1操作成功！';");

		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs,v_sizenum into :retcs,:sizenum from dual;");
		strSql.append("\n end;");
		// System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("sizenum", new ProdParam(Types.INTEGER));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		if (retcs.substring(0, 1).equals("1")) {
			errmess = "\"msg\": \"" + retcs.substring(1) + "\"" //
					+ ",\"SIZENUM\": " + param.get("sizenum").getParamvalue().toString();

			return 1;
		} else {
			errmess = retcs.substring(1);
			return 0;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM sizecode ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM sizecode a");
		// strSql.append(" left outer join warecode b on a.accid=b.accid and
		// a.groupno=b.sizegroupno");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable1(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM sizecode a");
		strSql.append(" join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取所有数据
	public Table GetTable(String qry) {
		return DbHelperSQL.GetTable(qry);
	}

	// 获取所有数据
	public Table GetTable(String strWhere, String fieldlist, String sort) {

		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM sizecode ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		if (!sort.equals("")) {
			strSql.append(" order by " + sort);
		}

		return DbHelperSQL.GetTable(strSql.toString());
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from sizecode");
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

	public int Isused(long wareid) {
		if (wareid == 0 || Sizeid == 0) {
			errmess = "商品id或尺码id无效！";
			return 0;
		}
		String qry = "  select f_waresizeisused(" + wareid + "," + Sizeid + ") as usedbj from dual";
		if (Integer.parseInt(DbHelperSQL.RunSql(qry).toString()) == 1) {
			errmess = "该尺码已有库存，不允许取消使用！";
			return 1;
		} else {
			errmess = "该尺码无库存，可以取消使用！";
			return 0;
		}

	}

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select sizeid,sizename,sizeno,groupno,noused,statetag,lastop,accid";
		qry += " from sizecode where accid=" + Accid;
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

			sql.append("\n delete from sizecode where accid=" + Accid + ";");

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
			sql.append("\n " + headsql + " into sizecode (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
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