/**
 * 
 */
package com.oracle.bean;

import com.comdot.data.Table;
import com.common.tool.Func;
//import com.common.tool.Func;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class vHousewhere {
	// private String Fieldlist = ""; // 要显示的项目列表
	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
	private String Sumlist = ""; // 要求和的项目列表
	// private String Calcfield = ""; // 要计算的项目列表

	private Long Userid = (long) 0;
	// private Long Accid = (long) 0;
	private String errmess = "";

	public String getErrmess() {
		return errmess;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	// public void setAccid(Long accid) {
	// Accid = accid;
	// }

	public int doTotal() {

		String qry = "declare ";
		// qry += " v_epid number(10);";
		// qry += " v_retsql nvarchar2(4000);";
		qry += "   v_retsql clob;";

		qry += "begin ";
		// qry += " v_epid:=" + userid + "; ";
		qry += "   DECLARE ";
		qry += "      CURSOR CUR_v_cxkczy_data IS ";
		qry += "      SELECT DISTINCT a.houseid,b.housename FROM v_cxkczy_data a ";
		qry += "      left outer join warehouse b on a.houseid=b.houseid where a.epid= " + Userid;
		qry += "      ORDER BY a.houseid;   ";
		qry += "      v_row CUR_v_cxkczy_data%rowtype; ";
		qry += "   begin ";
		qry += "      v_retsql:='sum(a.amount) as kchj'; ";
		qry += "      FOR v_row IN CUR_v_cxkczy_data LOOP ";
		qry += "        v_retsql:=v_retsql||',sum(decode(a.houseid,'||v_row.houseid||',a.amount)) as kc'||v_row.houseid; ";
		qry += "      end loop; ";
		qry += "   end; ";
		qry += "   select v_retsql into :retsql from dual;";
		qry += "end; ";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retsql", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		errmess = "\"msg\":\"操作成功！\",\"sumlist\":\"" + param.get("retsql").getParamvalue().toString() + "\"";
		// 各列名称及合计
		qry = "SELECT  a.houseid,b.housename,sum(a.amount) as amount FROM v_cxkczy_data a";

		qry += " left outer join warehouse b on a.houseid=b.houseid where a.epid=" + Userid;
		qry += " group BY a.houseid,b.housename  ";
		qry += " order by b.housename";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		Float totalamt = (float) 0;
		errmess += ",\"columns\":[";
		// String fh = "";
		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				errmess += ",";
			Float amount = Float.parseFloat(tb.getRow(i).get("AMOUNT").toString());
			errmess += "{\"houseid\":" + tb.getRow(i).get("HOUSEID").toString()//
					+ ",\"housename\":\"" + tb.getRow(i).get("HOUSENAME").toString() + "\"" //
					+ ",\"amount\":" + amount //
					// + ",\"curr\":" + tb.getRow(i).get("CURR").toString()
					+ "}";
			totalamt += amount;
		}
		errmess += "]";
		errmess += ",\"totalamt\":\"" + totalamt + "\"";

		return 1;
	}

	// 分页显示商品库存分布数据
	public Table GetTable(QueryParam qp) {

		Sumlist += ",min(id) as keyid";

		if (Func.isNull(Sortlist))
			Sortlist += "keyid";
		else
			Sortlist += ",keyid";

		String qry = "select " + Grouplist + "," + Sumlist;
		qry += "\n FROM v_cxkczy_data a";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		qry += "\n left outer join sizecode d on a.sizeid=d.sizeid";
		qry += "\n where a.epid=" + Userid;
		qry += "\n group by " + Func.delGroupOfAs(Grouplist);
		// a.wareid,b.wareno,b.warename,b.units,b.retailsale,a.colorid,c.colorname,a.sizeid,d.sizename,d.sizeno,b.locale,b.imagename0

		qp.setQueryString(qry);
		qp.setSortString(Sortlist);
		// System.out.println(qry);
		// qp.setSumString("nvl(sum(amtkchj),0) as totalkcamt,nvl(sum(amthj),0)
		// as totalamt,nvl(sum(curhj),0) as totalcurr");

		return DbHelperSQL.GetTable(qp);
	}

}
