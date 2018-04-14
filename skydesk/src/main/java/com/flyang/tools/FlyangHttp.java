package com.flyang.tools;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import tools.*;

public class FlyangHttp {
	// E001：不是正确的JSON格式
	// E002: 请求数据POST参数为null;
	// E003: 请求HTTP throw报错
	// E004: 请求HTTP 超时
	// E005: 请求数据格式不规范！
	private String HostIP = "";
	private int readtimeout = 30000;// 设置请求超时时间
	private boolean hasSign = true; // 是否需要签名
	private JSONObject sysJson = JSONObject.fromObject("{\"syserror\":\"登录信息获取失败，请重新登录！\"}");

	
	public String getHostIP() {
		return HostIP;
	}

	public void setHostIP(String hostIP) {
		HostIP = hostIP;
	}

	public int getReadtimeout() {
		return readtimeout;
	}

	public void setReadtimeout(int readtimeout) {
		this.readtimeout = readtimeout;
	}

	public boolean isHasSign() {
		return hasSign;
	}

	public void setHasSign(boolean hasSign) {
		this.hasSign = hasSign;
	}

	public JSONObject doPostRequest(String url, String content) {
		JSONObject object = new JSONObject();
		// System.out.println(url+"\n"+content);
		try {
			URL Url = new URL(removekg(url));// 根据参数创建URL对象
			HttpURLConnection con = (HttpURLConnection) Url.openConnection();// 得到HttpURLConnection对象
			con.setRequestMethod("POST");
			con.setUseCaches(false);
			con.setReadTimeout(readtimeout);
			con.setDoInput(true);
			con.setDoOutput(true);// 指示应用程序要将数据写入 URL 连接。
			// content解析参数（请求的内容）
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置内容
			if (content != null) {
				con.setRequestProperty("Content-Length", content.length() + "");// 设置内容长度
				OutputStream os = con.getOutputStream();
				os.write(content.getBytes("UTF-8"));// 发送参数内容
				if (con.getResponseCode() == 200) {
					object = ConvertStream2Json(con.getInputStream());
					con.getInputStream().close();
				} else {
					object.put("result", 0);
					object.put("msg", "网络请求数据超时，请重试！(E004," + con.getResponseCode() + ")");
					LogUtils.LogWrite(url, content);
				}
				os.flush();
				os.close();
			} else {
				LogUtils.LogWrite(url, content);
				object.put("result", 0);
				object.put("msg", "网络请求数据异常！(E002)");
			}
			con.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtils.LogWrite(url, content + "\n" + e.getMessage());
			object.put("result", 0);
			object.put("msg", "网络请求数据异常！(E003)");
		} finally {
			if (DataBase.isCS) {
				System.out.println("url:" + url);
				System.out.println("res:" + object.toString());
			}
		}
		return object;
	}

	public String removekg(String str) {
		// TODO 去除空格
		return str.replaceAll(" ", "").trim();
	}

	private JSONObject ConvertStream2Json(InputStream inputStream) {// 获取json数据流
		String jsonStr = "";
		JSONObject object = new JSONObject();
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
		if (JSONUtils.mayBeJSON(jsonStr)) {
			object = JSONObject.fromObject(jsonStr);
		} else {
			LogUtils.LogWrite("ErorData", jsonStr);
			object.put("result", 0);
			object.put("msg", "请求返回数据异常！(E001)");
		}
		return object;

	}

	public JSONObject FlyangdoPost(String action, JSONObject getobj, JSONObject postobj, AppData appData) {
		// TODO Auto-generated method stub
		String getstr = json2str(getobj, "&");
		String poststr = json2str(postobj, "&TXT_");
		if (poststr.length() > 0)
			poststr = poststr.substring(1, poststr.length());
		String url = DataBase.HostIP + action + getstr;
		if (hasSign)
			url += signature(appData);
		String content = poststr;
		return doPostRequest(url, content);
	}

	private String json2str(JSONObject obj, String split) {
		String str = "";
		@SuppressWarnings("rawtypes")
		Iterator iterator = obj.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = "";
			try {
				value = DataBase.encode(obj.getString(key));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			str += split + key + "=" + value;
		}
		return str;
	}

	private String signature(AppData appData) {// 一个生成签名函数
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
					+ "&username=" + DataBase.encode(appData.epname) + "&accdate=" + appData.accdate + "&timestamp="
					+ currentTime + "&signature=" + signature_new + "&qxpublic=" + appData.qxpublic;
			return signature_key;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	private String getSignatureMD5(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {// MD5加密
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(value.getBytes("utf-8"));
		byte[] m = md5.digest();
		return getString(m).toUpperCase();// 要转成大写！
	}

	private String getString(byte[] b) {// 转16进制
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

	// 新的请求方式
	/**
	 * 新的请求
	 * 
	 * @param action
	 * @param dataobj
	 * @param appData
	 * @return
	 * @throws IOException
	 */
	public JSONObject FlyangdoPost(String action, JSONObject dataobj, AppData appData) throws IOException {
		// TODO
		if (!valideReqData(dataobj)) {
			JSONObject errorResobj = new JSONObject();
			errorResobj.put("result", 0);
			errorResobj.put("msg", "请求数据不允许有单引号、双引号存在，请检查！");
			return errorResobj;
		}
		String url = HostIP + action + "&requesttimebj=" + DataBase.getRandomNo();
		JSONObject keyjson = getSign(appData);
		if (hasSign) {
			if (keyjson.has("maccid")) {
				if (keyjson.getString("maccid") == null) {
					return sysJson;
				}
			} else
				return sysJson;
			if (keyjson.has("userid")) {
				if (keyjson.getString("userid") == null) {
					return sysJson;
				}
			} else
				return sysJson;
		}
		String sign0 = keyjson.toString();
		dataobj.put("flyang", "20150107");
		String data = getRequestData(sign0, dataobj);// 加密数据
		String sign = getBase64String(sign0);
		// if (DataBase.isCS)
		// System.out.println(data + "\n" + sign);
		String content = "data=" + encode(data) + "&sign=" + encode(sign);
		return doPostRequest(url, content);
	}

	public JSONObject FlyangdoPost(String action, JSONObject dataobj, String exparams, AppData appData)
			throws IOException {
		// TODO
		if (!valideReqData(dataobj)) {
			JSONObject errorResobj = new JSONObject();
			errorResobj.put("result", 0);
			errorResobj.put("msg", "请求数据不允许有单引号、双引号存在，请检查！");
			return errorResobj;
		}
		String url = HostIP + action + "&requesttimebj=" + DataBase.getRandomNo();
		JSONObject keyjson = getSign(appData);
		if (hasSign) {
			if (keyjson.has("maccid")) {
				if (keyjson.getString("maccid") == null) {
					return sysJson;
				}
			}
			if (keyjson.has("userid")) {
				if (keyjson.getString("userid") == null) {
					return sysJson;
				}
			}
		}
		String sign0 = keyjson.toString();
		dataobj.put("flyang", "20150107");
		String data = getRequestData(sign0, dataobj);// 加密数据
		String sign = getBase64String(sign0);
		// if (DataBase.isCS)
		// System.out.println(data + "\n" + sign);
		String content = "data=" + encode(data) + "&sign=" + encode(sign) + exparams;
		return doPostRequest(url, content);
	}


	/**
	 * 2018年3月30日 为了满足接负载根据cookie分配服务器
	 */
	public JSONObject FlyangdoPost(String action, JSONObject dataobj, AppData appData, String cookie) throws IOException {
		// TODO
		if (!valideReqData(dataobj)) {
			JSONObject errorResobj = new JSONObject();
			errorResobj.put("result", 0);
			errorResobj.put("msg", "请求数据不允许有单引号、双引号存在，请检查！");
			return errorResobj;
		}
		String url = HostIP + action + "&requesttimebj=" + DataBase.getRandomNo();
		JSONObject keyjson = getSign(appData);
		if (hasSign) {
			if (keyjson.has("maccid")) {
				if (keyjson.getString("maccid") == null) {
					return sysJson;
				}
			} else
				return sysJson;
			if (keyjson.has("userid")) {
				if (keyjson.getString("userid") == null) {
					return sysJson;
				}
			} else
				return sysJson;
		}
		String sign0 = keyjson.toString();
		dataobj.put("flyang", "20150107");
		String data = getRequestData(sign0, dataobj);// 加密数据
		String sign = getBase64String(sign0);
		// if (DataBase.isCS)
		// System.out.println(data + "\n" + sign);
		String content = "data=" + encode(data) + "&sign=" + encode(sign);
		return doPostRequest(url, content, cookie);
	}
	public JSONObject FlyangdoPost(String action, JSONObject dataobj, String exparams, AppData appData, String cookie)
			throws IOException {
		// TODO
		if (!valideReqData(dataobj)) {
			JSONObject errorResobj = new JSONObject();
			errorResobj.put("result", 0);
			errorResobj.put("msg", "请求数据不允许有单引号、双引号存在，请检查！");
			return errorResobj;
		}
		String url = HostIP + action + "&requesttimebj=" + DataBase.getRandomNo();
		JSONObject keyjson = getSign(appData);
		if (hasSign) {
			if (keyjson.has("maccid")) {
				if (keyjson.getString("maccid") == null) {
					return sysJson;
				}
			}
			if (keyjson.has("userid")) {
				if (keyjson.getString("userid") == null) {
					return sysJson;
				}
			}
		}
		String sign0 = keyjson.toString();
		dataobj.put("flyang", "20150107");
		String data = getRequestData(sign0, dataobj);// 加密数据
		String sign = getBase64String(sign0);
		// if (DataBase.isCS)
		// System.out.println(data + "\n" + sign);
		String content = "data=" + encode(data) + "&sign=" + encode(sign) + exparams;
		return doPostRequest(url, content, cookie);
	}
	public JSONObject doPostRequest(String url, String content, String cookie) {
		JSONObject object = new JSONObject();
		// System.out.println(url+"\n"+content);
		try {
			URL Url = new URL(removekg(url));// 根据参数创建URL对象
			HttpURLConnection con = (HttpURLConnection) Url.openConnection();// 得到HttpURLConnection对象
			con.setRequestMethod("POST");
			con.setUseCaches(false);
			con.setReadTimeout(readtimeout);
			con.setDoInput(true);
			con.setDoOutput(true);// 指示应用程序要将数据写入 URL 连接。
			// content解析参数（请求的内容）
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置内容
			if (null != cookie && !"".equals(cookie)) {
				con.setRequestProperty("Cookie", cookie);
			}

			if (content != null) {
				con.setRequestProperty("Content-Length", content.length() + "");// 设置内容长度
				OutputStream os = con.getOutputStream();
				os.write(content.getBytes("UTF-8"));// 发送参数内容
				if (con.getResponseCode() == 200) {
					object = ConvertStream2Json(con.getInputStream());
					con.getInputStream().close();
				} else {
					object.put("result", 0);
					object.put("msg", "网络请求数据超时，请重试！(E004," + con.getResponseCode() + ")");
					LogUtils.LogWrite(url, content);
				}
				os.flush();
				os.close();
			} else {
				LogUtils.LogWrite(url, content);
				object.put("result", 0);
				object.put("msg", "网络请求数据异常！(E002)");
			}
			con.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtils.LogWrite(url, content + "\n" + e.getMessage());
			object.put("result", 0);
			object.put("msg", "网络请求数据异常！(E003)");
		} finally {
			if (DataBase.isCS) {
				System.out.println("url:" + url);
				System.out.println("res:" + object.toString());
			}
		}
		return object;
	}

	/**
	 * 加密
	 * 
	 * @param sign
	 * @param dataobj
	 * @return
	 */
	private String getRequestData(String sign, JSONObject dataobj) {
		String data = dataobj.toString();
		String key = "Fly#Ang" + sign.trim().replaceAll(" ", "").toUpperCase() + "Sky@D20$16";
		String Key = null;
		try {
			Key = getSignatureMD5(key);// .toUpperCase();//别忘记转成大写
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (DataBase.isCS)
			System.out.println(data);
		return new AESUtils().aesEncrypt(data, Key);
	}

	private JSONObject getSign(AppData appData) throws IOException {
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
		// keyjson.put("mlevelid", appData.levelid);
		keyjson.put("userid", appData.epid);
		// keyjson.put("accdate", appData.accdate);
		keyjson.put("author", "skydesk_wmg");// 签名人
		keyjson.put("timestamp", currentTime);
		keyjson.put("signature", signature_new);
		// keyjson.put("qxpublic", appData.qxpublic);
		if (DataBase.isCS)
			System.out.println(keyjson.toString());
		return keyjson;
	}

	private String getBase64String(String value) {
		byte[] text = null;
		try {
			text = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(text).trim().replaceAll("\r\n", "").replaceAll("\n", "")
				.replaceAll("\t", "");
	}

	private String encode(String str) throws IOException {
		// TODO 编码
		return (str == null || str.equals("") || str.equals("null") || str.equals(null)) ? ""
				: URLEncoder.encode(str, "UTF-8");
	}

	public static String getStringfromBase64(String base64str) {
		byte[] text = null;
		try {
			byte[] bytes = base64str.getBytes("UTF-8");
			text = Base64.getDecoder().decode(bytes);
			return new String(text, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		String s = getStringfromBase64(
				"eyJyZXN1bHQiOjEsIlZJUElEIjoiMTQxIiwiVklQTkFNRSI6IuWTpuWTpmZseWFuZzEiLCJBQ0NFU1NLRVkiOiI3NDg1OUMxNUJDNjdCMzlERjA4OEE3QUREMTQyNDc4OCIsIm9uaG91c2VsaXN0IjpbeyJPTkhPVVNFSUQiOiI0MjcifSx7Ik9OSE9VU0VJRCI6IjQifSx7Ik9OSE9VU0VJRCI6IjExNjEifSx7Ik9OSE9VU0VJRCI6IjMifSx7Ik9OSE9VU0VJRCI6IjQ5In0seyJPTkhPVVNFSUQiOiIyNjEifSx7Ik9OSE9VU0VJRCI6IjQ4In0seyJPTkhPVVNFSUQiOiIxNjIifSx7Ik9OSE9VU0VJRCI6IjEifSx7Ik9OSE9VU0VJRCI6IjEyMSJ9LHsiT05IT1VTRUlEIjoiNTAxIn0seyJPTkhPVVNFSUQiOiI1MDIifV0sIm1vYmlsZSI6IjEzOTAwMDAwMDAxIiwiaXNMb2dpbiI6dHJ1ZX0");
		System.out.println(s);
	}

	public boolean valideReqData(JSONObject dataobj) {
		@SuppressWarnings("rawtypes")
		Iterator it = dataobj.keys();
		if (!dataobj.has("fieldlist") && !dataobj.has("grouplist") && !dataobj.has("sumlist")
				&& !dataobj.has("sumsql")) {
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = dataobj.getString(key);
				if ((value.contains("\'") || value.contains("\"")) && !JSONUtils.mayBeJSON(value)) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 发送UDP信息
	 * @param prtserip
	 * @param msg
	 * @throws IOException
	 */
	public JSONObject sendUDP(String prtserip, String msg){
		String[] ipArr = prtserip.split(":");
		 JSONObject resobj = new JSONObject();
		if(ipArr.length!=2) {
			resobj.put("result", 0);
			resobj.put("msg", "发送失败！服务地址无效");
			return resobj;
		}
		String ip = ipArr[0];
		int port = Integer.parseInt(ipArr[1]);
		//UDP
		DatagramSocket ds = null;
		//UDP的数据包变量
		DatagramPacket dp = null;
		//UDP的数据包变量
		DatagramPacket backdp = null;
        try {
        	//定义接收空间大小
        	byte data[] = new byte[1024];
        	//实例化套接字数据存放空间
        	backdp = new DatagramPacket(data,data.length);
        	//实例化UDP的套接字,端口号为随机,UDP套接字绑定的端口
        	ds = new DatagramSocket();
        	ds.setSoTimeout(3000);
        	//需要发送的数据
        	String str = msg;
        	//指定需要发送的数据内容,数据长度,目的IP和目的端口号
        	dp = new DatagramPacket(str.getBytes(),str.length(),InetAddress.getByName(ip),port);
        	//发送数据
        	ds.send(dp);
        	ds.receive(backdp);
        	//打印获取到的消息
        	String backmsg = new String(backdp.getData(),"GBK"); //根据服务器返回编码类型修改
        	backmsg = backmsg.substring(backmsg.indexOf("{"), backmsg.indexOf("}")+1);
//        	System.out.println(backmsg);
//        	LogUtils.LogWrite("printprtback", backmsg);
        	resobj = JSONObject.fromObject(backmsg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			//关闭
			ds.disconnect();
			ds.close(); 
		}
        return resobj;
	}
}
