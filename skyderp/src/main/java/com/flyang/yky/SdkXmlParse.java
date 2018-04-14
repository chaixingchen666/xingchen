package com.flyang.yky;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SdkXmlParse {
	
	//获取读取xml的Element实体 
	private Element getXmlElement() {
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			InputStream in = OpenApiClient.class.getResourceAsStream("OpenApiData.xml");
			doc = reader.read(in);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc.getRootElement();
	}
	
	
	public String getXmlController(String action){
		Element root = getXmlElement();
		if(root == null){
			return null;
		}
		List<Element> subs = root.elements();
		
		for (int i = 0; i < subs.size(); i++) {
			Element e = subs.get(i);
			//System.out.println(" 第一层 ----->" + e.getName());
			if ("Action".equals(e.getName())) {
				String actionValue = e.attribute("name").getValue();
				String Controller = e.attribute("controller").getValue();
				if(actionValue.equals(action)){
					return Controller;
				}
			}
		}
		return null;
	}
	
	public  XmlBean xmlParse(String action){
		XmlBean xmlBean = new XmlBean();
		Element root = getXmlElement();
		if(root == null){
			return null;
		}
		//获取 actions的第一层 
		@SuppressWarnings("unchecked")
		List<Element> subs = root.elements();
		for (int i = 0; i < subs.size(); i++) {
			Element e = subs.get(i);
			//System.out.println(" 第一层 ----->" + e.getName());
			if ("Action".equals(e.getName())) {
				String actionValue = e.attribute("name").getValue();
				String Controller = e.attribute("controller").getValue();
				if(!actionValue.equals(action)){
					continue;
				}
				
				List<Element> actions = e.elements();
				if (actions != null && actions.size() > 0) {
					//获取第二层 action
					for (int j = 0; j < actions.size(); j++) {
						/*System.out.println(" 第二层 ---->>" + actions.size() + " "
								+ actions.get(j).getName() + "  " + actions.get(j).getTextTrim());*/
						String actionTag = actions.get(j).getName();
						if("Controller".equals(actionTag)){
							xmlBean.setControler(actions.get(j).getTextTrim());
						}
						
						//注释掉之前预留的对参数的判断
						/*if ("Datas".equals(actionTag)) {
							List<Element> datas = actions.get(j).elements();
							xmlBean.setDatas(xmlDates(datas));
						}*/
					}
				}
			}
		}

		return xmlBean;
	}
	
	//解析XML datas标签　并返回一个List
	private ArrayList<Map<String, Object>> xmlDates(List<Element> datas){
		ArrayList<Map<String, Object>> datesList = new ArrayList<>();
		for (int k = 0; k < datas.size(); k++) {
			//System.out.println("--------------->> 第三层"+ datas.size() + " "+ datas.get(k).getName());
			if ("Data".equals(datas.get(k).getName())) {
				List<Element> date = datas.get(k).elements();
				datesList.add(xmlDate(date));
			}
		}
		return datesList;
	}
	
	//解析xml下面的date标签
	private Map<String, Object> xmlDate(List<Element> date){
		Map<String, Object> dateMap = new HashMap<String, Object>();
		for (int m = 0; m < date.size(); m++) {
			String dateKey = date.get(m).getName();
			String dateValue = date.get(m).getTextTrim();
			dateMap.put(dateKey, dateValue);
		//	System.out.println(" -------->> 第四层 " + date.size() + " "+ date.get(m).getName() + " "+ date.get(m).getTextTrim());
		}
		return dateMap;
	}

}
