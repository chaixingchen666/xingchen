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
//  @author:sunhong  @date:2017-03-16 18:41:48
//*************************************
public class Cityhouse implements java.io.Serializable {
	private Long Cthouseid;
	private Long Accid;
	private String Cthousename;
	private String Shortname;
	private String Address;
	private String Tel;
	private Integer Opentag;
	private Integer Statetag;
	private String Remark;
	private String Imagename;
	private String Qxcs;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setCthouseid(Long cthouseid) {
		this.Cthouseid = cthouseid;
	}

	public Long getCthouseid() {
		return Cthouseid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setCthousename(String cthousename) {
		this.Cthousename = cthousename;
	}

	public String getCthousename() {
		return Cthousename;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setOpentag(Integer opentag) {
		this.Opentag = opentag;
	}

	public Integer getOpentag() {
		return Opentag;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
	}

	public void setQxcs(String qxcs) {
		this.Qxcs = qxcs;
	}

	public String getQxcs() {
		return Qxcs;
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
		if (Cthouseid == null || Cthouseid == 0) {
			errmess = "cthouseid无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n   v_housename varchar2(200);");
		strSql.append("\n begin ");
		strSql.append("\n   select cthousename into v_housename from cityhouse where  cthouseid=" + Cthouseid + " and accid=" + Accid + " ;");

		strSql.append("\n   update cityhouse set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append("\n   where cthouseid=" + Cthouseid + " and accid=" + Accid + ";");
		strSql.append("\n   insert into LOGRECORD (id,accid,progname,remark,lastop)");
		strSql.append("\n   values (LOGRECORD_id.nextval," + Accid + ",'商城档案','【删除记录】商城id:" + Cthouseid + "; 商城名称:'||v_housename,'" + Lastop + "');");
		strSql.append("\n   commit;");
		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n   v_housename:=''; ");
		strSql.append("\n end; ");
//System.out.println(strSql.toString());
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
		if (Cthousename == null || Cthousename.equals("")) {
			errmess = "商城名称不允许为空！";
			return 0;
		}
		if (Exists()) // 商城名称是否重复
			return 0;
		if (!jsonObject.has("warereclist")) {
			errmess = "首页商品参数无效！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("warereclist");
		if (jsonArray.size() < 3) {
			errmess = "商城首页至少要选择3种商品展示！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("cthouseid,");
		strSql2.append("v_cthouseid,");

		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Cthousename != null) {
			strSql1.append("cthousename,");
			strSql2.append("'" + Cthousename + "',");
			Shortname = PinYin.getPinYinHeadChar(Cthousename);
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("'" + Address + "',");
		}
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("'" + Tel + "',");
		}
		if (Opentag != null) {
			strSql1.append("opentag,");
			strSql2.append(Opentag + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Imagename != null) {
			strSql1.append("imagename,");
			strSql2.append("'" + Imagename + "',");
		}
		if (Qxcs != null) {
			strSql1.append("qxcs,");
			strSql2.append("'" + Qxcs + "',");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_cthouseid number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n  v_warename varchar2(60);");
		strSql.append("\n  v_citysale number;");
		strSql.append(" begin ");

		strSql.append("\n   v_cthouseid:=cityhouse_cthouseid.nextval; ");
		strSql.append("\n   insert into cityhouse (");
		strSql.append("\n  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long wareid = Long.parseLong(jsonObject2.getString("wareid"));
			strSql.append("\n   select count(*) into v_count from cityhouseware where cthouseid=v_cthouseid and wareid=" + wareid + ";");
			strSql.append("\n   if v_count=0 then");
			strSql.append("\n      select warename,case sale4 when 0 then retailsale else sale4 end into v_warename,v_citysale from warecode where wareid=" + wareid + " and accid=" + Accid + ";");
			strSql.append("\n      insert into cityhouseware (id,cthouseid,wareid,ordid,warename0,citysale,haveamt,newtag,onlinetag,opendate,lastdate,lastop) ");
			strSql.append("\n      values (cityhouseware_id.nextval,v_cthouseid," + wareid + "," + (i + 1) + ",v_warename,v_citysale,0,0,1,sysdate,sysdate,'" + Lastop + "');");
			strSql.append("\n   end if; ");
		}

		strSql.append("\n  select v_cthouseid into :id from dual; ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Cthouseid = Long.parseLong(param.get("id").getParamvalue().toString());
			errmess = Cthouseid.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update(JSONObject jsonObject, int fs) {
		StringBuilder strSql = new StringBuilder();
		if (Cthousename != null) {
			if (Cthousename.equals("")) {
				errmess = "商城名称不允许为空！";
				return 0;
			}
			if (Exists())
				return 0;
		}
		strSql.append("declare");
		strSql.append("\n  v_id number(20);");
		strSql.append("\n  v_warename varchar2(60);");
		strSql.append("\n  v_citysale number;");

		strSql.append("\n begin ");
		strSql.append("\n   update cityhouse set ");
		// if (Cthouseid != null) {
		// strSql.append("cthouseid=" + Cthouseid + ",");
		// }
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Cthousename != null) {
			strSql.append("cthousename='" + Cthousename + "',");
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Opentag != null) {
			strSql.append("opentag=" + Opentag + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
		}
		if (Qxcs != null) {
			strSql.append("qxcs='" + Qxcs + "',");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where Cthouseid=" + Cthouseid + " and accid=" + Accid + " ;");

		if (jsonObject.has("warereclist")) {
			JSONArray jsonArray = jsonObject.getJSONArray("warereclist");
			if (fs == 0 && jsonArray.size() < 3) {
				errmess = "商城首页至少要选择3种商品展示！";
				return 0;
			}
			strSql.append("\n   update cityhouseware set ordid=9999 where cthouseid=" + Cthouseid + ";");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long wareid = Long.parseLong(jsonObject2.getString("wareid"));
				float rowid = Float.parseFloat(jsonObject2.getString("rowid"));
				if (rowid == 0) {
					strSql.append("\n   begin");
					strSql.append("\n      select id into v_id from cityhouseware where cthouseid=" + Cthouseid + " and wareid=" + wareid + ";");
					strSql.append("\n      update  cityhouseware set ordid= " + (i + 1) + ",lastdate=sysdate,lastop='" + Lastop + "' where id=v_id; ");
					strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
					strSql.append("\n      select warename,case sale4 when 0 then retailsale else sale4 end into v_warename,v_citysale from warecode where wareid=" + wareid + " and accid=" + Accid + ";");
					strSql.append("\n      insert into cityhouseware (id,cthouseid,wareid,ordid,warename0,citysale,haveamt,newtag,onlinetag,opendate,lastdate,lastop) ");
					strSql.append("\n      values (cityhouseware_id.nextval," + Cthouseid + "," + wareid + "," + i + ",v_warename,v_citysale,0,0,1,sysdate,sysdate,'" + Lastop + "');");
					strSql.append("\n   end;");
				} else {
					strSql.append("\n   update  cityhouseware set ordid= " + (i + 1) + ",lastdate=sysdate,lastop='" + Lastop + "' where id=" + rowid + "; ");
				}

			}
		}

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
		strSql.append(" FROM cityhouse ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM cityhouse a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public String GetTable2json(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM cityhouse a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		Table tb = new Table();
		tb = DbHelperSQL.GetTable(qp);
		int count = tb.getRowCount();
		long cthouseid = 0;
		String qry1;
		String fh = "";
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\",\"total\":\"" + count + "\",\"rows\":[";
		for (int i = 0; i < count; i++) {
			cthouseid = Long.parseLong(tb.getRow(i).get("CTHOUSEID").toString());
			retcs += fh + "{\"CTHOUSEID\":\"" + cthouseid + "\"" + ",\"CTHOUSENAME\":\"" + tb.getRow(i).get("CTHOUSENAME").toString() + "\"" + ",\"ADDRESS\":\"" + tb.getRow(i).get("ADDRESS").toString() + "\""
					+ ",\"REMARK\":\"" + tb.getRow(i).get("REMARK").toString() + "\"" + ",\"TEL\":\"" + tb.getRow(i).get("TEL").toString() + "\"" + ",\"JXSTAG\":\"" + tb.getRow(i).get("JXSTAG").toString() + "\""
					+ ",\"ACCID\":\"" + tb.getRow(i).get("ACCID").toString() + "\"";
			// 取尺码数据
			qry1 = "  select id,wareid,warename0,warename,imagename0,retailsale,citysale,ordid,lastdate from (";
			qry1 += " select a.ID,a.WAREID,a.warename0,b.warename,b.IMAGENAME0,b.retailsale,a.citysale,a.ordid,a.lastdate from cityhouseware a ";
			qry1 += " join warecode b on a.wareid=b.wareid where a.cthouseid= " + cthouseid + " and length(b.imagename0)>0 and a.ordid>=1 order by a.ORDID,a.ID";
			qry1 += " ) where rownum<=6 order by ordid,lastdate desc,id";
			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
			int count1 = tb1.getRowCount();
			retcs += ",\"warelist\":[";
			String fh1 = "";
			for (int j = 0; j < count1; j++) {
				retcs += fh1 + "{\"ROWID\":\"" + tb1.getRow(j).get("ID").toString() + "\"" //
						+ ",\"WAREID\":\"" + tb1.getRow(j).get("WAREID").toString() + "\"" //
						+ ",\"WARENAME\":\"" + tb1.getRow(j).get("WARENAME").toString() + "\"" //
						+ ",\"WARENAME0\":\"" + tb1.getRow(j).get("WARENAME0").toString() + "\"" //
						+ ",\"IMAGENAME0\":\"" + tb1.getRow(j).get("IMAGENAME0").toString() + "\""//
						+ ",\"RETAILSALE\":\"" + tb1.getRow(j).get("RETAILSALE").toString() + "\""//
						+ ",\"CITYSALE\":\"" + tb1.getRow(j).get("CITYSALE").toString() + "\"}";
				fh1 = ",";
			}
			fh = ",";
			retcs += "]}";

		}
		retcs += "]}";

		return retcs;
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from cityhouse");
		strSql.append(" where cthousename='" + Cthousename + "' and statetag=1 and rownum=1 and accid=" + Accid);
		if (Cthouseid != null)
			strSql.append(" and Cthouseid<>" + Cthouseid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "" + Cthousename + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 获取指定商城店铺id信息
	public int doGetCityhouse() {
		if (Cthouseid == 0) {
			errmess = "cthouseid无效！";
			return 0;
		}
		String qry = "select CTHOUSEID,CTHOUSENAME,ACCID,ADDRESS,TEL,REMARK,OPENTAG,STATETAG,LASTOP,LASTDATE,QXCS ";
		qry += " from cityhouse where CTHOUSEID=" + Cthouseid + " and accid=" + Accid;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			// WriteResultx("0","无最近售价记录！");
			errmess = "无商城店铺记录！";
			return 0;
		}
		String responsestr = "\"msg\":\"操作成功！\",\"CTHOUSEID\":\"" + tb.getRow(0).get("CTHOUSEID").toString() + "\""//
				+ ",\"CTHOUSENAME\":\"" + tb.getRow(0).get("CTHOUSENAME").toString() + "\"" //
				+ ",\"ADDRESS\":\"" + tb.getRow(0).get("ADDRESS").toString() + "\"" //
				+ ",\"TEL\":\"" + tb.getRow(0).get("TEL").toString() + "\"" //
				+ ",\"REMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\"" //
				+ ",\"OPENTAG\":\"" + tb.getRow(0).get("OPENTAG").toString() + "\"" //
				+ ",\"STATETAG\":\"" + tb.getRow(0).get("STATETAG").toString() + "\"" //
				+ ",\"QXCS\":\"" + tb.getRow(0).get("QXCS").toString() + "\""
		// + ",\"JXSTAG\":\"" + tb.getRow(0).get("jxstag").toString() + "\""
		// + ",\"JXSTAG\":\"0\""
		;
		qry = "select a.id,a.wareid,a.warename0,b.wareno,b.imagename0 from cityhouseware a join warecode b on a.wareid=b.wareid where a.cthouseid="//
				+ Cthouseid + " and a.ordid>=1 and a.ordid<=6 order by ordid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		int rs = tb.getRowCount();

		responsestr += ",\"warelist\":[";
		String fh = "";
		for (int i = 0; i < rs; i++) {
			responsestr += fh + "{\"ROWID\":\"" + tb.getRow(i).get("ID").toString() + "\"" //
					+ ",\"WAREID\":\"" + tb.getRow(i).get("WAREID").toString() + "\"" //
					+ ",\"WARENO\":\"" + tb.getRow(i).get("WARENO").toString() + "\"" //
					+ ",\"WARENAME0\":\"" + tb.getRow(i).get("WARENAME0").toString() + "\"" //
					+ ",\"IMAGENAME0\":\"" + tb.getRow(i).get("IMAGENAME0").toString() + "\"}";
			fh = ",";
		}
		responsestr += "]";
		errmess = responsestr;
		return 1;
	}

	// 加盟商获取供应商指定商城店铺id信息
	public int doGetProvCityhouse() {
		if (Cthouseid == 0) {
			errmess = "cthouseid无效！";
			return 0;
		}
		String qry = "select CTHOUSEID,CTHOUSENAME,ACCID,ADDRESS,TEL,REMARK ";
		// qry += ",f_accidisbuyer as jxsbj";
		qry += " ,f_accidisbuyer(" + Accid + ",accid) as jxstag ";
		qry += " from cityhouse where CTHOUSEID=" + Cthouseid + " and opentag=1";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			// WriteResultx("0","无最近售价记录！");
			errmess = "无商城店铺记录！";
			return 0;
		}
		String responsestr = "\"msg\":\"操作成功！\",\"CTHOUSEID\":\"" + tb.getRow(0).get("CTHOUSEID").toString() + "\""//
				+ ",\"CTHOUSENAME\":\"" + tb.getRow(0).get("CTHOUSENAME").toString() + "\""//
				+ ",\"ADDRESS\":\"" + tb.getRow(0).get("ADDRESS").toString() + "\""//
				+ ",\"TEL\":\"" + tb.getRow(0).get("TEL").toString() + "\"" //
				+ ",\"REMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\""
				// + ",\"OPENTAG\":\"" + tb.getRow(0).get("OPENTAG").toString() + "\""
				// + ",\"STATETAG\":\"" + tb.getRow(0).get("STATETAG").toString() + "\""
				// + ",\"QXCS\":\"" + tb.getRow(0).get("QXCS").toString() + "\""
				+ ",\"JXSTAG\":\"" + tb.getRow(0).get("JXSTAG").toString() + "\"";
		errmess = responsestr;
		return 1;

	}
}