package com.flyang.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;


public class HttpUtil {
	private int readtimeout = 30000;//设置请求超时时间
	public int getReadtimeout() {
		return readtimeout;
	}
	public void setReadtimeout(int readtimeout) {
		this.readtimeout = readtimeout;
	}
	
	public JSONObject doPostRequest(String url, String content){
		JSONObject object = new JSONObject();
//		System.out.println(url+"\n"+content);
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
				}else{
					object.put("result", 0);
					object.put("msg", "网络请求数据超时，请重试！(E004,"+con.getResponseCode()+")");
				}
				os.flush();
				os.close();
			} else {
				object.put("result", 0);
				object.put("msg", "网络请求数据异常！(E002)");
			}
			con.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			object.put("result", 0);
			object.put("msg", "网络请求数据异常！(E003)");
		}
		return object;
	}
	public String doPostString(String url, String content){
//		System.out.println(url+"\n"+content);
		String in = null;
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
					in = ConvertStream2String(con.getInputStream());
					return in;
				}
				os.flush();
				os.close();
			}
			con.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}
	private String ConvertStream2String(InputStream inputStream) {// 获取json数据流
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
			object = JSONObject.parseObject(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			object.put("result", 0);
			object.put("msg", "请求返回数据异常！(E001)");
		}
		return object;
		
	}
}
