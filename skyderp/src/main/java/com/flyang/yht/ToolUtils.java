package com.flyang.yht;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

public class ToolUtils {

	/**
	 * 获取unix时间戳
	 *
	 * @return
	 */
	public static Long getUnixTime() {
		Timestamp appointTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = df.parse(String.valueOf(appointTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		long s = date.getTime();
		return s;
	}

	/**
	 * 字典排序参数
	 *
	 * @return
	 */
	public static String getSignData(HashMap<String, String> params) {

		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);

		String value;

		for (String key : keys) {
			value = String.valueOf(params.get(key));
			if (sb.length() > 0) {
				sb.append("&" + key + "=" + value);
			} else {
				sb.append(key + "=" + value);

			}

		}
		return sb.toString();
	}

	//map 转json
	public static JSONObject getObjFrom(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		JSONObject dataObj = JSONObject.fromObject("{}");
		Set<Entry<String, String>> set = map.entrySet();// Map.entrySet
														// 方法返回映射的
														// collection
														// 视图，其中的元素属于此类
		for (Entry<String, String> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					String val = i.getValue();
					if (!val.equals("null"))
						dataObj.put(i.getKey(), val);
					// 参数格式要求更改
				}
			}
		}
		return dataObj;
	}

	public static HashMap<String, String> getParamsMap(Map<String, String[]> params) throws IOException {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		Set<Entry<String, String[]>> set = params.entrySet();// Map.entrySet
																// 方法返回映射的
																// collection
																// 视图，其中的元素属于此类
		for (Entry<String, String[]> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					String[] val = i.getValue();
					String s = val.length == 0 ? "" : val[0];
					paramMap.put(i.getKey(), s);
					// 参数格式要求更改
				}
			}
		}

		// if (DataBase.isCS)
		// System.out.println(paramMap.toString());
		return paramMap;
	}

}
