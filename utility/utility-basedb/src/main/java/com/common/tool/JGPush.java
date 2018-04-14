/**
 * 
 */
package com.common.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.awt.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import com.google.gson.JsonObject;

//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
//import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * @author Administrator
 *
 */
public class JGPush {
	private static String errmess;
	private static String vipapp_key = "";
	private static String vipmaster_secret = "";// ConfigurationManager.AppSettings["vipAPIMasterSecret"].ToString();//Android密码

	private static String erpapp_key = "";// ConfigurationManager.AppSettings["erpApiKey"].ToString();//Android ApiKey
	private static String erpmaster_secret = "";// ConfigurationManager.AppSettings["erpAPIMasterSecret"].ToString();//Android密码

	// <add key="vipApiKey" value="d5e1e730109da56b0d54c0e3" />
	// <add key="vipAPIMasterSecret" value="41f9f1f0b25cc187c0c1652d" />
	//
	// <add key="erpApiKey" value="015eb190ba1f52e0f09d3f8d" />
	// <add key="erpAPIMasterSecret" value="4d14d8101283cc3bd030f770" />
	static {
		File file = new File(Func.getRootPath() + "jpush.properties");// 极光推送的配置文件
		FileInputStream is = null;
		Properties prop = new Properties();
		try {
			is = new FileInputStream(file);
			prop.load(is);
			// <add key="vipApiKey" value="d5e1e730109da56b0d54c0e3" />
			// <add key="vipAPIMasterSecret" value="41f9f1f0b25cc187c0c1652d" />
			//
			// <add key="erpApiKey" value="015eb190ba1f52e0f09d3f8d" />
			// <add key="erpAPIMasterSecret" value="4d14d8101283cc3bd030f770" />

			vipapp_key = prop.getProperty("vipApiKey");
			vipmaster_secret = prop.getProperty("vipAPIMasterSecret");
			erpapp_key = prop.getProperty("erpApiKey");
			erpmaster_secret = prop.getProperty("erpAPIMasterSecret");
			//			System.out.println("jpush.properties read");
			//			System.out.println(vipapp_key);
			//			System.out.println(vipmaster_secret);
			//			System.out.println(erpapp_key);
			//			System.out.println(erpmaster_secret);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errmess = "Err1033:" + e.getMessage();
//			LogUtils.LogErrWrite("JGPush", errmess);
			LogUtils.LogErrWrite("JGPush", errmess);
		}
		// public string vipapp_key = ConfigurationManager.AppSettings["vipApiKey"].ToString();//Android ApiKey
		// public string vipmaster_secret = ConfigurationManager.AppSettings["vipAPIMasterSecret"].ToString();//Android密码
		//
		// public string erpapp_key = ConfigurationManager.AppSettings["erpApiKey"].ToString();//Android ApiKey
		// public string erpmaster_secret = ConfigurationManager.AppSettings["erpAPIMasterSecret"].ToString();//Android密码

	}

	/*
	 * 发送通知
	 * 
	 * @param registrationId 设备标识
	 * 
	 * @param alert 推送内容
	 */
	// public static void jSend_notification(String registrationId, String alert) {
	// LogUtils.LogDebugWrite("jSend_notification", "1 " + erpmaster_secret + " " + erpapp_key);
	//
	// JPushClient jpushClient = new JPushClient(erpmaster_secret, erpapp_key, 3);
	//
	// LogUtils.LogDebugWrite("jSend_notification", "2");
	// PushPayload payload = send_N(registrationId, alert);
	// LogUtils.LogDebugWrite("jSend_notification", "3");
	// try {
	// PushResult result = jpushClient.sendPush(payload);
	// LogUtils.LogDebugWrite("jSend_notification", "4");
	//System.out.println(result);
	//
	// } catch (APIConnectionException e) {
	//System.out.println(e);
	// LogUtils.LogDebugWrite("jSend_notification 5", e.getMessage());
	// } catch (APIRequestException e) {
	//System.out.println(e);
	// LogUtils.LogDebugWrite("jSend_notification 6", e.getMessage());
	//
	//System.out.println("Error response from JPush server. Should review and fix it. " + e);
	//System.out.println("HTTP Status: " + e.getStatus());
	//System.out.println("Error Code: " + e.getErrorCode());
	//System.out.println("Error Message: " + e.getErrorMessage());
	//System.out.println("Msg ID: " + e.getMsgId());
	// }
	// }

	//	public static PushPayload send_N(String registrationId, String alert) {
	//
	//		return PushPayload.newBuilder().setPlatform(Platform.android_ios())// 必填 推送平台设置Platform.android_ios())
	//				// .setAudience(Audience.registrationId(registrationId))
	//
	//				.setAudience(Audience.newBuilder()//
	//						.addAudienceTarget(AudienceTarget.tag("erptag1489"))//
	//						.addAudienceTarget(AudienceTarget.alias(registrationId)).build())//
	//
	//				// .setAudience(Audience.newBuilder()
	//				// .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
	//				// .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
	//				// .build())
	//
	//				// .setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.tag("erptag1489")).addAudienceTarget(AudienceTarget.alias(registrationId)).build())
	//				// * 如果目标平台为 iOS 平台 需要在 options 中通过 apns_production 字段来制定推送环境。
	//				// * True 表示推送生产环境，False 表示要推送开发环境； 如 果不指定则为推送生产环境
	//				.setNotification(Notification.alert(alert)) //
	//
	//				.setOptions(Options.newBuilder().setApnsProduction(false).build()).build();
	//
	//	}

	public static PushPayload buildPushObject(ArrayList<String> s_alias, String strtag, String alert) {
		// return PushPayload.newBuilder().setPlatform(Platform.android_ios())//
		// .setAudience(Audience.alias("alias1")).setNotification(Notification.alert(alert)).build();

		return PushPayload.newBuilder().setPlatform(Platform.android_ios())//
				.setAudience(Audience.newBuilder()//
						.addAudienceTarget(AudienceTarget.tag(strtag))//
						.addAudienceTarget(AudienceTarget.alias(s_alias))//
						.build())//

				.setNotification(Notification.alert(alert)) // 消息内容

				// * 如果目标平台为 iOS 平台 需要在 options 中通过 apns_production 字段来制定推送环境。
				// * True 表示推送生产环境，False 表示要推送开发环境； 如 果不指定则为推送生产环境

				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();

	}

	public static PushPayload buildPushObject(ArrayList<String> s_alias, String strtag, String alert, long onhouseid, Float messid, int lx) {
		// JsonObject jsonExtra = new JsonObject();
		// jsonExtra.addProperty("onhouseid", onhouseid);
		// jsonExtra.addProperty("messid", messid);
		// jsonExtra.addProperty("lx", lx);

		Map<String, String> extras = new HashMap<String, String>();
		extras.put("onhouseid", "" + onhouseid);
		extras.put("messid", "" + messid);
		extras.put("lx", "" + lx);

		return PushPayload.newBuilder().setPlatform(Platform.android_ios())//
				.setAudience(Audience.newBuilder()//
						.addAudienceTarget(AudienceTarget.tag(strtag))//
						.addAudienceTarget(AudienceTarget.alias(s_alias)).build())//

				// .setNotification(Notification.alert(alert)) //
				.setNotification(Notification.newBuilder()//
						.setAlert(alert)//
						.addPlatformNotification(AndroidNotification.newBuilder()//
								// .setTitle("Android Title")//
								.addExtras(extras)

								// .addExtra("booleanExtra", false)//
								// .addExtra("numberExtra", 1)//
								// .addExtra("jsonExtra", jsonExtra)//
								.build())
						.addPlatformNotification(IosNotification.newBuilder()//
								.addExtras(extras)//
								.addExtras(extras).incrBadge(1)//
								// .addExtra("extra_key", "extra_value")//
								.build())//
						.build())//

				// * 如果目标平台为 iOS 平台 需要在 options 中通过 apns_production 字段来制定推送环境。
				// * True 表示推送生产环境，False 表示要推送开发环境； 如 果不指定则为推送生产环境

				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();

	}

	// public static PushPayload buildPushObject(String s_alias, String alert) {
	// // return PushPayload.newBuilder().setPlatform(Platform.android_ios())//
	// // .setAudience(Audience.alias("alias1")).setNotification(Notification.alert(alert)).build();
	//
	// return PushPayload.newBuilder().setPlatform(Platform.android_ios())//
	// .setAudience(Audience.alias(s_alias)).setNotification(Notification.alert(alert)).build();
	//
	// }

	// public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
	// return PushPayload.newBuilder().setPlatform(Platform.android_ios())
	// .setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.tag("tag1", "tag2")).addAudienceTarget(AudienceTarget.alias("alias1", "alias2")).build())
	// .setMessage(Message.newBuilder().setMsgContent(MSG_CONTENT).addExtra("from", "JPush").build()).build();
	// }
	// 向蓝窗ERP推送消息
	// string aliaslist = "erp10453^erp10551^erp990^";
	// string strtag = "erptag1489";

	@SuppressWarnings("deprecation")
	public static int PushToErp(String aliaslist, String strtag, String strmess) {
		//System.out.println("PushToErp begin 0");

		if (aliaslist.equals("") || strmess.equals("") || strtag.equals("")) {
			return 0;
		}
		// LogUtils.LogDebugWrite("PushToErp new 1", aliaslist);
		// List s_alias = new List();
		ArrayList<String> s_alias = new ArrayList<String>();
		//System.out.println("PushToErp begin 1");
		int p = 0;
		String ss = "";
		// int count = 0;
		// aliaslist=vip201^vip181^vip81^vip161^
		while (aliaslist.length() > 0) {
			p = aliaslist.indexOf("^");
			ss = aliaslist.substring(0, p);
			//System.out.println(ss);
			// LogUtils.LogDebugWrite("PushToErp new 2", ss);
			s_alias.add(ss);

			aliaslist = aliaslist.substring(p + 1);
			// WriteLogTXT("debug", "PushToVip" + count, ss);
			// count++;
		}
		// Audience.alias(s_alias)
		strmess += Func.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");

		// LogUtils.LogDebugWrite("PushToErp new 3.1", strmess);
		PushPayload payload = buildPushObject(s_alias, strtag, strmess);

		JPushClient jpushClient = new JPushClient(erpmaster_secret, erpapp_key, 3);
		// LogUtils.LogDebugWrite("PushToErp new 4.2", " ok ");
		//
		try {
			// PushResult result =
			jpushClient.sendPush(payload);
			// LogUtils.LogDebugWrite("PushToErp ok", "11111");
			//System.out.println(result);
			return 1;
		} catch (APIConnectionException e) {
			//System.out.println(e);
//			LogUtils.LogErrWrite("JGPush.PushToErp", "Err1035" + e.getMessage());
			LogUtils.LogErrWrite("JGPush.PushToErp",  "Err1035" + e.getMessage());
		} catch (APIRequestException e) {
//			LogUtils.LogErrWrite("JGPush.PushToErp", "Err1034" + e.getErrorMessage());
			LogUtils.LogErrWrite("JGPush.PushToErp", "Err1034" + e.getErrorMessage());

			//System.out.println(e);
			//System.out.println("Error response from JPush server. Should review and fix it. " + e);
			//System.out.println("HTTP Status: " + e.getStatus());
			//System.out.println("Error Code: " + e.getErrorCode());
			//System.out.println("Error Message: " + e.getErrorMessage());
			//System.out.println("Msg ID: " + e.getMsgId());
		}
		return 0;

	}

	// 向蓝窗VIP推送消息
	@SuppressWarnings("deprecation")
	public static int PushToVip(String aliaslist, String viptag, String strmess, long onhouseid, float messid, int lx) {
		//System.out.println("PushToErp begin 0");

		if (aliaslist.equals("") || strmess.equals("") || viptag.equals("")) {
			return 0;
		}
		// LogUtils.LogDebugWrite("PushToErp new 1", aliaslist);
		// List s_alias = new List();
		ArrayList<String> s_alias = new ArrayList<String>();
		//System.out.println("PushToErp begin 1");
		int p = 0;
		String ss = "";
		// int count = 0;
		// aliaslist=vip201^vip181^vip81^vip161^
		while (aliaslist.length() > 0) {
			p = aliaslist.indexOf("^");
			ss = aliaslist.substring(0, p);
			s_alias.add(ss);
			aliaslist = aliaslist.substring(p + 1);
		}
		// Audience.alias(s_alias)
		strmess += Func.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
		PushPayload payload;
		if (messid > 0)
			payload = buildPushObject(s_alias, viptag, strmess, onhouseid, messid, lx);
		else
			payload = buildPushObject(s_alias, viptag, strmess);

		JPushClient jpushClient = new JPushClient(vipmaster_secret, vipapp_key, 3);
		//
		try {
			//			PushResult result = jpushClient.sendPush(payload);

			jpushClient.sendPush(payload);
			// LogUtils.LogDebugWrite("PushToErp ok", "22222");
			//System.out.println(result);
			return 1;
		} catch (APIConnectionException e) {
			//System.out.println(e);
//			LogUtils.LogErrWrite("JGPush.PushToVip", "Err1036" + e.getMessage());
			LogUtils.LogErrWrite("JGPush.PushToVip", "Err1036" + e.getMessage());
		} catch (APIRequestException e) {
//			LogUtils.LogErrWrite("JGPush.PushToVip", "Err1037" + e.getErrorMessage());
			LogUtils.LogErrWrite("JGPush.PushToVip", "Err1037" + e.getErrorMessage());

			//System.out.println(e);
			//System.out.println("Error response from JPush server. Should review and fix it. " + e);
			//System.out.println("HTTP Status: " + e.getStatus());
			//System.out.println("Error Code: " + e.getErrorCode());
			//System.out.println("Error Message: " + e.getErrorMessage());
			//System.out.println("Msg ID: " + e.getMsgId());
		}
		return 0;

	}

}
