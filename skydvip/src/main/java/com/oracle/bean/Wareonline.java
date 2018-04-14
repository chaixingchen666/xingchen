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
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-25 18:51:01
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
		strSql.append(" update wareonline set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) == 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Onhouseid != null) {
			strSql1.append("Onhouseid,");
			strSql2.append(Onhouseid + ",");
		}
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
		strSql.append("   v_id:=wareonline_id.nextval; ");
		strSql.append("   insert into wareonline (");
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
		return ret;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update wareonline set ");
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
		return DbHelperSQL.ExecuteSql(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM wareonline ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM wareonline a join warecode b on a.wareid=b.wareid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from wareonline");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	public int getWareonline(long vipid) {
		String qry = "select a.Wareid,a.remark,b.warename,b.units,b.wareno,b.retailsale,a.saleprice,a.pageview,a.agreeview";// ,b.imagename0";
		qry += ",c.onhousename,c.imagename,f_cvipisbvip(" + vipid + "," + Onhouseid + ") as isvip ";
		if (vipid == 0) {
			qry += ",0 as iscollect,0 as isagree";
		} else {
			qry += ",nvl( (select 1 from vipcollect where Onhouseid=" + Onhouseid + " and Wareid=" + Wareid + " and vipid=" + vipid + "),0) as iscollect"; // 收藏
			qry += ",nvl( (select 1 from wareonlineagree where Onhouseid=" + Onhouseid + " and Wareid=" + Wareid + " and vipid=" + vipid + "),0) as isagree"; // 收藏
		}
		qry += " from wareonline a  ";
		qry += " left outer join warecode b on a.Wareid=b.Wareid";
		qry += " left outer join onlinehouse c on a.Onhouseid=c.Onhouseid";
		qry += " where a.Onhouseid=" + Onhouseid + " and a.Wareid=" + Wareid;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到商品信息！";
			return 0;
		}
		String retcs = "\"msg\":\"操作成功！\",\"WAREID\":\"" + tb.getRow(0).get("WAREID").toString() + "\"" //
				+ ",\"WARENAME\":\"" + tb.getRow(0).get("WARENAME").toString() + "\"" //
				+ ",\"WAREREMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\"" //
				+ ",\"WARENO\":\"" + tb.getRow(0).get("WARENO").toString() + "\"" //
				+ ",\"UNITS\":\"" + tb.getRow(0).get("UNITS").toString() + "\"" //
				+ ",\"RETAILSALE\":\"" + tb.getRow(0).get("RETAILSALE").toString() + "\""//
				+ ",\"SALEPRICE\":\"" + tb.getRow(0).get("SALEPRICE").toString() + "\"" //
				+ ",\"ISCOLLECT\":\"" + tb.getRow(0).get("ISCOLLECT").toString() + "\"" //
				+ ",\"ISAGREE\":\"" + tb.getRow(0).get("ISAGREE").toString() + "\"" //
				+ ",\"AGREEVIEW\":\"" + tb.getRow(0).get("AGREEVIEW").toString() + "\"" //
				+ ",\"ONHOUSENAME\":\"" + tb.getRow(0).get("ONHOUSENAME").toString() + "\""//
				+ ",\"ISVIP\":\"" + tb.getRow(0).get("ISVIP").toString() + "\"" //
				+ ",\"IMAGENAME\":\"" + tb.getRow(0).get("IMAGENAME").toString() + "\"";
		qry = "  update wareonline set pageview=pageview+1 where Onhouseid=" + Onhouseid + " and Wareid=" + Wareid;
		DbHelperSQL.ExecuteSql(qry);
		// 取商品图片
		qry = "select id,imagename from (select id,imagename from warepicture where Wareid=" + Wareid + " order by orderid) where rownum<=5";
		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		int rs = tb.getRowCount();
		retcs += ",\"wareimagelist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"IMGID\":\"" + tb.getRow(i).get("ID").toString() + "\"" + ",\"IMAGENAME\":\"" + tb.getRow(i).get("IMAGENAME").toString() + "\"}";
			fh = ",";
		}
		retcs += "]";
		// 取商品尺码
		qry = "select a.sizeid,a.sizename,a.sizeno ";
		qry += " from sizecode a join waredisplaysize b on a.sizeid=b.sizeid";
		qry += " where a.statetag=1 and a.noused=0 and b.Wareid=" + Wareid + " and b.Onhouseid=" + Onhouseid;
		qry += " order by a.sizeno,a.sizeid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		retcs += ",\"sizelist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" + ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
			fh = ",";
		}
		retcs += "]";
		// 取商品颜色
		qry = "select a.colorid,a.colorname,a.colorno ";
		qry += " from colorcode a join waredisplaycolor b on a.colorid=b.colorid";
		qry += " where a.statetag=1 and a.noused=0 and b.Wareid=" + Wareid + " and b.Onhouseid=" + Onhouseid;
		qry += " order by a.colorno,a.colorid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		retcs += ",\"colorlist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\"" + ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";
			fh = ",";
		}

		retcs += "]";

		// 取相关搭配
		qry = "select b.Wareid,c.remark,b.warename,b.units,b.wareno,b.imagename0,b.retailsale,c.saleprice";
		qry += " from wareonline1 a left outer join warecode b on a.Wareid1=b.Wareid";
		qry += " left outer join wareonline c on a.Wareid1=c.Wareid and c.Onhouseid=" + Onhouseid;
		qry += " where a.Wareid=" + Wareid;
		qry += " order by a.id";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		retcs += ",\"warelist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"WAREID\":\"" + tb.getRow(i).get("WAREID").toString() + "\"" + ",\"WARENAME\":\"" + tb.getRow(i).get("WARENAME").toString() + "\"" + ",\"WAREREMARK\":\""
					+ tb.getRow(i).get("REMARK").toString() + "\"" + ",\"WARENO\":\"" + tb.getRow(i).get("WARENO").toString() + "\"" + ",\"UNITS\":\"" + tb.getRow(i).get("UNITS").toString() + "\"" + ",\"RETAILSALE\":\""
					+ tb.getRow(i).get("RETAILSALE").toString() + "\"" + ",\"SALEPRICE\":\"" + tb.getRow(i).get("SALEPRICE").toString() + "\"" + ",\"IMAGENAME0\":\"" + tb.getRow(i).get("IMAGENAME0").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";
		errmess = retcs;
		return 1;
	}

}