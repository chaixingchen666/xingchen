package com.flyang.fypay;

import java.io.IOException;

import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;

public class MerchantConfig {

	private String merchantNo; // 商户号
	private String merchantKey; // 商户秘钥
	private String statetag = "-1"; // 状态 0未开通产品 1代码开通产品-1获取失败重新获取

	public MerchantConfig() {

	}

	public MerchantConfig(AppData appData) throws IOException {
		FlyangHttp fh = new FlyangHttp();
		fh.setHostIP(DataBase.JERPIP + "api?action=");
		JSONObject dataObj = new JSONObject();
		dataObj.put("accid", appData.accid);
		dataObj.put("fieldlist", "a.merchantno,a.merchantkey,a.statetag");
		JSONObject resobj = fh.FlyangdoPost("getaccregpay", dataObj, appData);
		if (appData.valideResData(resobj)) {
			if(resobj.has("total")) {
				if(resobj.getInt("total")==1) {
					JSONObject data = resobj.getJSONArray("rows").getJSONObject(0);
					this.merchantNo = data.getString("MERCHANTNO");
					this.merchantKey = data.getString("MERCHANTKEY");
					this.statetag = data.getString("STATETAG");
				}
			}
		}
	}

	public String getmerchantNo() {
		return merchantNo;
	}

	public void setmerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getmerchantKey() {
		return merchantKey;
	}

	public void setmerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public String getStatetag() {
		return statetag;
	}

}
