package com.flyang.pay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.api.response.AlipayTradePayResponse;
import com.flyang.pay.model.GoodsDetail;
import com.flyang.pay.model.builder.AlipayTradePayRequestBuilder;
import com.flyang.pay.model.result.AlipayF2FPayResult;
import com.flyang.pay.service.AlipayTradeService;


public class Pay {
	private static Log log = LogFactory.getLog(Pay.class);

	// 支付信息
	private HashMap<String, String> paymap = new HashMap<>();

	// 商品明细列表，需填写购买商品详细信息，
	private List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

	public HashMap<String, String> getPaymap() {
		return paymap;
	}

	public void setPaymap(HashMap<String, String> paymap) {
		this.paymap = paymap;
	}

	public List<GoodsDetail> getGoodsDetailList() {
		return goodsDetailList;
	}

	public void setGoodsDetailList(List<GoodsDetail> goodsDetailList) {
		this.goodsDetailList = goodsDetailList;
	}

	// 测试当面付2.0支付
	public String pay(AlipayTradeService service) {
		// (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
		// 需保证商户系统端不能重复，建议通过数据库sequence生成，
		String outTradeNo = paymap.get("outTradeNo");

		// (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
		String subject = paymap.get("subject");

		// (必填) 订单总金额，单位为元，不能超过1亿元
		// 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
		String totalAmount = paymap.get("totalAmount");

		// (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
		String authCode = paymap.get("authCode"); // 条码示例，286648048691290423

		// (可选，根据需要决定是否使用)
		// 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
		// 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
//		String discountableAmount = paymap.get("discountableAmount");

		// (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
		// 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
//		String undiscountableAmount = paymap.get("undiscountableAmount");

		// 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
		// 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
//		String sellerId = paymap.get("sellerId");

		// 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
		String body = paymap.get("body");

		// 商户操作员编号，添加此参数可以为商户操作员做销售统计
		String operatorId = paymap.get("operatorId");

		// (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
		String storeId = paymap.get("storeId");
		/*
		 * // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
		 * String providerId = "2088100200300400500"; ExtendParams extendParams
		 * = new ExtendParams();
		 * extendParams.setSysServiceProviderId(providerId);
		 */

		// 支付超时，线下扫码交易定义为5分钟
		String timeoutExpress = "5m";

		/*
		 * // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
		 * GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx面包",
		 * 1000, 1); // 创建好一个商品后添加至商品明细列表 goodsDetailList.add(goods1);
		 * 
		 * // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件 GoodsDetail goods2 =
		 * GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
		 * goodsDetailList.add(goods2);
		 */

		// String appAuthToken = "应用授权令牌";//根据真实值填写

		// 创建条码支付请求builder，设置请求参数
		AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
				// .setAppAuthToken(appAuthToken)
				.setOutTradeNo(outTradeNo).setSubject(subject).setAuthCode(authCode).setTotalAmount(totalAmount)
				.setStoreId(storeId)
//				.setUndiscountableAmount(undiscountableAmount)
//				.setDiscountableAmount(discountableAmount)
				.setBody(body)
				.setOperatorId(operatorId)
				// .setExtendParams(extendParams)
//				.setSellerId(sellerId)
//				.setGoodsDetailList(goodsDetailList)
				.setTimeoutExpress(timeoutExpress);

		// 调用tradePay方法获取当面付应答
		AlipayF2FPayResult result = service.tradePay(builder);
		AlipayTradePayResponse atpres = result.getResponse();

		switch (result.getTradeStatus()) {
		case SUCCESS:
			log.info("支付宝支付成功: )");
			return "{\"result\":1,\"msg\":\"支付宝支付成功\""
					+ ",\"PAYIDSTR\":\""+atpres.getTradeNo()+"\""
					+ ",\"PAYACCNO\":\""+atpres.getBuyerLogonId()+"\""
							+ "}";
		case FAILED:
			log.error("支付宝支付失败!!!");
			return "{\"result\":0,\"msg\":\"支付宝支付失败!!!"+atpres.getSubMsg()+"\"}";

		case UNKNOWN:
			log.error("系统异常，订单状态未知!!!");
			return "{\"result\":0,\"msg\":\"系统异常，订单状态未知!!!"+atpres.getSubMsg()+"\"}";

		default:
			log.error("不支持的交易状态，交易返回异常!!!");
			return "{\"result\":0,\"msg\":\"不支持的交易状态，交易返回异常!!!"+atpres.getSubMsg()+"\"}";
		}
	}

}
