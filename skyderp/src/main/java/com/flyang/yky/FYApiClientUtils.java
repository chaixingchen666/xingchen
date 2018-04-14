package com.flyang.yky;

import java.util.Map;

//网络请求框架
public class FYApiClientUtils {

	public static String httpPost(String resAction, Map<String, Object> map) {

		//		String OpenId = "B4410473271D49BE8D95FF5030629AD0";
		//		String Secret = "CU2KJ1";
		String OpenId = Constains.OpenId;
		String Secret = Constains.Secret;

		OpenApiClient apiClient = new OpenApiClient(OpenId, Secret);
		String jsonObject = apiClient.CallHttpPost(resAction, map, 1).toString();
		return jsonObject;
	}
}
