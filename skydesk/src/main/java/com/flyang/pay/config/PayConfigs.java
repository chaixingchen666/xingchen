package com.flyang.pay.config;


import net.sf.json.JSONObject;
import tools.AppData;

public class PayConfigs extends Configs {
	private AppData appData;
	private JSONObject payKeyObj;

	public AppData getappData() {
		return appData;
	}

	public void setappData(AppData appData) {
		this.appData = appData;
	}

	public JSONObject getPayKeyObj() {
		return payKeyObj;
	}

	public void setPayKeyObj(JSONObject payKeyObj) {
		this.payKeyObj = payKeyObj;
	}

	@Override
	public void init() {

		/*
		 * JSONObject dataobj = JSONObject.fromObject("{}").accumulate("accid",
		 * appData.accid); String action = "loadpayparam"; try { String res =
		 * DataBase.postRequest(action,dataobj,appData); JSONObject resObj =
		 * JSONObject.fromObject(res); int result = resObj.getInt("result");
		 * if(result==1){ JSONObject row =
		 * resObj.getJSONArray("pay_zfb").getJSONObject(0); if(row.size()>0){
		 * String pid = row.getString("pid"); String appid =
		 * row.getString("appid"); String privateKey =
		 * row.getString("privateKey"); String publicKey =
		 * row.getString("publicKey"); super.setPid(pid); super.setAppid(appid);
		 * super.setPrivateKey(privateKey); super.setPublicKey(publicKey);
		 * return; } }
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		if (payKeyObj.size() > 0) {
			String pid = payKeyObj.getString("pid");
			String appid = payKeyObj.getString("appid");
			String privateKey = payKeyObj.getString("privateKey");
			String publicKey = payKeyObj.getString("publicKey");
			super.setPid(pid);
			super.setAppid(appid);
			super.setPrivateKey(privateKey);
			super.setPublicKey(publicKey);
			return;
		}
	}
}
