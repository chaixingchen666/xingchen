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
//  @author:sunhong  @date:2017-02-25 18:20:13
//*************************************
public class Onlinehouse implements java.io.Serializable {
	private Long Onhouseid;
	private String Onhousename;
	private String Shortname;
	private Long Accid;
	private String Address;
	private String Tel;
	private Integer Noused;
	private Integer Statetag;
	private String Remark;
	private String Imagename;
	private Float Pageview;
	private Float Agreeview;
	private Long Numview;
	private String Lastop;
	private Date Lastdate;
	private String Notice;
	private String Notice1;
	private String Attention;
	private Integer Viewtag;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setOnhousename(String onhousename) {
		this.Onhousename = onhousename;
	}

	public String getOnhousename() {
		return Onhousename;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
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

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
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

	public void setNumview(Long numview) {
		this.Numview = numview;
	}

	public Long getNumview() {
		return Numview;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setNotice(String notice) {
		this.Notice = notice;
	}

	public String getNotice() {
		return Notice;
	}

	public void setNotice1(String notice1) {
		this.Notice1 = notice1;
	}

	public String getNotice1() {
		return Notice1;
	}

	public void setAttention(String attention) {
		this.Attention = attention;
	}

	public String getAttention() {
		return Attention;
	}

	public void setViewtag(Integer viewtag) {
		this.Viewtag = viewtag;
	}

	public Integer getViewtag() {
		return Viewtag;
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
		strSql.append(" update onlinehouse set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
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
			strSql1.append("onhouseid,");
			strSql2.append(Onhouseid + ",");
		}
		if (Onhousename != null) {
			strSql1.append("onhousename,");
			strSql2.append("'" + Onhousename + "',");
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("'" + Address + "',");
		}
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("'" + Tel + "',");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
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
		if (Pageview != null) {
			strSql1.append("pageview,");
			strSql2.append("'" + Pageview + "',");
		}
		if (Agreeview != null) {
			strSql1.append("agreeview,");
			strSql2.append("'" + Agreeview + "',");
		}
		if (Numview != null) {
			strSql1.append("numview,");
			strSql2.append(Numview + ",");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Notice != null) {
			strSql1.append("notice,");
			strSql2.append("'" + Notice + "',");
		}
		if (Notice1 != null) {
			strSql1.append("notice1,");
			strSql2.append("'" + Notice1 + "',");
		}
		if (Attention != null) {
			strSql1.append("attention,");
			strSql2.append("'" + Attention + "',");
		}
		if (Viewtag != null) {
			strSql1.append("viewtag,");
			strSql2.append(Viewtag + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=onlinehouse_id.nextval; ");
		strSql.append("   insert into onlinehouse (");
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
		strSql.append("   update onlinehouse set ");
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Onhousename != null) {
			strSql.append("onhousename='" + Onhousename + "',");
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
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
		if (Pageview != null) {
			strSql.append("pageview='" + Pageview + "',");
		}
		if (Agreeview != null) {
			strSql.append("agreeview='" + Agreeview + "',");
		}
		if (Numview != null) {
			strSql.append("numview=" + Numview + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Notice != null) {
			strSql.append("notice='" + Notice + "',");
		}
		if (Notice1 != null) {
			strSql.append("notice1='" + Notice1 + "',");
		}
		if (Attention != null) {
			strSql.append("attention='" + Attention + "',");
		}
		if (Viewtag != null) {
			strSql.append("viewtag=" + Viewtag + ",");
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
		strSql.append(" FROM onlinehouse ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM onlinehouse a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from onlinehouse");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	public int listMyHouse(long vipid) {
		//System.out.println("listMyHouse begin");
		String qry = "select a.onhouseid,a.onhousename,a.imagename,a.accid,a.remark,a.notice";
		// if (vipid > 0) qry += ",b.housename0";
		// else qry += ",'' as housename0";
		qry += " from onlinehouse a ";
		// if (vipid > 0) qry += " left outer join viphouse b on a.houseid=b.houseid and b.vipid="+vipid;
		qry += " where a.onhouseid=" + Onhouseid;
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到店铺信息！";
			return 0;
		}
		long accid = Long.parseLong(tb.getRow(0).get("ACCID").toString());
		String housename0 = tb.getRow(0).get("ONHOUSENAME").toString();
		String vipno = "";
		String vtname = "";
		long vtid = 0;
		String retcs = "\"msg\":\"操作成功！\",\"ONHOUSEID\":\"" + tb.getRow(0).get("ONHOUSEID").toString() + "\"" //
				+ ",\"ONHOUSENAME\":\"" + housename0 + "\"" //
				+ ",\"ACCID\":\"" + accid + "\"" //
				+ ",\"REMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\"" //
				+ ",\"NOTICE\":\"" + tb.getRow(0).get("NOTICE").toString() + "\""//
				+ ",\"IMAGENAME\":\"" + tb.getRow(0).get("IMAGENAME").toString() + "\"";
		int isvip = 0;
		Float balcurr=(float) 0;
		Float balcent=(float) 0;
//		System.out.println("listmyhouse vipid=" + vipid);
		if (vipid > 0) {
			qry = "select a.housename0,c.vipno,d.vtname,nvl(c.vtid,0) as vtid,a.statetag,c.balcurr,c.balcent ";
			qry += "\n from viphouse a join onlinehouse b on a.onhouseid=b.onhouseid";
			qry += "\n left outer join guestvip c on a.vipid=c.vipid and b.accid=c.accid";
			qry += "\n left outer join guesttype d on c.vtid=d.vtid";
			qry += "\n where a.onhouseid=" + Onhouseid + " and a.vipid=" + vipid;
//			System.out.println("listmyhouse qry=" + qry);

			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				housename0 = tb.getRow(0).get("HOUSENAME0").toString();
				vipno = tb.getRow(0).get("VIPNO").toString();
				vtname = tb.getRow(0).get("VTNAME").toString();
				balcurr = Float.parseFloat(tb.getRow(0).get("BALCURR").toString());
				balcent = Float.parseFloat(tb.getRow(0).get("BALCENT").toString());
				vtid = Long.parseLong(tb.getRow(0).get("VTID").toString());
				if (vtid == 0) {
					// 自动建立会员与线上店铺之间的关联
					qry = "declare";
					qry += "\n   v_count number;";
					qry += "\n   v_vtid number(10);";
					qry += "\n   v_mobile varchar2(20);";
					qry += "\n   v_statetag number(1);";
					qry += "\n begin";
					qry += "\n   begin";
					qry += "\n     select mobile into v_mobile from vipgroup where vipid=" + vipid + ";";
					qry += "\n     select vtid into v_vtid from guestvip where accid=" + accid + " and statetag<>2 and mobile=v_mobile;";
					qry += "\n     if v_vtid>0 then";//如果vip的手机号已在会员档案中存在
					qry += "\n        v_statetag:=1;";
					qry += "\n     end if;";
					qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
					qry += "\n     v_statetag:=0;";
					qry += "\n   end;";
					qry += "\n   select count(*) into v_count from viphouse where onhouseid=" + Onhouseid + " and vipid=" + vipid + ";";
					qry += "\n   if v_count=0 then";
					qry += "\n      insert into viphouse (onhouseid,vipid,housename0,statetag)";
					qry += "\n      values (" + Onhouseid + "," + vipid + ",'" + housename0 + "',v_statetag);";
					qry += "\n   else ";
					qry += "\n      update viphouse set statetag=v_statetag where onhouseid=" + Onhouseid + " and vipid=" + vipid + ";";
					qry += "\n   end if;";
					qry += "\n end;";
					// LogUtils.LogDebugWrite("listMyHouse", qry);
					DbHelperSQL.ExecuteSql(qry);
					isvip = 2; // 正在申请
				} else {
					// 自动建立会员与线上店铺之间的关联
					qry = "declare";
					qry += "\n   v_count number;";
					qry += "\n begin";
					qry += "\n   select count(*) into v_count from viphouse where onhouseid=" + Onhouseid + " and vipid=" + vipid + ";";
					qry += "\n   if v_count=0 then";
					qry += "\n      insert into viphouse (onhouseid,vipid,housename0,statetag)";
					qry += "\n      values (" + Onhouseid + "," + vipid + ",'" + housename0 + "',1);";
					qry += "\n   else ";
					qry += "\n      update viphouse set statetag=1 where onhouseid=" + Onhouseid + " and vipid=" + vipid + ";";
					qry += "\n   end if;";
					qry += "\n end;";
					// LogUtils.LogDebugWrite("listMyHouse", qry);
					DbHelperSQL.ExecuteSql(qry);
					isvip = 1;
				}
			}
		}
		retcs += ",\"HOUSENAME0\":\"" + housename0 + "\"" //
				+ ",\"VIPNO\":\"" + vipno + "\""//
				+ ",\"VTNAME\":\"" + vtname + "\"" //
				+ ",\"ISVIP\":\"" + isvip + "\""//
				+ ",\"BALCURR\":\"" + balcurr + "\""//
				+ ",\"BALCENT\":\"" + balcent + "\""
				;
		// 店铺优惠券
		qry = "select a.cpid,a.curr,a.xfcurr,a.remark,to_char(a.begindate,'yyyy-mm-dd') as begindate,to_char(a.overdate,'yyyy-mm-dd') as overdate";
		qry += ",'满'||a.xfcurr||'元减'||a.curr||'元' as remark0";

		qry += " from Housecoupon a  ";
		// qry += " where a.onhouseid=" + onhouseid + " and a.statetag=1";
		qry += " where a.onhouseid=" + Onhouseid + " and a.statetag=1 and to_char(sysdate,'yyyy-mm-dd')<=to_char(a.overdate,'yyyy-mm-dd') ";
		qry += " and not exists (select 1 from vipcoupon b where a.cpid=b.cpid and b.vipid=" + vipid + " )";
		qry += " order by a.overdate";
		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		int rs = tb.getRowCount();
		retcs += ",\"couponlist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"CPID\":\"" + tb.getRow(i).get("CPID").toString() + "\"" //
					+ ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"" //
					+ ",\"XSCURR\":\"" + tb.getRow(i).get("XFCURR").toString() + "\"" //
					+ ",\"REMARK\":\"" + tb.getRow(i).get("REMARK").toString() + "\"" //
					+ ",\"REMARK0\":\"" + tb.getRow(i).get("REMARK0").toString() + "\"" //
					+ ",\"BEGINDATE\":\"" + tb.getRow(i).get("BEGINDATE").toString() + "\"" //
					+ ",\"OVERDATE\":\"" + tb.getRow(i).get("OVERDATE").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";

		// 商品陈列种类
		qry = "select a.dspid,a.dspname";
		qry += " from displaycode a  ";
		qry += " where a.onhouseid=" + Onhouseid + " and a.statetag=1 and a.usetag=1";
		qry += " order by a.orderid,a.dspid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		retcs += ",\"displaylist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"DSPID\":\"" + tb.getRow(i).get("DSPID").toString() + "\""//
					+ ",\"DSPNAME\":\"" + tb.getRow(i).get("DSPNAME").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";
		// 记录线上店铺访问信息
		qry = " begin";
		qry += "   insert into ONLINEVIEW (id,onhouseid,vipid,LOGINDATE)";
		qry += "   values (ONLINEVIEW_id.nextval," + Onhouseid + "," + vipid + ",sysdate); ";
		qry += " end; ";
		errmess = retcs;
		return 1;
	}
}