package com.flyang.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flyang.fypay.FyPayConfig;
import com.flyang.fypay.MerchantConfig;
import com.flyang.tools.FlyangHttp;

import net.sf.json.JSONObject;
import tools.AppData;
import tools.DataBase;
import tools.ToolUtils;

/**
 * Servlet implementation class FypayService
 * 调用：支付 fypay
 */
@WebServlet(name = "fypay", urlPatterns = { "/fypay" })
public class FypayService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FypayService() {
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
		response.getWriter().append("Request only by POST!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		AppData appData = new AppData();
		appData.setqxpublic(request, response);
		// 读取参数
		String payser = request.getParameter("payser");
		if ("dopay".equals(payser)) {
			microPay(request, response, appData);
		} else if ("dorefund".equals(payser)) {
			refund(request, response, appData);
		} else if ("doquery".equals(payser)) {
			queryTrade(request, response, appData);
		}
	}

	private void queryTrade(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 查询
		JSONObject resObj = new JSONObject();
		int result = 0;
		String msg = "";
		try {
			JSONObject dataobj = DataBase.getObjFrom(request.getParameterMap());
			FlyangHttp fh = new FlyangHttp();
			String action = "getdealrecordbyno";
			fh.setHostIP(DataBase.JERPIP + "api?action=");
			JSONObject resobj1 = fh.FlyangdoPost(action, dataobj, appData);
			String payfs = request.getParameter("payfs");
			if (appData.isTotal(resobj1.toString())) {
				if (resobj1.getInt("total") > 0) {
					JSONObject row = resobj1.getJSONArray("rows").getJSONObject(0);
					// 更新流水
					JSONObject updatedataObj = new JSONObject();
					if (row.getString("statetag").equals("0")) {
						if (payfs.equals("0") || payfs.equals("1")) {
							String orderNo = row.getString("outtradeno");
							String zfnoteno = row.getString("noteno");
							updatedataObj.put("noteno", zfnoteno);
							MerchantConfig merchantConfig = new MerchantConfig(appData);
							String statetag = merchantConfig.getStatetag();
							if ("-1".equals(statetag)) {
								result = -4;
								msg = "您未申请开通任何支付产品！";
								return;
							} else if ("0".equals(statetag)) {
								result = -3;
								msg = "您的申请正在审核！";
								return;
							} else {
								String merchantNo = merchantConfig.getmerchantNo();
								String merchantKey = merchantConfig.getmerchantKey();
								JSONObject queryObj = new JSONObject();
								
								queryObj.put("merchantNo", merchantNo);
								queryObj.put("md5Key", merchantKey);
								queryObj.put("orderNo", orderNo);
								String queryUrl = FyPayConfig.FYPAY_URL + "?action=queryTrade";
								JSONObject queryResObj = fh.doPostRequest(queryUrl, ToolUtils.getContent(queryObj));
								if ("000000".equals(queryResObj.getString("respCode"))) {
									String tradeStatus = queryResObj.getString("tradeStatus");
									if ("100".equals(tradeStatus)) {
										String channelNo = queryResObj.getString("channelNo");
										String buyerId = queryResObj.getString("buyerId");
										updatedataObj.put("statetag", 1);
										updatedataObj.put("payidstr", channelNo);
										updatedataObj.put("payaccno", buyerId);
										result = 2;
										resObj.put("curr", row.getString("curr"));
										resObj.put("zfnoteno", row.getString("noteno"));
									} else if ("102".equals(tradeStatus) || "104".equals(tradeStatus)) {
										String emsg = queryResObj.getString("respMsg");
										updatedataObj.put("failedremark", emsg);
										updatedataObj.put("statetag", 2);
										result = 1;
									} else {
										result = 0;
										msg += "状态未知，请重试！";
									}
								} else {
									result = 1;
									updatedataObj.put("failedremark", queryResObj.getString("respMsg"));
									updatedataObj.put("statetag", 2);
									msg = queryResObj.getString("respMsg");
								}
							}
						} else if (payfs.equals("2")) {
							result = 1;
							msg += "未完成支付或退款，请使用终端扫码验证！";
						}
					} else {
						result = 2;
						resObj.put("curr", row.getString("curr"));
						resObj.put("zfnoteno", row.getString("noteno"));
						resObj.put("payfs", row.getString("payfs"));
					}
					JSONObject updateres = fh.FlyangdoPost("updatedealrecord", updatedataObj, appData);
					if (appData.isResult(updateres.toString())) {
						// querymsg += ",记账成功！" ;
					} else {
						result = 0;
						msg += ",记账失败！" + appData.msg;
					}
				}
			}
			result = 1;
		} finally {
			resObj.put("result", result);
			resObj.put("msg", msg);
			PrintWriter out = response.getWriter();
			out.println(resObj.toString());
			out.close();
		}
	}

	private void refund(HttpServletRequest request, HttpServletResponse response, AppData appData) throws IOException {
		// TODO 退款
		JSONObject resObj = new JSONObject();
		int result = 0;
		String msg = "";
		try {
			String ywtag = request.getParameter("ywtag");
			String payfs = request.getParameter("payfs");
			String noteno = request.getParameter("noteno");
			String outtradeno = request.getParameter("outtradeno").toUpperCase();
			String totalamt = request.getParameter("totalamt");
			String totalcurr = request.getParameter("totalcurr");
			String refundcurr = request.getParameter("refundcurr");
			String refundreseason = request.getParameter("refundreseaon");
			String subject = appData.housename + "购物退款";
			String body = "销售退货" + String.valueOf(Math.abs(Double.parseDouble(totalamt))) + "件商品 "
					+ String.valueOf(Math.abs(Double.parseDouble(refundcurr))) + "元;退货单号：" + noteno + ";退货原因："
					+ refundreseason;
			MerchantConfig merchantConfig = new MerchantConfig(appData);
			String statetag = merchantConfig.getStatetag();
			if ("-1".equals(statetag)) {
				result = -4;
				msg += "您未申请开通任何支付产品！";
			} else if ("0".equals(statetag)) {
				result = -3;
				msg += "您的申请正在审核！";
			} else {
				String merchantNo = merchantConfig.getmerchantNo();
				String merchantKey = merchantConfig.getmerchantKey();
				String randomNo = DataBase.getRandomNo();// 19位随机数
				String refundOrderNo = noteno + randomNo;
				// 退款流水
				JSONObject adddataObj = JSONObject.fromObject("{}");
				adddataObj.put("ywtag", ywtag);
				adddataObj.put("ywnoteno", noteno);
				adddataObj.put("payfs", payfs);
				adddataObj.put("curr", refundcurr);
				adddataObj.put("totalcurr", totalcurr);
				adddataObj.put("outtradeno", refundOrderNo);
				adddataObj.put("remark", subject + ";" + body);
				FlyangHttp fh = new FlyangHttp();
				fh.setHostIP(DataBase.JERPIP + "api?action=");
				JSONObject addresObj = fh.FlyangdoPost("adddealrecord", adddataObj, appData);
				if (appData.isResult(addresObj)) {
					String zfnoteno = addresObj.getString("noteno");// 支付流水的支付单号
					resObj.put("zfnoteno", zfnoteno);
					
					JSONObject updatedataObj = new JSONObject();
					updatedataObj.put("noteno", zfnoteno);
					// 退款
					String origOrderNo = outtradeno;
					JSONObject refundObj = new JSONObject();
					refundObj.put("merchantNo", merchantNo);
					refundObj.put("md5Key", merchantKey);
					refundObj.put("refundOrderNo", refundOrderNo);
					refundObj.put("amount", String.valueOf(Math.abs(Double.parseDouble(refundcurr))));
					refundObj.put("origOrderNo", origOrderNo);
					String fyrefundurl = FyPayConfig.FYPAY_URL + "?action=toRefund";
					JSONObject fypayresObj = fh.doPostRequest(fyrefundurl, ToolUtils.getContent(refundObj));
					if (!fypayresObj.has("result")&&"000000".equals(fypayresObj.getString("respCode"))) {
						String tradeNo = fypayresObj.getString("tradeNo");// 收银家平台流水号
						String respMsg = fypayresObj.getString("respMsg");// 退款描述
						updatedataObj.put("statetag", 1);
						updatedataObj.put("payidstr", tradeNo);
						updatedataObj.put("payaccno", respMsg);
						msg += "退款成功";
						result = 1;
					} else {
						String emsg = fypayresObj.getString("respMsg");
						msg += emsg;
						updatedataObj.put("failedremark", emsg);
						updatedataObj.put("statetag", 2);
						result = 0;
					}
					// 更新的支付记录
					JSONObject updateresObj = fh.FlyangdoPost("updatedealrecord", updatedataObj, appData);
					if (appData.isResult(updateresObj)) {
						msg += "记账成功！";
					} else {
						result = -1;
						msg += "退款成功，但是记账失败,请再点一次退款重新记账！";
					}
				} else {
					result = -2;
					msg += "未生成记账记录，无法退款！请重试！";
					return;
				}

			}
		} finally {
			resObj.put("result", result);
			resObj.put("msg", msg);
			PrintWriter out = response.getWriter();
			out.println(resObj.toString());
			out.close();
		}
	}

	private void microPay(HttpServletRequest request, HttpServletResponse response, AppData appData)
			throws IOException {
		// TODO 支付
		JSONObject resObj = new JSONObject();
		int result = 0;
		String msg = "";
		try {
			String ywtag = request.getParameter("ywtag");// 0=店铺零售，1=商场零售，2=线上
			String payfs = request.getParameter("payfs");// 0-支付宝 1-微信 2-拉卡拉 3-盛付通
			String payMethod = "";
			if ("0".equals(payfs)) {
				payMethod = "ALIPAY";
			} else if ("1".equals(payfs)) {
				payMethod = "WXPAY";
			} else {
				result = 0;
				msg += "请选择支付方式！";
				return;
			}
			String noteno = request.getParameter("noteno");
			if ("".equals(noteno) || noteno == null) {
				result = 0;
				msg += "单据号无效!";
				return;
			}
			String totalamt = request.getParameter("totalamt");
			String totalcurr = request.getParameter("totalcurr");
			String paycurr = request.getParameter("paycurr");
			String paycode = request.getParameter("paycode");
			String subject = appData.housename + "购物消费";
			String body = "购买" + totalamt + "件商品支付" + paycurr + "元;销售单号：" + noteno;
			MerchantConfig merchantConfig = new MerchantConfig(appData);
			String statetag = merchantConfig.getStatetag();
			if ("-1".equals(statetag)) {
				result = -4;
				msg += "您未申请开通任何支付产品！";
			} else if ("0".equals(statetag)) {
				result = -3;
				msg += "您的申请正在审核！";
			} else {
				String merchantNo = merchantConfig.getmerchantNo();
				String merchantKey = merchantConfig.getmerchantKey();
				if (!"".equals(payMethod)) {
					String randomNo = ToolUtils.getRandomNo();// 19位随机数
					String orderNo = noteno + randomNo;
					// 生成对应流水
					JSONObject adddataObj = new JSONObject();
					adddataObj.put("ywtag", ywtag); // 0=店铺零售，1=商场零售，2=线上,3=批发
					adddataObj.put("ywnoteno", noteno); // 业务单据号
					adddataObj.put("payfs", payfs); //
					adddataObj.put("curr", paycurr);
					adddataObj.put("totalcurr", totalcurr);
					adddataObj.put("outtradeno", orderNo);
					adddataObj.put("remark", subject + ";" + body);
					FlyangHttp fh = new FlyangHttp();
					fh.setHostIP(DataBase.JERPIP + "api?action=");
					JSONObject addresObj = fh.FlyangdoPost("adddealrecord", adddataObj, appData);
					if (appData.isResult(addresObj)) {
						String zfnoteno = addresObj.getString("noteno");// 支付流水的支付单号
						resObj.put("zfnoteno", zfnoteno);
						JSONObject updatedataObj = new JSONObject();
						updatedataObj.put("noteno", zfnoteno);
						// 支付
						JSONObject paydataobj = new JSONObject();
						paydataobj.put("orderNo", orderNo);
						paydataobj.put("merchantNo", merchantNo);
						paydataobj.put("md5Key", merchantKey);
						paydataobj.put("payMethod", payMethod);
						paydataobj.put("payByCredit", "TRUE");
						paydataobj.put("amount", paycurr);
						paydataobj.put("authCode", paycode);
						paydataobj.put("subject", subject);
						String fypayurl = FyPayConfig.FYPAY_URL + "?action=microPay";
						JSONObject fypayresObj = fh.doPostRequest(fypayurl, ToolUtils.getContent(paydataobj));
						if (fypayresObj.has("respCode")&&"000000".equals(fypayresObj.getString("respCode"))) {
							String channelNo = fypayresObj.getString("channelNo");// 微信支付宝流水号
							String buyerId = fypayresObj.getString("buyerId");// 支付账号
							// String bankType = fypayresObj.getString("bankType");//银行类型
							updatedataObj.put("statetag", 1);
							updatedataObj.put("payidstr", channelNo);
							updatedataObj.put("payaccno", buyerId);
							msg += "支付成功！";
							result = 1;
						} else {
							String emsg = fypayresObj.getString("respMsg");
							updatedataObj.put("failedremark", emsg);
							updatedataObj.put("statetag", 2);
							msg += emsg;
							result = 0;
						}
						// 更新的支付记录
						JSONObject updateresObj = fh.FlyangdoPost("updatedealrecord", updatedataObj, appData);
						if (appData.isResult(updateresObj)) {
							msg += "记账成功！";
						} else {
							result = -1;
							msg += "记账失败,请再点一次支付重新记账！";
						}
					} else {
						result = -2;
						msg += "未生成记账记录，无法支付！请重试！";
						return;
					}
				}
			}
		} finally {
			resObj.put("result", result);
			resObj.put("msg", msg);
			PrintWriter out = response.getWriter();
			out.println(resObj.toString());
			out.close();
		}

	}

}
