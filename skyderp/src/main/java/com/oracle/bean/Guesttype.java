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
//  @author:sunhong  @date:2017-03-21 10:17:37
//*************************************
public class Guesttype implements java.io.Serializable {
	private Long Vtid;
	private String Vtname;
	private Long Accid;
	private Float Discount;
	private Float Centrate;
	private Integer Statetag;
	private Integer Noused;
	private String Lastop;
	private Date Lastdate;
	private Integer Defbj;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setVtid(Long vtid) {
		this.Vtid = vtid;
	}

	public Long getVtid() {
		return Vtid;
	}

	public void setVtname(String vtname) {
		this.Vtname = vtname;
	}

	public String getVtname() {
		return Vtname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setDiscount(Float discount) {
		this.Discount = discount;
	}

	public Float getDiscount() {
		return Discount;
	}

	public void setCentrate(Float centrate) {
		this.Centrate = centrate;
	}

	public Float getCentrate() {
		return Centrate;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setDefbj(Integer defbj) {
		this.Defbj = defbj;
	}

	public Integer getDefbj() {
		return Defbj;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Vtid == 0) {
			errmess = "vtid参数未传入！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num from (");
		strSql.append("select guestid from guestvip where vtid=" + Vtid + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前类型已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update Guesttype set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where Vtid=" + Vtid + " and accid=" + Accid);
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

		if (Vtname == null || Vtname.trim().equals("")) {
			errmess = "会员类型名称不允许为空！";
			return 0;
		}
		if (Discount == null || Discount > 1)
			Discount =  (float) 1;
		if (Discount == 0) {
			errmess = "折扣不允许为零！";
			return 0;
		}
		if (Centrate == null)
			Centrate =  (float) 1;

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("vtid,");
		strSql2.append("v_id,");
		// if (Vtid != null) {
		// strSql1.append("vtid,");
		// strSql2.append(Vtid + ",");
		// }
		// if (Vtname != null) {
		strSql1.append("vtname,");
		strSql2.append("'" + Vtname + "',");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Discount != null) {
			strSql1.append("discount,");
			strSql2.append(Discount + ",");
		}
		if (Centrate != null) {
			strSql1.append("centrate,");
			strSql2.append(Centrate + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Defbj != null) {
			strSql1.append("defbj,");
			strSql2.append(Defbj + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Guesttype_vtid.nextval; ");
		strSql.append("   insert into Guesttype (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		if (Defbj != null && Defbj == 1) {
			strSql.append("  update guesttype set defbj=0 where accid=" + Accid + " and vtid<>v_id; ");
		}

		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Vtid = Long.parseLong(param.get("id").getParamvalue().toString());
			errmess =Vtid.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		if (Vtid==null || Vtid==0)
		{
			errmess="vtid参数无效！";
			return 0;
		}
		if (Discount != null && Discount == 0) {
			errmess = "折扣不允许为零！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		if (Vtid == 0) {
			strSql.append("  update guesttype set defbj=0 where accid=" + Accid + " ; ");

		} else {
			strSql.append("\n   update Guesttype set ");
			// if (Vtid != null) {
			// strSql.append("vtid=" + Vtid + ",");
			// }
			if (Vtname != null) {
				strSql.append("vtname='" + Vtname + "',");
			}
			// if (Accid != null) {
			// strSql.append("accid=" + Accid + ",");
			// }
			if (Discount != null) {
				if (Discount > 1)
					Discount = (float) 1;
				strSql.append("discount=" + Discount + ",");
			}
			if (Centrate != null) {
				strSql.append("centrate=" + Centrate + ",");
			}
			if (Statetag != null) {
				strSql.append("statetag=" + Statetag + ",");
			}
			if (Noused != null) {
				strSql.append("noused=" + Noused + ",");
			}
			// if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
			// }
			if (Defbj != null) {
				strSql.append("defbj=" + Defbj + ",");
			}
			strSql.append("lastdate=sysdate");
			strSql.append("  where vtid=" + Vtid + " and accid=" + Accid + " ;");

			if (Defbj != null && Defbj == 1) {
				strSql.append("\n  update guesttype set defbj=0 where accid=" + Accid + " and vtid<>" + Vtid + "; ");
			}

			strSql.append("\n  commit;");
		}
		strSql.append("\n end;");
		
//		System.out.println(strSql.toString());
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
		strSql.append(" FROM Guesttype ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Guesttype a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Guesttype");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
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
		String qry = "select vtid,vtname,discount,centrate,statetag,noused,lastop,accid";
		qry += " from guesttype where accid=" + Accid;
		if (downdate.length() <= 0) {
			qry += " and statetag=1 ";
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

			sql.append("\n delete from guesttype where accid=" + Accid + ";");

		for (int i = 0; i < tb.getRowCount(); i++) {
			String sql1 = "";//"accid";
			String sql2 = "";//Accid.toString();
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
				sql1 +=  strKey+",";
				sql2 +=  strValue+",";

			}
			sql.append("\n " + headsql + " into guesttype (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
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