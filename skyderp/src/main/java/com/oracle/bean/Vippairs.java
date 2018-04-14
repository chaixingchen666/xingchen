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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-14 18:32:07
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
		if (Paid == 0) {
			errmess = "搭配id无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin");
		strSql.append("  delete from vippairs where paid=" + Paid + ";");
		strSql.append("  delete from vippairs1 where paid=" + Paid + ";");
		strSql.append("end;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(JSONObject jsonObject, String pictjson) {

		if (Onhouseid == null || Onhouseid == 0) {
			errmess = "缺少线上店铺参数！";
			return 0;
		}
		if (Patitle == null || Patitle.equals("")) {
			errmess = "请输入搭配主题！";
			return 0;
		}
		if (Content == null || Content.equals("")) {
			errmess = "请输入搭配内容！";
			return 0;
		}
		if (pictjson == null || pictjson.equals("")) {
			errmess = "未传入图片组参数:pictjson！";
			return 0;
		}
//		LogUtils.LogDebugWrite("vippairs.Append", "begin");
		JSONObject jsonObject1 = JSONObject.fromObject(pictjson);
		if (!jsonObject1.has("base64string")) {
			errmess = "未传入搭配图片值参数:base64string！";
			return 0;
		}
		String base64string = jsonObject1.getString("base64string");
		String retcs = Func.Base64UpFastDFS(base64string);
		if (retcs.substring(0, 1).equals("1")) {
			Imagename = retcs.substring(1);
		} else {
			errmess = "商品图片上传失败！" + retcs.substring(1);
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("paid,");
		strSql2.append("v_paid,");
		// if (Patitle != null) {
		strSql1.append("patitle,");
		strSql2.append("'" + Patitle + "',");
		// }
		// if (Onhouseid != null) {
		strSql1.append("onhouseid,");
		strSql2.append(Onhouseid + ",");
		// }
		// if (Content != null) {
		strSql1.append("content,");
		strSql2.append("'" + Content + "',");
		// }
		if (Imagename != null) {
			strSql1.append("imagename,");
			strSql2.append("'" + Imagename + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
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
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
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
		strSql.append("   v_paid number; ");
		strSql.append("\n begin ");
		strSql.append("\n   v_paid:=vippairs_paid.nextval; ");
		strSql.append("\n   insert into vippairs (" + strSql1.toString() + ")");
		strSql.append("\n   values (" + strSql2.toString() + ");");
		if (jsonObject.has("warereclist")) {
			JSONArray jsonArray = jsonObject.getJSONArray("warereclist");

			for (int i = 0; i < jsonArray.size() && i < 5; i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long wareid = Long.parseLong(jsonObject2.getString("wareid"));
				String imgremark = jsonObject2.getString("imgremark");
				strSql.append("\n   insert into vippairs1 ( id,paid,wareid,ordid,imagename,remark0,lastop) ");
				strSql.append("\n   values (vippairs1_id.nextval,v_paid," + wareid + "," + i + ",'','" + imgremark + "','" + Lastop + "'); ");
			}

		}

		strSql.append("\n  select v_paid into :paid from dual; ");
		strSql.append("\n end;");
//		LogUtils.LogDebugWrite("vippairs.Append", strSql.toString());
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("paid", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Paid = Float.parseFloat(param.get("paid").getParamvalue().toString());
			errmess = Paid.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update(JSONObject jsonObject, String pictjson) {
		//System.out.println("00000");
		if (Paid == null || Paid == 0) {
			errmess = "缺少搭配id参数！";
			return 0;
		}
		if (pictjson != null && !pictjson.equals("")) {
			JSONObject jsonObject1 = JSONObject.fromObject(pictjson);
			if (jsonObject1.has("base64string")) {
				String base64string = jsonObject1.getString("base64string");
				String retcs = Func.Base64UpFastDFS(base64string);
				if (retcs.substring(0, 1).equals("1")) {
					Imagename = retcs.substring(1);
				} else {
					errmess = "商品图片上传失败！" + retcs.substring(1);
					return 0;
				}
			}
		}
		//System.out.println("00000----1");

		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("\n   update vippairs set ");
		// if (Paid != null) {
		// strSql.append("paid='" + Paid + "',");
		// }
		if (Patitle != null) {
			strSql.append("patitle='" + Patitle + "',");
		}
		// if (Statetag != null) {
		// strSql.append("statetag=" + Statetag + ",");
		// }
		// if (Onhouseid != null) {
		// strSql.append("onhouseid=" + Onhouseid + ",");
		// }
		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
		}
		if (Content != null) {
			strSql.append("content='" + Content + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		// if (Pageview != null) {
		// strSql.append("pageview='" + Pageview + "',");
		// }
		// if (Agreeview != null) {
		// strSql.append("agreeview='" + Agreeview + "',");
		// }
		if (Refuse != null) {
			strSql.append("refuse='" + Refuse + "',");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
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
		strSql.append("  where Paid=" + Paid + " ;");
		strSql.append("\n  delete from vippairs1 where Paid=" + Paid + " ;");
		//System.out.println("11111");
		if (jsonObject.has("warereclist")) {
			//System.out.println("2222");

			JSONArray jsonArray = jsonObject.getJSONArray("warereclist");
			//System.out.println("3333");

			for (int i = 0; i < jsonArray.size() && i < 5; i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);

				long wareid = Long.parseLong(jsonObject2.getString("wareid"));

				String imgremark = jsonObject2.getString("imgremark");
				strSql.append("\n   insert into vippairs1 ( id,paid,wareid,ordid,imagename,remark0,lastop) ");
				strSql.append("\n   values (vippairs1_id.nextval," + Paid + " ," + wareid + "," + i + ",'','" + imgremark + "','" + Lastop + "'); ");
			}

		}

		// strSql.append(" commit;");
		strSql.append("\n end;");
		// LogUtils.LogDebugWrite("updatevippair ", strSql.toString());
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
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM vippairs ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM vippairs a left outer join onlinehouse b on a.onhouseid=b.onhouseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from vippairs");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 获取潮流搭配
	public int doGetVippairs() {
		if (Paid == 0) {
			errmess = "搭配id参数无效！";
			return 0;
		}
		Table tb = new Table();
		String qry = "select paid,patitle,content,remark,styletag,statetag from vippairs where paid=" + Paid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到搭配！";
			return 0;
		}
		String retcs = "\"msg\":\"操作成功！\",\"PAID\":\"" + tb.getRow(0).get("PAID").toString() + "\"" + ",\"PATITLE\":\"" + tb.getRow(0).get("PATITLE").toString() + "\"" + ",\"CONTENT\":\"" + tb.getRow(0).get("CONTENT").toString() + "\""
				+ ",\"REMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\"" + ",\"STATETAG\":\"" + tb.getRow(0).get("STATETAG").toString() + "\"" + ",\"STYLETAG\":\"" + tb.getRow(0).get("STYLETAG").toString()
				+ "\"";

		String qry1 = "select a.ID,a.WAREID,b.IMAGENAME0,b.retailsale,a.ORDID,a.REMARK0 from VIPPAIRS1 a join warecode b on a.wareid=b.wareid where a.PAID= " + Paid + " order by a.ORDID,a.ID";
		Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
		int count1 = tb1.getRowCount();
		retcs += ",\"imagelist\":[";
		String fh1 = "";
		for (int j = 0; j < count1; j++) {
			retcs += fh1 + "{\"IMGID\":\"" + tb1.getRow(j).get("ID").toString() + "\"" + ",\"WAREID\":\"" + tb1.getRow(j).get("WAREID").toString() + "\"" + ",\"IMAGENAME0\":\""
					+ tb1.getRow(j).get("IMAGENAME0").toString() + "\"" + ",\"RETAILSALE\":\"" + tb1.getRow(j).get("RETAILSALE").toString() + "\"" + ",\"IMGREMARK\":\"" + tb1.getRow(j).get("REMARK0").toString() + "\"}";
			fh1 = ",";
		}
		// fh = ",";
		retcs += "]";
		errmess = retcs;
		return 1;
	}

	// 更改潮流搭配状态
	public int doChangeVippairs(int zt) {
		if (Paid == 0) {
			errmess = "搭配id参数无效！";
			return 0;
		}
		String qry = "";
		// if (zt == 0)
		// qry = "update vippairs set statetag=1,refuse='',lastdate=sysdate,lastop='" + Lastop + "' where statetag=0 and paid=" + Paid;
		// else if (zt == 1)
		// qry = "update vippairs set statetag=0,lastdate=sysdate,lastop='" + Lastop + "' where statetag=1 and paid=" + Paid;
		// else {
		// errmess = "操作参数错误:" + zt;
		// return 0;
		// }
		// zt=0:提交 statetag 0->1
		// zt=1:撤消待审 statetag 1->0
		// zt=2:发布 statetag 2->3
		// zt=3:撤消审核 statetag 2->1
		// zt=4:撤消发布 statetag 3->2
		// zt=5:审核通过 statetag 1->2
		// zt=6:审核驳回 statetag 1->0 要传入refuse

		if (zt == 0)
			qry = "update vippairs set statetag=1,refuse='',lastdate=sysdate,lastop='" + Lastop + "' where statetag=0 and paid=" + Paid;
		else if (zt == 1)
			qry = "update vippairs set statetag=0,lastdate=sysdate,lastop='" + Lastop + "' where statetag=1 and paid=" + Paid;
		else if (zt == 2)
			qry = "update vippairs set statetag=3,lastdate=sysdate,lastop='" + Lastop + "' where statetag=2 and paid=" + Paid;
		else if (zt == 3)
			qry = "update vippairs set statetag=1,lastdate=sysdate,lastop='" + Lastop + "' where statetag=2 and paid=" + Paid;
		else if (zt == 4)
			qry = "update vippairs set statetag=2,lastdate=sysdate,lastop='" + Lastop + "' where statetag=3 and paid=" + Paid;
		else if (zt == 5)
			qry = "update vippairs set statetag=2,lastdate=sysdate,lastop='" + Lastop + "' where statetag=1 and paid=" + Paid;
		else if (zt == 6) {
			if (Refuse == null || Refuse.equals("")) {
				errmess = "请输入拒绝原因！";
				return 0;
			}
			qry = "update vippairs set statetag=0,lastdate=sysdate,refuse='" + Refuse + "',lastop='" + Lastop + "' where statetag=1 and paid=" + Paid;
		} else {
			errmess = "操作参数错误:" + zt;
			return 0;
		}

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