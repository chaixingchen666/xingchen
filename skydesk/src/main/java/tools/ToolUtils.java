package tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

public class ToolUtils {
	public static String getBase64String(String value) {
		byte[] text = null;
		try {
			text = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(text).trim().replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t", "");
	}
	public static String getContent(JSONObject dataobj) throws IOException {
		String content = null;
		@SuppressWarnings("unchecked")
		Set<Entry<String, String>> set = dataobj.entrySet();// Map.entrySet
															// 方法返回映射的
															// collection
															// 视图，其中的元素属于此类
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> i : set) {// 将参数解析为"name=tom&age=21"的模式
			if (i != null) {
				if (i.getValue() != null) {
					if (!(i.getValue().equals("null")))
						sb.append(i.getKey()).append("=").append(encode(i.getValue())).append("&");
					// 参数格式要求更改
				}
			}
		}
		if (sb.length() > 1) {
			content = sb.substring(0, sb.length() - 1);
		}
		return content;
	}
	
	public static String encode(String str) throws IOException {
		// TODO 编码
		return (str == null || str.equals("") || str.equals("null") || str.equals(null)) ? ""
				: URLEncoder.encode(str, "UTF-8");
	}
	/**
	 * 时间戳+7位随机数
	 * 
	 * @return 13位随机数
	 */
	public static String getRandomNo() {
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		String currentTime = format.format(new Date());// 获取当前时间值
		int randomno = (int) (Math.random() * 10000000);
		String randno = currentTime + String.valueOf(randomno) + "00000000";
		return randno.substring(0, 13);
	}
	
}
