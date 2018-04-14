package com.flyang.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flyang.common.FlyangConfig;
import com.flyang.entity.MicroPayReqData;
import com.flyang.utils.HttpUtil;
import com.flyang.utils.MD5Util;

public class MicroPay {
	private static Logger log = LoggerFactory.getLogger(MicroPay.class);

	public static void main(String[] args) throws Exception {
		String payMethod = "WXPAY";
		String orderNo = "20161104022";
		String amount = "0.01";
		String subject = "测试商户";
		String subAppid = "";
		String authCode = "130449875315715166";
		String expireTime = "10";
		String limitPay = "";
		String goodsTag = null;
		MicroPayReqData req = new MicroPayReqData("201704123000034", "8c6cf4e5ca3b4bacb44a34166c435289", payMethod,
				orderNo, amount, subject, subAppid, authCode, expireTime, limitPay, goodsTag);
		String url = FlyangConfig.API_BASE_URL + "/gpay/microPay";
		JSONObject jsonObject = (JSONObject) JSON.toJSON(req);
		log.info(jsonObject.toString());
		HttpUtil httpUtil = new HttpUtil();
		String params = jsonObject.toString();
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
			log.info("验证返回参数的签名" + sign);
			boolean result = MD5Util.verifySign(resultObj, "8c6cf4e5ca3b4bacb44a34166c435289", sign);
			if (result != true) {
				throw new RuntimeException("返回参数的签名验证失败");
			}
		}
	}
}
