package com.flyang.test;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flyang.common.FlyangConfig;
import com.flyang.entity.QueryMerchantReqData;
import com.flyang.utils.HttpUtil;
import com.flyang.utils.MD5Util;

public class QueryMarchant {
	private static Logger log = LoggerFactory.getLogger(QueryMarchant.class);
	public static void main(String[] args) throws IOException {
		JSONObject resobj = new JSONObject();
		JSONObject dataobj = new JSONObject();
		String merchantNo = "201704123000034";// 商户号
		dataobj.put("merchantNo", merchantNo);
		String url = FlyangConfig.API_BASE_URL + "/mgmt/queryMerchant";
		QueryMerchantReqData req = new QueryMerchantReqData(FlyangConfig.AGENT_NO, merchantNo, FlyangConfig.AGENT_SECRET);
		JSONObject jsonObject = (JSONObject) JSON.toJSON(req);
		System.out.println(jsonObject);
		String params = jsonObject.toString();
		log.info("发送POST请求,请求参数" + params);
		HttpUtil httpUtil = new HttpUtil();
		JSONObject resultObj = httpUtil.doPostRequest(url, params);
		log.info("发送POST请求,返回参数" + resultObj);
		if (resultObj == null || "".equals(resultObj.toString())) {
			throw new RuntimeException("返回参数为空");
		}
		if ("000000".equals(resultObj.get("respCode"))) {
			String sign = resultObj.getString("sign");
			if (sign == null || "".equals(sign)) {
				throw new RuntimeException("签名为空");
			}
			log.info("验证返回参数的签名"+sign);
			boolean result = MD5Util.verifySign(resultObj, FlyangConfig.AGENT_SECRET, sign);
			if (result != true) {
				throw new RuntimeException("返回参数的签名验证失败");
			}
		}
		resobj = resultObj;
		System.out.println(resobj);
	}
}
