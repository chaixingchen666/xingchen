package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-16 18:42:03
//*************************************
public class Cityhouseware implements java.io.Serializable {
	private Float Id;
	private Long Cthouseid;
	private Long Wareid;
	private String Warename0;
	private Long Ordid;
	private Float Citysale;
	private Float Haveamt;
	private Integer Newtag;
	private Integer Onlinetag;
	private String Opendate;
	private Date Lastdate;
	private String Lastop;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setCthouseid(Long cthouseid) {
		this.Cthouseid = cthouseid;
	}

	public Long getCthouseid() {
		return Cthouseid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setWarename0(String warename0) {
		this.Warename0 = warename0;
	}

	public String getWarename0() {
		return Warename0;
	}

	public void setOrdid(Long ordid) {
		this.Ordid = ordid;
	}

	public Long getOrdid() {
		return Ordid;
	}

	public void setCitysale(Float citysale) {
		this.Citysale = citysale;
	}

	public Float getCitysale() {
		return Citysale;
	}

	public void setHaveamt(Float haveamt) {
		this.Haveamt = haveamt;
	}

	public Float getHaveamt() {
		return Haveamt;
	}

	public void setNewtag(Integer newtag) {
		this.Newtag = newtag;
	}

	public Integer getNewtag() {
		return Newtag;
	}

	public void setOnlinetag(Integer onlinetag) {
		this.Onlinetag = onlinetag;
	}

	public Integer getOnlinetag() {
		return Onlinetag;
	}

	public void setOpendate(String opendate) {
		this.Opendate = opendate;
	}

	public String getOpendate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return Opendate;
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

	// 删除记录
	public int Delete() {
		if (Cthouseid == 0 || Wareid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append(" delete from  cityhouseware ");
		strSql.append(" where wareid=" + Wareid + " and cthouseid=" + Cthouseid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 清空记录
	public int Delete(int newtag) {
		if (Cthouseid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append(" delete from  cityhouseware ");
		strSql.append(" where cthouseid=" + Cthouseid);
		if (newtag < 2)
			strSql.append(" and newtag=" + newtag);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "清空失败！";
			return 0;
		} else {
			errmess = "清空成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(JSONObject jsonObject, int newtag, long accid) {
		if (!jsonObject.has("warereclist")) {
			errmess = "参数无效！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("warereclist");
		if (jsonArray.size() == 0) {
			errmess = "未选中商品！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_warename varchar2(100);");
		strSql.append("\n   v_citysale number;");
		strSql.append("\n begin ");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long wareid = Long.parseLong(jsonObject2.getString("wareid"));
			strSql.append("\n   select count(*) into v_count from cityhouseware where cthouseid=" + Cthouseid + " and wareid=" + wareid + ";");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      select warename,case sale4 when 0 then retailsale else sale4 end into v_warename,v_citysale from warecode where wareid=" + wareid + " and accid=" + accid + ";");
			strSql.append("\n      insert into cityhouseware (id,cthouseid,wareid,ordid,warename0,citysale,haveamt,newtag,onlinetag,opendate,lastdate,lastop) ");
			strSql.append("\n      values (cityhouseware_id.nextval," + Cthouseid + "," + wareid + ",9999,v_warename,v_citysale,0," + newtag + ",1,trunc(sysdate),sysdate,'" + Lastop + "');");
			strSql.append("\n   end if; ");
		}

		strSql.append("\n end;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

		// StringBuilder strSql1 = new StringBuilder();
		// StringBuilder strSql2 = new StringBuilder();
		// strSql1.append("id,");
		// strSql2.append("v_id,");
		// if (Id != null) {
		// strSql1.append("id,");
		// strSql2.append("'" + Id + "',");
		// }
		// if (Cthouseid != null) {
		// strSql1.append("cthouseid,");
		// strSql2.append(Cthouseid + ",");
		// }
		// if (Wareid != null) {
		// strSql1.append("wareid,");
		// strSql2.append(Wareid + ",");
		// }
		// if (Warename0 != null) {
		// strSql1.append("warename0,");
		// strSql2.append("'" + Warename0 + "',");
		// }
		// if (Ordid != null) {
		// strSql1.append("ordid,");
		// strSql2.append(Ordid + ",");
		// }
		// if (Citysale != null) {
		// strSql1.append("citysale,");
		// strSql2.append("'" + Citysale + "',");
		// }
		// if (Haveamt != null) {
		// strSql1.append("haveamt,");
		// strSql2.append("'" + Haveamt + "',");
		// }
		// if (Newtag != null) {
		// strSql1.append("newtag,");
		// strSql2.append(Newtag + ",");
		// }
		// if (Onlinetag != null) {
		// strSql1.append("onlinetag,");
		// strSql2.append(Onlinetag + ",");
		// }
		// if (Opendate != null) {
		// strSql1.append("opendate,");
		// strSql2.append("'" + Opendate + "',");
		// }
		// if (Lastop != null) {
		// strSql1.append("lastop,");
		// strSql2.append("'" + Lastop + "',");
		// }
		// strSql1.append("lastdate");
		// strSql2.append("sysdate");
		// strSql.append("declare ");
		// strSql.append(" v_id number; ");
		// strSql.append(" begin ");
		// strSql.append(" v_id:=cityhouseware_id.nextval; ");
		// strSql.append(" insert into cityhouseware (");
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
		// if (ret < 0) {
		// errmess = "操作异常！";
		// return 0;
		// } else {
		// errmess = "操作成功！";
		// return 1;
		// }
	}

	public int Updateall() {
		if (Cthouseid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update cityhouseware set ");

		if (Onlinetag != null) {
			strSql.append("onlinetag=" + Onlinetag + ",");
		}
		if (Opendate != null) {
			strSql.append("opendate=to_date('" + Opendate + "','yyyy-mm-dd'),");
		}
		strSql.append(" lastop='" + Lastop + "',");
		// }
		strSql.append(" lastdate=sysdate");
		strSql.append("  where  cthouseid=" + Cthouseid + " ;");
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

	// 修改记录
	public int Update() {
		if (Cthouseid == 0 || Wareid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update cityhouseware set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		// if (Cthouseid != null) {
		// strSql.append("cthouseid=" + Cthouseid + ",");
		// }
		// if (Wareid != null) {
		// strSql.append("wareid=" + Wareid + ",");
		// }
		if (Warename0 != null) {
			strSql.append("warename0='" + Warename0 + "',");
		}
		if (Ordid != null) {
			strSql.append("ordid=" + Ordid + ",");
		}
		if (Citysale != null) {
			strSql.append("citysale='" + Citysale + "',");
		}
		if (Haveamt != null) {
			strSql.append("haveamt='" + Haveamt + "',");
		}
		if (Newtag != null) {
			strSql.append("newtag=" + Newtag + ",");
		}
		if (Onlinetag != null) {
			strSql.append("onlinetag=" + Onlinetag + ",");
		}
		if (Opendate != null) {
			strSql.append("opendate=to_date('" + Opendate + "','yyyy-mm-dd'),");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where wareid=" + Wareid + " and cthouseid=" + Cthouseid + " ;");
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
		strSql.append(" FROM cityhouseware ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM cityhouseware a join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join waretype c on b.typeid=c.typeid");
		strSql.append("\n left outer join brand e on b.brandid=e.brandid");

		// qry += " from cityhouseware a join warecode b on a.wareid=b.wareid";
		// qry += " join cityhouse c on a.cthouseid=c.cthouseid";
		// qry += " join accreg d on c.accid=d.accid";

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable1(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM cityhouseware a join warecode b on a.wareid=b.wareid");
		strSql.append("\n join cityhouse c on a.cthouseid=c.cthouseid");
		strSql.append("\n join accreg d on c.accid=d.accid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from cityhouseware");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 获取商城店铺的商品信息
	public int doGetCityhouseware() {
		if (Wareid == 0 || Cthouseid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		String qry = "select a.wareid,a.warename0,a.citysale,a.haveamt,a.newtag,a.onlinetag,b.wareno,b.retailsale ";
		qry += " ,d.mobile,c.accid,c.tel,0 as jxstag,to_char(a.opendate,'yyyy-mm-dd')  as opendate";
		// qry += " ,f_accidisbuyer(" + accid + ",c.accid) as jxstag ";
		qry += " from cityhouseware a join warecode b on a.wareid=b.wareid ";
		qry += " join cityhouse c on a.cthouseid=c.cthouseid";
		qry += " join accreg d on c.accid=d.accid";
		qry += "  where a.CTHOUSEID=" + Cthouseid + " and a.wareid=" + Wareid;
//		System.out.println("000:"+qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);

		if (tb.getRowCount() == 0) {
			// WriteResultx("0","无最近售价记录！");
			errmess = "无商品记录！";
			return 0;
		}
		String responsestr = "\"msg\":\"操作成功！\",\"WAREID\":\"" + tb.getRow(0).get("WAREID").toString() + "\"" //
				+ ",\"WARENO\":\"" + tb.getRow(0).get("WARENO").toString() + "\""//
				+ ",\"WARENAME0\":\"" + tb.getRow(0).get("WARENAME0").toString() + "\"" //
				+ ",\"CITYSALE\":\"" + tb.getRow(0).get("CITYSALE").toString() + "\"" //
				+ ",\"RETAILSALE\":\"" + tb.getRow(0).get("RETAILSALE").toString() + "\"" //
				+ ",\"HAVEAMT\":\"" + tb.getRow(0).get("HAVEAMT").toString() + "\"" //
				+ ",\"ONLINETAG\":\"" + tb.getRow(0).get("ONLINETAG").toString() + "\""//
				+ ",\"NEWTAG\":\"" + tb.getRow(0).get("NEWTAG").toString() + "\"" //
				+ ",\"TEL\":\"" + tb.getRow(0).get("TEL").toString() + "\""//
				+ ",\"MOBILE\":\"" + tb.getRow(0).get("MOBILE").toString() + "\"" //
				+ ",\"JXSTAG\":\"" + tb.getRow(0).get("JXSTAG").toString() + "\"" //
				+ ",\"ACCID\":\"" + tb.getRow(0).get("ACCID").toString() + "\"" //
				+ ",\"OPENDATE\":\"" + tb.getRow(0).get("OPENDATE").toString() + "\"";
		qry = "select id,imagename from warepicture where wareid=" + Wareid + " order by orderid,id";
//		System.out.println("1111:"+qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		if (rs > 10)
			rs = 10;
		responsestr += ",\"picturenum\":\"" + rs + "\",\"picturelist\":[";
		String fh = "";
		for (int i = 0; i < rs; i++) {
			responsestr += fh + "{\"IMAGEID\":\"" + tb.getRow(i).get("ID").toString() + "\""//
					+ ",\"IMAGENAME\":\"" + tb.getRow(i).get("IMAGENAME").toString() + "\"}";
			fh = ",";
		}
		responsestr += "]";
		errmess = responsestr;
		return 1;
	}

	// 获取商城订货商品颜色及尺码数量明细
	public int doGetCityorderware(long accid) {

		// string qry = "select wareid,wareno,warename,entersale,retailsale,sale1,sale2,sale3,sale4,sale5,units,imagename from warecode where wareid=" + wareid;
		String qry = "select a.wareid,b.wareno,b.warename,b.retailsale,b.units,b.imagename0,a.citysale,a.warename0 ";
		qry += " from cityhouseware a join warecode b  on a.wareid=b.wareid where a.cthouseid=" + Cthouseid + " and a.wareid=" + Wareid;
		Table tb = new Table();
		// WriteLogTXT("debug", "getcityorderbywareid1", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		if (rs == 0) {
			errmess = "未找到商品！";
			return 0;
		}
		Float price = Float.parseFloat(tb.getRow(0).get("CITYSALE").toString());
		String retcs = "\"msg\":\"操作成功！\",\"WAREID\":\"" + tb.getRow(0).get("WAREID").toString() + "\"" + ",\"WARENO\":\"" + tb.getRow(0).get("WARENO").toString() + "\"" + ",\"WARENAME\":\"" + tb.getRow(0).get("WARENAME").toString()
				+ "\"" + ",\"WARENAME0\":\"" + tb.getRow(0).get("WARENAME0").toString() + "\"" + ",\"UNITS\":\"" + tb.getRow(0).get("UNITS").toString() + "\"" + ",\"RETAILSALE\":\""
				+ tb.getRow(0).get("RETAILSALE").toString() + "\"" + ",\"CITYSALE\":\"" + tb.getRow(0).get("CITYSALE").toString() + "\"" + ",\"IMAGENAME0\":\"" + tb.getRow(0).get("IMAGENAME0").toString() + "\"";

		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += " FROM warecolor a";
		qry += " left outer join colorcode b on a.colorid=b.colorid";
		qry += " where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0 order by b.colorno";
		// WriteLogTXT("debug", "getcityorderbywareid2", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		rs = tb.getRowCount();
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"colorlist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" + ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";
			fh = ",";
		}
		retcs += "]";

		// 取尺码
		qry = "select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += " left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += " where a.statetag=1 and a.noused=0 and b.wareid=" + Wareid;
		qry += " and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += " order by a.sizeno,a.sizename";

		// WriteLogTXT("debug", "getcityorderbywareid3", qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"sizelist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" + ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";
		// 取入库数

		qry = "  select colorid,sizeid,amount,price,curr";
		qry += " from cityorder a ";
		qry += " where ordaccid=" + accid + " and cthouseid=" + Cthouseid + " and wareid=" + Wareid;
		qry += " order by colorid";
		// WriteLogTXT("debug", "getcityorderbywareid4", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		retcs += ",\"amountlist\":[";
		Float totalcurr = (float) 0;
		Float totalamt = (float) 0;
		Float amount = (float) 0;
		Float curr = (float) 0;
		// Float price = 0;

		for (int i = 0; i < rs; i++) {
			amount = Float.parseFloat(tb.getRow(i).get("AMOUNT").toString());
			curr = Float.parseFloat(tb.getRow(i).get("CURR").toString());
			// price0 = Float.Parse(tb.getRow(i).get("price0").toString());
			retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" + ",\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" + ",\"AMOUNT\":\"" + amount + "\"}";
			fh = ",";
			// totalcurr += curr;
			totalamt += amount;
		}
		retcs += "]";
		totalcurr = price * totalamt;
		retcs += ",\"ZAMOUNT\":\"" + totalamt + "\"" + ",\"ZPRICE\":\"" + price + "\"" + ",\"ZCURR\":\"" + totalcurr + "\"";
		errmess = retcs;
		return 1;
	}

}