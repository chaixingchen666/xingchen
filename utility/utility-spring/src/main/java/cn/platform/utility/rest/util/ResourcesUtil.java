package cn.platform.utility.rest.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @comment 读取资源文件信息
 * @author wuyao
 * @date 2014年12月8日 下午6:40:06
 * @version 1.0.0
 * @see
 */
public class ResourcesUtil extends PropertyPlaceholderConfigurer {
	private static Map<String, Object> resMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory process, Properties props) {
		super.processProperties(process, props);
		resMap = new HashMap<>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			resMap.put(keyStr, value);
		}
	}

	public static String getProperty(Object name) {
		return resMap.get(String.valueOf(name)).toString();
	}

	/**
	 * 获取指定资源文件信息
	 * @param propName 资源文件路径
	 * @param jsonName 资源文件中key
	 */
	public static String getProperty(String propName,String jsonName) {
		try {
			Resource resource = new ClassPathResource(propName);
			Properties props = PropertiesLoaderUtils.loadProperties(resource);

			Map<String, Object> map = new HashMap<>();
			for (Object key : props.keySet()) {
				String keyStr = key.toString();
				String value = props.getProperty(keyStr);
				map.put(keyStr, value);
			}
			return map.get(jsonName).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取模板消息并填充参数
	 * 
	 * @param messageCode  messageCode
	 * @param dataMap dataMap
	 * @return Map<String,Object> key:title,content
	 */
	public static Map<String, Object> getMessageContent(String messageCode, Map<String, Object> dataMap) {
		Map<String, Object> messageMap = new HashMap<>();
		String title = "";
		String content = getProperty(messageCode);
		if (content.indexOf("|") > -1) {
			title = content.substring(0, content.indexOf("|", 0));
			content = content.substring(content.indexOf("|") + 1, content.length());
		}
		String key;
		String value;
		int mgLength = content.length();
		for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
			key = entry.getKey();
			int start = content.indexOf("{" + key + "}");
			if (start < 0 || entry.getValue() == null) {
				continue;
			}
			value = entry.getValue().toString();
			int end = content.indexOf("}", start) + 1;
			content = content.substring(0, start) + value + content.substring(end, mgLength);
			mgLength = content.length();
		}

		messageMap.put("title", title);
		messageMap.put("content", content);
		return messageMap;
	}
}
