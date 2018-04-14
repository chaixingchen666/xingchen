package com.flyang.common;

import com.alibaba.fastjson.JSONObject;

public class MerchantConfig {
	private String merchantNo="";
	private String merchantKey = "";
	
	public MerchantConfig() {
		
	}
	public MerchantConfig(JSONObject configObj) {
		this.merchantNo = configObj.getString("merchantNo");
		this.merchantKey = configObj.getString("merchantKey");
	}
	
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getMerchantKey() {
		return merchantKey;
	}
	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}
}
