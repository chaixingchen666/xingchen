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
import com.common.tool.TwoBarCode;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-13 16:05:20
//*************************************
public class Onlinehouse implements java.io.Serializable {
	private Long Onhouseid;
	private String Onhousename;
	private String Shortname;
	private Long Accid;
	private Long Userid;
	private String Address;
	private String Tel;
	private Integer Noused;
	private Integer Statetag;
	private String Remark;
	private String Imagename;
	private Float Pageview;
	private Float Agreeview;
	private Integer Numview;
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

	public void setUserid(Long userid) {
		this.Userid = userid;
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

	public void setNumview(Integer numview) {
		this.Numview = numview;
	}

	public Integer getNumview() {
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
		// strSql.append(" update onlinehouse set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		return 1;
	}

	// 增加记录
	public int Append(JSONObject jsonObject) {

		if (Onhousename == null && Onhousename.equals("")) {
			errmess = "线上店铺名称无效！";
			return 0;
		}
		if (!jsonObject.has("houselist")) {
			errmess = "未指定线上店铺对应的实体店参数！";
			return 0;
		}
		if (!jsonObject.has("typelist")) {
			errmess = "未指定线上店铺的经营类型参数！";
			return 0;
		}
		// if (!jsonObject.has("typelist")) {
		// errmess = "未指定线上店铺的经营类型参数！";
		// return 0;
		// }
		if (Numview == null)
			Numview =  1;
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("onhouseid,");
		strSql2.append("v_onhouseid,");
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// if (Onhouseid != null) {
		// strSql1.append("onhouseid,");
		// strSql2.append(Onhouseid + ",");
		// }
		if (Onhousename != null) {
			strSql1.append("onhousename,");
			strSql2.append("'" + Onhousename + "',");
			Shortname = PinYin.getPinYinHeadChar(Onhousename);
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		// if (Accid != null) {
		// }
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
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
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
		strSql.append("\n   v_onhouseid number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n begin ");
		strSql.append("\n   v_onhouseid:=onlinehouse_onhouseid.nextval; ");
		strSql.append("\n   insert into onlinehouse (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");

		JSONArray jsonArray = jsonObject.getJSONArray("houselist");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long houseid = Long.parseLong(jsonObject2.getString("houseid"));
			strSql.append("\n   update warehouse set onhouseid=v_onhouseid where houseid=" + houseid + ";");
		}
		jsonArray = jsonObject.getJSONArray("typelist");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long typeid = Long.parseLong(jsonObject2.getString("typeid"));
			strSql.append("\n   insert into online2type (ONHOUSEID,TYPEID,LASTOP)");
			strSql.append("\n   values (v_onhouseid," + typeid + ",'" + Lastop + "');");
		}
		if (jsonObject.has("userlist")) {

			jsonArray = jsonObject.getJSONArray("userlist");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long userid = Long.parseLong(jsonObject2.getString("userid"));
				String xxx = jsonObject2.getString("xxx");
				strSql.append("\n   insert into online2user (ONHOUSEID,epid,xxx,LASTOP)");
				strSql.append("\n   values (v_onhouseid," + userid + ",'" + xxx + "','" + Lastop + "');");
			}
		}

		strSql.append("\n   select count(*) into v_count from online2user where onhouseid=v_onhouseid and epid=" + Userid + ";");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      insert into online2user (ONHOUSEID,epid,xxx,LASTOP)");
		strSql.append("\n      values (v_onhouseid," + Userid + ",'1111111111','" + Lastop + "');");
		strSql.append("\n   end if;");

		// 增加默认陈列类别
		strSql.append("\n   select count(*) into v_count from DISPLAYCODE where onhouseid=v_onhouseid and statetag=1; ");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      insert into DISPLAYCODE (dspid,dspname,onhouseid,statetag,orderid,usetag,lastop,lastdate)");
		strSql.append("\n      values (DISPLAYCODE_dspid.nextval,'新品',v_onhouseid,1,1,1,'" + Lastop + "',sysdate);");
		strSql.append("\n   end if;");

		strSql.append("\n  select v_onhouseid into :id from dual; ");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "创建店铺失败！";
			return 0;
		} else {
			Onhouseid = Long.parseLong(param.get("id").getParamvalue().toString());
			errmess = Onhouseid.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		if (Onhouseid == null || Onhouseid == 0) {
			errmess = "线上店铺id参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update onlinehouse set ");
		// if (Onhouseid != null) {
		// strSql.append("onhouseid=" + Onhouseid + ",");
		// }
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
		strSql.append("  where onhouseid=" + Onhouseid + " and accid=" + Accid + " ;");
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
		strSql.append(" FROM onlinehouse a");
		if (Accid != null && Accid > 0)
			strSql.append(" left outer join (select accid,vtid,vtname from guesttype where accid=" + Accid + " and statetag=1 and noused=0 and defbj=1 and rownum=1) b on a.accid=b.accid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM onlinehouse a ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable1(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM onlinehouse a join online2user b on a.onhouseid=b.onhouseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from onlinehouse");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 生成店铺二维码，供VIP扫码注册,扫码后自动成为该店铺的会员
	public int doHouseTwobarcode() {
		if (Onhouseid == 0) {
			errmess = "线上店铺id无效1!";
			return 0;
		}
		Table tb = new Table();

		String qry = "select a.onhouseid,a.onhousename,a.imagename,b.accname from onlinehouse a ";
		qry += " join accreg b on a.accid=b.accid where a.onhouseid=" + Onhouseid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "线上店铺id无效2!";
			return 0;
		}
		// string str = DbHelperSQL.RunSql(qry);
		// String accname = tb.getRow(0).get("ACCNAME").toString();
		// String onhousename = tb.getRow(0).get("ONHOUSENAME").toString();

		String path = Func.getRootPath();

		String tempfilename = "";
		String logfilename = "img/logo.png";
		String desfilename = "imgbar/onhouse2bar_" + Onhouseid + ".png";// +"_" + num.ToString() + ".jpeg";// string.Format(DateTime.Now.ToString(), "yyyymmddhhmmss") + ".jpg";
		String content = "https://jerp.skydispark.com:62220/help/skydvipreg.html?" + Func.desEncode(Onhouseid.toString());

		String imagename = tb.getRow(0).get("IMAGENAME").toString();

		if (imagename != null && !imagename.equals("")) {
			logfilename = Func.fastDFSDownload(imagename);
			System.out.println(logfilename);
			tempfilename = logfilename;
		}

		if (TwoBarCode.encode(content, path + logfilename, path + desfilename, true)) {
			errmess = desfilename;// TwoBarCode.encode(link, 300,"logo.png");
			if (!tempfilename.equals(""))
				Func.delFile(path + tempfilename);
			return 1;
		} else {
			errmess = "生成二维码失败！";
			if (!tempfilename.equals(""))
				Func.delFile(path + tempfilename);
			return 0;
		}

	}

	// 显示线上店铺首页信息，可发放优惠券，设置商品陈列类别
	public int doListOnlineFirst() {
		if (Onhouseid == 0) {
			errmess = "店铺id参数无效！";
			return 0;
		}
		String qry = "select a.onhouseid,a.onhousename,a.imagename,a.accid,a.remark,a.notice,a.pageview,a.statetag";
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

		String housename0 = tb.getRow(0).get("ONHOUSENAME").toString();

		String retcs = "\"msg\":\"操作成功！\",\"ONHOUSEID\":\"" + tb.getRow(0).get("ONHOUSEID").toString() + "\"" //
				+ ",\"ONHOUSENAME\":\"" + housename0 + "\"" //
				+ ",\"ACCID\":\"" + tb.getRow(0).get("ACCID").toString() + "\"" //
				+ ",\"REMARK\":\"" + tb.getRow(0).get("REMARK").toString() + "\"" //
				+ ",\"NOTICE\":\"" + tb.getRow(0).get("NOTICE").toString() + "\""//
				+ ",\"STATETAG\":\"" + tb.getRow(0).get("STATETAG").toString() + "\"" //
				+ ",\"PAGEVIEW\":\"" + tb.getRow(0).get("PAGEVIEW").toString() + "\"" //
				+ ",\"IMAGENAME\":\"" + tb.getRow(0).get("IMAGENAME").toString() + "\"";
		// 店铺优惠券
		// qry = "select a.cpid,a.curr,a.xfcurr,to_char(a.begindate,'yyyy-mm-dd') as begindate,to_char(a.overdate,'yyyy-mm-dd') as overdate,a.statetag,a.totalnum";
		qry = "select a.cpid,a.curr,a.xfcurr,a.remark,to_char(a.begindate,'yyyy-mm-dd') as begindate,to_char(a.overdate,'yyyy-mm-dd') as overdate,to_char(a.lastdate,'yyyy-mm-dd') as lastdate,a.statetag,a.totalnum";
		qry += " from Housecoupon a  ";
		qry += " where a.onhouseid=" + Onhouseid + " and a.statetag=1 and to_char(sysdate,'yyyy-mm-dd')<=to_char(a.overdate,'yyyy-mm-dd') ";
		// trunc(sysdate)<=trunc(a.overdate)
		// qry += " and not exists (select 1 from vipcoupon b where a.cpid=b.cpid and b.vipid=" + vipid + " )";
		qry += " order by a.overdate";
		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		int rs = tb.getRowCount();
		retcs += ",\"couponlist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"CPID\":\"" + tb.getRow(i).get("CPID").toString() + "\"" //
					+ ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\"" //
					+ ",\"XFCURR\":\"" + tb.getRow(i).get("XFCURR").toString() + "\"" //
					+ ",\"BEGINDATE\":\"" + tb.getRow(i).get("BEGINDATE").toString() + "\"" //
					+ ",\"OVERDATE\":\"" + tb.getRow(i).get("OVERDATE").toString() + "\"" //
					+ ",\"TOTALNUM\":\"" + tb.getRow(i).get("TOTALNUM").toString() + "\""
					// + ",\"USETAG\":\"" + dt.Rows[i]["usetag"].ToString() + "\""
					+ ",\"STATETAG\":\"" + tb.getRow(i).get("STATETAG").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";

		// 商品陈列种类(在用的)
		qry = "select a.dspid,a.dspname,a.statetag,a.usetag,a.orderid";
		qry += " from displaycode a  ";
		qry += " where a.onhouseid=" + Onhouseid + " and a.usetag=1";
		qry += " order by a.orderid,a.dspid";
		tb = DbHelperSQL.Query(qry).getTable(1);
		fh = "";
		rs = tb.getRowCount();
		retcs += ",\"displaylist\":[";

		for (int i = 0; i < rs; i++) {
			retcs += fh + "{\"DSPID\":\"" + tb.getRow(i).get("DSPID").toString() + "\""//
					+ ",\"DSPNAME\":\"" + tb.getRow(i).get("DSPNAME").toString() + "\"" //
					+ ",\"USETAG\":\"" + tb.getRow(i).get("USETAG").toString() + "\"" //
					+ ",\"ORDERID\":\"" + tb.getRow(i).get("ORDERID").toString() + "\"" //
					+ ",\"STATETAG\":\"" + tb.getRow(i).get("STATETAG").toString() + "\"}";
			fh = ",";

		}
		retcs += "]";
		errmess = retcs;
		return 1;
	}

	public int doGetOnlinehouse(long userid) {
		String qry = "select count(*) as num from onlinehouse where accid=" + Accid;
		if (Integer.parseInt(DbHelperSQL.RunSql(qry).toString()) == 0) {
			// WriteResult("2", "系统未设置线上店铺！");
			errmess = "系统未设置线上店铺！";
			return 2;
		}
		Table tb = new Table();
		// 查询当前用户是不是系统管理员
		qry = "select a.onhouseid,b.glevelid from employe a join userrole b on a.levelid=b.levelid where a.accid=" + Accid + " and a.epid=" + userid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "用户无效！";
			return 0;
		}
		int glevelid = Integer.parseInt(tb.getRow(0).get("GLEVELID").toString());
		long onhouseid = Long.parseLong(tb.getRow(0).get("ONHOUSEID").toString());
		if (onhouseid > 0) {
			if (glevelid > 0) {
				// 非系统管理员，要判断用户是否有登录线上店铺的权限
				qry = "select a.onhouseid,b.onhousename,a.xxx from ONLINE2USER a  join onlinehouse b on a.onhouseid=b.onhouseid where b.accid=" + Accid + " and a.onhouseid=" + onhouseid + " and a.epid=" + userid;
				tb = DbHelperSQL.Query(qry).getTable(1);
				if (tb.getRowCount() == 0) {
					errmess = "当前用户没有登录线上店铺的权限！";
					return 0;
				}
			} else {
				qry = "select a.onhouseid,a.onhousename,'1111111111' as xxx from onlinehouse a ";
				qry += " where a.onhouseid=" + onhouseid + " and a.accid=" + Accid;
				tb = DbHelperSQL.Query(qry).getTable(1);
				if (tb.getRowCount() == 0) {
					errmess = "当前用户没有登录线上店铺的权限！";
					return 0;
				}
			}
		} else {
			if (glevelid > 0) {
				// 非系统管理员，要判断用户是否有登录线上店铺的权限
				qry = "select onhouseid,onhousename,xxx from (select a.onhouseid,b.onhousename,a.xxx from ONLINE2USER a ";
				qry += " join onlinehouse b on a.onhouseid=b.onhouseid where b.accid=" + Accid + " and a.epid=" + userid;
				qry += " order by a.lastdate desc) where rownum=1";
				tb = DbHelperSQL.Query(qry).getTable(1);
				if (tb.getRowCount() == 0) {
					errmess = "当前用户没有登录线上店铺的权限！";
					return 0;
				}
			} else {
				qry = "select onhouseid,onhousename,xxx from (select a.onhouseid,b.onhousename,'1111111111' as xxx from ONLINE2USER a ";
				qry += " join onlinehouse b on a.onhouseid=b.onhouseid where b.accid=" + Accid;
				qry += " order by a.lastdate desc) where rownum=1";
				tb = DbHelperSQL.Query(qry).getTable(1);
				if (tb.getRowCount() == 0) {
					errmess = "当前用户没有登录线上店铺的权限！";
					return 0;
				}
			}
			onhouseid = Long.parseLong(tb.getRow(0).get("ONHOUSEID").toString());
			// 更改职员登录的线上店铺id
			qry = "update employe set onhouseid=" + onhouseid + " where epid=" + userid;
			DbHelperSQL.ExecuteSql(qry);
		}

		// WriteResult("1", onhouseid.ToString());
		errmess = "\"msg\": \"" + onhouseid + "\"" + ",\"ONHOUSEID\": \"" + onhouseid + "\"" + ",\"ONHOUSENAME\": \"" + tb.getRow(0).get("ONHOUSENAME").toString() + "\"" + ",\"XXX\": \""
				+ tb.getRow(0).get("XXX").toString() + "\"" + ",\"GLEVELID\": \"" + glevelid + "\"";

		return 1;
	}

	// 上传线上店铺logo
	public int doUploadHouselogo(String pictjson) {
		if (Onhouseid == 0) {
			errmess = "店铺id参数无效！";
			return 0;
		}
		if (pictjson == null || pictjson.equals("")) {
			errmess = "未传入图片组参数:pictjson！";
			return 0;
		}
		JSONObject jsonObject = JSONObject.fromObject(pictjson);
		if (!jsonObject.has("base64string")) {
			errmess = "未传入图片值参数:base64string！";
			return 0;
		}
		String base64string = jsonObject.getString("base64string");
		String retcs = Func.Base64UpFastDFS(base64string);
		if (retcs.substring(0, 1).equals("1")) {
			Imagename = retcs.substring(1);
		} else {
			errmess = "店铺图片上传失败！" + retcs.substring(1);
			return 0;
		}

		String qry = "declare ";
		qry += "\n  v_oldimagename varchar(60);";
		qry += "\n begin ";
		qry += "\n   select imagename into v_oldimagename from onlinehouse where onhouseid=" + Onhouseid + ";";
		qry += "\n   update onlinehouse set imagename='" + Imagename + "' where Onhouseid=" + Onhouseid + ";";
		qry += "\n   select v_oldimagename into :retcs from dual;";
		qry += "\n end;";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		// 调用
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "上传失败！";
			return 0;
		}
		// 取返回值
		// String oldimagename = param.get("retcs").getParamvalue().toString();
		// if (oldimagename != null && !oldimagename.equals("")) {
		// Func.fastDFSDelete(oldimagename);
		// }
		errmess = "上传成功！";
		return 1;
	}

	// 发布线上店铺
	public int doUpOnlinehouse(int fbbj) {
		if (Onhouseid == 0) {
			errmess = "线上店铺id无效!";
			return 0;
		}
		String qry = " declare ";
		qry += "   v_count number(10); ";
		qry += "   v_retcs varchar2(200);";
		qry += " begin ";
		if (fbbj == 1) {
			qry += "\n  select count(*) into v_count from vipbanner where lx=1 and onhouseid=" + Onhouseid + " and statetag=3;";
			qry += "\n  if v_count=0 then";
			qry += "\n     v_retcs:= '0您的店铺还没有形象广告，请前往商家中心设置！';";
			qry += "\n     goto exit;";
			qry += "\n  end if;";
			// qry += "\n update Housecoupon set statetag=1,lastdate=sysdate where onhouseid=" + onhouseid + "; ";
			// qry += "\n update displaycode set statetag=1,lastdate=sysdate where onhouseid=" + onhouseid + " and usetag=1; ";
			// qry += "\n update wareonline set statetag=case uptag when 1 then 1 else 0 end,lastdate=sysdate where onhouseid=" + onhouseid + "; ";
			// qry += "\n update wareonline set statetag=1,lastdate=sysdate where onhouseid=" + onhouseid + " and uptag=1; ";
			qry += "\n   update onlinehouse set notice=notice1,lastdate=sysdate,statetag=1 where onhouseid=" + Onhouseid + "; ";
		} else {
			qry += "\n   update onlinehouse set statetag=0 where onhouseid=" + Onhouseid + "; ";
			// 清除异常的线上店铺id
			qry += "\n   update warehouse a set onhouseid=0";
			qry += "\n 	 where accid=" + Accid + " and statetag=1 and onhouseid>0 ";
			qry += "\n 	 and not exists (select 1 from onlinehouse b where a.accid=b.accid and a.onhouseid=b.onhouseid);";

		}
		qry += "\n     v_retcs:= '1操作成功！' ;";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret == 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

}