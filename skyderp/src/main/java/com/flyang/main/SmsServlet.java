package com.flyang.main;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.tool.Func;
import com.common.tool.LogUtils;
import com.oracle.bean.Smsrecord;
import com.netdata.db.DbHelperSQL;

import net.sf.json.JSONObject;

/**
 * 短信接口SmsServlet
 */
@WebServlet("/sms")
public class SmsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String url = "http://210.5.152.50:7100/submit";
	private String url1 = "http://sms.10690221.com:9011/hy/";
	//	http://ip:port/hy/m?uid=1234&auth=fcea920f7412b5da7be0cf42b8c93759

	// 蓝窗店管家用户uid：805351
	// 企业代码：flyang
	// 密：flyang2015
	private String uid = "805351";// ConfigurationManager.AppSettings["uid"].ToString();
	// private String auth = System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile("flyangflyang2015", "MD5").ToLower(); // MD5(企业代码+用户密码),32位加密小写
	private String auth = Func.StrtoMD5("flyangflyang2015").toLowerCase(); // MD5(企业代码+用户密码),32位加密小写
	// 蓝窗会员 用户uid：805352
	// 企业代码：flyang1
	// 密：flyang2016
	private String uid1 = "805352";// ConfigurationManager.AppSettings["uid"].ToString();
	// private String auth1 = System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile("flyang1flyang2016", "MD5").ToLower(); // MD5(企业代码+用户密码),32位加密小写
	private String auth1 = Func.StrtoMD5("flyang1flyang2016").toLowerCase(); // MD5(企业代码+用户密码),32位加密小写

	// 蓝窗店管家网页注册专用 用户uid：805353
	// 企业代码：flyang2
	// 密：flyang2016
	private String uid2 = "805353";// ConfigurationManager.AppSettings["uid"].ToString();
	// private String auth2 = System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile("flyang2flyang2016", "MD5").ToLower(); // MD5(企业代码+用户密码),32位加密小写
	private String auth2 = Func.StrtoMD5("flyang2flyang2016").toLowerCase(); // MD5(企业代码+用户密码),32位加密小写

	// 原创店管家用
	// 用户uid：805354
	// 企业代码：linwodcl
	// 密：linwodcl2017

	private String uid_dcl_erp = "805354";// ConfigurationManager.AppSettings["uid"].ToString();
	// private String auth2 = System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile("flyang2flyang2016", "MD5").ToLower(); // MD5(企业代码+用户密码),32位加密小写
	private String auth2_dcl_erp = Func.StrtoMD5("linwodcllinwodcl2017").toLowerCase(); // MD5(企业代码+用户密码),32位加密小写

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SmsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		// response.getWriter().append("sms ").append(request.getContextPath());
		String url = request.getRequestURL() + "?" + request.getQueryString();
		String action = request.getParameter("action");

		HttpInfo htp1 = new HttpInfo();
		htp1.setAction(action);// 设action

		String signjson = request.getParameter("sign"); // 取sign参数
		if (signjson == null || signjson == "") {
			WriteResult(response, 0, "sign参数无效！");
			return;
		}
		signjson = Func.Base64Decode(signjson);// sign base64解码
		htp1.setUrl(url);//

		htp1.setDatajson(request.getParameter("data"));// 传入data参数
		// htp1.setPictjson(request.getParameter("pict"));// 传入pict参数
		if (htp1.setSignjsonSms(signjson) == 0)// 校验参数是否有效
		{
			Write(response, htp1.getErrmess());
			return;

		}

		LogUtils.LogWrite(htp1.getUsername(), htp1.getUrl());

		switch (action) {

		// case "getcount": //验证登录
		// GetAccount();
		// break;
		case "getsms": // 获取短信校验码,找回密码用
			Getsms(response, htp1, 0);
			break;
		case "sendsms": // 返回验 证码
			Sendsms(response, htp1, 0); // 正式
			break;

		case "getsmsdcl": // 原创店管家获取短信校验码,找回密码用
			Getsms_dcl(response, htp1, 0);
			break;

		case "sendsmsdcl": // 原创店管家返回验 证码
			Sendsms_dcl(response, htp1, 0); // 正式
			break;

		// case "sendsms1": // 企业注册返回验证码
		// Sendsms(response, htp1, 0); // Sendsms(1);
		// break;
		// case "sendsms3": //获取会员储值消费验证码
		// Sendsms3();
		// break;
		case "sendsms4": // web企业注册返回验证码
			Sendsms4(response, htp1);
			break;

		case "sendsms2": // VIP注册返回验证码
			Sendsms(response, htp1, 2);
			break;
		case "getsms2": // VIP获取短信校验码,找回密码用
			Getsms(response, htp1, 2);
			break;
		default:
			WriteResult(response, 0, "接口未定义1:" + action + "!");
			// response.getWriter().append("接口未定义:"+action+" !");
			// String jsonstr="{\"result\":0,\"msg\":\"接口 "+action+" 未定义!\"}";
			// response.getWriter().write(jsonstr);
			break;
		}
	}

	/// <summary>
	/// app注册返回验证码
	/// </summary>
	/// <param name="fs"></param>

	protected void Sendsms(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {

		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		Smsrecord dal = new Smsrecord();
		dal.setMobile(mobile);
		long vipid = 0;// jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;
		long accid = 0;// jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;
		// System.out.println("Sendsms 0 ");
		if (fs == 0)
			accid = jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;
		else
			vipid = jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;

		if (dal.doSendsms(accid, vipid, fs) == 0) {
			WriteResult(response, 0, dal.getErrmess());
			return;
		}
		// System.out.println("Sendsms 1 ");
		String sjh = Func.getRandomNum(6);
		String smsstr = "校验码:" + sjh + "，为保证您的账号安全，请勿转发他人。";
		// WriteDebugTXT("sendsms_2", smsstr);
		if (fs == 0)
			SendMessage1(response, mobile, sjh, smsstr, "蓝窗店管家", 0); // 蓝窗店管家
		else
			SendMessage2(response, mobile, sjh, smsstr, "蓝窗会员"); // 蓝窗会员
	}

	// 原创注册
	protected void Sendsms_dcl(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {

		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		Smsrecord dal = new Smsrecord();
		dal.setMobile(mobile);
		long vipid = 0;// jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;
		long accid = 0;// jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;
		// System.out.println("Sendsms 0 ");
		if (fs == 0)
			accid = jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;
		else
			vipid = jsonObject.has("vipid") ? Long.parseLong(jsonObject.getString("vipid")) : 0;

		if (dal.doSendsms(accid, vipid, fs) == 0) {
			WriteResult(response, 0, dal.getErrmess());
			return;
		}
		// System.out.println("Sendsms 1 ");
		String sjh = Func.getRandomNum(6);
		String smsstr = "校验码:" + sjh + "，为保证您的账号安全，请勿转发他人。";
		// private String uid_dcl_erp = "805354";// ConfigurationManager.AppSettings["uid"].ToString();
		// private String auth2_dcl_erp = Func.StrtoMD5("linwodcllinwodcl2017").toLowerCase(); // MD5(企业代码+用户密码),32位加密小写
		// if (fs == 0)
		SendMessage(response, mobile, sjh, smsstr, "原创进销存", uid_dcl_erp, auth2_dcl_erp);
		// else
		// SendMessage2(response, mobile, sjh, smsstr, "取校验码"); // 蓝窗会员
	}

	/// <summary>
	/// web注册返回验证码
	/// </summary>

	protected void Sendsms4(HttpServletResponse response, HttpInfo htp) throws ServletException, IOException {

		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
		long accid = jsonObject.has("accid") ? Long.parseLong(jsonObject.getString("accid")) : 0;
		Smsrecord dal = new Smsrecord();
		dal.setMobile(mobile);
		if (dal.doSendsms4(accid) == 0) {
			WriteResult(response, 0, dal.getErrmess());
			return;
		}
		// ==============================================
		String sjh = Func.getRandomNum(6);
		String smsstr = "校验码:" + sjh + "，为保证您的账号安全，请勿转发他人。";
		SendMessage1(response, mobile, sjh, smsstr, "蓝窗店管家", 1); // 蓝窗店管家

	}

	private void Getsms(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";

		Smsrecord dal = new Smsrecord();
		dal.setMobile(mobile);

		if (dal.doGetms(fs) == 0) {
			WriteResult(response, 0, dal.getErrmess());
			return;
		}

		String sjh = Func.getRandomNum(6);
		String smsstr = "校验码:" + sjh + "，为保证您的账号安全，请勿转发他人。";
		if (fs == 0)
			SendMessage1(response, mobile, sjh, smsstr, "蓝窗店管家", 0);
		else
			SendMessage2(response, mobile, sjh, smsstr, "蓝窗会员");

	}

	// 原创
	private void Getsms_dcl(HttpServletResponse response, HttpInfo htp, int fs) throws ServletException, IOException {
		JSONObject jsonObject = JSONObject.fromObject(htp.getDatajson());
		String mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";

		Smsrecord dal = new Smsrecord();
		dal.setMobile(mobile);

		if (dal.doGetms(fs) == 0) {
			WriteResult(response, 0, dal.getErrmess());
			return;
		}

		String sjh = Func.getRandomNum(6);
		String smsstr = "校验码:" + sjh + "，为保证您的账号安全，请勿转发他人。";
		// if (fs == 0)
		SendMessage(response, mobile, sjh, smsstr, "原创进销存", uid_dcl_erp, auth2_dcl_erp);
		// else
		// SendMessage2(response, mobile, sjh, smsstr, "蓝窗会员");

	}

	protected void WriteResult(HttpServletResponse response, int result, String mess) throws ServletException, IOException {
		String jsonstr = "{\"result\":" + result + ",\"msg\":\"" + mess + "\"}";
		response.getWriter().write(jsonstr);
	}

	protected void WriteResultJson(HttpServletResponse response, int result, String mess) throws ServletException, IOException {
		String jsonstr = "{\"result\":" + result + "," + mess + "}";
		response.getWriter().write(jsonstr);
	}

	protected void Write(HttpServletResponse response, String outstr) throws ServletException, IOException {
		response.getWriter().write(outstr);
		// return;
	}

	/// <summary>
	/// 通用新接口 店管家注册
	/// </summary>
	protected void SendMessage(HttpServletResponse response, String phone, String sjs, String content, String operant, String myuid, String myauth) // 发验 证码短信
			throws ServletException, IOException {
		String content0 = content;
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd+hh:mm:ss");

		// String ss = df.format(new Date()).replace(":", "%3A");

		String param = "";
		param = "uid=" + myuid + "&auth=" + myauth + "&mobile=" + phone + "&msg=" + content0 + "&expid=0&encode=utf-8";// &time=" + ss;
		// WriteResult("url", url1);
		if (content.equals("") || phone.equals("")) {
			WriteResult(response, 0, "短信内容或者发送移动电话为空！");
		} else {
			if (Func.NetIsConn()) {
				String msg = Func.getHtmlFromUrl(url1, "GET", param);

				if (msg.substring(0, 1).equals("0")) // 成功
				{
					String qry = "insert into smsrecord (id,senddate,messstr,mobile,smspwd,operant,tag) values (smsrecord_id.nextval,sysdate,'" + content0 + "','" + phone + "','" + sjs + "','" + operant + "',0)";
					// System.out.println(qry);
					DbHelperSQL.ExecuteSql(qry);
					WriteResult(response, 1, "校验码已发送到" + phone + "，请尽快输入！");
				} else {
					WriteResult(response, 0, "发送失败1:" + msg);
				}
			} else {
				WriteResult(response, 0, "网络未连通！");
			}
		}

	}

	/// <summary>
	/// 新接口 店管家注册
	/// </summary>
	protected void SendMessage1(HttpServletResponse response, String phone, String sjs, String content, String operant, int bj) // 发验 证码短信
			throws ServletException, IOException {
		String content0 = content;
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd+hh:mm:ss");
		// String ss = df.format(new Date()).replace(":", "%3A");

		// String ss=DateTime.Now.ToString("yyyy-MM-dd+hh:mm:ss").Replace(":","%3A");
		String param = "";
		// bj:1=web端注册
		if (bj == 1)
			param = "uid=" + uid2 + "&auth=" + auth2 + "&mobile=" + phone + "&msg=" + content0 + "&expid=0&encode=utf-8";// &time=" + ss;
		else
			param = "uid=" + uid + "&auth=" + auth + "&mobile=" + phone + "&msg=" + content0 + "&expid=0&encode=utf-8";// &time=" + ss;
		// WriteResult("url", url1);
		if (content.equals("") || phone.equals("")) {
			WriteResult(response, 0, "短信内容或者发送移动电话为空！");
		} else {
			if (Func.NetIsConn()) {
				String msg = Func.getHtmlFromUrl(url1, "GET", param);

				if (msg.substring(0, 1).equals("0")) // 成功
				{
					String qry = "insert into smsrecord (id,senddate,messstr,mobile,smspwd,operant,tag) values (smsrecord_id.nextval,sysdate,'" + content0 + "','" + phone + "','" + sjs + "','" + operant + "',0)";
					// System.out.println(qry);
					DbHelperSQL.ExecuteSql(qry);
					WriteResult(response, 1, "校验码已发送到" + phone + "，请尽快输入！");
				} else {
					WriteResult(response, 0, "发送失败1:" + msg);
				}
			} else {
				WriteResult(response, 0, "网络未连通！");
			}
		}

	}

	/// <summary>
	/// 会员注册
	/// </summary>
	/// <param name="phone"></param>
	/// <param name="sjs"></param>
	/// <param name="content"></param>
	/// <param name="operant"></param>

	protected void SendMessage2(HttpServletResponse response, String phone, String sjs, String content, String operant) // 发验 证码短信
			throws ServletException, IOException {
		String content0 = content;
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd+hh:mm:ss");
		// String ss = df.format(new Date()).replace(":", "%3A");

		String param = "uid=" + uid1 + "&auth=" + auth1 + "&mobile=" + phone + "&msg=" + content0 + "&expid=0&encode=utf-8";// &time=" + ss;
		// WriteResult("url", url1);
		if (content.equals("") || phone.equals("")) {
			WriteResult(response, 0, "短信内容或者发送移动电话为空！");
		} else {
			// LogUtils.LogDebugWrite("SendMessage1.1", "");
			if (Func.NetIsConn()) {
				// LogUtils.LogDebugWrite("SendMessage2", param);
				String msg = Func.getHtmlFromUrl(url1, "GET", param);
				// LogUtils.LogDebugWrite("SendMessage3", msg);
				if (msg.substring(0, 1).equals("0")) // 成功
				{
					String qry = "insert into smsrecord (id,senddate,messstr,mobile,smspwd,operant,tag) values (smsrecord_id.nextval,sysdate,'" + content0 + "','" + phone + "','" + sjs + "','" + operant + "',0)";
					// System.out.println(qry);
					DbHelperSQL.ExecuteSql(qry);
					WriteResult(response, 1, "校验码已发送到" + phone + "，请尽快输入！");
				} else {
					WriteResult(response, 0, "发送失败2:" + msg);
				}
			} else {
				// LogUtils.LogDebugWrite("SendMessage3", "网络未连通");
				WriteResult(response, 0, "网络未连通！");
			}
		}

	}

}
