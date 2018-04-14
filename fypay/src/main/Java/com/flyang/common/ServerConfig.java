package com.flyang.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSONObject;
import com.flyang.utils.XmlTool;

public class ServerConfig {
	public static File f = new File(getRootPath() + "skydesk.xml");
//	public static File f = new File(getRootPath() + "skydeskzs.xml");

	public static JSONObject getIPJson(){
		 //字符流输出
		try {
	        @SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
	        String string=null;
	        StringBuffer sb = new StringBuffer();
	        while((string = reader.readLine())!=null){
	            sb.append(string);
	        }
	        JSONObject json=XmlTool.documentToJSONObject(sb.toString());
	        System.out.println(json);
	        return json;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String getRootPath() {
		String rootPath = "";
		String classPath = ServerConfig.class.getClassLoader().getResource("/").getPath();
		File rootfile = new File(classPath).getParentFile().getParentFile().getParentFile();
		rootPath = rootfile.getPath();
		return rootPath +"/";
	}

}
