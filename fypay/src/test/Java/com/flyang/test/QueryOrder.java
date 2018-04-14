package com.flyang.test;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flyang.common.FlyangConfig;
import com.flyang.entity.QueryOrderReqData;
import com.flyang.utils.HttpUtil;
import com.flyang.utils.MD5Util;

public class QueryOrder {
	private static Logger log = LoggerFactory.getLogger(QueryOrder.class);
	public static void main(String[] args) throws IOException {
		JSONObject resobj = new JSONObject();
		String errorMsg = "";
		String md5Key = "4fbb4c971e3b402584cd51d51871460a";
		String merchantNo = "201708313000942";
		String orderNo = "XX20170831000011809533737896";
		QueryOrderReqData queryOrderReqData = new QueryOrderReqData(merchantNo, md5Key, orderNo);
		queryOrderReqData.setSignType("MD5");
		queryOrderReqData.setRandomStr(UUID.randomUUID().toString().replace("-", ""));
		JSONObject jsonObject = (JSONObject) JSON.toJSON(queryOrderReqData);
		String sign = MD5Util.getSign(jsonObject, md5Key);
		jsonObject.put("sign", sign);
		String requestParam = jsonObject.toString();

		log.info("发送查询交易的POST请求,请求参数" + requestParam);
		HttpUtil httpUtil = new HttpUtil();
		String url = FlyangConfig.API_BASE_URL + "/mgmt/orderQuery";
		JSONObject resultObj = httpUtil.doPostRequest(url, requestParam);

		if (resultObj == null) {
			throw new RuntimeException("返回参数为空");
		}
		log.info("交易查询返回结果" + resultObj);
		if ("000000".equals(resultObj.get("respCode"))) {
			String respSign = resultObj.getString("sign");
			if (respSign == null || "".equals(respSign)) {
				throw new RuntimeException("签名为空");
			}
			log.info("验证返回参数的签名");
			boolean result = MD5Util.verifySign(resultObj, md5Key, respSign);
			if (result != true) {
				throw new RuntimeException("返回参数的签名验证失败");
			}
		} else {
			errorMsg += resultObj.getString("respMsg");
		}
		if (errorMsg.length() > 0) {
			resobj.put("result", 0);
			resobj.put("msg", errorMsg);
		}
		System.out.println(resobj);
	}
}
