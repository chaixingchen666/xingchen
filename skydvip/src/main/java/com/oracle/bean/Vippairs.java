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
//  @author:sunhong  @date:2017-02-27 08:53:00
//*************************************
public class Vippairs implements java.io.Serializable {
	private Float Paid;
	private String Patitle;
	private Integer Statetag;
	private Long Onhouseid;
	private String Imagename;
	private String Content;
	private String Remark;
	private Float Pageview;
	private Float Agreeview;
	private String Refuse;
	private String Lastop;
	private Date Lastdate;
	private Integer Styletag;
	private Float Imgheight;
	private Float Imgheight0;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setPaid(Float paid) {
		this.Paid = paid;
	}

	public Float getPaid() {
		return Paid;
	}

	public void setPatitle(String patitle) {
		this.Patitle = patitle;
	}

	public String getPatitle() {
		return Patitle;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public String getContent() {
		return Content;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setPageview(Float pageview) {
		this.Pageview = pageview;
	}

	public Float getPageview() {
		return Pageview;
	}

	public void setAgreeview(Float agreeview) {
		this.Agreeview = agreeview;
	}

	public Float getAgreeview() {
		return Agreeview;
	}

	public void setRefuse(String refuse) {
		this.Refuse = refuse;
	}

	public String getRefuse() {
		return Refuse;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setStyletag(Integer styletag) {
		this.Styletag = styletag;
	}

	public Integer getStyletag() {
		return Styletag;
	}

	public void setImgheight(Float imgheight) {
		this.Imgheight = imgheight;
	}

	public Float getImgheight() {
		return Imgheight;
	}

	public void setImgheight0(Float imgheight0) {
		this.Imgheight0 = imgheight0;
	}

	public Float getImgheight0() {
		return Imgheight0;
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
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update VIPpairs set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
		if (Paid != null) {
			strSql1.append("paid,");
			strSql2.append("'" + Paid + "',");
		}
		if (Patitle != null) {
			strSql1.append("patitle,");
			strSql2.append("'" + Patitle + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Onhouseid != null) {
			strSql1.append("onhouseid,");
			strSql2.append(Onhouseid + ",");
		}
		if (Imagename != null) {
			strSql1.append("imagename,");
			strSql2.append("'" + Imagename + "',");
		}
		if (Content != null) {
			strSql1.append("content,");
			strSql2.append("'" + Content + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Pageview != null) {
			strSql1.append("pageview,");
			strSql2.append("'" + Pageview + "',");
		}
		if (Agreeview != null) {
			strSql1.append("agreeview,");
			strSql2.append("'" + Agreeview + "',");
		}
		if (Refuse != null) {
			strSql1.append("refuse,");
			strSql2.append("'" + Refuse + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Styletag != null) {
			strSql1.append("styletag,");
			strSql2.append(Styletag + ",");
		}
		if (Imgheight != null) {
			strSql1.append("imgheight,");
			strSql2.append("'" + Imgheight + "',");
		}
		if (Imgheight0 != null) {
			strSql1.append("imgheight0,");
			strSql2.append("'" + Imgheight0 + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=VIPpairs_id.nextval; ");
		strSql.append("   insert into VIPpairs (");
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
		strSql.append("   update VIPpairs set ");
		if (Paid != null) {
			strSql.append("paid='" + Paid + "',");
		}
		if (Patitle != null) {
			strSql.append("patitle='" + Patitle + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
		}
		if (Content != null) {
			strSql.append("content='" + Content + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Pageview != null) {
			strSql.append("pageview='" + Pageview + "',");
		}
		if (Agreeview != null) {
			strSql.append("agreeview='" + Agreeview + "',");
		}
		if (Refuse != null) {
			strSql.append("refuse='" + Refuse + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Styletag != null) {
			strSql.append("styletag=" + Styletag + ",");
		}
		if (Imgheight != null) {
			strSql.append("imgheight='" + Imgheight + "',");
		}
		if (Imgheight0 != null) {
			strSql.append("imgheight0='" + Imgheight0 + "',");
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
		strSql.append(" FROM VIPpairs a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM VIPpairs ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {

		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from VIPpairs");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}
}