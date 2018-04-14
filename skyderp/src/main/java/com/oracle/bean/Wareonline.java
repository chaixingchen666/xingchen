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
//  @author:sunhong  @date:2017-03-14 19:47:44
//*************************************
public class Wareonline implements java.io.Serializable {
	private Long Onhouseid;
	private Long Wareid;
	private String Remark;
	private Float Saleprice;
	private Float Saleamt;
	private Integer Uptag;
	private Integer Statetag;
	private Date Lastdate;
	private String Lastop;
	private String Infocs;
	private Long Pageview;
	private Long Agreeview;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setOnhouseid(Long Onhouseid) {
		this.Onhouseid = Onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setWareid(Long Wareid) {
		this.Wareid = Wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setSaleprice(Float saleprice) {
		this.Saleprice = saleprice;
	}

	public Float getSaleprice() {
		return Saleprice;
	}

	public void setSaleamt(Float saleamt) {
		this.Saleamt = saleamt;
	}

	public Float getSaleamt() {
		return Saleamt;
	}

	public void setUptag(Integer uptag) {
		this.Uptag = uptag;
	}

	public Integer getUptag() {
		return Uptag;
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

	public void setInfocs(String infocs) {
		this.Infocs = infocs;
	}

	public String getInfocs() {
		return Infocs;
	}

	public void setPageview(Long pageview) {
		this.Pageview = pageview;
	}

	public Long getPageview() {
		return Pageview;
	}

	public void setAgreeview(Long agreeview) {
		this.Agreeview = agreeview;
	}

	public Long getAgreeview() {
		return Agreeview;
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
		strSql.append("select count(*) as num from (");
		// strSql.append("select Wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		// strSql.append(" update Wareonline set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		return 1;
	}

	// 增加记录
	public int Append() {

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("Onhouseid,");
		strSql2.append("v_id,");
		// if (Onhouseid != null) {
		// strSql1.append("Onhouseid,");
		// strSql2.append(Onhouseid + ",");
		// }
		if (Wareid != null) {
			strSql1.append("Wareid,");
			strSql2.append(Wareid + ",");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Saleprice != null) {
			strSql1.append("saleprice,");
			strSql2.append("'" + Saleprice + "',");
		}
		if (Saleamt != null) {
			strSql1.append("saleamt,");
			strSql2.append("'" + Saleamt + "',");
		}
		if (Uptag != null) {
			strSql1.append("uptag,");
			strSql2.append(Uptag + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Infocs != null) {
			strSql1.append("infocs,");
			strSql2.append("'" + Infocs + "',");
		}
		if (Pageview != null) {
			strSql1.append("pageview,");
			strSql2.append(Pageview + ",");
		}
		if (Agreeview != null) {
			strSql1.append("agreeview,");
			strSql2.append(Agreeview + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Wareonline_id.nextval; ");
		strSql.append("   insert into Wareonline (");
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
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Wareonline set ");
		if (Onhouseid != null) {
			strSql.append("Onhouseid=" + Onhouseid + ",");
		}
		if (Wareid != null) {
			strSql.append("Wareid=" + Wareid + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Saleprice != null) {
			strSql.append("saleprice='" + Saleprice + "',");
		}
		if (Saleamt != null) {
			strSql.append("saleamt='" + Saleamt + "',");
		}
		if (Uptag != null) {
			strSql.append("uptag=" + Uptag + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Infocs != null) {
			strSql.append("infocs='" + Infocs + "',");
		}
		if (Pageview != null) {
			strSql.append("pageview=" + Pageview + ",");
		}
		if (Agreeview != null) {
			strSql.append("agreeview=" + Agreeview + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Wareonline ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Wareonline a join warecode b on a.wareid=b.wareid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Wareonline");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	public int doGetWaredisplay() {
		if (Onhouseid == 0 || Wareid == 0) {
			errmess = "线上店铺id或商品id无效！";
			return 0;
		}
		Table tb = new Table();
		String qry = " select a.Wareid,a.remark,b.wareremark,b.units,b.wareno,a.saleprice,a.infocs,a.uptag,a.statetag,b.imagename0  ";
		qry += " from wareonline a left outer join warecode b on a.Wareid=b.Wareid where a.Onhouseid=" + Onhouseid + " and a.Wareid=" + Wareid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();
		if (rs == 0) {
			qry = "select Wareid,wareremark,units,wareno,retailsale as saleprice,'' as infocs,0 as uptag,0 as statetag,imagename0 from warecode where Wareid=" + Wareid;
			tb = DbHelperSQL.Query(qry).getTable(1);
			rs = tb.getRowCount();
			if (rs == 0) {
				errmess = "未找到记录！";
				return 0;
			}
		}
		String retcs = "\"msg\":\"操作成功！\",\"WAREID\":\"" + tb.getRow(0).get("WAREID").toString() + "\"" + ",\"REMARK\":\"" + tb.getRow(0).get("WAREREMARK").toString() + "\"" + ",\"WAREREMARK\":\""
				+ tb.getRow(0).get("WAREREMARK").toString() + "\"" + ",\"UNITS\":\"" + tb.getRow(0).get("UNITS").toString() + "\"" + ",\"WARENO\":\"" + tb.getRow(0).get("WARENO").toString() + "\"" + ",\"INFOCS\":\""
				+ tb.getRow(0).get("INFOCS").toString() + "\"" + ",\"UPTAG\":\"" + tb.getRow(0).get("UPTAG").toString() + "\"" + ",\"STATETAG\":\"" + tb.getRow(0).get("STATETAG").toString() + "\"" + ",\"SALEPRICE\":\""
				+ tb.getRow(0).get("SALEPRICE").toString() + "\"" + ",\"IMAGENAME0\":\"" + tb.getRow(0).get("IMAGENAME0").toString() + "\"";
		// 陈列图片

		qry = "select id,imagename from warepicture where Wareid=" + Wareid + " order by orderid,id";
		tb = DbHelperSQL.Query(qry).getTable(1);
		rs = tb.getRowCount();
		if (rs > 5)
			rs = 5;
		retcs += ",\"picturelist\":[";
		String fh = "";
		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"IMAGEID\":\"" + tb.getRow(i).get("ID").toString() + "\"" + ",\"IMAGENAME\":\"" + tb.getRow(i).get("IMAGENAME").toString() + "\"}";
			fh = ",";
		}
		retcs += "]";

		// 商品颜色

		// 取颜色
		qry = "select a.colorid,b.colorname,b.colorno,1 as selbj from waredisplaycolor a";
		qry += " left outer join colorcode b on a.colorid=b.colorid ";
		qry += " where a.Wareid=" + Wareid + " and a.Onhouseid=" + Onhouseid + " and b.statetag=1 and b.noused=0";
		qry += " union all";
		qry += " select a.colorid,b.colorname,b.colorno,0 as selbj";
		qry += " FROM warecolor a";
		qry += " left outer join colorcode b on a.colorid=b.colorid";
		qry += " where a.Wareid=" + Wareid + " and b.statetag=1 and b.noused=0";
		qry += " and not exists (select 1 from waredisplaycolor a1 where a1.Onhouseid=" + Onhouseid + " and a.colorid=a1.colorid and a1.Wareid=" + Wareid + ")";
		qry += " order by colorno,colorid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		rs = tb.getRowCount();

		retcs += ",\"colorlist\":[";
		fh = "";
		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" + ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"" + ",\"SELBJ\":\"" + tb.getRow(i).get("SELBJ").toString()
					+ "\"}";
			fh = ",";

		}
		retcs += "]";

		// 取尺码
		qry = "select a.sizeid,b.sizename,b.sizeno,1 as selbj from waredisplaysize a";
		qry += " left outer join sizecode b on a.sizeid=b.sizeid ";
		qry += " where a.Wareid=" + Wareid + " and a.Onhouseid=" + Onhouseid + " and b.statetag=1 and b.noused=0";
		qry += " union all";
		qry += " select a.sizeid,a.sizename,a.sizeno,0 as selbj";
		qry += " FROM sizecode a ";
		qry += " where exists (select 1 from warecode b where a.accid=b.accid and a.groupno=b.sizegroupno and b.Wareid=" + Wareid + ")";
		qry += " and a.statetag=1 and a.noused=0";
		qry += " and not exists (select 1 from waredisplaysize a1 where a1.Onhouseid=" + Onhouseid + " and  a.sizeid=a1.sizeid and a1.Wareid=" + Wareid + ")";
		qry += " order by sizeno,sizeid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		// {"colorlist":[{"COLORID":"1","COLORNAME":"红色"},{"COLORID":"2","COLORNAME":"黄色"}]
		// ,"sizelist":[{"SIZEID":"1","SIZENAME":"XS","SIZENO":"01"},{"SIZEID":"2","SIZENAME":"S","SIZENO":"02"},{"SIZEID":"3","SIZENAME":"M","SIZENO":"03"},{"SIZEID":"4","SIZENAME":"L","SIZENO":"04"},{"SIZEID":"5","SIZENAME":"XL","SIZENO":"05"},{"SIZEID":"6","SIZENAME":"XXL","SIZENO":"06"},{"SIZEID":"7","SIZENAME":"3XL","SIZENO":"07"}]}

		retcs += ",\"sizelist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" + ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"" + ",\"SELBJ\":\"" + tb.getRow(i).get("SELBJ").toString()
					+ "\"}";
			fh = ",";

		}
		retcs += "]";
		// 取该商品所属分组

		qry = " select a.dspid,b.dspname,b.orderid,1 as selbj from waredisplay a join displaycode b on a.dspid=b.dspid ";
		qry += " where a.Wareid=" + Wareid + " and b.usetag=1 and b.statetag=1 ";
		qry += " and b.Onhouseid=" + Onhouseid;
		qry += " union all";
		qry += " select b.dspid,b.dspname,b.orderid,0 as selbj";
		qry += " FROM displaycode b ";
		qry += " where b.usetag=1 and b.statetag=1 ";
		qry += " and b.Onhouseid=" + Onhouseid;
		qry += " and not exists (select 1 from waredisplay a where a.Wareid=" + Wareid + " and a.dspid=b.dspid)";

		qry += " order by orderid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		retcs += ",\"dsplist\":[";
		rs = tb.getRowCount();
		fh = "";
		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"DSPID\":\"" + tb.getRow(i).get("DSPID").toString() + "\"" + ",\"DSPNAME\":\"" + tb.getRow(i).get("DSPNAME").toString() + "\"" + ",\"SELBJ\":\"" + tb.getRow(i).get("SELBJ").toString()
					+ "\"}";
			fh = ",";
		}
		// }
		retcs += "]";

		qry = "select a.Wareid1,b.wareno,b.imagename0 from wareonline1 a join warecode b on a.Wareid1=b.Wareid";
		qry += " where a.Onhouseid=" + Onhouseid + " and a.Wareid=" + Wareid + " order by a.id";

		tb = DbHelperSQL.Query(qry).getTable(1);
		retcs += ",\"warelist\":[";
		rs = tb.getRowCount();
		fh = "";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"WAREID\":\"" + tb.getRow(i).get("WAREID1").toString() + "\"" + ",\"WARENO\":\"" + tb.getRow(i).get("WARENO").toString() + "\"" + ",\"IMAGENAME0\":\""
					+ tb.getRow(i).get("IMAGENAME0").toString() + "\"}";
			fh = ",";

		}
		// }
		retcs += "]";
		errmess = retcs;
		return 1;
	}

	// 保存陈列商品信息
	public int doWriteWaredisplay(JSONObject jsonObject) {

		if (Onhouseid == null || Wareid == null || Onhouseid == 0 || Wareid == 0) {
			errmess = "缺少线上店铺id或商品id参数！";
			return 0;
		}
		//System.out.println("1111");
		String sqltext1 = "wareid";
		String sqltext2 = Wareid.toString();
		String sqltext3 = " lastdate=sysdate,lastop='" + Lastop + "'";
		sqltext1 += ",onhouseid";
		sqltext2 += "," + Onhouseid;
		if (Remark != null) {
			sqltext1 += ",remark";
			sqltext2 += ",'" + Remark + "'";
			sqltext3 += ",remark='" + Remark + "'";
		}
		if (Saleprice != null) {
			sqltext1 += ",saleprice";
			sqltext2 += "," + Saleprice.toString();
			sqltext3 += ",saleprice=" + Saleprice.toString();
		}
		if (Infocs != null) {
			sqltext1 += ",infocs";
			sqltext2 += ",'" + Infocs + "'";
			sqltext3 += ",infocs='" + Infocs + "'";
		}
		if (Uptag != null) {
			sqltext1 += ",uptag";
			sqltext2 += "," + Uptag;
			sqltext3 += ",uptag=" + Uptag;
		}
		sqltext1 += ",lastop,lastdate,statetag";
		sqltext2 += ",'" + Lastop + "',sysdate,1";
		//System.out.println("2222");
		String qry = "declare ";
		qry += "\n   v_count number(10); ";
		qry += "\n   v_imagename varchar2(60); ";
		qry += "\n begin ";
		if (Remark != null) {
			qry += "\n   update warecode set wareremark='" + Remark + "' where wareid=" + Wareid + ";";
		}
		qry += "\n   select count(*) into v_count from wareonline where onhouseid=" + Onhouseid + " and wareid=" + Wareid + ";";
		qry += "\n   if v_count>0 then ";
		qry += "\n      update wareonline set " + sqltext3;
		qry += "\n      where  onhouseid=" + Onhouseid + " and wareid=" + Wareid + "; ";
		qry += "\n   else ";
		qry += "\n      insert into wareonline (" + sqltext1 + ")";
		qry += "\n      values (" + sqltext2 + ");";
		qry += "\n   end if ;";
		// 将商品添加到商品陈列类别
		// if (ht["dspcount"] != null)
		if (jsonObject.has("dspidlist")) {
			// 写颜色
			qry += "\n   delete from waredisplay a where wareid=" + Wareid + " and exists (select 1 from displaycode b where a.dspid=b.dspid and b.onhouseid=" + Onhouseid + ");";
			JSONArray jsonArray = jsonObject.getJSONArray("dspidlist");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long dspid = Long.parseLong(jsonObject2.getString("dspid"));
				// value = int.Parse(ht["value" + i].ToString());
				qry += "\n      insert into  waredisplay (wareid,dspid) values (" + Wareid + "," + dspid + ");";
			}
		}
		// if (ht["colorcount"] != null)
		if (jsonObject.has("coloridlist")) {
			// 写颜色
			qry += "\n   delete from waredisplaycolor where onhouseid=" + Onhouseid + " and wareid=" + Wareid + ";";
			JSONArray jsonArray = jsonObject.getJSONArray("coloridlist");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long colorid = Long.parseLong(jsonObject2.getString("colorid"));
				// value = int.Parse(ht["value" + i].ToString());
				qry += "\n   insert into waredisplaycolor (onhouseid,wareid,colorid,lastop,lastdate)";
				qry += "\n   values (" + Onhouseid + "," + Wareid + "," + colorid + ",'" + Lastop + "',sysdate); ";
			}
		}
		// if (ht["sizecount"] != null)
		if (jsonObject.has("sizeidlist")) {
			// 写尺码
			qry += "\n   delete from waredisplaysize where onhouseid=" + Onhouseid + " and wareid=" + Wareid + ";";
			JSONArray jsonArray = jsonObject.getJSONArray("sizeidlist");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long sizeid = Long.parseLong(jsonObject2.getString("sizeid"));
				// value = int.Parse(ht["value" + i].ToString());
				qry += "\n   insert into waredisplaysize (onhouseid,wareid,sizeid,lastop,lastdate)";
				qry += "\n   values (" + Onhouseid + "," + Wareid + "," + sizeid + ",'" + Lastop + "',sysdate); ";
			}
		}

		if (jsonObject.has("wareidlist")) {
			// 写颜色
			qry += "\n   delete from wareonline1 where onhouseid=" + Onhouseid + " and wareid=" + Wareid + ";";
			JSONArray jsonArray = jsonObject.getJSONArray("wareidlist");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long wareid1 = Long.parseLong(jsonObject2.getString("wareid"));
				// value = int.Parse(ht["value" + i].ToString());
				qry += "\n   insert into wareonline1 (id,onhouseid,wareid,wareid1,lastop)";
				qry += "\n   values (wareonline1_id.nextval," + Onhouseid + "," + Wareid + "," + wareid1 + ",'" + Lastop + "');";

			}
		}

		qry += "\n   commit;";
		// qry += "\n select '1操作成功！' into :retcs from dual; ";
		qry += "\n end; ";
		//System.out.println(qry);
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 更改陈列商品状态(上下架)
	public int doUpAndDown() {
		// Uptag:0=下架，1=上架，2=发布，3=取消发布
		// statetag:0=下架 ，1=上架
		if (Uptag == null || Uptag > 1 || Onhouseid == 0 || Wareid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		String qry = "begin ";
		if (Uptag == 0) // 下架
		{
			qry += "   update wareonline set uptag=0,statetag=0,lastop='" + Lastop + "',lastdate=sysdate where onhouseid=" + Onhouseid + " and wareid=" + Wareid + ";";
		} else if (Uptag == 1) // 上架
		{
			qry += "   update wareonline set uptag=1,statetag=0,lastop='" + Lastop + "',lastdate=sysdate where onhouseid=" + Onhouseid + " and wareid=" + Wareid + ";";
			qry += "   update onlinehouse set lastdate=sysdate where onhouseid=" + Onhouseid + ";";
		}
		qry += " end;";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 成批更改陈列商品状态(上下架)
	public int doAllUpAndDown(JSONObject jsonObject) {
		if (Uptag == null || Onhouseid == 0) {
			errmess = "店铺id参数无效！";
			return 0;
		}
		if (!jsonObject.has("wareidlist")) {
			errmess = "未选中商品！";
			return 0;
		}

		String qry = "declare ";
		qry += "    v_count number;";
		qry += "\n begin ";
		if (Uptag == 0) // 下架
		{
			JSONArray jsonArray = jsonObject.getJSONArray("wareidlist");

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long wareid = Long.parseLong(jsonObject2.getString("wareid"));
				qry += "\n   update wareonline set uptag=0,statetag=0,lastop='" + Lastop + "',lastdate=sysdate where onhouseid=" + Onhouseid + " and wareid=" + wareid + ";";
			}
		} else if (Uptag == 1) // 上架
		{
			JSONArray jsonArray = jsonObject.getJSONArray("wareidlist");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long wareid = Long.parseLong(jsonObject2.getString("wareid"));
				qry += "\n   update wareonline set uptag=1,statetag=0,lastop='" + Lastop + "',lastdate=sysdate where onhouseid=" + Onhouseid + " and wareid=" + wareid + ";";
			}
			qry += "\n   update onlinehouse set lastdate=sysdate where onhouseid=" + Onhouseid + ";";
		} else if (Uptag == 4) // 删除
		{
			JSONArray jsonArray = jsonObject.getJSONArray("wareidlist");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long wareid = Long.parseLong(jsonObject2.getString("wareid"));
				qry += "\n   delete wareonline  where onhouseid=" + Onhouseid + " and wareid=" + wareid + ";";
			}
		} else if (Uptag == 5) // 分组到
		{

			long dspid = jsonObject.has("dspid") ? Long.parseLong(jsonObject.getString("dspid")) : 0;
			// long dspid = YTJR.COMMON.PubObj.sink("dspid", MethodType.Get) == "" ? 0 : long.Parse(YTJR.COMMON.PubObj.sink("dspid", MethodType.Get));
			if (dspid == 0) {
				errmess = "商品分组无效！";
				return 0;
			}
			// long dspid = long.Parse(ht["dspid"].ToString());
			JSONArray jsonArray = jsonObject.getJSONArray("wareidlist");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long wareid = Long.parseLong(jsonObject2.getString("wareid"));
				qry += "\n   select count(*) into v_count from waredisplay where dspid=" + dspid + " and wareid=" + wareid + ";";
				qry += "\n   if v_count=0 then ";
				qry += "\n      insert into waredisplay (wareid,dspid)";
				qry += "\n      values (" + wareid + "," + dspid + ");";
				qry += "\n   end if; ";
			}
		} else {
			errmess = "参数无效！";
			return 0;
		}
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
