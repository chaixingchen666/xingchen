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
//import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-06-06 20:00:20
//*************************************
public class Warewarn implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private Long Houseid;
	private Long Wareid;
	private Long Colorid;
	private Long Sizeid;
	private Float Minamt;
	private Float Maxamt;
	private String Wareno;
	private String Housename;
	private String Colorname;
	private String Sizename;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setColorid(Long colorid) {
		this.Colorid = colorid;
	}

	public Long getColorid() {
		return Colorid;
	}

	public void setSizeid(Long sizeid) {
		this.Sizeid = sizeid;
	}

	public Long getSizeid() {
		return Sizeid;
	}

	public void setMinamt(Float minamt) {
		this.Minamt = minamt;
	}

	public Float getMinamt() {
		return Minamt;
	}

	public void setMaxamt(Float maxamt) {
		this.Maxamt = maxamt;
	}

	public Float getMaxamt() {
		return Maxamt;
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
		if (Houseid == 0 || Wareid == 0 || Colorid == 0) {
			errmess = "houseid或wareid或colorid不是一个有效值！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append(" delete warewarn ");
		strSql.append(" where accid=" + Accid + " and houseid=" + Houseid + " and wareid=" + Wareid + " and colorid=" + Colorid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(JSONObject jsonObject) {

		Wareid = jsonObject.has("wareid") ? Long.parseLong(jsonObject.getString("wareid")) : 0;
		// Colorid = jsonObject.has("colorid") ? Long.parseLong(jsonObject.getString("colorid")) : 0;
		if (Wareid == 0) {
			errmess = "houseid或wareid或colorid不是一个有效值！";
			return 0;
		}
		if (!jsonObject.has("amountlist")) {
			errmess = "amountlist不是一个有效值！";
			return 0;
		}

		if (!jsonObject.has("houselist")) {
			errmess = "houselist不是一个有效值！";
			return 0;
		}
		String qry = "declare";
		qry += "\n   v_count number; ";
		qry += "\n begin ";
		JSONArray sizeamtArray = jsonObject.getJSONArray("amountlist");
		JSONArray houseArray = jsonObject.getJSONArray("houselist");
		for (int i = 0; i < houseArray.size(); i++) {
			JSONObject jsonObject2 = houseArray.getJSONObject(i);
			Houseid = Long.parseLong(jsonObject2.getString("houseid"));
			for (int j = 0; j < sizeamtArray.size(); j++) {
				JSONObject jsonObject3 = sizeamtArray.getJSONObject(j);
				Colorid = Long.parseLong(jsonObject3.getString("colorid"));
				Sizeid = Long.parseLong(jsonObject3.getString("sizeid"));
				Minamt = !jsonObject3.has("minamt") || jsonObject3.getString("minamt").length() <= 0 ? null : Float.parseFloat(jsonObject3.getString("minamt"));
				Maxamt = !jsonObject3.has("maxamt") || jsonObject3.getString("maxamt").length() <= 0 ? null : Float.parseFloat(jsonObject3.getString("maxamt"));
				qry += "\n   delete from warewarn where accid=" + Accid + " and houseid=" + Houseid + " and Wareid=" + Wareid + " and colorid=" + Colorid + " and sizeid=" + Sizeid + ";";
				qry += "\n   insert into warewarn (id,accid,houseid,wareid,colorid,sizeid,minamt,maxamt,lastop)";
				qry += "\n   values (warewarn_id.nextval," + Accid + "," + Houseid + "," + Wareid + "," + Colorid + "," + Sizeid + "," + Minamt + "," + Maxamt + ",'" + Lastop + "'); ";
			}
		}
		qry += "\n end; ";

		if (DbHelperSQL.ExecuteSql(qry) < 0)

		{
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
		strSql.append("   update warewarn set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Colorid != null) {
			strSql.append("colorid=" + Colorid + ",");
		}
		if (Sizeid != null) {
			strSql.append("sizeid=" + Sizeid + ",");
		}
		if (Minamt != null) {
			strSql.append("minamt='" + Minamt + "',");
		}
		if (Maxamt != null) {
			strSql.append("maxamt='" + Maxamt + "',");
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
		strSql.append(" FROM warewarn ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM warewarn a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode e on a.sizeid=e.sizeid");
		strSql.append("\n left outer join warehouse d on a.houseid=d.houseid");
		if (strWhere.length() > 0) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from warewarn");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	public int Load() {
		if (Wareid == 0 || Houseid == 0) {
			errmess = "wareid或houseid不是一个有效值！";
			return 0;
		}
		Table tb = new Table();
		String qry = "";
		String retcs = "";
		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += "\n  FROM warecolor a";
		qry += "\n  left outer join colorcode b on a.colorid=b.colorid";
		qry += "\n  where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0 order by b.colorno";
		tb = DbHelperSQL.Query(qry).getTable(1);
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs = "\"msg\":\"操作成功\",\"colorlist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\""//
					+ ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";

		}
		retcs += "]";
		// 取尺码
		qry = "select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += "\n  left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += "\n  where a.statetag=1 and a.noused=0 and a.accid=" + Accid + " and b.wareid=" + Wareid;
		qry += "\n  and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += "\n  order by a.sizeno,a.sizename";

		tb = DbHelperSQL.Query(qry).getTable(1);

		retcs += ",\"sizelist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //
					+ ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";

		}
		retcs += "]";
		// 取入库数

		qry = "  select colorid,sizeid,minamt,maxamt";
		qry += "\n  from warewarn a ";
		qry += "\n  where accid=" + Accid + " and wareid=" + Wareid + " and houseid=" + Houseid;
		qry += "\n  order by colorid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		retcs += ",\"amountlist\":[";
		// Float totalcurr = (float) 0;
		// Float totalamt = (float) 0;
		for (int i = 0; i < rs; i++) {
			if (i > 0)
				retcs += ",";
			// Minamt = Float.parseFloat(tb.getRow(i).get("MINAMT").toString());
			// Maxamt = Float.parseFloat(tb.getRow(i).get("MAXAMT").toString());
			retcs += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" //
					+ ",\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //

					+ ",\"MINAMT\":\"" + tb.getRow(i).get("MINAMT").toString() + "\"" //
					+ ",\"MAXAMT\":\"" + tb.getRow(i).get("MAXAMT").toString() + "\"}";
			// totalcurr += Curr;
			// totalamt += Amount;
		}
		retcs += "]";
		errmess = retcs;
		return 1;
	}

	public int doGetColorsumList(QueryParam qp, int sizenum, String strwhere) {
		String qry = "select a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname,a.houseid,d.housename";
		qry += ", b.retailsale ";
		qry += ",sum(a.minamt) as minamt,sum(a.maxamt) as maxamt,min(a.id) as keyid";
		qry += "\n from warewarn a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		qry += "\n left outer join warehouse d on a.houseid=d.houseid";
		if (strwhere.length() > 0)
			qry += "\n where " + strwhere;
		qry += "\n group by a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname";
		qry += ",a.houseid,d.housename,b.retailsale";

		Table tb = new Table();
		qp.setQueryString(qry);
		// qp.setSumString("nvl(sum(amount),0) as totalamt");
		tb = DbHelperSQL.GetTable(qp);
		int count = tb.getRowCount();
		String sqlsize = "";
		String sqlsum = "";
		String qry1;
		// long wareid;
		long wareid0 = 0;
		// String fh1="";
		String fh = "";
		// long colorid;
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";
		for (int i = 0; i < count; i++) {
			Houseid = Long.parseLong(tb.getRow(i).get("HOUSEID").toString());
			Wareid = Long.parseLong(tb.getRow(i).get("WAREID").toString());
			Colorid = Long.parseLong(tb.getRow(i).get("COLORID").toString());
			if (Wareid != wareid0) {
				SizeParam sp = new SizeParam(Wareid, sizenum, 3); // fs 0=普通，1=盘点，2=临时盘点,3=库存上下限报警
				sqlsize = sp.getSqlSize();
				sqlsum = sp.getSqlSum();
				wareid0 = Wareid;
			}
			//System.out.println(sqlsum);
			retcs += fh + "{";
			String fh1 = "";
			for (int j = 0; j < tb.getColumnCount(); j++) {
				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();
				String strValue = tb.getRow(i).get(j).toString();
				retcs += fh1 + "\"" + strKey + "\":\"" + strValue + "\"";
				// jsonString.append(fh1 + "\"" + strKey + "\":\"" + strValue + "\"");
				fh1 = ",";
			}

			// 取尺码数据
			qry1 = "select " + sqlsum + " \n ," + sqlsize;
			qry1 += "\n from warewarn a where accid=" + Accid + " and houseid=" + Houseid;
			qry1 += "\n and wareid=" + Wareid + " and colorid=" + Colorid;
			//System.out.println(qry1);

			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
			//System.out.println("22222");

			for (int j = 1; j <= sizenum; j++) {
				retcs += ",\"MINAMT" + j + "\":\"" + tb1.getRow(0).get("MINAMT" + j).toString() + "\"" //
						+ ",\"MAXAMT" + j + "\":\"" + tb1.getRow(0).get("MAXAMT" + j).toString() + "\"" //
						+ ",\"SIZEID" + j + "\":\"" + tb1.getRow(0).get("SIZEID" + j).toString() + "\"" //
						+ ",\"SIZENAME" + j + "\":\"" + tb1.getRow(0).get("SIZENAME" + j).toString() + "\"";
			}

			retcs += "}";
			fh = ",";

		}
		retcs += "]}";
		errmess = retcs;
		return 1;
	}

	public int LoadFromXLS() {
		if (Func.isNull(Housename)) {
			errmess = "店铺无效！";
			return 0;
		}
		if (Func.isNull(Wareno)) {
			errmess = "货号无效！";
			return 0;
		}
		if (Func.isNull(Colorname)) {
			errmess = "颜色无效！";
			return 0;
		}
		if (Func.isNull(Sizename)) {
			errmess = "尺码无效！";
			return 0;
		}
		if (Minamt == null) {
			errmess = "最低库存无效！";
			return 0;

		}
		if (Maxamt == null) {
			errmess = "最高库存无效！";
			return 0;

		}
		String qry = "declare ";
		qry += "\n    v_count  number;";
		qry += "\n    v_wareid  number(10);";
		qry += "\n    v_houseid  number(10);";
		qry += "\n    v_colorid  number(10);";
		qry += "\n    v_sizeid  number(10);";
		qry += "\n    v_retcs  varchar2(100);";
		qry += "\n    v_sizegroupno  varchar2(60);";

		qry += "\n begin";
		qry += "\n   begin ";
		qry += "\n     select houseid into v_houseid from warehouse where accid= " + Accid + " and Housename='" + Housename + "' and statetag=1 and rownum=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:= '0店铺:" + Housename + " 未找到!' ; ";
		qry += "\n     goto exit; ";
		qry += "\n   end; ";

		qry += "\n   begin ";
		qry += "\n     select wareid,sizegroupno into v_wareid,v_sizegroupno from warecode where accid= " + Accid + " and wareno='" + Wareno + "' and statetag=1 and rownum=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:= '0货号:" + Wareno + " 未找到!' ; ";
		qry += "\n     goto exit; ";
		qry += "\n   end; ";

		qry += "\n   begin ";
		qry += "\n     select colorid into v_colorid from colorcode where accid= " + Accid + " and Colorname='" + Colorname + "' and statetag=1 and rownum=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:= '0颜色:" + Colorname + " 未找到!' ; ";
		qry += "\n     goto exit; ";
		qry += "\n   end; ";

		qry += "\n   begin ";
		qry += "\n     select sizeid into v_sizeid from sizecode where accid= " + Accid + " and sizename='" + Sizename + "' and groupno=v_sizegroupno and statetag=1 and rownum=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:= '0尺码:" + Sizename + " 未找到!' ; ";
		qry += "\n     goto exit; ";
		qry += "\n   end; ";
		qry += "\n   insert into warewarn (id,houseid,wareid,colorid,sizeid,minamt,maxamt,lastop)";
		qry += "\n   values (warewarn_id.nextval,v_houseid,v_wareid,v_colorid,v_sizeid," + Minamt + "," + Maxamt + ",'" + Lastop + "');";

		qry += "\n   v_retcs:= '1操作成功!' ; ";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
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
}