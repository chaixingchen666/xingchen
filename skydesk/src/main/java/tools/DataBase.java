/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
import com.uwantsoft.goeasy.client.goeasyclient.GoEasy;
import com.uwantsoft.goeasy.client.goeasyclient.listener.GoEasyError;
import com.uwantsoft.goeasy.client.goeasyclient.listener.PublishListener;
*/

/**
 * http://dev.skydispark.com:94/interface/ebdress.aspx?action=serverdate
 * 
 * @author m495
 */
public class DataBase {
	public static final JSONObject CONFIGOBJ = ReaderServer.getIPJson();
	public static final String SIP = CONFIGOBJ.getString("ServerIP"); 
	public static final String JERPIP = CONFIGOBJ.getString("JerpIP");
	public static final String VIPIP = CONFIGOBJ.getString("VipIP");
	public static final String OUTSIP = CONFIGOBJ.getString("OutIP");
	public static final String IMGIP = CONFIGOBJ.getString("ImgIP");
	public static final String IMGSHOWIP = CONFIGOBJ.getString("ImgShowIP");
	public static boolean isCS = "true".equalsIgnoreCase(CONFIGOBJ.getString("outBool"));
	public static final String SNAME = AppData.versionname;
	public static String HostIP = SIP + "/interface/erp.aspx?action=";
	public static String HostIP2 = SIP + "/interface/erp2.aspx?action=";
	public static String HostSM2 = SIP + "/interface/sms2.aspx?action=";
	public static String HostSM = SIP + "/interface/sms.aspx?action=";
	public static String _HostIP = SIP + "/interface/erp.aspx?";
	public static String ImageIP = IMGIP + "/interface/erp.aspx?action=";
	public static String ImageshowIP = IMGSHOWIP + "/image/";
	public static final String VERSION = "3.1.101";

//	public static boolean isCS = false;
	public static String getJsonContent(String urlStr) {// 得到对应URL的json数据流
		try {
			URL url = new URL(removekg(urlStr));
			if (DataBase.isCS)
				System.out.println(url);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(100000); // 连接数超时也是100s
			httpConn.setReadTimeout(100000); // 读取数据超时也是100s
			httpConn.setDoInput(true);
			httpConn.setUseCaches(false);
			httpConn.setRequestMethod("GET");
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			// if(DataBase.isCS)System.out.println(respCode);
			if (respCode == 200) {
				String restr = ConvertStream2Json(httpConn.getInputStream());
				if (DataBase.isCS)
					System.out.println(restr);
				httpConn.getInputStream().close();
				httpConn.disconnect();
				return restr;
			} else if (respCode == 404) {
				httpConn.disconnect();
				return "syserror";
			} else {
				httpConn.disconnect();
				return "fwq";
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			if (DataBase.isCS)
				System.out.println("-------连接超时1-----");
			e.printStackTrace();
			return "fwq";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (DataBase.isCS)
				System.out.println("-------数据异常-----");
			e.printStackTrace();
			return "fwq";
		}
	}

	public static String postJsonContent(String apiUrl, HashMap<String, String> params) {
		// post方法
		try {
			String str = null;
			URL url = new URL(removekg(apiUrl));// 根据参数创建URL对象
			HttpURLConnection con = (HttpURLConnection) url.openConnection();// 得到HttpURLConnection对象
			con.setRequestMethod("POST");
			con.setUseCaches(false);
			con.setReadTimeout(100000);
			con.setDoInput(true);
			con.setDoOutput(true);// 指示应用程序要将数据写入 URL 连接。
			String content = getContent(params);// 解析参数（请求的内容）
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置内容
			if (content != null) {
				con.setRequestProperty("Content-Length", content.length() + "");// 设置内容长度
				OutputStream os = con.getOutputStream();
				os.write(content.getBytes("utf-8"));// 发送参数内容
				if (con.getResponseCode() == 200) {
					str = ConvertStream2Json(con.getInputStream());
					con.getInputStream().close();
				}
				os.flush();
				os.close();
			} else {
				str = "{\"result\":\"0\",\"msg\":\"请求异常！\"}";
			}
			if (str == null)
				str = "{\"result\":\"0\",\"msg\":\"请求异常！\"}";
			if (DataBase.isCS) {
				System.out.println(url);
				System.out.println(content);
				System.out.println(str);
			}
			con.disconnect();
			return str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (DataBase.isCS)
				System.out.println("-------连接超时2-----");
			e.printStackTrace();
			return "fwq";
		}
	}

	public static String postJsonContent(String apiUrl, Map<String, String[]> params) {
		// post方法
		try {
			String str = null;
			URL url = new URL(removekg(apiUrl));// 根据参数创建URL对象
			if (DataBase.isCS)
				System.out.println(url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();// 得到HttpURLConnection对象
			con.setRequestMethod("POST");
			con.setUseCaches(false);
			con.setReadTimeout(100000);
			con.setDoInput(true);
			con.setDoOutput(true);// 指示应用程序要将数据写入 URL 连接。
			String content = postParamsData(params);// 解析参数（请求的内容）
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置内容
			if (content != null) {
				con.setRequestProperty("Content-Length", content.length() + "");// 设置内容长度
				OutputStream os = con.getOutputStream();
				os.write(content.getBytes("utf-8"));// 发送参数内容
				if (con.getResponseCode() == 200) {
					str = ConvertStream2Json(con.getInputStream());
					con.getInputStream().close();
				}
				os.flush();
				os.close();
			} else {
				str = "{\"result\":\"0\",\"msg\":\"请求异常！\"}";
			}
			if (str == null)
				str = "{\"result\":\"0\",\"msg\":\"请求异常！\"}";
			if (DataBase.isCS)
				System.out.println(str);
			con.disconnect();
			return str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (DataBase.isCS)
				System.out.println("-------连接超时2-----");
			e.printStackTrace();
			return "fwq";
		}
	}

	public static String postContent(String apiUrl, HashMap<String, String> params) {
		// post方法
		try {
			String str = null;
			URL url = new URL(removekg(apiUrl));// 根据参数创建URL对象
			// if(DataBase.isCS)System.out.println(url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();// 得到HttpURLConnection对象
			con.setRequestMethod("POST");
			con.setUseCaches(false);
			con.setReadTimeout(100000);
			con.setDoInput(true);
			con.setDoOutput(true);// 指示应用程序要将数据写入 URL 连接。
			String content = postParams(params);// 解析参数（请求的内容）
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置内容
			if (content != null) {
				con.setRequestProperty("Content-Length", content.length() + "");// 设置内容长度
				OutputStream os = con.getOutputStream();
				os.write(content.getBytes("utf-8"));// 发送参数内容
				if (con.getResponseCode() == 200) {
					str = ConvertStream2Json(con.getInputStream());
					con.getInputStream().close();
				}
				os.flush();
				os.close();
			} else {
				str = "{\"result\":\"0\",\"msg\":\"请求异常！\"}";
			}
			if (str == null)
				str = "{\"result\":\"0\",\"msg\":\"请求异常！\"}";
			if (DataBase.isCS)
				System.out.println(str);
			con.disconnect();
			return str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (DataBase.isCS)
				System.out.println("-------连接超时2-----");
			e.printStackTrace();
			return "fwq";
		}
	}

	public static String getContent(HashMap<String, String> params) throws IOException {
		String content = null;
		Set<Entry<String, String>> set = params.entrySet();// Map.entrySet
															// 方法返回映射的
															// collection
															// 视图，其中的元素属于此类
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					if (!(i.getValue().equals("null")))
						sb.append("TXT_" + i.getKey()).append("=").append(encode(i.getValue())).append("&");
					// 参数格式要求更改
				}
			}
		}
		if (sb.length() > 1) {
			content = sb.substring(0, sb.length() - 1);
		}
		if (DataBase.isCS)
			System.out.println(content);
		return content;
	}

	public static String getParamsData(Map<String, String[]> params) throws IOException {
		String content = null;
		Set<Entry<String, String[]>> set = params.entrySet();// Map.entrySet
																// 方法返回映射的
																// collection
																// 视图，其中的元素属于此类
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					String[] val = i.getValue();
					String s = val.length == 0 ? "" : encode(val[0]);
					if (!s.equals(""))
						sb.append(i.getKey()).append("=").append(s).append("&");
					// 参数格式要求更改
				}
			}
		}
		if (sb.length() > 1) {
			content = sb.substring(0, sb.length() - 1);
		}
		if (DataBase.isCS)
			System.out.println(content);
		return content;
	}

	public static HashMap<String, String> getParamsMap(Map<String, String[]> params) throws IOException {
		HashMap<String, String> paramMap = new HashMap<>();
		Set<Entry<String, String[]>> set = params.entrySet();// Map.entrySet
																// 方法返回映射的
																// collection
																// 视图，其中的元素属于此类
		for (Entry<String, String[]> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					String[] val = i.getValue();
					String s = val.length == 0 ? "" : val[0];
					paramMap.put(i.getKey(), s);
					// 参数格式要求更改
				}
			}
		}

		// if (DataBase.isCS)
		// System.out.println(paramMap.toString());
		return paramMap;
	}

	public static String postParamsData(Map<String, String[]> params) throws UnsupportedEncodingException {
		String content = null;
		Set<Entry<String, String[]>> set = params.entrySet();// Map.entrySet
																// 方法返回映射的
																// collection
																// 视图，其中的元素属于此类
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					String[] val = i.getValue();
					String s = val.length == 0 ? "" : val[0];
					if (!s.equals("null"))
						sb.append("TXT_" + i.getKey()).append("=").append(s).append("&");
					// 参数格式要求更改
				}
			}
		}
		if (sb.length() > 1) {
			content = sb.substring(0, sb.length() - 1);
		}
		if (DataBase.isCS)
			System.out.println(content);
		return content;
	}

	public static String postParams(HashMap<String, String> params) throws IOException {
		String content = null;
		Set<Entry<String, String>> set = params.entrySet();// Map.entrySet
															// 方法返回映射的
															// collection
															// 视图，其中的元素属于此类
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					String val = encode(i.getValue());
					if (!val.equals(""))
						sb.append(i.getKey()).append("=").append(val).append("&");
					// 参数格式要求更改
				}
			}
		}
		if (sb.length() > 1) {
			content = sb.substring(0, sb.length() - 1);
		}
		// if(DataBase.isCS)System.out.println(content);
		return content;
	}

	public static String getParams(HashMap<String, String> params) throws IOException {
		String content = null;
		Set<Entry<String, String>> set = params.entrySet();// Map.entrySet
															// 方法返回映射的
															// collection
															// 视图，其中的元素属于此类
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					String val = encode(i.getValue());
					if (!val.equals(""))
						sb.append(i.getKey()).append("=").append(val).append("&");
					// 参数格式要求更改
				}
			}
		}
		if (sb.length() > 1) {
			content = sb.substring(0, sb.length() - 1);
		}
		// if(DataBase.isCS)System.out.println(content);
		return content;
	}

	private static String ConvertStream2Json(InputStream inputStream) {// 获取json数据流
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray(), "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}

	public static String getMeg(String str, String jsonStr) {// 获取json数据
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		return jsonObj.getString(str);

	}

	public static String getjson(String str, String str1, String jsonStr) {// 获取json下的json数据
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		String str2 = jsonObj.getString(str1).replace("[", "").replace("]", "");
		return getMeg(str, str2);
	}

	public static void SendSms(String mob, String sentpeople, String smsmsg) {// 发送短信
		String urlStr = HostSM + "send&mobile=" + mob + "&operant=" + "&content=" + smsmsg;
		getJsonContent(urlStr);
	}

	public static String getparameter(String accid, String usection, String usymbol) {// 获取参数
		String urlStr = HostIP + "getparameter&accid=" + accid + "&usection=" + usection + "&usymbol" + usymbol;
		String jsonstr = getJsonContent(urlStr);
		return jsonstr;
	}

	public static String getworkday() {// 获取服务器当前工作日期
		String urlStr = HostIP + "getworkday";
		String jsonstr = getJsonContent(urlStr);
		return jsonstr;
	}

	public static String writeworkday(String date) {// 写当前工作日期
		String urlStr = HostIP + "writeworkday&workday=" + date;
		String jsonstr = getJsonContent(urlStr);
		return jsonstr;
	}

	public static String getserverdatetime() {// 获取服务器日期
		String urlStr = HostIP + "serverdatetime";
		String jsonstr = getJsonContent(urlStr);
		return jsonstr;
	}

	public static String getSignatureMD5(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {// MD5加密
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(value.getBytes("utf-8"));
		byte[] m = md5.digest();
		return getString(m).toUpperCase();// 要转成大写！
	}

	public static String getSignMD5(String currentTime) {// MD5加密
		// epid=[职员代号]&signature=[签名]&timestamp=[时间:150716123001]
		// signature=md5('FlyAng@2015'+epid+timestamp+accesskey)
		// StrMD5('FlyAng@2015'+pUserid+timestamp+pAccesskey)
		String sign = JSONObject.fromObject("{}").put("timestamp", currentTime).toString().trim().replace(" ", "")
				.toUpperCase();
		String value = "Fly#Ang" + sign + "Sky@D20$16";
		System.out.println(value);
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			md5.update(value.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] m = md5.digest();
		return getString(m).toUpperCase();// 要转成大写！
	}

	public static String login(String accid, String userid, String password, String comid, String winip,
                               HttpServletRequest request, HttpServletResponse response, HttpSession session, AppData appData)
			throws IOException {// 登录界面
		try {
			String md5password = MD5Utils.getMD5(password.toUpperCase());
			appData.epno = userid.toUpperCase();
			String deviceno = MD5Utils.getMD5(accid + userid.toUpperCase() + winip);
			JSONObject dataobj = new JSONObject();
			dataobj.put("accid", accid);
			if(accid.equals("1000")&&appData.epno.equals("DEMO")) {
				md5password = MD5Utils.getMD5("2015301".toUpperCase());
				comid = "SYSDEMO";
				dataobj.put("epno", "DEMO");
			}else {
				dataobj.put("epno", userid.toUpperCase());
			}
			dataobj.put("password", md5password);
			dataobj.put("gybj", 1);
			dataobj.put("deviceno", deviceno);
			dataobj.put("devicetype", "WIN");
			dataobj.put("fieldlist", "*");
			FlyangHttp fHttp = new FlyangHttp();
			fHttp.setHostIP(DataBase.JERPIP+"api?action=");
			fHttp.setHasSign(false);
			JSONObject resobj = fHttp.FlyangdoPost("employelogin",dataobj,appData);
			String jsonstr = resobj.toString();
			if (appData.isResult(jsonstr)) {
				// session.setMaxInactiveInterval(-1);
				appData.houseid = resobj.getString("HOUSEID");
				appData.epno = userid.toUpperCase();
				appData.housename = resobj.getString("HOUSENAME");
				appData.epname = resobj.getString("EPNAME");
				appData.accdate = resobj.getString("ACCDATE");
				appData.epid = resobj.getString("EPID");
				appData.qxpublic = resobj.getString("QXPUBLIC");
				appData.qxcs = resobj.getString("QXCS");
				appData.accesskey = resobj.getString("ACCESSKEY");
				appData.levelid = resobj.getString("GLEVELID");
				appData.levelname = resobj.getString("LEVELNAME");
				appData.balcurr = resobj.getString("BALCURR");
				appData.locked = resobj.getString("LOCKED");
				appData.psw =  md5password;
				appData.comid = comid.toUpperCase();
				appData.sizenum = resobj.getString("SIZENUM");
				appData.sizenum = appData.sizenum.equals("") ? "0" : appData.sizenum;
				appData.sizenum = Integer.valueOf(appData.sizenum) <= 5 ? "5" : appData.sizenum;
				appData.sizenum = Integer.valueOf(appData.sizenum) >= 15 ? "15" : appData.sizenum;
				JSONObject logobj = JSONObject.fromObject(jsonstr);
				logobj.remove("result");
				logobj.put("ACCID", accid);
				logobj.put("COMID", comid.toUpperCase());
				logobj.put("EPNO", userid.toUpperCase());
				logobj.put("SIZENUM", appData.sizenum);
				logobj.put("DEVICENO", deviceno);
				logobj.put("PSW", md5password);
				Cookie ck1 = new Cookie("SKYSTR", getBase64String(logobj.toString()));
				ck1.setMaxAge(30 * 24 * 60 * 60);
				ck1.setPath("/");
				response.addCookie(ck1);
				return "t";
			} else {
				return jsonstr;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}

	public static String idconfirm(String Comid, HttpServletRequest request, HttpServletResponse response,
                                   AppData appData) throws IOException {
		JSONObject dataobj = new JSONObject();
		dataobj.put("accname", Comid);
		dataobj.put("fieldlist", "*");
		FlyangHttp fHttp = new FlyangHttp();
		fHttp.setHostIP(DataBase.JERPIP+"api?action=");
		fHttp.setHasSign(false);
		JSONObject resobj = fHttp.FlyangdoPost("getaccregbyname",dataobj,appData);
		if (resobj.has("total")) {
			int total = resobj.getInt("total");
			if (total > 0) {
				JSONObject logobj = new JSONObject();
				JSONObject rowObj =  resobj.getJSONArray("rows").getJSONObject(0);
				String accid = rowObj.getString("ACCID");
				String tag = rowObj.getString("TAG");
				logobj.put("ACCID", accid);
				logobj.put("ACCTAG", tag); //accreg->tag:0=零售 1=批发  2=零售+批发,3=迷你版
				logobj.put("COMID", Comid.toUpperCase());
				appData.accid = accid;
				appData.comid = Comid;
				Cookie ck1 = new Cookie("SKYSTR", getBase64String(logobj.toString()));
				ck1.setMaxAge(30 * 24 * 60 * 60);
				ck1.setPath("/");
				response.addCookie(ck1);
				return "t";
			} else {
				return "none";
			}
		} else {
			return resobj.toString();
		}
	}


	public static void resetCoolkie(HttpServletResponse response) throws IOException {
		Cookie ck2 = new Cookie("HOUSEID", "");
		Cookie ck3 = new Cookie("HOUSENAME", "");
		Cookie ck4 = new Cookie("SKYPW", "");
		Cookie ck5 = new Cookie("EPNAME", "");
		Cookie ck6 = new Cookie("EPID", "");
		Cookie ck7 = new Cookie("QXPUBLIC", "");
		Cookie ck8 = new Cookie("ACCESSKEY", "");
		Cookie ck9 = new Cookie("LEVELID", "");
		Cookie ck10 = new Cookie("BALCURR", "");
		Cookie ck11 = new Cookie("SIZENUM", "");
		Cookie ck12 = new Cookie("LEVELNAME", "");
		Cookie ck13 = new Cookie("RMBPW", "");
		ck2.setMaxAge(0);
		ck3.setMaxAge(0);
		ck4.setMaxAge(0);
		ck5.setMaxAge(0);
		ck6.setMaxAge(0);
		ck7.setMaxAge(0);
		ck8.setMaxAge(0);
		ck9.setMaxAge(0);
		ck10.setMaxAge(0);
		ck11.setMaxAge(0);
		ck12.setMaxAge(0);
		ck13.setMaxAge(0);
		response.addCookie(ck2);
		response.addCookie(ck3);
		response.addCookie(ck4);
		response.addCookie(ck5);
		response.addCookie(ck6);
		response.addCookie(ck7);
		response.addCookie(ck8);
		response.addCookie(ck9);
		response.addCookie(ck10);
		response.addCookie(ck11);
		response.addCookie(ck12);
		response.addCookie(ck13);
	}

	public static String getString(byte[] b) {// 转16进制
		// StringBuffer sb=new StringBuffer();
		// for (int i = 0; i < b.length; i++) {
		// sb.append(b[i]);
		// }
		// return sb.toString();
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			int val = ((int) b[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String signature(AppData appData) {// 一个生成签名函数
		String signature_new = null;
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String currentTime = format.format(new Date());// 获取当前时间值
		// epid=[职员代号]&signature=[签名]&timestamp=[时间:150716123001]
		// signature=md5('FlyAng@2015'+epid+timestamp+accesskey)
		// StrMD5('FlyAng@2015'+pUserid+timestamp+pAccesskey)
		String signature_past = "FlyAng@2015" + appData.epid + currentTime + appData.accesskey + appData.accid;
		// 加密密码！
		try {
			signature_new = getSignatureMD5(signature_past);// .toUpperCase();//别忘记转成大写
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String signature_key;
		try {
			signature_key = "&mlevelid=" + appData.levelid + "&maccid=" + appData.accid + "&userid=" + appData.epid
					+ "&username=" + encode(appData.epname) + "&accdate=" + appData.accdate + "&timestamp="
					+ currentTime + "&signature=" + signature_new + "&qxpublic=" + appData.qxpublic;
			return signature_key;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static String getSign(AppData appData) {
		String signature_new = null;
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String currentTime = format.format(new Date());// 获取当前时间值
		String signature_past = "FlyAng@2015" + appData.epid + currentTime + appData.accesskey + appData.accid;
		// 加密密码！
		try {
			signature_new = getSignatureMD5(signature_past);// .toUpperCase();//别忘记转成大写
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		JSONObject keyjson = JSONObject.fromObject("{}");
		keyjson.put("maccid", appData.accid);
		keyjson.put("mlevelid", appData.levelid);
		keyjson.put("userid", appData.epid);
		keyjson.put("username", appData.epname);
		keyjson.put("accdate", appData.accdate);
		keyjson.put("timestamp", currentTime);
		keyjson.put("signature", signature_new);
		keyjson.put("qxpublic", appData.qxpublic);
		if (DataBase.isCS)
			System.out.println(keyjson.toString());
		return keyjson.toString();
	}

	public static String getuserinfo(AppData appData) {// 获取用户的资料
		// http://119.84.84.173:94/interface/ebdress.aspx?action=getemployebyid&accid=[账户id]&epid=[职员id]&fieldlist=
		// [a.EPID,a.EPNO,a.EPNAME,a.ACCID,a.LEVELID,a.QXSTR,a.SEX,a.MOBILE,a.ADDRESS,a.POSTCODE,a.TEL,a.IDNO,a.WORKDATE,a.DUTY,a.REMARK,a.NOUSED,a.PASSKEY,a.PASSWORD,a.STATETAG,a.LASTOP,a.LASTDATE,A.HOUSEID,B.HOUSENAME]
		String url = HostIP + "getemployebyid&accid=" + appData.accid + "&epid=" + appData.epid
				+ "&fieldlist=a.EPNO,a.EPNAME,a.SEX,a.MOBILE,a.ADDRESS,a.POSTCODE,a.TEL,a.IDNO,a.PASSWORD"
				+ signature(appData);
		String jsonstr = getJsonContent(url);
		if (appData.isTotal(jsonstr)) {
			return jsonstr;
		} else {
			return jsonstr;
		}
	}

	public static String updateuerinfo(AppData appData, HashMap<String, String> params) throws Exception {
		// http://119.84.84.173:94/interface/ebdress.aspx?action=updateemployebyid&accid=[账户id]&epid=[职员id]&lastop=[用户名]
		// 更新用户资料
		String url = HostIP + "updateemployebyid&accid=" + appData.accid + "&epid=" + appData.epid + "&lastop="
				+ encode(appData.epname) + signature(appData);
		String jsonstr = postJsonContent(url, params);
		if (appData.isResult(jsonstr)) {
			appData = null;
			return "t";
		}
		return jsonstr;
	}
	public static JSONObject updateuserpsw(String oldpsw, String newpsw, AppData appData) throws Exception {
		// 更改用户密码
		FlyangHttp fHttp = new FlyangHttp();
		fHttp.setHostIP(DataBase.JERPIP+"api?action=");
		JSONObject dataobj = new JSONObject();
		JSONObject resobj = new JSONObject();
		dataobj.put("epid", appData.epid);
		String oldpsd = appData.psw;
		String md5oldpsw = MD5Utils.getMD5(oldpsw.toUpperCase());
		if (md5oldpsw.equals(oldpsd)) {
			String md5password = MD5Utils.getMD5(newpsw.toUpperCase());
			dataobj.put("password", md5password);
			resobj = fHttp.FlyangdoPost("updateemployebyid",dataobj,appData);
		} else {
			resobj.put("result", 0);
			resobj.put("msg", "原密码错误");
		}
		return resobj;
	}

	/**
	 * 返回两个JsonArray的合并后的字符串
	 * 
	 * @param mData
	 * @param array
	 * @return
	 */
	public static String joinJSONArray(JSONArray mData, JSONArray array) {
		StringBuffer buffer = new StringBuffer();
		try {
			int len = mData.size();
			for (int i = 0; i < len; i++) {
				JSONObject obj1 = (JSONObject) mData.get(i);
				if (i == len - 1)
					buffer.append(obj1.toString());
				else
					buffer.append(obj1.toString()).append(",");
			}
			len = array.size();
			if (len > 0)
				buffer.append(",");
			for (int i = 0; i < len; i++) {
				JSONObject obj1 = (JSONObject) array.get(i);
				if (i == len - 1)
					buffer.append(obj1.toString());
				else
					buffer.append(obj1.toString()).append(",");
			}
			// buffer.insert(0, "[").append("]");
			return buffer.toString();
		} catch (Exception e) {
		}
		return null;
	}

	public static String encode(String str) throws IOException {
		// TODO 编码
		return (str == null || str.equals("") || str.equals("null") || str.equals(null)) ? ""
				: URLEncoder.encode(str, "UTF-8");
	}

	public static String decode(String str) throws IOException {
		// TODO 解码码
		return (str == null || str.equals("") || str.equals("null") || str.equals(null)) ? ""
				: URLDecoder.decode(str, "UTF-8");
	}

	public static String removekg(String str) {
		// TODO 去除空格
		return str.replaceAll(" ", "").trim();
	}

	public static InputStream getimgIS(String urlString) throws IOException {
		// 构造URL
		URL url = new URL(urlString);
		try {
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			// 输入流
			InputStream is = con.getInputStream();
			return is;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static String getfilestr(String path) {
		// windows下
		if ("\\".equals(File.separator)) {
			path = path.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			path = path.replace("\\", "/");
		}
		return path;
	}

	public static String getStr(HttpServletRequest request, String realFileName) throws IOException {
		String browName = null;
		String clientInfo = request.getHeader("User-agent");
		// if(DataBase.isCS)System.out.println(clientInfo);
		if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {//
			// IE采用URLEncoder方式处理
			if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {// IE6，用GBK，此处实现由局限性
				browName = new String(realFileName.getBytes("GBK"), "ISO-8859-1");
			} else {// ie7+用URLEncoder方式
				browName = URLEncoder.encode(realFileName, "UTF-8");
			}
		} else {// 其他浏览器
			browName = new String(realFileName.getBytes("GBK"), "ISO-8859-1");
		}
		return browName;
	}

	/*
	 * public static boolean postmsg(String channel, String content) throws
	 * IOException { GoEasy goEasy = new
	 * GoEasy("c7ece70e-0088-46fc-acdd-8ee63919316d"); goEasy.publish(channel,
	 * content, new PublishListener() {
	 * 
	 * @Override public void onSuccess() { // System.out.print("消息发布成功。"); }
	 * 
	 * @Override public void onFailed(GoEasyError error) { //
	 * System.out.print("消息发布失败, 错误编码："+error.getCode()+" 错误信息： " + //
	 * error.getContent()); } }); return true; }
	 * 
	 * public static void main(String[] args) throws Exception { if
	 * (DataBase.isCS) System.out.println(MD5Utils.getMD5("123456"));
	 * postmsg("color-1400-806", "&lt;无&gt;"); }
	 */
	/**
	 * 获取double字符串
	 * 
	 * @param d
	 * @return
	 */
	public static String getNumricStr(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		String val1 = df.format(d).replace(".00", "");
		String val = String.valueOf(d);
		if (val.contains("E")) {
			BigDecimal bd = new BigDecimal(d);
			return bd.toString();
		} else {
			return val1;
		}
	}

	public static JSONObject getObjFrom(Map<String, String[]> params) {
		// TODO Auto-generated method stub
		JSONObject dataObj = JSONObject.fromObject("{}");
		Set<Entry<String, String[]>> set = params.entrySet();// Map.entrySet
																// 方法返回映射的
																// collection
																// 视图，其中的元素属于此类
		for (Entry<String, String[]> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					String[] val = i.getValue();
					String s = val.length == 0 ? "" : val[0];
					if (!s.equals("null"))
						dataObj.put(i.getKey(), s);
					// 参数格式要求更改
				}
			}
		}
		return dataObj;
	}

	public static JSONObject getObjFrom(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		JSONObject dataObj = JSONObject.fromObject("{}");
		Set<Entry<String, String>> set = map.entrySet();// Map.entrySet
														// 方法返回映射的
														// collection
														// 视图，其中的元素属于此类
		for (Entry<String, String> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					String val = i.getValue();
					if (!val.equals("null"))
						dataObj.put(i.getKey(), val);
					// 参数格式要求更改
				}
			}
		}
		return dataObj;
	}

	public static String getRequestData(String sign, JSONObject dataobj) {
		String data = dataobj.toString();
		String key = "Fly#Ang" + sign.trim().replaceAll(" ", "").toUpperCase() + "Sky@D20$16";
		String resKey = null;
		try {
			resKey = getSignatureMD5(key);// .toUpperCase();//别忘记转成大写
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (DataBase.isCS)
			System.out.println(data);
		return new AESUtils().aesEncrypt(data, resKey);
	}

	public static String postRequest(String action, JSONObject dataobj, AppData appData) throws IOException {
		// TODO
		String url = HostIP2 + action + "&requesttimebj=" + getRandomNo();
		String sign0 = getSign(appData);
		dataobj.put("flyang", "20150107");
		String data = getRequestData(sign0, dataobj);// 加密数据
		String sign = getBase64String(sign0);
		// if (DataBase.isCS)
		// System.out.println(data + "\n" + sign);
		String content = "data=" + encode(data) + "&sign=" + encode(sign);
		String jsonstr = HttpPost(url, content);
		return jsonstr;
	}

	public static String postSmSRequest(String action, JSONObject dataobj, AppData appData) throws IOException {
		// TODO
		String url = HostSM2 + action + "&requesttimebj=" + getRandomNo();
		String sign0 = getSign(appData);
		dataobj.put("flyang", "20150107");
		String data = getRequestData(sign0, dataobj);// 加密数据
		String sign = getBase64String(sign0);
		// if (DataBase.isCS)
		// System.out.println(data + "\n" + sign);
		String content = "data=" + encode(data) + "&sign=" + encode(sign);
		String jsonstr = HttpPost(url, content);
		return jsonstr;
	}

	public synchronized static String postRequest(String action, JSONObject dataobj, String exparams, AppData appData)
			throws IOException {
		// TODO
		String url = HostIP2 + action + "&requesttimebj=" + getRandomNo();
		String sign0 = getSign(appData);
		dataobj.put("flyang", "20150107");
		String data = getRequestData(sign0, dataobj);// 加密数据
		String sign = getBase64String(sign0);
		// if (DataBase.isCS)
		// System.out.println(data + "\n" + sign);
		String content = "data=" + encode(data) + "&sign=" + encode(sign) + exparams;
		String jsonstr = HttpPost(url, content);
		return jsonstr;
	}

	private static String HttpPost(String url, String content) {
		// TODO Auto-generated method stub
		try {
			String str = null;
			URL Url = new URL(removekg(url));// 根据参数创建URL对象
			if (DataBase.isCS)
				System.out.println(url);
			HttpURLConnection con = (HttpURLConnection) Url.openConnection();// 得到HttpURLConnection对象
			con.setRequestMethod("POST");
			con.setUseCaches(false);
			con.setReadTimeout(100000);
			con.setDoInput(true);
			con.setDoOutput(true);// 指示应用程序要将数据写入 URL 连接。
			// content解析参数（请求的内容）
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置内容
			if (content != null) {
				con.setRequestProperty("Content-Length", content.length() + "");// 设置内容长度
				OutputStream os = con.getOutputStream();
				os.write(content.getBytes("UTF-8"));// 发送参数内容
				if (con.getResponseCode() == 200) {
					str = ConvertStream2Json(con.getInputStream());
					con.getInputStream().close();
				}
				os.flush();
				os.close();
			} else {
				LogUtils.LogWrite(url, content);
				str = "{\"result\":\"0\",\"msg\":\"请求异常1！\"}";
			}
			if (str == null) {
				LogUtils.LogWrite(url, content);
				str = "{\"result\":\"0\",\"msg\":\"请求异常2！\"}";
			}
			if (DataBase.isCS)
				System.out.println(str);
			con.disconnect();
			return str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtils.LogWrite(url, content);
			return "{\"result\":\"0\",\"msg\":\"请求异常3！\"}";
		}
	}

	public static String getBase64String(String value) {
		byte[] text = null;
		try {
			text = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(text).trim().replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t", "");
	}

	public static String getStringfromBase64(String base64str) {
		byte[] text = null;
		try {
			byte[] bytes = base64str.getBytes("UTF-8");
			text = Base64.getDecoder().decode(bytes);
			return new String(text,"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 时间戳+7位随机数
	 * 
	 * @return 13位随机数
	 */
	public static String getRandomNo() {
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		String currentTime = format.format(new Date());// 获取当前时间值
		int randomno = (int) (Math.random() * 10000000);
		String randno = currentTime + String.valueOf(randomno) + "00000000";
		return randno.substring(0, 13);
	}
}
