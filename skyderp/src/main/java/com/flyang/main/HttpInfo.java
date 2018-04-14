package com.flyang.main;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.LogUtils;
import com.netdata.db.DbHelperSQL;

import net.sf.json.JSONObject;

public class HttpInfo {
	private String url = "";
	private String url0 = ""; // 原
	private String url1 = ""; // 原

	// 简单签名接口
	private static String[] actionarrays = { "serverdate", "serverdatetime", "test", "path", "testsaveimage", "employelogin", "employeloginx", "omuserlogin", //
			"totalcost", "totalcurr", "totalwareout", "passday", "getworkday", "cleardemo", "resetaccnamepwd", "getaccnamebymobile", "addemployex", //
			"sysrolelistx", "createwarehouse", "accnamevalid", "addsysnotice", "writeworkday", "addemployex", "printsetlistx",// "accregcount",//
			"backprintlist", "totalacc", "getaccregbyname", "totalwaresum", "omuserloginx", "uploadlogmess", "updateaccregbyid", "warehouselistx",//
			"addaccreg", "banklistx", "updateaccregpay", "deletenotenox", "prtserverstart", "updateprintset", "getprintset", "uploadimagex", "accregnearlist",//
			"yky_syncvip1" };
	private static List<String> actionlist = Arrays.asList(actionarrays);

	// actionarrays1不判断签名的接口但要取登录信息
	private static String[] actionarrays1 = { "houseworktotalx", "deletenoteno", "getincomecurrbyidx", "getallotinhbyidx", "getwareoutpayex", "loadprintnotenox", //
			"getwarepeihbyidx", "gettempcheckhbyidx", "getwaresalepayx", "getincomebalnotenox", "getpaybalnotenox", "getwaresalehbyidx", "getprovorderhbyidx", //
			"getallotorderhbyidx", "loadprintnotenoh", "deletenotenox", "overbackprint", "getwareinpayx", "getcustorderhbyidx", "getallotouthbyidx", "getpaybaltimex", //
			"getincomebaltimex", "warebarprintlist", "getwareinhbyidx", "getwareouthbyidx", "getprintcsx", "getshopsalehbyidx", "getpaycurrbyidx", //
			"getrefundoutpayex", "getrefundaskhbyidx", "getguestcurrbyidx", "addcreditrecord", "listcreditrecord", "delcreditrecord", "yht_custaccess", "yht_loaninformation",//
			"yht_getaccregbyid", "getincomecostbyidx", "getpaycostbyidx" };

	private static List<String> actionlist1 = Arrays.asList(actionarrays1);

	public Integer MAX_SIZE = 15; // 最大尺码数
	private String author = ""; // 原

	private String action = "";
	private String datajson = "";
	private String signjson = "";
	private String pictjson = "";
	private String username = "";
	private String timestamp = "";
	private String signature = "";
	private String accdate = "";// 账套启用时间
	private String longdatetime = ""; // 服务器当前时间
	private String calcdate = "";// 登账时间

	private String rootfilepath = "";
	private String ipaddress = "";

	private String devicetype = "???";
	private Integer vertag = -1;  //0=零售，1=批发，2=零售+批发，3=迷你

	private long maccid = -1;
	private long userid = -1;
	private long levelid = -1;
	// private long glevelid = -1;

	private long omid = -1;
	private long custid = -1;
	private Integer sizenum = 5;
	private long provid = -1;

	private String smsuid; // 短信用户id
	private String smsuser; // 短信用户代号
	private String smspwd;// 短信用户密码
	// QXPUBLIC:共40位，1-20:用户系统参数，21-30:用户角色参数(在角色中定义) 31-40为账户控制参数 accreg->uqxcs
	private String qxpublic = "0000000000000000000000000000000000000000";
	// 1234567890123456789012345678901234567890
	// XXXXXXXXXXXXXXXXXXXXXXXXXXX
	// |||||   |||||||||||||||||||_27 1=允许调拨出库直接入库
	// |||||   ||||||||||||||||||_26 1=会员生日双倍积分
	// |||||   |||||||||||||||||__25 1=特价商品要积分
	// |||||   ||||||||||||||||___24 会员价方式:0=零售价，1=售价1，2=售价2，3=售价3
	// |||||   |||||||||||||||____23 移动设备授权后登录
	// |||||   ||||||||||||||_____22 零售额小位数位(0,1,2)
	// |||||   |||||||||||||______21 自动勾单
	// |||||   ||||||||||||_______20 自动产生条码
	// |||||   |||||||||||________19  批发启用收银台
	// |||||   ||||||||||_________18  收银启用C端验证
	// |||||   |||||||||__________17  零售启用收银台
	// |||||   ||||||||___________16  零售抹零方式：0=取整，1=四舍五入,2=保留原数
	// |||||   |||||||____________15 0=按店铺交班 1=按收银员交班
	// |||||   ||||||_____________14  往来帐款分店铺核对
	// |||||   |||||______________13  条码产生方式(1-9)
	// |||||   ||||_______________12  自动生成货号
	// |||||   |||________________11  配货单允许直接出库
	// |||||   ||_________________10  配货单审核后才允许拣货
	// |||||   |__________________9 采购入库自动更新进价
	// |||||______________________5-8 积分抵扣比例（4位）
	// ||||_______________________4 系统价格精度(0,1,2)
	// |||________________________3 启用最近售价
	// ||_________________________2 允许负数出库
	// |__________________________1 启用权限控制
	private String rolepublic = "0000000000"; // 用户角色参数(在角色中定义) userrole->xxstr
	// 原qxpublic 21-30位参数对应
	// 1234567890123456789012345678901234567890
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// |||| ||||_10 pc端允许显示首页
	// |||| |||__9 仅允许浏览商品编码
	// |||| ||___8 允许冲单
	// |||| |____7 允许撤单
	// ||||______4-6 允许查询天数（0表示不限止）
	// |||_______3 允许设置打印页眉页脚
	// ||________2 允许分享
	// |_________1 允许查看进价

	private String accpublic = "0000000000";// 账户控制参数 accreg->qxcs
	// 原qxpublic 31-40为账户控制参数
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// |||||||_7 1=开通会员商城功能
	// ||||||__6 1=开通采购商城后台功能()
	// |||||___5 1=开通订货会功能
	// ||||____4 1=订货会截屏锁住
	// |||_____3 1=允许更改单据日期 (未用)
	// ||______2 1=表示分店铺算成本
	// |_______1 1=表示允许转枫杨果

	private String errmess;

	public String getErrmess() {
		return errmess;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUrl0(String url0) {
		this.url0 = url0;
	}

	public void setUrl1(String url1) {
		this.url1 = url1;
	}

	public String getUrl() {
		return url;
	}

	public String getNowdatetime() {
		return longdatetime;
	}

	public String getCalcdate() {
		return calcdate;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Integer getSizenum() {

		return sizenum;
	}

	public void setCalcdate(String calcdate) {
		this.calcdate = calcdate;
	}

	public String getNowdate() {
		return longdatetime.substring(0, 10);
	}

	public String getDevicetype() {
		return devicetype;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public Integer getVertag() {
		return vertag;
	}

	public void setVertag(Integer vertag) {
		this.vertag = vertag;
	}

	// =====================================

	public int getQxbj() {// 1=启用权限控制
		int ret = 0;
		if (Func.subString(qxpublic, 1, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getFsout() {// 2 允许负数出库
		int ret = 0;
		if (Func.subString(qxpublic, 2, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getNearsale() {// 3 启用最近售价
		int ret = 0;
		if (Func.subString(qxpublic, 3, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getPriceprec() { // 4 单价小数位数(0,1,2)
		int ret = Integer.parseInt(Func.subString(qxpublic, 4, 1));
		return ret;
	}

	public float getJfrate() {// 5-8 积分抵扣比例（4位）
		float jfrate = 0;
		try {
			jfrate = Float.parseFloat(Func.subString(qxpublic, 5, 4));
		} catch (Exception e) {
			jfrate = 0;
		}

		return jfrate;
	}

	public int getChangeenterprice() {// 9 采购入库自动更新进价
		int ret = 0;
		if (Func.subString(qxpublic, 9, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getPhjhfs() { // 10 配货单审核后才允许拣货
		int ret = 0;
		if (Func.subString(qxpublic, 10, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getPhckfs() {// 11 配货单允许直接出库
		int ret = 0;
		if (Func.subString(qxpublic, 11, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getAutowareno() {// 12 自动生成货号
		int ret = 0;
		if (Func.subString(qxpublic, 12, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getBarfs() {// 13 条码产生方式(1-9)
		int ret = 0;
		try {
			ret = Integer.parseInt(Func.subString(qxpublic, 13, 1));
		} catch (Exception e) {
			ret = 0;
		}

		return ret;
	}

	public int getHousezkbj() {// 14 往来帐款分店铺核对
		int ret = 0;
		if (Func.subString(qxpublic, 14, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getJbtag() {// 15 0=按店铺交班 1=按收银员交班
		int ret = 0;
		try {
			ret = Integer.parseInt(Func.subString(qxpublic, 15, 1));
		} catch (Exception e) {
			ret = 0;
		}

		return ret;
	}

	public int getClearzero() {// 16 零售抹零方式：0=取整，1=四舍五入,2=保留原数
		int ret = 0;
		try {
			ret = Integer.parseInt(Func.subString(qxpublic, 16, 1));
		} catch (Exception e) {
			ret = 0;
		}
		return ret;
	}

	public int getSalecashenabled() {// 17 零售启用收银台
		int ret = 0;
		if (Func.subString(qxpublic, 17, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getVipvalid() {// 18 收银启用C端验证
		int ret = 0;
		if (Func.subString(qxpublic, 18, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getPfcashenabled() {// 19 批发启用收银台
		int ret = 0;
		if (Func.subString(qxpublic, 19, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getAutobar() {// 20 增加商品自动产生条码
		int ret = 0;
		if (Func.subString(qxpublic, 20, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getAutogd() {// accpublic： 21 1=自动勾单
		int ret = 0;
		if (Func.subString(qxpublic, 21, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getCurrprec() { // 22 零售额小位数位(0,1,2)
		int ret = 0;// Integer.parseInt(Func.subString(qxpublic, 22, 1));
		try {
			ret = Integer.parseInt(Func.subString(qxpublic, 22, 1));
		} catch (Exception e) {
			ret = 0;
		}
		return ret;
	}

	public int getVipPricetype() {// 24会员价方式:0=零售价，1=售价1，2=售价2，3=售价3
		int ret = 0;
		try {
			ret = Integer.parseInt(Func.subString(qxpublic, 24, 1));
		} catch (Exception e) {
			ret = 0;
		}
		return ret;
	}

	public int getTjspjf() {// 25  1=特价商品要积分
		int ret = 0;
		if (Func.subString(qxpublic, 25, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getVipDoublecent() {// 26 1=会员生日双倍积分
		int ret = 0;
		if (Func.subString(qxpublic, 26, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getAllotout2in() {// 27 1=允许调拨出库直接入库
		int ret = 0;
		if (Func.subString(qxpublic, 27, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getHousecostbj() {// accpublic： 2 1=表示分店铺算成本
		int ret = 0;
		if (Func.subString(accpublic, 2, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getChangedatebj() {// accpublic： 3 1=允许更改单据日期
		int ret = 0;
		if (Func.subString(accpublic, 3, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getOpenvipcity() {// accpublic：7 1=开通会员商城功能
		int ret = 0;
		if (Func.subString(accpublic, 7, 1).equals("1"))
			ret = 1;
		return ret;
	}
	//	private String rolepublic = "0000000000"; // 用户角色参数(在角色中定义) userrole->xxstr
	// 原qxpublic 21-30位参数对应
	// 1234567890123456789012345678901234567890
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// |||| |||_9 仅允许浏览商品编码
	// |||| ||_8 允许冲单
	// |||| |__7 允许撤单
	// ||||____4-6 允许查询天数（0表示不限止）
	// |||_____3 允许设置打印页眉页脚
	// ||______2 允许分享
	// |_______1 允许查看进价

	public int getShowentersale() {// rolepublic：1= 允许查看进价
		int ret = 0;
		if (Func.subString(rolepublic, 1, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getShareok() {// rolepublic：2= 允许分享
		int ret = 0;
		if (Func.subString(rolepublic, 2, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getSetprint() {// rolepublic：3 允许设置打印页眉页脚
		int ret = 0;
		if (Func.subString(rolepublic, 3, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getMaxday() {// rolepublic： 4-6 允许查询天数（0表示不限止）
		int ret = 0;
		try {
			ret = Integer.parseInt(Func.subString(rolepublic, 4, 3));
		} catch (Exception e) {
			ret = 0;
		}
		return ret;
	}

	public int getChedan() {// rolepublic：7 允许撤单
		int ret = 0;
		if (Func.subString(rolepublic, 7, 1).equals("1"))
			ret = 1;
		return ret;
	}

	public int getChongdan() {// rolepublic：8 允许冲单
		int ret = 0;
		if (Func.subString(rolepublic, 8, 1).equals("1"))
			ret = 1;
		return ret;
	}

	//	private String accpublic = "0000000000";// 账户控制参数 accreg->qxcs
	// 原qxpublic 31-40为账户控制参数
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// |||||||_7 1=开通会员商城功能
	// ||||||__6 1=开通采购商城后台功能()
	// |||||___5 1=开通订货会功能
	// ||||____4 1=订货会截屏锁住
	// |||_____3 1=允许更改单据日期 (未用)
	// ||______2 1=表示分店铺算成本
	// |_______1 1=表示允许转枫杨果

	public int getOrdmeetenabled() {// accpublic：1=开通订货会功能
		int ret = 0;
		if (Func.subString(accpublic, 5, 1).equals("1"))
			ret = 1;
		return ret;
	}

	// =====================================

	public String getSmsuid() {
		return smsuid;
	}

	public String getSmsuser() {
		return smsuser;
	}

	public String getSmspwd() {
		return smspwd;
	}

	public String getRootfilepath() {
		return rootfilepath;
	}

	public void setRootfilepath(String rootfilepath) {
		this.rootfilepath = rootfilepath;
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
		this.datajson = datajson.replaceAll(" ", "+");
		//		this.datajson = datajson;
	}

	public String getSignjson() {
		return signjson;
	}

	public int setSignjson(String signjson, int nojm) {
		this.signjson = signjson;
		// LogUtils.LogWrite("setSignjson 1","2", signjson);
		// LogUtils.LogDebugWrite(action+" setSignjson 1 ", signjson);

		JSONObject jsonObject = JSONObject.fromObject(signjson);

		// System.out.println("action=" + action + " signjson=" +
		// httpinfo1.getSignjson());
		// 简单签名的调用｛"timestamp":"170216102345"｝

		timestamp = jsonObject.has("timestamp") ? jsonObject.getString("timestamp") : "151231235959";
		author = jsonObject.has("author") ? jsonObject.getString("author") : "***";

		DateFormat df = new SimpleDateFormat("yyMMddHHmmss");

		Date nowdate = pFunc.getServerdatetime();
		String nowdatetime = df.format(nowdate.getTime() - 1000 * 60 * 10); // 10分钟
		// System.out.println(nowdatetime);
		if (timestamp.compareTo(nowdatetime) < 0) {
			this.errmess = "{\"result\":-1,\"msg\":\"调用已超时:501！\",\"syserror\":\"调用已超时:501\"}";
			return 0;
		}
		longdatetime = Func.DateToStr(nowdate, "yyyy-MM-dd HH:mm:ss");

		url += " sign=" + signjson;
		//		 System.out.println(url);
		//		 System.out.println("signjson="+signjson);

		// 解密数据datajson
		if (datajson != null && !datajson.equals("")) {
			//			System.out.println("解密前datajson=" + datajson);

			if (nojm == 0) {// 数据解密

				String ss = "Fly#Ang" + this.signjson.toUpperCase() + "Sky@D20$16";
				String keystr = Func.StrtoMD5(ss); // 大写md5

				Func fyfun = new Func();
				datajson = fyfun.aesDecrypt(datajson, keystr);
				//				System.out.println("解密后datajson=" + datajson);

				if (datajson == null) {// 解密出错
					String mess = "url0=" + url0 + " url=" + url + " 密钥原文件=" + ss + " 密钥=" + keystr;
					LogUtils.LogErrWrite("aesDecrypt err", mess);

					System.out.println(mess);
					errmess = "{\"result\":-1,\"msg\":\"异常数据:511！\",\"syserror\":\"异常数据:511\"}";
					return 0;
				}
			}
			// datajson = datajson.replace("'", "");

			url += " data=" + datajson;

			JSONObject jsonObject1 = JSONObject.fromObject(datajson);

			// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"brandid,brandname","sort":"brandid","order":"asc"}
			String flyang = jsonObject1.has("flyang") ? jsonObject1.getString("flyang") : "******";

			if (!flyang.equals("20150107")) // 数据标志
			{
				errmess = "{\"result\":-1,\"msg\":\"异常数据:506！\",\"syserror\":\"异常数据:506\"}";
				return 0;
			}
			url1 += " data=" + datajson;
		}

		if (action.equals("test1")) {
			//			System.out.println(url1);
			errmess = Func.StrtoMD5(url1);
			return -1;
		}
		if (pictjson != null && !pictjson.equals(""))
			url += " pict={...}";
		// LogUtils.LogDebugWrite(action+" setSignjson 5", "aaaa");
		// try {
		// } catch (Exception e) {
		// }
		try {
			maccid = jsonObject.has("maccid") ? Long.parseLong(jsonObject.getString("maccid")) : -1;
		} catch (Exception e) {
			maccid = -1;
		}
		try {
			userid = jsonObject.has("userid") ? Long.parseLong(jsonObject.getString("userid")) : -1;
		} catch (Exception e) {
			userid = -1;
		}
		signature = jsonObject.has("signature") ? jsonObject.getString("signature") : "";
		// if (actionlist.contains("^" + action + "^"))// 不判断签名的接口=简单签名
		if (actionlist.contains(action)) {
			return 1;
		}

		if (maccid < 0 || userid < 0) {
			errmess = "{\"result\":-1,\"msg\":\"缺少maccid或userid参数:505！\",\"syserror\":\"账号参数无效:505\"}";
			LogUtils.LogErrWrite("缺少maccid或userid参数:505", url);
			return 0;
		}
		// 判断是否在后台维护时间
		DateFormat df1 = new SimpleDateFormat("HH:mm");
		String nowtime = df1.format(nowdate);// df1.format(pFunc.getServerdatetime());

		// maccid=1004 FYKJ2015 这是苹果专用测试账户
		// if (maccid != 1004 && maccid > 0 && nowtime.compareTo("00:00") >= 0
		// && nowtime.compareTo("00:30") <= 0) {
		if (maccid != 1004 && maccid > 0 && nowtime.compareTo("02:00") >= 0 && nowtime.compareTo("04:30") <= 0) {
			// errmess =
			// "{\"syserror\":\"系统维护中(00:00-00:30)，请稍候使用，谢谢！:509\""+nowtime+"}";
			errmess = "{\"result\":-1,\"msg\":\"系统维护中(02:00-04:30)，请稍候使用，谢谢！:509！\",\"syserror\":\"系统维护中(02:00-04:30)，请稍候使用，谢谢！:509\"}";
			return 0;
		}

		// 苹果升级之后开放这段代码
		Table tb = new Table();

		String qry = "select a.accesskey,a.accid,a.ontime,a.offtime,b.levelid,to_char(c.begindate,'yyyy-mm-dd') as begindate,b.epname "; // ,b.epname
		qry += ",c.qxcs,c.uqxcs,d.xxstr,d.glevelid,c.sizenum,to_char(c.calcdate,'yyyy-mm-dd') as calcdate";
		qry += ",c.smsuid,c.smsuser,c.smspwd,a.devicetype,c.tag as vertag";
		qry += "\n from useronline a ";
		qry += "\n left outer join employe b on a.epid=b.epid ";
		qry += "\n left outer join accreg c on b.accid=c.accid ";
		qry += "\n left outer join userrole d on b.levelid=d.levelid";
		qry += "\n where a.epid=" + userid + " and b.passkey=1";
		// System.out.println("111: "+qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("12345678");
		if (tb == null || tb.getRowCount() == 0) {
			errmess = "{\"result\":-1,\"msg\":\"未找到登录信息:508！\",\"syserror\":\"未找到登录信息:508\"}";
			// LogUtils.LogDebugWrite("setSignjson", qry);
			return 0;
		}
		String accesskey = tb.getRow(0).get("ACCESSKEY").toString();
		long maccid0 = Long.parseLong(tb.getRow(0).get("ACCID").toString());
		maccid = maccid0;

		// String ss = "FlyAng@2015" + userid + timestamp + accesskey+maccid;

		// Integer.parseInt
		String mintime = tb.getRow(0).get("ONTIME").toString();
		String maxtime = tb.getRow(0).get("OFFTIME").toString();
		levelid = Long.parseLong(tb.getRow(0).get("GLEVELID").toString());
		// System.out.println(workday);
		username = tb.getRow(0).get("EPNAME").toString();
		accdate = tb.getRow(0).get("BEGINDATE").toString();
		calcdate = tb.getRow(0).get("CALCDATE").toString();
		qxpublic = tb.getRow(0).get("UQXCS").toString();// 用户参数

		accpublic = tb.getRow(0).get("QXCS").toString();// 账套参数
		rolepublic = tb.getRow(0).get("XXSTR").toString();// 角色参数
		qxpublic = Func.subString(qxpublic + "0000000000000000000000000000000000000000", 1, 40);
		accpublic = Func.subString(accpublic + "0000000000", 1, 10);
		rolepublic = Func.subString(rolepublic + "0000000000", 1, 10);
		// qxpublic += rolepublic + accpublic;// 控制参数20+角色参数10+账户参数10
		// System.out.println("qxpublic===" + qxpublic);
		sizenum = Integer.parseInt(tb.getRow(0).get("SIZENUM").toString());
		devicetype = tb.getRow(0).get("DEVICETYPE").toString();// 运行设备版本 devicetype:APH=安桌手机，IPH=苹果手机,IPD=苹果IPD,WIN=pc
		vertag = Integer.parseInt(tb.getRow(0).get("VERTAG").toString());//版本 0=零售，1=批发，2=零售+批发，3=迷你

		smsuid = tb.getRow(0).get("SMSUID").toString();
		smsuser = tb.getRow(0).get("SMSUSER").toString();
		smspwd = tb.getRow(0).get("SMSPWD").toString();

		if (sizenum == 0)
			sizenum = 5;
		else if (sizenum > 15)
			sizenum = 15;
		// private String rolepublic = "0000000000"; // 用户角色参数(在角色中定义)
		// private String accpublic = "0000000000";// 账户控制参数
		// System.out.println("levelid="+levelid+" nowtime="+nowtime+"
		// mintime="+mintime+" maxtime="+maxtime);
		if (levelid > 0 && (!mintime.equals("00:00") || !maxtime.equals("00:00"))) {
			if (nowtime.compareTo(mintime) < 0 || nowtime.compareTo(maxtime) > 0) {
				errmess = "{\"result\":-1,\"msg\":\"非工作时间不允许使用:508！\",\"syserror\":\"非工作时间不允许使用:508\"}";
				return 0;
			}
		}
		// actionarrays1不判断签名的接口但要取登录信息
		if (maccid != 1000 && !actionlist1.contains(action) && nojm == 0) {
			String ss = "FlyAng@2015" + userid + timestamp + accesskey + maccid0;
			String ss1 = Func.StrtoMD5(ss);// System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(ss,
											// "MD5");
			if (!signature.equals(ss1)) {
				errmess = "{\"syserror\":\"签名无效,您已在另外的设备登录:510\",\"result\":-1,\"msg\":\"签名无效,您已在另外的设备登录:510！\"}";

				LogUtils.LogDebugWrite(maccid + "  " + errmess, "后台:" + "FlyAng@2015, userid=" + userid + " timestamp=" + timestamp + " accesskey=" + accesskey + " accid=" + maccid0//
						+ "  前端调用: " + jsonObject.toString());
				return 0;
			}
		}
		return 1;
	}

	// 上下衣校验接口
	public int setSignjsonSxy(String signjson) {
		this.signjson = signjson;

		JSONObject jsonObject = JSONObject.fromObject(signjson);

		// System.out.println("action=" + action + " signjson=" +
		// httpinfo1.getSignjson());
		// 简单签名的调用｛"timestamp":"170216102345"｝

		timestamp = jsonObject.has("timestamp") ? jsonObject.getString("timestamp") : "151231235959";
		Date nowdate = pFunc.getServerdatetime();

		DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
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
			String keystr = Func.StrtoMD5(ss); // 大写md5

			Func fyfun = new Func();
			datajson = fyfun.aesDecrypt(datajson, keystr);
			// datajson = datajson.replace("'", "");
			url += " data=" + datajson;

			JSONObject jsonObject1 = JSONObject.fromObject(datajson);
			// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"brandid,brandname","sort":"brandid","order":"asc"}
			String flyang = jsonObject1.has("flyang") ? jsonObject1.getString("flyang") : "******";

			if (!flyang.equals("20150107")) // 数据标志
			{
				errmess = "{\"result\":-1,\"msg\":\"缺少数据标志:506!\",\"syserror\":\"缺少数据标志:506\"}";
				return 0;
			}

		}
		if (pictjson != null && !pictjson.equals(""))
			url += " pict={...}";

		maccid = jsonObject.has("maccid") ? Long.parseLong(jsonObject.getString("maccid")) : -1;
		userid = jsonObject.has("userid") ? Long.parseLong(jsonObject.getString("userid")) : -1;
		signature = jsonObject.has("signature") ? jsonObject.getString("signature") : "";
		// if (actionlist.contains("^" + action + "^")) {
		if (actionlist.contains(action)) {
			return 1;
		}

		if (maccid < 0 || userid < 0) { // || levelid < 0
			errmess = "{\"result\":-1,\"msg\":\"账号参数无效:505!\",\"syserror\":\"账号参数无效:505\"}";
			return 0;
		}
		// 判断是否在后台维护时间
		DateFormat df1 = new SimpleDateFormat("HH:mm");
		String nowtime = df1.format(nowdate);
		// if (maccid != 1004 && maccid > 0 && nowtime.compareTo("00:00") >= 0
		// && nowtime.compareTo("00:30") <= 0) {
		if (maccid != 1004 && maccid > 0 && nowtime.compareTo("02:00") >= 0 && nowtime.compareTo("04:30") <= 0) {
			// errmess =
			// "{\"syserror\":\"系统维护中(00:00-00:30)，请稍候使用，谢谢！:509\""+nowtime+"}";
			errmess = "{\"result\":-1,\"msg\":\"系统维护中(02:00-04:30)，请稍候使用，谢谢！:509!\",\"syserror\":\"系统维护中(02:00-04:30)，请稍候使用，谢谢！:509\"}";
			return 0;
		}
		// 苹果升级之后开放这段代码
		Table tb = new Table();

		String qry = "select a.accesskey,a.accid,a.ontime,a.offtime,b.levelid,to_char(c.begindate,'yyyy-mm-dd') as begindate,b.epname "; // ,b.epname
		qry += ",c.qxcs,c.uqxcs,d.xxstr,d.glevelid,to_char(c.calcdate,'yyyy-mm-dd') as calcdate";
		// qry += "\n ,(select uvalue from uparameter where accid=0 and
		// usection='OPTIONS' and usymbol='WORKDAY') as WORKDAY";
		qry += ",c.smsuid,c.smsuser,c.smspwd";
		qry += " from useronline a ";
		qry += " left outer join employe b on a.epid=b.epid ";
		qry += " left outer join accreg c on b.accid=c.accid ";
		qry += " left outer join userrole d on b.levelid=d.levelid";
		qry += " where a.epid=" + userid + " and b.passkey=1";

		// System.out.println(qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("12345678");
		if (tb == null || tb.getRowCount() == 0) {
			errmess = "{\"result\":-1,\"msg\":\"未找到登录信息:508!\",\"syserror\":\"未找到登录信息:508\"}";
			return 0;
		}

		String accesskey = tb.getRow(0).get("ACCESSKEY").toString();
		long maccid0 = Long.parseLong(tb.getRow(0).get("ACCID").toString());

		// Integer.parseInt
		String mintime = tb.getRow(0).get("ONTIME").toString();
		String maxtime = tb.getRow(0).get("OFFTIME").toString();
		levelid = Long.parseLong(tb.getRow(0).get("GLEVELID").toString());

		username = tb.getRow(0).get("EPNAME").toString();
		accdate = tb.getRow(0).get("BEGINDATE").toString();
		calcdate = tb.getRow(0).get("CALCDATE").toString();

		qxpublic = tb.getRow(0).get("UQXCS").toString();// 用户参数
		accpublic = tb.getRow(0).get("QXCS").toString();// 账套参数
		rolepublic = tb.getRow(0).get("XXSTR").toString();// 角色参数
		// qxpublic = Func.subString(qxpublic +
		// "0000000000000000000000000000000000000000", 1, 40);
		// accpublic = Func.subString(accpublic + "0000000000", 1, 10);
		// rolepublic = Func.subString(rolepublic + "0000000000", 1, 10);

		qxpublic = Func.subString(qxpublic + "0000000000000000000000000000000000000000", 1, 40);
		accpublic = Func.subString(accpublic + "0000000000", 1, 10);
		rolepublic = Func.subString(rolepublic + "0000000000", 1, 10);
		// qxpublic += rolepublic + accpublic;// 控制参数20+角色参数10+账户参数10
		smsuid = tb.getRow(0).get("SMSUID").toString();
		smsuser = tb.getRow(0).get("SMSUSER").toString();
		smspwd = tb.getRow(0).get("SMSPWD").toString();

		if (levelid > 0 && !mintime.equals("00:00") && !maxtime.equals("00:00")) {
			if (nowtime.compareTo(mintime) < 0 || nowtime.compareTo(maxtime) > 0) {
				errmess = "{\"result\":-1,\"msg\":\"非工作时间不允许使用:508\",\"syserror\":\"非工作时间不允许使用:508\"}";
				return 0;
			}
		}
		if (maccid != 1000) {
			// string ss = "FlyAng@2015" + htp0.userid + htp0.timestamp +
			// accesskey + maccid0;

			String ss = "FlyAng@2015" + userid + timestamp + accesskey + maccid0;
			// System.out.println("签名=" + "FlyAng@2015 " + userid + " " +
			// timestamp + " " + accesskey + " " + maccid0);
			// +" "+ httpinfo1.getTimestamp()+" " + accesskey +" "+ maccid0);
			String ss1 = Func.StrtoMD5(ss);// System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(ss,
											// "MD5");
			if (!signature.equals(ss1)) {
				// System.out.println(signature + " ? " + ss1);
				// errmess = "{\"syserror\":\"签名无效:510\"}";
				// errmess = "{\"syserror\":\"您已在另外的设备登录:510\"}";
				errmess = "{\"result\":-1,\"msg\":\"签名无效,您已在另外的设备登录:510!\",\"syserror\":\"签名无效,您已在另外的设备登录:510\"}";
				return 0;

			}
		}
		// S
		return 1;
	}

	// 订货会校验接口
	public int setSignjsonOrd(String signjson) {
		this.signjson = signjson;

		JSONObject jsonObject = JSONObject.fromObject(signjson);

		// System.out.println("action=" + action + " signjson=" +
		// httpinfo1.getSignjson());
		// 简单签名的调用｛"timestamp":"170216102345"｝

		timestamp = jsonObject.has("timestamp") ? jsonObject.getString("timestamp") : "151231235959";
		Date nowdate = pFunc.getServerdatetime();
		DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		String nowdatetime = df.format(nowdate.getTime() - 1000 * 60 * 10); // 10分钟
		if (timestamp.compareTo(nowdatetime) < 0) {
			this.errmess = "{\"result\":-1,\"msg\":\"调用已超时:501!\",\"syserror\":\"调用已超时:501\"}";
			return 0;
		}

		url += " sign=" + signjson;

		// 解密数据datajson
		if (datajson != null && !datajson.equals("")) {

			String ss = "Fly#Ang" + this.signjson.toUpperCase() + "Sky@D20$16";
			String keystr = Func.StrtoMD5(ss); // 大写md5

			Func fyfun = new Func();
			datajson = fyfun.aesDecrypt(datajson, keystr);
			// datajson = datajson.replace("'", "");
			url += " data=" + datajson;

			JSONObject jsonObject1 = JSONObject.fromObject(datajson);
			// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"brandid,brandname","sort":"brandid","order":"asc"}
			String flyang = jsonObject1.has("flyang") ? jsonObject1.getString("flyang") : "******";

			if (!flyang.equals("20150107")) // 数据标志
			{
				errmess = "{\"result\":-1,\"msg\":\"异常数据:506!\",\"syserror\":\"异常数据:506\"}";
				return 0;
			}

		}
		if (pictjson != null && !pictjson.equals(""))
			url += " pict={...}";

		maccid = jsonObject.has("maccid") ? Long.parseLong(jsonObject.getString("maccid")) : -1;
		userid = jsonObject.has("userid") ? Long.parseLong(jsonObject.getString("userid")) : -1;
		// levelid = jsonObject.has("userid") ?
		// Long.parseLong(jsonObject.getString("mlevelid")) : -1;
		// username = jsonObject.has("username") ?
		// jsonObject.getString("username") : "";
		// custid = jsonObject.has("custid") ?
		// Long.parseLong(jsonObject.getString("custid")) : -1;
		omid = jsonObject.has("omid") ? Long.parseLong(jsonObject.getString("omid")) : -1;
		// accdate = jsonObject.has("accdate") ? jsonObject.getString("accdate")
		// : "";
		signature = jsonObject.has("signature") ? jsonObject.getString("signature") : "";
		if (actionlist.contains(action)) {

			// if (actionlist.contains("^" + action + "^")) {

			return 1;
		}
		// qxpublic = jsonObject.has("qxpublic") ?
		// jsonObject.getString("qxpublic") : "";
		// qxpublic += "0000000000000000000000000000000000000000";
		if (maccid < 0 || userid < 0) { // || levelid < 0
			errmess = " {\"result\":-1,\"msg\":\"异常数据:515!\",\"syserror\":\"异常数据:515\"}";
			return 0;
		}
		// 判断是否在后台维护时间
		DateFormat df1 = new SimpleDateFormat("HH:mm");
		String nowtime = df1.format(nowdate);
		// if (maccid != 1004 && maccid > 0 && nowtime.compareTo("00:00") >= 0
		// && nowtime.compareTo("00:30") <= 0) {
		if (maccid != 1004 && maccid > 0 && nowtime.compareTo("02:00") >= 0 && nowtime.compareTo("04:30") <= 0) {
			// errmess =
			// "{\"syserror\":\"系统维护中(00:00-00:30)，请稍候使用，谢谢！:509\""+nowtime+"}";
			errmess = "{\"result\":-1,\"msg\":\"系统维护中(02:00-04:30)，请稍候使用，谢谢！:509!\",\"syserror\":\"系统维护中(02:00-04:30)，请稍候使用，谢谢！:509\"}";
			return 0;
		}
		// 苹果升级之后开放这段代码
		Table tb = new Table();

		// String qry = "select
		// a.accesskey,a.accid,a.ontime,a.offtime,b.levelid,to_char(c.begindate,'yyyy-mm-dd')
		// as begindate,b.epname "; // ,b.epname
		// qry += " from useronline a ";
		// qry += " left outer join employe b on a.epid=b.epid ";
		// qry += " left outer join accreg c on b.accid=c.accid ";
		// qry += " where a.epid=" + userid;
		String qry = "select a.accesskey,a.accid,b.js as levelid,to_char(c.begindate,'yyyy-mm-dd') as begindate,b.omepname,b.statetag,b.custid "; // ,b.epname
		qry += ",c.smsuid,c.smsuser,c.smspwd";
		qry += " from omuseronline a ";
		qry += " left outer join omuser b on a.omepid=b.omepid ";
		qry += " left outer join accreg c on a.accid=c.accid ";
		qry += " where a.omepid=" + userid + " and b.statetag<>2";

		// System.out.println(qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("12345678");
		// if (tb == null) {
		// errmess = "{\"syserror\":\"未找到登录信息:508\"}";
		// return 0;
		// }
		if (tb == null || tb.getRowCount() == 0) {
			errmess = "{\"result\":-1,\"msg\":\"未找到登录信息:508!\",\"syserror\":\"未找到登录信息:508\"}";
			return 0;
		}
		// tb.getRow(index)
		int statetag = Integer.parseInt(tb.getRow(0).get("STATETAG").toString());
		if (statetag == 0) {
			errmess = "{\"result\":-1,\"msg\":\"当前账户已被禁用:519!\",\"syserror\":\"当前账户已被禁用:519\"}";
			// WriteLogTXT(username, "", "签名无效:617 "+ signature + " ? " + ss1);
			// HttpContext.Current.Response.Write(responsestr);
			return 0;
		} else if (statetag == 3) {
			errmess = "{\"result\":-1,\"msg\":\"当前账户因订货会截屏被锁:520!\",\"syserror\":\"当前账户因订货会截屏被锁:520\"}";
			// WriteLogTXT(username, "", "签名无效:617 "+ signature + " ? " + ss1);
			// HttpContext.Current.Response.Write(responsestr);
			return 0;
		}
		String accesskey = tb.getRow(0).get("ACCESSKEY").toString();
		long maccid0 = Long.parseLong(tb.getRow(0).get("ACCID").toString());

		// Integer.parseInt
		// String mintime = tb.getRow(0).get("ONTIME").toString();
		// String maxtime = tb.getRow(0).get("OFFTIME").toString();

		username = tb.getRow(0).get("OMEPNAME").toString();
		accdate = tb.getRow(0).get("BEGINDATE").toString();
		levelid = Long.parseLong(tb.getRow(0).get("LEVELID").toString());
		custid = Long.parseLong(tb.getRow(0).get("CUSTID").toString());
		smsuid = tb.getRow(0).get("SMSUID").toString();
		smsuser = tb.getRow(0).get("SMSUSER").toString();
		smspwd = tb.getRow(0).get("SMSPWD").toString();

		if (maccid != 1000) { // 演示账号不判断签名
			String ss = "FlyAng@2015" + userid + timestamp + accesskey + maccid0;
			String ss1 = Func.StrtoMD5(ss);// System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(ss,
			// "MD5");
			if (!signature.equals(ss1)) {
				// System.out.println(signature + " ? " + ss1);
				// errmess = "{\"syserror\":\"您已在另外的设备登录:510\"}";
				errmess = "{\"result\":-1,\"msg\":\"签名无效,您已在另外的设备登录:510!\",\"syserror\":\"签名无效,您已在另外的设备登录:510\"}";
				// errmess = "{\"syserror\":\"签名无效:510\"}";
				return 0;
			}
		}
		// S
		return 1;
	}

	// 短信接口
	public int setSignjsonSms(String signjson) {
		this.signjson = signjson;

		JSONObject jsonObject = JSONObject.fromObject(signjson);

		// System.out.println("action=" + action + " signjson=" +
		// httpinfo1.getSignjson());
		// 简单签名的调用｛"timestamp":"170216102345"｝

		timestamp = jsonObject.has("timestamp") ? jsonObject.getString("timestamp") : "151231235959";
		DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		String nowdatetime = df.format(pFunc.getServerdatetime().getTime() - 1000 * 60 * 10); // 10分钟
		if (timestamp.compareTo(nowdatetime) < 0) {
			this.errmess = "{\"result\":-1,\"msg\":\"调用已超时:501!\",\"syserror\":\"调用已超时:501\"}";
			return 0;
		}
		// maccid = jsonObject.has("maccid") ?
		// Long.parseLong(jsonObject.getString("maccid")) : -1;

		url += " sign=" + signjson;

		// 解密数据datajson
		if (datajson != null) {

			String ss = "Fly#Ang" + this.signjson.toUpperCase() + "Sky@D20$16";
			String keystr = Func.StrtoMD5(ss); // 大写md5

			Func fyfun = new Func();
			datajson = fyfun.aesDecrypt(datajson, keystr);
			url += " data=" + datajson;

			JSONObject jsonObject1 = JSONObject.fromObject(datajson);
			// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"brandid,brandname","sort":"brandid","order":"asc"}
			String flyang = jsonObject1.has("flyang") ? jsonObject1.getString("flyang") : "******";

			if (!flyang.equals("20150107")) // 数据标志
			{
				errmess = "{\"result\":-1,\"msg\":\"异常数据:506!\",\"syserror\":\"异常数据:506\"}";
				return 0;
			}

		}

		return 1;
	}

	// 银货通外部调用检验
	public int setSignjsonOut(String Signjson) {
		// this.signjson =Signjson;
		try {
			// System.out.println("000:" + Signjson);
			this.signjson = URLDecoder.decode(Signjson, "UTF-8");
			// System.out.println("111:" + signjson);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(signjson);

		// 简单签名的调用｛"timestamp":"170216102345"｝

		timestamp = jsonObject.has("timestamp") ? jsonObject.getString("timestamp") : "151231235959";
		DateFormat df = new SimpleDateFormat("yyMMddHHmmss");

		Date nowdate = pFunc.getServerdatetime();
		String nowdatetime = df.format(nowdate.getTime() - 1000 * 60 * 10); // 10分钟
		if (timestamp.compareTo(nowdatetime) < 0) {
			this.errmess = "{\"result\":-1,\"msg\":\"调用已超时:501!\",\"syserror\":\"调用已超时:501\"}";
			return 0;
		}
		longdatetime = Func.DateToStr(nowdate, "yyyy-MM-dd HH:mm:ss");

		url += " sign=" + signjson;
		// System.out.println(url);
		// LogUtils.LogDebugWrite(action+" setSignjson 2", url);
		//		url += " data=" + datajson;
		// 解密数据datajson 暂时不用

		if (datajson != null && !datajson.equals("")) {

			// String ss = "Fly#Ang" + this.signjson.toUpperCase() +
			// "Sky@D20$16";
			//			String ss = "Fly#Out" + this.signjson.toUpperCase() + "Sky@D20$17";
			//			// keystr:=StrMD5('Fly#Out'+uppercase(signjson)+'Sky@D20$17');
			//			String keystr = Func.StrtoMD5(ss); // 大写md5
			//			System.out.println("signjson=" + this.signjson.toUpperCase() + ",keystr=" + keystr);
			//			Func fyfun = new Func();
			//			datajson = fyfun.aesDecrypt(datajson, keystr);
			//
			//			if (datajson == null) {// 解密出错
			//				LogUtils.LogErrWrite("aesDecrypt err", "url0=" + url0 + " url=" + url + " 密钥原文件=" + ss + " 密钥=" + keystr);
			//				errmess = "{\"syserror\":\"异常数据:511\"}";
			//				return 0;
			//			}

			url += " data=" + datajson;

			System.out.println(url);

			JSONObject jsonObject1 = JSONObject.fromObject(datajson);
			// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"brandid,brandname","sort":"brandid","order":"asc"}
			String flyang = jsonObject1.has("flyang") ? jsonObject1.getString("flyang") : "******";

			if (!flyang.equals("20150107")) // 数据标志
			{
				errmess = "{\"result\":-1,\"msg\":\"异常数据:506!\",\"syserror\":\"异常数据:506\"}";
				return 0;
			}
		}

		if (pictjson != null && !pictjson.equals(""))
			url += " pict={...}";

		try {
			userid = jsonObject.has("userid") ? Long.parseLong(jsonObject.getString("userid")) : -1;
		} catch (Exception e) {
			userid = -1;
		}
		signature = jsonObject.has("signature") ? jsonObject.getString("signature") : "";
		String surname = jsonObject.has("surname") ? jsonObject.getString("surname") : "";
		String idno = jsonObject.has("idno") ? jsonObject.getString("idno") : "";

		// if (actionlist.contains("^" + action + "^"))// 不判断签名的接口
		if (actionlist.contains(action)) {
			return 1;
		}

		if (userid <= 0 || Func.isNull(surname) || Func.isNull(idno) || Func.isNull(signature)) {
			errmess = "{\"result\":-1,\"msg\":\"账号参数无效:505!\",\"syserror\":\"账号参数无效:505\"}";
			LogUtils.LogErrWrite("账号参数无效:505", url);
			return 0;
		}

		Table tb = new Table();

		String qry = "select a.accid,a.epname,a.epid,b.sessionkey "; // ,b.epname
		qry += ",to_char(b.calcdate,'yyyy-mm-dd') as calcdate,to_char(b.begindate,'yyyy-mm-dd') as begindate";
		qry += "\n from employe a join accreg b on a.accid=b.accid ";
		qry += "\n where a.epid=" + userid;
		//		System.out.println(qry);
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb == null || tb.getRowCount() == 0) {
			errmess = "{\"result\":-1,\"msg\":\"未找到接口登录信息:508!\",\"syserror\":\"未找到接口登录信息:508\"}";
			// LogUtils.LogDebugWrite("setSignjson", qry);
			return 0;
		}
		username = tb.getRow(0).get("EPNAME").toString();
		String sessionkey = tb.getRow(0).get("SESSIONKEY").toString();

		if (!actionlist1.contains(action)) {// 判断签名
			String ss = "FlyOut@" + timestamp + "@" + sessionkey + "@" + userid + "@2017";
			String ss1 = Func.StrtoMD5(ss);
			// System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(ss,MD5");
			if (!signature.equals(ss1)) {
				errmess = "{\"result\":-1,\"msg\":\"签名无效,您已在另外的设备登录:510!\",\"syserror\":\"签名无效,您已在另外的设备登录:510\"}";
				return 0;
			}
		}
		//		qry = "select a.accid,a.sessionkey "; // ,b.epname
		//		qry += ",to_char(a.calcdate,'yyyy-mm-dd') as calcdate,to_char(a.begindate,'yyyy-mm-dd') as begindate";
		//		qry += "\n from accreg a ";
		//		qry += "\n where a.linkman='" + surname + "' and a.idno='" + idno + "' and rownum=1";	
		//		qry = "select a.accid "; // ,b.epname  //,a.sessionkey
		//		qry += ",to_char(b.calcdate,'yyyy-mm-dd') as calcdate,to_char(b.begindate,'yyyy-mm-dd') as begindate";
		//		qry += "\n from creditrecord a join accreg b on a.accid=b.accid ";
		//		qry += "\n where a.surname='" + surname + "' and a.idno='" + idno + "' and rownum=1 and a.statetag>=1 and statetag<6";

		qry = "select a.accid,a.dkok "; // ,b.epname  //,a.sessionkey
		qry += ",to_char(a.calcdate,'yyyy-mm-dd') as calcdate,to_char(a.begindate,'yyyy-mm-dd') as begindate";
		qry += "\n from accreg a ";
		qry += "\n where a.dksurname='" + surname + "' and a.dkidno='" + idno + "' and rownum=1 ";

		// statetag:0=申请，1=提交，2=审核通过 3=贷款中，4=已还款，7=异常 ，8=申请驳回,9=删除

		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("12345678");
		if (tb == null || tb.getRowCount() == 0) {
			errmess = "{\"result\":-1,\"msg\":\"未找到贷款人信息:508!\",\"syserror\":\"未找到贷款人信息:508\"}";
			// LogUtils.LogDebugWrite("setSignjson", qry);
			return 0;
		}
		if (!tb.getRow(0).get("DKOK").toString().equals("1")) {
			errmess = "{\"result\":-1,\"msg\":\"贷款人未同意协议:509!\",\"syserror\":\"贷款人未同意协议:509\"}";
			return 0;
		}
		maccid = Long.parseLong(tb.getRow(0).get("ACCID").toString());
		accdate = tb.getRow(0).get("BEGINDATE").toString();
		calcdate = tb.getRow(0).get("CALCDATE").toString();

		// System.out.println(username);
		// accpublic = tb.getRow(0).get("QXCS").toString();// 账套参数
		// rolepublic = tb.getRow(0).get("XXSTR").toString();// 角色参数
		// qxpublic = Func.subString(tb.getRow(0).get("UQXCS").toString() +
		// "0000000000000000000000000000000000000000", 1, 20);
		// accpublic = Func.subString(accpublic + "0000000000", 1, 10);
		// rolepublic = Func.subString(rolepublic + "0000000000", 1, 10);
		// LogUtils.LogDebugWrite(action+" setSignjson ok ", " okokok");
		return 1;
	}

	// 标准外部调用检验
	public int setSignjsonExt(String signjson) {
		this.signjson = signjson;
		// LogUtils.LogWrite("setSignjson 1","2", signjson);

		// LogUtils.LogDebugWrite(action+" setSignjson 1 ", signjson);

		JSONObject jsonObject = JSONObject.fromObject(signjson);

		// System.out.println("action=" + action + " signjson=" +
		// httpinfo1.getSignjson());
		// 简单签名的调用｛"timestamp":"170216102345"｝

		timestamp = jsonObject.has("timestamp") ? jsonObject.getString("timestamp") : "151231235959";
		DateFormat df = new SimpleDateFormat("yyMMddHHmmss");

		Date nowdate = pFunc.getServerdatetime();
		String nowdatetime = df.format(nowdate.getTime() - 1000 * 60 * 10); // 10分钟
		if (timestamp.compareTo(nowdatetime) < 0) {
			this.errmess = "{\"result\":-1,\"msg\":\"调用已超时:501!\",\"syserror\":\"调用已超时:501\"}";
			return 0;
		}
		longdatetime = Func.DateToStr(nowdate, "yyyy-MM-dd HH:mm:ss");

		url += " sign=" + signjson;
		// LogUtils.LogDebugWrite(action+" setSignjson 2", url);

		// 解密数据datajson 暂时不用
		if (datajson != null && !datajson.equals("")) {

			// String ss = "Fly#Ang" + this.signjson.toUpperCase() +
			// "Sky@D20$16";
			String ss = "Fly#Ext" + this.signjson.toUpperCase() + "Sky@D20$17";

			// keystr:=StrMD5('Fly#Out'+uppercase(signjson)+'Sky@D20$17');

			String keystr = Func.StrtoMD5(ss); // 大写md5

			Func fyfun = new Func();
			datajson = fyfun.aesDecrypt(datajson, keystr);

			url += " data=" + datajson;

			JSONObject jsonObject1 = JSONObject.fromObject(datajson);
			// {"flyang":"20150107","rows":10,"page":1,"fieldlist":"brandid,brandname","sort":"brandid","order":"asc"}
			String flyang = jsonObject1.has("flyang") ? jsonObject1.getString("flyang") : "******";

			if (!flyang.equals("20150107")) // 数据标志
			{
				errmess = "{\"result\":-1,\"msg\":\"异常数据:506!\",\"syserror\":\"异常数据:506\"}";
				return 0;
			}
		}

		if (pictjson != null && !pictjson.equals(""))
			url += " pict={...}";

		// try {
		// maccid = jsonObject.has("maccid") ?
		// Long.parseLong(jsonObject.getString("maccid")) : -1;
		// } catch (Exception e) {
		// maccid = -1;
		// }
		try {
			userid = jsonObject.has("userid") ? Long.parseLong(jsonObject.getString("userid")) : -1;
		} catch (Exception e) {
			userid = -1;
		}
		signature = jsonObject.has("signature") ? jsonObject.getString("signature") : "";

		// if (actionlist.contains("^" + action + "^"))// 不判断签名的接口
		if (actionlist.contains(action)) {
			return 1;
		}

		if (userid < 0) {
			errmess = "{\"result\":-1,\"msg\":\"账号参数无效:505!\",\"syserror\":\"账号参数无效:505\"}";
			LogUtils.LogErrWrite("用户账号参数无效:505", url);
			return 0;
		}

		Table tb = new Table();

		String qry = "select a.epname,a.epid,b.sessionkey,a.accid "; // ,b.epname
		qry += ",to_char(b.calcdate,'yyyy-mm-dd') as calcdate,to_char(b.begindate,'yyyy-mm-dd') as begindate";
		qry += "\n from employe a join accreg b on a.accid=b.accid ";
		qry += "\n where a.epid=" + userid + " and rownum=1";
		// System.out.println("111: "+qry);

		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("12345678");
		if (tb == null || tb.getRowCount() == 0) {
			errmess = "{\"result\":-1,\"msg\":\"未找到用户登录账号信息:508!\",\"syserror\":\"未找到用户登录账号信息:508\"}";
			// LogUtils.LogDebugWrite("setSignjson", qry);
			return 0;
		}
		String sessionkey = tb.getRow(0).get("SESSIONKEY").toString();
		maccid = Long.parseLong(tb.getRow(0).get("ACCID").toString());
		username = tb.getRow(0).get("EPNAME").toString();
		accdate = tb.getRow(0).get("BEGINDATE").toString();
		calcdate = tb.getRow(0).get("CALCDATE").toString();

		// accpublic = tb.getRow(0).get("QXCS").toString();// 账套参数
		// rolepublic = tb.getRow(0).get("XXSTR").toString();// 角色参数
		// qxpublic = Func.subString(tb.getRow(0).get("UQXCS").toString() +
		// "0000000000000000000000000000000000000000", 1, 20);
		// accpublic = Func.subString(accpublic + "0000000000", 1, 10);
		// rolepublic = Func.subString(rolepublic + "0000000000", 1, 10);
		if (!actionlist1.contains(action)) {// 判断签名
			// String ss = "FlyAng@2015" + userid + timestamp + accesskey +
			// maccid0;
			String ss = "FlyExt@" + timestamp + "@" + sessionkey + "@" + userid + "@2017";
			// System.out.println("签名=" + "FlyAng@2015 " + userid + " " +
			// timestamp + " " + accesskey + " " + maccid0);
			// +" "+ httpinfo1.getTimestamp()+" " + accesskey +" "+ maccid0);
			String ss1 = Func.StrtoMD5(ss);// System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(ss,
			// "MD5");
			if (!signature.equals(ss1)) {
				// errmess = "{\"syserror\":\"签名无效:510\"}";
				errmess = "{\"result\":-1,\"msg\":\"签名无效,您已在另外的设备登录:510!\",\"syserror\":\"签名无效,您已在另外的设备登录:510\"}";
				return 0;
			}
		}
		// LogUtils.LogDebugWrite(action+" setSignjson ok ", " okokok");
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

	public String getAccdate() {
		return accdate;
	}

	public void setAccdate(String accdate) {
		this.accdate = accdate;
	}

	public long getMaccid() {
		return maccid;
	}

	public void setMaccid(long maccid) {
		this.maccid = maccid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getLevelid() {
		return levelid;
	}

	public void setLevelid(long levelid) {
		this.levelid = levelid;
	}

	public long getOmid() {
		if (omid < 0)
			return 0;
		return omid;
	}

	public void setOmid(long omid) {
		this.omid = omid;
	}

	public long getCustid() {
		return custid;
	}

	public long getProvid() {
		return provid;
	}

	public void setCustid(long custid) {
		this.custid = custid;
	}

	public String getRolepublic() {
		return rolepublic;
	}

	// public void setRolepublic(String rolepublic) {
	// this.rolepublic = rolepublic;
	// }

	public String getAccpublic() {
		return accpublic;
	}

	// public void setAccpublic(String accpublic) {
	// this.accpublic = accpublic;
	// }

}
