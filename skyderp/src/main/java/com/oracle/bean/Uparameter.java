package com.oracle.bean;

import java.sql.Types;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
//import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
//import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-20 17:00:59
//*************************************
public class Uparameter implements java.io.Serializable {
	// private Long Id;
	private Long Accid;
	private String Usection;
	private String Usymbol;
	// private String Uvalue1;
	// private Date Lastdate;
	private String Uvalue;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	// public void setId(Long id) {
	// this.Id = id;
	// }
	//
	// public Long getId() {
	// return Id;
	// }

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setUsection(String usection) {
		this.Usection = usection.toUpperCase();
	}

	public String getUsection() {
		return Usection;
	}

	public void setUsymbol(String usymbol) {
		this.Usymbol = usymbol.toUpperCase();
	}

	public String getUsymbol() {
		return Usymbol;
	}

	// public void setUvalue1(String uvalue1) {
	// this.Uvalue1 = uvalue1;
	// }
	//
	// public String getUvalue1() {
	// return Uvalue1;
	// }

	// public void setLastdate(Date lastdate) {
	// this.Lastdate = lastdate;
	// }

	// public String getLastdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Lastdate);
	// }

	public void setUvalue(String uvalue) {
		this.Uvalue = uvalue;
	}

	public String getUvalue() {
		return Uvalue;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	// public int Delete() {
	// StringBuilder strSql = new StringBuilder();
	// strSql.append("select count(*) as num from (");
	// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
	// strSql.append(")");
	// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
	// errmess = "当前记录已使用，不允许删除";
	// return 0;
	// }
	// strSql.setLength(0);
	// //strSql.append(" update uparameter set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
	// // strSql.append(" where id=" + id + " and accid=" + Accid);
	// if (DbHelperSQL.ExecuteSql(strSql.toString()) == 0) {
	// errmess = "删除失败！";
	// return 0;
	// }
	// return 1;
	// }

	// 增加记录
	// public int Append() {
	// StringBuilder strSql = new StringBuilder();
	// StringBuilder strSql1 = new StringBuilder();
	// StringBuilder strSql2 = new StringBuilder();
	// strSql1.append("id,");
	// strSql2.append("v_id,");
	// if (Id != null) {
	// strSql1.append("id,");
	// strSql2.append(Id + ",");
	// }
	// if (Accid != null) {
	// strSql1.append("accid,");
	// strSql2.append(Accid + ",");
	// }
	// if (Usection != null) {
	// strSql1.append("usection,");
	// strSql2.append("'" + Usection + "',");
	// }
	// if (Usymbol != null) {
	// strSql1.append("usymbol,");
	// strSql2.append("'" + Usymbol + "',");
	// }
	// if (Uvalue1 != null) {
	// strSql1.append("uvalue1,");
	// strSql2.append("'" + Uvalue1 + "',");
	// }
	// if (Lastdate != null) {
	// strSql1.append("lastdate,");
	// strSql2.append("'" + Lastdate + "',");
	// }
	// if (Uvalue != null) {
	// strSql1.append("uvalue,");
	// strSql2.append("'" + Uvalue + "',");
	// }
	// strSql1.append("lastdate");
	// strSql2.append("sysdate");
	// strSql.append("declare ");
	// strSql.append(" v_id number; ");
	// strSql.append(" begin ");
	// strSql.append(" v_id:=uparameter_id.nextval; ");
	// strSql.append(" insert into uparameter (");
	// strSql.append(" " + strSql1.toString());
	// strSql.append(")");
	// strSql.append(" values (");
	// strSql.append(" " + strSql2.toString());
	// strSql.append(");");
	// strSql.append(" select v_id into :id from dual; ");
	// strSql.append(" end;");
	// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
	// param.put("id", new ProdParam(Types.BIGINT));
	// int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
	// // id = Long.parseLong(param.get("id").getParamvalue().toString());
	// return ret;
	// }

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("   v_count number;");
		strSql.append(" begin");

		strSql.append(" select count(*) into v_count from uparameter where accid=" + Accid + " and usection='" + Usection + "' and usymbol='" + Usymbol + "' ;");
		strSql.append(" if v_count=0 then");
		strSql.append("    insert into uparameter (id,accid,usection,usymbol,uvalue,lastdate) ");
		strSql.append("    values (uparameter_id.nextval," + Accid + ", '" + Usection + "','" + Usymbol + "','" + Uvalue + "',sysdate);");
		strSql.append(" else ");
		strSql.append("    update  uparameter set uvalue='" + Uvalue + "',lastdate=sysdate");
		strSql.append("    where accid=" + Accid + " and usection='" + Usection + "' and usymbol='" + Usymbol + "';");
		strSql.append(" end if;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
		// strSql.append("begin ");
		// strSql.append(" update uparameter set ");
		// if (Id != null) {
		// strSql.append("id=" + Id + ",");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		// if (Usection != null) {
		// strSql.append("usection='" + Usection + "',");
		// }
		// if (Usymbol != null) {
		// strSql.append("usymbol='" + Usymbol + "',");
		// }
		// if (Uvalue1 != null) {
		// strSql.append("uvalue1='" + Uvalue1 + "',");
		// }
		// if (Lastdate != null) {
		// strSql.append("lastdate='" + Lastdate + "',");
		// }
		// if (Uvalue != null) {
		// strSql.append("uvalue='" + Uvalue + "',");
		// }
		// strSql.append("lastdate=sysdate");
		// strSql.append(" where id=id ;");
		// strSql.append(" commit;");
		// strSql.append(" end;");
	}

	public int UpdateX() {

		String codeFormat = "%03d";// %03d

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("   v_count number;");
		strSql.append("\n begin");
		strSql.append("\n    delete from  uparameter where accid=" + Accid + " and usection='" + Usection + "' and usymbol like '" + Usymbol + "#%' ;");
		String ss = Uvalue;
		int i = 1;
		while (ss.length() > 0) {
			String str = String.format(codeFormat, i);
			strSql.append("\n    insert into uparameter (id,accid,usection,usymbol,uvalue,lastdate) ");
			strSql.append("\n    values (uparameter_id.nextval," + Accid + ", '" + Usection + "','" + Usymbol + "#" + str + "','" + Func.subString(ss, 1, 800) + "',sysdate);");
			if (ss.length() > 800)
				ss = ss.substring(800);
			else
				ss = "";
			i++;
		}
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
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
	public DataSet GetList(String strWhere) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select uvalue ");
		strSql.append(" FROM uparameter ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表
	public String GetList() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select uvalue ");
		strSql.append(" FROM uparameter ");
		strSql.append(" where accid="+Accid+" and usection='"+Usection+"' and usymbol='"+Usymbol+"'");
		return DbHelperSQL.RunSql(strSql.toString()).toString();
	}

	// 获得数据列表
	public String GetListX(String strWhere) {

		Table tb = new Table();
		StringBuilder strSql = new StringBuilder();
		strSql.append("select uvalue ");
		strSql.append(" FROM uparameter ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		strSql.append(" order by id");

		tb = DbHelperSQL.Query(strSql.toString()).getTable(1);
		// {"total":1,"rows":[{
		errmess = "{\"total\":1,\"msg\":\"操作成功！\",\"rows\":[{\"UVALUE\":\"";
		for (int i = 0; i < tb.getRowCount(); i++) {
			errmess += tb.getRow(i).get("UVALUE");
		}

		errmess += "\"}]}";
		//System.out.println(strSql.toString())
		return errmess;
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM uparameter ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from uparameter");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	// 保存支付宝微信接口参数
	public int doSavePayparam(JSONObject jsonObject, int zfbok, int wxok) {

		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n begin";

		Iterator iterator = jsonObject.keys();
		while (iterator.hasNext()) {
			String usection = (String) iterator.next();
			//System.out.println(usection);
			if (usection.equals("pay_zfb") || usection.equals("pay_wx") || usection.equals("pay_lkl")) {
				// JSONArray jsonArray = jsonObject.getJSONArray(usection);
				qry += "\n   delete from UParameter where accid=" + Accid + " and usection='" + usection + "';";
				JSONObject jsonObject2 = jsonObject.getJSONArray(usection).getJSONObject(0);
				Iterator iterator2 = jsonObject2.keys();
				while (iterator2.hasNext()) {
					String key = (String) iterator2.next();
					String value = jsonObject2.get(key).toString();
					qry += "\n insert into UParameter (id,accid,usection,usymbol,uvalue)";
					qry += "\n values (UParameter_id.nextval," + Accid + ",'" + usection + "','" + key + "','" + value + "');";
				}
			}
		}

		if (zfbok == 0)
			qry += "\n update payway set statetag=2,noused=0 where accid=" + Accid + " and payno='Z';";
		else if (zfbok == 1)
			qry += "\n update payway set statetag=1,noused=0 where accid=" + Accid + " and payno='Z';";

		if (wxok == 0)
			qry += "\n update payway set statetag=2,noused=0 where accid=" + Accid + " and payno='W';";
		else if (wxok == 1)
			qry += "\n update payway set statetag=1,noused=0 where accid=" + Accid + " and payno='W';";

		qry += "\n end;";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 载入支付宝，微信,拉卡拉接口参数
	public String doLoadPayparam() {

		Table tb = new Table();
		String responsestr = "\"msg\":\"操作成功！\",";

		String qry = "select usymbol,uvalue from UParameter where accid=" + Accid + " and usection='pay_zfb' order by id";
		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		int rs = tb.getRowCount();
		responsestr += "\"pay_zfb\":[{";
		for (int i = 0; i < rs; i++) {
			responsestr += fh + "\"" + tb.getRow(i).get("USYMBOL").toString() + "\":\"" + tb.getRow(i).get("UVALUE").toString() + "\"";
			fh = ",";
		}
		responsestr += "}]";

		qry = "select usymbol,uvalue from UParameter where accid=" + Accid + " and usection='pay_wx' order by id";
		tb = DbHelperSQL.Query(qry).getTable(1);
		rs = tb.getRowCount();
		responsestr += ",\"pay_wx\":[{";
		fh = "";
		for (int i = 0; i < rs; i++) {
			responsestr += fh + "\"" + tb.getRow(i).get("USYMBOL").toString() + "\":\"" + tb.getRow(i).get("UVALUE").toString() + "\"";
			fh = ",";
		}
		responsestr += "}]";

		qry = "select usymbol,uvalue from UParameter where accid=" + Accid + " and usection='pay_lkl' order by id";
		tb = DbHelperSQL.Query(qry).getTable(1);
		rs = tb.getRowCount();
		responsestr += ",\"pay_lkl\":[{";
		fh = "";
		for (int i = 0; i < rs; i++) {
			responsestr += fh + "\"" + tb.getRow(i).get("USYMBOL").toString() + "\":\"" + tb.getRow(i).get("UVALUE").toString() + "\"";
			fh = ",";
		}
		responsestr += "}]";

		qry = "select count(*) as num from payway where accid=" + Accid + " and payno='Z' and statetag=1";
		if (DbHelperSQL.GetSingleCount(qry) > 0)
			responsestr += ",\"zfbok\":1";
		else
			responsestr += ",\"zfbok\":0";

		qry = "select count(*) as num  from payway where accid=" + Accid + " and payno='W' and statetag=1";
		if (DbHelperSQL.GetSingleCount(qry) > 0)
			responsestr += ",\"wxok\":1";
		else
			responsestr += ",\"wxok\":0";

		qry = "select count(*) as num  from payway where accid=" + Accid + " and payno='R' and statetag=1";
		if (DbHelperSQL.GetSingleCount(qry) > 0)
			responsestr += ",\"lklok\":1";
		else
			responsestr += ",\"lklok\":0";
		return responsestr;
	}

	// 取批发默认客户id
	public int doGetDefaultcust() {
		String qry = "select uvalue from uparameter where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTCUST'";
		String ss = DbHelperSQL.RunSql(qry).toString();
		if (ss == null || ss.equals("")) {
			errmess = "未设置批发默认客户";
			return 0;
		}
		qry = "select custid,custname,pricetype,discount,address from customer where accid=" + Accid + " and custid=" + ss;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "客户(" + ss + ")无效";
			return 0;
		}
		errmess = "\"msg\":\"操作成功！\",\"CUSTID\": \"" + tb.getRow(0).get("CUSTID").toString() + "\""//
				+ ",\"CUSTNAME\":\"" + tb.getRow(0).get("CUSTNAME").toString() + "\"" //
				+ ",\"DISCOUNT\":\"" + tb.getRow(0).get("DISCOUNT").toString() + "\"" //
				+ ",\"ADDRESS\":\"" + tb.getRow(0).get("ADDRESS").toString() + "\"" //
				+ ",\"PRICETYPE\":\"" + tb.getRow(0).get("PRICETYPE").toString() + "\"";
		return 1;
	}

	public int doWriteDefaultcust(long custid) {
		String qry = "";
		if (custid == 0) {
			qry = " delete from uparameter  where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTCUST' ";
		} else {
			qry = "declare ";
			qry += "   v_count number(10); ";
			qry += " begin ";
			qry += "   select count(*) into v_count from uparameter  where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTCUST'; ";
			qry += "   if v_count=0 then ";
			qry += "      insert into uparameter (id,accid,usection,usymbol,uvalue,lastdate)";
			qry += "      values (uparameter_id.nextval," + Accid + ",'OPTIONS','DEFAULTCUST','" + custid + "',sysdate);";
			qry += "   else";
			qry += "      update uparameter set uvalue='" + custid + "',lastdate=sysdate where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTCUST';";
			qry += "   end if;";
			qry += " end; ";
		}
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	public int doWriteDefaultcolor(long colorid) {
		String qry = "";
		if (colorid == 0) {
			qry = " delete from uparameter  where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTCOLOR' ";
		} else {
			qry = "declare ";
			qry += "   v_count number(10); ";
			qry += " begin ";
			qry += "   select count(*) into v_count from uparameter  where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTCOLOR'; ";
			qry += "   if v_count=0 then ";
			qry += "      insert into uparameter (id,accid,usection,usymbol,uvalue,lastdate)";
			qry += "      values (uparameter_id.nextval," + Accid + ",'OPTIONS','DEFAULTCOLOR','" + colorid + "',sysdate);";
			qry += "   else";
			qry += "      update uparameter set uvalue='" + colorid + "',lastdate=sysdate where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTCOLOR';";
			qry += "   end if;";
			qry += " end; ";
		}
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	public int doWriteDefaultsize(String sizegroupno) {
		//		if (sizegroupno.equals("")) {
		//			errmess = "尺码组名无效";
		//			return 0;
		//		}
		String qry = "";
		if (sizegroupno == "") {
			// WriteResult("0", "客户id无效");
			// return;
			qry = " delete from uparameter  where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTSIZE' ";
		} else {
			qry = "declare ";
			qry += "   v_count number(10); ";
			qry += " begin ";
			qry += "   select count(*) into v_count from uparameter  where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTSIZE'; ";
			qry += "   if v_count=0 then ";
			qry += "      insert into uparameter (id,accid,usection,usymbol,uvalue,lastdate)";
			qry += "      values (uparameter_id.nextval," + Accid + ",'OPTIONS','DEFAULTSIZE','" + sizegroupno + "',sysdate);";
			qry += "   else";
			qry += "      update uparameter set uvalue='" + sizegroupno + "',lastdate=sysdate where accid=" + Accid + " and usection='OPTIONS' and usymbol='DEFAULTSIZE';";
			qry += "   end if;";
			qry += " end; ";
		}
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

}