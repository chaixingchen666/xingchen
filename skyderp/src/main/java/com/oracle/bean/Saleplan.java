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
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-20 22:53:27
//*************************************
public class Saleplan implements java.io.Serializable {
	private Long Accid;
	private Integer Yearnum;
	private Float Curr1;
	private Float Curr2;
	private Float Curr3;
	private Float Curr4;
	private Float Curr5;
	private Float Curr6;
	private Float Curr7;
	private Float Curr8;
	private Float Curr9;
	private Float Curr10;
	private Float Curr11;
	private Float Curr12;
	private Float Curr;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setYearnum(Integer yearnum) {
		this.Yearnum = yearnum;
	}

	public Integer getYearnum() {
		return Yearnum;
	}

	public void setCurr1(Float curr1) {
		this.Curr1 = curr1;
	}

	public Float getCurr1() {
		return Curr1;
	}

	public void setCurr2(Float curr2) {
		this.Curr2 = curr2;
	}

	public Float getCurr2() {
		return Curr2;
	}

	public void setCurr3(Float curr3) {
		this.Curr3 = curr3;
	}

	public Float getCurr3() {
		return Curr3;
	}

	public void setCurr4(Float curr4) {
		this.Curr4 = curr4;
	}

	public Float getCurr4() {
		return Curr4;
	}

	public void setCurr5(Float curr5) {
		this.Curr5 = curr5;
	}

	public Float getCurr5() {
		return Curr5;
	}

	public void setCurr6(Float curr6) {
		this.Curr6 = curr6;
	}

	public Float getCurr6() {
		return Curr6;
	}

	public void setCurr7(Float curr7) {
		this.Curr7 = curr7;
	}

	public Float getCurr7() {
		return Curr7;
	}

	public void setCurr8(Float curr8) {
		this.Curr8 = curr8;
	}

	public Float getCurr8() {
		return Curr8;
	}

	public void setCurr9(Float curr9) {
		this.Curr9 = curr9;
	}

	public Float getCurr9() {
		return Curr9;
	}

	public void setCurr10(Float curr10) {
		this.Curr10 = curr10;
	}

	public Float getCurr10() {
		return Curr10;
	}

	public void setCurr11(Float curr11) {
		this.Curr11 = curr11;
	}

	public Float getCurr11() {
		return Curr11;
	}

	public void setCurr12(Float curr12) {
		this.Curr12 = curr12;
	}

	public Float getCurr12() {
		return Curr12;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
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
		StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update saleplan set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Yearnum != null) {
			strSql1.append("yearnum,");
			strSql2.append(Yearnum + ",");
		}
		if (Curr1 != null) {
			strSql1.append("curr1,");
			strSql2.append("'" + Curr1 + "',");
		}
		if (Curr2 != null) {
			strSql1.append("curr2,");
			strSql2.append("'" + Curr2 + "',");
		}
		if (Curr3 != null) {
			strSql1.append("curr3,");
			strSql2.append("'" + Curr3 + "',");
		}
		if (Curr4 != null) {
			strSql1.append("curr4,");
			strSql2.append("'" + Curr4 + "',");
		}
		if (Curr5 != null) {
			strSql1.append("curr5,");
			strSql2.append("'" + Curr5 + "',");
		}
		if (Curr6 != null) {
			strSql1.append("curr6,");
			strSql2.append("'" + Curr6 + "',");
		}
		if (Curr7 != null) {
			strSql1.append("curr7,");
			strSql2.append("'" + Curr7 + "',");
		}
		if (Curr8 != null) {
			strSql1.append("curr8,");
			strSql2.append("'" + Curr8 + "',");
		}
		if (Curr9 != null) {
			strSql1.append("curr9,");
			strSql2.append("'" + Curr9 + "',");
		}
		if (Curr10 != null) {
			strSql1.append("curr10,");
			strSql2.append("'" + Curr10 + "',");
		}
		if (Curr11 != null) {
			strSql1.append("curr11,");
			strSql2.append("'" + Curr11 + "',");
		}
		if (Curr12 != null) {
			strSql1.append("curr12,");
			strSql2.append("'" + Curr12 + "',");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
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
		strSql.append("   v_id:=saleplan_id.nextval; ");
		strSql.append("   insert into saleplan (");
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
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update saleplan set ");
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Yearnum != null) {
			strSql.append("yearnum=" + Yearnum + ",");
		}
		if (Curr1 != null) {
			strSql.append("curr1='" + Curr1 + "',");
		}
		if (Curr2 != null) {
			strSql.append("curr2='" + Curr2 + "',");
		}
		if (Curr3 != null) {
			strSql.append("curr3='" + Curr3 + "',");
		}
		if (Curr4 != null) {
			strSql.append("curr4='" + Curr4 + "',");
		}
		if (Curr5 != null) {
			strSql.append("curr5='" + Curr5 + "',");
		}
		if (Curr6 != null) {
			strSql.append("curr6='" + Curr6 + "',");
		}
		if (Curr7 != null) {
			strSql.append("curr7='" + Curr7 + "',");
		}
		if (Curr8 != null) {
			strSql.append("curr8='" + Curr8 + "',");
		}
		if (Curr9 != null) {
			strSql.append("curr9='" + Curr9 + "',");
		}
		if (Curr10 != null) {
			strSql.append("curr10='" + Curr10 + "',");
		}
		if (Curr11 != null) {
			strSql.append("curr11='" + Curr11 + "',");
		}
		if (Curr12 != null) {
			strSql.append("curr12='" + Curr12 + "',");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
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
		strSql.append(" FROM saleplan ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM saleplan ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from saleplan");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 载入计划
	public int Load() {
		if (Yearnum <= 2000 || Yearnum >= 3000) {
			errmess = "年份参数无效";
			return 0;
		}
		String retcs = "\"msg\":\"操作成功！\",";

		// 取颜色
		String qry = "select a.curr,a.curr1,a.curr2,a.curr3,a.curr4,a.curr5,a.curr6,a.curr7,a.curr8,a.curr9,a.curr10,a.curr11,a.curr12";
		qry += " FROM saleplan a ";
		qry += " where a.accid=" + Accid + " and a.yearnum=" + Yearnum;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);

		if (tb.getRowCount() == 0) {
			retcs += "\"CURR\":\"0\",\"CURR1\":\"0\",\"CURR2\":\"0\",\"CURR3\":\"0\",\"CURR4\":\"0\",\"CURR5\":\"0\",\"CURR6\":\"0\""
					+ ",\"CURR7\":\"0\",\"CURR8\":\"0\",\"CURR9\":\"0\",\"CURR10\":\"0\",\"CURR11\":\"0\",\"CURR12\":\"0\"";
		} else {
			retcs += "\"CURR\":\"" + tb.getRow(0).get("CURR").toString() + "\"";

			for (int i = 1; i <= 12; i++) {
				retcs += ",\"CURR" + i + "\":\"" + tb.getRow(0).get("CURR" + i).toString() + "\"";
			}
		}
		errmess = retcs;
		return 1;
	}

	// 保存计划
	public int Save() {
		if (Yearnum == null || Yearnum <= 2000 || Yearnum >= 3000) {

			errmess = "年份参数无效";
			return 0;
		}
		if (Curr1 == null)
			Curr1 = (float) 0;
		if (Curr2 == null)
			Curr2 = (float) 0;
		if (Curr3 == null)
			Curr3 = (float) 0;
		if (Curr4 == null)
			Curr4 = (float) 0;
		if (Curr5 == null)
			Curr5 = (float) 0;
		if (Curr6 == null)
			Curr6 = (float) 0;
		if (Curr7 == null)
			Curr7 = (float) 0;
		if (Curr8 == null)
			Curr8 = (float) 0;
		if (Curr9 == null)
			Curr9 = (float) 0;
		if (Curr10 == null)
			Curr10 = (float) 0;
		if (Curr11 == null)
			Curr11 = (float) 0;
		if (Curr12 == null)
			Curr12 = (float) 0;
		if (Curr == null)
			Curr = (float) 0;
		// if (Curr != Curr1 + Curr2 + Curr3 + Curr4 + Curr5 + Curr6 + Curr7 + Curr8 + Curr9 + Curr10 + Curr11 + Curr12) {
		float curr = Func.getRound(Curr1 + Curr2 + Curr3 + Curr4 + Curr5 + Curr6 + Curr7 + Curr8 + Curr9 + Curr10 + Curr11 + Curr12, 2);
		if (!Func.isEqual(Curr, curr)) {
			// errmess = "年计划不等于各月计划之和！";
			errmess = "年计划不等于各月计划之和！" + Curr + " ? " + curr;
			return 0;

		}
		String qry = "declare ";
		qry += "    v_count number(10);";
		qry += " begin ";
		qry += "    select count(*) into v_count from saleplan where accid=" + Accid + " and yearnum=" + Yearnum + "; ";
		qry += "    if v_count=0  then";
		qry += "       insert into saleplan (accid,yearnum,curr,curr1,curr2,curr3,curr4,curr5,curr6,curr7,curr8,curr9,curr10,curr11,curr12,lastop,lastdate) ";
		qry += "       values (" + Accid + "," + Yearnum + "," + Curr + "," + Curr1 + "," + Curr2 + "," + Curr3 + "," + Curr4 + "," + Curr5 + "," + Curr6 + "," + Curr7 + "," + Curr8 + "," + Curr9 + "," + Curr10 + ","
				+ Curr11 + "," + Curr12 + ",'" + Lastop + "',sysdate); ";
		qry += "    else ";
		qry += "       update saleplan set curr =" + Curr + ",curr1=" + Curr1 + ",curr2=" + Curr2 + ",curr3=" + Curr3 + ",curr4=" + Curr4 + ",curr5=" + Curr5 + ",curr6=" + Curr6;
		qry += "       ,curr7=" + Curr7 + ",curr8=" + Curr8 + ",curr9=" + Curr9 + ",curr10=" + Curr10 + ",curr11=" + Curr11 + ",curr12=" + Curr12;
		qry += "       ,lastop='" + Lastop + "',lastdate=sysdate";
		qry += "       where accid=" + Accid + " and yearnum=" + Yearnum + "; ";
		qry += "    end if; ";
		qry += " end; ";
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