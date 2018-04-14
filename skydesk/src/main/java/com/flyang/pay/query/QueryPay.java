package com.flyang.pay.query;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.flyang.pay.model.builder.AlipayTradeQueryRequestBuilder;
import com.flyang.pay.model.result.AlipayF2FQueryResult;
import com.flyang.pay.service.AlipayTradeService;

import net.sf.json.JSONObject;

public class QueryPay {
	Log log = LogFactory.getLog("trade_query");
	private HashMap<String, String> querymap = new HashMap<>();
	
	public HashMap<String, String> getQuerymap() {
		return querymap;
	}

	public void setQuerymap(HashMap<String, String> querymap) {
		this.querymap = querymap;
	}

	public String queryPay(AlipayTradeService tradeService){
		JSONObject resobj = JSONObject.fromObject("{}");
		int queryres = 0;
		String querymsg = "";
		if(querymap.get("outTradeNo")!=null){
			// (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
			String outTradeNo = querymap.get("outTradeNo");
	        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
	        		.setOutTradeNo(outTradeNo);
			AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
			switch (result.getTradeStatus()) {
				case SUCCESS:
					log.info("查询返回该订单支付成功: )");
					AlipayTradeQueryResponse resp = result.getResponse();
					log.info(resp.getTradeStatus());
					log.info(resp.getFundBillList());
					queryres = 1;
					querymsg = "查询返回该订单支付成功: )";
					resobj.put("payidstr", resp.getOpenId());
					resobj.put("payaccno", resp.getBuyerLogonId());
					resobj.put("curr", resp.getTotalAmount());
					break;
				case FAILED:
					log.error("查询返回该订单支付失败!!!");
					AlipayTradeQueryResponse resp1 = result.getResponse();
					double curr = Double.parseDouble(resp1.getTotalAmount());
					if(curr>0){
						queryres = 1;
						querymsg = "查询返回该订单退款成功: )";
						resobj.put("payidstr", resp1.getOpenId());
						resobj.put("payaccno", resp1.getBuyerLogonId());
						resobj.put("curr", resp1.getTotalAmount());
					}else{
						queryres = 0;
						querymsg = "查询返回该订单支付失败!";
					}
					break;

				case UNKNOWN:
					log.error("系统异常，订单支付状态未知!!!");
					queryres = 0;
					querymsg = "系统异常，订单支付状态未知!";
					break;

				default:
					log.error("不支持的交易状态，交易返回异常!!!");
					queryres = 0;
					querymsg = "不支持的交易状态，交易返回异常!";
					break;
			}
		}else{
			queryres = 0;
			querymsg = "查询单号为空";
		}
		resobj.put("result", queryres);
		resobj.put("msg",  querymsg);
		return resobj.toString();
	}
}
