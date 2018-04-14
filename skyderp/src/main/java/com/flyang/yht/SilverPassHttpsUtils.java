package com.flyang.yht;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.common.tool.LogUtils;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class SilverPassHttpsUtils {

	public static JSONObject httpPost(String resAction, HashMap<String, String> map) {
		String url = Constains.HOST_SliverUrl + resAction;
		JSONObject dataobj = ToolUtils.getObjFrom(map);// 获取body参数

		System.out.println("url=" + url);

		
		System.out.println("bodyRequest=" + dataobj.toString());

		String signature_new = null;
		Long currentTime = ToolUtils.getUnixTime(); // Unix时间戳
		String timeStamp = String.valueOf(currentTime);
		String signData = ToolUtils.getSignData(map);
		System.out.println("signData=" + signData);
		// head里sign加密前的参数（密钥+参数集合+时间戳）
		StringBuffer sb = new StringBuffer();
		sb.append(Constains.KEY_SALT_KEY + "=" + Constains.APP_SECRET);
		sb.append("&");
		sb.append(signData);
		sb.append("&");
		sb.append(Constains.KEY_TIMESTAMP + "=" + timeStamp);
//		System.out.println("加密前的参数" + sb.toString());

		JSONObject result = new JSONObject();

		try {
			signature_new = getSingatureMD5(sb.toString());// MD5加密
//			System.out.println("sign=" + signature_new);
			final JSONObject headjsonObject = new JSONObject();// head参数
			headjsonObject.put("appKey", Constains.SliverappKey);
			headjsonObject.put("deviceId", Constains.HOSTID);
			headjsonObject.put("timestamp", currentTime);
			headjsonObject.put("contentType", "json");
			headjsonObject.put("sign", signature_new);
			System.out.println("head=" + headjsonObject.toString());

			// 在请求的时候，最上层封装一个message
			JSONObject messageJsonObject = new JSONObject();// 顶层JsonObject
			JSONObject mapJsonObject = new JSONObject();
			mapJsonObject.put("head", headjsonObject);
			mapJsonObject.put("body", dataobj);
			messageJsonObject.put("message", mapJsonObject);
			System.out.println("message=" + messageJsonObject);
			// 请求参数Json结束

			// 创建默认的httpClient实例
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 创建httppost
			HttpPost httppost = new HttpPost(url);
			StringEntity entity = new StringEntity(messageJsonObject.toString().trim(), "utf-8");// 解决中文乱码问题

			entity.setContentEncoding("UTF-8");
			entity.setContentType("stream/json");
			httppost.setEntity(entity);

			CloseableHttpResponse response = httpClient.execute(httppost);
			if (response != null) {

				System.out.println("response不为空");

			} else {

				System.out.println("response为空");

			}
			HttpEntity entityRespose = response.getEntity();
			if (entityRespose != null) {

				System.out.println("entityRespose不为空");

			} else {

				System.out.println("entityRespose为空");

			}
			if (entity != null) {

				System.out.println("..............发送请求成功...............");
				String resultStr = EntityUtils.toString(entityRespose, "UTF-8");
				System.out.println("返回:" + resultStr);
				result = JSONObject.fromObject(resultStr);
				System.out.println("httpPost over!!!");

			}

		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (JSONException e2) {
			e2.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("..............发送请求失败...............");
			LogUtils.LogWrite("ErorRequestData2", dataobj.toString());
		}

		return result;
	}

	// MD5加密
	public static String getSingatureMD5(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(value.getBytes("utf-8"));
		byte[] m = md5.digest();
		return getString(m).toUpperCase();// 要转成大写！
	}

	public static String getString(byte[] b) {
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

}
