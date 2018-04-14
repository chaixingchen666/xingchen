package com.flyang.pay.config;

import com.flyang.pay.service.AlipayMonitorService;
import com.flyang.pay.service.AlipayTradeService;
import com.flyang.pay.service.impl.AlipayMonitorServiceImpl;
import com.flyang.pay.service.impl.AlipayTradeServiceImpl;
import com.flyang.pay.service.impl.AlipayTradeWithHBServiceImpl;

import net.sf.json.JSONObject;

public class PayService {
	// 支付宝当面付2.0服务
	private AlipayTradeService tradeService;
	// 支付宝当面付2.0服务（集成了交易保障接口逻辑）
	private AlipayTradeService tradeWithHBService;

	// 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
	private AlipayMonitorService monitorService;

	public AlipayTradeService getTradeService() {
		return tradeService;
	}

	public void setTradeService(AlipayTradeService tradeService) {
		this.tradeService = tradeService;
	}

	public AlipayTradeService getTradeWithHBService() {
		return tradeWithHBService;
	}

	public void setTradeWithHBService(AlipayTradeService tradeWithHBService) {
		this.tradeWithHBService = tradeWithHBService;
	}

	public AlipayMonitorService getMonitorService() {
		return monitorService;
	}

	public void setMonitorService(AlipayMonitorService monitorService) {
		this.monitorService = monitorService;
	}

	/**
	 * 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
	 * Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
	 */
	public boolean init(JSONObject payKeyObj) {
		try {
			PayConfigs configs = new PayConfigs();
			configs.setPayKeyObj(payKeyObj);
			configs.init();
			if(configs.getAppid()==null)
				return false;
			if (configs.getAppid().length() != 16) {
				return false;
			}
			// Configs.init("zfbinfo_sx.properties");

			/**
			 * 使用Configs提供的默认参数 AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
			 */
			
			tradeService = new AlipayTradeServiceImpl.ClientBuilder().build(configs);

			// 支付宝当面付2.0服务（集成了交易保障接口逻辑）
			tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build(configs);

			/**
			 * 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数
			 * 否则使用代码中的默认设置
			 */
			monitorService = new AlipayMonitorServiceImpl.ClientBuilder().setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("utf-8").setFormat("json")
					.build(configs);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

}
