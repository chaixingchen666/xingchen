package com.flyang.pay.refund;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.flyang.pay.model.builder.AlipayTradeRefundRequestBuilder;
import com.flyang.pay.model.result.AlipayF2FRefundResult;
import com.flyang.pay.service.AlipayTradeService;

import net.sf.json.JSONObject;

public class RefundPay {
	private HashMap<String, String> refundmap = new HashMap<>();
	
	public HashMap<String, String> getRefundmap() {
		return refundmap;
	}

	public void setRefundmap(HashMap<String, String> refundmap) {
		this.refundmap = refundmap;
	}
	 Log log = LogFactory.getLog("trade_refund");
	public String refundPay(AlipayTradeService tradeService) {
		JSONObject resobj = JSONObject.fromObject("{}");
		if (refundmap.get("outTradeNo") != null) {

			// (必填) 支付宝内部订单号，需要退款交易的商户外部订单号
			String outTradeNo = refundmap.get("outTradeNo");

			// (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
			String refundAmount = refundmap.get("refundAmount");

			// (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
			// 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
			String outRequestNo = refundmap.get("outRequestNo");

			// (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
			String refundReason = refundmap.get("refundReason");

			// (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
			String storeId = refundmap.get("storeId");

			AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder().setOutTradeNo(outTradeNo)
					.setRefundAmount(refundAmount).setRefundReason(refundReason).setOutRequestNo(outRequestNo)
					.setStoreId(storeId);

			AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
			
			switch (result.getTradeStatus()) {
			case SUCCESS:
				log.info("支付宝退款成功: )");
				resobj.put("result", 1);
				resobj.put("msg", "退款成功");
				resobj.put("openid", result.getResponse().getTradeNo());
				resobj.put("logid", result.getResponse().getBuyerLogonId());
				break;

			case FAILED:
				log.error("支付宝退款失败!!!");
				resobj.put("result", 0);
				resobj.put("msg", "支付宝退款失败!"+result.getResponse().getSubMsg());
				break;

			case UNKNOWN:
				log.error("系统异常，订单退款状态未知!!!");
				resobj.put("result", 0);
				resobj.put("msg", "系统异常，订单退款状态未知!"+result.getResponse().getSubMsg());
				break;

			default:
				log.error("不支持的交易状态，交易返回异常!!!");
				resobj.put("result", 0);
				resobj.put("msg",  "不支持的交易状态，交易返回异常!"+result.getResponse().getSubMsg());
				break;
			}
		}else{
			resobj.put("result", 0);
			resobj.put("msg",  "退款单号为空!");
		}
		return resobj.toString();
	}
}
