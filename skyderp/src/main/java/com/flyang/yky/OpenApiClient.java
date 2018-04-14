package com.flyang.yky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.google.gson.JsonObject;
//import com.sz1card1.sdk.log.LogUtils;

public class OpenApiClient {
	private String openId;
	private String secret;

	public OpenApiClient(String openId, String secret) {
		// TODO Auto-generated constructor stub
		this.openId = openId;
		this.secret = secret;
	}

	//默认使用HttpURLConnection 发起Http请求
	public JsonObject CallHttpPost(String action, Map<String, Object> map) {
		return CallHttpPost(action, map, 0);
	}

	//0为HttpURLConnection 1为DefaultHttpClient 两种不同的http请求方式
	public JsonObject CallHttpPost(String action, Map<String, Object> map, int type) {

		String mapStr = SdkJsonParse.toJsonString(map);
		String data = mapStr;
		String controller = xmlParse(action);

		ResultBean checkResult;
		JsonObject result = null;

		if (controller == null) {
			checkResult = CheckParams.getInstance.initCheckResult(5, action);
			String jsonStr = SdkJsonParse.toJsonString(checkResult);
			result = SdkJsonParse.toJsonObject(jsonStr);
			return result;
		}
		String url = splitUrl(action, data, controller);
		//	String httpResult = httpClientPost(url, data);
		String httpResult = null;
		//		LogUtils.logFlag = false;
		if (type == 0) {
			httpResult = HttpPostUrl(url, data);
		} else if (type == 1) {
			httpResult = httpClientPost(url, data);
		}
		if (httpResult == null) {
			return null;
		}
		result = SdkJsonParse.toJsonObject(httpResult);
		return result;

	}

	// 将xml解析为 Xml格式的数据
	private String xmlParse(String action) {
		SdkXmlParse parse = new SdkXmlParse();
		return parse.getXmlController(action);
	}

	//拼接出来url
	private String splitUrl(String action, String data, String controler) {

		long timeStamp = System.currentTimeMillis() / 1000;
		//long timeStamp  = 1464689711;
		String signature = Md5.md5Digest(openId + secret + timeStamp + data);
		String url = String.format("http://openapi.1card1.cn/%1$s/%2$s?openId=" + openId + "&signature=" + signature + "&timestamp=" + timeStamp, controler, action);

		//		LogUtils.printLog("请求action = " + action);

		//		LogUtils.printLog("请求Url = " + url);
		return url;
	}

	//使用httpclient的post方式发送请求 
	@SuppressWarnings("deprecation")
	private String httpClientPost(String urls, String data) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(urls);
		InputStream inputStream = null;
		BufferedReader reader = null;

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("data", data));
		HttpEntity entity;
		try {
			entity = new UrlEncodedFormEntity(postParameters, HTTP.UTF_8);
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			int httpCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			inputStream = responseEntity.getContent();
			reader = new BufferedReader(new InputStreamReader(inputStream, "Utf-8"));// 读字符串用的
			String stLine = "";
			StringBuilder jsonResult = new StringBuilder();
			while (((stLine = reader.readLine()) != null)) {
				jsonResult.append(stLine);
			}
			if (httpCode == 200) {
				return jsonResult.toString();
			} else {
				ResultBean bean = new ResultBean();
				bean.setStatus(-1);
				bean.setHttpCode(httpCode);
				return SdkJsonParse.toJsonString(bean);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private String HttpPostUrl(String urls, String data) {
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		// BufferedReader bufferedReader = null;
		StringBuffer responseResult = new StringBuffer();
		StringBuffer params = new StringBuffer();
		HttpURLConnection httpURLConnection = null;
		// 组织请求参数
		params = params.append("&data=" + data);

		try {
			URL realUrl = new URL(urls);
			// 打开和URL之间的连接
			httpURLConnection = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			httpURLConnection.setRequestProperty("accept", "*/*");
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));
			httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
			//	httpURLConnection.setRequestProperty("Content-type", "text/html");
			httpURLConnection.setRequestProperty("contentType", "utf-8");
			httpURLConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			// 发送请求参数
			String dataStr = new String(params.toString().getBytes(), "utf-8");
			printWriter.write(dataStr);
			// flush输出流的缓冲
			printWriter.flush();
			// 根据ResponseCode判断连接是否成功
			int responseCode = httpURLConnection.getResponseCode();

			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "Utf-8"));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				responseResult.append(line);
			}
			//			LogUtils.printLog("—----->>>responseCode " + responseCode);
			//			LogUtils.printLog("—----->>>responseResult " + responseResult.toString());
			if (responseCode == 200) {
				// 定义BufferedReader输入流来读取URL的ResponseData
				return responseResult.toString();
				//return responseResult.toString();
			} else {
				ResultBean bean = new ResultBean();
				bean.setStatus(-1);
				bean.setHttpCode(responseCode);
				//bean.setMessage(responseCode +  " : "+responseResult.toString());

				return SdkJsonParse.toJsonString(bean);
			}
		} catch (Exception e) {
			//			LogUtils.printLog(" exception ----------->> " + e.getMessage());
			return e.getMessage();
			//
		} finally {
			httpURLConnection.disconnect();
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
