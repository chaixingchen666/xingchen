package com.flyang.yky;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import com.flyang.sdk.OpenApiClient;
//import com.sz1card1.sdk.log.LogUtils;


public class TestMain {

	/**author 李奎
	 * @param args
	 */
	public static void main(String[] args) {
			
		Map<String, Object> data = new HashMap<>();
		data.put("password", "4446064");
		data.put("memberGroupName", "银卡");
		data.put("userAccount", "10000");
		data.put("cardId", "10001");
		
		//Post异步获取数据
		System.out.println("data数据"+data);
		String responseOut = FYApiClientUtils.httpPost("Add_Member", data);
		System.out.println("解析的数据"+responseOut);
		
	}

}
