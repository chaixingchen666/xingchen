package com.flyang.service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flyang.common.FlyangConfig;
import com.flyang.entity.MicroPayReqData;
import com.flyang.entity.QueryMerchantReqData;
import com.flyang.entity.QueryOrderReqData;
import com.flyang.entity.RefundReqData;
import com.flyang.utils.HttpUtil;
import com.flyang.utils.MD5Util;

/**
 * Servlet implementation class FlyangPayAPI
 */
@WebServlet("/fypayapi")
public class FlyangPayAPI extends HttpServlet {
	Logger log = LoggerFactory.getLogger(FlyangPayAPI.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FlyangPayAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		String action = request.getParameter("action"); // 查询
		if (action == null) {
			PrintWriter out = response.getWriter();
			out.print("{\"result\":0,\"msg\":\"缺少请求参数！\"}");
			out.close();
		}
		if ("queryMerchant".equals(action))// 查询商户
			queryMerchant(request, response);
		else if ("createMerchantInfo".equals(action))// 商户入驻
			createMerchantInfo(request, response);
		else if ("reCreateMerchantInfo".equals(action))// 商户重新入驻
			reCreateMerchantInfo(request, response);
		else if ("openMerchantProduct".equals(action))// 选择支付
			openMerchantProduct(request, response);
		else if ("modifyMerchant".equals(action))// 修改商户
			modifyMerchant(request, response);
		else if ("modifyMerchantProduct".equals(action))// 修改商户产品
			modifyMerchantProduct(request, response);
		else if ("payConfigAdd".equals(action))
			payConfigAdd(request, response);
		else if ("microPay".equals(action))// 扫码支付
			microPay(request, response);
		else if ("toRefund".equals(action))// 退款
			toRefund(request, response);
		else if ("queryTrade".equals(action))// 查询
			queryTrade(request, response);
		else if ("DownloadReconFile".equals(action))// 对账查询
			DownloadReconFile(request, response);
		else if ("downloadMerchantReconFile".equals(action))// 对账查询
			downloadMerchantReconFile(request, response);
		else if ("getSetting".equals(action))// 对账查询
			getSetting(request, response);
		else {
			PrintWriter out = response.getWriter();
			out.print("{\"result\":0,\"msg\":\"请求参数未找到：" + action + "\"}");
			out.close();
		}
	}

	private void getSetting(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.print(FlyangConfig.getServerObj());
		out.close();
	}

	private void downloadMerchantReconFile(HttpServletRequest request, HttpServletResponse respon) throws IOException {
		// TODO Auto-generated method stub
		String reconDate = request.getParameter("reconDate");
		String merchantNo = request.getParameter("merchantNo");
		String merchantKey = request.getParameter("merchantKey");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("signType", "MD5");
		// jsonObject.put("reconDate", "20170831");
		// jsonObject.put("merchantNo", "201708313000942");
		jsonObject.put("reconDate", reconDate);
		jsonObject.put("merchantNo", merchantNo);
		jsonObject.put("randomStr", UUID.randomUUID());
		String sign = MD5Util.getSign(jsonObject, merchantKey);
		jsonObject.put("sign", sign);

		String requestParam = jsonObject.toString();
		log.info("发送下载对账文件POST请求,请求参数" + requestParam);
		HttpUtil httpUtil = new HttpUtil();
		String responseResult = null;
		try {
			String url = FlyangConfig.API_BASE_URL + "/mgmt/downloadMerchantReconFile";
			responseResult = httpUtil.doPostString(url, requestParam);
		} catch (Exception e) {
			throw new RuntimeException("调用对账接口异常");
		}
		if (responseResult == null || responseResult == "") {
			throw new RuntimeException("返回参数为空");
		}

		BufferedOutputStream bos = new BufferedOutputStream(respon.getOutputStream());
		respon.reset();// 清空输出流
		respon.setHeader("Content-disposition",
				"attachment; filename=" + URLEncoder.encode("业务对账文件" + reconDate + ".txt", "utf-8"));
		respon.setContentType("application/txt");// 定义输出类型
		bos.write(responseResult.getBytes());
		bos.flush();
		respon.flushBuffer();
	}

	private void DownloadReconFile(HttpServletRequest request, HttpServletResponse respon) throws IOException {
		// TODO 下载对账
		String reconDate = request.getParameter("reconDate");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("agentNo", FlyangConfig.AGENT_NO);
		jsonObject.put("signType", "MD5");
		jsonObject.put("reconDate", reconDate);
		jsonObject.put("randomStr", UUID.randomUUID());
		String sign = MD5Util.getSign(jsonObject, FlyangConfig.AGENT_SECRET);
		jsonObject.put("sign", sign);

		String requestParam = jsonObject.toString();
		log.info("发送下载对账文件POST请求,请求参数" + requestParam);
		HttpUtil httpUtil = new HttpUtil();
		String responseResult = null;
		try {
			String url = FlyangConfig.API_BASE_URL + "/mgmt/downloadReconFile";
			responseResult = httpUtil.doPostString(url, requestParam);
		} catch (Exception e) {
			throw new RuntimeException("调用对账接口异常");
		}
		if (responseResult == null || responseResult == "") {
			throw new RuntimeException("返回参数为空");
		}

		BufferedOutputStream bos = new BufferedOutputStream(respon.getOutputStream());
		respon.reset();// 清空输出流
		respon.setHeader("Content-disposition",
				"attachment; filename=" + URLEncoder.encode("结算文件" + reconDate + ".txt", "utf-8"));
		respon.setContentType("application/txt");// 定义输出类型
		bos.write(responseResult.getBytes());
		bos.flush();
		respon.flushBuffer();
	}

	private void queryTrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 查询
		JSONObject resobj = new JSONObject();
		String md5Key = request.getParameter("md5Key");
		String merchantNo = request.getParameter("merchantNo");
		String orderNo = request.getParameter("orderNo");
		QueryOrderReqData queryOrderReqData = new QueryOrderReqData(merchantNo, md5Key, orderNo);
		queryOrderReqData.setSignType("MD5");
		queryOrderReqData.setRandomStr(UUID.randomUUID().toString().replace("-", ""));
		JSONObject jsonObject = (JSONObject) JSON.toJSON(queryOrderReqData);
		String sign = MD5Util.getSign(jsonObject, md5Key);
		jsonObject.put("sign", sign);
		String requestParam = jsonObject.toString();

		log.info("发送查询交易的POST请求,请求参数" + requestParam);
		HttpUtil httpUtil = new HttpUtil();
		String url = FlyangConfig.API_BASE_URL + "/gpay/orderQuery";
		JSONObject resultObj = httpUtil.doPostRequest(url, requestParam);

		if (resultObj == null) {
			throw new RuntimeException("返回参数为空");
		}
		log.info("交易查询返回结果" + resultObj);
		String respSign = resultObj.getString("sign");
		if (respSign == null || "".equals(respSign)) {
			throw new RuntimeException("签名为空");
		}
		log.info("验证返回参数的签名");
		boolean result = MD5Util.verifySign(resultObj, md5Key, respSign);
		if (result != true) {
			throw new RuntimeException("返回参数的签名验证失败");
		}
		resobj = resultObj;
		PrintWriter out = response.getWriter();
		out.print(resobj);
		out.close();
		// QueryOrderResData queryOrderResData = JSONObject.toJavaObject(resultObj,
		// QueryOrderResData.class);

	}

	private void toRefund(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 申请退款
		JSONObject resobj = new JSONObject();
		String md5Key = request.getParameter("md5Key");
		String merchantNo = request.getParameter("merchantNo");
		String origOrderNo = request.getParameter("origOrderNo");
		String refundOrderNo = request.getParameter("refundOrderNo");
		String amount = request.getParameter("amount");

		RefundReqData refundReqData = new RefundReqData(merchantNo, md5Key, refundOrderNo, origOrderNo, amount);
		refundReqData.setSignType("MD5");
		refundReqData.setRandomStr(UUID.randomUUID().toString().replace("-", ""));
		JSONObject jsonObject = (JSONObject) JSON.toJSON(refundReqData);
		String sign = MD5Util.getSign(jsonObject, md5Key);
		jsonObject.put("sign", sign);
		String requestParam = jsonObject.toString();

		log.info("发起退款POST请求,请求参数" + requestParam);
		HttpUtil httpUtil = new HttpUtil();
		String url = FlyangConfig.API_BASE_URL + "/gpay/refund";
		JSONObject resultObj = httpUtil.doPostRequest(url, requestParam);

		if (resultObj == null) {
			throw new RuntimeException("返回参数为空");
		}
		log.info("退款返回结果" + resultObj);
		String respSign = resultObj.getString("sign");
		if (respSign == null || "".equals(respSign)) {
			throw new RuntimeException("签名为空");
		}
		boolean result = MD5Util.verifySign(resultObj, md5Key, respSign);
		log.info("验证返回参数的签名" + result);
		if (result != true) {
			throw new RuntimeException("返回参数的签名验证失败");
		}
		resobj = resultObj;
		PrintWriter out = response.getWriter();
		out.print(resobj);
		out.close();
		// RefundResData RefundResData = JSONObject.toJavaObject(resultObj,
		// RefundResData.class);

	}

	private void microPay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 扫码支付
		JSONObject resobj = new JSONObject();
		String md5Key = request.getParameter("md5Key");
		String payMethod = request.getParameter("payMethod");
		String merchantNo = request.getParameter("merchantNo");
		String orderNo = request.getParameter("orderNo");
		String amount = request.getParameter("amount");
		String subject = request.getParameter("subject");
		String subAppid = request.getParameter("subAppid");
		String authCode = request.getParameter("authCode");
		String expireTime = "5";
		String limitPay = request.getParameter("payByCredit");
		String goodsTag = request.getParameter("goodsTag");
		if ("TRUE".equals(limitPay)) {
			limitPay = null;
		} else {
			limitPay = "no_credit";
		}
		MicroPayReqData microPayReqData = new MicroPayReqData(merchantNo, md5Key, payMethod, orderNo, amount, subject,
				subAppid, authCode, expireTime, limitPay, goodsTag);
		microPayReqData.setSignType("MD5");
		microPayReqData.setRandomStr(UUID.randomUUID().toString().replace("-", ""));
		JSONObject jsonObject = (JSONObject) JSON.toJSON(microPayReqData);
		String sign = MD5Util.getSign(jsonObject, md5Key);
		jsonObject.put("sign", sign);
		String params = jsonObject.toString();

		String url = FlyangConfig.API_BASE_URL + "/gpay/microPay";
		log.info("发送条码支付POST请求,请求参数" + params);
		HttpUtil httpUtil = new HttpUtil();
		JSONObject resultObj = httpUtil.doPostRequest(url, params);
		if (resultObj == null || "".equals(resultObj.toString())) {
			throw new RuntimeException("返回参数为空");
		}
		log.info("条码支付返回结果" + resultObj);
		String respSign = resultObj.getString("sign");
		if (respSign == null || "".equals(respSign)) {
			throw new RuntimeException("签名为空");
		}
		log.info("验证返回参数的签名");
		boolean result = MD5Util.verifySign(resultObj, md5Key, respSign);
		if (result != true) {
			throw new RuntimeException("返回参数的签名验证失败");
		}
		resobj = resultObj;
		// MicroPayResData microPayResData = JSONObject.toJavaObject(resultObj,
		// MicroPayResData.class);
		PrintWriter out = response.getWriter();
		out.print(resobj);
		out.close();
	}

	private void payConfigAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 子商户配置
		JSONObject resobj = new JSONObject();
		String merchantNo = request.getParameter("merchantNo");
		String jsapiPath = request.getParameter("jsapiPath");
		String subAppid = request.getParameter("subAppid");
		String subscribeAppid = request.getParameter("subscribeAppid");
		// 请求报备到华夏银行的api接口
		JSONObject dataobj = new JSONObject();
		dataobj.put("agentNo", FlyangConfig.AGENT_NO);
		dataobj.put("merchantNo", merchantNo);
		dataobj.put("jsapiPath", jsapiPath);
		dataobj.put("subAppid", subAppid);
		dataobj.put("subscribeAppid", subscribeAppid);
		String randomStr = UUID.randomUUID().toString();
		dataobj.put("randomStr", randomStr);
		dataobj.put("signType", "MD5");
		String sign = MD5Util.getSign(dataobj, FlyangConfig.AGENT_SECRET);
		dataobj.put("sign", sign);

		String params = dataobj.toJSONString();
		log.info("商户配置添加请求参数：{}", params);
		HttpUtil httpUtil = new HttpUtil();
		// 请求的报备商户url
		String url = FlyangConfig.API_BASE_URL + "/mgmt/payConfigAdd";
		resobj = httpUtil.doPostRequest(url, params);
		PrintWriter out = response.getWriter();
		out.print(resobj);
		out.close();
	}

	private void modifyMerchantProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		JSONObject resobj = new JSONObject();
		String merchantNo = request.getParameter("merchantNo");
		String merchantProduct = request.getParameter("merchantProduct");
		JSONObject merchantProductJson = JSONObject.parseObject(merchantProduct);
		// 请求报备到华夏银行的api接口
		JSONObject dataobj = new JSONObject();
		dataobj.put("agentNo", FlyangConfig.AGENT_NO);
		dataobj.put("merchantNo", merchantNo);
		dataobj.put("merchantProduct", merchantProductJson);
		System.out.println(merchantProductJson);
		String randomStr = UUID.randomUUID().toString();
		dataobj.put("randomStr", randomStr);
		dataobj.put("signType", "MD5");
		String sign = MD5Util.getSign(dataobj, FlyangConfig.AGENT_SECRET);
		dataobj.put("sign", sign);

		String params = dataobj.toJSONString();
		log.info("银行api商家报备请求参数：{}", params);
		HttpUtil httpUtil = new HttpUtil();
		// 请求的报备商户url
		String url = FlyangConfig.API_BASE_URL + "/mgmt/modifyMerchantProduct";
		resobj = httpUtil.doPostRequest(url, params);
		PrintWriter out = response.getWriter();
		out.print(resobj);
		out.close();
	}

	private void modifyMerchant(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 商户修改
		JSONObject resobj = new JSONObject();
		String merchantNo = request.getParameter("merchantNo");
		String merchantInfo = request.getParameter("merchantInfo");
		JSONObject merchantInfoJson = JSONObject.parseObject(merchantInfo);
		// 请求报备到华夏银行的api接口
		JSONObject dataobj = new JSONObject();
		dataobj.put("agentNo", FlyangConfig.AGENT_NO);
		dataobj.put("merchantNo", merchantNo);
		String randomStr = UUID.randomUUID().toString();
		dataobj.put("randomStr", randomStr);
		dataobj.put("merchantInfo", merchantInfoJson);
		dataobj.put("signType", "MD5");
		String sign = MD5Util.getSign(dataobj, FlyangConfig.AGENT_SECRET);
		dataobj.put("sign", sign);

		String params = dataobj.toJSONString();
		log.info("银行api商家报备请求参数：{}", params);
		HttpUtil httpUtil = new HttpUtil();
		// 请求的报备商户url
		String url = FlyangConfig.API_BASE_URL + "/mgmt/modifyMerchant";
		resobj = httpUtil.doPostRequest(url, params);
		PrintWriter out = response.getWriter();
		out.print(resobj);
		out.close();
	}

	private void openMerchantProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 商户开通产品
		String errorMsg = "";
		String merchantNo = request.getParameter("merchantNo");
		String remitType = request.getParameter("remitType");
		String merchantProduct = request.getParameter("merchantProduct");

		JSONObject merchantProductJson = JSONObject.parseObject(merchantProduct);
		JSONObject wxProduct = merchantProductJson.getJSONObject("wxProduct");
		JSONObject alipayProduct = merchantProductJson.getJSONObject("alipayProduct");
		JSONObject flyangbj = merchantProductJson.getJSONObject("flyangbj");
		if (wxProduct != null && flyangbj != null) {
			wxProduct.put("onlineRate", "0.00240");
			if ("0".equals(remitType)) {
				wxProduct.put("d0SettleRate", "0.00260");
			}
			wxProduct.put("source", FlyangConfig.WX_SOURCE);
		} else if (wxProduct != null) {
			wxProduct.put("onlineRate", FlyangConfig.ONLINERATE);
			if ("0".equals(remitType)) {
				wxProduct.put("d0SettleRate", FlyangConfig.D0SETTLERATE);
			}
			wxProduct.put("source", FlyangConfig.WX_SOURCE);
		}
		if (alipayProduct != null) {
			alipayProduct.put("onlineRate", FlyangConfig.ONLINERATE);
			if ("0".equals(remitType)) {
				alipayProduct.put("d0SettleRate", FlyangConfig.D0SETTLERATE);
			}
		}
		// 请求报备到华夏银行的api接口
		JSONObject dataobj = new JSONObject();
		dataobj.put("agentNo", FlyangConfig.AGENT_NO);
		dataobj.put("merchantNo", merchantNo);
		dataobj.put("merchantProduct", merchantProductJson);
		String randomStr = UUID.randomUUID().toString();
		dataobj.put("randomStr", randomStr);
		dataobj.put("signType", "MD5");
		String sign = MD5Util.getSign(dataobj, FlyangConfig.AGENT_SECRET);
		dataobj.put("sign", sign);
		String params = dataobj.toJSONString();
		log.info("银行api商家报备请求参数：{}", params);
		HttpUtil httpUtil = new HttpUtil();
		// 请求的报备商户url
		String url = FlyangConfig.API_BASE_URL + "/mgmt/openMerchantProduct";
		JSONObject resultObj = httpUtil.doPostRequest(url, params);
		log.info("银行api商家返回参数：{}", resultObj.toJSONString());
		if (!"000000".equals(resultObj.getString("respCode"))) {
			errorMsg = resultObj.getString("respMsg");
		} else {
			resultObj.put("result", 1);
		}
		if (errorMsg.length() > 0) {
			resultObj.put("result", 0);
			resultObj.put("msg", errorMsg);
		}
		PrintWriter out = response.getWriter();
		out.print(resultObj);
		out.close();
	}

	private void createMerchantInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 商户入驻
		JSONObject resobj = new JSONObject();
		String errorMsg = "";
		String merchantInfo = request.getParameter("merchantInfo");
		String accidstr = request.getParameter("accid");
		String remitType = request.getParameter("remitType");
		String bizCateGory = request.getParameter("bizCateGory");
		int accid = 0;
		if (accidstr != null) {
			try {
				accid = Integer.parseInt(accidstr);
			} catch (Exception e) {
				// TODO: 不是整数
				errorMsg += "未检测到企业账号参数，请页面重试";
			}
		}
		if (accid > 0) {
			JSONObject merchantInfoJson = JSONObject.parseObject(merchantInfo);
			String imageyyzz = request.getParameter("imageyyzz");
			String imagefrsfza = request.getParameter("imagefrsfza");
			String imagefrsfzb = request.getParameter("imagefrsfzb");
			String imagezzjg = request.getParameter("imagezzjg");
			String imagejskcyrsfza = request.getParameter("imagejskcyrsfza");
			String imagejskcyrsfzb = request.getParameter("imagejskcyrsfzb");
			String imagedpzpmt = request.getParameter("imagedpzpmt");
			String imagedpzpsyt = request.getParameter("imagedpzpsyt");
			String imagedpzpjycs = request.getParameter("imagedpzpjycs");
			merchantInfoJson.put("remitT1", "0.00");
			if ("0".equals(remitType)) {
				merchantInfoJson.put("remitD0", "2.00");
			}
			// 请求报备到华夏银行的api接口
			JSONObject dataobj = new JSONObject();

			dataobj.put("agentNo", FlyangConfig.AGENT_NO);
			String randomStr = UUID.randomUUID().toString();
			dataobj.put("randomStr", randomStr);
			dataobj.put("merchantInfo", merchantInfoJson);
			dataobj.put("signType", "MD5");
			String sign = MD5Util.getSign(dataobj, FlyangConfig.AGENT_SECRET);
			dataobj.put("sign", sign);
			String params = dataobj.toJSONString();
			log.info("银行api商家报备请求参数：{}", params);
			HttpUtil httpUtil = new HttpUtil();
			// 请求的报备商户url
			String url = FlyangConfig.API_BASE_URL + "/mgmt/createMerchantInfo";
			JSONObject resultObj = httpUtil.doPostRequest(url, params);
			if (!"000000".equals(resultObj.getString("respCode"))) {
				errorMsg = resultObj.getString("respMsg");
			} else {
				resobj.put("result", 1);
				resobj.put("msg", "商家入驻成功，请等待审核……");
				resobj.put("merchantNo", resultObj.getString("merchantNo"));
				resobj.put("merchantKey", resultObj.getString("merchantKey"));
				SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
				String currentTime = format.format(new Date());// 获取当前时间值
				JSONObject datajson = new JSONObject();
				JSONObject signjson = new JSONObject();

				signjson.put("timestamp", currentTime);
				// 读取商户上传的信息，并且保存到蓝窗；
				datajson.put("flyang", "20150107");
				datajson.put("accid", accid);
				datajson.put("merchantno", resultObj.getString("merchantNo"));
				datajson.put("merchantkey", resultObj.getString("merchantKey"));
				datajson.put("fullname", merchantInfoJson.getString("fullName"));
				datajson.put("shortname", merchantInfoJson.getString("shortName"));
				datajson.put("servicephone", merchantInfoJson.getString("servicePhone"));
				datajson.put("businesslicense", merchantInfoJson.getString("businessLicense"));
				datajson.put("businesslicensetype", merchantInfoJson.getString("businessLicenseType"));
				// 联系人档案
				JSONObject contact = merchantInfoJson.getJSONObject("contactInfo");
				datajson.put("contactname", contact.getString("contactName"));
				datajson.put("contactphone", contact.getString("contactPhone"));
				datajson.put("contactidcard", contact.getString("contactIdCard"));
				datajson.put("contactpersontype", contact.getString("contactPersonType"));
				datajson.put("contactemail", contact.getString("contactEmail"));
				// 结算信息
				JSONObject bankinfo = merchantInfoJson.getJSONObject("bankCardInfo");
				datajson.put("bankaccountno", bankinfo.getString("bankAccountNo"));
				datajson.put("bankaccountname", bankinfo.getString("bankAccountName"));
				datajson.put("idcard", bankinfo.getString("idCard"));
				datajson.put("bankaccounttype", bankinfo.getString("bankAccountType"));
				datajson.put("bankaccountlineno", bankinfo.getString("bankAccountLineNo"));
				datajson.put("bankaccountaddress", bankinfo.getString("bankAccountAddress"));
				// 地址信息
				JSONObject addressInfo = merchantInfoJson.getJSONObject("addressInfo");
				datajson.put("province", addressInfo.getString("province"));
				datajson.put("city", addressInfo.getString("city"));
				datajson.put("district", addressInfo.getString("district"));
				datajson.put("address", addressInfo.getString("address"));
				// 产品申请
				datajson.put("remitt1", merchantInfoJson.getString("remitT1"));
				datajson.put("remitd0", merchantInfoJson.getString("remitD0"));
				datajson.put("bizcategory", bizCateGory);
				// 图片保存
				datajson.put("imageyyzz", imageyyzz);
				datajson.put("imagezzjg", imagezzjg);
				datajson.put("imagefrsfza", imagefrsfza);
				datajson.put("imagefrsfzb", imagefrsfzb);
				datajson.put("imagejskzp", imagefrsfzb);
				datajson.put("imagejskcyrsfza", imagejskcyrsfza);
				datajson.put("imagejskcyrsfzb", imagejskcyrsfzb);
				datajson.put("imagedpzpmt", imagedpzpmt);
				datajson.put("imagedpzpsyt", imagedpzpsyt);
				datajson.put("imagedpzpjycs", imagedpzpjycs);

				String fycontent = "data=" + URLEncoder.encode(datajson.toString(), "UTF-8") + "&sign="
						+ URLEncoder.encode(signjson.toString(), "UTF-8");
				url = FlyangConfig.API_JERP_URL + "/pub?action=updateaccregpay";
				JSONObject resobj1 = httpUtil.doPostRequest(url, fycontent);// 保存信息到蓝窗
				log.info("保存信息到蓝窗：" + resobj1.toString());
				resobj.put("fyResult", resobj1.getString("result"));
				resobj.put("fyMsg", resobj1.getString("msg"));
				resobj.put("fyRes", resobj1);
			}
		} else
			errorMsg += "未检测到企业账号参数，请页面重试吧";
		if (errorMsg.length() > 0) {
			resobj.put("result", 0);
			resobj.put("msg", errorMsg);
		}
		PrintWriter out = response.getWriter();
		out.print(resobj);
		out.close();
	}

	private void reCreateMerchantInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 商户入驻
		String merchantInfo = request.getParameter("merchantInfo");
		String remitType = request.getParameter("remitType");
		JSONObject merchantInfoJson = JSONObject.parseObject(merchantInfo);
		merchantInfoJson.put("remitT1", "0.00");
		if ("0".equals(remitType)) {
			merchantInfoJson.put("remitD0", "2.00");
		}
		// 请求报备到华夏银行的api接口
		JSONObject dataobj = new JSONObject();
		dataobj.put("agentNo", FlyangConfig.AGENT_NO);
		String randomStr = UUID.randomUUID().toString();
		dataobj.put("randomStr", randomStr);
		dataobj.put("merchantInfo", merchantInfoJson);
		dataobj.put("signType", "MD5");
		String sign = MD5Util.getSign(dataobj, FlyangConfig.AGENT_SECRET);
		dataobj.put("sign", sign);
		String params = dataobj.toJSONString();
		log.info("银行api商家报备请求参数：{}", params);
		HttpUtil httpUtil = new HttpUtil();
		// 请求的报备商户url
		String url = FlyangConfig.API_BASE_URL + "/mgmt/createMerchantInfo";
		JSONObject resultObj = httpUtil.doPostRequest(url, params);
		PrintWriter out = response.getWriter();
		out.print(resultObj);
		out.close();
	}

	private void queryMerchant(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 查询商户
		JSONObject resobj = new JSONObject();
		String errorMsg = "";
		JSONObject dataobj = new JSONObject();
		String merchantNo = request.getParameter("merchantNo");// 商户号
		if (merchantNo == null) {
			errorMsg = "请输入正确的商户号";
		} else {
			dataobj.put("merchantNo", merchantNo);
			String url = FlyangConfig.API_BASE_URL + "/mgmt/queryMerchant";
			QueryMerchantReqData req = new QueryMerchantReqData(FlyangConfig.AGENT_NO, merchantNo,
					FlyangConfig.AGENT_SECRET);
			JSONObject jsonObject = (JSONObject) JSON.toJSON(req);
			String params = jsonObject.toString();
			System.out.println("发送POST请求,请求参数" + params);
			HttpUtil httpUtil = new HttpUtil();
			JSONObject resultObj = httpUtil.doPostRequest(url, params);
			System.out.println("发送POST请求,返回参数" + resultObj);
			if (response == null || "".equals(resultObj.toString())) {
				throw new RuntimeException("返回参数为空");
			}
			System.out.println("返回结果" + resultObj);
			if ("000000".equals(resultObj.get("respCode"))) {
				String sign = resultObj.getString("sign");
				if (sign == null || "".equals(sign)) {
					throw new RuntimeException("签名为空");
				}
				System.out.println("验证返回参数的签名");
				boolean result = MD5Util.verifySign(resultObj, FlyangConfig.AGENT_SECRET, sign);
				if (result != true) {
					throw new RuntimeException("返回参数的签名验证失败");
				}
			}
			// QueryMerchantResData microPayResData = (QueryMerchantResData)
			// JSONObject.toBean(resultObj, QueryMerchantResData.class);
			resobj = resultObj;
		}
		if (errorMsg.length() > 0) {
			resobj.put("result", 0);
			resobj.put("msg", errorMsg);
		}
		PrintWriter out = response.getWriter();
		out.print(resobj);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
