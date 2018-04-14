package com.flyang.common;

import com.alibaba.fastjson.JSONObject;

public class FlyangConfig {
	
	private static JSONObject serverObj = ServerConfig.getIPJson();
	
	public static JSONObject getServerObj() {
		return serverObj;
	}
	public static final String API_JERP_URL = serverObj.getString("JerpIP");
	
	//商户进件mgmt 支付gpay
	public static final String API_BASE_URL = serverObj.getString("apiUrl");;
	//代理账号 2017041200002   
	//密钥166d6c2dff164b479fa2df30533230ad   
	//微信渠道号source  24782090  (注意生产环境请使用自己渠道号)
//	public static final String AGENT_NO = "2017083100001";
//	public static final String AGENT_SECRET = "6819040d318043beada25ce83f1d0b63";
//	public static final String WX_SOURCE = "47264883";
//	
//	public static final String ONLINERATE = "0.00380";
//	public static final String D0SETTLERATE = "0.00400";
	
	public static final String AGENT_NO = serverObj.getString("agentNo");
	public static final String AGENT_SECRET = serverObj.getString("agentSecret");
	public static final String WX_SOURCE = serverObj.getString("wxSource");
	
	public static final String ONLINERATE = serverObj.getString("onlineRate");
	public static final String D0SETTLERATE = serverObj.getString("d0SettleRate");
	
	
	
}
