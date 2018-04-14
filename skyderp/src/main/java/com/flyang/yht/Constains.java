package com.flyang.yht;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.common.tool.Func;

public class Constains {

	//银货通
	public static String HOST_SliverUrl = "";// "http://61.164.59.232:9093/yht-api-uzmd";//url（开发）
	//	public static final String HOST_SliverUrl = "http://61.164.59.230:9093/yht-api-uzmd";//url（生产）

	static {
		File file = new File(Func.getRootPath() + "options.properties");
		FileInputStream is = null;
		Properties prop = new Properties();
		try {
			is = new FileInputStream(file);
			prop.load(is);
			HOST_SliverUrl = prop.getProperty("yht_url");
			System.out.println("HOST_SliverUrl=" + HOST_SliverUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			HOST_SliverUrl = "http://61.164.59.232:9093/yht-api-uzmd";
		}
	}

	public static final String SliverappKey = "123e51b3eecf458a9ba4591b9542ee53";//商户号

	public static final String APP_SECRET = "0a186c6c085640fea38d42ad23420b9701be2476ffe9ce348399b184a829591e";//商户密钥

	public static final String KEY_SALT_KEY = "_salt_key";

	public static final String KEY_TIMESTAMP = "_timestamp";

	public static final String HOSTID = "118.178.229.131"; //服务器id（测试）

}
