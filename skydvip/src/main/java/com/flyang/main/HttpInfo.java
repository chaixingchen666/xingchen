package com.flyang.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.netdata.db.DbHelperSQL;

import net.sf.json.JSONObject;

//会员http结构
public class HttpInfo {
	private String url = "";
	// 不需要签名校验的接口
	// private static String actionlist = "^serverdate^serverdatetime^testsaveimage^test^path^viplogin^listhousebanner^listpublicbanner"
	// + "^resetvipnamepwd^getvipnamebymobile^addvipgroup^getdeviceno^listnearhouse^listvippairs^listmyhouse^listwaredisplay"
	// + "^getvippairs^listwaretype1^addbuycar^listbuycar^delbuycar^updatebuycar^getwareonline^uploadlogmess^";

	private static String[] actionarrays = { "serverdate", "serverdatetime", "testsaveimage", "test", "path", "viplogin", "listhousebanner", //
			"listpublicbanner", "resetvipnamepwd", "getvipnamebymobile", "addvipgroup", "getdeviceno", "listnearhouse", "listvippairs", //
			"listmyhouse", "listwaredisplay", "getvippairs", "listwaretype1", "addbuycar^listbuycar", "delbuycar", "updatebuycar", //
			"getwareonline", "uploadlogmess" };
	private static List<String> actionlist = Arrays.asList(actionarrays);

	private String action = "";
	private String datajson = "";
	private String signjson = "";
	private String pictjson = "";
	private String username = "";
	private String timestamp = "";
	private String signature = "";
	private String longdatetime = ""; // 今天
	// private String accdate = "";
	// private long maccid = -1;
	private long userid = -1;
	// private long levelid = -1;

	private String qxpublic = "0000000000000000000000000000000000000000";
	// private int errbj;
	private String errmess;

	// XXXXXXXXXXXXXXXXXXXXXXXXXXX
	// ||||| ||｜|｜______________13 条码产生方式(0-4)
	// ||||| ||｜|________________12 自动生成货号
	// ||||| ||｜_________________11 配货单允许直接出库
	// ||||| ||___________________10 配货单审核后才允许拣货
	// ||||| |________________9 采购入库自动更新进价
	// |||||___________________5-8 积分抵扣比例（4位）
	// ||||____________________4 单价小数位数(0,1,2)
	// |||_____________________3 启用最近售价
	// ||______________________2 允许负数出库
	// |_______________________1 启用权限控制
	//
	// ==========================

	public String getErrmess() {
		return errmess;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getNowdatetime() {
		return longdatetime;
	}

	public String getNowdate() {
		return longdatetime.substring(0, 10);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDatajson() {
		return datajson;
	}

	public void setDatajson(String datajson) {
		this.datajson = datajson;
	}

	public String getSignjson() {
		return signjson;
	}

	public int setSignjson(String signjson) {
		//System.out.println(" setSignjson begin");
		this.signjson = signjson;

		JSONObject jsonObject = JSONObject.fromObject(signjson);

		timestamp = jsonObject.has("timestamp") ? jsonObject.getString("timestamp") : "151231235959";
		DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		// String nowdatetime = df.format(pFunc.getServerdatetime().getTime() - 1000 * 60 * 10); // 10分钟
		//
		// if (timestamp.compareTo(nowdatetime) < 0) {
		// this.errmess = "{\"syserror\":\"调用已超时:501\"}";
		// return 0;
		// }

		Date nowdate = pFunc.getServerdatetime();
		String nowdatetime = df.format(nowdate.getTime() - 1000 * 60 * 10); // 10分钟
		if (timestamp.compareTo(nowdatetime) < 0) {
			this.errmess = "{\"result\":-1,\"msg\":\"调用已超时:501！\",\"syserror\":\"调用已超时:501\"}";
			return 0;
		}
		longdatetime = Func.DateToStr(nowdate, "yyyy-MM-dd HH:mm:ss");

		url += " sign=" + signjson;

		// 解密数据datajson
		if (datajson != null && !datajson.equals("")) {

			String ss = "Fly#Ang" + this.signjson.toUpperCase() + "Sky@D20$16";
			// string ss = "Fly#Ang" + htp0.signjson.ToUpper() + "Sky@D20$16";
			String keystr = Func.StrtoMD5(ss); // 大写md5

			Func fyfun = new Func();
			datajson = fyfun.aesDecrypt(datajson, keystr);
			url += " data=" + datajson;

			JSONObject jsonObject1 = JSONObject.fromObject(datajson);
			// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"brandid,brandname","sort":"brandid","order":"asc"}
			String flyang = jsonObject1.has("flyang") ? jsonObject1.getString("flyang") : "******";

			if (!flyang.equals("20150107")) // 数据标志
			{
				errmess = "{\"result\":-1,\"msg\":\"异常数据:506！\",\"syserror\":\"异常数据:506\"}";
				return 0;
			}

		}
		if (pictjson != null && !pictjson.equals(""))
			url += " pict={...}";
		userid = jsonObject.has("userid") ? Long.parseLong(jsonObject.getString("userid")) : -1;
		username = jsonObject.has("username") ? jsonObject.getString("username") : "";
		signature = jsonObject.has("signature") ? jsonObject.getString("signature") : "";

//		if (actionlist.contains("^" + action + "^")) {// 不需要登录校验
		if (actionlist.contains(action)) {
			return 1;
		}
		qxpublic = jsonObject.has("qxpublic") ? jsonObject.getString("qxpublic") : "";
		Table tb = new Table();

		// String qry = "select a.accesskey,a.accid,a.ontime,a.offtime,b.levelid,to_char(c.begindate,'yyyy-mm-dd') as begindate,b.epname "; // ,b.epname
		// qry += " from useronline a ";
		// qry += " left outer join employe b on a.epid=b.epid ";
		// qry += " left outer join accreg c on b.accid=c.accid ";
		// qry += " where a.epid=" + userid;
		String qry = "select a.accesskey,b.vipname from viponline a ";
		qry += " left outer join vipgroup b on a.vipid=b.vipid";
		qry += " where a.vipid=" + userid;

		//System.out.println(qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		//System.out.println("12345678");
		// if (tb == null) {
		// errmess = "{\"syserror\":\"未找到登录信息:508\"}";
		// return 0;
		// }
		if (tb == null || tb.getRowCount() == 0) {
			errmess = "{\"result\":-1,\"msg\":\"未找到登录信息:508！\",\"syserror\":\"未找到登录信息:508\"}";
			return 0;
		}
		// tb.getRow(index)
		String accesskey = tb.getRow(0).get("ACCESSKEY").toString();
		// long maccid0 = Long.parseLong(tb.getRow(0).get("ACCID").toString());

		// Integer.parseInt
		// String mintime = tb.getRow(0).get("ONTIME").toString();
		// String maxtime = tb.getRow(0).get("OFFTIME").toString();

		username = tb.getRow(0).get("VIPNAME").toString();

		// i/f (accesskey == null) accesskey = "";
		String ss = "FlyAng@2015" + userid + timestamp + accesskey;

		String ss1 = Func.StrtoMD5(ss);
		// if (signature != System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(ss, "MD5"))
		if (!signature.equals(ss1)) {
			// string responsestr = "{\"syserror\":\"签名无效！" + htp0.signature + " " + ss1 + "\"}";
			errmess = "{\"result\":-1,\"msg\":\"签名无效！\",\"syserror\":\"签名无效！\"}";
			// HttpContext.Current.Response.Write(responsestr);
			return 0;

		}

		return 1;
	}

	public String getPictjson() {
		return pictjson;
	}

	public void setPictjson(String pictjson) {
		this.pictjson = pictjson;
	}

	public String getQxpublic() {
		return qxpublic;
	}

	public void setQxpublic(String qxpublic) {
		this.qxpublic = qxpublic;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	// public String getAccdate() {
	// return accdate;
	// }
	//
	// public void setAccdate(String accdate) {
	// this.accdate = accdate;
	// }
	//
	// public long getMaccid() {
	// return maccid;
	// }
	//
	// public void setMaccid(long maccid) {
	// this.maccid = maccid;
	// }

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	// public long getLevelid() {
	// return levelid;
	// }
	//
	// public void setLevelid(long levelid) {
	// this.levelid = levelid;
	// }

}
